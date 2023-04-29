import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubSubCategoryTest {
    SubSubCategory s = new SubSubCategory("sweet");
    @Test
    void getName() {
        assertEquals("sweet", s.getName());
    }
}