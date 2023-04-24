package SuppliersModule.Business;

import SuppliersModule.Business.Manufacturer;
import SuppliersModule.Business.SupplierProduct;

import java.util.ArrayList;
import java.util.List;

/**
A product class that describes a product produced by a manufacturer
*/
public class GenericProduct {

    private static int uniqueBarcode = 990000;

    //------------------------------------------ Attribute ---------------------------------------
    private String Name;

    private int Barcode;

    //------------------------------------------ References ---------------------------------------
    private final Manufacturer MyManufacturer; // The manufacturer of the product
    private List<SupplierProduct> MySuppliersProduct; //All The suppliers that supply this product. Described by a SuppliersModule.Business.SupplierProduct object.

    //--------------------------------------Methods related to This ----------------------------------------

    //Constructor
    public GenericProduct(String name, Manufacturer myManufacturer, int barcode) {

        if(myManufacturer == null) throw new RuntimeException("Product should have a manufacturer");

        Name = name;
        MyManufacturer = myManufacturer;
        myManufacturer.addProduct(this);                        // Add the product to the manufacturer's products.
        MySuppliersProduct = new ArrayList<SupplierProduct>();

        if(barcode == -1){
            uniqueBarcode++;
            barcode = uniqueBarcode;
        }
        Barcode = barcode;


    }

    public String getName() {return Name;}

    public String toString() {return  "Product name: " + Name + ", manufacturer: " + MyManufacturer.getName();}

    // -------------------------------- Methods related to SuppliersModule.Business.Manufacturer ------------------------------

    public Manufacturer getMyManufacturer() {return MyManufacturer;}

    // -------------------------------- Methods related to SuppliersModule.Business.Supplier ------------------------------

    public List<SupplierProduct> getMySuppliersProduct() {return MySuppliersProduct;}

    /**
     * This method used when a certain supplier stops supply this product.
     */
    public void deleteSupplierProduct(SupplierProduct supplierProduct) {
        MySuppliersProduct.remove(supplierProduct);}

    public int getBarcode() {
        return Barcode;
    }
}
