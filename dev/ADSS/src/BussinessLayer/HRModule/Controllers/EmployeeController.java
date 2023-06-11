package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.TransportationModule.objects.License;
import BussinessLayer.TransportationModule.objects.Truck_Driver;
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
        if (firstName == null || lastName == null || age < 0 || employeeID < 0 || bankAccount == null || firstName == "" || lastName == "" || bankAccount == "")
            throw new IllegalArgumentException("Invalid arguments");
        return _employeesDAO.insertEmployee(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployement, password);
    }

    public boolean createDriver(int employeeID, String firstName, String lastName, int age, String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployement, String password, int license_id, cold_level level, double truck_weight) {
        boolean res = _employeesDAO.insertDriver(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployement, password,license_id, level, truck_weight);
        res = res && _employeesDAO.insertRoleToEmployee(employeeID, RoleType.Driver.toString());
        res = res && _employeesDAO.insertRoleToEmployee(employeeID, RoleType.DriverStandBy.toString());
        res = res && _licenseDAO.insert(employeeID,license_id,level.toString(),truck_weight);
        return res;
    }

    /**
     * @param employeeID - the id of the employee
     * @param password - the password of the employee
     * @return - the employee if the login was successful, null otherwise
     */
    public Employee login(int employeeID, String password){
        if (password == null)
            throw new IllegalArgumentException("password can't be null");
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
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
        if (newPassword == null)
            throw new IllegalArgumentException("password can't be null");
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        boolean res = _employeesDAO.getEmployee(employeeID).setNewPassword(newPassword);
        res = res && _employeesDAO.updatePassword(employeeID, newPassword);
        return res;
    }

    public boolean updateFirstName(int employeeID, String newFirstName){
        if (newFirstName == null)
            throw new IllegalArgumentException("password can't be null");
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        boolean res = _employeesDAO.getEmployee(employeeID).setNewFirstName(newFirstName);
        res = res && _employeesDAO.updateFirstName(employeeID, newFirstName);
        return res;
    }

    public boolean updateLastName(int employeeID, String newLastName){
        if (newLastName == null)
            throw new IllegalArgumentException("password can't be null");
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        boolean res = _employeesDAO.getEmployee(employeeID).setNewLastName(newLastName);
        res = res && _employeesDAO.updateLastName(employeeID, newLastName);
        return res;
    }

    public boolean updateBankAccount(int employeeID, String newBankAccount){
        if (newBankAccount == null)
            throw new IllegalArgumentException("password can't be null");
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        boolean res = _employeesDAO.getEmployee(employeeID).setNewBankAccount(newBankAccount);
        res = res && _employeesDAO.updateBankAccount(employeeID, newBankAccount);
        return res;
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

    public Truck_Driver getDriver(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        return _employeesDAO.getDriver(employeeID);
    }

    /**
     * @param employeeID - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public String getEmployeeFullNameById(int employeeID){
        if (employeeID < 0)
            throw new IllegalArgumentException("Illegal employee ID");
        return _employeesDAO.getEmployee(employeeID).getFullName();
    }

    /**
     * @param employeeID - the id of the employee
     * for HRMenuRemoveRoleFromEmployee
     */
    public List<RoleType> getEmployeeRoles(int employeeID) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        try{
            Employee employee = getEmployee(employeeID);
            return employee.getRoles();
        }catch (IllegalArgumentException e){
            System.out.println("Employee not found");
        }
        return null;
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

    public boolean removeDriver(int employeeID) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        return _licenseDAO.deleteLicense(employeeID) && _employeesDAO.deleteEmployee(employeeID);
    }


    /**
     * @param employeeID - the id of the employee
     * @param roleIndex - the role to remove
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRoleFromEmployee(int employeeID, int roleIndex) {
        if (employeeID <0)
            throw new IllegalArgumentException("Illegal employee ID");
        return getEmployee(employeeID).removeRole(roleIndex);
    }


    /**
     * @param employeeID - the id of the employee
     * @param role - the role of the employee
     * @return - true if the role was added successfully, false otherwise
     */
    public boolean addRoleToEmployee(int employeeID, RoleType role) {
        if (employeeID <0 || role == null)
            throw new IllegalArgumentException("Invalid arguments");
        boolean res = _employeesDAO.insertRoleToEmployee(employeeID, role.toString());
        return res && getEmployee(employeeID).addRole(role);
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