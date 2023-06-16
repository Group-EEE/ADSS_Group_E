package MainClasses;

import InventoryModule.PresentationCLI.StoreKeeperCLI;
import SuppliersModule.PresentationCLI.SupplierManagerCLI;

import java.util.Scanner;

public class StoreManagerCLI {

    SupplierManagerCLI supplierManagerCLI;
    StoreKeeperCLI storeKeeperCLI;

    public StoreManagerCLI(SupplierManagerCLI supplierManagerCLI, StoreKeeperCLI storeKeeperCLI)
    {
        this.supplierManagerCLI = supplierManagerCLI;
        this.storeKeeperCLI = storeKeeperCLI;
    }

    public void PowerOn() {

        String yourChoice = "";
        Scanner reader = new Scanner(System.in);

        while (!yourChoice.equals("0")) {
            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. SupplierManager menu");
            System.out.println("2. StoreKeeper menu");
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
