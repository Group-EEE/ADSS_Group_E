package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

public class StoresDAO extends DAO {
    private static StoresDAO _storesDAO = null;
    private final HashMap<String,Store> storesCache;

    private final String StoreNameColumnName = "storeName";
    private final String AddressColumnName = "address";
    private final String PhoneColumnName = "phone";
    private final String ContactColumnName= "contactName";
    private final String AreaColumnName = "area";
    private final String ScheduleIDColumnName = "scheduleID";

    private StoresDAO() {
        super("Stores");
        storesCache = new HashMap<>();
    }

    public static StoresDAO getInstance() {
        if (_storesDAO == null)
            _storesDAO = new StoresDAO();
        return _storesDAO;
    }

    public boolean insertStore(String storeName, String address, String phone, String siteContactName, int area) {
        insert(_tableName,makeList(StoreNameColumnName, AddressColumnName, PhoneColumnName, ContactColumnName, AreaColumnName),
                makeList(storeName, address, phone, siteContactName, area));
        storesCache.put(storeName, new Store(storeName, address,phone,siteContactName, area));
        return true;
    }

    public boolean deleteStore(String storeName) {
        if (!delete(_tableName,makeList(StoreNameColumnName), makeList(storeName)))
            return false;
        storesCache.remove(storeName);
        return true;
    }

    @Override
    public Store convertReaderToObject(ResultSet rs) throws SQLException {
        if(storesCache.containsKey(rs.getInt(1)))
            return storesCache.get(rs.getInt(1));
        Store store = new Store(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
        storesCache.put(store.getName(),store);
        return store;
    }

    public boolean existsStore(String storeName){
        if (storesCache.containsKey(storeName))
            return true;
        return getStore(storeName) != null;
    }

    public Store getStore(String storeName) {
        if (storesCache.containsKey(storeName))
            return storesCache.get(storeName);
        List<Store> result = select(_tableName,makeList(StoreNameColumnName), makeList(storeName));
        if (result.size() == 0)
            return null;
        Store store = result.get(0);
        storesCache.put(store.getName(),store);
        return store;

    }

    public List<Store> SelectAllStores() {
        return (List<Store>) (List<?>) select();
    }

    public boolean isAnyStoreExist(){
        return select().size() > 0;
    }


    public boolean isStoreInArea(String storeName, int area){
        return select(_tableName,makeList(StoreNameColumnName,AreaColumnName),makeList(storeName,area)).size() > 0;
    }

    public boolean insertActiveSchedule(String storeName, int scheduleID){
        return update(_tableName,ScheduleIDColumnName,scheduleID,makeList(StoreNameColumnName),makeList(storeName));
    }

    public boolean deleteActive(String storeName){
        return update(_tableName,ScheduleIDColumnName,null,makeList(ScheduleIDColumnName),makeList(storeName));
    }

    public int getActiveSchedule(String storeName){
        List<Integer> result = selectT(_tableName,ScheduleIDColumnName,makeList(StoreNameColumnName),makeList(storeName),Integer.class);
        return result.get(0);
    }

}
