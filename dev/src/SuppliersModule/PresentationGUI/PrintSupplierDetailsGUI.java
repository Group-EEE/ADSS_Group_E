package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PrintSupplierDetailsGUI {

    static SupplierController supplierController;
    static JFrame OldFrame;
    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = HelperFunctionGUI.createNewFrame("supplier details");

        //------------------------------------ Create JTextArea ---------------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //------------------------------------ Create JComboBox -------------------------------------

        JComboBox<String> comboBoxAllSuppliers = HelperFunctionGUI.createComboBoxSupplierNum();

        //----------------------------------------- Create JButton ----------------------------------------

        JButton exitButton = HelperFunctionGUI.createExitButton(currFrame, oldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        comboBoxAllSuppliers.setBounds(100,10,300,30);
        scrollPane.setBounds(50, 50, 400,300);;

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(currFrame, new JComponent[] {scrollPane,
                comboBoxAllSuppliers ,exitButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxAllSuppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierNum = comboBoxAllSuppliers.getSelectedItem().toString();

                if(!supplierNum.equals("")) {
                    String details = supplierController.StringSupplierDetails(supplierNum);
                    textArea.setText(details);
                }
                else
                    textArea.setText("");
            }});


        currFrame.setVisible(true);
    }
}
