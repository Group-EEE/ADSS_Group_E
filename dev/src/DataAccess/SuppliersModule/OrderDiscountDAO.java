package DataAccess.SuppliersModule;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;

import java.sql.*;
import java.util.*;

/**
 * Data access object class of OrderDiscount.
 */
public class OrderDiscountDAO {

    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    private Map<List<String>, OrderDiscount> IdentifyMapOrderDiscount;
    static OrderDiscountDAO orderDiscountDAO;

    // -----------------------------------------------------------------------------------------------------

    /**
     * Singleton constructor
     */
    private OrderDiscountDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapOrderDiscount = new HashMap<>();
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - OrderDiscountDAO
     */
    public static OrderDiscountDAO getInstance(Connection conn) {
        if (orderDiscountDAO == null)
            orderDiscountDAO = new OrderDiscountDAO(conn);
        return orderDiscountDAO;
    }

    /**
     * Get all orderDiscount that belongs to the agreement
     * @param agreement - desired agreement.
     * @param supplierNum - number of the supplier.
     * @return List of all OrderDiscount.
     */
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

    /**
     * Write all the orderDiscount from cache to DB
     */
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
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    /**
     * Get orderDiscount by key
     * @param supplierNum - number of supplier
     * @param priceOrQuantity - price or Quantity
     * @param amount - quantity
     * @return desired orderDiscount
     */
    public OrderDiscount getOrderDiscountByKey(String supplierNum, String priceOrQuantity, int amount)
    {
        List<String> key = createKey(supplierNum, priceOrQuantity, amount);
        return IdentifyMapOrderDiscount.get(key);
    }

    /**
     * Insert orderDiscount to DB
     * @param supplierNum - number of supplier
     * @param orderDiscount - desired orderDiscount
     */
    public void insert(String supplierNum, OrderDiscount orderDiscount)
    {
        List<String> key = createKey(supplierNum, orderDiscount.getByPriceOrQuantity(), orderDiscount.getAmount());
        IdentifyMapOrderDiscount.put(key, orderDiscount);
    }

    /**
     * Delete orderDiscount from DB
     * @param supplierNum - desired agreement that belongs to supplier.
     */
    public void deleteByAgreement(String supplierNum)
    {
        Map<List<String>, OrderDiscount> copyMap = new HashMap<>(IdentifyMapOrderDiscount);
        for (Map.Entry<List<String>, OrderDiscount> pair : copyMap.entrySet()) {
            if(pair.getKey().get(0).equals(supplierNum))
                IdentifyMapOrderDiscount.remove(pair.getKey());
        }
    }

    /**
     * Delete orderDiscount from DB
     * @param supplierNum - desired agreement that belongs to supplier.
     * @param priceOrQuantity - price or Quantity
     * @param minimumAmount - quantity
     */
    public void delete(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        List<String> key = createKey(supplierNum, priceOrQuantity, minimumAmount);
        IdentifyMapOrderDiscount.remove(key);
    }

    /**
     * @param supplierNum - desired agreement that belongs to supplier.
     * @param priceOrQuantity - price or Quantity .
     * @param minimumAmount - quantity.
     * @return keyPair
     */
    private List<String> createKey(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(supplierNum);
        keyPair.add(priceOrQuantity);
        keyPair.add(String.valueOf(minimumAmount));
        return keyPair;
    }
}
