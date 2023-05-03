package DataAccess.InventoryModule;

import InventoryModule.Business.SubCategory;
import InventoryModule.Business.SubSubCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubSubCategoryDAO {
    private Connection conn;
    static SubSubCategoryDAO subSubCategoryDAO;
    private Map<String, SubSubCategory> IdentifyMapSubSubCategory;

    private SubSubCategoryDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSubSubCategory = new HashMap<>();
    }

    public static SubSubCategoryDAO getInstance(Connection conn) {
        if (subSubCategoryDAO == null)
            subSubCategoryDAO = new SubSubCategoryDAO(conn);
        return subSubCategoryDAO;
    }


    public List<SubSubCategory> getAllSubSubCategoriesBySubCategoryName(String subCategory) {
        List<SubSubCategory> subsub = new ArrayList<>();
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SubSubCategory WHERE SubCategory = ?");
            stmt.setString(1, subCategory);
            ResultSet rs = stmt.executeQuery();
            //-----------------------------------------Create array-----------------------------------------
            List<String> key;
            while (rs.next()) {
                String subSubCategory =rs.getString("Name");
                SubSubCategory subsubcat = new SubSubCategory(subSubCategory);
                IdentifyMapSubSubCategory.put(subSubCategory, subsubcat);
                subsub.add(subsubcat);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
        return subsub;
    }
}
