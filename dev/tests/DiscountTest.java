import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {
    ProductController productController = new ProductController();
    CategoryController categoryController = new CategoryController();
    Product p1 = new Product(1234, "Bamba", "Osem", 5.00, 6.00, 50, 1000, "Snack", "salty", "yam", 5, "Osem", 3);
    Product p2 = new Product(12345, "Bamba", "Lulu", 4.5, 5.00, 50, 500, "Snack", "salty", "yam", 3, "Lulu", 5);
    Product p3 = new Product(123456, "Bisli", "Osem", 6.00, 7.00, 70, 700, "Snack", "salty", "yam", 5, "Osem", 3);

    Discount d0 = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2024-03-04T00:00:00"), 0);
    @Test
    void update_discount_bycategory_from_supplier() {
        productController.addProduct(1234, "Bamba", "Osem", 5.00, 6.00, 50, 1000, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.addProduct(12345, "Bamba", "Lulu", 4.5, 5.00, 50, 500, "Snack", "salty", "yam", 3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", "Osem", 6.00, 7.00, 70, 700, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct(1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(12345).addSpecificProduct(12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(123456).addSpecificProduct(123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 15);
        d1.update_discount_bycategory_from_supplier("Snack", "Osem");
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d1, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount());

    }

    @Test
    void update_discount_bycategory() {
        productController.addProduct(1234, "Bamba", "Osem", 5.00, 6.00, 50, 1000, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.addProduct(12345, "Bamba", "Lulu", 4.5, 5.00, 50, 500, "Snack", "salty", "yam", 3, "Lulu", 5);
        productController.addProduct(124, "Oreo", "Oreo", 3.00, 4.00, 100, 10000, "Cookies", "sweet", "choco", 15, "Oreo", 10);
        productController.addProduct(125, "Kinder", "Ferero", 9.00, 10.50, 200, 20000, "Choclate bars", "milky", "yummy", 20, "Ferero", 20);
        productController.getProductByBarcode(1234).addSpecificProduct(1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(12345).addSpecificProduct(12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(124).addSpecificProduct(124, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(125).addSpecificProduct(125, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 20);
        d1.update_discount_bycategory("Snack");
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d1, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(124).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(125).getSpecificProducts().get(0).getDiscount());
    }

    @Test
    void update_discount_byproduct_from_supplier() {
        productController.addProduct(1234, "Bamba", "Osem", 5.00, 6.00, 50, 1000, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.addProduct(12345, "Bamba", "Lulu", 4.5, 5.00, 50, 500, "Snack", "salty", "yam", 3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", "Osem", 6.00, 7.00, 70, 700, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct(1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(12345).addSpecificProduct(12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(123456).addSpecificProduct(123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 20);
        d1.update_discount_byproduct_from_supplier("Bamba", "Osem");
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount());
    }

    @Test
    void update_discount_byproduct() {
        productController.addProduct(1234, "Bamba", "Osem", 5.00, 6.00, 50, 1000, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.addProduct(12345, "Bamba", "Lulu", 4.5, 5.00, 50, 500, "Snack", "salty", "yam", 3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", "Osem", 6.00, 7.00, 70, 700, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct(1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(12345).addSpecificProduct(12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(123456).addSpecificProduct(123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 20);
        d1.update_discount_byproduct("Bamba");
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d1, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount());
    }

    @Test
    void update_discount_byspecificproduct_from_supplier() {
        productController.addProduct(1234, "Bamba", "Osem", 5.00, 6.00, 50, 1000, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.addProduct(12345, "Bamba", "Lulu", 4.5, 5.00, 50, 500, "Snack", "salty", "yam", 3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", "Osem", 6.00, 7.00, 70, 700, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct(1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(12345).addSpecificProduct(12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(123456).addSpecificProduct(123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 20);
        d1.update_discount_byspecificproduct_from_supplier("Bamba", 1, "Osem");
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount());
    }

    @Test
    void update_discount_byspecificproduct() {
        productController.addProduct(1234, "Bamba", "Osem", 5.00, 6.00, 50, 1000, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.addProduct(12345, "Bamba", "Lulu", 4.5, 5.00, 50, 500, "Snack", "salty", "yam", 3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", "Osem", 6.00, 7.00, 70, 700, "Snack", "salty", "yam", 5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct(1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(12345).addSpecificProduct(12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        productController.getProductByBarcode(123456).addSpecificProduct(123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 20);
        d1.update_discount_byspecificproduct("Bamba", 1);
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d1, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount());
    }
}