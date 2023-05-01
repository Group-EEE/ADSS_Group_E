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
}
