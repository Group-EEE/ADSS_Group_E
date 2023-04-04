import java.util.ArrayList;
import java.util.List;
//this class represent the main categories that products are categorized by
public class Category {
    private String Name; //the name of the category
    private int cat_counter=0; //used to count how many categories we created
    private List<SubCategory> subCategories; //each category has subcategories- we save them in a list

    public Category(String n){ //constructor
        this.Name = n;
        cat_counter++; //in every new category the counter is +1
        subCategories = new ArrayList<SubCategory>(); //create the list
    }

   //this function let us know how many categories we have, by return the counter
    public int getCat_counter() {
        return cat_counter;
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
