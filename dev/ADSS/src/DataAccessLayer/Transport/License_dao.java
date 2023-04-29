package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.License;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;

public class License_dao extends DAO {
    private HashMap<Integer, License> Licenses;

    public License_dao(String table_name){
        super(table_name);
        Licenses = new HashMap<>();
    }
    @Override
    public boolean Insert(Object licenseObj) {
        License license = (License) licenseObj;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            String query = "INSERT INTO License (registration_plate, model, net_weight, max_weight, cold_level) VALUES (?, ?, ?, ?, ?)";

            // Prepare SQL statement with parameters
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, license.getRegistration_plate());
            pstmt.setString(2, license.getModel());
            pstmt.setDouble(3, license.getNet_weight());
            // Execute SQL statement and print result
            pstmt.executeUpdate();
            Trucks.put(truck.getRegistration_plate(), truck);
            return true;
        } catch (SQLException e) {
            System.out.println("Truck already exist");
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
