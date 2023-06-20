package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Schedule;
import DataAccessLayer.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SchedulesDAO extends DAO {
    private static SchedulesDAO _schedulesDAO = null;

    //schedule table
    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String StoreNameColumnName = "storeName";
    public static final String StartDateOfWeekColumnName = "startDateOfWeek";
    private static int _scheduleIDcache =-1;
    private HashMap<Integer,Schedule> scheduleCache;

    private SchedulesDAO(){
        super("Schedules");
        _scheduleIDcache = getScheduleMaxID();
        scheduleCache = new HashMap<>();
    }

    public static SchedulesDAO getInstance(){
        if (_schedulesDAO == null)
            _schedulesDAO = new SchedulesDAO();
        return _schedulesDAO;
    }

    public boolean insertSchedule(int scheduleID, String storeName, LocalDate startDateOfWeek){
        if (scheduleID > _scheduleIDcache)
            throw new IllegalArgumentException("Invalid schedule ID");
        Schedule schedule = new Schedule(scheduleID, storeName, startDateOfWeek);
        scheduleCache.put(scheduleID, schedule);
        return insert(_tableName, makeList(ScheduleIDColumnName, StoreNameColumnName, StartDateOfWeekColumnName),
                makeList(scheduleID, storeName, startDateOfWeek.format(formatters)));
    }

    public boolean deleteSchedule(int scheduleID) {
        if (scheduleCache.containsKey(scheduleID))
            scheduleCache.remove(scheduleID);
        return delete(_tableName, makeList(ScheduleIDColumnName), makeList(scheduleID));
    }

    @Override
    public Schedule convertReaderToObject(ResultSet rs) throws SQLException {
        return new Schedule(rs.getInt(1),  rs.getString(2),parseLocalDate(rs.getString(3)));
    }

    public int getScheduleMaxID(){
        if (_scheduleIDcache != -1)
            return _scheduleIDcache++;
        List<String> listMaxScheduleID = selectMaxString(_tableName, ScheduleIDColumnName, null, null);
        if (listMaxScheduleID.size() == 0)
            _scheduleIDcache = 0;
        else
            _scheduleIDcache =Integer.valueOf(listMaxScheduleID.get(0))+1;
        return _scheduleIDcache++;
    }


    public Schedule getSchedule(LocalDate date, String storeName) {
        List<Schedule> result = new ArrayList<>();
//        LocalDate temp_date = null;
//        for (int i = 0; i < 7; i++) {
//            temp_date = date.minusDays(i);
            result = select(_tableName, makeList(StartDateOfWeekColumnName, StoreNameColumnName), makeList(date.format(formatters), storeName));
//            if (result.size() > 0){
//                break;
//            }
//        }
        if (result.size() == 0)
            throw new IllegalArgumentException("Could not find schedule for date " + date.format(formatters) + " and store " + storeName);
        Schedule schedule = result.get(0);
        if (scheduleCache.containsKey(schedule.getScheduleID()))
            return scheduleCache.get(schedule.getScheduleID());
        scheduleCache.put(schedule.getScheduleID(), schedule);
        return schedule;

    }

    public Schedule getSchedule(int scheduleID) {
        if (scheduleCache.containsKey(scheduleID))
            return scheduleCache.get(scheduleID);
        List<Schedule> result = select(_tableName,makeList(ScheduleIDColumnName), makeList(String.valueOf(scheduleID)));
        if (result.size() == 0)
            throw new IllegalArgumentException("Could not find schedule with id " + scheduleID);
        scheduleCache.put(scheduleID, result.get(0));
        return result.get(0);

    }

    public boolean hasScheduleInCache(int scheduleID){
        return scheduleCache.containsKey(scheduleID);
    }

    /**
     * for testing scheduleController. needs to check load a schedule without cache.
     */
    public boolean removeCache(){
        scheduleCache.clear();
        return true;
    }

//    public boolean loadSchedules(LocalDate localDate){
//        String sql = "SELECT storeName, scheduleID FROM Schedules WHERE startDateOfWeek = ?";
//        try (Connection connection = DriverManager.getConnection(url);
//             PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate("DELETE FROM " + _tableName);
//
//            pstmt.setString(1, localDate.format(formatters));
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Insert(convertReaderToObject(rs));
//            }
//        } catch (SQLException e) {
//            System.out.println("Got Exception:");
//            System.out.println(e.getMessage());
//            System.out.println(sql);
//            return false;
//        }
//        return true;
//    }
}
