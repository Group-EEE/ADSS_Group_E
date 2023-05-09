package DataAccess.InventoryModule;

import InventoryModule.Business.Category;
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
    private Map<List<String>, SubSubCategory> IdentifyMapSubSubCategory;

    private SubSubCategoryDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapSubSubCategory = new HashMap<>();
    }

    public static SubSubCategoryDAO getInstance(Connection conn) {
        if (subSubCategoryDAO == null)
            subSubCategoryDAO = new SubSubCategoryDAO(conn);
        return subSubCategoryDAO;
    }


    public List<SubSubCategory> ReadAllSubSubCategoriesBySubCategoryName(String subCategory, String Category) {
        List<SubSubCategory> subsub = new ArrayList<>();
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SubSubCategory WHERE SubCategory = ? AND Category = ? ");
            stmt.setString(1, subCategory);
            stmt.setString(2, Category);
            ResultSet rs = stmt.executeQuery();
            //-----------------------------------------Create array-----------------------------------------
            while (rs.next()) {
                List<String> subsubkey = new ArrayList<>();
                String subSubCategory =rs.getString("Name");
                SubSubCategory subsubcat = new SubSubCategory(subSubCategory);
                subsubkey.add(rs.getString("Category"));
                subsubkey.add(rs.getString("SubCategory"));
                subsubkey.add(subSubCategory);
                IdentifyMapSubSubCategory.put(subsubkey, subsubcat);
                subsub.add(subsubcat);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
        return subsub;
    }

    public void DeleteFromDB(){
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM SubSubCategory");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void WriteFromCacheToDB(List <SubSubCategory> ssl, String sub, String cat){
        PreparedStatement stmt;
        try{
            for(int i=0; i< ssl.size(); i++){
                stmt = conn.prepareStatement("Insert into SubSubCategory VALUES (?,?,?)");
                stmt.setString(1, ssl.get(i).getName());
                stmt.setString(2, cat);
                stmt.setString(3, sub);
                stmt.executeUpdate();
            }
        }

        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void Insert(SubSubCategory ssc, String subcat, String cat){
        List<String> l = new ArrayList<>();
        l.add(cat);
        l.add(subcat);
        l.add(ssc.getName());
        IdentifyMapSubSubCategory.put(l, ssc);
    }

    public void delete (SubSubCategory sscat){
        IdentifyMapSubSubCategory.remove(sscat.getName());
    }

    public Map<List<String>, SubSubCategory> getIdentifyMapSubSubCategory() {
        return IdentifyMapSubSubCategory;
    }
}
