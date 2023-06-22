package SuppliersModule.PresentationGUI;

import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationGUI.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierManagerGUI {

    static JFrame OldFrame;
    public static void powerOn(JFrame oldFrame)
    {
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame menuFrame = HelperFunctionGUI.createNewFrame("Supplier Manager Menu");

        //------------------------------------- Create jLabel -------------------------------------------

        JLabel jLabel = new JLabel("Welcome To Supplier Manager Menu");
        jLabel.setFont(new Font("Ariel", Font.BOLD + Font.ITALIC, 20));
        jLabel.setForeground(Color.RED);
        jLabel.setBounds(80, 5, 350, 25);
        menuFrame.add(jLabel);

        //------------------------------------ Create JButtons -------------------------------------------

        JButton opt1 = new JButton("Insert a new supplier");
        JButton opt2 = new JButton("Create a new periodic order");
        JButton opt3 = new JButton("Show orders history");
        JButton opt4 = new JButton("Delete supplier");
        JButton opt5 = new JButton("Update Supplier Agreement");
        JButton opt6 = new JButton("Change supplier details");
        JButton opt7 = new JButton("Print supplier details");
        JButton opt8 = new JButton("Exit");

        //-------------------------------------- Set JButton ---------------------------------------------

        JButton[] buttons = {opt1, opt2, opt3, opt4, opt5, opt6, opt7, opt8};

        for(int i=0 ; i < 8 ; i++)
        {
            buttons[i].setBounds(150, 40 + i*50, 200, 40);
            menuFrame.add(buttons[i]);
        }

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

                if(OldFrame == null)
                    OrderController.getInstance().cancelTimer();

                else
                    OldFrame.setVisible(true);
            }
        });


        menuFrame.setVisible(true);
    }
}
