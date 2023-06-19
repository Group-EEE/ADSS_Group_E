package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.objects.Truck_Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Add_Standby_driver extends JFrame {
    private JButton addButton;
    private JButton cancelButton;
    private JLabel dateLabel;
    private JComboBox<String> dateComboBox;

    private Transport_main main_frame;

    public Add_Standby_driver(Transport_main transportMain) {
        this.main_frame = transportMain;
        setTitle("Add Standby Driver");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(2, 2));
        setPreferredSize(new Dimension(400, 100));

        dateLabel = new JLabel("Date: ");
        dateComboBox = new JComboBox<>();

        LocalDate today = LocalDate.now(); // Start from tomorrow
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Generate a list of selectable dates for the next week excluding today
        List<String> dateOptions = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            dateOptions.add(date.format(formatter));
        }

        // Populate the combo box with selectable dates
        for (String dateOption : dateOptions) {
            dateComboBox.addItem(dateOption);
        }

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDate = dateComboBox.getSelectedItem().toString();

                LocalDate date = LocalDate.parse(selectedDate, formatter);

                Truck_Driver added_driver = Logistical_center_controller.getInstance().add_standby_driver_by_date(date);

                if (added_driver != null) {
                    JOptionPane.showMessageDialog(null, "the driver " + added_driver.getFullName() + " was added as a Standby driver successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to add standby driver for the specified date.");
                }
                main_frame.setVisible(true); // Show the main frame
                dispose(); // Close the current window

            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main_frame.setVisible(true); // Show the main frame
                dispose(); // Close the current window
            }
        });

        add(dateLabel);
        add(dateComboBox);
        add(addButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
