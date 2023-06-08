package SuppliersModule.PresentationGUI;
import MainClasses.SuperLiMainGUI;
import SuppliersModule.Business.Controllers.SupplierController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierManagerGUI {

    static SupplierController supplierController = SupplierController.getInstance();
    public static void powerOn()
    {

        //------------------------------------- Create new frame -------------------------------------------

        JFrame frame = new JFrame("Supplier Manager Menu");
        frame.setSize(500,500);
        frame.setLayout(null);

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

        opt1.addActionListener(new opt1Click(frame));
        opt3.addActionListener(new opt3Click(frame));
        opt4.addActionListener(new opt4Click(frame));
        opt7.addActionListener(new opt7Click(frame));
        opt8.addActionListener(new opt8Click(frame));

        // ------------------------------------------- Add buttons to frame ------------------------------------
        frame.add(opt1);
        frame.add(opt2);
        frame.add(opt3);
        frame.add(opt4);
        frame.add(opt5);
        frame.add(opt6);
        frame.add(opt7);
        frame.add(opt8);

        frame.setVisible(true);
    }
    // -------------------------------------------------------------------------------------------------

    private static class opt1Click implements ActionListener {
        private JFrame thisFrame;

        /**
         * Constructor
         * @param thisFrame - This frame.
         */
        public opt1Click(JFrame thisFrame) {
            this.thisFrame = thisFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            thisFrame.setVisible(false);
            CreateSupplierGUI.powerOn(supplierController, thisFrame);
        }
    }

    // --------------------------------------------------------------------------------------------------
    private static class opt3Click implements ActionListener {
        private JFrame thisFrame;

        /**
         * Constructor
         * @param thisFrame - This frame.
         */
        public opt3Click(JFrame thisFrame) {
            this.thisFrame = thisFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            thisFrame.setVisible(false);
            OrderHistoryGUI.powerOn(supplierController, thisFrame);
        }
    }

    // --------------------------------------------------------------------------------------------------
    private static class opt4Click implements ActionListener {
        private JFrame thisFrame;

        /**
         * Constructor
         * @param thisFrame - This frame.
         */
        public opt4Click(JFrame thisFrame) {
            this.thisFrame = thisFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            thisFrame.setVisible(false);
            DeleteSupplierGUI.powerOn(supplierController, thisFrame);
        }
    }

    // -------------------------------------------------------------------------------------------------

    /** class that implements ActionListener.
     * actionPerformed when click on opt7
     */
    private static class opt7Click implements ActionListener {
        private JFrame thisFrame;

        /**
         * Constructor
         * @param thisFrame - This frame.
         */
        public opt7Click(JFrame thisFrame) {
            this.thisFrame = thisFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            thisFrame.setVisible(false);
            SupplierDetailsGUI.powerOn(supplierController, thisFrame);
        }
    }

    // -------------------------------------------------------------------------------------------------

    /** class that implements ActionListener.
     * actionPerformed when click on opt8
     */
    private static class opt8Click implements ActionListener {
        private JFrame thisFrame;

        /**
         * Constructor
         * @param thisFrame - This frame.
         */
        public opt8Click(JFrame thisFrame) {
            this.thisFrame = thisFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            thisFrame.dispose();
            SuperLiMainGUI.closeProgram();
        }
    }
}
