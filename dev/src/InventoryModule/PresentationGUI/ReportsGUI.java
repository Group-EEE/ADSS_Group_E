package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.ReportController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsGUI {
    static JFrame OldFrame;
    static ReportController reportController;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        reportController = ReportController.getInstance();
        //------------------------------------- Create new frame -------------------------------------------
        JFrame ShowOrderReportFrame = HelperFunctionGUI.createNewFrame("Reports");

        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel chooseLabel = new JLabel("Please choose an option:");
        JLabel nameOfPublisherLabel = new JLabel("Please enter Issue's reporter's name:");

        //option 2
        JLabel sendOrderLabel = new JLabel("Do you want to send the order?");

        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField nameOfPublisherField = new JTextField();


        //----------------------------------------- Create JComboBox ----------------------------------------
        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Get all reports in system", "Issue order products report",
                "Issue current supply", "Issue EXP/ defected products report", "Issue report by category"});

        //option 2
        JComboBox<String> yesNoComboBox = new JComboBox<>(new String[]{"", "yes", "no"});

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
        scrollPane.setBounds(40, 100, 400,250);

        //option 2
        sendOrderLabel.setBounds(40,350,450, 20);
        yesNoComboBox.setBounds(250,350,50, 20);



        //-------------------------------------- Set not visible ---------------------------------------------
        sendOrderLabel.setVisible(false);
        yesNoComboBox.setVisible(false);


        IssueButton.setVisible(false);
        nameOfPublisherLabel.setVisible(false);
        nameOfPublisherField.setVisible(false);
        scrollPane.setVisible(false);
        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(ShowOrderReportFrame, new JComponent[] {chooseComboBox,chooseLabel,sendOrderLabel,
                yesNoComboBox,IssueButton, scrollPane,nameOfPublisherField,exitButton, nameOfPublisherLabel});


        JComponent[] JComponentsOrderReport = new JComponent[]{yesNoComboBox, sendOrderLabel};
        HelperFunctionGUI.hideComponents(JComponentsOrderReport);


        // ------------------------------------- Add action listener to JObjects ------------------------------

        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choosen = chooseComboBox.getSelectedItem().toString();
                if(choosen.equals("Get all reports in system")){
                    String details = HelperFunctionGUI.createTextAreaALLReports();
                    orderReportTextArea.setText(details);
                    IssueButton.setVisible(false);
                    nameOfPublisherLabel.setVisible(false);
                    nameOfPublisherField.setVisible(false);
                    scrollPane.setVisible(true);

                }
                if(choosen.equals("")){
                    IssueButton.setVisible(false);
                    nameOfPublisherLabel.setVisible(false);
                    nameOfPublisherField.setVisible(false);
                }
                else{
                    IssueButton.setVisible(true);
                    nameOfPublisherLabel.setVisible(true);
                    nameOfPublisherField.setVisible(true);
                }
            }
        });
        IssueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choosen = chooseComboBox.getSelectedItem().toString();
                if(!nameOfPublisherField.getText().equals("")){
                    if(choosen.equals("Issue order products report")){
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
                        String details = HelperFunctionGUI.createTextAreaCurrSupplyReport(nameOfPublisherField.getText());
                        orderReportTextArea.setText(details);
                        scrollPane.setVisible(true);
                        sendOrderLabel.setVisible(false);
                        yesNoComboBox.setVisible(false);
                    }
                    if(choosen.equals("Issue EXP/ defected products report")){
                        String details = HelperFunctionGUI.createTextAreaCreateExpOrDefectReport(nameOfPublisherField.getText());
                        orderReportTextArea.setText(details);
                        scrollPane.setVisible(true);
                        sendOrderLabel.setVisible(false);
                        yesNoComboBox.setVisible(false);
                    }
                    if(choosen.equals("Issue report by category")){
                        scrollPane.setVisible(true);
                        sendOrderLabel.setVisible(false);
                        yesNoComboBox.setVisible(false);
                    }
                }




            }
        });

        ShowOrderReportFrame.setVisible(true);
    }
}
