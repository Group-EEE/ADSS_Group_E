import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierController {
    Map<String, Supplier> AllSuppliers; // All the suppliers that we work with
    Map<List<String>, GenericProduct> AllProducts; // All the products we sell
    Map<String, Manufacturer> AllManufacturers; // All the manufacturers that we work with

    static SupplierController supplierController;

    private SupplierController() {
        AllSuppliers = new HashMap<>();
        AllProducts = new HashMap<>();
        AllManufacturers = new HashMap<>();
    }

    public static SupplierController getInstance() {
        if (supplierController == null)
            supplierController = new SupplierController();
        return supplierController;
    }

    public boolean checkIfSupplierExist(String supplierNum) {
        return AllSuppliers.containsKey(supplierNum);
    }

    public void addNewSupplier(Supplier supplier) {
        AllSuppliers.put(supplier.getSupplierNum(), supplier);
    }


    public void addSupplierProduct(String productName, String manufacturerName, int barcode, String supplierNum, float price, String supplierCatalog, int amount) {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);

        if (!AllManufacturers.containsKey(manufacturerName))
            AllManufacturers.put(manufacturerName, new Manufacturer(manufacturerName));

        if (!AllProducts.containsKey(keyPair))
            AllProducts.put(keyPair, new GenericProduct(productName, AllManufacturers.get(manufacturerName), barcode));

        Supplier supplier = AllSuppliers.get(supplierNum);

        GenericProduct product = AllProducts.get(keyPair);

        new SupplierProduct(price,supplierCatalog,amount, supplier, product, supplier.getMyAgreement());

    }

    public boolean checkIfSupplierSupplyProduct(String supplierCatalog, String supplierNum)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        return supplier.getSupplierProduct(supplierCatalog) != null;
    }

    public void deleteProductFromSupplier(String supplierCatalog, String supplierNum){
        Supplier supplier = AllSuppliers.get(supplierNum);
        SupplierProduct currSupplierProduct = supplier.getSupplierProduct(supplierCatalog);
        currSupplierProduct.delete();
    }

    public void addProductDiscount(String supplierCatalog, float discountPercentage, int minimumQuantity, String supplierNum){
        Supplier supplier = AllSuppliers.get(supplierNum);
        SupplierProduct supplierProduct = supplier.getSupplierProduct(supplierCatalog);
        supplierProduct.addProductDiscount(discountPercentage, minimumQuantity);
    }

    public boolean CheckIfExistOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount, float discountPercentage)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        agreement.CheckIfExistOrderDiscount(priceOrQuantity, discountPercentage, minimumAmount);
    }

    public void addOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount, float discountPercentage)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        agreement.addOrderDiscount(priceOrQuantity, minimumAmount, discountPercentage);
    }

}
