package DataAccess.InventoryModule;

import InventoryModule.Business.Discount;
import InventoryModule.Business.SpecificProduct;
import InventoryModule.Business.SuperLiProduct;

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
        discountDAO = DiscountDAO.getInstance(this.conn);
        IdentifyMapSpecificProduct = new HashMap<>();
    }

    public static SpecificProductDAO getInstance(Connection conn) {
        if (specificProductDAO == null)
            specificProductDAO = new SpecificProductDAO(conn);
        return specificProductDAO;
    }

    public List<SpecificProduct> ReadAllSpecificProductsByBarcode(int barcode){
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

    public void DeleteFromDB(){
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM SpecificProduct");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }
    public void WriteFromCacheToDB(List <SpecificProduct> lsp){
        PreparedStatement stmt;
        for (int i=0; i< lsp.size();i++) {
            try{
                stmt = conn.prepareStatement("Insert into SuperLiProduct VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setInt(1, lsp.get(i).getSp_ID());
                stmt.setInt(2, lsp.get(i).getBarcode());
                stmt.setObject(3, lsp.get(i).getExpDate());
                stmt.setString(4, lsp.get(i).getDefect_Report_By());
                stmt.setBoolean(5, lsp.get(i).isDefective());
                stmt.setBoolean(6, lsp.get(i).isInWarehouse());
                stmt.setString(7, lsp.get(i).getStore_Branch());
                stmt.setInt(8, lsp.get(i).getLocation_in_Store());
                stmt.setString(9, lsp.get(i).getDefectType());
                stmt.setString(10, lsp.get(i).getSupplierNum());
                stmt.setDouble(11, lsp.get(i).getSupplier_Price());
                stmt.setObject(12, lsp.get(i).getArrivaldate());
                stmt.executeUpdate();
                discountDAO.WriteFromCacheToDB(lsp.get(i), lsp.get(i).getDiscount());
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }


}
