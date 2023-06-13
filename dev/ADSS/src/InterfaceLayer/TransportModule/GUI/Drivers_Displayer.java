package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.objects.Truck_Driver;
import InterfaceLayer.TransportModule.GUI.Transport_main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Drivers_Displayer extends JFrame {
    private JTable table;
    private Transport_main main_frame;
    private List<Truck_Driver> drivers = Logistical_center_controller.getInstance().getDrivers();
    private DefaultTableModel tableModel;


    public Drivers_Displayer(Transport_main transportMain) {
        this.main_frame = transportMain;
        setTitle("Drivers Display");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        pack();

        if (drivers.isEmpty()) {
            setPreferredSize(new Dimension(400, 120));
            // Show message instead of the table
            JLabel messageLabel = new JLabel("Currently, there are no drivers in the system.");
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(messageLabel, BorderLayout.CENTER);
        } else {
            // Create table model
            tableModel = new DefaultTableModel();
            tableModel.addColumn("Driver ID Number");
            tableModel.addColumn("Driver Name");
            tableModel.addColumn("License ID");
            tableModel.addColumn("Truck Cold Level");
            tableModel.addColumn("Max Truck Weight");
            tableModel.addColumn("Current Truck");

            // Populate table model with driver data
            for (Truck_Driver driver : drivers) {
                String driverID = "N/A";
                String driverName = "N/A";
                String licenseID = "N/A";
                String truckColdLevel = "N/A";
                String maxTruckWeight = "N/A";
                String currentTruck = "N/A";

                driverID = Integer.toString(driver.getEmployeeID());
                driverName = driver.getFirstName();
                licenseID = Integer.toString(driver.getLicense().getL_ID());
                if (driver.getCurrent_truck() != null) {
                    currentTruck = driver.getCurrent_truck().getRegistration_plate();
                }
                truckColdLevel = driver.getLicense().getCold_level().toString();
                maxTruckWeight = Double.toString(driver.getLicense().getWeight());
                Object[] rowData = {driverID, driverName, licenseID, truckColdLevel, maxTruckWeight, currentTruck};
                tableModel.addRow(rowData);
            }

            // Create table and set table model
            table = new JTable(tableModel);
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                main_frame.setVisible(true); // Show the main frame
                dispose(); // Close the current window
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}
