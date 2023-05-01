package DataAccess;

import SuppliersModule.Business.Manufacturer;
import SuppliersModule.Business.OrderFromSupplier;
import SuppliersModule.Business.OrderedProduct;
import SuppliersModule.Business.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFromSupplierDAO {
    private Connection conn;
    static OrderFromSupplierDAO orderFromSupplierDAO;
    private Map<Integer, OrderFromSupplier> IdentifyMapOrderFromSupplier;

    private OrderedProductDAO orderedProductDAO;

    private OrderFromSupplierDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapOrderFromSupplier = new HashMap<>();
        orderedProductDAO = OrderedProductDAO.getInstance(this.conn);
    }

    public static OrderFromSupplierDAO getInstance(Connection conn) {
        if (orderFromSupplierDAO == null)
            orderFromSupplierDAO = new OrderFromSupplierDAO(conn);
        return orderFromSupplierDAO;
    }

    public List<OrderFromSupplier> getAll(Supplier supplier) {
        List<OrderFromSupplier> orderFromSupplierList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderFromSupplier WHERE SupplierNum = ?");
            stmt.setString(1, supplier.getSupplierNum());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderFromSupplier orderFromSupplier = new OrderFromSupplier(supplier);
                orderFromSupplier.setId(rs.getInt("Id"));
                orderFromSupplier.setQuantity(rs.getInt("Quantity"));
                orderFromSupplier.setPriceBeforeTotalDiscount(rs.getFloat("PriceBeforeTotalDiscount"));

                orderFromSupplier.setProductsInOrder(orderedProductDAO.getAll(rs.getInt("Id")));

                IdentifyMapOrderFromSupplier.put(rs.getInt("Id"), orderFromSupplier);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return orderFromSupplierList;
    }

    public OrderFromSupplier getOrderFromSupplier(String Id)
    {
        return IdentifyMapOrderFromSupplier.get(Id);
    }
}
