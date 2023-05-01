import DataAccess.SuperLeeDB;
import InventoryModule.Presentation.InventoryModulePresentation;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Presentation.SupplierModulePresentation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        SupplierInventoryPresentation supplierInventoryPresentation = new SupplierInventoryPresentation();
        supplierInventoryPresentation.PowerOn();
    }
}