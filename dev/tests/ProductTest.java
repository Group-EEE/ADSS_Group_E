import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @org.junit.jupiter.api.Test
    void getBarcode() {
        Product p = new Product(1234, "Bamba", "Osem", 5.00, 6.00, 3, 4, "Snack", "salty", "yam", 5, "Osem", 3);
        assertEquals(1234, p.getBarcode());
    }
}