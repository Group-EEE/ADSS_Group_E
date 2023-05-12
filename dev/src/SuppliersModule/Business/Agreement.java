package SuppliersModule.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
SuppliersModule.Business.Agreement class describing the agreement signed by the supplier
*/
public class Agreement {

    //------------------------------------------ Attributes ---------------------------------------

    private boolean HasPermanentDays; // Describes whether the supplier has permanent delivery days
    private boolean IsSupplierBringProduct;
    private boolean[] DeliveryDays; // 7 size array, describe the permanent delivery days of the supplier
    private int NumberOfDaysToSupply; // Describe how many days after the order has been committed, should the supplier supply the product.

    //------------------------------------------ References ---------------------------------------

    private Map<String , SupplierProduct> ProductInAgreement; // All the product supplied in the agreement
    private Supplier MySupplier;
    private List<OrderDiscount> DiscountOnOrder; // List of the total discounts provided by the supplier. (only discount for the order, not included discounts for specific product)

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public Agreement(boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply, Supplier supplier)
    {
        HasPermanentDays = hasPermanentDays;
        IsSupplierBringProduct = isSupplierBringProduct;
        DeliveryDays = deliveryDays;
        NumberOfDaysToSupply = numberOdDaysToSupply;
        DiscountOnOrder = new ArrayList<OrderDiscount>();
        MySupplier = supplier;
        ProductInAgreement = new HashMap<String , SupplierProduct>();
    }

    /**
     * Setting the details of the supplier
     */

    public void setDetails(boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply)
    {
        HasPermanentDays = hasPermanentDays;
        IsSupplierBringProduct = isSupplierBringProduct;
        DeliveryDays = deliveryDays;
        NumberOfDaysToSupply = numberOdDaysToSupply;
    }

    // -------------------------------- Methods related to SuppliersModule.Business.SupplierProduct ------------------------------

    public void addProductToTheAgreement(SupplierProduct supplierProduct){
        ProductInAgreement.put(supplierProduct.getSupplierCatalog() ,supplierProduct);
    }

    public void deleteProductFromTheAgreement(SupplierProduct supplierProduct)
    {
        supplierProduct.delete();  // Remove the supplier product.
        ProductInAgreement.remove(supplierProduct.getSupplierCatalog());
    }

    // -------------------------------- Methods related to SuppliersModule.Business.OrderDiscount ------------------------------
    public void addOrderDiscount(String discountByPriceOrProducts, int amount, float discount){
        DiscountOnOrder.add(new OrderDiscount(discountByPriceOrProducts, amount, discount, this));
    }

    public List<OrderDiscount> getDiscountOnOrder() {
        return DiscountOnOrder;
    }

    /**
     * finding if there is an order discount with the given details.
     * @param priceOrQuantity : Describes whether the discount is getting for minimum price or minimum quantity
     * @param minimumAmount the minimum quantity or price needed to get the discount
     * @return
     */
    public boolean CheckIfExistOrderDiscount(String priceOrQuantity, int minimumAmount)
    {
        for(OrderDiscount orderDiscount : DiscountOnOrder)
        {
            if(orderDiscount.getAmount() == minimumAmount && orderDiscount.getByPriceOrQuantity().equals(priceOrQuantity)) {
                System.out.println("The discount is exist\n");
                return true;
            }
        }
        return false;
    }

    /**
     * finding the discount with the given details and delete it (Only for OrderDiscounts not for product discounts)
     */

    public void deleteOrderDiscount(String priceOrQuantity,int minimumAmount)
    {
        for(OrderDiscount orderDiscount : DiscountOnOrder)
        {
            if(orderDiscount.getAmount() == minimumAmount
                    && orderDiscount.getByPriceOrQuantity().equals(priceOrQuantity)) {
                DiscountOnOrder.remove(orderDiscount);
                System.out.println("The discount has been deleted\n");
                return;
            }
        }
        System.out.println("The discount is not exist\n");
    }

    public Map<String, SupplierProduct> getProductInAgreement() {return ProductInAgreement;}

    public Supplier getMySupplier() {
        return MySupplier;
    }

    public boolean supplierHasPermanentDays() {
        return HasPermanentDays;
    }

    public boolean isSupplierBringProduct() {
        return IsSupplierBringProduct;
    }

    public boolean[] getDeliveryDays() {
        return DeliveryDays;
    }

    public int getNumberOfDaysToSupply() {
        return NumberOfDaysToSupply;
    }

    public void setMySupplier(Supplier mySupplier) {MySupplier = mySupplier;}

    public void setDiscountOnOrder(List<OrderDiscount> discountOnOrder) {
        DiscountOnOrder = discountOnOrder;
    }

    public OrderDiscount getOrderDiscount(String byPriceOrQuantity, int amount)
    {
        for(OrderDiscount orderDiscount : DiscountOnOrder){
            if(orderDiscount.getByPriceOrQuantity().equals(byPriceOrQuantity) && orderDiscount.getAmount() == amount)
                return orderDiscount;
        }
        return null;
    }
}


