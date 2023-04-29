import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//the main represents the "SuperLi" supply system. it is a UI that helps the employee gets information
//of all the products, reports, discounts etc to help him control the supply in store
public class Main {
    static ProductController productController = new ProductController(); //create a product controller
    static CategoryController categoryController = new CategoryController(); //create a category controller
    static ReportController reportController = new ReportController(); //create a report controller
    public static void main(String[] args) {
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
                            //get from the warehouse manager all the information needed to create new product
                            Scanner b = new Scanner(System.in);
                            System.out.println("Please enter Barcode:");
                            int Barcode = b.nextInt();
                            Scanner n = new Scanner(System.in);
                            System.out.println("Please enter Name:");
                            String Name = n.nextLine();
                            Scanner sn = new Scanner(System.in);
                            System.out.println("Please enter Supplier name:");
                            String SupplierName = sn.nextLine();
                            Scanner sp = new Scanner(System.in);
                            System.out.println("Please enter Supplier price:");
                            Double Supplierprice = sp.nextDouble();
                            Scanner cp = new Scanner(System.in);
                            System.out.println("Please enter Costumer price:");
                            Double Costumerprice = cp.nextDouble();
                            Scanner ca = new Scanner(System.in);
                            System.out.println("Please enter Category:");
                            String Category = ca.nextLine();
                            Scanner sca = new Scanner(System.in);
                            System.out.println("Please enter Subcategory:");
                            String Subcategory = sca.nextLine();
                            Scanner ssca = new Scanner(System.in);
                            System.out.println("Please enter Subsubcategory:");
                            String Subsubcategory = ssca.nextLine();
                            Scanner sd = new Scanner(System.in);
                            System.out.println("Please enter Supply days:");
                            int Supplydays = sd.nextInt();
                            Scanner m = new Scanner(System.in);
                            System.out.println("Please enter Manufacturer:");
                            String Manufacturer = m.nextLine();
                            Scanner ma = new Scanner(System.in);
                            System.out.println("Please enter Minimum amount:");
                            int Minimumamount = ma.nextInt();
                            //add the new product to the controller, inside there is a call to the constructor
                            productController.addProduct(Barcode, Name, SupplierName, Supplierprice,
                                    Costumerprice, Category,Subcategory, Subsubcategory,
                                    Supplydays,Manufacturer,Minimumamount);
                            break;
                        case 2:
                            //call the function that print all products barcodes
                            productController.GetAllProductBarcode();
                            break;
                    }
                    break;
                case 2: //sub menu - everything about Specific Products
                    Scanner option2 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Add new specific product to store");
                    System.out.println("2. Remove specific product from store");
                    System.out.println("3. Report defected specific product");
                    System.out.println("4. Find specific product in store");
                    System.out.println("5. Change specific product place in store");
                    System.out.println("6. Transfer specific product from/to warehouse");
                    int c2 = option2.nextInt();
                    switch (c2) {
                        case 1:
                            //get from the warehouse manager all the information needed to create new spec. product
                            Scanner b = new Scanner(System.in);
                            System.out.println("Please enter Barcode:");
                            int Barcode = b.nextInt();
                            Scanner d = new Scanner(System.in);
                            System.out.println("Please enter Exp Date yyyy-MM-dd:");
                            String date = d.nextLine();
                            LocalDateTime aExp_date = LocalDateTime.parse(date+"T00:00:00");
                            Scanner n = new Scanner(System.in);
                            System.out.println("Is the product defective? - please enter true/false:");
                            boolean aDefective = n.nextBoolean();
                            Scanner rd = new Scanner(System.in);
                            System.out.println("Please enter Defect reporter name else press null:");
                            String aDefect_report_by = rd.nextLine();
                            Scanner dt = new Scanner(System.in);
                            System.out.println("Please enter Defect type else press null:");
                            String  defectype = dt.nextLine();
                            Scanner wh = new Scanner(System.in);
                            System.out.println("Is the product stored in warehouse? - please enter true/false:");
                            boolean aInWarehouse = wh.nextBoolean();
                            Scanner bn = new Scanner(System.in);
                            System.out.println("Please enter store branch name:");
                            String aStoreBranch = bn.nextLine();
                            Scanner ls = new Scanner(System.in);
                            System.out.println("Please enter product Shelf number - if the product located in warehouse enter -1:");
                            int aLocationInStore = ls.nextInt();
                            Scanner wa = new Scanner(System.in);
                            System.out.println("Please enter discount amount:");
                            double aDiscount = wa.nextDouble();
                            Scanner s = new Scanner(System.in);
                            System.out.println("If the product has discount please enter start Date yyyy-MM-dd else press null:");
                            String start = s.nextLine();
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
                            Product p = productController.getProductByBarcode(Barcode); //find the general product
                            if(p!=null) { //general product found
                                Discount dd = new Discount(startdate, enddate, aDiscount); //create the discount
                                //use function add spec. product of this general product thar found. call to
                                //specific product constructor inside
                                p.addSpecificProduct(Barcode,aExp_date, aDefective, aDefect_report_by,
                                aInWarehouse,aStoreBranch,aLocationInStore,dd, defectype);
                            }
                            else{ //general product wasnt found
                                System.out.println("Product doesn't exist in store!");
                            }
                            break;
                        case 2: //Remove specific product from store
                            Scanner ba = new Scanner(System.in);
                            System.out.println("Please enter product's Barcode:");
                            int barcode = ba.nextInt();
                            Scanner sp = new Scanner(System.in);
                            System.out.println("Please enter specific product id:");
                            int spid = sp.nextInt();
                            Product np = productController.getProductByBarcode(barcode);
                            if(np!=null){ //general product found
                                np.removeSpecificProduct(spid);
                            }
                            else{//general product wasnt found
                                System.out.println("Product wasn't found!");
                            }
                            break;
                        case 3: //Report defected specific product
                            Scanner ba3 = new Scanner(System.in);
                            System.out.println("Please enter product's Barcode:");
                            int barcode3 = ba3.nextInt();
                            Scanner sp3 = new Scanner(System.in);
                            System.out.println("Please enter specific product id:");
                            int spid3 = sp3.nextInt();
                            Product np3 = productController.getProductByBarcode(barcode3);
                            if(np3!=null){
                                Scanner rd3 = new Scanner(System.in);
                                System.out.println("Please enter defect reporter:");
                                String dere = rd3.nextLine();
                                Scanner dt3 = new Scanner(System.in);
                                System.out.println("Please enter defect type:");
                                String dtype = dt3.nextLine();
                                np3.add_defected_specific_product(spid3, dere, dtype);
                            }
                            else{
                                System.out.println("Product wasn't found!");
                            }
                            break;
                        case 4: //Find specific product in store
                            Scanner ba4 = new Scanner(System.in);
                            System.out.println("Please enter product's Barcode:");
                            int barcode4 = ba4.nextInt();
                            Scanner sp4 = new Scanner(System.in);
                            System.out.println("Please enter specific product id:");
                            int spid4 = sp4.nextInt();
                            Product np4 = productController.getProductByBarcode(barcode4);
                            if(np4!=null){
                                np4.getProductLocationInStore(spid4);
                            }
                            else{
                                System.out.println("Product wasn't found!");
                            }
                            break;
                        case 5: //Change specific product place in store
                            Scanner ba5 = new Scanner(System.in);
                            System.out.println("Please enter product's Barcode:");
                            int barcode5 = ba5.nextInt();
                            Scanner sp5 = new Scanner(System.in);
                            System.out.println("Please enter specific product id:");
                            int spid5 = sp5.nextInt();
                            Product np5 = productController.getProductByBarcode(barcode5);
                            if(np5!=null){
                                SpecificProduct thep5 = np5.getSpecificProduct(spid5);
                                Scanner location = new Scanner(System.in);
                                System.out.println("Please enter product's new shelf number in store:");
                                int location5 = location.nextInt();
                                thep5.setLocation_in_Store(location5);
                            }
                            else{
                                System.out.println("Product wasn't found!");
                            }
                            break;
                        case 6: //Transfer specific product from/to warehouse
                            Scanner ba6 = new Scanner(System.in);
                            System.out.println("Please enter product's Barcode:");
                            int barcode6 = ba6.nextInt();
                            Scanner sp6 = new Scanner(System.in);
                            System.out.println("Please enter specific product id:");
                            int spid6 = sp6.nextInt();
                            productController.change_Shelf_Warehouse(spid6, barcode6);
                            break;
                    }
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
                    System.out.println("1. Update discount for category from a specific supplier");
                    System.out.println("2. Update discount for product from a specific supplier");
                    System.out.println("3. Update discount for specific product from a specific supplier");
                    System.out.println("4. Update discount for category in store");
                    System.out.println("5. Update discount for product in store");
                    System.out.println("6. Update discount for specific product in store");
                    System.out.println("7. Update supplier price");
                    System.out.println("8. Update store price");
                    int c7 = option7.nextInt();
                    switch (c7) {
                        case 1: //Update discount for category from a specific supplier
                            Scanner cat = new Scanner(System.in);
                            System.out.println("Please enter Category:");
                            String category = cat.nextLine();
                            Scanner sup = new Scanner(System.in);
                            System.out.println("Please enter Supplier name:");
                            String supplier = sup.nextLine();
                            Scanner d = new Scanner(System.in);
                            System.out.println("Please enter discount amount:");
                            double discount = d.nextDouble();
                            Scanner s = new Scanner(System.in);
                            System.out.println("Please enter discount start Date yyyy-MM-dd:");
                            String start = s.nextLine();
                            LocalDateTime start1 = LocalDateTime.parse(start+"T00:00:00");
                            Scanner e = new Scanner(System.in);
                            System.out.println("Please enter discount end Date yyyy-MM-dd:");
                            String end = e.nextLine();
                            LocalDateTime end1 = LocalDateTime.parse(end+"T00:00:00");
                            Discount dis = new Discount(start1, end1, discount);
                            dis.update_discount_bycategory_from_supplier(category, supplier);
                            break;
                        case 2: //Update discount for product from a specific supplier
                            Scanner name2 = new Scanner(System.in);
                            System.out.println("Please enter Product's name:");
                            String nam2 = name2.nextLine();
                            Scanner sup2 = new Scanner(System.in);
                            System.out.println("Please enter Supplier name:");
                            String supplier2 = sup2.nextLine();
                            Scanner d2 = new Scanner(System.in);
                            System.out.println("Please enter discount amount:");
                            double discount2 = d2.nextDouble();
                            Scanner s2 = new Scanner(System.in);
                            System.out.println("Please enter discount start Date yyyy-MM-dd:");
                            String start2 = s2.nextLine();
                            LocalDateTime start22 = LocalDateTime.parse(start2+"T00:00:00");
                            Scanner e2 = new Scanner(System.in);
                            System.out.println("Please enter discount end Date yyyy-MM-dd:");
                            String end2 = e2.nextLine();
                            LocalDateTime end22 = LocalDateTime.parse(end2+"T00:00:00");
                            Discount dis2 = new Discount(start22, end22, discount2);
                            dis2.update_discount_byproduct_from_supplier(nam2, supplier2);
                            break;
                        case 3: //Update discount for specific product from a specific supplier
                            Scanner name3 = new Scanner(System.in);
                            System.out.println("Please enter Product's name:");
                            String nam3 = name3.nextLine();
                            Scanner pid3 = new Scanner(System.in);
                            System.out.println("Please enter Product's name:");
                            int pid33 = pid3.nextInt();
                            Scanner sup3 = new Scanner(System.in);
                            System.out.println("Please enter Supplier name:");
                            String supplier3 = sup3.nextLine();
                            Scanner d3 = new Scanner(System.in);
                            System.out.println("Please enter discount amount:");
                            double discount3 = d3.nextDouble();
                            Scanner s3 = new Scanner(System.in);
                            System.out.println("Please enter discount start Date yyyy-MM-dd:");
                            String start3 = s3.nextLine();
                            LocalDateTime start33 = LocalDateTime.parse(start3+"T00:00:00");
                            Scanner e3 = new Scanner(System.in);
                            System.out.println("Please enter discount end Date yyyy-MM-dd:");
                            String end3 = e3.nextLine();
                            LocalDateTime end33 = LocalDateTime.parse(end3+"T00:00:00");
                            Discount dis3 = new Discount(start33, end33, discount3);
                            dis3.update_discount_byspecificproduct_from_supplier(nam3 , pid33, supplier3);
                            break;
                        case 4: //Update discount for category in store
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
                        case 5: //Update discount for product in store
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
                        case 6: //Update discount for specific product in store
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
                        case 7: //Update supplier price
                            Scanner barcode = new Scanner(System.in);
                            System.out.println("Please enter Product's barcode:");
                            int Barcode = barcode.nextInt();
                            Scanner price = new Scanner(System.in);
                            System.out.println("Please enter Product's new price:");
                            double np = price.nextDouble();
                            productController.getProductByBarcode(Barcode).setSupplier_Price(np);
                            break;
                        case 8: //Update store price
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
                    addData();

            }

        }
    }

    public static void addData(){
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
    }

}