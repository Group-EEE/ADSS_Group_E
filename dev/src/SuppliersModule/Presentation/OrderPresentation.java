package SuppliersModule.Presentation;

import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.Generator.OrderGenerator;

public class OrderPresentation {
    OrderController orderController = OrderController.getInstance();

    public void createPeriodicOrder() {
        System.out.println("\nWelcome to the PeriodicOrder Generator.");
        while (true){
            System.out.println("Enter supplier number");
            String supplierNum = SupplierModulePresentation.reader.nextLine();
            if(orderController.enterSupplier(supplierNum))
                break;
        }
        enterThePermanentSupplyDay();
        buildProductsList();
    }

    public void enterThePermanentSupplyDay(){
        boolean IsValid = false;
        int yourDay = 0;
        while (!IsValid) {
            System.out.println("Enter day (enter number):");
            System.out.println("1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday");
            try {yourDay = Integer.parseInt(SupplierModulePresentation.reader.nextLine()) - 1;}
            catch (NumberFormatException error) {continue;}
            if (yourDay < 0 || yourDay > 6) continue;
            IsValid = true;
        }
        orderController.enterPermanentDay(yourDay);
    }


    /**
     * Building the products list of the order. (Initializes the static variables "ProductsInOrder", "ProductsQuantity")
     */
    private void buildProductsList(){
        orderController.createPeriodicOrder();
        SupplierModulePresentation.yourChoice = "y";
        String catalogNum;
        int productQuantity;
        while (SupplierModulePresentation.yourChoice.equals("y")) {

            System.out.println("Please enter the supplier's product catalog number: ");
            catalogNum = SupplierModulePresentation.reader.nextLine();

            if(orderController.addProductToTheList(catalogNum))
                continue;

            productQuantity = SupplierModulePresentation.CheckIntInputAndReturn("Enter the quantity of products you want to order:");
            orderController.addQuantityOfTheLastEnteredProduct(productQuantity);

            SupplierModulePresentation.checkValidWithMessage("Do you want to insert another product? (y/n)");
        }
    }
}
