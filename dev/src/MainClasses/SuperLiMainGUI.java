package MainClasses;

import InventoryModule.PresentationCLI.ReportPresentation;
import InventoryModule.PresentationCLI.StoreKeeperPresentationCLI;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationGUI.SupplierManagerGUI;

import java.util.Scanner;

public class SuperLiMainGUI {

    private static SupplierManagerGUI supplierManagerGUI;
    private static StoreKeeperPresentationCLI storeKeeperPresentationCLI;
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

        supplierManagerGUI = new SupplierManagerGUI();
        storeKeeperPresentationCLI = new StoreKeeperPresentationCLI();
        orderController = OrderController.getInstance();

        switch (args[0]) {
            case "StoreManager":
                StoreManagerMenu();
                break;

            case "SupplierManager":
                SupplierManagerGUI.powerOn();
                break;

            case "Storekeeper":
                storeKeeperPresentationCLI.Start();
                break;
        }
    }

    private static void StoreManagerMenu()
    {
        ReportPresentation reportPresentation = new ReportPresentation();
        String yourChoice = "";
        Scanner reader = new Scanner(System.in);

        while (!yourChoice.equals("0")) {
            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. Show suppliers' order history");
            System.out.println("2. Print supplier details");
            System.out.println("3. Show inventory report menu");
            System.out.println("0. Exit");

            yourChoice = reader.nextLine();
            switch (yourChoice) {
                case "1":
                    //supplierManagerPresentationCLI.showSupplierOrdersHistory();
                    break;
                case "2":
                    //supplierManagerPresentationCLI.PrintSupplierDetails();
                    break;
                case "3":
                    reportPresentation.ShowReportMenu();
                    break;
            }
        }
    }

    public static void closeProgram()
    {
        orderController.cancelTimer();
    }
}
