package DataAccess.InventoryModule;

import InventoryModule.Business.Category;
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

public class SubCategoryDAO {
    private Connection conn;
    static SubCategoryDAO subCategoryDAO;
    private SubSubCategoryDAO subSubCategoryDAO;
    private Map<String, SubCategory> IdentifyMapSubCategory;

    private SubCategoryDAO(Connection conn) {
        this.conn = conn;
        subSubCategoryDAO = SubSubCategoryDAO.getInstance(this.conn);
        IdentifyMapSubCategory = new HashMap<>();
    }

    public static SubCategoryDAO getInstance(Connection conn) {
        if (subCategoryDAO == null)
            subCategoryDAO = new SubCategoryDAO(conn);
        return subCategoryDAO;
    }

    public List<SubCategory> ReadAllSubCategoriesByCategoryName(String Category){
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
                List<SubSubCategory> subsubcat = subSubCategoryDAO.ReadAllSubSubCategoriesBySubCategoryName(subCategory);
                subcat.setSubSubCategories(subsubcat);
                IdentifyMapSubCategory.put(subCategory, subcat);
                sub.add(subcat);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return sub;
    }

    public void DeleteFromDB(){
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM SubCategory");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }
    public void WriteFromCacheToDB(List <SubCategory> ls, String c){
        PreparedStatement stmt;
        try{
            for(int i=0; i< ls.size(); i++){
                stmt = conn.prepareStatement("Insert into SubCategory VALUES (?,?)");
                stmt.setString(1, ls.get(i).getName());
                stmt.setString(2, c);
                stmt.executeUpdate();
                subSubCategoryDAO.WriteFromCacheToDB(ls.get(i).getSubSubCategories(), ls.get(i).getName(), c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Insert(SubCategory sc){
        IdentifyMapSubCategory.put(sc.getName(), sc);
    }
    public Map<String, SubCategory> getIdentifyMapSubCategory() {
        return IdentifyMapSubCategory;
    }

    public void delete(SubCategory subcat){
        IdentifyMapSubCategory.remove(subcat.getName());
        for(SubSubCategory subsubcategory: subcat.getSubSubCategories()){
            subSubCategoryDAO.delete(subsubcategory);
        }
    }

}
