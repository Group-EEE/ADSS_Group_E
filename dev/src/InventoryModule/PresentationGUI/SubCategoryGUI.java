package InventoryModule.PresentationGUI;

import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;

public class SubCategoryGUI {
    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        //------------------------------------- Create new frame -------------------------------------------
        JFrame SubCategoryFrame = HelperFunctionGUI.createNewFrame("SubCategory");
        //----------------------------------------- Create JLabel ----------------------------------------

        //----------------------------------------- Create JTextField ----------------------------------------

        //----------------------------------------- Create JComboBox ----------------------------------------

        //----------------------------------------- Create JButton ----------------------------------------

        //----------------------------------------- Set bounds ---------------------------------------------

        //-------------------------------------- Set not visible ---------------------------------------------

        //------------------------------------ Add to currFrame -------------------------------------

        // ------------------------------------- Add action listener to JObjects ------------------------------
        SubCategoryFrame.setVisible(true);
    }
}
