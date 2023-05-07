package DataAccessLayer.HRMoudle;

import DataAccessLayer.DAO;
import BussinessLayer.HRModule.Objects.Employee;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class EmployeesDAO extends DAO {

    //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
    private final String IDColumnName = "employeeID";
    private final String FirstNameColumnName = "firstName";
    private final String LastNameColumnName = "lastName";
    private final String AgeColumnName = "age";
    private final String BankAccountColumnName = "bankAccount";
    private final String SalaryColumnName = "salary";
    private final String HiringConditionsColumnName = "hiringConditions";
    private final String StartOfEmploymentColumnName = "startDateOfEmployment";
    private final String FinishWorkingColumnName = "finishedWorking";
    private final String PasswordColumnName = "password";

    private static EmployeesDAO _employeesDAO;
    private static EmployeesToRolesDAO _employeesToRolesDAO;
    private HashMap<Integer, Employee> employeesCache;
    private HashMap<Integer, Driver> driverCache;

    private EmployeesDAO() {
        super("Employees");
        _employeesToRolesDAO = EmployeesToRolesDAO.getInstance();
        employeesCache = new HashMap<>();
    }
    public static EmployeesDAO getInstance(){
        if (_employeesDAO == null)
            _employeesDAO = new EmployeesDAO();
        return _employeesDAO;
    }

    public boolean insertEmployee(int employeeID, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment, String password) {
        if (employeesCache.containsKey(employeeID))
            throw new IllegalArgumentException("Employee already exists with this ID");
        insert(_tableName, makeList(IDColumnName, FirstNameColumnName, LastNameColumnName, AgeColumnName, BankAccountColumnName, SalaryColumnName, HiringConditionsColumnName, StartOfEmploymentColumnName, FinishWorkingColumnName, PasswordColumnName),
                makeList(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment.format(formatters), false, password));
        employeesCache.put(employeeID, new Employee(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password));
        return true;
    }

    public boolean deleteEmployee(int employeeID) {
        if (employeesCache.containsKey(employeeID)) {
            employeesCache.remove(employeeID);
        }
        return delete(_tableName, makeList(IDColumnName), makeList(employeeID));
    }

    public List<Employee> SelectAllEmployees() {
        return (List<Employee>) (List<?>) select();
    }

    @Override
    public Employee convertReaderToObject(ResultSet rs) throws SQLException {
        if(employeesCache.containsKey(rs.getInt(1)))
            return employeesCache.get(rs.getInt(1));
        Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), parseLocalDate(rs.getString(8)),rs.getString(9));
        _employeesToRolesDAO.addRolesToEmployee(employee);
        employeesCache.put(employee.getEmployeeID(), employee);
        return employee;
    }

    public boolean updateBankAccount(int employeeID, int bankAccount) {
        return update(_tableName,BankAccountColumnName, bankAccount, makeList(IDColumnName), makeList(employeeID));
    }

    public boolean updateSalary(int employeeID, int salary) {
        return update(_tableName, SalaryColumnName, salary, makeList(IDColumnName), makeList(employeeID));
    }

    public boolean updateHiringConditions(int employeeID, String hiringConditions) {
        return update(_tableName,HiringConditionsColumnName, hiringConditions, makeList(IDColumnName), makeList(employeeID));
    }

    public boolean updateStartOfEmployment(int employeeID, String startOfEmployment) {
        return update(_tableName, StartOfEmploymentColumnName, startOfEmployment, makeList(IDColumnName), makeList(employeeID));
    }

    public boolean updateFinishWorking(int employeeID, boolean finishWorking) {
        return update(_tableName,FinishWorkingColumnName, finishWorking, makeList(IDColumnName), makeList(employeeID));
    }

    public Employee getEmployee(int employeeID) {
        if (employeesCache.containsKey(employeeID)) //Employee in cache
            return employeesCache.get(employeeID);
        List<Employee> result = select(_tableName,makeList(IDColumnName), makeList(employeeID));
        if (result.size() == 0)
            return null;
        Employee employee = result.get(0);
        employeesCache.put(employeeID, employee);
        return employee;

    }

    public boolean existEmployee(int employeeID){
        List employees = select(_tableName,makeList(IDColumnName), makeList(employeeID));
        return employees.size() != 0;
    }

    public boolean checkPassword(int employeeID, String password){
        return selectT(_tableName,PasswordColumnName,makeList(IDColumnName,PasswordColumnName), makeList(employeeID,password),String.class).get(0).equals(password);
    }

    public boolean updatePassword(int employeeID, String password){
        return update("Passwords",PasswordColumnName,password,makeList(IDColumnName),makeList(employeeID));
    }
}


