package BussinessLayer.Controllers;

import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;

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
        _loggedUser = null;
        return true;
    }

    public boolean setNewFirstName(String firstName){
        if (firstName == null)
            throw new IllegalArgumentException("Invalid first name");
        _loggedUser.setNewFirstName(firstName);
        return true;
    }

    public boolean setNewLastName(String lastName){
        if (lastName == null)
            throw new IllegalArgumentException("Invalid last name");
        _loggedUser.setNewLastName(lastName);
        return true;
    }

    public boolean setNewBankAccount(String bankAccount){
        if (bankAccount == null)
            throw new IllegalArgumentException("Invalid bank account");
        _loggedUser.setNewBankAccount(bankAccount);
        return true;
    }

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password) {
        if (firstName == null || lastName == null || age < 0 || id < 0 || bankAccount == null)
            throw new IllegalArgumentException("Invalid arguments");
        return _employeeController.createEmployee(firstName, lastName, age, id, bankAccount, password);
    }

    public boolean addRoleToEmployee(int employeeID, RoleType role){;
        return _employeeController.addRoleToEmployee(employeeID, role);
    }

    //_storeController
    public boolean createStore(int storeId, String storeName, String storeAddress){
        return _storeController.createStore(storeId, storeName, storeAddress);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        Employee employee = _employeeController.getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee id");
        return _storeController.addEmployeeToStore(employee, storeName);
    }
}
