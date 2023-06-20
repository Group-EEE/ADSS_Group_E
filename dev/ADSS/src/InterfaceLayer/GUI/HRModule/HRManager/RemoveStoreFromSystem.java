package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RemoveStoreFromSystem extends JFrame {
    private final Facade _facade = Facade.getInstance();

    public RemoveStoreFromSystem() {
        // Set the size and layout of the frame
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create 10 JLabel and JTextField pairs
        JLabel[] labels = new JLabel[1];
        labels[0] = new JLabel("Store name:");




        JTextField[] textFields = new JTextField[1];
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
        JButton createButton = new JButton("remove");

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


        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storename = textFields[0].getText();



                // Call your removeStore function and display a message
                if (_facade.removeStore(storename)) {
                    JOptionPane.showMessageDialog(null, "Store removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Return to the HRmenu screen
                    HRmenu hrmenu = new HRmenu();
                    hrmenu.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                }


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
                new RemoveStoreFromSystem();
            }
        });
    }
}

