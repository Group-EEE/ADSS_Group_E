package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Pair;
import BussinessLayer.HRModule.Objects.Schedule;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ActiveSchedulesDAO extends DAO {

    private static ActiveSchedulesDAO _activeSchedulesDAO = null;

    private SchedulesDAO _schedulesDAO;
    public static final String StoreIdColumnName = "storeID";
    public static final String ScheduleIDColumnName = "scheduleID";
    private final HashMap<Integer, Schedule> storeIDtoActiveSchedule;
    private final HashMap<String, Schedule> storeNametoActiveSchedule;

    private ActiveSchedulesDAO(){
        super("ActiveSchedules");
        storeIDtoActiveSchedule = new HashMap<>();
        storeNametoActiveSchedule = new HashMap<>();
    }

    private void getschedulesDAO(){
        if (_schedulesDAO == null)
            _schedulesDAO = SchedulesDAO.getInstance();
    }

    public static ActiveSchedulesDAO getInstance(){
        if (_activeSchedulesDAO == null)
            _activeSchedulesDAO = new ActiveSchedulesDAO();
        return _activeSchedulesDAO;
    }

    public boolean Insert(Object pairObj){
        getschedulesDAO();
        if (pairObj == null)
            throw new IllegalArgumentException("Invalid pair object active schedule");

        boolean res = true;
        int storeID = (int)(((Pair)pairObj).getKey());
        int scheduleID = (int)(((Pair)pairObj).getValue());

        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES(?, ?) "
                , _tableName, StoreIdColumnName, ScheduleIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, storeID);
            pstmt.setInt(2, scheduleID);
            pstmt.executeUpdate();
            storeIDtoActiveSchedule.put(storeID, _schedulesDAO.getSchedule(scheduleID));
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }


    public boolean insertActiveSchedule(int storeID, int scheduleID){
        if (isThereActiveSchedule(storeID))
            return Update(StoreIdColumnName,ScheduleIDColumnName,String.valueOf(storeID),String.valueOf(scheduleID));
        return Insert(new Pair<>(storeID,scheduleID));
    }

    public Pair<Integer,Integer> convertReaderToObject(ResultSet rs) throws SQLException {
        Integer storeID = rs.getInt(1);
        Integer scheduleID = rs.getInt(2);
        return new Pair<>(storeID, scheduleID);
    }

    @Override
    public boolean Delete(Object pairObj) {
        int storeID = (int)(((Pair)pairObj).getKey());
        int scheduleID = (int)(((Pair)pairObj).getValue());
        boolean res = true;
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", _tableName, StoreIdColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, storeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    public Schedule getSchedule(int storeID){
        if (storeIDtoActiveSchedule.containsKey(storeID)) {
            return storeIDtoActiveSchedule.get(storeID);
        }
        List<String> listSchedulesID = SelectString(ScheduleIDColumnName,makeList(StoreIdColumnName),makeList(String.valueOf(storeID)));
        if (listSchedulesID.size() > 1)
            throw new IllegalArgumentException("There are more than one active schedule for storeID: " + storeID);
        if (listSchedulesID.size() == 1) {
            Schedule schedule = _schedulesDAO.getSchedule(Integer.valueOf(listSchedulesID.get(0)));
            storeIDtoActiveSchedule.put(storeID, schedule);
            storeNametoActiveSchedule.put(StoresDAO.getInstance().getStoreNameByID(storeID), schedule);
            return schedule;
        }
        return null;
    }

    public Schedule getSchedule(String storeName){
        if (storeNametoActiveSchedule.containsKey(storeName)) {
            return storeNametoActiveSchedule.get(storeName);
        }
        int storeID = StoresDAO.getInstance().getStoreIDByName(storeName);
        List<String> listSchedulesID = SelectString(ScheduleIDColumnName,makeList(StoreIdColumnName),makeList(String.valueOf(storeID)));
        if (listSchedulesID.size() > 1)
            throw new IllegalArgumentException("There are more than one active schedule for storeName: " + storeName);
        if (listSchedulesID.size() == 1) {
            Schedule schedule = SchedulesDAO.getInstance().getSchedule(Integer.valueOf(listSchedulesID.get(0)));
            storeIDtoActiveSchedule.put(storeID, schedule);
            storeNametoActiveSchedule.put(storeName, schedule);
            return schedule;
        }
        return null;
    }

    public boolean isThereActiveSchedule(int storeID){
        if(storeIDtoActiveSchedule.containsKey(storeID))
            return true;
        List<String> res = SelectString(ScheduleIDColumnName,makeList(StoreIdColumnName),makeList(String.valueOf(storeID)));
        if (res.size() > 1)
            throw new IllegalArgumentException("There are more than one active schedule for storeID: " + storeID);
        return res.get(0).equals(String.valueOf(storeID));
    }

    public boolean loadSchedules(LocalDate localDate){
        String sql = "SELECT storeID,scheduleID FROM Schedules WHERE startDateOfWeek = ?";
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
