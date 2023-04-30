package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.Shift;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;

public class SchedulesDAO extends DAO {
    private static SchedulesDAO _schedulesDAO = null;
    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String StartDateOfWeekColumnName = "startDateOfWeek";
    public static final String StoreId = "StoreID";

    private final HashMap<Integer, Schedule> storeIDtoActiveSchedule;

    private SchedulesDAO(){
        super("Schedules");
        storeIDtoActiveSchedule = new HashMap<>();
    }

    public static SchedulesDAO getInstance(){
        if (_schedulesDAO == null)
            _schedulesDAO = new SchedulesDAO();
        return _schedulesDAO;
    }

    public boolean Insert(Object scheduleObj){
        if (scheduleObj == null)
            throw new IllegalArgumentException("Invalid object schedule");

        Schedule schedule = (Schedule)scheduleObj;
        boolean res = true;

        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}) VALUES(?, ?, ?) "
                , _tableName, ScheduleIDColumnName, StartDateOfWeekColumnName, StoreId);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, schedule.getScheduleID());
            pstmt.setDate(2, schedule.getStartDateOfWeek());
            pstmt.setInt(3, schedule.getStoreID());
            pstmt.executeUpdate();
            if (storeIDtoActiveSchedule.containsKey(schedule.getStoreID()))
                storeIDtoActiveSchedule.replace(schedule.getStoreID(), schedule);
            else
                storeIDtoActiveSchedule.put(schedule.getStoreID(), schedule);
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    @Override
    public boolean Delete(Object objectSchedule) {
        Schedule schedule = (Schedule)objectSchedule;
        boolean res = true;
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", _tableName, ScheduleIDColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, schedule.getScheduleID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }


    @Override
    public Schedule convertReaderToObject(ResultSet rs) throws SQLException {
        Shift[] shifts = new Shift[14];
        ShiftsDAO shiftsDAO = ShiftsDAO.getInstance();
        for (int i = 0; i < 14; i++) {
            shifts = shiftsDAO.get
        }

        Employee employee = new Schedule();
        employeesCache.put(employee.getID(), employee);
        return employee;
    }
}
