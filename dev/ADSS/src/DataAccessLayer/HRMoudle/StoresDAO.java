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
    public static final String AreaColumnName = "area";

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
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}, {4}, {5}, {6}) VALUES(?, ?, ?, ? ,?, ?) "
                , _tableName, StoreIDColumnName, NameColumnName, AddressColumnName, PhoneColumnName, ContactColumnName, AreaColumnName
        );

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, store.getStoreID());
            pstmt.setString(2, store.getName());
            pstmt.setString(3, store.getAddress());
            pstmt.setString(4, store.getPhone());
            pstmt.setString(5, store.getSite_contact_name());
            pstmt.setInt(6, store.get_area());
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
        Store store = new Store(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
        storesCache.put(store.getStoreID(),store);
        storesCacheByName.put(store.getName(),store);
        return store;
    }

    public int getStoreIDByName(String storeName){
        if (storesCacheByName.containsKey(storeName))
            return storesCacheByName.get(storeName).getStoreID();
        List<String> storesIDList = SelectString(StoreIDColumnName,makeList(NameColumnName),makeList(storeName));
        for (String strStoreID : storesIDList) {
            return Integer.parseInt(strStoreID);
        }
        return -1;
    }

    public String getStoreNameByID(int storeID){
        if (storesCache.containsKey(storeID))
            return storesCache.get(storeID).getName();
        List<String> storesNameList = SelectString(NameColumnName,makeList(StoreIDColumnName),makeList(String.valueOf((storeID))));
        if (storesNameList.size() == 0)
            throw new IllegalArgumentException("store " +storeID + " does not exist");
        if (storesNameList.size() > 1)
            throw new IllegalArgumentException("store " +storeID + " is not unique");
        for (String strStoreID : storesNameList) {
            return strStoreID;
        }
        return null;
    }

    public boolean existsStore(int storeID){
        if (storesCache.containsKey(storeID))
            return true;
        List<Store> listStores = Select(StoreIDColumnName);
        for (Store store : listStores) {
            if (store.getStoreID() == storeID) {
                return true;
            }
        }
        return false;
    }

    public boolean existsStore(String storeName){
        if (storesCacheByName.containsKey(storeName))
            return true;
        List<Store> listStores = Select(StoreIDColumnName);
        for (Store store : listStores) {
            if (store.getName().equals(storeName)) {
                return true;
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


    public boolean is_store_in_the_area(String store_name, int area){
        List<String> res = SelectString(AreaColumnName,makeList(NameColumnName),makeList(String.valueOf(area)));
        for (String str : res) {
            if (str.equals(store_name)){
                return true;
            }
        }
        return false;
    }

}
