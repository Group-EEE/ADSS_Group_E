import InventoryModule.Business.Category;
import InventoryModule.Business.SubCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    Category category = new Category("snacks");

    @Test
    void getName() {
        assertEquals("snacks", category.getName());
    }

    @Test
    void getSubCategories() {
        SubCategory sub1 = new SubCategory("salty");
        category.addSub(sub1);
        SubCategory sub2 = new SubCategory("sweet");
        category.addSub(sub2);
        assertEquals(2, category.getSubCategories().size());
    }

    @Test
    void addSub() {
        SubCategory sub1 = new SubCategory("salty");
        category.addSub(sub1);
        SubCategory sub2 = new SubCategory("sweet");
        category.addSub(sub2);
        assertEquals("salty", category.getSubCategories().get(0).getName());
    }
}