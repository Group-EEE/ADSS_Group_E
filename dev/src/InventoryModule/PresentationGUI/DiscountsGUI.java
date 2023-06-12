package InventoryModule.PresentationGUI;

import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import static InventoryModule.Business.Discount.update_discount_bycategory;

public class DiscountsGUI {

    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame) {

        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame discountFrame = HelperFunctionGUI.createNewFrame("Discounts");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel chooseLabel = new JLabel("Please choose an option");

        JLabel categoryLabel = new JLabel("Choose Category");
        JLabel discountLabel = new JLabel("Enter discount amount");
        JLabel expDateLabel = new JLabel("Enter exp Date");
        JLabel startDateLabel = new JLabel("Enter start Date");
        JLabel checkDiscountLabel = HelperFunctionGUI.createCheckLabel("Invalid value",250, 70, 100, 20);
        JLabel checkDateLabel = HelperFunctionGUI.createCheckLabel("Invalid ExpDate",380, 130, 80, 20);

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField discountField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Update discount for category in store",
                "Update discount for product in store", "Update discount for specific product in store"
                , "Update store price"});


        JComboBox<String> categoriesComboBox = HelperFunctionGUI.createComboBoxCategories();

        JComboBox<String> dayComboBoxStart = new JComboBox<>();
        JComboBox<String> dayComboBoxExp = new JComboBox<>();

        dayComboBoxStart.addItem("");
        dayComboBoxExp.addItem("");
        for(int i = 1 ; i <=31 ; i++)
        {
            if(i<10) {
                dayComboBoxStart.addItem("0" + i);
                dayComboBoxExp.addItem("0" + i);
            }
            else {
                dayComboBoxStart.addItem(String.valueOf(i));
                dayComboBoxExp.addItem(String.valueOf(i));
            }
        }


        JComboBox<String> monthComboBoxStart = new JComboBox<>();
        JComboBox<String> monthComboBoxExp = new JComboBox<>();

        monthComboBoxStart.addItem("");
        monthComboBoxExp.addItem("");
        for(int i = 1 ; i <=12 ; i++)
        {
            if(i<10) {
                monthComboBoxStart.addItem("0"+i);
                monthComboBoxExp.addItem("0"+i);
            }
            else {
                monthComboBoxStart.addItem(String.valueOf(i));
                monthComboBoxExp.addItem(String.valueOf(i));
            }
        }


        JComboBox<String> yearComboBoxStart = new JComboBox<>();
        JComboBox<String> yearComboBoxExp = new JComboBox<>();

        yearComboBoxStart.addItem("");
        yearComboBoxExp.addItem("");
        for(int i = 2022 ; i <=2025 ; i++)
        {
            yearComboBoxStart.addItem(String.valueOf(i));
            yearComboBoxExp.addItem(String.valueOf(i));
        }

        //----------------------------------------- Create JButton ----------------------------------------

        JButton addDiscountToCategory = new JButton("Submit");
        JButton exitButton = HelperFunctionGUI.createExitButton(discountFrame, OldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

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

        //-------------------------------------- Set not visible ---------------------------------------------

        JComponent[] JComponentsUpdateCategory = new JComponent[]{categoryLabel, categoriesComboBox,
                discountLabel, discountField, startDateLabel, dayComboBoxStart,
                monthComboBoxStart, yearComboBoxStart, expDateLabel, dayComboBoxExp, monthComboBoxExp,
                yearComboBoxExp, addDiscountToCategory};

        HelperFunctionGUI.hideComponents(JComponentsUpdateCategory);

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(discountFrame, new JComponent[]{chooseLabel, chooseComboBox,
                categoryLabel, categoriesComboBox, discountLabel, discountField, checkDiscountLabel,
                startDateLabel, dayComboBoxStart, monthComboBoxStart, yearComboBoxStart, expDateLabel,
                dayComboBoxExp, monthComboBoxExp, yearComboBoxExp, checkDateLabel, addDiscountToCategory,
                exitButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------


        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseComboBox.getSelectedItem().toString();

                if(choose.equals("")) {
                    checkDiscountLabel.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsUpdateCategory);
                }

                if(choose.equals("Update discount for category in store"))
                    HelperFunctionGUI.showComponents(JComponentsUpdateCategory);
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


                if(category.equals(""))
                    isValid = false;

                if(!HelperFunctionGUI.CheckDoubleInput(discountField.getText()))
                {
                    checkDiscountLabel.setVisible(true);
                    isValid = false;
                }
                else {
                    checkDiscountLabel.setVisible(false);
                    discount = Double.parseDouble(discountField.getText());
                }

                if(dayComboBoxStart.getSelectedItem().toString().equals("") ||
                        monthComboBoxStart.getSelectedItem().toString().equals("") ||
                        yearComboBoxStart.getSelectedItem().toString().equals(""))
                {
                    isValid = false;
                    return;
                }
                else
                    startDate = LocalDateTime.parse(yearComboBoxStart.getSelectedItem().toString() + "-" +
                            monthComboBoxStart.getSelectedItem().toString() + "-" +
                            dayComboBoxStart.getSelectedItem().toString() + "T00:00:00");


                if(dayComboBoxExp.getSelectedItem().toString().equals("") ||
                        monthComboBoxExp.getSelectedItem().toString().equals("") ||
                        yearComboBoxExp.getSelectedItem().toString().equals(""))

                {
                    isValid = false;
                    return;
                }

                else
                    ExpDate = LocalDateTime.parse(yearComboBoxExp.getSelectedItem().toString() + "-" +
                            monthComboBoxExp.getSelectedItem().toString() + "-" +
                            dayComboBoxExp.getSelectedItem().toString() + "T00:00:00");


                if(ExpDate.isBefore(startDate))
                {
                    checkDateLabel.setVisible(true);
                    isValid = false;
                }
                else
                    checkDateLabel.setVisible(false);

                if(isValid)
                {
                    update_discount_bycategory(category, startDate, ExpDate, discount);
                    discountFrame.dispose();
                    OldFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });

        discountFrame.setVisible(true);
    }

}
