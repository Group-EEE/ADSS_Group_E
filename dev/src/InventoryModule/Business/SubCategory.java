package InventoryModule.Business;

import java.util.ArrayList;
import java.util.List;
//this class represent the subcategories that products are categorized by,
//each subcategory belongs to main category
public class SubCategory {
    private String Name; //the name of the category
    private List<SubSubCategory> subSubCategories; //list of all the subsubcategories of this subcategory

    public SubCategory(String subcatname) { //constructor
        this.Name = subcatname;
        subSubCategories = new ArrayList<SubSubCategory>(); //create the list
    }
    //return the name of the subcategory
    public String getName() {
        return Name;
    }

    //return the list of all the subcategories
    public List<SubSubCategory> getSubSubCategories() {
        return subSubCategories;
    }

    //this function add subsubcategory to the subcategory(to its list)
    public void addSubSub(SubSubCategory ssc){
        subSubCategories.add(ssc);
    }
}
