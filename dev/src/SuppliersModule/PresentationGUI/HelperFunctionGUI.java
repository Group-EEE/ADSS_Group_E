package SuppliersModule.PresentationGUI;

import InventoryModule.Business.*;
import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.Controllers.ReportController;
import SuppliersModule.Business.*;
import SuppliersModule.Business.Controllers.SupplierController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HelperFunctionGUI {

    static SupplierController supplierController = SupplierController.getInstance();
    static CategoryController categoryController = CategoryController.getInstance();
    static ProductController productController = ProductController.getInstance();
    static ReportController reportController = ReportController.getInstance();

    public static void addComponentsToFrame(JFrame frame, JComponent[] components) {
        for (JComponent component : components)
            frame.add(component);
    }

    public static void hideComponents(JComponent[] components) {
        for (JComponent component : components)
            component.setVisible(false);
    }

    public static void showComponents(JComponent[] components) {
        for (JComponent component : components)
            component.setVisible(true);
    }

    public static JComboBox<String> createComboBoxSupplierNum() {
        JComboBox<String> comboBoxSupplierNum = new JComboBox<>();
        List<String> comboBoxSupplierNumItems = new ArrayList<>();
        comboBoxSupplierNumItems.add("");
        Map<String, Supplier> allSuppliers = supplierController.returnAllSuppliers();
        for (Map.Entry<String, Supplier> pair : allSuppliers.entrySet())
            comboBoxSupplierNumItems.add(pair.getKey());

        for (String item : comboBoxSupplierNumItems)
            comboBoxSupplierNum.addItem(item);

        return comboBoxSupplierNum;
    }

    public static void createComboBoxManufacturer(JComboBox<String> comboBox, String supplierNum) {
        comboBox.removeAllItems();

        List<String> comboBoxSupplierNumItems = new ArrayList<>();
        comboBoxSupplierNumItems.add("");
        Map<String, Manufacturer> allManufacturers = supplierController.getSupplier(supplierNum).getMyManufacturers();

        for (Map.Entry<String, Manufacturer> pair : allManufacturers.entrySet())
            comboBoxSupplierNumItems.add(pair.getKey());

        for (String item : comboBoxSupplierNumItems)
            comboBox.addItem(item);
    }

    public static void createComboBoxSupplierProductDiscount(JComboBox<String> comboBox, String supplierNum, String catalog) {
        comboBox.removeAllItems();

        List<String> comboBoxProductDiscountItems = new ArrayList<>();
        comboBoxProductDiscountItems.add("");

        Map<Integer, SupplierProductDiscount> discountTreeMap = supplierController.getSupplier(supplierNum).getMyProducts().get(catalog).getDiscountProducts();

        for (Map.Entry<Integer, SupplierProductDiscount> pair : discountTreeMap.entrySet())
            comboBoxProductDiscountItems.add(String.valueOf(pair.getKey()));

        for (String item : comboBoxProductDiscountItems)
            comboBox.addItem(item);

    }

    public static JComboBox<String> createComboBoxSupplierProduct(String supplierNum) {
        JComboBox<String> comboBoxSupplierProduct = new JComboBox<>();
        List<String> comboBoxSupplierProductItems = new ArrayList<>();

        comboBoxSupplierProductItems.add("");
        Map<String, SupplierProduct> allSupplierProduct = supplierController.returnAllProductOfSupplier(supplierNum);
        for (Map.Entry<String, SupplierProduct> pair : allSupplierProduct.entrySet())
            comboBoxSupplierProductItems.add(pair.getKey());

        for (String item : comboBoxSupplierProductItems)
            comboBoxSupplierProduct.addItem(item);

        return comboBoxSupplierProduct;
    }

    public static JComboBox<String> createComboBoxContact(String supplierNum) {
        JComboBox<String> comboBoxContact = new JComboBox<>();
        List<String> comboBoxSupplierProductItems = new ArrayList<>();

        comboBoxSupplierProductItems.add("");
        Map<String, Contact> allSupplierProduct = supplierController.getSupplier(supplierNum).getMyContacts();
        for (Map.Entry<String, Contact> contact : allSupplierProduct.entrySet()) {
            comboBoxSupplierProductItems.add(contact.getValue().getName() + "," + contact.getKey());
        }

        for (String item : comboBoxSupplierProductItems)
            comboBoxContact.addItem(item);

        return comboBoxContact;
    }

    public static JComboBox<String> createComboBoxOrderDiscount(String supplierNum) {
        JComboBox<String> comboBoxSupplierProduct = new JComboBox<>();
        List<String> comboBoxSupplierProductItems = new ArrayList<>();

        comboBoxSupplierProductItems.add("");
        List<OrderDiscount> allOrderDiscount = supplierController.getSupplier(supplierNum).getMyAgreement().getAllOrderDiscount();

        for (OrderDiscount orderDiscount : allOrderDiscount)
            comboBoxSupplierProductItems.add(orderDiscount.getByPriceOrQuantity() + "," + orderDiscount.getAmount());

        for (String item : comboBoxSupplierProductItems)
            comboBoxSupplierProduct.addItem(item);

        return comboBoxSupplierProduct;
    }


    public static JComboBox<String> createComboBoxBarcodes() {
        JComboBox<String> comboBoxGenericProduct = new JComboBox<>();
        List<String> comboBoxGenericProductItems = new ArrayList<>();
        comboBoxGenericProductItems.add("");
        Map<Integer, GenericProduct> genericProductMap = supplierController.getAllGenericProduct();

        for (Map.Entry<Integer, GenericProduct> genericProduct : genericProductMap.entrySet()) {
            comboBoxGenericProductItems.add(String.valueOf(genericProduct.getKey()));
        }

        for (String item : comboBoxGenericProductItems)
            comboBoxGenericProduct.addItem(item);

        return comboBoxGenericProduct;
    }

    /**
     * This method create an "ProcessSuccessfully" frame.
     */
    public static void ShowProcessSuccessfully() {
        JFrame ProcessSuccessfullyFrame = new JFrame("The process ended successfully");
        ProcessSuccessfullyFrame.setSize(300, 300);
        ProcessSuccessfullyFrame.setLayout(null);

        JLabel addSuccessLabel = new JLabel("The process ended successfully");
        addSuccessLabel.setBounds(50, 50, 250, 20);
        ProcessSuccessfullyFrame.add(addSuccessLabel);

        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 100, 80, 20);
        ProcessSuccessfullyFrame.add(okButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProcessSuccessfullyFrame.dispose();
            }
        });

        ProcessSuccessfullyFrame.setVisible(true);
    }


    /**
     * This method create an "AddSuccess" frame.
     */
    public static void ShowAddSuccess() {
        JFrame AddSuccessFrame = new JFrame("Add success");
        AddSuccessFrame.setSize(200, 200);
        AddSuccessFrame.setLayout(null);

        JLabel addSuccessLabel = new JLabel("Add success");
        addSuccessLabel.setBounds(50, 50, 150, 20);
        AddSuccessFrame.add(addSuccessLabel);

        JButton okButton = new JButton("OK");
        okButton.setBounds(50, 100, 80, 20);
        AddSuccessFrame.add(okButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddSuccessFrame.dispose();
            }
        });

        AddSuccessFrame.setVisible(true);
    }

    public static JFrame createNewFrame(String title) {
        JFrame page1Frame = new JFrame(title);
        page1Frame.setSize(500, 500);
        page1Frame.setLayout(null);
        ImageIcon backgroundImage = new ImageIcon(HelperFunctionGUI.class.getResource("/SuperMarketImage.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        page1Frame.setLayout(new BorderLayout());
        page1Frame.setContentPane(backgroundLabel);

        return page1Frame;
    }

    public static JButton createExitButton(JFrame currFrame, JFrame oldFrame) {
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200, 410, 100, 40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.dispose();
                oldFrame.setVisible(true);
            }
        });

        currFrame.add(exitButton);
        return exitButton;
    }

    /**
     * This method create label which functions as a check label
     *
     * @param message - A message that the label will display
     * @param x       - coordinate x
     * @param y       - coordinate y
     * @param width   - width
     * @param height  - height
     * @return JLabel
     */
    public static JLabel createCheckLabel(String message, int x, int y, int width, int height) {
        JLabel checkLabel = new JLabel(message);
        checkLabel.setForeground(Color.RED);
        checkLabel.setBounds(x, y, width, height);
        checkLabel.setVisible(false);
        return checkLabel;
    }

    //------------------------------------- Check functions ----------------------------------------

    /**
     * Check if value is valid integer
     *
     * @param value - value
     * @return true - if valid, false - else
     */
    public static boolean CheckIntInput(String value) {
        int num;
        try {
            num = Integer.parseInt(value);
        } catch (NumberFormatException error) {
            return false;
        }
        return num > 0;
    }

    /**
     * Check if value is valid float
     *
     * @param value - value
     * @return true - if valid, false - else
     */
    public static boolean CheckFloatInput(String value) {
        float num;
        try {
            num = Float.parseFloat(value);
        } catch (NumberFormatException error) {
            return false;
        }
        return !(num <= 0);
    }

    /**
     * Check if value is valid float
     *
     * @param value - value
     * @return true - if valid, false - else
     */
    public static boolean CheckDoubleInput(String value) {
        double num;
        try {
            num = Double.parseDouble(value);
        } catch (NumberFormatException error) {
            return false;
        }
        return !(num <= 0);
    }


    public static JComboBox<String> createComboBoxCategories() {
        JComboBox<String> comboBoxCategories = new JComboBox<>();

        List<String> comboBoxCategoriesItems = new ArrayList<>();
        comboBoxCategoriesItems.add("");

        List<Category> allCategories = categoryController.getCategories();
        for (Category category : allCategories)
            comboBoxCategoriesItems.add(category.getName());

        for (String item : comboBoxCategoriesItems)
            comboBoxCategories.addItem(item);

        return comboBoxCategories;
    }

    public static JComboBox<String> createComboBoxProductName() {
        JComboBox<String> comboBoxProductName = new JComboBox<>();
        List<String> comboBoxProductsItems = new ArrayList<>();
        comboBoxProductsItems.add("");

        List<SuperLiProduct> allProducts = ProductController.getProducts();
        for (SuperLiProduct slp : allProducts)
            comboBoxProductsItems.add(slp.getPName());

        for (String item : comboBoxProductsItems)
            comboBoxProductName.addItem(item);

        return comboBoxProductName;
    }

    public static void createSpecificProductIds(String ProductName, JComboBox<String> comboBoxProductID) {
        comboBoxProductID.removeAllItems();
        List<String> comboBoxSpecificProductItems = new ArrayList<>();
        comboBoxSpecificProductItems.add("");

        List<SpecificProduct> allSpecificProducts = ProductController.getSpecificProductsByProductName(ProductName);
        for (SpecificProduct slp : allSpecificProducts)
            comboBoxSpecificProductItems.add(Integer.toString(slp.getSp_ID()));
        for (String item : comboBoxSpecificProductItems)
            comboBoxProductID.addItem(item);
        comboBoxProductID.setSelectedItem(comboBoxProductID);
    }

    public static JComboBox<String> createComboBoxProductBarcode() {
        JComboBox<String> comboBoxProductBarcode = new JComboBox<>();
        List<String> comboBoxProductItems = new ArrayList<>();
        comboBoxProductItems.add("");

        List<SuperLiProduct> allProducts = ProductController.getProducts();
        for (SuperLiProduct slp : allProducts)
            comboBoxProductItems.add(Integer.toString(slp.getBarcode()));
        for (String item : comboBoxProductItems)
            comboBoxProductBarcode.addItem(item);

        return comboBoxProductBarcode;
    }

    public static void setProductNameField(String choose, JTextField productNameField) {
        productNameField.removeAll();
        String name = productController.getProductByBarcode(Integer.parseInt(choose)).getPName();
        productNameField.setText(name);
    }

    public static JComboBox<String> createComboBoxofnewProductBarcode() {
        JComboBox<String> comboBoxProductBarcode = new JComboBox<>();
        List<String> comboBoxProductItems = new ArrayList<>();
        comboBoxProductItems.add("");

        List<Integer> allProducts = ProductController.returnListofneProductsBarcode();
        for (Integer barcode : allProducts)
            comboBoxProductItems.add(Integer.toString(barcode));
        for (String item : comboBoxProductItems)
            comboBoxProductBarcode.addItem(item);
        return comboBoxProductBarcode;
    }

    public static void settextAreaAllBarcodes(JTextArea textAreaAllBarcodes) {
        String barcodes = ProductController.getInstance().GetAllProductBarcode();
        textAreaAllBarcodes.setText(barcodes);
    }

    public static String createTextAreaCategories() {
        List<String> CategoryNames = new ArrayList<>();
        String willBeReturned = "";
        List<Category> allCategories = categoryController.getCategories();
        for (Category category : allCategories)
            CategoryNames.add(category.getName());
        for (String categoryName : CategoryNames) {
            willBeReturned = willBeReturned + categoryName + "\n";
        }

        return willBeReturned;
    }

    public static boolean canRemoveCategory(String categoryName) {
        //if true cannot remove category
        boolean exist = false; //if there is a product that belongs to this category- will be true
        Category c = null;
        if (categoryController.check_if_exist_cat(categoryName)) {
            for (int j = 0; j < ProductController.getProducts().size(); j++) {
                //check for every product in ths store if its category is the given category
                if (ProductController.getProducts().get(j).getCategory().compareTo(categoryName) == 0) {
                    exist = true;
                }
            }
        }
        return exist;
    }

    public static void setSupplierComboBoxField(String choose, JComboBox<String> SupplierNameComboBox) {
        SupplierNameComboBox.removeAllItems();
        List<String> SupplierNameItems = supplierController.returnSuppliersaccordingtoBarcode(Integer.parseInt(choose));
        SupplierNameComboBox.addItem("");
        for (String supplier : SupplierNameItems) {
            SupplierNameComboBox.addItem(supplier);
        }
    }


    public static void setSubCategoriesComboBoxField(String choose, JComboBox<String> SubCategoriesComboBox) {
        SubCategoriesComboBox.removeAllItems();

        List<String> comboBoxSubCategoriesItems = new ArrayList<>();
        comboBoxSubCategoriesItems.add("");

        List<SubCategory> allCategories = categoryController.getAllSubByMain(choose);
        for (SubCategory subCategory : allCategories)
            comboBoxSubCategoriesItems.add(subCategory.getName());

        for (String item : comboBoxSubCategoriesItems)
            SubCategoriesComboBox.addItem(item);

    }

    public static boolean canRemoveSubCategory(String categoryName, String SubCategoryName) {
        //if exist is true - cant remove
        boolean exist = false; //if there is a product that belongs to this subcategory- will be true
        if (categoryController.check_if_exist_subcat(SubCategoryName)) {
            for (int j = 0; j < ProductController.getProducts().size(); j++) {
                //check for every product in ths store if its subcategory is the given subcategory
                if (ProductController.getProducts().get(j).getSubCategory().compareTo(SubCategoryName) == 0) {
                    exist = true;
                }
            }
        }
        return exist;
    }
    public static boolean canRemoveSubSubCategory(String SubSubCategoryName) {
        //if exist is true - cant remove
        boolean exist = false; //if there is a product that belongs to this subcategory- will be true
        if (categoryController.check_if_exist_subsubcat(SubSubCategoryName)) {
            for (int j = 0; j < ProductController.getProducts().size(); j++) {
                //check for every product in ths store if its subcategory is the given subcategory
                if (ProductController.getProducts().get(j).getSubSubCategory().compareTo(SubSubCategoryName) == 0) {
                    exist = true;
                }
            }
        }
        return exist;
    }
    public static void setSubSubCategoriesComboBoxField(String Mainchoose,String Subchoose, JComboBox<String> SubSubCategoriesComboBox) {
        SubSubCategoriesComboBox.removeAllItems();

        List<String> comboBoxSubCategoriesItems = new ArrayList<>();
        comboBoxSubCategoriesItems.add("");

        List<SubSubCategory> allCategories = categoryController.getAllSubSubByMainandSub(Mainchoose,Subchoose);
        for (SubSubCategory subCategory : allCategories)
            comboBoxSubCategoriesItems.add(subCategory.getName());

        for (String item : comboBoxSubCategoriesItems)
            SubSubCategoriesComboBox.addItem(item);

    }
    public static String createTextAreaOrderReport(String reporter){
        return reportController.createOrderReport(reporter).toString();
    }
}

