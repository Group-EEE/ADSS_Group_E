import InventoryModule.Business.Category;
import InventoryModule.Business.Controllers.CategoryController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {
    CategoryController categoryController = CategoryController.getInstance();

    @Test
    void addCategory() {
        categoryController.addCategory("Candy");
        Category c = new Category("Candy");
        assertEquals(true, categoryController.getCategories().contains(c));
    }

    @Test
    void addSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        assertEquals("sweet", categoryController.getSubcategories().get(0));
    }

    @Test
    void addSubSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals("very sweet", categoryController.getSubSubCategories().get(0));
    }

    @Test
    void removeCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy");
        categoryController.removeCategory("Candy");
        assertEquals(0, categoryController.getCategories().size());
    }

    @Test
    void removeSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        categoryController.removeSubCategory("very sweet", "Candy");
        assertEquals(0, categoryController.getSubcategories().size());
    }

    @Test
    void removeSubSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        categoryController.removeSubSubCategory("very sweet", "sweet", "Candy");
        assertEquals(0, categoryController.getSubSubCategories().size());
    }

    @Test
    void check_if_exist_cat() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals(true, categoryController.check_if_exist_cat("Candy"));
    }

    @Test
    void check_if_exist_subcat() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals(true, categoryController.check_if_exist_subcat("sweet"));
    }

    @Test
    void check_if_exist_subsubcat() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals(true, categoryController.check_if_exist_subsubcat("very sweet"));
    }

}