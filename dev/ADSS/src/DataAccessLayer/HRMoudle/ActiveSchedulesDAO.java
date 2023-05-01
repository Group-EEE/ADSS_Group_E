package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Pair;
import BussinessLayer.HRModule.Objects.Schedule;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

public class ActiveSchedulesDAO extends DAO {

    private static ActiveSchedulesDAO _activeSchedulesDAO = null;

    private final SchedulesDAO _schedulesDAO;
    public static final String StoreIdColumnName = "storeID";
    public static final String ScheduleIDColumnName = "scheduleID";
    private final HashMap<Integer, Schedule> storeIDtoActiveSchedule;
    private final HashMap<String, Schedule> storeNametoActiveSchedule;

    private ActiveSchedulesDAO(){
        super("ActiveSchedules");
        _schedulesDAO = SchedulesDAO.getInstance();
        storeIDtoActiveSchedule = new HashMap<>();
        storeNametoActiveSchedule = new HashMap<>();
    }

    public static ActiveSchedulesDAO getInstance(){
        if (_activeSchedulesDAO == null)
            _activeSchedulesDAO = new ActiveSchedulesDAO();
        return _activeSchedulesDAO;
    }

    public boolean Insert(Object pairObj){
        if (pairObj == null)
            throw new IllegalArgumentException("Invalid pair object active schedule");

        boolean res = true;
        int storeID = (int)(((Pair)pairObj).getKey());
        int ScheduleID = (int)(((Pair)pairObj).getValue());

        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES(?, ?) "
                , _tableName, StoreIdColumnName, ScheduleIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, storeID);
            pstmt.setInt(2, ScheduleID);
            pstmt.executeUpdate();
            storeIDtoActiveSchedule.put(storeID, _schedulesDAO.getSchedule(ScheduleID));
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    public boolean Insert(int storeID){
        boolean res = true;
        String sql = MessageFormat.format("INSERT INTO {0} ({1}) VALUES(?) "
                , _tableName, StoreIdColumnName);
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

    public boolean createNewSchedule(int storeID, int scheduleID){
        //check if storeID is a key
        List<Pair<Integer,Integer>> stores = Select(makeList(StoreIdColumnName), makeList(String.valueOf(storeID)));
        if (stores.size() == 0)
            return Insert(new Pair<>(storeID, scheduleID));
        //storeID is already a key


        storeIDtoActiveSchedule.put(storeID, _schedulesDAO.getSchedule(scheduleID));
        return Update(StoreIdColumnName,ScheduleIDColumnName,String.valueOf(storeID), String.valueOf(scheduleID));
    }

    public Pair<Integer,Integer> convertReaderToObject(ResultSet rs) throws SQLException {
        Integer storeID = rs.getInt(1);
        Integer scheduleID = rs.getInt(2);
        return new Pair<>(storeID, scheduleID);
    }

    @Override
    public boolean Delete(Object pairObj) {
        int storeID = (int)(((Pair)pairObj).getKey());
        int ScheduleID = (int)(((Pair)pairObj).getValue());
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
        //TODO: retrived from db
        return null;
    }

    public Schedule getSchedule(String storeName){
        if (storeNametoActiveSchedule.containsKey(storeName)) {
            return storeNametoActiveSchedule.get(storeName);
        }
        //TODO: retrived from db
        return null;
    }
}
