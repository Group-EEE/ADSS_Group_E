package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        labels[1] = new JLabel("role:");




        JTextField[] textFields = new JTextField[2];
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
        JButton createButton = new JButton("Add");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = -1;

                try{
                    id = Integer.parseInt(textFields[0].getText());
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "id must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                RoleType role = null;
                try {
                    role = RoleType.valueOf(textFields[1].getText());
                }catch (Exception exp){
                    JOptionPane.showMessageDialog(null, "no such role", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                // Call your createEmployee function and display a message
                if (_facade.addRoleToEmployee(id,role)) {
                    JOptionPane.showMessageDialog(null, "role" + role.toString() + "Added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Return to the HRmenu screen
                    HRmenu hrmenu = new HRmenu();
                    hrmenu.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "invalid input", "Error", JOptionPane.ERROR_MESSAGE);
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
                new AddRoleToEmployee();
            }
        });
    }
}

