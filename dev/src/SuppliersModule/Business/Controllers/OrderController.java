package SuppliersModule.Business.Controllers;

import SuppliersModule.Business.*;
import SuppliersModule.Business.Generator.OrderGenerator;

import java.util.HashMap;
import java.util.Map;

public class OrderController {

    static OrderController orderController;

    OrderFromSupplier curOrder;

    Supplier curSupplier;

    SupplierProduct curSupplierProduct;

    SupplierController supplierController;

    Map<Integer, Order> OrderHistory;

    private OrderController(){
        OrderHistory = new HashMap<>();
        supplierController = SupplierController.getInstance();
    }

    public static OrderController getInstance(){
        if(orderController == null)
            orderController = new OrderController();
        return orderController;
    }

    public void addOrder(Order order){
        OrderHistory.put(order.getId(),order);
    }

    public boolean enterSupplier(String supplerNum){
        curSupplier = supplierController.getSupplier(supplerNum);
        return curSupplier != null;
    }

    public void createPeriodicOrder(){
        curOrder = new OrderFromSupplier(curSupplier);
    }

    public void enterPermanentDay(int day){
        curOrder.setDayForPeriodicOrder(day);
    }

    public boolean addProductToTheList(String catalogNum){
        curSupplierProduct = curSupplier.getSupplierProduct(catalogNum);
        return curSupplierProduct != null;
    }


    public void addQuantityOfTheLastEnteredProduct(int quantity){
        curOrder.addProductToOrder(quantity,curSupplierProduct);
    }

    public void savePeriodicOrder(){

    }
}
