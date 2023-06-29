package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.objects.Transport;
import DataAccessLayer.HRMoudle.StoresDAO;
import DataAccessLayer.Transport.Suppliers_dao;
import DataAccessLayer.Transport.Transport_dao;
import InterfaceLayer.GUI.All_Roles_GUI;
import InterfaceLayer.GUI.HRModule.HRManager.HRmenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Transport_main extends JFrame{
    private JButton startButton;
    private JPanel panelMain;
    private JLabel label1;
    private JComboBox menuOptions;
    private JTextField txtName;
    private String action;
    private String cold_level;
    private All_Roles_GUI all_roles_gui;
    public Transport_main(All_Roles_GUI all_roles_gui) {
        this.all_roles_gui = all_roles_gui;
        setContentPane(panelMain);
        setTitle("Transport Manager");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });

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

        setLocationRelativeTo(null);

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
                    setVisible(true);
                    break;
                }
                if (!Suppliers_dao.getInstance().is_any_supplier_exist()){
                    JOptionPane.showMessageDialog(null, "There's no suppliers in the Database!");
                    setVisible(true);
                    break;
                }
                int counter = 0;
                for (Store store: StoresDAO.getInstance().SelectAllStores()){
                    counter++;
                }
                if (counter == 0){
                    JOptionPane.showMessageDialog(null, "There's no stores that have active schedules in the Database!");
                    setVisible(true);
                    break;
                }
                Create_transport create_transport = new Create_transport(this);
                JOptionPane.showMessageDialog(null, "Please fill in the required fields in order from top to bottom. \n please notice: \n\tthe date must be no further than one week, and not before today. \n\tif you don't have any choices at all in some field, it means that we don't have a match for what you've asked.");
                create_transport.setVisible(true);
                setVisible(false);
                break;
            case 4:
                ArrayList<Integer> transport_ids = new ArrayList<>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                // Get the current date
                LocalDate currentDate = LocalDate.now();
                String date_today = currentDate.format(formatter);
                for (Map.Entry<Integer, Transport> entry : Logistical_center_controller.getInstance().getTransport_Log().entrySet()) {
                    if (!entry.getValue().Started() && entry.getValue().getPlanned_date().equals(date_today) && all_stores_have_schedules(entry.getValue())) {
                        transport_ids.add(entry.getKey());
                    }
                }


                if (transport_ids.size() == 0){
                    JOptionPane.showMessageDialog(this, "There's no transports planned for today that all the stores have schedules!");
                    setVisible(true);
                    break;
                }
                Send_transport send_transport = new Send_transport(this, transport_ids);
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
            case 11:
                Transport_display transport_display = new Transport_display(this);
                transport_display.setVisible(true);
                setVisible(false);
        }

    }
    public void set_cold_level(String level){
        cold_level = level;
    }

    private boolean all_stores_have_schedules(Transport transport){
        //Facade.getInstance().hasSchedule()
        Calendar calendar = Calendar.getInstance();

        // Get the day of the month
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Get the month (Note: Month values start from 0 for January)
        int month = calendar.get(Calendar.MONTH) + 1;

        // Get the year
        int year = calendar.get(Calendar.YEAR);

        for (Store store: transport.getStores()){
            if (!Facade.getInstance().hasSchedule(store.getName(), day, month, year)){
                return false;
            }
        }
        return true;
    }

    public boolean is_store_have_schedule(Store store){
        Calendar calendar = Calendar.getInstance();
        // Get the day of the month
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Get the month (Note: Month values start from 0 for January)
        int month = calendar.get(Calendar.MONTH) + 1;
        // Get the year
        int year = calendar.get(Calendar.YEAR);
        return Facade.getInstance().hasSchedule(store.getName(), day, month, year);
    }

    private void goBack(){
            // Create an instance of the main menu frame
            all_roles_gui = new All_Roles_GUI("TransportManager");
            all_roles_gui.setVisible(true);
            dispose();
    }
}
