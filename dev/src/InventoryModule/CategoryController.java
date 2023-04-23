package InventoryModule;

import java.util.ArrayList;
import java.util.List;
// this class controls all categories, subcategories and subsubcategories that exist in "SuperLi"
//in this class we save all the information about them
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
        SubCategory sc = new SubCategory(subcatname); //create a subcategory
        for(int i=0; i<categories.size(); i++){ //looking for its main category
            if(categories.get(i).getName().compareTo(catname)==0){
                categories.get(i).addSub(sc); //add the subcategory to the main category's list
            }
        }
        subcategories.add(sc);
    }

    //this function adds subsubcategory to the subsubcategories list
    public static void addSubSubCategory(String subcatname, String subsubcatname){
        SubSubCategory ssc = new SubSubCategory(subsubcatname);
        for(int i=0; i<subcategories.size(); i++){ //looking for its subcategory
            if(subcategories.get(i).getName().compareTo(subcatname)==0){
                subcategories.get(i).addSubSub(ssc); //add the subcategory to the subcategory's list
            }
        }
        subSubCategories.add(ssc);
    }

    //this function removes category from the categories list
    public void removeCategory(String name){
        boolean exist = false; //if there is a product that belongs to this category- will be true
        Category c = null;
        for(int i=0;i<categories.size();i++){ //find the category by its name in the list
            if(categories.get(i).getName().compareTo(name)==0){
                 c = categories.get(i); //the category found and saved, so we can remove it if need
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    //check for every product in ths store if its category is the given category
                    if(ProductController.getProducts().get(j).getCategory().compareTo(name)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){ //we couldnt find product that belongs to this category
            categories.remove(c);
        }
        else{ //exist = true!
            System.out.println("Can't remove category");
        }
    }

    //this function removes subcategory from the subcategories list
    public void removeSubCategory(String subname, String catname){
        boolean exist = false; //if there is a product that belongs to this subcategory- will be true
        SubCategory c = null;
        for(int i=0;i<subcategories.size();i++){ //find the subcategory by its name in the list
            if(subcategories.get(i).getName().compareTo(subname)==0){
                c = subcategories.get(i); //the subcategory found and saved, so we can remove it if need
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    //check for every product in ths store if its subcategory is the given subcategory
                    if(ProductController.getProducts().get(j).getSubCategory().compareTo(subname)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){ //we couldnt find product that belongs to this sucategory
            for(int  j=0;j<CategoryController.categories.size();j++){//find the main category of the subcategory
                if(CategoryController.categories.get(j).getName().compareTo(catname)==0){ //the main category
                    //remove the subcategory from the main category's list
                    CategoryController.categories.get(j).getSubCategories().remove(c);
                }
            }
            subcategories.remove(c); //remove the subcategory from the controllers' list
        }
        else{
            System.out.println("Can't remove subcategory");
        }
    }

    //this function removes subsubcategory from the subsubcategories list
    public void removeSubSubCategory(String subsubname,String subname, String catname){
        boolean exist = false;//if there is a product that belongs to this subsubcategory- will be true
        SubSubCategory c = null;
        for(int i=0;i<subSubCategories.size();i++){//find the subsubcategory by its name in the list
            if(subSubCategories.get(i).getName().compareTo(subsubname)==0){
                c = subSubCategories.get(i);//the subsubcategory found and saved, so we can remove it if need
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    //check for every product in ths store if its subcategory is the given subsubcategory
                    if(ProductController.getProducts().get(j).getSubSubCategory().compareTo(subsubname)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){//we couldnt find product that belongs to this subsucategory
            //find the subcategory of the subsubcategory
            for(int  j=0;j<CategoryController.subcategories.size();j++){
                if(CategoryController.subcategories.get(j).getName().compareTo(catname)==0){
                    //remove the subsubcategory from the subcategory's list
                    CategoryController.subcategories.get(j).getSubSubCategories().remove(c);
                }
            }
            subSubCategories.remove(c);//remove the subsubcategory from the controllers' list
        }
        else{
            System.out.println("Can't remove subsubcategory");
        }
    }

    //this function checks if the category exist in the system
    public static boolean check_if_exist_cat(String cat_name){
       if(categories!=null){
           for(int i=0; i<categories.size(); i++){ //check every category in the controller's list
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
