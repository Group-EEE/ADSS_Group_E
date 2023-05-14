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
//this class connect between the DB and the SpecificProduct.
public class SpecificProductDAO {
    private Connection conn;
    private DiscountDAO discountDAO;
    static SpecificProductDAO specificProductDAO;
    private Map<List<Integer>, SpecificProduct> IdentifyMapSpecificProduct;

    //constructor
    private SpecificProductDAO(Connection conn) {
        this.conn = conn;
        discountDAO = DiscountDAO.getInstance(this.conn);
        IdentifyMapSpecificProduct = new HashMap<>();
    }
    //implementation of Singeltone Design Pattern
    public static SpecificProductDAO getInstance(Connection conn) {
        if (specificProductDAO == null)
            specificProductDAO = new SpecificProductDAO(conn);
        return specificProductDAO;
    }

    //this method is for upload all the information from the DB when opening the system
    public List<SpecificProduct> ReadAllSpecificProductsByBarcode(int barcode){
        List<SpecificProduct> allsp = new ArrayList<>();
        //-----------------------------------------Create a query-----------------------------------------
        try { //the specific products will be read by their SuperLiProduct
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SpecificProduct WHERE Barcode = ?");
            stmt.setInt(1, barcode);
            ResultSet rs = stmt.executeQuery();
            //-----------------------------------------Create array-----------------------------------------
            while (rs.next()) {
                List<Integer> spkey = new ArrayList<>();
                int sp =rs.getInt("Sp_ID");
                Discount d = discountDAO.ReadDiscountToCache(rs.getInt("Barcode"), rs.getInt("Sp_ID"));
                String exp = rs.getString("ExpDate");
                LocalDateTime expdate = LocalDateTime.parse(exp);
                SpecificProduct specificProduct = new SpecificProduct(rs.getDouble("Supplier_Price"), rs.getString("Supplier"), rs.getInt("Sp_ID"),rs.getInt("Barcode"), expdate, rs.getBoolean("Defective"), rs.getString("Defect_Report_By"), rs.getBoolean("InWarehouse"), rs.getString("Store_Branch"), rs.getInt("Location_in_Store"), d, rs.getString("DefectType"));
                String arrival = rs.getString("arrivaldate");
                LocalDateTime arrDate = LocalDateTime.parse(arrival);
                specificProduct.setArrivaldate(arrDate);
                spkey.add(sp);
                spkey.add(barcode);
                IdentifyMapSpecificProduct.put(spkey, specificProduct);
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
            try{ //"break" every SpecificProduct to its field to enter to the DB
                stmt = conn.prepareStatement("Insert into SpecificProduct VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
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
