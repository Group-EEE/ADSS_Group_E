package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.EmployeeController;
//TODO: FIX EXCEPTIONS

import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdatePersonalInformation extends JFrame {
    private final EmployeeController employeeController = EmployeeController.getInstance();

    private final int hrManagerID;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField bankAccountField;

    public UpdatePersonalInformation() {

        this.hrManagerID = employeeController.getHRManagerID();

        // Set the size and layout of the frame
        setSize(400, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create labels and text fields
        JLabel idLabel = new JLabel("HR Manager ID:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel bankAccountLabel = new JLabel("Bank Account:");

        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        bankAccountField = new JTextField(20);

        // Add labels and text fields to the frame
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(idLabel, gbc);

        gbc.gridx = 1;
        getContentPane().add(new JLabel(String.valueOf(hrManagerID)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(firstNameLabel, gbc);

        gbc.gridx = 1;
        getContentPane().add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(lastNameLabel, gbc);

        gbc.gridx = 1;
        getContentPane().add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        getContentPane().add(bankAccountLabel, gbc);

        gbc.gridx = 1;
        getContentPane().add(bankAccountField, gbc);

        // Create an "Update" button and add an ActionListener
        JButton updateButton = new JButton("Update");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        getContentPane().add(updateButton, gbc);

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

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(UpdatePersonalInformation.this, "Are you sure you want to update your personal information?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String bankAccount = bankAccountField.getText();

                    // Check which fields are updated and call the appropriate update methods
                    boolean success = true;
                    if (!firstName.isEmpty()) {
                        if (!employeeController.updateFirstName(hrManagerID, firstName)) {
                            success = false;
                        }
                    }
                    if (!lastName.isEmpty()) {
                        if (!employeeController.updateLastName(hrManagerID, lastName)) {
                            success = false;
                        }
                    }
                    if (!bankAccount.isEmpty()) {
                        if (!employeeController.updateBankAccount(hrManagerID, bankAccount)) {
                            success = false;
                        }
                    }

                    // Display appropriate message based on the update result
                    if (success) {
                        JOptionPane.showMessageDialog(UpdatePersonalInformation.this, "Your personal information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(UpdatePersonalInformation.this, "Error updating your personal information.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UpdatePersonalInformation();
            }
        });
    }
}