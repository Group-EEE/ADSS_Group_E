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

        // Create the shift selection panel
        shiftPanel = new JPanel();
        shiftPanel.setLayout(new BoxLayout(shiftPanel, BoxLayout.Y_AXIS));
        add(shiftPanel, BorderLayout.CENTER);

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitSelections();
            }
        });
        add(submitButton, BorderLayout.SOUTH);

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
                JCheckBox shiftCheckbox = new JCheckBox(String.valueOf(shift.getShiftID()));
                shiftPanel.add(shiftCheckbox);
                shiftCheckboxes.add(shiftCheckbox);
            }
        } else {
            JLabel noShiftsLabel = new JLabel("You don't work in this store.");
            shiftPanel.add(noShiftsLabel);
        }

        revalidate();
        repaint();
    }

    private void submitSelections() {
        String storeName = storeNameField.getText();

        for (JCheckBox checkbox : shiftCheckboxes) {
            if (checkbox.isSelected()) {
                String shiftID = checkbox.getText();
                _facade.addEmployeeToShift(storeName, Integer.parseInt(shiftID));
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
