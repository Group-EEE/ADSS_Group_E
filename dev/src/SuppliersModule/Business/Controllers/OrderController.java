package SuppliersModule.Business.Controllers;

import DataAccess.SuperLiDB;
import SuppliersModule.Business.*;
import java.util.*;

/**
 * controller of all Orders.
 */
public class OrderController {

    //------------------------------------------ Attributes ---------------------------------------
    static OrderController orderController;
    private OrderFromSupplier curOrder;
    private PeriodicOrder curPeriodicOrder;
    private Supplier curSupplier;
    private OrderedProduct curOrderedProduct;
    private SupplierProduct curSupplierProduct;
    private final SupplierController supplierController;
    private SuperLiDB superLiDB;
    Timer timer;


    /**
     * Singleton constructor
     */
    private OrderController(){
        superLiDB = SuperLiDB.getInstance();
        supplierController = SupplierController.getInstance();
        taskTimer();
    }

    /**
     * Get instance
     * @return - OrderController
     */
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

    /**
     * The method that timer performs every 11:00
     * @param curDay - yhe current day.
     */
    public void invitePeriodicOrders(int curDay){
        OrderFromSupplier orderFromSupplier;
        for(Map.Entry<List<Integer>, PeriodicOrder> pair : superLiDB.getAllPeriodicOrder().entrySet()){
            if(pair.getValue().getDayForInvite() == curDay) {
                orderFromSupplier = pair.getValue().invite();
                superLiDB.insertOrderFromSupplier(orderFromSupplier);
                System.out.println(orderFromSupplier);
            }
        }
    }

    /**
     * Check if a change can be made to the order
     * @return boolean
     */
    public boolean checkInvalidDayForChange(){
        Calendar.getInstance();
        int curDay = Calendar.DAY_OF_WEEK;
        return curDay == curPeriodicOrder.getDayForInvite();
    }

    /**
     * Enter supplier
     * @param supplerNum - number of supplier
     * @return bool if existed.
     */
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

    public boolean findPeriodicOrder(int barcode, int id){
        curPeriodicOrder = superLiDB.getPeriodicOrder(id);
        if(curPeriodicOrder == null)
            return false;
        curOrder = curPeriodicOrder.getOrderFromSupplier();
        curSupplier = curOrder.getMySupplier();
        curSupplierProduct = curSupplier.getSupplierProductByBarcode(barcode);
        return true;
    }

    public void deleteCurPeriodicOrder(){
        superLiDB.deletePeriodicOrder(curPeriodicOrder.getId());
        curPeriodicOrder.delete();
    }

    public void changeDayForInviteForCurPeriodicOrder(int day){
        curPeriodicOrder.setDayForInvite(day);
    }

    public boolean findOrderedProduct(int barcode){
        curOrderedProduct = curOrder.getOrderedProductByBarcode(barcode);
        return curOrderedProduct != null;
    }

    public void deleteOrderedProduct(int barcode){
        curOrder.removeOrderedProductByBarcode(barcode);
    }

    public void changeCurOrderedProductQuantity(int quantity){
        curOrder.setQuantityForProductInOrder(curOrderedProduct, quantity);
    }

    public void cancelTimer()
    {
        timer.cancel();
        superLiDB.WriteAllToDB();
    }

    /**
     * Find all periodic order that contain this barcode
     * @param barcode - desired barcode
     * @return All periodic order
     */
    public List<String> findAllPeriodicOrderThatContainThisBarcode(int barcode)
    {
        String dayForInvaite;
        int currId;
        List<String> RelevantList = new ArrayList<>();
        for(Map.Entry<List<Integer>, PeriodicOrder> pair : superLiDB.getAllPeriodicOrder().entrySet()){
            if(pair.getValue().checkIfBarcodeExistInOrder(barcode))
            {
                dayForInvaite = "day for invite " + pair.getValue().dayForInviteToString() + ", ";
                currId = pair.getValue().getId();
                RelevantList.add(dayForInvaite + "Order Id " + currId);
            }
        }

        return RelevantList;
    }

    /**
     * Find all periodic order that can be contained in this barcode
     * @param barcode - desired barcode
     * @return All periodic order
     */
    public List <String> findAllPeriodicOrderThatCanBeContainThisBarcode(int barcode)
    {
        String dayForInvaite;
        int currId;
        List<String> RelevantList = new ArrayList<>();
        for(Map.Entry<List<Integer>, PeriodicOrder> pair : superLiDB.getAllPeriodicOrder().entrySet()){
            if(pair.getValue().getOrderFromSupplier().getMySupplier().CheckIfSupplierCanSupplyByBarcode(barcode)
            && pair.getValue().getOrderFromSupplier().getOrderedProductByBarcode(barcode) == null)
            {
                dayForInvaite = "day for invite " + pair.getValue().dayForInviteToString() + ", ";
                currId = pair.getValue().getId();
                RelevantList.add(dayForInvaite + "Order Id " + currId);
            }
        }

        return RelevantList;
    }

    public boolean CheckIfSupplierCanSupplyThisQuantity(int quantity)
    {
        return quantity <= curSupplierProduct.getAmount();
    }

    public void addProductToTheListByBarcode(int barcode)
    {
        curSupplierProduct = curSupplier.getSupplierProductByBarcode(barcode);
    }

    public OrderFromSupplier getOrderFromSupplier()
    {
        return curOrder;
    }

    public OrderedProduct getOrderedProduct()
    {
        return curOrderedProduct;
    }

    public PeriodicOrder getPeriodicOrder ()
    {
        return curPeriodicOrder;
    }
}
