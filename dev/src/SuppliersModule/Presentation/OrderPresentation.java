package SuppliersModule.Presentation;

import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.Generator.OrderGenerator;
import SuppliersModule.Business.PaymentTerm;

import java.util.List;

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
            if(!orderController.CheckIfSupplierCanSupplyThisQuantity(productQuantity)) {
                System.out.println("The supplier cannot supply the requested quantity");
                continue;
            }
            orderController.addQuantityOfTheLastEnteredProduct(productQuantity);

            SupplierModulePresentation.checkValidWithMessage("Do you want to insert another product? (y/n)");
        }
        orderController.savePeriodicOrder();
    }

    public void editOrDeletePeriodicOrder() {

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
                    deleteCurPeriodicOrder();
                    break;
            }

        }
    }

    public void deleteCurPeriodicOrder()
    {
        if(ChoosePeriodicOrderThatContainBarcode(0) == null)
            return;

        orderController.deleteCurPeriodicOrder();
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
                    ChangeTheDayForInvite();
                    break;
                case "2":
                    editProductsList();
                    break;
            }

        }
    }

    public void ChangeTheDayForInvite()
    {
        if(ChoosePeriodicOrderThatContainBarcode(0) == null)
            return;

        int day = checkValidDayAndReturn();
        orderController.changeDayForInviteForCurPeriodicOrder(day);
    }

    private void editProductsList(){
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Add products to the list");
            System.out.println("2. Change the quantity of product in the list");
            System.out.println("3. Delete product from the list");
            System.out.println("0. Exit.");
            Choose = SupplierModulePresentation.reader.nextLine();

            switch (Choose) {
                case "1":
                    addProductToTheList();
                    break;
                case "2":
                    ChangeTheQuantityOfProductInTheList();
                    break;
                case "3":
                    DeleteProductFromTheList();
            }
        }
    }

    public void addProductToTheList()
    {
        int[] barcodeAndId = ChoosePeriodicOrderThatContainBarcode(1);
        if(barcodeAndId == null)
            return;

        orderController.addProductToTheListByBarcode(barcodeAndId[0]);
        int productQuantity = SupplierModulePresentation.CheckIntInputAndReturn("Enter the quantity of products you want to order:");

        if(!orderController.CheckIfSupplierCanSupplyThisQuantity(productQuantity)) {
            System.out.println("The supplier cannot supply the requested quantity");
            return;
        }
        orderController.addQuantityOfTheLastEnteredProduct(productQuantity);
    }

    public void ChangeTheQuantityOfProductInTheList()
    {
        int[] BarcodeAndId = ChoosePeriodicOrderThatContainBarcode(0);
        if(BarcodeAndId == null)
            return;

        orderController.findOrderedProduct(BarcodeAndId[0]);
        int productQuantity = SupplierModulePresentation.CheckIntInputAndReturn("Enter the new desired quantity");

        if(!orderController.CheckIfSupplierCanSupplyThisQuantity(productQuantity)) {
            System.out.println("The supplier cannot supply the requested quantity");
            return;
        }

        orderController.changeCurOrderedProductQuantity(productQuantity);
    }

    public void DeleteProductFromTheList()
    {
        int[] BarcodeAndId = ChoosePeriodicOrderThatContainBarcode(0);
        if(BarcodeAndId == null)
            return;

        orderController.deleteOrderedProduct(BarcodeAndId[0]);
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

    public int[] ChoosePeriodicOrderThatContainBarcode(int method)
    {
        int[] barcodeAndId = new int[2];

        barcodeAndId[0] = SupplierModulePresentation.CheckIntInputAndReturn("Enter Barcode please");

        List<String> relevantOrder;
        if(method == 0)
            relevantOrder = orderController.findAllPeriodicOrderThatContainThisBarcode(barcodeAndId[0]);
        else
            relevantOrder = orderController.findAllPeriodicOrderThatCanBeContainThisBarcode(barcodeAndId[0]);

        if(relevantOrder.size() == 0)
        {
            System.out.println("There is no periodic order that contains the barcode");
            return null;
        }

        for(String currOrderString : relevantOrder)
            System.out.println(currOrderString);

        while (true)
        {
            barcodeAndId[1] = SupplierModulePresentation.CheckIntInputAndReturn("Enter Periodic Order id");
            if (orderController.findPeriodicOrder(barcodeAndId[0], barcodeAndId[1]))
                break;
            System.out.println("Wrong Periodic Order id");
        }

        if(orderController.checkInvalidDayForChange())
        {
            System.out.println("Periodic order cannot change in the day of ordering");
            return null;
        }

        return barcodeAndId;
    }
}
