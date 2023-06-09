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

        //------------------------------------ Name label and field ----------------------------------------
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 10, 100,20);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 10, 80,20);

        JLabel checkLabel1 = new JLabel("Empty");
        checkLabel1.setForeground(Color.RED);
        checkLabel1.setBounds(250, 10, 150,20);
        checkLabel1.setVisible(false);

        page1Frame.add(nameLabel);
        page1Frame.add(nameField);
        page1Frame.add(checkLabel1);

        //----------------------------- SupplierNum label and field and checkLabel ----------------------------------------

        JLabel supplierNumLabel = new JLabel("SupplierNum");
        supplierNumLabel.setBounds(10, 50, 100,20);

        JTextField supplierNumField = new JTextField();
        supplierNumField.setBounds(150, 50, 80,20);

        JLabel checkLabel2 = new JLabel("SupplierNum exist");
        checkLabel2.setForeground(Color.RED);
        checkLabel2.setBounds(250, 50, 150,20);
        checkLabel2.setVisible(false);

        page1Frame.add(supplierNumLabel);
        page1Frame.add(supplierNumField);
        page1Frame.add(checkLabel2);

        //------------------------------------ bankAccount label and field ----------------------------------------

        JLabel bankAccountLabel = new JLabel("bankAccount");
        bankAccountLabel.setBounds(10, 80, 100,20);

        JTextField bankAccountField = new JTextField();
        bankAccountField.setBounds(150, 80, 80,20);

        JLabel checkLabel3 = new JLabel("Empty");
        checkLabel3.setForeground(Color.RED);
        checkLabel3.setBounds(250, 80, 150,20);
        checkLabel3.setVisible(false);

        page1Frame.add(bankAccountLabel);
        page1Frame.add(bankAccountField);
        page1Frame.add(checkLabel3);

        //------------------------------------ PaymentTerm label and field ----------------------------------------

        JLabel PaymentTermLabel = new JLabel("PaymentTerm");
        PaymentTermLabel.setBounds(10, 110, 100,20);

        JComboBox<String> comboBoxPaymentTerm = new JComboBox<>();
        comboBoxPaymentTerm.setBounds(150,110,80,20);
        comboBoxPaymentTerm.addItem("");
        comboBoxPaymentTerm.addItem("Net");
        comboBoxPaymentTerm.addItem("Net 30 days");
        comboBoxPaymentTerm.addItem("Net 60 days");

        JLabel checkLabel4 = new JLabel("Empty");
        checkLabel4.setForeground(Color.RED);
        checkLabel4.setBounds(250, 110, 150,20);
        checkLabel4.setVisible(false);

        page1Frame.add(PaymentTermLabel);
        page1Frame.add(comboBoxPaymentTerm);
        page1Frame.add(checkLabel4);

        //-------------------------------- Contact table and label --------------------------------------

        JLabel ContactLabel = new JLabel("Contacts");
        ContactLabel.setBounds(10, 140, 100,20);

        String[][] dataContact = {{"",""},{"",""},{"",""},{"",""}};
        String[] columnsContact = {"Name", "PhoneNumber"};

        JTable jTableContacts= new JTable(dataContact, columnsContact);
        jTableContacts.setBounds(150, 140, 200, 87);

        JScrollPane jScrollPane1 = new JScrollPane(jTableContacts);
        jScrollPane1.setBounds(150, 140, 200, 87);

        JLabel checkLabel5 = new JLabel("At least one");
        checkLabel5.setForeground(Color.RED);
        checkLabel5.setBounds(370, 157, 150,20);
        checkLabel5.setVisible(false);

        page1Frame.add(ContactLabel);
        page1Frame.add(jScrollPane1);
        page1Frame.add(checkLabel5);

        //-------------------------------- Contact table and label --------------------------------------

        JLabel CategoryLabel = new JLabel("Categories");
        CategoryLabel.setBounds(10, 240, 100,20);

        String[][] dataCategory = {{""},{""},{""}, {""}};
        String[] columnsCategory = {"Category"};

        JTable jTableCategories= new JTable(dataCategory, columnsCategory);
        jTableCategories.setBounds(150, 240, 200, 87);

        JScrollPane jScrollPane2 = new JScrollPane(jTableCategories);
        jScrollPane2.setBounds(150, 240, 100, 87);

        JLabel checkLabel6 = new JLabel("At least one");
        checkLabel6.setForeground(Color.RED);
        checkLabel6.setBounds(270, 257, 150,20);
        checkLabel6.setVisible(false);

        page1Frame.add(CategoryLabel);
        page1Frame.add(jScrollPane2);
        page1Frame.add(checkLabel6);

        //------------------------------------ Create nextButton -------------------------------------

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(200,370,100,30);
        page1Frame.add(nextButton);
        nextButton.addActionListener(new nextClick(page1Frame, nameField, checkLabel1, supplierNumField, checkLabel2, bankAccountField
                , checkLabel3, comboBoxPaymentTerm, checkLabel4, jTableContacts, checkLabel5, jTableCategories, checkLabel6));

        page1Frame.setVisible(true);
    }


    private static class nextClick implements ActionListener {
        private JFrame Page1Frame;
        private JTextField NameField;
        private JTextField SupplierNumField;
        private JTextField BankField;
        private JComboBox<String> ComboBoxPaymentTerm;
        private JTable JTableContacts;
        private JTable JTableCategories;

        private JLabel CheckLabelName;
        private JLabel CheckLabelNum;
        private JLabel CheckLabelBank;
        private JLabel CheckLabelPayment;
        private JLabel CheckLabelContacts;
        private JLabel CheckLabelCategories;


        public nextClick(JFrame page1Frame, JTextField nameField, JLabel checkLabelName, JTextField supplierNumField, JLabel checkLabelNum, JTextField bankField, JLabel checkLabelBank
                ,JComboBox<String> comboBoxPaymentTerm, JLabel checkLabelPayment ,JTable jTableContacts, JLabel checkLabelContacts , JTable jTableCategories, JLabel checkLabelCategories)
        {
            Page1Frame = page1Frame;

            NameField = nameField;
            CheckLabelName = checkLabelName;

            SupplierNumField = supplierNumField;
            CheckLabelNum = checkLabelNum;

            BankField = bankField;
            CheckLabelBank = checkLabelBank;

            ComboBoxPaymentTerm = comboBoxPaymentTerm;
            CheckLabelPayment = checkLabelPayment;

            JTableContacts = jTableContacts;
            CheckLabelContacts = checkLabelContacts;

            JTableCategories = jTableCategories;
            CheckLabelCategories = checkLabelCategories;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isValid = true;

            if(NameField.getText().equals(""))
            {
                CheckLabelName.setVisible(true);
                isValid = false;
            }
            else
                CheckLabelName.setVisible(false);

            if(supplierController.checkIfSupplierExist(SupplierNumField.getText()) || SupplierNumField.getText().equals(""))
            {
                CheckLabelNum.setVisible(true);
                isValid = false;
            }
            else
                CheckLabelNum.setVisible(false);

            if(BankField.getText().equals(""))
            {
                CheckLabelBank.setVisible(true);
                isValid = false;
            }
            else
                CheckLabelBank.setVisible(false);

            if(ComboBoxPaymentTerm.getSelectedItem().equals(""))
            {
                CheckLabelPayment.setVisible(true);
                isValid = false;
            }
            else
                CheckLabelPayment.setVisible(false);

            if(JTableContacts.getValueAt(0, 0).equals("") || JTableContacts.getValueAt(0, 1).equals(""))
            {
                CheckLabelContacts.setVisible(true);
                isValid = false;
            }
            else
                CheckLabelContacts.setVisible(false);

            if(JTableCategories.getValueAt(0, 0).toString().equals(""))
            {
                CheckLabelCategories.setVisible(true);
                isValid = false;
            }
            else
                CheckLabelCategories.setVisible(false);

            if(isValid)
            {
                supplierGenerator.reset();
                String contactName = "";
                String phoneNumber = "";
                String categoryName = "";

                for (int i = 0; i < JTableContacts.getRowCount(); i++)
                {
                    contactName = JTableContacts.getValueAt(i, 0).toString();
                    phoneNumber = JTableContacts.getValueAt(i, 1).toString();
                    if(contactName.equals("") || phoneNumber.equals(""))
                        continue;
                    supplierGenerator.addContact(contactName, phoneNumber);
                }

                for (int i = 0; i < JTableCategories.getRowCount(); i++)
                {
                    categoryName = JTableCategories.getValueAt(i, 0).toString();
                    if(categoryName.equals(""))
                        continue;
                    categories.add(categoryName);
                }

                supplierName = NameField.getText();
                supplierNum = SupplierNumField.getText();
                bankAccount = BankField.getText();
                paymentTerm = PaymentTerm.values()[ComboBoxPaymentTerm.getSelectedIndex()-1];

                Page1Frame.dispose();
                Page2();
            }


        }
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

        JTextField daysToSupply = new JTextField();
        daysToSupply.setBounds(280, 360, 80,20);
        daysToSupply.setVisible(false);

        JLabel checkLabel1 = new JLabel("Empty");
        checkLabel1.setForeground(Color.RED);
        checkLabel1.setBounds(380, 360, 50,20);
        checkLabel1.setVisible(false);

        page2Frame.add(daysToSupplyLabel);
        page2Frame.add(daysToSupply);
        page2Frame.add(checkLabel1);

        //------------------------------------ deliveryDays label and checkBox ----------------------------------------

        JLabel deliveryDaysLabel = new JLabel("Choose days");
        deliveryDaysLabel.setBounds(100, 100, 200,20);
        deliveryDaysLabel.setVisible(false);

        JLabel checkLabel2 = new JLabel("At least one");
        checkLabel2.setForeground(Color.RED);
        checkLabel2.setBounds(350, 100, 100,20);
        checkLabel2.setVisible(false);

        JCheckBox[] checkBoxes = new JCheckBox[7];
        String[] daysName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for(int i=0 ; i<7 ; i++)
        {
            checkBoxes[i] = new JCheckBox(daysName[i]);
            checkBoxes[i].setBounds(200, 130 + i*30, 100, 20);
            checkBoxes[i].setVisible(false);
            page2Frame.add(checkBoxes[i]);
        }

        page2Frame.add(deliveryDaysLabel);
        page2Frame.add(checkLabel2);


        //------------------------------------ hasPermanentDays label and comboBox ----------------------------------------

        JLabel hasPermanentDaysLabel = new JLabel("Does he have permanent days that he comes?");
        hasPermanentDaysLabel.setBounds(10, 50, 300,20);
        hasPermanentDaysLabel.setVisible(false);

        JComboBox<String> PermanentDaysComboBox = new JComboBox<>();
        PermanentDaysComboBox.addActionListener(new HasPermanentDaysActionListener(checkBoxes, deliveryDaysLabel, daysToSupplyLabel, daysToSupply, nextButton, checkLabel1, checkLabel2));

        PermanentDaysComboBox.setBounds(350,50,50,20);
        PermanentDaysComboBox.addItem("");
        PermanentDaysComboBox.addItem("Yes");
        PermanentDaysComboBox.addItem("No");
        PermanentDaysComboBox.setVisible(false);

        page2Frame.add(hasPermanentDaysLabel);
        page2Frame.add(PermanentDaysComboBox);

        //------------------------------------ isSupplierBringProduct label and comboBox ----------------------------------------

        JLabel BringProductLabel = new JLabel("Does the supplier transport his products himself?");
        BringProductLabel.setBounds(10, 10, 300,20);

        JComboBox<String> comboBoxBring = new JComboBox<>();
        comboBoxBring.addActionListener(new BringProductActionListener(hasPermanentDaysLabel, PermanentDaysComboBox, nextButton));

        comboBoxBring.setBounds(350,10,50,20);
        comboBoxBring.addItem("");
        comboBoxBring.addItem("Yes");
        comboBoxBring.addItem("No");

        page2Frame.add(BringProductLabel);
        page2Frame.add(comboBoxBring);

        nextButton.addActionListener(new next2Click(page2Frame, comboBoxBring, PermanentDaysComboBox, checkBoxes, daysToSupply, checkLabel1, checkLabel2));
        page2Frame.setVisible(true);
    }

    private static class BringProductActionListener implements ActionListener {
        JLabel HasPermanentDaysLabel;
        JComboBox<String> PermanentDaysComboBox;
        JButton NextButton;

        public BringProductActionListener(JLabel hasPermanentDaysLabel, JComboBox<String> permanentDaysComboBox, JButton nextButton) {
            HasPermanentDaysLabel = hasPermanentDaysLabel;
            PermanentDaysComboBox = permanentDaysComboBox;
            NextButton = nextButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String yesORno = (String) combo.getSelectedItem();

            if(yesORno.equals("Yes")) {
                HasPermanentDaysLabel.setVisible(true);
                PermanentDaysComboBox.setVisible(true);
                NextButton.setVisible(false);

            }

            if(yesORno.equals("No"))
            {
                HasPermanentDaysLabel.setVisible(false);
                PermanentDaysComboBox.setVisible(false);
                PermanentDaysComboBox.setSelectedItem("");
                NextButton.setVisible(true);
            }

            if(yesORno.equals(""))
            {
                HasPermanentDaysLabel.setVisible(false);
                PermanentDaysComboBox.setVisible(false);
                NextButton.setVisible(false);
            }
        }
    }

    private static class HasPermanentDaysActionListener implements ActionListener {
        JCheckBox[] CheckBoxes;
        JLabel DeliveryDaysLabel;
        JLabel DaysToSupplyLabel;
        JTextField DaysField;
        JButton NextButton;
        JLabel CheckLabelDaysToSupply;
        JLabel CheckLabelDeliveryDays;


        public HasPermanentDaysActionListener(JCheckBox[] checkBoxes, JLabel deliveryDaysLabel, JLabel daysToSupplyLabel, JTextField daysField, JButton nextButton, JLabel checkLabelDaysToSupply, JLabel checkLabelDeliveryDays) {

            CheckBoxes = checkBoxes;
            DeliveryDaysLabel = deliveryDaysLabel;
            DaysToSupplyLabel = daysToSupplyLabel;
            DaysField = daysField;
            NextButton = nextButton;
            CheckLabelDaysToSupply = checkLabelDaysToSupply;
            CheckLabelDeliveryDays= checkLabelDeliveryDays;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String yesORno = (String) combo.getSelectedItem();

            if(yesORno.equals("Yes")) {
                DeliveryDaysLabel.setVisible(true);
                NextButton.setVisible(true);
                for(int i=0 ; i<7 ; i++)
                    CheckBoxes[i].setVisible(true);

                DaysToSupplyLabel.setVisible(false);
                DaysField.setVisible(false);
                CheckLabelDaysToSupply.setVisible(false);
            }

            else if(yesORno.equals("No"))
            {
                DeliveryDaysLabel.setVisible(false);
                CheckLabelDeliveryDays.setVisible(false);
                for(int i=0 ; i<7 ; i++)
                    CheckBoxes[i].setVisible(false);


                DaysToSupplyLabel.setVisible(true);
                DaysField.setVisible(true);
                NextButton.setVisible(true);
            }

            else
            {
                DeliveryDaysLabel.setVisible(false);
                DaysToSupplyLabel.setVisible(false);
                DaysField.setVisible(false);
                NextButton.setVisible(false);
                CheckLabelDaysToSupply.setVisible(false);
                CheckLabelDeliveryDays.setVisible(false);

                for(int i=0 ; i<7 ; i++)
                    CheckBoxes[i].setVisible(false);
            }
        }
    }

    private static class next2Click implements ActionListener {

        Frame Page2Frame;
        JComboBox<String> ComboBoxBring;
        JComboBox<String> PermanentDaysComboBox;
        JCheckBox[] CheckBoxes;
        JTextField DaysToSupply;
        JLabel CheckLabelDaysToSupply;
        JLabel CheckLabelDeliveryDays;

        public next2Click(Frame page2Frame, JComboBox<String> comboBoxBring, JComboBox<String> permanentDaysComboBox, JCheckBox[] checkBoxes, JTextField daysToSupply, JLabel checkLabelDaysToSupply, JLabel checkLabelDeliveryDays) {

            Page2Frame = page2Frame;
            ComboBoxBring = comboBoxBring;
            PermanentDaysComboBox = permanentDaysComboBox;
            CheckBoxes = checkBoxes;
            DaysToSupply = daysToSupply;
            CheckLabelDaysToSupply = checkLabelDaysToSupply;
            CheckLabelDeliveryDays = checkLabelDeliveryDays;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] deliveryDays = new boolean[7];
            int daysToSupply = -1;
            boolean isSupplierBringProduct = false;
            boolean hasPermanentDays = false;

            int countFalse = 0;


            if(ComboBoxBring.getSelectedItem().toString().equals("Yes"))
            {
                isSupplierBringProduct = true;
                if(PermanentDaysComboBox.getSelectedItem().toString().equals("Yes"))
                {
                    hasPermanentDays = true;
                    for(int i=0 ; i<7 ; i++)
                    {
                        deliveryDays[i] = CheckBoxes[i].isSelected();
                        if(!deliveryDays[i])
                            countFalse++;
                    }
                    if(countFalse == 7) {
                        CheckLabelDeliveryDays.setVisible(true);
                        return;
                    }
                }
                if(PermanentDaysComboBox.getSelectedItem().toString().equals("No"))
                {
                    int num;
                    try {num = Integer.parseInt(DaysToSupply.getText());}
                    catch (NumberFormatException error) {
                        CheckLabelDaysToSupply.setVisible(true);
                        return;
                    }
                    if (num <= 0) {
                        CheckLabelDaysToSupply.setVisible(true);
                        return;
                    }
                }
            }

            supplierGenerator.CreateSupplierAndAgreement(supplierName, supplierNum, bankAccount, paymentTerm, categories, hasPermanentDays, isSupplierBringProduct, deliveryDays, daysToSupply);
            Page2Frame.dispose();
            Page3();
        }
    }

    public static void Page3() {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page3Frame = new JFrame("Create new Supplier");
        page3Frame.setSize(500, 500);
        page3Frame.setLayout(null);

        JLabel productNameLabel = new JLabel("Enter the product name");
        productNameLabel.setBounds(10, 10, 290, 20);
        JTextField productNameField = new JTextField();
        productNameField.setBounds(300, 10, 50, 20);
        JLabel checkProdNameLabel = new JLabel("Empty");
        checkProdNameLabel.setBounds(370, 10, 100, 20);
        checkProdNameLabel.setForeground(Color.RED);
        checkProdNameLabel.setVisible(false);
        page3Frame.add(productNameLabel);
        page3Frame.add(productNameField);
        page3Frame.add(checkProdNameLabel);

        JLabel manufacturerNameLabel = new JLabel("Enter the manufacturer name");
        manufacturerNameLabel.setBounds(10, 40, 290, 20);
        JTextField manufacturerNameField = new JTextField();
        manufacturerNameField.setBounds(300, 40, 50, 20);
        JLabel checkManuNameLabel = new JLabel("Empty");
        checkManuNameLabel.setBounds(370, 40, 100, 20);
        checkManuNameLabel.setForeground(Color.RED);
        checkManuNameLabel.setVisible(false);
        page3Frame.add(manufacturerNameLabel);
        page3Frame.add(manufacturerNameField);
        page3Frame.add(checkManuNameLabel);

        JLabel barcodeLabel = new JLabel("Enter barcode: (If unknown enter: 99)");
        barcodeLabel.setBounds(10, 70, 290, 20);
        JTextField barcodeField = new JTextField();
        barcodeField.setBounds(300, 70, 50, 20);
        JLabel checkBarcodeLabel = new JLabel("Must be positive");
        checkBarcodeLabel.setBounds(370, 70, 100, 20);
        checkBarcodeLabel.setForeground(Color.RED);
        checkBarcodeLabel.setVisible(false);
        page3Frame.add(barcodeLabel);
        page3Frame.add(barcodeField);
        page3Frame.add(checkBarcodeLabel);

        JLabel supplierCatalogLabel = new JLabel("Enter supplier catalog");
        supplierCatalogLabel.setBounds(10, 100, 290, 20);
        JTextField supplierCatalogField = new JTextField();
        supplierCatalogField.setBounds(300, 100, 50, 20);
        JLabel checkCatalogLabel = new JLabel("Empty or Exist");
        checkCatalogLabel.setBounds(370, 100, 100, 20);
        checkCatalogLabel.setForeground(Color.RED);
        checkCatalogLabel.setVisible(false);
        page3Frame.add(supplierCatalogLabel);
        page3Frame.add(supplierCatalogField);
        page3Frame.add(checkCatalogLabel);

        JLabel priceLabel = new JLabel("Enter price per unit");
        priceLabel.setBounds(10, 130, 290, 20);
        JTextField priceField = new JTextField();
        priceField.setBounds(300, 130, 50, 20);
        JLabel checkPriceLabel = new JLabel("Must be positive");
        checkPriceLabel.setBounds(370, 130, 100, 20);
        checkPriceLabel.setForeground(Color.RED);
        checkPriceLabel.setVisible(false);
        page3Frame.add(priceLabel);
        page3Frame.add(priceField);
        page3Frame.add(checkPriceLabel);

        JLabel quantityLabel = new JLabel("Enter the quantity of products you can supply");
        quantityLabel.setBounds(10, 160, 290, 20);
        JTextField quantityField = new JTextField();
        quantityField.setBounds(300, 160, 50, 20);
        JLabel checkQuantityLabel = new JLabel("Must be positive");
        checkQuantityLabel.setBounds(370, 160, 100, 20);
        checkQuantityLabel.setForeground(Color.RED);
        checkQuantityLabel.setVisible(false);
        page3Frame.add(quantityLabel);
        page3Frame.add(quantityField);
        page3Frame.add(checkQuantityLabel);

        //------------------------------------ Create nextButton -------------------------------------

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(100,410,100,30);
        page3Frame.add(nextButton);
        nextButton.setVisible(false);

        //------------------------------------ Create addProductButton -------------------------------------

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setBounds(210,410,150,30);
        addProductButton.addActionListener(new addProductClick(page3Frame, nextButton, productNameField, manufacturerNameField, barcodeField, supplierCatalogField, priceField, quantityField
                , checkProdNameLabel, checkManuNameLabel, checkBarcodeLabel, checkCatalogLabel, checkPriceLabel, checkQuantityLabel));

        page3Frame.add(addProductButton);


        page3Frame.setVisible(true);
    }

    private static class addProductClick implements ActionListener {
        private JFrame Page3Frame;
        private JButton NextButton;
        private JTextField ProductNameField, ManufacturerNameField, BarcodeField, SupplierCatalogField, PriceField, QuantityField;
        private JLabel CheckProductNameLabel, CheckManufacturerNameLabel, CheckBarcodeLabel, CheckSupplierCatalogLabel, CheckPriceLabel, CheckQuantityLabel;

        public addProductClick(JFrame page3Frame, JButton nextButton, JTextField productNameField, JTextField manufacturerNameField, JTextField barcodeField, JTextField supplierCatalogField, JTextField priceField, JTextField quantityField
                , JLabel checkProductNameLabel, JLabel checkManufacturerNameLabel, JLabel checkBarcodeLabel, JLabel checkSupplierCatalogLabel, JLabel checkPriceLabel, JLabel checkQuantityLabel) {

            Page3Frame = page3Frame;
            NextButton = nextButton;
            ProductNameField = productNameField;
            ManufacturerNameField = manufacturerNameField;
            BarcodeField = barcodeField;
            SupplierCatalogField = supplierCatalogField;
            PriceField = priceField;
            QuantityField = quantityField;
            CheckProductNameLabel = checkProductNameLabel;
            CheckManufacturerNameLabel = checkManufacturerNameLabel;
            CheckBarcodeLabel = checkBarcodeLabel;
            CheckSupplierCatalogLabel = checkSupplierCatalogLabel;
            CheckPriceLabel = checkPriceLabel;
            CheckQuantityLabel = checkQuantityLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isValid = true;

            if(ProductNameField.getText().equals(""))
            {
                CheckProductNameLabel.setVisible(true);
                isValid = false;
            }
            else
                CheckProductNameLabel.setVisible(false);

            if(ManufacturerNameField.getText().equals(""))
            {
                CheckManufacturerNameLabel.setVisible(true);
                isValid = false;
            }
            else
                CheckManufacturerNameLabel.setVisible(false);

            if(!CheckIntInput(BarcodeField.getText()))
            {
                CheckBarcodeLabel.setVisible(true);
                isValid = false;
            }
            else
                CheckBarcodeLabel.setVisible(false);

            if(SupplierCatalogField.getText().equals("") || supplierController.checkIfSupplierSupplyProduct(SupplierCatalogField.getText(), supplierNum))
            {
                CheckSupplierCatalogLabel.setVisible(true);
                isValid = false;
            }
            else
                CheckSupplierCatalogLabel.setVisible(false);

            if(!CheckFloatInput(PriceField.getText()))
            {
                CheckPriceLabel.setVisible(true);
                isValid = false;
            }
            else
                CheckPriceLabel.setVisible(false);

            if(!CheckIntInput(QuantityField.getText()))
            {
                CheckQuantityLabel.setVisible(true);
                isValid = false;
            }
            else
                CheckQuantityLabel.setVisible(false);

            if(isValid)
            {
                supplierController.addSupplierProduct(ProductNameField.getText(), ManufacturerNameField.getText(), Integer.parseInt(BarcodeField.getText()), supplierNum
                        , Float.parseFloat(PriceField.getText()), SupplierCatalogField.getText(), Integer.parseInt(QuantityField.getText()));

                JFrame AddSuccess = new JFrame("Add success");
                AddSuccess.setSize(200, 200);
                AddSuccess.setLayout(null);

                JLabel addSuccessLabel = new JLabel("Add success");
                addSuccessLabel.setBounds(50, 50, 150, 20);
                AddSuccess.add(addSuccessLabel);

                JButton okButton = new JButton("OK");
                okButton.setBounds(50, 100, 80, 20);
                AddSuccess.add(okButton);
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Action to perform when the button is clicked
                        AddSuccess.dispose();
                    }
                });

                AddSuccess.setVisible(true);
                NextButton.setVisible(true);
                ProductNameField.setText("");
                ManufacturerNameField.setText("");
                BarcodeField.setText("");
                SupplierCatalogField.setText("");
                PriceField.setText("");
                QuantityField.setText("");

            }
        }
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
}

