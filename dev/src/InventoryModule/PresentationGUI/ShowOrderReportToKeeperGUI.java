package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ReportController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowOrderReportToKeeperGUI {
    static JFrame OldFrame;
    static ReportController reportController;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        reportController = ReportController.getInstance();
        //------------------------------------- Create new frame -------------------------------------------
        JFrame ShowOrderReportFrame = HelperFunctionGUI.createNewFrame("Show Order Report");

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel nameOfPublisherLabel = new JLabel("Please enter Issue's reporter's name:");


        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField nameOfPublisherField = new JTextField();


        //----------------------------------------- Create JComboBox ----------------------------------------



        //----------------------------------------- Create JButton ----------------------------------------
        JButton exitButton = HelperFunctionGUI.createExitButton(ShowOrderReportFrame, oldFrame);



        //----------------------------------------- Set bounds ---------------------------------------------
        nameOfPublisherLabel.setBounds(30, 40, 250, 20);
        nameOfPublisherField.setBounds(250, 40, 145, 25);

        //-------------------------------------- Set not visible ---------------------------------------------


        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(ShowOrderReportFrame, new JComponent[] {nameOfPublisherField,exitButton, nameOfPublisherLabel});

        // ------------------------------------- Add action listener to JObjects ------------------------------


        ShowOrderReportFrame.setVisible(true);
    }
}
