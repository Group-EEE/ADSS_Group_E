package InterfaceLayer.GUI.HRModule.EmployeesGUI;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Shift;
import InterfaceLayer.GUI.HRModule.HRManager.HRmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class PrintEmployeeScheduleForm extends JFrame {

    private Facade facade = Facade.getInstance();

    public PrintEmployeeScheduleForm() {
        setTitle("Employee Schedule");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the text area to display the schedule
        JTextArea scheduleTextArea = new JTextArea();
        scheduleTextArea.setEditable(false);

        // Create the button to print the schedule
        JButton printButton = new JButton("Print Schedule");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Shift> employeeSchedule;
                try {
                    facade.login(2, "1234");
                    employeeSchedule = facade.printEmployeeSchedule();
                    displaySchedule(scheduleTextArea, employeeSchedule);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // Add a WindowListener to the frame
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Create an instance of the main menu frame
                EmployeesMenu mainMenu = new EmployeesMenu();
                //mainMenu.setVisible(true);
            }
        });

        // Create the back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeesMenu employeesMenu = new EmployeesMenu();
                //employeesMenu.setVisible(true);
                dispose();
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(printButton);
        buttonPanel.add(backButton);

        // Add the components to the main panel
        mainPanel.add(scheduleTextArea, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame visible
        setVisible(true);
    }

    private void displaySchedule(JTextArea scheduleTextArea, List<Shift> employeeSchedule) {
        scheduleTextArea.setText(""); // Clear the text area

        if (employeeSchedule.isEmpty()) {
            scheduleTextArea.setText("No shifts found for the employee.");
        } else {
            for (Shift shift : employeeSchedule) {
                scheduleTextArea.append("Shift ID: " + shift.getShiftID() + "\n");
                scheduleTextArea.append("Date: " + shift.getDate() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        // Create an instance of the form
        PrintEmployeeScheduleForm scheduleForm = new PrintEmployeeScheduleForm();
    }
}
