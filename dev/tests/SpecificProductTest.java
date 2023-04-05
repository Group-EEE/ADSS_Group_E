import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SpecificProductTest {

    Product p = new Product(1234, "Bamba", "Osem", 5.00, 6.00, 3, 1000, "Snack", "salty", "yam", 5, "Osem", 3);

    @Test
    void isDefective() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), true, "sapir", true, "shufersal", -1, d,"open");
        assertEquals(true, p.getSpecificProducts().get(0).isDefective());
    }

    @Test
    void setDefective() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, "sapir", true, "shufersal", -1, d,null);
        p.getSpecificProducts().get(0).setDefective(true, "wet bamba");
        assertEquals(true, p.getSpecificProducts().get(0).isDefective());
        assertEquals("wet bamba", p.getSpecificProducts().get(0).getDefectType());
    }

    @Test
    void getDefect_Report_By() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, true, "shufersal", -1,d, null);
        assertEquals(null , p.getSpecificProducts().get(0).getDefect_Report_By());
    }

    @Test
    void setDefect_Report_By() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, true, "shufersal", -1, d,null);
        p.getSpecificProducts().get(0).setDefective(true, "broken");
        p.getSpecificProducts().get(0).setDefect_Report_By("sapir");
        assertEquals("sapir", p.getSpecificProducts().get(0).getDefect_Report_By());
    }

    @Test
    void isInWarehouse() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, "sapir", true, "shufersal", -1, d,null);
        assertEquals(true, p.getSpecificProducts().get(0).isInWarehouse());
    }

    @Test
    void setInWarehouse() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        p.getSpecificProducts().get(0).setInWarehouse(true);
        assertEquals(true, p.getSpecificProducts().get(0).isInWarehouse());
    }

    @Test
    void getLocation_in_Store() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        assertEquals(10, p.getSpecificProducts().get(0).getLocation_in_Store());
    }

    @Test
    void setLocation_in_Store() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        p.getSpecificProducts().get(0).setLocation_in_Store(11);
        assertEquals(11, p.getSpecificProducts().get(0).getLocation_in_Store());
    }

    @Test
    void getDiscount() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        assertEquals(25, p.getSpecificProducts().get(0).getDiscount().Discount);
    }

    @Test
    void setDiscount() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        Discount newd = new Discount(LocalDateTime.parse("2023-06-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 55);
        p.getSpecificProducts().get(0).setDiscount(newd);
        assertEquals(55, p.getSpecificProducts().get(0).getDiscount().Discount);
    }

    @Test
    void getSp_ID() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, "sapir", false, "shufersal", 10, d,null);
        assertEquals(1, p.getSpecificProducts().get(0).getSp_ID());
    }

    @Test
    void getExpDate() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        assertEquals(LocalDateTime.parse("2023-03-04T00:00:00"), p.getSpecificProducts().get(0).getExpDate());
    }

    @Test
    void getDefectType() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        assertEquals(null, p.getSpecificProducts().get(0).getDefectType());

    }

    @Test
    void setDefectType() {
        Discount d = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-15T00:00:00"), 25);
        p.addSpecificProduct(1234, LocalDateTime.parse("2023-03-04T00:00:00"), false, null, false, "shufersal", 10, d,null);
        p.getSpecificProducts().get(0).setDefectType("broken");
        assertEquals("broken", p.getSpecificProducts().get(0).getDefectType());
    }
}