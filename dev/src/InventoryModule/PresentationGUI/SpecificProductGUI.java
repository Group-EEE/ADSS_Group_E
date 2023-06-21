package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.SpecificProduct;
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

        JLabel checkDiscountLabel = HelperFunctionGUI.createCheckLabel("Invalid Value",270, 333, 90,20);
        JLabel checkDateLabel = HelperFunctionGUI.createCheckLabel("Invalid Value", 400,390, 90,20);
        JLabel checkDefectedLabel = HelperFunctionGUI.createCheckLabel("Invalid Value", 270, 183, 90,20);
        JLabel checkSelectedBranch = HelperFunctionGUI.createCheckLabel("Select Value", 270, 273, 90,20);
        JLabel checkLocationInStoreLabel = HelperFunctionGUI.createCheckLabel("Invalid Value", 270, 303, 90,20);
        JLabel checkSupplierLabel = HelperFunctionGUI.createCheckLabel("Select Value",320, 65, 270, 20);
        JLabel checkSupplierPriceLabel = HelperFunctionGUI.createCheckLabel("Invalid Value",270, 93, 90,20);

        //option2 - Remove specific product from store
        JLabel productBarcodeLabel2 = new JLabel("Choose Product");
        JLabel productsId2 = new JLabel("Choose Product's id:");
        JLabel checkIDLabel2 = HelperFunctionGUI.createCheckLabel("Choose Id",350,70,270,20);
        JLabel checkNoavaliableProductLabel2 = HelperFunctionGUI.createCheckLabel("Missing Product",350,70,270,20);
        JLabel checkBarcodeLabel2 = HelperFunctionGUI.createCheckLabel("Choose Barcode",350,40,270,20);

        //option 3 - Report defected specific product
        JLabel productBarcodeLabel3 = new JLabel("Choose Product");
        JLabel productsId3 = new JLabel("Choose Product's id:");
        JLabel checkIDLabel3 = HelperFunctionGUI.createCheckLabel("Choose Id",350,70,270,20);
        JLabel checkNoavaliableProductLabel3 = HelperFunctionGUI.createCheckLabel("Missing Product",350,70,270,20);
        JLabel checkBarcodeLabel3 = HelperFunctionGUI.createCheckLabel("Choose Barcode",350,40,270,20);

        JLabel defectReporterName3 = new JLabel("Enter Reporter Name:");
        JLabel checkReporterName3 = HelperFunctionGUI.createCheckLabel("Enter Name!",350,100,270,20);

        JLabel defectTypeName3 = new JLabel("Enter Defect Type:");
        JLabel checkTypeName3 = HelperFunctionGUI.createCheckLabel("Enter Defect Type!",350,130,270,20);

        //option 4
        JLabel productBarcodeLabel4 = new JLabel("Choose Product");
        JLabel productsId4 = new JLabel("Choose Product's id:");
        JLabel checkIDLabel4 = HelperFunctionGUI.createCheckLabel("Choose Id",370,70,270,20);
        JLabel checkNoavaliableProductLabel4 = HelperFunctionGUI.createCheckLabel("Missing Product",370,70,270,20);
        JLabel checkBarcodeLabel4 = HelperFunctionGUI.createCheckLabel("Choose Barcode",370,40,270,20);
        JLabel productLocationInStore = new JLabel("Product location in store is:");

        //option 5
        JLabel productBarcodeLabel5 = new JLabel("Choose Product");
        JLabel productsId5 = new JLabel("Choose Product's id:");
        JLabel checkIDLabel5 = HelperFunctionGUI.createCheckLabel("Choose Id",350,70,270,20);
        JLabel checkNoavaliableProductLabel5 = HelperFunctionGUI.createCheckLabel("Missing Product",350,70,270,20);
        JLabel checkBarcodeLabel5 = HelperFunctionGUI.createCheckLabel("Choose Barcode",350,40,270,20);
        JLabel newShelfNumber5 = new JLabel("Choose Shelf Number");
        JLabel checkShelf = HelperFunctionGUI.createCheckLabel("Choose Shelf Number",350,100,180,20);

        //option 6
        JLabel productBarcodeLabel6 = new JLabel("Choose Product");
        JLabel productsId6 = new JLabel("Choose Product's id:");
        JLabel checkIDLabel6 = HelperFunctionGUI.createCheckLabel("Choose Id",370,70,270,20);
        JLabel checkNoavaliableProductLabel6 = HelperFunctionGUI.createCheckLabel("Missing Product",370,70,270,20);
        JLabel checkBarcodeLabel6 = HelperFunctionGUI.createCheckLabel("Choose Barcode",370,40,270,20);
        JLabel productLocationInStore6 = new JLabel("Product location is:");

        //----------------------------------------- Create JTextField ----------------------------------------
        //option 1
        JTextField supplierPriceField = new JTextField();
        JTextField defectReporterNameField = new JTextField();
        JTextField defectTypeField = new JTextField();
        JTextField ProductShelfNumberField = new JTextField();
        JTextField discountAmountField = new JTextField();

        //option 3
        JTextField ReporterName3 = new JTextField();
        JTextField DefectType3 = new JTextField();

        //option 4
        JTextField ProductLocation4 = new JTextField();

        //option 6
        JTextField ProductLocation6 = new JTextField();

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

        //option 3
        JComboBox<String> productBarcodeComboBox3= HelperFunctionGUI.createComboBoxProductBarcode();
        JComboBox<String> specificProductIDComboBox3 = new JComboBox<>();

        //option 4
        JComboBox<String> productBarcodeComboBox4 = HelperFunctionGUI.createComboBoxProductBarcode();
        JComboBox<String> specificProductIDComboBox4 = new JComboBox<>();

        //option 5
        JComboBox<String> productBarcodeComboBox5 = HelperFunctionGUI.createComboBoxProductBarcode();
        JComboBox<String> specificProductIDComboBox5 = new JComboBox<>();
        JComboBox<String> shelfNumberComboBox5 = new JComboBox<>();
        shelfNumberComboBox5.addItem("");
        for(int i=1; i<101; i++){
            shelfNumberComboBox5.addItem(Integer.toString(i));
        }

        //option 6
        JComboBox<String> productBarcodeComboBox6 = HelperFunctionGUI.createComboBoxProductBarcode();
        JComboBox<String> specificProductIDComboBox6 = new JComboBox<>();

        //----------------------------------------- Create JButton ----------------------------------------
        //option 1
        JButton AddNewProduct = new JButton("Submit");

        //option2
        JButton RemoveProduct = new JButton("Submit");

        //option 3
        JButton ReportDefectedProduct = new JButton("Submit");

        //option 4
        JButton FindLocation = new JButton("Submit");

        //option 5
        JButton SetLocation = new JButton("Submit");

        //option 6
        JButton TransformLocation = new JButton("Submit");

        JButton exitButton = HelperFunctionGUI.createExitButton(SpecificProductFrame, OldFrame);

        //----------------------------------------- Set bounds ---------------------------------------------

        chooseLabel.setBounds(10, 10, 150, 20);
        chooseComboBox.setBounds(210, 10, 270, 20);

        //option 1
        productBarcodeLabel.setBounds(10,33, 150,20);
        productBarcodeComboBox.setBounds(210,35,270,20);

        supplierLabel.setBounds(10, 63, 150,20);
        SupplierNameComboBox.setBounds(210, 65, 100, 20);

        supplierPriceLable.setBounds(10, 93, 160,20);
        supplierPriceField.setBounds(210, 93, 50,20);

        expDateLabel.setBounds(10, 123, 150,20);
        EXPDayComboBox.setBounds(210, 123, 50,20);
        EXPMonthComboBox.setBounds(270,123,50,20);
        EXPYearComboBox.setBounds(340, 123, 50, 20);

        defectiveproductLabel.setBounds(10, 153, 150,20);
        defectiveproductComboBox.setBounds(210, 153, 50, 20);

        defectReporterNameLabel.setBounds(10, 183, 200,20);
        defectReporterNameField.setBounds(210, 183, 50,20);

        defectTypeLabel.setBounds(10, 213, 150,20);
        defectTypeField.setBounds(210, 213, 50,20);

        StoredProductLabel.setBounds(10, 243, 200,20);
        StoredProductComboBox.setBounds(210, 243, 50,20);

        StoreBranchNameLabel.setBounds(10, 273, 200,20);
        StoreBrunchComboBox.setBounds(210, 273, 50,20);

        ProductShelfNumberLabel.setBounds(10, 303, 200,20);
        ProductShelfNumberField.setBounds(210, 303, 50,20);

        discountAmountLabel.setBounds(10, 333, 150,20);
        discountAmountField.setBounds(210, 333, 50,20);
        doesProducthasDiscount.setBounds(350, 333, 90,20);
        HasDiscountComboBox.setBounds(430,333, 50,20);

        startDateDiscountLabel.setBounds(10, 363, 150,20);
        startDateDayDiscountComboBox.setBounds(210, 363, 50,20);
        startDateMonthDiscountComboBox.setBounds(270,363, 50,20);
        startDateYearDiscountComboBox.setBounds(340,363, 50,20);

        endDateDiscountLabel.setBounds(10, 390, 150,20);
        endDateDayDiscountComboBox.setBounds(210, 390, 50,20);
        endDateMonthDiscountComboBox.setBounds(270,390, 50,20);
        endDateYearDiscountComboBox.setBounds(340,390, 50,20);
        AddNewProduct.setBounds(310, 410, 100, 40);

        //option 2
        productBarcodeLabel2.setBounds(10,40, 150,20);
        productBarcodeComboBox2.setBounds(190,40,150,20);

        productsId2.setBounds(10, 70, 150,20);
        specificProductID2ComboBox.setBounds(190,70,150,20);
        RemoveProduct.setBounds(200, 360, 100, 40);

        //option 3

        productBarcodeLabel3.setBounds(10,40, 150,20);
        productBarcodeComboBox3.setBounds(190,40,150,20);

        productsId3.setBounds(10, 70, 150,20);
        specificProductIDComboBox3.setBounds(190,70,150,20);
        ReportDefectedProduct.setBounds(200, 360, 100, 40);

        defectReporterName3.setBounds(10, 100, 150,20);
        ReporterName3.setBounds(190, 100, 150,20);

        defectTypeName3.setBounds(10, 130, 150,20);
        DefectType3.setBounds(190, 130, 150,20);

        //option 4
        productBarcodeLabel4.setBounds(10,40, 150,20);
        productBarcodeComboBox4.setBounds(190,40,180,20);

        productsId4.setBounds(10, 70, 150,20);
        specificProductIDComboBox4.setBounds(190,70,180,20);

        productLocationInStore.setBounds(10, 100, 160,20);
        ProductLocation4.setBounds(190, 100, 230,20);

        FindLocation.setBounds(200, 360, 100, 40);

        //option 5

        productBarcodeLabel5.setBounds(10,40, 150,20);
        productBarcodeComboBox5.setBounds(190,40,150,20);

        productsId5.setBounds(10, 70, 150,20);
        specificProductIDComboBox5.setBounds(190,70,150,20);

        newShelfNumber5.setBounds(10, 100, 150,20);
        shelfNumberComboBox5.setBounds(190,100,150,20);

        SetLocation.setBounds(200, 360, 100, 40);

        //option 6
        productBarcodeLabel6.setBounds(10,40, 150,20);
        productBarcodeComboBox6.setBounds(190,40,180,20);

        productsId6.setBounds(10, 70, 150,20);
        specificProductIDComboBox6.setBounds(190,70,180,20);

        productLocationInStore6.setBounds(10, 100, 160,20);
        ProductLocation6.setBounds(190, 100, 250,20);

        TransformLocation.setBounds(200, 360, 100, 40);

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
                productsId2,specificProductID2ComboBox,RemoveProduct};
        HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);

        //option 3
        JComponent[] JComponentsDefectedProduct = new JComponent[]{productBarcodeLabel3,productsId3,
                defectReporterName3, defectTypeName3,ReporterName3, DefectType3,
                productBarcodeComboBox3,specificProductIDComboBox3,ReportDefectedProduct};
        HelperFunctionGUI.hideComponents(JComponentsDefectedProduct);

        //option 4
        JComponent[] JComponentsLocationInStore = new JComponent[]{productBarcodeComboBox4,specificProductIDComboBox4,
                productBarcodeLabel4, productsId4 ,productLocationInStore ,ProductLocation4 ,FindLocation};

        HelperFunctionGUI.hideComponents(JComponentsLocationInStore);

        //option 5
        JComponent[] JComponentsNewLocationInStore = new JComponent[]{productBarcodeLabel5, productsId5,
                newShelfNumber5, productBarcodeComboBox5, specificProductIDComboBox5,shelfNumberComboBox5,
                SetLocation};
        HelperFunctionGUI.hideComponents(JComponentsNewLocationInStore);

        //option 6
        JComponent[] JComponentsTransformLocationInStore = new JComponent[]{productBarcodeLabel6,
                productsId6, productLocationInStore6, ProductLocation6, productBarcodeComboBox6,
                specificProductIDComboBox6, TransformLocation};

        HelperFunctionGUI.hideComponents(JComponentsTransformLocationInStore);

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
                productsId2,specificProductID2ComboBox, RemoveProduct,checkIDLabel2, checkNoavaliableProductLabel2,
                checkBarcodeLabel2,productBarcodeLabel3,productsId3,checkIDLabel3,
                checkNoavaliableProductLabel3,checkBarcodeLabel3, defectReporterName3,checkReporterName3,
                defectTypeName3, checkTypeName3,ReporterName3, DefectType3,
                productBarcodeComboBox3,specificProductIDComboBox3,ReportDefectedProduct,
                productBarcodeComboBox4,specificProductIDComboBox4,
                productBarcodeLabel4, productsId4 ,productLocationInStore ,ProductLocation4 ,FindLocation,
                checkIDLabel4 ,checkNoavaliableProductLabel4 ,checkBarcodeLabel4,productBarcodeLabel5, productsId5, newShelfNumber5, checkIDLabel5, checkNoavaliableProductLabel5,
                checkBarcodeLabel5, productBarcodeComboBox5, specificProductIDComboBox5,shelfNumberComboBox5,
                SetLocation, checkShelf, productBarcodeLabel6, productsId6, checkIDLabel6, checkNoavaliableProductLabel6,
                checkBarcodeLabel6, productLocationInStore6, ProductLocation6, productBarcodeComboBox6,
                specificProductIDComboBox6, TransformLocation
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

        /*
        productBarcodeLabel5, productsId5, newShelfNumber5, checkIDLabel5, checkNoavaliableProductLabel5,
        checkBarcodeLabel5, productBarcodeComboBox5, specificProductIDComboBox5,shelfNumberComboBox5,
        SetLocation, checkShelf
         */
        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseComboBox.getSelectedItem().toString();

                if (choose.equals("")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);
                    HelperFunctionGUI.hideComponents(JComponentsDefectedProduct);
                    HelperFunctionGUI.hideComponents(JComponentsLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsNewLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsTransformLocationInStore);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkBarcodeLabel2.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkBarcodeLabel3.setVisible(false);
                    checkReporterName3.setVisible(false);
                    checkTypeName3.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkBarcodeLabel4.setVisible(false);
                    checkShelf.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkBarcodeLabel5.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkBarcodeLabel6.setVisible(false);
                }
                if (choose.equals("Add new specific product to store")) {
                    HelperFunctionGUI.showComponents(JComponentsAddProduct);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);
                    HelperFunctionGUI.hideComponents(JComponentsDefectedProduct);
                    HelperFunctionGUI.hideComponents(JComponentsLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsNewLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsTransformLocationInStore);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkBarcodeLabel2.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkBarcodeLabel3.setVisible(false);
                    checkReporterName3.setVisible(false);
                    checkTypeName3.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkBarcodeLabel4.setVisible(false);
                    checkShelf.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkBarcodeLabel5.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkBarcodeLabel6.setVisible(false);
                }
                if (choose.equals("Remove specific product from store")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    HelperFunctionGUI.showComponents(JComponentsRemoveProduct);
                    HelperFunctionGUI.hideComponents(JComponentsDefectedProduct);
                    HelperFunctionGUI.hideComponents(JComponentsLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsNewLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsTransformLocationInStore);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkBarcodeLabel2.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkBarcodeLabel3.setVisible(false);
                    checkReporterName3.setVisible(false);
                    checkTypeName3.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkBarcodeLabel4.setVisible(false);
                    checkShelf.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkBarcodeLabel5.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkBarcodeLabel6.setVisible(false);
                }
                if (choose.equals("Report defected specific product")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);
                    HelperFunctionGUI.showComponents(JComponentsDefectedProduct);
                    HelperFunctionGUI.hideComponents(JComponentsLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsNewLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsTransformLocationInStore);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkBarcodeLabel2.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkBarcodeLabel3.setVisible(false);
                    checkReporterName3.setVisible(false);
                    checkTypeName3.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkBarcodeLabel4.setVisible(false);
                    checkShelf.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkBarcodeLabel5.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkBarcodeLabel6.setVisible(false);
                }
                if (choose.equals("Find specific product in store")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);
                    HelperFunctionGUI.hideComponents(JComponentsDefectedProduct);
                    HelperFunctionGUI.showComponents(JComponentsLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsNewLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsTransformLocationInStore);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkBarcodeLabel2.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkBarcodeLabel3.setVisible(false);
                    checkReporterName3.setVisible(false);
                    checkTypeName3.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkBarcodeLabel4.setVisible(false);
                    checkShelf.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkBarcodeLabel5.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkBarcodeLabel6.setVisible(false);
                }
                if (choose.equals("Change specific product place in store")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);
                    HelperFunctionGUI.hideComponents(JComponentsDefectedProduct);
                    HelperFunctionGUI.hideComponents(JComponentsLocationInStore);
                    HelperFunctionGUI.showComponents(JComponentsNewLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsTransformLocationInStore);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkBarcodeLabel2.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkBarcodeLabel3.setVisible(false);
                    checkReporterName3.setVisible(false);
                    checkTypeName3.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkBarcodeLabel4.setVisible(false);
                    checkShelf.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkBarcodeLabel5.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkBarcodeLabel6.setVisible(false);

                }
                if (choose.equals("Transfer specific product from/to warehouse")) {
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveProduct);
                    HelperFunctionGUI.hideComponents(JComponentsDefectedProduct);
                    HelperFunctionGUI.hideComponents(JComponentsLocationInStore);
                    HelperFunctionGUI.hideComponents(JComponentsNewLocationInStore);
                    HelperFunctionGUI.showComponents(JComponentsTransformLocationInStore);
                    checkSupplierLabel.setVisible(false);
                    checkDiscountLabel.setVisible(false);
                    checkSupplierPriceLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDefectedLabel.setVisible(false);
                    checkSelectedBranch.setVisible(false);
                    checkLocationInStoreLabel.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkBarcodeLabel2.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkBarcodeLabel3.setVisible(false);
                    checkReporterName3.setVisible(false);
                    checkTypeName3.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkBarcodeLabel4.setVisible(false);
                    checkShelf.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkBarcodeLabel5.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkBarcodeLabel6.setVisible(false);
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
                Boolean isValid = true;
                if(productBarcodeComboBox2.getSelectedItem().equals("")){
                    checkBarcodeLabel2.setVisible(true);
                    checkNoavaliableProductLabel2.setVisible(false);
                    checkIDLabel2.setVisible(false);
                    isValid = false;
                }
                else{
                    checkBarcodeLabel2.setVisible(false);
                    if(specificProductID2ComboBox.getSelectedItem().equals("")){
                        if(specificProductID2ComboBox.getItemCount()==1){
                            checkNoavaliableProductLabel2.setVisible(true);
                            checkIDLabel2.setVisible(false);
                            isValid = false;
                        }
                        else{
                            checkIDLabel2.setVisible(true);
                            checkNoavaliableProductLabel2.setVisible(false);
                            isValid = false;
                        }
                    }
                    else{
                        checkIDLabel2.setVisible(false);
                        checkNoavaliableProductLabel2.setVisible(false);
                        if(isValid){
                            SuperLiProduct s = ProductController.getInstance().getProductByBarcode(Integer.parseInt(productBarcodeComboBox2.getSelectedItem().toString()));
                            String specific = specificProductID2ComboBox.getSelectedItem().toString();
                            s.removeSpecificProduct(Integer.parseInt(specific));
                            SpecificProductFrame.dispose();
                            OldFrame.setVisible(true);
                            HelperFunctionGUI.ShowProcessSuccessfully();
                        }
                    }

                }
            }
        });

        //option 3
        productBarcodeComboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox3.getSelectedItem().toString();
                if(!choose.equals(""))
                    HelperFunctionGUI.setSpecifcProductIDComboBoxField(choose, specificProductIDComboBox3);
            }
        });

        ReportDefectedProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkNoavaliableProductLabel3.setVisible(false);
                checkIDLabel3.setVisible(false);
                checkBarcodeLabel3.setVisible(false);
                Boolean isValid = true;
                String reporterName = null;
                String defectType = null;
                if(productBarcodeComboBox3.getSelectedItem().equals("")){
                    checkBarcodeLabel3.setVisible(true);
                    checkNoavaliableProductLabel3.setVisible(false);
                    checkIDLabel3.setVisible(false);
                    isValid = false;
                }
                else{
                    checkBarcodeLabel3.setVisible(false);
                    if(specificProductIDComboBox3.getSelectedItem().equals("")){
                        if(specificProductIDComboBox3.getItemCount()==1){
                            checkNoavaliableProductLabel3.setVisible(true);
                            checkIDLabel3.setVisible(false);
                            isValid = false;
                        }
                        else{
                            checkIDLabel3.setVisible(true);
                            checkNoavaliableProductLabel3.setVisible(false);
                            isValid = false;
                        }
                    }
                    else{
                        checkIDLabel3.setVisible(false);
                        if(ReporterName3.getText().equals("")){
                            checkReporterName3.setVisible(true);
                            isValid = false;
                        }
                        else{
                            reporterName = ReporterName3.getText();
                            checkReporterName3.setVisible(false);

                        }
                        if(DefectType3.getText().equals("")){
                            checkTypeName3.setVisible(true);
                            isValid = false;
                        }
                        else{
                            defectType = DefectType3.getText();
                            checkTypeName3.setVisible(false);
                        }
                        if(isValid){
                            SuperLiProduct s = ProductController.getInstance().getProductByBarcode(Integer.parseInt(productBarcodeComboBox3.getSelectedItem().toString()));
                            String specific = specificProductIDComboBox3.getSelectedItem().toString();
                            s.add_defected_specific_product(Integer.parseInt(specific), reporterName, defectType);
                            SpecificProductFrame.dispose();
                            OldFrame.setVisible(true);
                            HelperFunctionGUI.ShowProcessSuccessfully();
                        }
                    }
                }
            }
        });

        //option 4
        productBarcodeComboBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox4.getSelectedItem().toString();
                if(!choose.equals(""))
                    HelperFunctionGUI.setSpecifcProductIDComboBoxField(choose, specificProductIDComboBox4);
            }
        });

        FindLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean isValid = true;
                if(productBarcodeComboBox4.getSelectedItem().equals("")){
                    checkBarcodeLabel4.setVisible(true);
                    checkNoavaliableProductLabel4.setVisible(false);
                    checkIDLabel4.setVisible(false);
                    isValid = false;
                }
                else{
                    checkBarcodeLabel4.setVisible(false);
                    if(specificProductIDComboBox4.getSelectedItem().equals("")){
                        if(specificProductIDComboBox4.getItemCount()==1){
                            checkNoavaliableProductLabel4.setVisible(true);
                            checkIDLabel4.setVisible(false);
                            isValid = false;
                        }
                        else{
                            checkIDLabel4.setVisible(true);
                            checkNoavaliableProductLabel4.setVisible(false);
                            isValid = false;
                        }
                    }
                    else{
                        checkIDLabel4.setVisible(false);
                        checkNoavaliableProductLabel4.setVisible(false);
                        if(isValid){
                            SuperLiProduct s = ProductController.getInstance().getProductByBarcode(Integer.parseInt(productBarcodeComboBox4.getSelectedItem().toString()));
                            String specific = specificProductIDComboBox4.getSelectedItem().toString();
                            ProductLocation4.setText(s.getProductLocationInStore(Integer.parseInt(specific)));
                        }
                    }

                }
            }
        });

        //option 5
        productBarcodeComboBox5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox5.getSelectedItem().toString();
                if(!choose.equals(""))
                    HelperFunctionGUI.setSpecifcProductIDComboBoxField(choose, specificProductIDComboBox5);
            }
        });

        SetLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean isValid = true;
                if(productBarcodeComboBox5.getSelectedItem().equals("")){
                    checkBarcodeLabel5.setVisible(true);
                    checkNoavaliableProductLabel5.setVisible(false);
                    checkIDLabel5.setVisible(false);
                    isValid = false;
                }
                else{
                    checkBarcodeLabel5.setVisible(false);
                    if(specificProductIDComboBox5.getSelectedItem().equals("")){
                        if(specificProductIDComboBox5.getItemCount()==1){
                            checkNoavaliableProductLabel5.setVisible(true);
                            checkIDLabel5.setVisible(false);
                            isValid = false;
                        }
                        else{
                            checkIDLabel5.setVisible(true);
                            checkNoavaliableProductLabel5.setVisible(false);
                            isValid = false;
                        }
                    }
                    else{
                        checkIDLabel5.setVisible(false);
                        if(shelfNumberComboBox5.getSelectedItem().equals("")){
                            isValid = false;
                            checkShelf.setVisible(true);
                        }
                        else{
                            checkIDLabel5.setVisible(false);
                            checkNoavaliableProductLabel5.setVisible(false);
                            if(isValid){
                                SuperLiProduct s = ProductController.getInstance().getProductByBarcode(Integer.parseInt(productBarcodeComboBox5.getSelectedItem().toString()));
                                String specific = specificProductIDComboBox5.getSelectedItem().toString();
                                SpecificProduct sp = s.getSpecificProduct(Integer.parseInt(specific));
                                sp.setLocation_in_Store(Integer.parseInt(shelfNumberComboBox5.getSelectedItem().toString()));
                                SpecificProductFrame.dispose();
                                OldFrame.setVisible(true);
                                HelperFunctionGUI.ShowProcessSuccessfully();
                            }
                        }
                    }

                }
            }
        });

        //option 6
        productBarcodeComboBox6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox6.getSelectedItem().toString();
                if(!choose.equals(""))
                    HelperFunctionGUI.setSpecifcProductIDComboBoxField(choose, specificProductIDComboBox6);
            }
        });

        TransformLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean isValid = true;
                if(productBarcodeComboBox6.getSelectedItem().equals("")){
                    checkBarcodeLabel6.setVisible(true);
                    checkNoavaliableProductLabel6.setVisible(false);
                    checkIDLabel6.setVisible(false);
                    isValid = false;
                }
                else{
                    checkBarcodeLabel6.setVisible(false);
                    if(specificProductIDComboBox6.getSelectedItem().equals("")){
                        if(specificProductIDComboBox6.getItemCount()==1){
                            checkNoavaliableProductLabel6.setVisible(true);
                            checkIDLabel6.setVisible(false);
                            isValid = false;
                        }
                        else{
                            checkIDLabel6.setVisible(true);
                            checkNoavaliableProductLabel6.setVisible(false);
                            isValid = false;
                        }
                    }
                    else{
                        checkIDLabel6.setVisible(false);
                        checkNoavaliableProductLabel6.setVisible(false);
                        if(isValid){
                            SuperLiProduct s = ProductController.getInstance().getProductByBarcode(Integer.parseInt(productBarcodeComboBox6.getSelectedItem().toString()));
                            String specific = specificProductIDComboBox6.getSelectedItem().toString();
                            ProductLocation6.setText(ProductController.getInstance().change_Shelf_Warehouse(Integer.parseInt(specific), s.getBarcode()));
                        }
                    }

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
