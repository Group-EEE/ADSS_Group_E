package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Pair;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.HRMoudle.EmployeesDAO;
import DataAccessLayer.HRMoudle.PasswordsDAO;
import DataAccessLayer.HRMoudle.EmployeesToStoreDAO;
import DataAccessLayer.HRMoudle.EmployeesToRolesDAO;

import java.time.LocalDate;
import java.util.Map;

public class EmployeeController {


    private final EmployeesDAO _employeesDAO;
    private final PasswordsDAO _passwordsDAO;
    private final EmployeesToStoreDAO _employeesToStoreDAO;
    private final EmployeesToRolesDAO _employeesToRolesDAO;

    private static EmployeeController _employeeController;


    private EmployeeController() {
        _passwordsDAO = PasswordsDAO.getInstance();
        _employeesDAO = EmployeesDAO.getInstance();
        _employeesToRolesDAO = EmployeesToRolesDAO.getInstance();
        _employeesToStoreDAO = EmployeesToStoreDAO.getInstance();
    }

    public static EmployeeController getInstance() {
        if (_employeeController == null) {
            _employeeController = new EmployeeController();
        }
        return _employeeController;
    }


    /**
     * @param firstName - the first name of the employee
     * @param lastName - the last name of the employee
     * @param age - the age of the employee
     * @param employeeID - the id of the employee
     * @param bankAccount - the bank account of the employee
     * @param password - the password of the employee
     * @return - true if the employee was created successfully, false otherwise
     */
    public boolean createEmployee(int employeeID, String firstName, String lastName, int age, String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployement, String password) {
        if (firstName == null || lastName == null || age < 0 || employeeID < 0 || bankAccount == null)
            throw new IllegalArgumentException("Invalid arguments");
        Employee employee = new Employee(employeeID, firstName,lastName, age , bankAccount, salary, hiringCondition, startDateOfEmployement);
        _employeesDAO.Insert(employee);
        _passwordsDAO.Insert(new Pair<Integer,String>(employeeID, password));
        return true;
    }

    public Employee login(int id, String password){
        if (id < 0 || password == null)
            return null;
        if (_employeesDAO.ExistEmployee(Integer.valueOf(id)) && PasswordsDAO.(Integer.valueOf(id)) && _passwords.get(Integer.valueOf(id)).equals(password))
            return _employees.get(Integer.valueOf(id));
        return null;
    }


    public boolean changePassword(int employeeID, String newPassword){
        if (employeeID < 0 || newPassword == null)
            return false;
        if (_passwords.containsKey(Integer.valueOf(employeeID))){
            _passwords.replace(employeeID, newPassword);
            return true;
        }
        return false;
    }

    public Employee getEmployeeByID(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        for (Map.Entry<Integer, Employee> entry : _employees.entrySet()){
            if (entry.getKey().equals(Integer.valueOf(employeeID)))
                return entry.getValue();
        }
        return null;
    }

    /**
     * @param employeeID - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public String getEmployeeNameById(int employeeID){
        if (employeeID < 0)
            return null;
        return getEmployeeByID(employeeID).getName();
    }

    /**
     * @param employeeID - the id of the employee
     * for HRMenuRemoveRoleFromEmployee
     */
    public boolean printEmployeeRoles(int employeeID) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("Employee not found");
        for (int i=1; i<=employee.getRoles().size(); i++) {
            System.out.println(i + ". " + employee.getRoles().get(i-1));
        }
        return true;
    }

    /**
     * @param employeeID - the id of the employee
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeEmployee(int employeeID) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }
        this._employees.remove(Integer.valueOf(employeeID));
        this._passwords.remove(Integer.valueOf(employeeID));
        this._employeeStoreMap.remove(employee);

        return true;
    }

//    public boolean checkIfEmployeeWorkInStore(Store store,Employee employee){
//        if (store == null)
//            throw new IllegalArgumentException("store not found");
//        if (employee == null)
//            throw new IllegalArgumentException("employee not found");
//        return employee.checkIfEmployeeWorkInStore(store);
//    }

    /**
     * @param employeeID - the id of the employee
     * @param roleIndex - the role to remove
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRoleFromEmployee(int employeeID, int roleIndex) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        return employee.removeRole(roleIndex);
    }


    /**
     * @param employeeID - the id of the employee
     * @param role - the role of the employee
     * @return - true if the role was added successfully, false otherwise
     */
    public boolean addRoleToEmployee(int employeeID, RoleType role) {
        if (employeeID <0 || role == null)
            throw new IllegalArgumentException("Invalid arguments");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        return employee.addRole(role);
    }

    /**
     * @param employeeID - the id of the employee
     * @param store - the store to add
     * @return - true if the store was added successfully, false otherwise
     */
    public boolean addStoreToEmployee(int employeeID, Store store){
        if (employeeID < 0)
            throw new IllegalArgumentException("Invalid employee ID");
        if (store == null)
            throw new IllegalArgumentException("Invalid store");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        _employeeStoreMap.get(employee).add(store);
        return true;
    }

    public boolean removeStoreFromEmployee(int employeeID, Store store){
        if (employeeID < 0)
            throw new IllegalArgumentException("Invalid employee ID");
        if (store == null)
            throw new IllegalArgumentException("Invalid store");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        _employeeStoreMap.get(employee).remove(store);
        return true;
    }

    public boolean printEmployees() {
        for (Map.Entry<Integer, Employee> entry : _employees.entrySet()){
            System.out.println(entry.getValue().toString());
        }
        return true;
    }

}