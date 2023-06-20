package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Controllers.StoreController;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class ApproveSchedule extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField storeNameField;
    private JButton nextButton;
    private JTextArea shiftStatusArea;
    private List<Shift> rejectedShifts;
    private int currentIndex;
    private boolean storeNameEntered;

    public ApproveSchedule() {
        // Set the size and layout of the frame
        setSize(400, 300);
        setLayout(new GridLayout(4, 1));

        // Create labels and input fields
        JLabel storeNameLabel = new JLabel("Store Name:");
        JLabel shiftStatusLabel = new JLabel("Shift Status:");

        storeNameField = new JTextField(20);
        nextButton = new JButton("Next");
        shiftStatusArea = new JTextArea();
        shiftStatusArea.setEditable(false);

        // Add labels, input fields, and button to the frame
        getContentPane().add(storeNameLabel);
        getContentPane().add(storeNameField);
        getContentPane().add(shiftStatusLabel);
        getContentPane().add(new JScrollPane(shiftStatusArea));
        getContentPane().add(nextButton);

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

        // Set the initial state
        nextButton.setEnabled(false);
        shiftStatusArea.setText("Please enter the store name and click Next.");
        storeNameEntered = false;

        // Add a FocusAdapter to the store name field
        storeNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                storeNameEntered = !storeNameField.getText().isEmpty();
                nextButton.setEnabled(storeNameEntered);
            }
        });

        // Add an ActionListener to the Next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextShift();
            }
        });

        // Set the frame to be visible
        setVisible(true);
    }

    private void showNextShift() {
        if (!storeNameEntered) {
            JOptionPane.showMessageDialog(ApproveSchedule.this, "Please enter a store name.", "Missing Information", JOptionPane.ERROR_MESSAGE);
            storeNameField.requestFocus();
            return;
        }

        String storeName = storeNameField.getText();
        try{
            _facade.getAllStores().contains(StoreController.getInstance().getStore(storeName));
        }catch (Exception ex){

            JOptionPane.showMessageDialog(ApproveSchedule.this, "store dosent exist", "Missing Information", JOptionPane.ERROR_MESSAGE);
        }
        try{
            rejectedShifts = _facade.approveSchedule(storeName);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(ApproveSchedule.this, "the store dosent have a schedual", "Missing Information", JOptionPane.ERROR_MESSAGE);
        }
        if (currentIndex < rejectedShifts.size()) {
            Shift shift = rejectedShifts.get(currentIndex);
            StringBuilder message = new StringBuilder("Shift ID: " + shift.getShiftID() + "\n");
            message.append("Store Name: ").append(storeName).append("\n");
            message.append("Start Time: ").append(shift.getStartHour()).append("\n");
            message.append("End Time: ").append(shift.getEndHour()).append("\n");
            message.append("Required Roles:\n");
            for (RoleType role : shift.getRequiredRoles()) {
                message.append("- ").append(role).append("\n");
            }

            List<RoleType> rolesToRemove = new ArrayList<>();

            for (RoleType role : shift.getRequiredRoles()) {
                int choice = JOptionPane.showConfirmDialog(ApproveSchedule.this, "The role " + role + " is missing in Shift ID " + shift.getShiftID() + ". Do you want to remove it from the required roles?", "Missing Role", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    rolesToRemove.add(role);
                }
            }

            if (!rolesToRemove.isEmpty()) {
                message.append("Removed Roles:\n");
                for (RoleType role : rolesToRemove) {
                    message.append("- ").append(role).append("\n");
                }
            }

            String[] options = {"Next Shift"};
            int choice = JOptionPane.showOptionDialog(ApproveSchedule.this, message.toString(), "Rejected Shift", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                currentIndex++;
                showNextShift();
            }
        } else {
            shiftStatusArea.setText("All rejected shifts have been processed.");
            nextButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ApproveSchedule();
            }
        });
    }
}