package DataAccess.InventoryModule;

import InventoryModule.Business.Category;
import InventoryModule.Business.Discount;
import InventoryModule.Business.SpecificProduct;
import InventoryModule.Business.SubCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DiscountDAO {
    private Connection conn;
    static DiscountDAO discountDAO;
    private Map<List<String>, Discount> IdentifyMapDiscount;

    private DiscountDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapDiscount = new HashMap<>();
    }

    public static DiscountDAO getInstance(Connection conn) {
        if (discountDAO == null)
            discountDAO = new DiscountDAO(conn);
        return discountDAO;
    }

    public Discount ReadDiscountToCache(int barcode, int sp_id){
        // -----------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Discount WHERE Barcode = ? AND Sp_ID = ?");
            stmt.setInt(1, barcode);
            stmt.setInt(2, sp_id);
            ResultSet rs = stmt.executeQuery();
            // -----------------------------------For each Supplier------------------------------------
            while (rs.next()) {
                String s = rs.getString("Start");
                LocalDateTime start = LocalDateTime.parse(s+"T00:00:00");
                String e = rs.getString("End");
                LocalDateTime end = LocalDateTime.parse(e+"T00:00:00");
                Double dis = rs.getDouble("Discount");
                Discount d = new Discount(start, end, dis);
                List<String> alldis = new ArrayList<>();
                alldis.add(s);
                alldis.add(e);
                alldis.add(dis.toString());
                alldis.add(String.valueOf(barcode));
                alldis.add(String.valueOf(sp_id));
                IdentifyMapDiscount.put(alldis, d);
                return d;
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
        return null;
    }

    /*public Discount getDiscountByParameters(String s, String e, String d){
        List<String> para = new ArrayList<>();
        para.add(s);
        para.add(e);
        para.add(d);
        return IdentifyMapDiscount.get(para);
    }*/

    public void DeleteFromDB(){
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM Discount");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void WriteFromCacheToDB(SpecificProduct sp, Discount d){
        PreparedStatement stmt;
        try{
            stmt = conn.prepareStatement("Insert into Discount VALUES (?,?,?,?,?)");
            stmt.setInt(1, sp.getBarcode());
            stmt.setInt(2, sp.getSp_ID());
            String s = d.getStart().toString().substring(0,11);
            stmt.setObject(3, s);
            String e = d.getEnd().toString().substring(0,11);
            stmt.setObject(4, e);
            stmt.setDouble(5, d.getDiscount());
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }




}
