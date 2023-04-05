import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
The class describes the interface with the user. The user enters inputs, and the class acts accordingly.
*/
public class SupplierController {

    //------------------------------------------ Attributes ---------------------------------------
    static Map<String, Supplier> AllSuppliers = new HashMap<String, Supplier>();
    static Map<List<String>, Product> AllProducts = new HashMap<List<String>, Product>();
    static Map<String, Manufacturer> AllManufacturers = new HashMap<String, Manufacturer>();
    static Scanner reader = new Scanner(System.in);
    static String yourChoice;

    //------------------------------------------ Main menu ---------------------------------------
    public static void PowerOn() {

        System.out.println("Hello, welcome to the suppliers system.");

        checkValidWithMessage("Do you want to run the system with existing data? (y/n)");

        if (yourChoice.equals("y"))
            InsertData();

        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("\nPlease choose one of the options shown in the menu:\n");
            System.out.println("1. Insert a new supplier into the system.");
            System.out.println("2. Insert a new order.");
            System.out.println("3. Show orders history.");
            System.out.println("4. Delete supplier from the system.");
            System.out.println("5. Update Supplier Agreement.");
            System.out.println("6. Change supplier details.");
            System.out.println("7. Print supplier details.");
            System.out.println("0. Exit.");

            Choose = reader.nextLine();
            switch (Choose) {

                case "1":
                    SupplierGenerator.createNewSupplier();
                    break;

                case "2":
                    OrderGenerator.createNewOrder();
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
            SupplierGenerator.createNewSupplier();
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
     * A method that deletes supplier in the system
     */
    private static void deleteSupplier() {

        String supplierNum = AskAndCheckSupplierNumber();
        checkValidWithMessage("Delete supplier: " + supplierNum + "\nAre you sure you want to delete? (y/n)");

        if (yourChoice.equals("y")) {
            AllSuppliers.get(supplierNum).fireSupplier();
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
            if (!Choose.equals("0")) supplier = AllSuppliers.get(AskAndCheckSupplierNumber());

            switch (Choose) {

                case "1":
                    SupplierGenerator.CreateSupplierAndAgreement(supplier.getName(),supplier.getSupplierNum(),supplier.getBankAccount(),
                            supplier.getPayment(),supplier.getCategories(),supplier.getMyContacts(), supplier);
                    break;

                case "2":
                    SupplierGenerator.createSupplierProduct(supplier);
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
                    SupplierGenerator.addOrderDiscount(supplier.getMyAgreement());
                    break;

                case "4":
                    DeleteOrderDiscount(supplier);
                    break;
            }
        }

    }

    /**------------------------------------------ Case 5.4.1 -----------------------------------------
     * Add a discount to the product.
     */
    private static void AddProductDiscount(Supplier supplier){

        SupplierProduct currSupplierProduct = findSupplierProduct(supplier);

        if(currSupplierProduct != null)
            SupplierGenerator.addProductDiscount(currSupplierProduct);
    }

    /**------------------------------------------ Case 5.4.2 -----------------------------------------
     * Delete a discount from a product
     */
    private static void DeleteProductDiscount(Supplier supplier){

        SupplierProduct currSupplierProduct = findSupplierProduct(supplier);

        if(currSupplierProduct != null)
        {
            int minimumAmount = CheckIntInputAndReturn("Enter the minimum amount:");

            currSupplierProduct.deleteDiscountProduct(minimumAmount);
        }
    }

    //------------------------------------------ Case 5.4.4 -----------------------------------------
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
            if (!Choose.equals("0")) supplier = AllSuppliers.get(AskAndCheckSupplierNumber());

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

    private static void UpdateSupplierBankAccount(Supplier supplier){
        System.out.println("\nEnter supplier new bank account\n");
        yourChoice = reader.nextLine();
        supplier.setBankAccount(yourChoice);
    }

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

    private static void addContact(Supplier supplier){
        while (yourChoice.equals("y")) {
            System.out.println("Enter a contact name: ");
            String name = reader.nextLine();

            System.out.println("Enter the contact's phone number: ");
            String phoneNumber = reader.nextLine();

            supplier.addContact(name,phoneNumber);

            checkValidWithMessage("Do you want to insert another contact? (y/n)");
        }
    }

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

    private static void deleteContact(Supplier supplier){
        System.out.println("Enter contact phone number:");
        supplier.deleteContact(reader.nextLine());

    }

    private static void UpdateSupplierPaymentTerm(Supplier supplier){
        int yourPayment = SupplierGenerator.enterPaymentTerm();
        PaymentTerm payment = PaymentTerm.values()[yourPayment];
        supplier.setPayment(payment);
    }

    private static void StopWorkingWithAManufacturer(Supplier supplier)
    {
        System.out.println("Enter manufacturer name:");
        supplier.deleteManufacturer(reader.nextLine());
    }

    //---------------------------------------Case 7----------------------------------------------

    private static void PrintSupplierDetails(){
        Supplier supplier = AllSuppliers.get(AskAndCheckSupplierNumber());
        System.out.println(supplier);
    }

    // ------------------------------------------------------------------------------------------

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

    public static void checkValidWithMessage(String message)
    {
        do{
            System.out.println(message);
            yourChoice = reader.nextLine();
        } while (!yourChoice.equals("n") && !yourChoice.equals("y"));
    }

    public static String AskAndCheckSupplierNumber()
    {
        do {
            System.out.println("\nEnter supplier number\n");
            yourChoice = reader.nextLine();
        } while (!AllSuppliers.containsKey(yourChoice));
        return yourChoice;
    }

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