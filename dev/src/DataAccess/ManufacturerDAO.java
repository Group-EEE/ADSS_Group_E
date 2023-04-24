package DataAccess;


import SuppliersModule.Business.Manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ManufacturerDAO {

    private Connection conn;
    static ManufacturerDAO manufacturerDAO;
    private Map<String, Manufacturer> IdentifyMapManufacturer;

    private ManufacturerDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapManufacturer = new HashMap<>();
    }

    public static ManufacturerDAO getInstance(Connection conn) {
        if (manufacturerDAO == null)
            manufacturerDAO = new ManufacturerDAO(conn);
        return manufacturerDAO;
    }

    public void saveInCacheManufacturer(Manufacturer manufacturer) {
        IdentifyMapManufacturer.put(manufacturer.getName(), manufacturer);
    }

    public boolean checkIfManufacturerExist(String manufacturerName){
        if(IdentifyMapManufacturer.containsKey(manufacturerName))
            return true;

        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Manufacturer WHERE Name = ?");
            stmt.setString(1, manufacturerName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }







    public void saveManufacturer(String manufacturerName, String SupplierNum) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Supplier VALUES (?)");
            stmt.setString(1, manufacturerName);
            stmt.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Supplier_Manufacturer VALUES (?,?)");
            stmt2.setString(1, SupplierNum);
            stmt2.setString(2, manufacturerName);
            stmt2.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }


    public Manufacturer getManufacturer(String manufacturerName){

        Manufacturer manufacturer = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Manufacturer WHERE Name = ?");
            stmt.setString(1, manufacturerName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                manufacturer = new Manufacturer(manufacturerName);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return manufacturer;
    }

}
