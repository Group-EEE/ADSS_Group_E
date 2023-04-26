package SuppliersModule.Presentation;

import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.Generator.OrderGenerator;
import SuppliersModule.Business.PaymentTerm;

public class OrderPresentation {
    private final OrderController orderController = OrderController.getInstance();

    private String Choose;

    public void mainManu(){
        System.out.println("\nWelcome to the PeriodicOrders system.");
        Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Create new Periodic Order");
            System.out.println("2. Edit/delete existing Periodic Order");
            System.out.println("0. Exit.");
            Choose = SupplierModulePresentation.reader.nextLine();

            switch (Choose) {
                case "1":
                    createPeriodicOrder();
                    break;
                case "2":
                    editOrDeletePeriodicOrder();
                    break;
            }
        }
    }

    public void createPeriodicOrder() {
        System.out.println("\nWelcome to the PeriodicOrder Generator.");
        while (true){
            System.out.println("Enter supplier number");
            String supplierNum = SupplierModulePresentation.reader.nextLine();
            if(orderController.enterSupplier(supplierNum))
                break;
            System.out.println("Wrong supplier num");
        }
        orderController.createPeriodicOrder();
        enterThePermanentSupplyDay();
        buildProductsList();
    }

    public void enterThePermanentSupplyDay(){
        int yourDay = checkValidDayAndReturn();
        orderController.enterPermanentDay(yourDay);
    }


    /**
     * Building the products list of the order. (Initializes the static variables "ProductsInOrder", "ProductsQuantity")
     */
    private void buildProductsList(){
        SupplierModulePresentation.yourChoice = "y";
        String catalogNum;
        int productQuantity;
        while (SupplierModulePresentation.yourChoice.equals("y")) {

            System.out.println("Please enter the supplier's product catalog number: ");
            catalogNum = SupplierModulePresentation.reader.nextLine();

            if(!orderController.addProductToTheList(catalogNum))
                continue;

            productQuantity = SupplierModulePresentation.CheckIntInputAndReturn("Enter the quantity of products you want to order:");
            orderController.addQuantityOfTheLastEnteredProduct(productQuantity);

            SupplierModulePresentation.checkValidWithMessage("Do you want to insert another product? (y/n)");
        }
        orderController.savePeriodicOrder();
    }

    public void editOrDeletePeriodicOrder() {

        while (true) {
            int id = SupplierModulePresentation.CheckIntInputAndReturn("Enter Periodic Order id");
            if (orderController.findPeriodicOrder(id))
                break;
            System.out.println("Wrong Periodic Order id");
        }

        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Edit Periodic Order details");
            System.out.println("2. Delete Periodic Order");
            System.out.println("0. Exit.");
            Choose = SupplierModulePresentation.reader.nextLine();

            switch (Choose) {
                case "1":
                    editPeriodicOrder();
                    break;
                case "2":
                    orderController.deleteCurPeriodicOrder();
                    break;
            }

        }
    }

    private void editPeriodicOrder(){
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Change the day for invite");
            System.out.println("2. Edit products list");
            System.out.println("0. Exit.");
            Choose = SupplierModulePresentation.reader.nextLine();

            switch (Choose) {
                case "1":
                    int day = checkValidDayAndReturn();
                    orderController.changeDayForInviteForCurPeriodicOrder(day);
                    break;
                case "2":
                    editProductsList();
                    break;
            }

        }
    }

    private void editProductsList(){
        orderController.setCurOrder();
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Add products to the list");
            System.out.println("2. Change the quantity of product in the list");
            System.out.println("3. Delete product from the list");
            System.out.println("0. Exit.");
            Choose = SupplierModulePresentation.reader.nextLine();

            switch (Choose) {
                case "1":
                    buildProductsList();
                    break;
                case "2":
                    findOrderedProduct();
                    int quantity = SupplierModulePresentation.CheckIntInputAndReturn("Enter the new desired quantity");
                    orderController.changeCurOrderedProductQuantity(quantity);
                    break;
                case "3":
                    String catalogNum = findOrderedProduct();
                    orderController.deleteOrderedProduct(catalogNum);
            }

        }
    }


    public int checkValidDayAndReturn(){
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
        return yourDay;
    }

    public String findOrderedProduct(){
        String catalogNum;
        do {

            System.out.println("Please enter the supplier's product catalog number: ");
            catalogNum = SupplierModulePresentation.reader.nextLine();

        } while (orderController.findOrderedProduct(catalogNum));
        orderController.findOrderedProduct(catalogNum);
        return catalogNum;
    }
}
