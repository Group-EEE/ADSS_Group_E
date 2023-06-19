package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Cold_level_choice extends JFrame{
    private JComboBox coldLevels;
    private JButton backButton;
    private JButton saveColdLevelButton;
    private JPanel choose_level;
    private Transport_main main_frame;
    private int current_scene;
    private String cold_level;
public Cold_level_choice(Transport_main transportMain, int scene) {
    this.main_frame = transportMain;
    this.current_scene = scene;
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setSize(900, 200);
    getContentPane().add(choose_level);
    setLocationRelativeTo(null);
    addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            goBack();
        }
    });
    coldLevels.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
    saveColdLevelButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cold_level = coldLevels.getSelectedItem().toString();
            main_frame.set_cold_level(cold_level);
            open_frame(scene);
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
        main_frame.setVisible(true);
        dispose();
    }
    private void open_frame(int choice){
        switch (choice){
            case 1:
                ArrayList<String> trucks_IDs = Logistical_center_controller.getInstance().get_trucks_by_cold_level(cold_level, false);
                if (trucks_IDs.size() == 0){
                    JOptionPane.showMessageDialog(null, "Currently we don't have any trucks with this cold level.");
                    goBack();
                    break;
                }
                Trucks_presentation trucks_presentation = new Trucks_presentation(trucks_IDs, this);
                trucks_presentation.setVisible(true);
                setVisible(false);
                break;
            case 2:

            case 3:

        }
    }

}
