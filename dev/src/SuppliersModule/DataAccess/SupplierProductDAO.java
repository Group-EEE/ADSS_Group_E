package SuppliersModule.DataAccess;

import SuppliersModule.Business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SupplierProductDAO {

    private Connection conn;
    static SupplierProductDAO supplierProductDAO;
    private Map<String, SupplierProduct> IdentifyMapSupplierProductDAO;

    private SupplierProductDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSupplierProductDAO = new HashMap<>();
    }

    public static SupplierProductDAO getInstance(Connection conn) {
        if (supplierProductDAO == null)
            supplierProductDAO = new SupplierProductDAO(conn);
        return supplierProductDAO;
    }

    public void saveInCacheSupplierProduct(SupplierProduct supplierProduct)
    {
        IdentifyMapSupplierProductDAO.put(supplierProduct.getSupplierCatalog(), supplierProduct);
    }

    public Map<String, SupplierProduct> getALL(String supplierNum)
    {
        Map<String, SupplierProduct> supplierProductMap = new HashMap<>();

        return null;
    }















    public void saveSupplierProduct(float price, String supplierCatalog, int amount, Supplier supplier, GenericProduct genericProduct, Agreement agreement) {

        new SupplierProduct(price, supplierCatalog, amount, supplier, genericProduct, supplier.getMyAgreement());

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SupplierProduct VALUES (?,?,?,?,?,?)");
            stmt.setString(1, genericProduct.getName());
            stmt.setString(2, genericProduct.getMyManufacturer().getName());
            stmt.setString(3, supplier.getSupplierNum());
            stmt.setFloat(4, price);
            stmt.setString(5, supplierCatalog);
            stmt.setInt(6, amount);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public Map<String, SupplierProduct> getAll(Supplier supplier)
    {
        Map<String, SupplierProduct> contactSupplierProduct = new HashMap<>();
        //-----------------------------------------Create a query-----------------------------------------
        ResultSet rs;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE SupplierNum = ?");
            stmt.setString(1, supplier.getSupplierNum());
            rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            SupplierProduct supplierProduct;
            while (rs.next()) {
                supplierProduct = new SupplierProduct(rs.getFloat("Price"), rs.getString("SupplierCatalog"), rs.getInt("Amount"), supplier, null, supplier.getMyAgreement());
                contactSupplierProduct.put(rs.getString("SupplierCatalog"), supplierProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return null;
    }
}
