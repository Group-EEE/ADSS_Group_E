import java.util.ArrayList;
import java.util.List;

/*
A product class that describes a product produced by a manufacturer
*/
public class Product {

    //------------------------------------------ Attribute ---------------------------------------
    private String Name;

    //------------------------------------------ References ---------------------------------------
    private Manufacturer MyManufacturer;
    private List<SupplierProduct> MySuppliersProduct;

    //--------------------------------------Methods related to This ----------------------------------------

    //Constructor
    public Product(String name, Manufacturer myManufacturer) {

        if(myManufacturer == null) throw new RuntimeException("Product should have a manufacturer");

        Name = name;
        MyManufacturer = myManufacturer;
        myManufacturer.addProduct(this);                        // Add the product to the manufacturer's products.
        MySuppliersProduct = new ArrayList<SupplierProduct>();
    }

    public String getName() {return Name;}

    public String toString() {return  "Product name: " + Name + ", manufacturer: " + MyManufacturer.getName();}

    // -------------------------------- Methods related to Manufacturer ------------------------------

    public Manufacturer getMyManufacturer() {return MyManufacturer;}

    // -------------------------------- Methods related to Supplier ------------------------------

    public List<SupplierProduct> getMySuppliersProduct() {return MySuppliersProduct;}

    public void deleteSupplierProduct(SupplierProduct supplierProduct) {
        MySuppliersProduct.remove(supplierProduct);}
}
