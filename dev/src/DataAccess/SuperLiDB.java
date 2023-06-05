package DataAccess;

import DataAccess.InventoryModule.*;
import DataAccess.SuppliersModule.*;
import InventoryModule.Business.*;
import SuppliersModule.Business.*;
import org.sqlite.JDBC;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Data access object class that through it all access to the DB.
 */
public class SuperLiDB {

    //------------------------------------------ Attributes ---------------------------------------
    private static SuperLiDB superLiDB;
    private Connection conn;

    // ------------------------------ References to another Suppliers DAO's------------------------------
    private SupplierDAO supplierDAO;                        // Data access object of suppliers
    private ManufacturerDAO manufacturerDAO;                // Data access object of manufacturers
    private GenericProductDAO genericProductDAO;            // Data access object of genericProducts
    private SupplierProductDAO supplierProductDAO;
    private SupplierProductDiscountDAO supplierProductDiscountDAO;
    private OrderDiscountDAO orderDiscountDAO;
    private ContactDAO contactDAO;
    private PeriodicOrderDAO periodicOrderDAO;
    private  OrderFromSupplierDAO orderFromSupplierDAO;

    // ------------------------------ References to another Inventory DAO's------------------------------
    private CategoryDAO categoryDAO;
    private DiscountDAO discountDAO;
    private SpecificProductDAO specificProductDAO;
    private SubCategoryDAO subCategoryDAO;
    private SubSubCategoryDAO subSubCategoryDAO;
    private SuperLiProductDAO superLiProductDAO;

    /**
     * Singleton constructor
     */
    private SuperLiDB() {

        //---------------------------------Try connect to DB-----------------------------------
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDB");
        }
        catch (SQLException e) {
            try {conn = DriverManager.getConnection("jdbc:sqlite::resource:SuperLeeDB");}
            catch (SQLException ex) {throw new RuntimeException(ex);}
        }

        //------------------------------------Initialization--------------------------------------
        supplierDAO = SupplierDAO.getInstance(conn);
        manufacturerDAO = ManufacturerDAO.getInstance(conn);
        genericProductDAO = GenericProductDAO.getInstance(conn);
        supplierProductDAO = SupplierProductDAO.getInstance(conn);
        supplierProductDiscountDAO = SupplierProductDiscountDAO.getInstance(conn);
        orderDiscountDAO = OrderDiscountDAO.getInstance(conn);
        contactDAO = ContactDAO.getInstance(conn);
        periodicOrderDAO = PeriodicOrderDAO.getInstance(conn);
        orderFromSupplierDAO = OrderFromSupplierDAO.getInstance(conn);

        categoryDAO = CategoryDAO.getInstance(conn);
        discountDAO = DiscountDAO.getInstance(conn);
        specificProductDAO = SpecificProductDAO.getInstance(conn);
        subCategoryDAO = SubCategoryDAO.getInstance(conn);
        subSubCategoryDAO = SubSubCategoryDAO.getInstance(conn);
        superLiProductDAO = SuperLiProductDAO.getInstance(conn);
    }

    /**
     * Get instance
     * @return - SupplierDAO
     */
    public static SuperLiDB getInstance() {
        if (superLiDB == null) {
            superLiDB = new SuperLiDB();
            superLiDB.ReadAllToCache();
        }
        return superLiDB;
    }

    /**
     * Get connection
     * @return connection object
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Read all data to cache
     */
    public void ReadAllToCache()
    {
        manufacturerDAO.ReadManufacturersToCache();
        genericProductDAO.ReadGenericProductsToCache();
        supplierDAO.ReadSuppliersToCache();
        categoryDAO.ReadCategoryToCache();
        superLiProductDAO.ReadSuperLiProductToCache();
    }

    /**
     * Write all data to DB
     */
    public void WriteAllToDB(){
        manufacturerDAO.WriteFromCacheToDB();
        genericProductDAO.WriteFromCacheToDB();
        supplierDAO.WriteFromCacheToDB();
        categoryDAO.WriteFromCacheToDB();
        superLiProductDAO.WriteFromCacheToDB();

        try{conn.close();}
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    //------------------------------------SupplierDAO-----------------------------------------------
    public Supplier getSupplierBySupplierNumber(String supplierNum)
    {
        return supplierDAO.getSupplierBySupplierNumber(supplierNum);
    }

    public void insertSupplier(Supplier supplier)
    {
        supplierDAO.insert(supplier);
    }

    public Map<String, Supplier> gatAllSuppliers()
    {
        return supplierDAO.getAllSuppliers();
    }

    public void deleteSupplier(String supplierNum)
    {
        supplierDAO.delete(supplierNum);
    }


    //------------------------------------ManufacturerDAO-----------------------------------------------

    public void insertManufacturer(Manufacturer manufacturer)
    {
        manufacturerDAO.insert(manufacturer);
    }

    public Manufacturer getManufacturer(String manufacturerName)
    {
        return manufacturerDAO.getManufacturerByManufacturerName(manufacturerName);
    }



    //------------------------------------GenericProductDAO-----------------------------------------------

    public void insertGenericProduct(GenericProduct genericProduct)
    {
        genericProductDAO.insert(genericProduct);
    }

    public GenericProduct getGenericProductByName(String name , String manufacturerName)
    {
        return genericProductDAO.getGenericProductByName(name, manufacturerName);
    }

    public GenericProduct getGenericProductByBarcode(int barcode)
    {
        return genericProductDAO.getGenericProductByBarcode(barcode);
    }

    public void addToObserverBarcodeList(int barcode)
    {
        genericProductDAO.addToObserverBarcodeList(barcode);
    }



    public List<Integer> getObserverBarcodeList()
    {
        return genericProductDAO.getObserverBarcodeList();
    }

    //------------------------------------SupplierProductDAO-----------------------------------------------

    public void insertSupplierProduct(SupplierProduct supplierProduct)
    {
        supplierProductDAO.insert(supplierProduct);
    }

    public void deleteSupplierProduct(SupplierProduct supplierProduct)
    {
        supplierProductDAO.delete(supplierProduct);
    }

    //------------------------------------SupplierProductDiscountDAO-----------------------------------------------

    public void insertSupplierProductDiscount(SupplierProduct supplierProduct, SupplierProductDiscount supplierProductDiscount)
    {
        supplierProductDiscountDAO.insert(supplierProduct, supplierProductDiscount);
    }

    public void deleteSupplierProductDiscount(String supplierNum, String supplierCatalog, int amount)
    {
        supplierProductDiscountDAO.delete(supplierNum, supplierCatalog ,amount);
    }

    //------------------------------------OrderDiscountDAO-----------------------------------------------

    public boolean CheckIfOrderDiscountExist(String supplierNum, String priceOrQuantity, int amount)
    {
        return orderDiscountDAO.getOrderDiscountByKey(supplierNum, priceOrQuantity, amount) != null;
    }

    public void deleteOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        orderDiscountDAO.delete(supplierNum, priceOrQuantity ,minimumAmount);
    }

    public void insertOrderDiscount(String supplierNum, OrderDiscount orderDiscount)
    {
        orderDiscountDAO.insert(supplierNum, orderDiscount);
    }

    //------------------------------------ContactDAO-----------------------------------------------

    public void insertContact(Contact contact)
    {
        contactDAO.insert(contact);
    }

    public boolean CheckIfContactExist(String phoneNumber)
    {
        return contactDAO.getByPhoneNumber(phoneNumber) != null;
    }

    public void deleteContact(String phoneNumber)
    {
        contactDAO.delete(phoneNumber);
    }

    //------------------------------------PeriodicOrderDAO-----------------------------------------------

    public Map<List<Integer>, PeriodicOrder> getAllPeriodicOrder()
    {
        return periodicOrderDAO.getAllPeriodicOrder();
    }

    public void insertPeriodicOrder(PeriodicOrder periodicOrder)
    {
        periodicOrderDAO.insert(periodicOrder);
    }

    public PeriodicOrder getPeriodicOrder(int id)
    {
        return periodicOrderDAO.getById(id);
    }

    public void deletePeriodicOrder(int id)
    {
        periodicOrderDAO.delete(id);
    }

    //------------------------------------OrderFromSupplierDAO-----------------------------------------------

    public void insertOrderFromSupplier(OrderFromSupplier orderFromSupplier)
    {
        orderFromSupplierDAO.insert(orderFromSupplier);
    }

    public int getSizeOfOrderFromSuppliers()
    {
        return orderFromSupplierDAO.getSizeOfOrderFromSuppliers();
    }

    //------------------------------------CategoryDAO-----------------------------------------------
    public void insertCategory(Category category)
    {
        categoryDAO.Insert(category);
    }

    public Map<String, Category> getCategoriesMap(){
        return categoryDAO.getIdentifyMapCategory();
    }

    public void removeCategory(Category c){
        categoryDAO.delete(c);
    }

    //------------------------------------SubCategoryDAO-----------------------------------------------

    public Map<List<String>, SubCategory> getSubCategoriesMap(){
        return subCategoryDAO.getIdentifyMapSubCategory();
    }

    public void insertSubCategory(SubCategory subcategory, String catname)
    {
        subCategoryDAO.Insert(subcategory, catname);
    }

    public void removeSubCategory(SubCategory sc, String category){
        subCategoryDAO.delete(category, sc);
    }


    //------------------------------------SubSubCategoryDAO-----------------------------------------------

    public Map<List<String>, SubSubCategory> getSubSubCategoriesMap(){
        return subSubCategoryDAO.getIdentifyMapSubSubCategory();
    }

    public void insertSubSubCategory(SubSubCategory subsubcategory, String subcat, String cat)
    {
        subSubCategoryDAO.Insert(subsubcategory, subcat, cat);
    }

    public void removeSubSubCategory(List<String> subsubkey){
        subSubCategoryDAO.delete(subsubkey.get(0), subsubkey.get(1), subsubkey.get(2));
    }

    //------------------------------------SuperLiProductDAO-----------------------------------------------

    public void insertSuperLiProduct(SuperLiProduct p)
    {
        superLiProductDAO.Insert(p);
    }

    public Map<Integer, SuperLiProduct> getSuperLiProductMap(){

        return superLiProductDAO.getIdentifySuperLiProduct();
    }
}
