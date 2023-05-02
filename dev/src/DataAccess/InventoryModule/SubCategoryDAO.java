package DataAccess.InventoryModule;

import InventoryModule.Business.SubCategory;
import InventoryModule.Business.SubSubCategory;
import SuppliersModule.Business.OrderDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryDAO {
    private Connection conn;
    static SubCategoryDAO subCategoryDAO;

    private SubSubCategoryDAO subSubCategoryDAO;
    private Map<String, SubCategory> IdentifyMapSubCategory;

    private SubCategoryDAO(Connection conn) {
        this.conn = conn;
        subCategoryDAO = SubCategoryDAO.getInstance(this.conn);
        subSubCategoryDAO = SubSubCategoryDAO.getInstance(this.conn);
        IdentifyMapSubCategory = new HashMap<>();
    }

    public static SubCategoryDAO getInstance(Connection conn) {
        if (subCategoryDAO == null)
            subCategoryDAO = new SubCategoryDAO(conn);
        return subCategoryDAO;
    }

    public List<SubCategory> getAllSubCategoriesByCategoryName(String Category){
        List<SubCategory> sub = new ArrayList<>();
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SubCategory WHERE Category = ?");
            stmt.setString(1, Category);
            ResultSet rs = stmt.executeQuery();
            //-----------------------------------------Create array-----------------------------------------
            while (rs.next()) {
                String subCategory =rs.getString("Name");
                SubCategory subcat = new SubCategory(subCategory);
                List<SubSubCategory> subsubcat = subSubCategoryDAO.getAllSubSubCategoriesBySubCategoryName(subCategory);
                subcat.setSubSubCategories(subsubcat);
                IdentifyMapSubCategory.put(subCategory, subcat);
                sub.add(subcat);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return sub;
    }

}
