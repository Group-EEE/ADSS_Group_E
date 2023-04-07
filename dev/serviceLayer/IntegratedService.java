package serviceLayer;

import BussinessLayer.Controllers.Facade;
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Shift;

import java.util.List;

public class IntegratedService {
    private static IntegratedService _integratedService;
    private EmployeeService _employeeService;

    private IntegratedService(){
        _employeeService = _employeeService.getInstance();
    }

    public static IntegratedService getInstance(){
        if(_integratedService == null)
            _integratedService = new IntegratedService();
        return _integratedService;
    }

    public Employee login(int id, String password){
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

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password) {
        return _employeeService.createEmployee(firstName, lastName, age, id, bankAccount, password);
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
        return _employeeService.createStore(storeId, storeName, storeAddress);
    }

    public boolean removeStore(String storeName){
        return _employeeService.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        return _employeeService.addEmployeeToStore(employeeID, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        return _employeeService.removeEmployeeFromStore(employeeID, storeName);
    }
    //

    //_ScheduleController
    public boolean createNewSchedule(String StoreName, int day, int month, int year){
        return _employeeService.createNewSchedule(StoreName, day, month, year);
    }

    public boolean addEmployeeToShift(String storeName, int choice){
        return _employeeService.addEmployeeToShift(storeName, choice);
    }

    public boolean printSchedule(String storeName){
        return _employeeService.printSchedule(storeName);
    }

    public List<Shift> approveSchedule(String storeName){
        return _employeeService.approveSchedule(storeName);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        return _employeeService.changeHoursShift(storeName, newStartHour, newEndHour, shiftID);
    }
}
