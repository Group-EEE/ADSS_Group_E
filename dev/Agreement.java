import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Agreement class describing the agreement signed by the supplier
*/
public class Agreement {

    //------------------------------------------ Attributes ---------------------------------------

    private boolean HasPermanentDays;
    private boolean IsSupplierBringProduct;
    private boolean[] DeliveryDays;
    private int NumberOfDaysToSupply;

    //------------------------------------------ References ---------------------------------------

    private Map<String ,SupplierProduct> ProductInAgreement;
    private Supplier MySupplier;
    private List<OrderDiscount> DiscountOnOrder;

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public Agreement(boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply, Supplier supplier)
    {
        if(supplier == null) throw new RuntimeException("Agreement should have a supplier");

        HasPermanentDays = hasPermanentDays;
        IsSupplierBringProduct = isSupplierBringProduct;
        DeliveryDays = deliveryDays;
        NumberOfDaysToSupply = numberOdDaysToSupply;
        DiscountOnOrder = new ArrayList<OrderDiscount>();
        MySupplier = supplier;
        ProductInAgreement = new HashMap<String ,SupplierProduct>();
    }

    public void setDetails(boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply)
    {
        HasPermanentDays = hasPermanentDays;
        IsSupplierBringProduct = isSupplierBringProduct;
        DeliveryDays = deliveryDays;
        NumberOfDaysToSupply = numberOdDaysToSupply;
    }

    // -------------------------------- Methods related to SupplierProduct ------------------------------

    public void addProductToTheAgreement(SupplierProduct supplierProduct){
        ProductInAgreement.put(supplierProduct.getSupplierCatalog() ,supplierProduct);
    }

    public void deleteProductFromTheAgreement(SupplierProduct supplierProduct)
    {
        supplierProduct.delete();  // Remove the supplier product.
        ProductInAgreement.remove(supplierProduct.getSupplierCatalog());
    }

    // -------------------------------- Methods related to OrderDiscount ------------------------------
    public void addOrderDiscount(String discountByPriceOrProducts, int amount, float discount){
        DiscountOnOrder.add(new OrderDiscount(discountByPriceOrProducts, amount, discount, this));
    }

    public List<OrderDiscount> getDiscountOnOrder() {
        return DiscountOnOrder;
    }

    public boolean CheckIfExistOrderDiscount(String priceOrQuantity, float discountPercentages, int minimumAmount)
    {
        for(OrderDiscount orderDiscount : DiscountOnOrder)
        {
            if(orderDiscount.getDiscount() == discountPercentages && orderDiscount.getAmount() == minimumAmount
            && orderDiscount.getDiscountByPriceOrQuantity().equals(priceOrQuantity)) {
                System.out.println("The discount is exist\n");
                return true;
            }
        }
        return false;
    }

    public void deleteOrderDiscount(String priceOrQuantity, float discountPercentages, int minimumAmount)
    {
        for(OrderDiscount orderDiscount : DiscountOnOrder)
        {
            if(orderDiscount.getDiscount() == discountPercentages && orderDiscount.getAmount() == minimumAmount
                    && orderDiscount.getDiscountByPriceOrQuantity().equals(priceOrQuantity))
                DiscountOnOrder.remove(orderDiscount);
            break;
        }
        System.out.println("The discount is not exist\n");
    }
}
