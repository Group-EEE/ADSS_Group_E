package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Trucks_presentation extends JFrame{
    private JComboBox trucks;
    private JTextArea truckDetails;
    private JButton backButton;
    private JPanel trucksPresentation;
    private JButton present;
    private ArrayList<String> trucks_IDs;
    Cold_level_choice parent_frame;
    public Trucks_presentation(ArrayList<String> trucks_IDs, Cold_level_choice parent_frame) {
        this.parent_frame = parent_frame;
        this.trucks_IDs = trucks_IDs;
        for (String truckID : trucks_IDs) {
            trucks.addItem(truckID);
        }
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 200);
        getContentPane().add(trucksPresentation);

        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });
        trucks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        present.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                truckDetails.setText("");
                truckDetails.append(Logistical_center_controller.getInstance().getTruckByNumber((String) trucks.getSelectedItem()).details());
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private void goBack() {
        parent_frame.setVisible(true);
        dispose();
    }
}
