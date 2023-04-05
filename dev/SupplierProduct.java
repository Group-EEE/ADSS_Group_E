import java.util.*;

public class SupplierProduct {

    //------------------------------------------ Attributes ---------------------------------------
    private float Price;
    private String SupplierCatalog;
    private int Amount;

    //------------------------------------------ References ---------------------------------------

    private Supplier MySupplier;
    private Product MyProduct;
    private Agreement MyAgreement;
    private TreeMap<Integer, ProductDiscount> DiscountProducts;

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

    public void delete()
    {
        MyProduct.deleteSupplierProduct(this);          //The supplier no longer supplies the product
        MySupplier.deleteSupplierProduct(this);
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

    //Calculate the discount I will receive if I buy X units of this product
    public float getDiscountPercentages(int quantity){
        Map.Entry<Integer, ProductDiscount> pair = DiscountProducts.floorEntry(quantity);   //The maximum discount I will receive
        if(pair != null)
            return pair.getValue().getDiscountPercentages();
        return 0;
    }

    public void deleteDiscountProduct(int minimumAmount)
    {
        if(DiscountProducts.remove(minimumAmount) == null)
            System.out.println("The discount is not exist\n");
    }
}
