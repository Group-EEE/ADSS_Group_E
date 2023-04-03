import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @org.junit.jupiter.api.Test
    void getBarcode() {
        Product p = new Product(1234, "Bamba", "Osem", 5.00, 6.00, 3, 4, "Snack", "salty", "yam", 5, "Osem", 3);
        assertEquals(1234, p.getBarcode());
    }

    @Test
    void getSupplier_Price() {
        Product p = new Product(1234, "Bamba", "Osem", 5.00, 6.00, 3, 4, "Snack", "salty", "yam", 5, "Osem", 3);
        assertEquals(5.00, p.getSupplier_Price());
    }

    @Test
    void setSupplier_Price() {
    }

    @Test
    void getCostumer_Price() {
    }

    @Test
    void setCostumer_Price() {
    }

    @Test
    void getShelf_amount() {
    }

    @Test
    void setShelf_amount() {
    }

    @Test
    void getWarehouse_amount() {
    }

    @Test
    void setWarehouse_amount() {
    }

    @Test
    void getSupply_Days() {
    }

    @Test
    void setSupply_Days() {
    }

    @Test
    void getPName() {
    }

    @Test
    void getMinimum_Amount() {
    }

    @Test
    void getSpecificProducts() {
    }

    @Test
    void setSpecificProducts() {
    }

    @Test
    void addSpecificProduct() {
    }

    @Test
    void removeSpecificProduct() {
    }

    @Test
    void add_defected_specific_product() {
    }

    @Test
    void getSpecificProduct() {
    }

    @Test
    void getProductLocationInStore() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getSubCategory() {
    }

    @Test
    void getSubSubCategory() {
    }

    @Test
    void getSupplier() {
    }

    @Test
    void getManufacturer() {
    }
}