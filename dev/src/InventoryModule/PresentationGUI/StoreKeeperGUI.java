package InventoryModule.PresentationGUI;

import MainClasses.SuperLiMainGUI;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;
import SuppliersModule.PresentationGUI.OrderHistoryGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreKeeperGUI {

    public static void powerOn()
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame menuFrame = HelperFunctionGUI.createNewFrame("Store keeper Menu");

        //------------------------------------ Create JButtons -------------------------------------------

        JButton opt1 = new JButton("Products");
        JButton opt2 = new JButton("Specific Products");
        JButton opt3 = new JButton("Show order report");
        JButton opt4 = new JButton("Categories");
        JButton opt5 = new JButton("SubCategories");
        JButton opt6 = new JButton("SubSubCategories");
        JButton opt7 = new JButton("Discounts");
        JButton opt8 = new JButton("Update/Delete periodic order");
        JButton opt9 = new JButton("Exit");

        //-------------------------------------- Set JButton ---------------------------------------------

        JButton[] buttons = {opt1, opt2, opt3, opt4, opt5, opt6, opt7, opt8, opt9};

        for(int i=0 ; i < 9 ; i++)
        {
            buttons[i].setBounds(125, i*50, 250, 40);
            menuFrame.add(buttons[i]);
        }

        // ------------------------------------- Add action listener to buttons ------------------------------

        opt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                SuperLiProductGUI.powerOn(menuFrame);
            }
        });

        opt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                SpecificProductGUI.powerOn(menuFrame);
            }
        });


        opt4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                CategoriesGUI.powerOn(menuFrame);
            }
        });

        opt5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                SubCategoryGUI.powerOn(menuFrame);
            }
        });
        opt7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                DiscountsGUI.powerOn(menuFrame);
            }
        });

        opt8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                UpdateDeletePeriodicOrderGUI.powerOn(menuFrame);
            }
        });

        opt9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                SuperLiMainGUI.closeProgram();
            }
        });

        menuFrame.setVisible(true);
    }
}
