import DataAccess.SuperLiDB;
import InventoryModule.Business.Category;
import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.Controllers.ReportController;
import InventoryModule.Business.OrderReport;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.Controllers.SupplierController;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombineTest {

    ProductController productController = ProductController.getInstance();
    SupplierController supplierController = SupplierController.getInstance();
    ReportController reportController = ReportController.getInstance();
    OrderController orderController = OrderController.getInstance();

    //adding new SuperLiProduct according to new GenericProduct
    @Test
    public void Test1(){ //need to add generic product to the db
        int s = productController.getBarcodesOfNewProductsSize();
        supplierController.addSupplierProduct("Rollups", "Bad guys", 882288, "0001", 10,"111", 50);
        assertEquals(s+1, productController.getBarcodesOfNewProductsSize());
        productController.addProduct(882288, "Rollups", 100.00, "Candy", "Ice cream", "Gimic", 5, "Bad guys", 10);
        assertEquals(s, productController.getBarcodesOfNewProductsSize());
    }

    @Test
    public void Test2(){ //need to add generic product to the db
        SuperLiDB superLiDB = SuperLiDB.getInstance();
        int s = superLiDB.getSizeOfOrderFromSuppliers();
        reportController.createOrderReport("yoni");
        reportController.makeOrderForLastReport();
        assertNotEquals(s, superLiDB.getSizeOfOrderFromSuppliers());
    }

    @Test
    public void Test3(){ //need to add generic product to the db
        orderController.enterSupplier("0001");
        orderController.createPeriodicOrder();
        orderController.enterPermanentDay(1);
        orderController.addProductToTheList("001");
        orderController.addQuantityOfTheLastEnteredProduct(250);
        orderController.savePeriodicOrder();

        int s = orderController.getOrderFromSupplier().getProductsInOrder().size();

        orderController.addProductToTheListByBarcode(990002);
        orderController.addQuantityOfTheLastEnteredProduct(100);

        assertEquals(s+1, orderController.getOrderFromSupplier().getProductsInOrder().size());
    }

    @Test
    public void Test4() { //need to add generic product to the db
        orderController.enterSupplier("0001");
        orderController.createPeriodicOrder();
        orderController.enterPermanentDay(1);
        orderController.addProductToTheList("001");
        orderController.addQuantityOfTheLastEnteredProduct(250);
        orderController.savePeriodicOrder();

        orderController.findOrderedProduct(990001);
        assertEquals(250, orderController.getOrderedProduct().getQuantity());

        orderController.findOrderedProduct(990001);
        orderController.changeCurOrderedProductQuantity(300);

        assertEquals(300, orderController.getOrderedProduct().getQuantity());
    }
}

