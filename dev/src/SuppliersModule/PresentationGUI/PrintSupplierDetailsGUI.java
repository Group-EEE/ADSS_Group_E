package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintSupplierDetailsGUI {

    static SupplierController supplierController;
    static JFrame OldFrame;
    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = new JFrame("supplier details");
        currFrame.setSize(500,500);
        currFrame.setLayout(null);

        //--------------------------------- Create textArea and scrollPane ------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 50, 400,300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        currFrame.add(scrollPane);

        //------------------------------------ Create new comboBox -------------------------------------

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(100,10,300,30);
        comboBox.addActionListener(new MyComboBoxActionListener(textArea));

        List<String> comboBoxItems = new ArrayList<>();
        comboBoxItems.add("");
        Map<String, Supplier> allSuppliers = supplierController.returnAllSuppliers();

        for (Map.Entry<String, Supplier> pair : allSuppliers.entrySet())
            comboBoxItems.add(pair.getKey());

        for (String item : comboBoxItems)
            comboBox.addItem(item);

        currFrame.add(comboBox);

        //------------------------------------ Create backButton -------------------------------------

        JButton backButton = new JButton("Back");
        backButton.setBounds(200,370,100,30);
        backButton.addActionListener(new backClick(currFrame));
        currFrame.add(backButton);

        //--------------------------------------------------------------------------------------------

        currFrame.setVisible(true);
    }

    /** class that implements ActionListener.
     * actionPerformed when set ComboBox
     */
    private static class MyComboBoxActionListener implements ActionListener {
        JTextArea textArea;

        /**
         * Constructor
         * @param textArea - Text area in currFrame.
         */
        public MyComboBoxActionListener(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String supplierNum = (String) combo.getSelectedItem();

            if(!supplierNum.equals("")) {
                String details = supplierController.StringSupplierDetails(supplierNum);
                textArea.setText(details);
            }
            else
                textArea.setText("");

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
