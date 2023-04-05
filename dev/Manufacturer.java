import java.util.HashMap;
import java.util.Map;

/*
A manufacturer class that describes a manufacturer of products
*/
public class Manufacturer {

    //------------------------------------------ Attribute ---------------------------------------
    private String Name;

    //------------------------------------------ References ---------------------------------------

    private Map<String,Supplier> MySuppliers;
    private Map<String,Product> MyProducts;

    //--------------------------------------Methods related to This ----------------------------------------

    //Constructor
    public Manufacturer(String name) {
        Name = name;
        MySuppliers = new HashMap<String,Supplier>();
        MyProducts = new HashMap<String,Product>();
    }

    public String getName() {
        return Name;
    }

    public String toString(){
        return Name + "\t";
    }

    //-------------------------------------- Methods related to Supplier ----------------------------------------
    public void addSupplier(Supplier supplier) {MySuppliers.put(supplier.getSupplierNum(), supplier);}

    public void deleteSupplier(Supplier supplier) {MySuppliers.remove(supplier.getSupplierNum());}

    //-------------------------------------- Methods related to Product ----------------------------------------
    public void addProduct(Product product) {MyProducts.put(product.getName(), product);}


}
