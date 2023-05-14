package DataAccess.SuppliersModule;

import SuppliersModule.Business.*;

import java.sql.*;
import java.util.*;


/**
 * Data access object class of Supplier.
 */
public class SupplierDAO {

    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    static SupplierDAO supplierDAO;
    private Map<String, Supplier> IdentifyMapSupplier;  // Identify Map

    // ------------------------------------- References to another DAO's--------------------------------
    private AgreementDAO agreementDAO;
    private ContactDAO contactDAO;
    private ManufacturerDAO manufacturerDAO;
    private SupplierProductDAO supplierProductDAO;
    private OrderFromSupplierDAO orderFromSupplierDAO;
    private PeriodicOrderDAO periodicOrderDAO;

    // -----------------------------------------------------------------------------------------------------

    /**
     * Singleton constructor
     */
    private SupplierDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSupplier = new HashMap<>();

        agreementDAO = AgreementDAO.getInstance(this.conn);
        contactDAO = ContactDAO.getInstance(this.conn);
        manufacturerDAO = ManufacturerDAO.getInstance(this.conn);
        supplierProductDAO = SupplierProductDAO.getInstance(this.conn);
        orderFromSupplierDAO = OrderFromSupplierDAO.getInstance(this.conn);
        periodicOrderDAO = PeriodicOrderDAO.getInstance(this.conn);
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - SupplierDAO
     */
    public static SupplierDAO getInstance(Connection conn) {
        if (supplierDAO == null)
            supplierDAO = new SupplierDAO(conn);
        return supplierDAO;
    }

    /**
     * Read all the suppliers from DB to cache
     */
    public void ReadSuppliersToCache() {
        // -----------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier");
            ResultSet rs = stmt.executeQuery();

            // -----------------------------------For each Supplier------------------------------------
            while (rs.next()) {
                String supplierNum = rs.getString("SupplierNum");

                // ---------------------Restore the Supplier, Agreement, Contacts and Categories---------------------------------
                Agreement currAgreement = agreementDAO.getAgreementBySupplierNum(supplierNum);

                Supplier supplier = new Supplier(rs.getString("Name"), supplierNum, rs.getString("BankAccount"), PaymentTerm.values()[rs.getInt("PaymentTerm")],
                        contactDAO.getAllBySupplierNum(supplierNum), getCategories(supplierNum), currAgreement.supplierHasPermanentDays(), currAgreement.isSupplierBringProduct(),
                        currAgreement.getDeliveryDays(), currAgreement.getNumberOfDaysToSupply());

                supplier.setMyAgreement(currAgreement);

                // --------------------------------Restore the Manufacturer---------------------------------
                for (Manufacturer manufacturer : manufacturerDAO.getAllBySupplierNum(supplierNum))
                    supplier.addManufacturer(manufacturer);

                // --------------------------------Restore the SupplierProducts---------------------------------
                supplierProductDAO.GetAllSupplierProductsBySupplier(supplier);

                // --------------------------------Restore the OrderFromSuppliers---------------------------------
                for (OrderFromSupplier orderFromSupplier : orderFromSupplierDAO.getAllBySupplier(supplier))
                    orderFromSupplier.invite();

                // --------------------------------Restore the PeriodicOrders---------------------------------
                periodicOrderDAO.GetAllBySupplierNum(supplier);

                // ------------------------------------Save in cache-----------------------------------------
                IdentifyMapSupplier.put(supplierNum, supplier);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }


    /**
     * Get all categories that belongs to the supplier
     * @param supplierNum - number of the supplier.
     * @return List of all categories.
     */
    private List<String> getCategories(String supplierNum) {
        List<String> categories = new ArrayList<>();

        // -----------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT Category FROM Supplier_Categories WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            // -----------------------------------For each Category------------------------------------
            while (rs.next()) {
                categories.add(rs.getString("Category"));   // Add to list
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return categories;
    }

    /**
     * Write all the suppliers from cache to DB
     */
    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        // ---------------------------------Delete all records in the table-------------------------------------
        try {
            stmt = conn.prepareStatement("DELETE FROM Supplier");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        contactDAO.deleteAllTable();        // Delete all row in contact table

        // -----------------------------------For each Supplier------------------------------------
        for (Map.Entry<String, Supplier> pair : IdentifyMapSupplier.entrySet()) {

            try {
                //----------------------------Insert supplier to the table-------------------------------
                stmt = conn.prepareStatement("Insert into Supplier VALUES (?,?,?,?)");
                stmt.setString(1, pair.getKey());
                stmt.setString(2, pair.getValue().getName());
                stmt.setString(3, pair.getValue().getBankAccount());
                stmt.setInt(4, pair.getValue().getPayment().ordinal());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}

            //----------------------------Insert supplier contacts to the table-------------------------------
            contactDAO.WriteFromCacheToDB(pair.getKey(), pair.getValue().getMyContacts());
        }

        agreementDAO.WriteFromCacheToDB();                  //Insert All Agreements to the table

        WriteAllCategories();                               //Insert All Categories to the table

        supplierProductDAO.WriteFromCacheToDB();            //Insert All SupplierProducts to the table

        orderFromSupplierDAO.WriteFromCacheToDB();          //Insert All OrderFromSuppliers to the table

        periodicOrderDAO.WriteFromCacheToDB();              //Insert All PeriodicOrders to the table
    }

    /**
     * Write all the categories from cache to DB
     */
    private void WriteAllCategories() {
        PreparedStatement stmt;

        // ---------------------------------Delete all records in the table-------------------------------------
        try {
            stmt = conn.prepareStatement("DELETE FROM Supplier_Categories");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, Supplier> pair : IdentifyMapSupplier.entrySet()) {
            for (String category : pair.getValue().getCategories()) {
                try {
                    stmt = conn.prepareStatement("Insert into Supplier_Categories VALUES (?,?)");
                    stmt.setString(1, pair.getKey());
                    stmt.setString(2, category);
                    stmt.executeUpdate();
                }
                catch (SQLException e) {throw new RuntimeException(e);}
            }
        }
    }

    /**
     * Get supplier by supplierNum
     * @param supplierNum - number of the supplier.
     * @return desired supplier.
     */
    public Supplier getSupplierBySupplierNumber(String supplierNum)
    {
        return IdentifyMapSupplier.get(supplierNum);
    }

    /**
     * Insert supplier to DB
     * @param supplier - desired supplier.
     */
    public void insert(Supplier supplier)
    {
        IdentifyMapSupplier.put(supplier.getSupplierNum(), supplier);
        agreementDAO.insert(supplier.getMyAgreement());
    }

    /**
     * Get all Suppliers
     * @return - All Suppliers
     */
    public Map<String, Supplier> getAllSuppliers()
    {
        return IdentifyMapSupplier;
    }

    /**
     * Delete supplier from DB
     * @param supplierNum - desired supplier.
     */
    public void delete(String supplierNum)
    {

        agreementDAO.delete(supplierNum);
        contactDAO.deleteBySupplier(supplierNum);
        supplierProductDAO.deleteBySupplier(supplierNum);
        orderFromSupplierDAO.deleteBySupplier(supplierNum);
        periodicOrderDAO.deleteBySupplier(supplierNum);
        IdentifyMapSupplier.remove(supplierNum);

        try {
            PreparedStatement stmt = conn.prepareStatement("Delete FROM Supplier_Categories WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }
}
