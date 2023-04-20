import java.util.*;

/**
 * SupplierProduct class describe a certain product that supply by certain supplier in the agreement.
 */
public class SupplierProduct {

    //------------------------------------------ Attributes ---------------------------------------
    private float Price; // Price per unit
    private String SupplierCatalog; // product catalog number given by the supplier
    private int Amount; // The minimum amount of units that can supplied in one order.

    //------------------------------------------ References ---------------------------------------

    private Supplier MySupplier; // The supplier of this product
    private Product MyProduct; // The product object
    private Agreement MyAgreement; // The agreement that contains this supplier's product.
    private TreeMap<Integer, ProductDiscount> DiscountProducts; // Discounts provided for this product in the agreement

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public SupplierProduct(float price, String supplierCatalog, int amount, Supplier supplier, Product product, Agreement agreement)
    {
        Price = price;
        SupplierCatalog = supplierCatalog;
        Amount = amount;
        MySupplier = supplier;
        MyProduct = product;
        MySupplier.addNewProduct(this);                                //Add the product to the products provided by the supplier.
        MyProduct.getMySuppliersProduct().add(this);                   //Link the product provided by the supplier to the product
        MyAgreement = agreement;
        agreement.addProductToTheAgreement(this);         //Add the product to the agreement.
        DiscountProducts = new TreeMap<Integer, ProductDiscount>();
    }

    public float getPrice() {return Price;}
    public String getSupplierCatalog() {return SupplierCatalog;}
    public int getAmount() {return Amount;}

    public String toString()
    {
        return  MyProduct + ", catalog number: " + SupplierCatalog;
    }

    /**
     * This method used when the supplier is no longer supply this product to us
     */
    public void delete()
    {
        MyProduct.deleteSupplierProduct(this);          //The supplier no longer supplies the product
        MySupplier.deleteSupplierProduct(this);         //The product is no longer supplied by this supplier
    }

    // --------------------------------- Methods related to Supplier ------------------------------

    public Supplier getMySupplier() {return MySupplier;}


    // --------------------------------- Methods related to Product ------------------------------

    public Product getMyProduct() {return MyProduct;}


    // --------------------------------- Methods related to ProductDiscount ------------------------------

    public void addProductDiscount(float discountPercentages, int minimumAmount){

        ProductDiscount discountProduct = new ProductDiscount(discountPercentages,minimumAmount);

        if(DiscountProducts.containsKey(minimumAmount))
            System.out.println("The minimum Amount is exist");

        DiscountProducts.put(minimumAmount, discountProduct);
    }

    /**
     * Calculate the discount we will receive if we buy X units of this product
     */
    public float getDiscountPercentages(int quantity){
        Map.Entry<Integer, ProductDiscount> pair = DiscountProducts.floorEntry(quantity);   //The maximum discount I will receive
        if(pair != null)
            return pair.getValue().getDiscountPercentages();
        return 0;
    }

    /**
     * delete the discount with the given minimumAmount
     */
    public void deleteDiscountProduct(int minimumAmount)
    {
        if(DiscountProducts.remove(minimumAmount) == null)
            System.out.println("The discount is not exist\n");
        else
            System.out.println("The discount has been deleted\n");
    }
}
