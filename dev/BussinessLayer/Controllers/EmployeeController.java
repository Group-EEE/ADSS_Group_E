package BussinessLayer.Controllers;

import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Store;

import java.util.*;

public class EmployeeController {
    private HashMap<Integer,String> _passwords;
    private HashMap<Integer, Employee> _employees;
    private Scanner scanner;
    private static EmployeeController _employeeController;


    private EmployeeController() {
        scanner = new Scanner(System.in);
        _employees = new HashMap<Integer,Employee>();
        _passwords = new HashMap<Integer,String>();
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
     * @param bank_account - the bank account of the employee
     * @param password - the password of the employee
     * @return - true if the employee was created successfully, false otherwise
     */
    public boolean createEmployee(String firstName, String lastName, int age, int employeeID, String bank_account, String password) {
        if (firstName == null || lastName == null || age < 0 || employeeID < 0 || bank_account == null)
            return false;
        Employee employee = new Employee(firstName, lastName, age, employeeID, bank_account);
        _employees.put(employeeID, employee);
        _passwords.put(employeeID, password);
        return true;
    }

    public Employee login(int id, String password){
        if (id < 0 || password == null)
            return null;
        if (_employees.containsKey(id) && _passwords.containsKey(id) && _passwords.get(id).equals(password))
            return _employees.get(id);
        return null;
    }


    public boolean changePassword(int employeeID, String newPassword){
        if (employeeID < 0 || newPassword == null)
            return false;
        if (_passwords.containsKey(employeeID)){
            _passwords.replace(employeeID, newPassword);
            return true;
        }
        return false;
    }

    public Employee getEmployeeByID(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        for (Map.Entry<Integer, Employee> entry : _employees.entrySet()){
            if (entry.getKey().equals(employeeID))
                return entry.getValue();
        }
        return null;
    }

    /**
     * @param employeeID - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public String getEmployeeFirstNameById(int employeeID){
        if (employeeID < 0)
            return null;
        return getEmployeeByID(employeeID).getFirstName();
    }

    /**
     * @param employeeID - the id of the employee
     * @return the list of roles of the employee
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
        List<Store> stores = employee.getStores();
        for (Store store : stores) {
            if (!store.removeEmployee(employee))
                return false;
        }
        this._employees.remove(employeeID);
        this._passwords.remove(employeeID);
        return true;
    }

    public boolean checkIfEmployeeWorkInStore(Store store,Employee employee){
        if (store == null)
            throw new IllegalArgumentException("store not found");
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        return employee.checkIfEmployeeWorkInStore(store);
    }

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
        return employee.addStore(store);
    }

    public boolean removeStoreFromEmployee(int employeeID, Store store){
        if (employeeID < 0)
            throw new IllegalArgumentException("Invalid employee ID");
        if (store == null)
            throw new IllegalArgumentException("Invalid store");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        return employee.removeStore(store);
    }

}
