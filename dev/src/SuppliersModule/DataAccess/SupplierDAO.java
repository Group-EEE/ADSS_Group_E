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
    private AgreementDAO agreementDAO;
    private ContactDAO contactDAO;
    static SupplierDAO supplierDAO;

    private Map<String, Supplier> IdentifyMapSupplier;

    private SupplierDAO(Connection conn) {
        this.conn = conn;
        agreementDAO = AgreementDAO.getInstance(this.conn);
        contactDAO = ContactDAO.getInstance(this.conn);
        IdentifyMapSupplier = new HashMap<>();
    }

    public static SupplierDAO getInstance(Connection conn) {
        if (supplierDAO == null)
            supplierDAO = new SupplierDAO(conn);
        return supplierDAO;
    }

    public void saveInCacheSupplier(Supplier supplier)
    {
        IdentifyMapSupplier.put(supplier.getSupplierNum(), supplier);
        agreementDAO.saveInCacheSAgreement(supplier.getSupplierNum(), supplier.getMyAgreement());
    }

    public boolean checkIfSupplierExist(String supplierNum){

        if(IdentifyMapSupplier.containsKey(supplierNum))
            return true;

        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public Supplier getSupplier(String supplierNum) {

        Supplier supplier = IdentifyMapSupplier.get(supplierNum);
        if(supplier != null)
            return supplier;

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-------------------------------------Create Supplier---------------------------------
            if (rs.next()) {
                Agreement currAgreement = agreementDAO.getAgreement(supplierNum);
                Map<String, Contact> currContactMap = contactDAO.getAll(supplierNum);

                supplier = new Supplier(rs.getString("Name"), rs.getString("SupplierNum"), rs.getString("BankAccount"), PaymentTerm.values()[rs.getInt("PaymentTerm")],
                        currContactMap, null, currAgreement.isHasPermanentDays(), currAgreement.isSupplierBringProduct(),
                        currAgreement.getDeliveryDays(), currAgreement.getNumberOfDaysToSupply());

                supplier.setMyAgreement(currAgreement);
                currAgreement.setMySupplier(supplier);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return supplier;
    }




    public void saveSupplier(Supplier supplier) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Supplier VALUES (?,?,?,?)");
            stmt.setString(1, supplier.getSupplierNum());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getBankAccount());
            stmt.setInt(3, supplier.getPayment().ordinal());
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}

        //-----------------------------------------Insert Agreement to database----------------------------------
        agreementDAO.saveAgreement(supplier.getMyAgreement());
        for (Map.Entry<String, Contact> entry : supplier.getMyContacts().entrySet())
            contactDAO.saveContact(entry.getValue(), supplier.getSupplierNum());
    }
}
