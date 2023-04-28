package DataAccess.SuppliersModule;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;
import SuppliersModule.Business.Supplier;
import SuppliersModule.Business.SupplierProduct;

import java.sql.*;
import java.util.*;

public class OrderDiscountDAO {

    private Connection conn;
    private Map<List<String>, OrderDiscount> IdentifyMapOrderDiscount;
    static OrderDiscountDAO orderDiscountDAO;

    private OrderDiscountDAO(Connection conn) {
        this.conn = conn;
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
            List<String> key;
            while (rs.next()) {
                OrderDiscount orderDiscount = new OrderDiscount(rs.getString("ByPriceOrQuantity"), rs.getInt("Amount"), rs.getFloat("Discount"), agreement);
                key = createKey(supplierNum, orderDiscount.getByPriceOrQuantity(), orderDiscount.getAmount());
                IdentifyMapOrderDiscount.put(key, orderDiscount);
                orderDiscountList.add(orderDiscount);

            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return orderDiscountList;
    }

    public List<String> createKey(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(supplierNum);
        keyPair.add(priceOrQuantity);
        keyPair.add(String.valueOf(minimumAmount));
        return keyPair;
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

    public boolean CheckIfOrderDiscountExist(String supplierNum, String priceOrQuantity, int amount)
    {
        List<String> key = createKey(supplierNum, priceOrQuantity, amount);
        return IdentifyMapOrderDiscount.containsKey(key);
    }

    public void insert(String supplierNum, OrderDiscount orderDiscount)
    {
        List<String> key = createKey(supplierNum, orderDiscount.getByPriceOrQuantity(), orderDiscount.getAmount());
        IdentifyMapOrderDiscount.put(key, orderDiscount);
    }

    public void deleteByAgreement(String supplierNum)
    {
        Map<List<String>, OrderDiscount> copyMap = new HashMap<>(IdentifyMapOrderDiscount);
        for (Map.Entry<List<String>, OrderDiscount> pair : copyMap.entrySet()) {
            if(pair.getKey().get(0).equals(supplierNum))
                IdentifyMapOrderDiscount.remove(pair.getKey());
        }
    }

    public void delete(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        List<String> key = createKey(supplierNum, priceOrQuantity, minimumAmount);
        IdentifyMapOrderDiscount.remove(key);
    }
}
