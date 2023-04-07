package BussinessLayer.Controllers;

import BussinessLayer.Objects.*;

import java.time.LocalDate;
import java.util.List;

public class Facade {
    private Employee _loggedUser;
    private Employee _HRManager;
    private static Facade _facade = null;
    private final StoreController _storeController;
    private final EmployeeController _employeeController;
    private final ScheduleController _scheduleController;

    private Facade(){
        _storeController = StoreController.getInstance();
        _employeeController = EmployeeController.getInstance();
        _scheduleController = ScheduleController.getInstance();
    }

    public static Facade getInstance(){
        if (_facade == null)
            _facade = new Facade();
        return _facade;
    }

    //_employeeController
    public Employee login(int id, String password){
        _loggedUser =  _employeeController.login(id, password);
        if (_loggedUser == null)
            throw new IllegalArgumentException("Invalid id or password");
        return _loggedUser;
    }
    public boolean hasHRManager(){
        return _HRManager != null;
    }

    public boolean hasLoggedUser(){
        return _loggedUser != null;
    }

    public boolean isLoggedUserIsHRManager(){
        return _loggedUser == _HRManager;
    }

    public boolean logout(){
        if (_loggedUser == null)
            throw new IllegalArgumentException("No user is logged in");
        _loggedUser = null;
        return true;
    }

    public boolean printEmployeeRoles(int employeeID){
        return _employeeController.printEmployeeRoles(employeeID);
    }

    public boolean removeRoleFromEmployee(int employeeID, int roleIndex){ //the index needs to be -1 less
        return _employeeController.removeRoleFromEmployee(employeeID, roleIndex);
    }

    public boolean setNewFirstName(String firstName){
        if (firstName == null)
            throw new IllegalArgumentException("Invalid first name");
        return _loggedUser.setNewFirstName(firstName);
    }

    public boolean setNewLastName(String lastName){
        if (lastName == null)
            throw new IllegalArgumentException("Invalid last name");
        return _loggedUser.setNewLastName(lastName);
    }

    public boolean setNewBankAccount(String bankAccount){
        if (bankAccount == null)
            throw new IllegalArgumentException("Invalid bank account");
        return _loggedUser.setNewBankAccount(bankAccount);
    }

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password) {
        if (firstName == null || lastName == null || age < 0 || id < 0 || bankAccount == null)
            throw new IllegalArgumentException("Invalid arguments");
        return _employeeController.createEmployee(firstName, lastName, age, id, bankAccount, password);
    }

    public boolean addRoleToEmployee(int employeeID, RoleType role){;
        return _employeeController.addRoleToEmployee(employeeID, role);
    }

    public String getEmployeeFirstNameById(int employeeID){
        return _employeeController.getEmployeeFirstNameById(employeeID);
    }

    public boolean removeEmployee(int employeeID){
        return _employeeController.removeEmployee(employeeID);
    }
    //_storeController
    public boolean createStore(int storeId, String storeName, String storeAddress){
        return _storeController.createStore(storeId, storeName, storeAddress);
    }

    public boolean removeStore(String storeName){
        return _storeController.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        Employee employee = _employeeController.getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee id");
        _employeeController.addStoreToEmployee(employee, storeName);
        return _storeController.addEmployeeToStore(employee, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        Employee employee = _employeeController.getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee id");
        return _storeController.removeEmployeeFromStore(employee, storeName);
    }
    //

    //_ScheduleController
    public boolean createNewSchedule(String StoreName, int day, int month, int year){
        Store store = _storeController.getStoreByName(StoreName);
        return _scheduleController.createNewSchedule(store, day, month, year);
    }

    public boolean addEmployeeToShift(String storeName, int choice){
        //if employee not working in this store

        Store store = _storeController.getStoreByName(storeName);
        if (!_employeeController.checkIfEmployeeWorkInStore(store, _loggedUser))
            throw new IllegalArgumentException("Employee not working in this store");
        return _scheduleController.addEmployeeToShift(_loggedUser, store, choice);
    }

    public boolean printSchedule(String storeName){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.printSchedule(store);
    }

    public List<Shift> approveSchedule(String storeName){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.approveSchedule(store);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.changeShiftHours(store, newStartHour, newEndHour, shiftID);
    }

    public boolean selectRequiredRoles(String storeName, int shiftID, RoleType role){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.selectRequiredRole(store, shiftID, role);
    }
}
