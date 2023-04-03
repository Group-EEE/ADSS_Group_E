import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        CategoryController categoryController = new CategoryController();
        ReportController reportController = new ReportController();
        while(true){
            Scanner option = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Products");
            System.out.println("2. Specific Products");
            System.out.println("3. Reports");
            System.out.println("4. Categories");
            System.out.println("5. SubCategories");
            System.out.println("6. SubCategories");
            System.out.println("7. Discounts");
            int c = option.nextInt();
            switch (c) {
                case 1:
                    Scanner option1 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Add new product to the store");
                    System.out.println("2. Get all products' barcode");
                    int c1 = option1.nextInt();
                    switch (c1) {
                        case 1:
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
                            Scanner sa = new Scanner(System.in);
                            System.out.println("Please enter Shelf amount:");
                            int Shelfamount = sa.nextInt();
                            Scanner wa = new Scanner(System.in);
                            System.out.println("Please enter Warehouse amount:");
                            int Warehouseamount = wa.nextInt();
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
                            productController.addProduct(Barcode, Name, SupplierName, Supplierprice,
                                    Costumerprice, Shelfamount,Warehouseamount, Category,Subcategory, Subsubcategory,
                                    Supplydays,Manufacturer,Minimumamount);
                            break;
                        case 2:
                            productController.GetAllProductBarcode();
                            break;
                    }
                    break;
                case 2:
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
                            if(start.compareTo("null")==0){
                                start = "1111-11-11";
                            }
                            LocalDateTime startdate = LocalDateTime.parse(start + "T00:00:00");
                            Scanner e = new Scanner(System.in);
                            System.out.println("If the product has discount please enter end Date yyyy-MM-dd else press null:");
                            String end = s.nextLine();
                            if(end.compareTo("null")==0){
                                end = "1111-11-11";
                            }
                            LocalDateTime enddate = LocalDateTime.parse(end+"T00:00:00");
                            Product p = productController.getProductByBarcode(Barcode);
                            if(p!=null) {
                                Discount dd = new Discount(startdate, enddate, aDiscount);
                                p.addSpecificProduct(Barcode,aExp_date, aDefective, aDefect_report_by,
                                aInWarehouse,aStoreBranch,aLocationInStore,dd);
                            }
                            else{
                                System.out.println("Product doesn't exist in store!");
                            }
                            break;
                        case 2:
                            Scanner ba = new Scanner(System.in);
                            System.out.println("Please enter product's Barcode:");
                            int barcode = ba.nextInt();
                            Scanner sp = new Scanner(System.in);
                            System.out.println("Please enter specific product id:");
                            int spid = sp.nextInt();
                            Product np = productController.getProductByBarcode(barcode);
                            if(np!=null){
                                np.removeSpecificProduct(spid);
                            }
                            else{
                                System.out.println("Product wasn't found!");
                            }
                            break;
                        case 3:
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
                                System.out.println("Please enter defect reporter:");
                                String dtype = dt3.nextLine();
                                np3.add_defected_specific_product(spid3, dere, dtype);
                            }
                            else{
                                System.out.println("Product wasn't found!");
                            }
                            break;
                        case 4:
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
                        case 5:
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
                        case 6:
                            Scanner ba6 = new Scanner(System.in);
                            System.out.println("Please enter product's Barcode:");
                            int barcode6 = ba6.nextInt();
                            Scanner sp6 = new Scanner(System.in);
                            System.out.println("Please enter specific product id:");
                            int spid6 = sp6.nextInt();
                            productController.change_Shelf_Warehouse(barcode6, spid6);
                            break;
                    }
                    break;
                case 3: //Reports
                    Scanner option3 = new Scanner(System.in);
                    System.out.println("Please choose an option");
                    System.out.println("1. Get all reports in system");
                    System.out.println("2. Issue order products report");
                    System.out.println("3. Issue EXP/ defected products report");
                    System.out.println("4. Issue current supply");
                    System.out.println("5. Issue report by category");
                    int c3 = option3.nextInt();
                    switch (c3) {
                        case 1:
                            reportController.GetAllIssuedReports();
                            break;
                        case 2:
                            Scanner reporter = new Scanner(System.in);
                            System.out.println("Please enter Issue's reporter's name:");
                            String r = reporter.nextLine();
                            OrderReport orderReport = new OrderReport(r);
                            break;
                        case 3:
                            Scanner reporter3 = new Scanner(System.in);
                            System.out.println("Please enter Issue's reporter's name:");
                            String r3 = reporter3.nextLine();
                            CurrSupplyReport currSupplyReport = new CurrSupplyReport(r3);
                            break;
                        case 4:
                            Scanner reporter4 = new Scanner(System.in);
                            System.out.println("Please enter Issue's reporter's name:");
                            String r4 = reporter4.nextLine();
                            ExpOrDefectReport expOrDefectReport = new ExpOrDefectReport(r4);
                            break;
                        case 5:
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
                        case 1:
                            categoryController.PrintCategorysInSystem();
                            break;
                        case 2:
                            Scanner newcat = new Scanner(System.in);
                            System.out.println("Please enter new Category name:");
                            String cat = newcat.nextLine();
                            CategoryController.addCategory(cat);
                            break;
                        case 3:
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
                        case 1:
                            Scanner newcat = new Scanner(System.in);
                            System.out.println("Please enter new Category name:");
                            String cat = newcat.nextLine();
                            Scanner newsubcat = new Scanner(System.in);
                            System.out.println("Please enter new SubCategory name:");
                            String subcat = newsubcat.nextLine();
                            CategoryController.addSubCategory(cat,subcat);
                            break;
                        case 2:
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
                        case 1:
                            Scanner newsubcat1 = new Scanner(System.in);
                            System.out.println("Please enter new SubCategory name:");
                            String subcat1 = newsubcat1.nextLine();
                            Scanner newsubsubcat1 = new Scanner(System.in);
                            System.out.println("Please enter new SubCategory name:");
                            String subsubcat1 = newsubsubcat1.nextLine();
                            CategoryController.addSubSubCategory(subcat1, subsubcat1);
                            break;
                        case 2:
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
                        case 1:
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
                        case 2:
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
                        case 3:
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
                        case 4:
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
                        case 5:
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
                        case 6:
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
                        case 7:
                            Scanner barcode = new Scanner(System.in);
                            System.out.println("Please enter Product's barcode:");
                            int Barcode = barcode.nextInt();
                            Scanner price = new Scanner(System.in);
                            System.out.println("Please enter Product's new price:");
                            double np = price.nextDouble();
                            productController.getProductByBarcode(Barcode).setSupplier_Price(np);
                            break;
                        case 8:
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
            }

        }
    }

}