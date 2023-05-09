import InventoryModule.Business.SubCategory;
import InventoryModule.Business.SubSubCategory;
import InventoryModule.Business.SuperLiProduct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubCategoryTest {

    SubCategory s = new SubCategory("candy");
    SuperLiProduct p = new SuperLiProduct(1234, "Bamba", "Osem", 5.00, 6.00,"Snack", "salty", "yam", 5, "Osem", 3);
    @Test
    void getName() {
        assertEquals("salty", p.getSubCategory());
    }

    @Test
    void getSubSubCategories() {
        assertEquals("yam", p.getSubSubCategory());
    }

    @Test
    void addSubSub() {
        SubSubCategory ss = new SubSubCategory("sweet");
        s.addSubSub(ss);
        assertEquals("sweet", s.getSubSubCategories().get(0).getName());
    }
}