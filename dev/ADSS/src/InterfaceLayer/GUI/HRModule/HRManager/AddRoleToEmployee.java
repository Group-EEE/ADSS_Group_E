package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;
//todo: find out how to make only one pop massage
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddRoleToEmployee extends JFrame {
    private final Facade _facade = Facade.getInstance();

    public AddRoleToEmployee() {
        // Set the size and layout of the frame
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create 10 JLabel and JTextField pairs
        JLabel[] labels = new JLabel[2];
        labels[0] = new JLabel("Employee ID:");
        labels[1] = new JLabel("Role:");

        JTextField[] textFields = new JTextField[1];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new JTextField(10);
        }

        // Create a combo box for the roles
        JComboBox<RoleType> roleComboBox = new JComboBox<>(RoleType.values());

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

        // Add labels, text fields, and combo box to the frame
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(labels[0], gbc);

        gbc.gridx = 1;
        getContentPane().add(textFields[0], gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(labels[1], gbc);

        gbc.gridx = 1;
        getContentPane().add(roleComboBox, gbc);

        // Create an "Add" button and add an ActionListener
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = -1;

                try {
                    id = Integer.parseInt(textFields[0].getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ID must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                RoleType role = (RoleType) roleComboBox.getSelectedItem();
                List<RoleType> employeeroles = _facade.getEmployeeRoles(id);
                for(int i = 0; i < employeeroles.size(); i++){
                    if(role.equals(employeeroles.get(i))){
                        JOptionPane.showMessageDialog(null, _facade.getEmployeeFullNameById(id) + " already have " + role.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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

        // Add the "Add" button to the frame
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(addButton, gbc);

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
