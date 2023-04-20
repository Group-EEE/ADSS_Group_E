import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
The class describes the interface with the user. The user enters inputs, and the class acts accordingly.
*/
public class SupplierModulePresentation {

    OrderPresentation orderPresentation;
    CreateSupplierPresentation createSupplierPresentation;
    SupplierController supplierController;

    //------------------------------------------ Attributes ---------------------------------------


    //------------------------------------------ User insertion variables ---------------------------------------
    static Scanner reader = new Scanner(System.in);
    static String yourChoice;

    //------------------------------------------ Main menu ---------------------------------------


    public SupplierModulePresentation() {
        this.orderPresentation = new OrderPresentation();
        this.createSupplierPresentation = new CreateSupplierPresentation();
        supplierController = SupplierController.getInstance();
    }

    public void PowerOn() {

        System.out.println("Hello, welcome to the suppliers system.");

        checkValidWithMessage("Do you want to run the system with existing data? (y/n)");

        if (yourChoice.equals("y"))
            InsertData();

        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. Insert a new supplier into the system.");
            System.out.println("2. Create a new manual order.");
            System.out.println("3. Show orders history.");
            System.out.println("4. Delete supplier from the system.");
            System.out.println("5. Update Supplier Agreement.");
            System.out.println("6. Change supplier details.");
            System.out.println("7. Print supplier details.");
            System.out.println("0. Exit.");

            Choose = reader.nextLine();
            switch (Choose) {
                case "1":
                    CreateSupplierPresentation.createNewSupplier();
                    break;
                case "2":
                    orderPresentation.createManualOrder();
                    break;
                case "3":
                    showOrdersHistory();
                    break;
                case "4":
                    deleteSupplier();
                    break;
                case "5":
                    UpdateSupplierAgreement();
                     break;
                case "6":
                    ChangeSupplierDetails();
                    break;
                case "7":
                    PrintSupplierDetails();
                    break;
            }
        }
    }

    /**------------------------------------------ Create data base ---------------------------------------*/
    public static void InsertData() {
        System.out.println("Please enter file path: ");
        String path = reader.nextLine();

        try {reader = new Scanner(new File(path));}     //Try open the file.
        catch (FileNotFoundException ex) {
            System.out.println("\nThe file is not found, the system will operate without existing data\n");
            return;
        }
        while (reader.hasNextLine())
            CreateSupplierPresentation.createNewSupplier();
        reader = new Scanner(System.in);
    }

    /**------------------------------------------ Case 3 -----------------------------------------
     * A method that displays the order history from a specific supplier or from all suppliers in the system
     */
    private static void showOrdersHistory() {

        do {
            System.out.println("\nEnter supplier number. (For all the suppliers enter 'ALL' )");
            yourChoice = reader.nextLine();
        } while (!AllSuppliers.containsKey(yourChoice) && !yourChoice.equals("ALL"));

        if (yourChoice.equals("ALL")) {
            for (Map.Entry<String, Supplier> pair : AllSuppliers.entrySet()) {
                System.out.println("********************* " + pair.getValue().getName() + " *********************");
                pair.getValue().printOrdersHistory();
            }
        } else
            AllSuppliers.get(yourChoice).printOrdersHistory();
    }

    /**------------------------------------------ Case 4 -----------------------------------------
     * A method that deletes supplier from the system, including all the supplier's products signed in the agreement
     */
    private static void deleteSupplier() {

        Supplier supplier = AskAndCheckSupplierNumber();
        String supplierNum = supplier.getSupplierNum();
        checkValidWithMessage("Delete supplier: " + supplierNum + "\nAre you sure you want to delete? (y/n)");

        if (yourChoice.equals("y")) {
            supplier.fireSupplier();
            AllSuppliers.remove(supplierNum);
        }
    }

    /**------------------------------------------ Case 5 -------------------------------------------
     * A method that is responsible for updating the details of the supplier's agreement.
     * Inside the method there is an internal menu.
     */
    private static void UpdateSupplierAgreement() {
        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Update delivery details");
            System.out.println("2. Update/Add new product to the Agreement");
            System.out.println("3. Delete product from the Agreement");
            System.out.println("4. Update discounts");
            System.out.println("0. Exit.");
            Choose = reader.nextLine();

            Supplier supplier = null;
            if (!Choose.equals("0")) supplier = AskAndCheckSupplierNumber();

            switch (Choose) {
                case "1":
                    CreateSupplierPresentation.CreateSupplierAndAgreement(supplier.getName(), supplier.getSupplierNum(), supplier.getBankAccount(),
                        supplier.getPayment(), supplier.getCategories(), supplier.getMyContacts(), supplier);
                    break;
                case "2":
                    CreateSupplierPresentation.createSupplierProduct(supplier);
                    break;
                case "3":
                    DeleteProductFromTheAgreement(supplier);
                    break;
                case "4":
                    UpdateDiscounts(supplier);
                    break;
            }
        }
    }


    /**------------------------------------------ Case 5.3 -----------------------------------------
     * The supplier has stopped supplying a product, so it should be deleted from the agreement
     */
    private static void DeleteProductFromTheAgreement(Supplier supplier){

        SupplierProduct currSupplierProduct = findSupplierProduct(supplier);
        if(currSupplierProduct != null){
            checkValidWithMessage("Are you sure you want to delete this product? (y/n)");
            if(yourChoice.equals("y"))
                currSupplierProduct.delete();
        }
    }

    /**------------------------------------------ Case 5.4 -----------------------------------------
     * A method that is responsible for updating discounts on products or orders.
     */
    private static void UpdateDiscounts(Supplier supplier){
        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Add product discount");
            System.out.println("2. Delete product discount");
            System.out.println("3. Add order discount");
            System.out.println("4. Delete order discount");
            System.out.println("0. Exit.");
            Choose = reader.nextLine();

            switch (Choose) {
                case "1":
                    AddProductDiscount(supplier);
                    break;
                case "2":
                    DeleteProductDiscount(supplier);
                    break;
                case "3":
                    CreateSupplierPresentation.addOrderDiscount(supplier.getMyAgreement());
                    break;
                case "4":
                    DeleteOrderDiscount(supplier);
                    break;
            }
        }

    }

    /**------------------------------------------ Case 5.4.1 -----------------------------------------
     * Add a discount of a product supplied by the supplier.
     */
    private static void AddProductDiscount(Supplier supplier){

        SupplierProduct currSupplierProduct = findSupplierProduct(supplier);

        if(currSupplierProduct != null)
            CreateSupplierPresentation.addProductDiscount(currSupplierProduct);
    }

    /**------------------------------------------ Case 5.4.2 -----------------------------------------
     * Delete a discount of a product supplied by the supplier.
     */
    private static void DeleteProductDiscount(Supplier supplier){

        SupplierProduct currSupplierProduct = findSupplierProduct(supplier);

        if(currSupplierProduct != null)
        {
            int minimumAmount = CheckIntInputAndReturn("Enter the minimum amount:");

            currSupplierProduct.deleteDiscountProduct(minimumAmount);
        }
    }

    /**------------------------------------------ Case 5.4.4 -----------------------------------------
     * Delete an order discount to the agreement with the supplier.
     */
    private static void DeleteOrderDiscount(Supplier supplier){
        String priceOrQuantity;
        do {
            System.out.println("Do the discount is for minimum price or for minimum quantity ? (p/q)");
            priceOrQuantity = reader.nextLine();
        } while (!priceOrQuantity.equals("p") && !priceOrQuantity.equals("q"));

        float discountPercentage = CheckFloatInputAndReturn("Enter the Discount percentage: ");
        int minimumAmount = CheckIntInputAndReturn("Enter the minimum amount:");
        supplier.getMyAgreement().deleteOrderDiscount(priceOrQuantity, discountPercentage, minimumAmount);
    }

    //---------------------------------------Case 6----------------------------------------------

    /**
     * This method used to change the details of the supplier, such as bank account, payment term and contact list.
     */
    private static void ChangeSupplierDetails(){
        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Update supplier bank account");
            System.out.println("2. Update supplier payment term");
            System.out.println("3. Update supplier contact list");
            System.out.println("4. Stop working with a manufacturer");
            System.out.println("0. Exit.");
            Choose = reader.nextLine();

            Supplier supplier = null;
            if (!Choose.equals("0")) supplier = AskAndCheckSupplierNumber();

            switch (Choose) {
                case "1":
                    UpdateSupplierBankAccount(supplier);
                    break;
                case "2":
                    UpdateSupplierPaymentTerm(supplier);
                    break;
                case "3":
                    UpdateSupplierContactList(supplier);
                    break;
                case "4":
                    StopWorkingWithAManufacturer(supplier);
                    break;
            }
        }
    }

    /**
     * Update the supplier bank account of the given supplier
     */
    private static void UpdateSupplierBankAccount(Supplier supplier){
        System.out.println("\nEnter supplier new bank account\n");
        yourChoice = reader.nextLine();
        supplier.setBankAccount(yourChoice);
    }

    /**
     * This method update the contact list of a given supplier
     */
    private static void UpdateSupplierContactList(Supplier supplier){
        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Add new contact");
            System.out.println("2. Update existing contact number");
            System.out.println("3. Delete existing contact");
            System.out.println("0. Exit.");
            Choose = reader.nextLine();

            switch (Choose) {
                case "1":
                    addContact(supplier);
                    break;
                case "2":
                    updateContactPhoneNumber(supplier);
                    break;
                case "3":
                    deleteContact(supplier);
                    break;
            }

        }
    }

    /**
     * This method add new contact to a given supplier
     */
    private static void addContact(Supplier supplier){
        do {
            System.out.println("Enter a contact name: ");
            String name = reader.nextLine();

            System.out.println("Enter the contact's phone number: ");
            String phoneNumber = reader.nextLine();

            supplier.addContact(name,phoneNumber);

            checkValidWithMessage("Do you want to insert another contact? (y/n)");
        }while (yourChoice.equals("y"));
    }

    /**
     * Update the phone number of a contact, of the given supplier
     */
    private static void updateContactPhoneNumber(Supplier supplier){
        System.out.println("Enter contact phone number:");
        Contact currContact = supplier.getContact(reader.nextLine());
        if(currContact != null) {
            System.out.println("Enter contact NEW phone number:");
            currContact.setPhoneNumber(reader.nextLine());
        }
        else
            System.out.println("This phone is not exist");

    }

    /**
     * Delete a contact of the given supplier
     */
    private static void deleteContact(Supplier supplier){
        System.out.println("Enter contact phone number:");
        supplier.deleteContact(reader.nextLine());
    }

    /**
     * Update the payment term of the supplier (Net , Net30days, Net60days)
     */
    private static void UpdateSupplierPaymentTerm(Supplier supplier){
        int yourPayment = CreateSupplierPresentation.enterPaymentTerm();
        PaymentTerm payment = PaymentTerm.values()[yourPayment];
        supplier.setPayment(payment);
    }

    /**
     * This method used when this supplier stops working with a specific manufacturer. The method delete all the products
     * that produce by this manufacturer, and delete that manufacturer from the map of the manufacturer, that this supplier working with
     */
    private static void StopWorkingWithAManufacturer(Supplier supplier)
    {
        System.out.println("Enter manufacturer name:");
        supplier.stopWorkingWithManufacturer(reader.nextLine());
    }

    //---------------------------------------Case 7----------------------------------------------

    /**
     * Print all the: attributes of the supplier, manufacturers that the supplier work with, product supplied by him to us,
     * and orders that we order from him
     */
    private static void PrintSupplierDetails(){
        Supplier supplier = AskAndCheckSupplierNumber();
        System.out.println(supplier);
    }

    // ------------------------------------------------------------------------------------------

    /**An "Helper function"
     * Finding a certain product given by the user, that supplied by the given supplier
     */
    private static SupplierProduct findSupplierProduct(Supplier supplier){
        System.out.println("Enter the product name:");
        String productName = reader.nextLine();

        System.out.println("Enter the manufacturer name:");
        String manufacturerName = reader.nextLine();

        SupplierProduct currSupplierProduct = supplier.getSupplierProduct(productName, manufacturerName);

        if(currSupplierProduct != null)
            return currSupplierProduct;
        System.out.println("The product is not supply by the supplier");
        return null;
    }

    /** Helper function
     * Asking "yes or no" question given in the function. Checking if the answer is valid and puts the choice in the input variable
     */
    public static void checkValidWithMessage(String message)
    {
        do{
            System.out.println(message);
            yourChoice = reader.nextLine();
        } while (!yourChoice.equals("n") && !yourChoice.equals("y"));
    }

    /** Helper function
     * Asking "yes or no" question given in the function. Checking if the answer is valid and puts the choice in the input variable
     */
    public static Supplier AskAndCheckSupplierNumber()
    {
        Supplier supplier;
        do {
            System.out.println("\nEnter supplier number\n");
            yourChoice = reader.nextLine();
            supplier = AllSuppliers.get(yourChoice);
        } while (supplier == null);
        return supplier;
    }

    /**
     * Asking the user a given message and check weather the input is a valid float input.
     */
    public static float CheckFloatInputAndReturn(String message)
    {
        float num;
        while (true) {
            System.out.println(message);
            try {num = Float.parseFloat(reader.nextLine());}
            catch (NumberFormatException error) {continue;}
            if (num <= 0) {continue;}
            break;
        }
        return num;
    }

    /**
     * Asking the user a given message and check weather the input is a valid int input.
     */
    public static int CheckIntInputAndReturn(String message)
    {
        int num;
        while (true) {
            System.out.println(message);
            try {num = Integer.parseInt(reader.nextLine());}
            catch (NumberFormatException error) {continue;}
            if (num <= 0) {continue;}
            break;
        }
        return num;
    }
}