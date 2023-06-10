package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;
import SuppliersModule.Business.SupplierProduct;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreatePeriodicOrderGUI {

    static OrderController orderController;
    static SupplierController supplierController;
    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame)
    {
        orderController = OrderController.getInstance();
        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page1Frame = new JFrame("Create a new periodic order");
        page1Frame.setSize(500,500);
        page1Frame.setLayout(null);

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel welcomeLabel = new JLabel("Welcome to the PeriodicOrder Generator");
        JLabel PermanentSupplyDayLabel = new JLabel("Enter Permanent Supply Day");
        JLabel supplierNumLabel = new JLabel("Enter supplier number");

        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> comboBoxPermanentSupplyDay = new JComboBox<>();
        String[] daysName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        comboBoxPermanentSupplyDay.addItem("");
        for(String day: daysName)
            comboBoxPermanentSupplyDay.addItem(day);

        //************************************************************************

        JComboBox<String> comboBoxSupplierNum = new JComboBox<>();
        List<String> comboBoxSupplierNumItems = new ArrayList<>();
        comboBoxSupplierNumItems.add("");
        Map<String, Supplier> allSuppliers = supplierController.returnAllSuppliers();
        for (Map.Entry<String, Supplier> pair : allSuppliers.entrySet())
            comboBoxSupplierNumItems.add(pair.getKey());

        for (String item : comboBoxSupplierNumItems)
            comboBoxSupplierNum.addItem(item);

        //----------------------------------------- Create JButton ----------------------------------------
        JButton nextButton = new JButton("Next");

        //-------------------------------------- Set bounds ---------------------------------------------

        welcomeLabel.setBounds(100,10,300,20);

        supplierNumLabel.setBounds(10,40,200,20);
        comboBoxSupplierNum.setBounds(250,40,80,20);

        PermanentSupplyDayLabel.setBounds(10,80,200,20);
        comboBoxPermanentSupplyDay.setBounds(250,80,80,20);

        nextButton.setBounds(200,370,100,30);

        //-------------------------------------- Set not visible ---------------------------------------------

        nextButton.setVisible(false);
        PermanentSupplyDayLabel.setVisible(false);
        comboBoxPermanentSupplyDay.setVisible(false);


        //------------------------------------ Add to currFrame -------------------------------------
        page1Frame.add(welcomeLabel);
        page1Frame.add(supplierNumLabel);
        page1Frame.add(comboBoxSupplierNum);
        page1Frame.add(PermanentSupplyDayLabel);
        page1Frame.add(comboBoxPermanentSupplyDay);
        page1Frame.add(nextButton);

        // ------------------------------------- Add action listener to JObjects ------------------------------

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderController.enterSupplier(comboBoxSupplierNum.getSelectedItem().toString());
                orderController.createPeriodicOrder();
                orderController.enterPermanentDay(comboBoxPermanentSupplyDay.getSelectedIndex()-1);
                page1Frame.dispose();
                Page2(comboBoxSupplierNum.getSelectedItem().toString());
            }
        });

        //**********************************************************************
        comboBoxPermanentSupplyDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String day = (String) combo.getSelectedItem();
                nextButton.setVisible(!day.equals(""));
            }
        });

        //****************************************************************

        comboBoxSupplierNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String supplierNum = (String) combo.getSelectedItem();

                if(supplierNum.equals("")) {
                    comboBoxPermanentSupplyDay.setSelectedItem("");
                    PermanentSupplyDayLabel.setVisible(false);
                    comboBoxPermanentSupplyDay.setVisible(false);
                }
                else {
                    comboBoxPermanentSupplyDay.setVisible(true);
                    PermanentSupplyDayLabel.setVisible(true);
                }
            }});

        page1Frame.setVisible(true);
    }

    //------------------------------------- Page 2 -------------------------------------------------------
    public static void Page2(String supplierName)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page2Frame = new JFrame("Create a new periodic order");
        page2Frame.setSize(500, 500);
        page2Frame.setLayout(null);

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel productCatalogLabel = new JLabel("Choose the supplier's product catalog number");
        JLabel quantityLabel = new JLabel("Enter the quantity of products");
        JLabel checkQuantityLabel = CreateSupplierGUI.createCheckLabel("",320,50, 150, 20);

        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField quantityField = new JTextField();

        //----------------------------------------- Create JButton ----------------------------------------
        JButton addButton = new JButton("Add To Order");
        JButton finishButton = new JButton("Finish");

        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> comboBoxCatalogNumbers = new JComboBox<>();
        List <String> comboBoxCatalogNumbersItems = new ArrayList<>();
        comboBoxCatalogNumbersItems.add("");
        Map<String, SupplierProduct> allCatalog = supplierController.returnAllProductOfSupplier(supplierName);
        for (Map.Entry<String, SupplierProduct> pair : allCatalog.entrySet())
            comboBoxCatalogNumbersItems.add(pair.getKey());

        for (String CatalogNumber : comboBoxCatalogNumbersItems)
            comboBoxCatalogNumbers.addItem(CatalogNumber);

        //-------------------------------------- Set bounds ---------------------------------------------
        productCatalogLabel.setBounds(10, 10, 300, 20);
        comboBoxCatalogNumbers.setBounds(350, 10, 80, 20);

        quantityLabel.setBounds(10, 50, 180, 20);
        quantityField.setBounds(220, 50, 80, 20);

        addButton.setBounds(210,410,150,30);;
        finishButton.setBounds(100,410,100,30);

        //-------------------------------------- Set not visible ---------------------------------------------
        addButton.setVisible(false);
        finishButton.setVisible(false);
        quantityLabel.setVisible(false);
        quantityField.setVisible(false);

        //------------------------------------ Add to currFrame -------------------------------------
        page2Frame.add(productCatalogLabel);
        page2Frame.add(comboBoxCatalogNumbers);
        page2Frame.add(quantityLabel);
        page2Frame.add(quantityField);
        page2Frame.add(checkQuantityLabel);
        page2Frame.add(addButton);
        page2Frame.add(finishButton);

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxCatalogNumbers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String catalogNumber = (String) combo.getSelectedItem();

                if(catalogNumber.equals(""))
                {
                    addButton.setVisible(false);
                    quantityLabel.setVisible(false);
                    quantityField.setVisible(false);
                    checkQuantityLabel.setVisible(false);
                }
                else
                {
                    quantityLabel.setVisible(true);
                    quantityField.setVisible(true);
                    addButton.setVisible(true);
                }
            }});

        //*****************************************************************8

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String desiredCatalog = "";
                int desiredQuantity = 0;

                if(!CreateSupplierGUI.CheckIntInput(quantityField.getText())) {
                    checkQuantityLabel.setText("Must be positive");
                    checkQuantityLabel.setVisible(true);
                    isValid = false;
                }
                else
                {
                    desiredQuantity = Integer.parseInt(quantityField.getText());
                    desiredCatalog = comboBoxCatalogNumbers.getSelectedItem().toString();
                    int MaximumQuantity = allCatalog.get(desiredCatalog).getAmount();

                    if(desiredQuantity > MaximumQuantity)
                    {
                        checkQuantityLabel.setText("Can supply maximum " + MaximumQuantity);
                        checkQuantityLabel.setVisible(true);
                        isValid = false;
                    }
                }

                if(isValid) {
                    CreateSupplierGUI.ShowAddSuccess();
                    orderController.addProductToTheList(desiredCatalog);
                    orderController.addQuantityOfTheLastEnteredProduct(desiredQuantity);

                    comboBoxCatalogNumbers.removeItem(desiredCatalog);
                    comboBoxCatalogNumbers.setSelectedItem("");
                    quantityField.setText("");

                    finishButton.setVisible(true);
                }
            }});

        //****************************************************************

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderController.savePeriodicOrder();
                page2Frame.dispose();
                OldFrame.setVisible(true);
                CreateSupplierGUI.ShowProcessSuccessfully();
            }});

        page2Frame.setVisible(true);
    }
}
