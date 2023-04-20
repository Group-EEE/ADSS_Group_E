public class OrderPresentation {

    public void createManualOrder() {
        System.out.println("\nWelcome to the Order Generator.");
        OrderGenerator.reset();
        buildProductsList();
        System.out.println(OrderGenerator.makeOrder());
    }


    /**
     * Building the products list of the order. (Initializes the static variables "ProductsInOrder", "ProductsQuantity")
     */
    private void buildProductsList(){
        SupplierModulePresentation.yourChoice = "y";
        while (SupplierModulePresentation.yourChoice.equals("y")) {

            System.out.println("Please enter the product name: ");
            String productName = SupplierModulePresentation.reader.nextLine();
            System.out.println("Please enter the name of the manufacturer: ");
            String manufacturerName = SupplierModulePresentation.reader.nextLine();

            if(OrderGenerator.addProductToTheList(productName, manufacturerName))
                continue;

            int productQuantity = SupplierModulePresentation.CheckIntInputAndReturn("Enter the quantity of products you want to order:");
            OrderGenerator.addQuantityOfTheLestEnteredProduct(productQuantity);

            SupplierModulePresentation.checkValidWithMessage("Do you want to insert another product? (y/n)");
        }
    }
}
