package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.Store;

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

    //initiate
    public boolean loadData(){
        createEmployee("Chen","Frydman", 22, 1,"BankA","123",true);
        createEmployee("Gali","Frydman", 17, 2,"BankA","234",false);
        createEmployee("Amit","Oren",23, 3, "BankA", "345", false);
        createEmployee("Daniel","Sphira", 26, 4, "BankC", "456", false);
        createEmployee("Ido","Paz", 26, 5, "BankC", "567", false);
        createEmployee("Gal","Chen", 26, 6, "BankC", "678", false);

        addRoleToEmployee(1, RoleType.ShiftManager);
        addRoleToEmployee(2, RoleType.ShiftManager);
        addRoleToEmployee(3, RoleType.General);

        createStore(1,"a","a");
        createStore(2,"b","b");
        createStore(3,"c","c");

        addEmployeeToStore(1,"a");
        addEmployeeToStore(2,"a");
        addEmployeeToStore(3,"a");
        addEmployeeToStore(4,"b");
        addEmployeeToStore(5,"b");
        addEmployeeToStore(6,"b");

        createNewSchedule("a", 1, 1, 2020);
        login(1,"123");
        addEmployeeToShift("a", 1);
        logout();
        login(2,"234");
        addEmployeeToShift("a", 1);
        logout();
        login(3,"345");
        addEmployeeToShift("a", 1);
        logout();

        return true;
    }

    //_employeeController
    public boolean login(int id, String password){
        _loggedUser =  _employeeController.login(id, password);
        if (_loggedUser == null)
            throw new IllegalArgumentException("Invalid id or password");
        return true;
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

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password, boolean isHRManager) {
        if (firstName == null || lastName == null || age < 0 || id < 0 || bankAccount == null)
            throw new IllegalArgumentException("Invalid arguments");
        _employeeController.createEmployee(firstName, lastName, age, id, bankAccount, password);
        if (isHRManager) {
            _HRManager = _employeeController.getEmployeeByID(id);
            _HRManager.addRole(RoleType.HRManager);
        }
        return true;
    }

    public boolean addRoleToEmployee(int employeeID, RoleType role){
        return _employeeController.addRoleToEmployee(employeeID, role);
    }

    public String getEmployeeNameById(int employeeID){
        return _employeeController.getEmployeeNameById(employeeID);
    }

    public boolean removeEmployee(int employeeID){
        Employee employee = _employeeController.getEmployeeByID(employeeID);
        _storeController.removeEmployee(employee);
        return _employeeController.removeEmployee(employeeID);
    }
    //_storeController
    public boolean createStore(int storeId, String storeName, String storeAddress){
        return _storeController.createStore(storeId, storeName, storeAddress);
    }

    public boolean removeStore(String storeName){
        Store store = _storeController.getStoreByName(storeName);
        for (Employee employee : _storeController.getEmployeesByStore(storeName)) {
            _employeeController.removeStoreFromEmployee(employee.getID(), store);
        }
        return _storeController.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        Store store = _storeController.getStoreByName(storeName);
        if (store == null)
            throw new IllegalArgumentException("Invalid store name");
        _employeeController.addStoreToEmployee(employeeID, store);
        Employee employee = _employeeController.getEmployeeByID(employeeID);
        if (employee == null) {
            throw new IllegalArgumentException("Invalid employee id");
        }
        return _storeController.addEmployeeToStore(employee, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        Store store = _storeController.getStoreByName(storeName);
        if (store == null)
            throw new IllegalArgumentException("Invalid store name");
        _employeeController.removeStoreFromEmployee(employeeID, store);
        Employee employee = _employeeController.getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee id");
        return _storeController.removeEmployeeFromStore(employee, storeName);
    }

    //_ScheduleController
    public boolean createNewSchedule(String StoreName, int day, int month, int year){
        Store store = _storeController.getStoreByName(StoreName);
        return _scheduleController.createNewSchedule(store, day, month, year);
    }

    public boolean addEmployeeToShift(String storeName, int shiftID){
        //if employee not working in this store

        Store store = _storeController.getStoreByName(storeName);
        if (!_storeController.checkIfEmployeeWorkInStore(store, _loggedUser))
            throw new IllegalArgumentException("Employee not working in this store");
        return _scheduleController.addEmployeeToShift(_loggedUser, store, shiftID);
    }

    public boolean printSchedule(String storeName){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.printSchedule(store);
    }

    public boolean printEmployeeSchedule(){
        return _scheduleController.printEmployeeSchedule(_loggedUser);
    }

    public List<Shift> approveSchedule(String storeName){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.approveSchedule(store);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.changeShiftHours(store, newStartHour, newEndHour, shiftID);
    }

    public boolean addRequiredRoleToShift(String storeName, int shiftID, RoleType role){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.addRequiredRoleToShift(store, shiftID, role);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        Store store = _storeController.getStoreByName(storeName);
        return _scheduleController.removeRequiredRoleFromShift(store, shiftID, role);
    }

    public boolean printStores(){
        return _storeController.printStores();
    }

    public boolean printEmployees(){
        return _employeeController.printEmployees();
    }
}
