package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.Truck;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.DAO;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Trucks_dao extends DAO {
    HashMap<String, Truck> Trucks;

    private static Trucks_dao trucks_dao = null;

    public static Trucks_dao get_instance(){
        if (trucks_dao == null)
            trucks_dao = new Trucks_dao("Trucks");
        return trucks_dao;
    }

    private Trucks_dao(String tableName) {
        super(tableName);
        Trucks = new HashMap<>();
        get_all_trucks_from_database();
    }


    public boolean Insert(Object obj) {
        Truck truck = (Truck) obj;
        try {
            String query = "INSERT INTO Trucks (registration_plate, model, net_weight, max_weight, cold_level) VALUES (?, ?, ?, ?, ?)";

            // Prepare SQL statement with parameters
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, truck.getRegistration_plate());
            pstmt.setString(2, truck.getModel());
            pstmt.setDouble(3, truck.getNet_weight());
            pstmt.setDouble(4, truck.getMax_weight());
            pstmt.setString(5, truck.getCold_level().toString());
            // Execute SQL statement and print result
            pstmt.executeUpdate();
            Trucks.put(truck.getRegistration_plate(), truck);
            return true;
        } catch (SQLException e) {
            System.out.println("Exception");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean Delete(Object Truckobj) {
        Truck truck = (Truck) Truckobj;
        String query = "DELETE FROM Trucks WHERE registration_plate = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, truck.getRegistration_plate());
            pstmt.executeUpdate();
            if (Trucks.containsKey(truck.getRegistration_plate())) {
                Trucks.remove(truck.getRegistration_plate());
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Exception thrown");
        }
        return false;
    }

    @Override
    public Truck convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        if (Trucks.containsKey(res.getString(1))) {
            return Trucks.get(res.getString(1));
        }
        Truck truck = new Truck(res.getString(1), res.getString(2), res.getDouble(3), res.getDouble(4), cold_level.valueOf(res.getString(5)), res.getDouble(3));
        Trucks.put(res.getString(1), truck);
        return truck;
    }

    public Truck get_truck_by_registration_plate(String registration_plate) {
        if (Trucks.containsKey(registration_plate)) {
            return Trucks.get(registration_plate);
        }
        String query = "SELECT * FROM Trucks WHERE registration_plate = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, registration_plate);
            ResultSet rs = pstmt.executeQuery();
            Truck truck = convertReaderToObject(rs);
            return truck;
        } catch (SQLException ex) {
            System.out.println("We don't have these truck in the Database.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean check_if_truck_exists(String registration_plate) {
        if (Trucks.containsKey(registration_plate)) {
            return true;
        }
        String query = "SELECT * FROM Trucks WHERE registration_plate = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, registration_plate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException ex) {
            System.out.println("We don't have these truck in the Database.");
        }
        return false;
    }

    public ArrayList<Truck> getTrucks() {
        return new ArrayList<>(Trucks.values());
    }

    private void get_all_trucks_from_database() {
        String query = "SELECT * FROM Trucks";
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Truck truck = convertReaderToObject(rs);
                Trucks.put(truck.getRegistration_plate(), truck);
            }

        } catch (SQLException | ParseException e) {
            System.out.println("Exception thrown");
        }
    }

}

