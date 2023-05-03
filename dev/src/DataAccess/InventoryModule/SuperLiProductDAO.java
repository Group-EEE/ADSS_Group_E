package DataAccess.InventoryModule;

import InventoryModule.Business.Category;
import InventoryModule.Business.SpecificProduct;
import InventoryModule.Business.SubCategory;
import InventoryModule.Business.SuperLiProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperLiProductDAO {
    private Connection conn;
    static SuperLiProductDAO superLiProductDAO;
    private SpecificProductDAO specificProductDAO;
    private Map<Integer, SuperLiProduct> IdentifyMapSuperLiProduct;
    private SuperLiProductDAO(Connection conn) {
        this.conn = conn;
        specificProductDAO = SpecificProductDAO.getInstance(this.conn);
        IdentifyMapSuperLiProduct = new HashMap<>();
    }
    public static SuperLiProductDAO getInstance(Connection conn) {
        if (superLiProductDAO == null)
            superLiProductDAO = new SuperLiProductDAO(conn);
        return superLiProductDAO;
    }

    public void ReadSuperLiProductToCache(){
        // -----------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SuperLiProduct");
            ResultSet rs = stmt.executeQuery();

            // -----------------------------------For each Supplier------------------------------------
            while (rs.next()) {
                int barcode = rs.getInt("Barcode");
                SuperLiProduct slp = new SuperLiProduct(rs.getInt("Barcode"), rs.getString("PName"), rs.getDouble("Costumer_Price"), rs.getString("Category"), rs.getString("SubCategory"),rs.getString("SubSubCategory"), rs.getInt("Supply_Days"), rs.getString("Manufacturer"), rs.getInt("Minimum_Amount"));
                IdentifyMapSuperLiProduct.put(barcode, slp);
                List <SpecificProduct> allsp = specificProductDAO.getAllSpecificProductsByBarcode(barcode);
                slp.setSpecificProducts(allsp);
                rs.next();
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }


}
