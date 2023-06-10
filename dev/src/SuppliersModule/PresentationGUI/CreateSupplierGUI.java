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

    static String supplierName;
    static String supplierNum;
    static String bankAccount;
    static PaymentTerm paymentTerm;
    static List<String> categories;

    static List<String> productsAdd;


    public static void powerOn(SupplierController suppController, JFrame oldFrame)
    {
        supplierController = suppController;
        supplierGenerator = SupplierGenerator.getInstance();
        OldFrame = oldFrame;
        categories = new ArrayList<>();

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page1Frame = new JFrame("Create new Supplier");
        page1Frame.setSize(500, 500);
        page1Frame.setLayout(null);

        //---------------------------------------- Name  -------------------------------------------------
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 10, 100,20);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 10, 80,20);

        JLabel checkNameLabel = createCheckLabel("Empty", 250,10,150,20);

        page1Frame.add(nameLabel);
        page1Frame.add(nameField);
        page1Frame.add(checkNameLabel);

        //----------------------------------- SupplierNum  ------------------------------------------

        JLabel supplierNumLabel = new JLabel("SupplierNum");
        supplierNumLabel.setBounds(10, 50, 100,20);

        JTextField supplierNumField = new JTextField();
        supplierNumField.setBounds(150, 50, 80,20);

        JLabel checkSupplierNumLabel = createCheckLabel("SupplierNum exist", 250,50,150,20);

        page1Frame.add(supplierNumLabel);
        page1Frame.add(supplierNumField);
        page1Frame.add(checkSupplierNumLabel);

        //------------------------------------ bankAccount  ----------------------------------------

        JLabel bankAccountLabel = new JLabel("bankAccount");
        bankAccountLabel.setBounds(10, 80, 100,20);

        JTextField bankAccountField = new JTextField();
        bankAccountField.setBounds(150, 80, 80,20);

        JLabel checkBankAccountLabel = createCheckLabel("Empty", 250,80,150,20);

        page1Frame.add(bankAccountLabel);
        page1Frame.add(bankAccountField);
        page1Frame.add(checkBankAccountLabel);

        //------------------------------------ PaymentTerm  ----------------------------------------

        JLabel PaymentTermLabel = new JLabel("PaymentTerm");
        PaymentTermLabel.setBounds(10, 110, 100,20);

        JComboBox<String> comboBoxPaymentTerm = new JComboBox<>();
        comboBoxPaymentTerm.setBounds(150,110,80,20);
        comboBoxPaymentTerm.addItem("");
        comboBoxPaymentTerm.addItem("Net");
        comboBoxPaymentTerm.addItem("Net 30 days");
        comboBoxPaymentTerm.addItem("Net 60 days");

        JLabel checkPaymentTermLabel = createCheckLabel("Empty", 250,110,150,20);

        page1Frame.add(PaymentTermLabel);
        page1Frame.add(comboBoxPaymentTerm);
        page1Frame.add(checkPaymentTermLabel);

        //-------------------------------- Contacts --------------------------------------

        JLabel ContactLabel = new JLabel("Contacts");
        ContactLabel.setBounds(10, 140, 100,20);

        String[][] dataContact = {{"",""},{"",""},{"",""},{"",""}};
        String[] columnsContact = {"Name", "PhoneNumber"};

        JTable jTableContacts= new JTable(dataContact, columnsContact);
        jTableContacts.setBounds(150, 140, 200, 87);

        JScrollPane jScrollPane1 = new JScrollPane(jTableContacts);
        jScrollPane1.setBounds(150, 140, 200, 87);

        JLabel checkContactsLabel = createCheckLabel("At least one", 370,157,150,20);

        page1Frame.add(ContactLabel);
        page1Frame.add(jScrollPane1);
        page1Frame.add(checkContactsLabel);

        //-------------------------------- Contact table and label --------------------------------------

        JLabel CategoryLabel = new JLabel("Categories");
        CategoryLabel.setBounds(10, 240, 100,20);

        String[][] dataCategory = {{""},{""},{""}, {""}};
        String[] columnsCategory = {"Category"};

        JTable jTableCategories= new JTable(dataCategory, columnsCategory);
        jTableCategories.setBounds(150, 240, 200, 87);

        JScrollPane jScrollPane2 = new JScrollPane(jTableCategories);
        jScrollPane2.setBounds(150, 240, 100, 87);

        JLabel checkCategoriesLabel = createCheckLabel("At least one", 270,257,150,20);

        page1Frame.add(CategoryLabel);
        page1Frame.add(jScrollPane2);
        page1Frame.add(checkCategoriesLabel);

        //------------------------------------ Create nextButton -------------------------------------

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(200,370,100,30);
        page1Frame.add(nextButton);
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

                    supplierName = nameField.getText();
                    supplierNum = supplierNumField.getText();
                    bankAccount = bankAccountField.getText();
                    paymentTerm = PaymentTerm.values()[comboBoxPaymentTerm.getSelectedIndex()-1];

                    page1Frame.dispose();
                    Page2();
                }
            }});

        page1Frame.setVisible(true);
    }

    //------------------------------------- Page 2 -------------------------------------------------------
    public static void Page2()
    {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page2Frame = new JFrame("Create new Supplier");
        page2Frame.setSize(500, 500);
        page2Frame.setLayout(null);

        //------------------------------------ Create nextButton -------------------------------------

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(200,410,100,30);
        page2Frame.add(nextButton);
        nextButton.setVisible(false);

        //------------------------------------ daysToSupply label and field ----------------------------------------
        JLabel daysToSupplyLabel = new JLabel("How many days to deliver the products?");
        daysToSupplyLabel.setBounds(10, 360, 250,20);
        daysToSupplyLabel.setVisible(false);

        JTextField daysToSupplyField = new JTextField();
        daysToSupplyField.setBounds(280, 360, 80,20);
        daysToSupplyField.setVisible(false);

        JLabel checkDaysToSupplyLabel = createCheckLabel("Empty", 380, 360, 50,20);

        page2Frame.add(daysToSupplyLabel);
        page2Frame.add(daysToSupplyField);
        page2Frame.add(checkDaysToSupplyLabel);

        //------------------------------------ deliveryDays label and checkBox ----------------------------------------

        JLabel deliveryDaysLabel = new JLabel("Choose days");
        deliveryDaysLabel.setBounds(100, 100, 200,20);
        deliveryDaysLabel.setVisible(false);

        JLabel checkDeliveryDaysLabel = createCheckLabel("At least one", 350, 100, 100,20);

        JCheckBox[] checkBoxesDeliveryDays = new JCheckBox[7];
        String[] daysName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for(int i=0 ; i<7 ; i++)
        {
            checkBoxesDeliveryDays[i] = new JCheckBox(daysName[i]);
            checkBoxesDeliveryDays[i].setBounds(200, 130 + i*30, 100, 20);
            checkBoxesDeliveryDays[i].setVisible(false);
            page2Frame.add(checkBoxesDeliveryDays[i]);
        }
        page2Frame.add(deliveryDaysLabel);
        page2Frame.add(checkDeliveryDaysLabel);

        //------------------------------------ hasPermanentDays label and comboBox ----------------------------------------

        JLabel hasPermanentDaysLabel = new JLabel("Does he have permanent days that he comes?");
        hasPermanentDaysLabel.setBounds(10, 50, 300,20);
        hasPermanentDaysLabel.setVisible(false);

        JComboBox<String> hasPermanentDaysComboBox = new JComboBox<>();

        hasPermanentDaysComboBox.setBounds(350,50,50,20);
        hasPermanentDaysComboBox.addItem("");
        hasPermanentDaysComboBox.addItem("Yes");
        hasPermanentDaysComboBox.addItem("No");
        hasPermanentDaysComboBox.setVisible(false);

        page2Frame.add(hasPermanentDaysLabel);
        page2Frame.add(hasPermanentDaysComboBox);

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

        //------------------------------------ isSupplierBringProduct label and comboBox ----------------------------------------

        JLabel BringProductLabel = new JLabel("Does the supplier transport his products himself?");
        BringProductLabel.setBounds(10, 10, 300,20);

        JComboBox<String> comboBoxBring = new JComboBox<>();
        comboBoxBring.setBounds(350,10,50,20);
        comboBoxBring.addItem("");
        comboBoxBring.addItem("Yes");
        comboBoxBring.addItem("No");

        page2Frame.add(BringProductLabel);
        page2Frame.add(comboBoxBring);

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

                else if(yesORno.equals("No"))
                {
                    hasPermanentDaysLabel.setVisible(false);
                    hasPermanentDaysComboBox.setVisible(false);
                    hasPermanentDaysComboBox.setSelectedItem("");
                    nextButton.setVisible(true);
                }

                else
                {
                    hasPermanentDaysLabel.setVisible(false);
                    hasPermanentDaysComboBox.setVisible(false);
                    nextButton.setVisible(false);
                }
            }});

        //-------------------------------------------------------------------------

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

        productsAdd = new ArrayList<>();

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page3Frame = new JFrame("Create new Supplier");
        page3Frame.setSize(500, 500);
        page3Frame.setLayout(null);

        //------------------------------------- product name ---------------------------------------------

        JLabel productNameLabel = new JLabel("Enter the product name");
        productNameLabel.setBounds(10, 10, 290, 20);

        JTextField productNameField = new JTextField();
        productNameField.setBounds(300, 10, 50, 20);

        JLabel checkProdNameLabel = createCheckLabel("Empty", 370, 10, 100, 20);

        page3Frame.add(productNameLabel);
        page3Frame.add(productNameField);
        page3Frame.add(checkProdNameLabel);

        //------------------------------------ Manufacturer name -----------------------------------------

        JLabel manufacturerNameLabel = new JLabel("Enter the manufacturer name");
        manufacturerNameLabel.setBounds(10, 40, 290, 20);

        JTextField manufacturerNameField = new JTextField();
        manufacturerNameField.setBounds(300, 40, 50, 20);

        JLabel checkManuNameLabel = createCheckLabel("Empty", 370, 40, 100, 20);

        page3Frame.add(manufacturerNameLabel);
        page3Frame.add(manufacturerNameField);
        page3Frame.add(checkManuNameLabel);

        //------------------------------------ Barcode -----------------------------------------

        JLabel barcodeLabel = new JLabel("Enter barcode: (If unknown enter: 99)");
        barcodeLabel.setBounds(10, 70, 290, 20);

        JTextField barcodeField = new JTextField();
        barcodeField.setBounds(300, 70, 50, 20);

        JLabel checkBarcodeLabel = createCheckLabel("Must be positive", 370, 70, 100, 20);

        page3Frame.add(barcodeLabel);
        page3Frame.add(barcodeField);
        page3Frame.add(checkBarcodeLabel);

        //------------------------------------ Supplier catalog -----------------------------------------

        JLabel supplierCatalogLabel = new JLabel("Enter supplier catalog");
        supplierCatalogLabel.setBounds(10, 100, 290, 20);

        JTextField supplierCatalogField = new JTextField();
        supplierCatalogField.setBounds(300, 100, 50, 20);

        JLabel checkCatalogLabel = createCheckLabel("Empty or Exist", 370, 100, 100, 20);

        page3Frame.add(supplierCatalogLabel);
        page3Frame.add(supplierCatalogField);
        page3Frame.add(checkCatalogLabel);

        //------------------------------------ Price -----------------------------------------

        JLabel priceLabel = new JLabel("Enter price per unit");
        priceLabel.setBounds(10, 130, 290, 20);

        JTextField priceField = new JTextField();
        priceField.setBounds(300, 130, 50, 20);

        JLabel checkPriceLabel = createCheckLabel("Must be positive", 370, 130, 100, 20);

        page3Frame.add(priceLabel);
        page3Frame.add(priceField);
        page3Frame.add(checkPriceLabel);

        //------------------------------------ Quantity -----------------------------------------

        JLabel quantityLabel = new JLabel("Enter the quantity of products you can supply");
        quantityLabel.setBounds(10, 160, 290, 20);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(300, 160, 50, 20);

        JLabel checkQuantityLabel =createCheckLabel("Must be positive", 370, 160, 100, 20);

        page3Frame.add(quantityLabel);
        page3Frame.add(quantityField);
        page3Frame.add(checkQuantityLabel);

        //------------------------------------ Create nextButton -------------------------------------

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(100,410,100,30);
        page3Frame.add(nextButton);
        nextButton.setVisible(false);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page3Frame.dispose();
                Page4();
            }
        });

        //------------------------------------ Create addProductButton -------------------------------------

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setBounds(210,410,150,30);
        page3Frame.add(addProductButton);
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(productNameField.getText().equals("")) {checkProdNameLabel.setVisible(true); isValid = false;}
                else checkProdNameLabel.setVisible(false);

                if(manufacturerNameField.getText().equals("")) {checkManuNameLabel.setVisible(true); isValid = false;}
                else checkManuNameLabel.setVisible(false);

                if(!CheckIntInput(barcodeField.getText())) {checkBarcodeLabel.setVisible(true); isValid = false;}
                else checkBarcodeLabel.setVisible(false);

                if(supplierCatalogField.getText().equals("") || supplierController.checkIfSupplierSupplyProduct(supplierCatalogField.getText(), supplierNum)) {checkCatalogLabel.setVisible(true);isValid = false;}
                else checkCatalogLabel.setVisible(false);

                if(!CheckFloatInput(priceField.getText())) {checkPriceLabel.setVisible(true); isValid = false;}
                else checkPriceLabel.setVisible(false);

                if(!CheckIntInput(quantityField.getText())) {checkQuantityLabel.setVisible(true); isValid = false;}
                else checkQuantityLabel.setVisible(false);

                if(isValid) {
                    supplierController.addSupplierProduct(productNameField.getText(), manufacturerNameField.getText(), Integer.parseInt(barcodeField.getText()), supplierNum
                            , Float.parseFloat(priceField.getText()), supplierCatalogField.getText(), Integer.parseInt(quantityField.getText()));

                    productsAdd.add(supplierCatalogField.getText());

                    ShowAddSuccess();

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


    public static void Page4() {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page4Frame = new JFrame("Create new Supplier");
        page4Frame.setSize(500, 500);
        page4Frame.setLayout(null);

        //------------------------------------- minimum Quantity -----------------------------------

        JLabel minimumQuantityLabel = new JLabel("Minimum quantity for discount?");
        minimumQuantityLabel.setBounds(10, 100, 200, 20);
        minimumQuantityLabel.setVisible(false);

        JTextField minimumQuantityField = new JTextField();
        minimumQuantityField.setBounds(240, 100, 50, 20);
        minimumQuantityField.setVisible(false);

        JLabel checkMinimumQuantityLabel = createCheckLabel("Not Valid or Exist", 300, 100, 100, 20);

        page4Frame.add(minimumQuantityLabel);
        page4Frame.add(minimumQuantityField);
        page4Frame.add(checkMinimumQuantityLabel);

        //------------------------------------- Percent -----------------------------------

        JLabel PercentLabel = new JLabel("How many percent off?");
        PercentLabel.setBounds(10, 140, 200, 20);
        PercentLabel.setVisible(false);

        JTextField PercentField = new JTextField();
        PercentField.setBounds(240, 140, 50, 20);
        PercentField.setVisible(false);

        JLabel checkPercentLabel = createCheckLabel("Not Valid", 300, 140, 80, 20);

        page4Frame.add(PercentLabel);
        page4Frame.add(PercentField);
        page4Frame.add(checkPercentLabel);


        //------------------------------------- comboBoxProductAdd -----------------------------------

        JLabel discountProductLabel = new JLabel("Choose product that supplier provide any discounts?");
        discountProductLabel.setBounds(50, 10, 350, 20);

        JComboBox<String> comboBoxProductAdd = new JComboBox<>();
        comboBoxProductAdd.setBounds(225, 40, 50, 20);

        comboBoxProductAdd.addItem("");
        for (int i = 0; i < productsAdd.size(); i++)
            comboBoxProductAdd.addItem(productsAdd.get(i));

        page4Frame.add(discountProductLabel);
        page4Frame.add(comboBoxProductAdd);

        comboBoxProductAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String choose = (String) combo.getSelectedItem();

                if(choose.equals(""))
                {
                    PercentLabel.setVisible(false);
                    PercentField.setVisible(false);
                    checkPercentLabel.setVisible(false);
                    minimumQuantityLabel.setVisible(false);
                    minimumQuantityField.setVisible(false);
                    checkMinimumQuantityLabel.setVisible(false);
                }

                else
                {
                    PercentLabel.setVisible(true);
                    PercentField.setVisible(true);
                    checkPercentLabel.setVisible(false);
                    minimumQuantityLabel.setVisible(true);
                    minimumQuantityField.setVisible(true);
                    checkMinimumQuantityLabel.setVisible(false);
                }
            }
        });

        //------------------------------------ Create addProductButton -------------------------------------

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setBounds(210,410,150,30);
        page4Frame.add(addProductButton);
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(!CheckIntInput(minimumQuantityField.getText())) { checkMinimumQuantityLabel.setVisible(true); isValid = false;}
                else checkMinimumQuantityLabel.setVisible(false);

                if(!CheckFloatInput(PercentField.getText())) { checkPercentLabel.setVisible(true); isValid = false;}
                else checkPercentLabel.setVisible(false);

                if(isValid) {
                    if (!supplierController.addSupplierProductDiscount(comboBoxProductAdd.getSelectedItem().toString(), Float.parseFloat(PercentField.getText()), Integer.parseInt(minimumQuantityField.getText()), supplierNum))
                        checkMinimumQuantityLabel.setVisible(true);
                    else
                    {
                        ShowAddSuccess();
                        PercentField.setText("");
                        minimumQuantityField.setText("");
                    }
                }
            }
        });

        //------------------------------------ Create nextButton -------------------------------------

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(100,410,100,30);
        page4Frame.add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page4Frame.dispose();
                Page5();
            }
        });

        page4Frame.setVisible(true);
    }

    //----------------------------------------------Page 5---------------------------------------------
    public static void Page5() {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page5Frame = new JFrame("Create new Supplier");
        page5Frame.setSize(500, 500);
        page5Frame.setLayout(null);

        //------------------------------------- minimum Quantity -----------------------------------

        JLabel minimumLabel = new JLabel("Minimum quantity/price for discount?");
        minimumLabel.setBounds(10, 130, 250, 20);
        minimumLabel.setVisible(false);

        JTextField minimumField = new JTextField();
        minimumField.setBounds(290, 130, 50, 20);
        minimumField.setVisible(false);

        JLabel checkMinimumLabel = createCheckLabel("Not Valid or Exist", 350, 100, 100, 20);

        page5Frame.add(minimumLabel);
        page5Frame.add(minimumField);
        page5Frame.add(checkMinimumLabel);

        //------------------------------------- Percent -----------------------------------

        JLabel PercentLabel = new JLabel("How many percent off?");
        PercentLabel.setBounds(10, 100, 250, 20);
        PercentLabel.setVisible(false);

        JTextField PercentField = new JTextField();
        PercentField.setBounds(290, 100, 50, 20);
        PercentField.setVisible(false);

        JLabel checkPercentLabel = createCheckLabel("Not Valid", 350, 100, 100, 20);

        page5Frame.add(PercentLabel);
        page5Frame.add(PercentField);
        page5Frame.add(checkPercentLabel);

        //------------------------------------ Create DiscountButton -------------------------------------

        JComboBox<String> comboBoxPorQ = new JComboBox<>();
        JComboBox<String> comboBoxDiscountOrder = new JComboBox<>();


        JButton addDiscountButton = new JButton("Add Discount");
        addDiscountButton.setBounds(210,410,150,30);
        page5Frame.add(addDiscountButton);
        addDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(!CheckIntInput(minimumField.getText()) || supplierController.CheckIfExistOrderDiscount(supplierNum, comboBoxPorQ.getSelectedItem().toString(), Integer.parseInt(minimumField.getText()))) { checkMinimumLabel.setVisible(true); isValid = false;}
                else checkMinimumLabel.setVisible(false);

                if(!CheckFloatInput(PercentField.getText())) { checkPercentLabel.setVisible(true); isValid = false;}
                else checkPercentLabel.setVisible(false);

                if(isValid) {
                    supplierController.addOrderDiscount(supplierNum, comboBoxPorQ.getSelectedItem().toString(), Integer.parseInt(minimumField.getText()), Float.parseFloat(PercentField.getText()));
                    ShowAddSuccess();
                    comboBoxDiscountOrder.setSelectedItem("");
                }
            }
        });

        //------------------------------------- comboBoxPorQLabel -----------------------------------

        JLabel PorQLabel = new JLabel("Do the discount is for minimum price or for minimum quantity? (p/q)");
        PorQLabel.setBounds(10, 70, 400, 20);
        PorQLabel.setVisible(false);

        comboBoxPorQ.setBounds(430, 70, 50, 20);
        comboBoxPorQ.setVisible(false);

        comboBoxPorQ.addItem("");
        comboBoxPorQ.addItem("p");
        comboBoxPorQ.addItem("q");

        page5Frame.add(PorQLabel);
        page5Frame.add(comboBoxPorQ);

        comboBoxPorQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String choose = (String) combo.getSelectedItem();

                if(choose.equals(""))
                {
                    PercentLabel.setVisible(false);
                    PercentField.setVisible(false);
                    checkPercentLabel.setVisible(false);
                    minimumLabel.setVisible(false);
                    minimumField.setVisible(false);
                    checkMinimumLabel.setVisible(false);
                    addDiscountButton.setVisible(false);
                }
                else
                {
                    PercentLabel.setVisible(true);
                    PercentField.setVisible(true);
                    checkPercentLabel.setVisible(false);
                    minimumLabel.setVisible(true);
                    minimumField.setVisible(true);
                    checkMinimumLabel.setVisible(false);
                    addDiscountButton.setVisible(true);
                }
            }
        });

        //------------------------------------- comboBoxProductAdd -----------------------------------

        JLabel discountOrderLabel = new JLabel("Do the supplier supply any discounts for order?");
        discountOrderLabel.setBounds(50, 10, 350, 20);

        comboBoxDiscountOrder.setBounds(225, 40, 50, 20);

        comboBoxDiscountOrder.addItem("");
        comboBoxDiscountOrder.addItem("Yes");

        page5Frame.add(discountOrderLabel);
        page5Frame.add(comboBoxDiscountOrder);

        comboBoxDiscountOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String choose = (String) combo.getSelectedItem();

                if(choose.equals(""))
                {
                    comboBoxPorQ.setSelectedItem("");
                    comboBoxPorQ.setVisible(false);
                    PorQLabel.setVisible(false);
                    addDiscountButton.setVisible(true);
                }
                else
                {
                    comboBoxPorQ.setVisible(true);
                    PorQLabel.setVisible(true);
                    addDiscountButton.setVisible(false);
                }
            }
        });

        //------------------------------------ Create nextButton -------------------------------------

        JButton finishButton = new JButton("Finish");
        finishButton.setBounds(100,410,100,30);
        page5Frame.add(finishButton);

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page5Frame.dispose();
                OldFrame.setVisible(true);
            }
        });

        page5Frame.setVisible(true);
    }

    public static boolean CheckIntInput(String value)
    {
        int num;
        try {num = Integer.parseInt(value);}
        catch (NumberFormatException error) {return false;}
        return num > 0;
    }
    public static boolean CheckFloatInput(String value)
    {
        float num;
        try {num = Float.parseFloat(value);}
        catch (NumberFormatException error) {return false;}
        return !(num <= 0);
    }

    public static JLabel createCheckLabel(String message, int x, int y, int width, int height)
    {
        JLabel checkLabel = new JLabel(message);
        checkLabel.setForeground(Color.RED);
        checkLabel.setBounds(x, y, width,height);
        checkLabel.setVisible(false);
        return checkLabel;
    }

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
}

