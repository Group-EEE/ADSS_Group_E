package ServiceLayer.ModulesServices;


import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Shift;
import ServiceLayer.objectsServices.EmployeeService;
import ServiceLayer.objectsServices.ScheduleService;
import ServiceLayer.objectsServices.StoreService;

import java.util.List;

// write for the service layer that connects to the facade in the bussiness Layer
public class HRModuleService {

    private static HRModuleService _hrModuleService;
    private final EmployeeService _employeeService;
    private final StoreService _storeService;
    private final ScheduleService _scheduleService;

    private HRModuleService(){
        _employeeService = EmployeeService.getInstance();
        _storeService = StoreService.getInstance();
        _scheduleService = ScheduleService.getInstance();
    }

    public boolean loadData(){
        return _employeeService.loadData();
    }

    public static HRModuleService getInstance(){
        if(_hrModuleService == null)
            _hrModuleService = new HRModuleService();
        return _hrModuleService;
    }
    public boolean login(int id, String password){
        return _employeeService.login(id, password);
    }
    public boolean hasHRManager(){
        return _employeeService.hasHRManager();
    }

    public boolean hasLoggedUser(){
        return _employeeService.hasLoggedUser();
    }

    public boolean isLoggedUserIsHRManager(){
        return _employeeService.isLoggedUserIsHRManager();
    }

    public boolean logout(){
        return _employeeService.logout();
    }

    public boolean printEmployeeRoles(int employeeID){
        return _employeeService.printEmployeeRoles(employeeID);
    }

    public boolean removeRoleFromEmployee(int employeeID, int roleIndex){ //the index needs to be -1 less
        return _employeeService.removeRoleFromEmployee(employeeID, roleIndex);
    }

    public boolean setNewFirstName(String firstName){
        return _employeeService.setNewFirstName(firstName);
    }

    public boolean setNewLastName(String lastName){
        return _employeeService.setNewLastName(lastName);
    }

    public boolean setNewBankAccount(String bankAccount){
        return _employeeService.setNewBankAccount(bankAccount);
    }

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password, boolean isHRManager) {
        return _employeeService.createEmployee(firstName, lastName, age, id, bankAccount, password, isHRManager);
    }

    public boolean addRoleToEmployee(int employeeID, RoleType role){;
        return _employeeService.addRoleToEmployee(employeeID, role);
    }

    public String getEmployeeFirstNameById(int employeeID){
        return _employeeService.getEmployeeFirstNameById(employeeID);
    }

    public boolean removeEmployee(int employeeID){
        return _employeeService.removeEmployee(employeeID);
    }

    //_storeController
    public boolean createStore(int storeId, String storeName, String storeAddress){
        return _storeService.createStore(storeId, storeName, storeAddress);
    }

    public boolean removeStore(String storeName){
        return _storeService.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        return _storeService.addEmployeeToStore(employeeID, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        return _storeService.removeEmployeeFromStore(employeeID, storeName);
    }

    //_ScheduleController
    public boolean createNewSchedule(String StoreName, int day, int month, int year){
        return _scheduleService.createNewSchedule(StoreName, day, month, year);
    }

    public boolean addEmployeeToShift(String storeName, int choice){
        return _scheduleService.addEmployeeToShift(storeName, choice);
    }

    public boolean printSchedule(String storeName){
       return _scheduleService.printSchedule(storeName);
    }

    public List<Shift> approveSchedule(String storeName){
        return _scheduleService.approveSchedule(storeName);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        return _scheduleService.changeHoursShift(storeName, newStartHour, newEndHour, shiftID);
    }

    public boolean addRequiredRoleToShift(String storeName, int shiftID, RoleType role){
        return _scheduleService.addRequiredRoleToShift(storeName, shiftID, role);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        return _scheduleService.removeRequiredRoleFromShift(storeName, shiftID, role);
    }

    public boolean printEmployees(){
        return _employeeService.printEmployees();
    }

    public boolean printStores(){
        return _storeService.printStores();
    }
}
