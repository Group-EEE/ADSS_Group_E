package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Pair;
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

    //schedule table
    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String StoreNameColumnName = "storeName";
    public static final String StartDateOfWeekColumnName = "startDateOfWeek";
    private static int _scheduleIDcache =-1;

    //ActiveSchedules table
    public static final String ActiveStoreNameColumnName = "storeName";
    public static final String ActiveScheduleIDColumnName = "scheduleID";
    private final HashMap<String, Schedule> storeNametoActiveSchedule;


    private SchedulesDAO(){
        super("Schedules");
        storeNametoActiveSchedule = new HashMap<>();
        _scheduleIDcache = getScheduleMaxID();
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
                , _tableName, ScheduleIDColumnName, StoreNameColumnName, StartDateOfWeekColumnName );
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, schedule.getScheduleID());
            pstmt.setString(2, schedule.getStoreName());
            pstmt.setString(3, schedule.getStartDateOfWeek().format(formatters));
            pstmt.executeUpdate();
            boolean res = insertActiveSchedule(schedule.getStoreName(),schedule);
            if (!res)
                return false;
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

    public boolean insertActiveSchedule(String storeName, Schedule schedule){
        boolean res = isThereActiveSchedule(storeName);
        if (res)
            return Update("ActiveSchedules",StoreNameColumnName,ScheduleIDColumnName,storeName,String.valueOf(schedule.getScheduleID()));
        return InsertActive(storeName,schedule);
    }

    public boolean InsertActive(String storeName, Schedule schedule){
        boolean res = true;

        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES(?, ?) "
                , "ActiveSchedules", StoreNameColumnName, ScheduleIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, storeName);
            pstmt.setInt(2, schedule.getScheduleID());
            pstmt.executeUpdate();
            storeNametoActiveSchedule.put(storeName, schedule);
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
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", _tableName, ScheduleIDColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, schedule.getScheduleID());
            pstmt.executeUpdate();

            //delete the active schedules if there is
            DeleteActive(schedule.getStoreName());
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    public boolean DeleteActive(String storeName){
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", "ActiveSchedules", StoreNameColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, storeName);
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
        return new Schedule(rs.getInt(1),  parseLocalDate(rs.getString(3)), rs.getString(2));
    }

    public Pair<Integer,Integer> convertReaderToObjectActive(ResultSet rs) throws SQLException {
        Integer storeName = rs.getInt(1);
        Integer scheduleID = rs.getInt(2);
        return new Pair<>(storeName, scheduleID);
    }



    public int getScheduleMaxID(){
        if (_scheduleIDcache != -1)
            return _scheduleIDcache++;
        List<String> listMaxScheduleID = SelectMaxString(_tableName, ScheduleIDColumnName, null, null);
        if (listMaxScheduleID.size() == 0)
            _scheduleIDcache = 0;
        else
            _scheduleIDcache =Integer.valueOf(listMaxScheduleID.get(0));
        _scheduleIDcache++;
        return _scheduleIDcache;
    }

    public Schedule getSchedule(String storeName){
        if (storeNametoActiveSchedule.containsKey(storeName))
            return storeNametoActiveSchedule.get(storeName);
        List<String> listSchedulesID = SelectString("ActiveSchedules",ScheduleIDColumnName,makeList(StoreNameColumnName),makeList(storeName));
        if (listSchedulesID.size() > 1)
            throw new IllegalArgumentException("There are more than one active schedule for storeName: " + storeName);
        if (listSchedulesID.size() == 1) {
            Schedule schedule = getSchedule(Integer.valueOf(listSchedulesID.get(0)));
            storeNametoActiveSchedule.put(storeName, schedule);
            return schedule;
        }
        return null;
    }

    public Schedule getSchedule(LocalDate date, String storeName) {
        List<Schedule> result = Select(makeList(StartDateOfWeekColumnName, StoreNameColumnName), makeList(date.format(formatters), storeName));
        if (result.size() == 0)
            throw new IllegalArgumentException("Could not find schedule for date " + date.format(formatters) + " and store " + storeName);
        return result.get(0);

    }

    public Schedule getSchedule(int scheduleID) {
        List<Schedule> result = Select(makeList(ScheduleIDColumnName), makeList(String.valueOf(scheduleID)));
        if (result.size() == 0)
            throw new IllegalArgumentException("Could not find schedule with id " + scheduleID);
        return result.get(0);

    }

    public boolean isThereActiveSchedule(String storeName){
        if(storeNametoActiveSchedule.containsKey(storeName))
            return true;
        List<String> res = SelectString("ActiveSchedules",ScheduleIDColumnName,makeList(StoreNameColumnName),makeList(storeName));
        if (res.size() > 1)
            throw new IllegalArgumentException("There are more than one active schedule for storeName: " + storeName);
        return res.size() != 0;
    }

    public boolean loadSchedules(LocalDate localDate){
        String sql = "SELECT storeName, scheduleID FROM Schedules WHERE startDateOfWeek = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + _tableName);

            pstmt.setString(1, localDate.format(formatters));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Insert(convertReaderToObject(rs));
            }
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }
}
