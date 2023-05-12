package DataAccess.SuppliersModule;

import SuppliersModule.Business.OrderFromSupplier;
import SuppliersModule.Business.PeriodicOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicOrderDAO {

    private Connection conn;
    static PeriodicOrderDAO periodicOrderDAO;
    private Map<Integer, PeriodicOrder> IdentifyMapPeriodicOrder;

    private Map<List<Integer>, PeriodicOrder> IdentifyMapPeriodicOrderByDay;
    private OrderFromSupplierDAO orderFromSupplierDAO;

    /**
     * Singleton constructor
     */
    private PeriodicOrderDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapPeriodicOrder = new HashMap<>();
        IdentifyMapPeriodicOrderByDay = new HashMap<>();
        orderFromSupplierDAO = OrderFromSupplierDAO.getInstance(this.conn);
    }

    public static PeriodicOrderDAO getInstance(Connection conn) {
        if (periodicOrderDAO == null)
            periodicOrderDAO = new PeriodicOrderDAO(conn);
        return periodicOrderDAO;
    }

    public void createAllPeriodicOrder(String supplierNum) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PeriodicOrder WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            OrderFromSupplier currOrderFromSupplier;
            while(rs.next())
            {
                currOrderFromSupplier = orderFromSupplierDAO.getOrderFromSupplier(rs.getInt("OrderFromSupplierId"));
                PeriodicOrder currPeriodicOrder = new PeriodicOrder(currOrderFromSupplier, rs.getInt("DayForInvite"));

                IdentifyMapPeriodicOrder.put(rs.getInt("Id"), currPeriodicOrder);
                List<Integer> key = new ArrayList<>();
                key.add(rs.getInt("Id"));
                key.add(rs.getInt("DayForInvite"));
                IdentifyMapPeriodicOrderByDay.put(key, currPeriodicOrder);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

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

    public Map<List<Integer>, PeriodicOrder> getIdentifyMapPeriodicOrderByDay()
    {
        return IdentifyMapPeriodicOrderByDay;
    }

    public void insert(PeriodicOrder periodicOrder)
    {
        IdentifyMapPeriodicOrder.put(periodicOrder.getId(), periodicOrder);
        List<Integer> key = new ArrayList<>();
        key.add(periodicOrder.getId());
        key.add(periodicOrder.getDayForInvite());
        IdentifyMapPeriodicOrderByDay.put(key, periodicOrder);
    }

    public PeriodicOrder getById(int id)
    {
        return IdentifyMapPeriodicOrder.get(id);
    }

    public void delete(int id)
    {
        PeriodicOrder periodicOrder = IdentifyMapPeriodicOrder.remove(id);
        List<Integer> key = new ArrayList<>();
        key.add(id);
        key.add(periodicOrder.getDayForInvite());
        IdentifyMapPeriodicOrderByDay.remove(key);
        orderFromSupplierDAO.deleteById(id);
    }
}
