package BussinessLayer.TransportationModule.controllers;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.*;
import DataAccessLayer.Transport.Drivers_dao;
import DataAccessLayer.Transport.Transport_dao;

import java.util.ArrayList;

// singleton
public class underway_transport_controller {
    private static underway_transport_controller instance;
    private static Logistical_center_controller logistical_center_controller;

    public static underway_transport_controller getInstance() {
        if (instance == null) {
            instance = new underway_transport_controller();
        }
        return instance;
    }

    private underway_transport_controller() {
        logistical_center_controller = Logistical_center_controller.getInstance();
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

    private Truck_Driver get_driver_by_transport_id(int transport_id){
        Transport transport = logistical_center_controller.get_transport_by_id(transport_id);
        return Drivers_dao.get_instance().getDriver(transport.getDriver_ID());
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
        Truck truck = driver.getCurrent_truck();
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
        Truck_Driver driver = Drivers_dao.get_instance().get_instance().getDriver(transport.getDriver_ID());
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
            if(t.getCold_level().getValue() <= level.getValue() && t.getMax_weight() > weight && Transport_dao.getInstance().getInstance().check_if_truck_taken_that_date(transport.getPlanned_date(), t.getRegistration_plate())) {
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
        Truck new_truck = null;
        // getting the truck that have the minimum weight that suits the current transport weight, and the most close cold level.
        for(Truck t : logistical_center_controller.get_trucks()){
            if(t.getCold_level().getValue() <= level.getValue() && t.getMax_weight() > weight) {
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
        // searching for a driver that can drive the new truck
        if (logistical_center_controller.truck_assigning_drivers_in_shift(new_truck.getRegistration_plate(), transport.getPlanned_date())) {
            // updating the current driver's truck and the opposite.
            Truck_Driver old_driver = truck.getCurrent_driver();
            truck.setCurrent_driver(null);
            truck.setOccupied(false);
            // updating the details in the transport document
            transport.setTruck_number(new_truck.getRegistration_plate());
            new_truck.setCurrent_weight(truck.getCurrent_weight());
            // reset the weight of the old truck
            truck.setCurrent_weight(truck.getNet_weight());
            // transferring the goods and the documents
            Truck_Driver new_truck_driver = new_truck.getCurrent_driver();
            if (!new_truck_driver.equals(old_driver)) {
                transport.setDriver_name(new_truck_driver.getName());
                new_truck_driver.setSites_documents(old_driver.getSites_documents());
                old_driver.setSites_documents(null);
                old_driver.setCurrent_truck(null);
            }
            new_truck.setNavigator(truck.getNavigator().getRoute());
            return true;
        }
        return false;
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
                    System.out.println("We dropped " + site_supply.getItems().get(product) + " of the product - " + product);
                }
                truck.addWeight(-1 * site_supply.getProducts_total_weight());
            }
        }
        driver.delete_site_document_by_destination(site_name);
        transport.deleteDestination(site_name);
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

    public boolean check_if_warehouse_worker_exist_in_all_stores(int transport_ID){
        Transport transport = Transport_dao.getInstance().getTransport(transport_ID);
        boolean exist = true;
        for (Site site: transport.getDestinations()){
            if (site.is_store()){
                //if(boolean function that chen need to implement to check if there's warehouse worker in store){
                // exist = false;
                // break;
                // }
            }
        }
        return exist;
    }
}
