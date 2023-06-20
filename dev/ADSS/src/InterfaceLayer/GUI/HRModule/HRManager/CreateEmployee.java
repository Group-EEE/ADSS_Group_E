package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class CreateEmployee extends JFrame {
    private final Facade _facade = Facade.getInstance();

    public CreateEmployee() {
        // Set the size and layout of the frame
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create 10 JLabel and JTextField pairs
        JLabel[] labels = new JLabel[11];
        labels[0] = new JLabel("ID:");
        labels[1] = new JLabel("First Name:");
        labels[2] = new JLabel("Last Name:");
        labels[3] = new JLabel("Age:");
        labels[4] = new JLabel("Bank account:");
        labels[5] = new JLabel("Salary:");
        labels[6] = new JLabel("Hiring condition:");
        labels[7] = new JLabel("Day:");
        labels[8] = new JLabel("Month:");
        labels[9] = new JLabel("Year:");
        labels[10] = new JLabel("Password:");

        JTextField[] textFields = new JTextField[11];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new JTextField(10);
        }

        // Add labels and text fields to the frame
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            getContentPane().add(labels[i], gbc);

            gbc.gridx = 1;
            getContentPane().add(textFields[i], gbc);
        }

        // Create a "Create" button and add an ActionListener
        JButton createButton = new JButton("Create");

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
                int ID = -1;
                int age = -1;
                int salary = -1;
                LocalDate startDateOfEmployment = null;
                try{
                    ID = Integer.parseInt(textFields[0].getText());
                    age = Integer.parseInt(textFields[3].getText());
                    salary = Integer.parseInt(textFields[5].getText());
                    startDateOfEmployment = LocalDate.of(
                            Integer.parseInt(textFields[9].getText()),
                            Integer.parseInt(textFields[8].getText()),
                            Integer.parseInt(textFields[7].getText())
                    );
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "ID,age,salary and date parts must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String first_name = textFields[1].getText();
                String last_name = textFields[2].getText();

                String bank_account = textFields[4].getText();

                String hiring_condition = textFields[6].getText();

                String password = textFields[10].getText();
//                if(_facade.getEmployeeFullNameById(ID)!=null){
//                    JOptionPane.showMessageDialog(null, "id already exists", "Error", JOptionPane.ERROR_MESSAGE);
//                }

                // Call your createEmployee function and display a message
                try{
                    _facade.createEmployee(ID, first_name, last_name, age, bank_account, salary, hiring_condition, startDateOfEmployment, password, false);
                    JOptionPane.showMessageDialog(null, "Employee created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Return to the HRmenu screen
                    HRmenu hrmenu = new HRmenu();
                    hrmenu.setVisible(true);
                    dispose();
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }


            }
        });

        // Add the "Create" button to the frame
        gbc.gridx = 1;
        gbc.gridy = labels.length;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(createButton, gbc);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CreateEmployee();
            }
        });
    }
}
