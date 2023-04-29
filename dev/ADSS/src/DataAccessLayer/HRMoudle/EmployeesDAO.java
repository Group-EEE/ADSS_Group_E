package DataAccessLayer.HRMoudle;

import DataAccessLayer.DAO;
import BussinessLayer.HRModule.Objects.Employee;
import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

public class EmployeesDAO extends DAO {

    //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
    private static final String TableName = "Employee";
    public static final String IDColumnName = "ID";
    public static final String FirstNameColumnName = "FirstName";
    public static final String LastNameColumnName = "lastName";
    public static final String AgeColumnName = "Age";
    public static final String BankAccountColumnName = "BankAccount";
    public static final String SalaryColumnName = "Salary";
    public static final String HiringConditionsColumnName = "HiringConditions";
    public static final String StartOfEmploymentColumnName = "StartOfEmployment";
    public static final String FinishWorkingColumnName = "FinishWorking";

    private static EmployeesDAO _employeesDAO = null;
    private HashMap<Integer, Employee> employeesCache;
    private HashMap<Integer, Driver> driverCache;

    private EmployeesDAO() {
        super(TableName);
        employeesCache = new HashMap<>();
    }
    public static EmployeesDAO getInstance(){
        if (_employeesDAO == null)
            _employeesDAO = new EmployeesDAO();
        return _employeesDAO;
    }

    //table column names

    public boolean Insert(Object employeeObj) {
        Employee employee = (Employee) employeeObj;
        boolean res = true;
        //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}, {3}, {4}, {5}, {6} ,{7},{8}) VALUES(?, ?, ?, ?, ?, ?,?,?) "
                , _tableName, IDColumnName, FirstNameColumnName, LastNameColumnName, AgeColumnName, BankAccountColumnName, SalaryColumnName, HiringConditionsColumnName, StartOfEmploymentColumnName);
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getID());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setInt(4, employee.getAge());
            pstmt.setString(5, employee.getBankAccount());
            pstmt.setInt(6, employee.getSalary());
            pstmt.setString(7, employee.getHiringCondition());
            pstmt.setString(8, employee.getStartDateOfEmployement().format(formatters));
            //pstmt.setBoolean(8, employee.getFinishWorking());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
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

    @Override
    public boolean Delete(Object employeeObj) {
        Employee employee = (Employee) employeeObj;
        boolean res = true;
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", _tableName, IDColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
             pstmt.setInt(1, employee.getID());
             pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    public boolean inCache(int idEmployee){
        return employeesCache.containsKey(idEmployee);
    }

    public List<Employee> SelectAllEmployees() {
        return (List<Employee>) (List<?>) Select();
    }

    public List<Employee> SelectAllEmployeesID() {
        return (List<Employee>) (List<?>) Select();
    }

    @Override
    public Employee convertReaderToObject(ResultSet rs) throws SQLException {
        if(employeesCache.containsKey(rs.getInt(1)))
            return employeesCache.get(rs.getInt(1));
        //int id, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
        Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), parseLocalDate(rs.getString(8)));
        employeesCache.put(employee.getID(), employee);
        return employee;
    }

    public void setBankAccount(int id, int bankAccount) {
        Update(BankAccountColumnName, bankAccount, makeList(IDColumnName), makeList(String.valueOf(id)));
    }

    public void setSalary(int id, int salary) {
        Update(SalaryColumnName, salary, makeList(IDColumnName), makeList(String.valueOf(id)));
    }

    public void setHiringConditions(int id, String hiringConditions) {
        Update(HiringConditionsColumnName, hiringConditions, makeList(IDColumnName), makeList(String.valueOf(id)));
    }

    public void setStartOfEmployment(int id, String startOfEmployment) {
        Update(StartOfEmploymentColumnName, startOfEmployment, makeList(IDColumnName), makeList(String.valueOf(id)));
    }

    public void setFinishWorking(int id, boolean finishWorking) {
        Update(FinishWorkingColumnName, finishWorking, makeList(IDColumnName), makeList(String.valueOf(id)));
    }


    public Employee getEmployee(int id) {
        if (employeesCache.containsKey(id)) //Employee in cache
            return employeesCache.get(id);
        else {//Employee in db
            List<Employee> result = Select(makeList(IDColumnName), makeList(String.valueOf(id)));
            if (result.size() == 0)
                throw new IllegalArgumentException("Employee " + id + " does not exist");
            Employee employee = result.get(0);
            employeesCache.put(id, employee);
            return employee;
        }
    }


    public boolean ExistEmployee(int id){
        boolean exist =false;
        /// keys is for tables that have more than one key
        String sql = MessageFormat.format("SELECT {0} From {1} WHERE {2} = ?",
                IDColumnName,  _tableName, IDColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                // Fetch each row from the result set
                exist=true;
            }

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return exist;
    }

    public void resetCache() {
        employeesCache.clear();
    }


}


