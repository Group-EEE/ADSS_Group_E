package StoreManagerCLIorGUI;

import InventoryModule.PresentationGUI.StoreKeeperGUI;
import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationGUI.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerGUI {

    public static void powerOn()
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame menuFrame = HelperFunctionGUI.createNewFrame("Supplier Manager Menu");

        //------------------------------------- Create jLabel -------------------------------------------

        JLabel jLabel = new JLabel("Welcome to Store Manager menu");
        jLabel.setFont(new Font("Ariel", Font.BOLD + Font.ITALIC, 25));
        jLabel.setForeground(Color.RED);
        jLabel.setBounds(50, 20, 400, 30);
        menuFrame.add(jLabel);

        //------------------------------------ Create JButtons -------------------------------------------

        JButton opt1 = new JButton("SupplierManager menu");
        JButton opt2 = new JButton("Storekeeper menu");
        JButton opt3 = new JButton("Exit");

        //-------------------------------------- Set JButton ---------------------------------------------

        JButton[] buttons = {opt1, opt2, opt3};

        for(int i=0 ; i < 3 ; i++)
        {
            buttons[i].setBounds(150, (i+1)*100, 200, 80);
            menuFrame.add(buttons[i]);
        }

        // ------------------------------------- Add action listener to buttons ------------------------------

        opt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                SupplierManagerGUI.powerOn(menuFrame);
            }
        });

        opt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                StoreKeeperGUI.powerOn(menuFrame);
            }
        });

        opt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                OrderController.getInstance().cancelTimer();
            }
        });

        menuFrame.setVisible(true);
    }
}
