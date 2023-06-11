package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateSupplierAgreementGUI {

    static JFrame OldFrame;
    static SupplierController supplierController;

    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page1Frame = HelperFunctionGUI.createNewFrame("Update Supplier Agreement");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel supplierNumLabel = new JLabel("Choose supplier");

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxSupplierNum = HelperFunctionGUI.createComboBoxSupplierNum();

        //----------------------------------------- Create JButton ----------------------------------------

        JButton UpdateDeliveryDetailsButton = new JButton("Update delivery details");
        JButton UpdateAddDeleteProductButton = new JButton("Update/Add/Delete Product");
        JButton AddDeleteProductDiscountButton = new JButton("Add/Delete product discount");
        JButton AddDeleteOrderDiscountButton = new JButton("Add/Delete order discount");
        JButton ExitButton = HelperFunctionGUI.createExitButton(page1Frame, oldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        supplierNumLabel.setBounds(10, 10, 200, 20);
        comboBoxSupplierNum.setBounds(230, 10, 100, 20);

        UpdateDeliveryDetailsButton.setBounds(150, 100, 200, 40);
        UpdateAddDeleteProductButton.setBounds(150, 160, 200, 40);
        AddDeleteProductDiscountButton.setBounds(150, 220, 200, 40);
        AddDeleteOrderDiscountButton.setBounds(150, 280, 200, 40);

        //-------------------------------------- Set not visible -------------------------------------------------------------

        JComponent[] hiddenComponents = {UpdateDeliveryDetailsButton, UpdateAddDeleteProductButton,
                AddDeleteProductDiscountButton, AddDeleteOrderDiscountButton};

        HelperFunctionGUI.hideComponents(hiddenComponents);

        //------------------------------------ Add to currFrame --------------------------------------------------------------

        JComponent[] addComponents = {supplierNumLabel, comboBoxSupplierNum, UpdateDeliveryDetailsButton,
                UpdateAddDeleteProductButton, AddDeleteProductDiscountButton, AddDeleteOrderDiscountButton, ExitButton};

        HelperFunctionGUI.addComponentsToFrame(page1Frame, addComponents);

        // ------------------------------------- Add action listener to JObjects -------------------------------------

        comboBoxSupplierNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String supplierNum = (String) combo.getSelectedItem();

                if(supplierNum.equals("")) {
                    HelperFunctionGUI.hideComponents(hiddenComponents);
                }
                else
                    HelperFunctionGUI.showComponents(hiddenComponents);
            }
        });

        UpdateDeliveryDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page1Frame.setVisible(false);
                UpdateDeliveryDetailsPage(comboBoxSupplierNum.getSelectedItem().toString(), page1Frame);
            }
        });

        UpdateAddDeleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page1Frame.setVisible(false);
                UpdateAddDeleteProductPage(comboBoxSupplierNum.getSelectedItem().toString(), page1Frame);
            }
        });

        page1Frame.setVisible(true);

        AddDeleteProductDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page1Frame.setVisible(false);
                AddDeleteProductDiscountPage(comboBoxSupplierNum.getSelectedItem().toString(), page1Frame);
            }
        });

        AddDeleteOrderDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page1Frame.setVisible(false);
                AddDeleteOrderDiscountPage(comboBoxSupplierNum.getSelectedItem().toString(), page1Frame);
            }
        });
    }

    public static void UpdateDeliveryDetailsPage(String supplierNum, JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page2Frame = HelperFunctionGUI.createNewFrame("Update Supplier Agreement");

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

        JButton applyButton = new JButton("Apply");

        //-------------------------------------- Set bounds ---------------------------------------------

        bringProductLabel.setBounds(10, 10, 300,20);
        comboBoxBring.setBounds(350,10,50,20);

        hasPermanentDaysLabel.setBounds(10, 50, 300,20);
        hasPermanentDaysComboBox.setBounds(350,50,50,20);

        deliveryDaysLabel.setBounds(100, 100, 200,20);

        daysToSupplyLabel.setBounds(10, 330, 250, 20);
        daysToSupplyField.setBounds(280, 330, 80,20);
        checkDaysToSupplyLabel.setBounds(390, 330, 80, 20);

        applyButton.setBounds(200,360,100,30);

        //-------------------------------------- Set not visible ---------------------------------------------

        JComponent [] hiddenComponents = {hasPermanentDaysLabel, hasPermanentDaysComboBox,
                deliveryDaysLabel, daysToSupplyLabel, daysToSupplyField, applyButton};

        HelperFunctionGUI.hideComponents(hiddenComponents);

        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {bringProductLabel, comboBoxBring,
                hasPermanentDaysLabel, hasPermanentDaysComboBox,
                deliveryDaysLabel, checkDeliveryDaysLabel,
                daysToSupplyLabel, daysToSupplyField, checkDaysToSupplyLabel, applyButton};

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
                    applyButton.setVisible(false);
                }

                else if(yesORno.equals("No")) {
                    hasPermanentDaysLabel.setVisible(false);
                    hasPermanentDaysComboBox.setVisible(false);
                    hasPermanentDaysComboBox.setSelectedItem("");
                    applyButton.setVisible(true);
                }

                else {
                    hasPermanentDaysLabel.setVisible(false);
                    hasPermanentDaysComboBox.setVisible(false);
                    hasPermanentDaysComboBox.setSelectedItem("");
                    applyButton.setVisible(false);
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
                    applyButton.setVisible(true);
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
                    applyButton.setVisible(true);
                }

                else {
                    checkDaysToSupplyLabel.setVisible(false);
                    daysToSupplyField.setVisible(false);
                    daysToSupplyLabel.setVisible(false);
                    applyButton.setVisible(false);
                    checkDeliveryDaysLabel.setVisible(false);
                    deliveryDaysLabel.setVisible(false);

                    for (int i = 0; i < 7; i++)
                        checkBoxesDeliveryDays[i].setVisible(false);
                }
            }});
        //*************************************************************************

        applyButton.addActionListener(new ActionListener() {
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

                supplierController.editAgreement(supplierNum, hasPermanentDays, isSupplierBringProduct, deliveryDays, daysToSupply);
                page2Frame.dispose();
                backFrame.setVisible(true);
                HelperFunctionGUI.ShowProcessSuccessfully();
            }});

        page2Frame.setVisible(true);
    }

    public static void UpdateAddDeleteProductPage(String supplierNum, JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page3Frame = HelperFunctionGUI.createNewFrame("Update Supplier Agreement");

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel productNameLabel = new JLabel("Enter the product name");
        JLabel checkProdNameLabel = HelperFunctionGUI.createCheckLabel("Empty", 370, 10, 100, 20);

        JLabel manufacturerNameLabel = new JLabel("Enter the manufacturer name");
        JLabel checkManuNameLabel = HelperFunctionGUI.createCheckLabel("Empty", 370, 40, 100, 20);

        JLabel barcodeLabel = new JLabel("Enter barcode: (If unknown enter: 99)");
        JLabel checkBarcodeLabel = HelperFunctionGUI.createCheckLabel("Must be positive", 370, 70, 100, 20);

        JLabel supplierCatalogLabel = new JLabel("Enter supplier catalog");
        JLabel checkCatalogLabel = HelperFunctionGUI.createCheckLabel("", 370, 100, 100, 20);

        JLabel priceLabel = new JLabel("Enter price per unit");
        JLabel checkPriceLabel = HelperFunctionGUI.createCheckLabel("Must be positive", 370, 130, 100, 20);

        JLabel quantityLabel = new JLabel("Enter the quantity of products you can supply");
        JLabel checkQuantityLabel = HelperFunctionGUI.createCheckLabel("Must be positive", 370, 160, 100, 20);

        JLabel buffer = new JLabel("-------------------------------------------------------------------------------------------------------------------------------------------");
        JLabel deleteProductLabel = new JLabel("Choose a product to delete");

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField productNameField = new JTextField();
        JTextField manufacturerNameField = new JTextField();
        JTextField barcodeField = new JTextField();
        JTextField supplierCatalogField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        //------------------------------------ Create comboBox ---------------------------------------

        JComboBox<String> comboBoxSupplierCatalog = HelperFunctionGUI.createComboBoxSupplierProduct(supplierNum);

        //------------------------------------ Create nextButton -------------------------------------

        JButton addProductButton = new JButton("Add Product");
        JButton updateProductButton = new JButton("Update Product");
        JButton deleteProductButton = new JButton("Delete Product");


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

        addProductButton.setBounds(300,200,150,30);
        updateProductButton.setBounds(140, 200, 150, 30);

        buffer.setBounds(0,250, 500, 10);

        deleteProductLabel.setBounds(10, 300, 200, 20);
        comboBoxSupplierCatalog.setBounds(230, 300, 80, 20 );
        deleteProductButton.setBounds(320,295,150,30);

        //-------------------------------------- Set not visible ---------------------------------------------

        deleteProductButton.setVisible(false);

        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {productNameLabel, productNameField, checkProdNameLabel,
                manufacturerNameLabel, manufacturerNameField, checkManuNameLabel,
                barcodeLabel, barcodeField, checkBarcodeLabel,
                supplierCatalogLabel, supplierCatalogField, checkCatalogLabel,
                priceLabel, priceField, checkPriceLabel,
                quantityLabel, quantityField, checkQuantityLabel,
                buffer, addProductButton, updateProductButton,
                deleteProductLabel, comboBoxSupplierCatalog, deleteProductButton};

        HelperFunctionGUI.addComponentsToFrame(page3Frame ,components);


        // ------------------------------------- Add action listener to JObjects ------------------------------


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

                if(supplierCatalogField.getText().equals("") || supplierController.checkIfSupplierSupplyProduct(supplierCatalogField.getText(), supplierNum)) {
                    checkCatalogLabel.setText("Empty or Exist");
                    checkCatalogLabel.setVisible(true);
                    isValid = false;
                }
                else checkCatalogLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckFloatInput(priceField.getText())) {checkPriceLabel.setVisible(true); isValid = false;}
                else checkPriceLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckIntInput(quantityField.getText())) {checkQuantityLabel.setVisible(true); isValid = false;}
                else checkQuantityLabel.setVisible(false);

                if(isValid) {
                    supplierController.addSupplierProduct(productNameField.getText(), manufacturerNameField.getText(), Integer.parseInt(barcodeField.getText()), supplierNum
                            , Float.parseFloat(priceField.getText()), supplierCatalogField.getText(), Integer.parseInt(quantityField.getText()));

                    HelperFunctionGUI.ShowAddSuccess();

                    productNameField.setText("");
                    manufacturerNameField.setText("");
                    barcodeField.setText("");
                    supplierCatalogField.setText("");
                    priceField.setText("");
                    quantityField.setText("");

                    page3Frame.dispose();
                    backFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();

                }
            }});

        updateProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(productNameField.getText().equals("")) {checkProdNameLabel.setVisible(true); isValid = false;}
                else checkProdNameLabel.setVisible(false);

                if(manufacturerNameField.getText().equals("")) {checkManuNameLabel.setVisible(true); isValid = false;}
                else checkManuNameLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckIntInput(barcodeField.getText())) {checkBarcodeLabel.setVisible(true); isValid = false;}
                else checkBarcodeLabel.setVisible(false);

                if(supplierCatalogField.getText().equals("") || !supplierController.checkIfSupplierSupplyProduct(supplierCatalogField.getText(), supplierNum)) {
                    checkCatalogLabel.setText("Empty or Not exist");
                    checkCatalogLabel.setVisible(true);
                    isValid = false;
                }
                else checkCatalogLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckFloatInput(priceField.getText())) {checkPriceLabel.setVisible(true); isValid = false;}
                else checkPriceLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckIntInput(quantityField.getText())) {checkQuantityLabel.setVisible(true); isValid = false;}
                else checkQuantityLabel.setVisible(false);

                if(isValid) {
                    supplierController.deleteProductFromSupplier(supplierCatalogField.getText(), supplierNum);

                    supplierController.addSupplierProduct(productNameField.getText(), manufacturerNameField.getText(), Integer.parseInt(barcodeField.getText()), supplierNum
                            , Float.parseFloat(priceField.getText()), supplierCatalogField.getText(), Integer.parseInt(quantityField.getText()));

                    HelperFunctionGUI.ShowAddSuccess();

                    productNameField.setText("");
                    manufacturerNameField.setText("");
                    barcodeField.setText("");
                    supplierCatalogField.setText("");
                    priceField.setText("");
                    quantityField.setText("");

                    page3Frame.dispose();
                    backFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();

                }
            }});

        comboBoxSupplierCatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String deleteProduct = (String) combo.getSelectedItem();
                deleteProductButton.setVisible(!deleteProduct.equals(""));
            }});

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supplierController.deleteProductFromSupplier(comboBoxSupplierCatalog.getSelectedItem().toString(), supplierNum);
                page3Frame.dispose();
                backFrame.setVisible(true);
                HelperFunctionGUI.ShowProcessSuccessfully();
            }
        });

        page3Frame.setVisible(true);
    }

    public static void AddDeleteProductDiscountPage(String supplierNum, JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page4Frame = HelperFunctionGUI.createNewFrame("Update Supplier Agreement");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel discountProductLabel = new JLabel("Choose product that supplier provide any discounts?");

        JLabel minimumQuantityLabel = new JLabel("Minimum quantity for discount?");
        JLabel checkMinimumQuantityLabel = HelperFunctionGUI.createCheckLabel("Not Valid or Exist", 300, 50, 120, 20);

        JLabel percentLabel = new JLabel("How many percent off?");
        JLabel checkPercentLabel = HelperFunctionGUI.createCheckLabel("Not Valid", 300, 90, 80, 20);

        JLabel buffer = new JLabel("-------------------------------------------------------------------------------------------------------------------------------------------");
        JLabel deleteProductDiscountLabel = new JLabel("Choose a product and minimum amount to delete");

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField minimumQuantityField = new JTextField();
        JTextField PercentField = new JTextField();

        //------------------------------------ Create comboBox ---------------------------------------

        JComboBox<String> comboBoxSupplierCatalog1 = HelperFunctionGUI.createComboBoxSupplierProduct(supplierNum);
        JComboBox<String> comboBoxSupplierCatalog2 = HelperFunctionGUI.createComboBoxSupplierProduct(supplierNum);
        JComboBox<String> comboBoxProductDiscount = new JComboBox<>();

        //----------------------------------------- Create JButton ----------------------------------------

        JButton addProductDiscountButton = new JButton("Add Product Discount");
        JButton deleteProductDiscountButton = new JButton("Delete Product Discount");

        //-------------------------------------- Set bounds ---------------------------------------------

        discountProductLabel.setBounds(10, 10, 330, 20);
        comboBoxSupplierCatalog1.setBounds(370, 10, 50, 20);


        minimumQuantityLabel.setBounds(10, 50, 200, 20);
        minimumQuantityField.setBounds(240, 50, 50, 20);

        percentLabel.setBounds(10, 90, 200, 20);
        PercentField.setBounds(240, 90, 50, 20);

        addProductDiscountButton.setBounds(175,130,200,30);

        buffer.setBounds(0, 170, 500, 10);

        deleteProductDiscountLabel.setBounds(10, 210, 330, 20);
        comboBoxSupplierCatalog2.setBounds(350, 210, 60, 20);
        comboBoxProductDiscount.setBounds(430, 210, 60, 20);
        deleteProductDiscountButton.setBounds(175, 240, 200, 30);

        //-------------------------------------- Set not visible ---------------------------------------------
        minimumQuantityLabel.setVisible(false);
        minimumQuantityField.setVisible(false);

        percentLabel.setVisible(false);
        PercentField.setVisible(false);

        addProductDiscountButton.setVisible(false);

        comboBoxProductDiscount.setVisible(false);
        deleteProductDiscountButton.setVisible(false);


        //------------------------------------ Add to currFrame -------------------------------------

        JComponent [] components = {discountProductLabel, comboBoxSupplierCatalog1,
                minimumQuantityLabel, minimumQuantityField, checkMinimumQuantityLabel,
                percentLabel, PercentField, checkPercentLabel,
                addProductDiscountButton, buffer, deleteProductDiscountLabel, comboBoxSupplierCatalog2,
                comboBoxProductDiscount, deleteProductDiscountButton};

        HelperFunctionGUI.addComponentsToFrame(page4Frame, components);

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxSupplierCatalog1.addActionListener(new ActionListener() {
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
                    addProductDiscountButton.setVisible(false);
                }

                else {
                    percentLabel.setVisible(true);
                    PercentField.setVisible(true);
                    minimumQuantityLabel.setVisible(true);
                    minimumQuantityField.setVisible(true);
                    checkMinimumQuantityLabel.setVisible(false);
                    checkPercentLabel.setVisible(false);
                    addProductDiscountButton.setVisible(true);
                }
            }});

        //********************************************************************8

        addProductDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isValid = true;

                if(!HelperFunctionGUI.CheckIntInput(minimumQuantityField.getText())) { checkMinimumQuantityLabel.setVisible(true); isValid = false;}
                else checkMinimumQuantityLabel.setVisible(false);

                if(!HelperFunctionGUI.CheckFloatInput(PercentField.getText())) { checkPercentLabel.setVisible(true); isValid = false;}
                else checkPercentLabel.setVisible(false);

                if(isValid) {
                    if (!supplierController.addSupplierProductDiscount(comboBoxSupplierCatalog1.getSelectedItem().toString(), Float.parseFloat(PercentField.getText()), Integer.parseInt(minimumQuantityField.getText()), supplierNum))
                        checkMinimumQuantityLabel.setVisible(true);

                    else {
                        page4Frame.dispose();
                        backFrame.setVisible(true);
                        HelperFunctionGUI.ShowProcessSuccessfully();
                    }
                }
            }});

        comboBoxSupplierCatalog2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String catalog = comboBoxSupplierCatalog2.getSelectedItem().toString();

                if(catalog.equals(""))
                {
                    comboBoxProductDiscount.setVisible(false);
                    deleteProductDiscountButton.setVisible(false);
                }
                else {
                    HelperFunctionGUI.createComboBoxSupplierProductDiscount(comboBoxProductDiscount, supplierNum, catalog);
                    comboBoxProductDiscount.setVisible(true);
                    deleteProductDiscountButton.setVisible(true);
                }
            }
        });
        deleteProductDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBoxProductDiscount.getSelectedItem().equals("")) {
                    supplierController.deleteSupplierProductDiscount(supplierNum, comboBoxSupplierCatalog2.getSelectedItem().toString(), Integer.parseInt(comboBoxProductDiscount.getSelectedItem().toString()));
                    page4Frame.dispose();
                    backFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });

        page4Frame.setVisible(true);
    }

    public static void AddDeleteOrderDiscountPage(String supplierNum, JFrame backFrame) {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page5Frame = HelperFunctionGUI.createNewFrame("Update Supplier Agreement");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel PorQLabel = new JLabel("Do the discount is for minimum price or for minimum quantity? (p/q)");

        JLabel minimumLabel = new JLabel("Minimum quantity/price for discount?");
        JLabel checkMinimumLabel = HelperFunctionGUI.createCheckLabel("Not Valid or Exist", 350, 50, 130, 20);

        JLabel PercentLabel = new JLabel("How many percent off?");
        JLabel checkPercentLabel = HelperFunctionGUI.createCheckLabel("Not Valid", 350, 80, 100, 20);

        JLabel buffer = new JLabel("-------------------------------------------------------------------------------------------------------------------------------------------");
        JLabel deleteOrderDiscountLabel = new JLabel("Choose a p/q and amount to delete");

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField minimumField = new JTextField();
        JTextField PercentField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxPorQ = new JComboBox<>(new String[]{"", "p", "q"});
        JComboBox<String> comboBoxOrderDiscount = HelperFunctionGUI.createComboBoxOrderDiscount(supplierNum);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton addDiscountButton = new JButton("Add Discount");
        JButton deleteOrderDiscountButton = new JButton("DeleteDiscount");

        //-------------------------------------- Set bounds ---------------------------------------------

        PorQLabel.setBounds(10, 10, 400, 20);
        comboBoxPorQ.setBounds(430, 10, 50, 20);

        minimumLabel.setBounds(10, 50, 250, 20);
        minimumField.setBounds(290, 50, 50, 20);

        PercentLabel.setBounds(10, 80, 250, 20);
        PercentField.setBounds(290, 80, 50, 20);

        addDiscountButton.setBounds(210, 120, 150, 30);

        buffer.setBounds(0,180, 500, 10);
        deleteOrderDiscountLabel.setBounds(10, 210, 230, 20);
        comboBoxOrderDiscount.setBounds(240, 210, 70, 20);
        deleteOrderDiscountButton.setBounds(340, 210, 130, 30);


        //-------------------------------------- Set not visible ---------------------------------------------

        minimumLabel.setVisible(false);
        minimumField.setVisible(false);

        PercentLabel.setVisible(false);
        PercentField.setVisible(false);

        addDiscountButton.setVisible(false);


        //------------------------------------ Add to currFrame -------------------------------------

        JComponent[] components = {PorQLabel, comboBoxPorQ,
                minimumLabel, minimumField, checkMinimumLabel,
                PercentLabel, PercentField, checkPercentLabel,
                addDiscountButton, buffer, deleteOrderDiscountLabel, comboBoxOrderDiscount, deleteOrderDiscountButton};

        HelperFunctionGUI.addComponentsToFrame(page5Frame, components);

        // ------------------------------------- Add action listener to JObjects ------------------------------
        comboBoxPorQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = comboBoxPorQ.getSelectedItem().toString();

                if (choose.equals("")) {
                    PercentLabel.setVisible(false);
                    PercentField.setVisible(false);
                    checkPercentLabel.setVisible(false);
                    minimumLabel.setVisible(false);
                    minimumField.setVisible(false);
                    checkMinimumLabel.setVisible(false);
                    addDiscountButton.setVisible(false);
                } else {
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

                if (!HelperFunctionGUI.CheckIntInput(minimumField.getText()) || supplierController.CheckIfExistOrderDiscount(supplierNum, comboBoxPorQ.getSelectedItem().toString(), Integer.parseInt(minimumField.getText()))) {
                    checkMinimumLabel.setVisible(true);
                    isValid = false;
                } else checkMinimumLabel.setVisible(false);

                if (!HelperFunctionGUI.CheckFloatInput(PercentField.getText())) {
                    checkPercentLabel.setVisible(true);
                    isValid = false;
                } else checkPercentLabel.setVisible(false);

                if (isValid) {
                    supplierController.addOrderDiscount(supplierNum, comboBoxPorQ.getSelectedItem().toString(), Integer.parseInt(minimumField.getText()), Float.parseFloat(PercentField.getText()));
                    page5Frame.dispose();
                    HelperFunctionGUI.ShowProcessSuccessfully();
                    backFrame.setVisible(true);
                }
            }
        });

        deleteOrderDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String discount = comboBoxOrderDiscount.getSelectedItem().toString();
                if(!comboBoxOrderDiscount.getSelectedItem().equals("")) {
                    String[] QorPAndAmount = discount.split(",");

                    supplierController.deleteOrderDiscount(supplierNum, QorPAndAmount[0], Integer.parseInt(QorPAndAmount[1]));
                    page5Frame.dispose();
                    backFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });

        page5Frame.setVisible(true);
    }
}
