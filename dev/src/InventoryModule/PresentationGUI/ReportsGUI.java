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
        JLabel countingCategorisLabel = HelperFunctionGUI.createCheckLabel("Invalid choose", 350, 130, 250, 20);

        //----------------------------------------- Create JTextField ----------------------------------------

        JTextField nameOfPublisherField = new JTextField();

        //----------------------------------------- Create JTable ----------------------------------------

        JTable[] jTables = new JTable[3];
        JScrollPane[] scrollPanes = new JScrollPane[3];
        for(int i=0 ; i < 3 ; i++)
        {
            jTables[i] = new JTable();
            scrollPanes[i] = new JScrollPane();
        }

        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Get all reports in system", "Issue order products report",
                "Issue current supply", "Issue EXP/ defected products report", "Issue report by category"});

        //option 2
        JComboBox<String> yesNoComboBox = new JComboBox<>(new String[]{"", "yes",});


        //option 5
        int numOfCategories = categoryController.getCategories().size();
        JComboBox<String> howManyCategoriesComboBox = new JComboBox<>(new String[]{""});
        for(int i=0; i<numOfCategories; i++){
            howManyCategoriesComboBox.addItem(Integer.toString(i));
        }


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
            categoriesCheckBox[i].setBounds(150, 130 + i*20, 100, 20);
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


        //option 5
        JTextArea CategoryReportTextArea = new JTextArea();
        JScrollPane CategoryReportscrollPane = new JScrollPane(CategoryReportTextArea);
        CategoryReportscrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Set bounds ---------------------------------------------
        chooseLabel.setBounds(30, 40, 250, 20);
        chooseComboBox.setBounds(180, 40, 200, 20);
        nameOfPublisherLabel.setBounds(30, 70, 250, 20);
        nameOfPublisherField.setBounds(250, 70, 120, 25);
        IssueButton.setBounds(380, 70, 80, 25);
        scrollPane.setBounds(40, 100, 400,250);

        //option 2
        sendOrderLabel.setBounds(40,350,450, 20);
        yesNoComboBox.setBounds(250,350,50, 20);

        //option 5
        //allCategoriesComboBox.setBounds(150, 130, 250, 20);
        howManyCategoriesComboBox.setBounds(170, 100, 250, 20);
        chooseCategoriesLabel.setBounds(30, 130, 250, 20);
        howManyCategoriesLabel.setBounds(30, 100, 250, 20);
        CategoryReportscrollPane.setBounds(40, 270, 400,130);

        //-------------------------------------- Set not visible ---------------------------------------------
        sendOrderLabel.setVisible(false);
        yesNoComboBox.setVisible(false);


        IssueButton.setVisible(false);
        nameOfPublisherLabel.setVisible(false);
        nameOfPublisherField.setVisible(false);
        scrollPane.setVisible(false);

        //option 5
        countingCategorisLabel.setVisible(false);
        CategoryReportscrollPane.setVisible(false);
        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(ShowOrderReportFrame, new JComponent[] {chooseComboBox,chooseLabel,sendOrderLabel,
                yesNoComboBox,IssueButton, scrollPane,nameOfPublisherField,exitButton,
                nameOfPublisherLabel, howManyCategoriesComboBox,countingCategorisLabel,
                howManyCategoriesLabel, chooseCategoriesLabel, CategoryReportscrollPane});


        //option 2
        JComponent[] JComponentsOrderReport = new JComponent[]{yesNoComboBox, sendOrderLabel};
        HelperFunctionGUI.hideComponents(JComponentsOrderReport);

        //option 5
        JComponent[] JComponentsCategoryReport = new JComponent[]{chooseCategoriesLabel,
               howManyCategoriesComboBox, howManyCategoriesLabel, CategoryReportscrollPane};
        HelperFunctionGUI.hideComponents(JComponentsCategoryReport);



        // ------------------------------------- Add action listener to JObjects ------------------------------

        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choosen = chooseComboBox.getSelectedItem().toString();
                if (choosen.equals("")) {
                    HelperFunctionGUI.hideComponents(JComponentsOrderReport);
                    HelperFunctionGUI.hideComponents(JComponentsCategoryReport);

                    for(int i=0 ; i < 3 ; i++)
                        scrollPanes[i].setVisible(false);

                    for (int i = 0; i < numOfCategories; i++)
                        categoriesCheckBox[i].setVisible(false);

                    scrollPane.setVisible(false);
                    IssueButton.setVisible(false);
                    nameOfPublisherLabel.setVisible(false);
                    nameOfPublisherField.setVisible(false);
                }
                if (choosen.equals("Get all reports in system")) {
                    orderReportTextArea.setText("");
                    HelperFunctionGUI.hideComponents(JComponentsOrderReport);
                    HelperFunctionGUI.hideComponents(JComponentsCategoryReport);
                    String details = HelperFunctionGUI.createTextAreaALLReports();
                    orderReportTextArea.setText(details);

                    for(int i=0 ; i < 3 ; i++)
                        scrollPanes[i].setVisible(false);

                    for (int i = 0; i < numOfCategories; i++)
                        categoriesCheckBox[i].setVisible(false);

                    IssueButton.setVisible(false);
                    nameOfPublisherLabel.setVisible(false);
                    nameOfPublisherField.setVisible(false);
                    scrollPane.setVisible(true);
                }
                if (choosen.equals("Issue order products report")){
                    orderReportTextArea.setText("");
                    HelperFunctionGUI.hideComponents(JComponentsCategoryReport);

                    scrollPane.setVisible(false);

                    for(int i=0 ; i < 3 ; i++)
                        scrollPanes[i].setVisible(false);

                    for (int i = 0; i < numOfCategories; i++)
                        categoriesCheckBox[i].setVisible(false);


                    IssueButton.setVisible(true);
                    nameOfPublisherLabel.setVisible(true);
                    nameOfPublisherField.setVisible(true);
                }
                if(choosen.equals("Issue current supply") || choosen.equals("Issue EXP/ defected products report")) {
                    orderReportTextArea.setText("");
                    HelperFunctionGUI.hideComponents(JComponentsOrderReport);
                    HelperFunctionGUI.hideComponents(JComponentsCategoryReport);

                    for(int i=0 ; i < 3 ; i++)
                        scrollPanes[i].setVisible(false);

                    for (int i = 0; i < numOfCategories; i++)
                        categoriesCheckBox[i].setVisible(false);

                    IssueButton.setVisible(true);
                    nameOfPublisherLabel.setVisible(true);
                    nameOfPublisherField.setVisible(true);
                    scrollPane.setVisible(false);
                }
                if (choosen.equals("Issue report by category")) {
                    HelperFunctionGUI.hideComponents(JComponentsOrderReport);

                    for(int i=0 ; i < 3 ; i++)
                        scrollPanes[i].setVisible(false);

                    scrollPane.setVisible(false);
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
                        String[][] data = HelperFunctionGUI.createDataForOpt2(nameOfPublisherField.getText());
                        String[] columns = {"Barcode", "Name", "Manufacturer", "Amount"};

                        jTables[1] = new JTable(data, columns);
                        scrollPanes[1] = new JScrollPane(jTables[1]);
                        setJTable(jTables[1], scrollPanes[1],ShowOrderReportFrame, data, columns);

                        sendOrderLabel.setVisible(true);
                        yesNoComboBox.setVisible(true);

                        yesNoComboBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(yesNoComboBox.getSelectedItem().toString().equals("yes")){
                                    scrollPanes[1].setVisible(false);
                                    orderReportTextArea.setText(reportController.makeOrderForLastReport());
                                    scrollPane.setVisible(true);
                                    HelperFunctionGUI.ShowProcessSuccessfully();
                                }
                            }});
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
                    if(choosen.equals("Issue report by category") &&
                            !howManyCategoriesComboBox.getSelectedItem().equals("")){
                        int numOfChosenCategories = Integer.parseInt(howManyCategoriesComboBox.getSelectedItem().toString());
                        int counting = 0;
                        for (int i = 0; i < numOfCategories; i++){
                            if(categoriesCheckBox[i].isSelected())
                                counting++;
                        }
                        if(counting!=numOfChosenCategories)
                            countingCategorisLabel.setVisible(true);
                        if(counting == numOfChosenCategories){
                            countingCategorisLabel.setVisible(false);
                            List<String>chosenCategories = new ArrayList<>();
                            for (int i = 0; i < numOfCategories; i++){
                                if(categoriesCheckBox[i].isSelected())
                                    chosenCategories.add(categoriesCheckBox[i].getText());
                            }
                            String details = HelperFunctionGUI.createTextAreaCreateByCategoryReport(nameOfPublisherField.getText(), chosenCategories);
                            CategoryReportTextArea.setText(details);
                            CategoryReportscrollPane.setVisible(true);
                        }
                    }
                    }
                }
        });
        ShowOrderReportFrame.setVisible(true);
    }

    public static void setJTable(JTable jTable, JScrollPane scrollPane, JFrame frame, String[][] data, String[] columns)
    {
        jTable.setBounds(40, 100, 400,250);
        scrollPane.setBounds(40, 100, 400,250);
        frame.add(scrollPane);
    }
}
