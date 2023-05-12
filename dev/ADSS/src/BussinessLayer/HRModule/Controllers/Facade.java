package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.*;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.HRMoudle.EmployeesDAO;

import java.time.LocalDate;
import java.util.List;

public class Facade {
    private Employee _loggedUser;
    int _HRManagerID;
    private boolean _hasHRManager;
    private static Facade _facade = null;
    private final StoreController _storeController;
    private final EmployeeController _employeeController;
    private final ScheduleController _scheduleController;

    private Facade(){
        _storeController = StoreController.getInstance();
        _employeeController = EmployeeController.getInstance();
        _scheduleController = ScheduleController.getInstance();
        _hasHRManager = _employeeController.hasHRManager();
        if (_hasHRManager)
            _HRManagerID = _employeeController.getHRManagerID();
    }

    public static Facade getInstance(){
        if (_facade == null)
            _facade = new Facade();
        return _facade;
    }

    //_employeeController
    public boolean login(int employeeID, String password){
        try {
            _loggedUser = _employeeController.login(employeeID, password);
        }
        catch (IllegalArgumentException e){
            return false;
        }
        if (_loggedUser == null)
            throw new IllegalArgumentException("Invalid id or password");
        return true;
    }
    public boolean hasHRManager(){
        return _hasHRManager;
    }

    public boolean hasLoggedUser(){
        return _loggedUser != null;
    }

    public boolean isLoggedUserIsHRManager(){
        return _loggedUser.getEmployeeID() == _HRManagerID;
    }

    public boolean logout(){
        if (_loggedUser == null)
            throw new IllegalArgumentException("No user is logged in");
        _loggedUser = null;
        return true;
    }

    public List<RoleType> getEmployeeRoles(int employeeID){
        return _employeeController.getEmployeeRoles(employeeID);
    }

    public boolean removeRoleFromEmployee(int employeeID, int roleIndex){ //the index needs to be -1 less
        return _employeeController.removeRoleFromEmployee(employeeID, roleIndex);
    }

    public boolean setNewFirstName(String firstName){
        if (firstName == null)
            throw new IllegalArgumentException("Invalid first name");
        return _employeeController.updateFirstName(_loggedUser.getEmployeeID(), firstName);
    }

    public boolean setNewLastName(String lastName){
        if (lastName == null)
            throw new IllegalArgumentException("Invalid last name");
        return _employeeController.updateLastName(_loggedUser.getEmployeeID(), lastName);
    }

    public boolean setNewBankAccount(String bankAccount){
        if (bankAccount == null)
            throw new IllegalArgumentException("Invalid bank account");
        return _employeeController.updateBankAccount(_loggedUser.getEmployeeID(), bankAccount);
    }

    public boolean setNewPassword(String password){
        if (password == null)
            throw new IllegalArgumentException("Invalid password");
        return _employeeController.updatePassword(_loggedUser.getEmployeeID(), password);
    }

    public boolean createEmployee(int employeeID, String firstName, String lastName, int age, String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployement, String password, boolean isHRManager) {
        if (firstName == null || lastName == null || age < 0 || employeeID < 0 || bankAccount == null)
            throw new IllegalArgumentException("Invalid arguments");
        if (!_employeeController.createEmployee(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployement, password))
            return false;
        if (isHRManager) {
            return addRoleToEmployee(employeeID, RoleType.HRManager);
        }
        return true;
    }

    public boolean addRoleToEmployee(int employeeID, RoleType role){
        return _employeeController.addRoleToEmployee(employeeID, role);
    }

    public String getEmployeeFullNameById(int employeeID){
        return _employeeController.getEmployeeFullNameById(employeeID);
    }

    public boolean removeEmployee(int employeeID){
        if (employeeID <0)
            throw new IllegalArgumentException("Invalid employee id");
        if (!_storeController.removeEmployee(employeeID))
            return false;
        return _employeeController.removeEmployee(employeeID);
    }

    public boolean createDriver(int employeeID, String firstName, String lastName, int age, String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployement, String password, int licenseID, cold_level level, double truck_weight) {
        if (firstName == null || lastName == null || age < 0 || employeeID < 0 || bankAccount == null|| licenseID < 0 || level == null || truck_weight < 0)
            throw new IllegalArgumentException("Invalid arguments");
        return _employeeController.createDriver(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployement, password, licenseID, level, truck_weight);
    }


    //_storeController
    public boolean createStore(String storeName, String storeAddress, String phone, String siteContactName, int area){
        return _storeController.createStore(storeName, storeAddress,phone,siteContactName,area);
    }

    public boolean removeStore(String storeName){
        return _storeController.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        if (!_employeeController.existsEmployee(employeeID))
            return false;
        return _storeController.addEmployeeToStore(employeeID, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        return _storeController.removeEmployeeFromStore( employeeID,storeName);
    }

    //_ScheduleController
    public boolean createNewStoreSchedule(String StoreName, int day, int month, int year){
        if (!_storeController.existsStore(StoreName))
            throw new IllegalArgumentException("Store doesn't exist in order to create for it a schedule");
        return _scheduleController.createNewStoreSchedule(StoreName, day, month, year);
    }

    public boolean createNewLogisiticsSchedule(int day, int month, int year){
        return _scheduleController.createNewLogisticsSchedule(day, month, year);
    }

    public boolean checkIfEmployeeWorkInStore(String storeName){
        return _storeController.checkIfEmployeeWorkInStore(_loggedUser.getEmployeeID(), storeName);
    }

    public boolean addEmployeeToShift(String storeName, int shiftID){
        //if employee not working in this store
        if (!checkIfEmployeeWorkInStore(storeName))
            throw new IllegalArgumentException("Employee not working in this store");
        return _scheduleController.addEmployeeToShift(_loggedUser, storeName, shiftID);
    }

    public Schedule getSchedule(String storeName){
        return _scheduleController.getSchedule(storeName);
    }

    public List<Shift> printEmployeeSchedule(){
        return _scheduleController.getEmployeeSchedule(_loggedUser);
    }

    public List<Shift> approveSchedule(String storeName){
        return _scheduleController.approveSchedule(storeName);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        return _scheduleController.changeShiftHours(storeName, newStartHour, newEndHour, shiftID);
    }

    public boolean addRequiredRoleToShift(String storeName, int shiftID, RoleType role,boolean mustBeFilled){
        return _scheduleController.addRequiredRoleToShift(storeName, shiftID, role, mustBeFilled);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        return _scheduleController.removeRequiredRoleFromShift(storeName, shiftID, role);
    }

    public List<Store> getAllStores(){
        return _storeController.getAllStores();
    }

    public List<Employee> getAllEmployees(){
        return _employeeController.getAllEmployees();
    }

    public boolean deleteSchedule(String storeName){
        return _scheduleController.deleteSchedule(storeName);
    }

}
