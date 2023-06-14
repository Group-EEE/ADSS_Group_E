package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.CategoryController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriesGUI {
    static JFrame OldFrame;
    static CategoryController categoryController;
    public static void powerOn(JFrame oldFrame) {

        OldFrame = oldFrame;
        categoryController = CategoryController.getInstance();

        //------------------------------------- Create new frame -------------------------------------------

        JFrame categoriesFrame = HelperFunctionGUI.createNewFrame("Categories");
        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel chooseLabel = new JLabel("Please choose an option:");
        //option 1 - get all categories' names
        JLabel showAllCategories = new JLabel("All Categories:");

        //option 2- add new category
        JLabel newCategoryLabel = new JLabel("New category name:");

        //option 3- remove category
        JLabel removeCategoryLabel = new JLabel("Choose category to remove:");
        JLabel cantRemoveLabel = HelperFunctionGUI.createCheckLabel("Can't remove category", 175, 150, 150, 20);
        JLabel categoryExist = HelperFunctionGUI.createCheckLabel("Category already exist", 175, 150, 150, 20);

        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField addCategoryField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseOptionComboBox = new JComboBox<>(new String[]{"", "Get all category names",
                "Add new category", "Remove category"});
        JComboBox<String> chooseCategoryToRemove = HelperFunctionGUI.createComboBoxCategories();


        //------------------------------------ Create JTextArea ---------------------------------------
        //option1
        JTextArea textAreaCategories = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textAreaCategories);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //----------------------------------------- Create JButton ----------------------------------------

        JButton exitButton = HelperFunctionGUI.createExitButton(categoriesFrame, oldFrame);
        //option 2
        JButton AddCategoryJButton = new JButton("Add Category");
        //option 3
        JButton RemoveCategoryJButton = new JButton("Remove Category");


        //-------------------------------------- Set bounds ---------------------------------------------
        chooseLabel.setBounds(30, 40, 150, 20);
        chooseOptionComboBox.setBounds(180, 40, 250, 20);

        //option 1
        showAllCategories.setBounds(40, 60, 150, 20);
        scrollPane.setBounds(40, 80, 400,300);

        //option 2
        newCategoryLabel.setBounds(40, 100, 150, 20);
        AddCategoryJButton.setBounds(325, 100, 115, 25);
        addCategoryField.setBounds(175, 100, 145, 25);

        //option 3
        chooseCategoryToRemove.setBounds(200, 100, 250, 20);
        removeCategoryLabel.setBounds(20, 100, 200, 20);
        RemoveCategoryJButton.setBounds(175, 200, 150, 25);

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(categoriesFrame, new JComponent[] {exitButton,categoryExist,cantRemoveLabel,addCategoryField,RemoveCategoryJButton ,removeCategoryLabel,chooseCategoryToRemove,chooseOptionComboBox, chooseLabel, scrollPane,AddCategoryJButton, showAllCategories, newCategoryLabel});

        //-------------------------------------- Set not visible ---------------------------------------------
        //option 1
        JComponent[] JComponentsShowAllCategories = new JComponent[]{scrollPane, showAllCategories};
        HelperFunctionGUI.hideComponents(JComponentsShowAllCategories);
        categoriesFrame.setVisible(true);

        //option 2
        JComponent[] JComponentsNewCategory = new JComponent[]{newCategoryLabel,addCategoryField ,AddCategoryJButton};
        HelperFunctionGUI.hideComponents(JComponentsNewCategory);

        //option 3
        JComponent[] JComponentsRemoveCategory = new JComponent[]{chooseCategoryToRemove, removeCategoryLabel, RemoveCategoryJButton};
        HelperFunctionGUI.hideComponents(JComponentsRemoveCategory);

        // ------------------------------------- Add action listener to JObjects ------------------------------
        chooseOptionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseOptionComboBox.getSelectedItem().toString();
                if (choose.equals("")) {
                    categoryExist.setVisible(false);
                    cantRemoveLabel.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsNewCategory);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveCategory);
                    HelperFunctionGUI.hideComponents(JComponentsShowAllCategories);
                }
                if(choose.equals("Get all category names")){
                    cantRemoveLabel.setVisible(false);
                    categoryExist.setVisible(false);
                    String details = HelperFunctionGUI.createTextAreaCategories();
                    textAreaCategories.setText(details);
                    HelperFunctionGUI.hideComponents(JComponentsNewCategory);
                    HelperFunctionGUI.showComponents(JComponentsShowAllCategories);
                }
                if(choose.equals("Add new category")){
                    categoryExist.setVisible(false);
                    cantRemoveLabel.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsShowAllCategories);
                    HelperFunctionGUI.hideComponents(JComponentsRemoveCategory);
                    HelperFunctionGUI.showComponents(JComponentsNewCategory);
                    AddCategoryJButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(!(addCategoryField.getText().equals("")) && !(categoryController.check_if_exist_cat(addCategoryField.getText()))){
                                categoryExist.setVisible(false);
                                HelperFunctionGUI.ShowProcessSuccessfully();
                                CategoryController.addCategory(addCategoryField.getText());
                            }
                            else if(categoryController.check_if_exist_cat(addCategoryField.getText()))
                                categoryExist.setVisible(true);
                        }
                    });

                }
                if(choose.equals("Remove category")){
                    cantRemoveLabel.setVisible(false);
                    categoryExist.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsShowAllCategories);
                    HelperFunctionGUI.hideComponents(JComponentsNewCategory);
                    HelperFunctionGUI.showComponents(JComponentsRemoveCategory);

                    chooseCategoryToRemove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            cantRemoveLabel.setVisible(false);
                        }
                    });
                    RemoveCategoryJButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String categoryToRemoveString = chooseCategoryToRemove.getSelectedItem().toString();
                            boolean canRemove = HelperFunctionGUI.canRemoveCategory(categoryToRemoveString);
                            if(canRemove){
                                //show label cannot remove
                                cantRemoveLabel.setVisible(true);
                            }
                            else{
                                HelperFunctionGUI.ShowProcessSuccessfully();
                                categoryController.removeCategory(categoryToRemoveString);
                                cantRemoveLabel.setVisible(false);
                            }
                        }
                    });
                }

            }
        });
    }
}
