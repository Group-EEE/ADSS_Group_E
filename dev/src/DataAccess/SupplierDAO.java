package DataAccess;

import SuppliersModule.Business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDAO {

    private Connection conn;
    private AgreementDAO agreementDAO;
    private ContactDAO contactDAO;
    static SupplierDAO supplierDAO;
    private ManufacturerDAO manufacturerDAO;

    private Map<String, Supplier> IdentifyMapSupplier;

    private SupplierDAO(Connection conn) {
        this.conn = conn;
        agreementDAO = AgreementDAO.getInstance(this.conn);
        contactDAO = ContactDAO.getInstance(this.conn);
        manufacturerDAO = ManufacturerDAO.getInstance(this.conn);
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String supplierNum = rs.getString("SupplierNum");
                Agreement currAgreement = agreementDAO.getAgreement(supplierNum);

                Supplier supplier = new Supplier(rs.getString("Name"), supplierNum, rs.getString("BankAccount"), PaymentTerm.values()[rs.getInt("PaymentTerm")],
                        contactDAO.getAll(supplierNum), getCategories(supplierNum), currAgreement.isHasPermanentDays(), currAgreement.isSupplierBringProduct(),
                        currAgreement.getDeliveryDays(), currAgreement.getNumberOfDaysToSupply());

                supplier.setMyAgreement(currAgreement);
                IdentifyMapSupplier.put(supplierNum, supplier);

                for(Manufacturer manufacturer : manufacturerDAO.getAll(supplierNum))
                    supplier.addManufacturer(manufacturer);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public List<String> getCategories(String supplierNum)
    {
        List<String> categories = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT Category FROM Supplier_Categories WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                categories.add(rs.getString("Category"));
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
        return categories;
    }


    //**********************************************************************************

}
