package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.*;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;

public class Drivers_dao extends DAO {
    HashMap<Integer, Truck_Driver> Drivers;

    public Drivers_dao(String table_name){
        super(table_name);
        Drivers = new HashMap<>();
    }
    @Override
    public boolean Insert(Object Driverobj) {
        Truck_Driver driver = (Truck_Driver) Driverobj;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(u);
            String query = "INSERT INTO Drivers (registration_plate, model, License_ID) VALUES (?, ?, ?)";

            // Prepare SQL statement with parameters
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, truck.getRegistration_plate());
            pstmt.setString(2, truck.getModel());
            pstmt.setDouble(3, truck.getNet_weight());
            // Execute SQL statement and print result
            pstmt.executeUpdate();
            Trucks.put(truck.getRegistration_plate(), truck);
            return true;
        } catch (SQLException e) {
            System.out.println("Exception");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Exception thrown");
                System.out.println(ex.getMessage());
            }
        }
        return false;

        return false;
    }

    @Override
    public boolean Delete(Object obj) {
        return false;
    }

    @Override
    public Truck_Driver convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        return null;
    }

    public Truck_Driver getDriver(int id){

    }
}
