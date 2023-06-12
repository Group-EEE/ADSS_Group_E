package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.objects.Truck;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Truck_Display extends JFrame {
    private Transport_main main_frame;
    private ArrayList<Truck> trucks;

    public Truck_Display(Transport_main transportMain) {
        this.main_frame = transportMain;
        this.trucks = Logistical_center_controller.getInstance().get_trucks();

        setTitle("Trucks Display");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 600));

        if (trucks.isEmpty()) {
            setPreferredSize(new Dimension(400, 120));
            // Show message instead of the table
            JLabel messageLabel = new JLabel("Currently, there are no trucks in the system.");
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(messageLabel, BorderLayout.CENTER);
        } else {
            // Create table model
            String[] columnNames = {
                    "Registration Plate Number",
                    "Model",
                    "Net Weight",
                    "Max Weight",
                    "Cold Level",
                    "Truck Driver Name",
                    "Current Weight"
            };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            // Populate table model with truck data
            for (Truck truck : trucks) {
                Object[] rowData = {
                        truck.getRegistration_plate(),
                        truck.getModel(),
                        truck.getNet_weight(),
                        truck.getMax_weight(),
                        Objects.requireNonNull(truck.getCold_level()).name(),
                        (truck.getCurrent_driver() != null) ? truck.getCurrent_driver().getFullName() : "N/A",
                        truck.getCurrent_weight()
                };
                tableModel.addRow(rowData);
            }

            // Create table and set table model
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            // Add table to the frame
            add(scrollPane, BorderLayout.CENTER);
        }

        // Create back button and add action listener
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main_frame.setVisible(true); // Show the main frame
                dispose(); // Close the current window
            }
        });

        // Add back button to the frame
        add(backButton, BorderLayout.SOUTH);

        // Add window listener to handle window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                main_frame.setVisible(true); // Show the main frame
                dispose(); // Close the current window
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}
