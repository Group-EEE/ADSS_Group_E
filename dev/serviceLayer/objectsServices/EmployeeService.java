package serviceLayer.objectsServices;

import BussinessLayer.Controllers.Facade;
import BussinessLayer.Objects.RoleType;


public class EmployeeService {
    private static EmployeeService _employeeService;
    private final Facade _facade;

    private EmployeeService(){
        _facade = Facade.getInstance();
    }

    public static EmployeeService getInstance(){
        if(_employeeService == null)
            _employeeService = new EmployeeService();
        return _employeeService;
    }
    public boolean login(int id, String password){
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

    public boolean createEmployee(String firstName, String lastName, int age, int id, String bankAccount, String password, boolean isHRManager) {
        return _facade.createEmployee(firstName, lastName, age, id, bankAccount, password, isHRManager);
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

    public boolean printEmployees(){
        return _facade.printEmployees();
    }
}
