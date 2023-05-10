package SuppliersModule.Business.Controllers;

import DataAccess.SuperLiDB;
import SuppliersModule.Business.*;

import java.util.*;

public class OrderController {

    static OrderController orderController;
    private OrderFromSupplier curOrder;
    private PeriodicOrder curPeriodicOrder;
    private Supplier curSupplier;
    private OrderedProduct curOrderedProduct;
    private SupplierProduct curSupplierProduct;
    private final SupplierController supplierController;
    private SuperLiDB superLiDB;
    Timer timer;

    private OrderController(){
        superLiDB = SuperLiDB.getInstance();
        supplierController = SupplierController.getInstance();
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
        OrderFromSupplier orderFromSupplier;
        for(Map.Entry<Integer, PeriodicOrder> pair : superLiDB.getAllPeriodicOrder().entrySet()){
            if(pair.getValue().getDayForInvite() == curDay) {
                orderFromSupplier = pair.getValue().invite();
                superLiDB.insertOrderFromSupplier(orderFromSupplier);
                System.out.println(orderFromSupplier);
            }
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
        superLiDB.insertPeriodicOrder(curPeriodicOrder);
        superLiDB.insertOrderFromSupplier(curPeriodicOrder.getOrderFromSupplier());
    }

    public boolean findPeriodicOrder(int id){
        curPeriodicOrder = superLiDB.getPeriodicOrder(id);
        return curPeriodicOrder != null;
    }

    public void deleteCurPeriodicOrder(){
        superLiDB.deletePeriodicOrder(curPeriodicOrder.getId());
        curPeriodicOrder.delete();
    }

    public void changeDayForInviteForCurPeriodicOrder(int day){
        curPeriodicOrder.setDayForInvite(day);
    }

    public boolean findOrderedProduct(int barcode){
        curOrderedProduct = curPeriodicOrder.getOrderFromSupplier().getOrderedProductByBarcode(barcode);
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

    public void cancelTimer()
    {
        timer.cancel();
        superLiDB.WriteAllToDB();
    }

    public List<String> findAllPeriodicOrderThatContainThisBarcode(int barcode)
    {
        String dayForInvaite;
        int currId;
        List<String> RelevantList = new ArrayList<>();
        for(Map.Entry<Integer, PeriodicOrder> pair : superLiDB.getAllPeriodicOrder().entrySet()){
            if(pair.getValue().checkIfBarcodeExistInOrder(barcode))
            {
                dayForInvaite = "day for invite " + pair.getValue().dayForInviteToString() + ", ";
                currId = pair.getValue().getId();
                RelevantList.add(dayForInvaite + "Order Id " + currId);
            }
        }

        return RelevantList;
    }

    public List <String> findAllPeriodicOrderThatCanBeContainThisBarcode(int barcode)
    {
        String dayForInvaite;
        int currId;
        List<String> RelevantList = new ArrayList<>();
        for(Map.Entry<Integer, PeriodicOrder> pair : superLiDB.getAllPeriodicOrder().entrySet()){
            if(pair.getValue().getOrderFromSupplier().getMySupplier().CheckIfSupplierCanSupplyByBarcode(barcode))
            {
                dayForInvaite = "day for invite " + pair.getValue().dayForInviteToString() + ", ";
                currId = pair.getValue().getId();
                RelevantList.add(dayForInvaite + "Order Id " + currId);
            }
        }

        return RelevantList;
    }
}
