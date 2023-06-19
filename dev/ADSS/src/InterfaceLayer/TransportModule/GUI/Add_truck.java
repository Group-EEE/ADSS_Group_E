package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.objects.cold_level;

import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Add_truck extends JFrame {

    private Transport_main main_frame;
    private JTextField registration_number;
    private JTextField truck_model;
    private JComboBox cool_level;
    private JTextField truck_net_weight;
    private JTextField truck_max_weight;
    private JButton create_button;
    private JButton backButton;
    private JPanel create_new_truck;

    private String registration_number_str;
    private String truck_model_string;
    private String cool_level_string;
    private double truck_net_weight_double;
    private double truck_max_weight_double;

    private boolean is_all_filled = false;


    public Add_truck(Transport_main transportMain) {
        this.main_frame = transportMain;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600, 280);
        getContentPane().add(create_new_truck);

        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                backButton();
            }
        });

        registration_number.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                is_all_filled = false;
                String input = registration_number.getText();
                if (!input.isEmpty()) {
                    if (!containsOnlyNumbers(input) || input.length() != 8) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid 8-digit integer.");
                        registration_number.setText("");
                        return;
                    }
                    registration_number_str = input;
                    if (Logistical_center_controller.getInstance().is_truck_exist(registration_number_str)) {
                        JOptionPane.showMessageDialog(null, "This truck already exists.");
                        registration_number.setText("");
                        return;
                    }
                    is_all_filled = true;
                }
            }
        });



        truck_model.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                is_all_filled = false;
                truck_model_string = truck_model.getText();
                is_all_filled = true;
            }
        });


        cool_level.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cool_level_string = cool_level.getSelectedItem().toString();
            }
        });


        truck_net_weight.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                is_all_filled = false;
                if(!truck_net_weight.getText().isEmpty()) {
                    try {
                        truck_net_weight_double = Double.parseDouble(truck_net_weight.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number");
                        truck_net_weight.setText("");
                        return;
                    }
                    is_all_filled = true;
                }
            }
        });


        truck_max_weight.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                is_all_filled = false;
                if(!truck_max_weight.getText().isEmpty()) {
                    try {
                        truck_max_weight_double = Double.parseDouble(truck_max_weight.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number");
                        truck_max_weight.setText("");
                        return;
                    }
                    is_all_filled = true;
                }
            }
        });


        create_button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(registration_number.getText().isEmpty() || registration_number.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a registration number");
                    registration_number.setText("");
                    return;
                }
                else if (truck_model.getText().isEmpty() || truck_model.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter the truck model");
                    truck_model.setText("");
                    return;
                }
                else if (truck_net_weight.getText().isEmpty() || truck_net_weight.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter the truck net weight");
                    truck_net_weight.setText("");
                    return;
                }
                else if (truck_max_weight.getText().isEmpty() || truck_max_weight.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter the truck max weight");
                    truck_max_weight.setText("");
                    return;
                }
                else if (cool_level.getSelectedItem().toString().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please select a cool level");
                    return;
                }
                if(is_all_filled){
                    Logistical_center_controller.getInstance().add_truck(registration_number_str, truck_model_string, truck_net_weight_double, truck_max_weight_double, cold_level.fromString(cool_level_string), truck_net_weight_double);
                    JOptionPane.showMessageDialog(null, "Truck: " + registration_number_str +" has been added to the system successfully");
                }
                registration_number.setText("");
                truck_model.setText("");
                truck_net_weight.setText("");
                truck_max_weight.setText("");
                cool_level.setSelectedIndex(0);
            }
        });


        backButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton();
            }
        });
    }

    public void backButton(){
        main_frame.setVisible(true);
        dispose();
    }

    private boolean containsOnlyNumbers(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}