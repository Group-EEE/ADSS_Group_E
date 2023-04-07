package serviceLayer.ModulesServices;

import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Shift;

import java.util.List;

public class IntegratedService {
    private static IntegratedService _integratedService;
    private HRModuleService _hrModuleService;

    private IntegratedService(){
        _hrModuleService = _hrModuleService.getInstance();
    }

    public static IntegratedService getInstance(){
        if(_integratedService == null)
            _integratedService = new IntegratedService();
        return _integratedService;
    }

    public boolean login(int id, String password){
        return _hrModuleService.login(id, password);
    }
    public boolean hasHRManager(){
        return _hrModuleService.hasHRManager();
    }

    public boolean hasLoggedUser(){
        return _hrModuleService.hasLoggedUser();
    }

    public boolean isLoggedUserIsHRManager(){
        return _hrModuleService.isLoggedUserIsHRManager();
    }

    public boolean logout(){
        return _hrModuleService.logout();
    }

    public boolean printEmployeeRoles(int employeeID){
        return _hrModuleService.printEmployeeRoles(employeeID);
    }

    public boolean removeRoleFromEmployee(int employeeID, int roleIndex){ //the index needs to be -1 less
        return _hrModuleService.removeRoleFromEmployee(employeeID, roleIndex);
    }

    public boolean setNewFirstName(String firstName){
        return _hrModuleService.setNewFirstName(firstName);
    }

    public boolean setNewLastName(String lastName){
        return _hrModuleService.setNewLastName(lastName);
    }

    public boolean setNewBankAccount(String bankAccount){
        return _hrModuleService.setNewBankAccount(bankAccount);
    }

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password, boolean isHRManager) {
        return _hrModuleService.createEmployee(firstName, lastName, age, id, bankAccount, password, isHRManager);
    }

    public boolean addRoleToEmployee(int employeeID, RoleType role){;
        return _hrModuleService.addRoleToEmployee(employeeID, role);
    }

    public String getEmployeeFirstNameById(int employeeID){
        return _hrModuleService.getEmployeeFirstNameById(employeeID);
    }

    public boolean removeEmployee(int employeeID){
        return _hrModuleService.removeEmployee(employeeID);
    }

    //_storeController
    public boolean createStore(int storeId, String storeName, String storeAddress){
        return _hrModuleService.createStore(storeId, storeName, storeAddress);
    }

    public boolean removeStore(String storeName){
        return _hrModuleService.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        return _hrModuleService.addEmployeeToStore(employeeID, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        return _hrModuleService.removeEmployeeFromStore(employeeID, storeName);
    }
    //

    //_ScheduleController
    public boolean createNewSchedule(String StoreName, int day, int month, int year){
        return _hrModuleService.createNewSchedule(StoreName, day, month, year);
    }

    public boolean addEmployeeToShift(String storeName, int choice){
        return _hrModuleService.addEmployeeToShift(storeName, choice);
    }

    public boolean printSchedule(String storeName){
        return _hrModuleService.printSchedule(storeName);
    }

    public List<Shift> approveSchedule(String storeName){
        return _hrModuleService.approveSchedule(storeName);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        return _hrModuleService.changeHoursShift(storeName, newStartHour, newEndHour, shiftID);
    }

    public boolean addRequiredRoleToShift(String storeName, int shiftID, RoleType role){
        return _hrModuleService.addRequiredRoleToShift(storeName, shiftID, role);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        return _hrModuleService.removeRequiredRoleFromShift(storeName, shiftID, role);
    }

    public boolean printStores(){
        return _hrModuleService.printStores();
    }

    public boolean printEmployees(){
        return _hrModuleService.printEmployees();
    }
}
