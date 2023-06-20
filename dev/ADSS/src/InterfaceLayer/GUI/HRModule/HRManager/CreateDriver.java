package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.TransportationModule.objects.cold_level;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class CreateDriver extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField bankAccountField;
    private JTextField salaryField;
    private JTextField hiringConditionField;
    private JTextField startDayField;
    private JTextField startMonthField;
    private JTextField startYearField;
    private JTextField passwordField;
    private JTextField licenseIDField;
    private JTextField coldLevelField;
    private JTextField maxWeightField;

    public CreateDriver() {
        // Set the size and layout of the frame
        setSize(400, 400);
        setLayout(new GridLayout(15, 2));

        // Create labels and input fields
        JLabel idLabel = new JLabel("ID:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel bankAccountLabel = new JLabel("Bank Account:");
        JLabel salaryLabel = new JLabel("Salary:");
        JLabel hiringConditionLabel = new JLabel("Hiring Condition:");
        JLabel startDayLabel = new JLabel("Start Day:");
        JLabel startMonthLabel = new JLabel("Start Month:");
        JLabel startYearLabel = new JLabel("Start Year:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel licenseIDLabel = new JLabel("License ID:");
        JLabel coldLevelLabel = new JLabel("Cold Level:");
        JLabel maxWeightLabel = new JLabel("Max Weight:");

        idField = new JTextField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        ageField = new JTextField(20);
        bankAccountField = new JTextField(20);
        salaryField = new JTextField(20);
        hiringConditionField = new JTextField(20);
        startDayField = new JTextField(20);
        startMonthField = new JTextField(20);
        startYearField = new JTextField(20);
        passwordField = new JTextField(20);
        licenseIDField = new JTextField(20);
        coldLevelField = new JTextField(20);
        maxWeightField = new JTextField(20);

        // Add labels and input fields to the frame
        getContentPane().add(idLabel);
        getContentPane().add(idField);
        getContentPane().add(firstNameLabel);
        getContentPane().add(firstNameField);
        getContentPane().add(lastNameLabel);
        getContentPane().add(lastNameField);
        getContentPane().add(ageLabel);
        getContentPane().add(ageField);
        getContentPane().add(bankAccountLabel);
        getContentPane().add(bankAccountField);
        getContentPane().add(salaryLabel);
        getContentPane().add(salaryField);
        getContentPane().add(hiringConditionLabel);
        getContentPane().add(hiringConditionField);
        getContentPane().add(startDayLabel);
        getContentPane().add(startDayField);
        getContentPane().add(startMonthLabel);
        getContentPane().add(startMonthField);
        getContentPane().add(startYearLabel);
        getContentPane().add(startYearField);
        getContentPane().add(passwordLabel);
        getContentPane().add(passwordField);
        getContentPane().add(licenseIDLabel);
        getContentPane().add(licenseIDField);
        getContentPane().add(coldLevelLabel);
        getContentPane().add(coldLevelField);
        getContentPane().add(maxWeightLabel);
        getContentPane().add(maxWeightField);

        // Create a "Create Driver" button and add an ActionListener
        JButton createButton = new JButton("Create Driver");

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
                int id;
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                int age;
                String bankAccount = bankAccountField.getText();
                int salary;
                String hiringCondition = hiringConditionField.getText();
                LocalDate startDateOfEmployment;
                String password = passwordField.getText();
                int licenseID;
                cold_level coldLevel;
                double maxWeight;

                // Validate and parse input values
                try {
                    id = Integer.parseInt(idField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CreateDriver.this, "Invalid ID. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                age = validateIntegerInput(ageField.getText(), "Invalid age. Please enter a valid integer.", "Age");
                if (age == -1)
                    return;

                try {
                    salary = Integer.parseInt(salaryField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CreateDriver.this, "Invalid salary. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int startDay = validateIntegerInput(startDayField.getText(), "Invalid start day. Please enter a valid integer.", "Start Day");
                if (startDay == -1)
                    return;

                int startMonth = validateIntegerInput(startMonthField.getText(), "Invalid start month. Please enter a valid integer.", "Start Month");
                if (startMonth == -1)
                    return;

                int startYear = validateIntegerInput(startYearField.getText(), "Invalid start year. Please enter a valid integer.", "Start Year");
                if (startYear == -1)
                    return;

                try {
                    startDateOfEmployment = LocalDate.of(startYear, startMonth, startDay);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CreateDriver.this, "Invalid date. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                licenseID = validateIntegerInput(licenseIDField.getText(), "Invalid license ID. Please enter a valid integer.", "License ID");
                if (licenseID == -1)
                    return;

                try {
                    coldLevel = cold_level.valueOf(coldLevelField.getText());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(CreateDriver.this, "Invalid cold level. Please enter one of the following: Freeze, Cold, Dry.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    maxWeight = Double.parseDouble(maxWeightField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CreateDriver.this, "Invalid max weight. Please enter a valid double.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    _facade.createDriver(id, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password, licenseID, coldLevel, maxWeight);
                    JOptionPane.showMessageDialog(CreateDriver.this, "Driver created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CreateDriver.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the "Create Driver" button to the frame
        getContentPane().add(createButton);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CreateDriver();
            }
        });
    }

    // Validate and parse integer input values
    private int validateIntegerInput(String input, String errorMessage, String fieldName) {
        int value;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(CreateDriver.this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return value;
    }

}
