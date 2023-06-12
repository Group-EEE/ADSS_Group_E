package DataAccess.SuppliersModule;

import SuppliersModule.Business.GenericProduct;

import java.sql.*;
import java.util.*;

/**
 * Data access object class of GenericProduct.
 */
public class GenericProductDAO {
    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    static GenericProductDAO genericProductDAO;
    Map<List<String>, GenericProduct> IdentifyMapGenericProductByName;
    Map<Integer, GenericProduct> IdentifyMapGenericProductByBarcode;
    List<Integer> observerBarocdeList;

    // ------------------------------------- References to another DAO's--------------------------------
    private ManufacturerDAO manufacturerDAO;

    // -----------------------------------------------------------------------------------------------------

    /**
     * Singleton constructor
     */
    private GenericProductDAO(Connection conn) {
        this.conn = conn;
        manufacturerDAO = ManufacturerDAO.getInstance(this.conn);
        IdentifyMapGenericProductByName = new HashMap<>();
        IdentifyMapGenericProductByBarcode = new HashMap<>();
        observerBarocdeList = new ArrayList<>();
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - GenericProductDAO
     */
    public static GenericProductDAO getInstance(Connection conn) {
        if (genericProductDAO == null)
            genericProductDAO = new GenericProductDAO(conn);
        return genericProductDAO;
    }

    /**
     * Read all the GenericProducts from DB to cache
     */
    public void ReadGenericProductsToCache() {
        PreparedStatement stmt;

        // -----------------------------------Create a query-----------------------------------------
        try{
            stmt = conn.prepareStatement("SELECT * FROM ObserverBarcode");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                observerBarocdeList.add(rs.getInt("Barcode"));
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        // -----------------------------------Create a query-----------------------------------------
        try{
            stmt = conn.prepareStatement("SELECT Barcode FROM staticValue");
            ResultSet rs = stmt.executeQuery();
            GenericProduct.setUniqueBarcode(rs.getInt("Barcode"));
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        // -----------------------------------Create a query-----------------------------------------
        try {
            stmt = conn.prepareStatement("SELECT * FROM GenericProduct");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String productName = rs.getString("Name");
                String ManufacturerName =rs.getString("ManufacturerName");
                GenericProduct currGenericProduct = new GenericProduct(productName, manufacturerDAO.getManufacturerByManufacturerName(ManufacturerName), rs.getInt("Barcode"));

                IdentifyMapGenericProductByName.put(createKey(productName, ManufacturerName),currGenericProduct);
                IdentifyMapGenericProductByBarcode.put(rs.getInt("Barcode"), currGenericProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Write all the suppliers from cache to DB
     */
    public void WriteFromCacheToDB()
    {
        PreparedStatement stmt;

        // ---------------------------------Delete all records in the table-------------------------------------
        try{
            stmt = conn.prepareStatement("DELETE FROM ObserverBarcode");
            stmt.executeUpdate();

            // ---------------------------------Insert new record to ObserverBarcode-------------------------------------
            for(int currBarcode : observerBarocdeList)
            {
                stmt = conn.prepareStatement("Insert into ObserverBarcode VALUES (?)");
                stmt.setInt(1, currBarcode);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        // -----------------------------------Create a query-----------------------------------------
        try
        {
            stmt = conn.prepareStatement("UPDATE staticValue SET Barcode = ?");
            stmt.setInt(1, GenericProduct.getUniqueBarcode());
            stmt.executeUpdate();

        }
        catch (SQLException e) {throw new RuntimeException(e);}

        // ---------------------------------Delete all records in the table-------------------------------------
        try{
            stmt = conn.prepareStatement("DELETE FROM GenericProduct");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        // -----------------------------------For each GenericProduct------------------------------------
        for (Map.Entry<Integer, GenericProduct> pair : IdentifyMapGenericProductByBarcode.entrySet()) {
            try{
                stmt = conn.prepareStatement("Insert into GenericProduct VALUES (?,?,?)");
                stmt.setString(1, pair.getValue().getName());
                stmt.setString(2, pair.getValue().getMyManufacturer().getName());
                stmt.setInt(3, pair.getKey());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    /**
     * Insert genericProduct to DB
     * @param genericProduct - desired genericProduct.
     */
    public void insert(GenericProduct genericProduct)
    {
        List<String> key = createKey(genericProduct.getName(), genericProduct.getMyManufacturer().getName());
        IdentifyMapGenericProductByName.put(key, genericProduct);
        IdentifyMapGenericProductByBarcode.put(genericProduct.getBarcode(), genericProduct);
    }

    /**
     * Get GenericProduct by names
     * @param name - name of genericProduct
     * @param manufacturerName - name of manufacturerName
     * @return desired genericProduct
     */
    public GenericProduct getGenericProductByName(String name , String manufacturerName)
    {
        return IdentifyMapGenericProductByName.get(createKey(name, manufacturerName));
    }

    /**
     * Get GenericProduct by barcode
     * @param barcode - barcode of genericProduct
     * @return desired genericProduct
     */
    public GenericProduct getGenericProductByBarcode(int barcode)
    {
        return IdentifyMapGenericProductByBarcode.get(barcode);
    }

    /**
     * Add to ObserverBarcodeList
     * @param barcode - desired barcode
     */
    public void addToObserverBarcodeList(int barcode)
    {
        if(!observerBarocdeList.contains(barcode))
            observerBarocdeList.add(barcode);
    }

    /**
     * Get observerBarcodeList
     * @return observerBarcodeList
     */
    public List<Integer> getObserverBarcodeList()
    {
        return observerBarocdeList;
    }

    public Map<Integer, GenericProduct> getAllGenericProduct()
    {
        return IdentifyMapGenericProductByBarcode;
    }

    /**
     * @param productName - name of genericProduct
     * @param manufacturerName - name of manufacturerName
     * @return keyPair
     */
    private List<String> createKey(String productName, String manufacturerName)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);
        return keyPair;
    }
}
