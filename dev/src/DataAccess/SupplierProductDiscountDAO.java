package DataAccess;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.OrderDiscount;
import SuppliersModule.Business.SupplierProductDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SupplierProductDiscountDAO {

    private Connection conn;
    static SupplierProductDiscountDAO supplierProductDiscountDAO;
    private Map<List<String>, SupplierProductDiscount> IdentifyMapSupplierProductDiscount;

    private SupplierProductDiscountDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSupplierProductDiscount = new HashMap<>();
    }

    public static SupplierProductDiscountDAO getInstance(Connection conn) {
        if (supplierProductDiscountDAO == null)
            supplierProductDiscountDAO = new SupplierProductDiscountDAO(conn);
        return supplierProductDiscountDAO;
    }

    public TreeMap<Integer, SupplierProductDiscount> getAll(String supplierNum, String supplierCatalog)
    {
        TreeMap<Integer, SupplierProductDiscount> DiscountProducts = new TreeMap<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SupplierProductDiscount WHERE SupplierNum = ? AND SupplierCatalog = ?");
            stmt.setString(1, supplierNum);
            stmt.setString(2, supplierCatalog);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SupplierProductDiscount supplierProductDiscount = new SupplierProductDiscount(rs.getFloat("Percentages"), rs.getInt("Amount"));
                IdentifyMapSupplierProductDiscount.put(createKey(supplierNum, supplierCatalog, rs.getInt("Amount")), supplierProductDiscount);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return DiscountProducts;
    }

    public List<String> createKey(String supplierNum, String supplierCatalog, int minimumAmount)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(supplierNum);
        keyPair.add(supplierCatalog);
        keyPair.add(String.valueOf(minimumAmount));
        return keyPair;
    }
}
