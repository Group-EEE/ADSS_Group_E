import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int unique = 1;

    //------------------------------------------ Attributes ---------------------------------------
    private int Id;
    private int Quantity;

    private Float priceBeforeTotalDiscount;

    //------------------------------------------ References ---------------------------------------

    private Supplier MySupplier;
    private List<OrderedProduct> ProductsInOrder;

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public Order(Supplier supplier) {

        MySupplier = supplier;
        Id = unique;
        unique++;
        ProductsInOrder = new ArrayList<OrderedProduct>();
        priceBeforeTotalDiscount = (float) 0;
    }

    //Calculates the order price based on the best total discount
    public float getTotalPrice(){

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

    // Price before total discount
    public float getPriceBeforeTotalDiscount(){
        return priceBeforeTotalDiscount;
    }

    public int getQuantity() {
        return Quantity;
    }


    //Link the order to the appropriate supplier and print it.
    public void invite(){
        MySupplier.addNewOrder(this);
        System.out.println(this);
    }

    public String toString()
    {
        String details = "\n";
        details += "Order number " + Id + " from supplier " + MySupplier.getName() + ", supplier number: " + MySupplier.getSupplierNum() + "\n";
        for (OrderedProduct orderedProduct : ProductsInOrder)
            details += orderedProduct;
        details += "\nTotal order price after discount: " + getTotalPrice() + "\n";
        details += "\nContacts:\n";
        details += MySupplier.stringContacts();
        return details;
    }

    public Order clone() {
        Order orderCopy = new Order(MySupplier);
        for(OrderedProduct orderedProduct : ProductsInOrder)
            orderCopy.addProductToOrder(orderedProduct.getQuantity(),orderedProduct.getMyProduct());
        return orderCopy;
    }

    //-----------------------------------Methods related to OrderedProduct -------------------------------------------

    public void addProductToOrder(int quantity, SupplierProduct supplierProduct)
    {
        OrderedProduct orderedProduct = new OrderedProduct(quantity, supplierProduct);
        ProductsInOrder.add(orderedProduct);
        Quantity += quantity;
        priceBeforeTotalDiscount += orderedProduct.getFinalPrice();     //Calculate price before total discount
    }

    public List<OrderedProduct> getProductsInOrder() {return ProductsInOrder;}
}

