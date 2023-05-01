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

public class SupplierProductDAO {

    private Connection conn;
    static SupplierProductDAO supplierProductDAO;
    private Map<List<String>, SupplierProduct> IdentifyMapSupplierProduct;

    private SupplierProductDiscountDAO supplierProductDiscountDAO;

    private GenericProductDAO genericProductDAO;

    private SupplierProductDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSupplierProduct = new HashMap<>();
        genericProductDAO = GenericProductDAO.getInstance(this.conn);
        supplierProductDiscountDAO = SupplierProductDiscountDAO.getInstance(this.conn);
    }

    public static SupplierProductDAO getInstance(Connection conn) {
        if (supplierProductDAO == null)
            supplierProductDAO = new SupplierProductDAO(conn);
        return supplierProductDAO;
    }

    public void creatAllSupplierProductsBySupplier(Supplier supplier)
    {
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE SupplierNum = ?");
            stmt.setString(1, supplier.getSupplierNum());
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String productName = rs.getString("Name");
                String ManufacturerName =rs.getString("ManufacturerName");
                GenericProduct genericProduct = genericProductDAO.getGenericProductByName(productName, ManufacturerName);

                SupplierProduct currSupplierProduct = new SupplierProduct(rs.getFloat("Price"), rs.getString("SupplierCatalog"),
                        rs.getInt("Amount"), supplier, genericProduct, supplier.getMyAgreement());

                currSupplierProduct.setDiscountProducts(supplierProductDiscountDAO.getAll(supplier.getSupplierNum(),rs.getString("SupplierCatalog")));

                IdentifyMapSupplierProduct.put(createKey(supplier.getSupplierNum(), rs.getString("SupplierCatalog")), currSupplierProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public List<String> createKey(String supplierNum, String supplierCatalog)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(supplierNum);
        keyPair.add(supplierCatalog);
        return keyPair;
    }

    public SupplierProduct getSupplierProduct(String supplierNum, String supplierCatalog)
    {
        return IdentifyMapSupplierProduct.get(createKey(supplierNum, supplierCatalog));
    }

}
