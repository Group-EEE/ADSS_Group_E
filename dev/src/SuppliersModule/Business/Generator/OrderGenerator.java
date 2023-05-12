package SuppliersModule.Business.Generator;

import DataAccess.SuperLiDB;
import InventoryModule.Business.OrderReport;
import SuppliersModule.Business.*;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.Controllers.SupplierController;

import java.util.*;

/**
 * The class OrderController ask the user for products that he want to invite.
 * The class creates the cheapest order that he can build, and invite the products from the supplier.
 */
public class OrderGenerator {
    static OrderController orderController;
    static SupplierController supplierController;
    static SuperLiDB superLiDB;
    static float cheapestCombination; // The price of the cheapest combination of orders from suppliers
    static Map<Supplier, OrderFromSupplier> bestSuppliersCombination; // The cheapest combination of orders from suppliers
    static Map<Supplier, OrderFromSupplier> MustBuyOrders; // When we want to buy a certain quantity of certain product, and there is no supplier that can supply the desired quantity, We must buy from the supplier that can supply the maximum quantity. This map includes all the orders like mention

    static List<GenericProduct> ProductsInOrder ; // contain all the products in the order

    static List<Integer> ProductsQuantity ; // contain the quantity of the products found in the ProductsInOrder list. ProductsQuantity[n] = ProductsInOrder[n] -> quantity

    static List<SupplierProduct>[] productsSuppliersArray; // Array contains a lists of all the suppliers that can supply a certain product with the desired quantity. productsSuppliersArray[n] = ProductsInOrder[n] -> list of the supplier that can supply this product (SupplierProduct object)


    public static String makeOrderFromOrderReport(OrderReport orderReport){
        reset();
        List <Integer> barcodes = orderReport.getBarcodes(); //get barcode method
        for(Integer barcode : barcodes)
            ProductsInOrder.add(supplierController.findGenericProductByBarcode(barcode));
        ProductsQuantity = orderReport.getAmount();
        return makeOrder();
    }

    public static void reset() {

        ProductsInOrder = new ArrayList<>(); // contain all the products in the order
        ProductsQuantity = new ArrayList<>(); // contain the quantity of the products found in the ProductsInOrder list. ProductsQuantity[n] = ProductsInOrder[n] -> quantity
        MustBuyOrders = new HashMap<>();
        bestSuppliersCombination = new HashMap<>();
        cheapestCombination = Float.POSITIVE_INFINITY;

        orderController = OrderController.getInstance();
        supplierController = SupplierController.getInstance();
        superLiDB = SuperLiDB.getInstance();
    }

    public static String makeOrder(){
        try{createProductsSuppliersArray();}
        catch (RuntimeException e){ // Can not supply the quantity that we want for one of the products product
            System.out.println(e.getMessage());
        }
        invite();
        return stringOrder();
    }

    public static String stringOrder() {
        String s = "";
        s += "\nThe order has been committed\n";
        for (Map.Entry<Supplier, OrderFromSupplier> pair : bestSuppliersCombination.entrySet())
            s += pair.getValue().toString();

        s += "Final price: " + cheapestCombination;
        return s;
    }

    /**
     * create the "ProductsSuppliersArray". This array will use us to find the best combination of orders from suppliers.
     * Each cell in the array will contain a list of all the suppliers that can supply this product (SupplierProduct object)
     */
    private static void createProductsSuppliersArray() {
        productsSuppliersArray = new List[ProductsInOrder.size()];
        int[] numberOfSuppliersThatCanSupplyEachProduct = new int[ProductsInOrder.size()]; // numberOfSuppliersThatCanSupplyEachProduct[n] = ProductsInOrder[n] -> number of suppliers that can supply the product

        for (int i = 0; i < productsSuppliersArray.length; i++) { // for each product we build a list of all the suppliers that can supply it (SupplierProduct object)
            productsSuppliersArray[i] = peakSuppliersByPriority(ProductsInOrder.get(i).getMySuppliersProduct(),ProductsQuantity.get(i), i);
            numberOfSuppliersThatCanSupplyEachProduct[i] = productsSuppliersArray[i].size();
        }

        generateCombinations(new int[ProductsInOrder.size()], numberOfSuppliersThatCanSupplyEachProduct,
                new int[ProductsInOrder.size()], 0); // find the cheapest option for order of the given product

    }

    private static List<SupplierProduct> peakSuppliersByPriority(List<SupplierProduct> supplierProducts, int desiredQuantity, int index){
        List<List<SupplierProduct>> suppliersByDay = createSuppliersByDayList(supplierProducts);

        int amountPerDay;
        int curAmount;
        List<SupplierProduct> returnList = new ArrayList<>();
        int quantity = desiredQuantity;
        for(List<SupplierProduct> supplierProductList : suppliersByDay){
            amountPerDay = 0;
            for (SupplierProduct supplierProduct : supplierProductList){
                curAmount = supplierProduct.getAmount();
                if(curAmount >= quantity)
                    returnList.add(supplierProduct);
                amountPerDay += curAmount;
            }
            if(returnList.size() != 0) {
                ProductsQuantity.set(index,quantity);
                return returnList;
            }
            if(amountPerDay >= quantity)
                return findSuppliersThatCanSupplyTheProduct(supplierProductList,quantity,index);
            addToMustBuyList(supplierProductList);
            quantity -= amountPerDay;
        }
        throw new RuntimeException("Can not supply the desired quantity for the product: " + supplierProducts.get(0).getMyProduct() +
                "\nIt is possible to supply maximum " + (desiredQuantity - quantity) + " units");
    }

    private static void addToMustBuyList(List<SupplierProduct> supplierProducts){
        Supplier supplier;
        for (SupplierProduct supplierProduct : supplierProducts){
            supplier = supplierProduct.getMySupplier();
            if (!MustBuyOrders.containsKey(supplier)) // If we haven't opened an order to this supplier in the blackList yet
                MustBuyOrders.put(supplier, new OrderFromSupplier(supplier)); // Open an order to this supplier in the blackList

            MustBuyOrders.get(supplier).addProductToOrder(supplierProduct.getAmount(), supplierProduct); // Add the product to the order, that opened to the supplier
        }
    }

    private static List<List<SupplierProduct>> createSuppliersByDayList(List<SupplierProduct> supplierProducts){
        List<List<SupplierProduct>> suppliersByDay = new ArrayList<>();

        Queue<SupplierProduct> queue = new PriorityQueue<>(new MyCompare());
        queue.addAll(supplierProducts);
        if(queue.isEmpty())
            return suppliersByDay;

        SupplierProduct curSupplierProduct = queue.poll();
        List <SupplierProduct> curList = new ArrayList<>();
        curList.add(curSupplierProduct);
        suppliersByDay.add(curList);

        int curNumberOfDaysToSupply;
        int prevNumberOfDaysToSupply = curSupplierProduct.getDaysToSupplyFromToday();

        while (!queue.isEmpty()){
            curSupplierProduct = queue.poll();
            curNumberOfDaysToSupply = curSupplierProduct.getDaysToSupplyFromToday();
            if(curNumberOfDaysToSupply > prevNumberOfDaysToSupply){
                curList = new ArrayList<>();
                suppliersByDay.add(curList);
                prevNumberOfDaysToSupply = curNumberOfDaysToSupply;
            }
            curList.add(curSupplierProduct);
        }
        return suppliersByDay;
    }

    /**
     * return a list of all the suppliers that can supply the given product (SupplierProduct object), with the desired quantity.
     * @param suppliersProduct  All the suppliers that the given product found in their agreement
     * @param quantity  The desired quantity
     * @param index The index of the product in the "ProductsInOrder" list
     */
    private static List<SupplierProduct> findSuppliersThatCanSupplyTheProduct(List<SupplierProduct> suppliersProduct, int quantity, int index) {

        List<SupplierProduct> suppliersThatCanSupplyTheProduct = new ArrayList<>();
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
                    index); // Call the function again. We want to find the list of suppliers that can supply the desired quantity **after** we order from the max supplier.
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
        for (Map.Entry<Supplier, OrderFromSupplier> pair : bestSuppliersCombination.entrySet()) {
            pair.getValue().invite();
            superLiDB.insertOrderFromSupplier(pair.getValue());
        }
    }


    /**
     * deep copy to the given map
     */
    private static Map<Supplier, OrderFromSupplier> cloneMap(Map<Supplier, OrderFromSupplier> originalMap){
        Map<Supplier, OrderFromSupplier> deepCopyMap = new HashMap<>();
        for (Map.Entry<Supplier, OrderFromSupplier> pair : originalMap.entrySet())
            deepCopyMap.put(pair.getKey(),pair.getValue().clone());
        return deepCopyMap;
    }
}

class MyCompare implements Comparator<SupplierProduct> {

    @Override
    public int compare(SupplierProduct first, SupplierProduct other) {
        return first.getDaysToSupplyFromToday() - other.getDaysToSupplyFromToday();
    }

}