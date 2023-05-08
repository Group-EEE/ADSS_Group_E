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

    public void ReadDiscountToCache(){
        // -----------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Discount");
            ResultSet rs = stmt.executeQuery();
            // -----------------------------------For each Supplier------------------------------------
            while (rs.next()) {
                String s = rs.getString("Start");
                DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime start = LocalDateTime.parse(s, sf);
                String e = rs.getString("End");
                LocalDateTime end = LocalDateTime.parse(e, sf);
                Double dis = rs.getDouble("Discount");
                Discount d = new Discount(start, end, dis);
                List<String> alldis = new ArrayList<>();
                alldis.add(s);
                alldis.add(e);
                alldis.add(dis.toString());
                IdentifyMapDiscount.put(alldis, d);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public Discount getDiscountByParameters(String s, String e, String d){
        List<String> para = new ArrayList<>();
        para.add(s);
        para.add(e);
        para.add(d);
        return IdentifyMapDiscount.get(para);
    }

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
            stmt.setObject(3, d.getStart());
            stmt.setObject(4, d.getEnd());
            stmt.setDouble(5, d.getDiscount());
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }




}
