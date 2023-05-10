//package DataAccessLayer.Transport;
//
//import BussinessLayer.TransportationModule.objects.*;
//import DataAccessLayer.DAO;
//
//import java.sql.*;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//
//public class Drivers_dao extends DAO {
//    HashMap<Integer, Truck_Driver> Drivers;
//    private License_dao L_dao;
//    private static Drivers_dao drivers_dao = null;
//
//
//    private Drivers_dao(String table_name){
//        super(table_name);
//        L_dao = License_dao.getInstance();
//        Drivers = new HashMap<>();
//        get_all_drivers_from_db();
//    }
//
//    public static Drivers_dao get_instance(){
//        if (drivers_dao == null)
//            drivers_dao = new Drivers_dao("Drivers");
//        return drivers_dao;
//    }
//
//
//    public boolean Insert(Object Driverobj) {
//        Truck_Driver driver = (Truck_Driver) Driverobj;
//        try {
//            String query = "INSERT INTO Drivers (ID, Name, License_ID) VALUES (?, ?, ?)";
//            L_dao.insert(driver.getLicense());
//            // Prepare SQL statement with parameters
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, driver.getID());
//            pstmt.setString(2, driver.getName());
//            pstmt.setInt(3, driver.getLicense().getL_ID());
//            // Execute SQL statement and print result
//            pstmt.executeUpdate();
//            if (!Drivers.containsKey(driver.getID())){
//                Drivers.put(driver.getID(), driver);
//        }
//            return true;
//
//        } catch (SQLException e) {
//            System.out.println("Exception");
//            System.out.println(e.getMessage());
//        }
//
//        return false;
//    }
//
//
//    public boolean Delete(Object obj) {
//        Truck_Driver driver = (Truck_Driver) obj;
//        try {
//            String query = "DELETE FROM Drivers WHERE ID = ?";
//            // Prepare SQL statement with parameters
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, driver.getID());
//            // Execute SQL statement and print result
//            pstmt.executeUpdate();
//            if (Drivers.containsKey(driver.getID())){
//                Drivers.remove(driver.getID());
//            }
//            return true;
//
//        } catch (SQLException e) {
//            System.out.println("Exception");
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Exception thrown");
//                System.out.println(ex.getMessage());
//            }
//        }
//
//        return false;
//    }
//
//    @Override
//    public Truck_Driver convertReaderToObject(ResultSet res) throws SQLException, ParseException {
//        try {
//            String query = "SELECT * FROM Drivers WHERE ID = ?";
//
//            // Prepare SQL statement with parameters
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, res.getInt("ID"));
//            // Execute SQL statement and print result
//            ResultSet rs = pstmt.executeQuery();
//            Truck_Driver driver = new Truck_Driver(rs.getInt("ID"), rs.getString("Name"), L_dao.getLicense(rs.getInt("License_ID")));
//            return driver;
//
//        }
//        catch (SQLException e) {
//            System.out.println("Exception");
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public Truck_Driver getDriver(int id){
//        if (Drivers.containsKey(id)){
//            return Drivers.get(id);
//        }
//        String query = "SELECT * FROM Drivers WHERE ID = ?";
//        try {
//            // Prepare SQL statement with parameters
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, id);
//            // Execute SQL statement and print result
//            ResultSet rs = pstmt.executeQuery();
//            Truck_Driver driver = convertReaderToObject(rs);
//            return driver;
//
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean check_if_driver_exists(int id){
//        if (Drivers.containsKey(id)){
//            return true;
//        }
//        String query = "SELECT * FROM Drivers WHERE ID = ?";
//        try {
//            // Prepare SQL statement with parameters
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, id);
//            // Execute SQL statement and print result
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next() == false)
//                return false;
//            if (rs.next() == true)
//                return true;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return false;
//    }
//
//    public ArrayList<Truck_Driver> getDrivers() {
//        Collection<Truck_Driver> drivers = Drivers.values();
//        return new ArrayList<>(drivers);
//    }
//
//    private void get_all_drivers_from_db(){
//        String query = "SELECT * FROM Drivers";
//        try {
//            // Prepare SQL statement with parameters
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            // Execute SQL statement and print result
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()){
//                Truck_Driver driver = convertReaderToObject(rs);
//                if (!Drivers.containsKey(driver.getID())){
//                    Drivers.put(driver.getID(), driver);
//                }
//            }
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
