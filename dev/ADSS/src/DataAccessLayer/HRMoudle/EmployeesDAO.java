package DataAccessLayer.HRMoudle;

import DataAccessLayer.DAO;
import BussinessLayer.HRModule.Objects.Employee;
import java.sql.*;
import java.text.MessageFormat;
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

    //table column names

    public boolean insertEmployee(int employeeID, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment, String password) {
        if (employeesCache.containsKey(employeeID))
            throw new IllegalArgumentException("Employee already exists with this ID");
        //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}, {4}, {5}, {6} ,{7},{8},{9},{10}) VALUES(?,?,?, ?, ?, ?, ?, ?,?,?) "
                , _tableName, IDColumnName, FirstNameColumnName, LastNameColumnName, AgeColumnName, BankAccountColumnName, SalaryColumnName, HiringConditionsColumnName, StartOfEmploymentColumnName, FinishWorkingColumnName, PasswordColumnName);
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setInt(4, age);
            pstmt.setString(5, bankAccount);
            pstmt.setInt(6, salary);
            pstmt.setString(7, hiringCondition);
            pstmt.setString(8, startDateOfEmployment.format(formatters));
            pstmt.setBoolean(9, false);
            pstmt.setString(10, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("A PRIMARY KEY constraint failed"))
                throw new IllegalArgumentException("An employee with this ID already exists");
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    /*public boolean Insert(Driver driver) {
        Employee employee = new Employee(driver.getID(), driver.getName(), driver.getBankAccount(), driver.getSalary(), driver.getHiringCondition(), driver.getJobType().toString());
        boolean res = true;
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}, {4}, {5}, {6} ,{7},{8}) VALUES(?, ?, ?, ?, ?, ?,?,?) "
                , _tableName, IDColumnName, NameColumnName, BankAccountColumnName, SalaryColumnName, HiringConditionsColumnName, StartOfEmploymentColumnName, FinishWorkingColumnName, JobTypeColumnName
        );
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            //Connection connection=open
            //pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setInt(3, employee.getBankAccount());
            pstmt.setInt(4, employee.getSalary());
            pstmt.setString(5, employee.getHiringCondition());
            pstmt.setString(6, employee.getStartOfEmployment().format(formatters));
            pstmt.setBoolean(7, employee.getFinishWorking());
            pstmt.setString(8, employee.getJobType().toString());
            pstmt.executeUpdate();
            //   EmployeeConstraintsDAO.Insert


        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }
*/

    public boolean Delete(Object objectEmployeeID) {
        int employeeID = (int)objectEmployeeID;
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", _tableName, IDColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
             pstmt.setInt(1, employeeID);
             pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        employeesCache.remove(employeeID);
        return true;
    }

    public List<Employee> SelectAllEmployees() {
        return (List<Employee>) (List<?>) select();
    }

    public List<Employee> SelectAllEmployeesID() {
        return (List<Employee>) (List<?>) select();
    }

    @Override
    public Employee convertReaderToObject(ResultSet rs) throws SQLException {
        if(employeesCache.containsKey(rs.getInt(1)))
            return employeesCache.get(rs.getInt(1));
        //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
        Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), parseLocalDate(rs.getString(8)),rs.getString(9));
        _employeesToRolesDAO.addRolesToEmployee(employee);
        employeesCache.put(employee.getEmployeeID(), employee);
        return employee;
    }

    public boolean updateBankAccount(int employeeID, int bankAccount) {
        return update(_tableName,BankAccountColumnName, bankAccount, makeList(IDColumnName), makeList(employeeID));
    }

    public boolean updateSalary(int id, int salary) {
        return update(_tableName, SalaryColumnName, salary, makeList(IDColumnName), makeList(id));
    }

    public boolean updateHiringConditions(int id, String hiringConditions) {
        return update(_tableName,HiringConditionsColumnName, hiringConditions, makeList(IDColumnName), makeList(String.valueOf(id)));
    }

    public boolean updateStartOfEmployment(int id, String startOfEmployment) {
        return update(_tableName, StartOfEmploymentColumnName, startOfEmployment, makeList(IDColumnName), makeList(String.valueOf(id)));
    }

    public boolean updateFinishWorking(int id, boolean finishWorking) {
        return update(_tableName,FinishWorkingColumnName, finishWorking, makeList(IDColumnName), makeList(String.valueOf(id)));
    }

    public Employee getEmployee(int employeeID) {
        if (employeesCache.containsKey(employeeID)) //Employee in cache
            return employeesCache.get(employeeID);
        List<Employee> result = select(_tableName,makeList(IDColumnName), makeList(employeeID));
        if (result.size() == 0)
            throw new IllegalArgumentException("Employee " + employeeID + " does not exist");
        Employee employee = result.get(0);
        employeesCache.put(employeeID, employee);
        return employee;

    }

    public boolean existEmployee(int employeeID){
        List employees = select(_tableName,makeList(IDColumnName), makeList(employeeID));
        return employees.size() != 0;
    }


    /**
     * @param employeeID - the employee id
     * @param password - the password to check
     * @return true if the password is correct, false otherwise
     * the list is always with size of 1
     */
    public boolean checkPassword(int employeeID, String password){
        List<String> listPasswords = selectString(_tableName,PasswordColumnName, makeList(IDColumnName), makeList(employeeID));
        for (String passwordsFromDB : listPasswords){
            if (passwordsFromDB.equals(password))
                return true;
        }
        return false;
    }

    public boolean updatePassword(int employeeID, String password){
        return update("Passwords",PasswordColumnName,password,makeList(IDColumnName),makeList(employeeID));
    }
}


