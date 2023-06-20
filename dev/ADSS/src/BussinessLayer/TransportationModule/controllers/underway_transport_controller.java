package BussinessLayer.TransportationModule.controllers;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Controllers.ScheduleController;
import BussinessLayer.HRModule.Objects.ShiftType;
import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.*;
import DataAccessLayer.HRMoudle.EmployeesDAO;
//import DataAccessLayer.Transport.Drivers_dao;
import DataAccessLayer.Transport.Transport_dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// singleton
public class underway_transport_controller {
    private static underway_transport_controller instance;
    private static Logistical_center_controller logistical_center_controller;
    EmployeesDAO _employeesDAO;

    public static underway_transport_controller getInstance() {
        if (instance == null) {
            instance = new underway_transport_controller();
        }
        return instance;
    }

    private underway_transport_controller() {
        logistical_center_controller = Logistical_center_controller.getInstance();
        _employeesDAO = EmployeesDAO.getInstance();
    }

    public void add_site_document_to_driver(int transport_id, int site_supplier_ID, String store_name) {
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Store store = transport.getStoreByName(store_name);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        String address = truck.get_current_location().getAddress();
        // creating and adding the document to the driver
        Site_Supply site_supply = new Site_Supply(site_supplier_ID, store, address);
        truck.getCurrent_driver().Add_site_document(site_supply);
    }

    public void insert_item_to_siteSupply(int site_supplier_ID, int transport_id, String item_name, int item_amount){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck_Driver driver = getDriverByTruckNumber(transport.getTruck_number());
        Site_Supply site_supply = null;
        for(Site_Supply s : driver.getSites_documents()){
            if(s.getId() == site_supplier_ID){
                site_supply = s;
                break;
            }
        }
        site_supply.insert_item(item_name, item_amount);
    }

    public void insert_item_to_transport(int transport_id, String item_name, int item_amount){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        transport.insertToProducts(item_name, item_amount);
    }

    private Truck_Driver get_driver_by_transport_id(int transport_id){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        return _employeesDAO.getDriver(transport.getDriver_ID());
    }

    /**
     * @param site_supplier_ID document ID
     * @param transport_id transport id
     * @param weight the weight of the items
     *               the function adding the weight we got from the user to the truck, transport document and the site supply document.
     */
    public void insert_weight_to_siteSupply(int site_supplier_ID, int transport_id, double  weight){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck_Driver driver = get_driver_by_transport_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        Site_Supply site_supply = null;
        for(Site_Supply s : driver.getSites_documents()){
            if(s.getId() == site_supplier_ID){
                site_supply = s;
                break;
            }
        }
        // inserting the weight.
        site_supply.setProducts_total_weight(weight);
        truck.addWeight(weight);
        transport.insertToWeights(weight);
    }


    /**
     * @param transport_id transport ID
     * @param finished_transport boolean value that indicates if the transport is finished
     *                           the function reset the transport details that need to be reset.
     */
    public void reset_transport(int transport_id, boolean finished_transport){
        Transport chosen_transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck_Driver driver = get_driver_by_transport_id(transport_id);
        ArrayList<Site_Supply> empty_array = new ArrayList<>();
        Truck truck = get_truck_by_registration_plate(chosen_transport.getTruck_number());
        chosen_transport.setStarted(true);
        driver.setSites_documents(empty_array);
        truck.setCurrent_weight(truck.getNet_weight());

        if (!finished_transport) {
            chosen_transport.setStarted(false);
            ArrayList<Double> empty_weights = new ArrayList<>();
            chosen_transport.setWeighing(empty_weights);
            return;
        }

        Transport_dao.getInstance().getInstance().mark_transport_as_finished(transport_id);
        Transport_dao.getInstance().insert_products_to_table(chosen_transport);
        truck.setCurrent_driver(null);
        truck.setOccupied(false);

        driver.setCurrent_truck(null);

    }

    /**
     * @param registration_plate registration plate of the truck
     * @return the truck that has the registration plate
     */
    public Truck get_truck_by_registration_plate(String registration_plate){
        return logistical_center_controller.getTruckByNumber(registration_plate);
    }

    /**
     * @param transport_ID transport ID
     *                     the function set the navigator for the transport
     */
    public void set_navigator_for_transport(int transport_ID){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        truck.setNavigator(transport.getDestinations());
    }

    /**
     * @param truck_number registration_plate of the truck
     * @return the driver of the truck
     */
    public Truck_Driver getDriverByTruckNumber(String truck_number){
        Truck truck = get_truck_by_registration_plate(truck_number);
        return truck.getCurrent_driver();
    }

    /**
     * @param transport_ID int represent the transport ID
     * @param Date String represent the date
     * @param Time String represent the current time
     *             the function set the date and time for the transport when it starts.
     */
    public void set_time_and_date_for_transport(int transport_ID,String Date,String Time){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        transport.setDate(Date);
        transport.setDeparture_time(Time);
        //I'm here in the check start transport
        // need to create :
        Transport_dao.getInstance().getInstance().update_transport_date_and_time(transport_ID, Date, Time);
    }

    public boolean is_siteSupply_id_exist_in_current_transport(int transport_id, int siteSupply_ID){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck_Driver driver = _employeesDAO.getDriver(transport.getDriver_ID());
        for(Site_Supply s : driver.getSites_documents()){
            if(s.getId() == siteSupply_ID){
                return true;
            }
        }
        return false;
    }

    public boolean is_siteSupply_id_exist_in_system(int siteSupply_ID){
        return logistical_center_controller.check_if_site_supply_exist(siteSupply_ID);
    }

    // unloading all the goods in a store, and update the weight of the truck accordingly.
    public boolean unload_goods(int transport_ID){
        Truck_Driver driver = get_driver_by_transport_id(transport_ID);
        Store store = (Store) driver.getCurrent_truck().get_current_location();
        boolean unloaded = false;
        for (int i = 0; i< driver.getSites_documents().size(); i++){
            if (driver.getSites_documents().get(i).getStore().getAddress().equals(store.getAddress())){
                unloaded = true;
                logistical_center_controller.insert_site_supply_to_database(driver.getSites_documents().get(i));
                // subtracts the weight of the goods that was unloaded
                driver.getCurrent_truck().addWeight(-1 * driver.getSites_documents().get(i).getProducts_total_weight());
                // change to delete only one site.
                driver.delete_site_document_by_ID(driver.getSites_documents().get(i).getId());
                i--;
            }
        }
        return unloaded;
    }

    /**
     * @param transport_id transport ID
     * @return boolean value that indicates if the current location is a store
     */
    public boolean is_current_location_is_store(int transport_id){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        return truck.get_current_location().is_store();
    }

    /**
     * @param transport_id transport ID
     * @return boolean value that indicates if the current location is not null
     */
    public boolean is_current_location_not_null(int transport_id){
        Transport  transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        return truck.get_current_location() != null;
    }

    /**
     * @param transport_id transport ID
     * @return the current location name
     */
    public String get_current_location_name(int transport_id){
        Transport  transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        return truck.get_current_location().getSite_name();
    }

    /**
     * @param transport_id transport ID
     * @return the current location address
     */
    public String get_current_location_address(int transport_id){
        Transport  transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        return truck.get_current_location().getAddress();
    }

    public void drive_to_next_location(int transport_id){
        Transport  transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        truck.get_next_site();
    }

    public boolean is_suitable_truck_exist(int transport_id){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        cold_level level = transport.getRequired_level();
        double weight = truck.getCurrent_weight();
        truck = null;
        for(Truck t : logistical_center_controller.get_trucks()){
            if(t.getCold_level().getValue() <= level.getValue() && t.getMax_weight() > weight && !Transport_dao.getInstance().getInstance().check_if_truck_taken_that_date(transport.getPlanned_date(), t.getRegistration_plate())) {
                return true;
            }
        }
        return false;
    }

    public String get_driver_name(int transport_id){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        return transport.getDriver_name();
    }

    public String get_truck_number(int transport_id){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        return transport.getTruck_number();
    }

    public boolean change_truck(int transport_id){
        // finding the most suitable truck
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        cold_level level = transport.getRequired_level();
        double weight = truck.getCurrent_weight();
        String date = transport.getDate();
        Truck new_truck = null;
        // getting the truck that have the minimum weight that suits the current transport weight, and the most close cold level.
        for(Truck t : logistical_center_controller.get_trucks()){
            if(t.getCold_level().getValue() <= level.getValue() && t.getMax_weight() > weight && !is_truck_taken_that_day(t.getRegistration_plate(), date, true, transport_id)) {
                if(t.getCold_level().getValue() == level.getValue()){
                    if (new_truck == null) {
                        new_truck = t;
                    }
                    else if (t.getMax_weight() < new_truck.getMax_weight()) {
                        new_truck = t;
                    }
                }
                else if (new_truck == null) {
                    new_truck = t;
                } else if (level.getValue() - t.getCold_level().getValue() < level.getValue() - new_truck.getCold_level().getValue()) {
                    new_truck = t;
                }
            }
        }
        Truck_Driver old_driver = truck.getCurrent_driver();
        if(old_driver.getLicense().getWeight() >= new_truck.getMax_weight() && old_driver.getLicense().getCold_level().getValue() <= new_truck.getCold_level().getValue()){
            truck.setCurrent_driver(null);
            truck.setOccupied(false);
            truck.setCurrent_driver(null);
            new_truck.setCurrent_driver(old_driver);
            // updating the details in the transport document
            transport.setTruck_number(new_truck.getRegistration_plate());
            // add the weight to the new truck.
            new_truck.setCurrent_weight(truck.getCurrent_weight());
            // reset the weight of the old truck.
            truck.setCurrent_weight(truck.getNet_weight());
            // reset the truck of the driver to be the new truck.
            old_driver.setCurrent_truck(new_truck);

            new_truck.set_ready_navigator(truck.getNavigator());
            Transport_dao.getInstance().update_truck_number(transport_id, new_truck.getRegistration_plate());

            return true;
        } else if (logistical_center_controller.truck_assigning_drivers_in_shift(new_truck.getRegistration_plate(), transport.getPlanned_date())) {
            // check what happens here!!!!
            Truck_Driver new_driver = new_truck.getCurrent_driver();
            truck.setCurrent_driver(null);
            truck.setOccupied(false);
            truck.setCurrent_driver(null);
            // updating the details in the transport document
            transport.setTruck_number(new_truck.getRegistration_plate());
            transport.setDriver_ID(new_truck.getCurrent_driver().getEmployeeID());
            // add the weight to the new truck.
            new_truck.setCurrent_weight(truck.getCurrent_weight());
            // add the sites documents to the new driver
            for (Site_Supply site_supply: old_driver.getSites_documents()){
                new_truck.getCurrent_driver().Add_site_document(site_supply);
            }
            // getting the route to the new truck
            new_truck.set_ready_navigator(truck.getNavigator());
            // reset the weight of the old truck.
            truck.setCurrent_weight(truck.getNet_weight());
            // reset the truck of the driver to be the new truck.
            old_driver.setCurrent_truck(null);
            Transport_dao.getInstance().update_truck_number(transport_id, new_truck.getRegistration_plate());
            Transport_dao.getInstance().update_driver_name_and_id(transport_id, new_driver.getEmployeeID(), new_driver.getFullName());
            return true;
        }
        return false;
//        ScheduleController.getInstance().addStandByDriverToLogisticsShift();
    }

    /**
     * @param transport_id transport ID
     * @return true if the truck is not in overweight, false otherwise
     */
    public boolean check_weight(int transport_id){
        Transport  transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        if (truck.getCurrent_weight() > truck.getMax_weight()){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * @param transport_id transport ID
     * @param choice c for current weight, m for max weight, n for net weight
     * @return the weight of the truck according to the choice given as a parameter, 0 otherwise.
     */
    public double get_truck_weight(int transport_id, String choice){
        Transport  transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        if (choice.equals("c")){
            return truck.getCurrent_weight();
        }
        if (choice.equals("m")){
            return truck.getMax_weight();
        }
        if (choice.equals("n")){
            return truck.getNet_weight();
        }
        return 0;
    }

    // ========= functions mainly for change transport =========

    /**
     * @param transport_id transport ID
     * @param type supplier or store
     * @return true if there is more than one supplier or store in the route, false otherwise
     */
    public boolean is_there_more_than_one(int transport_id, String type) {
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        if (type.equals("supplier")) {
            int supplier_count = 0;
            if (truck.get_current_location().is_supplier()){
                supplier_count++;
            }
            for (Site site : truck.getNavigator().getRoute()) {
                if (site.is_supplier()) {
                    supplier_count++;
                }
            }
            if (supplier_count <= 1) {
                return false;
            }
        }

        if(type.equals("store")){
            int store_count = 0;
            for (Site site : truck.getNavigator().getRoute()) {
                if (site.is_store()) {
                    store_count++;
                }
            }
            if (store_count == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param transport_ID transport ID
     * @param site_type supplier or store
     * @param site_name site name
     * @return true if the site exists in the route, false otherwise
     */
    public boolean is_site_exist(int transport_ID, String site_type, String site_name ){

        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        for (Site site : transport.getDestinations()) {
            if (site_type.equals("supplier") && site.is_supplier()) {
                if (site_name.equals(site.getSite_name())){
                    return true;
                }
            }
            else if (site_type.equals("store") && site.is_store()) {
                if (site_name.equals(site.getSite_name())){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean delete_store_from_route(int transport_ID, String site_name){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        Truck_Driver driver = truck.getCurrent_driver();
        for (Site_Supply site_supply : driver.getSites_documents()) {
            if (site_supply.getStore().getSite_name().equals(site_name)) {
                for (String product : site_supply.getItems().keySet()) {
                    transport.deleteProducts(product, site_supply.getItems().get(product));
                    //System.out.println("We dropped " + site_supply.getItems().get(product) + " of the product - " + product);
                }
                truck.addWeight(-1 * site_supply.getProducts_total_weight());
            }
        }
        driver.delete_site_document_by_destination(site_name);
        //transport.deleteDestination(site_name);
        truck.getNavigator().delete_site(site_name);
        return check_weight(transport_ID);
    }

    /**
     * @param transport_ID transport ID
     *                     the function deletes the site from the route and returns the products to the supplier
     */
    public void delete_supplier_from_route(int transport_ID){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        truck.getNavigator().delete_site(truck.get_current_location().getSite_name());

        // getting the products back to the supplier and delete the documents that not relevant for now.
        truck.getCurrent_driver().delete_site_document_by_origin(truck.get_current_location().getSite_name());

        // now we are getting back to the previous weight:
        transport.delete_last_Weight();
        truck.setCurrent_weight(transport.get_last_weight());
    }

    public boolean is_store_exist_in_route(int transport_id, String store_name){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        boolean is_store_exist_in_route = false;
        for (Site site : truck.getNavigator().getRoute()) {
            if (site.is_store() && site.getSite_name().equals(store_name)) {
                is_store_exist_in_route = true;
                break;
            }
        }
        return is_store_exist_in_route;
    }

    public ArrayList<String> get_all_stores_with_goods(int transport_id){
        ArrayList<String> stores_with_goods = new ArrayList<>();
        HashSet<String> stores_names = new HashSet<>();
        Truck_Driver driver = get_driver_by_transport_id(transport_id);
        for (Site_Supply site_supply : driver.getSites_documents()) {
            stores_names.add(site_supply.getStore().getSite_name());
        }
        for (String store_name : stores_names) {
            stores_with_goods.add(store_name);
        }
        return stores_with_goods;
    }

    /**
     * @param transport_ID transport ID
     *                     the function adds the site to the route and all the stores that he
     */
    public void add_supplier_to_route(int transport_ID){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        truck.getNavigator().add_site(truck.get_current_location());
    }

    /**
     * @param transport_ID transport ID
     * @param store_name the name of the store we're adding
     *                   the function add store to the transport.
     */
    public void add_store_to_route(int transport_ID, String store_name){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        for (Site site : transport.getDestinations()) {
            if (site.is_store() && site.getSite_name().equals(store_name)) {
                truck.getNavigator().add_site(site);
                return;
            }
        }
    }

    public void match_driver_and_truck(int transport_ID){
        Truck_Driver truck_driver = get_driver_by_transport_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(get_truck_number(transport_ID));
        truck_driver.setCurrent_truck(truck);
        truck.setCurrent_driver(truck_driver);
    }

    public boolean check_if_warehouse_worker_exist_in_all_stores(int transport_ID, LocalDate date){
        Transport transport = Transport_dao.getInstance().getTransport(transport_ID);
        for (Site site: transport.getDestinations()){
            if (site.is_store()){
                Store current_store = (Store) site;
                int shift_id = ScheduleController.getInstance().getShiftIDByDate(current_store.getName(), date, ShiftType.MORNING);
                boolean shift1 = Facade.getInstance().haswarehouse(shift_id, current_store.getName());
                boolean shift2 = Facade.getInstance().haswarehouse(shift_id+1, current_store.getName());
                if (!shift1 || !shift2) return false;
            }
        }
        return true;
    }


    public  void getRandomTimeAfter(String currentTimeString, int transport_ID) {
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        ArrayList<Site> destinations = transport.getDestinations();
        int maxSecondsToAdd = destinations.size() * 1800;  // 30 minutes in seconds

        if (maxSecondsToAdd == 0) {
            maxSecondsToAdd = 1;  // Ensure maxSecondsToAdd is at least 1
        }

// Parse the current time string using the format string
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar now = Calendar.getInstance();
        try {
            now.setTime(dateFormat.parse(currentTimeString));
        } catch (ParseException e) {
            // Handle the parse exception here
        }

// Add a random number of seconds to the current time (30-60 minutes per destination)
        Random random = new Random();
        int randomSeconds = random.nextInt(maxSecondsToAdd) + (30 * 60);  // Random number between 30-60 minutes
        now.add(Calendar.SECOND, randomSeconds);

// Format the new time as a string using the format string
        String newTimeString = dateFormat.format(now.getTime());

        transport.setEstimated_end_time(newTimeString);

// Update dao
        Transport_dao.getInstance().update_transport_estimated_end_time(transport_ID, newTimeString);
    }

    public void setEstimatedEndTime(int transport_ID, String newTime){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        transport.setEstimated_end_time(newTime);
        Transport_dao.getInstance().update_transport_estimated_end_time(transport_ID, newTime);
    }

    public String getEstimatedEndTime(int transport_ID){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        return transport.getEstimated_end_time();
    }

    public boolean isValidTime(String Time){
        LocalTime current_time = LocalTime.now();
        LocalTime time;
        try {
            time = LocalTime.parse(Time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception e) {
            return false;
        }
        return time.isAfter(current_time);
    }

    public boolean isValidTimeString(String timeString) {
        String pattern = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(timeString);
        return matcher.matches();
    }

    public boolean is_truck_taken_that_day(String registration_plate, String date, boolean is_transport_exist, int transport_id){
        ArrayList<Transport> transports = Transport_dao.getInstance().get_transports();
        if (is_transport_exist) {
            for (Transport transport : transports) {
                if (transport.getPlanned_date().equals(date) && transport.getTransport_ID() != transport_id && transport.getTruck_number().equals(registration_plate)) {
                    return true;
                }
            }
        }
        else {
            for (Transport transport : transports) {
                if (transport.getPlanned_date().equals(date) && transport.getTruck_number().equals(registration_plate)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void insert_weight_to_transport(int transport_ID){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        transport.insertToWeights(truck.getCurrent_weight());
    }
}
