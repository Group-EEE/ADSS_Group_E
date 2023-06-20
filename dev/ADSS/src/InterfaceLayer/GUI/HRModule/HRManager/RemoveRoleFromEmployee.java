package InterfaceLayer.GUI.HRModule.HRManager;
//TODO: layout fix
import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RemoveRoleFromEmployee extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField employeeIDField;
    private JComboBox<String> roleComboBox;
    private int employeeID;

    public RemoveRoleFromEmployee() {
        // Set the size of the frame
        setSize(400, 200);

        // Use GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Create a label and text field for employee ID
        JLabel employeeIDLabel = new JLabel("Employee ID:");
        employeeIDField = new JTextField(5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        getContentPane().add(employeeIDLabel, constraints);

        constraints.gridx = 1;
        getContentPane().add(employeeIDField, constraints);

        // Create a button to get the employee's roles
        JButton getRolesButton = new JButton("Get Roles");
        constraints.gridx = 0;
        constraints.gridy = 1;
        getContentPane().add(getRolesButton, constraints);

        // Create a combo box to display the employee's roles
        roleComboBox = new JComboBox<>();
        roleComboBox.setEnabled(false);
        constraints.gridx = 1;
        getContentPane().add(roleComboBox, constraints);

        // Create a button to remove the selected role
        JButton removeRoleButton = new JButton("Remove Role");
        removeRoleButton.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 2;
        getContentPane().add(removeRoleButton, constraints);

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

        // Add an ActionListener to the getRolesButton
        getRolesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeIDText = employeeIDField.getText();
                if (!employeeIDText.isEmpty()) {
                    try {
                        employeeID = Integer.parseInt(employeeIDText);
                        List<RoleType> employeeRoles = null;
                        // Get the employee's roles from the facade
                        try {
                            employeeRoles = _facade.getEmployeeRoles(employeeID);
                            if (employeeRoles.isEmpty()) {
                                JOptionPane.showMessageDialog(RemoveRoleFromEmployee.this, "The employee has no roles.", "No Roles", JOptionPane.INFORMATION_MESSAGE);
                                roleComboBox.setEnabled(false);
                                removeRoleButton.setEnabled(false);
                            } else {
                                // Populate the roleComboBox with the employee's roles
                                roleComboBox.removeAllItems();
                                for (RoleType role : employeeRoles) {
                                    roleComboBox.addItem(role.toString());
                                }
                                roleComboBox.setEnabled(true);
                                removeRoleButton.setEnabled(true);
                            }
                        }catch (Exception ex){
                            JOptionPane.showMessageDialog(RemoveRoleFromEmployee.this, "there is no such employee", "No employee", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(RemoveRoleFromEmployee.this, "Invalid employee ID. Please enter a valid number.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(RemoveRoleFromEmployee.this, "Please enter an employee ID.", "Empty ID", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Add an ActionListener to the removeRoleButton
        removeRoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRoleIndex = roleComboBox.getSelectedIndex();
                if (selectedRoleIndex != -1) {
                    int confirmation = JOptionPane.showConfirmDialog(RemoveRoleFromEmployee.this, "Are you sure you want to remove the role?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        // Call the removeRoleFromEmployee function from the facade
                        _facade.removeRoleFromEmployee(employeeID, selectedRoleIndex - 1);
                        JOptionPane.showMessageDialog(RemoveRoleFromEmployee.this, "Role removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        roleComboBox.removeItemAt(selectedRoleIndex);
                        if (roleComboBox.getItemCount() == 0) {
                            roleComboBox.setEnabled(false);
                            removeRoleButton.setEnabled(false);
                        }
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
                new RemoveRoleFromEmployee();
            }
        });
    }
}
