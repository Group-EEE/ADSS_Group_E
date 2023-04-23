package InventoryModule;

import InventoryModule.Presentation.SpecificProductPresentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//the main represents the "SuperLi" supply system. it is a UI that helps the employee gets information
//of all the products, reports, discounts etc to help him control the supply in store
public class InventoryModulePresentation {
    static Scanner reader;
    static String st;

    ProductController productController; //create a product controller
    CategoryController categoryController; //create a category controller
    ReportController reportController; //create a report controller

    SpecificProductPresentation specificProductPresentation;

    public InventoryModulePresentation() {
        reader = new Scanner(System.in);
        productController = new ProductController(); //create a product controller
        categoryController = new CategoryController(); //create a category controller
        reportController = new ReportController(); //create a report controller
        specificProductPresentation = new SpecificProductPresentation();
    }

    public void Start(){
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
                    Scanner option3 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Get all reports in system");
                    System.out.println("2. Issue order products report");
                    System.out.println("3. Issue current supply");
                    System.out.println("4. Issue EXP/ defected products report");
                    System.out.println("5. Issue report by category");
                    int c3 = option3.nextInt();
                    switch (c3) {
                        case 1: //Get all reports in system
                            reportController.GetAllIssuedReports();
                            break;
                        case 2: //Issue order products report
                            Scanner reporter = new Scanner(System.in);
                            System.out.println("Please enter Issue's reporter's name:");
                            String r = reporter.nextLine();
                            OrderReport orderReport = new OrderReport(r);
                            break;
                        case 3: //Issue current supply
                            Scanner reporter3 = new Scanner(System.in);
                            System.out.println("Please enter Issue's reporter's name:");
                            String r3 = reporter3.nextLine();
                            CurrSupplyReport currSupplyReport = new CurrSupplyReport(r3);
                            break;
                        case 4: //Issue EXP/ defected products report
                            Scanner reporter4 = new Scanner(System.in);
                            System.out.println("Please enter Issue's reporter's name:");
                            String r4 = reporter4.nextLine();
                            ExpOrDefectReport expOrDefectReport = new ExpOrDefectReport(r4);
                            break;
                        case 5: //Issue report by category
                            Scanner reporter5 = new Scanner(System.in);
                            System.out.println("Please enter Issue's reporter's name:");
                            String r5 = reporter5.nextLine();
                            Scanner num = new Scanner(System.in);
                            System.out.println("Please enter the number of categories:");
                            int number = num.nextInt();
                            List<String> cate = new ArrayList<String>();
                            for(int i=0; i<number; i++){
                                Scanner cat = new Scanner(System.in);
                                System.out.println("Please enter Issue's Category name:");
                                String category = cat.nextLine();
                                cate.add(category);
                            }
                            ByCategoryReport byCategoryReport = new ByCategoryReport(r5, cate);
                            break;
                    }
                    break;
                case 4: //Categories
                    Scanner option4 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Get all category names");
                    System.out.println("2. Add new category");
                    System.out.println("3. Remove category");
                    int c4 = option4.nextInt();
                    switch (c4) {
                        case 1: //Get all category names
                            categoryController.PrintCategorysInSystem();
                            break;
                        case 2: //Add new category
                            Scanner newcat = new Scanner(System.in);
                            System.out.println("Please enter new Category name:");
                            String cat = newcat.nextLine();
                            CategoryController.addCategory(cat);
                            break;
                        case 3: //Remove category
                            Scanner newcat3 = new Scanner(System.in);
                            System.out.println("Please enter Category name to remove:");
                            String cat3 = newcat3.nextLine();
                            categoryController.removeCategory(cat3);
                            break;
                    }
                    break;
                case 5: //SubCategories
                    Scanner option5 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Add new subcategory");
                    System.out.println("2. Remove subcategory");
                    int c5 = option5.nextInt();
                    switch (c5) {
                        case 1: //Add new subcategory
                            Scanner newcat = new Scanner(System.in);
                            System.out.println("Please enter new Category name:");
                            String cat = newcat.nextLine();
                            Scanner newsubcat = new Scanner(System.in);
                            System.out.println("Please enter new SubCategory name:");
                            String subcat = newsubcat.nextLine();
                            CategoryController.addSubCategory(cat,subcat);
                            break;
                        case 2: //Remove subcategory
                            Scanner newcat2 = new Scanner(System.in);
                            System.out.println("Please enter Category name:");
                            String cat2 = newcat2.nextLine();
                            Scanner newsubcat2 = new Scanner(System.in);
                            System.out.println("Please enter SubCategory name to remove:");
                            String subcat2 = newsubcat2.nextLine();
                            categoryController.removeSubCategory(cat2,subcat2);
                            break;
                    }
                    break;
                case 6: //SubSubCategories
                    Scanner option6 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Add new subsubcategory");
                    System.out.println("2. Remove subsubcategory");
                    int c6 = option6.nextInt();
                    switch (c6) {
                        case 1: //Add new subsubcategory
                            Scanner newsubcat1 = new Scanner(System.in);
                            System.out.println("Please enter new SubCategory name:");
                            String subcat1 = newsubcat1.nextLine();
                            Scanner newsubsubcat1 = new Scanner(System.in);
                            System.out.println("Please enter new SubCategory name:");
                            String subsubcat1 = newsubsubcat1.nextLine();
                            CategoryController.addSubSubCategory(subcat1, subsubcat1);
                            break;
                        case 2: //Remove subsubcategory
                            Scanner newcat2 = new Scanner(System.in);
                            System.out.println("Please enter Category name:");
                            String cat2 = newcat2.nextLine();
                            Scanner newsubcat2 = new Scanner(System.in);
                            System.out.println("Please enter SubCategory name:");
                            String subcat2 = newsubcat2.nextLine();
                            Scanner newsubsubcat2 = new Scanner(System.in);
                            System.out.println("Please enter SubCategory name to remove:");
                            String subsubcat2 = newsubsubcat2.nextLine();
                            categoryController.removeSubSubCategory(subsubcat2, subcat2, cat2);
                            break;
                    }
                    break;
                case 7: //Discounts
                    Scanner option7 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Update discount for category in store");
                    System.out.println("2. Update discount for product in store");
                    System.out.println("3. Update discount for specific product in store");
                    System.out.println("4. Update supplier price");
                    System.out.println("5. Update store price");
                    int c7 = option7.nextInt();
                    switch (c7) {
                        case 1: //Update discount for category in store
                            Scanner cat4 = new Scanner(System.in);
                            System.out.println("Please enter Category:");
                            String category4 = cat4.nextLine();
                            Scanner d4 = new Scanner(System.in);
                            System.out.println("Please enter discount amount:");
                            double discount4 = d4.nextDouble();
                            Scanner s4 = new Scanner(System.in);
                            System.out.println("Please enter Exp Date yyyy-MM-dd:");
                            String start4 = s4.nextLine();
                            LocalDateTime start44 = LocalDateTime.parse(start4+"T00:00:00");
                            Scanner e4 = new Scanner(System.in);
                            System.out.println("Please enter Exp Date yyyy-MM-dd:");
                            String end4 = e4.nextLine();
                            LocalDateTime end44 = LocalDateTime.parse(end4+"T00:00:00");
                            Discount dis4 = new Discount(start44, end44, discount4);
                            dis4.update_discount_bycategory(category4);
                            break;
                        case 2: //Update discount for product in store
                            Scanner name5 = new Scanner(System.in);
                            System.out.println("Please enter Product's name:");
                            String nam5 = name5.nextLine();
                            Scanner d5 = new Scanner(System.in);
                            System.out.println("Please enter discount amount:");
                            double discount5 = d5.nextDouble();
                            Scanner s5 = new Scanner(System.in);
                            System.out.println("Please enter discount start Date yyyy-MM-dd:");
                            String start5 = s5.nextLine();
                            LocalDateTime start55 = LocalDateTime.parse(start5+"T00:00:00");
                            Scanner e5 = new Scanner(System.in);
                            System.out.println("Please enter discount end Date yyyy-MM-dd:");
                            String end5 = e5.nextLine();
                            LocalDateTime end55 = LocalDateTime.parse(end5+"T00:00:00");
                            Discount dis5 = new Discount(start55, end55, discount5);
                            dis5.update_discount_byproduct(nam5);
                            break;
                        case 3: //Update discount for specific product in store
                            Scanner name6 = new Scanner(System.in);
                            System.out.println("Please enter Product's name:");
                            String nam6 = name6.nextLine();
                            Scanner pid6 = new Scanner(System.in);
                            System.out.println("Please enter Product's name:");
                            int pid66 = pid6.nextInt();
                            Scanner d6 = new Scanner(System.in);
                            System.out.println("Please enter discount amount:");
                            double discount6 = d6.nextDouble();
                            Scanner s6 = new Scanner(System.in);
                            System.out.println("Please enter discount start Date yyyy-MM-dd:");
                            String start6 = s6.nextLine();
                            LocalDateTime start66 = LocalDateTime.parse(start6+"T00:00:00");
                            Scanner e6 = new Scanner(System.in);
                            System.out.println("Please enter discount end Date yyyy-MM-dd:");
                            String end6 = e6.nextLine();
                            LocalDateTime end66 = LocalDateTime.parse(end6+"T00:00:00");
                            Discount dis6 = new Discount(start66, end66, discount6);
                            dis6.update_discount_byspecificproduct(nam6 , pid66);
                            break;
                        case 4: //Update supplier price
                            Scanner barcode = new Scanner(System.in);
                            System.out.println("Please enter Product's barcode:");
                            int Barcode = barcode.nextInt();
                            Scanner price = new Scanner(System.in);
                            System.out.println("Please enter Product's new price:");
                            double np = price.nextDouble();
                            productController.getProductByBarcode(Barcode).setSupplier_Price(np);
                            break;
                        case 5: //Update store price
                            Scanner barcode8 = new Scanner(System.in);
                            System.out.println("Please enter Product's barcode:");
                            int Barcode8 = barcode8.nextInt();
                            Scanner price8 = new Scanner(System.in);
                            System.out.println("Please enter Product's new price:");
                            double np8 = price8.nextDouble();
                            productController.getProductByBarcode(Barcode8).setCostumer_Price(np8);
                            break;
                    }
                    break;
                case 8: //Super-Li dataBase exist information
                    //addData();

            }

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