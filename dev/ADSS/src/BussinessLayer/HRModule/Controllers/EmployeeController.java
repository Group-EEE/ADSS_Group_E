package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.HRMoudle.EmployeesDAO;
import DataAccessLayer.Transport.License_dao;

import java.time.LocalDate;
import java.util.List;

public class EmployeeController {


    private final EmployeesDAO _employeesDAO;
    private License_dao _licenseDAO;
    private static EmployeeController _employeeController;


    private EmployeeController() {
        _employeesDAO = EmployeesDAO.getInstance();
        _licenseDAO = License_dao.getInstance();
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

    public boolean createDriver(int employeeID, String firstName, String lastName, int age, String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployement, String password, int license_id, cold_level level, double truck_weight) {
        createEmployee(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployement, password);
        return _licenseDAO.insert(employeeID,license_id,level.toString(),truck_weight);
    }

    /**
     * @param employeeID - the id of the employee
     * @param password - the password of the employee
     * @return - the employee if the login was successful, null otherwise
     */
    public Employee login(int employeeID, String password){
        if (employeeID < 0 || password == null)
            return null;
        if (!existsEmployee(employeeID))
            throw new IllegalArgumentException("Employee doesn't exists");
        if(!_employeesDAO.checkPassword(employeeID,password))
            throw new IllegalArgumentException("Wrong password");
        return _employeesDAO.getEmployee(employeeID);
    }

    public boolean existsEmployee(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        return _employeesDAO.existEmployee(employeeID);
    }


    /**
     * @param employeeID - the id of the employee
     * @param newPassword - the new password of the employee
     * @return - true if the password was changed successfully, false otherwise
     */
    public boolean updatePassword(int employeeID, String newPassword){
        if (employeeID < 0 || newPassword == null)
            return false;
        _employeesDAO.getEmployee(employeeID).setNewPassword(newPassword);
        return _employeesDAO.updatePassword(employeeID, newPassword);
    }

    public boolean updateFirstName(int employeeID, String newFirstName){
        if (employeeID < 0 || newFirstName == null)
            return false;
        _employeesDAO.getEmployee(employeeID).setNewFirstName(newFirstName);
        return _employeesDAO.updateFirstName(employeeID, newFirstName);
    }

    public boolean updateLastName(int employeeID, String newLastName){
        if (employeeID < 0 || newLastName == null)
            return false;
        _employeesDAO.getEmployee(employeeID).setNewLastName(newLastName);
        return _employeesDAO.updateLastName(employeeID, newLastName);
    }

    public boolean updateBankAccount(int employeeID, String newBankAccount){
        if (employeeID < 0 || newBankAccount == null)
            return false;
        _employeesDAO.getEmployee(employeeID).setNewBankAccount(newBankAccount);
        return _employeesDAO.updateBankAccount(employeeID, newBankAccount);
    }

    /**
     * @param employeeID - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public Employee getEmployee(int employeeID){
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
        return _employeesDAO.getEmployee(employeeID).getFullName();
    }

    /**
     * @param employeeID - the id of the employee
     * for HRMenuRemoveRoleFromEmployee
     */
    public List<RoleType> printEmployeeRoles(int employeeID) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        Employee employee = getEmployee(employeeID);
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
        return _employeesDAO.deleteEmployee(employeeID);
    }


    /**
     * @param employeeID - the id of the employee
     * @param roleIndex - the role to remove
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRoleFromEmployee(int employeeID, int roleIndex) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        Employee employee = getEmployee(employeeID);
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
        Employee employee = getEmployee(employeeID);
        if (employee == null)
            throw new IllegalArgumentException("employee not found");
        if (!_employeesDAO.insertRoleToEmployee(employeeID, role.toString()))
            return false;
        return employee.addRole(role);
    }

    public List<Employee> getAllEmployees() {
        return _employeesDAO.SelectAllEmployees();
    }

    public boolean hasHRManager(){
        return _employeesDAO.existHRmanager();
    }

    public int getHRManagerID(){
        return _employeesDAO.getHRManagerID();
    }

}