/**
 * Discount given for the final order. Can be for minimum price or minimum quantity.
 */
public class OrderDiscount {

    //------------------------------------------ Attributes ---------------------------------------

    private String DiscountByPriceOrQuantity; // Describes whether the discount is getting for minimum price or minimum quantity
    private int amount;             //Can be amounted of money or amount of products
    private float discount; // The amount of the discount percentage

    //------------------------------------------ References ---------------------------------------
    private Agreement myAgreement; // The agreement related to this discount

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public OrderDiscount(String discountByPriceOrQuantity, int amount, float discount, Agreement myAgreement) {
        DiscountByPriceOrQuantity = discountByPriceOrQuantity;
        this.amount = amount;
        this.discount = discount;
        this.myAgreement = myAgreement;
    }

    public int getAmount() {return amount;}
    public float getDiscount() {return discount;}


    /**
     * Calculate the price on the specific order after applying the total discount
     */
    public float getPriceAfterDiscount(OrderFromSupplier orderFromSupplier)
    {
        if(DiscountByPriceOrQuantity.equals("p"))       //If by products
        {
            if(orderFromSupplier.getPriceBeforeTotalDiscount() >= amount)
                return orderFromSupplier.getPriceBeforeTotalDiscount()*(100-discount)/100;
        }
        else {                                          //If by money
            if(orderFromSupplier.getQuantity() >= amount)
                return orderFromSupplier.getPriceBeforeTotalDiscount()*(100-discount)/100;
        }
        return orderFromSupplier.getPriceBeforeTotalDiscount();
    }

    public String getDiscountByPriceOrQuantity() {
        return DiscountByPriceOrQuantity;
    }
}


