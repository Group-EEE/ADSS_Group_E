package SuppliersModule.Business.Controllers;

import SuppliersModule.Business.*;
import DataAccess.*;
import java.util.Map;

/**
 * controller of all suppliers.
 */
public class SupplierController {

    //------------------------------------------ Attributes ---------------------------------------
    static SupplierController supplierController;
    private SuperLiDB superLiDB;

    /**
     * Singleton constructor
     */
    private SupplierController() {

        superLiDB = SuperLiDB.getInstance();
    }

    /**
     * Get instance
     * @return - SupplierController
     */
    public static SupplierController getInstance() {
        if (supplierController == null)
            supplierController = new SupplierController();
        return supplierController;
    }

    //------------------------------------------ Supplier ---------------------------------------
    public boolean checkIfSupplierExist(String supplierNum) {
        return getSupplier(supplierNum) != null;
    }

    public Supplier getSupplier(String supplierNum) {
        return superLiDB.getSupplierBySupplierNumber(supplierNum);
    }

    public void addNewSupplier(Supplier supplier) {
        superLiDB.insertSupplier(supplier);
    }

    public Map<String, Supplier> returnAllSuppliers(){return superLiDB.gatAllSuppliers();}

    public void fireSupplier(String supplierNum){
        Supplier supplier = getSupplier(supplierNum);
        supplier.fireSupplier();
        superLiDB.deleteSupplier(supplierNum);
    }

    public void updateSupplierPaymentTerm(String supplierNum, int yourPayment)
    {
        PaymentTerm payment = PaymentTerm.values()[yourPayment];
        Supplier supplier = getSupplier(supplierNum);
        supplier.setPayment(payment);
    }

    public void stopWorkingWithManufacturer(String supplierNum, String manufacturerName)
    {
        Supplier supplier = getSupplier(supplierNum);
        supplier.stopWorkingWithManufacturer(manufacturerName);
    }

    public String StringSupplierDetails(String supplierNum)
    {
        return getSupplier(supplierNum).toString();
    }

    public void editAgreement(String supplierNum, boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply)
    {
        getSupplier(supplierNum).getMyAgreement().setDetails(hasPermanentDays, isSupplierBringProduct, deliveryDays, numberOdDaysToSupply);
    }

    public void setBankAccount(String supplierNum, String account){
        Supplier supplier = getSupplier(supplierNum);
        supplier.setBankAccount(account);
    }

    //------------------------------------------ SupplierProduct ---------------------------------------
    public boolean checkIfSupplierSupplyProduct(String supplierNum, int barcode){
        GenericProduct genericProduct = superLiDB.getGenericProductByBarcode(barcode);
        if(genericProduct == null)
            return false;
        return genericProduct.checkIfSupplierSupplyTheProduct(supplierNum);
    }

    public void addSupplierProduct(String productName, String manufacturerName, int barcode, String supplierNum, float price, String supplierCatalog, int amount) {

        if (superLiDB.getManufacturer(manufacturerName) == null)
            superLiDB.insertManufacturer(new Manufacturer(manufacturerName));

        if (superLiDB.getGenericProductByName(productName, manufacturerName) == null){
            GenericProduct genericProduct = new GenericProduct(productName, superLiDB.getManufacturer(manufacturerName), barcode);
            superLiDB.insertGenericProduct(genericProduct);
        }

        Supplier supplier = getSupplier(supplierNum);
        GenericProduct genericProduct = superLiDB.getGenericProductByName(productName, manufacturerName);

        superLiDB.addToObserverBarcodeList(genericProduct.getBarcode());

        SupplierProduct supplierProduct = new SupplierProduct(price, supplierCatalog, amount,
                supplier, genericProduct, supplier.getMyAgreement());

        superLiDB.insertSupplierProduct(supplierProduct);
    }

    public boolean checkIfSupplierSupplyProduct(String supplierCatalog, String supplierNum)
    {
        Supplier supplier = getSupplier(supplierNum);
        return supplier.getSupplierProduct(supplierCatalog) != null;
    }

    public void deleteProductFromSupplier(String supplierCatalog, String supplierNum){
        Supplier supplier = getSupplier(supplierNum);
        SupplierProduct currSupplierProduct = supplier.getSupplierProduct(supplierCatalog);
        currSupplierProduct.delete();
        superLiDB.deleteSupplierProduct(currSupplierProduct);
    }

    public Map<String, SupplierProduct> returnAllProductOfSupplier(String Supplier){
        Supplier supplier = getSupplier(Supplier);
        return supplier.getMyProducts();
    }


    //------------------------------------------ SupplierProductDiscount ---------------------------------------

    public boolean addSupplierProductDiscount(String supplierCatalog, float discountPercentage, int minimumQuantity, String supplierNum) {
        Supplier supplier = getSupplier(supplierNum);
        SupplierProduct supplierProduct = supplier.getSupplierProduct(supplierCatalog);
        if (supplierProduct.addProductDiscount(discountPercentage, minimumQuantity)) {
            superLiDB.insertSupplierProductDiscount(supplierProduct, supplierProduct.getSupplierProductDiscount(minimumQuantity));
            return true;
        }
        return false;
    }

    public boolean deleteSupplierProductDiscount(String supplierNum, String supplierCatalog, int amount)
    {
        Supplier supplier = getSupplier(supplierNum);
        SupplierProduct supplierProduct = supplier.getSupplierProduct(supplierCatalog);
        if(supplierProduct.deleteDiscountProduct(amount))
        {
            superLiDB.deleteSupplierProductDiscount(supplierNum, supplierCatalog, amount);
            return true;
        }
        return false;


    }
    //------------------------------------------ OrderDiscount ---------------------------------------

    public boolean CheckIfExistOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        return superLiDB.CheckIfOrderDiscountExist(supplierNum, priceOrQuantity, minimumAmount);
    }

    public void addOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount, float discountPercentage)
    {
        Supplier supplier = getSupplier(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        agreement.addOrderDiscount(priceOrQuantity, minimumAmount, discountPercentage);
        superLiDB.insertOrderDiscount(supplierNum, agreement.getOrderDiscount(priceOrQuantity, minimumAmount));
    }


    public boolean deleteOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        Supplier supplier = getSupplier(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        if(agreement.deleteOrderDiscount(priceOrQuantity, minimumAmount))
        {
            superLiDB.deleteOrderDiscount(supplierNum, priceOrQuantity, minimumAmount);
            return true;
        }
        return false;
    }

    //------------------------------------------ Contact ---------------------------------------


    public boolean addContactToSupplier(String supplierNum, String name, String phoneNumber)
    {
        Supplier supplier = getSupplier(supplierNum);
        if(supplier.addContact(name, phoneNumber)) {
            superLiDB.insertContact(supplier.getContact(phoneNumber));
            return true;
        }
        return false;
    }
    public boolean checkIfContactExist(String phoneNumber)
    {
        return superLiDB.CheckIfContactExist(phoneNumber);
    }

    public void setNewContactPhone(String supplierNum, String NewPhone, String OldPhone)
    {
        Supplier supplier = getSupplier(supplierNum);
        Contact contact = supplier.getContact(OldPhone);
        deleteContactFromSupplier(supplierNum, OldPhone);
        addContactToSupplier(supplierNum, contact.getName(), NewPhone);
    }

    public void deleteContactFromSupplier(String supplierNum, String phoneNumber)
    {
        Supplier supplier = getSupplier(supplierNum);
        supplier.deleteContact(phoneNumber);
        superLiDB.deleteContact(phoneNumber);
    }

    //------------------------------------------ GenericProduct ---------------------------------------
    public GenericProduct findGenericProductByBarcode(int barcode){
        return superLiDB.getGenericProductByBarcode(barcode);
    }

    public Map<Integer, GenericProduct> getAllGenericProduct()
    {
        return superLiDB.getAllGenericProduct();
    }

}