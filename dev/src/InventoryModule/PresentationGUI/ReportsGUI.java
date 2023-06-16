package InventoryModule.PresentationGUI;

import InventoryModule.Business.Category;
import InventoryModule.Business.Controllers.CategoryController;
import InventoryModule.Business.Controllers.ReportController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReportsGUI {
    static JFrame OldFrame;
    static ReportController reportController;
    static CategoryController categoryController;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        reportController = ReportController.getInstance();
        categoryController = CategoryController.getInstance();
        //------------------------------------- Create new frame -------------------------------------------
        JFrame ShowOrderReportFrame = HelperFunctionGUI.createNewFrame("Reports");
        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel chooseLabel = new JLabel("Please choose an option:");
        JLabel nameOfPublisherLabel = new JLabel("Please enter Issue's reporter's name:");

        //option 2
        JLabel sendOrderLabel = new JLabel("Do you want to send the order?");

        //option 5
        JLabel howManyCategoriesLabel = new JLabel("How many Categories?");
        JLabel chooseCategoriesLabel = new JLabel("Choose categories:");
        JLabel countingCategorisLabel = HelperFunctionGUI.createCheckLabel("Invalid choose", 200, 130, 250, 20);

        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField nameOfPublisherField = new JTextField();


        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Get all reports in system", "Issue order products report",
                "Issue current supply", "Issue EXP/ defected products report", "Issue report by category"});

        //option 2
        JComboBox<String> yesNoComboBox = new JComboBox<>(new String[]{"", "yes", "no"});


        //option 5
        int numOfCategories = categoryController.getCategories().size();
        JComboBox<String> howManyCategoriesComboBox = new JComboBox<>(new String[]{""});
        for(int i=0; i<numOfCategories; i++){
            howManyCategoriesComboBox.addItem(Integer.toString(i));
        }

        //JComboBox<String> allCategoriesComboBox = HelperFunctionGUI.createComboBoxCategories();

        //------------------------------------ Create JCheckBox ---------------------------------------

        //option 5
        JCheckBox[] categoriesCheckBox = new JCheckBox[numOfCategories];
        List<Category> categories= categoryController.getCategories();
        List<String> categoryNames = new ArrayList<>();
        for(int i=0; i<numOfCategories; i++){
            categoryNames.add(categories.get(i).getName());
        }
        for(int i=0 ; i<numOfCategories ; i++)
        {
            categoriesCheckBox[i] = new JCheckBox(categoryNames.get(i));
            categoriesCheckBox[i].setBounds(200, 130 + i*30, 100, 20);
            categoriesCheckBox[i].setVisible(false);
            ShowOrderReportFrame.add(categoriesCheckBox[i]);
        }

        //----------------------------------------- Create JButton ----------------------------------------
        JButton exitButton = HelperFunctionGUI.createExitButton(ShowOrderReportFrame, oldFrame);
        JButton IssueButton = new JButton("Issue");




        //------------------------------------ Create JTextArea ---------------------------------------
        //option1
        JTextArea orderReportTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(orderReportTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Set bounds ---------------------------------------------
        chooseLabel.setBounds(30, 40, 250, 20);
        chooseComboBox.setBounds(180, 40, 200, 20);
        nameOfPublisherLabel.setBounds(30, 70, 250, 20);
        nameOfPublisherField.setBounds(250, 70, 120, 25);
        IssueButton.setBounds(380, 70, 80, 25);
        scrollPane.setBounds(40, 150, 400,250);

        //option 2
        sendOrderLabel.setBounds(40,350,450, 20);
        yesNoComboBox.setBounds(250,350,50, 20);

        //option 5
        //allCategoriesComboBox.setBounds(150, 130, 250, 20);
        howManyCategoriesComboBox.setBounds(170, 100, 250, 20);
        chooseCategoriesLabel.setBounds(30, 130, 250, 20);
        howManyCategoriesLabel.setBounds(30, 100, 250, 20);


        //-------------------------------------- Set not visible ---------------------------------------------
        sendOrderLabel.setVisible(false);
        yesNoComboBox.setVisible(false);


        IssueButton.setVisible(false);
        nameOfPublisherLabel.setVisible(false);
        nameOfPublisherField.setVisible(false);
        scrollPane.setVisible(false);

        //option 5
        countingCategorisLabel.setVisible(false);
        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(ShowOrderReportFrame, new JComponent[] {chooseComboBox,chooseLabel,sendOrderLabel,
                yesNoComboBox,IssueButton, scrollPane,nameOfPublisherField,exitButton,
                nameOfPublisherLabel, howManyCategoriesComboBox,countingCategorisLabel,
                howManyCategoriesLabel, chooseCategoriesLabel});


        //option 2
        JComponent[] JComponentsOrderReport = new JComponent[]{yesNoComboBox, sendOrderLabel};
        HelperFunctionGUI.hideComponents(JComponentsOrderReport);

        //option 5
        JComponent[] JComponentsCategoryReport = new JComponent[]{chooseCategoriesLabel,
               howManyCategoriesComboBox, howManyCategoriesLabel};
        HelperFunctionGUI.hideComponents(JComponentsCategoryReport);



        // ------------------------------------- Add action listener to JObjects ------------------------------

        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choosen = chooseComboBox.getSelectedItem().toString();
                if (choosen.equals("Get all reports in system")) {
                    HelperFunctionGUI.hideComponents(JComponentsCategoryReport);
                    String details = HelperFunctionGUI.createTextAreaALLReports();
                    orderReportTextArea.setText(details);
                    IssueButton.setVisible(false);
                    nameOfPublisherLabel.setVisible(false);
                    nameOfPublisherField.setVisible(false);
                    scrollPane.setVisible(true);

                }
                if (choosen.equals("")) {
                    HelperFunctionGUI.hideComponents(JComponentsCategoryReport);
                    IssueButton.setVisible(false);
                    nameOfPublisherLabel.setVisible(false);
                    nameOfPublisherField.setVisible(false);
                }
                if (choosen.equals("Issue order products report") || choosen.equals("Issue current supply") ||
                        choosen.equals("Issue EXP/ defected products report")) {
                    IssueButton.setVisible(true);
                    nameOfPublisherLabel.setVisible(true);
                    nameOfPublisherField.setVisible(true);
                }
                if (choosen.equals("Issue report by category")) {
                    IssueButton.setVisible(true);
                    nameOfPublisherLabel.setVisible(true);
                    nameOfPublisherField.setVisible(true);
                    HelperFunctionGUI.showComponents(JComponentsCategoryReport);
                    howManyCategoriesComboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int numOfCategories = categoryController.getCategories().size();
                            for (int i = 0; i < numOfCategories; i++)
                                categoriesCheckBox[i].setVisible(true);
                            sendOrderLabel.setVisible(false);
                            yesNoComboBox.setVisible(false);
                        }
                    });


                }
            }
        });
        IssueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choosen = chooseComboBox.getSelectedItem().toString();
                if(!nameOfPublisherField.getText().equals("")){
                    if(choosen.equals("Issue order products report")){
                        HelperFunctionGUI.hideComponents(JComponentsCategoryReport);
                        String details = HelperFunctionGUI.createTextAreaOrderReport(nameOfPublisherField.getText());
                        orderReportTextArea.setText(details);
                        sendOrderLabel.setVisible(true);
                        yesNoComboBox.setVisible(true);
                        scrollPane.setVisible(true);
                        yesNoComboBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(yesNoComboBox.getSelectedItem().toString().equals("yes")){
                                    orderReportTextArea.setText(reportController.makeOrderForLastReport());
                                    scrollPane.setVisible(true);
                                }


                            }
                        });
                    }
                    if (choosen.equals( "Issue current supply")){
                        HelperFunctionGUI.hideComponents(JComponentsCategoryReport);
                        String details = HelperFunctionGUI.createTextAreaCurrSupplyReport(nameOfPublisherField.getText());
                        orderReportTextArea.setText(details);
                        scrollPane.setVisible(true);
                        sendOrderLabel.setVisible(false);
                        yesNoComboBox.setVisible(false);
                    }
                    if(choosen.equals("Issue EXP/ defected products report")){
                        HelperFunctionGUI.hideComponents(JComponentsCategoryReport);
                        String details = HelperFunctionGUI.createTextAreaCreateExpOrDefectReport(nameOfPublisherField.getText());
                        orderReportTextArea.setText(details);
                        scrollPane.setVisible(true);
                        sendOrderLabel.setVisible(false);
                        yesNoComboBox.setVisible(false);
                    }
                    if(choosen.equals("Issue report by category")){
                        int numOfChosenCategories = Integer.parseInt(howManyCategoriesComboBox.getSelectedItem().toString());
                        int counting = 0;
                        for (int i = 0; i < numOfCategories; i++)
                            if(categoriesCheckBox[i].isSelected())
                                counting++;
                        if(counting!=numOfChosenCategories)
                            countingCategorisLabel.setVisible(true);
                    }
                    }
                }
        });

        ShowOrderReportFrame.setVisible(true);
    }
}
