package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import DataAccessLayer.Transport.Suppliers_dao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Register_supplier extends JFrame {
    private JTextField addressText;
    private JComboBox<String> phonePrefix;
    private JTextField phoneNumber;
    private JTextField supplierNameText;
    private JTextField contactText;
    private JPanel CreateSupplier;
    private JButton Create;
    private JButton GoBack;
    private String supplier_name;
    private String contact_name;
    private String phone_prefix;
    private String phone_number;
    private String address;
    private Transport_main main_frame;

    public Register_supplier(Transport_main transportMain) {
        main_frame = transportMain;
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 200);
        getContentPane().add(CreateSupplier);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });

        setLocationRelativeTo(null);

        Create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error = "These fields are empty:\n";
                int count = 0;

                if (supplierNameText.getText().isEmpty()) {
                    error += "* Name of supplier \n";
                    count++;
                }
                if (contactText.getText().isEmpty()) {
                    error += "* Contact name \n";
                    count++;
                }
                if (phoneNumber.getText().isEmpty()) {
                    error += "* Phone number \n";
                    count++;
                }
                if (addressText.getText().isEmpty()) {
                    error += "* Address \n";
                    count++;
                }
                if (count > 0) {
                    JOptionPane.showMessageDialog(null, error);
                    return;
                }

                phone_number = phoneNumber.getText();
                supplier_name = supplierNameText.getText();
                phone_prefix = phonePrefix.getSelectedItem().toString();
                address = addressText.getText();
                contact_name = contactText.getText();

                if (Suppliers_dao.getInstance().is_supplier_exist(supplier_name)) {
                    JOptionPane.showMessageDialog(null, "This supplier name already exists");
                    return;
                } else if (phone_number.length() != 7 || !containsOnlyNumbers(phone_number)) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number. It should be 7 digits between 0-9.");
                    return;
                }
                Logistical_center_controller.getInstance().add_supplier(address, phone_prefix+phone_number, supplier_name, contact_name);
                JOptionPane.showMessageDialog(null, "supplier successfully created.");
                addressText.setText("");
                contactText.setText("");
                phoneNumber.setText("");
                supplierNameText.setText("");
                // Perform the necessary actions for creating the supplier
            }
        });

        GoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private boolean containsOnlyNumbers(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void goBack() {
        main_frame.setVisible(true);
        dispose();
    }
}
