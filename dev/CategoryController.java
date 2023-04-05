import java.util.ArrayList;
import java.util.List;
// this class controls all categories, subcategories and subsubcategories that exist in "SuperLi"
public class CategoryController {
    private static List<Category> categories; // list of all categories in system
    private static List<SubCategory> subcategories; // list of all subcategories in system
    private static List<SubSubCategory> subSubCategories; // list of all subsubcategories in system

    //constructor of the category controller
    public CategoryController(){
        categories = new ArrayList<Category>();
        subcategories = new ArrayList<SubCategory>();
        subSubCategories = new ArrayList<SubSubCategory>();
    }

    //this function returns the categories list
    public static List<Category> getCategories() {
        return categories;
    }

    //this function returns the subcategories list
    public static List<SubCategory> getSubcategories() {
        return subcategories;
    }

    //this function returns the subsubcategories list
    public static List<SubSubCategory> getSubSubCategories() {
        return subSubCategories;
    }

    //this function adds category to the categories list
    public static void addCategory(String name){
        Category c = new Category(name);
        categories.add(c);
    }

    //this function adds subcategory to the subcategories list
    public static void addSubCategory(String catname, String subcatname){
        SubCategory sc = new SubCategory(subcatname);
        for(int i=0; i<categories.size(); i++){
            if(categories.get(i).getName().compareTo(catname)==0){
                categories.get(i).addSub(sc);
            }
        }
        subcategories.add(sc);
    }

    //this function adds subsubcategory to the subsubcategories list
    public static void addSubSubCategory(String subcatname, String subsubcatname){
        SubSubCategory ssc = new SubSubCategory(subsubcatname);
        for(int i=0; i<subcategories.size(); i++){
            if(subcategories.get(i).getName().compareTo(subcatname)==0){
                subcategories.get(i).addSubSub(ssc);
            }
        }
        subSubCategories.add(ssc);
    }

    //this function removes category from the categories list

    public void removeCategory(String name){
        boolean exist = false;
        Category c = null;
        for(int i=0;i<categories.size();i++){
            if(categories.get(i).getName().compareTo(name)==0){
                 c = categories.get(i);
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    if(ProductController.getProducts().get(j).getCategory().compareTo(name)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){
            categories.remove(c);
        }
        else{
            System.out.println("Can't remove category");
        }
    }

    //this function removes subcategory from the subcategories list
    public void removeSubCategory(String subname, String catname){
        boolean exist = false;
        SubCategory c = null;
        for(int i=0;i<subcategories.size();i++){
            if(subcategories.get(i).getName().compareTo(subname)==0){
                c = subcategories.get(i);
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    if(ProductController.getProducts().get(j).getSubCategory().compareTo(subname)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){
            for(int  j=0;j<CategoryController.categories.size();j++){
                if(CategoryController.categories.get(j).getName().compareTo(catname)==0){
                    CategoryController.categories.get(j).getSubCategories().remove(c);
                }
            }
            subcategories.remove(c);
        }
        else{
            System.out.println("Can't remove subcategory");
        }
    }

    //this function removes subsubcategory from the subsubcategories list
    public void removeSubSubCategory(String subsubname,String subname, String catname){
        boolean exist = false;
        SubSubCategory c = null;
        for(int i=0;i<subSubCategories.size();i++){
            if(subSubCategories.get(i).getName().compareTo(subsubname)==0){
                c = subSubCategories.get(i);
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    if(ProductController.getProducts().get(j).getSubSubCategory().compareTo(subsubname)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){
            for(int  j=0;j<CategoryController.subcategories.size();j++){
                if(CategoryController.subcategories.get(j).getName().compareTo(catname)==0){
                    CategoryController.subcategories.get(j).getSubSubCategories().remove(c);
                }
            }
            subSubCategories.remove(c);
        }
        else{
            System.out.println("Can't remove subsubcategory");
        }
    }

    //this function checks if the category exist in the system
    public static boolean check_if_exist_cat(String cat_name){
       if(categories!=null){
           for(int i=0; i<categories.size(); i++){
               if(categories.get(i).getName().compareTo(cat_name)==0){
                   return true;
               }
           }
       }
       return false;

    }

    //this function checks if the subcategory exist in the system
    public static boolean check_if_exist_subcat(String subcat_name){
        for(int i=0; i<subcategories.size(); i++){
            if(subcategories.get(i).getName().compareTo(subcat_name)==0){
                return true;
            }
        }
        return false;
    }

    //his function checks if the subsubcategory exist in the system
    public static boolean check_if_exist_subsubcat(String subsubcat_name){
        for(int i=0; i<subSubCategories.size(); i++){
            if(subSubCategories.get(i).getName().compareTo(subsubcat_name)==0){
                return true;
            }
        }
        return false;
    }

    //this methods prints all categories that exist in the system
    public void PrintCategorysInSystem(){
        for(int i=0; i<CategoryController.categories.size(); i++){
            System.out.println(CategoryController.categories.get(i).getName());
        }
    }

}
