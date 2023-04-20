/**
 * ProductDiscount class describe a discount that related to a specific product
 */
public class ProductDiscount {

    //------------------------------------------ Attributes ---------------------------------------
    private final float DiscountPercentages;
    private final int MinimumAmount; // The minimum amount (units/kilos) needed to get this discount

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public ProductDiscount(float discountPercentages, int minimumAmount) {
        DiscountPercentages = discountPercentages;
        MinimumAmount = minimumAmount;
    }
    public float getDiscountPercentages() {
        return DiscountPercentages;
    }

    public int getMinimumAmount() {
        return MinimumAmount;
    }
}
