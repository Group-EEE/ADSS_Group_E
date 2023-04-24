package DataAccess;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;

import java.sql.*;
import java.util.*;

public class OrderDiscountDAO {

    private Connection conn;
    private List<String> key;
    static OrderDiscountDAO orderDiscountDAO;

    private OrderDiscountDAO(Connection conn) {
        this.conn = conn;
        key = new ArrayList<>();
    }

    public static OrderDiscountDAO getInstance(Connection conn) {
        if (orderDiscountDAO == null)
            orderDiscountDAO = new OrderDiscountDAO(conn);
        return orderDiscountDAO;
    }

    public void saveOrderDiscount(String supplierNum, OrderDiscount orderDiscount) {
        //---------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO OrderDiscount (SupplierNum, ByPriceOrQuantity, Amount, Discount) VALUES (?,?,?,?)");
            stmt.setString(1, supplierNum);
            stmt.setString(2, orderDiscount.getByPriceOrQuantity());
            stmt.setInt(3, orderDiscount.getAmount());
            stmt.setFloat(4, orderDiscount.getDiscount());
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }


    public OrderDiscount getOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount) {
        OrderDiscount orderDiscount = null;

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderDiscount WHERE SupplierNum = ? AND ByPriceOrQuantity = ? AND MinimumAmount = ?");
            stmt.setString(1, supplierNum);
            stmt.setString(2, priceOrQuantity);
            stmt.setInt(3, minimumAmount);
            ResultSet rs = stmt.executeQuery();

            //-------------------------------------Create OrderDiscount---------------------------------
            if (rs.next()) {
                orderDiscount = new OrderDiscount(rs.getString("ByPriceOrQuantity"), rs.getInt("Amount"), rs.getFloat("Discount"), null);
            }
        }
        catch (SQLException e) {e.printStackTrace();}

        return orderDiscount;
    }

    public void deleteOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM OrderDiscount WHERE SupplierNum = ? AND ByPriceOrQuantity = ? AND MinimumAmount = ?");
            stmt.setString(1, supplierNum);
            stmt.setString(2, priceOrQuantity);
            stmt.setInt(3, minimumAmount);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public List<OrderDiscount> getAll(Agreement agreement, String supplierNum)
    {
        List<OrderDiscount> orderDiscountList = new ArrayList<>();
        //-----------------------------------------Create a query-----------------------------------------
        ResultSet rs;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderDiscount WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            while (rs.next())
                orderDiscountList.add(new OrderDiscount(rs.getString("ByPriceOrQuantity"), rs.getInt("Amount"), rs.getFloat("Discount"), agreement));
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
