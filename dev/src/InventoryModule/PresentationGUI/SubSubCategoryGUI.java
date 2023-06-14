package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.CategoryController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;

public class SubSubCategoryGUI {
    static JFrame OldFrame;
    static CategoryController categoryController;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        categoryController = CategoryController.getInstance();
        //------------------------------------- Create new frame -------------------------------------------
        JFrame SubSubCategoryFrame = HelperFunctionGUI.createNewFrame("SubSubCategory");
        //----------------------------------------- Create JLabel ----------------------------------------

        //----------------------------------------- Create JTextField ----------------------------------------

        //----------------------------------------- Create JComboBox ----------------------------------------

        //----------------------------------------- Create JButton ----------------------------------------

        //----------------------------------------- Set bounds ---------------------------------------------

        //-------------------------------------- Set not visible ---------------------------------------------

        //------------------------------------ Add to currFrame -------------------------------------

        // ------------------------------------- Add action listener to JObjects ------------------------------
        SubSubCategoryFrame.setVisible(true);
    }
}
