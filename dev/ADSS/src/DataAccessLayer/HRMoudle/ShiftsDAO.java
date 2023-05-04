package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ShiftsDAO extends DAO {
    private static ShiftsDAO _shiftsDAO = null;

    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String ShiftIDColumnName = "shiftID";
    public static final String ShiftTypeColumnName = "shiftType";
    public static final String StartTimeColumnName = "shiftStartTime";
    public static final String EndTimeColumnName = "shiftEndTime";
    public static final String DateColumnName = "date";
    public static final String ApprovedColumnName = "approved";
    public static final String RejectedColumnName = "rejected";

    private ShiftsDAO() {
        super("Shifts");
    }

    public static ShiftsDAO getInstance() {
        if (_shiftsDAO == null)
            _shiftsDAO = new ShiftsDAO();
        return _shiftsDAO;
    }

    @Override
    public boolean Insert(Object objectShift) {
        Shift shift = (Shift) objectShift;
        //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}, {4}, {5}, {6} ,{7},{8}) VALUES(?, ?, ?, ?, ?, ?, ?,?) "
                , _tableName, ScheduleIDColumnName, ShiftIDColumnName, ShiftTypeColumnName, StartTimeColumnName,EndTimeColumnName, DateColumnName, ApprovedColumnName, RejectedColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, shift.getScheduleID());
            pstmt.setInt(2, shift.getShiftID());
            pstmt.setString(3, shift.getShiftType().toString());
            pstmt.setInt(4, shift.getStartHour());
            pstmt.setInt(5, shift.getEndHour());
            pstmt.setString(6, shift.getDate().format(formatters));
            pstmt.setBoolean(7, shift.isApproved());
            pstmt.setBoolean(8, shift.isRejected());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("A PRIMARY KEY constraint failed"))
                throw new IllegalArgumentException("An shift with this ID already exists");
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    @Override
    public boolean Delete(Object objectShift) {
        Shift shift = (Shift) objectShift;
        int scheduleID = shift.getScheduleID();
        int shiftID = shift.getShiftID();
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? AND {2} = ?", _tableName, ScheduleIDColumnName,ShiftIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleID);
            pstmt.setInt(1, shiftID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    public boolean Delete(int scheduleID) {
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ?", _tableName, ScheduleIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    public void setStartTime(int scheduleID,int shiftID, int startTime) {
        Update(StartTimeColumnName, startTime, makeList(ScheduleIDColumnName,ShiftIDColumnName), makeList(String.valueOf(scheduleID),String.valueOf(shiftID)));
    }

    public void setEndTime(int scheduleID,int shiftID, int endTime) {
        Update(EndTimeColumnName, endTime, makeList(ScheduleIDColumnName,ShiftIDColumnName), makeList(String.valueOf(scheduleID),String.valueOf(shiftID)));
    }

    @Override
    public Shift convertReaderToObject(ResultSet rs) throws SQLException {
        return new Shift(rs.getInt(1), rs.getInt(2), ShiftType.toEnum(rs.getString(3)), rs.getInt(4), rs.getInt(5), parseLocalDate(rs.getString(6)));
    }


    /**
     * @param scheduleID - the schedule id
     * @return - list of all the shifts in the schedule
     *
     */
    public List<Shift> getShiftsByScheduleID(int scheduleID) {
        List<Shift> listShift = Select(makeList(ScheduleIDColumnName), makeList(String.valueOf(scheduleID)));
        for (Shift shift : listShift){
            //getInquiredEmployees returns list of employee ids, so we need to convert it employees by EmployeeDAO
            for (int employeeID : getInquireEmployees(scheduleID, shift.getShiftID())) {
                shift.addInquiredEmployee(EmployeesDAO.getInstance().getEmployee(employeeID));
            }
        }
        return listShift;
    }

    public List<Integer> getInquireEmployees(int scheduleID, int shiftID){
        String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ? AND {2} = ?"
                , "InquiredEmployees", "scheduleID", "shiftID"
        );
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleID);
            pstmt.setInt(2, shiftID);
            ResultSet rs = pstmt.executeQuery();
            List<Integer> list = new ArrayList<>();
            if (rs.next()) {
                list.add(rs.getInt(3));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return null;
    }

    public boolean InsertInquired(int scheduleID, int shiftID, int employeeID){
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}) VALUES(?, ?, ?) "
                , "InquiredEmployees", "scheduleID", "shiftID", "employeeID"
        );


        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleID);
            pstmt.setInt(2, shiftID);
            pstmt.setInt(3, employeeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed: InquiredEmployees.scheduleID, InquiredEmployees.shiftID, InquiredEmployees.employeeID"))
                throw new IllegalArgumentException("Employee already inquired");
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }
}
