package InterfaceLayer.GUI.HRModule.EmployeesGUI;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Shift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectShifts extends JFrame {
    private Facade _facade = Facade.getInstance();

    private JTextField storeNameField;
    private JPanel shiftPanel;
    private JButton submitButton;
    private JButton backButton;

    private List<JCheckBox> shiftCheckboxes;

    public SelectShifts() {
        setTitle("Shift Selection");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the store name input panel
        JPanel storeNamePanel = new JPanel();
        JLabel storeNameLabel = new JLabel("Store Name: ");
        storeNameField = new JTextField(20);
        storeNamePanel.add(storeNameLabel);
        storeNamePanel.add(storeNameField);
        add(storeNamePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeNameField.getText();
                populateShiftPanel(storeName);
            }
        });

        // Create the back button
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeesMenu employeesMenu = new EmployeesMenu();
                employeesMenu.setVisible(true);
                dispose();
            }
        });

        // Create the shift selection panel
        shiftPanel = new JPanel();
        shiftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        shiftPanel.add(submitButton);
        shiftPanel.add(backButton);

        add(shiftPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void populateShiftPanel(String storeName) {
        shiftPanel.removeAll();
        shiftCheckboxes = new ArrayList<>();

        // Check if employee works in the store
        boolean employeeWorksInStore = _facade.checkIfEmployeeWorkInStore(storeName);

        if (employeeWorksInStore) {
            // Get the shifts for the store
            List<Shift> shifts = _facade.getSchedule(storeName).getShifts();

            for (Shift shift : shifts) {
                JCheckBox shiftCheckbox = new JCheckBox(shift.toString());
                shiftPanel.add(shiftCheckbox);
                shiftCheckboxes.add(shiftCheckbox);
            }
        } else {
            JLabel noShiftsLabel = new JLabel("You don't work in this store.");
            shiftPanel.add(noShiftsLabel);
        }
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitSelections();
            }
        });
        shiftPanel.add(submitButton);
        revalidate();
        repaint();
    }

    private void submitSelections() {
        String storeName = storeNameField.getText();

        for (JCheckBox checkbox : shiftCheckboxes) {
            if (checkbox.isSelected()) {
                String text = checkbox.getText();
                String shiftID = text.substring(checkbox.getText().indexOf(":")+2, text.indexOf(","));
                try {
                    _facade.addEmployeeToShift(storeName, Integer.parseInt(shiftID));
                }
                catch (IllegalArgumentException e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Shifts selected and submitted successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectShifts();
            }
        });
    }
}