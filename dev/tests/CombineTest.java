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

    @Test
    public void CheckObserverList(){
        int s = productController.getBarcodesOfNewProductsSize();
        supplierController.addSupplierProduct("Rollups", "Bad guys", 882288, "0001", 10,"111", 50);
        assertEquals(s+1, productController.getBarcodesOfNewProductsSize());
        productController.addProduct(882288, "Rollups", 100.00, "Candy", "Ice cream", "Gimic", 5, "Bad guys", 10);
        assertEquals(s, productController.getBarcodesOfNewProductsSize());
    }

    @Test
    public void CreatAOrderDueToShortage(){
        SuperLiDB superLiDB = SuperLiDB.getInstance();
        int s = superLiDB.getSizeOfOrderFromSuppliers();
        reportController.createOrderReport("yoni");
        reportController.makeOrderForLastReport();
        assertNotEquals(s+1, superLiDB.getSizeOfOrderFromSuppliers());
    }

    @Test
    public void AddToPeriodicOrder(){
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
    public void ChangeOrderedProductQuantityInPeriodicOrder() {
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

