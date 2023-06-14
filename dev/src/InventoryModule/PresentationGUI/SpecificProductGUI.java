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
        JLabel supplierPriceLable = new JLabel("Enter Suppliers Product price:"); //combo box of all supplier according to barcode
        JLabel expDateLabel = new JLabel("Enter exp Date");
        JLabel defectiveproductLabel = new JLabel("Is the product defective?");//true/false combo
        JLabel defectReporterNameLabel = new JLabel("enter Defect reporter name else enter null");
        JLabel defectTypeLabel = new JLabel("Please enter Defect type else press null");
        JLabel StoredProductLabel = new JLabel("Is the product stored in warehouse?"); //true/false combo
        JLabel StoreBranchNameLabel  = new JLabel("Please choose store branch name:"); //combobox storebranch
        JLabel ProductShelfNumberLabel  = new JLabel("Please enter product Shelf number:");
        JLabel discountAmountLabel = new JLabel("Please enter discount amount:");
        JLabel startDateDiscountLabel = new JLabel("Select discount start Date:"); //combo and what about null
        JLabel endDateDiscountLabel = new JLabel("Select discount and Date:"); //combo and what about null

        //----------------------------------------- Create JTextField ----------------------------------------
        //option 1
        JTextField SupplierName = new JTextField();


        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Add new specific product to store",
                "Remove specific product from store", "Report defected specific product", "Find specific product in store",
        "Change specific product place in store", "Transfer specific product from/to warehouse"});

        //option 1
        JComboBox<String> productBarcodeComboBox = HelperFunctionGUI.createComboBoxProductBarcode();

        JComboBox<String> dayComboBoxExp = new JComboBox<>();

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
        SupplierName.setBounds(170, 70, 270, 20);





        //-------------------------------------- Set not visible ---------------------------------------------

        //------------------------------------ Add to currFrame -------------------------------------

        // ------------------------------------- Add action listener to JObjects ------------------------------
        productBarcodeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        SpecificProductFrame.setVisible(true);
    }

    public void createDayComboBox(JComboBox<String> dayComboBoxStart, JComboBox<String> dayComboBoxExp) {
        dayComboBoxStart.addItem("");
        dayComboBoxExp.addItem("");
        for (int i = 1; i <= 31; i++) {
            if (i < 10) {
                dayComboBoxStart.addItem("0" + i);
                dayComboBoxExp.addItem("0" + i);
            } else {
                dayComboBoxStart.addItem(String.valueOf(i));
                dayComboBoxExp.addItem(String.valueOf(i));
            }
        }
    }

    public void createMonthComboBox(JComboBox<String> monthComboBoxStart, JComboBox<String> monthComboBoxExp) {
        monthComboBoxStart.addItem("");
        monthComboBoxExp.addItem("");
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                monthComboBoxStart.addItem("0" + i);
                monthComboBoxExp.addItem("0" + i);
            } else {
                monthComboBoxStart.addItem(String.valueOf(i));
                monthComboBoxExp.addItem(String.valueOf(i));
            }
        }
    }

    public void createYearComboBox(JComboBox<String> yearComboBoxStart, JComboBox<String> yearComboBoxExp){
        yearComboBoxStart.addItem("");
        yearComboBoxExp.addItem("");
        for (int i = 2022; i <= 2025; i++) {
            yearComboBoxStart.addItem(String.valueOf(i));
            yearComboBoxExp.addItem(String.valueOf(i));
        }
    }
}
