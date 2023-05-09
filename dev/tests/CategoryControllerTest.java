import InventoryModule.Business.Controllers.CategoryController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {
    CategoryController categoryController = new CategoryController();

    @Test
    void addCategory() {
        CategoryController.addCategory("Candy");
        assertEquals("Candy", CategoryController.getCategories().get(0));
    }

    @Test
    void addSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        assertEquals("sweet", CategoryController.getSubcategories().get(0));
    }

    @Test
    void addSubSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet" );
        assertEquals("very sweet", CategoryController.getSubSubCategories().get(0));
    }

    @Test
    void removeCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet" );
        categoryController.removeCategory("Candy");
        assertEquals(0, CategoryController.getCategories().size());
    }

    @Test
    void removeSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet" );
        categoryController.removeSubCategory("very sweet", "Candy");
        assertEquals(0, CategoryController.getSubcategories().size());
    }

    @Test
    void removeSubSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet" );
        categoryController.removeSubSubCategory("very sweet", "sweet", "Candy");
        assertEquals(0, CategoryController.getSubSubCategories().size());
    }

    @Test
    void check_if_exist_cat() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet" );
        assertEquals(true, CategoryController.check_if_exist_cat("Candy"));
    }

    @Test
    void check_if_exist_subcat() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet" );
        assertEquals(true, CategoryController.check_if_exist_subcat("sweet"));
    }

    @Test
    void check_if_exist_subsubcat() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet" );
        assertEquals(true, CategoryController.check_if_exist_subsubcat("very sweet"));
    }

}