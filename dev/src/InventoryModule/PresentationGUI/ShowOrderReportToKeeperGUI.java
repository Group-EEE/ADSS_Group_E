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
        JLabel sendOrderLabel = new JLabel("Do you want to send the order?");

        //----------------------------------------- Create JTextField ----------------------------------------
        JTextField nameOfPublisherField = new JTextField();


        //----------------------------------------- Create JComboBox ----------------------------------------
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
        nameOfPublisherLabel.setBounds(30, 40, 250, 20);
        nameOfPublisherField.setBounds(270, 40, 145, 25);
        IssueButton.setBounds(200, 70, 120, 25);
        scrollPane.setBounds(40, 100, 400,250);
        sendOrderLabel.setBounds(40,350,450, 20);
        yesNoComboBox.setBounds(250,350,50, 20);



        //-------------------------------------- Set not visible ---------------------------------------------
        sendOrderLabel.setVisible(false);
        yesNoComboBox.setVisible(false);
        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(ShowOrderReportFrame, new JComponent[] {sendOrderLabel,yesNoComboBox,IssueButton, scrollPane,nameOfPublisherField,exitButton, nameOfPublisherLabel});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        IssueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameOfPublisherField.getText().equals("")){
                    String details = HelperFunctionGUI.createTextAreaOrderReport(nameOfPublisherField.getText());
                    orderReportTextArea.setText(details);
                    sendOrderLabel.setVisible(true);
                    yesNoComboBox.setVisible(true);
                }
                yesNoComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(yesNoComboBox.getSelectedItem().toString().equals("yes")){
                            orderReportTextArea.setText(reportController.makeOrderForLastReport());
                        }


                    }
                });
            }
        });

        ShowOrderReportFrame.setVisible(true);
    }
}
