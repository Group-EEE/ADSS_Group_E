package InterfaceLayer.GUI.HRModule.HRManager;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RemoveRequiredRolesFromShift extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField storeNameField;
    private JComboBox<Shift> shiftComboBox;
    private JComboBox<RoleType> roleComboBox;

    public RemoveRequiredRolesFromShift() {
        // Set the size and layout of the frame
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        // Create labels and input fields
        JLabel storeNameLabel = new JLabel("Store Name:");
        JLabel shiftLabel = new JLabel("Shift:");
        JLabel roleLabel = new JLabel("Role:");

        storeNameField = new JTextField(20);
        shiftComboBox = new JComboBox<>();
        roleComboBox = new JComboBox<>();

        // Add labels and input fields to the frame
        getContentPane().add(storeNameLabel);
        getContentPane().add(storeNameField);
        getContentPane().add(shiftLabel);
        getContentPane().add(shiftComboBox);
        getContentPane().add(roleLabel);
        getContentPane().add(roleComboBox);

        // Add FocusAdapter to the storeNameField to update shiftComboBox when the store name changes
        storeNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                populateShiftComboBox();
            }
        });

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



        // Add ActionListener to the shiftComboBox to populate roleComboBox with required roles for the selected shift
        shiftComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shift selectedShift = (Shift) shiftComboBox.getSelectedItem();
                List<RoleType> requiredRoles = selectedShift.getRequiredRoles();
                roleComboBox.removeAllItems();
                for (RoleType role : requiredRoles) {
                    roleComboBox.addItem(role);
                }
            }
        });

        // Create a "Remove" button and add an ActionListener
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeNameField.getText();
                Shift selectedShift = (Shift) shiftComboBox.getSelectedItem();
                RoleType role = (RoleType) roleComboBox.getSelectedItem();

                try {
                    _facade.removeRequiredRoleFromShift(storeName, selectedShift.getShiftID(), role);
                    JOptionPane.showMessageDialog(RemoveRequiredRolesFromShift.this, "Required role removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RemoveRequiredRolesFromShift.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the "Remove" button to the frame
        getContentPane().add(removeButton);

        // Set the frame to be visible
        setVisible(true);
    }

    private void populateShiftComboBox() {
        String storeName = storeNameField.getText();
        List<Shift> shifts = null;
        try{
            shifts = _facade.getSchedule(storeName).getShifts();
        }catch (Exception ex){}
        shiftComboBox.removeAllItems();
        if (shifts != null) {
            for (Shift shift : shifts) {
                shiftComboBox.addItem(shift);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RemoveRequiredRolesFromShift();
            }
        });
    }
}