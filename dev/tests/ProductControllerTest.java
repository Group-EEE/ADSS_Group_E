import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ProductController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    ProductController productController = new ProductController();
    CategoryController categoryController = new CategoryController();
    @Test
    void addProduct() {
        productController.addProduct(1234, "Kinder", "Ferero", 3.5, 5, "Choclate bars", "milky", "yummy", 10, "Fer", 500);
        assertEquals(1, ProductController.getProducts().size());
    }


    @Test
    void getProducts() {
        productController.addProduct(1234, "Kinder", "Ferero", 3.5, 5, "Choclate bars", "milky", "yummy", 10, "Fer", 500);
        productController.addProduct(123, "Bamba", "Osem", 3.5, 5,  "Snacks", "Salty", "yummy", 10, "Osem", 500);
        productController.addProduct(1235, "Petiber", "Osem", 3.5, 5, "Cookies", "milky", "yummy", 10, "Osem", 500);
        productController.addProduct(1236, "Pasta", "Barila", 3.5, 5, "Food", "Pasta", "gluten free", 10, "Barila", 500);
        assertEquals(4, ProductController.getProducts().size());
    }

    @Test
    void getProductByBarcode() {
        productController.addProduct(1234, "Kinder", "Ferero", 3.5, 5, "Choclate bars", "milky", "yummy", 10, "Fer", 500);
        productController.addProduct(123, "Bamba", "Osem", 3.5, 5 ,"Snacks", "Salty", "yummy", 10, "Osem", 500);
        assertEquals("Kinder", productController.getProductByBarcode(1234).getPName());
        assertEquals("Bamba", productController.getProductByBarcode(123).getPName());
    }
}