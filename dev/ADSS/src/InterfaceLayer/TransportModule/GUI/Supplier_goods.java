package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.controllers.underway_transport_controller;
import BussinessLayer.TransportationModule.objects.Transport;
import DataAccessLayer.Transport.Site_Supply_dao;
import DataAccessLayer.Transport.Transport_dao;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private JButton addDocumentButton;
    private JButton backButton;
    private JTextField itemWeight;
    private JTextField totalWeight;
    private Send_transport parent_frame;
    private int transport_id;
    private HashMap<String, String> items;
    private String chosen_store;
    private int documents_added;
    private double total_weight;
    private underway_transport_controller controller;
    private Transport_main main_frame;

    public Supplier_goods(Transport_main main_frame, Send_transport sendTransport, int transport_id, String supplier_name) {
        items = new HashMap<>();
        this.main_frame = main_frame;
        sendTransport.set_supplier_goods(this);
        controller = underway_transport_controller.getInstance();
        documents_added = 0;
        total_weight = 0;
        totalWeight.setEditable(false);
        totalWeight.setText("0");
        this.parent_frame = sendTransport;
        this.transport_id = transport_id;
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1100, 700);
        getContentPane().add(siteSupplies);
        setLocationRelativeTo(null);


        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        for (Store store : transport.getStores()){
            storesInTransport.addItem(store.getName());
        }
        chosen_store = (String) storesInTransport.getSelectedItem();

//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                goBack();
//            }
//        });

        supplierMessage.setText("Hey " + supplier_name + " manager! please choose the store you want to deliver the goods to, and insert the items");

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
                if (itemName.getText().isEmpty() || Amount.getText().isEmpty() || itemWeight.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all the relevant fields - 'name of item', 'single item weight' and 'amount'");
                    return;
                }
                if (!containsOnlyNumbers(Amount.getText()) || !containsOnlyNumbers(itemWeight.getText())){
                    return;
                }
                String wanted_item = itemName.getText();
                String amount = Amount.getText();
                String item_weight = itemWeight.getText();
                double total_weight_of_current_items = Integer.parseInt(amount) * Double.parseDouble(item_weight);
                // updating in case that the item exist
                if(items.containsKey(wanted_item)){
                    int new_amount = Integer.parseInt(amount) + get_amount(items.get(wanted_item));
                    double weight_to_insert = total_weight_of_current_items + get_weight(items.get(wanted_item));
                    items.put(itemName.getText(), new_amount + " : " + weight_to_insert);
                    int itemCount = Items.getItemCount();
                    for (int i = 0; i < itemCount; i++) {
                        if (get_item_name((String) Items.getItemAt(i)).equals(wanted_item)) {
                            Items.removeItemAt(i);
                            Items.addItem(wanted_item + "-" + items.get(wanted_item));
                            break;
                        }
                    }
                }

                else {
                    items.put(wanted_item, amount + " : " + total_weight_of_current_items);
                    Items.addItem(wanted_item + "-" + amount + " : " + total_weight_of_current_items);
                }
                total_weight += (Double.parseDouble(item_weight) * Integer.parseInt(amount));
                totalWeight.setText(Double.toString(total_weight));
                Amount.setText("");
                itemName.setText("");
                itemWeight.setText("");
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
                else if (Integer.parseInt(Amount.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount with digits only.");
                    Amount.setText("");
                }
                else if (Integer.parseInt(Amount.getText()) > 99999) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount with digits only (no larger than 99999).");
                    Amount.setText("");
                }
            }
        });
        resetAllItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                total_weight = 0;
            }
        });
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = Items.getSelectedIndex();
                if (selectedIndex != -1) {
                    // getting the weight and the amount of the deleted item
                    String item = (String) Items.getItemAt(selectedIndex);
                    String[] split = item.split("-");
                    String wanted_item = split[0].trim();
                    String amount_weight = items.get(wanted_item);
//                    int amount = get_amount(amount_weight);
                    double weight = get_weight(amount_weight);
                    // subtracting from the weight
                    total_weight -=  weight;
                    totalWeight.setText(Double.toString(total_weight));
                    items.remove(wanted_item);
                    int itemCount = Items.getItemCount();
                    // remove that item
                    Items.removeItemAt(selectedIndex);
                }
            }
        });
        addDocumentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (documentID.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a valid ID with digits only in a length of 5 digits.");
                    return;
                }
                if (items.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter at least one item.");
                    return;
                }
                // marking we added at least one document
                documents_added++;
                controller.add_site_document_to_driver(transport_id, Integer.parseInt(documentID.getText()) ,chosen_store);
                for (String item : items.keySet()){
                    controller.insert_item_to_siteSupply(Integer.parseInt(documentID.getText()), transport_id, item, get_amount(items.get(item)));
                    controller.insert_item_to_transport(transport_id, item, get_amount(items.get(item)));
                }
                controller.insert_weight_to_siteSupply(Integer.parseInt(documentID.getText()), transport_id, total_weight);
                boolean add_site_to_route = !underway_transport_controller.getInstance().is_store_exist_in_route(transport_id, chosen_store);
                if(add_site_to_route){
                    controller.add_store_to_route(transport_id, chosen_store);
                }
                // resets all the document after we added all the items.
                Items.removeAllItems();
                items.clear();
                documentID.setText("");
                storesInTransport.setSelectedIndex(0);
                itemName.setText("");
                Amount.setText("");
                totalWeight.setText("0");
                total_weight = 0;
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (documents_added  == 0)
                    JOptionPane.showMessageDialog(null, "Please add at least one document.");
                else{
                    get_estimated_time(transport_id);
                    // resets all the document after we added all the items.
                    documentID.setText("");
                    storesInTransport.setSelectedIndex(0);
                    reset();
                    // checking if the truck is in overweight
                    if (!controller.check_weight(transport_id)) {
                        JOptionPane.showMessageDialog(null, "Alert! the truck is in overweight! the truck max weight is - " + + controller.get_truck_weight(transport_id, "m") + " but her current weight is " + controller.get_truck_weight(transport_id, "c") + "!!!");
                        change_transport(transport_id);
                        return;
                    }
                    // proceeding with the transport
                    proceed_with_transport(transport_id);
                }
            }
        });
        itemWeight.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(itemWeight.getText().isEmpty()){
                    return;
                }

                try {
                    Double.parseDouble(itemWeight.getText());
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please put a valid weight with digits only (can be with floating point).");
                    itemWeight.setText("");
                }
            }
        });
    }

    private void goBack(){
        main_frame.setVisible(true);
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
        if(Site_Supply_dao.getInstance().check_if_site_supply_exists(Integer.parseInt(documentID.getText()))){
            JOptionPane.showMessageDialog(this, "This ID is already exist.");
            documentID.setText("");
            return;
        }
        if(controller.is_siteSupply_id_exist_in_current_transport(transport_id, Integer.parseInt(id))){
            JOptionPane.showMessageDialog(this, "This ID is already exist.");
            documentID.setText("");
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
        totalWeight.setText("0");
        total_weight = 0;
    }

    private void get_estimated_time(int transport_id) {
        String estimatedFinishTime = underway_transport_controller.getInstance().getEstimatedEndTime(transport_id);
        String[] options = {"YES", "NO"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "The current Estimated finish time is: " + estimatedFinishTime + "\nWould you like to update the estimated finish time of the transport?",
                "Update Estimated Finish Time",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            String newTime = JOptionPane.showInputDialog(null, "What is the new estimated finish time? (enter time in HH:mm:ss format)");
            while (!isValidTime(newTime) || isBeforeEstimatedTime(newTime, estimatedFinishTime)) {
                newTime = JOptionPane.showInputDialog(null, "Invalid input or time is before the current estimated finish time. Enter a valid time in HH:mm:ss format.");
            }
            controller.setEstimatedEndTime(transport_id, newTime);
        }
    }

    private boolean isValidTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setLenient(false);

        try {
            format.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isBeforeEstimatedTime(String newTime, String estimatedFinishTime) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        try {
            Date newDate = format.parse(newTime);
            Date estimatedDate = format.parse(estimatedFinishTime);
            return newDate.before(estimatedDate);
        } catch (ParseException e) {
            return true;  // Treat parse exception as time being before estimated time
        }
    }

    private void change_transport(int transport_id){
        Change_transport change_transport = new Change_transport(main_frame, this, transport_id);
        change_transport.setVisible(true);
        this.setVisible(false);
    }

    public void proceed_with_transport(int transport_id){
        controller.drive_to_next_location(transport_id);
        while(controller.is_current_location_is_store(transport_id)){
            if (controller.unload_goods(transport_id)) {
                JOptionPane.showMessageDialog(null,"goods unloaded in " + controller.get_current_location_name(transport_id));
            } else {
                JOptionPane.showMessageDialog(null,"We currently don't have any goods for " + controller.get_current_location_name(transport_id) + " ,skip this store for now.");
            }
            get_estimated_time(transport_id);
            controller.drive_to_next_location(transport_id);
            if (!controller.is_current_location_not_null(transport_id)){
                break;
            }
            JOptionPane.showMessageDialog(null,"GPS: You have arrived to " + controller.get_current_location_name(transport_id));
        }
        if (!controller.is_current_location_not_null(transport_id)){
            // means we reached the end!!!
            controller.reset_transport(transport_id, true);
            JOptionPane.showMessageDialog(null,"Transport " + transport_id + " now finished.");
            goBack();
        }
        else {
            JOptionPane.showMessageDialog(null,"GPS: You have arrived to " + controller.get_current_location_name(transport_id));
            documents_added = 0;
            supplierMessage.setText("Hey " + controller.get_current_location_name(transport_id) + "! please choose the store you want to deliver the goods to, and insert the items");
        }
    }

    private int get_amount(String amount_weight){
        String[] parts = amount_weight.split(":");

    // Trim whitespace from the parts
        String amount = parts[0].trim();
        return Integer.parseInt(amount);

    }

    private double get_weight(String amount_weight){
        String[] parts = amount_weight.split(":");

        // Trim whitespace from the parts
        String amount = parts[1].trim();
        return Double.parseDouble(amount);
    }

    private String get_item_name(String ItemsBoxString){
        String[] parts = ItemsBoxString.split("-");

    // Trim whitespace from the parts
        return parts[0].trim();
    }
}
