import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierGenerator{
    public static void createNewSupplier() {
        System.out.println("\nWelcome to the Supplier Generator.");

        System.out.println("What is the name of the supplier? ");
        String name = SupplierController.reader.nextLine();

        System.out.println("What is the supplier's number? ");
        String supplierNum = SupplierController.reader.nextLine();

        System.out.println("What is the supplier's bank account? ");
        String bankAccount = SupplierController.reader.nextLine();

        int yourPayment = enterPaymentTerm();
        PaymentTerm payment = PaymentTerm.values()[yourPayment];

        Map<String,Contact> contacts = EnterContact();

        List<String> Categories = EnterCategories();

        Supplier newSupplier = CreateSupplierAndAgreement(name, supplierNum, bankAccount, payment, Categories, contacts, null);

        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {
            createSupplierProduct(newSupplier);
            checkValidWithMessage("Do the supplier supply another products? (y/n)");
        }
        addOrderDiscount(newSupplier.getMyAgreement());
    }

    public static Map<String,Contact> EnterContact() {
        Map<String,Contact> contacts = new HashMap<String,Contact>();
        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {
            System.out.println("Enter a contact name: ");
            String name = SupplierController.reader.nextLine();
            System.out.println("Enter the contact's phone number: ");
            String phoneNumber = SupplierController.reader.nextLine();
            contacts.put(phoneNumber,new Contact(name, phoneNumber));
            checkValidWithMessage("Do you want to insert another contact? (y/n)");
        }
        return contacts;
    }

    public static List<String> EnterCategories() {
        List<String> category = new ArrayList<String>();
        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {
            System.out.println("Enter the supplier's category ");
            category.add(SupplierController.reader.nextLine());
            checkValidWithMessage("Do you want to insert another domain? (y/n)");
        }
        return category;
    }

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


    public static Supplier CreateSupplierAndAgreement(String name, String supplierNum, String bankAccount,
                                                      PaymentTerm paymentTerm, List<String> Categories, Map<String,Contact> contacts, Supplier supplier) {
        boolean[] deliveryDays = new boolean[7];
        int dayToSupply = -1;
        boolean isSupplierBringProduct;
        boolean hasPermanentDays = false;

        checkValidWithMessage("Does the supplier transport his products himself? (y/n)");
        isSupplierBringProduct = SupplierController.yourChoice.equals("y");

        if (isSupplierBringProduct) {
            checkValidWithMessage("Does he have permanent days that he comes? (y/n)");
            hasPermanentDays = SupplierController.yourChoice.equals("y");

            if (hasPermanentDays) {
                SupplierController.yourChoice = "y";
                int yourDays;
                while (SupplierController.yourChoice.equals("y")) {
                    System.out.println("Enter day: (enter number)");
                    System.out.println("1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday");
                    try {yourDays = Integer.parseInt(SupplierController.reader.nextLine()) - 1;}
                    catch (NumberFormatException error) {continue;}
                    if (yourDays < 0 || yourDays > 6) continue;
                    deliveryDays[yourDays] = true;
                    checkValidWithMessage("Do you want to add another day? (y/n)");
                }
            }
            else
                dayToSupply = CheckIntInputAndReturn("How many days does it take for the supplier to deliver the products?");
        }

        if (supplier != null)
        {
            supplier.getMyAgreement().setDetails(hasPermanentDays,isSupplierBringProduct,deliveryDays,dayToSupply);
            return supplier;
        }

        Supplier newSupplier = new Supplier(name, supplierNum, bankAccount, paymentTerm, contacts, Categories,
                hasPermanentDays, isSupplierBringProduct, deliveryDays, dayToSupply);
        SupplierController.AllSuppliers.put(supplierNum, newSupplier);
        return newSupplier;
    }

    public static void createSupplierProduct(Supplier supplier) {
        System.out.println("Enter the product name:");
        String productName = SupplierController.reader.nextLine();

        System.out.println("Enter the manufacturer name:");
        String manufacturerName = SupplierController.reader.nextLine();

        System.out.println("Enter supplier catalog:");
        String supplierCatalog = SupplierController.reader.nextLine();

        float price = CheckFloatInputAndReturn("Enter price per unit:");
        int amount = CheckIntInputAndReturn("Enter the quantity of products you can supply:");

        if (!SupplierController.AllManufacturers.containsKey(manufacturerName))
            SupplierController.AllManufacturers.put(manufacturerName, new Manufacturer(manufacturerName));

        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);

        if (!SupplierController.AllProducts.containsKey(keyPair))
            SupplierController.AllProducts.put(keyPair, new Product(productName, SupplierController.AllManufacturers.get(manufacturerName)));

        SupplierProduct currSupplierProduct = supplier.getSupplierProduct(productName, manufacturerName);

        if(currSupplierProduct != null){
            checkValidWithMessage("The supplier is already supply this product. Do you want to overwrite it? (y,n)");
            if(SupplierController.yourChoice.equals("y"))
                currSupplierProduct.delete();
            else return;
        }

        SupplierProduct newSupplierProduct = new SupplierProduct(price,supplierCatalog,amount, supplier,
                SupplierController.AllProducts.get(keyPair), supplier.getMyAgreement());

        addProductDiscount(newSupplierProduct);
    }

    public static void addProductDiscount(SupplierProduct supplierProduct) {
        checkValidWithMessage("Do the supplier provide any discounts for this product? (y/n)");

        while (SupplierController.yourChoice.equals("y")) {

            int minimumQuantity = CheckIntInputAndReturn("What is the minimum quantity for getting this discount? ");

            float discountPercentage = CheckFloatInputAndReturn("How many percent off? ");

            supplierProduct.addProductDiscount(discountPercentage, minimumQuantity);
            checkValidWithMessage("Do the supplier provide another discounts for this product? (y/n)");
        }
    }

    public static void addOrderDiscount(Agreement agreement) {
        checkValidWithMessage("Do the supplier supply any discounts for order ? (y/n)");

        while (SupplierController.yourChoice.equals("y")) {
            String priceOrQuantity;
            do {
                System.out.println("Do the discount is for minimum price or for minimum quantity ? (p/q)");
                priceOrQuantity = SupplierController.reader.nextLine();
            } while (!priceOrQuantity.equals("p") && !priceOrQuantity.equals("q"));


            int minimumAmount;
            if (priceOrQuantity.equals("q"))
                minimumAmount = CheckIntInputAndReturn("What is the minimum quantity for getting this discount? ");
            else
                minimumAmount = CheckIntInputAndReturn("What is the minimum price for getting this discount? ");

            float discountPercentage = CheckFloatInputAndReturn("How many percent off? ");

            if(agreement.CheckIfExistOrderDiscount(priceOrQuantity,discountPercentage, minimumAmount))
                return;

            agreement.addOrderDiscount(priceOrQuantity, minimumAmount, discountPercentage);
            checkValidWithMessage("Do the supplier provide another discounts for order? (y/n)");
        }
    }

    public static void checkValidWithMessage(String message)
    {
        do{
            System.out.println(message);
            SupplierController.yourChoice = SupplierController.reader.nextLine();
        } while (!SupplierController.yourChoice.equals("n") && !SupplierController.yourChoice.equals("y"));
    }

    public static float CheckFloatInputAndReturn(String message)
    {
        float num;
        while (true) {
            System.out.println(message);
            try {num = Float.parseFloat(SupplierController.reader.nextLine());}
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
            try {num = Integer.parseInt(SupplierController.reader.nextLine());}
            catch (NumberFormatException error) {continue;}
            if (num <= 0) {continue;}
            break;
        }
        return num;
    }
}
