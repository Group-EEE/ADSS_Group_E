package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.Pair;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeesToStoreDAO extends DAO {
    private static EmployeesToStoreDAO _employeeToStoreDAO = null;
    private final String EmployeeIDColumnName = "employeeID";
    private final String StoreNameColumnName = "storeName";

    private EmployeesToStoreDAO(){
        super("EmployeesToStores");
    }

    public static EmployeesToStoreDAO getInstance(){
        if (_employeeToStoreDAO == null)
            _employeeToStoreDAO = new EmployeesToStoreDAO();
        return _employeeToStoreDAO;
    }

    public boolean insertEmployeeToStore(int employeeID, String storeName) {
        return insert(_tableName, makeList(EmployeeIDColumnName, StoreNameColumnName), makeList(employeeID, storeName));
    }

    public boolean deleteEmployeeFromStore(int employeeID, String storeName) {
        return delete(_tableName, makeList(EmployeeIDColumnName, StoreNameColumnName), makeList(employeeID, storeName));
    }

    public boolean deleteEmployee(int employeeID){
        return delete(_tableName, makeList(EmployeeIDColumnName), makeList(employeeID));
    }

    public boolean deleteStore(String storeName){
        return delete(_tableName, makeList(StoreNameColumnName), makeList(storeName));
    }

    @Override
    public Pair<Integer,String> convertReaderToObject(ResultSet rs) throws SQLException {
        return new Pair<>(rs.getInt(1),rs.getString(2));
    }

    public List<Integer> getEmployeesByStoreName(String storeName){
        return selectT(_tableName, StoreNameColumnName, makeList(storeName), makeList(EmployeeIDColumnName), Integer.class);
    }

    public List<String> getStoreNameByEmployeeID(int employeeID){
        return selectT(_tableName, StoreNameColumnName,makeList(EmployeeIDColumnName), makeList(employeeID), String.class);
    }

    public boolean checkIfEmployeeInStore(int employeeID, String storeName){
        return select(_tableName, makeList(EmployeeIDColumnName, StoreNameColumnName), makeList(employeeID, storeName)).size() > 0;
    }
}
