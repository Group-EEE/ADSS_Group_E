package InterfaceLayer.TransportModule.GUI;

import BussinessLayer.HRModule.Controllers.EmployeeController;
import BussinessLayer.HRModule.Controllers.ScheduleController;
import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.ShiftType;
import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.controllers.underway_transport_controller;
import BussinessLayer.TransportationModule.objects.Supplier;
import BussinessLayer.TransportationModule.objects.Truck_Driver;
import DataAccessLayer.HRMoudle.StoresDAO;
import DataAccessLayer.Transport.Suppliers_dao;
import DataAccessLayer.Transport.Transport_dao;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Create_transport extends JFrame {
    private Transport_main main_frame;
    private JPanel createTransport;
    private JTextField plannedDate;
    private JTextField transportID;
    private JComboBox coldLevel;
    private JLabel Truck;
    private JComboBox Drivers;
    private JComboBox Trucks;
    private JComboBox areas;
    private JButton createTransportButton;
    private JButton goBack;
    private JComboBox Stores;
    private JComboBox Suppliers;
    private JButton storesReset;
    private JButton suppliersReset;
    private JTextArea selected_stores_text;
    private JTextArea selected_suppliers_text;
    private Truck_Driver truck_driver;
    private boolean isValidating = false;
    private boolean isFinished = true;
    private Set<String> selected_stores;
    private Set<String> selected_suppliers;
    private String current_planned_date;

    public Create_transport(Transport_main transportMain) {
        this.main_frame = transportMain;
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 600);

        getContentPane().add(createTransport);

        setLocationRelativeTo(null);

        selected_suppliers = new HashSet<>();
        selected_stores = new HashSet<>();

        List<Supplier> suppliers = Suppliers_dao.getInstance().SelectAllSuppliers();
        for (Supplier supplier : suppliers) {
            Suppliers.addItem(supplier.getSupplier_name());
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });


        transportID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!transportID.getText().isEmpty()) {
                    validate_transport_id();
                }
            }
        });

        plannedDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!plannedDate.getText().equals(current_planned_date)){
                    reset_fields();
                }
                if (!plannedDate.getText().isEmpty()) {
                    validate_planned_date();
                }
                current_planned_date = plannedDate.getText();
            }
        });

        coldLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFinished = false;
                if (transportID.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a transport ID first.");
                    coldLevel.setSelectedIndex(0);
                    isFinished = true;
                    return;
                }
                if (plannedDate.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a planned date first.");
                    coldLevel.setSelectedIndex(0);
                    isFinished = true;
                    return;
                }
                if (Trucks.getSelectedItem() != null){
                    Trucks.removeAllItems();
                }
                ArrayList<String> trucks_IDs = Logistical_center_controller.getInstance().get_trucks_by_cold_level((String) coldLevel.getSelectedItem(), true);
                if (trucks_IDs.isEmpty()) {
                    // Handle the empty drivers list, e.g., show a message or set a default driver
                    JOptionPane.showMessageDialog(null, "No trucks found for that cold level.");
                    if (Drivers.getSelectedItem() != null){
                        Drivers.removeAllItems();
                    }
                    return;
                }

                for (String truckID : trucks_IDs) {
                    if (underway_transport_controller.getInstance().is_truck_taken_that_day(truckID, current_planned_date, false, 0)){
                        continue;
                    }
                    Trucks.addItem(truckID);
                }
                update_drivers_list();
            }
        });
        Trucks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFinished) {
                    return;
                }
                isFinished = false;
                if (transportID.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a transport ID first.");
                    isFinished = true;
                    return;
                }
                if (plannedDate.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a planned date first.");
                    isFinished = true;
                    return;
                }
                if (Trucks.getSelectedItem() == null){
                    isFinished = true;
                    return;
                }
//                if (Transport_dao.getInstance().check_if_truck_taken_that_date(plannedDate.getText(), (String) Trucks.getSelectedItem())){
//                    JOptionPane.showMessageDialog(null, "This truck is already taken that date.");
//                    isFinished = true;
//                    return;
//                }
                if (Drivers.getSelectedItem() != null){
                    Drivers.removeAllItems();
                }
                update_drivers_list();
            }

        });
        Drivers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFinished){
                    return;
                }
                isFinished = false;
                if (transportID.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a transport ID first.");
                    isFinished = true;
                    return;
                }
                if (plannedDate.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a planned date first.");
                    isFinished = true;
                    return;
                }
                List<Store> stores = StoresDAO.getInstance().SelectAllStores();
                for (Store store : stores) {
                    if (store.get_area() == Integer.parseInt((String) areas.getSelectedItem()) && transportMain.is_store_have_schedule(store)){
                        Stores.addItem(store.getName());
                    }
                }

                isFinished = true;
            }
        });
        areas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFinished){
                    return;
                }
                isFinished = false;
                if (Stores.getSelectedItem() != null) {
                    Stores.removeAllItems();
                }
                selected_suppliers.clear();
                selected_stores.clear();
                selected_stores_text.setText("");
                selected_suppliers_text.setText("");

                List<Store> stores = StoresDAO.getInstance().SelectAllStores();
                int count = 0;
                for (Store store : stores) {
                    if (store.get_area() == Integer.parseInt((String) areas.getSelectedItem()) && transportMain.is_store_have_schedule(store)){
                        Stores.addItem(store.getName());
                        count++;
                    }
                }
                if (count == 0){
                    JOptionPane.showMessageDialog(null, "There are not known stores in that area that currently have an active schedule.");
                }
                isFinished = true;
            }
        });



        createTransportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transportID.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a transport ID first.");
                    return;
                }
                if (plannedDate.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a planned date first.");
                    return;
                }
                if (Trucks.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null, "Please select a truck.");
                    return;
                }
                if (Drivers.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null, "Please select a driver.");
                    return;
                }
                if (selected_stores.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please select at least one store.");
                    return;
                }
                if (selected_suppliers.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please select at least one supplier.");
                    return;
                }

                String driver = Drivers.getSelectedItem().toString();
                int transport_Id = Integer.parseInt(transportID.getText());
                int separatorIndex = driver.indexOf("-");
                String driver_ID = driver.substring(separatorIndex + 1).trim();
                String driver_name = driver.substring(0, separatorIndex).trim();
                // check how the destinations are inserted inside in the create transport function in the UI.
                Logistical_center_controller.getInstance().add_transport(transport_Id, (String) Trucks.getSelectedItem(), driver_name, (String) coldLevel.getSelectedItem(),  plannedDate.getText(), Integer.parseInt(driver_ID));

                String suppliers_list = "";
                for (String supplier : selected_suppliers){
                    Logistical_center_controller.getInstance().insert_supplier_to_transport(transport_Id, supplier);
                    suppliers_list += supplier + ",";
                }

                String stores_list = "";
                for (String store : selected_stores){
                    Logistical_center_controller.getInstance().insert_store_to_transport(transport_Id, store);
                    stores_list += store + ",";
                }

                LocalDate planned_date = LocalDate.parse(plannedDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Logistical_center_controller.getInstance().insert_sites_names_to_transport(transport_Id, stores_list, suppliers_list);
                for (String store : stores_list.split(",")){
                    int shift_id = ScheduleController.getInstance().getShiftIDByDate(store, planned_date, ShiftType.MORNING);
                    try {
                        ScheduleController.getInstance().addMustBeFilledWareHouse(store, shift_id);
                        ScheduleController.getInstance().addMustBeFilledWareHouse(store, shift_id + 1);
                    }
                    catch (Exception ex){}
                }
                Truck_Driver driver_to_shift = EmployeeController.getInstance().getDriver(Integer.parseInt(driver_ID));
                int shift_id = ScheduleController.getInstance().getShiftIDByDate("Logistics", planned_date, ShiftType.MORNING);
                try {
                    ScheduleController.getInstance().addEmployeeToShift(driver_to_shift, "Logistics", shift_id);
                    ScheduleController.getInstance().addEmployeeToShift(driver_to_shift, "Logistics", shift_id + 1);
                }
                catch (Exception exc){}
                JOptionPane.showMessageDialog(null, "The transport was created successfully");
                reset_fields();
                plannedDate.setText("");
                transportID.setText("");

            }
        });
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });


        Stores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFinished){
                    return;
                }
                isFinished = false;
                int size = selected_stores.size();
                selected_stores.add(Stores.getSelectedItem().toString());
                if (size + 1  == selected_stores.size()){
                    selected_stores_text.append(Stores.getSelectedItem().toString() + "\n");
                }
                isFinished = true;
            }
        });
        Suppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isFinished){
                    return;
                }
                int size = selected_suppliers.size();
                selected_suppliers.add(Suppliers.getSelectedItem().toString());
                if (size + 1 == selected_suppliers.size()){
                    selected_suppliers_text.append(Suppliers.getSelectedItem().toString() + "\n");
                }
            }
        });
        storesReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_stores.clear();
                selected_stores_text.setText("");
            }
        });
        suppliersReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_suppliers.clear();
                selected_suppliers_text.setText("");
            }
        });
    }



    public void goBack(){
        main_frame.setVisible(true);
        dispose();
    }

    private void validate_transport_id() {
        if (!containsOnlyNumbers(transportID.getText()) || transportID.getText().length() != 5) {
            JOptionPane.showMessageDialog(null, "Transport ID must be 5 digits long and only contain numbers");
            transportID.setText("");
            return;
        }
        if (Logistical_center_controller.getInstance().check_if_transport_id_exist(Integer.parseInt(transportID.getText()))) {
            JOptionPane.showMessageDialog(null, "This transport ID already exists.");
            transportID.setText("");
            return;
        }

    }

    private void validate_planned_date() {
        if (transportID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a transport ID first.");
            plannedDate.setText("");
            return;
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(plannedDate.getText(), dateFormatter);
            if (date.isBefore(currentDate) || date.isAfter(currentDate.plusWeeks(1))) {
                JOptionPane.showMessageDialog(null, "The date must not be before the current date and not more than one week from the current date.");
                plannedDate.setText("");
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(null, "Invalid date format.");
            plannedDate.setText("");
        }
    }

    private void update_drivers_list() {
        if (Drivers.getSelectedItem() != null){
            Drivers.removeAllItems();
        }
        ArrayList<Truck_Driver> drivers = Logistical_center_controller.getInstance().get_possible_drivers_for_truck((String) Trucks.getSelectedItem(), plannedDate.getText());
        if (drivers.isEmpty()) {
            // Handle the empty drivers list, e.g., show a message or set a default driver
            JOptionPane.showMessageDialog(null, "No drivers found.");
            return;
        }
        for (Truck_Driver driver : drivers) {
            if (drivers.indexOf(driver) == drivers.size() -1){
                isFinished = true;
            }
            Drivers.addItem(driver.getFullName() + " - " + driver.getEmployeeID());
        }
    }

    private void reset_fields() {
        isFinished = false;
        if (Trucks.getSelectedItem() != null) {
            Trucks.removeAllItems();
        }
        if (Drivers.getSelectedItem() != null) {
            Drivers.removeAllItems();
        }
        if (Stores.getSelectedItem() != null) {
            Stores.removeAllItems();
        }
        selected_suppliers.clear();
        selected_stores.clear();
        selected_stores_text.setText("");
        selected_suppliers_text.setText("");
        areas.setSelectedIndex(0);
        Suppliers.setSelectedIndex(0);
        //plannedDate.setText("");
        //transportID.setText("");
        isFinished = true;
    }

    private boolean containsOnlyNumbers(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
