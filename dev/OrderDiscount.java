public class OrderDiscount {

    //------------------------------------------ Attributes ---------------------------------------

    private String DiscountByPriceOrQuantity;
    private int amount;             //Can be amounted of money or amount of products
    private float discount;

    //------------------------------------------ References ---------------------------------------
    private Agreement myAgreement;

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


    //Calculate the price on the specific order after applying the total discount
    public float getPriceAfterDiscount(Order order)
    {
        if(DiscountByPriceOrQuantity.equals("p"))       //If by products
        {
            if(order.getPriceBeforeTotalDiscount() >= amount)
                return order.getPriceBeforeTotalDiscount()*(100-discount)/100;
        }
        else {                                          //If by money
            if(order.getQuantity() >= amount)
                return order.getPriceBeforeTotalDiscount()*(100-discount)/100;
        }
        return order.getPriceBeforeTotalDiscount();
    }

    public String getDiscountByPriceOrQuantity() {
        return DiscountByPriceOrQuantity;
    }
}


