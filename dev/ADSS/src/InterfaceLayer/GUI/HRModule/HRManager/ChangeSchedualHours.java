package InterfaceLayer.GUI.HRModule.HRManager;
import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Controllers.StoreController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangeSchedualHours extends JFrame{
    private final Facade _facade = Facade.getInstance();
    private JTextField storeNameField;
    private JTextField shiftIDField;
    private JTextField newStartHourField;
    private JTextField newEndHourField;

    public ChangeSchedualHours()  {
        // Set the size and layout of the frame
        setSize(400, 200);
        setLayout(new GridLayout(5, 2));

        // Create labels and text fields
        JLabel storeNameLabel = new JLabel("Store Name:");
        JLabel shiftIDLabel = new JLabel("Shift ID:");
        JLabel newStartHourLabel = new JLabel("New Start Hour:");
        JLabel newEndHourLabel = new JLabel("New End Hour:");

        storeNameField = new JTextField(20);
        shiftIDField = new JTextField(20);
        newStartHourField = new JTextField(20);
        newEndHourField = new JTextField(20);

        // Add labels and text fields to the frame
        getContentPane().add(storeNameLabel);
        getContentPane().add(storeNameField);
        getContentPane().add(shiftIDLabel);
        getContentPane().add(shiftIDField);
        getContentPane().add(newStartHourLabel);
        getContentPane().add(newStartHourField);
        getContentPane().add(newEndHourLabel);
        getContentPane().add(newEndHourField);

        // Create a "Change" button and add an ActionListener
        JButton changeButton = new JButton("Change");

        // Create a button to go back to the main menu
        JButton backToMenuButton = new JButton("Back to Main Menu");
        getContentPane().add(backToMenuButton);

        // Add an ActionListener to the backToMenuButton
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

        // Add a WindowListener to the frame
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Create an instance of the main menu frame
                HRmenu mainMenu = new HRmenu();
                mainMenu.setVisible(true);
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeNameField.getText();
                int shiftID = 0;
                int newStartHour = 0;
                int newEndHour = 0;

                try {
                    shiftID = Integer.parseInt(shiftIDField.getText());
                    newStartHour = Integer.parseInt(newStartHourField.getText());
                    newEndHour = Integer.parseInt(newEndHourField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ChangeSchedualHours.this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try{
                    _facade.getAllStores().contains(StoreController.getInstance().getStore(storeName));
                }catch (Exception ex){

                    JOptionPane.showMessageDialog(ChangeSchedualHours.this, "store dosent exist", "Missing Information", JOptionPane.ERROR_MESSAGE);
                }

                // Call the changeHoursShift function from the Facade class
                boolean success = _facade.changeHoursShift(storeName, newStartHour, newEndHour, shiftID);

                // Display appropriate message based on the update result
                if (success) {
                    JOptionPane.showMessageDialog(ChangeSchedualHours.this, "Shift hours changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ChangeSchedualHours.this, "invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the "Change" button to the frame
        getContentPane().add(changeButton);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChangeSchedualHours();
            }
        });
    }

}
