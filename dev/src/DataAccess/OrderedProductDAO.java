package DataAccess;

import SuppliersModule.Business.OrderFromSupplier;
import SuppliersModule.Business.OrderedProduct;
import SuppliersModule.Business.Supplier;
import SuppliersModule.Business.SupplierProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
}
