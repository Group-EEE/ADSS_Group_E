package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class OrderHistoryGUI {

    static SupplierController supplierController;
    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = HelperFunctionGUI.createNewFrame("Order History");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel supplierNumLabel = new JLabel("Enter supplier number. (For all the suppliers choose 'ALL') ");

        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneHistory = new JScrollPane(textAreaHistory);
        scrollPaneHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> comboBoxAllSuppliers = HelperFunctionGUI.createComboBoxSupplierNum();
        comboBoxAllSuppliers.addItem("ALL");

        //----------------------------------------- Create JButton ----------------------------------------

        JButton exitButton = HelperFunctionGUI.createExitButton(currFrame, oldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------
        supplierNumLabel.setBounds(70,10,360,30);
        scrollPaneHistory.setBounds(10, 90, 480,300);
        comboBoxAllSuppliers.setBounds(100,50,300,30);

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(currFrame, new JComponent[]{supplierNumLabel,
                scrollPaneHistory, comboBoxAllSuppliers, exitButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxAllSuppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierNum = comboBoxAllSuppliers.getSelectedItem().toString();

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

        currFrame.setVisible(true);
    }
}
