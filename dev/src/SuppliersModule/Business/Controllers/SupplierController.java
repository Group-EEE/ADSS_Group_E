package SuppliersModule.Business.Controllers;

import SuppliersModule.Business.*;
import DataAccess.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierController {
    Map<String, Supplier> AllSuppliers; // All the suppliers that we work with
    Map<List<String>, GenericProduct> AllProducts; // All the products we sell (the key is the list contains the product name and manufacturer name)
    Map<Integer, GenericProduct> ProductsByBarcode; // All the products we sell (the key is the barcode)
    Map<String, Manufacturer> AllManufacturers; // All the manufacturers that we work with

    SupplierDAO supplierDAO;
    ManufacturerDAO manufacturerDAO;
    GenericProductDAO genericProductDAO;
    SupplierProductDAO supplierProductDAO;
    SuperLeeDBConnection superLeeDBConnection;
    static SupplierController supplierController;

    private SupplierController() {
        AllSuppliers = new HashMap<>();
        AllProducts = new HashMap<>();
        AllManufacturers = new HashMap<>();

        superLeeDBConnection = SuperLeeDBConnection.getInstance();
        supplierDAO = SupplierDAO.getInstance(superLeeDBConnection.getConnection());
        manufacturerDAO = ManufacturerDAO.getInstance(superLeeDBConnection.getConnection());
        genericProductDAO = GenericProductDAO.getInstance(superLeeDBConnection.getConnection());
        supplierProductDAO = SupplierProductDAO.getInstance(superLeeDBConnection.getConnection());
    }

    public static SupplierController getInstance() {
        if (supplierController == null)
            supplierController = new SupplierController();
        return supplierController;
    }

    public boolean checkIfSupplierExist(String supplierNum) {
        return AllSuppliers.containsKey(supplierNum);
    }

    public Supplier getSupplier(String supplierNum) {
        return AllSuppliers.get(supplierNum);
    }

    public void addNewSupplier(Supplier supplier) {
        AllSuppliers.put(supplier.getSupplierNum(),supplier);
    }

    public void addSupplierProduct(String productName, String manufacturerName, int barcode, String supplierNum, float price, String supplierCatalog, int amount) {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);

        if (!AllManufacturers.containsKey(manufacturerName))
            AllManufacturers.put(manufacturerName,new Manufacturer(manufacturerName));

        if (!AllProducts.containsKey(keyPair))
            AllProducts.put(keyPair,new GenericProduct(productName, manufacturerDAO.getManufacturer(manufacturerName), barcode));

        Supplier supplier = AllSuppliers.get(supplierNum);
        GenericProduct genericProduct = AllProducts.get(keyPair);
        ProductsByBarcode.put(genericProduct.getBarcode(), genericProduct);

        new SupplierProduct(price, supplierCatalog, amount, supplier, genericProduct, supplier.getMyAgreement());
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
        return agreement.CheckIfExistOrderDiscount(priceOrQuantity, discountPercentage, minimumAmount);
    }

    public void addOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount, float discountPercentage)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        agreement.addOrderDiscount(priceOrQuantity, minimumAmount, discountPercentage);
    }

    public Map<String, Supplier> returnAllSuppliers(){return AllSuppliers;}

    public void fireSupplier(String supplierNum){
        Supplier supplier = AllSuppliers.get(supplierNum);
        supplier.fireSupplier();
        AllSuppliers.remove(supplierNum);
    }

    public void deleteDiscountProduct(String supplierNum, String supplierCatalog, int amount)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        SupplierProduct supplierProduct = supplier.getSupplierProduct(supplierCatalog);
        supplierProduct.deleteDiscountProduct(amount);
    }

    public void deleteOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        Agreement agreement = supplier.getMyAgreement();
        agreement.deleteOrderDiscount(priceOrQuantity, minimumAmount);
    }

    public void setBankAccount(String supplierNum, String account){
        Supplier supplier = AllSuppliers.get(supplierNum);
        supplier.setBankAccount(account);
    }

    public Map<List<String>, GenericProduct> getAllProducts() {
        return AllProducts;
    }

    public Map<String, Manufacturer> getAllManufacturers() {
        return AllManufacturers;
    }

    public void addContactToSupplier(String supplierNum, String name, String phoneNumber)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        supplier.addContact(name, phoneNumber);
    }

    public boolean checkIfContactExist(String supplierNum, String phoneNumber)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        return supplier.getContact(phoneNumber) != null;
    }

    public void setNewContactPhone(String supplierNum, String NewPhone, String OldPhone)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        Contact contact = supplier.getContact(OldPhone);
        contact.setPhoneNumber(NewPhone);
    }

    public void deleteContactFromSupplier(String supplierNum, String phoneNumber)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        supplier.deleteContact(phoneNumber);
    }

    public void updateSupplierPaymentTerm(String supplierNum, int yourPayment)
    {
        PaymentTerm payment = PaymentTerm.values()[yourPayment];
        Supplier supplier = AllSuppliers.get(supplierNum);
        supplier.setPayment(payment);
    }

    public void stopWorkingWithManufacturer(String supplierNum, String manufacturerName)
    {
        Supplier supplier = AllSuppliers.get(supplierNum);
        supplier.stopWorkingWithManufacturer(manufacturerName);
    }

    public String StringSupplierDetails(String supplierNum)
    {
        return AllSuppliers.get(supplierNum).toString();
    }

    public void editAgreement(String supplierNum, boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOdDaysToSupply)
    {
        AllSuppliers.get(supplierNum).getMyAgreement().setDetails(hasPermanentDays, isSupplierBringProduct, deliveryDays, numberOdDaysToSupply);
    }

    public GenericProduct findGenericProductByBarcode(int barcode){
        return ProductsByBarcode.get(barcode);
    }
}
