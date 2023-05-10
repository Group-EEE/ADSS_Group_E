import DataAccess.SuperLiDB;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.PeriodicOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicOrderTest {

    OrderController orderController = OrderController.getInstance();
    SuperLiDB superLiDB = SuperLiDB.getInstance();

    PeriodicOrder periodicOrder;
    @Test
    void CreatPeriodicOrder() {

        int s = superLiDB.getAllPeriodicOrder().size();

        orderController.enterSupplier("0001");
        orderController.createPeriodicOrder();
        orderController.enterPermanentDay(1);
        orderController.addProductToTheList("001");
        orderController.addQuantityOfTheLastEnteredProduct(250);
        orderController.savePeriodicOrder();

        periodicOrder = orderController.getPeriodicOrder();
        assertEquals(s+1, superLiDB.getAllPeriodicOrder().size());
    }
    @Test
    void getOrderFromSupplier()
    {
        orderController.enterSupplier("0001");
        orderController.createPeriodicOrder();
        orderController.enterPermanentDay(1);
        orderController.addProductToTheList("001");
        orderController.addQuantityOfTheLastEnteredProduct(250);
        orderController.savePeriodicOrder();
        periodicOrder = orderController.getPeriodicOrder();
        assertEquals(1, periodicOrder.getDayForInvite());
    }

    @Test
    void getSupplierNum()
    {
        orderController.enterSupplier("0001");
        orderController.createPeriodicOrder();
        orderController.enterPermanentDay(1);
        orderController.addProductToTheList("001");
        orderController.addQuantityOfTheLastEnteredProduct(250);
        orderController.savePeriodicOrder();
        periodicOrder = orderController.getPeriodicOrder();
        assertEquals(periodicOrder.getOrderFromSupplier().getMySupplier().getSupplierNum(), "0001");
    }
}