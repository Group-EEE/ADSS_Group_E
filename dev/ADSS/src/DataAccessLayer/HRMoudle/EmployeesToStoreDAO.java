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
    private final HashMap<String, List<Integer>> storeCache;
    private final HashMap<Integer,List<String>> employeeCache;
    public static final String EmployeeIDColumnName = "employeeID";
    public static final String StoreNameColumnName = "storeName";

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

    public boolean insertEmployeeToStore(int employeeID, String storeName) {
        if (employeeID < 0)
            throw new IllegalArgumentException("Invalid employee id");
        if (employeeCache.containsKey(employeeID) && employeeCache.get(employeeID).contains(storeName))
            throw new IllegalArgumentException("Employee already belongs to this store");
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        if (storeCache.containsKey(storeName) && storeCache.get(storeName).contains(employeeID)) {
            throw new IllegalArgumentException("store already contains this employee");
        }
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES(?, ?) "
                , _tableName, EmployeeIDColumnName, StoreNameColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.setString(2, storeName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("A PRIMARY KEY constraint failed"))
                throw new IllegalArgumentException("Employee already belongs to this store");
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        if (storeCache.containsKey(storeName)) {
            storeCache.get(storeName).add(employeeID);
        } else { // If the key does not exist, create a new list and add the value to it
            List<Integer> list = new ArrayList<>();
            list.add(employeeID);
            storeCache.put(storeName, list);
        }

        if (employeeCache.containsKey(employeeID)) {
            employeeCache.get(employeeID).add(storeName);
        } else { // If the key does not exist, create a new list and add the value to it
            List<String> list = new ArrayList<>();
            list.add(storeName);
            employeeCache.put(employeeID, list);
        }
        return true;
    }

    public boolean deleteEmployeeFromStore(int employeeID, String storeName) {
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? AND {2} = ?"
                , _tableName, EmployeeIDColumnName, StoreNameColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, employeeID);
            pstmt.setString(2, storeName);
            pstmt.executeUpdate();
            if (storeCache.get(storeName).contains(employeeID))
                storeCache.get(storeName).remove(employeeID);
            if (employeeCache.get(employeeID).contains(storeName))
                employeeCache.get(employeeID).remove(storeName);

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    public boolean deleteEmployee(int employeeID){
        return delete(_tableName, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean deleteStore(String storeName){
        return delete(_tableName, makeList(StoreNameColumnName), makeList(storeName));
    }

    @Override
    public Pair<Integer,String> convertReaderToObject(ResultSet rs) throws SQLException {
        return new Pair<>(rs.getInt(1),rs.getString(2));
    }

    public List<Integer> getEmployeesByStoreName(String storeName){
        if (storeCache.containsKey(storeName))
            return storeCache.get(storeName);

        List<Integer> list = new ArrayList<>();
        String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?"
                , _tableName, StoreNameColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, storeName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pair<Integer,String> pair = convertReaderToObject(rs);
                list.add(pair.getKey());
            }
            storeCache.put(storeName, list);
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    public List<String> getStoreNameByEmployeeID(int employeeID){
        List<String> list = new ArrayList<>();
        String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?"
                , _tableName, EmployeeIDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pair<Integer,String> pair = convertReaderToObject(rs);
                list.add(pair.getValue());
            }
            employeeCache.put(employeeID, list);
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    public boolean checkIfEmployeeInStore(int employeeID, String storeName){
        if (employeeCache.containsKey(employeeID) && storeCache.containsKey(storeName)){
            if (employeeCache.get(employeeID).contains(storeName) && storeCache.get(storeName).contains(employeeID))
                return true;
        }
        String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ? AND {2} = ?"
                , _tableName, EmployeeIDColumnName, StoreNameColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.setString(2, storeName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pair<Integer,String> pair = convertReaderToObject(rs);
                if (pair.getKey() == employeeID && pair.getValue().equals(storeName)){
                    if (!storeCache.containsKey(storeName))
                        storeCache.put(storeName, new ArrayList<>());
                    storeCache.get(storeName).add(employeeID);
                    if (!employeeCache.containsKey(employeeID))
                        employeeCache.put(employeeID, new ArrayList<>());
                    employeeCache.get(employeeID).add(storeName);
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
