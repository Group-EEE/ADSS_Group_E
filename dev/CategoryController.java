import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    private static List<Category> categories;
    private static List<SubCategory> subcategories;
    private static List<SubSubCategory> subSubCategories;

    public CategoryController(){
        categories = new ArrayList<Category>();
        subcategories = new ArrayList<SubCategory>();
        subSubCategories = new ArrayList<SubSubCategory>();
    }


    public static List<Category> getCategories() {
        return categories;
    }

    public static List<SubCategory> getSubcategories() {
        return subcategories;
    }

    public static List<SubSubCategory> getSubSubCategories() {
        return subSubCategories;
    }

    public static void addCategory(String name){
        Category c = new Category(name);
        categories.add(c);
    }

    public static void addSubCategory(String catname, String subcatname){
        SubCategory sc = new SubCategory(subcatname);
        for(int i=0; i<categories.size(); i++){
            if(categories.get(i).getName().compareTo(catname)==0){
                categories.get(i).addSub(sc);
            }
        }
        subcategories.add(sc);
    }

    public static void addSubSubCategory(String subcatname, String subsubcatname){
        SubSubCategory ssc = new SubSubCategory(subsubcatname);
        for(int i=0; i<subcategories.size(); i++){
            if(subcategories.get(i).getName().compareTo(subcatname)==0){
                subcategories.get(i).addSubSub(ssc);
            }
        }
        subSubCategories.add(ssc);
    }


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

    public static boolean check_if_exist_subcat(String subcat_name){
        for(int i=0; i<subcategories.size(); i++){
            if(subcategories.get(i).getName().compareTo(subcat_name)==0){
                return true;
            }
        }
        return false;
    }

    public static boolean check_if_exist_subsubcat(String subsubcat_name){
        for(int i=0; i<subSubCategories.size(); i++){
            if(subSubCategories.get(i).getName().compareTo(subsubcat_name)==0){
                return true;
            }
        }
        return false;
    }

    public void PrintCategorysInSystem(){
        for(int i=0; i<CategoryController.categories.size(); i++){
            System.out.println(CategoryController.categories.get(i).getName());
        }
    }

}
