package DataAccess;

import SuppliersModule.Business.*;

import java.sql.*;
import java.util.*;


public class SupplierDAO {

    private Connection conn;
    private AgreementDAO agreementDAO;
    private ContactDAO contactDAO;
    static SupplierDAO supplierDAO;
    private ManufacturerDAO manufacturerDAO;
    private SupplierProductDAO supplierProductDAO;
    private OrderFromSupplierDAO orderFromSupplierDAO;

    private PeriodicOrderDAO periodicOrderDAO;
    private Map<String, Supplier> IdentifyMapSupplier;

    private SupplierDAO(Connection conn) {
        this.conn = conn;
        agreementDAO = AgreementDAO.getInstance(this.conn);
        contactDAO = ContactDAO.getInstance(this.conn);
        manufacturerDAO = ManufacturerDAO.getInstance(this.conn);
        supplierProductDAO = SupplierProductDAO.getInstance(this.conn);
        orderFromSupplierDAO = OrderFromSupplierDAO.getInstance(this.conn);
        periodicOrderDAO = PeriodicOrderDAO.getInstance(this.conn);
        IdentifyMapSupplier = new HashMap<>();
    }

    public static SupplierDAO getInstance(Connection conn) {
        if (supplierDAO == null)
            supplierDAO = new SupplierDAO(conn);
        return supplierDAO;
    }

    public void WriteSuppliersToCache() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String supplierNum = rs.getString("SupplierNum");
                Agreement currAgreement = agreementDAO.getAgreement(supplierNum);

                Supplier supplier = new Supplier(rs.getString("Name"), supplierNum, rs.getString("BankAccount"), PaymentTerm.values()[rs.getInt("PaymentTerm")],
                        contactDAO.getAll(supplierNum), getCategories(supplierNum), currAgreement.isHasPermanentDays(), currAgreement.isSupplierBringProduct(),
                        currAgreement.getDeliveryDays(), currAgreement.getNumberOfDaysToSupply());

                supplier.setMyAgreement(currAgreement);

                for (Manufacturer manufacturer : manufacturerDAO.getAll(supplierNum))
                    supplier.addManufacturer(manufacturer);

                supplierProductDAO.creatAllSupplierProductsBySupplier(supplier);

                for (OrderFromSupplier orderFromSupplier : orderFromSupplierDAO.getAll(supplier))
                    orderFromSupplier.invite();

                periodicOrderDAO.createAllPeriodicOrder(supplier.getSupplierNum());

                IdentifyMapSupplier.put(supplierNum, supplier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getCategories(String supplierNum) {
        List<String> categories = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT Category FROM Supplier_Categories WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM Supplier");
            stmt.executeQuery();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, Supplier> pair : IdentifyMapSupplier.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into Supplier VALUES (?,?,?,?)");
                stmt.setString(1, pair.getKey());
                stmt.setString(2, pair.getValue().getName());
                stmt.setString(3, pair.getValue().getBankAccount());
                stmt.setInt(4, pair.getValue().getPayment().ordinal());
                stmt.executeQuery();
            }
            catch (SQLException e) {throw new RuntimeException(e);}

            contactDAO.deleteAllTable();
            contactDAO.WriteFromCacheToDB(pair.getValue().getSupplierNum());
        }
        agreementDAO.WriteFromCacheToDB();
        WriteAllCategories();
    }

    public void WriteAllCategories() {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM Supplier_Categories");
            stmt.executeQuery();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, Supplier> pair : IdentifyMapSupplier.entrySet()) {
            for (String category : pair.getValue().getCategories()) {
                try {
                    stmt = conn.prepareStatement("Insert into Supplier_Categories VALUES (?,?)");
                    stmt.setString(1, pair.getKey());
                    stmt.setString(2, category);
                    stmt.executeQuery();
                }
                catch (SQLException e) {throw new RuntimeException(e);}
            }
        }
    }
}
