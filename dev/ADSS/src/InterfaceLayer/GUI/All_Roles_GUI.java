package InterfaceLayer.GUI;

import BussinessLayer.HRModule.Objects.RoleType;
import InterfaceLayer.GUI.HRModule.HRManager.HRmenu;
import InterfaceLayer.TransportModule.GUI.Transport_main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class All_Roles_GUI extends JFrame{
    private JPanel AllRoles;
    private JButton logisticsManagerButton;
    private JButton HRManagerButton;
    private JLabel message;
    private JButton employeeButton;

    public All_Roles_GUI(String Role){
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        getContentPane().add(AllRoles);
        setLocationRelativeTo(null);

        message.setText("Hey " + Role + ", Please choose the relevant choice for your desired actions:");


        HRManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Role.equals("TransportManager") || Role.equals("Employee")) {
                    JOptionPane.showMessageDialog(null, "Sorry Boss, you have access only to the HR's Menu.");
                    return;
                }
                // here will be the code that will be executed when the user clicks on the HRManagerButton
                start_HR_GUI();
            }
        });


        logisticsManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Role.equals("HRManager") || Role.equals("Employee")) {
                    JOptionPane.showMessageDialog(null, "Sorry Boss, you have access only to the Transport's Menu.");
                    return;
                }
                // here will be the code that will be executed when the user clicks on the logisticsManagerButton
                start_Transport_GUI();
            }
        });
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Role.equals("HRManager") || Role.equals("TransportManager")) {
                    JOptionPane.showMessageDialog(null, "Sorry Boss, you have access only to the Employee's Menu.");
                    return;
                }
                // here will be the code that will be executed when the user clicks on the logisticsManagerButton
                Login.setRoleTypePremission(RoleType.Employee);
                Login login = new Login();
                dispose();
            }
        });
    }

    private void start_Transport_GUI() {
        Transport_main transport_main = new Transport_main(this);
        transport_main.setVisible(true);
        dispose();
    }

    private void start_HR_GUI() {
        HRmenu hRmenu = new HRmenu();
        //hRmenu.setVisible(true);
        dispose();
        // HR_main hr_main = new HR_main(this); or something like that I don't know
    }
}
