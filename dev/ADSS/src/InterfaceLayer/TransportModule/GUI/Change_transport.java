package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.underway_transport_controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Change_transport extends JFrame {
    private int transport_id;
    private JPanel ChangeTransport;
    private JComboBox changeOptions;
    private JButton excuteSolutionButton;
    private JButton iGiveUpOnButton;
    private JComboBox storesWithGoods;
    private Supplier_goods parent_frame;
    private underway_transport_controller controller;
    private Transport_main transportMain;

    public Change_transport(Transport_main transportMain, Supplier_goods supplier_goods, int transport_id){
        this.transportMain = transportMain;
        controller =  underway_transport_controller.getInstance();
        this.parent_frame = supplier_goods;
        this.transport_id = transport_id;
        pack();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400, 400);
        getContentPane().add(ChangeTransport);
        setLocationRelativeTo(null);

        for (String store_name : underway_transport_controller.getInstance().get_all_stores_with_goods(transport_id)){
            storesWithGoods.addItem(store_name);
        }

        excuteSolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = Character.getNumericValue(((String) changeOptions.getSelectedItem()).charAt(0));
                switch (choice){
                    case 1:
                        if (!controller.is_there_more_than_one(transport_id, "store")) {
                            JOptionPane.showMessageDialog(null,"Sorry Boss, this is the only store left for today...");
                            break;
                        }
                        if(!controller.delete_store_from_route(transport_id, (String) storesWithGoods.getSelectedItem())){
                            JOptionPane.showMessageDialog(null, "The items for the chosen store were dispatched from the truck, but the truck is still in overweight. Please perform another action");
                            break;
                        }
                        JOptionPane.showMessageDialog(null, "The items we currently have for " + (String) storesWithGoods.getSelectedItem() + " were removed.");

                        goBack();
                        break;
                    case 2:
                        if(!controller.is_suitable_truck_exist(transport_id)){
                            JOptionPane.showMessageDialog(null,"Sorry Boss, we don't have a suitable truck...");
                            break;
                        }
                        //now we need to check if we need to assign a driver to the new truck.
                        if(controller.change_truck(transport_id)){
                            JOptionPane.showMessageDialog(null,"The trucks finished to transfer all the goods and it's ready to go,\n" +
                                    "The new driver is: " + controller.get_driver_name(transport_id) + " and his driving the truck: " + controller.get_truck_number(transport_id));
                            goBack();
                            break;
                        }
                        JOptionPane.showMessageDialog(null, "Sorry Boss, we have the right truck for the job, but we don't have a driver with a license for that truck right now...");
                        break;
                    case 3:
                        if (!check_supplier()){
                            JOptionPane.showMessageDialog(null,"Sorry Boss, this is the only supplier left for today...");
                            break;
                        }
                        controller.delete_supplier_from_route(transport_id);
                        controller.add_supplier_to_route(transport_id);
                        JOptionPane.showMessageDialog(null,"The truck route has changed and the supplier goods will be shipped later today by the truck.");
                        goBack();
                        break;
                    case 4:
                        if (!check_supplier()){
                            JOptionPane.showMessageDialog(null,"Sorry Boss, this is the only supplier left for today...");
                            break;
                        }
                        controller.delete_supplier_from_route(transport_id);
                        JOptionPane.showMessageDialog(null,"The goods from the current supplier removed from the truck.");
                        goBack();
                        break;
                }
            }
        });
        changeOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (changeOptions.getSelectedItem().equals("1. I want to cancel shipping to a certain store")){
                    storesWithGoods.setEnabled(true);
                }
                else {
                    storesWithGoods.setEnabled(false);
                    storesWithGoods.setSelectedIndex(0);
                }

            }
        });
        iGiveUpOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"The transport is cancelled and you are getting back to the main menu. you can try to send it later");
                controller.reset_transport(transport_id, false);
                return_to_main_menu();
            }
        });
    }

    private void goBack(){
        parent_frame.setVisible(true);
        parent_frame.proceed_with_transport(transport_id);
        dispose();
    }

    private void return_to_main_menu(){
        transportMain.setVisible(true);
        dispose();
    }

    private boolean check_supplier(){
        boolean moreThanOne = controller.is_there_more_than_one(transport_id, "supplier");
        if (!moreThanOne){
            return false;
        }
        return true;
    }



}
