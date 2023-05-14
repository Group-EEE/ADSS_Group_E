package DataAccess.SuppliersModule;

import SuppliersModule.Business.OrderFromSupplier;
import SuppliersModule.Business.OrderedProduct;
import SuppliersModule.Business.SupplierProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Data access object class of OrderedProduct.
 */
public class OrderedProductDAO {

    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    static OrderedProductDAO orderedProductDAO;
    private Map<Integer, OrderedProduct> IdentifyMapOrderedProduct;

    // ------------------------------------- References to another DAO's--------------------------------
    private SupplierProductDAO supplierProductDAO;

    // -----------------------------------------------------------------------------------------------------

    /**
     * Singleton constructor
     */
    private OrderedProductDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapOrderedProduct = new HashMap<>();
        supplierProductDAO = SupplierProductDAO.getInstance(this.conn);
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - OrderedProductDAO
     */
    public static OrderedProductDAO getInstance(Connection conn) {
        if (orderedProductDAO == null)
            orderedProductDAO = new OrderedProductDAO(conn);
        return orderedProductDAO;
    }

    /**
     * Get all OrderedProducts that belongs to the OrderFromSupplier
     * @param OrderFromSupplierId - desired OrderFromSupplier.
     * @return Map of all OrderedProduct.
     */
    public Map<String, OrderedProduct> getAllByOrderFromSupplierId(int OrderFromSupplierId) {

        PreparedStatement stmt;

        Map<String, OrderedProduct> orderedProductList = new HashMap<>();

        try{
            stmt = conn.prepareStatement("SELECT * FROM OrderedProduct WHERE OrderFromSupplierId = ?");
            stmt.setInt(1, OrderFromSupplierId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SupplierProduct currSupplierProduct = supplierProductDAO.getSupplierProduct(rs.getString("SupplierNum"), rs.getString("supplierCatalog"));
                OrderedProduct orderedProduct = new OrderedProduct(rs.getInt("Quantity"), currSupplierProduct);
                orderedProduct.setId(rs.getInt("Id"));

                orderedProductList.put(rs.getString("SupplierCatalog"), orderedProduct);

                IdentifyMapOrderedProduct.put(rs.getInt("Id"), orderedProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return orderedProductList;
    }

    /**
     * Write all the suppliers from cache to DB
     * @param order - desired orderFromSupplier
     */
    public void WriteFromCacheToDB(OrderFromSupplier order) {
        PreparedStatement stmt;

        try
        {
            stmt = conn.prepareStatement("UPDATE staticValue SET OrderedProductId = ?");
            stmt.setInt(1, OrderedProduct.getUnique());
            stmt.executeUpdate();

        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, OrderedProduct> pair : order.getProductsInOrder().entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into OrderedProduct VALUES (?,?,?,?,?,?,?)");
                stmt.setInt(1,  pair.getValue().getId());
                stmt.setInt(2,  order.getId());
                stmt.setInt(3, pair.getValue().getQuantity());
                stmt.setFloat(4, pair.getValue().getDiscount());
                stmt.setFloat(5, pair.getValue().getFinalPrice());
                stmt.setString(6, pair.getValue().getMyProduct().getMySupplier().getSupplierNum());
                stmt.setString(7, pair.getValue().getMyProduct().getSupplierCatalog());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    /**
     * Delete all the records in the table
     */
    public void deleteAllTable()
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM OrderedProduct");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Delete All orderedProducts from DB that belongs to the supplier.
     * @param supplierNum - supplierNum.
     */
    public void deleteBySupplier(String supplierNum)
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT Id FROM OrderedProduct WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                IdentifyMapOrderedProduct.remove(rs.getInt("Id"));
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Insert contact to DB
     * @param orderedProduct - desired orderedProduct.
     */
    public void insert(OrderedProduct orderedProduct)
    {
        IdentifyMapOrderedProduct.put(orderedProduct.getId(), orderedProduct);
    }

    /**
     * Delete All orderedProducts from DB that belongs to the orderFromSupplier.
     * @param id - OrderFromSupplier id.
     */
    public void deleteByOrderFromSupplierId(int id)
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT Id FROM OrderedProduct WHERE OrderFromSupplierId = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                IdentifyMapOrderedProduct.remove(rs.getInt("Id"));
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }
}
