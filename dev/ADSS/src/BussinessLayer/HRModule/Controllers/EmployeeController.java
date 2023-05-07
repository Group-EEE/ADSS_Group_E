package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Pair;
import BussinessLayer.HRModule.Objects.RoleType;
import DataAccessLayer.HRMoudle.EmployeesDAO;
import DataAccessLayer.HRMoudle.EmployeesToRolesDAO;

import java.time.LocalDate;
import java.util.List;

public class EmployeeController {


    private final EmployeesDAO _employeesDAO;
    private final EmployeesToRolesDAO _employeesToRolesDAO;

    private static EmployeeController _employeeController;


    private EmployeeController() {
        _employeesDAO = EmployeesDAO.getInstance();
        _employeesToRolesDAO = EmployeesToRolesDAO.getInstance();
        //_employeesToStoreDAO = EmployeesToStoreDAO.getInstance();
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
        return _employeesDAO.insertEmployee(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployement, password);
    }

    /**
     * @param employeeID - the id of the employee
     * @param password - the password of the employee
     * @return - the employee if the login was successful, null otherwise
     */
    public Employee login(int employeeID, String password){
        if (employeeID < 0 || password == null)
            return null;
        if (!_employeesDAO.existEmployee(employeeID))
            return null;
        if(!_employeesDAO.checkPassword(employeeID,password))
            return null;
        return _employeesDAO.getEmployee(employeeID);
    }


    /**
     * @param employeeID - the id of the employee
     * @param newPassword - the new password of the employee
     * @return - true if the password was changed successfully, false otherwise
     */
    public boolean updatePassword(int employeeID, String newPassword){
        if (employeeID < 0 || newPassword == null)
            return false;
        return _employeesDAO.updatePassword(employeeID, newPassword);
    }

    /**
     * @param employeeID - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public Employee getEmployeeByID(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        return _employeesDAO.getEmployee(employeeID);
    }

    /**
     * @param employeeID - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public String getEmployeeFullNameById(int employeeID){
        if (employeeID < 0)
            return null;
        return _employeesDAO.getEmployee(employeeID).getFullNameName();
    }

    /**
     * @param employeeID - the id of the employee
     * for HRMenuRemoveRoleFromEmployee
     */
    public List<RoleType> printEmployeeRoles(int employeeID) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        Employee employee = getEmployeeByID(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("Employee not found");
        return employee.getRoles();
    }

    /**
     * @param employeeID - the id of the employee
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeEmployee(int employeeID) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        if (!_employeesDAO.deleteEmployee(employeeID))
            return false;
        return _employeesToRolesDAO.Delete(employeeID);
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
        _employeesToRolesDAO.insert(new Pair<Integer,String>(employeeID, role.toString()));
        return employee.addRole(role);
    }

    public List<Employee> getAllEmployees() {
        return _employeesDAO.SelectAllEmployees();
    }

}