import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubCategoryTest {

    SubCategory s = new SubCategory("candy");
    Product p = new Product(1234, "Bamba", "Osem", 5.00, 6.00, 3,
            1000, "Snack", "salty", "yam", 5, "Osem", 3);
    @Test
    void getName() {
        assertEquals("salty", p.getSubCategory());
    }

    @Test
    void getSubSubCategories() {
        assertEquals("Osem", p.getSubSubCategory());

    }

    @Test
    void addSubSub() {
        SubSubCategory ss = new SubSubCategory("sweet");
        s.addSubSub(ss);
        assertEquals("sweet", s.getSubSubCategories().get(0).getName());
    }
}