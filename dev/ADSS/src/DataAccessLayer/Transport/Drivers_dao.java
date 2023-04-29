package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.Truck_Driver;
import DataAccessLayer.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

public class Drivers_dao extends DAO {
    HashMap<Integer, Truck_Driver> Drivers;

    public Drivers_dao(String table_name){
        super(table_name);
        Drivers = new HashMap<>();
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
