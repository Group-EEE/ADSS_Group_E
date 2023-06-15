package InventoryModule.PresentationCLI;

import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.SpecificProduct;
import InventoryModule.Business.SuperLiProduct;
import SuppliersModule.Business.Controllers.SupplierController;

import java.time.LocalDateTime;
import java.util.Scanner;

import static InventoryModule.PresentationCLI.StoreKeeperCLI.reader;
//this class represent the sub menu of specificProduct - here all the actions on
//specificProduct are available - add, remove, find location in store etc.
public class SpecificProductCLI {
    private ProductController productController;
    public SpecificProductCLI(){

        productController = ProductController.getInstance();
    }
    //print to the screen the specificProduct menu
    public void ShowSpecificProductMenu(){
        String c2 = "";
        while (!c2.equals("0")) {
            Scanner option2 = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Add new specific product to store");
            System.out.println("2. Remove specific product from store");
            System.out.println("3. Report defected specific product");
            System.out.println("4. Find specific product in store");
            System.out.println("5. Change specific product place in store");
            System.out.println("6. Transfer specific product from/to warehouse");
            System.out.println("0. Exit");
            c2 = option2.nextLine();
            switch (c2) {
                case "1":
                    addSpecificProduct();
                    break;
                case "2": //Remove specific product from store
                    removeSpecificProduct();
                    break;
                case "3": //Report defected specific product
                    reportDefectedSpecificProduct();
                    break;
                case "4": //Find specific product in store
                    getSpecificProductLocationInStore();
                    break;
                case "5": //Change specific product place in store
                    changeSpecificProductLocationInStore();
                    break;
                case "6": //Transfer specific product from/to warehouse
                    Scanner ba6 = new Scanner(System.in);
                    System.out.println("Please enter product's Barcode:");
                    int barcode6 = ba6.nextInt();
                    Scanner sp6 = new Scanner(System.in);
                    System.out.println("Please enter specific product id:");
                    int spid6 = sp6.nextInt();
                    productController.change_Shelf_Warehouse(spid6, barcode6);
                    break;
            }
        }
    }

    //---------------------------------------------Case Methods---------------------------------------------
    //this method is used to add specific product to the store - we need all the details about
    //the specific product
    public void addSpecificProduct() {
        //we need an instance of supplierController to check if the product is supplied
        //by known supplier
        SupplierController supplierController = SupplierController.getInstance();
        //get from the warehouse manager all the information needed to create new spec. product
        System.out.println("Please enter Barcode:");
        int Barcode = Integer.parseInt(reader.nextLine());
        SuperLiProduct p = productController.getProductByBarcode(Barcode); //find the general product
        if (p != null) { //general product found
            System.out.println("Please enter Exp Date yyyy-MM-dd:");
            String date = reader.nextLine();
            LocalDateTime aExp_date = LocalDateTime.parse(date + "T00:00:00");
            System.out.println("Is the product defective? - please enter true/false:");
            boolean aDefective = Boolean.parseBoolean(reader.nextLine());
            System.out.println("Please enter Defect reporter name else press null:");
            String aDefect_report_by = reader.nextLine();
            System.out.println("Please enter Defect type else press null:");
            String defectype = reader.nextLine();
            System.out.println("Is the product stored in warehouse? - please enter true/false:");
            boolean aInWarehouse = Boolean.parseBoolean(reader.nextLine());
            System.out.println("Please enter store branch name:");
            String aStoreBranch = reader.nextLine();
            System.out.println("Please enter product Shelf number - if the product located in warehouse enter -1:");
            int aLocationInStore = Integer.parseInt(reader.nextLine());
            System.out.println("Please enter discount amount:");
            double aDiscount = Double.parseDouble(reader.nextLine());
            System.out.println("If the product has discount please enter start Date yyyy-MM-dd else press null:");
            String start = reader.nextLine();
            if (start.compareTo("null") == 0) { //there is no discount
                start = "1111-11-11";
            }
            //create local date time for the discount object
            LocalDateTime startdate = LocalDateTime.parse(start + "T00:00:00");
            Scanner e = new Scanner(System.in);
            System.out.println("If the product has discount please enter end Date yyyy-MM-dd else press null:");
            String end = e.nextLine();
            if (end.compareTo("null") == 0) { //there is no discount
                end = "1111-11-11";
            }
            //create local date time for the discount object
            LocalDateTime enddate = LocalDateTime.parse(end + "T00:00:00");

            ///check if there is a supplierNum like this in the system
            boolean isSupplierNumExist = false;
            String supp = null;
            while (!isSupplierNumExist) {
                System.out.println("Enter supplier Num");
                supp = reader.nextLine();
                if (!supplierController.checkIfSupplierExist(supp)) {
                    System.out.println("Wrong supplier Num!");
                } else
                    isSupplierNumExist = true;
            }

            System.out.println("Enter supplier price");
            double supPrice = Double.parseDouble(reader.nextLine());

            //check if there is barcode like this in the system//
            if (supplierController.checkIfSupplierSupplyProduct(supp, Barcode)) {
                productController.addspecificproduct(supp, supPrice, Barcode, aExp_date, aDefective, aDefect_report_by,
                        aInWarehouse, aStoreBranch, aLocationInStore, startdate, enddate, aDiscount, defectype);
            } else { //general product wasnt found
                System.out.println("Product doesn't exist in store!");
            }
        } else
            System.out.println("Wrong barcode entered!");
    }

    //this method is used to remove specific product to the store
    public void removeSpecificProduct(){
        System.out.println("Please enter product's Barcode:");
        int barcode = Integer.parseInt(reader.nextLine());
        System.out.println("Please enter specific product id:");
        int spid = Integer.parseInt(reader.nextLine());
        SuperLiProduct np = productController.getProductByBarcode(barcode);
        if(np!=null){ // SuperLiProduct found
            np.removeSpecificProduct(spid);
        }
        else{//general product wasnt found
            System.out.println("Product wasn't found!");
        }
    }

    //this method is used to report specific product as defected from any reason
    public void reportDefectedSpecificProduct(){
        System.out.println("Please enter product's Barcode:");
        int barcode3 = Integer.parseInt(reader.nextLine());
        System.out.println("Please enter specific product id:");
        int spid3 = Integer.parseInt(reader.nextLine());
        SuperLiProduct np3 = productController.getProductByBarcode(barcode3);
        if(np3!=null){ //SuperLiProduct found
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

    //this method is used to find specific product location in store
    public void getSpecificProductLocationInStore(){
        System.out.println("Please enter product's Barcode:");
        int barcode4 = Integer.parseInt(reader.nextLine());
        System.out.println("Please enter specific product id:");
        int spid4 = Integer.parseInt(reader.nextLine());
        SuperLiProduct np4 = productController.getProductByBarcode(barcode4);
        if(np4!=null){//SuperLiProduct found
            np4.getProductLocationInStore(spid4);
        }
        else{
            System.out.println("Product wasn't found!");
        }
    }

    //this method is used to change specific product location in store
    public void changeSpecificProductLocationInStore(){
        System.out.println("Please enter product's Barcode:");
        int barcode5 = Integer.parseInt(reader.nextLine());
        System.out.println("Please enter specific product id:");
        int spid5 = Integer.parseInt(reader.nextLine());
        SuperLiProduct np5 = productController.getProductByBarcode(barcode5);
        if(np5!=null){
            SpecificProduct thep5 = np5.getSpecificProduct(spid5);
            System.out.println("Please enter product's new shelf number in store:");
            int location5 = Integer.parseInt(reader.nextLine());
            thep5.setLocation_in_Store(location5);
        }
        else{
            System.out.println("Product wasn't found!");
        }
    }


}
