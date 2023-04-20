import java.util.HashMap;
import java.util.Map;

public class OrderController {

    static OrderController orderController;

    Map<Integer, Order> OrderHistory;

    private OrderController(){
        OrderHistory = new HashMap<>();
    }

    public static OrderController getInstance(){
        if(orderController == null)
            orderController = new OrderController();
        return orderController;
    }

    public void addOrder(Order order){
        OrderHistory.put(order.getId(),order);
    }
}
