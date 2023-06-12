package InventoryModule.PresentationGUI;

import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UpdateDeletePeriodicOrderGUI {

    static OrderController orderController;
    public static void powerOn(JFrame oldFrame)
    {
        orderController = OrderController.getInstance();

        //------------------------------------- Create new frame -------------------------------------------

        JFrame subMenuPeriodicOrder = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //----------------------------------------- Create JButton ----------------------------------------

        JButton deletePeriodicOrderButton = new JButton("Delete Periodic Order");
        JButton changeDayButton = new JButton("Change the day for invite");
        JButton addProductButton = new JButton("Add product");
        JButton changeQuantityButton = new JButton("Change the quantity of product");
        JButton deleteProductButton = new JButton("Delete product");
        JButton ExitButton = HelperFunctionGUI.createExitButton(subMenuPeriodicOrder, oldFrame);

        deletePeriodicOrderButton.setBounds(100, 50, 300, 40);
        changeDayButton.setBounds(100, 100, 300, 40);
        addProductButton.setBounds(100, 150, 300, 40);
        changeQuantityButton.setBounds(100, 200, 300, 40);
        deleteProductButton.setBounds(100, 250, 300, 40);

        HelperFunctionGUI.addComponentsToFrame(subMenuPeriodicOrder, new JComponent[]{deletePeriodicOrderButton
        , changeDayButton, addProductButton, changeQuantityButton, deleteProductButton});

        deletePeriodicOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subMenuPeriodicOrder.setVisible(false);
                deletePeriodicOrderPage(subMenuPeriodicOrder);
            }
        });
        subMenuPeriodicOrder.setVisible(true);
    }

    public static void deletePeriodicOrderPage(JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page1Frame = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //------------------------------------ Create comboBox ---------------------------------------

        JComboBox<String> comboBoxBarcodes = HelperFunctionGUI.createComboBoxBarcodes();
        JComboBox<String> comboBoxIds = new JComboBox<>();

        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneOrders = new JScrollPane(textAreaHistory);
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton deleteButton = new JButton("Delete");
        JButton exitButton = HelperFunctionGUI.createExitButton(page1Frame, backFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        comboBoxBarcodes.setBounds(200, 10, 100, 20);
        scrollPaneOrders.setBounds(10, 50, 480,200);
        comboBoxIds.setBounds(200, 270, 100,20);
        deleteButton.setBounds(200, 300, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        HelperFunctionGUI.addComponentsToFrame(page1Frame, new JComponent[]{comboBoxBarcodes,
                scrollPaneOrders, comboBoxIds, deleteButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxBarcodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String barcode = comboBoxBarcodes.getSelectedItem().toString();

                if(barcode.equals(""))isValid = false;

                else
                {
                    List<String> relevantOrder = orderController.findAllPeriodicOrderThatContainThisBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                    String relevantOrdersString = "";
                    comboBoxIds.addItem("");
                    for(String currOrderString : relevantOrder) {
                        comboBoxIds.addItem(currOrderString.split("Id")[1]);
                        relevantOrdersString = relevantOrdersString + currOrderString + "\n";
                    }
                }
            }
        });

        page1Frame.setVisible(true);
    }
}
