import InventoryModule.Business.Discount;
import InventoryModule.Business.SuperLiProduct;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuperLiProductTest {
    SuperLiProduct p = new SuperLiProduct(1234, "Bamba", 5.00, "Osem","Snack", "salty", 5, "Osem", 3);

    @org.junit.jupiter.api.Test
    void getBarcode() {
        assertEquals(1234, p.getBarcode());
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
        assertEquals(0, p.getShelf_amount());
    }

    @Test
    void getWarehouse_amount() {
        assertEquals(0, p.getWarehouse_amount());
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
        p.addSpecificProduct("osem", 5.00, 1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, true, "shufersal", -1, d, null);
        assertEquals(1, p.getSpecificProducts().get(0).getSp_ID());
    }

    @Test
    void addSpecificProduct() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct("osem", 5.00, 1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, true, "shufersal", -1, d, null);
        assertEquals(-1, p.getSpecificProducts().get(0).getLocation_in_Store());
    }

    @Test
    void removeSpecificProduct() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct("osem", 5.00, 1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, true, "shufersal", -1, d, null);
        p.removeSpecificProduct(1);
        assertEquals(0, p.getSpecificProducts().size());
    }

    @Test
    void add_defected_specific_product() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct("osem", 5.00, 1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d, "opened");
        p.add_defected_specific_product(1, "Liron", "wet bamba");
        assertEquals("opened", p.getDefectedProducts().get(0).getDefectType());
    }

    @Test
    void getSpecificProduct() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct("osem", 5.00, 1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d, "opened");
        assertEquals("opened", p.getDefectedProducts().get(0).getDefectType());
    }

    @Test
    void getProductLocationInStore() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct("osem", 5.00, 1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d, null);
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
    void getManufacturer() {
        assertEquals("Osem", p.getManufacturer());
    }
}