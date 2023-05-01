package InventoryModule;

import InventoryModule.Presentation.CategoryPresentation;
import InventoryModule.Presentation.ReportPresentation;
import InventoryModule.Presentation.SpecificProductPresentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static InventoryModule.Discount.*;

//the main represents the "SuperLi" supply system. it is a UI that helps the employee gets information
//of all the products, reports, discounts etc to help him control the supply in store
public class InventoryModulePresentation {
    static Scanner reader;
    static String choice;

    ProductController productController; //create a product controller
    CategoryController categoryController; //create a category controller
    ReportController reportController; //create a report controller

    SpecificProductPresentation specificProductPresentation;
    ReportPresentation reportPresentation;

    CategoryPresentation categoryPresentation;

    public InventoryModulePresentation() {
        reader = new Scanner(System.in);
        productController = ProductController.getInstance(); //create a product controller
        categoryController = CategoryController.getInstance(); //create a category controller
        reportController = ReportController.getInstance(); //create a report controller
        specificProductPresentation = new SpecificProductPresentation();
    }

    public void Start(){
        if(productController.getBarcodesOfNewProductsSize() !=0)
            System.out.println("There are new products that are waiting to be added to the system!");
        while(true){ //main menu of all possible choices of information the employee needs
            Scanner option = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Products");
            System.out.println("2. Specific Products");
            System.out.println("3. Reports");
            System.out.println("4. Categories");
            System.out.println("5. SubCategories");
            System.out.println("6. SubSubCategories");
            System.out.println("7. Discounts");
            System.out.println("8. Super-Li dataBase exist information");//option to charge information like
            //data base
            int c = option.nextInt();
            switch (c) {
                case 1: //sub menu - everything about products
                    Scanner option1 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Add new product to the store");
                    System.out.println("2. Get all products' barcode");
                    int c1 = option1.nextInt();
                    switch (c1) {
                        case 1:
                            //add the new product to the controller, inside there is a call to the constructor
                            productController.addProduct();
                            break;
                        case 2:
                            //call the function that print all products barcodes
                            productController.GetAllProductBarcode();
                            break;
                    }
                    break;
                case 2: //sub menu - everything about Specific Products
                    specificProductPresentation.ShowSpecificProductMenu();
                    break;
                case 3: //Reports
                    reportPresentation.ShowReportMenu();
                    break;
                case 4: //Categories
                    categoryPresentation.ShowCategoryMenu();
                    break;
                case 5: //SubCategories
                    categoryPresentation.ShowSubCategory();
                    break;
                case 6: //SubSubCategories
                    categoryPresentation.ShowSubSubCategory();
                    break;
                case 7: //Discounts
                    this.ShowDiscountMenu();
                    break;
                case 8: //Super-Li dataBase exist information
                    //addData();

            }

        }
    }

    /** -------------------------------Case 7------------------------------------------------------
     */

    public void ShowDiscountMenu(){
        Scanner option7 = new Scanner(System.in);
        System.out.println("Please choose an option");
        System.out.println("1. Update discount for category in store");
        System.out.println("2. Update discount for product in store");
        System.out.println("3. Update discount for specific product in store");
        System.out.println("4. Update store price");
        int c7 = option7.nextInt();
        switch (c7) {
            case 1: //Update discount for category in store
                System.out.println("Please enter Category:");
                String category4 = reader.nextLine();
                System.out.println("Please enter discount amount:");
                double discount4 = reader.nextDouble();
                System.out.println("Please enter Exp Date yyyy-MM-dd:");
                String start4 = reader.nextLine();
                LocalDateTime start44 = LocalDateTime.parse(start4+"T00:00:00");
                System.out.println("Please enter Exp Date yyyy-MM-dd:");
                String end4 = reader.nextLine();
                LocalDateTime end44 = LocalDateTime.parse(end4+"T00:00:00");
                update_discount_bycategory(category4, start44, end44, discount4);
                break;
            case 2: //Update discount for product in store
                System.out.println("Please enter Product's name:");
                String nam5 = reader.nextLine();
                System.out.println("Please enter discount amount:");
                double discount5 = reader.nextDouble();
                System.out.println("Please enter discount start Date yyyy-MM-dd:");
                String start5 = reader.nextLine();
                LocalDateTime start55 = LocalDateTime.parse(start5+"T00:00:00");
                System.out.println("Please enter discount end Date yyyy-MM-dd:");
                String end5 = reader.nextLine();
                LocalDateTime end55 = LocalDateTime.parse(end5+"T00:00:00");
                update_discount_byproduct(nam5, start55, end55, discount5);
                break;
            case 3: //Update discount for specific product in store
                System.out.println("Please enter Product's name:");
                String nam6 = reader.nextLine();
                System.out.println("Please enter Product's name:");
                int pid66 = reader.nextInt();
                System.out.println("Please enter discount amount:");
                double discount6 = reader.nextDouble();
                System.out.println("Please enter discount start Date yyyy-MM-dd:");
                String start6 = reader.nextLine();
                LocalDateTime start66 = LocalDateTime.parse(start6+"T00:00:00");
                System.out.println("Please enter discount end Date yyyy-MM-dd:");
                String end6 = reader.nextLine();
                LocalDateTime end66 = LocalDateTime.parse(end6+"T00:00:00");
                update_discount_byspecificproduct(nam6 , pid66, start66, end66, discount6);
                break;
            case 4: //Update store price
                System.out.println("Please enter Product's barcode:");
                int Barcode8 = reader.nextInt();
                System.out.println("Please enter Product's new price:");
                double np8 = reader.nextDouble();
                productController.getProductByBarcode(Barcode8).setCostumer_Price(np8);
                break;
        }
    }
    /*public static void addData(){
        productController.addProduct(123, "pasta", "barila", 5, 10,"cooking", "italian","gluten free",5,"barila", 100);
        productController.addProduct(456, "bamba", "osem", 4,5,"snack", "salty", "contain peanuts", 3, "osem", 150);
        productController.addProduct(789, "bisli", "osem", 3,4,"snack","salty", "crunchy", 4,"osem", 200);
        productController.addProduct(112, "kinder", "ferero", 5,10,"candy","chocolate bar", "contain milk", 6, "ferero", 300);
        productController.addProduct(113, "rice", "sugat", 4,6, "cooking", "legums", "contain strach", 7, "sugat", 100);
        LocalDateTime lcs5 = LocalDateTime.parse("2023-04-10T00:00:00");
        LocalDateTime lce5 = LocalDateTime.parse("2023-04-20T00:00:00");
        LocalDateTime lcs10 = LocalDateTime.parse("2023-06-01T00:00:00");
        LocalDateTime lce10 = LocalDateTime.parse("2023-06-10T00:00:00");
        Discount d5 = new Discount(lcs5,lce5,5);
        Discount d10 = new Discount(lcs10, lce10, 10);
        LocalDateTime ex1231 = LocalDateTime.parse("2023-04-11T00:00:00");
        LocalDateTime ex1232 = LocalDateTime.parse("2023-04-12T00:00:00");
        LocalDateTime ex1233 = LocalDateTime.parse("2023-04-13T00:00:00");
        productController.getProductByBarcode(123).addSpecificProduct(123,ex1231, false, null, false, "superlirehovot",6,d5, null);
        productController.getProductByBarcode(123).addSpecificProduct(123,ex1232, true, "sapir", true, "superlirehovot",-1,null, "open package");
        productController.getProductByBarcode(123).addSpecificProduct(123,ex1233, false, null, true, "superlirehovot",-1,null, null);
        LocalDateTime ex4561 = LocalDateTime.parse("2023-08-06T00:00:00");
        productController.getProductByBarcode(456).addSpecificProduct(456,ex4561, false, null, false, "superlirehovot",10,d10, null);
        productController.getProductByBarcode(456).addSpecificProduct(456,ex4561, false, null, false, "superlirehovot",10,d10, null);
        productController.getProductByBarcode(456).addSpecificProduct(456,ex4561, false, null, false, "superlirehovot",10,d10, null);
        LocalDateTime ex7891 = LocalDateTime.parse("2023-10-18T00:00:00");
        productController.getProductByBarcode(789).addSpecificProduct(789,ex7891,true, "liron", true, "superlirehovot", -1,null,"open package");
        productController.getProductByBarcode(789).addSpecificProduct(789,ex7891,true, "liron", true, "superlirehovot", -1,null,"open package");
        LocalDateTime ex1121 = LocalDateTime.parse("2023-10-18T00:00:00");
        productController.getProductByBarcode(112).addSpecificProduct(112,ex1121,true, "liron", true, "superlirehovot", -1,null,"Expired");
        productController.getProductByBarcode(112).addSpecificProduct(112,ex1121,true, "liron", true, "superlirehovot", -1,null,"Expired");
        LocalDateTime ex1131 = LocalDateTime.parse("2023-09-12T00:00:00");
        LocalDateTime ex1133 = LocalDateTime.parse("2023-09-13T00:00:00");
        productController.getProductByBarcode(113).addSpecificProduct(113,ex1131,false, null, false, "superlirehovot", 8,null,null);
        productController.getProductByBarcode(113).addSpecificProduct(113,ex1133,false, null, false, "superlirehovot", 8,null,null);
    }*/

}