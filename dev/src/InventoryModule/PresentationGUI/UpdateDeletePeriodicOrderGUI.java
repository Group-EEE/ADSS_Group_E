package InventoryModule.PresentationGUI;

import SuppliersModule.Business.Controllers.OrderController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UpdateDeletePeriodicOrderGUI {

    static OrderController orderController;
    public static void powerOn(JFrame oldFrame)
    {
        orderController = OrderController.getInstance();

        //------------------------------------- Create new frame -------------------------------------------

        JFrame subMenuPeriodicOrder = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //----------------------------------------- Create JButton ----------------------------------------

        JButton deletePeriodicOrderButton = new JButton("Delete Periodic Order");
        JButton changeDayButton = new JButton("Change the day for invite");
        JButton addProductButton = new JButton("Add product");
        JButton changeQuantityButton = new JButton("Change the quantity of product");
        JButton deleteProductButton = new JButton("Delete product");
        JButton ExitButton = HelperFunctionGUI.createExitButton(subMenuPeriodicOrder, oldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------


        deletePeriodicOrderButton.setBounds(100, 50, 300, 40);
        changeDayButton.setBounds(100, 100, 300, 40);
        addProductButton.setBounds(100, 150, 300, 40);
        changeQuantityButton.setBounds(100, 200, 300, 40);
        deleteProductButton.setBounds(100, 250, 300, 40);

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(subMenuPeriodicOrder, new JComponent[]{deletePeriodicOrderButton
        , changeDayButton, addProductButton, changeQuantityButton, deleteProductButton});

        // ------------------------------------- Add action listener to buttons ------------------------------

        deletePeriodicOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subMenuPeriodicOrder.setVisible(false);
                deletePeriodicOrderPage(subMenuPeriodicOrder);
            }});

        subMenuPeriodicOrder.setVisible(true);

        changeDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subMenuPeriodicOrder.setVisible(false);
                changeDayPage(subMenuPeriodicOrder);
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subMenuPeriodicOrder.setVisible(false);
                addProductPage(subMenuPeriodicOrder);
            }
        });

        changeQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subMenuPeriodicOrder.setVisible(false);
                changeQuantityPage(subMenuPeriodicOrder);
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subMenuPeriodicOrder.setVisible(false);
                deleteProductPage(subMenuPeriodicOrder);
            }
        });

    }

    public static void deletePeriodicOrderPage(JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page1Frame = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel barcodeLabel = new JLabel("Choose barcode");
        JLabel idLabel = new JLabel("Choose id");
        JLabel checkIdDelete = HelperFunctionGUI.createCheckLabel("Periodic order cannot change in the day of ordering",100,320,300,20);

        //------------------------------------ Create comboBox ---------------------------------------

        JComboBox<String> comboBoxBarcodes = HelperFunctionGUI.createComboBoxBarcodes();
        JComboBox<String> comboBoxIds = new JComboBox<>();

        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneOrders = new JScrollPane(textAreaHistory);
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton deleteButton = new JButton("Delete");
        JButton exitButton = HelperFunctionGUI.createExitButton(page1Frame, backFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        barcodeLabel.setBounds(10, 10, 100, 20);
        comboBoxBarcodes.setBounds(200, 10, 100, 20);

        scrollPaneOrders.setBounds(10, 50, 480,200);

        idLabel.setBounds(10, 270, 100, 20);
        comboBoxIds.setBounds(200, 270, 100,20);

        deleteButton.setBounds(200, 350, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        HelperFunctionGUI.addComponentsToFrame(page1Frame, new JComponent[]{barcodeLabel, comboBoxBarcodes,
                scrollPaneOrders, idLabel, comboBoxIds, checkIdDelete, deleteButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxBarcodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = comboBoxBarcodes.getSelectedItem().toString();

                if(barcode.equals(""))textAreaHistory.setText("");

                else
                {
                    List<String> relevantOrder = orderController.findAllPeriodicOrderThatContainThisBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                    String relevantOrdersString = "";
                    comboBoxIds.removeAllItems();

                    comboBoxIds.addItem("");

                    for(String currOrderString : relevantOrder) {
                        comboBoxIds.addItem(currOrderString.split("Id ")[1]);
                        relevantOrdersString = relevantOrdersString + currOrderString + "\n";
                    }

                    textAreaHistory.setText(relevantOrdersString);
                }
            }});

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                if(comboBoxBarcodes.getSelectedItem().toString().equals("") || comboBoxIds.getSelectedItem().toString().equals("")){isValid=false;}


                if(isValid)
                {
                    orderController.findPeriodicOrder(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()), Integer.parseInt(comboBoxIds.getSelectedItem().toString()));
                    if (orderController.checkInvalidDayForChange())
                        checkIdDelete.setVisible(true);
                    else
                    {
                        orderController.deleteCurPeriodicOrder();
                        page1Frame.dispose();
                        backFrame.setVisible(true);
                        HelperFunctionGUI.ShowProcessSuccessfully();
                    }
                }
            }});

        page1Frame.setVisible(true);
    }

    public static void changeDayPage(JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page2Frame = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel barcodeLabel = new JLabel("Choose barcode");
        JLabel idLabel = new JLabel("Choose id");
        JLabel dayLabel = new JLabel("Choose day");

        JLabel checkIdDelete = HelperFunctionGUI.createCheckLabel("Periodic order cannot change in the day of ordering",100,320,300,20);

        //------------------------------------ Create comboBox ---------------------------------------

        JComboBox<String> comboBoxBarcodes = HelperFunctionGUI.createComboBoxBarcodes();
        JComboBox<String> comboBoxIds = new JComboBox<>();
        JComboBox<String> comboBoxDay = new JComboBox<>(new String[]{"","Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});

        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneOrders = new JScrollPane(textAreaHistory);
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton changeDayButton = new JButton("Change");
        JButton exitButton = HelperFunctionGUI.createExitButton(page2Frame, backFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        barcodeLabel.setBounds(10, 10, 100, 20);
        comboBoxBarcodes.setBounds(200, 10, 100, 20);

        scrollPaneOrders.setBounds(10, 50, 480,200);

        idLabel.setBounds(10, 270, 100, 20);
        comboBoxIds.setBounds(200, 270, 100,20);

        dayLabel.setBounds(10, 310, 100, 20);
        comboBoxDay.setBounds(200, 310, 100, 20);

        changeDayButton.setBounds(200, 350, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        HelperFunctionGUI.addComponentsToFrame(page2Frame, new JComponent[]{barcodeLabel, comboBoxBarcodes,
                scrollPaneOrders, idLabel, comboBoxIds, checkIdDelete, dayLabel,
                comboBoxDay, changeDayButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxBarcodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = comboBoxBarcodes.getSelectedItem().toString();

                if(barcode.equals(""))textAreaHistory.setText("");

                else
                {
                    List<String> relevantOrder = orderController.findAllPeriodicOrderThatContainThisBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                    String relevantOrdersString = "";
                    comboBoxIds.removeAllItems();

                    comboBoxIds.addItem("");

                    for(String currOrderString : relevantOrder) {
                        comboBoxIds.addItem(currOrderString.split("Id ")[1]);
                        relevantOrdersString = relevantOrdersString + currOrderString + "\n";
                    }
                    textAreaHistory.setText(relevantOrdersString);
                }
            }});

        changeDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                if(comboBoxBarcodes.getSelectedItem().toString().equals("") || comboBoxIds.getSelectedItem().toString().equals("")){isValid=false;}

                if(isValid)
                {
                    orderController.findPeriodicOrder(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()), Integer.parseInt(comboBoxIds.getSelectedItem().toString()));
                    if (orderController.checkInvalidDayForChange())
                        checkIdDelete.setVisible(true);
                    else
                    {
                        orderController.changeDayForInviteForCurPeriodicOrder(comboBoxDay.getSelectedIndex()-1);
                        page2Frame.dispose();
                        backFrame.setVisible(true);
                        HelperFunctionGUI.ShowProcessSuccessfully();
                    }
                }
            }});

        page2Frame.setVisible(true);
    }

    public static void addProductPage(JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page3Frame = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel barcodeLabel = new JLabel("Choose barcode");
        JLabel idLabel = new JLabel("Choose id");
        JLabel quantityLabel = new JLabel("Enter quantity");

        JLabel checkIdDelete = HelperFunctionGUI.createCheckLabel("Periodic order cannot change in the day of ordering",100,320,300,20);
        JLabel checkQuantityLabel = HelperFunctionGUI.createCheckLabel("cannot supply the requested quantity",260,310,240,20);

        //------------------------------------ Create JComboBox ---------------------------------------

        JComboBox<String> comboBoxBarcodes = HelperFunctionGUI.createComboBoxBarcodes();
        JComboBox<String> comboBoxIds = new JComboBox<>();

        //------------------------------------ Create JComboBox ---------------------------------------

        JTextField quantityField = new JTextField();
        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneOrders = new JScrollPane(textAreaHistory);
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton addButton = new JButton("Add");
        JButton exitButton = HelperFunctionGUI.createExitButton(page3Frame, backFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        barcodeLabel.setBounds(10, 10, 100, 20);
        comboBoxBarcodes.setBounds(200, 10, 100, 20);

        scrollPaneOrders.setBounds(10, 50, 480,200);

        idLabel.setBounds(10, 270, 100, 20);
        comboBoxIds.setBounds(200, 270, 50,20);

        quantityLabel.setBounds(10, 310, 100, 20);
        quantityField.setBounds(200, 310, 50, 20);

        addButton.setBounds(200, 350, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        HelperFunctionGUI.addComponentsToFrame(page3Frame, new JComponent[]{barcodeLabel, comboBoxBarcodes,
                scrollPaneOrders, idLabel, comboBoxIds, checkIdDelete, quantityLabel, quantityField,
                checkQuantityLabel, addButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxBarcodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = comboBoxBarcodes.getSelectedItem().toString();

                if(barcode.equals(""))textAreaHistory.setText("");

                else
                {
                    List<String> relevantOrder = orderController.findAllPeriodicOrderThatCanBeContainThisBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                    String relevantOrdersString = "";
                    comboBoxIds.removeAllItems();

                    comboBoxIds.addItem("");

                    for(String currOrderString : relevantOrder) {
                        comboBoxIds.addItem(currOrderString.split("Id ")[1]);
                        relevantOrdersString = relevantOrdersString + currOrderString + "\n";
                    }
                    textAreaHistory.setText(relevantOrdersString);
                }
            }});

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                if(comboBoxBarcodes.getSelectedItem().toString().equals("") || comboBoxIds.getSelectedItem().toString().equals("")){isValid = false;}

                if(!HelperFunctionGUI.CheckIntInput(quantityField.getText()))
                {
                    checkQuantityLabel.setVisible(true);
                    isValid = false;
                }
                else isValid = true;

                if(isValid)
                {
                    orderController.findPeriodicOrder(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()), Integer.parseInt(comboBoxIds.getSelectedItem().toString()));
                    if (orderController.checkInvalidDayForChange())
                        checkIdDelete.setVisible(true);
                    else
                    {
                        orderController.addProductToTheListByBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                        if((!orderController.CheckIfSupplierCanSupplyThisQuantity(Integer.parseInt(quantityField.getText()))))
                            checkQuantityLabel.setVisible(true);
                        else {
                            orderController.addQuantityOfTheLastEnteredProduct(Integer.parseInt(quantityField.getText()));
                            page3Frame.dispose();
                            backFrame.setVisible(true);
                            HelperFunctionGUI.ShowProcessSuccessfully();
                        }
                    }
                }
            }});

        page3Frame.setVisible(true);
    }

    public static void changeQuantityPage(JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page4Frame = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel barcodeLabel = new JLabel("Choose barcode");
        JLabel idLabel = new JLabel("Choose id");
        JLabel quantityLabel = new JLabel("Enter quantity");

        JLabel checkIdDelete = HelperFunctionGUI.createCheckLabel("Periodic order cannot change in the day of ordering",100,320,300,20);
        JLabel checkQuantityLabel = HelperFunctionGUI.createCheckLabel("cannot supply the requested quantity",260,310,240,20);

        //------------------------------------ Create JComboBox ---------------------------------------

        JComboBox<String> comboBoxBarcodes = HelperFunctionGUI.createComboBoxBarcodes();
        JComboBox<String> comboBoxIds = new JComboBox<>();

        //------------------------------------ Create JComboBox ---------------------------------------

        JTextField quantityField = new JTextField();
        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneOrders = new JScrollPane(textAreaHistory);
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton changeQuantityButton = new JButton("Update");
        JButton exitButton = HelperFunctionGUI.createExitButton(page4Frame, backFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        barcodeLabel.setBounds(10, 10, 100, 20);
        comboBoxBarcodes.setBounds(200, 10, 100, 20);

        scrollPaneOrders.setBounds(10, 50, 480,200);

        idLabel.setBounds(10, 270, 100, 20);
        comboBoxIds.setBounds(200, 270, 50,20);

        quantityLabel.setBounds(10, 310, 100, 20);
        quantityField.setBounds(200, 310, 50, 20);

        changeQuantityButton.setBounds(200, 350, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        HelperFunctionGUI.addComponentsToFrame(page4Frame, new JComponent[]{barcodeLabel, comboBoxBarcodes,
                scrollPaneOrders, idLabel, comboBoxIds, checkIdDelete, quantityLabel, quantityField,
                checkQuantityLabel, changeQuantityButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxBarcodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = comboBoxBarcodes.getSelectedItem().toString();

                if(barcode.equals(""))textAreaHistory.setText("");

                else
                {
                    List<String> relevantOrder = orderController.findAllPeriodicOrderThatContainThisBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                    String relevantOrdersString = "";
                    comboBoxIds.removeAllItems();

                    comboBoxIds.addItem("");

                    for(String currOrderString : relevantOrder) {
                        comboBoxIds.addItem(currOrderString.split("Id ")[1]);
                        relevantOrdersString = relevantOrdersString + currOrderString + "\n";
                    }
                    textAreaHistory.setText(relevantOrdersString);
                }
            }});

        changeQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                if(comboBoxBarcodes.getSelectedItem().toString().equals("") || comboBoxIds.getSelectedItem().toString().equals("")){isValid = false;}

                if(!HelperFunctionGUI.CheckIntInput(quantityField.getText()))
                {
                    checkQuantityLabel.setVisible(true);
                    isValid = false;
                }
                else isValid = true;

                if(isValid)
                {
                    orderController.findPeriodicOrder(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()), Integer.parseInt(comboBoxIds.getSelectedItem().toString()));
                    if (orderController.checkInvalidDayForChange())
                        checkIdDelete.setVisible(true);
                    else
                    {
                        orderController.addProductToTheListByBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                        if((!orderController.CheckIfSupplierCanSupplyThisQuantity(Integer.parseInt(quantityField.getText()))))
                            checkQuantityLabel.setVisible(true);
                        else {
                            orderController.addQuantityOfTheLastEnteredProduct(Integer.parseInt(quantityField.getText()));
                            page4Frame.dispose();
                            backFrame.setVisible(true);
                            HelperFunctionGUI.ShowProcessSuccessfully();
                        }
                    }
                }
            }});

        page4Frame.setVisible(true);
    }

    public static void deleteProductPage(JFrame backFrame)
    {
        //------------------------------------- Create new frame -------------------------------------------

        JFrame page5Frame = HelperFunctionGUI.createNewFrame("Update or Delete periodic order");

        //----------------------------------------- Create JLabel ----------------------------------------

        JLabel barcodeLabel = new JLabel("Choose barcode");
        JLabel idLabel = new JLabel("Choose id");
        JLabel checkIdDelete = HelperFunctionGUI.createCheckLabel("Periodic order cannot change in the day of ordering",100,320,300,20);

        //------------------------------------ Create comboBox ---------------------------------------

        JComboBox<String> comboBoxBarcodes = HelperFunctionGUI.createComboBoxBarcodes();
        JComboBox<String> comboBoxIds = new JComboBox<>();

        //----------------------------------------- Create JTextArea ----------------------------------------

        JTextArea textAreaHistory = new JTextArea();
        JScrollPane scrollPaneOrders = new JScrollPane(textAreaHistory);
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //----------------------------------------- Create JButton ----------------------------------------

        JButton deleteButton = new JButton("Delete");
        JButton exitButton = HelperFunctionGUI.createExitButton(page5Frame, backFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        barcodeLabel.setBounds(10, 10, 100, 20);
        comboBoxBarcodes.setBounds(200, 10, 100, 20);

        scrollPaneOrders.setBounds(10, 50, 480,200);

        idLabel.setBounds(10, 270, 100, 20);
        comboBoxIds.setBounds(200, 270, 100,20);

        deleteButton.setBounds(200, 350, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        HelperFunctionGUI.addComponentsToFrame(page5Frame, new JComponent[]{barcodeLabel, comboBoxBarcodes,
                scrollPaneOrders, idLabel, comboBoxIds, checkIdDelete, deleteButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxBarcodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = comboBoxBarcodes.getSelectedItem().toString();

                if(barcode.equals(""))textAreaHistory.setText("");

                else
                {
                    List<String> relevantOrder = orderController.findAllPeriodicOrderThatContainThisBarcode(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                    String relevantOrdersString = "";
                    comboBoxIds.removeAllItems();

                    comboBoxIds.addItem("");

                    for(String currOrderString : relevantOrder) {
                        comboBoxIds.addItem(currOrderString.split("Id ")[1]);
                        relevantOrdersString = relevantOrdersString + currOrderString + "\n";
                    }

                    textAreaHistory.setText(relevantOrdersString);
                }
            }});

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                if(comboBoxBarcodes.getSelectedItem().toString().equals("") || comboBoxIds.getSelectedItem().toString().equals("")){isValid=false;}


                if(isValid)
                {
                    orderController.findPeriodicOrder(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()), Integer.parseInt(comboBoxIds.getSelectedItem().toString()));
                    if (orderController.checkInvalidDayForChange())
                        checkIdDelete.setVisible(true);
                    else
                    {
                        orderController.deleteOrderedProduct(Integer.parseInt(comboBoxBarcodes.getSelectedItem().toString()));
                        page5Frame.dispose();
                        backFrame.setVisible(true);
                        HelperFunctionGUI.ShowProcessSuccessfully();
                    }
                }
            }});

        page5Frame.setVisible(true);
    }


}
