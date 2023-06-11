package InterfaceLayer.GUI.HRModule;

import javax.swing.*;
import java.awt.*;

public class HRmenu extends JFrame {
    public HRmenu() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HR Menu Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            JLabel welcomeLabel = new JLabel("Welcome to the Main Menu, please choose your action and press Start:");

            JComboBox<String> actionComboBox = new JComboBox<>(new String[]{"create new employee", "create new store", "add employee to store",
                    "add role to employee","create new schedule","approve shifts", "update personal information", "change schedule hours",
                    "remove role from employee", "remove employee from store", "remove employee from system", "remove store from system",
                    "select required roles from a shift", "remove required roles from a shift", "print all employees", "print all stores",
                    "print schedule", "create new driver", "create new logistics schedule", "log out"});
            actionComboBox.setPreferredSize(new Dimension(100, 20));
            actionComboBox.setMaximumSize(new Dimension(100, Integer.MAX_VALUE));

            // Set up the GridBagLayout
            GridBagLayout gridBagLayout = new GridBagLayout();
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.fill = GridBagConstraints.BOTH;

            JButton startButton = new JButton("Start");

            // Set up the BoxLayout
            JPanel panel = new JPanel(gridBagLayout);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Add components to the panel
            panel.add(Box.createVerticalGlue());
            panel.add(welcomeLabel);
            panel.add(Box.createVerticalGlue());
            panel.add(actionComboBox);
            panel.add(Box.createVerticalGlue());
            panel.add(startButton);
            panel.add(Box.createVerticalGlue());

            // Add the panel to the frame
            frame.add(panel);

            // Show the frame
            frame.setVisible(true);

            startButton.addActionListener(e -> {
                String selectedAction = (String) actionComboBox.getSelectedItem();
                int choice = Integer.parseInt(selectedAction);

                switch (choice) {
                    case 1:
                        // Open screen for Action 1
                        break;
                    case 2:
                        // Open screen for Action 2
                        break;
                    case 3:
                        // Open screen for Action 3
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
            });
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HRmenu();
            }
        });
    }
}
