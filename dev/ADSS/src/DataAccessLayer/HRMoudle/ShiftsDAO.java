package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShiftsDAO extends DAO {
    private static ShiftsDAO _shiftsDAO = null;

    private final String ScheduleIDColumnName = "scheduleID";
    private final String ShiftIDColumnName = "shiftID";
    private final String ShiftTypeColumnName = "shiftType";
    private final String StartTimeColumnName = "shiftStartTime";
    private final String EndTimeColumnName = "shiftEndTime";
    private final String DateColumnName = "date";
    private final String ApprovedColumnName = "approved";
    private final String RejectedColumnName = "rejected";

    //RequiredRolesToEmployees table
    private final String RoleTypeColumnName = "roleType";
    private final String EmployeeIDColumnName = "employeeID";
    private final String MustBeFilledColumnName = "mustBeFilled";
    private ShiftsDAO() {
        super("Shifts");
    }

    public static ShiftsDAO getInstance() {
        if (_shiftsDAO == null)
            _shiftsDAO = new ShiftsDAO();
        return _shiftsDAO;
    }

    public Shift insertShift(int scheduleID, int shiftID, String strShiftType, int startTime, int endTime, LocalDate date) {
        insert(_tableName,makeList(ScheduleIDColumnName, ShiftIDColumnName, ShiftTypeColumnName, StartTimeColumnName, EndTimeColumnName, DateColumnName),
                makeList(scheduleID, shiftID, strShiftType, startTime, endTime, date.format(formatters)));
        return new Shift(scheduleID, shiftID, ShiftType.valueOf(strShiftType), startTime, endTime, date);
    }

    public boolean deleteShift(int scheduleID, int shiftID) {
        boolean res = deleteRequiredRoles(scheduleID,shiftID);
        res = res && deleteInquiredEmployees(scheduleID,shiftID);
        res = res && delete(_tableName,makeList(ScheduleIDColumnName, ShiftIDColumnName),makeList(scheduleID, shiftID));
        return res;
    }

    public boolean deleteShifts(int scheduleID) {
        boolean res = deleteRequiredRoles(scheduleID);
        res = res && deleteInquiredEmployees(scheduleID);
        res = res && deleteRequiredRoles(scheduleID);
        res = res && delete(_tableName,makeList(ScheduleIDColumnName),makeList(scheduleID));
        return res;
    }

    public boolean updateRejected(int scheduleID, int shiftID, boolean rejected) {
        return update(_tableName,RejectedColumnName,rejected,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID));
    }

    public boolean updateApproved(int scheduleID, int shiftID, boolean rejected) {
        return update(_tableName,ApprovedColumnName,rejected,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID));
    }

    public boolean getApproved(int scheduleID, int shiftID) {
        List<Integer> boolList = selectT(_tableName,ApprovedColumnName,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID),Integer.class);
        if (boolList == null || boolList.size() == 0)
            return false;
        if (boolList.get(0) == 1)
            return true;
        return false;
    }
    public boolean getRejected(int scheduleID, int shiftID) {
        List<Integer> boolList = selectT(_tableName,ApprovedColumnName,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID),Integer.class);
        if (boolList == null || boolList.size() == 0)
            return false;
        if (boolList.get(0) == 1)
            return true;
        return false;
    }

    public boolean deleteRequiredRoles(int scheduleID){
        return delete("RequiredRolesToEmployees",makeList(ScheduleIDColumnName),makeList(scheduleID));
    }
    public boolean deleteRequiredRoles(int scheduleID,int shiftID){
        return delete("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID));
    }

    public boolean deleteInquiredEmployee(int scheduleID,int shiftID,int employeeID){
        return delete("InquiredEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,EmployeeIDColumnName),makeList(scheduleID,shiftID,employeeID));
    }
    public boolean deleteInquiredEmployees(int scheduleID){
        return delete("InquiredEmployees",makeList(ScheduleIDColumnName),makeList(scheduleID));
    }
    public boolean deleteInquiredEmployees(int scheduleID,int shiftID){
        return delete("InquiredEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID));
    }

    public boolean setStartTime(int scheduleID,int shiftID, int startTime) {
        return update(_tableName,StartTimeColumnName,startTime,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID));
    }

    public boolean setEndTime(int scheduleID,int shiftID, int endTime) {
        return update(_tableName,EndTimeColumnName,endTime,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID));
    }

    @Override
    public Shift convertReaderToObject(ResultSet rs) throws SQLException {
        return new Shift(rs.getInt(1), rs.getInt(2), ShiftType.valueOf(rs.getString(3)), rs.getInt(4), rs.getInt(5), parseLocalDate(rs.getString(6)));
    }

    /**
     * @param scheduleID - the schedule id
     * @return - list of all the shifts in the schedule
     */
    public List<Shift> getShiftsByScheduleID(int scheduleID) {
        return select(_tableName,makeList(ScheduleIDColumnName),makeList(scheduleID));
    }

    public List<String> getMustBeFilledRole(int scheduleID,int shiftID){
        return selectT("RequiredRolesToEmployees",RoleTypeColumnName,makeList(ScheduleIDColumnName,ShiftIDColumnName, MustBeFilledColumnName),makeList(scheduleID,shiftID,true),String.class);
    }

    public List<Integer> getInquireEmployees(int scheduleID, int shiftID){
        return selectT("InquiredEmployees",EmployeeIDColumnName,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID),Integer.class);
    }

    public boolean insertInquiredEmployee(int scheduleID, int shiftID, int employeeID){
        return insert("InquiredEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,EmployeeIDColumnName),makeList(scheduleID,shiftID,employeeID));
    }

    public boolean insertRequiredRole(int scheduleID, int shiftID, String roleStr, Boolean mustBeFilled){
        return insert("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,RoleTypeColumnName,MustBeFilledColumnName),makeList(scheduleID,shiftID,roleStr,mustBeFilled));
    }

    public List<String> getRequiredRoles(int scheduleID, int shiftID){
        return selectT("RequiredRolesToEmployees",RoleTypeColumnName,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID),String.class);
    }

    public boolean removeRequiredRole(int scheduleID, int shiftID, String roleStr){
        return delete("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,RoleTypeColumnName),makeList(scheduleID,shiftID,roleStr));
    }

    public boolean insertAssignedEmployee(int scheduleID, int shiftID, String roleStr, int employeeID){
        if (selectExists("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,RoleTypeColumnName),makeList(scheduleID,shiftID,roleStr)))
            return update("RequiredRolesToEmployees",EmployeeIDColumnName,employeeID,makeList(ScheduleIDColumnName,ShiftIDColumnName,RoleTypeColumnName),makeList(scheduleID,shiftID,roleStr));
        return insert("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,RoleTypeColumnName,EmployeeIDColumnName),makeList(scheduleID,shiftID,roleStr,employeeID));
    }

    public boolean updateMustBeFilledRole(int scheduleID, int shiftID, String roleStr, boolean mustBeFilled){
        return update("RequiredRolesToEmployees",MustBeFilledColumnName,mustBeFilled,makeList(ScheduleIDColumnName,ShiftIDColumnName,RoleTypeColumnName),makeList(scheduleID,shiftID,roleStr));
    }

    public HashMap<String,Integer> getAssignedEmployees(int scheduleID, int shiftID){
        String sql = MessageFormat.format("SELECT roleType, employeeID FROM {0} WHERE {1} = ? AND {2} = ? AND employeeID IS NOT NULL"
                , "RequiredRolesToEmployees", "scheduleID", "shiftID"
        );
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleID);
            pstmt.setInt(2, shiftID);
            ResultSet rs = pstmt.executeQuery();
            HashMap<String,Integer> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
            return map;
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return null;
    }

    public boolean have_warehouse_by_schedule_and_shift(int shift_id, int schedule_id){
        String query = "SELECT * FROM RequiredRolesToEmployees WHERE scheduleID = ? AND shiftID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, schedule_id);
            statement.setInt(2, shift_id);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                if (res.getString(3).equals("Warehouse") && res.getBoolean(5) == true){
                    return true;
                }
            }
        } catch (SQLException e) {
        }
        return false;
    }

}
