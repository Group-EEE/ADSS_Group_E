package InventoryModule;

//this class represent the most specific category- subsubcategory.
//each subsubcategory belongs to one subcategory that belongs to one main category
public class SubSubCategory {
    private String Name;

    public SubSubCategory(String name) { //constructor
        Name = name;
    }

    //this function return the name of the subsubcategory
    public String getName() {
        return Name;
    }
}
