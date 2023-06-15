package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.HRMoudle.StoresDAO;

import java.util.List;
public class StoreController {
    private final StoresDAO _storesDAO;
    private static StoreController _storeController = null;

    public StoreController(){
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
        if (storeName == null || storeName == "")
            throw new IllegalArgumentException("Invalid store name");
        if (storeAddress == null || storeAddress == "")
            throw new IllegalArgumentException("Invalid store address");
        if (phone == null || phone == "")
            throw new IllegalArgumentException("Invalid store phone");
        if (siteContactName == null || siteContactName == "")
            throw new IllegalArgumentException("Invalid store contact name");
        if (area <0)
            throw new IllegalArgumentException("Invalid store area");
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
        return _storesDAO.insertEmployeeToStore(employeeID,storeName);
    }

    /**
     * @param storeName - the name of the store
     * @return the store with the given name
     */
    public Store getStore(String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        return _storesDAO.getStore(storeName);
    }

    /**
     * @param employeeID - the ID of the employee
     * @param storeName - the name of the store
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeEmployeeFromStore(int employeeID,String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid employee or store name");
        if (!checkIfEmployeeWorkInStore(employeeID,storeName))
            throw new IllegalArgumentException("Employee not found in store to remove him");
        return _storesDAO.deleteEmployeeFromStore(employeeID,storeName);
    }

    public boolean removeEmployee(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Invalid employee id");
        return _storesDAO.deleteEmployeeFromAllStores(employeeID);
    }

    /**
     * @param storeName - the name of the store
     * @return true if the store was removed successfully, false otherwise
     */
    public boolean removeStore(String storeName) {
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        if(this.getStore(storeName) == null){
            System.out.println("no such store");
            return false;
        }
        return _storesDAO.deleteStore(storeName);
    }

    public boolean checkIfEmployeeWorkInStore(int employeeID,String storeName){
        if (employeeID < 0)
            throw new IllegalArgumentException("invalid employee id");
        return _storesDAO.checkIfEmployeeInStore(employeeID,storeName);
    }

    public List<Store> getAllStores(){
        return _storesDAO.SelectAllStores();
    }

}
