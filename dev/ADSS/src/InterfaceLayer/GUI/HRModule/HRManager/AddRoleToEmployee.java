package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AddRoleToEmployee extends JFrame {
    private final Facade _facade = Facade.getInstance();

    public AddRoleToEmployee() {
        // Set the size of the frame
        setSize(400, 300);

        // Create a main panel with a BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        getContentPane().add(mainPanel);

        // Create a panel for the employee ID
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.add(new JLabel("Employee ID:"));
        JTextField employeeIdField = new JTextField(10);
        idPanel.add(employeeIdField);

        // Create a panel for the role
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.add(new JLabel("Role:"));
        JComboBox<RoleType> roleComboBox = new JComboBox<>(RoleType.values());
        rolePanel.add(roleComboBox);

        // Add the panels to the main panel
        mainPanel.add(idPanel);
        mainPanel.add(rolePanel);

        // Create a button to go back to the main menu
        JButton backToMenuButton = new JButton("Back to Main Menu");
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

        // Create an "Add" button and add an ActionListener
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = -1;

                try {
                    id = Integer.parseInt(employeeIdField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ID must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                RoleType role = (RoleType) roleComboBox.getSelectedItem();
                List<RoleType> employeeRoles = _facade.getEmployeeRoles(id);
                for(int i = 0; i < employeeRoles.size(); i++){
                    if(role.equals(employeeRoles.get(i))){
                        JOptionPane.showMessageDialog(null, _facade.getEmployeeFullNameById(id) + " already have " + role.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Call your addRoleToEmployee function and display a message
                if (_facade.addRoleToEmployee(id, role)) {
                    JOptionPane.showMessageDialog(null, "Role " + role.toString() + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Return to the HRmenu screen
                    HRmenu hrmenu = new HRmenu();
                    hrmenu.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the "Add" and "Back to Main Menu" buttons to the main panel
        mainPanel.add(addButton);
        mainPanel.add(backToMenuButton);

        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddRoleToEmployee();
            }
        });
    }
}
