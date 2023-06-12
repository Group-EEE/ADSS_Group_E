package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Contact;
import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;
import SuppliersModule.Business.SupplierProduct;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                UpdateContactsFrame(comboBoxSupplierNum.getSelectedItem().toString(), page1Frame);
            }});

        page1Frame.setVisible(true);
    }

    public static void UpdateContactsFrame(String supplierNum, JFrame backFrame) {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame page2Frame = HelperFunctionGUI.createNewFrame("Change supplier details");

        //------------------------------------- Create JLabel -------------------------------------------
        JLabel addContactLabel = new JLabel("Add contact");

        JLabel contactNameLabel = new JLabel("Enter name");
        JLabel checkContactNameLabel = HelperFunctionGUI.createCheckLabel("Empty",250,40,100,20);

        JLabel contactPhoneLabel = new JLabel("Enter phone number");
        JLabel checkContactPhoneLabel = HelperFunctionGUI.createCheckLabel("Empty or Exist",250,80,100,20);

        JLabel buffer = new JLabel("-------------------------------------------------------------------------------------------------------------------------------------------");

        JLabel updateContactLabel = new JLabel("Update contact phone number");

        JLabel chooseUpdateContactLabel = new JLabel("Choose contact");
        JLabel contactNewPhoneLabel = new JLabel("Enter new phone number");
        JLabel checkContactNewPhoneLabel = HelperFunctionGUI.createCheckLabel("Empty or Exist",285,220,100,20);

        JLabel buffer2 = new JLabel("-------------------------------------------------------------------------------------------------------------------------------------------");

        JLabel deleteContactLabel = new JLabel("Delete contact");

        JLabel chooseDeleteContactLabel = new JLabel("Choose contact");

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField contactNameField = new JTextField();

        JTextField contactPhoneField = new JTextField();

        JTextField contactNewPhoneField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxUpdateContact = HelperFunctionGUI.createComboBoxContact(supplierNum);

        JComboBox<String> comboBoxDeleteContact = HelperFunctionGUI.createComboBoxContact(supplierNum);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton addButton = new JButton("Add");

        JButton updateButton = new JButton("Update");

        JButton deleteButton = new JButton("Delete");

        JButton exitButton = HelperFunctionGUI.createExitButton(page2Frame, backFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        addContactLabel.setBounds(200, 10, 100, 20);
        contactNameLabel.setBounds(10, 40, 150, 20);
        contactNameField.setBounds(150, 40, 80, 20);

        contactPhoneLabel.setBounds(10, 80, 150, 20);
        contactPhoneField.setBounds(150,80,80,20);

        addButton.setBounds(380, 50, 80, 40);
        buffer.setBounds(0, 120, 500, 10);

        updateContactLabel.setBounds(150, 130, 200, 20);

        chooseUpdateContactLabel.setBounds(10, 180, 160, 20);
        comboBoxUpdateContact.setBounds(170, 180,110,20);

        contactNewPhoneLabel.setBounds(10, 220, 160, 20);
        contactNewPhoneField.setBounds(170,220,110,20);

        updateButton.setBounds(380, 210, 80, 40);
        buffer2.setBounds(0,270,500,10);

        deleteContactLabel.setBounds(200, 280, 100, 20);

        chooseDeleteContactLabel.setBounds(10, 320, 100, 20);
        comboBoxDeleteContact.setBounds(130, 320,110,20);

        deleteButton.setBounds(380, 310, 80, 40);

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(page2Frame, new JComponent[]{addContactLabel,
                contactNameLabel, contactNameField, checkContactNameLabel, contactPhoneLabel,
                contactPhoneField, checkContactPhoneLabel, addButton, buffer, exitButton});

        HelperFunctionGUI.addComponentsToFrame(page2Frame, new JComponent[]{updateContactLabel, chooseUpdateContactLabel,
                comboBoxUpdateContact, contactNewPhoneLabel, contactNewPhoneField, checkContactNewPhoneLabel,
                buffer2, updateButton});

        HelperFunctionGUI.addComponentsToFrame(page2Frame, new JComponent[]{deleteContactLabel,
        chooseDeleteContactLabel, comboBoxDeleteContact, deleteButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                if(contactNameField.getText().equals("")){checkContactNameLabel.setVisible(true); isValid = false;}
                else checkContactNameLabel.setVisible(false);

                if(contactPhoneField.getText().equals("") || supplierController.checkIfContactExist(contactPhoneLabel.getText())){checkContactPhoneLabel.setVisible(true); isValid = false;}
                else checkContactPhoneLabel.setVisible(false);

                if(isValid)
                {
                    supplierController.addContactToSupplier(supplierNum, contactNameField.getText(), contactPhoneField.getText());
                    page2Frame.dispose();
                    backFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });

        
        page2Frame.setVisible(true);
    }
}
