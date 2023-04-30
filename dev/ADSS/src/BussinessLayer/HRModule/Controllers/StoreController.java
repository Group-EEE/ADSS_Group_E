package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Pair;
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
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        if (storeAddress == null)
            throw new IllegalArgumentException("Invalid store address");
        if (_storesDAO.existsStore(storeID))
            throw new IllegalArgumentException("Store already has this ID");
        if (_storesDAO.existsStore(storeName))
            throw new IllegalArgumentException("Store already has this name");
        Store newStore = new Store(storeID,storeName, storeAddress);
        return _storesDAO.Insert(newStore);
    }

    /**
     * @param employeeID - the employee to add
     * @param storeName - the name of the store
     */
    public boolean addEmployeeToStore(int employeeID, String storeName) {
        if (employeeID < 0 || storeName == null)
            throw new IllegalArgumentException("Invalid employee id or store name");
        int storeID = _storesDAO.getStoreIDByName(storeName);
        return _employeesToStoreDAO.Insert(new Pair<Integer,Integer>(employeeID,storeID));
    }

    /**
     * @param storeName - the name of the store
     * @return the store with the given name
     */
    public Store findStoreByName(String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        return _storesDAO.getStore(storeName);
    }

    /**
     * @param employeeID - the ID of the employee
     * @param storeName - the name of the store
     * @return
     */
    public boolean removeEmployeeFromStore(int employeeID, String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid employee or store name");
        int storeID = _storesDAO.getStoreIDByName(storeName);
        return _employeesToStoreDAO.Delete(new Pair<Integer,Integer>(employeeID,storeID));
    }

    public boolean removeEmployee(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Invalid employee");
        return _employeesToStoreDAO.Delete(employeeID,true);
    }

    /**
     * @param storeName - the name of the store
     * @return true if the store was removed successfully, false otherwise
     */
    public boolean removeStore(String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        Store store = _storesDAO.getStore(storeName);
        _employeesToStoreDAO.Delete(store.getStoreID(),false);
        return _storesDAO.Delete(store);
    }

    public boolean checkIfEmployeeWorkInStore(Store store,Employee employee){
        if (store == null)
            throw new IllegalArgumentException("store not found");
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        return _employeesToStoreDAO.checkIfEmployeeInStore(employee.getID(),store.getStoreID());
    }

//    public boolean printStores(){
//        for (Store store : _storeEmployeeMap.keySet()){
//            System.out.println(store);
//            for (Employee employee : _storeEmployeeMap.get(store)){
//                System.out.println(employee);
//            }
//            System.out.println();
//        }
//        return true;
//    }
//
//    public int getStoreIDbyName(String storeName){
//        return _storesDAO.getStoreIDByName(storeName);
//    }
}
