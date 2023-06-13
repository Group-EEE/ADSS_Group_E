package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.HRMoudle.StoresDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Store_Display extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Store> stores;
    private Transport_main main_frame;

    public Store_Display(Transport_main transportMain) {
        this.main_frame = transportMain;
        this.stores = StoresDAO.getInstance().SelectAllStores();

        setTitle("Stores Display");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Store Name");
        tableModel.addColumn("Store Address");
        tableModel.addColumn("Store Phone Number");
        tableModel.addColumn("Contact Name");
        tableModel.addColumn("Store Area");

        // Populate table model with store data
        for (Store store : stores) {
            Object[] rowData = {
                    store.getName(),
                    store.getAddress(),
                    store.getPhone(),
                    store.getSite_contact_name(),
                    store.get_area()
            };
            tableModel.addRow(rowData);
        }

        // Create table and set table model
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add table to the frame
        add(scrollPane, BorderLayout.CENTER);

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
