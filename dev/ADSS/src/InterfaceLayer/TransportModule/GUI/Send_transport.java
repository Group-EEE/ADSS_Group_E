package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.underway_transport_controller;
import BussinessLayer.TransportationModule.objects.Transport;
import DataAccessLayer.Transport.Transport_dao;
import InterfaceLayer.TransportModule.underway_transport_UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Send_transport extends JFrame{
    private JComboBox transportIDs;
    private JPanel sendTransport;
    private JTextArea textArea1;
    private JButton sendTransportButton;
    private JButton backButton;
    private Transport_main main_frame;
    private boolean change_transport_succeed;
    private boolean is_finished;
    private Supplier_goods supplier_goods;
    private boolean need_to_change;

    public Send_transport(Transport_main transportMain, ArrayList<Integer> transport_ids) {
        this.main_frame = transportMain;
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 400);
        getContentPane().add(sendTransport);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });
        transportIDs.setPrototypeDisplayValue("Prototype Value");

        ArrayList<Transport> transports = Transport_dao.getInstance().get_transports();
        for (Integer transport_id : transport_ids) {
            transportIDs.addItem(transport_id);
        }
        Transport first_transport = Transport_dao.getInstance().getTransport((Integer) transportIDs.getSelectedItem());
        textArea1.setText(first_transport.details());

        transportIDs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transportIDs.getSelectedItem() != null) {
                    Transport transport = Transport_dao.getInstance().getTransport((Integer) transportIDs.getSelectedItem());
                    textArea1.setText(transport.details());
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        sendTransportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start_transport((Integer) transportIDs.getSelectedItem());
            }
        });
    }

    private void goBack() {
        main_frame.setVisible(true);
        dispose();
    }
    private void Start_transport(int transport_id) {
        underway_transport_controller controller = underway_transport_controller.getInstance();
        // ======================== get Date And Time ======================== //
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String Date = now.toLocalDate().format(dateFormatter);
        String Time = now.toLocalTime().format(timeFormatter);
        // ======================== check if the transport is in the right status ========================
         //check_if_warehouse_worker_exist_in_all_stores(transport_ID) - need to implement the right functions with Chen
        if (!controller.check_if_warehouse_worker_exist_in_all_stores(transport_id, today)) {
            send_message("Transport cancelled, there's no warehouse worker in all of the stores.");
            goBack();
            return;
        }
        // ======================== Update Date And Time ======================== //
        controller.match_driver_and_truck(transport_id);
        controller.set_time_and_date_for_transport(transport_id, Date, Time);
        // ======================== Estimated End Time ======================== //
        controller.getRandomTimeAfter(Time, transport_id);
        // ========================= starting the transport ======================= //
        controller.set_navigator_for_transport(transport_id);
        controller.insert_weight_to_transport(transport_id);
        send_message("Transport - " + transport_id + " started.");
        // driving to the first supplier
        setVisible(false);
        controller.drive_to_next_location(transport_id);

        JOptionPane.showMessageDialog(null,"GPS: You have arrived to " + controller.get_current_location_name(transport_id));

        get_items_from_supplier(controller.get_current_location_name(transport_id));



        // if the transport wasn't aborted, we update the truck and the driver, so they can now go to another shipment.

    }
    public void send_message(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void get_items_from_supplier(String supplier_name){
        Supplier_goods supplier_goods = new Supplier_goods(main_frame, this, (Integer) transportIDs.getSelectedItem(), supplier_name);
        supplier_goods.setVisible(true);

    }


    public void set_change_transport_succeed(boolean succeed){
        change_transport_succeed = succeed;
    }



    public void set_is_finished(boolean finished) {
        is_finished = finished;
    }

    public boolean Is_finished() {
        return is_finished;
    }

    public void set_supplier_goods(Supplier_goods supplier_goods){
        this.supplier_goods = supplier_goods;
    }

    public boolean is_supplier_goods_is_visible(){
        return supplier_goods.isVisible();

    }
}
