package SuppliersModule.Presentation;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.PaymentTerm;
import SuppliersModule.Business.Supplier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
The class describes the interface with the user. The user enters inputs, and the class acts accordingly.
*/
public class SupplierModulePresentation {
    //------------------------------------------ Attributes ---------------------------------------
    OrderPresentation orderPresentation;
    CreateSupplierPresentation createSupplierPresentation;
    SupplierController supplierController;


    //------------------------------------------ User insertion variables ---------------------------------------
    static Scanner reader;
    static String yourChoice;

    //------------------------------------------ Main menu ---------------------------------------


    public SupplierModulePresentation() {
        reader = new Scanner(System.in);
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
            System.out.println("2. Create a new periodic order.");
            System.out.println("3. Show orders history.");
            System.out.println("4. Delete supplier from the system.");
            System.out.println("5. Update Supplier Agreement.");
            System.out.println("6. Change supplier details.");
            System.out.println("7. Print supplier details.");
            System.out.println("0. Exit.");

            Choose = reader.nextLine();
            switch (Choose) {
                case "1":
                    createSupplierPresentation.createNewSupplier();
                    break;
                case "2":
                    orderPresentation.createPeriodicOrder();
                    break;
                case "3":
                    showSupplierOrdersHistory();
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
    public void InsertData() {
        System.out.println("Please enter file path: ");
        String path = reader.nextLine();

        try {reader = new Scanner(new File(path));}     //Try open the file.
        catch (FileNotFoundException ex) {
            System.out.println("\nThe file is not found, the system will operate without existing data\n");
            return;
        }
        while (reader.hasNextLine())
            createSupplierPresentation.createNewSupplier();
        reader = new Scanner(System.in);
    }

    /**------------------------------------------ Case 3 -----------------------------------------
     * A method that displays the order history from a specific supplier or from all suppliers in the system
     */
    private void showSupplierOrdersHistory() {

        do {
            System.out.println("\nEnter supplier number. (For all the suppliers enter 'ALL' )");
            yourChoice = reader.nextLine();
        } while (!supplierController.checkIfSupplierExist(yourChoice) && !yourChoice.equals("ALL"));

        if (yourChoice.equals("ALL")) {
            for (Map.Entry<String, Supplier> pair : supplierController.returnAllSuppliers().entrySet()) {
                System.out.println("********************* " + pair.getValue().getName() + " *********************");
                System.out.println(pair.getValue().StringOrdersHistory());
            }
        }
        else
            System.out.println(supplierController.returnAllSuppliers().get(yourChoice).StringOrdersHistory());
    }

    /**------------------------------------------ Case 4 -----------------------------------------
     * A method that deletes supplier from the system, including all the supplier's products signed in the agreement
     */
    private void deleteSupplier() {

        String supplierNum;
        do {
            System.out.println("\nEnter supplier number\n");
            supplierNum = reader.nextLine();
        } while (!supplierController.checkIfSupplierExist(supplierNum));
        checkValidWithMessage("Delete supplier: " + supplierNum + "\nAre you sure you want to delete? (y/n)");
        yourChoice = reader.nextLine();

        if (yourChoice.equals("y"))
            supplierController.fireSupplier(supplierNum);
    }

    /**------------------------------------------ Case 5 -------------------------------------------
     * A method that is responsible for updating the details of the supplier's agreement.
     * Inside the method there is an internal menu.
     */
    private void UpdateSupplierAgreement() {
        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Update delivery details");
            System.out.println("2. Update/Add new product to the SuppliersModule.Business.Agreement");
            System.out.println("3. Delete product from the SuppliersModule.Business.Agreement");
            System.out.println("4. Update discounts");
            System.out.println("0. Exit.");
            Choose = reader.nextLine();

            String supplierNum = "";
            if (!Choose.equals("0")){
                do {
                    System.out.println("\nEnter supplier number\n");
                    supplierNum = reader.nextLine();
                } while (!supplierController.checkIfSupplierExist(supplierNum));
            }

            switch (Choose) {
                case "1":
                    createSupplierPresentation.CreateSupplierAndAgreement("a", supplierNum, "a", PaymentTerm.values()[2], null, true);
                    break;
                case "2":
                    createSupplierPresentation.createSupplierProduct(supplierNum);
                    break;
                case "3":
                    DeleteProductFromTheAgreement(supplierNum);
                    break;
                case "4":
                    UpdateDiscounts(supplierNum);
                    break;
            }
        }
    }


    /**------------------------------------------ Case 5.3 -----------------------------------------
     * The supplier has stopped supplying a product, so it should be deleted from the agreement
     */
    private void DeleteProductFromTheAgreement(String supplierNum){

        System.out.println("Enter SupplierCatalog:");
        String supplierCatalog = reader.nextLine();

        if(supplierController.checkIfSupplierSupplyProduct(supplierCatalog, supplierNum)) {
            checkValidWithMessage("Are you sure you want to delete this product? (y/n)");
            if(yourChoice.equals("y"))
                supplierController.deleteProductFromSupplier(supplierCatalog, supplierNum);
        }
        else
            System.out.println("The product is not supply by the supplier");
    }

    /**------------------------------------------ Case 5.4 -----------------------------------------
     * A method that is responsible for updating discounts on products or orders.
     */
    private void UpdateDiscounts(String supplierNum){
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
                    AddProductDiscount(supplierNum);
                    break;
                case "2":
                    DeleteProductDiscount(supplierNum);
                    break;
                case "3":
                    createSupplierPresentation.addOrderDiscount(supplierNum);
                    break;
                case "4":
                    DeleteOrderDiscount(supplierNum);
                    break;
            }
        }

    }

    /**------------------------------------------ Case 5.4.1 -----------------------------------------
     * Add a discount of a product supplied by the supplier.
     */
    private void AddProductDiscount(String supplierNum){

        System.out.println("Enter SupplierCatalog:");
        String supplierCatalog = reader.nextLine();

        if(supplierController.checkIfSupplierSupplyProduct(supplierCatalog, supplierNum)){
            createSupplierPresentation.addProductDiscount(supplierCatalog, supplierNum);
        }
        else
            System.out.println("The product is not supply by the supplier");


    }

    /**------------------------------------------ Case 5.4.2 -----------------------------------------
     * Delete a discount of a product supplied by the supplier.
     */
    private void DeleteProductDiscount(String supplierNum){

        System.out.println("Enter SupplierCatalog:");
        String supplierCatalog = reader.nextLine();
        if(supplierController.checkIfSupplierSupplyProduct(supplierCatalog, supplierNum)){
            int minimumAmount = CheckIntInputAndReturn("Enter the minimum amount:");
            supplierController.deleteDiscountProduct(supplierNum, supplierCatalog, minimumAmount);
        }
        else
            System.out.println("The product is not supply by the supplier");
    }

    /**------------------------------------------ Case 5.4.4 -----------------------------------------
     * Delete an order discount to the agreement with the supplier.
     */
    private void DeleteOrderDiscount(String supplierNum){
        String priceOrQuantity;
        do {
            System.out.println("Do the discount is for minimum price or for minimum quantity ? (p/q)");
            priceOrQuantity = reader.nextLine();
        } while (!priceOrQuantity.equals("p") && !priceOrQuantity.equals("q"));

        int minimumAmount = CheckIntInputAndReturn("Enter the minimum amount:");
        supplierController.deleteOrderDiscount(supplierNum, priceOrQuantity, minimumAmount);
    }

    //---------------------------------------Case 6----------------------------------------------

    /**
     * This method used to change the details of the supplier, such as bank account, payment term and contact list.
     */
    private void ChangeSupplierDetails(){
        String Choose = "";
        while (!Choose.equals("0")) {

            System.out.println("Please choose one of the options shown in the menu:");
            System.out.println("1. Update supplier bank account");
            System.out.println("2. Update supplier payment term");
            System.out.println("3. Update supplier contact list");
            System.out.println("4. Stop working with a manufacturer");
            System.out.println("0. Exit.");
            Choose = reader.nextLine();

            String supplierNum = "";
            if (!Choose.equals("0")){
                do {
                    System.out.println("\nEnter supplier number\n");
                    supplierNum = reader.nextLine();
                } while (!supplierController.checkIfSupplierExist(supplierNum));
            }

            switch (Choose) {
                case "1":
                    UpdateSupplierBankAccount(supplierNum);
                    break;
                case "2":
                    UpdateSupplierPaymentTerm(supplierNum);
                    break;
                case "3":
                    UpdateSupplierContactList(supplierNum);
                    break;
                case "4":
                    StopWorkingWithAManufacturer(supplierNum);
                    break;
            }
        }
    }

    /**
     * Update the supplier bank account of the given supplier
     */
    private void UpdateSupplierBankAccount(String supplierNum){
        System.out.println("\nEnter supplier new bank account\n");
        yourChoice = reader.nextLine();
        supplierController.setBankAccount(supplierNum, yourChoice);
    }

    /**
     * This method update the contact list of a given supplier
     */
    private void UpdateSupplierContactList(String supplierNum){
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
                    addContact(supplierNum);
                    break;
                case "2":
                    updateContactPhoneNumber(supplierNum);
                    break;
                case "3":
                    deleteContact(supplierNum);
                    break;
            }

        }
    }

    /**
     * This method add new contact to a given supplier
     */
    private void addContact(String supplierNum){
        do {
            System.out.println("Enter a contact name: ");
            String name = reader.nextLine();

            System.out.println("Enter the contact's phone number: ");
            String phoneNumber = reader.nextLine();

            supplierController.addContactToSupplier(supplierNum, name,phoneNumber);

            checkValidWithMessage("Do you want to insert another contact? (y/n)");
        }while (yourChoice.equals("y"));
    }

    /**
     * Update the phone number of a contact, of the given supplier
     */
    private void updateContactPhoneNumber(String supplierNum){
        System.out.println("Enter contact phone number:");
        String OldPhone =  reader.nextLine();

        if(supplierController.checkIfContactExist(supplierNum, OldPhone)) {
            System.out.println("Enter contact NEW phone number:");
            supplierController.setNewContactPhone(supplierNum, reader.nextLine(), OldPhone);
        }
        else
            System.out.println("This phone is not exist");

    }

    /**
     * Delete a contact of the given supplier
     */
    private void deleteContact(String supplierNum){
        System.out.println("Enter contact phone number:");
        supplierController.deleteContactFromSupplier(supplierNum, reader.nextLine());
    }

    /**
     * Update the payment term of the supplier (Net , Net30days, Net60days)
     */
    private void UpdateSupplierPaymentTerm(String supplierNum){
        supplierController.updateSupplierPaymentTerm(supplierNum, createSupplierPresentation.enterPaymentTerm());
    }

    /**
     * This method used when this supplier stops working with a specific manufacturer. The method delete all the products
     * that produce by this manufacturer, and delete that manufacturer from the map of the manufacturer, that this supplier working with
     */
    private void StopWorkingWithAManufacturer(String supplierNum)
    {
        System.out.println("Enter manufacturer name:");
        supplierController.stopWorkingWithManufacturer(supplierNum, reader.nextLine());
    }

    //---------------------------------------Case 7----------------------------------------------

    /**
     * Print all the: attributes of the supplier, manufacturers that the supplier work with, product supplied by him to us,
     * and orders that we order from him
     */
    private void PrintSupplierDetails(){
        String supplierNum;
        do {
            System.out.println("\nEnter supplier number\n");
            supplierNum = reader.nextLine();
        } while (!supplierController.checkIfSupplierExist(supplierNum));

        System.out.println(supplierController.StringSupplierDetails(supplierNum));
    }

    // ------------------------------------------------------------------------------------------

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