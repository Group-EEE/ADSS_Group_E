package DataAccess.SuppliersModule;

import SuppliersModule.Business.GenericProduct;
import SuppliersModule.Business.Manufacturer;
import SuppliersModule.Business.Supplier;

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

    Map<List<String>, GenericProduct> IdentifyMapGenericProductByName;
    Map<Integer, GenericProduct> IdentifyMapGenericProductByBarcode;

    private GenericProductDAO(Connection conn) {
        this.conn = conn;
        manufacturerDAO = ManufacturerDAO.getInstance(this.conn);
        IdentifyMapGenericProductByName = new HashMap<>();
        IdentifyMapGenericProductByBarcode = new HashMap<>();
    }

    public static GenericProductDAO getInstance(Connection conn) {
        if (genericProductDAO == null)
            genericProductDAO = new GenericProductDAO(conn);
        return genericProductDAO;
    }

    public void WriteGenericProductsToCache() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM GenericProduct");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String productName = rs.getString("Name");
                String ManufacturerName =rs.getString("ManufacturerName");
                GenericProduct currGenericProduct = new GenericProduct(productName,  manufacturerDAO.getManufacturer(ManufacturerName), rs.getInt("Barcode"));

                IdentifyMapGenericProductByName.put(createKey(productName, ManufacturerName),currGenericProduct);
                IdentifyMapGenericProductByBarcode.put(rs.getInt("Barcode"), currGenericProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public List<String> createKey(String productName, String manufacturerName)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);
        return keyPair;
    }

    public void WriteFromCacheToDB()
    {
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM GenericProduct");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<Integer, GenericProduct> pair : IdentifyMapGenericProductByBarcode.entrySet()) {
            try{
                stmt = conn.prepareStatement("Insert into GenericProduct VALUES (?,?,?)");
                stmt.setString(1, pair.getValue().getName());
                stmt.setString(2, pair.getValue().getMyManufacturer().getName());
                stmt.setInt(3, pair.getKey());
                stmt.executeQuery();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    public boolean CheckIfGenericProductExist(String name , String manufacturerName)
    {
        return IdentifyMapGenericProductByName.containsKey(createKey(name, manufacturerName));
    }

    public void insert(GenericProduct genericProduct)
    {
        List<String> key = createKey(genericProduct.getName(), genericProduct.getMyManufacturer().getName());
        IdentifyMapGenericProductByName.put(key, genericProduct);
        IdentifyMapGenericProductByBarcode.put(genericProduct.getBarcode(), genericProduct);
    }

    public GenericProduct getGenericProductByName(String name , String manufacturerName)
    {
        return IdentifyMapGenericProductByName.get(createKey(name, manufacturerName));
    }

    public Map<List<String>, GenericProduct> getIdentifyMapGenericProductByName()
    {
        return IdentifyMapGenericProductByName;
    }

    public GenericProduct getGenericProductByBarcode(int barcode)
    {
        return IdentifyMapGenericProductByBarcode.get(barcode);
    }


}
