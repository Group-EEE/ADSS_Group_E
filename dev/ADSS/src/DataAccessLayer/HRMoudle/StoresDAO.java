package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

public class StoresDAO extends DAO {
    private static StoresDAO _storesDAO = null;
    private final HashMap<Integer,Store> storesCache;
    private final HashMap<String,Store> storesCacheByName;

    public static final String StoreIDColumnName = "storeID";
    public static final String NameColumnName = "name";
    public static final String AddressColumnName = "address";
    public static final String PhoneColumnName = "phone";
    public static final String ContactColumnName= "contactName";

    private StoresDAO() {
        super("Stores");
        storesCache = new HashMap<>();
        storesCacheByName = new HashMap<>();
    }

    public static StoresDAO getInstance() {
        if (_storesDAO == null)
            _storesDAO = new StoresDAO();
        return _storesDAO;
    }

    @Override
    public boolean Insert(Object storeObj) {
        Store store = (Store) storeObj;
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}, {4}, {5}) VALUES(?, ?, ?, ? ,?) "
                , _tableName, StoreIDColumnName, NameColumnName, AddressColumnName, PhoneColumnName, ContactColumnName
        );

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, store.getStoreID());
            pstmt.setString(2, store.getName());
            pstmt.setString(3, store.getAddress());
            pstmt.setString(4, store.getPhone());
            pstmt.setString(5, store.getSite_contact_name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        storesCache.put(store.getStoreID(), store);
        storesCacheByName.put(store.getName(), store);
        return true;
    }

    @Override
    public boolean Delete(Object storeObj) {
        Store store = (Store) storeObj;
        boolean res = true;
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? "
                , _tableName, StoreIDColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, store.getStoreID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        storesCache.remove(store.getStoreID());
        storesCacheByName.remove(store.getName());
        return res;
    }

    @Override
    public Store convertReaderToObject(ResultSet rs) throws SQLException {
        if(storesCache.containsKey(rs.getInt(1)))
            return storesCache.get(rs.getInt(1));
        Store store = new Store(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        storesCache.put(store.getStoreID(),store);
        storesCacheByName.put(store.getName(),store);
        return store;
    }

    public int getStoreIDByName(String storeName){
        if (storesCacheByName.containsKey(storeName))
            return storesCacheByName.get(storeName).getStoreID();
        List<ResultSet> rsList = Select(StoreIDColumnName);
        for (ResultSet rs : rsList) {
            try {
                if (rs.getString(2).equals(storeName)) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public boolean existsStore(int storeID){
        if (storesCache.containsKey(storeID))
            return true;
        List<ResultSet> rsList = Select(StoreIDColumnName);
        for (ResultSet rs : rsList) {
            try {
                if (rs.getInt(1) == storeID) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean existsStore(String storeName){
        if (storesCacheByName.containsKey(storeName))
            return true;
        List<ResultSet> rsList = Select(StoreIDColumnName);
        for (ResultSet rs : rsList) {
            try {
                if (rs.getString(2).equals(storeName)) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Store getStore(int storeID) {
        if (storesCache.containsKey(storeID))
            return storesCache.get(storeID);
        else {//Employee in db
            List<Store> result = Select(makeList(StoreIDColumnName), makeList(String.valueOf(storeID)));
            if (result.size() == 0)
                throw new IllegalArgumentException("store " +storeID + " does not exist");
            Store store = result.get(0);
            storesCache.put(storeID,store);
            storesCacheByName.put(store.getName(),store);
            return store;
        }
    }

    public Store getStore(String storeName) {
        if (storesCacheByName.containsKey(storeName))
            return storesCacheByName.get(storeName);
        else {//Employee in db
            List<Store> result = Select(makeList(NameColumnName), makeList(storeName));
            if (result.size() == 0)
                throw new IllegalArgumentException("store " +storeName + " does not exist");
            Store store = result.get(0);
            storesCache.put(store.getStoreID(),store);
            storesCacheByName.put(storeName,store);
            return store;
        }
    }

    public List<Store> SelectAllStores() {
        return (List<Store>) (List<?>) Select();
    }

    public boolean is_any_store_exist(){
        if (storesCache.size() > 0){
            return true;
        }
        else {
            String query = "SELECT * FROM " + this._tableName;
            try (PreparedStatement pstmt = connection.prepareStatement(query);) {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()){
                    return true;
                }
                return false;
            } catch (SQLException ex) {
            }
            return false;
        }
    }



}
