package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddEmployeeToStore extends JFrame {
    private final Facade _facade = Facade.getInstance();

    public AddEmployeeToStore() {
        // Set the size and layout of the frame
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create 10 JLabel and JTextField pairs
        JLabel[] labels = new JLabel[2];
        labels[0] = new JLabel("Employee ID:");
        labels[1] = new JLabel("Store name:");





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
                int id = -1;

                try{
                    id = Integer.parseInt(textFields[0].getText());
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "id must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String store_name = textFields[1].getText();



                // Call your createEmployee function and display a message
                if (_facade.addEmployeeToStore(id,store_name)) {
                    JOptionPane.showMessageDialog(null, "Employee Added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
                new AddEmployeeToStore();
            }
        });
    }
}

