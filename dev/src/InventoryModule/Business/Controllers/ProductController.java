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
    public void addProduct(){
       // get from the warehouse manager all the information needed to create new product
        boolean isBarcodeExist = false;
        int Barcode = 0;
        while(!isBarcodeExist){
            System.out.println("Please enter Barcode:");
            Barcode = reader.nextInt();
            if(!BarcodesOfNewProducts.contains(Barcode)){
                System.out.println("Wrong barcode!");
            }
            else
                isBarcodeExist = true;
        }
        System.out.println("Please enter Name:");
        String Name = reader.nextLine();
        System.out.println("Please enter Costumer price:");
        Double Costumerprice = reader.nextDouble();
        System.out.println("Please enter Category:");
        String Category = reader.nextLine();
        System.out.println("Please enter Subcategory:");
        String Subcategory = reader.nextLine();
        System.out.println("Please enter Subsubcategory:");
        String Subsubcategory = reader.nextLine();
        System.out.println("Please enter Supply days:");
        int Supplydays = reader.nextInt();
        System.out.println("Please enter Manufacturer:");
        String Manufacturer = reader.nextLine();
        System.out.println("Please enter Minimum amount:");
        int Minimumamount = reader.nextInt();
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
        ///*****************the change**********************////
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

    public void addspecificproduct(){
        ///*****************the change**********************////
        SupplierController supplierController= SupplierController.getInstance();
        //get from the warehouse manager all the information needed to create new spec. product
        System.out.println("Please enter Barcode:");
        int Barcode = reader.nextInt();
        System.out.println("Please enter Exp Date yyyy-MM-dd:");
        String date = reader.nextLine();
        LocalDateTime aExp_date = LocalDateTime.parse(date+"T00:00:00");
        System.out.println("Is the product defective? - please enter true/false:");
        boolean aDefective = reader.nextBoolean();
        System.out.println("Please enter Defect reporter name else press null:");
        String aDefect_report_by = reader.nextLine();
        System.out.println("Please enter Defect type else press null:");
        String  defectype = reader.nextLine();
        System.out.println("Is the product stored in warehouse? - please enter true/false:");
        boolean aInWarehouse = reader.nextBoolean();
        System.out.println("Please enter store branch name:");
        String aStoreBranch = reader.nextLine();
        System.out.println("Please enter product Shelf number - if the product located in warehouse enter -1:");
        int aLocationInStore = reader.nextInt();
        System.out.println("Please enter discount amount:");
        double aDiscount = reader.nextDouble();
        System.out.println("If the product has discount please enter start Date yyyy-MM-dd else press null:");
        String start = reader.nextLine();
        if(start.compareTo("null")==0){ //there is no discount
            start = "1111-11-11";
        }
        //create local date time for the discount object
        LocalDateTime startdate = LocalDateTime.parse(start + "T00:00:00");
        Scanner e = new Scanner(System.in);
        System.out.println("If the product has discount please enter end Date yyyy-MM-dd else press null:");
        String end = e.nextLine();
        if(end.compareTo("null")==0){ //there is no discount
            end = "1111-11-11";
        }
        //create local date time for the discount object
        LocalDateTime enddate = LocalDateTime.parse(end+"T00:00:00");

        ///check if there is a supplierNum like this in the system
        boolean isSupplierNumExist = false;
        String supp = null;
        while(!isSupplierNumExist){
            System.out.println("Enter supplier Num");
            supp = reader.nextLine();
            if(!supplierController.checkIfSupplierExist(supp)){
                System.out.println("Wrong supplier Num!");
            }
            else
                isSupplierNumExist = true;
        }

        System.out.println("Enter supplier price");
        double supPrice = reader.nextDouble();

        ///*****************the change**********************////
        //check if there is barcode like this in the system//
        if(supplierController.checkIfSupplierSupplyProduct(supp,Barcode)){
            SuperLiProduct p = this.getProductByBarcode(Barcode); //find the general product
            if(p!=null) { //general product found
                Discount dd = new Discount(startdate, enddate, aDiscount); //create the discount
                //use function add spec. product of this general product thar found. call to
                //specific product constructor inside
                p.addSpecificProduct(supp,supPrice, Barcode,aExp_date, aDefective, aDefect_report_by,
                        aInWarehouse,aStoreBranch,aLocationInStore,dd, defectype);
            }
            else{ //general product wasnt found
                System.out.println("Product doesn't exist in store!");
            }
        }
        else
            System.out.println("Wrong barcode entered!");
    }

    public void removespecificproduct(){
        System.out.println("Please enter product's Barcode:");
        int barcode = reader.nextInt();
        System.out.println("Please enter specific product id:");
        int spid = reader.nextInt();
        SuperLiProduct np = this.getProductByBarcode(barcode);
        if(np!=null){ //general product found
            np.removeSpecificProduct(spid);
        }
        else{//general product wasnt found
            System.out.println("Product wasn't found!");
        }
    }

    public void reportdefectedspecificproduct(){
        System.out.println("Please enter product's Barcode:");
        int barcode3 = reader.nextInt();
        System.out.println("Please enter specific product id:");
        int spid3 = reader.nextInt();
        SuperLiProduct np3 = this.getProductByBarcode(barcode3);
        if(np3!=null){
            System.out.println("Please enter defect reporter:");
            String dere = reader.nextLine();
            System.out.println("Please enter defect type:");
            String dtype = reader.nextLine();
            np3.add_defected_specific_product(spid3, dere, dtype);
        }
        else{
            System.out.println("Product wasn't found!");
        }
    }

    public void getspecifproductlocationinstore(){
        System.out.println("Please enter product's Barcode:");
        int barcode4 = reader.nextInt();
        System.out.println("Please enter specific product id:");
        int spid4 = reader.nextInt();
        SuperLiProduct np4 = this.getProductByBarcode(barcode4);
        if(np4!=null){
            np4.getProductLocationInStore(spid4);
        }
        else{
            System.out.println("Product wasn't found!");
        }
    }

    public void changespecificproductlocationinstore(){
        System.out.println("Please enter product's Barcode:");
        int barcode5 = reader.nextInt();
        System.out.println("Please enter specific product id:");
        int spid5 = reader.nextInt();
        SuperLiProduct np5 = this.getProductByBarcode(barcode5);
        if(np5!=null){
            SpecificProduct thep5 = np5.getSpecificProduct(spid5);
            System.out.println("Please enter product's new shelf number in store:");
            int location5 = reader.nextInt();
            thep5.setLocation_in_Store(location5);
        }
        else{
            System.out.println("Product wasn't found!");
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
