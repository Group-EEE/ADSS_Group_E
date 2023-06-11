package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.Transport;
import DataAccessLayer.Transport.Transport_dao;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.util.HashMap;

public class Supplier_goods extends JFrame{
    private JTextField documentID;
    private JLabel supplierMessage;
    private JComboBox storesInTransport;
    private JPanel siteSupplies;
    private JTextField itemName;
    private JTextField Amount;
    private JButton Submission;
    private JButton resetAllItemsButton;
    private JButton deleteItemButton;
    private JComboBox Items;
    private JButton button1;
    private JButton button2;
    private Send_transport parent_frame;
    private int transport_id;
    private HashMap<String, Integer> items;
    private String chosen_store;

    public Supplier_goods(Send_transport sendTransport, int transport_id, String supplier_name) {
        this.parent_frame = sendTransport;
        this.transport_id = transport_id;
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400, 400);
        getContentPane().add(siteSupplies);

        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        for (Store store : transport.getStores()){
            storesInTransport.addItem(store.getName());
        }
        chosen_store = (String) storesInTransport.getSelectedItem();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });

        supplierMessage.setText("Hey " + supplier_name + "! please choose the store you want to deliver the goods to, and insert the items");

        documentID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!documentID.getText().isEmpty())
                    validate_ID();
            }
        });
        storesInTransport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!chosen_store.equals((String) storesInTransport.getSelectedItem())){
                    reset();
                }
                chosen_store = (String) storesInTransport.getSelectedItem();
            }
        });
        Submission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (itemName.getText().isEmpty() || Amount.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all the relevant fields - 'name of item' and 'amount'");
                    return;
                }
                String wanted_item = itemName.getText();
                String amount = Amount.getText();
                if(items.containsKey(wanted_item)){
                    items.put(itemName.getText(), items.get(itemName.getText()) + Integer.parseInt(amount));
                    int itemCount = Items.getItemCount();
                    for (int i = 0; i < itemCount; i++) {
                        if (Items.getItemAt(i).equals(wanted_item + "-" + amount)) {
                            Items.setSelectedItem(wanted_item + "-" + items.get(wanted_item).toString());
                            break;
                        }
                    }
                }
                else {
                    items.put(wanted_item, Integer.parseInt(amount));
                    Items.addItem(wanted_item + "-" + amount);
                }
            }
        });


        Amount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (Amount.getText().isEmpty()) {
                    return;
                }
                if (!containsOnlyNumbers(Amount.getText())) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount with digits only.");
                    Amount.setText("");
                }
                if (Integer.parseInt(Amount.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount with digits only.");
                    Amount.setText("");
                }
                if (Integer.parseInt(Amount.getText()) > 99999) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount with digits only.");
                    Amount.setText("");
                }
            }
        });
        resetAllItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = Items.getSelectedIndex();
                if (selectedIndex != -1) {
                    Items.removeItemAt(selectedIndex);
                }
            }
        });
    }

    private void goBack(){
        parent_frame.setVisible(true);
        this.dispose();
    }

    private void validate_ID(){
        String id = documentID.getText();
        if (!containsOnlyNumbers(id)){
            JOptionPane.showMessageDialog(this, "Please enter a valid ID with digits only in a length of 5 digits.");
            documentID.setText("");
            return;
        }
        if (id.length() != 5){
            JOptionPane.showMessageDialog(this, "Please enter a valid ID with digits only in a length of 5 digits.");
            documentID.setText("");
            return;
        }
    }

    private boolean containsOnlyNumbers(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void reset() {
        Amount.setText("");
        itemName.setText("");
        items.clear();
        Items.removeAllItems();
    }
}
