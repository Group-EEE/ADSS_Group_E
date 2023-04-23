package SuppliersModule.Business;

/**
 * SuppliersModule.Business.OrderedProduct class describe a product in a specific order. Including quantity and price.
 */
public class OrderedProduct {

    private static int unique = 1;

    //------------------------------------------ Attributes ---------------------------------------
    private final int Quantity;
    private final float FinalPrice;
    private float Discount;
    private final int Id; // unique orderProduct ID

    //------------------------------------------ References ---------------------------------------
    private SupplierProduct MyProduct;

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public OrderedProduct(int quantity, SupplierProduct myProduct) {
        Quantity = quantity;
        MyProduct = myProduct;
        Discount = myProduct.getDiscountPercentages(quantity);
        FinalPrice = quantity * myProduct.getPrice() * ((100 - Discount)/ 100);
        Id = unique;
        unique++;
    }

    public float getFinalPrice() {return FinalPrice;}
    public int getQuantity() {return Quantity;}

    public String toString() {return  MyProduct + ", quantity: " + Quantity + ", price: " + FinalPrice + "\n";}

    // -------------------------------- Methods related to SuppliersModule.Business.SupplierProduct ------------------------------

    public SupplierProduct getMyProduct() {
        return MyProduct;
    }
}
