import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    Product p = new Product(1234, "Bamba", "Osem", 5.00, 6.00, 3, 1000, "Snack", "salty", "yam", 5, "Osem", 3);

    @org.junit.jupiter.api.Test
    void getBarcode() {
        assertEquals(1234, p.getBarcode());
    }

    @Test
    void getSupplier_Price() {
        assertEquals(5.00, p.getSupplier_Price());
    }

    @Test
    void setSupplier_Price() {
        p.setSupplier_Price(6);
        assertEquals(6.00, p.getSupplier_Price());
    }

    @Test
    void getCostumer_Price() {
        assertEquals(6.00, p.getCostumer_Price());
    }

    @Test
    void setCostumer_Price() {
        p.setCostumer_Price(7);
        assertEquals(7.00, p.getCostumer_Price());
    }

    @Test
    void getShelf_amount() {
        assertEquals(3, p.getShelf_amount());
    }

    @Test
    void getWarehouse_amount() {
        assertEquals(1000, p.getWarehouse_amount());
    }

    @Test
    void getSupply_Days() {
        assertEquals(5, p.getSupply_Days());
    }

    @Test
    void getPName() {
        assertEquals("Bamba", p.getPName());
    }

    @Test
    void getMinimum_Amount() {
        assertEquals(3, p.getMinimum_Amount());
    }

    @Test
    void getSpecificProducts() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d, "opened");
        assertEquals(1, p.getSpecificProducts().get(0).getSp_ID());
    }

    @Test
    void addSpecificProduct() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d, "opened");
        assertEquals(-1, p.getSpecificProducts().get(0).getLocation_in_Store());
    }

    @Test
    void removeSpecificProduct() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d, "opened");
        p.removeSpecificProduct(1);
        assertEquals(0, p.getSpecificProducts().size());
    }

    @Test
    void add_defected_specific_product() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d, "opened");
        p.add_defected_specific_product(1, "Liron", "wet bamba");
        assertEquals("wet bamba", p.getSpecificProducts().get(0).getDefectType());
    }

    @Test
    void getSpecificProduct() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d, "opened");
    assertEquals(null, p.getSpecificProduct(1).getDefectType());
    }

    @Test
    void getProductLocationInStore() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", false, "shufersal", 10, d, null);
        assertEquals(10, p.getSpecificProducts().get(0).getLocation_in_Store());
    }

    @Test
    void getCategory() {
        assertEquals("Snack", p.getCategory());

    }

    @Test
    void getSubCategory() {
        assertEquals("salty", p.getSubCategory());
    }

    @Test
    void getSubSubCategory() {
        assertEquals("yam", p.getSubSubCategory());
    }

    @Test
    void getSupplier() {
        assertEquals("Osem", p.getSupplier());
    }

    @Test
    void getManufacturer() {
        assertEquals("Osem", p.getManufacturer());
    }
}