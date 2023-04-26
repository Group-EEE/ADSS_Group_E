package DataAccess;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;

import java.sql.*;
import java.util.*;

public class OrderDiscountDAO {

    private Connection conn;
    private List<String> key;
    private Map<List<String>, OrderDiscount> IdentifyMapAgreement;
    static OrderDiscountDAO orderDiscountDAO;

    private OrderDiscountDAO(Connection conn) {
        this.conn = conn;
        key = new ArrayList<>();
        IdentifyMapAgreement = new HashMap<>();
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
                IdentifyMapAgreement.put(key, orderDiscount);
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
}
