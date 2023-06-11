package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangeSupplierDetailsGUI {

    static JFrame OldFrame;
    static SupplierController supplierController;

    public static void powerOn(JFrame oldFrame) {

        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page1Frame = HelperFunctionGUI.createNewFrame("Change supplier details");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel supplierNumLabel = new JLabel("Choose supplier");

        JLabel bankLabel = new JLabel("Update supplier bank account");
        JLabel checkBankLabel = HelperFunctionGUI.createCheckLabel("empty", 420, 50, 60, 20);

        JLabel paymentTermLabel = new JLabel("Update supplier payment term");

        JLabel stopManufacturerLabel = new JLabel("Stop working with a manufacturer");


        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField bankField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxPaymentTerm = new JComboBox<>(new String[]{"", "Net", "Net 30 days", "Net 60 days"});
        JComboBox<String> comboBoxSupplierNum = HelperFunctionGUI.createComboBoxSupplierNum();
        JComboBox<String> comboBoxManufacturer = new JComboBox<>();

        //----------------------------------------- Create JButton ----------------------------------------

        JButton paymentTermApplyButton = new JButton("Apply");
        JButton bankApplyButton = new JButton("Apply");
        JButton manufacturerApplyButton = new JButton("Apply");
        JButton updateContactsButton = new JButton("Update Contacts");
        JButton exitButton = HelperFunctionGUI.createExitButton(page1Frame, oldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        supplierNumLabel.setBounds(10, 10, 200, 20);
        comboBoxSupplierNum.setBounds(230, 10, 100, 20);

        bankLabel.setBounds(10, 50, 200, 20);
        bankField.setBounds(230, 50, 80, 20);
        bankApplyButton.setBounds(320, 50, 80, 20);

        paymentTermLabel.setBounds(10, 90, 200, 20);
        comboBoxPaymentTerm.setBounds(230, 90, 80, 20);
        paymentTermApplyButton.setBounds(320, 90, 80, 20);

        stopManufacturerLabel.setBounds(10, 130, 200, 20);
        comboBoxManufacturer.setBounds(230, 130, 80, 20);
        manufacturerApplyButton.setBounds(320, 130, 80, 20);

        updateContactsButton.setBounds(10, 350, 390, 40);



        //-------------------------------------- Set not visible -------------------------------------------------------------

        JComponent[] hiddenComponents = {bankLabel, bankField, bankApplyButton, paymentTermLabel
                , comboBoxPaymentTerm, paymentTermApplyButton, stopManufacturerLabel, comboBoxManufacturer, manufacturerApplyButton, updateContactsButton};

        HelperFunctionGUI.hideComponents(hiddenComponents);

        //------------------------------------ Add to currFrame --------------------------------------------------------------
        JComponent[] addComponents = {supplierNumLabel, comboBoxSupplierNum, bankLabel, bankField, bankApplyButton, checkBankLabel, paymentTermLabel
                , comboBoxPaymentTerm, paymentTermApplyButton, stopManufacturerLabel, comboBoxManufacturer, manufacturerApplyButton, updateContactsButton, exitButton};

        HelperFunctionGUI.addComponentsToFrame(page1Frame, addComponents);

        // ------------------------------------- Add action listener to JObjects -------------------------------------

        comboBoxSupplierNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String supplierNum = (String) combo.getSelectedItem();

                if(supplierNum.equals("")) {
                    HelperFunctionGUI.hideComponents(hiddenComponents);
                    checkBankLabel.setVisible(false);
                }
                else {
                    HelperFunctionGUI.showComponents(hiddenComponents);
                    HelperFunctionGUI.createComboBoxManufacturer(comboBoxManufacturer, supplierNum);

                }
            }
        });

        //**************************************************************************

        bankApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newBankAccount = bankField.getText();
                if(newBankAccount.equals(""))
                    checkBankLabel.setVisible(true);
                else
                    supplierController.setBankAccount(comboBoxSupplierNum.getSelectedItem().toString(),newBankAccount);
            }});

        //**************************************************************************

        paymentTermApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payment = comboBoxPaymentTerm.getSelectedItem().toString();
                if(!payment.equals(""))
                    supplierController.updateSupplierPaymentTerm(comboBoxSupplierNum.getSelectedItem().toString(), comboBoxPaymentTerm.getSelectedIndex()-1);
            }});

        manufacturerApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String manufacturer = comboBoxManufacturer.getSelectedItem().toString();
                if(!manufacturer.equals(""))
                    supplierController.stopWorkingWithManufacturer(comboBoxSupplierNum.getSelectedItem().toString(), manufacturer);
            }});

        updateContactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page1Frame.setVisible(false);
                UpdateContactsFrame();
            }});

        page1Frame.setVisible(true);
    }

    public static void UpdateContactsFrame() {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page2Frame = HelperFunctionGUI.createNewFrame("Change supplier details");



        page2Frame.setVisible(true);
    }
}
