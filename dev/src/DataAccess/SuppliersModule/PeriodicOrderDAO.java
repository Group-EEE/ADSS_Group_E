package DataAccess.SuppliersModule;

import SuppliersModule.Business.OrderFromSupplier;
import SuppliersModule.Business.PeriodicOrder;
import SuppliersModule.Business.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data access object class of PeriodicOrder.
 */
public class PeriodicOrderDAO {

    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    static PeriodicOrderDAO periodicOrderDAO;
    private Map<Integer, PeriodicOrder> IdentifyMapPeriodicOrder;
    private Map<List<Integer>, PeriodicOrder> IdentifyMapPeriodicOrderByDay;

    // ------------------------------------- References to another DAO's--------------------------------
    private OrderFromSupplierDAO orderFromSupplierDAO;

    // -----------------------------------------------------------------------------------------------------

    /**
     * Singleton constructor
     */
    private PeriodicOrderDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapPeriodicOrder = new HashMap<>();
        IdentifyMapPeriodicOrderByDay = new HashMap<>();
        orderFromSupplierDAO = OrderFromSupplierDAO.getInstance(this.conn);
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - PeriodicOrderDAO
     */
    public static PeriodicOrderDAO getInstance(Connection conn) {
        if (periodicOrderDAO == null)
            periodicOrderDAO = new PeriodicOrderDAO(conn);
        return periodicOrderDAO;
    }

    /**
     * Get all OrderFromSuppliers that belongs to the supplier
     * @param supplier - desired supplier.
     * @return List of all OrderFromSupplier.
     */
    public void GetAllBySupplierNum(Supplier supplier) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PeriodicOrder WHERE SupplierNum = ?");
            stmt.setString(1, supplier.getSupplierNum());
            ResultSet rs = stmt.executeQuery();

            OrderFromSupplier currOrderFromSupplier;
            while(rs.next())
            {
                currOrderFromSupplier = orderFromSupplierDAO.getOrderFromSupplierById(rs.getInt("OrderFromSupplierId"));
                PeriodicOrder currPeriodicOrder = new PeriodicOrder(currOrderFromSupplier, rs.getInt("DayForInvite"));
                supplier.addPeriodicOrder(currPeriodicOrder);

                IdentifyMapPeriodicOrder.put(rs.getInt("Id"), currPeriodicOrder);
                List<Integer> key = new ArrayList<>();
                key.add(rs.getInt("Id"));
                key.add(rs.getInt("DayForInvite"));
                IdentifyMapPeriodicOrderByDay.put(key, currPeriodicOrder);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Write all the categories from cache to DB
     */
    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM PeriodicOrder");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<Integer, PeriodicOrder> pair : IdentifyMapPeriodicOrder.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into PeriodicOrder VALUES (?,?,?,?)");
                stmt.setInt(1,  pair.getKey());
                stmt.setInt(2, pair.getValue().getDayForInvite());
                stmt.setInt(3, pair.getValue().getOrderFromSupplier().getId());
                stmt.setString(4, pair.getValue().getOrderFromSupplier().getMySupplier().getSupplierNum());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    /**
     * Delete All orderFromSuppliers from DB that belongs to the supplier.
     * @param supplierNum - supplierNum.
     */
    public void deleteBySupplier(String supplierNum)
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PeriodicOrder WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                IdentifyMapPeriodicOrder.remove(rs.getInt("Id"));
                List<Integer> key = new ArrayList<>();
                key.add(rs.getInt("Id"));
                key.add(rs.getInt("DayForInvite"));
                IdentifyMapPeriodicOrderByDay.remove(key);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Get all PeriodicOrders
     * @return - All PeriodicOrders
     */
    public Map<List<Integer>, PeriodicOrder> getAllPeriodicOrder()
    {
        return IdentifyMapPeriodicOrderByDay;
    }

    /**
     * Insert supplier to DB
     * @param periodicOrder - desired periodicOrder.
     */
    public void insert(PeriodicOrder periodicOrder)
    {
        IdentifyMapPeriodicOrder.put(periodicOrder.getId(), periodicOrder);
        List<Integer> key = new ArrayList<>();
        key.add(periodicOrder.getId());
        key.add(periodicOrder.getDayForInvite());
        IdentifyMapPeriodicOrderByDay.put(key, periodicOrder);
    }

    /**
     * Get supplier by supplierNum
     * @param id - number of the periodicOrder.
     * @return desired periodicOrder.
     */
    public PeriodicOrder getById(int id)
    {
        return IdentifyMapPeriodicOrder.get(id);
    }

    /**
     * Delete periodicOrder from DB
     * @param id - desired periodicOrder.
     */
    public void delete(int id)
    {
        PeriodicOrder periodicOrder = IdentifyMapPeriodicOrder.remove(id);
        List<Integer> key = new ArrayList<>();
        key.add(id);
        key.add(periodicOrder.getDayForInvite());
        IdentifyMapPeriodicOrderByDay.remove(key);
        orderFromSupplierDAO.delete(id);
    }
}
