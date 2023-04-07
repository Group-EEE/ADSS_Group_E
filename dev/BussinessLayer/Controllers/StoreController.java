package BussinessLayer.Controllers;

import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreController {
    private HashMap<Integer,Store> _storesByID = new HashMap<Integer, Store>();
    private HashMap<String,Store> _storesByName = new HashMap<String, Store>();
    private static StoreController _storeController = null;
    public StoreController(){

    }

    public static StoreController getInstance(){
        if (_storeController == null)
            _storeController = new StoreController();
        return _storeController;
    }

    /**
     * @param storeName - the name of the store
     * @param storeAddress - the address of the store
     * @return - true if the store was created successfully, false otherwise
     */
    public boolean createStore(int storeId, String storeName, String storeAddress) {
        if (storeName == null || storeAddress == null)
            throw new IllegalArgumentException("Invalid store name or address");
        if (_storesByName.containsKey(storeName))
            throw new IllegalArgumentException("Store already has this name");
        if (_storesByID.containsKey(storeId))
            throw new IllegalArgumentException("Store already has this id");
        Store newStore = new Store(storeId,storeName, storeAddress);
        _storesByID.put(storeId, newStore);
        _storesByName.put(storeName, newStore);
        return true;
    }

    /**
     * @param store_id - the name of the store
     * @return - the store with the given name, null if the name is invalid
     */
    public Store getStoreByID(int store_id){
        if (store_id <0)
            return null;
        for (Map.Entry<Integer, Store> entry : _storesByID.entrySet()){
            if (entry.getKey().equals(store_id))
                return entry.getValue();
        }
        return null;
    }

    /**
     * @param storeName - the name of the store
     * @return - the store with the given name, null if didn't find the Store
     */
    public Store getStoreByName(String storeName){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        for (Map.Entry<String, Store> entry : _storesByName.entrySet()){
            if (entry.getKey().equals(storeName))
                return entry.getValue();
        }
        throw new IllegalArgumentException("Store not found");
    }

    /**
     * @param employee - the employee to add
     * @param storeName - the name of the store
     * @return
     */
    public boolean addEmployeeToStore(Employee employee, String storeName) {
        if (employee == null || storeName == null)
            throw new IllegalArgumentException("Invalid employee id or store name");
        Store store = getStoreByName(storeName);
        if (store == null)
            throw new IllegalArgumentException("Invalid store name");
        return store.addEmployee(employee);
    }

    /**
     * @param storeName - the name of the store
     * @return the store with the given name
     */
    public Store findStoreByName(String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        for (Map.Entry<String,Store> entry : _storesByName.entrySet()) {
            if (entry.getKey().equals(storeName)) {
                return entry.getValue();
            }
        }
        throw new IllegalArgumentException("Store not found");
    }

    /**
     * @param employeeID - the id of the employee
     * @param storeName - the name of the store
     * @return
     */
    public boolean removeEmployeeFromStore(Employee employee, String storeName) {
        if (employee == null || storeName == null)
            throw new IllegalArgumentException("Invalid employee or store name");
        Store store = findStoreByName(storeName);
        store.removeEmployee(employee);
        return true;
    }

    /**
     * @param storeName - the name of the store
     * @return true if the store was removed successfully, false otherwise
     */
    public boolean removeStore(String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        Store store = findStoreByName(storeName);
        if (store == null) {
            return false;
        }
        List<Employee> employees = store.getEmployees();
        for (Employee employee : employees) {
            if (!employee.removeStore(store))
                return false;
        }
        this._storesByName.remove(store);
        this._storesByID.remove(store);
        return true;
    }
}
