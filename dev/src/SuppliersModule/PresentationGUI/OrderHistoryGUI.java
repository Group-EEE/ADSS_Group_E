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

    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = new JFrame("Order History");
        currFrame.setSize(500,500);
        currFrame.setLayout(null);

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel supplierNumLabel = new JLabel("Enter supplier number. (For all the suppliers choose 'ALL') ");

        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneHistory = new JScrollPane(textAreaHistory);
        scrollPaneHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxAllSuppliers = new JComboBox<>(new String[]{"","ALL"});
        List<String> comboBoxAllSuppliersItems = new ArrayList<>();
        Map<String, Supplier> allSuppliers = supplierController.returnAllSuppliers();

        for (Map.Entry<String, Supplier> pair : allSuppliers.entrySet())
            comboBoxAllSuppliersItems.add(pair.getKey());

        for (String item : comboBoxAllSuppliersItems)
            comboBoxAllSuppliers.addItem(item);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton backButton = new JButton("Back");

        //-------------------------------------- Set bounds ---------------------------------------------
        supplierNumLabel.setBounds(70,10,360,30);
        scrollPaneHistory.setBounds(10, 90, 480,300);
        comboBoxAllSuppliers.setBounds(100,50,300,30);
        backButton.setBounds(200,400,100,30);

        //------------------------------------ Add to currFrame -------------------------------------
        currFrame.add(supplierNumLabel);
        currFrame.add(scrollPaneHistory);
        currFrame.add(comboBoxAllSuppliers);
        currFrame.add(backButton);

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxAllSuppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String supplierNum = (String) combo.getSelectedItem();

                if(!supplierNum.equals("") && !(supplierNum.equals("ALL"))) {
                    String ordersHistory = supplierController.getSupplier(supplierNum).StringOrdersHistory();
                    textAreaHistory.setText(ordersHistory);
                }

                else if(supplierNum.equals(""))
                    textAreaHistory.setText("");

                else {
                    String allOrders = "";
                    for (Map.Entry<String, Supplier> pair : supplierController.returnAllSuppliers().entrySet()) {
                        allOrders = allOrders + "********************* " + pair.getValue().getName() + " *********************\n";
                        allOrders = allOrders + pair.getValue().StringOrdersHistory() + "\n";
                    }

                    textAreaHistory.setText(allOrders);
                }
            }});

        //**********************************************************************

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.dispose();
                OldFrame.setVisible(true);
            }});

        currFrame.setVisible(true);
    }
}
