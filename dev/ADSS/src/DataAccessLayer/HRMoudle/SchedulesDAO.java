package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.Shift;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class SchedulesDAO extends DAO {
    private static SchedulesDAO _schedulesDAO = null;
    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String StartDateOfWeekColumnName = "startDateOfWeek";
    public static final String StoreIDColumnName = "StoreID";
    private int _scheduleIDcache = 0;


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
        if (schedule.getScheduleID() > _scheduleIDcache)
            throw new IllegalArgumentException("Invalid schedule ID");

        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}) VALUES(?, ?, ?) "
                , _tableName, ScheduleIDColumnName, StoreIDColumnName, StartDateOfWeekColumnName );
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, schedule.getScheduleID());
            pstmt.setInt(2, schedule.getStoreID());
            pstmt.setString(3, schedule.getStartDateOfWeek().format(formatters));
            pstmt.executeUpdate();
            _scheduleIDcache++;
        } catch (SQLException e) {
            if (e.getMessage().contains("A PRIMARY KEY constraint failed"))
                throw new IllegalArgumentException("A schedule with this ID already exists");
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    @Override
    public boolean Delete(Object objectSchedule) {
        Schedule schedule = (Schedule)objectSchedule;
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", _tableName, ScheduleIDColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, schedule.getScheduleID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }


    @Override
    public Schedule convertReaderToObject(ResultSet rs) throws SQLException {
        ShiftsDAO shiftsDAO = ShiftsDAO.getInstance();
        List<Shift> shifts = shiftsDAO.getShiftsByScheduleID(rs.getInt(1));
        return new Schedule(rs.getInt(1), parseLocalDate(rs.getString(3)), rs.getInt(2), shifts);
    }

    public Schedule getSchedule(LocalDate date, int storeID) {
        List<Schedule> result = Select(makeList(StartDateOfWeekColumnName, StoreIDColumnName), makeList(date.format(formatters), String.valueOf(storeID)));
        if (result.size() == 0)
            throw new IllegalArgumentException("Could not find schedule for date " + date.format(formatters) + " and store " + storeID);
        return result.get(0);

    }

    public Schedule getSchedule(int scheduleID) {
        List<Schedule> result = Select(makeList(ScheduleIDColumnName), makeList(String.valueOf(scheduleID)));
        if (result.size() == 0)
            throw new IllegalArgumentException("Could not find schedule with id " + scheduleID);
        return result.get(0);

    }

    public int getmaxID(){
        if (_scheduleIDcache != 0)
            return _scheduleIDcache;
        List<ResultSet> rsList = Select();
        int max = 0;
        for (ResultSet rs : rsList) {
            try {
                if (rs.getInt(1) > max)
                    max = rs.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        _scheduleIDcache = max;
        return _scheduleIDcache;
    }
}
