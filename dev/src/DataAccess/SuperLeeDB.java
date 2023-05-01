package DataAccess;

import DataAccess.SuppliersModule.*;
import SuppliersModule.Business.*;

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



    private SuperLeeDB() {

        //---------------------------------Try connect to DB-----------------------------------
        try {conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDB");}
        catch (SQLException e) {e.printStackTrace();}

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
        manufacturerDAO.WriteManufacturersToCache();
        genericProductDAO.ReadGenericProductsToCache();
        supplierDAO.ReadSuppliersToCache();
    }

    public void WriteAllToDB(){
        manufacturerDAO.WriteFromCacheToDB();
        genericProductDAO.WriteFromCacheToDB();
        supplierDAO.WriteFromCacheToDB();

        try{conn.close();}
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public boolean CheckIfSupplierExist(String supplierNum)
    {
        return supplierDAO.CheckIfSupplierExist(supplierNum);
    }

    public Supplier getSupplierBySupplierNumber(String supplierNum)
    {
        return supplierDAO.getSupplierBySupplierNumber(supplierNum);
    }

    public void insertSupplier(Supplier supplier)
    {
        supplierDAO.insert(supplier);
    }

    public boolean CheckIfManufacturerExist(String manufacturerName)
    {
        return manufacturerDAO.CheckIfManufacturerExist(manufacturerName);
    }

    public void insertManufacturer(Manufacturer manufacturer)
    {
        manufacturerDAO.insert(manufacturer);
    }

    public boolean CheckIfGenericProductExist(String name , String manufacturerName)
    {
        return genericProductDAO.CheckIfGenericProductExist(name, manufacturerName);
    }

    public void insertGenericProduct(GenericProduct genericProduct)
    {
        genericProductDAO.insert(genericProduct);
    }

    public Manufacturer getManufacturer(String manufacturerName)
    {
        return manufacturerDAO.getManufacturer(manufacturerName);
    }

    public GenericProduct getGenericProductByName(String name , String manufacturerName)
    {
        return genericProductDAO.getGenericProductByName(name, manufacturerName);
    }

    public GenericProduct getGenericProductByBarcode(int barcode)
    {
        return genericProductDAO.getGenericProductByBarcode(barcode);
    }

    public void insertSupplierProduct(SupplierProduct supplierProduct)
    {
        supplierProductDAO.insert(supplierProduct);
    }

    public void deleteSupplierProduct(SupplierProduct supplierProduct)
    {
        supplierProductDAO.delete(supplierProduct);
    }

    public void insertSupplierProductDiscount(SupplierProduct supplierProduct, SupplierProductDiscount supplierProductDiscount)
    {
        supplierProductDiscountDAO.insert(supplierProduct, supplierProductDiscount);
    }

    public boolean CheckIfOrderDiscountExist(String supplierNum, String priceOrQuantity, int amount)
    {
        return orderDiscountDAO.CheckIfOrderDiscountExist(supplierNum, priceOrQuantity, amount);
    }

    public void insertOrderDiscount(String supplierNum, OrderDiscount orderDiscount)
    {
        orderDiscountDAO.insert(supplierNum, orderDiscount);
    }

    public Map<String, Supplier> gatAllSuppliers()
    {
        return supplierDAO.getIdentifyMapSupplier();
    }

    public void deleteSupplier(String supplierNum)
    {
        supplierDAO.delete(supplierNum);
    }

    public void deleteSupplierProductDiscount(String supplierNum, String supplierCatalog, int amount)
    {
        supplierProductDiscountDAO.delete(supplierNum, supplierCatalog ,amount);
    }

    public void deleteOrderDiscount(String supplierNum, String priceOrQuantity, int minimumAmount)
    {
        orderDiscountDAO.delete(supplierNum, priceOrQuantity ,minimumAmount);
    }

    public Map<List<String>, GenericProduct> getAllGenericProduct()
    {
        return genericProductDAO.getIdentifyMapGenericProductByName();
    }

    public void insertContact(Contact contact)
    {
        contactDAO.insert(contact);
    }

    public boolean CheckIfContactExist(String phoneNumber)
    {
        return contactDAO.CheckIfContactExist(phoneNumber);
    }

    public void updateContact(String oldPhone, Contact contact)
    {
        contactDAO.update(oldPhone, contact);
    }

    public void deleteContact(String phoneNumber)
    {
        contactDAO.delete(phoneNumber);
    }

    public void deleteWorkingWithManufacturer(String supplierNum, String manufacturerNum)
    {
        manufacturerDAO.deleteWorkingWithManufacturer(supplierNum, manufacturerNum);
    }

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

    public void insertOrderFromSupplier(OrderFromSupplier orderFromSupplier)
    {
        orderFromSupplierDAO.insert(orderFromSupplier);
    }
}
