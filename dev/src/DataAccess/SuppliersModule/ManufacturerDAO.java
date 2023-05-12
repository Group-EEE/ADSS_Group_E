package DataAccess.SuppliersModule;


import SuppliersModule.Business.*;

import java.sql.*;
import java.util.*;

/**
 * Data access object class of Manufacturer.
 */
public class ManufacturerDAO {

    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    static ManufacturerDAO manufacturerDAO;
    private Map<String, Manufacturer> IdentifyMapManufacturer;

    // -----------------------------------------------------------------------------------------------------
    /**
     * Singleton constructor
     */
    private ManufacturerDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapManufacturer = new HashMap<>();
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - ManufacturerDAO
     */
    public static ManufacturerDAO getInstance(Connection conn) {
        if (manufacturerDAO == null)
            manufacturerDAO = new ManufacturerDAO(conn);
        return manufacturerDAO;
    }

    /**
     * Read all the manufacturers from DB to cache
     */
    public void ReadManufacturersToCache() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Manufacturer");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String manufacturerName = rs.getString("Name");
                IdentifyMapManufacturer.put(manufacturerName, new Manufacturer(manufacturerName));
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Get all manufacturers that belongs to the supplier
     * @param supplierNum - number of the supplier.
     * @return desired agreement.
     */
    public List<Manufacturer> getAllBySupplierNum(String supplierNum) {
        List<Manufacturer> manufacturerList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier_Manufacturer WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            Manufacturer currManufacturer;
            while (rs.next()) {
                currManufacturer = IdentifyMapManufacturer.get(rs.getString("ManufacturerName"));
                manufacturerList.add(currManufacturer);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return manufacturerList;
    }

    /**
     * Write all the suppliers from cache to DB
     */
    public void WriteFromCacheToDB()
    {
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM Manufacturer");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, Manufacturer> pair : IdentifyMapManufacturer.entrySet()) {
            try{
                stmt = conn.prepareStatement("Insert into Manufacturer VALUES (?)");
                stmt.setString(1, pair.getKey());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }

        writeAllToSupplierManufacturer();
    }

    /**
     * Write all the suppliers and Manufacturers to Supplier_Manufacturer table in DB
     */
    private void writeAllToSupplierManufacturer()
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM Supplier_Manufacturer");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, Manufacturer> pair : IdentifyMapManufacturer.entrySet()) {
            for (Map.Entry<String, Supplier> pair2 : pair.getValue().getMySuppliers().entrySet()) {
                try {
                    stmt = conn.prepareStatement("Insert into Supplier_Manufacturer VALUES (?,?)");
                    stmt.setString(1, pair2.getKey());
                    stmt.setString(2, pair.getKey());
                    stmt.executeUpdate();
                }
                catch (SQLException e) {throw new RuntimeException(e);}
            }
        }
    }

    /**
     * Get Manufacturer by manufacturerName
     * @param manufacturerName - name of the manufacturer.
     * @return desired manufacturer.
     */
    public Manufacturer getManufacturerByManufacturerName(String manufacturerName)
    {
        return IdentifyMapManufacturer.get(manufacturerName);
    }

    /**
     * Insert manufacturer to DB
     * @param manufacturer - desired manufacturer.
     */

    public void insert(Manufacturer manufacturer)
    {
        IdentifyMapManufacturer.put(manufacturer.getName(), manufacturer);
    }
}
