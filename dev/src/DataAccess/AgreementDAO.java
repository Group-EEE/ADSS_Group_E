package DataAccess;

import SuppliersModule.Business.Agreement;

import java.sql.*;
import java.util.*;

public class AgreementDAO {

    private Connection conn;
    private OrderDiscountDAO orderDiscountDAO;
    static AgreementDAO agreementDAO;

    private Map<String, Agreement> IdentifyMapAgreement;

    private AgreementDAO(Connection conn) {
        this.conn = conn;
        orderDiscountDAO = OrderDiscountDAO.getInstance(this.conn);
        IdentifyMapAgreement = new HashMap<>();
    }

    public static AgreementDAO getInstance(Connection conn) {
        if (agreementDAO == null)
            agreementDAO = new AgreementDAO(conn);
        return agreementDAO;
    }

    public Agreement getAgreement(String supplierNum) {
        Agreement agreement = IdentifyMapAgreement.get(supplierNum);
        if(agreement != null)
            return agreement;

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Agreement WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-------------------------------------Create OrderDiscount---------------------------------
            if (rs.next()) {

                boolean[] days = new boolean[7];
                for(int i=0 ; i < 7 ; i++)
                    days[i] = rs.getBoolean(i+4);

                agreement = new Agreement(rs.getBoolean("HasPermanentDays"), rs.getBoolean("IsSupplierBringProduct"), days, rs.getInt("NumberOfDaysToSupply"), null);
                agreement.setDiscountOnOrder(orderDiscountDAO.getAll(agreement, supplierNum));
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        IdentifyMapAgreement.put(supplierNum, agreement);
        return agreement;

    }

    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM Agreement");
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, Agreement> pair : IdentifyMapAgreement.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into Agreement VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setString(1, pair.getKey());
                stmt.setBoolean(2, pair.getValue().supplierHasPermanentDays());
                stmt.setBoolean(3, pair.getValue().isSupplierBringProduct());
                stmt.setBoolean(4, pair.getValue().getDeliveryDays()[0]);
                stmt.setBoolean(5, pair.getValue().getDeliveryDays()[1]);
                stmt.setBoolean(6, pair.getValue().getDeliveryDays()[2]);
                stmt.setBoolean(7, pair.getValue().getDeliveryDays()[3]);
                stmt.setBoolean(8, pair.getValue().getDeliveryDays()[4]);
                stmt.setBoolean(9, pair.getValue().getDeliveryDays()[5]);
                stmt.setBoolean(10, pair.getValue().getDeliveryDays()[6]);
                stmt.setInt(11, pair.getValue().getNumberOfDaysToSupply());
                stmt.executeQuery();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }

        orderDiscountDAO.WriteFromCacheToDB();
    }
}
