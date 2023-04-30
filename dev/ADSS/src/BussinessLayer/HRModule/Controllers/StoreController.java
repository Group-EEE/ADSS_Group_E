package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.HRMoudle.EmployeesToStoreDAO;
import DataAccessLayer.HRMoudle.StoresDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreController {

    private final EmployeesToStoreDAO _employeesToStoreDAO;
    private final StoresDAO _storesDAO;
    private static StoreController _storeController = null;
    private Map<String, Store> _storesByName = new HashMap<>();
    private Map<Integer, Store> _storesByID = new HashMap<>();
    private Map<Store, List<Employee>> _storeEmployeeMap = new HashMap<>();

    public StoreController(){
        _employeesToStoreDAO = EmployeesToStoreDAO.getInstance();
        _storesDAO = StoresDAO.getInstance();
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
    public boolean createStore(int storeID, String storeName, String storeAddress) {
        if (storeName == null || storeAddress == null)
            throw new IllegalArgumentException("Invalid store name or address");
        if (_storesByName.containsKey(storeName))
            throw new IllegalArgumentException("Store already has this name");
        if (_storesByID.containsKey(Integer.valueOf(storeID)))
            throw new IllegalArgumentException("Store already has this id");
        Store newStore = new Store(storeID,storeName, storeAddress);
        _storesByID.put(storeID, newStore);
        _storesByName.put(storeName, newStore);
        _storeEmployeeMap.put(newStore, new ArrayList<Employee>());
        return true;
    }

    /**
     * @param storeID - the name of the store
     * @return - the store with the given name, null if the name is invalid
     */
    public Store getStoreByID(int storeID){
        if (storeID <0)
            return null;
        for (Map.Entry<Integer, Store> entry : _storesByID.entrySet()){
            if (entry.getKey().equals(Integer.valueOf(storeID)))
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
        _storeEmployeeMap.get(getStoreByName(storeName)).add(employee);
        /*Store store = getStoreByName(storeName);
        if (store == null)
            throw new IllegalArgumentException("Invalid store name");
        return store.addEmployee(employee);*/
        return true;
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
     * @param employee - the of the employee
     * @param storeName - the name of the store
     * @return
     */
    public boolean removeEmployeeFromStore(Employee employee, String storeName) {
        if (employee == null || storeName == null)
            throw new IllegalArgumentException("Invalid employee or store name");
        _storeEmployeeMap.get(getStoreByName(storeName)).remove(employee);
        return true;
    }

    public boolean removeEmployee(Employee employee){
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee");
        for (Store store : _storeEmployeeMap.keySet()){
            if (_storeEmployeeMap.get(store).contains(employee)){
                _storeEmployeeMap.get(store).remove(employee);
                return true;
            }
        }
        return false;
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
            throw new IllegalArgumentException("Store not found");
        }
        this._storesByName.remove(storeName);
        this._storesByID.remove((Integer)store.getStoreID());
        this._storeEmployeeMap.remove(store);
        return true;
    }

    public List<Employee> getEmployeesByStore(String storeName){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        return _storeEmployeeMap.get(getStoreByName(storeName));
    }

        public boolean checkIfEmployeeWorkInStore(Store store,Employee employee){
        if (store == null)
            throw new IllegalArgumentException("store not found");
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        return _storeEmployeeMap.get(store).contains(employee);
    }

    public boolean printStores(){
        for (Store store : _storeEmployeeMap.keySet()){
            System.out.println(store);
            for (Employee employee : _storeEmployeeMap.get(store)){
                System.out.println(employee);
            }
            System.out.println();
        }
        return true;
    }
}
