import InventoryModule.PresentationGUI.StoreKeeperGUI;
import StoreManagerCLIorGUI.StoreManagerGUI;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationGUI.SupplierManagerGUI;

public class SuperLiMainGUI {

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
            System.out.println("The second argument must be StoreManager or SupplierManager or StoreKeeper");
            return;
        }

        //----------------------------------------------------------------------------------------------

        orderController = OrderController.getInstance();

        switch (args[0]) {
            case "StoreManager":
                StoreManagerGUI.powerOn();
                break;

            case "SupplierManager":
                SupplierManagerGUI.powerOn(null);
                break;

            case "StoreKeeper":
                StoreKeeperGUI.powerOn(null);
                break;
        }
    }
}
