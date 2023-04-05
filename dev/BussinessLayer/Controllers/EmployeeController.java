package BussinessLayer.Controllers;

import BussinessLayer.Objects.AEmployee;
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.Store;

import java.util.*;

public class EmployeeController {
    private HashMap<Integer,String> _passwords;
    private HashMap<Integer, Employee> _employees;
    private Scanner scanner;
    private static EmployeeController _employeeController = null;
    private EmployeeController() {
        scanner = new Scanner(System.in);
        _employees = new HashMap<Integer,Employee>();
        _passwords = new HashMap<Integer,String>();
    }

    public static EmployeeController getInstance() {
        if (_employeeController == null) {
            new EmployeeController();
        }
        return _employeeController;
    }
    /**
     * @param first_name - the first name of the employee
     * @param last_name - the last name of the employee
     * @param age - the age of the employee
     * @param id - the id of the employee
     * @param bank_account - the bank account of the employee
     * @param password - the password of the employee
     * @return - true if the employee was created successfully, false otherwise
     */
    public boolean createEmployee(String first_name, String last_name, int age, int id, String bank_account, String password) {
        if (first_name == null || last_name == null || age < 0 || id < 0 || bank_account == null)
            return false;
        Employee employee = new Employee(first_name, last_name, age, id, bank_account);
        _employees.put(id, employee);
        _passwords.put(id, password);
        return true;
    }

    public Employee login(int id, String password){
        if (id < 0 || password == null)
            return null;
        if (_employees.containsKey(id) && _passwords.containsKey(id) && _passwords.get(id).equals(password))
            return _employees.get(id);
        return null;
    }
    public boolean removeEmployee(int id){
        if (id < 0)
            return false;
        _employees.remove(id);
        _passwords.remove(id);
        return true;
    }
    public boolean changePassword(int id, String new_password){
        if (id < 0 || new_password == null)
            return false;
        if (_passwords.containsKey(id)){
            _passwords.replace(id, new_password);
            return true;
        }
        return false;
    }

    public Employee getEmployeeByID(int employee_id){
        if (employee_id < 0)
            return null;
        for (Map.Entry<Integer, Employee> entry : _employees.entrySet()){
            if (entry.getKey().equals(employee_id))
                return entry.getValue();
        }
        return null;
    }

}
