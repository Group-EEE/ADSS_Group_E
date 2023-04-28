package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class EmployeeController {
    private final HashMap<Integer,String> _passwords;
    private final HashMap<Integer, Employee> _employees;
    private final HashMap<Employee,List<Store>> _employeeStoreMap;
    private static EmployeeController _employeeController;


    private EmployeeController() {
        _passwords = new HashMap<Integer,String>();
        _employees = new HashMap<Integer,Employee>();
        _employeeStoreMap = new HashMap<Employee,List<Store>>();
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
            throw new IllegalArgumentException("Invalid arguments");
        Employee employee = new Employee(firstName, lastName, age, employeeID, bank_account);
        _employees.put(employeeID, employee);
        _passwords.put(employeeID, password);
        _employeeStoreMap.put(employee, new ArrayList<Store>());
        //_storeEmployeeMap.put(newStore, new ArrayList<Employee>());
        return true;
    }

    public Employee login(int id, String password){
        if (id < 0 || password == null)
            return null;
        if (_employees.containsKey(Integer.valueOf(id)) && _passwords.containsKey(Integer.valueOf(id)) && _passwords.get(Integer.valueOf(id)).equals(password))
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
