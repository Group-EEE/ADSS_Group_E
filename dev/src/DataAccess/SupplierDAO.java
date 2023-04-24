package DataAccess;

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

    public void WriteSuppliersToCache()
    {
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String supplierNum = rs.getString("SupplierNum");
                Agreement currAgreement = agreementDAO.getAgreement(supplierNum);

                Supplier supplier = new Supplier(rs.getString("Name"), supplierNum, rs.getString("BankAccount"), PaymentTerm.values()[rs.getInt("PaymentTerm")],
                        contactDAO.getAll(supplierNum), null, currAgreement.isHasPermanentDays(), currAgreement.isSupplierBringProduct(),
                        currAgreement.getDeliveryDays(), currAgreement.getNumberOfDaysToSupply());

                supplier.setMyAgreement(currAgreement);
                currAgreement.setMySupplier(supplier);

                IdentifyMapSupplier.put(supplierNum, supplier);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void saveInCacheSupplier(Supplier supplier)
    {
        IdentifyMapSupplier.put(supplier.getSupplierNum(), supplier);
        agreementDAO.saveInCacheSAgreement(supplier.getSupplierNum(), supplier.getMyAgreement());
    }

    public boolean checkIfSupplierExist(String supplierNum) {

        if (IdentifyMapSupplier.containsKey(supplierNum))
            return true;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
