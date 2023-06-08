package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;

import javax.swing.*;

public class CreateSupplierGUI {

    static SupplierController supplierController;
    static JFrame OldFrame;
    public static void powerOn(SupplierController suppController, JFrame oldFrame)
    {
        supplierController = suppController;
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = new JFrame("Create new Supplier");
        currFrame.setSize(500, 500);
        currFrame.setLayout(null);

        //------------------------------------ Name label and field ----------------------------------------
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 10, 100,20);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 10, 80,20);

        currFrame.add(nameLabel);
        currFrame.add(nameField);

        //------------------------------------ SupplierNum label and field ----------------------------------------

        JLabel supplierNumLabel = new JLabel("SupplierNum");
        supplierNumLabel.setBounds(10, 40, 100,20);

        JTextField supplierNumField = new JTextField();
        supplierNumField.setBounds(150, 40, 80,20);

        currFrame.add(supplierNumLabel);
        currFrame.add(supplierNumField);

        //------------------------------------ bankAccount label and field ----------------------------------------

        JLabel bankAccountLabel = new JLabel("bankAccount");
        bankAccountLabel.setBounds(10, 70, 100,20);

        JTextField bankAccountField = new JTextField();
        bankAccountField.setBounds(150, 70, 80,20);

        currFrame.add(bankAccountLabel);
        currFrame.add(bankAccountField);

        //------------------------------------ PaymentTerm label and field ----------------------------------------

        JLabel PaymentTermLabel = new JLabel("PaymentTerm");
        PaymentTermLabel.setBounds(10, 100, 100,20);

        JComboBox<String> comboBoxPaymentTerm = new JComboBox<>();
        comboBoxPaymentTerm.setBounds(150,100,80,20);
        comboBoxPaymentTerm.addItem("");
        comboBoxPaymentTerm.addItem("Net");
        comboBoxPaymentTerm.addItem("Net 30 days");
        comboBoxPaymentTerm.addItem("Net 60 days");

        currFrame.add(PaymentTermLabel);
        currFrame.add(comboBoxPaymentTerm);

        //-----------------------------------------------------------------------------------

        String[][] data = {{"",""},{"",""},{"",""}};
        String[] columns = {"Name", "PhoneNumber"};

        JLabel ContactLabel = new JLabel("Contacts");
        ContactLabel.setBounds(10, 130, 100,20);

        JTable jTableContacts= new JTable(data, columns);
        jTableContacts.setBounds(150, 130, 200, 80);

        JScrollPane jScrollPane = new JScrollPane(jTableContacts);
        jScrollPane.setBounds(150, 130, 200, 80);

        currFrame.add(ContactLabel);
        currFrame.add(jScrollPane);

        currFrame.setVisible(true);

    }
}
