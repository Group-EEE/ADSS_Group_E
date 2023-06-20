package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateSchedual extends JFrame {
    private final Facade _facade = Facade.getInstance();

    public CreateSchedual() {
        // Set the size and layout of the frame
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create 10 JLabel and JTextField pairs
        JLabel[] labels = new JLabel[4];
        labels[0] = new JLabel("Store name:");
        labels[1] = new JLabel("start day:");
        labels[2] = new JLabel("start month:");
        labels[3] = new JLabel("start year:");




        JTextField[] textFields = new JTextField[4];
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
        JButton createButton = new JButton("Create");

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
                int day = -1;
                int month = -1;
                int year = -1;
                try{
                    day = Integer.parseInt(textFields[1].getText());
                    month = Integer.parseInt(textFields[2].getText());
                    year = Integer.parseInt(textFields[3].getText());
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "date parts must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String name = textFields[0].getText();
                if(day < 1 || day > 31){
                    JOptionPane.showMessageDialog(null, "day must be between 1 to 31", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (month < 1 || month > 12){
                    JOptionPane.showMessageDialog(null, "month must be between 1 to 12", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (year < 2020 || year > 2025){
                    JOptionPane.showMessageDialog(null, "year must be between 2020 to 2025", "Error", JOptionPane.ERROR_MESSAGE);
                }



                // Call your createEmployee function and display a message
                if (_facade.createNewStoreSchedule(name, day, month, year)) {
                    JOptionPane.showMessageDialog(null, "Schedual created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
                new CreateSchedual();
            }
        });
    }
}

