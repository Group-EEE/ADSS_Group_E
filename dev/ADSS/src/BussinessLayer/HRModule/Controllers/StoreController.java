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
    public boolean createStore(String storeName, String storeAddress, String phone, String siteContactName, int area) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        if (storeAddress == null)
            throw new IllegalArgumentException("Invalid store address");
        if (existsStore(storeName))
            throw new IllegalArgumentException("Store already has this name");
        return _storesDAO.insertStore(storeName, storeAddress, phone, siteContactName, area);
    }

    public boolean existsStore(String storeName){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        return _storesDAO.existsStore(storeName);
    }

    /**
     * @param employeeID - the employee to add
     * @param storeName - the name of the store
     */
    public boolean addEmployeeToStore(int employeeID, String storeName) {
        if (employeeID < 0 || storeName == null)
            throw new IllegalArgumentException("Invalid employee id or store name");
        return _employeesToStoreDAO.insertEmployeeToStore(employeeID,storeName);
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
        return _employeesToStoreDAO.deleteEmployeeFromStore(employeeID,storeName);
    }

    public boolean removeEmployee(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Invalid employee id");
        return _employeesToStoreDAO.deleteEmployee(employeeID);
    }

    /**
     * @param storeName - the name of the store
     * @return true if the store was removed successfully, false otherwise
     */
    public boolean removeStore(String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        _employeesToStoreDAO.deleteStore(storeName);
        return _storesDAO.deleteStore(storeName);
    }

    public boolean checkIfEmployeeWorkInStore(String storeName,Employee employee){
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        return _employeesToStoreDAO.checkIfEmployeeInStore(employee.getEmployeeID(),storeName);
    }

    public List<Store> getAllStores(){
        return _storesDAO.SelectAllStores();
    }

}
