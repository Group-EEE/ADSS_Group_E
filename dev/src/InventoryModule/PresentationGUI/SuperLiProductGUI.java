package InventoryModule.PresentationGUI;

import InventoryModule.Business.Controllers.ProductController;
import SuppliersModule.PresentationGUI.HelperFunctionGUI;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static InventoryModule.Business.Discount.update_discount_bycategory;
import static InventoryModule.PresentationCLI.StoreKeeperPresentationCLI.reader;

public class SuperLiProductGUI {
    static JFrame OldFrame;

    public static void powerOn(JFrame oldFrame) {
        OldFrame = oldFrame;
        //------------------------------------- Create new frame -------------------------------------------
        JFrame SuperLiProductFrame = HelperFunctionGUI.createNewFrame("SuperLiProduct");
        //----------------------------------------- Create JLabel ----------------------------------------
        JLabel chooseLabel = new JLabel("Please choose an option");

        //option 1 - Add new product to the store
        JLabel productBarcodeLabel = new JLabel("Choose Product");
        JLabel productNameLabel = new JLabel("Product name:");
        JLabel CostumerPriceLabel = new JLabel("Enter Costumer Price");
        JLabel CategoryLabel = new JLabel("Enter Category");
        JLabel SubCategoryLabel = new JLabel("Enter Sub-Category");
        JLabel SubSubCategoryLabel = new JLabel("Enter Sub-Category");
        JLabel SupplyDaysLabel = new JLabel("Enter Supply Days");
        JLabel ManufacturerLabel = new JLabel("Enter Manufacturer");
        JLabel MinimumAmountLabel = new JLabel("Enter Minimum amount");
        JLabel checkCostumerPriceLabel = HelperFunctionGUI.createCheckLabel("Invalid Value",350, 100, 150, 20);
        JLabel checkSupplyDaysLabel = HelperFunctionGUI.createCheckLabel("Invalid Value",350, 210, 150, 20);
        JLabel checkMinimumAmountLabel = HelperFunctionGUI.createCheckLabel("Invalid Value",350,280,150,20);

        //----------------------------------------- Create JTextField ----------------------------------------

        //option 1 - Add new product to the store
        JTextField productNameField = new JTextField();
        JTextField CostumerPriceField = new JTextField();
        JTextField CategoryField = new JTextField();
        JTextField SubCategoryField = new JTextField();
        JTextField SubSubCategoryField = new JTextField();
        JTextField SupplyDaysField = new JTextField();
        JTextField ManufacturerField = new JTextField();
        JTextField MinimumAmountField = new JTextField();

        //----------------------------------------- Create JComboBox ----------------------------------------

        JComboBox<String> chooseComboBox = new JComboBox<>(new String[]{"", "Add new product to the store",
                "Get all products' barcode"});

        JComboBox<String> productBarcodeComboBox = HelperFunctionGUI.createComboBoxofnewProductBarcode();
        //JComboBox<String> productBarcodeComboBox = HelperFunctionGUI.createComboBoxProductBarcode();

        //----------------------------------------- Create JButton ----------------------------------------

        //option 1
        JButton AddNewProduct = new JButton("Submit");

        //option 2
        JButton ShowBarcodes = new JButton("Show Barcodes");

        //exit
        JButton exitButton = HelperFunctionGUI.createExitButton(SuperLiProductFrame, OldFrame);

        //----------------------------------------- Set bounds ---------------------------------------------

        chooseLabel.setBounds(10, 10, 150, 20);
        chooseComboBox.setBounds(170, 10, 270, 20);

        //option 1
        productBarcodeLabel.setBounds(10, 40, 150, 20);
        productBarcodeComboBox.setBounds(170, 40, 150, 20);

        productNameLabel.setBounds(10, 70, 150, 20);
        productNameField.setBounds(170, 70, 150, 20);

        CostumerPriceLabel.setBounds(10, 100, 150, 20);
        CostumerPriceField.setBounds(170, 100, 150, 20);

        CategoryLabel.setBounds(10, 130, 150, 20);
        CategoryField.setBounds(170, 130, 150, 20);

        SubCategoryLabel.setBounds(10, 160, 150, 20);
        SubCategoryField.setBounds(170, 160, 150, 20);

        SubSubCategoryLabel.setBounds(10, 190, 150, 20);
        SubSubCategoryField.setBounds(170, 190, 150, 20);

        SupplyDaysLabel.setBounds(10, 210, 150, 20);
        SupplyDaysField.setBounds(170, 210, 150, 20);

        ManufacturerLabel.setBounds(10, 240, 150, 20);
        ManufacturerField.setBounds(170, 240, 150, 20);

        MinimumAmountLabel.setBounds(10, 270, 150, 20);
        MinimumAmountField.setBounds(170, 270, 150, 20);

        AddNewProduct.setBounds(200, 360, 100, 40);

        //-------------------------------------- Set not visible ---------------------------------------------

        //option 1
        JComponent[] JComponentsAddProduct = new JComponent[]{productBarcodeLabel,productNameLabel
                ,CostumerPriceLabel,CategoryLabel,SubCategoryLabel,SubSubCategoryLabel,SupplyDaysLabel
                ,ManufacturerLabel,MinimumAmountLabel, productNameField,CostumerPriceField,
                CategoryField,SubCategoryField,SubSubCategoryField ,SupplyDaysField,
                ManufacturerField,MinimumAmountField, productBarcodeComboBox, AddNewProduct};

        HelperFunctionGUI.hideComponents(JComponentsAddProduct);

        //------------------------------------ Add to currFrame -------------------------------------
        HelperFunctionGUI.addComponentsToFrame(SuperLiProductFrame, new JComponent[]{chooseLabel,
                chooseComboBox, productBarcodeLabel,productNameLabel
                ,CostumerPriceLabel,CategoryLabel,SubCategoryLabel,SubSubCategoryLabel,SupplyDaysLabel
                ,ManufacturerLabel,MinimumAmountLabel, productNameField,CostumerPriceField,
                CategoryField,SubCategoryField,SubSubCategoryField ,SupplyDaysField,
                ManufacturerField,MinimumAmountField, productBarcodeComboBox, AddNewProduct, checkCostumerPriceLabel,
                checkSupplyDaysLabel, checkMinimumAmountLabel});

        // ------------------------------------- Add action listener to JObjects ------------------------------
        productNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = productBarcodeComboBox.getSelectedItem().toString();
                HelperFunctionGUI.setProductNameField(choose, productNameField);
            }
        });

        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choose = chooseComboBox.getSelectedItem().toString();

                if (choose.equals("")) {
                    checkSupplyDaysLabel.setVisible(false);
                    checkMinimumAmountLabel.setVisible(false);
                    checkCostumerPriceLabel.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                }
                if (choose.equals("Add new product to the store")) {
                    checkSupplyDaysLabel.setVisible(false);
                    checkMinimumAmountLabel.setVisible(false);
                    checkCostumerPriceLabel.setVisible(false);
                    HelperFunctionGUI.showComponents(JComponentsAddProduct);
                }
                if (choose.equals("Get all products' barcode")) {
                    checkSupplyDaysLabel.setVisible(false);
                    checkMinimumAmountLabel.setVisible(false);
                    checkCostumerPriceLabel.setVisible(false);
                    HelperFunctionGUI.hideComponents(JComponentsAddProduct);
                }
            }
        });

        AddNewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                int Barcode = Integer.parseInt(productBarcodeComboBox.getSelectedItem().toString());
                String Pname = productNameField.getText();
                Double CostumerPrice = 0.0;
                if (!HelperFunctionGUI.CheckDoubleInput(CostumerPriceField.getText())) {
                    checkCostumerPriceLabel.setVisible(true);
                    isValid = false;
                } else {
                    checkCostumerPriceLabel.setVisible(false);
                    CostumerPrice = Double.parseDouble(CostumerPriceField.getText());
                }

                String Category = CategoryField.getText();
                String SubCategory = SubCategoryField.getText();
                String SubSubCategory = SubSubCategoryField.getText();
                int SupplyDays = 0;
                if (!HelperFunctionGUI.CheckIntInput(SupplyDaysField.getText())) {
                    checkSupplyDaysLabel.setVisible(true);
                    isValid = false;
                } else {
                    checkSupplyDaysLabel.setVisible(false);
                    SupplyDays = Integer.parseInt(SupplyDaysField.getText());
                }

                String Manufacturer = ManufacturerField.getText();
                int MinimumAmount = 0;

                if (!HelperFunctionGUI.CheckIntInput(MinimumAmountField.getText())) {
                    checkMinimumAmountLabel.setVisible(true);
                    isValid = false;
                } else {
                    checkMinimumAmountLabel.setVisible(false);
                    MinimumAmount = Integer.parseInt(MinimumAmountField.getText());
                }
                if (isValid) {
                    ProductController.getInstance().addProduct(Barcode, Pname, CostumerPrice, Category,SubCategory,
                            SubSubCategory, SupplyDays, Manufacturer, MinimumAmount);
                    SuperLiProductFrame.dispose();
                    OldFrame.setVisible(true);
                    HelperFunctionGUI.ShowProcessSuccessfully();
                }
            }
        });
        SuperLiProductFrame.setVisible(true);
    }
}
