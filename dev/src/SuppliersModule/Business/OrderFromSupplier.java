package SuppliersModule.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * SuppliersModule.Business.Order class describe an order for one supplier
 */
public class OrderFromSupplier {

    private static int unique = 1; // Every new order that open gets a unique id. For every new open the unique variable is incremented by one.

    //------------------------------------------ Attributes ---------------------------------------
    private final int Id; // unique order ID
    private int Quantity; // The quantity of products in the order

    private float priceBeforeTotalDiscount; // price after calculate the product discount, but before calculate the total SuppliersModule.Business.Order Discount

    int dayForPeriodicOrder; // The day in the week that the supplier gets the order from the store

    //------------------------------------------ References ---------------------------------------

    private Supplier MySupplier; // The SuppliersModule.Business.Supplier that supply the product for the order
    private List<OrderedProduct> ProductsInOrder; // All the product in the order with their quantity.

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public OrderFromSupplier(Supplier supplier) {

        MySupplier = supplier;
        Id = unique;
        unique++;
        ProductsInOrder = new ArrayList<>();
        priceBeforeTotalDiscount = (float) 0;
        dayForPeriodicOrder = 0;
    }

    /**
     * Calculates the order price based on the best total discount. (including SuppliersModule.Business.OrderDiscount)
     */
    public float getTotalPriceAfterDiscount(){

        List<OrderDiscount> orderDiscount = MySupplier.getMyAgreement().getDiscountOnOrder();
        float bestPrice = priceBeforeTotalDiscount;
        float currPrice;
        for(OrderDiscount od: orderDiscount){
            currPrice = od.getPriceAfterDiscount(this);
            if(currPrice < bestPrice)                           //If a better discount is found
                bestPrice = currPrice;
        }

        return bestPrice;
    }
    /**
     * return price before total discount
     */
    public float getPriceBeforeTotalDiscount(){
        return priceBeforeTotalDiscount;
    }

    public int getQuantity() {
        return Quantity;
    }


    /**
     *Link the order to the appropriate supplier and print it.
     */
    public void invite(){
        MySupplier.addNewOrder(this);
    }

    public String toString()
    {
        String details = "\n";
        details += "SuppliersModule.Business.Order number " + Id + " from supplier " + MySupplier.getName() + ", supplier number: " + MySupplier.getSupplierNum() + "\n";
        for (OrderedProduct orderedProduct : ProductsInOrder)
            details += orderedProduct;
        details += "\nTotal order price after discount: " + getTotalPriceAfterDiscount() + "\n";
        details += "\nContacts:\n";
        details += MySupplier.stringContacts();
        return details;
    }

    /**
     * Return new SuppliersModule.Business.Order object with the same products. The product are being shallow copied
     */
    public OrderFromSupplier clone() {
        OrderFromSupplier orderFromSupplierCopy = new OrderFromSupplier(MySupplier);
        for(OrderedProduct orderedProduct : ProductsInOrder)
            orderFromSupplierCopy.addProductToOrder(orderedProduct.getQuantity(),orderedProduct.getMyProduct());
        return orderFromSupplierCopy;
    }

    //-----------------------------------Methods related to SuppliersModule.Business.OrderedProduct -------------------------------------------

    public void addProductToOrder(int quantity, SupplierProduct supplierProduct)
    {
        OrderedProduct orderedProduct = new OrderedProduct(quantity, supplierProduct);
        ProductsInOrder.add(orderedProduct);
        Quantity += quantity;
        priceBeforeTotalDiscount += orderedProduct.getFinalPrice();     //Calculate price before total discount
    }

    /**
     * Return list of all the product in the order, including quantity and price
     */
    public List<OrderedProduct> getProductsInOrder() {return ProductsInOrder;}

    public void setDayForPeriodicOrder(int dayForPeriodicOrder) {
        this.dayForPeriodicOrder = dayForPeriodicOrder;
    }

    public int getId() {
        return Id;
    }

    public int getDayForPeriodicOrder() {
        return dayForPeriodicOrder;
    }

    public Supplier getMySupplier() {
        return MySupplier;
    }
}

