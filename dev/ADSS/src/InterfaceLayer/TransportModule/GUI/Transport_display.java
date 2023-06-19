package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.objects.Transport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Transport_display extends JFrame {
    private DefaultTableModel tableModel;
    private ArrayList<Transport> transportList;

    public Transport_display() {
        this.transportList = getTransportData(); // Replace with your own method to fetch transport data

        setTitle("Transport Display");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Create table model
        String[] columnNames = {"ID", "Date", "Departure Time", "Truck Number", "Driver Name",
                "Origin", "Cold Level", "Suppliers", "Stores", "Finished", "Planned Date" ,"Driver ID", "Estimated End Time"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };

        // Populate table model with transport data
        for (Transport transport : transportList) {
            Object[] rowData = {
                    transport.getValue1(),
                    transport.getValue2(),
                    transport.getValue3(),
                    transport.getValue4(),
                    transport.getValue5(),
                    transport.getValue6(),
                    transport.getValue7(),
                    transport.getValue8(),
                    transport.getValue9(),
                    transport.getValue10(),
                    transport.getValue11()
            };
            tableModel.addRow(rowData);
        }

        // Create table
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add table to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Create back button and add action listener
        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> dispose());

        // Add back button to the frame
        add(backButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        // Override processWindowEvent() to perform an action when the frame is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Add your code here to handle the frame closing event
            }
        });
    }

    private ArrayList<Transport> getTransportData() {
        // Replace with your own method to fetch the transport data
        // Return a list of Transport objects
        return new ArrayList<>();
    }
}
