package SuppliersModule.PresentationCLI;

import SuppliersModule.Business.Controllers.OrderController;
import java.util.List;

/**
 * The class OrderPresentation responsibility on order menu
 */
public class OrderCLI {
    private final OrderController orderController = OrderController.getInstance();
    private String Choose;


    /**
     * Create periodic order
     */
    public void createPeriodicOrder() {
        System.out.println("\nWelcome to the PeriodicOrder Generator.");
        while (true){
            System.out.println("Enter supplier number");
            String supplierNum = SupplierManagerCLI.reader.nextLine();
            if(orderController.enterSupplier(supplierNum))
                break;
            System.out.println("Wrong supplier num");
        }
        orderController.createPeriodicOrder();
        enterThePermanentSupplyDay();
        buildProductsList();
    }

    /**
     * Enter the permanent supply day
     */
    public void enterThePermanentSupplyDay(){
        int yourDay = checkValidDayAndReturn();
        orderController.enterPermanentDay(yourDay);
    }


    /**
     * Building the products list of the order. (Initializes the static variables "ProductsInOrder", "ProductsQuantity")
     */
    private void buildProductsList(){
        SupplierManagerCLI.yourChoice = "y";
        String catalogNum;
        int productQuantity;
        while (SupplierManagerCLI.yourChoice.equals("y")) {

            System.out.println("Please enter the supplier's product catalog number: ");
            catalogNum = SupplierManagerCLI.reader.nextLine();

            if(!orderController.addProductToTheList(catalogNum))
                continue;

            productQuantity = SupplierManagerCLI.CheckIntInputAndReturn("Enter the quantity of products you want to order:");
            if(!orderController.CheckIfSupplierCanSupplyThisQuantity(productQuantity)) {
                System.out.println("The supplier cannot supply the requested quantity");
                continue;
            }
            orderController.addQuantityOfTheLastEnteredProduct(productQuantity);

            SupplierManagerCLI.checkValidWithMessage("Do you want to insert another product? (y/n)");
        }
        orderController.savePeriodicOrder();
    }

    /**
     * Edit or delete periodic order - use by inventory
     */
    public void editOrDeletePeriodicOrder() {
        Choose = "";
        while (!Choose.equals("0")) {
            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Edit Periodic Order details");
            System.out.println("2. Delete Periodic Order");
            System.out.println("0. Exit.");
            Choose = SupplierManagerCLI.reader.nextLine();

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

    /**
     * Delete cur periodic order
     */
    public void deleteCurPeriodicOrder()
    {
        if(ChoosePeriodicOrderThatContainBarcode(0) == null)
            return;

        orderController.deleteCurPeriodicOrder();
    }

    /**
     * Edit periodic order
     */
    private void editPeriodicOrder(){
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Change the day for invite");
            System.out.println("2. Edit products list");
            System.out.println("0. Exit.");
            Choose = SupplierManagerCLI.reader.nextLine();

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

    /**
     * Change the day for invite
     */
    public void ChangeTheDayForInvite()
    {
        if(ChoosePeriodicOrderThatContainBarcode(0) == null)
            return;

        int day = checkValidDayAndReturn();
        orderController.changeDayForInviteForCurPeriodicOrder(day);
    }

    /**
     * Edit products list
     */
    private void editProductsList(){
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Add products to the list");
            System.out.println("2. Change the quantity of product in the list");
            System.out.println("3. Delete product from the list");
            System.out.println("0. Exit.");
            Choose = SupplierManagerCLI.reader.nextLine();

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

    /**
     * Add product to the list
     */
    public void addProductToTheList()
    {
        int[] barcodeAndId = ChoosePeriodicOrderThatContainBarcode(1);
        if(barcodeAndId == null)
            return;

        orderController.addProductToTheListByBarcode(barcodeAndId[0]);
        int productQuantity = SupplierManagerCLI.CheckIntInputAndReturn("Enter the quantity of products you want to order:");

        if(!orderController.CheckIfSupplierCanSupplyThisQuantity(productQuantity)) {
            System.out.println("The supplier cannot supply the requested quantity");
            return;
        }
        orderController.addQuantityOfTheLastEnteredProduct(productQuantity);
    }

    /**
     * Change the quantity of product in the list
     */
    public void ChangeTheQuantityOfProductInTheList()
    {
        int[] BarcodeAndId = ChoosePeriodicOrderThatContainBarcode(0);
        if(BarcodeAndId == null)
            return;

        orderController.findOrderedProduct(BarcodeAndId[0]);
        int productQuantity = SupplierManagerCLI.CheckIntInputAndReturn("Enter the new desired quantity");

        if(!orderController.CheckIfSupplierCanSupplyThisQuantity(productQuantity)) {
            System.out.println("The supplier cannot supply the requested quantity");
            return;
        }

        orderController.changeCurOrderedProductQuantity(productQuantity);
    }

    /**
     * Delete product from the list
     */
    public void DeleteProductFromTheList()
    {
        int[] BarcodeAndId = ChoosePeriodicOrderThatContainBarcode(0);
        if(BarcodeAndId == null)
            return;

        orderController.deleteOrderedProduct(BarcodeAndId[0]);
    }


    /**
     * Check valid day and return
     * @return yourDay
     */
    public int checkValidDayAndReturn(){
        boolean IsValid = false;
        int yourDay = 0;
        while (!IsValid) {
            System.out.println("Enter day (enter number):");
            System.out.println("1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday");
            try {yourDay = Integer.parseInt(SupplierManagerCLI.reader.nextLine()) - 1;}
            catch (NumberFormatException error) {continue;}
            if (yourDay < 0 || yourDay > 6) continue;
            IsValid = true;
        }
        return yourDay;
    }

    /**
     * Choose periodic order that contain barcode
     * @param method - 0 contain, 1 - can be contain
     * @return [barcode, periodic order id]
     */
    public int[] ChoosePeriodicOrderThatContainBarcode(int method)
    {
        int[] barcodeAndId = new int[2];

        barcodeAndId[0] = SupplierManagerCLI.CheckIntInputAndReturn("Enter Barcode please");

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
            barcodeAndId[1] = SupplierManagerCLI.CheckIntInputAndReturn("Enter Periodic Order id");
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
