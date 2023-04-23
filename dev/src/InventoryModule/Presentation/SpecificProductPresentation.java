package InventoryModule.Presentation;

import InventoryModule.ProductController;

import java.util.Scanner;

public class SpecificProductPresentation {
    private ProductController productController;
    public SpecificProductPresentation(){
        productController = ProductController.getInstance();
    }
    public void ShowSpecificProductMenu(){
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
                productController.addspecificproduct();
                break;
            case 2: //Remove specific product from store
                productController.removespecificproduct();
                break;
            case 3: //Report defected specific product
                productController.reportdefectedspecificproduct();
                break;
            case 4: //Find specific product in store
                productController.getspecifproductlocationinstore();
                break;
            case 5: //Change specific product place in store
                productController.changespecificproductlocationinstore();
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
    }
}
