package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.TransportationModule.objects.License;
import BussinessLayer.TransportationModule.objects.Truck_Driver;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.DAO;
import BussinessLayer.HRModule.Objects.Employee;
import DataAccessLayer.Transport.License_dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class EmployeesDAO extends DAO {
    //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
    private final String EmployeeIDColumnName = "employeeID";
    private final String FirstNameColumnName = "firstName";
    private final String LastNameColumnName = "lastName";
    private final String AgeColumnName = "age";
    private final String BankAccountColumnName = "bankAccount";
    private final String SalaryColumnName = "salary";
    private final String HiringConditionsColumnName = "hiringConditions";
    private final String StartOfEmploymentColumnName = "startDateOfEmployment";
    private final String FinishWorkingColumnName = "finishedWorking";
    private final String PasswordColumnName = "password";

    //EmployeesToRolesDAO
    private final String RoleTypeColumnName = "roleType";
    private int _HRmanagerID = -1;

    //licenseDAO
    private final String LicenseIDColumnName = "licenseID";
    private final String cold_levelColumnName = "cold_level";
    private final String truck_weightColumnName = "weight";

    private static EmployeesDAO _employeesDAO;
    private HashMap<Integer, Employee> employeesCache;
    private HashMap<Integer, Truck_Driver> driverCache;

    private EmployeesDAO() {
        super("Employees");
        employeesCache = new HashMap<>();
        driverCache = new HashMap<>();
    }
    public static EmployeesDAO getInstance(){
        if (_employeesDAO == null)
            _employeesDAO = new EmployeesDAO();
        return _employeesDAO;
    }

    public boolean insertEmployee(int employeeID, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment, String password) {
        if (employeesCache.containsKey(employeeID))
            throw new IllegalArgumentException("Employee already exists with this ID");
        insert(_tableName, makeList(EmployeeIDColumnName, FirstNameColumnName, LastNameColumnName, AgeColumnName, BankAccountColumnName, SalaryColumnName, HiringConditionsColumnName, StartOfEmploymentColumnName, FinishWorkingColumnName, PasswordColumnName),
                makeList(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment.format(formatters), false, password));
        employeesCache.put(employeeID, new Employee(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password));
        return true;
    }
    public boolean insertDriver(int employeeID, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment, String password, int license_id, cold_level level, double truck_weight) {
        if (driverCache.containsKey(employeeID))
            throw new IllegalArgumentException("Employee already exists with this ID");
        insert(_tableName, makeList(EmployeeIDColumnName, FirstNameColumnName, LastNameColumnName, AgeColumnName, BankAccountColumnName, SalaryColumnName, HiringConditionsColumnName, StartOfEmploymentColumnName, FinishWorkingColumnName, PasswordColumnName),
                makeList(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment.format(formatters), false, password));
        driverCache.put(employeeID, new Truck_Driver(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password,new License(employeeID,license_id, level, truck_weight)));
        return true;
    }

    public boolean deleteEmployee(int employeeID) {
        if (employeesCache.containsKey(employeeID)) {
            employeesCache.remove(employeeID);
        }
        deleteAllRolesFromEmployee(employeeID);
        return delete(_tableName, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public List<Employee> SelectAllEmployees(){
        List<Employee> employees = select();
        for (Employee employee : employees) {
            if (!employeesCache.containsKey(employee.getEmployeeID()))
                employeesCache.put(employee.getEmployeeID(), employee);
        }
        return employees;
    }

    public List<Truck_Driver> getDrivers(){
        //get list of all ids of drivers
        List<Integer> driversIDs = selectT("EmployeesToRoles",EmployeeIDColumnName,makeList(RoleTypeColumnName),makeList("Driver"),Integer.class);
        List<Truck_Driver> drivers = selectIN(_tableName,EmployeeIDColumnName,driversIDs,this::convertDriver);
        for (Truck_Driver driver : drivers)
            if (!driverCache.containsKey(driver.getEmployeeID()))
                driverCache.put(driver.getEmployeeID(), driver);
        return drivers;
    }

    public boolean existDriver(int employeeID){
        //TODO:
        return true;
    }


    public Truck_Driver convertDriver(ResultSet rs){
        try {
            int employeeID = rs.getInt(1);
            if (driverCache.containsKey(employeeID))
                return driverCache.get(employeeID);
            License license = License_dao.getInstance().getLicense(employeeID);
            Truck_Driver truckDriver = new Truck_Driver(employeeID, rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), parseLocalDate(rs.getString(8)), rs.getString(9), license);
            addRolesToEmployee(truckDriver);
            driverCache.put(truckDriver.getEmployeeID(), truckDriver);
            return truckDriver;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Employee convertReaderToObject(ResultSet rs) throws SQLException {
        int employeeID = rs.getInt(1);
        if (employeesCache.containsKey(employeeID))
            return employeesCache.get(employeeID);
        Employee employee;
        employee = new Employee(employeeID, rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), parseLocalDate(rs.getString(8)), rs.getString(9));
        addRolesToEmployee(employee);
        employeesCache.put(employee.getEmployeeID(), employee);
        return employee;

    }

    public boolean updateBankAccount(int employeeID, String bankAccount) {
        return update(_tableName,BankAccountColumnName, bankAccount, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean updateSalary(int employeeID, int salary) {
        return update(_tableName, SalaryColumnName, salary, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean updateFirstName(int employeeID, String firstName) {
        return update(_tableName, FirstNameColumnName, firstName, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean updateLastName(int employeeID, String lastName) {
        return update(_tableName, LastNameColumnName, lastName, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean updateFinishWorking(int employeeID, boolean finishWorking) {
        return update(_tableName,FinishWorkingColumnName, finishWorking, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean updatePassword(int employeeID, String password) {
        return update(_tableName, PasswordColumnName, password, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public Employee getEmployee(int employeeID) {
        if (employeesCache.containsKey(employeeID)) //Employee in cache
            return employeesCache.get(employeeID);
        List<Employee> result = select(_tableName, makeList(EmployeeIDColumnName), makeList(employeeID));
        if (result.size() == 0)
            throw new IllegalArgumentException("Employee ID doesn't exist");
        Employee employee = result.get(0);
        employeesCache.put(employeeID, employee);
        return employee;
    }

    public Truck_Driver getDriver(int employeeID) {
        if (driverCache.containsKey(employeeID)) //Employee in cache
            return driverCache.get(employeeID);
        List<Truck_Driver> result = select(_tableName, makeList(EmployeeIDColumnName), makeList(employeeID),this::convertDriver);
        if (result.size() == 0)
            throw new IllegalArgumentException("Employee ID doesn't exist");
        Truck_Driver truckDriver = result.get(0);
        driverCache.put(employeeID, truckDriver);
        return truckDriver;
    }


    public boolean existEmployee(int employeeID){
        try{
            getEmployee(employeeID);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public boolean checkPassword(int employeeID, String password){
        return selectT(_tableName,PasswordColumnName,makeList(EmployeeIDColumnName), makeList(employeeID),String.class).get(0).equals(password);
    }



    public boolean insertRoleToEmployee(int employeeID, String strRoleType) {
        if (strRoleType.equals("HRManager"))
            _HRmanagerID = employeeID;
        return insert("EmployeesToRoles", makeList(EmployeeIDColumnName, RoleTypeColumnName), makeList(employeeID, strRoleType));
    }

    public boolean deleteRoleFromEmployee(int employeeID,String strRoleType) {
        return delete("EmployeesToRoles", makeList(EmployeeIDColumnName, RoleTypeColumnName), makeList(employeeID, strRoleType));
    }

    public boolean deleteAllRolesFromEmployee(int employeeID) {
        return delete("EmployeesToRoles", makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean existHRmanager(){
        if (_HRmanagerID != -1)
            return true;
        List<Integer> result = selectT("EmployeesToRoles",EmployeeIDColumnName,makeList(RoleTypeColumnName),makeList("HRManager"),Integer.class);
        if (result.size() == 0)
            return false;
        _HRmanagerID = result.get(0);
        return true;
    }

    public boolean isDriver(int employeeID){
        return selectT("EmployeesToRoles",RoleTypeColumnName,makeList(EmployeeIDColumnName),makeList(employeeID),String.class).contains("Driver");
    }

    public int getHRManagerID() {
        if (_HRmanagerID != -1)
            return _HRmanagerID;
        existHRmanager();
        return _HRmanagerID;
    }

    public boolean addRolesToEmployee(Employee employee){
        if (employee == null)
            return false;
        List<String> strRoles = selectT("EmployeesToRoles",RoleTypeColumnName,makeList(EmployeeIDColumnName),makeList(employee.getEmployeeID()),String.class);
        for (String strRole : strRoles) {
            employee.addRole(RoleType.valueOf(strRole));
        }
        return true;
    }
}




