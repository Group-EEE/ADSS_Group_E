package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.CategoryController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubCategoryGUI {
    static JFrame OldFrame;
    static CategoryController categoryController;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        categoryController = CategoryController.getInstance();
        //------------------------------------- Create new frame -------------------------------------------
        JFrame SubCategoryFrame = HelperFunctionGUI.createNewFrame("SubCategory");

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel chooseLabel = new JLabel("Please choose an option:");

        //option 1
        JLabel CategoryLabel = new JLabel("Category name:");
        JLabel newSubCategoryLabel = new JLabel("New subcategory name:");

        //option 2
        JLabel CategoryLabel2 = new JLabel("Category name:");
        JLabel SubCategoryLabel2 = new JLabel("Subcategory name:");

        //----------------------------------------- Create JTextField ----------------------------------------

        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseOptionComboBox = new JComboBox<>(new String[]{"", "Add new subcategory", "Remove subcategory"});

        //option 1
        JComboBox<String> chooseMainCategoryComboBox = HelperFunctionGUI.createComboBoxCategories();

        //option 2
        JComboBox<String> chooseMainCategoryComboBox2 = HelperFunctionGUI.createComboBoxCategories();

        //----------------------------------------- Create JButton ----------------------------------------

        JButton exitButton = HelperFunctionGUI.createExitButton(SubCategoryFrame, oldFrame);

        //option 1
        JButton AddSubcategoryJButton = new JButton("Add subcategory");

        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField addSubCategoryField = new JTextField();


        //----------------------------------------- Set bounds ---------------------------------------------
        chooseLabel.setBounds(30, 40, 150, 20);
        chooseOptionComboBox.setBounds(180, 40, 250, 20);

        //option 1
        newSubCategoryLabel.setBounds(50, 150, 150, 20);
        CategoryLabel.setBounds(50, 100, 150, 20);
        chooseMainCategoryComboBox.setBounds(160, 100, 250, 20);
        addSubCategoryField.setBounds(200, 150, 145, 25);
        AddSubcategoryJButton.setBounds(200, 200, 145, 25);

        //option 2
        CategoryLabel2.setBounds(50, 100, 150, 20);
        SubCategoryLabel2.setBounds(50, 150, 150, 20);
        chooseMainCategoryComboBox2.setBounds(160, 100, 250, 20);

        //-------------------------------------- Set not visible ---------------------------------------------

        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(SubCategoryFrame, new JComponent[] {chooseMainCategoryComboBox2,SubCategoryLabel2,CategoryLabel2,AddSubcategoryJButton,exitButton,addSubCategoryField,addSubCategoryField,CategoryLabel,chooseMainCategoryComboBox,newSubCategoryLabel,chooseLabel, chooseOptionComboBox});

        //option 1
        JComponent[] JComponentsNewCategory = new JComponent[]{AddSubcategoryJButton,addSubCategoryField,CategoryLabel,newSubCategoryLabel, chooseMainCategoryComboBox};
        HelperFunctionGUI.hideComponents(JComponentsNewCategory);

        //option 2
        JComponent[] JComponentsRemoveSubCategory = new JComponent[]{chooseMainCategoryComboBox2, SubCategoryLabel2, CategoryLabel2};
        HelperFunctionGUI.hideComponents(JComponentsRemoveSubCategory);

        // ------------------------------------- Add action listener to JObjects ------------------------------
        chooseOptionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseOptionComboBox.getSelectedItem().toString();
                if (choose.equals("")) {
                    HelperFunctionGUI.hideComponents(JComponentsNewCategory);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveSubCategory);
                }
                if(choose.equals("Add new subcategory")){
                    HelperFunctionGUI.showComponents(JComponentsNewCategory);
                    AddSubcategoryJButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String mainCategory = chooseMainCategoryComboBox.getActionCommand().toString();
                            String newsubCategoryName = addSubCategoryField.getText().toString();
                            if(!newsubCategoryName.equals("")){
                                CategoryController.addSubCategory(mainCategory, newsubCategoryName);
                                HelperFunctionGUI.ShowProcessSuccessfully();
                            }
                        }
                    });


                }
                if(choose.equals("Remove subcategory")){
                    HelperFunctionGUI.showComponents(JComponentsRemoveSubCategory);
                    HelperFunctionGUI.hideComponents(JComponentsNewCategory);
                }
            }
        });


        SubCategoryFrame.setVisible(true);
    }
}
