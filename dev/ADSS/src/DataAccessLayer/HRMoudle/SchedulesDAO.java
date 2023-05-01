package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.Shift;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

public class SchedulesDAO extends DAO {
    private static SchedulesDAO _schedulesDAO = null;
    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String StartDateOfWeekColumnName = "startDateOfWeek";
    public static final String StoreId = "StoreID";



    private SchedulesDAO(){
        super("Schedules");
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
        ShiftsDAO shiftsDAO = ShiftsDAO.getInstance();
        List<Shift> shifts = shiftsDAO.getShiftsByScheduleID(rs.getInt(1));
        Schedule schedule = new Schedule(rs.getInt(1), rs.getDate(2).toLocalDate(), rs.getInt(3), shifts);
        return schedule;
    }

    public Schedule getSchedule(int storeID) {
        if (storeIDtoActiveSchedule.containsKey(rs.getInt(1))){
            return storeIDtoActiveSchedule.get(rs.getInt(1));
        }
        List<Employee> result = Select(makeList(IDColumnName), makeList(String.valueOf(id)));
        if (result.size() == 0)
            throw new IllegalArgumentException("Employee " + id + " does not exist");
        Employee employee = result.get(0);
        employeesCache.put(id, employee);
        return employee;

    }
}
