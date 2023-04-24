package SuppliersModule.Business.Generator;

import InventoryModule.OrderReport;
import SuppliersModule.Business.*;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.Controllers.SupplierController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class SuppliersModule.Business.Controllers.OrderController ask the user for products that he want to invite.
 * The class creates the cheapest order that he can build, and invite the products from the supplier.
 */
public class OrderGenerator {
    static OrderController orderController;
    static SupplierController supplierController;

    static float cheapestCombination; // The price of the cheapest combination of orders from suppliers
    static Map<Supplier, OrderFromSupplier> bestSuppliersCombination; // The cheapest combination of orders from suppliers
    static Map<Supplier, OrderFromSupplier> MustBuyOrders; // When we want to buy a certain quantity of certain product, and there is no supplier that can supply the desired quantity, We must buy from the supplier that can supply the maximum quantity. This map includes all the orders like mention

    static List<GenericProduct> ProductsInOrder ; // contain all the products in the order

    static List<Integer> ProductsQuantity ; // contain the quantity of the products found in the ProductsInOrder list. ProductsQuantity[n] = ProductsInOrder[n] -> quantity

    static List<SupplierProduct>[] productsSuppliersArray; // Array contains a lists of all the suppliers that can supply a certain product with the desired quantity. productsSuppliersArray[n] = ProductsInOrder[n] -> list of the supplier that can supply this product (SuppliersModule.Business.SupplierProduct object)


    public static String makeOrderFromOrderReport(OrderReport orderReport){
        reset();
        ProductsInOrder = orderReport.getGenericProducts();
        ProductsQuantity = orderReport.getAmount();
        return makeOrder();
    }

    public static void reset() {

        ProductsInOrder = new ArrayList<GenericProduct>(); // contain all the products in the order
        ProductsQuantity = new ArrayList<Integer>(); // contain the quantity of the products found in the ProductsInOrder list. ProductsQuantity[n] = ProductsInOrder[n] -> quantity
        MustBuyOrders = new HashMap<Supplier, OrderFromSupplier>();
        bestSuppliersCombination = new HashMap<Supplier, OrderFromSupplier>();
        cheapestCombination = Float.POSITIVE_INFINITY;

        orderController = OrderController.getInstance();
        supplierController = SupplierController.getInstance();
    }

    public static boolean addProductToTheList(String productName, String manufacturerName){
        GenericProduct genericProduct = checkIfProductExistAndReturn(productName, manufacturerName);
        if(genericProduct == null)
            return false;
        ProductsInOrder.add(genericProduct);
        return true;
    }

    public static void addQuantityOfTheLestEnteredProduct(int productQuantity){
        ProductsQuantity.add(productQuantity);
    }

    public static String makeOrder(){
        try{createProductsSuppliersArray();}
        catch (RuntimeException e){ // Can not supply the quantity that we want for one of the products product
            System.out.println(e.getMessage());
        }
        invite();
        Order order = new Order(cheapestCombination,bestSuppliersCombination,ProductsInOrder,ProductsQuantity);
        orderController.addOrder(order);
        return order.toString();
    }

    /**
     * create the "ProductsSuppliersArray". This array will use us to find the best combination of orders from suppliers.
     * Each cell in the array will contain a list of all the suppliers that can supply this product (SuppliersModule.Business.SupplierProduct object)
     */
    private static void createProductsSuppliersArray() {
        productsSuppliersArray = new List[ProductsInOrder.size()];
        int[] numberOfSuppliersThatCanSupplyEachProduct = new int[ProductsInOrder.size()]; // numberOfSuppliersThatCanSupplyEachProduct[n] = ProductsInOrder[n] -> number of suppliers that can supply the product


        for (int i = 0; i < productsSuppliersArray.length; i++) { // for each product we build a list of all the suppliers that can supply it (SuppliersModule.Business.SupplierProduct object)
            productsSuppliersArray[i] = findSuppliersThatCanSupplyTheProduct(ProductsInOrder.get(i).getMySuppliersProduct(),
                    ProductsQuantity.get(i), i, 0, ProductsInOrder.get(i));

            numberOfSuppliersThatCanSupplyEachProduct[i] = productsSuppliersArray[i].size();
        }

        generateCombinations(new int[ProductsInOrder.size()], numberOfSuppliersThatCanSupplyEachProduct,
                new int[ProductsInOrder.size()], 0); // find the cheapest option for order of the given product

    }

    /**
     * return a list of all the suppliers that can supply the given product (SuppliersModule.Business.SupplierProduct object), with the desired quantity.
     * @param suppliersProduct  All the suppliers that the given product found in their agreement
     * @param quantity  The desired quantity
     * @param index The index of the product in the "ProductsInOrder" list
     * @param quantityForExc    In case of exception this quantity will be the max quantity that can be supplied
     * @param genericProduct   The desired product
     */
    private static List<SupplierProduct> findSuppliersThatCanSupplyTheProduct(List<SupplierProduct> suppliersProduct, int quantity,
                                                                              int index, int quantityForExc, GenericProduct genericProduct) {
        if(suppliersProduct.size() == 0)
            throw new RuntimeException("Can not supply the desired quantity for the product: " + genericProduct +
                    "\nIt is possible to supply maximum " + quantityForExc + " units");

        List<SupplierProduct> suppliersThatCanSupplyTheProduct = new ArrayList<SupplierProduct>();
        SupplierProduct maxSupplierProduct = null; // The supplier that can supply the maximum quantity
        int maxProducts = 0; // The maximum amount that the maxSupplierProduct can supply
        int curAmount;
        for (SupplierProduct supplierProduct : suppliersProduct) { // Ran through all the suppliers that the given product found in their agreement
            if (supplierProduct.getAmount() >= quantity)
                suppliersThatCanSupplyTheProduct.add(supplierProduct); // The current supplier can supply the desired quantity. We adding it to the list
            curAmount = supplierProduct.getAmount();
            if (curAmount > maxProducts)
            {
                maxProducts = curAmount;
                maxSupplierProduct = supplierProduct; // The current supplier supply the biggest quantity so far
            }
        }
        if(suppliersThatCanSupplyTheProduct.size() == 0){ // If there is no supplier that can supply the desired quantity

            Supplier maxSupplier = maxSupplierProduct.getMySupplier();
            if (!MustBuyOrders.containsKey(maxSupplier)) // If we haven't opened an order to this supplier in the blackList yet
                MustBuyOrders.put(maxSupplier, new OrderFromSupplier(maxSupplier)); // Open an order to this supplier in the blackList

            MustBuyOrders.get(maxSupplier).addProductToOrder(maxProducts, maxSupplierProduct); // Add the product to the order, that opened to the supplier

            // *************************** Update the variables before the recursive call ******************************
            List<SupplierProduct> newSuppliersProduct = new ArrayList<>(suppliersProduct); // Copy the given "suppliersProduct" list
            newSuppliersProduct.remove(maxSupplierProduct); // Remove the supplier from the list of the supplier that can supply the product
            ProductsQuantity.set(index,quantity - maxProducts); // Update the desired quantity of the product

            return findSuppliersThatCanSupplyTheProduct(newSuppliersProduct, quantity - maxProducts,
                    index, quantityForExc + maxProducts, genericProduct); // Call the function again. We want to find the list of suppliers that can supply the desired quantity **after** we order from the max supplier.
        }

        return suppliersThatCanSupplyTheProduct;
    }

    /**
     * Recursive function. This function helps us to ran through all the combinations of orders from suppliers,
     * so we can choose the cheapest one.
     * @param start The first combination
     * @param end The last combination
     * @param current The current combination
     * @param index The index of the product in the productsSuppliersArray.
     * */
    private static void generateCombinations(int[] start, int[] end, int[] current, int index) {
        if (index == current.length) {
            UpdateTheBestOrder(current);
            return;
        }

        for (int i = start[index]; i < end[index]; i++) {
            current[index] = i;
            generateCombinations(start, end, current, index + 1);
        }
    }

    /**
     * Calculating the current combination of orders from suppliers. If this combination is the cheapest so far,
     * we're updating the "cheapestCombination" static variable.
     * @param combination For example the combination = {1,5,0,3} means that first product will order from the supplier position in the 1 cell in the productsSuppliersArray[0] list. the second product from the 5 supplier in the productsSuppliersArray[0] list and so on.
     */
    private static void UpdateTheBestOrder(int[] combination) {

        Map<Supplier, OrderFromSupplier> orders = cloneMap(MustBuyOrders);
        for (int i = 0; i < combination.length; i++) { // For each product
            SupplierProduct supplierProduct = productsSuppliersArray[i].get(combination[i]); // save the current supplier product
            Supplier supplier = supplierProduct.getMySupplier();

            if (!orders.containsKey(supplierProduct.getMySupplier())) // If we haven't opened an order to this supplier in the current orders list yet
                orders.put(supplier, new OrderFromSupplier(supplier)); // open a new order in the current order list
            orders.get(supplier).addProductToOrder(ProductsQuantity.get(i), supplierProduct); // add this product to the current order
        }

        // ******************* Updating the "cheapestCombination" static variable in case needed ***********************
        float curPrice = 0;
        for (Map.Entry<Supplier, OrderFromSupplier> pair : orders.entrySet())
            curPrice += pair.getValue().getTotalPriceAfterDiscount(); // calculating the final price of the current order
        if (curPrice < cheapestCombination) { // if this order cheaper than the cheapest we found so far
            cheapestCombination = curPrice;
            bestSuppliersCombination = orders; // This order is the cheapest we found so far
        }

    }

    private static void invite(){
        for (Map.Entry<Supplier, OrderFromSupplier> pair : bestSuppliersCombination.entrySet())
            pair.getValue().invite();
    }

    /**
     * return the product with the compatible attribute (productName, manufacturerName). return null if not exist
     */
    public static GenericProduct checkIfProductExistAndReturn(String productName, String manufacturerName)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);

        if (!supplierController.getAllProducts().containsKey(keyPair)) {
            System.out.println("The product does not exist in the system! ");
            return null;
        }
        return supplierController.getAllProducts().get(keyPair);
    }

    /**
     * deep copy to the given map
     */
    private static Map<Supplier, OrderFromSupplier> cloneMap(Map<Supplier, OrderFromSupplier> originalMap){
        Map<Supplier, OrderFromSupplier> deepCopyMap = new HashMap<Supplier, OrderFromSupplier>();
        for (Map.Entry<Supplier, OrderFromSupplier> pair : originalMap.entrySet())
            deepCopyMap.put(pair.getKey(),pair.getValue().clone());
        return deepCopyMap;
    }
}
