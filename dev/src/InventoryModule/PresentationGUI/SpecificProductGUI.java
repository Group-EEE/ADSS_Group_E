package InventoryModule.PresentationGUI;

import InventoryModule.Business.SuperLiProduct;
import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Scanner;

import static InventoryModule.PresentationCLI.StoreKeeperPresentationCLI.reader;

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
        JLabel supplierLabel = new JLabel("Select Suppliers Product:"); //combo box of all supplier according to barcode
        JLabel supplierPriceLable = new JLabel("Enter Suppliers Product price:");
        JLabel expDateLabel = new JLabel("Enter exp Date");
        JLabel defectiveproductLabel = new JLabel("Is the product defective?");//true/false combo
        JLabel defectReporterNameLabel = new JLabel("enter Defect reporter name else enter null");
        JLabel defectTypeLabel = new JLabel("Please enter Defect type else enter null");
        JLabel StoredProductLabel = new JLabel("Is the product stored in warehouse?"); //true/false combo
        JLabel StoreBranchNameLabel  = new JLabel("Please choose store branch name:"); //combobox storebranch
        JLabel ProductShelfNumberLabel  = new JLabel("Please enter product Shelf number:");
        JLabel discountAmountLabel = new JLabel("Please enter discount amount:");
        JLabel startDateDiscountLabel = new JLabel("Select discount start Date:"); //combo and what about null
        JLabel endDateDiscountLabel = new JLabel("Select discount and Date:"); //combo and what about null

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

        //----------------------------------------- Create JButton ----------------------------------------
        //option 1
        JButton AddNewProduct = new JButton("Submit");

        //----------------------------------------- Set bounds ---------------------------------------------

        chooseLabel.setBounds(10, 10, 150, 20);
        chooseComboBox.setBounds(170, 10, 270, 20);

        //option 1
        productBarcodeLabel.setBounds(10,40, 150,20);
        productBarcodeComboBox.setBounds(170,40,270,20);

        supplierLabel.setBounds(10, 70, 150,20);
        SupplierNameComboBox.setBounds(170, 70, 270, 20);

        supplierPriceLable.setBounds(10, 100, 150,20);
        supplierPriceField.setBounds(170, 100, 150,20);

        expDateLabel.setBounds(10, 130, 50,20);
        EXPDayComboBox.setBounds(180, 130, 50,20);
        EXPMonthComboBox.setBounds(250,130,50,20);
        EXPYearComboBox.setBounds(320, 130, 50, 20);

        defectiveproductLabel.setBounds(10, 160, 50,20);
        defectiveproductComboBox.setBounds(170, 160, 270, 20);

        defectReporterNameLabel.setBounds(10, 190, 50,20);
        defectReporterNameField.setBounds(170, 190, 50,20);

        defectTypeLabel.setBounds(10, 210, 50,20);
        defectTypeField.setBounds(170, 210, 50,20);

        StoredProductLabel.setBounds(10, 240, 50,20);
        StoredProductComboBox.setBounds(170, 240, 50,20);

        StoreBranchNameLabel.setBounds(10, 270, 50,20);
        StoreBrunchComboBox.setBounds(170, 270, 50,20);

        ProductShelfNumberLabel.setBounds(10, 300, 50,20);
        ProductShelfNumberField.setBounds(170, 300, 50,20);

        discountAmountLabel.setBounds(10, 330, 50,20);
        discountAmountField.setBounds(170, 330, 50,20);

        startDateDiscountLabel.setBounds(10, 360, 50,20);
        startDateDayDiscountComboBox.setBounds(180, 360, 50,20);
        startDateMonthDiscountComboBox.setBounds(250,360, 50,20);
        startDateYearDiscountComboBox.setBounds(320,360, 50,20);

        endDateDiscountLabel.setBounds(10, 390, 50,20);
        endDateDayDiscountComboBox.setBounds(180, 390, 50,20);
        endDateMonthDiscountComboBox.setBounds(250,390, 50,20);
        endDateYearDiscountComboBox.setBounds(320,390, 50,20);


        //-------------------------------------- Set not visible ---------------------------------------------

        //------------------------------------ Add to currFrame -------------------------------------

        // ------------------------------------- Add action listener to JObjects ------------------------------
        productBarcodeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox.getSelectedItem().toString();
                if(!choose.equals(""))
                    HelperFunctionGUI.setSupplierComboBoxField(choose, SupplierNameComboBox);
            }
        });
        SpecificProductFrame.setVisible(true);
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
