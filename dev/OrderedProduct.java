public class OrderedProduct {

    //------------------------------------------ Attributes ---------------------------------------
    private final int Quantity;
    private final float FinalPrice;
    private float Discount;

    //------------------------------------------ References ---------------------------------------
    private SupplierProduct MyProduct;

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public OrderedProduct(int quantity, SupplierProduct myProduct) {
        Quantity = quantity;
        MyProduct = myProduct;
        Discount = myProduct.getDiscountPercentages(quantity);
        FinalPrice = quantity * myProduct.getPrice() * ((100 - Discount)/ 100);
    }

    public float getFinalPrice() {return FinalPrice;}
    public int getQuantity() {return Quantity;}

    public String toString() {return  MyProduct + ", quantity: " + Quantity + ", price: " + FinalPrice + "\n";}

    // -------------------------------- Methods related to SupplierProduct ------------------------------

    public SupplierProduct getMyProduct() {
        return MyProduct;
    }
}
