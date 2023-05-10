import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ProductController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    ProductController productController = ProductController.getInstance();
    CategoryController categoryController = CategoryController.getInstance();

    @Test
    void getProducts() {
        assertEquals(17, ProductController.getProducts().size());
    }

    @Test
    void getProductByBarcode() {
        assertEquals("Yellow cheese", productController.getProductByBarcode(123456).getPName());
        assertEquals("Steak", productController.getProductByBarcode(999911).getPName());
    }
}