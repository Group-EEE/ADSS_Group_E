package SuppliersModule.Business.Controllers;

import SuppliersModule.Business.*;
import SuppliersModule.Business.Generator.OrderGenerator;

import java.util.*;

public class OrderController {

    static OrderController orderController;
    private final Map<Integer,PeriodicOrder> periodicOrders;

    private OrderFromSupplier curOrder;

    private PeriodicOrder curPeriodicOrder;

    private Supplier curSupplier;

    private OrderedProduct curOrderedProduct;

    private SupplierProduct curSupplierProduct;

    private final SupplierController supplierController;
    Timer timer;

    private OrderController(){
        supplierController = SupplierController.getInstance();
        periodicOrders = new HashMap<>();
        taskTimer();
    }

    public static OrderController getInstance(){
        if(orderController == null)
            orderController = new OrderController();
        return orderController;
    }

    /**
     * This function scheduale a task to execute every day in 11 AM
     */
    public void taskTimer() {
        timer = new Timer();
        TimerTask dailyTask = new TimerTask() {
            @Override
            public void run() {
                Calendar.getInstance();
                int curDay = Calendar.DAY_OF_WEEK;
                invitePeriodicOrders(curDay);
            }
        };

        // Set the time to start executing the task
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 11);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        // Schedule the task to run every day at 11 AM
        timer.schedule(dailyTask, today.getTime(), 24 * 60 * 60 * 1000); // 24 * 60 * 60 * 1000 is the number of milliseconds a day
    }

    public void invitePeriodicOrders(int curDay){
        for(Map.Entry<Integer, PeriodicOrder> pair : periodicOrders.entrySet()){
            if(pair.getValue().getDayForInvite() == curDay)
                System.out.println(pair.getValue().invite());
        }
    }

    public boolean checkInvalidDayForChange(){
        Calendar.getInstance();
        int curDay = Calendar.DAY_OF_WEEK;
        return curDay == curPeriodicOrder.getDayForInvite();
    }

    public boolean enterSupplier(String supplerNum){
        curSupplier = supplierController.getSupplier(supplerNum);
        return curSupplier != null;
    }

    public void createPeriodicOrder(){
        curOrder = new OrderFromSupplier(curSupplier);
    }

    public void enterPermanentDay(int day){
        curPeriodicOrder = new PeriodicOrder(curOrder, day);
    }

    public boolean addProductToTheList(String catalogNum){
        curSupplierProduct = curSupplier.getSupplierProduct(catalogNum);
        return curSupplierProduct != null;
    }


    public void addQuantityOfTheLastEnteredProduct(int quantity){
        curOrder.addProductToOrder(quantity,curSupplierProduct);
    }

    public void savePeriodicOrder(){
        periodicOrders.put(curPeriodicOrder.getDayForInvite(),curPeriodicOrder);
    }

    public boolean findPeriodicOrder(int id){
        curPeriodicOrder = periodicOrders.get(id);
        return curPeriodicOrder != null;
    }

    public void deleteCurPeriodicOrder(){
        periodicOrders.remove(curPeriodicOrder.getId());
        curPeriodicOrder.delete();
    }

    public void changeDayForInviteForCurPeriodicOrder(int day){
        curPeriodicOrder.setDayForInvite(day);
    }

    public boolean findOrderedProduct(String catalogNum){
        curOrderedProduct = curPeriodicOrder.getOrderFromSupplier().getOrderedProduct(catalogNum);
        return curOrderedProduct != null;
    }

    public void deleteOrderedProduct(String catalogNum){
        curOrder.removeOrderedProduct(catalogNum);
    }

    public void setCurOrder(){
        curOrder = curPeriodicOrder.getOrderFromSupplier();
    }

    public void changeCurOrderedProductQuantity(int quantity){
        curOrderedProduct.setQuantity(quantity);
    }

    public void cancelTimer() {timer.cancel();}
}
