package SuppliersModule.PresentationGUI;

import InventoryModule.Business.Category;
import SuppliersModule.Business.*;
import SuppliersModule.Business.Controllers.SupplierController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintSupplierDetailsGUI {

    static SupplierController supplierController;
    static JFrame OldFrame;
    public static void powerOn(JFrame oldFrame)
    {
        supplierController = SupplierController.getInstance();
        OldFrame = oldFrame;

        //------------------------------------- Create new frame -------------------------------------------

        JFrame currFrame = HelperFunctionGUI.createNewFrame("supplier details");

        //------------------------------------ Create JTable ---------------------------------------

        JTable[] jTables = new JTable[5];
        JScrollPane[] scrollPanes = new JScrollPane[5];
        for(int i=0 ; i < 5 ; i++)
        {
            jTables[i] = new JTable();
            scrollPanes[i] = new JScrollPane();
        }

        //------------------------------------ Create JComboBox -------------------------------------

        JComboBox<String> comboBoxAllSuppliers = HelperFunctionGUI.createComboBoxSupplierNum();

        //----------------------------------------- Create JButton ----------------------------------------

        JButton exitButton = HelperFunctionGUI.createExitButton(currFrame, oldFrame);

        //-------------------------------------- Set bounds ---------------------------------------------

        comboBoxAllSuppliers.setBounds(100,10,300,30);

        //------------------------------------ Add to currFrame -------------------------------------

        HelperFunctionGUI.addComponentsToFrame(currFrame, new JComponent[] {
                comboBoxAllSuppliers ,exitButton});

        // ------------------------------------- Add action listener to JObjects ------------------------------

        comboBoxAllSuppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierNum = comboBoxAllSuppliers.getSelectedItem().toString();
                Supplier supplier = supplierController.getSupplier(supplierNum);


                if(supplierNum.equals(""))
                {
                    for(int i=0 ; i < 5 ; i++)
                        scrollPanes[i].setVisible(false);
                }

                else {

                    for(int i=0 ; i < 5 ; i++)
                        scrollPanes[i].setVisible(false);

                    //------------------------------- Details ------------------------------------

                    String[][] data1 = {{supplier.getName(), supplier.getSupplierNum(), supplier.getBankAccount(), supplier.getPayment().toString()}};
                    String[] columns1 = {"Name", "Number", "BankAccount", "Payment"};
                    jTables[0] = new JTable(data1, columns1);
                    scrollPanes[0] = new JScrollPane(jTables[0]);
                    jTables[0].setBounds(100, 80, 300,40);
                    scrollPanes[0].setBounds(100, 80, 300,40);

                    //------------------------------- Contacts ------------------------------------

                    String[][] data2 = createDataFromContact(supplier);
                    String[] columns2 = {"Name", "PhoneNumber"};
                    jTables[1] = new JTable(data2, columns2);
                    scrollPanes[1] = new JScrollPane(jTables[1]);
                    jTables[1].setBounds(20, 130, 180,100);
                    scrollPanes[1].setBounds(20, 130, 180,100);

                    //------------------------------- Categories ------------------------------------
                    String[][] data3 = createDataFromCategory(supplier);
                    String[] columns3 = {"Category"};
                    jTables[2] = new JTable(data3, columns3);
                    scrollPanes[2] = new JScrollPane(jTables[2]);
                    jTables[2].setBounds(220, 130, 100,100);
                    scrollPanes[2].setBounds(220, 130, 100,100);

                    //------------------------------- Manufacturers ------------------------------------

                    String[][] data4 = createDataFromManufacturer(supplier);
                    String[] columns4 = {"Working With"};
                    jTables[3] = new JTable(data4, columns4);
                    scrollPanes[3] = new JScrollPane(jTables[3]);
                    jTables[3].setBounds(350, 130, 100,100);
                    scrollPanes[3].setBounds(350, 130, 100,100);

                    //------------------------------- Product ------------------------------------

                    String[][] data5 = createDataFromProduct(supplier);
                    String[] columns5 = {"Name", "Manufacturer", "Barcode", "CatalogNum"};
                    jTables[4] = new JTable(data5, columns5);
                    scrollPanes[4] = new JScrollPane(jTables[4]);
                    jTables[4].setBounds(100, 270, 300,100);
                    scrollPanes[4].setBounds(100, 270, 300,100);

                    for(int i=0 ; i < 5 ; i++)
                       currFrame.add(scrollPanes[i]);
                }
            }});


        currFrame.setVisible(true);
    }

    public static String[][] createDataFromContact(Supplier supplier)
    {
        List<String[]> data = new ArrayList<>();
        Map<String, Contact> allContact = supplier.getMyContacts();
        for (Map.Entry<String, Contact> contact : allContact.entrySet()) {
            data.add(new String[]{contact.getValue().getName(), contact.getKey()});
        }
        return data.toArray(new String[data.size()][2]);
    }

    public static String[][] createDataFromCategory(Supplier supplier)
    {
        List<String[]> data = new ArrayList<>();
        List<String> allCategory = supplier.getCategories();
        for (String category : allCategory) {
            data.add(new String[]{category});
        }
        return data.toArray(new String[data.size()][1]);
    }

    public static String[][] createDataFromManufacturer(Supplier supplier)
    {
        List<String[]> data = new ArrayList<>();
        Map<String, Manufacturer> allManufacturer = supplier.getMyManufacturers();
        for (Map.Entry<String, Manufacturer> mManufacturer : allManufacturer.entrySet()) {
            data.add(new String[]{mManufacturer.getValue().getName()});
        }
        return data.toArray(new String[data.size()][1]);
    }

    public static String[][] createDataFromProduct(Supplier supplier)
    {
        List<String[]> data = new ArrayList<>();
        Map<String, SupplierProduct> allProducts = supplier.getMyProducts();
        for (Map.Entry<String, SupplierProduct> product : allProducts.entrySet()) {
            data.add(new String[]{product.getValue().getMyProduct().getName(), product.getValue().getMyProduct().getMyManufacturer().getName(), String.valueOf(product.getValue().getMyProduct().getBarcode()), product.getValue().getSupplierCatalog()});
        }
        return data.toArray(new String[data.size()][4]);
    }
}
