import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderGenerator{

    static float cheapestCombination = Float.POSITIVE_INFINITY;
    static Map<Supplier, Order> bestSuppliersCombination;
    static Map<Supplier, Order> blackList;

    public static void createNewOrder() {
        System.out.println("\nWelcome to the Order Generator.");

        List<Product> ProductsInOrder = new ArrayList<Product>();
        List<Integer> ProductsQuantity = new ArrayList<Integer>();
        blackList = new HashMap<Supplier, Order>();

        SupplierController.yourChoice = "y";
        while (SupplierController.yourChoice.equals("y")) {
            System.out.println("Please enter the product name: ");
            String productName = SupplierController.reader.nextLine();
            System.out.println("Please enter the name of the manufacturer: ");
            String manufacturerName = SupplierController.reader.nextLine();

            Product product = checkIfProductExistAndReturn(productName, manufacturerName);
            if(product == null)
                continue;
            ProductsInOrder.add(product);

            int productQuantity = CheckIntInputAndReturn("Enter the quantity of products you want to order:");
            ProductsQuantity.add(productQuantity);

            checkValidWithMessage("Do you want to insert another product? (y/n)");
        }

        try{createProductsSuppliersArray(ProductsInOrder, ProductsQuantity);}
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            createNewOrder();
        }
    }



    private static void createProductsSuppliersArray(List<Product> productsInOrder, List<Integer> productsQuantity) {
        List<SupplierProduct>[] productsSuppliersArray = new List[productsInOrder.size()];
        int[] numberOfSuppliersThatCanSupplyEachProduct = new int[productsInOrder.size()];

        for (int i = 0; i < productsSuppliersArray.length; i++) {
            productsSuppliersArray[i] = findSuppliersThatCanSupplyTheProduct(productsInOrder.get(i).getMySuppliersProduct(),
                    productsQuantity.get(i), productsQuantity, i, 0, productsInOrder.get(i));

            numberOfSuppliersThatCanSupplyEachProduct[i] = productsSuppliersArray[i].size();
        }

        generateCombinations(new int[productsInOrder.size()], numberOfSuppliersThatCanSupplyEachProduct,
                new int[productsInOrder.size()], 0, productsSuppliersArray, productsInOrder, productsQuantity);

        System.out.println("\nThe order has been committed\n");
        for (Map.Entry<Supplier, Order> pair : bestSuppliersCombination.entrySet())
            pair.getValue().invite();

        System.out.println("Final price: " + cheapestCombination);
    }

    private static List<SupplierProduct> findSuppliersThatCanSupplyTheProduct(List<SupplierProduct> suppliersProduct,
                                                                              int quantity, List<Integer> productsQuantity,
                                                                              int index, int quantityForExc, Product product) {
        if(suppliersProduct.size() == 0)
            throw new RuntimeException("Can not supply the quantity that you want for the product: " + product +
                    "\nIt is possible to supply maximum " + quantityForExc + " units");

        List<SupplierProduct> suppliersThatCanProvideTheProduct = new ArrayList<SupplierProduct>();
        SupplierProduct maxSupplierProduct = null;
        int maxProducts = 0;
        int curAmount = 0;
        for (SupplierProduct supplierProduct : suppliersProduct) {
            if (supplierProduct.getAmount() >= quantity)
                suppliersThatCanProvideTheProduct.add(supplierProduct);
            curAmount = supplierProduct.getAmount();
            if (curAmount > maxProducts)
            {
                maxProducts = curAmount;
                maxSupplierProduct = supplierProduct;
            }
        }
        if(suppliersThatCanProvideTheProduct.size() == 0){

            Supplier supplier = maxSupplierProduct.getMySupplier();
            if (!blackList.containsKey(maxSupplierProduct.getMySupplier()))
                blackList.put(supplier, new Order(supplier));

            blackList.get(supplier).addProductToOrder(maxProducts, maxSupplierProduct);
            List<SupplierProduct> newSuppliersProduct = new ArrayList<>(suppliersProduct);
            newSuppliersProduct.remove(maxSupplierProduct);
            productsQuantity.set(index,quantity - maxProducts);
            return findSuppliersThatCanSupplyTheProduct(newSuppliersProduct, quantity - maxProducts,productsQuantity,
                    index, quantityForExc + maxProducts, product);
        }

        return suppliersThatCanProvideTheProduct;
    }

    private static void generateCombinations(int[] start, int[] end, int[] current, int index,
                                             List<SupplierProduct>[] productsSuppliersArray, List<Product> productsInOrder,
                                             List<Integer> productsQuantity) {
        if (index == current.length) {
            UpdateTheBestOrder(productsSuppliersArray, productsInOrder, productsQuantity, current);
            return;
        }

        for (int i = start[index]; i < end[index]; i++) {
            current[index] = i;
            generateCombinations(start, end, current, index + 1, productsSuppliersArray, productsInOrder, productsQuantity);
        }
    }

    private static void UpdateTheBestOrder(List<SupplierProduct>[] productsSuppliersArray, List<Product> productsInOrder,
                                           List<Integer> productsQuantity, int[] combination) {

        Map<Supplier, Order> orders = new HashMap<Supplier, Order>(cloneMap(blackList));
        for (int i = 0; i < combination.length; i++) {
            SupplierProduct supplierProduct = productsSuppliersArray[i].get(combination[i]);
            Supplier supplier = supplierProduct.getMySupplier();

            if (!orders.containsKey(supplierProduct.getMySupplier()))
                orders.put(supplier, new Order(supplier));
            orders.get(supplier).addProductToOrder(productsQuantity.get(i), supplierProduct);
        }

        float curPrice = 0;
        for (Map.Entry<Supplier, Order> pair : orders.entrySet())
            curPrice += pair.getValue().getTotalPrice();
        if (curPrice < cheapestCombination) {
            cheapestCombination = curPrice;
            bestSuppliersCombination = orders;
        }

    }

    public static void checkValidWithMessage(String message)
    {
        do{
            System.out.println(message);
            SupplierController.yourChoice = SupplierController.reader.nextLine();
        } while (!SupplierController.yourChoice.equals("n") && !SupplierController.yourChoice.equals("y"));
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

    public static Product checkIfProductExistAndReturn(String productName, String manufacturerName)
    {
        List<String> keyPair = new ArrayList<>();
        keyPair.add(productName);
        keyPair.add(manufacturerName);

        if (!SupplierController.AllProducts.containsKey(keyPair)) {
            System.out.println("The product does not exist in the system! ");
            return null;
        }
        return SupplierController.AllProducts.get(keyPair);
    }

    public static Map<Supplier, Order> cloneMap(Map<Supplier, Order> originalMap){
        Map<Supplier, Order> deepCopyMap = new HashMap<Supplier, Order>();
        for (Map.Entry<Supplier, Order> pair : originalMap.entrySet())
            deepCopyMap.put(pair.getKey(),pair.getValue().clone());
        return deepCopyMap;
    }
}
