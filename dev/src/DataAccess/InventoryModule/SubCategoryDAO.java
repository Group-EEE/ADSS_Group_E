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

//this class connect between the DB and the SubCategory.
public class SubCategoryDAO {
    private Connection conn;
    static SubCategoryDAO subCategoryDAO;
    private SubSubCategoryDAO subSubCategoryDAO;
    private Map<List<String>, SubCategory> IdentifyMapSubCategory;

    //constructor
    private SubCategoryDAO(Connection conn) {
        this.conn = conn;
        subSubCategoryDAO = SubSubCategoryDAO.getInstance(this.conn);
        IdentifyMapSubCategory = new HashMap<>();
    }

    //implementation of Singeltone Design Pattern
    public static SubCategoryDAO getInstance(Connection conn) {
        if (subCategoryDAO == null)
            subCategoryDAO = new SubCategoryDAO(conn);
        return subCategoryDAO;
    }

    //this method is for upload all the information from the DB when opening the system
    public List<SubCategory> ReadAllSubCategoriesByCategoryName(String Category){
        List<SubCategory> sub = new ArrayList<>();

        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SubCategory WHERE Category = ?");
            stmt.setString(1, Category);
            ResultSet rs = stmt.executeQuery();
            //-----------------------------------------Create array-----------------------------------------
            while (rs.next()) {
                List<String> subkey = new ArrayList<>();
                String subCategory =rs.getString("Name");
                SubCategory subcat = new SubCategory(subCategory);
                //we will read all the subsubCategories for every subCategory we read
                List<SubSubCategory> subsubcat = subSubCategoryDAO.ReadAllSubSubCategoriesBySubCategoryName(subCategory, Category);
                subcat.setSubSubCategories(subsubcat);
                subkey.add(Category);
                subkey.add(subCategory);
                IdentifyMapSubCategory.put(subkey, subcat);
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
    //this method is for upload all the information to the DB before closing the system
    public void WriteFromCacheToDB(List <SubCategory> ls, String c){
        PreparedStatement stmt;
        try{
            for(int i=0; i< ls.size(); i++){ //the key of subCategory is its name and its Category name
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

    //insert new subcategory to the IdentifyMapSubCategory
    public void Insert(SubCategory sc, String catname){
        List<String> sublist = new ArrayList<>();
        sublist.add(catname);
        sublist.add(sc.getName());
        IdentifyMapSubCategory.put(sublist, sc);
    }
    public Map<List<String>, SubCategory> getIdentifyMapSubCategory() {
        return IdentifyMapSubCategory;
    }

    public void delete(String category, SubCategory subcat){
        List<String> subkey = new ArrayList<>();
        subkey.add(category);
        subkey.add(subcat.getName());
        IdentifyMapSubCategory.remove(subkey);
        for(SubSubCategory subsubcategory: subcat.getSubSubCategories()){
            subSubCategoryDAO.delete(category,subcat.getName(), subsubcategory.getName());
        }
    }

}
