import InventoryModule.Business.Category;
import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ProductController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombineTest {

    ProductController productController = ProductController.getInstance();

    //adding new SuperLiProduct according to new GenericProduct
    @Test
    public void Test1(){ //need to add generic product to the db
        int s = productController.getBarcodesOfNewProductsSize();
        productController.addProduct(882288, "Rollups", 100.00, "Candy", "Ice cream", "Gimic", 5, "Bad guys", 10);
        assertEquals(s-1, productController.getBarcodesOfNewProductsSize());
    }

    @Test
    public void Test2(){ //need to add generic product to the db

    }

}

