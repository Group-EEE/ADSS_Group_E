package SuppliersModule.DataAccess;

import SuppliersModule.Business.Agreement;

import java.sql.*;
import java.util.*;

public class AgreementDAO {

    private Connection conn;
    private Map<String, Agreement> IdentifyMapAgreement;
    private OrderDiscountDAO orderDiscountDAO;
    static AgreementDAO agreementDAO;

    private AgreementDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapAgreement = new HashMap<>();
        orderDiscountDAO = OrderDiscountDAO.getInstance(this.conn);
    }

    public static AgreementDAO getInstance(Connection conn) {
        if (agreementDAO == null)
            agreementDAO = new AgreementDAO(conn);
        return agreementDAO;
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

        //-----------------------------------------Insert into cache----------------------------------
        IdentifyMapAgreement.put(agreement.getMySupplier().getSupplierNum(), agreement);
    }

    public Agreement getAgreement(String supplierNum) {
        Agreement agreement = null;

        //----------------------------------Check if found in cache----------------------------------
        agreement = IdentifyMapAgreement.get(supplierNum);
        if(agreement != null)
            return agreement;

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Agreement WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-------------------------------------Create OrderDiscount---------------------------------
            if (rs.next()) {
                //Supplier currSupplier = agreementDAO.getAgreement(supplierNum);
                boolean[] days = new boolean[7];
                for(int i=0 ; i < 7 ; i++)
                    days[i] = rs.getBoolean(i+4);
                agreement = new Agreement(rs.getBoolean("HasPermanentDays"), rs.getBoolean("IsSupplierBringProduct"), days, rs.getInt("NumberOfDaysToSupply"), null);
            }
        }
        catch (SQLException e) {e.printStackTrace();}

        //-----------------------------------------Insert into cache----------------------------------
        IdentifyMapAgreement.put(supplierNum, agreement);

        orderDiscountDAO.getAll(agreement, supplierNum);
        return agreement;

    }

    public void updateAgreement(String supplierNum, boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply) {

        //----------------------------------Check if found in cache----------------------------------
        if (IdentifyMapAgreement.get(supplierNum) != null)
            IdentifyMapAgreement.get(supplierNum).setDetails(hasPermanentDays, isSupplierBringProduct, deliveryDays, numberOdDaysToSupply);

        // Need to update on supplier.

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
        //----------------------------------Check if found in cache----------------------------------
        IdentifyMapAgreement.remove(supplierNum);

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Agreement WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }
}
