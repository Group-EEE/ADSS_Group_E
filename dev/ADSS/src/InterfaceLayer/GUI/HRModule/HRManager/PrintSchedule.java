package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintSchedule extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField storeNameField;
    private JTextArea scheduleInfoArea;

    public PrintSchedule() {
        // Set the size and layout of the frame
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Create a panel for the store name input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Create a label and text field for the store name
        JLabel storeNameLabel = new JLabel("Store Name:");
        storeNameField = new JTextField(20);

        // Add the label and text field to the input panel
        inputPanel.add(storeNameLabel);
        inputPanel.add(storeNameField);

        // Create a button for printing the schedule
        JButton printButton = new JButton("Print Schedule");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeNameField.getText();
                if (storeName == null || storeName.isEmpty()) {
                    JOptionPane.showMessageDialog(PrintSchedule.this, "Please enter a store name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Schedule schedule = _facade.getSchedule(storeName);
                    scheduleInfoArea.setText(schedule.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PrintSchedule.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create a scroll pane for the schedule information
        JScrollPane scrollPane = new JScrollPane();
        scheduleInfoArea = new JTextArea();
        scheduleInfoArea.setEditable(false);
        scrollPane.setViewportView(scheduleInfoArea);

        // Add the input panel, print button, and scroll pane to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(printButton, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrintSchedule();
            }
        });
    }
}
