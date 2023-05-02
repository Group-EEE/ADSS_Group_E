import DataAccess.SuperLeeDB;
import InventoryModule.Presentation.InventoryModulePresentation;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Presentation.SupplierModulePresentation;

import java.util.Scanner;

public class SupplierInventoryPresentation {

    private SupplierModulePresentation supplierModulePresentation;
    private InventoryModulePresentation inventoryModulePresentation;
    private OrderController orderController;

    public SupplierInventoryPresentation()
    {
        supplierModulePresentation = new SupplierModulePresentation();
        inventoryModulePresentation = new InventoryModulePresentation();
        orderController = OrderController.getInstance();

    }

    public void PowerOn() {

        String yourChoice = "";
        Scanner reader = new Scanner(System.in);

        while (!yourChoice.equals("0")) {
            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. Suppliers system");
            System.out.println("2. Inventory system");
            System.out.println("0. Exit");

            yourChoice = reader.nextLine();
            switch (yourChoice) {
                case "1":
                    supplierModulePresentation.PowerOn();
                    break;
                case "2":
                    inventoryModulePresentation.Start();
                    break;
                case "0":
                    orderController.cancelTimer();
            }
        }
    }
}
