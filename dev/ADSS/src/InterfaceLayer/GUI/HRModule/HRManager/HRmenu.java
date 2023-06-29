package InterfaceLayer.GUI.HRModule.HRManager;

import InterfaceLayer.GUI.All_Roles_GUI;
import InterfaceLayer.GUI.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HRmenu extends JFrame {
    public HRmenu() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HR Menu Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            JLabel welcomeLabel = new JLabel("Welcome to the Main Menu, please choose your action and press Start:");
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JComboBox<String> actionComboBox = new JComboBox<>(new String[]{"create new employee", "create new store", "add employee to store",
                    "add role to employee","create new schedule","approve shifts", "update personal information", "change schedule hours",
                    "remove role from employee", "remove employee from store", "remove employee from system", "remove store from system",
                    "select required roles from a shift", "remove required roles from a shift", "print all employees", "print all stores",
                    "print schedule", "create new driver", "create new logistics schedule", "log out"});
            actionComboBox.setPreferredSize(new Dimension(200, 20));
            actionComboBox.setMaximumSize(new Dimension(200, 20));

            JButton startButton = new JButton("Start");

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

            // Set up the BoxLayout
            JPanel panel = new JPanel(gridBagLayout);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Add components to the panel
            panel.add(Box.createVerticalGlue());
            panel.add(welcomeLabel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(actionComboBox);
            panel.add(Box.createVerticalStrut(10));
            panel.add(startButton);
            panel.add(Box.createVerticalGlue());

            // Add the panel to the frame
            frame.add(panel);

            // Show the frame
            frame.setVisible(true);

            // Add a WindowListener to the frame
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Create an instance of the main menu frame
                    //Login login = new Login();
                    //login.setVisible(true);
                    All_Roles_GUI all_roles_gui = new All_Roles_GUI("HRManager");
                    all_roles_gui.setVisible(true);
                }
            });

            startButton.addActionListener(e -> {
                String selectedAction = (String) actionComboBox.getSelectedItem();

                switch (selectedAction) {
                    case "create new employee":
                        CreateEmployee createEmployeeScreen = new CreateEmployee();
                        createEmployeeScreen.setVisible(true);
                        frame.dispose();
                        break;
                    case "create new store":
                        CreateStore createStore = new CreateStore();
                        createStore.setVisible(true);
                        frame.dispose();
                        break;
                    case "add employee to store":
                        AddEmployeeToStore addEmployeeToStore = new AddEmployeeToStore();
                        addEmployeeToStore.setVisible(true);
                        frame.dispose();
                        break;
                    case "add role to employee":
                        AddRoleToEmployee addRoleToEmployee = new AddRoleToEmployee();
                        addRoleToEmployee.setVisible(true);
                        frame.dispose();
                        break;
                    case "create new schedule":
                        CreateSchedual createSchedual = new CreateSchedual();
                        createSchedual.setVisible(true);
                        frame.dispose();
                        break;
                    case "approve shifts":
                        ApproveSchedule approveSchedule = new ApproveSchedule();
                        approveSchedule.setVisible(true);
                        frame.dispose();
                        break;
                    case "update personal information":
                        UpdatePersonalInformation updatePersonalInformation = new UpdatePersonalInformation();
                        updatePersonalInformation.setVisible(true);
                        frame.dispose();
                        break;
                    case "change schedule hours":
                        ChangeSchedualHours changeSchedualHours = new ChangeSchedualHours();
                        changeSchedualHours.setVisible(true);
                        frame.dispose();
                        break;
                    case "remove role from employee":
                        RemoveRoleFromEmployee removeRoleFromEmployee = new RemoveRoleFromEmployee();
                        removeRoleFromEmployee.setVisible(true);
                        frame.dispose();
                        break;
                    case "remove employee from store":
                        RemoveEmployeeFromStore removeEmployeeFromStore = new RemoveEmployeeFromStore();
                        removeEmployeeFromStore.setVisible(true);
                        frame.dispose();
                        break;
                    case "remove employee from system":
                        RemoveEmployeeFromSystem removeEmployeeFromSystem = new RemoveEmployeeFromSystem();
                        removeEmployeeFromSystem.setVisible(true);
                        frame.dispose();
                        break;
                    case "remove store from system":
                        RemoveStoreFromSystem removeStoreFromSystem = new RemoveStoreFromSystem();
                        removeStoreFromSystem.setVisible(true);
                        frame.dispose();
                        break;
                    case "select required roles from a shift":
                        SelectRequiredRolesFromShift selectRequiredRolesFromShift = new SelectRequiredRolesFromShift();
                        selectRequiredRolesFromShift.setVisible(true);
                        frame.dispose();
                        break;
                    case "remove required roles from a shift":
                        RemoveRequiredRolesFromShift removeRequiredRolesFromShift = new RemoveRequiredRolesFromShift();
                        removeRequiredRolesFromShift.setVisible(true);
                        frame.dispose();
                        break;
                    case "print all employees":
                        PrintEmployees printEmployees = new PrintEmployees();
                        printEmployees.setVisible(true);
                        frame.dispose();
                        break;
                    case "print all stores":
                        PrintStores printStores = new PrintStores();
                        printStores.setVisible(true);
                        frame.dispose();
                        break;
                    case "print schedule":
                        PrintSchedule printSchedule = new PrintSchedule();
                        printSchedule.setVisible(true);
                        frame.dispose();
                        break;
                    case "create new driver":
                        CreateDriver createDriver = new CreateDriver();
                        createDriver.setVisible(true);
                        frame.dispose();
                        break;
                    case "create new logistics schedule":
                        CreateNewLogisticSchedule createNewLogisticSchedule = new CreateNewLogisticSchedule();
                        createNewLogisticSchedule.setVisible(true);
                        frame.dispose();
                        break;
                    case "log out":
                        All_Roles_GUI all_roles_gui = new All_Roles_GUI("HRManager");
                        all_roles_gui.setVisible(true);
                        frame.dispose();
                        break;
                }
            });
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HRmenu::new);
    }
}
