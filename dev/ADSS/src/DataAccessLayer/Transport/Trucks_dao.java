package DataAccessLayer.Transport;

import DataAccessLayer.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Trucks_dao extends DAO {
    public Trucks_dao(String tableName) {
        super(tableName);
    }

    @Override
    public boolean Insert(Object obj) {
        return false;
    }

    @Override
    public boolean Delete(Object obj) {
        return false;
    }

    @Override
    public Object convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        return null;
    }
}
