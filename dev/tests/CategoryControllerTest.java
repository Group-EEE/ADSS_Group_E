import InventoryModule.Business.Controllers.CategoryController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {
    CategoryController categoryController = CategoryController.getInstance();

    @Test
    void addCategory() {
        CategoryController.addCategory("Candy");
        assertEquals("Candy", categoryController.getCategories().get(0));
    }

    @Test
    void addSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        assertEquals("sweet", categoryController.getSubcategories().get(0));
    }

    @Test
    void addSubSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals("very sweet", categoryController.getSubSubCategories().get(0));
    }

    @Test
    void removeCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet", "Candy");
        categoryController.removeCategory("Candy");
        assertEquals(0, categoryController.getCategories().size());
    }

    @Test
    void removeSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        categoryController.removeSubCategory("very sweet", "Candy");
        assertEquals(0, categoryController.getSubcategories().size());
    }

    @Test
    void removeSubSubCategory() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        categoryController.removeSubSubCategory("very sweet", "sweet", "Candy");
        assertEquals(0, categoryController.getSubSubCategories().size());
    }

    @Test
    void check_if_exist_cat() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals(true, categoryController.check_if_exist_cat("Candy"));
    }

    @Test
    void check_if_exist_subcat() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals(true, categoryController.check_if_exist_subcat("sweet"));
    }

    @Test
    void check_if_exist_subsubcat() {
        CategoryController.addCategory("Candy");
        CategoryController.addSubCategory("Candy", "sweet");
        CategoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals(true, categoryController.check_if_exist_subsubcat("very sweet"));
    }

}