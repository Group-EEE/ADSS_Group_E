package DataAccess.InventoryModule;

import InventoryModule.Business.Category;
import InventoryModule.Business.SubCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAO {
    private Connection conn;
    static CategoryDAO categoryDAO;
    private SubCategoryDAO subCategoryDAO;
    private SubSubCategoryDAO subSubCategoryDAO;

    private Map<String, Category> IdentifyMapCategory;

    private CategoryDAO(Connection conn) {
        this.conn = conn;
        subCategoryDAO = SubCategoryDAO.getInstance(conn);
        subSubCategoryDAO = SubSubCategoryDAO.getInstance(conn);
        IdentifyMapCategory = new HashMap<>();
    }

    public static CategoryDAO getInstance(Connection conn) {
        if (categoryDAO == null)
            categoryDAO = new CategoryDAO(conn);
        return categoryDAO;
    }

    public void ReadCategoryToCache(){
        // -----------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Category");
            ResultSet rs = stmt.executeQuery();

            // -----------------------------------For each Supplier------------------------------------
            while (rs.next()) {
                String category = rs.getString("Name");
                Category cat = new Category(category);
                List<SubCategory> sub = subCategoryDAO.ReadAllSubCategoriesByCategoryName(category);
                cat.setSubCategories(sub);
                IdentifyMapCategory.put(category, cat);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void WriteFromCacheToDB()
    {
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM Category");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
        subCategoryDAO.DeleteFromDB();
        subSubCategoryDAO.DeleteFromDB();
        for (Map.Entry<String, Category> pair : IdentifyMapCategory.entrySet()) {
            try{
                stmt = conn.prepareStatement("Insert into Category VALUES (?)");
                stmt.setString(1, pair.getKey());
                stmt.executeUpdate();
                subCategoryDAO.WriteFromCacheToDB(pair.getValue().getSubCategories(), pair.getKey());
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    public void Insert(Category c){
        IdentifyMapCategory.put(c.getName(), c);
    }

    public Map<String, Category> getIdentifyMapCategory() {
        return IdentifyMapCategory;
    }

    public void delete(Category c){
        IdentifyMapCategory.remove(c.getName());
        for(SubCategory subCategory : c.getSubCategories()){
            subCategoryDAO.delete(c.getName(), subCategory);
        }

    }
}

