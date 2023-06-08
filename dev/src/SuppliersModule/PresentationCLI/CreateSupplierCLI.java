package SuppliersModule.PresentationCLI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Generator.SupplierGenerator;
import SuppliersModule.Business.PaymentTerm;

import java.util.ArrayList;
import java.util.List;

/**
 * The class CreateSupplierPresentation responsibility on create supplier process.
 */
public class CreateSupplierCLI {
    SupplierController supplierController;
    SupplierGenerator supplierGenerator;

    public CreateSupplierCLI() {
        this.supplierController = SupplierController.getInstance();
        this.supplierGenerator = SupplierGenerator.getInstance();
    }

    /**
     * Creates a new supplier and adding it to the system.
     */
    public void createNewSupplier() {
        System.out.println("\nWelcome to the Supplier Generator.");
        supplierGenerator.reset();

        // **************************** Asking the user for attributes of the supplier *********************************
        System.out.println("What is the name of the supplier? ");
        String name = SupplierManagerCLI.reader.nextLine();

        System.out.println("What is the supplier's number? ");
        String supplierNum = SupplierManagerCLI.reader.nextLine();
        if(supplierController.checkIfSupplierExist(supplierNum)){
            System.out.println("Supplier already exist");
            return;
        }

        System.out.println("What is the supplier's bank account? ");
        String bankAccount = SupplierManagerCLI.reader.nextLine();

        int yourPayment = enterPaymentTerm();
        PaymentTerm payment = PaymentTerm.values()[yourPayment];

        EnterContact();

        List<String> Categories = EnterCategories();

        // ******************************** create an agreement with the supplier *************************************
        CreateSupplierAndAgreement(name, supplierNum, bankAccount, payment, Categories, false);

        // Adding a new products to the agreement
        SupplierManagerCLI.yourChoice = "y";
        while (SupplierManagerCLI.yourChoice.equals("y")) {
            createSupplierProduct(supplierNum);
            SupplierManagerCLI.checkValidWithMessage("Do the supplier supply another products? (y/n)");
        }

        addOrderDiscount(supplierNum);
    }

    /**
     * Creates the contacts list of the supplier
     */
    public void EnterContact() {
        SupplierManagerCLI.yourChoice = "y";
        while (SupplierManagerCLI.yourChoice.equals("y")) {
            System.out.println("Enter a contact name: ");
            String name = SupplierManagerCLI.reader.nextLine();
            System.out.println("Enter the contact's phone number: ");
            String phoneNumber = SupplierManagerCLI.reader.nextLine();
            supplierGenerator.addContact(name,phoneNumber);
            SupplierManagerCLI.checkValidWithMessage("Do you want to insert another contact? (y/n)");
        }
    }

    /**
     * Creates the categories list of the supplier
     */
    public List<String> EnterCategories() {
        List<String> category = new ArrayList<String>();
        SupplierManagerCLI.yourChoice = "y";
        while (SupplierManagerCLI.yourChoice.equals("y")) {
            System.out.println("Enter the supplier's category ");
            category.add(SupplierManagerCLI.reader.nextLine());
            SupplierManagerCLI.checkValidWithMessage("Do you want to insert another category? (y/n)");
        }
        return category;
    }

    /**
     * Asking the user for the payment term of the supplier: Net, Net 30 days, Net 60 days.
     */
    public int enterPaymentTerm(){
        int yourPayment;
        while (true) {
            System.out.println("What are the terms of payment with the supplier? (enter number)");
            System.out.println("1. Net\n2. Net 30 days\n3. Net 60 days\nEnter the number: ");
            try {yourPayment = Integer.parseInt(SupplierManagerCLI.reader.nextLine()) - 1;}
            catch (NumberFormatException error) {continue;}
            if (yourPayment < 0 || yourPayment > 2) continue;
            break;
        }
        return yourPayment;
    }

    /**
     * Create a new supplier with an agreement. Returns the supplier object
     */
    public void CreateSupplierAndAgreement(String name, String supplierNum, String bankAccount,
                                           PaymentTerm paymentTerm, List<String> Categories, boolean SupplierAlreadyExist) {
        boolean[] deliveryDays = new boolean[7];
        int daysToSupply = -1; // -1 means that the supplier doesn't supply the product by himself, so this parameter is irrelevant
        boolean isSupplierBringProduct;
        boolean hasPermanentDays = false;

        // **************************** Asking the user for attributes of the agreement ********************************
        SupplierManagerCLI.checkValidWithMessage("Does the supplier transport his products himself? (y/n)");
        isSupplierBringProduct = SupplierManagerCLI.yourChoice.equals("y");

        if (isSupplierBringProduct) {
            SupplierManagerCLI.checkValidWithMessage("Does he have permanent days that he comes? (y/n)");
            hasPermanentDays = SupplierManagerCLI.yourChoice.equals("y");

            if (hasPermanentDays)
                enterThePermanentSupplyDays(deliveryDays);
            else
                daysToSupply = SupplierManagerCLI.CheckIntInputAndReturn("How many days does it take for the supplier to deliver the products?");
        }

        if(SupplierAlreadyExist)
            supplierController.editAgreement(supplierNum, hasPermanentDays, isSupplierBringProduct, deliveryDays, daysToSupply);

        else
            supplierGenerator.CreateSupplierAndAgreement(name, supplierNum, bankAccount, paymentTerm, Categories,
                hasPermanentDays, isSupplierBringProduct, deliveryDays, daysToSupply);
    }

    /**
     * Gets a 7 size boolean array, and change the compatible cells to true if the supplier supply the product in this day.
     * For example, if the supplier supply products every sunday, the first cell (number 0) must be "True"
     */
    public void enterThePermanentSupplyDays(boolean[] deliveryDays){
        SupplierManagerCLI.yourChoice = "y";
        int yourDays;
        while (SupplierManagerCLI.yourChoice.equals("y")) {
            System.out.println("Enter day: (enter number)");
            System.out.println("1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday");
            try {yourDays = Integer.parseInt(SupplierManagerCLI.reader.nextLine()) - 1;}
            catch (NumberFormatException error) {continue;}
            if (yourDays < 0 || yourDays > 6) continue;
            deliveryDays[yourDays] = true;
            SupplierManagerCLI.checkValidWithMessage("Do you want to add another day? (y/n)");
        }
    }

    /**
     * Add to the agreement a new product that supply by the given supplier
     */
    public void createSupplierProduct(String supplierNum) {
        // ************************ Asking the user for attributes of the supplier's product ***************************
        System.out.println("Enter the product name:");
        String productName = SupplierManagerCLI.reader.nextLine();

        System.out.println("Enter the manufacturer name:");
        String manufacturerName = SupplierManagerCLI.reader.nextLine();

        int barcode = SupplierManagerCLI.CheckIntInputAndReturn("Enter barcode: (If unknown enter: 99)");

        System.out.println("Enter supplier catalog:");
        String supplierCatalog = SupplierManagerCLI.reader.nextLine();

        float price = SupplierManagerCLI.CheckFloatInputAndReturn("Enter price per unit:");
        int amount = SupplierManagerCLI.CheckIntInputAndReturn("Enter the quantity of products you can supply:");


        if(supplierController.checkIfSupplierSupplyProduct(supplierCatalog, supplierNum)){
            SupplierManagerCLI.checkValidWithMessage("The supplier is already supply this product. Do you want to overwrite it? (y,n)");
            if(SupplierManagerCLI.yourChoice.equals("y"))
                supplierController.deleteProductFromSupplier(supplierCatalog, supplierNum);
            else return;
        }

        supplierController.addSupplierProduct(productName, manufacturerName, barcode, supplierNum, price, supplierCatalog, amount);


        addProductDiscount(supplierCatalog, supplierNum);
    }

    /**
     * Add new discount related to the given product
     */
    public void addProductDiscount(String supplierCatalog, String supplierNum) {
        SupplierManagerCLI.checkValidWithMessage("Do the supplier provide any discounts for this product? (y/n)");

        while (SupplierManagerCLI.yourChoice.equals("y")) {

            int minimumQuantity = SupplierManagerCLI.CheckIntInputAndReturn("What is the minimum quantity for getting this discount? ");

            float discountPercentage = SupplierManagerCLI.CheckFloatInputAndReturn("How many percent off? ");

            supplierController.addSupplierProductDiscount(supplierCatalog, discountPercentage, minimumQuantity, supplierNum);
            SupplierManagerCLI.checkValidWithMessage("Do the supplier provide another discounts for this product? (y/n)");
        }
    }

    /**
     * Add to the given agreement a new "SuppliersModule.Business.Order Discount".
     * (means discount given for the final order. Can be for minimum price or minimum quantity)
     */
    public void addOrderDiscount(String supplierNum) {
        SupplierManagerCLI.checkValidWithMessage("Do the supplier supply any discounts for order ? (y/n)");

        while (SupplierManagerCLI.yourChoice.equals("y")) {
            String priceOrQuantity;
            do {
                System.out.println("Do the discount is for minimum price or for minimum quantity ? (p/q)");
                priceOrQuantity = SupplierManagerCLI.reader.nextLine();
            } while (!priceOrQuantity.equals("p") && !priceOrQuantity.equals("q"));


            int minimumAmount;
            if (priceOrQuantity.equals("q"))
                minimumAmount = SupplierManagerCLI.CheckIntInputAndReturn("What is the minimum quantity for getting this discount? ");
            else
                minimumAmount = SupplierManagerCLI.CheckIntInputAndReturn("What is the minimum price for getting this discount? ");

            float discountPercentage = SupplierManagerCLI.CheckFloatInputAndReturn("How many percent off? ");

            if(supplierController.CheckIfExistOrderDiscount(supplierNum, priceOrQuantity, minimumAmount)) {
                System.out.println("The discount already exist");
                return;
            }

            supplierController.addOrderDiscount(supplierNum, priceOrQuantity, minimumAmount, discountPercentage);
            SupplierManagerCLI.checkValidWithMessage("Do the supplier provide another discounts for order? (y/n)");
        }
    }

}
