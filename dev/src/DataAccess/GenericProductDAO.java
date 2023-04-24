package DataAccess;

import SuppliersModule.Business.GenericProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericProductDAO {

    private Connection conn;
    static GenericProductDAO genericProductDAO;
    private ManufacturerDAO manufacturerDAO;

    Map<List<String>, GenericProduct> IdentifyMapGenericProduct;

    private GenericProductDAO(Connection conn) {
        this.conn = conn;
        manufacturerDAO = ManufacturerDAO.getInstance(this.conn);
        IdentifyMapGenericProduct = new HashMap<>();
    }

    public static GenericProductDAO getInstance(Connection conn) {
        if (genericProductDAO == null)
            genericProductDAO = new GenericProductDAO(conn);
        return genericProductDAO;
    }

    public void saveInCacheGenericProduct(GenericProduct genericProduct) {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(genericProduct.getName());
        keyPair.add(genericProduct.getMyManufacturer().getName());
        IdentifyMapGenericProduct.put(keyPair, genericProduct);
    }

    public boolean checkIfGenericProductExist(String manufacturerName, String ProductName){

        List<String> keyPair = new ArrayList<>();
        keyPair.add(ProductName); keyPair.add(manufacturerName);
        if(IdentifyMapGenericProduct.containsKey(keyPair))
            return true;

        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM GenericProduct WHERE Name = ? And ManufcturerName = ?");
            stmt.setString(1, ProductName);
            stmt.setString(2, manufacturerName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }








    public void saveGenericProduct(String manufacturerName, String ProductName, int Barcode) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO GenericProduct VALUES (?,?,?)");
            stmt.setString(1, ProductName);
            stmt.setString(2, manufacturerName);
            stmt.setInt(3, Barcode);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }


    public GenericProduct getGenericProduct(String manufacturerName, String ProductName) {

        List<String> keyPair = new ArrayList<>();
        keyPair.add(ProductName);
        keyPair.add(manufacturerName);

        GenericProduct genericProduct = IdentifyMapGenericProduct.get(keyPair);
        if(genericProduct != null)
            return genericProduct;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM GenericProduct WHERE Name = ? And ManufcturerName = ?");
            stmt.setString(1, ProductName);
            stmt.setString(2, manufacturerName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                genericProduct = new GenericProduct(ProductName, manufacturerDAO.getManufacturer(manufacturerName), rs.getInt("Barcode"));
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return genericProduct;
    }
}
