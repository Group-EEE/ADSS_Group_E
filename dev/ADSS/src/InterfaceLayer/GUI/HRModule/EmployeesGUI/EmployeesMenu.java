package InterfaceLayer.GUI.HRModule.EmployeesGUI;

import BussinessLayer.HRModule.Controllers.Facade;
import InterfaceLayer.GUI.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EmployeesMenu extends JFrame {
    Facade _facade = Facade.getInstance();
    public EmployeesMenu() {
        setTitle("Employees Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the label at the top
        JLabel titleLabel = new JLabel("Please select an option");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));

        // Create the buttons
        JButton selectShiftsButton = new JButton("Select Shifts for This Week");
        JButton updateInfoButton = new JButton("Update Personal Information");
        JButton printScheduleButton = new JButton("Print Your Schedule");
        JButton logoutButton = new JButton("Log Out");

        // Add action listeners to the buttons
        selectShiftsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectShifts selectShifts = new SelectShifts();
                //selectShifts.setVisible(true);
                dispose();
            }
        });

        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateInformation updateInformation = new UpdateInformation();
                //updateInformation.setVisible(true);
                dispose();
            }
        });

        printScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrintEmployeeScheduleForm printEmployeeScheduleForm = new PrintEmployeeScheduleForm();
                printEmployeeScheduleForm.setVisible(true);
                dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    _facade.logout();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });

        // Add buttons to the panel
        buttonPanel.add(selectShiftsButton);
        buttonPanel.add(updateInfoButton);
        buttonPanel.add(printScheduleButton);
        buttonPanel.add(logoutButton);

        // Add button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of the form
        EmployeesMenu employeesGUI = new EmployeesMenu();
    }
}
