import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.Discount;
import InventoryModule.Business.SuperLiProduct;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {
    ProductController productController = ProductController.getInstance();
    CategoryController categoryController = CategoryController.getInstance();
    SuperLiProduct p1 = new SuperLiProduct(1234, "Bamba", 5.00,"Osem", "Snack", "salty",  5, "Osem", 3);
    SuperLiProduct p2 = new SuperLiProduct(12345, "Bamba", 4.5, "Lulu", "Snack", "salty", 5, "Lulu", 5);
    SuperLiProduct p3 = new SuperLiProduct(123456, "Bisli", 6.00, "Osem", "Snack", "salty", 5, "Osem", 3);

    Discount d0 = new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2024-03-04T00:00:00"), 0);

    @Test
    void update_discount_bycategory() {
        productController.addProduct(1234, "Bamba",  5.00, "Osem",  "Snack", "salty",  5, "Osem", 3);
        productController.addProduct(12345, "Bamba",4.5 ,"Lulu","Snack", "salty",  3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", 6.00, "Osem", "Snack", "salty",  5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct("0001", 5,1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        productController.getProductByBarcode(12345).addSpecificProduct("0002", 6, 12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        productController.getProductByBarcode(123456).addSpecificProduct("0003", 7, 123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 15);
        d1.update_discount_bycategory("Snack", d1.getStart(), d1.getEnd(), d1.getDiscount());
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d1, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(124).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(125).getSpecificProducts().get(0).getDiscount());
    }


    @Test
    void update_discount_byproduct() {
        productController.addProduct(1234, "Bamba",  5.00, "Osem",  "Snack", "salty",  5, "Osem", 3);
        productController.addProduct(12345, "Bamba",4.5 ,"Lulu","Snack", "salty",  3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", 6.00, "Osem", "Snack", "salty",  5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct("0001", 5,1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        productController.getProductByBarcode(12345).addSpecificProduct("0002", 6, 12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        productController.getProductByBarcode(123456).addSpecificProduct("0003", 7, 123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 15);
        d1.update_discount_byproduct("Bamba", d1.getStart(), d1.getEnd(), d1.getDiscount());
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d1, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount());
    }

    @Test
    void update_discount_byspecificproduct() {
        productController.addProduct(1234, "Bamba",  5.00, "Osem",  "Snack", "salty",  5, "Osem", 3);
        productController.addProduct(12345, "Bamba",4.5 ,"Lulu","Snack", "salty",  3, "Lulu", 5);
        productController.addProduct(123456, "Bisli", 6.00, "Osem", "Snack", "salty",  5, "Osem", 3);
        productController.getProductByBarcode(1234).addSpecificProduct("0001", 5,1234, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        productController.getProductByBarcode(12345).addSpecificProduct("0002", 6, 12345, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        productController.getProductByBarcode(123456).addSpecificProduct("0003", 7, 123456, LocalDateTime.parse("2024-03-04T00:00:00"), false, null, false, "holon", 15, d0,null);
        Discount d1= new Discount(LocalDateTime.parse("2023-03-04T00:00:00"), LocalDateTime.parse("2023-03-29T00:00:00"), 15);
        d1.update_discount_byspecificproduct("Bamba", 1, d1.getStart(), d1.getEnd(), d1.getDiscount());
        assertEquals(d1, productController.getProductByBarcode(1234).getSpecificProducts().get(0).getDiscount());
        assertEquals(d1, productController.getProductByBarcode(12345).getSpecificProducts().get(0).getDiscount());
        assertEquals(d0, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount());
    }
}