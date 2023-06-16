package MainClasses;

import InventoryModule.PresentationCLI.StoreKeeperCLI;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationCLI.SupplierManagerCLI;


public class SuperLiMainCLI {

    private static SupplierManagerCLI supplierManagerCLI;
    private static StoreKeeperCLI storeKeeperCLI;
    private static StoreManagerCLI storeManagerCLI;
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

        if(!args[0].equals("StoreManager") && !args[0].equals("SupplierManager") && !args[0].equals("StoreKeeper"))
        {
            System.out.println("The second argument must be StoreManager or SupplierManager or Storekeeper");
            return;
        }

        //----------------------------------------------------------------------------------------------

        supplierManagerCLI = new SupplierManagerCLI();
        storeKeeperCLI = new StoreKeeperCLI();
        storeManagerCLI = new StoreManagerCLI(supplierManagerCLI, storeKeeperCLI);
        orderController = OrderController.getInstance();

        switch (args[0]) {
            case "StoreManager":
                storeManagerCLI.PowerOn();
                break;

            case "SupplierManager":
                supplierManagerCLI.PowerOn();
                break;

            case "StoreKeeper":
                storeKeeperCLI.Start();
                break;
        }

        orderController.cancelTimer();
    }
}
