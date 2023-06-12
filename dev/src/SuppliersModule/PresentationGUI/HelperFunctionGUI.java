package SuppliersModule.PresentationGUI;

import InventoryModule.Business.Controllers.ProductController;
import SuppliersModule.Business.*;
import SuppliersModule.Business.Controllers.SupplierController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class HelperFunctionGUI {

    static SupplierController supplierController = SupplierController.getInstance();

    public static void addComponentsToFrame(JFrame frame, JComponent[] components){
        for(JComponent component : components)
            frame.add(component);
    }

    public static void hideComponents(JComponent[] components){
        for(JComponent component : components)
            component.setVisible(false);
    }

    public static void showComponents(JComponent[] components){
        for(JComponent component : components)
            component.setVisible(true);
    }

    public static JComboBox<String> createComboBoxSupplierNum()
    {
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

    public static void createComboBoxManufacturer(JComboBox<String> comboBox, String supplierNum)
    {
        comboBox.removeAllItems();

        List<String> comboBoxSupplierNumItems = new ArrayList<>();
        comboBoxSupplierNumItems.add("");
        Map<String, Manufacturer> allManufacturers = supplierController.getSupplier(supplierNum).getMyManufacturers();

        for (Map.Entry<String, Manufacturer> pair : allManufacturers.entrySet())
            comboBoxSupplierNumItems.add(pair.getKey());

        for (String item : comboBoxSupplierNumItems)
            comboBox.addItem(item);
    }

    public static void createComboBoxSupplierProductDiscount(JComboBox<String> comboBox, String supplierNum, String catalog)
    {
        comboBox.removeAllItems();

        List<String> comboBoxProductDiscountItems = new ArrayList<>();
        comboBoxProductDiscountItems.add("");

        Map<Integer, SupplierProductDiscount> discountTreeMap = supplierController.getSupplier(supplierNum).getMyProducts().get(catalog).getDiscountProducts();

        for (Map.Entry<Integer, SupplierProductDiscount> pair : discountTreeMap.entrySet())
            comboBoxProductDiscountItems.add(String.valueOf(pair.getKey()));

        for (String item : comboBoxProductDiscountItems)
            comboBox.addItem(item);

    }

    public static JComboBox<String>  createComboBoxSupplierProduct(String supplierNum)
    {
        JComboBox<String> comboBoxSupplierProduct= new JComboBox<>();
        List<String> comboBoxSupplierProductItems = new ArrayList<>();

        comboBoxSupplierProductItems.add("");
        Map<String, SupplierProduct> allSupplierProduct = supplierController.returnAllProductOfSupplier(supplierNum);
        for (Map.Entry<String, SupplierProduct> pair : allSupplierProduct.entrySet())
            comboBoxSupplierProductItems.add(pair.getKey());

        for (String item : comboBoxSupplierProductItems)
            comboBoxSupplierProduct.addItem(item);

        return comboBoxSupplierProduct;
    }

    public static JComboBox<String>  createComboBoxContact(String supplierNum)
    {
        JComboBox<String> comboBoxContact= new JComboBox<>();
        List<String> comboBoxSupplierProductItems = new ArrayList<>();

        comboBoxSupplierProductItems.add("");
        Map<String, Contact> allSupplierProduct = supplierController.getSupplier(supplierNum).getMyContacts();
        for (Map.Entry<String, Contact> contact : allSupplierProduct.entrySet())
            comboBoxSupplierProductItems.add(contact.getValue().getName() + "," + contact.getKey());

        for (String item : comboBoxSupplierProductItems)
            comboBoxContact.addItem(item);

        return comboBoxContact;
    }

    public static JComboBox<String>  createComboBoxOrderDiscount(String supplierNum)
    {
        JComboBox<String> comboBoxSupplierProduct= new JComboBox<>();
        List<String> comboBoxSupplierProductItems = new ArrayList<>();

        comboBoxSupplierProductItems.add("");
        List<OrderDiscount> allOrderDiscount = supplierController.getSupplier(supplierNum).getMyAgreement().getAllOrderDiscount();

        for (OrderDiscount orderDiscount : allOrderDiscount)
            comboBoxSupplierProductItems.add(orderDiscount.getByPriceOrQuantity() + "," + orderDiscount.getAmount());

        for (String item : comboBoxSupplierProductItems)
            comboBoxSupplierProduct.addItem(item);

        return comboBoxSupplierProduct;
    }


    public static JComboBox<String> createComboBoxNewProductsBarcodes()
    {
        JComboBox<String> comboBoxSupplierNum = new JComboBox<>();
        List<String> comboBoxSupplierNumItems = new ArrayList<>();
        comboBoxSupplierNumItems.add("");
        for (int barcode : ProductController.BarcodesOfNewProducts)
            comboBoxSupplierNumItems.add(String.valueOf(barcode));

        for (String item : comboBoxSupplierNumItems)
            comboBoxSupplierNum.addItem(item);

        return comboBoxSupplierNum;
    }

    /**
     * This method create an "ProcessSuccessfully" frame.
     */
    public static void ShowProcessSuccessfully()
    {
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
            public void actionPerformed(ActionEvent e) {ProcessSuccessfullyFrame.dispose();}
        });

        ProcessSuccessfullyFrame.setVisible(true);
    }


    /**
     * This method create an "AddSuccess" frame.
     */
    public static void ShowAddSuccess()
    {
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
            public void actionPerformed(ActionEvent e) {AddSuccessFrame.dispose();}
        });

        AddSuccessFrame.setVisible(true);
    }

    public static JFrame createNewFrame(String title){
        JFrame page1Frame = new JFrame(title);
        page1Frame.setSize(500, 500);
        page1Frame.setLayout(null);
        return page1Frame;
    }

    public static JButton createExitButton(JFrame currFrame, JFrame oldFrame){
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200, 410, 100, 40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.dispose();
                oldFrame.setVisible(true);
            }});

        currFrame.add(exitButton);
        return exitButton;
    }

    /**
     * This method create label which functions as a check label
     * @param message - A message that the label will display
     * @param x - coordinate x
     * @param y - coordinate y
     * @param width - width
     * @param height - height
     * @return JLabel
     */
    public static JLabel createCheckLabel(String message, int x, int y, int width, int height)
    {
        JLabel checkLabel = new JLabel(message);
        checkLabel.setForeground(Color.RED);
        checkLabel.setBounds(x, y, width, height);
        checkLabel.setVisible(false);
        return checkLabel;
    }

    //------------------------------------- Check functions ----------------------------------------

    /**
     * Check if value is valid integer
     * @param value - value
     * @return true - if valid, false - else
     */
    public static boolean CheckIntInput(String value)
    {
        int num;
        try {num = Integer.parseInt(value);}
        catch (NumberFormatException error) {return false;}
        return num > 0;
    }

    /**
     * Check if value is valid float
     * @param value - value
     * @return true - if valid, false - else
     */
    public static boolean CheckFloatInput(String value)
    {
        float num;
        try {num = Float.parseFloat(value);}
        catch (NumberFormatException error) {return false;}
        return !(num <= 0);
    }


}
