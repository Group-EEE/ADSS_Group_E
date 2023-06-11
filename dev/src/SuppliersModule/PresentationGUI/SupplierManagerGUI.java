package SuppliersModule.PresentationGUI;
import MainClasses.SuperLiMainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierManagerGUI {

    public static void powerOn()
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame menuFrame = new JFrame("Supplier Manager Menu");
        menuFrame.setSize(500,500);
        menuFrame.setLayout(null);

        //------------------------------------ Create buttons -------------------------------------------

        JButton opt1 = new JButton("Insert a new supplier");
        JButton opt2 = new JButton("Create a new periodic order");
        JButton opt3 = new JButton("Show orders history");
        JButton opt4 = new JButton("Delete supplier");
        JButton opt5 = new JButton("Update Supplier Agreement");
        JButton opt6 = new JButton("Change supplier details");
        JButton opt7 = new JButton("Print supplier details");
        JButton opt8 = new JButton("Exit");

        //-------------------------------------- Set bounds ---------------------------------------------

        opt1.setBounds(150,20,200,30);
        opt2.setBounds(150,60,200,30);
        opt3.setBounds(150,100,200,30);
        opt4.setBounds(150,140,200,30);
        opt5.setBounds(150,180,200,30);
        opt6.setBounds(150,220,200,30);
        opt7.setBounds(150,260,200,30);
        opt8.setBounds(150,300,200,30);

        // ------------------------------------- Add action listener to buttons ------------------------------


        opt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                CreateSupplierGUI.powerOn(menuFrame);
            }
        });

        opt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                CreatePeriodicOrderGUI.powerOn(menuFrame);
            }
        });

        opt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                OrderHistoryGUI.powerOn(menuFrame);
            }
        });

        opt4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                DeleteSupplierGUI.powerOn(menuFrame);
            }
        });

        opt5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                UpdateSupplierAgreementGUI.powerOn(menuFrame);
            }
        });

        opt6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                ChangeSupplierDetailsGUI.powerOn(menuFrame);
            }
        });


        opt7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                PrintSupplierDetailsGUI.powerOn(menuFrame);
            }
        });

        opt8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                SuperLiMainGUI.closeProgram();
            }
        });

        // ------------------------------------------- Add buttons to frame ------------------------------------
        menuFrame.add(opt1);
        menuFrame.add(opt2);
        menuFrame.add(opt3);
        menuFrame.add(opt4);
        menuFrame.add(opt5);
        menuFrame.add(opt6);
        menuFrame.add(opt7);
        menuFrame.add(opt8);

        menuFrame.setVisible(true);
    }
}
