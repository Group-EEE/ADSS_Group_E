package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.DAO;

import java.sql.*;
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
    //EmployeeToStoreDAO
    private final String EmployeeIDColumnName = "employeeID";
    private final String EmployeeToStoreTable = "EmployeesToStores";

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
        delete(EmployeeToStoreTable, makeList(StoreNameColumnName), makeList(storeName));
        storesCache.remove(storeName);
        return true;
    }

    public Store convertReaderToObject (ResultSet rs) throws SQLException{
        if (storesCache.containsKey(rs.getInt(1)))
            return storesCache.get(rs.getInt(1));
        Store store = new Store(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
        storesCache.put(store.getName(), store);
        return store;
    }

    public boolean existsStore(String storeName){
        if (storesCache.containsKey(storeName))
            return true;
        try{
            getStore(storeName);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }

    }

    public Store getStore(String storeName) {
        if (storesCache.containsKey(storeName))
            return storesCache.get(storeName);
        List<Store> result = select(_tableName,makeList(StoreNameColumnName), makeList(storeName));
        if (result.size() == 0)
            throw new IllegalArgumentException("Store " + storeName + " doesn't exist");
        Store store = result.get(0);
        storesCache.put(store.getName(),store);
        return store;

    }

    public List<Store> SelectAllStores() {
        List<Store> stores = select();
        for (Store store : stores){
            if (!storesCache.containsKey(store.getName()))
                storesCache.put(store.getName(),store);
        }
        return stores;
    }

    public boolean isAnyStoreExist(){
        return select().size() > 0;
    }

    public boolean isStoreInArea(String storeName, int area){
        return selectExists(_tableName,makeList(StoreNameColumnName,AreaColumnName),makeList(storeName,area));
    }

    public boolean insertActiveSchedule(String storeName, int scheduleID){
        return update(_tableName,ScheduleIDColumnName,scheduleID,makeList(StoreNameColumnName),makeList(storeName));
    }

    public boolean deleteActive(String storeName){
        return update(_tableName,ScheduleIDColumnName,null,makeList(StoreNameColumnName),makeList(storeName));
    }

    public int getActiveSchedule(String storeName){
        List<Integer> result = selectT(_tableName,ScheduleIDColumnName,makeList(StoreNameColumnName),makeList(storeName),Integer.class);
        if (result.size() == 0)
            throw new IllegalArgumentException("not created Schedule for this store");
        return result.get(0);
    }

    public boolean insertEmployeeToStore(int employeeID, String storeName) {
        return insert(EmployeeToStoreTable, makeList(EmployeeIDColumnName, StoreNameColumnName), makeList(employeeID, storeName));
    }

    public boolean deleteEmployeeFromStore(int employeeID, String storeName) {
        return delete(EmployeeToStoreTable, makeList(EmployeeIDColumnName, StoreNameColumnName), makeList(employeeID, storeName));
    }

    public boolean deleteEmployeeFromAllStores(int employeeID){
        return delete(EmployeeToStoreTable, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public List<Integer> getEmployeesIDByStoreName(String storeName){
        return selectT(EmployeeToStoreTable, EmployeeIDColumnName, makeList(StoreNameColumnName), makeList(storeName), Integer.class);
    }

    public List<String> getStoreNamesByEmployeeID(int employeeID){
        return selectT(EmployeeToStoreTable, StoreNameColumnName,makeList(EmployeeIDColumnName), makeList(employeeID), String.class);
    }

    public boolean checkIfEmployeeInStore(int employeeID, String storeName){
        return selectExists(EmployeeToStoreTable,makeList(EmployeeIDColumnName, StoreNameColumnName),makeList(employeeID,storeName));
    }
}
