package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.objects.Site_Supply;
import DataAccessLayer.Transport.Site_Supply_dao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Site_Supply_Display extends JFrame {
    private Transport_main main_frame;
    private DefaultTableModel tableModel;
    private ArrayList<Site_Supply> siteSupplied_doc;

    public Site_Supply_Display(Transport_main transportMain) {
        this.main_frame = transportMain;
        this.siteSupplied_doc = Site_Supply_dao.getInstance().get_site_supply_documents();

        setTitle("Site Supply Display");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Create table model for outer table
        String[] outerColumnNames = {"Store Name", "Origin", "Items"};
        DefaultTableModel outerTableModel = new DefaultTableModel(outerColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };

        // Populate outer table model with site supply data
        for (Site_Supply siteSupply : siteSupplied_doc) {
            Object[] outerRowData = {
                    siteSupply.getStore().getName(),
                    siteSupply.getOrigin(),
                    createInnerTable(siteSupply)
            };
            outerTableModel.addRow(outerRowData);
        }

        // Create outer table
        JTable outerTable = new JTable(outerTableModel);
        outerTable.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return (Component) value;
            }
        });
        JScrollPane outerScrollPane = new JScrollPane(outerTable);

        // Add outer table to the frame
        // Add outer table to the frame
        add(outerScrollPane, BorderLayout.CENTER);

// Create back button and add action listener
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame

                // Perform the same action as the back button
                main_frame.setVisible(true);
            }
        });

// Add back button to the frame
        add(backButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);


        // Override processWindowEvent() to perform the same action as the back button when the frame is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                main_frame.setVisible(true);
            }
        });
    }

    private JTable createInnerTable(Site_Supply siteSupply) {
        String[] innerColumnNames = {"Item Name", "Quantity"};
        DefaultTableModel innerTableModel = new DefaultTableModel(innerColumnNames, 0);

        // Populate inner table model with item data
        for (String item : siteSupply.getItems().keySet()) {
            Object[] innerRowData = {item, siteSupply.getItems().get(item)};
            innerTableModel.addRow(innerRowData);
        }

        return new JTable(innerTableModel);
    }
}
