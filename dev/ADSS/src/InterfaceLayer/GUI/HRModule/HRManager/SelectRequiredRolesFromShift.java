package InterfaceLayer.GUI.HRModule.HRManager;


import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.Shift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class SelectRequiredRolesFromShift extends JFrame {
    private final Facade _facade = Facade.getInstance();
    private JTextField storeNameField;
    private JComboBox<Shift> shiftComboBox;
    private JComboBox<RoleType> roleComboBox;
    private JCheckBox mustBeFilledCheckBox;

    public SelectRequiredRolesFromShift() {
        // Set the size and layout of the frame
        setSize(400, 200);
        setLayout(new GridLayout(5, 2));

        // Create labels and input fields
        JLabel storeNameLabel = new JLabel("Store Name:");
        JLabel shiftLabel = new JLabel("Shift:");
        JLabel roleLabel = new JLabel("Role:");
        JLabel mustBeFilledLabel = new JLabel("Must be Filled:");

        storeNameField = new JTextField(20);
        shiftComboBox = new JComboBox<>();
        roleComboBox = new JComboBox<>(RoleType.values());
        mustBeFilledCheckBox = new JCheckBox();

        // Add labels and input fields to the frame
        getContentPane().add(storeNameLabel);
        getContentPane().add(storeNameField);
        getContentPane().add(shiftLabel);
        getContentPane().add(shiftComboBox);
        getContentPane().add(roleLabel);
        getContentPane().add(roleComboBox);
        getContentPane().add(mustBeFilledLabel);
        getContentPane().add(mustBeFilledCheckBox);

        storeNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                populateShiftComboBox();
            }
        });

        // Create an "Add" button and add an ActionListener
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeNameField.getText();
                int shiftID = shiftComboBox.getSelectedIndex();
                RoleType role = (RoleType) roleComboBox.getSelectedItem();
                boolean mustBeFilled = mustBeFilledCheckBox.isSelected();

                try {
                    boolean success = _facade.addRequiredRoleToShift(storeName, shiftID, role, mustBeFilled);
                    if (success) {
                        JOptionPane.showMessageDialog(SelectRequiredRolesFromShift.this, "Required role added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(SelectRequiredRolesFromShift.this, "Failed to add required role.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SelectRequiredRolesFromShift.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the "Add" button to the frame
        getContentPane().add(addButton);

        // Set the frame to be visible
        setVisible(true);

        // Add ActionListener to the storeNameField to update shiftComboBox when the store name changes
        storeNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateShiftComboBox();
            }
        });
    }


    private void populateShiftComboBox() {
        String storeName = storeNameField.getText();
        List<Shift> shifts = _facade.getSchedule(storeName).getShifts();
        shiftComboBox.removeAllItems();
        for (Shift shift : shifts) {
            shiftComboBox.addItem(shift);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectRequiredRolesFromShift();
            }
        });
    }
}