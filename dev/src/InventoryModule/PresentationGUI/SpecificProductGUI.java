package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.SuperLiProduct;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class SpecificProductGUI {
    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        //------------------------------------- Create new frame -------------------------------------------
        JFrame SpecificProductFrame = HelperFunctionGUI.createNewFrame("SpecificProduct");
        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel chooseLabel = new JLabel("Please choose an option");

        //option 1 - Add new product to the store
        JLabel productBarcodeLabel = new JLabel("Choose Product");
        JLabel supplierLabel = new JLabel("Select Suppliers Product"); //combo box of all supplier according to barcode
        JLabel supplierPriceLable = new JLabel("Enter Suppliers price");
        JLabel expDateLabel = new JLabel("Enter exp Date");
        JLabel defectiveproductLabel = new JLabel("Is the product defective?");//true/false combo
        JLabel defectReporterNameLabel = new JLabel("Enter Defect reporter name/null");
        JLabel defectTypeLabel = new JLabel("Enter Defect type/null");
        JLabel StoredProductLabel = new JLabel("Stored in warehouse"); //true/false combo
        JLabel StoreBranchNameLabel  = new JLabel("Choose store branch name"); //combobox storebranch
        JLabel ProductShelfNumberLabel  = new JLabel("Enter product Shelf number");
        JLabel discountAmountLabel = new JLabel("Enter discount amount");
        JLabel startDateDiscountLabel = new JLabel("Select discount start Date"); //combo and what about null
        JLabel endDateDiscountLabel = new JLabel("Select discount end Date"); //combo and what about null
        JLabel doesProducthasDiscount = new JLabel("Has Discount");

        JLabel checkDiscountLabel = HelperFunctionGUI.createCheckLabel("Invalid Value",250, 333, 90,20);
        JLabel checkDateLabel = HelperFunctionGUI.createCheckLabel("Invalid Value", 390,390, 90,20);
        JLabel checkDefectedLabel = HelperFunctionGUI.createCheckLabel("Invalid Value", 250, 183, 90,20);
        JLabel checkSelectedBranch = HelperFunctionGUI.createCheckLabel("Select Value", 250, 273, 90,20);
        JLabel checkLocationInStoreLabel = HelperFunctionGUI.createCheckLabel("Invalid Value", 250, 303, 90,20);
        JLabel checkSupplierLabel = HelperFunctionGUI.createCheckLabel("Select Value",300, 65, 270, 20);
        JLabel checkSupplierPriceLabel = HelperFunctionGUI.createCheckLabel("Invalid Value",250, 93, 90,20);

        //option2 - Remove specific product from store
        JLabel productBarcodeLabel2 = new JLabel("Choose Product");
        JLabel productsId2 = new JLabel("Choose Product's id:");

        //----------------------------------------- Create JTextField ----------------------------------------
        //option 1
        JTextField supplierPriceField = new JTextField();
        JTextField defectReporterNameField = new JTextField();
        JTextField defectTypeField = new JTextField();
        JTextField ProductShelfNumberField = new JTextField();
        JTextField discountAmountField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Add new specific product to store",
                "Remove specific product from store", "Report defected specific product", "Find specific product in store",
        "Change specific product place in store", "Transfer specific product from/to warehouse"});

        //option 1
        JComboBox<String> productBarcodeComboBox = HelperFunctionGUI.createComboBoxProductBarcode();
        JComboBox<String> SupplierNameComboBox = new JComboBox<>();

        JComboBox<String> EXPDayComboBox = new JComboBox<>();
        JComboBox<String> EXPMonthComboBox = new JComboBox<>();
        JComboBox<String> EXPYearComboBox = new JComboBox<>();

        createDayComboBox(EXPDayComboBox);
        createMonthComboBox(EXPMonthComboBox);
        createYearComboBox(EXPYearComboBox);

        JComboBox<String> defectiveproductComboBox = new JComboBox<>();
        createBooleanComboBox(defectiveproductComboBox);

        JComboBox<String> StoredProductComboBox = new JComboBox<>();
        createBooleanComboBox(StoredProductComboBox);

        JComboBox<String> StoreBrunchComboBox = new JComboBox<>();
        StoreBrunchComboBox.addItem("");
        StoreBrunchComboBox.addItem("SuperLi Rehovot");

        JComboBox<String> startDateDayDiscountComboBox = new JComboBox<>();
        JComboBox<String> startDateMonthDiscountComboBox = new JComboBox<>();
        JComboBox<String> startDateYearDiscountComboBox = new JComboBox<>();

        createDayComboBox(startDateDayDiscountComboBox);
        createMonthComboBox(startDateMonthDiscountComboBox);
        createYearComboBox(startDateYearDiscountComboBox);

        JComboBox<String> endDateDayDiscountComboBox = new JComboBox<>();
        JComboBox<String> endDateMonthDiscountComboBox = new JComboBox<>();
        JComboBox<String> endDateYearDiscountComboBox = new JComboBox<>();

        createDayComboBox(endDateDayDiscountComboBox);
        createMonthComboBox(endDateMonthDiscountComboBox);
        createYearComboBox(endDateYearDiscountComboBox);

        JComboBox<String> HasDiscountComboBox = new JComboBox<>();
        HasDiscountComboBox.addItem("");
        HasDiscountComboBox.addItem("true");
        HasDiscountComboBox.addItem("false");

        //option 2

        JComboBox<String> productBarcodeComboBox2 = HelperFunctionGUI.createComboBoxProductBarcode();
        JComboBox<String> specificProductID2ComboBox = new JComboBox<>();

        //----------------------------------------- Create JButton ----------------------------------------
        //option 1
        JButton AddNewProduct = new JButton("Submit");

        //option2
        JButton RemoveProduct = new JButton("Submit");

        JButton exitButton = HelperFunctionGUI.createExitButton(SpecificProductFrame, OldFrame);

        //----------------------------------------- Set bounds ---------------------------------------------

        chooseLabel.setBounds(10, 10, 150, 20);
        chooseComboBox.setBounds(190, 10, 270, 20);

        //option 1
        productBarcodeLabel.setBounds(10,33, 150,20);
        productBarcodeComboBox.setBounds(190,35,270,20);

        supplierLabel.setBounds(10, 63, 150,20);
        SupplierNameComboBox.setBounds(190, 65, 100, 20);

        supplierPriceLable.setBounds(10, 93, 160,20);
        supplierPriceField.setBounds(190, 93, 50,20);

        expDateLabel.setBounds(10, 123, 150,20);
        EXPDayComboBox.setBounds(190, 123, 50,20);
        EXPMonthComboBox.setBounds(250,123,50,20);
        EXPYearComboBox.setBounds(320, 123, 50, 20);

        defectiveproductLabel.setBounds(10, 153, 150,20);
        defectiveproductComboBox.setBounds(190, 153, 50, 20);

        defectReporterNameLabel.setBounds(10, 183, 200,20);
        defectReporterNameField.setBounds(190, 183, 50,20);

        defectTypeLabel.setBounds(10, 213, 150,20);
        defectTypeField.setBounds(190, 213, 50,20);

        StoredProductLabel.setBounds(10, 243, 200,20);
        StoredProductComboBox.setBounds(190, 243, 50,20);

        StoreBranchNameLabel.setBounds(10, 273, 200,20);
        StoreBrunchComboBox.setBounds(190, 273, 50,20);

        ProductShelfNumberLabel.setBounds(10, 303, 200,20);
        ProductShelfNumberField.setBounds(190, 303, 50,20);

        discountAmountLabel.setBounds(10, 333, 150,20);
        discountAmountField.setBounds(190, 333, 50,20);
        doesProducthasDiscount.setBounds(330, 333, 90,20);
        HasDiscountComboBox.setBounds(410,333, 70,20);

        startDateDiscountLabel.setBounds(10, 363, 150,20);
        startDateDayDiscountComboBox.setBounds(190, 363, 50,20);
        startDateMonthDiscountComboBox.setBounds(250,363, 50,20);
        startDateYearDiscountComboBox.setBounds(320,363, 50,20);

        endDateDiscountLabel.setBounds(10, 390, 150,20);
        endDateDayDiscountComboBox.setBounds(190, 390, 50,20);
        endDateMonthDiscountComboBox.setBounds(250,390, 50,20);
        endDateYearDiscountComboBox.setBounds(320,390, 50,20);
        AddNewProduct.setBounds(310, 410, 100, 40);

        //option 2
        productBarcodeLabel2.setBounds(10,40, 150,20);
        productBarcodeComboBox2.setBounds(190,40,270,20);

        productsId2.setBounds(10, 70, 150,20);
        specificProductID2ComboBox.setBounds(190,70,270,20);

        //-------------------------------------- Set not visible ---------------------------------------------

        //option 1
        JComponent[] JComponentsAddProduct = new JComponent[]{ productBarcodeLabel,
                supplierLabel,supplierPriceLable,expDateLabel,defectiveproductLabel,
                defectReporterNameLabel,defectTypeLabel,StoredProductLabel,StoreBranchNameLabel,
                ProductShelfNumberLabel, discountAmountLabel,startDateDiscountLabel,
                endDateDiscountLabel,supplierPriceField,defectReporterNameField,
                defectTypeField,ProductShelfNumberField,discountAmountField, productBarcodeComboBox,
                SupplierNameComboBox, EXPDayComboBox,EXPMonthComboBox,EXPYearComboBox,
                defectiveproductComboBox,StoredProductComboBox,StoreBrunchComboBox,
                startDateDayDiscountComboBox,startDateMonthDiscountComboBox,
                startDateYearDiscountComboBox, endDateDayDiscountComboBox,
                endDateMonthDiscountComboBox,endDateYearDiscountComboBox, AddNewProduct,
                doesProducthasDiscount, HasDiscountComboBox};
        HelperFunctionGUI.hideComponents(JComponentsAddProduct);

        //option 2
        JComponent[] JComponentsRemoveProduct = new JComponent[]{productBarcodeLabel2,productBarcodeComboBox2,
                productsId2,specificProductID2ComboBox, RemoveProduct};
        HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);


        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(SpecificProductFrame, new JComponent[]{chooseLabel,
                chooseComboBox, productBarcodeLabel,
                supplierLabel,supplierPriceLable,expDateLabel,defectiveproductLabel,
                defectReporterNameLabel,defectTypeLabel,StoredProductLabel,StoreBranchNameLabel,
                ProductShelfNumberLabel, discountAmountLabel,startDateDiscountLabel,
                endDateDiscountLabel,supplierPriceField,defectReporterNameField,
                defectTypeField,ProductShelfNumberField,discountAmountField, productBarcodeComboBox,
                SupplierNameComboBox, EXPDayComboBox,EXPMonthComboBox,EXPYearComboBox,
                defectiveproductComboBox,StoredProductComboBox,StoreBrunchComboBox,
                startDateDayDiscountComboBox,startDateMonthDiscountComboBox,
                startDateYearDiscountComboBox, endDateDayDiscountComboBox,
                endDateMonthDiscountComboBox,endDateYearDiscountComboBox, AddNewProduct, exitButton,
                checkDiscountLabel, checkDateLabel, checkDefectedLabel,checkLocationInStoreLabel,
                checkSelectedBranch,checkSupplierLabel,checkSupplierPriceLabel,doesProducthasDiscount,
                HasDiscountComboBox, productBarcodeLabel2,productBarcodeComboBox2,
                productsId2,specificProductID2ComboBox, RemoveProduct
        });

        // ------------------------------------- Add action listener to JObjects ------------------------------
        productBarcodeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox.getSelectedItem().toString();
                if(!choose.equals(""))
                    HelperFunctionGUI.setSupplierComboBoxField(choose, SupplierNameComboBox);
                else{
                    SupplierNameComboBox.removeAllItems();
                }
            }
        });
        SpecificProductFrame.setVisible(true);

        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseComboBox.getSelectedItem().toString();

                if (choose.equals("")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);

                }
                if (choose.equals("Add new specific product to store")) {
                    HelperFunctionGUI.showComponents(JComponentsAddProduct);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);

                }
                if (choose.equals("Remove specific product from store")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    HelperFunctionGUI.showComponents(JComponentsRemoveProduct);



                }
                if (choose.equals("Report defected specific product")) {

                }
                if (choose.equals("Find specific product in store")) {

                }
                if (choose.equals("Change specific product place in store")) {

                }
                if (choose.equals("Transfer specific product from/to warehouse")) {

                }
            }
        });

        AddNewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                int Barcode = 0;
                LocalDateTime startDate = null;
                LocalDateTime endDate = null;
                LocalDateTime ExpDate = null;
                if (productBarcodeComboBox.getSelectedItem().toString().equals("")) {
                    checkSupplierLabel.setVisible(true);
                    checkDiscountLabel.setVisible(true);
                    checkSupplierPriceLabel.setVisible(true);
                    checkDateLabel.setVisible(true);
                    checkDefectedLabel.setVisible(true);
                    checkSelectedBranch.setVisible(true);
                    checkLocationInStoreLabel.setVisible(true);
                    isValid = false;
                }
                else {
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    Barcode = Integer.parseInt(productBarcodeComboBox.getSelectedItem().toString());

                    String suppliernum ="";
                    if(SupplierNameComboBox.getSelectedItem().equals("")) {
                        checkSupplierLabel.setVisible(true);
                        isValid = false;
                    }else{
                        checkSupplierLabel.setVisible(false);
                        suppliernum = SupplierNameComboBox.getSelectedItem().toString();
                    }

                    double discount = 0;
                    if (!HelperFunctionGUI.CheckDoubleInput(discountAmountField.getText())) {
                        if(discount!=0.0){
                            checkDiscountLabel.setVisible(true);
                            isValid = false;
                        }
                    } else {
                        checkDiscountLabel.setVisible(false);
                        discount = Double.parseDouble(discountAmountField.getText());
                    }

                    double supplierPrice =0;
                    if(!HelperFunctionGUI.CheckDoubleInput(supplierPriceField.getText())){
                        checkSupplierPriceLabel.setVisible(true);
                        isValid = false;
                    }
                    else{
                        checkSupplierPriceLabel.setVisible(false);
                        supplierPrice = Double.parseDouble(supplierPriceField.getText());
                    }

                    if(HasDiscountComboBox.getSelectedItem().equals("false")){
                        startDate = LocalDateTime.parse("1111-11-11T00:00:00");
                        endDate = LocalDateTime.parse("1111-11-11T00:00:00");
                        discount = 0;
                    }
                    else if(HasDiscountComboBox.getSelectedItem().equals("true")){
                        if (startDateDayDiscountComboBox.getSelectedItem().toString().equals("") ||
                                startDateMonthDiscountComboBox.getSelectedItem().toString().equals("") ||
                                startDateYearDiscountComboBox.getSelectedItem().toString().equals("")) {
                            checkDateLabel.setVisible(true);
                            isValid = false;
                            return;
                        } else {
                            startDate = LocalDateTime.parse(startDateYearDiscountComboBox.getSelectedItem().toString() + "-" +
                                    startDateMonthDiscountComboBox.getSelectedItem().toString() + "-" +
                                    startDateDayDiscountComboBox.getSelectedItem().toString() + "T00:00:00");
                            checkDateLabel.setVisible(false);
                        }

                        if (endDateDayDiscountComboBox.getSelectedItem().toString().equals("") ||
                                endDateMonthDiscountComboBox.getSelectedItem().toString().equals("") ||
                                endDateYearDiscountComboBox.getSelectedItem().toString().equals("")) {
                            isValid = false;
                            checkDateLabel.setVisible(true);
                            return;
                        }
                        else {
                            endDate = LocalDateTime.parse(endDateYearDiscountComboBox.getSelectedItem().toString() + "-" +
                                    endDateMonthDiscountComboBox.getSelectedItem().toString() + "-" +
                                    endDateDayDiscountComboBox.getSelectedItem().toString() + "T00:00:00");
                            checkDateLabel.setVisible(false);
                        }
                    }
                    else if(HasDiscountComboBox.getSelectedItem().equals("")){
                        checkDiscountLabel.setVisible(true);
                        isValid = false;
                    }

                    if (EXPDayComboBox.getSelectedItem().toString().equals("") ||
                        EXPMonthComboBox.getSelectedItem().toString().equals("") ||
                        EXPYearComboBox.getSelectedItem().toString().equals("")) {
                        isValid = false;
                        checkDateLabel.setVisible(true);
                        //return;
                    }
                    else {
                        ExpDate = LocalDateTime.parse(EXPYearComboBox.getSelectedItem().toString() + "-" +
                                EXPMonthComboBox.getSelectedItem().toString() + "-" +
                                EXPDayComboBox.getSelectedItem().toString() + "T00:00:00");
                        checkDateLabel.setVisible(false);
                    }
                    if(!(startDateDayDiscountComboBox.getSelectedItem().toString().equals("") ||
                            startDateMonthDiscountComboBox.getSelectedItem().toString().equals("") ||
                            startDateYearDiscountComboBox.getSelectedItem().toString().equals("")||
                            endDateDayDiscountComboBox.getSelectedItem().toString().equals("") ||
                            endDateMonthDiscountComboBox.getSelectedItem().toString().equals("") ||
                            endDateYearDiscountComboBox.getSelectedItem().toString().equals("")||
                            EXPDayComboBox.getSelectedItem().toString().equals("") ||
                            EXPMonthComboBox.getSelectedItem().toString().equals("") ||
                            EXPYearComboBox.getSelectedItem().toString().equals(""))
                    ){
                        if (endDate.isBefore(startDate) || endDate.isAfter(ExpDate) || startDate.isAfter(ExpDate)) {
                            checkDateLabel.setVisible(true);
                            isValid = false;
                        } else {
                            checkDateLabel.setVisible(false);
                        }
                    }
                    else{
                        checkDateLabel.setVisible(true);
                        isValid = false;
                    }

                    String aDefect_report_by = "";
                    boolean aDefective = false;
                    if(defectiveproductComboBox.getSelectedItem().equals("")){
                        checkDefectedLabel.setVisible(true);
                        isValid = false;
                    }
                    else {
                        aDefective = Boolean.parseBoolean(defectiveproductComboBox.getSelectedItem().toString());
                        aDefect_report_by = defectReporterNameLabel.getText();
                        if (aDefective == true && aDefect_report_by.equals("")) {
                            checkDefectedLabel.setVisible(true);
                            isValid = false;
                        } else
                            checkDefectedLabel.setVisible(false);
                    }

                    String defectype =null;
                    defectype = defectTypeField.getText();
                    boolean aInWarehouse = Boolean.parseBoolean(StoredProductComboBox.getSelectedItem().toString());
                    String aStoreBranch = StoreBrunchComboBox.getSelectedItem().toString();
                    if (aStoreBranch.equals("")) {
                        checkSelectedBranch.setVisible(true);
                        isValid = false;
                    } else
                        checkSelectedBranch.setVisible(false);

                    int aLocationInStore =0;
                    if (!HelperFunctionGUI.CheckIntInput(ProductShelfNumberField.getText())) {
                        checkLocationInStoreLabel.setVisible(true);
                        isValid = false;
                    } else {
                        checkLocationInStoreLabel.setVisible(false);
                        aLocationInStore = Integer.parseInt(ProductShelfNumberField.getText());
                    }
                    if(aInWarehouse){
                        aLocationInStore = -1;
                    }
                    if (isValid) {
                        ProductController.getInstance().addspecificproduct(suppliernum, supplierPrice, Barcode, ExpDate, aDefective, aDefect_report_by,
                                aInWarehouse, aStoreBranch, aLocationInStore, startDate, endDate, discount, defectype);
                        SpecificProductFrame.dispose();
                        OldFrame.setVisible(true);
                        HelperFunctionGUI.ShowProcessSuccessfully();
                    }
                }
        }
        });
        //option 2
        productBarcodeComboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox2.getSelectedItem().toString();
                if(!choose.equals(""))
                    HelperFunctionGUI.setSpecifcProductIDComboBoxField(choose, specificProductID2ComboBox);
            }
        });

        RemoveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!specificProductID2ComboBox.getSelectedItem().equals("")){
                    SuperLiProduct s = ProductController.getInstance().getProductByBarcode(Integer.parseInt(productBarcodeComboBox2.getSelectedItem().toString()));
                    String specific = specificProductID2ComboBox.getSelectedItem().toString();
                    s.removeSpecificProduct(Integer.parseInt(specific));
                    SpecificProductFrame.dispose();
                    OldFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });
    }

    public static void createDayComboBox(JComboBox<String> dayComboBox) {
        dayComboBox.addItem("");
        for (int i = 1; i <= 31; i++) {
            if (i < 10) {
                dayComboBox.addItem("0" + i);
            } else {
                dayComboBox.addItem(String.valueOf(i));
            }
        }
    }

    public static void createMonthComboBox(JComboBox<String> monthComboBox) {
        monthComboBox.addItem("");
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                monthComboBox.addItem("0" + i);
            } else {
                monthComboBox.addItem(String.valueOf(i));
            }
        }
    }

    public static void createYearComboBox(JComboBox<String> yearComboBox){
        yearComboBox.addItem("");
        for (int i = 2022; i <= 2025; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }
    }

    public static void createBooleanComboBox(JComboBox<String> BooleanComboBox){
        BooleanComboBox.addItem("");
        BooleanComboBox.addItem("true");
        BooleanComboBox.addItem("false");
    }
}
