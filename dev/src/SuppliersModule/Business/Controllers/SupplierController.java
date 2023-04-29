package SuppliersModule.Business.Controllers;

import InventoryModule.Business.Controllers.ProductController;
import SuppliersModule.Business.*;
import DataAccess.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierController {
    static SupplierController supplierController;
    ProductController productController;
    private SuperLeeDB superLeeDB;

    private SupplierController() {

        superLeeDB = SuperLeeDB.getInstance();
        productController = ProductController.getInstance();
    }

    public static SupplierController getInstance() {
        if (supplierController == null)
            supplierController = new SupplierController();
        return supplierController;
    }

    public boolean checkIfSupplierExist(String supplierNum) {
        return superLeeDB.CheckIfSupplierExist(supplierNum);
    }

    public boolean checkIfSupplierSupplyProduct(String supplierNum, int barcode){
        GenericProduct genericProduct = superLeeDB.getGenericProductByBarcode(barcode);
        if(genericProduct == null)
            return false;
        return genericProduct.checkIfSupplierSupplyTheProduct(supplierNum);
    }

    public Supplier getSupplier(String supplierNum) {
        return superLeeDB.getSupplierBySupplierNumber(supplierNum);
    }

    public void addNewSupplier(Supplier supplier) {
        superLeeDB.insertSupplier(supplier);
    }


    public void addSupplierProduct(String productName, String manufacturerName, int barcode, String supplierNum, float price, String supplierCatalog, int amount) {

        if (!superLeeDB.CheckIfManufacturerExist(manufacturerName))
            superLeeDB.insertManufacturer(new Manufacturer(manufacturerName));

        if (!superLeeDB.CheckIfGenericProductExist(productName, manufacturerName)){
            GenericProduct genericProduct = new GenericProduct(productName, superLeeDB.getManufacturer(manufacturerName), barcode);
            superLeeDB.insertGenericProduct(genericProduct);
        }

        Supplier supplier = getSupplier(supplierNum);
        GenericProduct genericProduct = superLeeDB.getGenericProductByName(productName, manufacturerName);

        ProductController.BarcodesOfNewProducts.add(genericProduct.getBarcode());

        SupplierProduct supplierProduct = new SupplierProduct(price, supplierCatalog, amount,
                supplier, genericProduct, supplier.getMyAgreement());

        superLeeDB.insertSupplierProduct(supplierProduct);
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
        superLeeDB.deleteSupplierProduct(currSupplierProduct);
    }

    public void addProductDiscount(String supplierCatalog, float discountPercentage, int minimumQuantity, String supplierNum){
        Supplier supplier = getSupplier(supplierNum);
        SupplierProduct supplierProduct = supplier.getSupplierProduct(supplierCatalog);
        supplierProduct.addProductDiscount(discountPercentage, minimumQuantity);
        superLeeDB.insertSupplierProductDiscount(supplierProduct, supplierProduct.getSupplierProductDiscount(minimumQuantity));
    }

    public boolean CheckIfExistOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        return superLeeDB.CheckIfOrderDiscountExist(supplierNum, priceOrQuantity, minimumAmount);
    }

    public void addOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount, float discountPercentage)
    {
        Supplier supplier = getSupplier(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        agreement.addOrderDiscount(priceOrQuantity, minimumAmount, discountPercentage);
        superLeeDB.insertOrderDiscount(supplierNum, agreement.getOrderDiscount(priceOrQuantity, minimumAmount));
    }

    public Map<String, Supplier> returnAllSuppliers(){return superLeeDB.gatAllSuppliers();}

    public void fireSupplier(String supplierNum){
        Supplier supplier = getSupplier(supplierNum);
        supplier.fireSupplier();
        superLeeDB.deleteSupplier(supplierNum);
    }

    public void deleteDiscountProduct(String supplierNum, String supplierCatalog, int amount)
    {
        Supplier supplier = getSupplier(supplierNum);
        SupplierProduct supplierProduct = supplier.getSupplierProduct(supplierCatalog);
        supplierProduct.deleteDiscountProduct(amount);
        superLeeDB.deleteSupplierProductDiscount(supplierNum, supplierCatalog, amount);
    }

    public void deleteOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        Supplier supplier = getSupplier(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        agreement.deleteOrderDiscount(priceOrQuantity, minimumAmount);
        superLeeDB.deleteOrderDiscount(supplierNum, priceOrQuantity, minimumAmount);
    }

    public void setBankAccount(String supplierNum, String account){
        Supplier supplier = getSupplier(supplierNum);
        supplier.setBankAccount(account);
    }

    public Map<List<String>, GenericProduct> getAllProducts() {
        return superLeeDB.getAllGenericProduct();
    }


    public void addContactToSupplier(String supplierNum, String name, String phoneNumber)
    {
        Supplier supplier = getSupplier(supplierNum);
        supplier.addContact(name, phoneNumber);
        superLeeDB.insertContact(supplier.getContact(phoneNumber));
    }

    public boolean checkIfContactExist(String phoneNumber)
    {
        return superLeeDB.CheckIfContactExist(phoneNumber);
    }

    public void setNewContactPhone(String supplierNum, String NewPhone, String OldPhone)
    {
        Supplier supplier = getSupplier(supplierNum);
        Contact contact = supplier.getContact(OldPhone);
        contact.setPhoneNumber(NewPhone);
        superLeeDB.updateContact(OldPhone, contact);
    }

    public void deleteContactFromSupplier(String supplierNum, String phoneNumber)
    {
        Supplier supplier = getSupplier(supplierNum);
        supplier.deleteContact(phoneNumber);
        superLeeDB.deleteContact(phoneNumber);
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
        superLeeDB.deleteWorkingWithManufacturer(supplierNum, manufacturerName);
    }

    public String StringSupplierDetails(String supplierNum)
    {
        return getSupplier(supplierNum).toString();
    }

    public void editAgreement(String supplierNum, boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply)
    {
        getSupplier(supplierNum).getMyAgreement().setDetails(hasPermanentDays, isSupplierBringProduct, deliveryDays, numberOdDaysToSupply);
    }

    public GenericProduct findGenericProductByBarcode(int barcode){
        return superLeeDB.getGenericProductByBarcode(barcode);
    }
}