package SuppliersModule.DataAccess;

import SuppliersModule.Business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SupplierDAO {

    private Connection conn;
    private Map<String, Supplier> IdentifyMapSupplier;

    private AgreementDAO agreementDAO;
    private ContactDAO contactDAO;
    static SupplierDAO supplierDAO;

    private SupplierDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSupplier = new HashMap<>();
        agreementDAO = AgreementDAO.getInstance(this.conn);
        contactDAO = ContactDAO.getInstance(this.conn);
    }

    public static SupplierDAO getInstance(Connection conn) {
        if (supplierDAO == null)
            supplierDAO = new SupplierDAO(conn);
        return supplierDAO;
    }

    public void saveContact(Supplier supplier) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Supplier VALUES (?,?,?,?)");
            stmt.setString(1, supplier.getSupplierNum());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getBankAccount());
            stmt.setInt(3, supplier.getPayment().ordinal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //-----------------------------------------Insert into cache----------------------------------
        IdentifyMapSupplier.put(supplier.getSupplierNum(), supplier);
    }

    public Supplier getSupplier(String supplierNum) {
        Agreement currAgreement = null;

        //----------------------------------Check if found in cache----------------------------------
        Supplier supplier = IdentifyMapSupplier.get(supplierNum);
        if (supplier != null)
            return supplier;

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-------------------------------------Create OrderDiscount---------------------------------
            if (rs.next()) {
                currAgreement = agreementDAO.getAgreement(supplierNum);
                Map<String, Contact> currContactMap = contactDAO.getAll(supplierNum);
                supplier = new Supplier(rs.getString("Name"), rs.getString("SupplierNum"), rs.getString("BankAccount"), PaymentTerm.values()[rs.getInt("PaymentTerm")],
                        currContactMap, null, currAgreement.isHasPermanentDays(), currAgreement.isSupplierBringProduct(),
                        currAgreement.getDeliveryDays(), currAgreement.getNumberOfDaysToSupply());
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        //-----------------------------------------Insert into cache----------------------------------
        supplier.setMyAgreement(currAgreement);
        IdentifyMapSupplier.put(supplierNum, supplier);
        return supplier;
    }
}
