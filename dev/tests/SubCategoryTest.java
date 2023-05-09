import InventoryModule.Business.SubCategory;
import InventoryModule.Business.SubSubCategory;
import InventoryModule.Business.SuperLiProduct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubCategoryTest {

    SubCategory s = new SubCategory("candy");
    SuperLiProduct p = new SuperLiProduct(123456, "Bamba",11,  "peanuts", "Snack","salty", 5, "Osem",6);
    @Test
    void getName() {
        assertEquals("Snack", p.getSubCategory());
    }

    @Test
    void getSubSubCategories() {
        assertEquals("salty", p.getSubSubCategory());
    }

    @Test
    void addSubSub() {
        SubSubCategory ss = new SubSubCategory("sweet");
        s.addSubSub(ss);
        assertEquals("sweet", s.getSubSubCategories().get(0).getName());
    }
}