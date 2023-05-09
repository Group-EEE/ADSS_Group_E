package InventoryModule.Business.Controllers;

import DataAccess.SuperLiDB;
import InventoryModule.Business.Category;
import InventoryModule.Business.Discount;
import InventoryModule.Business.SpecificProduct;
import InventoryModule.Business.SuperLiProduct;
import SuppliersModule.Business.Controllers.SupplierController;

import java.time.LocalDateTime;
import java.util.*;

import static InventoryModule.Presentation.InventoryModulePresentation.reader;

//this class saves all the information about the products in the store
public class ProductController {
    public static List<Integer> BarcodesOfNewProducts;
    private CategoryController categoryController;
    static ProductController productController;
    private static SuperLiDB superLiDB;

    private ProductController() { //constructor
        superLiDB = SuperLiDB.getInstance();
        categoryController = CategoryController.getInstance();
        BarcodesOfNewProducts = superLiDB.getObserverBarcodeList();

    }
    public static ProductController getInstance(){
        if(productController == null)
            productController = new ProductController();
        return productController;
    }

    //function that add product to the store- to the controller
    public void addProduct(int Barcode,String Name,Double Costumerprice,String Category,String Subcategory,String Subsubcategory,
                           int Supplydays,String Manufacturer,int Minimumamount){
        //if the category of the new product doesnt exist yet, we will create it
        if(!categoryController.check_if_exist_cat(Category)){
            categoryController.addCategory(Category);
            categoryController.addSubCategory(Category,Subcategory);
            categoryController.addSubSubCategory(Subcategory, Subsubcategory,Category);
        }
        //create the new product - call to its constructor
        SuperLiProduct P = new SuperLiProduct(Barcode, Name, Costumerprice, Category,Subcategory, Subsubcategory,
                Supplydays,Manufacturer,Minimumamount);
        superLiDB.insertSuperLiProduct(P); //add to the controller's list
        BarcodesOfNewProducts.remove(Barcode);
    }


    //this function removes specific product from shelf in store to the warehouse
    //and from warehouse to the store
    public void change_Shelf_Warehouse(int sp_id, int p_id) {
        boolean found = false; //if true- the product was found
        //check if the product exist in the controller
        for (Map.Entry<Integer, SuperLiProduct> pair : superLiDB.getSuperLiProductMap().entrySet()){
            if (pair.getValue().getBarcode() == p_id) {
                found = true;
                //if the product is in warehouse
                if (pair.getValue().getSpecificProduct(sp_id).isInWarehouse() == true) {
                    //change the field to false - not in the warehouse
                    pair.getValue().getSpecificProduct(sp_id).setInWarehouse(false);
                    //get a random number for the shelf number
                    Random r = new Random();
                    int rand = r.nextInt(200);
                    //change the field to the new shelf number
                    pair.getValue().getSpecificProduct(sp_id).setLocation_in_Store(rand);
                    //change the number of spec. product that on the shelf for this main product
                    pair.getValue().setWarehouse_amount(pair.getValue().getWarehouse_amount()-1);
                    //change the number of spec. product that on the shelf for this main product
                    pair.getValue().setShelf_amount(pair.getValue().getShelf_amount()+1);
                    System.out.println("Product transfer to store in shelf number " + rand);
                } else {  //if the product is in store
                    ////change the field to true - in the warehouse
                    pair.getValue().getSpecificProduct(sp_id).setInWarehouse(true);
                    //change the location in store to -1 - represent warehouse
                    pair.getValue().getSpecificProduct(sp_id).setLocation_in_Store(-1);
                    //change the number of spec. product that on the shelf for this main product
                    pair.getValue().setShelf_amount( pair.getValue().getShelf_amount()-1);
                    //change the number of spec. product that on the shelf for this main product
                    pair.getValue().setWarehouse_amount( pair.getValue().getWarehouse_amount()+1);
                    System.out.println("Product transfer to warehouse!");
                }
            }
        }
        if(found==false){
            System.out.println("Product wasn't found!");
        }
    }

    public void GetAllProductBarcode(){ //print all the barcodes that exist in the store
        System.out.println("******Store Products Barcodes******");
        for (Map.Entry<Integer, SuperLiProduct> pair : superLiDB.getSuperLiProductMap().entrySet()){
            System.out.println(pair.getValue().getPName() + ": " +pair.getValue().getBarcode());
        }
    }

    //function that get barcode and return a product
    public SuperLiProduct getProductByBarcode(int BID){
        for (Map.Entry<Integer, SuperLiProduct> pair : superLiDB.getSuperLiProductMap().entrySet()){//search the given barcode in the controller's list
            if(pair.getValue().getBarcode()==BID){
                return pair.getValue();
            }
        }
        return null;
    }

    public void addspecificproduct(String supp,Double supPrice,int Barcode,LocalDateTime aExp_date,Boolean aDefective, String aDefect_report_by,
                                   Boolean aInWarehouse,String aStoreBranch,int aLocationInStore,LocalDateTime startdate,LocalDateTime enddate,Double aDiscount,String defectype) {
        SuperLiProduct p = this.getProductByBarcode(Barcode); //find the general product
        if (p != null) { //general product found
            Discount dd = new Discount(startdate, enddate, aDiscount); //create the discount
            //use function add spec. product of this general product thar found. call to
            //specific product constructor inside
            p.addSpecificProduct(supp, supPrice, Barcode, aExp_date, aDefective, aDefect_report_by,
                    aInWarehouse, aStoreBranch, aLocationInStore, dd, defectype);
        }
    }


    public int getBarcodesOfNewProductsSize(){
       return BarcodesOfNewProducts.size();
    }

    public static List<SuperLiProduct> getProducts(){
        List<SuperLiProduct> slp = new ArrayList<>();
        for (Map.Entry<Integer, SuperLiProduct> pair : superLiDB.getSuperLiProductMap().entrySet()) {//search the given barcode in the controller's list
            slp.add(pair.getValue());
        }
        return slp;
    }
}
