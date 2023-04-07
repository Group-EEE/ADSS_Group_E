package serviceLayer;

import BussinessLayer.Controllers.EmployeeController;
import BussinessLayer.Controllers.Facade;
import BussinessLayer.Controllers.ScheduleController;
import BussinessLayer.Controllers.StoreController;
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Shift;
import BussinessLayer.Objects.Store;
import InterfaceLayer.EmployeesCLI;

import java.util.List;

// write for the service layer that connects to the facade in the bussiness Layer
public class EmployeeService {
    private static EmployeeService _employeeService;
    private Facade _facade;

    private EmployeeService(){_facade = Facade.getInstance();}

    public static EmployeeService getInstance(){
        if(_employeeService == null)
            _employeeService = new EmployeeService();
        return _employeeService;
    }
    public Employee login(int id, String password){
        return _facade.login(id, password);
    }
    public boolean hasHRManager(){
        return _facade.hasHRManager();
    }

    public boolean hasLoggedUser(){
        return _facade.hasLoggedUser();
    }

    public boolean isLoggedUserIsHRManager(){
        return _facade.isLoggedUserIsHRManager();
    }

    public boolean logout(){
        return _facade.logout();
    }

    public boolean printEmployeeRoles(int employeeID){
        return _facade.printEmployeeRoles(employeeID);
    }

    public boolean removeRoleFromEmployee(int employeeID, int roleIndex){ //the index needs to be -1 less
        return _facade.removeRoleFromEmployee(employeeID, roleIndex);
    }

    public boolean setNewFirstName(String firstName){
        return _facade.setNewFirstName(firstName);
    }

    public boolean setNewLastName(String lastName){
        return _facade.setNewLastName(lastName);
    }

    public boolean setNewBankAccount(String bankAccount){
        return _facade.setNewBankAccount(bankAccount);
    }

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password) {
        return _facade.createEmployee(firstName, lastName, age, id, bankAccount, password);
    }

    public boolean addRoleToEmployee(int employeeID, RoleType role){;
        return _facade.addRoleToEmployee(employeeID, role);
    }

    public String getEmployeeFirstNameById(int employeeID){
        return _facade.getEmployeeFirstNameById(employeeID);
    }

    public boolean removeEmployee(int employeeID){
        return _facade.removeEmployee(employeeID);
    }

    //_storeController
    public boolean createStore(int storeId, String storeName, String storeAddress){
        return _facade.createStore(storeId, storeName, storeAddress);
    }

    public boolean removeStore(String storeName){
        return _facade.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        return _facade.addEmployeeToStore(employeeID, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        return _facade.removeEmployeeFromStore(employeeID, storeName);
    }
    //

    //_ScheduleController
    public boolean createNewSchedule(String StoreName, int day, int month, int year){
        return _facade.createNewSchedule(StoreName, day, month, year);
    }

    public boolean addEmployeeToShift(String storeName, int choice){
        return _facade.addEmployeeToShift(storeName, choice);
    }

    public boolean printSchedule(String storeName){
       return _facade.printSchedule(storeName);
    }

    public List<Shift> approveSchedule(String storeName){
        return _facade.approveSchedule(storeName);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        return _facade.changeHoursShift(storeName, newStartHour, newEndHour, shiftID);
    }
}
