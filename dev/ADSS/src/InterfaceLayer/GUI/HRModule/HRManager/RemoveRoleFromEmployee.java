package InterfaceLayer.GUI.HRModule.HRManager;
//TODO: layoutfix
import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveRoleFromEmployee extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField employeeIDField;
    private JComboBox<String> roleComboBox;
    private int employeeID;

    public RemoveRoleFromEmployee() {
        // Set the size and layout of the frame
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Create a label and text field for employee ID
        JLabel employeeIDLabel = new JLabel("Employee ID:");
        employeeIDField = new JTextField(20);

        // Add the employee ID label and text field to the frame
        getContentPane().add(employeeIDLabel);
        getContentPane().add(employeeIDField);

        // Create a button to get the employee's roles
        JButton getRolesButton = new JButton("Get Roles");
        getContentPane().add(getRolesButton);

        // Create a combo box to display the employee's roles
        roleComboBox = new JComboBox<>();
        roleComboBox.setEnabled(false);
        getContentPane().add(roleComboBox);

        // Create a button to remove the selected role
        JButton removeRoleButton = new JButton("Remove Role");
        removeRoleButton.setEnabled(false);
        getContentPane().add(removeRoleButton);

        // Add an ActionListener to the getRolesButton
        getRolesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeIDText = employeeIDField.getText();
                if (!employeeIDText.isEmpty()) {
                    try {
                        employeeID = Integer.parseInt(employeeIDText);

                        // Get the employee's roles from the facade
                        List<RoleType> employeeRoles = _facade.getEmployeeRoles(employeeID);
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
