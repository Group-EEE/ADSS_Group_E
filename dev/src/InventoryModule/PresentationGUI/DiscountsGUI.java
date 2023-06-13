package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.ProductController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;

import static InventoryModule.Business.Discount.*;

public class DiscountsGUI {

    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame) {

        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame discountFrame = HelperFunctionGUI.createNewFrame("Discounts");

        //----------------------------------------- Create JLabel ----------------------------------------
        //option 1 - Update discount for category in store
        JLabel chooseLabel = new JLabel("Please choose an option");

        JLabel categoryLabel = new JLabel("Choose Category");
        JLabel discountLabel = new JLabel("Enter discount amount");
        JLabel expDateLabel = new JLabel("Enter exp Date");
        JLabel startDateLabel = new JLabel("Enter start Date");
        JLabel checkDiscountLabel = HelperFunctionGUI.createCheckLabel("Invalid value", 250, 70, 100, 20);
        JLabel checkDateLabel = HelperFunctionGUI.createCheckLabel("Invalid ExpDate", 380, 130, 100, 20);

        //option 2 - Update discount for product in store
        JLabel productsName = new JLabel("Choose Product's name");
        JLabel discountLabel2 = new JLabel("Enter discount amount");
        JLabel expDateLabel2 = new JLabel("Enter exp Date");
        JLabel startDateLabel2 = new JLabel("Enter start Date");
        JLabel checkDiscountLabel2 = HelperFunctionGUI.createCheckLabel("Invalid value", 250, 70, 100, 20);
        JLabel checkDateLabel2 = HelperFunctionGUI.createCheckLabel("Invalid ExpDate", 380, 130, 100, 20);

        //option 3 - Update discount for specific product in store
        JLabel productsName3 = new JLabel("Choose Product's name");
        JLabel productsId3 = new JLabel("Choose Product's id:");
        JLabel discountLabel3 = new JLabel("Enter discount amount");
        JLabel expDateLabel3 = new JLabel("Enter exp Date");
        JLabel startDateLabel3 = new JLabel("Enter start Date");
        JLabel checkDiscountLabel3 = HelperFunctionGUI.createCheckLabel("Invalid value", 250, 100, 100, 20);
        JLabel checkDateLabel3 = HelperFunctionGUI.createCheckLabel("Invalid ExpDate", 380, 160, 100, 20);

        //option 4 - Update store price
        JLabel productsBarcode4 = new JLabel("Choose Product's Barcode");
        JLabel newPrice = new JLabel("Enter Product's new price");
        JLabel checkDiscountLabel4 = HelperFunctionGUI.createCheckLabel("Invalid value", 250, 70, 100, 20);
        //----------------------------------------- Create JTextField ----------------------------------------

        //option 1 - Update discount for category in store
        JTextField discountField = new JTextField();

        //option 2 - Update discount for product in store
        JTextField discountField2 = new JTextField();

        //option 3 - Update discount for specific product in store
        JTextField discountField3 = new JTextField();

        //option 4 - Update store price
        JTextField discountField4 = new JTextField();
        //----------------------------------------- Create JComboBox ----------------------------------------

        //option 1
        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Update discount for category in store",
                "Update discount for product in store", "Update discount for specific product in store"
                , "Update store price"});


        JComboBox<String> categoriesComboBox = HelperFunctionGUI.createComboBoxCategories();

        JComboBox<String> dayComboBoxStart = new JComboBox<>();
        JComboBox<String> dayComboBoxExp = new JComboBox<>();

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


        JComboBox<String> monthComboBoxStart = new JComboBox<>();
        JComboBox<String> monthComboBoxExp = new JComboBox<>();

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


        JComboBox<String> yearComboBoxStart = new JComboBox<>();
        JComboBox<String> yearComboBoxExp = new JComboBox<>();

        yearComboBoxStart.addItem("");
        yearComboBoxExp.addItem("");
        for (int i = 2022; i <= 2025; i++) {
            yearComboBoxStart.addItem(String.valueOf(i));
            yearComboBoxExp.addItem(String.valueOf(i));
        }

        //option 2
        JComboBox<String> produtNameComboBox = HelperFunctionGUI.createComboBoxProductName();

        JComboBox<String> dayComboBoxStart2 = new JComboBox<>();
        JComboBox<String> dayComboBoxExp2 = new JComboBox<>();

        dayComboBoxStart2.addItem("");
        dayComboBoxExp2.addItem("");
        for (int i = 1; i <= 31; i++) {
            if (i < 10) {
                dayComboBoxStart2.addItem("0" + i);
                dayComboBoxExp2.addItem("0" + i);
            } else {
                dayComboBoxStart2.addItem(String.valueOf(i));
                dayComboBoxExp2.addItem(String.valueOf(i));
            }
        }


        JComboBox<String> monthComboBoxStart2 = new JComboBox<>();
        JComboBox<String> monthComboBoxExp2 = new JComboBox<>();

        monthComboBoxStart2.addItem("");
        monthComboBoxExp2.addItem("");
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                monthComboBoxStart2.addItem("0" + i);
                monthComboBoxExp2.addItem("0" + i);
            } else {
                monthComboBoxStart2.addItem(String.valueOf(i));
                monthComboBoxExp2.addItem(String.valueOf(i));
            }
        }


        JComboBox<String> yearComboBoxStart2 = new JComboBox<>();
        JComboBox<String> yearComboBoxExp2 = new JComboBox<>();

        yearComboBoxStart2.addItem("");
        yearComboBoxExp2.addItem("");
        for (int i = 2022; i <= 2025; i++) {
            yearComboBoxStart2.addItem(String.valueOf(i));
            yearComboBoxExp2.addItem(String.valueOf(i));
        }

        //option 3
        JComboBox<String> productNameComboBox3 = HelperFunctionGUI.createComboBoxProductName();
        JComboBox<String> productIDComboBox3 = new JComboBox<>();
        JComboBox<String> dayComboBoxStart3 = new JComboBox<>();
        JComboBox<String> dayComboBoxExp3 = new JComboBox<>();

        dayComboBoxStart3.addItem("");
        dayComboBoxExp3.addItem("");
        for (int i = 1; i <= 31; i++) {
            if (i < 10) {
                dayComboBoxStart3.addItem("0" + i);
                dayComboBoxExp3.addItem("0" + i);
            } else {
                dayComboBoxStart3.addItem(String.valueOf(i));
                dayComboBoxExp3.addItem(String.valueOf(i));
            }
        }


        JComboBox<String> monthComboBoxStart3 = new JComboBox<>();
        JComboBox<String> monthComboBoxExp3 = new JComboBox<>();

        monthComboBoxStart3.addItem("");
        monthComboBoxExp3.addItem("");
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                monthComboBoxStart3.addItem("0" + i);
                monthComboBoxExp3.addItem("0" + i);
            } else {
                monthComboBoxStart3.addItem(String.valueOf(i));
                monthComboBoxExp3.addItem(String.valueOf(i));
            }
        }

        JComboBox<String> yearComboBoxStart3 = new JComboBox<>();
        JComboBox<String> yearComboBoxExp3 = new JComboBox<>();

        yearComboBoxStart3.addItem("");
        yearComboBoxExp3.addItem("");
        for (int i = 2022; i <= 2025; i++) {
            yearComboBoxStart3.addItem(String.valueOf(i));
            yearComboBoxExp3.addItem(String.valueOf(i));
        }

        //option 4
        JComboBox<String> productBarcodeComboBox = HelperFunctionGUI.createComboBoxProductBarcode();

        //----------------------------------------- Create JButton ----------------------------------------

        //option 1
        JButton addDiscountToCategory = new JButton("Submit");

        //option 2
        JButton addDiscountToProduct = new JButton("Submit");

        //option 3
        JButton addDiscountToSpecificProduct = new JButton("Submit");

        //option 4
        JButton changeProductPrice = new JButton("Submit");

        JButton exitButton = HelperFunctionGUI.createExitButton(discountFrame, OldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        //option 1 - Update discount for category in store
        chooseLabel.setBounds(10, 10, 150, 20);
        chooseComboBox.setBounds(170, 10, 270, 20);

        categoryLabel.setBounds(10, 40, 150, 20);
        categoriesComboBox.setBounds(180, 40, 50, 20);

        discountLabel.setBounds(10, 70, 150, 20);
        discountField.setBounds(180, 70, 50, 20);

        startDateLabel.setBounds(10, 100, 150, 20);
        dayComboBoxStart.setBounds(180, 100, 50, 20);
        monthComboBoxStart.setBounds(250, 100, 50, 20);
        yearComboBoxStart.setBounds(320, 100, 50, 20);

        expDateLabel.setBounds(10, 130, 150, 20);
        dayComboBoxExp.setBounds(180, 130, 50, 20);
        monthComboBoxExp.setBounds(250, 130, 50, 20);
        yearComboBoxExp.setBounds(320, 130, 50, 20);


        addDiscountToCategory.setBounds(200, 360, 100, 40);

        //option 2 - Update discount for product in store
        productsName.setBounds(10, 40, 150, 20);
        produtNameComboBox.setBounds(180, 40, 150, 20);

        discountLabel2.setBounds(10, 70, 150, 20);
        discountField2.setBounds(180, 70, 50, 20);

        startDateLabel2.setBounds(10, 100, 150, 20);
        dayComboBoxStart2.setBounds(180, 100, 50, 20);
        monthComboBoxStart2.setBounds(250, 100, 50, 20);
        yearComboBoxStart2.setBounds(320, 100, 50, 20);

        expDateLabel2.setBounds(10, 130, 150, 20);
        dayComboBoxExp2.setBounds(180, 130, 50, 20);
        monthComboBoxExp2.setBounds(250, 130, 50, 20);
        yearComboBoxExp2.setBounds(320, 130, 50, 20);

        addDiscountToProduct.setBounds(200, 360, 100, 40);

        //option 3 - Update discount for specific product in store

        productsName3.setBounds(10, 40, 150, 20);
        productNameComboBox3.setBounds(180, 40, 150, 20);

        productsId3.setBounds(10, 70, 150, 20);
        productIDComboBox3.setBounds(180, 70, 50, 20);

        discountLabel3.setBounds(10, 100, 150, 20);
        discountField3.setBounds(180, 100, 50, 20);

        startDateLabel3.setBounds(10, 130, 150, 20);
        dayComboBoxStart3.setBounds(180, 130, 50, 20);
        monthComboBoxStart3.setBounds(250, 130, 50, 20);
        yearComboBoxStart3.setBounds(320, 130, 50, 20);

        expDateLabel3.setBounds(10, 160, 150, 20);
        dayComboBoxExp3.setBounds(180, 160, 50, 20);
        monthComboBoxExp3.setBounds(250, 160, 50, 20);
        yearComboBoxExp3.setBounds(320, 160, 50, 20);

        addDiscountToSpecificProduct.setBounds(200, 360, 100, 40);

        //option 4
        productsBarcode4.setBounds(10, 40, 160, 20);
        productBarcodeComboBox.setBounds(180, 40, 150, 20);

        newPrice.setBounds(10, 70, 150, 20);
        discountField4.setBounds(180, 70, 50, 20);

        changeProductPrice.setBounds(200, 360, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        //option 1
        JComponent[] JComponentsUpdateCategory = new JComponent[]{categoryLabel, categoriesComboBox,
                discountLabel, discountField, startDateLabel, dayComboBoxStart,
                monthComboBoxStart, yearComboBoxStart, expDateLabel, dayComboBoxExp, monthComboBoxExp,
                yearComboBoxExp, addDiscountToCategory};

        HelperFunctionGUI.hideComponents(JComponentsUpdateCategory);

        //option 2
        JComponent[] JComponentUpdateProduct = new JComponent[]{productsName, produtNameComboBox, discountLabel2,
                discountField2, startDateLabel2, dayComboBoxStart2, monthComboBoxStart2, yearComboBoxStart2,
                expDateLabel2, dayComboBoxExp2, monthComboBoxExp2, yearComboBoxExp2, addDiscountToProduct};

        HelperFunctionGUI.hideComponents(JComponentUpdateProduct);

        //option 3
        JComponent[] JComponentUpdateSpecificProduct = new JComponent[]{productIDComboBox3, productNameComboBox3,
                discountField3, productsName3 ,productsId3, discountLabel3, expDateLabel3, startDateLabel3,
                addDiscountToSpecificProduct, dayComboBoxStart3,
                monthComboBoxStart3, yearComboBoxStart3, dayComboBoxExp3, monthComboBoxExp3, yearComboBoxExp3};

        HelperFunctionGUI.hideComponents(JComponentUpdateSpecificProduct);

        //option 4
        JComponent[] JComponentUpdateProductPrice = new JComponent[]{productsBarcode4, productBarcodeComboBox,
                newPrice, discountField4, changeProductPrice};

        HelperFunctionGUI.hideComponents(JComponentUpdateProductPrice);


        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(discountFrame, new JComponent[]{chooseLabel, chooseComboBox,
                categoryLabel, categoriesComboBox, discountLabel, discountField, checkDiscountLabel,
                startDateLabel, dayComboBoxStart, monthComboBoxStart, yearComboBoxStart, expDateLabel,
                dayComboBoxExp, monthComboBoxExp, yearComboBoxExp, checkDateLabel, addDiscountToCategory,
                exitButton, produtNameComboBox, discountLabel2, productsName,
                discountField2, startDateLabel2, dayComboBoxStart2, monthComboBoxStart2, yearComboBoxStart2,
                expDateLabel2, dayComboBoxExp2, monthComboBoxExp2, yearComboBoxExp2, addDiscountToProduct,
                checkDiscountLabel2, checkDateLabel2, discountField3, productsName3 ,productsId3,
                discountLabel3, expDateLabel3, startDateLabel3,checkDiscountLabel3, checkDateLabel3,
                addDiscountToSpecificProduct, dayComboBoxStart3, monthComboBoxStart3, yearComboBoxStart3,
                dayComboBoxExp3, monthComboBoxExp3, yearComboBoxExp3, productIDComboBox3, productNameComboBox3,
                checkDiscountLabel3, checkDateLabel3, productsBarcode4, productBarcodeComboBox,
                newPrice, discountField4, changeProductPrice, checkDiscountLabel4 });

        // ------------------------------------- Add action listener to JObjects ------------------------------

        //option 1
        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseComboBox.getSelectedItem().toString();

                if (choose.equals("")) {
                    checkDiscountLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDiscountLabel2.setVisible(false);
                    checkDateLabel2.setVisible(false);
                    checkDiscountLabel3.setVisible(false);
                    checkDateLabel3.setVisible(false);
                    checkDiscountLabel4.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsUpdateCategory);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProduct);
                    HelperFunctionGUI.hideComponents(JComponentUpdateSpecificProduct);                    HelperFunctionGUI.hideComponents(JComponentUpdateSpecificProduct);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProductPrice);

                }
                //
                if (choose.equals("Update discount for category in store")) {
                    checkDiscountLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDiscountLabel2.setVisible(false);
                    checkDateLabel2.setVisible(false);
                    checkDiscountLabel3.setVisible(false);
                    checkDateLabel3.setVisible(false);
                    checkDiscountLabel4.setVisible(false);
                    HelperFunctionGUI.showComponents(JComponentsUpdateCategory);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProduct);
                    HelperFunctionGUI.hideComponents(JComponentUpdateSpecificProduct);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProductPrice);

                }
                if (choose.equals("Update discount for product in store")) {
                    checkDiscountLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDiscountLabel2.setVisible(false);
                    checkDateLabel2.setVisible(false);
                    checkDiscountLabel3.setVisible(false);
                    checkDateLabel3.setVisible(false);
                    checkDiscountLabel4.setVisible(false);
                    HelperFunctionGUI.showComponents(JComponentUpdateProduct);
                    HelperFunctionGUI.hideComponents(JComponentsUpdateCategory);
                    HelperFunctionGUI.hideComponents(JComponentUpdateSpecificProduct);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProductPrice);

                }
                if (choose.equals("Update discount for specific product in store")) {
                    checkDiscountLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDiscountLabel2.setVisible(false);
                    checkDateLabel2.setVisible(false);
                    checkDiscountLabel3.setVisible(false);
                    checkDateLabel3.setVisible(false);
                    checkDiscountLabel4.setVisible(false);
                    HelperFunctionGUI.showComponents(JComponentUpdateSpecificProduct);
                    HelperFunctionGUI.hideComponents(JComponentsUpdateCategory);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProduct);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProductPrice);
                }
                if(choose.equals("Update store price")){
                    checkDiscountLabel.setVisible(false);
                    checkDateLabel.setVisible(false);
                    checkDiscountLabel2.setVisible(false);
                    checkDateLabel2.setVisible(false);
                    checkDiscountLabel3.setVisible(false);
                    checkDateLabel3.setVisible(false);
                    checkDiscountLabel4.setVisible(false);
                    HelperFunctionGUI.showComponents(JComponentUpdateProductPrice);
                    HelperFunctionGUI.hideComponents(JComponentUpdateSpecificProduct);
                    HelperFunctionGUI.hideComponents(JComponentsUpdateCategory);
                    HelperFunctionGUI.hideComponents(JComponentUpdateProduct);
                }
            }
        });


        //*************************************************************************************

        addDiscountToCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String category = categoriesComboBox.getSelectedItem().toString();
                double discount = 0;

                LocalDateTime startDate;
                LocalDateTime ExpDate;


                if (category.equals(""))
                    isValid = false;

                if (!HelperFunctionGUI.CheckDoubleInput(discountField.getText())) {
                    checkDiscountLabel.setVisible(true);
                    isValid = false;
                } else {
                    checkDiscountLabel.setVisible(false);
                    discount = Double.parseDouble(discountField.getText());
                }

                if (dayComboBoxStart.getSelectedItem().toString().equals("") ||
                        monthComboBoxStart.getSelectedItem().toString().equals("") ||
                        yearComboBoxStart.getSelectedItem().toString().equals("")) {
                    isValid = false;
                    return;
                } else
                    startDate = LocalDateTime.parse(yearComboBoxStart.getSelectedItem().toString() + "-" +
                            monthComboBoxStart.getSelectedItem().toString() + "-" +
                            dayComboBoxStart.getSelectedItem().toString() + "T00:00:00");


                if (dayComboBoxExp.getSelectedItem().toString().equals("") ||
                        monthComboBoxExp.getSelectedItem().toString().equals("") ||
                        yearComboBoxExp.getSelectedItem().toString().equals("")) {
                    isValid = false;
                    return;
                } else
                    ExpDate = LocalDateTime.parse(yearComboBoxExp.getSelectedItem().toString() + "-" +
                            monthComboBoxExp.getSelectedItem().toString() + "-" +
                            dayComboBoxExp.getSelectedItem().toString() + "T00:00:00");


                if (ExpDate.isBefore(startDate)) {
                    checkDateLabel.setVisible(true);
                    isValid = false;
                } else
                    checkDateLabel.setVisible(false);

                if (isValid) {
                    update_discount_bycategory(category, startDate, ExpDate, discount);
                    discountFrame.dispose();
                    OldFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });

        addDiscountToProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String productName = produtNameComboBox.getSelectedItem().toString();
                double discount = 0;

                LocalDateTime startDate;
                LocalDateTime ExpDate;


                if (productName.equals(""))
                    isValid = false;

                if (!HelperFunctionGUI.CheckDoubleInput(discountField2.getText())) {
                    checkDiscountLabel2.setVisible(true);
                    isValid = false;
                } else {
                    checkDiscountLabel2.setVisible(false);
                    discount = Double.parseDouble(discountField2.getText());
                }

                if (dayComboBoxStart2.getSelectedItem().toString().equals("") ||
                        monthComboBoxStart2.getSelectedItem().toString().equals("") ||
                        yearComboBoxStart2.getSelectedItem().toString().equals("")) {
                    isValid = false;
                    return;
                } else
                    startDate = LocalDateTime.parse(yearComboBoxStart2.getSelectedItem().toString() + "-" +
                            monthComboBoxStart2.getSelectedItem().toString() + "-" +
                            dayComboBoxStart2.getSelectedItem().toString() + "T00:00:00");


                if (dayComboBoxExp2.getSelectedItem().toString().equals("") ||
                        monthComboBoxExp2.getSelectedItem().toString().equals("") ||
                        yearComboBoxExp2.getSelectedItem().toString().equals("")) {
                    isValid = false;
                    return;
                } else
                    ExpDate = LocalDateTime.parse(yearComboBoxExp2.getSelectedItem().toString() + "-" +
                            monthComboBoxExp2.getSelectedItem().toString() + "-" +
                            dayComboBoxExp2.getSelectedItem().toString() + "T00:00:00");


                if (ExpDate.isBefore(startDate)) {
                    checkDateLabel2.setVisible(true);
                    isValid = false;
                } else
                    checkDateLabel2.setVisible(false);

                if (isValid) {
                    update_discount_byproduct(productName, startDate, ExpDate, discount);
                    discountFrame.dispose();
                    OldFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });

        //option 3
        productNameComboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productNameComboBox3.getSelectedItem().toString();
                HelperFunctionGUI.createSpecificProductIds(choose,productIDComboBox3);
            }
        });

        addDiscountToSpecificProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String productName = productNameComboBox3.getSelectedItem().toString();
                String productID = productIDComboBox3.getSelectedItem().toString();
                double discount = 0;

                LocalDateTime startDate;
                LocalDateTime ExpDate;


                if (productName.equals(""))
                    isValid = false;

                if (!HelperFunctionGUI.CheckDoubleInput(discountField3.getText())) {
                    checkDiscountLabel3.setVisible(true);
                    isValid = false;
                } else {
                    checkDiscountLabel3.setVisible(false);
                    discount = Double.parseDouble(discountField3.getText());
                }

                if (dayComboBoxStart3.getSelectedItem().toString().equals("") ||
                        monthComboBoxStart3.getSelectedItem().toString().equals("") ||
                        yearComboBoxStart3.getSelectedItem().toString().equals("")) {
                    isValid = false;
                    return;
                } else
                    startDate = LocalDateTime.parse(yearComboBoxStart3.getSelectedItem().toString() + "-" +
                            monthComboBoxStart3.getSelectedItem().toString() + "-" +
                            dayComboBoxStart3.getSelectedItem().toString() + "T00:00:00");


                if (dayComboBoxExp3.getSelectedItem().toString().equals("") ||
                        monthComboBoxExp3.getSelectedItem().toString().equals("") ||
                        yearComboBoxExp3.getSelectedItem().toString().equals("")) {
                    isValid = false;
                    return;
                } else
                    ExpDate = LocalDateTime.parse(yearComboBoxExp3.getSelectedItem().toString() + "-" +
                            monthComboBoxExp3.getSelectedItem().toString() + "-" +
                            dayComboBoxExp3.getSelectedItem().toString() + "T00:00:00");


                if (ExpDate.isBefore(startDate)) {
                    checkDateLabel3.setVisible(true);
                    isValid = false;
                } else
                    checkDateLabel3.setVisible(false);

                if (isValid) {
                    update_discount_byspecificproduct(productName ,Integer.parseInt(productID) , startDate, ExpDate, discount);
                    discountFrame.dispose();
                    OldFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });

        changeProductPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String productBarcode = productBarcodeComboBox.getSelectedItem().toString();
                double discount = 0;

                if (productBarcode.equals(""))
                    isValid = false;

                if (!HelperFunctionGUI.CheckDoubleInput(discountField4.getText())) {
                    checkDiscountLabel4.setVisible(true);
                    isValid = false;
                } else {
                    checkDiscountLabel4.setVisible(false);
                    discount = Double.parseDouble(discountField4.getText());
                }
                if (isValid) {
                    ProductController.getInstance().getProductByBarcode(Integer.parseInt(productBarcode)).setCostumer_Price(discount);
                    discountFrame.dispose();
                    OldFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });
        discountFrame.setVisible(true);
    }

}
