package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class CreateNewLogisticSchedule extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField dayField;
    private JTextField monthField;
    private JTextField yearField;

    public CreateNewLogisticSchedule() {
        // Set the size and layout of the frame
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        // Create labels and input fields
        JLabel dayLabel = new JLabel("Day:");
        JLabel monthLabel = new JLabel("Month:");
        JLabel yearLabel = new JLabel("Year:");

        dayField = new JTextField(20);
        monthField = new JTextField(20);
        yearField = new JTextField(20);

        // Add labels and input fields to the frame
        getContentPane().add(dayLabel);
        getContentPane().add(dayField);
        getContentPane().add(monthLabel);
        getContentPane().add(monthField);
        getContentPane().add(yearLabel);
        getContentPane().add(yearField);

        // Create a "Create Schedule" button and add an ActionListener
        JButton createButton = new JButton("Create Schedule");

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


        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int day = 0;
                int month = 0;
                int year = 0;

                try {
                    day = Integer.parseInt(dayField.getText());
                    month = Integer.parseInt(monthField.getText());
                    year = Integer.parseInt(yearField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CreateNewLogisticSchedule.this, "Invalid input for date. Please enter valid integers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    boolean result = _facade.createNewLogisiticsSchedule(day,month,year);
                    if (result) {
                        JOptionPane.showMessageDialog(CreateNewLogisticSchedule.this, "The schedule was created successfully for the store Logistics", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(CreateNewLogisticSchedule.this, "An error occurred while creating the schedule", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CreateNewLogisticSchedule.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the "Create Schedule" button to the frame
        getContentPane().add(createButton);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CreateNewLogisticSchedule();
            }
        });
    }
}
