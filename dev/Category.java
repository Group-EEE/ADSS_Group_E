import java.util.List;

public class Category {
    private String Name;
    private int cat_counter;
    private List<SubCategory> subCategories;

    public Category(String n){
        this.Name = n;
        cat_counter++;
    }

    public int getCat_counter() {
        return cat_counter;
    }

    public String getName() {
        return Name;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSub(SubCategory sc){
        subCategories.add(sc);
    }
}
