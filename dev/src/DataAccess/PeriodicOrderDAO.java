package DataAccess;

import SuppliersModule.Business.OrderFromSupplier;
import SuppliersModule.Business.PeriodicOrder;
import SuppliersModule.Business.Supplier;
import SuppliersModule.Business.SupplierProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicOrderDAO {

    private Connection conn;
    static PeriodicOrderDAO periodicOrderDAO;
    private Map<Integer, PeriodicOrder> IdentifyMapPeriodicOrder;

    private OrderFromSupplierDAO orderFromSupplierDAO;

    private PeriodicOrderDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapPeriodicOrder = new HashMap<>();
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
                currOrderFromSupplier = orderFromSupplierDAO.getOrderFromSupplier(rs.getString("OrderFromSupplierId"));
                PeriodicOrder currPeriodicOrder = new PeriodicOrder(currOrderFromSupplier, rs.getInt("DayForInvite"));

                IdentifyMapPeriodicOrder.put(rs.getInt("Id"), currPeriodicOrder);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }
}
