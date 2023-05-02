package DataAccess.InventoryModule;

import InventoryModule.Business.Discount;
import InventoryModule.Business.SpecificProduct;
import InventoryModule.Business.SubCategory;
import InventoryModule.Business.SubSubCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificProductDAO {
    private Connection conn;
    private DiscountDAO discountDAO;
    static SpecificProductDAO specificProductDAO;
    private Map<Integer, SpecificProduct> IdentifyMapSpecificProduct;

    private SpecificProductDAO(Connection conn) {
        this.conn = conn;
        specificProductDAO = SpecificProductDAO.getInstance(this.conn);
        discountDAO = DiscountDAO.getInstance(this.conn);
        IdentifyMapSpecificProduct = new HashMap<>();
    }

    public static SpecificProductDAO getInstance(Connection conn) {
        if (specificProductDAO == null)
            specificProductDAO = new SpecificProductDAO(conn);
        return specificProductDAO;
    }

    public List<SpecificProduct> getAllSpecificProductsByBarcode(int barcode){
        List<SpecificProduct> allsp = new ArrayList<>();
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SpecificProduct WHERE P_ID = ?");
            stmt.setInt(1, barcode);
            ResultSet rs = stmt.executeQuery();
            //-----------------------------------------Create array-----------------------------------------
            while (rs.next()) {
                int sp =rs.getInt("Sp_ID");
                Discount d = discountDAO.getDiscountByParameters(rs.getString("Start"), rs.getString("End"), rs.getString("Discount"));
                String exp = rs.getString("ExpDate");
                DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime expdate = LocalDateTime.parse(exp, sf);
                SpecificProduct specificProduct = new SpecificProduct(rs.getDouble("Supplier_Price"), rs.getString("Supplier"), rs.getInt("Sp_ID"),rs.getInt("P_ID"), expdate, rs.getBoolean("Defective"), rs.getString("Defect_Report_By"), rs.getBoolean("InWarehouse"), rs.getString("Store_Branch"), rs.getInt("Location_in_Store"), d, rs.getString("DefectType"));
                IdentifyMapSpecificProduct.put(sp, specificProduct);
                allsp.add(specificProduct);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return allsp;

    }


}
