package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PrintSchedule extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField storeNameField;
    private JTextArea scheduleInfoArea;

    public PrintSchedule() {
        // Set the size and layout of the frame
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Create a panel for the store name input and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5); // margin

        // Create a label and text field for the store name
        JLabel storeNameLabel = new JLabel("Store Name:");
        storeNameField = new JTextField(20);

        // Create a button for printing the schedule
        JButton printButton = new JButton("Print Schedule");
        printButton.setPreferredSize(new Dimension(150, 25)); // You can modify the dimensions as per your requirements

        // Create a button to go back to the main menu
        JButton backToMenuButton = new JButton("Back to Main Menu");
        backToMenuButton.setPreferredSize(new Dimension(150, 25)); // You can modify the dimensions as per your requirements

        // Add components to inputPanel with GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(storeNameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(storeNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(printButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(backToMenuButton, gbc);

        // Create a scroll pane for the schedule information
        JScrollPane scrollPane = new JScrollPane();
        scheduleInfoArea = new JTextArea();
        scheduleInfoArea.setEditable(false);
        scrollPane.setViewportView(scheduleInfoArea);

        // Add the input panel and scroll pane to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Set the frame to be visible
        setVisible(true);

        // Button listeners
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
                    JOptionPane.showMessageDialog(PrintSchedule.this, "There is no such store.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add a WindowListener to the frame
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Create an instance of the main menu frame
                HRmenu mainMenu = new HRmenu();
                mainMenu.setVisible(true);
            }
        });

        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HRmenu hrmenu = new HRmenu();
                // Hide this frame
                setVisible(false);

                // Show the main menu
                hrmenu.setVisible(true);
            }
        });
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
