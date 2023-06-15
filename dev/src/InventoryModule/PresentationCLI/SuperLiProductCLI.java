package InventoryModule.PresentationCLI;

import InventoryModule.Business.Controllers.ProductController;

import java.util.Scanner;

import static InventoryModule.PresentationCLI.StoreKeeperCLI.reader;
//this class represent the sub menu of the SuperLiProducts. Here we can do actions on SuperLiProducts:
//add and show all barcodes
public class SuperLiProductCLI {
    private ProductController productController;

    public SuperLiProductCLI(){

        productController = ProductController.getInstance();
    }

    //this method shows the sub menu to the screen
    public void ShowSuperLiProductMenu(){
        String c1 = "";
        while (!c1.equals("0")){
            Scanner option1 = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Add new product to the store");
            System.out.println("2. Get all products' barcode");
            System.out.println("0. Exit");
            c1 = option1.nextLine();
            switch (c1) {
                case "1":
                    //add the new product to the controller, inside there is a call to the constructor
                    addProduct();
                    break;
                case "2":
                    //call the function that print all products barcodes
                    System.out.println(productController.GetAllProductBarcode());
                    break;
            }
        }
    }

    //---------------------------------------Cases Methods-------------------------------------------------
    //this method is used to add SuperLiProduct to the store - we need all the details about the product
    public void addProduct(){
        // get from the warehouse manager all the information needed to create new product
        boolean isBarcodeExist = false;
        int Barcode = 0;
        while(!isBarcodeExist){
            System.out.println("Please enter Barcode:");
            Barcode = Integer.parseInt(reader.nextLine());
            //we add SuperLiProduct according to the BarcodesOfNewProducts List that the supplier module add
            if(!productController.BarcodesOfNewProducts.contains(Barcode)){
                System.out.println("Wrong barcode!");
            }
            else
                isBarcodeExist = true;
        }
        //ask for all the other details we need to enter new SuperLiProduct to the system
        System.out.println("Please enter Name:");
        String Name = reader.nextLine();
        System.out.println("Please enter Costumer price:");
        Double Costumerprice = Double.parseDouble(reader.nextLine());
        System.out.println("Please enter Category:");
        String Category = reader.nextLine();
        System.out.println("Please enter Subcategory:");
        String Subcategory = reader.nextLine();
        System.out.println("Please enter Subsubcategory:");
        String Subsubcategory = reader.nextLine();
        System.out.println("Please enter Supply days:");
        int Supplydays = Integer.parseInt(reader.nextLine());
        System.out.println("Please enter Manufacturer:");
        String Manufacturer = reader.nextLine();
        System.out.println("Please enter Minimum amount:");
        int Minimumamount = Integer.parseInt(reader.nextLine());
        productController.addProduct(Barcode, Name, Costumerprice, Category,Subcategory, Subsubcategory,
                Supplydays,Manufacturer,Minimumamount);
    }


}
