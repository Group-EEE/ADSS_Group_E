package SuppliersModule.Business;

/**
 * ProductDiscount class describe a discount that related to a specific product
 */
public class SupplierProductDiscount {

    //------------------------------------------ Attributes ---------------------------------------
    private final float Percentages;
    private final int MinimumAmount; // The minimum amount (units/kilos) needed to get this discount

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public SupplierProductDiscount(float percentages, int minimumAmount) {
        Percentages = percentages;
        MinimumAmount = minimumAmount;
    }
    public float getPercentages() {
        return Percentages;
    }

    public int getMinimumAmount() {
        return MinimumAmount;
    }
}
