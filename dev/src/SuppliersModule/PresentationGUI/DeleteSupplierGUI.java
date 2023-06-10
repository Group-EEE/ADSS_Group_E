package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteSupplierGUI {

    static SupplierController supplierController;
    static JFrame OldFrame;
    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();;
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = new JFrame("Delete Supplier");
        currFrame.setSize(500,500);
        currFrame.setLayout(null);

        //----------------------------------------- Create new label ----------------------------------------

        JLabel label = new JLabel("Enter supplier number that you want delete");
        label.setBounds(100,10,300,30);
        currFrame.add(label);

        //---------------------------------------- Create new textField ----------------------------------

        JTextField textField = new JTextField();
        textField.setBounds(150,200,200,30);
        currFrame.add(textField);

        //------------------------------------ Create backButton -------------------------------------

        JButton backButton = new JButton("Back");
        backButton.setBounds(200,370,100,30);
        backButton.addActionListener(new backClick(currFrame));
        currFrame.add(backButton);

        //------------------------------------ Create new comboBox -------------------------------------

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(100,50,300,30);
        comboBox.addActionListener(new MyComboBoxActionListener(textField));

        List <String> comboBoxItems = new ArrayList<>();
        comboBoxItems.add("");

        for (Map.Entry<String, Supplier> pair : supplierController.returnAllSuppliers().entrySet())
            comboBoxItems.add(pair.getKey());

        for (String item : comboBoxItems)
            comboBox.addItem(item);

        currFrame.add(comboBox);

        // ---------------------------------------------------------------------------------------------

        currFrame.setVisible(true);
    }

    private static class MyComboBoxActionListener implements ActionListener {
        JTextField textField;

        /**
         * Constructor
         * @param textField - Text field in currFrame.
         */
        public MyComboBoxActionListener(JTextField textField) {
            this.textField = textField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String supplierNum = (String) combo.getSelectedItem();

            if(!supplierNum.equals("")) {
                supplierController.fireSupplier(supplierNum);
                textField.setText("Supplier " + supplierNum + " has been deleted");
            }
            else
                textField.setText("");
        }
    }

    /** class that implements ActionListener.
     * actionPerformed when click on Back
     */
    private static class backClick implements ActionListener {
        private JFrame thisFrame;

        /**
         * Constructor
         * @param thisFrame - Current frame
         */
        public backClick(JFrame thisFrame) {
            this.thisFrame = thisFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            thisFrame.dispose();
            OldFrame.setVisible(true);
        }
    }
}
