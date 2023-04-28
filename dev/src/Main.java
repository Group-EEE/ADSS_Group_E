import DataAccess.SuperLeeDB;

public class Main {
    public static void main(String[] args)
    {

        //SupplierModulePresentation supplierModulePresentation = new SupplierModulePresentation();
        /*
        InventoryModulePresentation inventoryModulePresentation = new InventoryModulePresentation();
        String yourChoice = "";
        while (!yourChoice.equals("0")) {
            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. Suppliers.");
            System.out.println("2. Inventory.");
            System.out.println("0. Exit.");

            Choose = reader.nextLine();
            switch (Choose) {
                case "1":
                    supplierModulePresentation.PowerOn();
                    break;
                case "2":
                    inventoryModulePresentation.PowerOn();
                    break;
            }
        }

         */
        //supplierModulePresentation.PowerOn();

        SuperLeeDB superLeeDB = SuperLeeDB.getInstance();
        superLeeDB.ReadAllToCache();
        superLeeDB.WriteAllToDB();
    }
}