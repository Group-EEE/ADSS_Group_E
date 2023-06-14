package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.CategoryController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubSubCategoryGUI {
    static JFrame OldFrame;
    static CategoryController categoryController;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        categoryController = CategoryController.getInstance();
        //------------------------------------- Create new frame -------------------------------------------
        JFrame SubSubCategoryFrame = HelperFunctionGUI.createNewFrame("SubSubCategory");
        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel chooseLabel = new JLabel("Please choose an option:");

        //option 1
        JLabel CategoryLabel = new JLabel("Category name:");
        JLabel SubCategoryLabel = new JLabel("Subcategory name:");
        JLabel newSubSubCategoryLabel = new JLabel("New subsubcategory name:");

        //option 2
        JLabel CategoryLabel2 = new JLabel("Category name:");
        JLabel SubCategoryLabel2 = new JLabel("Subcategory name:");
        JLabel SubSubCategoryLabel2 = new JLabel("Subsubcategory name:");
        JLabel cantRemoveSub = HelperFunctionGUI.createCheckLabel("Can't remove subsubcategory", 170, 250, 175, 20);

        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField addSubSubCategoryField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseOptionComboBox = new JComboBox<>(new String[]{"", "Add new subsubcategory", "Remove subsubcategory"});

        //option 1
        JComboBox<String> chooseMainCategoryComboBox = HelperFunctionGUI.createComboBoxCategories();
        JComboBox<String> chooseSubCategoryComboBox = new JComboBox<>();

        //option 2
        JComboBox<String> chooseMainCategoryComboBox2 = HelperFunctionGUI.createComboBoxCategories();
        JComboBox<String> chooseSubCategoryComboBox2 = new JComboBox<>();
        JComboBox<String> chooseSubSubCategoryComboBox2 = new JComboBox<>();


        //----------------------------------------- Create JButton ----------------------------------------
        JButton exitButton = HelperFunctionGUI.createExitButton(SubSubCategoryFrame, oldFrame);

        //option 1
        JButton AddSubSubcategoryJButton = new JButton("Add subsubcategory");

        //option 2
        JButton RemoveSubSubcategoryJButton = new JButton("Remove subsubcategory");

        //----------------------------------------- Set bounds ---------------------------------------------
        chooseLabel.setBounds(30, 40, 150, 20);
        chooseOptionComboBox.setBounds(180, 40, 250, 20);

        //option 1
        CategoryLabel.setBounds(50, 100, 150, 20);
        SubCategoryLabel.setBounds(50, 150, 150, 20);
        newSubSubCategoryLabel.setBounds(50, 200, 200, 20);
        chooseMainCategoryComboBox.setBounds(160, 100, 250, 20);
        chooseSubCategoryComboBox.setBounds(170, 150, 240, 20);
        addSubSubCategoryField.setBounds(220, 200, 200, 25);
        AddSubSubcategoryJButton.setBounds(175, 250, 160, 25);

        //option 2
        CategoryLabel2.setBounds(50, 100, 150, 20);
        SubCategoryLabel2.setBounds(50, 150, 150, 20);
        SubSubCategoryLabel2.setBounds(50, 200, 150, 20);
        chooseMainCategoryComboBox2.setBounds(160, 100, 250, 20);
        chooseSubCategoryComboBox2.setBounds(170, 150, 240, 20);
        chooseSubSubCategoryComboBox2.setBounds(220, 200, 200, 25);
        RemoveSubSubcategoryJButton.setBounds(160, 300, 200, 25);

        //-------------------------------------- Set not visible ---------------------------------------------
        cantRemoveSub.setVisible(false);

        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(SubSubCategoryFrame, new JComponent[] {cantRemoveSub,
                AddSubSubcategoryJButton,addSubSubCategoryField,chooseSubCategoryComboBox,
                chooseMainCategoryComboBox,newSubSubCategoryLabel,SubCategoryLabel,CategoryLabel,
                exitButton,chooseLabel,chooseOptionComboBox,CategoryLabel2, SubCategoryLabel2
                ,chooseSubCategoryComboBox2,SubSubCategoryLabel2, cantRemoveSub, chooseMainCategoryComboBox2,
                chooseSubSubCategoryComboBox2,RemoveSubSubcategoryJButton});

        //option 1
        JComponent[] JComponentsNewCategory = new JComponent[]{AddSubSubcategoryJButton,addSubSubCategoryField,chooseSubCategoryComboBox,chooseMainCategoryComboBox,newSubSubCategoryLabel,SubCategoryLabel,CategoryLabel};
        HelperFunctionGUI.hideComponents(JComponentsNewCategory);

        //option 2
        JComponent[] JComponentsRemoveCategory = new JComponent[]{CategoryLabel2, SubCategoryLabel2,
                chooseSubCategoryComboBox2,chooseMainCategoryComboBox2 , SubSubCategoryLabel2
                ,chooseSubSubCategoryComboBox2, RemoveSubSubcategoryJButton};
        HelperFunctionGUI.hideComponents(JComponentsRemoveCategory);


        // ------------------------------------- Add action listener to JObjects ------------------------------
        chooseOptionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseOptionComboBox.getSelectedItem().toString();
                if (choose.equals("")) {
                    cantRemoveSub.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsNewCategory);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveCategory);
                }
                if(choose.equals("Add new subsubcategory")) {
                    cantRemoveSub.setVisible(false);
                    HelperFunctionGUI.showComponents(JComponentsNewCategory);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveCategory);
                    chooseMainCategoryComboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String choose = chooseMainCategoryComboBox.getSelectedItem().toString();
                            HelperFunctionGUI.setSubCategoriesComboBoxField(choose, chooseSubCategoryComboBox);
                        }
                    });
                    AddSubSubcategoryJButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String mainCategory = chooseMainCategoryComboBox.getActionCommand().toString();
                            String subCategory = chooseSubCategoryComboBox.getActionCommand().toString();
                            String newSubSubCategoryName = AddSubSubcategoryJButton.getText().toString();
                            if(!newSubSubCategoryName.equals("")){
                                CategoryController.addSubSubCategory(subCategory,newSubSubCategoryName,mainCategory);
                                HelperFunctionGUI.ShowProcessSuccessfully();
                            }
                        }
                    });

                }
                if(choose.equals("Remove subsubcategory")) {
                    HelperFunctionGUI.hideComponents(JComponentsNewCategory);
                    HelperFunctionGUI.showComponents(JComponentsRemoveCategory);

                    chooseMainCategoryComboBox2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String choose2 = chooseMainCategoryComboBox2.getSelectedItem().toString();
                            if(!choose2.equals(""))
                                HelperFunctionGUI.setSubCategoriesComboBoxField(choose2, chooseSubCategoryComboBox2);
                        }
                    });
                    chooseSubCategoryComboBox2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String mainChoose2 = chooseMainCategoryComboBox2.getSelectedItem().toString();
                            String subChoose2 = chooseSubCategoryComboBox2.getSelectedItem().toString();
                            if(!mainChoose2.equals("") && !subChoose2.equals(""))
                                HelperFunctionGUI.setSubSubCategoriesComboBoxField(mainChoose2,subChoose2, chooseSubSubCategoryComboBox2);
                        }
                    });
                    RemoveSubSubcategoryJButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(HelperFunctionGUI.canRemoveSubSubCategory(chooseSubSubCategoryComboBox2.getSelectedItem().toString()) &&
                                    !chooseMainCategoryComboBox2.getSelectedItem().toString().equals("")&&
                                    !chooseSubCategoryComboBox2.getSelectedItem().toString().equals("")){
                                cantRemoveSub.setVisible(true);
                            }
                            else{
                                String mainChoose2 = chooseMainCategoryComboBox2.getSelectedItem().toString();
                                String subChoose2 = chooseSubCategoryComboBox2.getSelectedItem().toString();
                                String subsubChoose = chooseSubSubCategoryComboBox2.getSelectedItem().toString();
                                CategoryController.addSubSubCategory(subChoose2, subsubChoose, mainChoose2);
                                HelperFunctionGUI.ShowProcessSuccessfully();
                            }
                        }
                    });

                }
            }
        });

        SubSubCategoryFrame.setVisible(true);
    }
}
