package InterfaceLayer.GUI.HRModule.HRManager;
import BussinessLayer.HRModule.Controllers.Facade;
//todo: dosent change in DB
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
