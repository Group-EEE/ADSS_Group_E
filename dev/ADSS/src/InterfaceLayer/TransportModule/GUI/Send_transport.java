package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.objects.Transport;
import DataAccessLayer.Transport.Transport_dao;
import InterfaceLayer.TransportModule.underway_transport_UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Send_transport extends JFrame{
    private JComboBox transportIDs;
    private JPanel sendTransport;
    private JTextArea textArea1;
    private JButton sendTransportButton;
    private JButton backButton;
    private Transport_main main_frame;

    public Send_transport(Transport_main transportMain) {
        this.main_frame = transportMain;
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400, 400);
        getContentPane().add(sendTransport);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });
        transportIDs.setPrototypeDisplayValue("Prototype Value");

        ArrayList<Transport> transports = Transport_dao.getInstance().get_transports();
        for (Transport transport : transports) {
            transportIDs.addItem(transport.getTransport_ID());
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
        underway_transport_UI underway_transport = new underway_transport_UI();
        underway_transport.start_transport(transport_id,  this);
    }
    public void send_message(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void get_items_from_supplier(String supplier_name){
        Supplier_goods supplier_goods = new Supplier_goods(this, (Integer) transportIDs.getSelectedItem(), supplier_name);
    }
}
