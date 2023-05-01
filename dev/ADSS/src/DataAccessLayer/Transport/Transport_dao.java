package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.*;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            String query = "INSERT INTO " + _tableName + " (ID, Date, Departure_Time, Truck_Number, Driver_Name, Origin, Cold_Level, Suppliers, Stores, Finished, Planned_Date, Driver_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport.getTransport_ID());
            statement.setString(2, transport.getDate());
            statement.setString(3, transport.getDeparture_time());
            statement.setString(4, transport.getTruck_number());
            statement.setString(5, transport.getDriver_name());
            statement.setString(6, transport.getOrigin());
            statement.setString(7, transport.getRequired_level().toString());
            statement.setString(8, get_suppliers_as_text(transport));
            statement.setString(9, get_stores_as_text(transport));
            if (transport.Started()){
                statement.setInt(10, 1);
                insert_products_to_table(transport);
            }
            else {
                statement.setInt(10, 0);
            }
            statement.setString(11, transport.getPlanned_date());
            statement.setInt(12, transport.getDriver_ID());
            if (!transport.getProducts().isEmpty()){
                for ()
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
        insert_suppliers_to_transport(transport, res.getString(8));
        insert_stores_to_transport(transport, res.getString(9));
        insert_products_to_transport(transport);
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

    private String get_suppliers_as_text(Transport transport){
        String suppliers = "";
        for (Site site: transport.getDestinations()){
            if (site.is_supplier()){
                if (suppliers.equals("")){
                    suppliers += site.getSite_name();
                }
                else {
                    suppliers += ", " + site.getSite_name();
                }
            }
        }
        return suppliers;
    }

    private String get_stores_as_text(Transport transport){
        String stores = "";
        for (Site site: transport.getDestinations()){
            if (site.is_store()){
                if (stores.equals("")){
                    stores += site.getSite_name();
                }
                else {
                    stores += ", " + site.getSite_name();
                }
            }
        }
        return stores;
    }

    private void insert_suppliers_to_transport(Transport transport, String suppliers){
        String[] suppliers_array = suppliers.split(",");
        for (String supplier: suppliers_array){

            if (supplier.equals("")){
                continue;
            }
            String query = "SELECT * FROM Suppliers WHERE Name = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, supplier);
                ResultSet res = statement.executeQuery();
                if (res.next()){
                    Supplier new_supplier = new Supplier(res.getString(1), res.getString(2), res.getString(3), res.getString(4));
                    transport.insertToDestinations(new_supplier);
                }
            } catch (SQLException e) {
            }
        }
    }

    private void insert_stores_to_transport(Transport transport, String stores){
        String[] stores_array = stores.split(",");
        for (String store: stores_array){

            if (store.equals("")){
                continue;
            }
            String query = "SELECT * FROM Stores WHERE Name = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, store);
                ResultSet res = statement.executeQuery();
                if (res.next()){
                    Store new_store = new Store(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));
                    transport.insertToDestinations(new_store);
                }
            } catch (SQLException e) {
            }
        }
    }


    private void insert_products_to_transport(Transport transport){
        String query = "SELECT * FROM Products WHERE Transport_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport.getTransport_ID());
            ResultSet res = statement.executeQuery();
            while (res.next()){
                transport.insertToProducts(res.getString(2), res.getInt(3));
            }
        } catch (SQLException e) {
        }
    }

    public void insert_products_to_table(Transport transport){
        try {
            for (Map.Entry<String, Integer> entry : transport.getProducts().entrySet()) {
                String query = "INSERT INTO Products (Transport_ID, Product, Amount) VALUES (?,?,?)";
                PreparedStatement statement = connection.prepareStatement();
                statement.setInt(1, transport.getTransport_ID());
                statement.setString(2, entry.getKey());
                statement.setInt(3, entry.getValue());
                statement.executeUpdate();
            }
        }
    }
}
