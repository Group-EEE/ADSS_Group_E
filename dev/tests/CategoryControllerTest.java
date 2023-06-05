import InventoryModule.Business.Category;
import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ProductController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {
    CategoryController categoryController = CategoryController.getInstance();
    ProductController productController = ProductController.getInstance();


    @Test
    void addCategory() {
        categoryController.addCategory("Candy");
        assertEquals(true, categoryController.check_if_exist_cat("Candy"));
    }

    @Test
    void addSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        assertEquals(true, categoryController.check_if_exist_subcat("sweet"));
    }

    @Test
    void addSubSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Candy" );
        assertEquals(true, categoryController.check_if_exist_subsubcat("very sweet"));
    }

    @Test
    void removeCategory() {
        categoryController.addCategory("Something");
        categoryController.addSubCategory("Something", "sweet");
        categoryController.addSubSubCategory("sweet", "very sweet", "Something");
        categoryController.removeCategory("Something");
        assertEquals(false, categoryController.check_if_exist_subcat("Something"));
    }

    @Test
    void removeSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "Something");
        categoryController.addSubSubCategory("Something", "very sweet", "Candy" );
        categoryController.removeSubCategory("Something", "Candy");
        assertEquals(false, categoryController.check_if_exist_subcat("Something"));
    }

    @Test
    void removeSubSubCategory() {
        categoryController.addCategory("Candy");
        categoryController.addSubCategory("Candy", "sweet");
        categoryController.addSubSubCategory("sweet", "Something", "Candy" );
        categoryController.removeSubSubCategory("Something", "sweet", "Candy");
        assertEquals(false, categoryController.check_if_exist_subsubcat("Something"));
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