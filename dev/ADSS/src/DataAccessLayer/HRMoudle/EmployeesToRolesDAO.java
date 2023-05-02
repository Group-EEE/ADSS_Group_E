package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Pair;
import BussinessLayer.HRModule.Objects.RoleType;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeesToRolesDAO extends DAO {
    private static EmployeesToRolesDAO _employeesToRolesDAO = null;
    public static final String EmployeeIDColumnName = "employeeID";
    public static final String RoleTypeColumnName = "roleType";
    private int _HRmanagerID = -1;
    private EmployeesToRolesDAO(){
        super("EmployeesToRoles");
    }

    public static EmployeesToRolesDAO getInstance(){
        if (_employeesToRolesDAO == null)
            _employeesToRolesDAO = new EmployeesToRolesDAO();
        return _employeesToRolesDAO;
    }

    @Override
    public boolean Insert(Object pairObj) {
        Pair pair = (Pair) pairObj;
        int employeeID = (int)pair.getKey();
        String strRoleType = (String)pair.getValue();
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES(?, ?) "
                , _tableName, EmployeeIDColumnName, RoleTypeColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.setString(2, strRoleType);
            pstmt.executeUpdate();
            if (strRoleType.equals("HRManager"))
                _HRmanagerID = employeeID;
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    @Override
    public boolean Delete(Object pairObj) {
        Pair pair = (Pair) pairObj;
        int employeeID = (int)pair.getKey();
        String strRoleType = (String)pair.getValue();

        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? AND {2} = ?"
                , _tableName, EmployeeIDColumnName, RoleTypeColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, employeeID);
            pstmt.setString(2, strRoleType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    public boolean Delete(int employeeID) {
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ?"
                , _tableName, EmployeeIDColumnName, RoleTypeColumnName);
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
        return true;
    }


    @Override
    public Pair<Integer,String> convertReaderToObject(ResultSet rs) throws SQLException {
        return new Pair<>(rs.getInt(1),rs.getString(2));
    }

    public boolean existHRmanager(){
        if (_HRmanagerID != -1)
            return true;
        String sql = MessageFormat.format("SELECT {0} From {1} WHERE {2} = ?",
                EmployeeIDColumnName,  _tableName, RoleTypeColumnName);
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "HRManager");
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                _HRmanagerID = resultSet.getInt(1);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return false;
    }

    public int getHRmangerID() {
        if (_HRmanagerID != -1)
            return _HRmanagerID;
        existHRmanager();
        if (_HRmanagerID != -1)
            return _HRmanagerID;
        return -1;
    }

    public boolean addRolesToEmployee(Employee employee){
        if (employee == null)
            return false;
        List<String> strRoles = SelectString(RoleTypeColumnName,makeList(EmployeeIDColumnName),makeList(String.valueOf(employee.getID())));
        for (String strRole : strRoles) {
            employee.addRole(RoleType.toEnum(strRole));
        }
        return true;
    }
}
