package DataAccessLayer.HRMoudle;
import BussinessLayer.HRModule.Objects.Pair;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.List;

public class PasswordsDAO extends DAO {
    private static PasswordsDAO _passwordsDAO = null;
    private static final String TableName = "Passwords";
    public static final String IDColumnName = "employeeID";
    public static final String PasswordColumnName = "password";

    private PasswordsDAO(){
        super(TableName);
    }

    public static PasswordsDAO getInstance(){
        if (_passwordsDAO == null)
            _passwordsDAO = new PasswordsDAO();
        return _passwordsDAO;
    }

    @Override
    public boolean Insert(Object pairObject){
        boolean res = true;
        int employeeID = (int)(((Pair)pairObject).getKey());
        String password = (String)(((Pair)pairObject).getValue());
        String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES(?, ?) "
                , _tableName, IDColumnName, PasswordColumnName
        );
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        }
        catch ( SQLException e){
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    @Override
    public boolean Delete(Object employeeIDObj) {
        int employeeID = (Integer)employeeIDObj;
        boolean res = true;
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ? ", _tableName, IDColumnName);

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    public Pair<Integer,String> convertReaderToObject(ResultSet rs) throws SQLException {
        Integer employeeID = rs.getInt(1);
        String password = rs.getString(2);
        return new Pair<>(employeeID,password);
    }

    public boolean checkPassword(int employeeID, String password){
        List<ResultSet> listRS = Select(IDColumnName);
        for (ResultSet rs : listRS) {
            try {
                Pair<Integer,String> pair = convertReaderToObject(rs);
                if (pair.getKey() == employeeID && pair.getValue().equals(password))
                    return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updatePassword(int employeeID, String password){
        return Update(IDColumnName,password,Integer.toString(employeeID));
    }
}
