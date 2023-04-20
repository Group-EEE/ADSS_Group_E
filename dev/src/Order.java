import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {
    float TotalPrice; // The price of the cheapest combination of orders from suppliers

    Map<Supplier, OrderFromSupplier> OrdersFromSuppliers; // The cheapest combination of orders from suppliers

    List<GenericProduct> ProductsInOrder ; // contain all the products in the order

    List<Integer> ProductsQuantity ; // contain the quantity of the products found in the ProductsInOrder list. ProductsQuantity[n] = ProductsInOrder[n] -> quantity

    Date OrderDate;

    private static int unique = 1;

    private final int Id; // unique order ID

    public Order(float totalPrice, Map<Supplier, OrderFromSupplier> ordersFromSuppliers, List<GenericProduct> productsInOrder, List<Integer> productsQuantity) {
        TotalPrice = totalPrice;
        OrdersFromSuppliers = ordersFromSuppliers;
        ProductsInOrder = productsInOrder;
        ProductsQuantity = productsQuantity;
        OrderDate = new Date();
        Id = unique;
        unique++;
    }

    @Override
    public String toString() {
        String s = "";
        s += "\nThe order has been committed\n";
        for (Map.Entry<Supplier, OrderFromSupplier> pair : OrdersFromSuppliers.entrySet())
            s += pair.getValue().toString();

        s += "Final price: " + TotalPrice;
        return s;
    }

    public float getTotalPrice() {
        return TotalPrice;
    }

    public Map<Supplier, OrderFromSupplier> getOrdersFromSuppliers() {
        return OrdersFromSuppliers;
    }

    public List<GenericProduct> getProductsInOrder() {
        return ProductsInOrder;
    }

    public List<Integer> getProductsQuantity() {
        return ProductsQuantity;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public int getId() {
        return Id;
    }
}
