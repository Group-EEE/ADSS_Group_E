public class ProductDiscount {

    //------------------------------------------ Attributes ---------------------------------------
    private float DiscountPercentages;
    private int MinimumAmount;

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public ProductDiscount(float discountPercentages, int minimumAmount) {
        DiscountPercentages = discountPercentages;
        MinimumAmount = minimumAmount;
    }
    public float getDiscountPercentages() {
        return DiscountPercentages;
    }

}
