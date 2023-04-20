import java.util.HashMap;
import java.util.Map;

/**
A manufacturer class that describes a manufacturer of products
*/
public class Manufacturer {

    //------------------------------------------ Attribute ---------------------------------------
    private String Name;

    //------------------------------------------ References ---------------------------------------

    private Map<String,Supplier> MySuppliers; //All the suppliers that work with this manufacturer
    private Map<String, GenericProduct> MyProducts; // All the product that made by the manufacturer

    //--------------------------------------Methods related to This ----------------------------------------

    //Constructor
    public Manufacturer(String name) {
        Name = name;
        MySuppliers = new HashMap<String,Supplier>();
        MyProducts = new HashMap<String, GenericProduct>();
    }

    public String getName() {
        return Name;
    }

    public String toString(){
        return Name + "\t";
    }

    //-------------------------------------- Methods related to Supplier ----------------------------------------

    /**
     * Add new supplier that working with this manufacturer
     */
    public void addSupplier(Supplier supplier) {MySuppliers.put(supplier.getSupplierNum(), supplier);}

    /**
     * Delete supplier that stop working with this manufacturer
     */
    public void deleteSupplier(Supplier supplier) {MySuppliers.remove(supplier.getSupplierNum());}

    //-------------------------------------- Methods related to Product ----------------------------------------

    /**
     * Add new product that the manufacturer starts to produce
     */
    public void addProduct(GenericProduct genericProduct) {MyProducts.put(genericProduct.getName(), genericProduct);}


}
