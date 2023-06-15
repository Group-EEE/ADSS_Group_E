package MainClasses;

import InventoryModule.PresentationCLI.StoreKeeperCLI;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationCLI.SupplierManagerCLI;

import java.util.Scanner;

public class SuperLiMainCLI {

    private static SupplierManagerCLI supplierManagerCLI;
    private static StoreKeeperCLI storeKeeperCLI;
    private static OrderController orderController;

    public static void main(String[] args)
    {
        //----------------------------------- Check length of args -----------------------------------

        if(args.length != 1)
        {
            System.out.println("The number of parameters must be 1");
            return;
        }

        //----------------------------------- Check If args[0] is valid -----------------------------------

        if(!args[0].equals("StoreManager") && !args[0].equals("SupplierManager") && !args[0].equals("Storekeeper"))
        {
            System.out.println("The second argument must be StoreManager or SupplierManager or Storekeeper");
            return;
        }

        //----------------------------------------------------------------------------------------------

        supplierManagerCLI = new SupplierManagerCLI();
        storeKeeperCLI = new StoreKeeperCLI();
        orderController = OrderController.getInstance();

        switch (args[0]) {
            case "StoreManager":
                StoreManagerMenu();
                break;

            case "SupplierManager":
                supplierManagerCLI.PowerOn();
                break;

            case "Storekeeper":
                storeKeeperCLI.Start();
                break;
        }

        orderController.cancelTimer();
    }

    // ---------------------------------------- StoreManagerMenu ---------------------------------------
    private static void StoreManagerMenu()
    {
        String yourChoice = "";
        Scanner reader = new Scanner(System.in);

        while (!yourChoice.equals("0")) {
            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. SupplierManager menu");
            System.out.println("2. Storekeeper menu");
            System.out.println("0. Exit");

            yourChoice = reader.nextLine();
            switch (yourChoice) {
                case "1":
                    supplierManagerCLI.PowerOn();
                    break;
                case "2":
                    storeKeeperCLI.Start();
                    break;
            }
        }
    }
}
