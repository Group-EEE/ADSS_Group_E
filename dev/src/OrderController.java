import java.util.ArrayList;
import java.util.HashMap;

public class OrderController {

    public void createManualOrder() {
        System.out.println("\nWelcome to the Order Generator.");
        OrderGenerator.start();
        buildProductsList();
        OrderGenerator.makeOrder();
    }


    /**
     * Building the products list of the order. (Initializes the static variables "ProductsInOrder", "ProductsQuantity")
     */
    private void buildProductsList(){
        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {

            System.out.println("Please enter the product name: ");
            String productName = SupplierController.reader.nextLine();
            System.out.println("Please enter the name of the manufacturer: ");
            String manufacturerName = SupplierController.reader.nextLine();

            if(OrderGenerator.addProductToTheList(productName, manufacturerName))
                continue;

            int productQuantity = SupplierController.CheckIntInputAndReturn("Enter the quantity of products you want to order:");
            OrderGenerator.addQuantityOfTheLestEnteredProduct(productQuantity);

            SupplierController.checkValidWithMessage("Do you want to insert another product? (y/n)");
        }
    }
}
