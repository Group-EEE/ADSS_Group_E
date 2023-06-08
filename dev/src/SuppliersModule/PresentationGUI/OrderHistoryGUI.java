package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderHistoryGUI {

    static SupplierController supplierController;
    static JFrame OldFrame;
    public static void powerOn(SupplierController suppController, JFrame oldFrame)
    {
        supplierController = suppController;
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = new JFrame("Order History");
        currFrame.setSize(500,500);
        currFrame.setLayout(null);

        //----------------------------------------- Create new label ----------------------------------------

        JLabel label = new JLabel("Enter supplier number. (For all the suppliers choose 'ALL') ");
        label.setBounds(70,10,360,30);
        currFrame.add(label);

        //--------------------------------- Create textArea and scrollPane ------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 90, 480,300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        currFrame.add(scrollPane);

        //------------------------------------ Create new comboBox -------------------------------------

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(100,50,300,30);
        comboBox.addActionListener(new MyComboBoxActionListener(textArea));

        List<String> comboBoxItems = new ArrayList<>();
        comboBoxItems.add("");
        comboBoxItems.add("ALL");
        Map<String, Supplier> allSuppliers = supplierController.returnAllSuppliers();

        for (Map.Entry<String, Supplier> pair : allSuppliers.entrySet())
            comboBoxItems.add(pair.getKey());

        for (String item : comboBoxItems)
            comboBox.addItem(item);

        currFrame.add(comboBox);

        //------------------------------------ Create backButton -------------------------------------

        JButton backButton = new JButton("Back");
        backButton.setBounds(200,400,100,30);
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

            if(!supplierNum.equals("") && !(supplierNum.equals("ALL"))) {
                String details = supplierController.StringSupplierDetails(supplierNum);
                textArea.setText(details);
            }
            else if(supplierNum.equals(""))
                textArea.setText("");

            else {
                String allOrders = "";
                for (Map.Entry<String, Supplier> pair : supplierController.returnAllSuppliers().entrySet()) {
                    allOrders = allOrders + "********************* " + pair.getValue().getName() + " *********************\n";
                    allOrders = allOrders + pair.getValue().StringOrdersHistory() + "\n";
                }
                textArea.setText(allOrders);
            }
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
