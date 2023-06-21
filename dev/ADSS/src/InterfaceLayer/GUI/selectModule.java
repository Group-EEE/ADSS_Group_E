package InterfaceLayer.GUI;

import InterfaceLayer.GUI.HRModule.HRManager.HRmenu;
import InterfaceLayer.TransportModule.GUI.Transport_main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class selectModule extends JFrame {
    private All_Roles_GUI all_roles_gui;
    public selectModule() {
        setTitle("Select Module");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Create the "HRModule" button
        JButton hrModuleButton = new JButton("HRModule");
        hrModuleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform actions when the "HRModule" button is clicked
                // Add your logic here
                HRmenu hrmenu = new HRmenu();
                dispose();
            }
        });
        panel.add(hrModuleButton);

        // Create the "TransportModule" button
        JButton transportModuleButton = new JButton("TransportModule");
        transportModuleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                all_roles_gui = new All_Roles_GUI("TransportModule");
                Transport_main transport_main = new Transport_main(all_roles_gui);
                transport_main.setVisible(true);
                dispose();
            }
        });
        panel.add(transportModuleButton);

        // Create the "Exit" button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform actions when the "Exit" button is clicked
                dispose(); // Close the form
            }
        });
        panel.add(exitButton);

        // Add the panel to the form's content pane
        getContentPane().add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        selectModule form = new selectModule();
    }
}