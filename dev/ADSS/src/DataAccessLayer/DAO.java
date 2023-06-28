package DataAccessLayer;

import java.lang.reflect.Type;
import java.sql.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


public abstract class DAO {

    protected final String _tableName;
    public static Connection connection;
    protected final String url = "jdbc:sqlite:res/SuperLi.db";
    protected DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //constructor
    public DAO(String tableName) {
        this._tableName = tableName;
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                try{
                    connection = DriverManager.getConnection("jdbc:sqlite:dev/ADSS/res/SuperLi.db");
                } catch (SQLException ex) {
                    try{
                        connection = DriverManager.getConnection("jdbc:sqlite::resource:SuperLi.db");
                    } catch (SQLException ex2) {
                        throw new RuntimeException(ex2);
                    }
                }

            }
        }
    }

    protected String keysQuery(List<Object> columnKeys) {
        String keysQuery = "";
        for (Object key : columnKeys) {
            keysQuery += " " + (String)key + " = ? AND";
        }
        keysQuery = keysQuery.substring(0, keysQuery.length() - 4);

        return keysQuery;
    }

    public List<Object> makeList(Object... objects) {
        List<Object> list = new ArrayList<Object>();
        list.addAll(Arrays.asList(objects));
        return list;
    }

    public abstract Object convertReaderToObject(ResultSet res) throws SQLException, ParseException;

    protected LocalDate parseLocalDate(String data) {
        LocalDate d = null;
        try {
            DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            d = LocalDate.parse(data, formatter_1);
        } catch (Exception e) {
        }
        return d;
    }

    public boolean delete() {
        boolean res = true;
        String sql = MessageFormat.format("DELETE FROM {0}", _tableName);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            res = false;
        }
        return res;
    }

    public boolean delete(String tableName, List<Object> columnKeys, List keys) {
        StringBuilder keysString= new StringBuilder();
        for (Object key : keys)
            keysString.append(String.valueOf(key)).append(", ");
        keysString = new StringBuilder(keysString.substring(0, keysString.length() - 2));
        String sql = MessageFormat.format("DELETE FROM {0} WHERE" + keysQuery(columnKeys)
                , tableName, keysString.toString());
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object key : keys)
                if (key instanceof String)
                    pstmt.setString(i++, (String) key);
                else
                    pstmt.setInt(i++, (int) key);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    protected List select() {
        List list = new ArrayList<>();
        String sql = MessageFormat.format("SELECT * From {0}"
                , _tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                // Fetch each row from the result set
                list.add(convertReaderToObject(resultSet));
            }

        } catch (SQLException | ParseException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    protected <T> List select(String tableName, Function<ResultSet,T> converter) {
        List list = new ArrayList<>();
        String sql = MessageFormat.format("SELECT * From {0}"
                , _tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                // Fetch each row from the result set
                list.add(converter.apply(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    protected <T> List<T> selectIN(String tableName,String columnKey, List keys,Function<ResultSet,T> converter) {
        List<T> list = new ArrayList<>();
        String joinedString ="";
        for (Object key : keys)
            joinedString += String.valueOf(key)+",";
        joinedString = joinedString.substring(0,joinedString.length()-1);
        String sql = MessageFormat.format("SELECT * From {0} WHERE "+columnKey+" IN ("+joinedString+")"
                , _tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                // Fetch each row from the result set
                list.add(converter.apply(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    protected List select(String tableName, List<Object> columnKeys, List keys) {
        List list = new ArrayList<>();
        String sql = MessageFormat.format("SELECT * From {0} WHERE" + keysQuery(columnKeys)
                , tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object key : keys)
                if (key instanceof String)
                    pstmt.setString(i++, (String) key);
                else
                    pstmt.setInt(i++, (int) key);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next())
                list.add(convertReaderToObject(resultSet));
        } catch (SQLException | ParseException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    protected <T> List<T> select(String tableName, List<Object> columnKeys, List keys, Function<ResultSet,T> converter) {
        List<T> list = new ArrayList<>();
        String sql = MessageFormat.format("SELECT * From {0} WHERE" + keysQuery(columnKeys)
                , tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object key : keys)
                if (key instanceof String)
                    pstmt.setString(i++, (String) key);
                else
                    pstmt.setInt(i++, (int) key);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next())
                list.add(converter.apply(resultSet));
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    
    protected <T> List<T> selectT(String tableName, String ColumnName, List<Object> Columnkeys, List<Object> keys,Class<T> type) {
        List list = new ArrayList<>();
        /// keys is for tables that have more that one key
        String sql = MessageFormat.format("SELECT {0} From {1} WHERE" + keysQuery(Columnkeys),
                ColumnName, tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object key : keys)
                if (key instanceof String)
                    pstmt.setString(i++, (String) key);
                else if (key instanceof Boolean)
                    pstmt.setBoolean(i++, (boolean) key);
                else if (key instanceof Double)
                    pstmt.setDouble(i++, (double) key);
                else
                    pstmt.setInt(i++, (int) key);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Object resultObj = resultSet.getObject(1);
                if (resultObj == null)
                    return list;
                list.add(type.cast(resultObj));
            }
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    protected boolean selectExists(String tableName, List<Object> columnKeys, List keys) {
        String sql = MessageFormat.format("SELECT * From {0} WHERE" + keysQuery(columnKeys)
                , tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object key : keys)
                if (key instanceof String)
                    pstmt.setString(i++, (String) key);
                else
                    pstmt.setInt(i++, (int) key);
            ResultSet resultSet = pstmt.executeQuery();
            i=1;
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return false;
    }
    protected List selectMaxString(String tableName, String ColumnName, List Columnkeys, List<String> keys) {
        List list = new ArrayList<>();
        /// keys is for tables that have more that one key
        String sql;
        if (Columnkeys == null && keys == null)
            sql = MessageFormat.format("SELECT MAX({0}) From {1}", ColumnName, tableName);
        else
            sql = MessageFormat.format("SELECT MAX({0}) From {1} WHERE" + keysQuery(Columnkeys),
                    ColumnName, tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (!(Columnkeys == null && keys == null)) {
                int i = 1;
                for (String key : keys) {
                    pstmt.setString(i, key);
                    i++;
                }
            }
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String value = resultSet.getString(1);
                if (!resultSet.wasNull())
                    list.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return list;
    }

    public boolean update(String tableName, String columnName, Object value, List columnKeys, List keys) {
        String keysString = String.join(", ", columnKeys);
        String sql = MessageFormat.format("UPDATE {0} SET {1} = ? WHERE" + keysQuery(columnKeys)
                , tableName, columnName,keysString);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (value instanceof String)
                pstmt.setString(1, (String) value);
            else
                pstmt.setObject(1, value, java.sql.Types.INTEGER);

            int i = 2;
            for (Object key : keys)
                if (key instanceof String)
                    pstmt.setString(i++, (String) key);
                else if (key instanceof Integer)
                    pstmt.setInt(i++, (int) key);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
            System.out.println(sql);
            return false;
        }
        return true;
    }

    public boolean insert(String tableName, List columnKeys, List keys) {
        String keysString = String.join(", ", columnKeys); // Combine columnKeys into a comma-separated string
        String sql =  "INSERT INTO {0} ("+String.join(",",columnKeys)+") VALUES ("+"?,".repeat(columnKeys.size()-1)+"?)";
        sql = MessageFormat.format(sql, tableName,keysString);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object key : keys) {
                if (key instanceof String)
                    pstmt.setString(i, (String) key);
                else if (key instanceof Integer)
                    pstmt.setInt(i, (Integer) key);
                else if (key instanceof Boolean)
                    pstmt.setBoolean(i, (Boolean) key);
                else if (key instanceof Double)
                    pstmt.setDouble(i, (Double) key);
                else
                    pstmt.setNull(i, Types.INTEGER);
                i++;
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
           //System.out.println("Got Exception:");
            //System.out.println(e.getMessage());
            //System.out.println(sql);
            return false;
        }
        return true;
    }
}