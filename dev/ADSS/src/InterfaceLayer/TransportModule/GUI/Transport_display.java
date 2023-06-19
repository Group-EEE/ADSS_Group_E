package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.objects.Transport;
import DataAccessLayer.Transport.Transport_dao;

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
    private Transport_main main_frame;

    public Transport_display(Transport_main transport_main) {
        this.transportList = getTransportData(); // Replace with your own method to fetch transport data
        this.main_frame = transport_main;

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
                    transport.getTransport_ID(),
                    transport.getDate(),
                    transport.getDeparture_time(),
                    transport.getTruck_number(),
                    transport.getDriver_name(),
                    transport.getOrigin(),
                    transport.getRequired_level().toString(),
                    transport.get_suppliers_name(),
                    transport.get_stores_name(),
                    transport.Started() ? "Yes" : "No",
                    transport.getPlanned_date(),
                    transport.getDriver_ID(),
                    transport.getEstimated_end_time()
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
        backButton.addActionListener((ActionEvent e) -> goBack());

        // Add back button to the frame
        add(backButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        // Override processWindowEvent() to perform an action when the frame is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });
    }

    private ArrayList<Transport> getTransportData() {
        return Transport_dao.getInstance().get_transports();
    }

    private void goBack() {
        main_frame.setVisible(true);
        dispose();
    }
}
