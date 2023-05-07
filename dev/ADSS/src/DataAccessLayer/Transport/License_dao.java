package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.License;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;

public class License_dao extends DAO {
    private HashMap<Integer, License> Licenses;
    private static License_dao license_dao = null;
    public static License_dao getInstance(){
        if (license_dao == null)
            license_dao = new License_dao("Licenses");
        return license_dao;
    }
    private License_dao(String table_name){
        super(table_name);
        Licenses = new HashMap<>();
        get_all_licenses_from_db();
    }


    public boolean insert(Object licenseObj) {
        License license = (License) licenseObj;
        try {
            String query = "INSERT INTO Licenses (ID, cold_level, weight) VALUES (?, ?, ?)";

            // Prepare SQL statement with parameters
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, license.getL_ID());
            pstmt.setString(2, license.getCold_level().toString());
            pstmt.setDouble(3, license.getWeight());
            // Execute SQL statement and print result
            pstmt.executeUpdate();
            Licenses.put(license.getL_ID(), license);
            return true;
        } catch (SQLException e) {
            System.out.println("License already exist");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean Delete(Object Licenseobj) {
        License license = (License) Licenseobj;
        String query = "DELETE FROM Licenses WHERE ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, license.getL_ID());
            pstmt.executeUpdate();
            if (Licenses.containsKey(license.getL_ID())) {
                Licenses.remove(license.getL_ID());
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Exception thrown");
        }
        return false;
    }

    @Override
    public Object convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        if (Licenses.containsKey(res.getInt(1))) {
            return Licenses.get(res.getInt(1));
        }
        License license = new License(res.getInt(1), cold_level.fromString(res.getString(2)), res.getDouble(3));
        Licenses.put(license.getL_ID(), license);
        return license;
    }

    public License getLicense(int id){
        if (Licenses.containsKey(id)) {
            return Licenses.get(id);
        }
        String query = "SELECT * FROM Licenses WHERE ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, id);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                return (License) convertReaderToObject(res);
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean check_if_license_exist(int license_id){
        if (Licenses.containsKey(license_id)){
            return true;
        }
        try {
            String query = "SELECT * FROM Licenses WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, license_id);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    private void get_all_licenses_from_db(){
        try {
            String query = "SELECT * FROM Licenses";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                License license = (License)convertReaderToObject(res);
                Licenses.put(license.getL_ID(), license);
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
