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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Transport_dao extends DAO {
    private static Transport_dao transport_dao = null;

    HashMap<Integer, Transport> transports;

    private Transport_dao(String tableName) {
        super(tableName);
        transports = new HashMap<>();
        get_transports_map_from_database();
    }

    public static Transport_dao getInstance() {
        if (transport_dao == null)
            transport_dao = new Transport_dao("Transports");
        return transport_dao;
    }


    public boolean Insert(Object obj) {
        Transport transport = (Transport) obj;
        try {
            String query = "INSERT INTO " + _tableName + " (ID, Date, Departure_Time, Truck_Number, Driver_Name, Origin, Cold_Level, Suppliers, Stores, Finished, Planned_Date, Driver_ID, Estimated_End_Time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            if (transport.Started()) {
                statement.setInt(10, 1);
                insert_products_to_table(transport);
                statement.setString(13, null);
            } else {
                statement.setInt(10, 0);
            }
            statement.setString(11, transport.getPlanned_date());
            statement.setInt(12, transport.getDriver_ID());
            if (!transport.getProducts().isEmpty()) {
                insert_products_to_table(transport);
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
        if (transports.containsKey(res.getInt(1))) {
            return transports.get(res.getInt(1));
        }
        Transport transport = new Transport(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), cold_level.fromString(res.getString(7)), res.getString(11), res.getInt(12));
        transport.setStarted(res.getInt(10) == 1);
        insert_suppliers_to_transport(transport, res.getString(8));
        insert_stores_to_transport(transport, res.getString(9));
        insert_products_to_transport(transport);
        transport.setEstimated_end_time(res.getString(13));
        transports.put(transport.getTransport_ID(), transport);
        return transport;
    }

    public Transport getTransport(int transport_id) {
        if (transports.containsKey(transport_id)) {
            return transports.get(transport_id);
        }
        String query = "SELECT * FROM " + this._tableName + " WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport_id);
            ResultSet res = statement.executeQuery();
            Transport transport = (Transport) convertReaderToObject(res);
            return transport;
        } catch (SQLException e) {
            System.out.println("This transport is not exist in the Database.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean check_if_Transport_exist(int transport_id) {
        String query = "SELECT * FROM " + this._tableName + " WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport_id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("This transport is not exist in the Database.");
        }
        return false;
    }

    public ArrayList<Transport> get_transports() {
        return new ArrayList<Transport>(transports.values());
    }

    public HashMap<Integer, Transport> get_transports_map() {
        return transports;
    }

    public void get_transports_map_from_database() {
        String query = "SELECT * FROM " + this._tableName;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Transport transport = (Transport) convertReaderToObject(res);
                transports.put(transport.getTransport_ID(), transport);
            }
        } catch (SQLException e) {
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String get_suppliers_as_text(Transport transport) {
        String suppliers = "";
        for (Site site : transport.getDestinations()) {
            if (site.is_supplier()) {
                if (suppliers.equals("")) {
                    suppliers += site.getSite_name();
                } else {
                    suppliers += ", " + site.getSite_name();
                }
            }
        }
        return suppliers;
    }

    private String get_stores_as_text(Transport transport) {
        String stores = "";
        for (Site site : transport.getDestinations()) {
            if (site.is_store()) {
                if (stores.equals("")) {
                    stores += site.getSite_name();
                } else {
                    stores += ", " + site.getSite_name();
                }
            }
        }
        return stores;
    }

    private void insert_suppliers_to_transport(Transport transport, String suppliers) {
        String[] suppliers_array = suppliers.split(",");
        for (String supplier : suppliers_array) {

            if (supplier.equals("")) {
                continue;
            }
            String query = "SELECT * FROM Suppliers WHERE Name = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, supplier);
                ResultSet res = statement.executeQuery();
                if (res.next()) {
                    Supplier new_supplier = new Supplier(res.getString(1), res.getString(2), res.getString(3), res.getString(4));
                    transport.insertToDestinations(new_supplier);
                }
            } catch (SQLException e) {
            }
        }
    }

    private void insert_stores_to_transport(Transport transport, String stores) {
        String[] stores_array = stores.split(",");
        for (String store : stores_array) {

            if (store.equals("")) {
                continue;
            }
            String query = "SELECT * FROM Stores WHERE storeName = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, store);
                ResultSet res = statement.executeQuery();
                if (res.next()) {
                    Store new_store = new Store(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getInt(5));
                    transport.insertToDestinations(new_store);
                }
            } catch (SQLException e) {
            }
        }
    }


    private void insert_products_to_transport(Transport transport) {
        String query = "SELECT * FROM Products_by_transport WHERE Transport_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transport.getTransport_ID());
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                transport.insertToProducts(res.getString(2), res.getInt(3));
            }
        } catch (SQLException e){
        }
    }

    public void insert_products_to_table(Transport transport) {
        for (Map.Entry<String, Integer> entry : transport.getProducts().entrySet()) {
            String query = "INSERT INTO Products_by_transport (Transport_ID, Name, Amount) VALUES (?,?,?)";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, transport.getTransport_ID());
                statement.setString(2, entry.getKey());
                statement.setInt(3, entry.getValue());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean check_if_truck_taken_that_date(String planned_date, String registration_plate) {
        String query = "SELECT * FROM " + this._tableName + " WHERE Planned_Date = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, planned_date);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                if (res.getString(4).equals(registration_plate)) {
                    return true;
                }
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean check_if_driver_taken_that_date(String planned_date, int driver_ID) {
        String query = "SELECT * FROM " + this._tableName + " WHERE Planned_Date = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, planned_date);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                if (res.getInt(12) == driver_ID) {
                    return true;
                }
            }
        } catch (SQLException e) {

        }
        return false;
    }


    public void update_transport_date_and_time(int transport_ID, String Date, String Time) {
        String query = "UPDATE " + this._tableName + " SET Date = ?, Departure_Time = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, Date);
            statement.setString(2, Time);
            statement.setInt(3, transport_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update_transport_estimated_end_time(int transport_ID, String newTime){
        String query = "UPDATE " + this._tableName + " SET Estimated_End_Time = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newTime);
            statement.setInt(2, transport_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void mark_transport_as_finished(int transport_ID){
        String query = "UPDATE " + this._tableName + " SET Finished = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 1);
            statement.setInt(2, transport_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update_truck_number(int transport_ID, String new_truck){
        String query = "UPDATE " + this._tableName + " SET Truck_Number = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, new_truck);
            statement.setInt(2, transport_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update_driver_name_and_id(int transport_ID, int driver_id, String driver_name){
        String query = "UPDATE " + this._tableName + " SET Driver_ID = ?, Driver_Name = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, driver_id);
            statement.setString(2, driver_name);
            statement.setInt(3, transport_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // fix insert Names.
    public void insert_stores_and_suppliers_to_table(int transport_ID, String stores, String suppliers){
        String query = "UPDATE " + this._tableName + " SET Suppliers = ?, Stores = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, suppliers.substring(0, suppliers.length()-1));
            statement.setString(2, stores.substring(0, stores.length()-1));
            statement.setInt(3, transport_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
