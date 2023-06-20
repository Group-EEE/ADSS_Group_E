package InterfaceLayer.GUI.HRModule.EmployeesGUI;

import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdateInformation extends JFrame{
    Facade _facade = Facade.getInstance();
    public UpdateInformation() {
        setTitle("Update Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the label at the top
        JLabel titleLabel = new JLabel("Please select what you want to update");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));

        // Create the buttons
        JButton firstNameButton = new JButton("First Name");
        JButton lastNameButton = new JButton("Last Name");
        JButton bankAccountButton = new JButton("Bank Account");
        JButton passwordButton = new JButton("Password");
        JButton backButton = new JButton("Back to Main Menu");

        // Add action listeners to the buttons
        firstNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateFrame("First Name");
            }
        });

        // Add a WindowListener to the frame
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Create an instance of the main menu frame
                EmployeesMenu mainMenu = new EmployeesMenu();
                mainMenu.setVisible(true);
            }
        });

        lastNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateFrame("Last Name");
            }
        });

        bankAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateFrame("Bank Account");
            }
        });

        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateFrame("Password");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle back button click
                // Add your logic here
            }
        });

        // Add buttons to the panel
        buttonPanel.add(firstNameButton);
        buttonPanel.add(lastNameButton);
        buttonPanel.add(bankAccountButton);
        buttonPanel.add(passwordButton);
        buttonPanel.add(backButton);

        // Add button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame visible
        setVisible(true);
    }
    private void openUpdateFrame(String field) {
        JFrame updateFrame = new JFrame("Update " + field);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setSize(400, 200);

        // Create the panel
        JPanel updatePanel = new JPanel(new BorderLayout());

        // Create the label and text field
        JLabel updateLabel = new JLabel("Enter new " + field + ":");
        JTextComponent updateTextField;
        if (field == "Password")
            updateTextField = new JPasswordField();
        else
           updateTextField = new JTextField();

        // Create the update button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newInformation = updateTextField.getText();
                switch (field){
                    case "First Name":
                        _facade.setNewFirstName(newInformation);
                        break;
                    case "Last Name":
                        _facade.setNewLastName(newInformation);
                        break;
                    case "Bank Account":
                        _facade.setNewBankAccount(newInformation);
                        break;
                    case "Password":
                        _facade.setNewPassword(newInformation);
                        break;

                }
                updateFrame.dispose(); // Close the update frame after updating

            }
        });

        // Add components to the panel
        updatePanel.add(updateLabel, BorderLayout.NORTH);
        updatePanel.add(updateTextField, BorderLayout.CENTER);
        updatePanel.add(updateButton, BorderLayout.SOUTH);

        // Add the panel to the update frame
        updateFrame.add(updatePanel);

        // Set the update frame visible
        updateFrame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UpdateInformation();
            }
        });
    }
}
