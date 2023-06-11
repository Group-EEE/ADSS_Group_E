package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Generator.SupplierGenerator;
import SuppliersModule.Business.PaymentTerm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateSupplierGUI {
    static SupplierController supplierController;
    static SupplierGenerator supplierGenerator;
    static JFrame OldFrame;
    static String supplierNum;

    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();
        supplierGenerator = SupplierGenerator.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page1Frame = HelperFunctionGUI.createNewFrame("Create new Supplier");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel nameLabel = new JLabel("Name");
        JLabel checkNameLabel = HelperFunctionGUI.createCheckLabel("Empty", 250,10,150,20);

        JLabel supplierNumLabel = new JLabel("SupplierNum");
        JLabel checkSupplierNumLabel = HelperFunctionGUI.createCheckLabel("SupplierNum exist", 250,50,150,20);

        JLabel bankAccountLabel = new JLabel("bankAccount");
        JLabel checkBankAccountLabel = HelperFunctionGUI.createCheckLabel("Empty", 250,80,150,20);

        JLabel PaymentTermLabel = new JLabel("PaymentTerm");
        JLabel checkPaymentTermLabel = HelperFunctionGUI.createCheckLabel("Empty", 250,110,150,20);

        JLabel contactLabel = new JLabel("Contacts");
        JLabel checkContactsLabel = HelperFunctionGUI.createCheckLabel("At least one", 370,157,150,20);

        JLabel categoryLabel = new JLabel("Categories");
        JLabel checkCategoriesLabel = HelperFunctionGUI.createCheckLabel("At least one", 270,257,150,20);

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField nameField = new JTextField();
        JTextField supplierNumField = new JTextField();
        JTextField bankAccountField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxPaymentTerm = new JComboBox<>(new String[]{"", "Net", "Net 30 days", "Net 60 days"});

        //--------------------------------------- Create JTable ---------------------------------------

        String[][] dataContact = {{"",""},{"",""},{"",""},{"",""}};
        String[] columnsContact = {"Name", "PhoneNumber"};
        JTable jTableContacts= new JTable(dataContact, columnsContact);
        JScrollPane jScrollContacts = new JScrollPane(jTableContacts);

        //*************************************************************

        String[][] dataCategory = {{""},{""},{""}, {""}};
        String[] columnsCategory = {"Category"};
        JTable jTableCategories= new JTable(dataCategory, columnsCategory);
        JScrollPane jScrollCategories = new JScrollPane(jTableCategories);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton nextButton = new JButton("Next");

        //-------------------------------------- Set bounds ---------------------------------------------
        nameLabel.setBounds(10, 10, 100,20);
        nameField.setBounds(150, 10, 80,20);

        supplierNumLabel.setBounds(10, 50, 100,20);
        supplierNumField.setBounds(150, 50, 80,20);

        bankAccountLabel.setBounds(10, 80, 100,20);
        bankAccountField.setBounds(150, 80, 80,20);

        PaymentTermLabel.setBounds(10, 110, 100,20);
        comboBoxPaymentTerm.setBounds(150,110,80,20);

        contactLabel.setBounds(10, 140, 100,20);
        jTableContacts.setBounds(150, 140, 200, 87);
        jScrollContacts.setBounds(150, 140, 200, 87);

        categoryLabel.setBounds(10, 240, 100,20);
        jTableCategories.setBounds(150, 240, 200, 87);
        jScrollCategories.setBounds(150, 240, 100, 87);

        nextButton.setBounds(200,370,100,30);
        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {nameLabel,nameField, checkNameLabel,
                supplierNumLabel, supplierNumField, checkSupplierNumLabel,
                bankAccountLabel, bankAccountField, checkBankAccountLabel,
                PaymentTermLabel, comboBoxPaymentTerm, checkPaymentTermLabel,
                contactLabel, jScrollContacts, checkContactsLabel,
                categoryLabel, jScrollCategories, checkCategoriesLabel, nextButton};

        HelperFunctionGUI.addComponentsToFrame(page1Frame,components);


        // ------------------------------------- Add action listener to JObjects ------------------------------

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                if(nameField.getText().equals("")) {checkNameLabel.setVisible(true); isValid = false;}
                else checkNameLabel.setVisible(false);

                if(supplierController.checkIfSupplierExist(supplierNumField.getText()) || supplierNumField.getText().equals("")) {checkSupplierNumLabel.setVisible(true); isValid = false;}
                else checkSupplierNumLabel.setVisible(false);

                if(bankAccountField.getText().equals("")) {checkBankAccountLabel.setVisible(true); isValid = false;}
                else checkBankAccountLabel.setVisible(false);

                if(comboBoxPaymentTerm.getSelectedItem().equals("")) {checkPaymentTermLabel.setVisible(true); isValid = false;}
                else checkPaymentTermLabel.setVisible(false);

                if(jTableContacts.getValueAt(0, 0).equals("") || jTableContacts.getValueAt(0, 1).equals("")) {checkContactsLabel.setVisible(true); isValid = false;}
                else checkContactsLabel.setVisible(false);

                if(jTableCategories.getValueAt(0, 0).toString().equals("")) {checkCategoriesLabel.setVisible(true); isValid = false;}
                else checkCategoriesLabel.setVisible(false);

                if(isValid) {
                    supplierGenerator.reset();
                    List<String> categories = new ArrayList<>();

                    String contactName = ""; String phoneNumber = ""; String categoryName = "";

                    for (int i = 0; i < jTableContacts.getRowCount(); i++)
                    {
                        contactName = jTableContacts.getValueAt(i, 0).toString();
                        phoneNumber = jTableContacts.getValueAt(i, 1).toString();
                        if(contactName.equals("") || phoneNumber.equals(""))
                            continue;
                        supplierGenerator.addContact(contactName, phoneNumber);
                    }

                    for (int i = 0; i < jTableCategories.getRowCount(); i++)
                    {
                        categoryName = jTableCategories.getValueAt(i, 0).toString();
                        if(categoryName.equals(""))
                            continue;
                        categories.add(categoryName);
                    }

                    supplierNum = supplierNumField.getText();

                    page1Frame.dispose();
                    Page2(nameField.getText(), bankAccountField.getText(), PaymentTerm.values()[comboBoxPaymentTerm.getSelectedIndex()-1], categories);
                }
            }});

        page1Frame.setVisible(true);
    }

    //------------------------------------- Page 2 -------------------------------------------------------
    public static void Page2(String supplierName, String bankAccount, PaymentTerm paymentTerm, List<String> categories)
    {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page2Frame = HelperFunctionGUI.createNewFrame("Create new Supplier");

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel daysToSupplyLabel = new JLabel("How many days to deliver the products?");
        JLabel checkDaysToSupplyLabel = HelperFunctionGUI.createCheckLabel("Empty", 380, 360, 50,20);

        JLabel deliveryDaysLabel = new JLabel("Choose days");
        JLabel checkDeliveryDaysLabel = HelperFunctionGUI.createCheckLabel("At least one", 350, 100, 100,20);

        JLabel hasPermanentDaysLabel = new JLabel("Does he have permanent days that he comes?");

        JLabel bringProductLabel = new JLabel("Does the supplier transport his products himself?");

        //----------------------------------------- Create JCheckBox ----------------------------------------

        JCheckBox[] checkBoxesDeliveryDays = new JCheckBox[7];
        String[] daysName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for(int i=0 ; i<7 ; i++)
        {
            checkBoxesDeliveryDays[i] = new JCheckBox(daysName[i]);
            checkBoxesDeliveryDays[i].setBounds(200, 130 + i*30, 100, 20);
            checkBoxesDeliveryDays[i].setVisible(false);
            page2Frame.add(checkBoxesDeliveryDays[i]);
        }

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxBring = new JComboBox<>(new String[]{"", "Yes", "No"});
        JComboBox<String> hasPermanentDaysComboBox = new JComboBox<>(new String[]{"", "Yes", "No"});

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField daysToSupplyField = new JTextField();

        //------------------------------------ Create JButton -------------------------------------

        JButton nextButton = new JButton("Next");

        //-------------------------------------- Set bounds ---------------------------------------------

        bringProductLabel.setBounds(10, 10, 300,20);
        comboBoxBring.setBounds(350,10,50,20);

        hasPermanentDaysLabel.setBounds(10, 50, 300,20);
        hasPermanentDaysComboBox.setBounds(350,50,50,20);

        deliveryDaysLabel.setBounds(100, 100, 200,20);

        daysToSupplyLabel.setBounds(10, 360, 200, 20);
        daysToSupplyField.setBounds(280, 360, 80,20);
        checkDaysToSupplyLabel.setBounds(390, 360, 80, 20);

        nextButton.setBounds(200,410,100,30);

        //-------------------------------------- Set not visible ---------------------------------------------

        JComponent [] hiddenComponents = {hasPermanentDaysLabel, hasPermanentDaysComboBox,
                deliveryDaysLabel, daysToSupplyLabel, daysToSupplyField, nextButton};

        HelperFunctionGUI.hideComponents(hiddenComponents);

        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {bringProductLabel, comboBoxBring,
                hasPermanentDaysLabel, hasPermanentDaysComboBox,
                deliveryDaysLabel, checkDeliveryDaysLabel,
                daysToSupplyLabel, daysToSupplyField, checkDaysToSupplyLabel, nextButton};

        HelperFunctionGUI.addComponentsToFrame(page2Frame, components);

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxBring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String yesORno = (String) combo.getSelectedItem();

                if(yesORno.equals("Yes")) {
                    hasPermanentDaysLabel.setVisible(true);
                    hasPermanentDaysComboBox.setVisible(true);
                    nextButton.setVisible(false);
                }

                else if(yesORno.equals("No")) {
                    hasPermanentDaysLabel.setVisible(false);
                    hasPermanentDaysComboBox.setVisible(false);
                    hasPermanentDaysComboBox.setSelectedItem("");
                    nextButton.setVisible(true);
                }

                else {
                    hasPermanentDaysLabel.setVisible(false);
                    hasPermanentDaysComboBox.setVisible(false);
                    nextButton.setVisible(false);
                }
            }});

        //********************************************************************

        hasPermanentDaysComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String yesORno = (String) combo.getSelectedItem();

                if(yesORno.equals("Yes")) {
                    deliveryDaysLabel.setVisible(true);
                    nextButton.setVisible(true);
                    for(int i=0 ; i<7 ; i++)
                        checkBoxesDeliveryDays[i].setVisible(true);

                    daysToSupplyLabel.setVisible(false);
                    daysToSupplyField.setVisible(false);
                    checkDaysToSupplyLabel.setVisible(false);
                }

                else if(yesORno.equals("No")) {
                    deliveryDaysLabel.setVisible(false);
                    checkDeliveryDaysLabel.setVisible(false);
                    for(int i=0 ; i<7 ; i++)
                        checkBoxesDeliveryDays[i].setVisible(false);

                    daysToSupplyLabel.setVisible(true);
                    daysToSupplyField.setVisible(true);
                    nextButton.setVisible(true);
                }

                else {
                    checkDaysToSupplyLabel.setVisible(false);
                    daysToSupplyField.setVisible(false);
                    daysToSupplyLabel.setVisible(false);
                    nextButton.setVisible(false);
                    checkDeliveryDaysLabel.setVisible(false);
                    deliveryDaysLabel.setVisible(false);

                    for (int i = 0; i < 7; i++)
                        checkBoxesDeliveryDays[i].setVisible(false);
                }
            }});
        //*************************************************************************

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean[] deliveryDays = new boolean[7];
                int daysToSupply = -1;
                boolean isSupplierBringProduct = false;
                boolean hasPermanentDays = false;
                int countFalse = 0;

                if (comboBoxBring.getSelectedItem().toString().equals("Yes")) {
                    isSupplierBringProduct = true;

                    if (hasPermanentDaysComboBox.getSelectedItem().toString().equals("Yes")) {
                        hasPermanentDays = true;

                        for (int i = 0; i < 7; i++) {
                            deliveryDays[i] = checkBoxesDeliveryDays[i].isSelected();
                            if (!deliveryDays[i])
                                countFalse++;
                        }

                        if (countFalse == 7) {checkDeliveryDaysLabel.setVisible(true);return;}
                    }

                    if (hasPermanentDaysComboBox.getSelectedItem().toString().equals("No")) {
                        int num;
                        try {num = Integer.parseInt(daysToSupplyField.getText());}
                        catch (NumberFormatException error) {checkDaysToSupplyLabel.setVisible(true); return;}
                        if (num <= 0) {checkDaysToSupplyLabel.setVisible(true); return;}
                    }
                }

                supplierGenerator.CreateSupplierAndAgreement(supplierName, supplierNum, bankAccount, paymentTerm, categories, hasPermanentDays, isSupplierBringProduct, deliveryDays, daysToSupply);
                page2Frame.dispose();
                Page3();
            }});

        page2Frame.setVisible(true);
    }

    public static void Page3() {

        List<String> productsAdd = new ArrayList<>();

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page3Frame = HelperFunctionGUI.createNewFrame("Create new Supplier");

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel productNameLabel = new JLabel("Enter the product name");
        JLabel checkProdNameLabel = HelperFunctionGUI.createCheckLabel("Empty", 370, 10, 100, 20);

        JLabel manufacturerNameLabel = new JLabel("Enter the manufacturer name");
        JLabel checkManuNameLabel = HelperFunctionGUI.createCheckLabel("Empty", 370, 40, 100, 20);

        JLabel barcodeLabel = new JLabel("Enter barcode: (If unknown enter: 99)");
        JLabel checkBarcodeLabel = HelperFunctionGUI.createCheckLabel("Must be positive", 370, 70, 100, 20);

        JLabel supplierCatalogLabel = new JLabel("Enter supplier catalog");
        JLabel checkCatalogLabel = HelperFunctionGUI.createCheckLabel("Empty or Exist", 370, 100, 100, 20);

        JLabel priceLabel = new JLabel("Enter price per unit");
        JLabel checkPriceLabel = HelperFunctionGUI.createCheckLabel("Must be positive", 370, 130, 100, 20);

        JLabel quantityLabel = new JLabel("Enter the quantity of products you can supply");
        JLabel checkQuantityLabel = HelperFunctionGUI.createCheckLabel("Must be positive", 370, 160, 100, 20);


        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField productNameField = new JTextField();
        JTextField manufacturerNameField = new JTextField();
        JTextField barcodeField = new JTextField();
        JTextField supplierCatalogField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        //------------------------------------ Create nextButton -------------------------------------

        JButton addProductButton = new JButton("Add Product");
        JButton nextButton = new JButton("Next");

        //-------------------------------------- Set bounds ---------------------------------------------

        productNameLabel.setBounds(10, 10, 290, 20);
        productNameField.setBounds(300, 10, 50, 20);

        manufacturerNameLabel.setBounds(10, 40, 290, 20);
        manufacturerNameField.setBounds(300, 40, 50, 20);

        barcodeLabel.setBounds(10, 70, 290, 20);
        barcodeField.setBounds(300, 70, 50, 20);

        supplierCatalogLabel.setBounds(10, 100, 290, 20);
        supplierCatalogField.setBounds(300, 100, 50, 20);

        priceLabel.setBounds(10, 130, 290, 20);
        priceField.setBounds(300, 130, 50, 20);

        quantityLabel.setBounds(10, 160, 290, 20);
        quantityField.setBounds(300, 160, 50, 20);

        addProductButton.setBounds(210,410,150,30);
        nextButton.setBounds(100,410,100,30);

        //-------------------------------------- Set not visible ---------------------------------------------

        nextButton.setVisible(false);

        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {productNameLabel, productNameField, checkProdNameLabel,
                manufacturerNameLabel, manufacturerNameField, checkManuNameLabel,
                barcodeLabel, barcodeField, checkBarcodeLabel,
                priceLabel, priceField, checkPriceLabel,
                quantityLabel, quantityField, checkQuantityLabel, nextButton, addProductButton};

        HelperFunctionGUI.addComponentsToFrame(page3Frame ,components);


        // ------------------------------------- Add action listener to JObjects ------------------------------

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page3Frame.dispose();
                Page4(productsAdd);
            }
        });

        //*****************************************************************************
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(productNameField.getText().equals("")) {checkProdNameLabel.setVisible(true); isValid = false;}
                else checkProdNameLabel.setVisible(false);

                if(manufacturerNameField.getText().equals("")) {checkManuNameLabel.setVisible(true); isValid = false;}
                else checkManuNameLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckIntInput(barcodeField.getText())) {checkBarcodeLabel.setVisible(true); isValid = false;}
                else checkBarcodeLabel.setVisible(false);

                if(supplierCatalogField.getText().equals("") || supplierController.checkIfSupplierSupplyProduct(supplierCatalogField.getText(), supplierNum)) {checkCatalogLabel.setVisible(true);isValid = false;}
                else checkCatalogLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckFloatInput(priceField.getText())) {checkPriceLabel.setVisible(true); isValid = false;}
                else checkPriceLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckIntInput(quantityField.getText())) {checkQuantityLabel.setVisible(true); isValid = false;}
                else checkQuantityLabel.setVisible(false);

                if(isValid) {
                    supplierController.addSupplierProduct(productNameField.getText(), manufacturerNameField.getText(), Integer.parseInt(barcodeField.getText()), supplierNum
                            , Float.parseFloat(priceField.getText()), supplierCatalogField.getText(), Integer.parseInt(quantityField.getText()));

                    productsAdd.add(supplierCatalogField.getText());

                    HelperFunctionGUI.ShowAddSuccess();

                    nextButton.setVisible(true);

                    productNameField.setText("");
                    manufacturerNameField.setText("");
                    barcodeField.setText("");
                    supplierCatalogField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                }
        }});

        page3Frame.setVisible(true);
    }

    public static void Page4(List<String> productsAdd) {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page4Frame = HelperFunctionGUI.createNewFrame("Create new Supplier");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel discountProductLabel = new JLabel("Choose product that supplier provide any discounts?");

        JLabel minimumQuantityLabel = new JLabel("Minimum quantity for discount?");
        JLabel checkMinimumQuantityLabel = HelperFunctionGUI.createCheckLabel("Not Valid or Exist", 300, 100, 100, 20);

        JLabel percentLabel = new JLabel("How many percent off?");
        JLabel checkPercentLabel = HelperFunctionGUI.createCheckLabel("Not Valid", 300, 140, 80, 20);


        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField minimumQuantityField = new JTextField();
        JTextField PercentField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxProductAdd = new JComboBox<>();
        comboBoxProductAdd.addItem("");
        for (int i = 0; i < productsAdd.size(); i++)
            comboBoxProductAdd.addItem(productsAdd.get(i));

        //----------------------------------------- Create JButton ----------------------------------------

        JButton addProductButton = new JButton("Add Product");
        JButton nextButton = new JButton("Next");


        //-------------------------------------- Set bounds ---------------------------------------------

        discountProductLabel.setBounds(50, 10, 350, 20);
        comboBoxProductAdd.setBounds(225, 40, 50, 20);


        minimumQuantityLabel.setBounds(10, 100, 200, 20);
        minimumQuantityField.setBounds(240, 100, 50, 20);

        percentLabel.setBounds(10, 140, 200, 20);
        PercentField.setBounds(240, 140, 50, 20);

        addProductButton.setBounds(210,410,150,30);
        nextButton.setBounds(100,410,100,30);

        //-------------------------------------- Set not visible ---------------------------------------------
        minimumQuantityLabel.setVisible(false);
        minimumQuantityField.setVisible(false);

        percentLabel.setVisible(false);
        PercentField.setVisible(false);

        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {discountProductLabel, comboBoxProductAdd,
                minimumQuantityLabel, minimumQuantityField, checkMinimumQuantityLabel,
                percentLabel, PercentField, checkPercentLabel,
                addProductButton, nextButton};

        HelperFunctionGUI.addComponentsToFrame(page4Frame, components);

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxProductAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String choose = (String) combo.getSelectedItem();

                if(choose.equals("")) {
                    percentLabel.setVisible(false);
                    PercentField.setVisible(false);
                    checkPercentLabel.setVisible(false);
                    minimumQuantityLabel.setVisible(false);
                    minimumQuantityField.setVisible(false);
                    checkMinimumQuantityLabel.setVisible(false);
                }

                else {
                    percentLabel.setVisible(true);
                    PercentField.setVisible(true);
                    minimumQuantityLabel.setVisible(true);
                    minimumQuantityField.setVisible(true);
                    checkMinimumQuantityLabel.setVisible(false);
                    checkPercentLabel.setVisible(false);
                }
            }});

        //********************************************************************8

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(!HelperFunctionGUI.CheckIntInput(minimumQuantityField.getText())) { checkMinimumQuantityLabel.setVisible(true); isValid = false;}
                else checkMinimumQuantityLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckFloatInput(PercentField.getText())) { checkPercentLabel.setVisible(true); isValid = false;}
                else checkPercentLabel.setVisible(false);

                if(isValid) {
                    if (!supplierController.addSupplierProductDiscount(comboBoxProductAdd.getSelectedItem().toString(), Float.parseFloat(PercentField.getText()), Integer.parseInt(minimumQuantityField.getText()), supplierNum))
                        checkMinimumQuantityLabel.setVisible(true);

                    else {
                        HelperFunctionGUI.ShowAddSuccess();
                        PercentField.setText("");
                        minimumQuantityField.setText("");
                    }
                }
            }});

        //****************************************************************8

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page4Frame.dispose();
                Page5();
            }});

        page4Frame.setVisible(true);
    }

    //----------------------------------------------Page 5---------------------------------------------
    public static void Page5() {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page5Frame = HelperFunctionGUI.createNewFrame("Create new Supplier");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel discountOrderLabel = new JLabel("Do the supplier supply any discounts for order?");

        JLabel PorQLabel = new JLabel("Do the discount is for minimum price or for minimum quantity? (p/q)");

        JLabel minimumLabel = new JLabel("Minimum quantity/price for discount?");
        JLabel checkMinimumLabel = HelperFunctionGUI.createCheckLabel("Not Valid or Exist", 350, 100, 100, 20);

        JLabel PercentLabel = new JLabel("How many percent off?");
        JLabel checkPercentLabel = HelperFunctionGUI.createCheckLabel("Not Valid", 350, 100, 100, 20);


        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField minimumField = new JTextField();
        JTextField PercentField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxDiscountOrder = new JComboBox<>(new String[]{"", "Yes"});
        JComboBox<String> comboBoxPorQ = new JComboBox<>(new String[]{"", "p", "q"});

        //----------------------------------------- Create JButton ----------------------------------------

        JButton addDiscountButton = new JButton("Add Discount");
        JButton finishButton = new JButton("Finish");

        //-------------------------------------- Set bounds ---------------------------------------------

        discountOrderLabel.setBounds(50, 10, 350, 20);
        comboBoxDiscountOrder.setBounds(225, 40, 50, 20);

        PorQLabel.setBounds(10, 70, 400, 20);
        comboBoxPorQ.setBounds(430, 70, 50, 20);

        minimumLabel.setBounds(10, 130, 250, 20);
        minimumField.setBounds(290, 130, 50, 20);

        PercentLabel.setBounds(10, 100, 250, 20);
        PercentField.setBounds(290, 100, 50, 20);

        addDiscountButton.setBounds(210,410,150,30);
        finishButton.setBounds(100,410,100,30);


        //-------------------------------------- Set not visible ---------------------------------------------

        PorQLabel.setVisible(false);
        comboBoxPorQ.setVisible(false);

        minimumLabel.setVisible(false);
        minimumField.setVisible(false);

        PercentLabel.setVisible(false);
        PercentField.setVisible(false);


        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {discountOrderLabel, comboBoxDiscountOrder,
                PorQLabel, comboBoxPorQ,
                minimumLabel, minimumField, checkMinimumLabel,
                PercentLabel, PercentField, checkPercentLabel,
                addDiscountButton, finishButton};

        HelperFunctionGUI.addComponentsToFrame(page5Frame, components);

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxDiscountOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String choose = (String) combo.getSelectedItem();

                if(choose.equals("")) {
                    comboBoxPorQ.setSelectedItem("");
                    comboBoxPorQ.setVisible(false);
                    PorQLabel.setVisible(false);
                    addDiscountButton.setVisible(true);
                }

                else {
                    comboBoxPorQ.setVisible(true);
                    PorQLabel.setVisible(true);
                    addDiscountButton.setVisible(false);
                }
            }});

        //*********************************************************************

        comboBoxPorQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String choose = (String) combo.getSelectedItem();

                if(choose.equals("")) {
                    PercentLabel.setVisible(false);
                    PercentField.setVisible(false);
                    checkPercentLabel.setVisible(false);
                    minimumLabel.setVisible(false);
                    minimumField.setVisible(false);
                    checkMinimumLabel.setVisible(false);
                    addDiscountButton.setVisible(false);
                }

                else {
                    PercentLabel.setVisible(true);
                    PercentField.setVisible(true);
                    minimumLabel.setVisible(true);
                    minimumField.setVisible(true);
                    addDiscountButton.setVisible(true);

                    checkMinimumLabel.setVisible(false);
                    checkPercentLabel.setVisible(false);
                }
            }
        });

        //*****************************************************************************

        addDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(!HelperFunctionGUI.CheckIntInput(minimumField.getText()) || supplierController.CheckIfExistOrderDiscount(supplierNum, comboBoxPorQ.getSelectedItem().toString(), Integer.parseInt(minimumField.getText()))) { checkMinimumLabel.setVisible(true); isValid = false;}
                else checkMinimumLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckFloatInput(PercentField.getText())) { checkPercentLabel.setVisible(true); isValid = false;}
                else checkPercentLabel.setVisible(false);

                if(isValid) {
                    supplierController.addOrderDiscount(supplierNum, comboBoxPorQ.getSelectedItem().toString(), Integer.parseInt(minimumField.getText()), Float.parseFloat(PercentField.getText()));
                    HelperFunctionGUI.ShowAddSuccess();
                    comboBoxDiscountOrder.setSelectedItem("");
                }
            }});

        //******************************************************************************

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page5Frame.dispose();
                HelperFunctionGUI.ShowProcessSuccessfully();
                OldFrame.setVisible(true);
            }});

        page5Frame.setVisible(true);
    }

}

