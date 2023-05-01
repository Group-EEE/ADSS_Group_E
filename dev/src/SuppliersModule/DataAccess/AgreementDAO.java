package SuppliersModule.DataAccess;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;
import SuppliersModule.Business.Supplier;

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

    public void saveInCacheSAgreement(String SupplierNum, Agreement agreement)
    {
        IdentifyMapAgreement.put(SupplierNum, agreement);
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
        return agreement;

    }






    public void saveAgreement(Agreement agreement) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Agreement VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, agreement.getMySupplier().getSupplierNum());
            stmt.setBoolean(2, agreement.isHasPermanentDays());
            stmt.setBoolean(3, agreement.isSupplierBringProduct());
            for(int i = 4 ; i < agreement.getDeliveryDays().length + 4 ; i++)
                stmt.setBoolean(i, agreement.getDeliveryDays()[i-4]);
            stmt.setInt(11, agreement.getNumberOfDaysToSupply());
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}

    }









    public void updateAgreement(String supplierNum, boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Agreement SET HasPermanentDays = ? , IsSupplierBringProduct = ? , Sunday = ? , Monday = ? , Tuesday = ? , Wednesday = ? , Thursday = ? , Friday = ? , Saturday = ? , NumberOfDaysToSupply = ? WHERE supplierNum = ?");
            stmt.setBoolean(1, hasPermanentDays);
            stmt.setBoolean(2, isSupplierBringProduct);
            for(int i = 3 ; i < deliveryDays.length + 3 ; i++)
                stmt.setBoolean(i, deliveryDays[i-3]);
            stmt.setInt(10, numberOdDaysToSupply);
            stmt.setString(11, supplierNum);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public void deleteAgreement(String supplierNum) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Agreement WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }
}
