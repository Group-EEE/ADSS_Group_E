package InventoryModule.Business;

import java.util.ArrayList;
import java.util.List;
//this class represent the main categories that products are categorized by
public class Category {
    private String Name; //the name of the category
    private List<SubCategory> subCategories; //each category has subcategories- we save them in a list

    public Category(String n){ //constructor
        this.Name = n;
        subCategories = new ArrayList<SubCategory>(); //create the list
    }

    //this function return the name of the category
    public String getName() {
        return Name;
    }
    //this function return the list of all the subcategories
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }
    //this function add subcategory to the main category(to its list)
    public void addSub(SubCategory sc){
        subCategories.add(sc);
    }
}
