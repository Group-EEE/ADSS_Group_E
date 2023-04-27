package DataAccess;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;

import java.sql.*;
import java.util.*;

public class OrderDiscountDAO {

    private Connection conn;
    private List<String> key;
    private Map<List<String>, OrderDiscount> IdentifyMapOrderDiscount;
    static OrderDiscountDAO orderDiscountDAO;

    private OrderDiscountDAO(Connection conn) {
        this.conn = conn;
        key = new ArrayList<>();
        IdentifyMapOrderDiscount = new HashMap<>();
    }

    public static OrderDiscountDAO getInstance(Connection conn) {
        if (orderDiscountDAO == null)
            orderDiscountDAO = new OrderDiscountDAO(conn);
        return orderDiscountDAO;
    }

    public List<OrderDiscount> getAll(Agreement agreement, String supplierNum)
    {
        List<OrderDiscount> orderDiscountList = new ArrayList<>();
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderDiscount WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            while (rs.next()) {
                OrderDiscount orderDiscount = new OrderDiscount(rs.getString("ByPriceOrQuantity"), rs.getInt("Amount"), rs.getFloat("Discount"), agreement);
                createKey(supplierNum, orderDiscount.getByPriceOrQuantity(), orderDiscount.getAmount());
                IdentifyMapOrderDiscount.put(key, orderDiscount);
                orderDiscountList.add(orderDiscount);

            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return orderDiscountList;
    }

    public void createKey(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        key.clear();
        key.add(supplierNum);
        key.add(priceOrQuantity);
        key.add(String.valueOf(minimumAmount));
    }

    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM OrderDiscount");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<List<String>, OrderDiscount> pair : IdentifyMapOrderDiscount.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into OrderDiscount VALUES (?,?,?,?)");
                stmt.setString(1, pair.getKey().get(0));
                stmt.setString(2, pair.getKey().get(1));
                stmt.setInt(3, Integer.parseInt(pair.getKey().get(2)));
                stmt.setFloat(4,pair.getValue().getDiscount());
                stmt.executeQuery();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }
}
