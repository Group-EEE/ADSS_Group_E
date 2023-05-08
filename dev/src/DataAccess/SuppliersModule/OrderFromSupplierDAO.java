package DataAccess.SuppliersModule;

import SuppliersModule.Business.*;

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

        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("SELECT * FROM OrderFromSupplier WHERE SupplierNum = ?");
            stmt.setString(1, supplier.getSupplierNum());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderFromSupplier orderFromSupplier = new OrderFromSupplier(supplier);
                orderFromSupplier.setId(rs.getInt("Id"));
                orderFromSupplier.setQuantity(rs.getInt("Quantity"));
                orderFromSupplier.setPriceBeforeTotalDiscount(rs.getFloat("PriceBeforeTotalDiscount"));
                orderFromSupplier.setProductsInOrder(orderedProductDAO.getAll(rs.getInt("Id")));

                PreparedStatement stmt2 = conn.prepareStatement("SELECT Id FROM PeriodicOrder WHERE Id = ?");
                stmt2.setInt(1, rs.getInt("Id"));
                ResultSet rs2 = stmt2.executeQuery();
                if(!rs2.next())
                    orderFromSupplierList.add(orderFromSupplier);

                IdentifyMapOrderFromSupplier.put(rs.getInt("Id"), orderFromSupplier);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        try{
            stmt = conn.prepareStatement("SELECT OrderFromSupplierId FROM staticValue");
            ResultSet rs = stmt.executeQuery();
            OrderFromSupplier.setUnique(rs.getInt("OrderFromSupplierId"));

            stmt = conn.prepareStatement("SELECT OrderedProductId FROM staticValue");
            rs = stmt.executeQuery();
            OrderedProduct.setUnique(rs.getInt("OrderedProductId"));
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return orderFromSupplierList;
    }

    public OrderFromSupplier getOrderFromSupplier(int Id)
    {
        return IdentifyMapOrderFromSupplier.get(Id);
    }

    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        try
        {
            stmt = conn.prepareStatement("UPDATE staticValue SET OrderFromSupplierId = ?");
            stmt.setInt(1, OrderFromSupplier.getUnique());
            stmt.executeUpdate();

        }
        catch (SQLException e) {throw new RuntimeException(e);}

        try {
            stmt = conn.prepareStatement("DELETE FROM OrderFromSupplier");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<Integer, OrderFromSupplier> pair : IdentifyMapOrderFromSupplier.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into OrderFromSupplier VALUES (?,?,?,?)");
                stmt.setInt(1,  pair.getKey());
                stmt.setInt(2, pair.getValue().getQuantity());
                stmt.setFloat(3, pair.getValue().getPriceBeforeTotalDiscount());
                stmt.setString(4, pair.getValue().getMySupplier().getSupplierNum());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}

            orderedProductDAO.deleteAllTable();
            orderedProductDAO.WriteFromCacheToDB(pair.getValue().getId());
        }
    }

    public void deleteBySupplier(String supplierNum)
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT Id FROM OrderFromSupplier WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                IdentifyMapOrderFromSupplier.remove(rs.getInt("Id"));
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        orderedProductDAO.deleteBySupplier(supplierNum);
    }

    public void insert(OrderFromSupplier orderFromSupplier)
    {
        IdentifyMapOrderFromSupplier.put(orderFromSupplier.getId(), orderFromSupplier);
        for (Map.Entry<String, OrderedProduct> pair : orderFromSupplier.getProductsInOrder().entrySet()) {
            orderedProductDAO.insert(pair.getValue());
        }
    }
}
