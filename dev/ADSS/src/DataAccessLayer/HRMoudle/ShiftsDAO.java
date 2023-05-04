package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
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
        return false;
    }

    @Override
    public Shift convertReaderToObject(ResultSet rs) throws SQLException {
        return new Shift(rs.getInt(1), rs.getInt(2), ShiftType.toEnum(rs.getString(3)), rs.getInt(4), rs.getInt(5), parseLocalDate(rs.getString(6)));
    }


    public List<Shift> getShiftsByScheduleID(int scheduleID) {
        return Select(makeList(ScheduleIDColumnName), makeList(String.valueOf(scheduleID)));
    }
}
