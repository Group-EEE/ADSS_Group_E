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
        Discount.update_discount_bycategory("Drinks", LocalDateTime.parse("2024-03-04T00:00:00"), LocalDateTime.parse("2024-03-10T00:00:00"), 15.00);
        assertEquals(15, productController.getProductByBarcode(113366).getSpecificProducts().get(0).getDiscount().getDiscount());
    }


    @Test
    void update_discount_byproduct() {
        Discount.update_discount_byproduct("Yellow cheese", LocalDateTime.parse("2024-03-04T00:00:00"), LocalDateTime.parse("2024-03-10T00:00:00"), 15.00);
        assertEquals(15, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount().getDiscount());
    }

    @Test
    void update_discount_byspecificproduct() {
        Discount.update_discount_byspecificproduct("Yellow cheese", 1, LocalDateTime.parse("2024-03-04T00:00:00"), LocalDateTime.parse("2024-03-10T00:00:00"), 15.00);
        assertEquals(15, productController.getProductByBarcode(123456).getSpecificProducts().get(0).getDiscount().getDiscount());
    }
}