package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.Transport;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Transport_dao extends DAO {

    HashMap<Integer, Transport> transports;

    public Transport_dao(String tableName) {
        super(tableName);
        transports = new HashMap<>();
        get_transports_map_from_database();
    }

    @Override
    public boolean Insert(Object obj) {
        Transport transport = (Transport) obj;
        try {
            String query = "INSERT INTO " + _tableName + " (ID, Date, Departure_Time, Truck_Number, Driver_Name, Origin, Cold_Level, Finished ) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport.getTransport_ID());
            statement.setString(2, transport.getDate());
            statement.setString(3, transport.getDeparture_time());
            statement.setString(4, transport.getTruck_number());
            statement.setString(5, transport.getDriver_name());
            statement.setString(6, transport.getOrigin());
            statement.setString(7, transport.getRequired_level().toString());
            if (transport.Started()){
                statement.setInt(8, 1);
            }
            else {
                statement.setInt(8, 0);
            }
            statement.executeUpdate();

            transports.put(transport.getTransport_ID(), transport);
            return true;

        } catch (SQLException e) {
            System.out.println("Exception thrown");
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean Delete(Object obj) {
        Transport transport = (Transport) obj;
        String query = "DELETE FROM " + this._tableName + " WHERE Transport_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport.getTransport_ID());
            statement.executeUpdate();

            transports.remove(transport.getTransport_ID());
            return true;

        } catch (SQLException e) {
            System.out.println("Exception thrown");
        }
        return false;
    }

    @Override
    public Object convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        if(transports.containsKey(res.getInt(1))){
            return transports.get(res.getInt(1));
        }
        Transport transport = new Transport(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), cold_level.fromString(res.getString(7)), res.getString(11), res.getInt(12));
        transport.setStarted(res.getInt(10) == 1);
        transports.put(transport.getTransport_ID(), transport);
        return transport;
    }

    public Transport getTransport(int transport_id){
        if(transports.containsKey(transport_id)){
            return transports.get(transport_id);
        }
        String query = "SELECT * FROM " + this._tableName + " WHERE ID = ?";
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport_id);
            ResultSet res = statement.executeQuery();
            Transport transport = (Transport) convertReaderToObject(res);
            return transport;
        }
        catch (SQLException e) {
            System.out.println("This transport is not exist in the Database.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean check_if_Transport_exist(int transport_id){
        String query = "SELECT * FROM " + this._tableName + " WHERE ID = ?";
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport_id);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("This transport is not exist in the Database.");
        }
        return false;
    }

    public ArrayList<Transport> get_transports(){
        return new ArrayList<Transport>(transports.values());
    }

    public HashMap<Integer, Transport> get_transports_map() {
        return transports;
    }

    public void get_transports_map_from_database(){
        String query = "SELECT * FROM " + this._tableName;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                Transport transport = (Transport) convertReaderToObject(res);
                transports.put(transport.getTransport_ID(), transport);
            }
        }
        catch (SQLException e) {
            System.out.println("This transport is not exist in the Database.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
