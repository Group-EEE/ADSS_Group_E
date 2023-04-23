package SuppliersModule.DataAccess;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;

import java.sql.*;
import java.util.*;

public class OrderDiscountDAO {

    private Connection conn;
    private Map<List<String>, OrderDiscount> IdentifyMapOrderDiscount;
    private List<String> key;
    private AgreementDAO agreementDAO;
    static OrderDiscountDAO orderDiscountDAO;

    private OrderDiscountDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapOrderDiscount = new HashMap<>();
        key = new ArrayList<>();
        agreementDAO = AgreementDAO.getInstance(this.conn);
    }

    public static OrderDiscountDAO getInstance(Connection conn) {
        if (orderDiscountDAO == null)
            orderDiscountDAO = new OrderDiscountDAO(conn);
        return orderDiscountDAO;
    }

    public void saveOrderDiscount(String supplierNum, OrderDiscount orderDiscount) {
        //---------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Contact (SupplierNum, BypPiceOrQuantity, Amount, DiscountPercentage) VALUES (?,?,?,?)");
            stmt.setString(1, supplierNum);
            stmt.setString(2, orderDiscount.getByPriceOrQuantity());
            stmt.setInt(3, orderDiscount.getAmount());
            stmt.setFloat(4, orderDiscount.getDiscount());
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}

        //-----------------------------------------Insert into cache----------------------------------
        createKey(supplierNum,orderDiscount.getByPriceOrQuantity(),orderDiscount.getAmount());
        IdentifyMapOrderDiscount.put(key, orderDiscount);

        //------------------------------Update the add in Agreement cache-------------------------
        agreementDAO.getAgreement(supplierNum).addOrderDiscount(orderDiscount.getByPriceOrQuantity(), orderDiscount.getAmount(), orderDiscount.getDiscount());
    }


    public OrderDiscount getOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount) {
        OrderDiscount orderDiscount = null;

        //----------------------------------Check if found in cache----------------------------------
        createKey(supplierNum,priceOrQuantity,minimumAmount);
        orderDiscount = IdentifyMapOrderDiscount.get(key);
        if(orderDiscount != null)
            return orderDiscount;

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderDiscount WHERE SupplierNum = ? AND ByPriceOrQuantity = ? AND MinimumAmount = ?");
            stmt.setString(1, supplierNum);
            stmt.setString(2, priceOrQuantity);
            stmt.setInt(3, minimumAmount);
            ResultSet rs = stmt.executeQuery();

            //-------------------------------------Create OrderDiscount---------------------------------
            if (rs.next()) {
                Agreement currAgreement = agreementDAO.getAgreement(supplierNum);
                orderDiscount = new OrderDiscount(rs.getString("ByPriceOrQuantity"), rs.getInt("MinimumAmount"), rs.getFloat("Discount"), currAgreement);
            }
        }
        catch (SQLException e) {e.printStackTrace();}

        //-----------------------------------------Insert into cache----------------------------------
        IdentifyMapOrderDiscount.put(key, orderDiscount);
        return orderDiscount;
    }

    public void deleteOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount) {
        //----------------------------------Check if found in cache----------------------------------
        createKey(supplierNum,priceOrQuantity,minimumAmount);
        IdentifyMapOrderDiscount.remove(key);

        //------------------------------Update the deletion in Agreement cache-------------------------
        agreementDAO.getAgreement(supplierNum).deleteOrderDiscount(priceOrQuantity, minimumAmount);

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
                orderDiscountList.add(new OrderDiscount(rs.getString("ByPriceOrQuantity"), rs.getInt("MinimumAmount"), rs.getFloat("Discount"), agreement));
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
