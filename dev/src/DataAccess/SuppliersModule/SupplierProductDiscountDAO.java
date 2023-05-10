package DataAccess.SuppliersModule;

import SuppliersModule.Business.SupplierProduct;
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
                SupplierProductDiscount supplierProductDiscount = new SupplierProductDiscount(rs.getFloat("Percentages"), rs.getInt("MinimumAmount"));
                IdentifyMapSupplierProductDiscount.put(createKey(supplierNum, supplierCatalog, rs.getInt("MinimumAmount")), supplierProductDiscount);
                DiscountProducts.put(rs.getInt("MinimumAmount"), supplierProductDiscount);
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

    public void WriteFromCacheToDB() {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM SupplierProductDiscount");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<List<String>, SupplierProductDiscount> pair : IdentifyMapSupplierProductDiscount.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into SupplierProductDiscount VALUES (?,?,?,?)");
                stmt.setString(1, pair.getKey().get(0));
                stmt.setString(2,pair.getKey().get(1));
                stmt.setFloat(3, pair.getValue().getPercentages());
                stmt.setInt(4, Integer.parseInt(pair.getKey().get(2)));
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    public void insert(SupplierProduct supplierProduct, SupplierProductDiscount supplierProductDiscount)
    {
        List<String> key = createKey(supplierProduct.getMySupplier().getSupplierNum(), supplierProduct.getSupplierCatalog(), supplierProductDiscount.getMinimumAmount());
        IdentifyMapSupplierProductDiscount.put(key, supplierProductDiscount);
    }

    public void deleteBySupplier(String supplierNum)
    {
        Map<List<String>, SupplierProductDiscount> copyMap = new HashMap<>(IdentifyMapSupplierProductDiscount);
        for (Map.Entry<List<String>, SupplierProductDiscount> pair : copyMap.entrySet()) {
            if (pair.getKey().get(0).equals(supplierNum))
                IdentifyMapSupplierProductDiscount.remove(pair.getKey());
        }
    }

    public void delete(String supplierNum, String supplierCatalog, int amount)
    {
        List<String> key = createKey(supplierNum, supplierCatalog, amount);
        IdentifyMapSupplierProductDiscount.remove(key);
    }
}
