package DataAccess.SuppliersModule;

import SuppliersModule.Business.OrderedProduct;
import SuppliersModule.Business.SupplierProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrderedProductDAO {

    private Connection conn;
    static OrderedProductDAO orderedProductDAO;
    private Map<Integer, OrderedProduct> IdentifyMapOrderedProduct;

    private SupplierProductDAO supplierProductDAO;

    private OrderedProductDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapOrderedProduct = new HashMap<>();
        supplierProductDAO = SupplierProductDAO.getInstance(this.conn);
    }

    public static OrderedProductDAO getInstance(Connection conn) {
        if (orderedProductDAO == null)
            orderedProductDAO = new OrderedProductDAO(conn);
        return orderedProductDAO;
    }

    public Map<String, OrderedProduct> getAll(int OrderFromSupplierId) {

        Map<String, OrderedProduct> orderedProductList = new HashMap<>();

        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderedProduct WHERE OrderFromSupplierId = ?");
            stmt.setInt(1, OrderFromSupplierId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SupplierProduct currSupplierProduct = supplierProductDAO.getSupplierProduct(rs.getString("SupplierNum"), rs.getString("SupplierNum"));
                OrderedProduct orderedProduct = new OrderedProduct(rs.getInt("Quantity"), currSupplierProduct);
                orderedProduct.setId(rs.getInt("Id"));

                orderedProductList.put(rs.getString("SupplierCatalog"), orderedProduct);

                IdentifyMapOrderedProduct.put(rs.getInt("Id"), orderedProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return orderedProductList;
    }

    public void WriteFromCacheToDB(int Id) {
        PreparedStatement stmt;

        for (Map.Entry<Integer, OrderedProduct> pair : IdentifyMapOrderedProduct.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into SupplierProduct VALUES (?,?,?,?,?,?,?)");
                stmt.setInt(1,  pair.getKey());
                stmt.setInt(2,  Id);
                stmt.setInt(3, pair.getValue().getQuantity());
                stmt.setFloat(4, pair.getValue().getDiscount());
                stmt.setFloat(5, pair.getValue().getFinalPrice());
                stmt.setString(6, pair.getValue().getMyProduct().getMySupplier().getSupplierNum());
                stmt.setString(7, pair.getValue().getMyProduct().getSupplierCatalog());
                stmt.executeQuery();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    public void deleteAllTable()
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM OrderedProduct");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

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
}
