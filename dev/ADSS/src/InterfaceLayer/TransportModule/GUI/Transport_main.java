package InterfaceLayer.TransportModule.GUI;

import DataAccessLayer.HRMoudle.StoresDAO;
import DataAccessLayer.Transport.Suppliers_dao;
import DataAccessLayer.Transport.Transport_dao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transport_main extends JFrame{
    private JButton startButton;
    private JPanel panelMain;
    private JLabel label1;
    private JComboBox menuOptions;
    private JTextField txtName;
    private String action;
    private String cold_level;
    public Transport_main() {


        menuOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = menuOptions.getSelectedItem().toString();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                action = menuOptions.getSelectedItem().toString();
                int choice;
                if (action.length() >= 2 && Character.isDigit(action.charAt(0)) && Character.isDigit(action.charAt(1))) {
                    choice = Integer.parseInt(action.substring(0, 2));
                } else {
                    choice = Integer.parseInt(action.substring(0, 1));
                }
                open_frame(choice);
            }

        });
    }

    private void open_frame(int choice){
        switch (choice){
            case 1:
                Register_supplier registerSupplier = new Register_supplier(this);
                registerSupplier.setVisible(true);
                setVisible(false);
                break;
            case 2:
                Cold_level_choice cold_level_choice = new Cold_level_choice(this, 1);
                cold_level_choice.setVisible(true);
                setVisible(false);
                break;
            case 3:
                if (!StoresDAO.getInstance().isAnyStoreExist()){
                    JOptionPane.showMessageDialog(null, "There's no stores in the Database!");
                    break;
                }
                if (!Suppliers_dao.getInstance().is_any_supplier_exist()){
                    JOptionPane.showMessageDialog(null, "There's no suppliers in the Database!");
                    break;
                }
                Create_transport create_transport = new Create_transport(this);
                JOptionPane.showMessageDialog(null, "Please fill in the required fields in order from top to bottom. \n please notice: \n\tthe date must be no further than one week, and not before today. \n\tif you don't have any choices at all in some field, it means that we don't have a match for what you've asked.");
                create_transport.setVisible(true);
                setVisible(false);
                break;
            case 4:
                if (Transport_dao.getInstance().get_transports().size() == 0){
                    JOptionPane.showMessageDialog(null, "There's no transports in the Database!");
                    break;
                }
                Send_transport send_transport = new Send_transport(this);
                send_transport.setVisible(true);
                setVisible(false);
                break;
            case 5:
                Add_truck new_truck = new Add_truck(this);
                new_truck.setVisible(true);
                setVisible(false);
                break;
            case 6:
                Drivers_Displayer drivers = new Drivers_Displayer(this);
                drivers.setVisible(true);
                setVisible(false);
                break;
            case 7:
                Truck_Display trucks = new Truck_Display(this);
                trucks.setVisible(true);
                setVisible(false);
                break;
            case 8:
                Site_Supply_Display site_supply_dis = new Site_Supply_Display(this);
                site_supply_dis.setVisible(true);
                setVisible(false);
                break;
            case 9:
                Store_Display stores = new Store_Display(this);
                stores.setVisible(true);
                setVisible(false);
                break;
            case 10:
                Add_Standby_driver sd = new Add_Standby_driver(this);
                sd.setVisible(true);
                setVisible(false);
                break;
        }
    }
    public void set_cold_level(String level){
        cold_level = level;
    }

    public static void main(String[] args) {
        Transport_main transportMain = new Transport_main();
        transportMain.setContentPane(transportMain.panelMain);
        transportMain.setTitle("Transport Manager");
        transportMain.setSize(500, 300);
        transportMain.setVisible(true);
        transportMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
