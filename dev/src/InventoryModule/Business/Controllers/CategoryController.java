package InventoryModule.Business.Controllers;

import DataAccess.SuperLiDB;
import InventoryModule.Business.Category;
import InventoryModule.Business.SubCategory;
import InventoryModule.Business.SubSubCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// this class controls all categories, subcategories and subsubcategories that exist in "SuperLi"
//in this class we save all the information about them
public class CategoryController {
    static CategoryController categoryController;
    private static SuperLiDB superLiDB;

    //constructor of the category controller
    private CategoryController(){
        superLiDB = SuperLiDB.getInstance();
    }
    public static CategoryController getInstance(){
        if(categoryController == null)
            categoryController = new CategoryController();
        return categoryController;
    }

    //this function adds category to the categories list
    public static void addCategory(String name){
        Category c = new Category(name);
        superLiDB.insertCategory(c);
    }

    //this function adds subcategory to the subcategories list
    public static void addSubCategory(String catname, String subcatname){
        SubCategory sc = new SubCategory(subcatname); //create a subcategory
        for (Map.Entry<String, Category> pair : superLiDB.getCategoriesMap().entrySet()){
            if(pair.getValue().getName().compareTo(catname)==0){
                pair.getValue().addSub(sc);
            }
        }
        superLiDB.insertSubCategory(sc, catname);
    }

    //this function adds subsubcategory to the subsubcategories list
    public static void addSubSubCategory(String subcatname, String subsubcatname, String catname){
        SubSubCategory ssc = new SubSubCategory(subsubcatname);
        for (Map.Entry<String, Category> pair : superLiDB.getCategoriesMap().entrySet()){
            if(pair.getValue().getName().compareTo(catname)==0){
                for(int i=0; i<pair.getValue().getSubCategories().size(); i++){
                    if(pair.getValue().getSubCategories().get(i).getName().compareTo(subcatname)==0){
                        pair.getValue().getSubCategories().get(i).addSubSub(ssc);
                    }
                }
            }
        }
        superLiDB.insertSubSubCategory(ssc, subcatname, catname);
    }

    public List<Category> getCategories() {
        List<Category> cat = new ArrayList<>();
        for (Map.Entry<String, Category> pair : superLiDB.getCategoriesMap().entrySet()){
            cat.add(pair.getValue());
        }
        return cat;
    }

    //this function removes category from the categories list
    public void removeCategory(String name){
        boolean exist = false; //if there is a product that belongs to this category- will be true
        Category c = null;
        for (Map.Entry<String, Category> pair : superLiDB.getCategoriesMap().entrySet()){
            if(pair.getValue().getName().compareTo(name)==0){
                 c = pair.getValue(); //the category found and saved, so we can remove it if need
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    //check for every product in ths store if its category is the given category
                    if(ProductController.getProducts().get(j).getCategory().compareTo(name)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){ //we couldnt find product that belongs to this category
            superLiDB.removeCategory(c);
        }
        else{ //exist = true!
            System.out.println("Can't remove category");
        }
    }

    //this function removes subcategory from the subcategories list
    public void removeSubCategory(String subname, String catname){
        boolean exist = false; //if there is a product that belongs to this subcategory- will be true
        SubCategory c = null;
        for (Map.Entry<List<String>, SubCategory> pair : superLiDB.getSubCategoriesMap().entrySet()){
            if(pair.getValue().getName().compareTo(subname)==0){
                c = pair.getValue(); //the subcategory found and saved, so we can remove it if need
                for(int j=0; j< ProductController.getProducts().size(); j++){
                    //check for every product in ths store if its subcategory is the given subcategory
                    if(ProductController.getProducts().get(j).getSubCategory().compareTo(subname)==0){
                        exist = true;
                    }
                }
            }
        }
        if(exist==false){ //we couldnt find product that belongs to this sucategory
            for (Map.Entry<String, Category> pair : superLiDB.getCategoriesMap().entrySet()){//find the main category of the subcategory
                if(pair.getValue().getName().compareTo(catname)==0){ //the main category
                    //remove the subcategory from the main category's list
                    pair.getValue().getSubCategories().remove(c);
                }
            }
            superLiDB.removeSubCategory(c);//remove the subcategory from the controllers' list
        }
        else{
            System.out.println("Can't remove subcategory");
        }
    }

    //this function removes subsubcategory from the subsubcategories list
    public void removeSubSubCategory(String subsubname,String subname, String catname){
        boolean exist = false;//if there is a product that belongs to this subsubcategory- will be true
        SubSubCategory c = null;
        for (Map.Entry<List<String>, SubSubCategory> pair : superLiDB.getSubSubCategoriesMap().entrySet()){//find the subsubcategory by its name in the list
            if(pair.getValue().getName().compareTo(subsubname)==0){
                c = pair.getValue();//the subsubcategory found and saved, so we can remove it if need
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
            for (Map.Entry<List<String>, SubCategory> pair : superLiDB.getSubCategoriesMap().entrySet()){
                if(pair.getValue().getName().compareTo(catname)==0){
                    //remove the subsubcategory from the subcategory's list
                    pair.getValue().getSubSubCategories().remove(c);
                }
            }
            superLiDB.removeSubSubCategory(c);//remove the subsubcategory from the controllers' list
        }
        else{
            System.out.println("Can't remove subsubcategory");
        }
    }

    //this function checks if the category exist in the system
    public boolean check_if_exist_cat(String cat_name){
       if(superLiDB.getCategoriesMap()!=null){
           for (Map.Entry<String, Category> pair : superLiDB.getCategoriesMap().entrySet()){//check every category in the controller's list
               if(pair.getValue().getName().compareTo(cat_name)==0){
                   return true;
               }
           }
       }
       return false;

    }

    //this function checks if the subcategory exist in the system
    public boolean check_if_exist_subcat(String subcat_name){
        for (Map.Entry<List<String>, SubCategory> pair : superLiDB.getSubCategoriesMap().entrySet()){//check every category in the controller's list
            if(pair.getValue().getName().compareTo(subcat_name)==0){
                return true;
            }
        }
        return false;
    }

    //his function checks if the subsubcategory exist in the system
    public boolean check_if_exist_subsubcat(String subsubcat_name){
        for (Map.Entry<List<String>, SubSubCategory> pair : superLiDB.getSubSubCategoriesMap().entrySet()){//check every category in the controller's list
            if(pair.getValue().getName().compareTo(subsubcat_name)==0){
                return true;
            }
        }
        return false;
    }

    //this methods prints all categories that exist in the system
    public void PrintCategorysInSystem(){
        for (Map.Entry<String, Category> pair : superLiDB.getCategoriesMap().entrySet()){//check every category in the controller's list
            System.out.println(pair.getValue().getName());
        }
    }

    public List<SubCategory> getSubcategories() {
        List<SubCategory> subcat = new ArrayList<>();
        for (Map.Entry<List<String>, SubCategory> pair : superLiDB.getSubCategoriesMap().entrySet()){
            subcat.add(pair.getValue());
        }
        return subcat;
    }

    public List<SubSubCategory> getSubSubCategories() {
        List<SubSubCategory> subsubcat = new ArrayList<>();
        for (Map.Entry<List<String>, SubSubCategory> pair : superLiDB.getSubSubCategoriesMap().entrySet()){
            subsubcat.add(pair.getValue());
        }
        return subsubcat;
    }
}
