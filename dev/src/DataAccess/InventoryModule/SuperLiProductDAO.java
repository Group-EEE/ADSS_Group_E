package DataAccess.InventoryModule;

import DataAccess.SuperLiDB;
import InventoryModule.Business.SpecificProduct;
import InventoryModule.Business.SuperLiProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//this class connect between the DB and the SuperLiProduct and SpecificProduct.
public class SuperLiProductDAO {
    private Connection conn;
    static SuperLiProductDAO superLiProductDAO;
    private SpecificProductDAO specificProductDAO;
    private DiscountDAO discountDAO;
    private Map<Integer, SuperLiProduct> IdentifyMapSuperLiProduct;

    //constructor
    private SuperLiProductDAO(Connection conn) {
        this.conn = conn;
        specificProductDAO = SpecificProductDAO.getInstance(this.conn);
        discountDAO = DiscountDAO.getInstance(this.conn);
        IdentifyMapSuperLiProduct = new HashMap<>();
    }
    //implementation of Singeltone Design Pattern
    public static SuperLiProductDAO getInstance(Connection conn) {
        if (superLiProductDAO == null)
            superLiProductDAO = new SuperLiProductDAO(conn);
        return superLiProductDAO;
    }

    //this method is for upload all the information from the DB when opening the system
    public void ReadSuperLiProductToCache(){
        // -----------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SuperLiProduct");
            ResultSet rs = stmt.executeQuery();

            // -----------------------------------For each Supplier------------------------------------
            while (rs.next()) {
                int barcode = rs.getInt("Barcode");
                SuperLiProduct slp = new SuperLiProduct(rs.getInt("Barcode"), rs.getString("PName"), rs.getDouble("Costumer_Price"), rs.getString("Category"), rs.getString("SubCategory"),rs.getString("SubSubCategory"), rs.getInt("Supply_Days"), rs.getString("Manufacturer"), rs.getInt("Minimum_Amount"));
                slp.setSp_counter(rs.getInt("SP_Counter"));
                IdentifyMapSuperLiProduct.put(barcode, slp);
                //each SuperLiProduct has list of al its SpecificProducts
                List <SpecificProduct> allsp = specificProductDAO.ReadAllSpecificProductsByBarcode(barcode);
                slp.setSpecificProducts(allsp);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    //this method is for upload all the information to the DB before closing the system
    public void WriteFromCacheToDB()
    {
        PreparedStatement stmt;
        try{
            stmt = conn.prepareStatement("DELETE FROM SuperLiProduct");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
        //delete all the old information about the specificProducts and discount
        //so we won't have inconsistency
        specificProductDAO.DeleteFromDB();
        discountDAO.DeleteFromDB();
        //"break" every SuperLiProduct to its field to enter to the DB
        for (Map.Entry<Integer, SuperLiProduct> pair : IdentifyMapSuperLiProduct.entrySet()) {
            try{
                stmt = conn.prepareStatement("Insert into SuperLiProduct VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setInt(1, pair.getValue().getBarcode());
                stmt.setString(2, pair.getValue().getPName());
                stmt.setDouble(3, pair.getValue().getCostumer_Price());
                stmt.setInt(4, pair.getValue().getShelf_amount());
                stmt.setInt(5, pair.getValue().getWarehouse_amount());
                stmt.setString(6, pair.getValue().getCategory());
                stmt.setString(7, pair.getValue().getSubCategory());
                stmt.setString(8, pair.getValue().getSubSubCategory());
                stmt.setInt(9, pair.getValue().getSupply_Days());
                stmt.setString(10, pair.getValue().getManufacturer());
                stmt.setInt(11, pair.getValue().getMinimum_Amount());
                stmt.setInt(12, pair.getValue().getSp_counter());
                stmt.executeUpdate();
                specificProductDAO.WriteFromCacheToDB(pair.getValue().getSpecificProducts());
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    public void Insert(SuperLiProduct p){
        IdentifyMapSuperLiProduct.put(p.getBarcode(), p);
    }

    public Map<Integer, SuperLiProduct> getIdentifySuperLiProduct(){
        return IdentifyMapSuperLiProduct;
    }




}
