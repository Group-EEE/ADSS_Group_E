package InventoryModule.PresentationGUI;

import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;

public class SpecificProductGUI {
    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        //------------------------------------- Create new frame -------------------------------------------
        JFrame SpecificProductFrame = HelperFunctionGUI.createNewFrame("SpecificProduct");
        //----------------------------------------- Create JLabel ----------------------------------------
        //option 1 - Add new product to the store
        JLabel chooseLabel = new JLabel("Please choose an option");

        //----------------------------------------- Create JTextField ----------------------------------------

        //----------------------------------------- Create JComboBox ----------------------------------------

        //----------------------------------------- Create JButton ----------------------------------------

        //----------------------------------------- Set bounds ---------------------------------------------

        //-------------------------------------- Set not visible ---------------------------------------------

        //------------------------------------ Add to currFrame -------------------------------------

        // ------------------------------------- Add action listener to JObjects ------------------------------
        SpecificProductFrame.setVisible(true);
    }
}
