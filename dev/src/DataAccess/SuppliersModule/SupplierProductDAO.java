package DataAccess.SuppliersModule;

import SuppliersModule.Business.*;

import java.sql.*;
import java.util.*;

/**
 * Data access object class of SupplierProduct.
 */
public class SupplierProductDAO {

    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    static SupplierProductDAO supplierProductDAO;
    private Map<List<String>, SupplierProduct> IdentifyMapSupplierProduct;

    // ------------------------------------- References to another DAO's--------------------------------
    private SupplierProductDiscountDAO supplierProductDiscountDAO;
    private GenericProductDAO genericProductDAO;

    // -----------------------------------------------------------------------------------------------------

    /**
     * Singleton constructor
     */
    private SupplierProductDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSupplierProduct = new HashMap<>();
        genericProductDAO = GenericProductDAO.getInstance(this.conn);
        supplierProductDiscountDAO = SupplierProductDiscountDAO.getInstance(this.conn);
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - SupplierProductDAO
     */
    public static SupplierProductDAO getInstance(Connection conn) {
        if (supplierProductDAO == null)
            supplierProductDAO = new SupplierProductDAO(conn);
        return supplierProductDAO;
    }

    /**
     * Get all SupplierProducts that belongs to the supplier
     * @param supplier - desired supplier.
     * @return List of all SupplierProducts.
     */
    public void GetAllSupplierProductsBySupplier(Supplier supplier)
    {
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE SupplierNum = ?");
            stmt.setString(1, supplier.getSupplierNum());
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String productName = rs.getString("GenericProductName");
                String ManufacturerName =rs.getString("ManufacturerName");
                GenericProduct genericProduct = genericProductDAO.getGenericProductByName(productName, ManufacturerName);

                SupplierProduct currSupplierProduct = new SupplierProduct(rs.getFloat("Price"), rs.getString("SupplierCatalog"),
                        rs.getInt("Amount"), supplier, genericProduct, supplier.getMyAgreement());

                currSupplierProduct.setDiscountProducts(supplierProductDiscountDAO.getAll(supplier.getSupplierNum(),rs.getString("SupplierCatalog")));

                IdentifyMapSupplierProduct.put(createKey(supplier.getSupplierNum(), rs.getString("SupplierCatalog")), currSupplierProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Create key
     * @param supplierNum - number of supplier
     * @param supplierCatalog - number of supplierProduct
     * @return keyPair
     */
    public List<String> createKey(String supplierNum, String supplierCatalog)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(supplierNum);
        keyPair.add(supplierCatalog);
        return keyPair;
    }

    /**
     * Get SupplierProduct by supplierNum and supplierCatalog
     * @param supplierNum - number of supplier
     * @param supplierCatalog - number of supplierProduct
     */
    public SupplierProduct getSupplierProduct(String supplierNum, String supplierCatalog)
    {
        return IdentifyMapSupplierProduct.get(createKey(supplierNum, supplierCatalog));
    }

    /**
     * Write all the supplierProduct from cache to DB
     */
    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM SupplierProduct");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<List<String>, SupplierProduct> pair : IdentifyMapSupplierProduct.entrySet())
        {
            try
            {
                stmt = conn.prepareStatement("Insert into SupplierProduct VALUES (?,?,?,?,?,?)");
                stmt.setString(1, pair.getValue().getMyProduct().getName());
                stmt.setString(2, pair.getValue().getMyProduct().getMyManufacturer().getName());
                stmt.setString(3, pair.getKey().get(0));
                stmt.setFloat(4, pair.getValue().getPrice());
                stmt.setString(5, pair.getKey().get(1));
                stmt.setInt(6, pair.getValue().getAmount());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
        supplierProductDiscountDAO.WriteFromCacheToDB();
    }

    /**
     * Insert supplierProduct to DB
     * @param supplierProduct - desired supplierProduct.
     */
    public void insert(SupplierProduct supplierProduct)
    {
        List<String> key = createKey(supplierProduct.getMySupplier().getSupplierNum(),supplierProduct.getSupplierCatalog());
        IdentifyMapSupplierProduct.put(key, supplierProduct);
    }

    /**
     * Delete supplierProduct from DB
     * @param supplierProduct - desired supplierProduct.
     */
    public void delete(SupplierProduct supplierProduct)
    {
        List<String> key = createKey(supplierProduct.getMySupplier().getSupplierNum(),supplierProduct.getSupplierCatalog());
        IdentifyMapSupplierProduct.remove(key);
    }

    /**
     * Delete All supplierProducts from DB that belongs to the supplier.
     * @param supplierNum - supplierNum.
     */
    public void deleteBySupplier(String supplierNum)
    {
        Map<List<String>, SupplierProduct> copyMap = new HashMap<>(IdentifyMapSupplierProduct);
        for (Map.Entry<List<String>, SupplierProduct> pair : copyMap.entrySet()) {
            if (pair.getKey().get(0).equals(supplierNum))
                IdentifyMapSupplierProduct.remove(pair.getKey());
        }

        supplierProductDiscountDAO.deleteBySupplier(supplierNum);
    }
}
