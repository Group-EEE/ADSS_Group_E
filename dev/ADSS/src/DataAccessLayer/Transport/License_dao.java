package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.License;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

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


    public boolean insert(int employeeID, int license_id, String level, double truck_weight) {
        return insert(_tableName,makeList("employeeID", "licenseID", "cold_Level", "weight"),makeList(employeeID, license_id, level, truck_weight));
    }


    public boolean deleteLicense(int employeeID) {
        if (Licenses.containsKey(employeeID)) {
            Licenses.remove(employeeID);
        }
        return delete(_tableName,makeList("employeeID"),makeList(employeeID));
    }

    @Override
    public Object convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        if (Licenses.containsKey(res.getInt(1))) {
            return Licenses.get(res.getInt(1));
        }
        License license = new License(res.getInt(1), res.getInt(2), cold_level.fromString(res.getString(3)), res.getDouble(4));
        Licenses.put(license.getL_ID(), license);
        return license;
    }

    public License getLicense(int id){
        if (Licenses.containsKey(id)) {
            return Licenses.get(id);
        }
        List<License> licenses = select(_tableName,makeList("employeeID"),makeList(id));
        if (licenses.size() == 0)
            return null;
        Licenses.put(id, licenses.get(0));
        return licenses.get(0);
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
