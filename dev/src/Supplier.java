import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Supplier class describe a certain supplier in the system. The supplier has attributes like: name, bank account,
 * and references like: manufactures that he works with, and product that he supplies.
 */
public class Supplier {

    //------------------------------------------ Attributes ---------------------------------------
    private String Name;
    private final String SupplierNum;
    private String BankAccount;
    private PaymentTerm Payment; // Net , Net30days, Net60days
    private List<String> Categories; // For example: fruits, meat, cleaning

    //------------------------------------------ References ---------------------------------------
    private Map<String,Contact> MyContacts; // All the contacts of the supplier
    private Map<String,Manufacturer> MyManufacturers; // All the manufacturers that working with this supplier
    private List<SupplierProduct> MyProducts; // All the products supply by this supplier in the agreement.
    private List<Order> MyOrders; // All the orders that we ordered from this supplier
    private Agreement MyAgreement; // The agreement that we signed with this supplier

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public Supplier(String name, String supplierNum, String bankAccount, PaymentTerm payment, Map<String,Contact> myContacts, List<String> categories,
                    boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOfDaysToSupply) {

        if (myContacts.size() < minimumContacts())
            throw new RuntimeException("Must at least " + minimumContacts() + " contacts");

        MyProducts = new ArrayList<SupplierProduct>();
        MyManufacturers = new HashMap<String, Manufacturer>();
        MyOrders = new ArrayList<Order>();
        Name = name;
        SupplierNum = supplierNum;
        BankAccount = bankAccount;
        Payment = payment;
        MyContacts = myContacts;
        Categories = categories;

        //Set agreement
        MyAgreement = new Agreement(hasPermanentDays, isSupplierBringProduct, deliveryDays, numberOfDaysToSupply, this);
    }

    /**
     * This method use when we stop working with this supplier
     */
    public void fireSupplier()
    {
        String[] keys = MyManufacturers.keySet().toArray(new String[MyManufacturers.size()]);
        for(String key : keys)
            stopWorkingWithManufacturer(key);
    }

    public String toString() {
        return  "Supplier name: " + Name + '\n' +
                "\nSupplier number: " + SupplierNum + '\n' +
                "\nBank account: " + BankAccount + '\n' +
                "\nPayment: " + Payment + '\n' +
                "\nCategories: " + Categories + '\n' +
                "\nWorking with manufacturers:\n" + toStringMyManufacturers(MyManufacturers) + '\n' +
                "\nSupplier products:\n" + printProducts() + '\n' +
                "\nSupplier contacts:\n" + stringContacts() + '\n';
    }

    // --------------------------------- Methods related to Contacts ------------------------------
    public static int minimumContacts() {return 1;}
    public int numberOfContacts() {return this.MyContacts.size();}
    public Map<String,Contact> getMyContacts() {
        return MyContacts;
    }
    public Contact getContact(String phoneNumber){return MyContacts.get(phoneNumber);}

    public void addContact(String name, String phoneNumber) {
        if(getContact(phoneNumber) != null)
            System.out.println("This phone is already exist");
        else
            this.MyContacts.put(phoneNumber,new Contact(name, phoneNumber));
    }

    public void deleteContact(String phoneNumber){
        if(getContact(phoneNumber) == null)
            System.out.println("This phone is not exist");
        else if(numberOfContacts() != minimumContacts())
            MyContacts.remove(phoneNumber);
        else
            System.out.println("Can't delete, must have at least one contact");
    }

    // -------------------------------- Methods related to SupplierProduct ------------------------------
    public void addNewProduct(SupplierProduct newProduct){
        MyProducts.add(newProduct);
        addManufacturer(newProduct.getMyProduct().getMyManufacturer());     //A supplier works with a manufacturer
    }

    public void deleteSupplierProduct(SupplierProduct supplierProduct)
    {
        MyProducts.remove(supplierProduct);
    }

    /**
     * return SupplierProduct object given the name and the manufacturer name of the product.
     */
    public SupplierProduct getSupplierProduct(String productName, String manufacturerName)
    {
        for(SupplierProduct supplierProduct : MyProducts)
        {
            Product currProduct = supplierProduct.getMyProduct();
            if(currProduct.getName().equals(productName) && currProduct.getMyManufacturer().getName().equals(manufacturerName))
                return supplierProduct;
        }
        return null;
    }

    /**
     * return string describe all the product that this supplier supply to us
     */
    private String printProducts(){
        String s = "";
        for(SupplierProduct supplierProduct : MyProducts)
            s += (supplierProduct + "\n");
        return s;
    }

    public List<SupplierProduct> getMyProducts() {return MyProducts;}

    // -------------------------------- Methods related to Order ------------------------------
    public void addNewOrder(Order newOrder) {MyOrders.add(newOrder);}

    /**
     * print all the orders that we ordered from this supplier
     */
    public void printOrdersHistory()
    {
        for(Order order: MyOrders)
            System.out.println(order);
    }

    /**
     * return list of all the orders that we ordered from this supplier
     */
    public List<Order> getMyOrders() {return MyOrders;}

    /**
     * return string describe all the contacts of this supplier
     */
    public String stringContacts(){
        String details = "";
        for (Map.Entry<String, Contact> entry : MyContacts.entrySet())
            details += entry.getValue();
        return details;
    }

    // -------------------------------- Methods related to Agreement ------------------------------

    public Agreement getMyAgreement() {return MyAgreement;}
    public String getName() {
        return Name;
    }
    public String getSupplierNum() {
        return SupplierNum;
    }
    public String getBankAccount() {
        return BankAccount;
    }
    public PaymentTerm getPayment() {
        return Payment;
    }
    public List<String> getCategories() {
        return Categories;
    }
    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }
    public void setPayment(PaymentTerm payment) {
        Payment = payment;
    }


    // -------------------------------- Methods related to Manufacturer ------------------------------
    public void addManufacturer(Manufacturer manufacturer) {
        MyManufacturers.put(manufacturer.getName(), manufacturer);
        manufacturer.addSupplier(this);
    }

    /**
     * This method used when this supplier stops working with a specific manufacturer. The method delete all the products
     * that produce by this manufacturer, and delete that manufacturer from the map of the manufacturer, that this supplier working with
     */
    public void stopWorkingWithManufacturer(String manufacturerName)
    {
        if(!MyManufacturers.containsKey(manufacturerName)) {
            System.out.println("The supplier does not work with this manufacturer");
            return;
        }
        List<SupplierProduct> newSupplierProducts = new ArrayList<SupplierProduct>(MyProducts);

        for(SupplierProduct myProduct : newSupplierProducts)    //The supplier stops supplying all the manufacturer's products
        {
            if(myProduct.getMyProduct().getMyManufacturer().getName().equals(manufacturerName))
                MyAgreement.deleteProductFromTheAgreement(myProduct);
        }

        MyManufacturers.remove(manufacturerName).deleteSupplier(this);  //Stop their cooperation

    }
    public Map<String, Manufacturer> getMyManufacturers() {return MyManufacturers;}

    /**
     * return string of all the manufacturers that the supplier working with.
     */
    private String toStringMyManufacturers(Map<String, Manufacturer> map){
        String details = "";
        for (Map.Entry<String, Manufacturer> entry : map.entrySet())
            details += entry.getValue();
        return details;
    }
}
