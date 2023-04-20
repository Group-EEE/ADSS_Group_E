import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class SupplierGenerator creates a new supplier and adding it to the system.
 * We use this class when we meet with a new supplier, and we want to sign an agreement with him/her.
 */
public class SupplierGenerator{
    /**
     * Creates a new supplier and adding it to the system.
     */
    public static void createNewSupplier() {
        System.out.println("\nWelcome to the Supplier Generator.");

        // **************************** Asking the user for attributes of the supplier *********************************
        System.out.println("What is the name of the supplier? ");
        String name = SupplierController.reader.nextLine();

        System.out.println("What is the supplier's number? ");
        String supplierNum = SupplierController.reader.nextLine();
        if(SupplierController.AllSuppliers.containsKey(supplierNum)){
            System.out.println("Supplier already exist!");
            return;
        }

        System.out.println("What is the supplier's bank account? ");
        String bankAccount = SupplierController.reader.nextLine();

        int yourPayment = enterPaymentTerm();
        PaymentTerm payment = PaymentTerm.values()[yourPayment];

        Map<String,Contact> contacts = EnterContact();

        List<String> Categories = EnterCategories();

        // ******************************** create an agreement with the supplier *************************************
        Supplier newSupplier = CreateSupplierAndAgreement(name, supplierNum, bankAccount, payment, Categories, contacts, null);

        // Adding a new products to the agreement
        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {
            createSupplierProduct(newSupplier);
            SupplierController.checkValidWithMessage("Do the supplier supply another products? (y/n)");
        }

        addOrderDiscount(newSupplier.getMyAgreement());
    }

    /**
     * Creates the contacts list of the supplier
     */
    public static Map<String,Contact> EnterContact() {
        Map<String,Contact> contacts = new HashMap<String,Contact>();
        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {
            System.out.println("Enter a contact name: ");
            String name = SupplierController.reader.nextLine();
            System.out.println("Enter the contact's phone number: ");
            String phoneNumber = SupplierController.reader.nextLine();
            contacts.put(phoneNumber,new Contact(name, phoneNumber));
            SupplierController.checkValidWithMessage("Do you want to insert another contact? (y/n)");
        }
        return contacts;
    }

    /**
     * Creates the categories list of the supplier
     */
    public static List<String> EnterCategories() {
        List<String> category = new ArrayList<String>();
        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {
            System.out.println("Enter the supplier's category ");
            category.add(SupplierController.reader.nextLine());
            SupplierController.checkValidWithMessage("Do you want to insert another domain? (y/n)");
        }
        return category;
    }

    /**
     * Asking the user for the payment term of the supplier: Net, Net 30 days, Net 60 days.
     */
    public static int enterPaymentTerm(){
        int yourPayment;
        while (true) {
            System.out.println("What are the terms of payment with the supplier? (enter number)");
            System.out.println("1. Net\n2. Net 30 days\n3. Net 60 days\nEnter the number: ");
            try {yourPayment = Integer.parseInt(SupplierController.reader.nextLine()) - 1;}
            catch (NumberFormatException error) {continue;}
            if (yourPayment < 0 || yourPayment > 2) continue;
            break;
        }
        return yourPayment;
    }

    /**
     * Create a new supplier with an agreement. Returns the supplier object
     */
    public static Supplier CreateSupplierAndAgreement(String name, String supplierNum, String bankAccount,
                                                      PaymentTerm paymentTerm, List<String> Categories, Map<String,Contact> contacts, Supplier supplier) {
        boolean[] deliveryDays = new boolean[7];
        int daysToSupply = -1; // -1 means that the supplier doesn't supply the product by himself, so this parameter is irrelevant
        boolean isSupplierBringProduct;
        boolean hasPermanentDays = false;

        // **************************** Asking the user for attributes of the agreement ********************************
        SupplierController.checkValidWithMessage("Does the supplier transport his products himself? (y/n)");
        isSupplierBringProduct = SupplierController.yourChoice.equals("y");

        if (isSupplierBringProduct) {
            SupplierController.checkValidWithMessage("Does he have permanent days that he comes? (y/n)");
            hasPermanentDays = SupplierController.yourChoice.equals("y");

            if (hasPermanentDays)
                enterThePermanentSupplyDays(deliveryDays);
            else
                daysToSupply = SupplierController.CheckIntInputAndReturn("How many days does it take for the supplier to deliver the products?");
        }

        if (supplier != null)
        {
            supplier.getMyAgreement().setDetails(hasPermanentDays,isSupplierBringProduct,deliveryDays,daysToSupply);
            return supplier;
        }

        Supplier newSupplier = new Supplier(name, supplierNum, bankAccount, paymentTerm, contacts, Categories,
                hasPermanentDays, isSupplierBringProduct, deliveryDays, daysToSupply);
        SupplierController.AllSuppliers.put(supplierNum, newSupplier);
        return newSupplier;
    }

    /**
     * Gets a 7 size boolean array, and change the compatible cells to true if the supplier supply the product in this day.
     * For example, if the supplier supply products every sunday, the first cell (number 0) must be "True"
     */
    public static void enterThePermanentSupplyDays(boolean[] deliveryDays){
        SupplierController.yourChoice = "y";
        int yourDays;
        while (SupplierController.yourChoice.equals("y")) {
            System.out.println("Enter day: (enter number)");
            System.out.println("1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday");
            try {yourDays = Integer.parseInt(SupplierController.reader.nextLine()) - 1;}
            catch (NumberFormatException error) {continue;}
            if (yourDays < 0 || yourDays > 6) continue;
            deliveryDays[yourDays] = true;
            SupplierController.checkValidWithMessage("Do you want to add another day? (y/n)");
        }
    }

    /**
     * Add to the agreement a new product that supply by the given supplier
     */
    public static void createSupplierProduct(Supplier supplier) {
        // ************************ Asking the user for attributes of the supplier's product ***************************
        System.out.println("Enter the product name:");
        String productName = SupplierController.reader.nextLine();

        System.out.println("Enter the manufacturer name:");
        String manufacturerName = SupplierController.reader.nextLine();

        System.out.println("Enter supplier catalog:");
        String supplierCatalog = SupplierController.reader.nextLine();

        float price = SupplierController.CheckFloatInputAndReturn("Enter price per unit:");
        int amount = SupplierController.CheckIntInputAndReturn("Enter the quantity of products you can supply:");

        if (!SupplierController.AllManufacturers.containsKey(manufacturerName))
            SupplierController.AllManufacturers.put(manufacturerName, new Manufacturer(manufacturerName));

        // Create a key for the products map founded in the database
        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);

        // If the product is not in our database, we need to add it
        if (!SupplierController.AllProducts.containsKey(keyPair))
            SupplierController.AllProducts.put(keyPair, new GenericProduct(productName, SupplierController.AllManufacturers.get(manufacturerName)));

        SupplierProduct currSupplierProduct = supplier.getSupplierProduct(productName, manufacturerName);

        if(currSupplierProduct != null){
            SupplierController.checkValidWithMessage("The supplier is already supply this product. Do you want to overwrite it? (y,n)");
            if(SupplierController.yourChoice.equals("y"))
                currSupplierProduct.delete();
            else return;
        }

        SupplierProduct newSupplierProduct = new SupplierProduct(price,supplierCatalog,amount, supplier,
                SupplierController.AllProducts.get(keyPair), supplier.getMyAgreement());

        addProductDiscount(newSupplierProduct);
    }

    /**
     * Add new discount related to the given product
     */
    public static void addProductDiscount(SupplierProduct supplierProduct) {
        SupplierController.checkValidWithMessage("Do the supplier provide any discounts for this product? (y/n)");

        while (SupplierController.yourChoice.equals("y")) {

            int minimumQuantity = SupplierController.CheckIntInputAndReturn("What is the minimum quantity for getting this discount? ");

            float discountPercentage = SupplierController.CheckFloatInputAndReturn("How many percent off? ");

            supplierProduct.addProductDiscount(discountPercentage, minimumQuantity);
            SupplierController.checkValidWithMessage("Do the supplier provide another discounts for this product? (y/n)");
        }
    }

    /**
     * Add to the given agreement a new "Order Discount".
     * (means discount given for the final order. Can be for minimum price or minimum quantity)
     */
    public static void addOrderDiscount(Agreement agreement) {
        SupplierController.checkValidWithMessage("Do the supplier supply any discounts for order ? (y/n)");

        while (SupplierController.yourChoice.equals("y")) {
            String priceOrQuantity;
            do {
                System.out.println("Do the discount is for minimum price or for minimum quantity ? (p/q)");
                priceOrQuantity = SupplierController.reader.nextLine();
            } while (!priceOrQuantity.equals("p") && !priceOrQuantity.equals("q"));


            int minimumAmount;
            if (priceOrQuantity.equals("q"))
                minimumAmount = SupplierController.CheckIntInputAndReturn("What is the minimum quantity for getting this discount? ");
            else
                minimumAmount = SupplierController.CheckIntInputAndReturn("What is the minimum price for getting this discount? ");

            float discountPercentage = SupplierController.CheckFloatInputAndReturn("How many percent off? ");

            if(agreement.CheckIfExistOrderDiscount(priceOrQuantity,discountPercentage, minimumAmount))
                return;

            agreement.addOrderDiscount(priceOrQuantity, minimumAmount, discountPercentage);
            SupplierController.checkValidWithMessage("Do the supplier provide another discounts for order? (y/n)");
        }
    }


}
