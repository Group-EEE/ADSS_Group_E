package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Pair;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeesToStoreDAO extends DAO {
    private static EmployeesToStoreDAO _employeeToStoreDAO = null;
    private final HashMap<Integer, List<Integer>> storeCache;
    private final HashMap<Integer,List<Integer>> employeeCache;
    public static final String EmployeeIDColumnName = "employeeID";
    public static final String StoreIDColumnName = "storeID";

    private EmployeesToStoreDAO(){
        super("EmployeesToStores");
        storeCache = new HashMap<>();
        employeeCache = new HashMap<>();
    }

    public static EmployeesToStoreDAO getInstance(){
        if (_employeeToStoreDAO == null)
            _employeeToStoreDAO = new EmployeesToStoreDAO();
        return _employeeToStoreDAO;
    }

    @Override
    public boolean Insert(Object pairObj) {
        boolean res = true;
        Pair pair = (Pair) pairObj;
        int employeeID = (int)pair.getKey();
        int storeID = (int)pair.getValue();
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES(?, ?) "
                , _tableName, EmployeeIDColumnName, StoreIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.setInt(2, storeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        if (storeCache.containsKey(storeID)) {
            storeCache.get(storeID).add(employeeID);
        } else { // If the key does not exist, create a new list and add the value to it
            List<Integer> list = new ArrayList<>();
            list.add(employeeID);
            storeCache.put(storeID, list);
        }

        if (employeeCache.containsKey(employeeID)) {
            employeeCache.get(employeeID).add(storeID);
        } else { // If the key does not exist, create a new list and add the value to it
            List<Integer> list = new ArrayList<>();
            list.add(storeID);
            storeCache.put(employeeID, list);
        }
        return res;
    }

    @Override
    public boolean Delete(Object pairObj) {
        boolean res = true;
        Pair pair = (Pair) pairObj;
        int employeeID = (int)pair.getKey();
        int storeID = (int)pair.getValue();

        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? AND {2} = ?"
                , _tableName, EmployeeIDColumnName, StoreIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, employeeID);
            pstmt.setInt(2, storeID);
            pstmt.executeUpdate();
            if (storeCache.get(storeID).contains(employeeID))
                storeCache.get(storeID).remove(employeeID);
            if (employeeCache.get(employeeID).contains(storeID))
                employeeCache.get(employeeID).remove(storeID);

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    /**
     * @param id - the id of the employee or the store
     * @param isEmployee - true if the id is of an employee, false if it's of a store
     * @return true if delete was successful, false otherwise
     */
    public boolean Delete(int id, boolean isEmployee){
        boolean res = true;
        String sql;
        if (isEmployee)
            sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ?", _tableName, EmployeeIDColumnName);
        else
            sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ?", _tableName, StoreIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            if (isEmployee) {
                for (int storeID : employeeCache.get(id)) {
                    storeCache.get(storeID).remove(id);
                }
                employeeCache.remove(id);
            }
            else {
                for(int employeeID : storeCache.get(id)){
                    employeeCache.get(employeeID).remove(id);
                }
                storeCache.remove(id);
            }

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    @Override
    public Pair<Integer,Integer> convertReaderToObject(ResultSet rs) throws SQLException {
        return new Pair<>(rs.getInt(1),rs.getInt(2));
    }

    public List<Integer> getEmployeesByStoreID(int storeID){
        if (storeCache.containsKey(storeID))
            return storeCache.get(storeID);

        List<Integer> list = new ArrayList<>();
        String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?"
                , _tableName, StoreIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, storeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pair<Integer,Integer> pair = convertReaderToObject(rs);
                list.add(pair.getKey());
            }
            storeCache.put(storeID, list);
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    public boolean checkIfEmployeeInStore(int employeeID, int storeID){
        if (employeeCache.get(employeeID).contains(storeID) && storeCache.get(storeID).contains(employeeID)){
            return true;
        }
        String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ? AND {2} = ?"
                , _tableName, EmployeeIDColumnName, StoreIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.setInt(2, storeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pair<Integer,Integer> pair = convertReaderToObject(rs);
                if (pair.getKey() == employeeID && pair.getValue() == storeID){
                    storeCache.get(storeID).add(employeeID);
                    employeeCache.get(employeeID).add(storeID);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return false;
    }


}
