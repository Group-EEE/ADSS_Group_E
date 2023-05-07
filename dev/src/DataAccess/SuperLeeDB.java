package DataAccess;

import DataAccess.InventoryModule.*;
import DataAccess.SuppliersModule.*;
import SuppliersModule.Business.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class SuperLeeDB {

    private static SuperLeeDB superLeeDB;
    private Connection conn;

    private SupplierDAO supplierDAO;                        // Data access object of suppliers
    private ManufacturerDAO manufacturerDAO;                // Data access object of manufacturers
    private GenericProductDAO genericProductDAO;            // Data access object of genericProducts
    private SupplierProductDAO supplierProductDAO;
    private SupplierProductDiscountDAO supplierProductDiscountDAO;
    private OrderDiscountDAO orderDiscountDAO;
    private ContactDAO contactDAO;
    private PeriodicOrderDAO periodicOrderDAO;
    private  OrderFromSupplierDAO orderFromSupplierDAO;
    private CategoryDAO categoryDAO;
    private DiscountDAO discountDAO;
    private SpecificProductDAO specificProductDAO;
    private SubCategoryDAO subCategoryDAO;
    private SubSubCategoryDAO subSubCategoryDAO;
    private SuperLiProductDAO superLiProductDAO;

    URL resourceUrl;
    Path tempDbFile;
    private SuperLeeDB() {

        //---------------------------------Try connect to DB-----------------------------------
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDB");
        }
        catch (SQLException e) {throw new RuntimeException(e);}

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

    public static SuperLeeDB getInstance() {
        if (superLeeDB == null) {
            superLeeDB = new SuperLeeDB();
            //superLeeDB.WriteAllToDB();
            superLeeDB.ReadAllToCache();
        }
        return superLeeDB;
    }

    public Connection getConnection() {
        return conn;
    }


    public void ReadAllToCache()
    {
        manufacturerDAO.ReadManufacturersToCache();
        genericProductDAO.ReadGenericProductsToCache();
        supplierDAO.ReadSuppliersToCache();
        categoryDAO.ReadCategoryToCache();
        discountDAO.ReadDiscountToCache();
        superLiProductDAO.ReadSuperLiProductToCache();
    }

    public void WriteAllToDB(){
        manufacturerDAO.WriteFromCacheToDB();
        genericProductDAO.WriteFromCacheToDB();
        supplierDAO.WriteFromCacheToDB();

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
        return supplierDAO.getIdentifyMapSupplier();
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
        return manufacturerDAO.getManufacturer(manufacturerName);
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

    public void removeFromObserverBarcodeList(int barcode)
    {
        genericProductDAO.removeFromObserverBarcodeList(barcode);
    }

    public int getBarcodeFromObserverBarcodeList(int barcode)
    {
        return genericProductDAO.getBarcodeFromObserverBarcodeList(barcode);
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
        return orderDiscountDAO.CheckIfOrderDiscountExist(supplierNum, priceOrQuantity, amount);
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
        return contactDAO.CheckIfContactExist(phoneNumber);
    }

    public void deleteContact(String phoneNumber)
    {
        contactDAO.delete(phoneNumber);
    }

    //------------------------------------PeriodicOrderDAO-----------------------------------------------

    public Map<Integer, PeriodicOrder> getAllPeriodicOrder()
    {
        return periodicOrderDAO.getIdentifyMapPeriodicOrderByDay();
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
}
