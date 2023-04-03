import java.util.List;

public class SubCategory {
    private String Name;
    private List<SubSubCategory> subSubCategories;
    private Category category;

    public SubCategory(String subcatname) {
        this.Name = subcatname;
    }

    public String getName() {
        return Name;
    }

    public List<SubSubCategory> getSubSubCategories() {
        return subSubCategories;
    }

    public void addSubSub(SubSubCategory ssc){
        subSubCategories.add(ssc);
    }
}
