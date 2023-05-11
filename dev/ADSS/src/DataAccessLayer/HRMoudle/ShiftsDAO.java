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
        Shift shift = new Shift(scheduleID, shiftID, ShiftType.valueOf(strShiftType), startTime, endTime, date);
        return shift;
    }

    public boolean deleteShift(int scheduleID, int shiftID) {
        if (!deleteRequiredRoles(scheduleID,shiftID))
            return false;
        if (!deleteInquiredEmployees(scheduleID,shiftID))
            return false;
        return delete(_tableName,makeList(ScheduleIDColumnName, ShiftIDColumnName),makeList(scheduleID, shiftID));
    }

    public boolean deleteShifts(int scheduleID) {
        if (!deleteRequiredRoles(scheduleID))
            return false;
        if (!deleteInquiredEmployees(scheduleID))
            return false;
        return delete(_tableName,makeList(ScheduleIDColumnName),makeList(scheduleID));
    }

    public boolean deleteRequiredRoles(int scheduleID){
        return delete("RequiredRolesToEmployees",makeList(ScheduleIDColumnName),makeList(scheduleID));
    }
    public boolean deleteRequiredRoles(int scheduleID,int shiftID){
        return delete("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID));
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

    public List<Integer> getInquireEmployees(int scheduleID, int shiftID){
        return selectT("InquiredEmployees",EmployeeIDColumnName,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID),Integer.class);
    }

    public boolean insertInquiredEmployee(int scheduleID, int shiftID, int employeeID){
        return insert("InquiredEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,EmployeeIDColumnName),makeList(scheduleID,shiftID,employeeID));
    }

    public boolean insertRequiredRole(int scheduleID, int shiftID, String roleStr){
        return insert("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,"roleType"),makeList(scheduleID,shiftID,roleStr));
    }

    public List<String> getRequiredRoles(int scheduleID, int shiftID){
        return selectT("RequiredRolesToEmployees",RoleTypeColumnName,makeList(ScheduleIDColumnName,ShiftIDColumnName),makeList(scheduleID,shiftID),String.class);
    }

    public boolean removeRequiredRole(int scheduleID, int shiftID, String roleStr){
        return delete("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,"roleType"),makeList(scheduleID,shiftID,roleStr));
    }

    public boolean insertAssignedEmployee(int scheduleID, int shiftID, String roleStr, int employeeID){
        return insert("RequiredRolesToEmployees",makeList(ScheduleIDColumnName,ShiftIDColumnName,"roleType",EmployeeIDColumnName),makeList(scheduleID,shiftID,roleStr,employeeID));
    }

    public HashMap<String,Integer> getAssignedEmployees(int scheduleID, int shiftID){
        String sql = MessageFormat.format("SELECT roleType, employeeID FROM {0} WHERE {1} = ? AND {2} = ? AND employeeID IS NOT NULL"
                , "RequiredRolesToEmployees", "scheduleID", "shiftID"
        );
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
}
