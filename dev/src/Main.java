import SuppliersModule.Presentation.SupplierModulePresentation;

public class Main {
    public static void main(String[] args)
    {
        SupplierModulePresentation supplierModulePresentation = new SupplierModulePresentation();
        //InventoryModulePresentation supplierModulePresentation = new InventoryModulePresentation();
        String yourChoice = "";
        while (!yourChoice.equals("0")) {
            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. Suppliers.");
            System.out.println("2. Inventory.");
            System.out.println("0. Exit.");

        }
        supplierModulePresentation.PowerOn();
    }

}
