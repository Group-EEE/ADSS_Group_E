package InterfaceLayer.GUI.HRModule.EmployeesGUI;

import BussinessLayer.HRModule.Controllers.Facade;

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
                openNewFrame("Select Shifts for This Week");
            }
        });

        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewFrame("Update Personal Information");
            }
        });

        printScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewFrame("Print Your Schedule");
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

    private void openNewFrame(String option) {
        JFrame newFrame = new JFrame(option);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setSize(400, 200);

        // Create a panel for the new frame
        JPanel panel = new JPanel();
        JLabel label = new JLabel("This is the " + option + " frame.");
        panel.add(label);

        // Add the panel to the new frame
        newFrame.getContentPane().add(panel);

        // Set the new frame visible
        newFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of the form
        EmployeesMenu employeesGUI = new EmployeesMenu();
    }
}
