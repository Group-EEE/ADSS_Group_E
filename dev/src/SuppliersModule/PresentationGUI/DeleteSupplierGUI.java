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

        JFrame currFrame = HelperFunctionGUI.createNewFrame("Delete Supplier");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel supplierNumLabel = new JLabel("Enter supplier number that you want delete");

        //---------------------------------------- Create new textField ----------------------------------

        JTextField deleteField = new JTextField();

        //------------------------------------ Create JButton -------------------------------------

        JButton exitButton = HelperFunctionGUI.createExitButton(currFrame, oldFrame);

        //------------------------------------ Create JComboBox -------------------------------------

        JComboBox<String> comboBoxSupplierNum = HelperFunctionGUI.createComboBoxSupplierNum();

        //-------------------------------------- Set bounds ---------------------------------------------

        supplierNumLabel.setBounds(100,10,300,30);
        comboBoxSupplierNum.setBounds(100,50,300,30);
        deleteField.setBounds(150,200,200,30);

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(currFrame, new JComponent[] {supplierNumLabel,
                comboBoxSupplierNum, deleteField, exitButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxSupplierNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierNum = comboBoxSupplierNum.getSelectedItem().toString();

                if(!supplierNum.equals("")) {
                    supplierController.fireSupplier(supplierNum);
                    deleteField.setText("Supplier " + supplierNum + " has been deleted");
                }
                else
                    deleteField.setText("");
            }});

        currFrame.setVisible(true);
    }
}
