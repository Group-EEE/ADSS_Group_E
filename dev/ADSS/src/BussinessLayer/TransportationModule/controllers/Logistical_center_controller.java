package BussinessLayer.TransportationModule.controllers;

import BussinessLayer.TransportationModule.objects.*;
import DataAccessLayer.Transport.ready_database;

import java.util.ArrayList;
import java.util.Map;

// singleton
public class Logistical_center_controller {
    // singleton
    private static Logistical_center_controller instance;
    private Transport_System transport_system;
    private Logistical_Center logistical_center;
    private ready_database ready_database;
    public static Logistical_center_controller getInstance(){
        if (instance == null){
            instance = new Logistical_center_controller();
        }
        return instance;
    }
    // singleton

    private Logistical_center_controller(){
        transport_system = new Transport_System();
    }

    public void create_Logistical_Center(String address, String phone, String name, String site_contact_name){
        if (logistical_center == null){
            logistical_center = new Logistical_Center(address, phone, name, site_contact_name);
        }
    }

    public Logistical_Center getLogistical_center() {
        return logistical_center;
    }

    public void add_driver (int driver_ID, String driver_name, int license_id, cold_level level, double truck_weight){
        Truck_Driver new_driver = new Truck_Driver(driver_ID, driver_name, license_id, level, truck_weight);
        logistical_center.add_driver(new_driver);
    }

    public Transport get_transport_by_id(int transport_id){
        return logistical_center.get_transport_by_id(transport_id);
    }

    public void add_transport(int transport_id, String truck_number, String driver_name, String cold_lvl){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        Transport transport = new Transport(transport_id, "TBD", "TBD", truck_number, driver_name, this.logistical_center.getSite_name(), cool_level);
        logistical_center.add_transport(transport);
    }

    public boolean truck_assigning(String new_truck_registration_plate){
        Truck_Driver driver;
        Truck truck = getTruckByNumber(new_truck_registration_plate);
        // checking if the given parameters are valid, and getting the diver and the truck if they exist.

        for (Truck_Driver truck_driver  : logistical_center.getDrivers()) {
            if (truck_driver.getLicense().getWeight() >= truck.getMax_weight() && truck_driver.getLicense().getCold_level().getValue() <= truck.getCold_level().getValue() && truck_driver.getCurrent_truck() == null){
                driver = truck_driver;
                truck.setCurrent_driver(truck_driver);
                truck.setOccupied(true);
                driver.setCurrent_truck(truck);
                return true;
            }
        }
        return false;
    }

    public Truck getTruckByColdLevel (cold_level level){
        Truck truck = null;
        for(Truck t : logistical_center.getTrucks()){
            if(t.getCold_level().getValue() <= level.getValue() && !t.Occupied()) {
                if(t.getCold_level().getValue() == level.getValue()){
                    truck = t;
                    break;
                }
                else if (truck == null) {
                    truck = t;
                } else if (level.getValue() - t.getCold_level().getValue() < level.getValue() - truck.getCold_level().getValue()) {
                    truck = t;
                }
            }
        }
        return truck;
    }

    public String get_truck_number_by_cold_level(String cold_lvl){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        Truck truck = getTruckByColdLevel(cool_level);
        return truck.getRegistration_plate();
    }

    public boolean check_if_truck_exist_by_cold_level(String cold_lvl){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        for(Truck t : logistical_center.getTrucks()){
            if(!t.Occupied() && t.getCold_level().getValue() <= cool_level.getValue()){
                return true;
            }
        }
        return false;
    }

    /**
     * @param transport_id int representing the transport id
     * @param truck_ID string representing the truck number
     *                 this function inserts the truck weight to the transport
     */
    public void insert_weight_to_transport(int transport_id, String truck_ID){
        Truck truck = getTruckByNumber(truck_ID);
        Transport transport = logistical_center.get_transport_by_id(transport_id);
        transport.insertToWeights(truck.getNet_weight());
    }

    public ArrayList<Truck_Driver> getDrivers(){
        return logistical_center.getDrivers();
    }

    public boolean check_if_site_exist_in_transport(String site_name, int transport_id){
        Transport transport = logistical_center.get_transport_by_id(transport_id);
        for (Site site: transport.getDestinations()){
            if (site.getSite_name().equals(site_name)){
                return true;
            }
        }
        return false;
    }

    public void insert_store_to_transport(int transport_id, String store_address, String phone_number, String store_name, String manager_name, int area, String store_contact_name){
        Transport transport = logistical_center.get_transport_by_id(transport_id);
        Store store = new Store(store_address, phone_number, store_name, manager_name, area, store_contact_name);
        transport.insertToDestinations(store);
    }

    public void insert_supplier_to_transport(int transport_id, String supplier_name, String supplier_address, String phone_number, String contact_name){
        Transport transport = logistical_center.get_transport_by_id(transport_id);
        Supplier supplier = new Supplier(supplier_name, supplier_address, phone_number, contact_name);
        transport.insertToDestinations(supplier);
    }

    public Map<Integer, Transport> getTransport_Log(){
        return logistical_center.getTransport_Log();
    }

    public Truck getTruckByNumber(String truck_number){
        Truck truck = null;
        for(Truck t : logistical_center.getTrucks()){
            if(t.getRegistration_plate().equals(truck_number)){
                truck = t;
            }
        }
        return truck;
    }

    public void add_truck(String registration, String truck_moodle, double truck_net_weight, double truck_max_weight, cold_level level, double current_weight){
        Truck truck = new Truck(registration, truck_moodle, truck_net_weight, truck_max_weight, level, current_weight);
        logistical_center.add_truck(truck);
    }

    public boolean is_truck_exist(String truck_ID){
        for(Truck truck : logistical_center.getTrucks()){
            if(truck.getRegistration_plate().equals(truck_ID)){
                return false;
            }
        }
        return true;
    }

    public void display_transport_doc(){
        for (Map.Entry<Integer, Transport> entry : logistical_center.getTransport_Log().entrySet()) {
            int id = entry.getKey();
            Transport transport = entry.getValue();
            System.out.println("=========== Transport - " + id + " - information ===========");
            transport.transportDisplay();
        }
    }

    public void display_trucks(){
        System.out.println("======================================= Trucks in the system =======================================");
        for(Truck t : logistical_center.getTrucks()){
            t.truckDisplay();
        }
    }

    public void display_drivers(){
        System.out.println("======================================= Drivers in the system =======================================");
        for(Truck_Driver driver : logistical_center.getDrivers()){
            driver.driverDisplay();
        }
    }

    public void display_site_supply(){
        for (Map.Entry<Store, ArrayList<Site_Supply>> entry : logistical_center.getDelivered_supplies_documents().entrySet()) {
            for(Site_Supply siteSupply : entry.getValue()){
                siteSupply.sDisplay();
            }
        }
    }

    public boolean check_if_driver_Id_exist(int driver_ID){
        for (Truck_Driver driver: logistical_center.getDrivers()){
            if (driver.getID() == driver_ID){
                return true;
            }
        }
        return false;
    }

    public boolean check_if_license_id_exist(int license_ID){
        for (Truck_Driver driver: logistical_center.getDrivers()){
            if (driver.getLicense().getL_ID() == license_ID){
                return true;
            }
        }
        return false;
    }

    public boolean check_if_transport_id_exist(int transport_ID){
        for (Transport transport: logistical_center.getTransport_Log().values()){
            if (transport.getTransport_ID() == transport_ID){
                return true;
            }
        }
        return false;
    }

    public void display_trucks_by_cold_level(String cold_lvl){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        System.out.println("======================================= Trucks with cold level '" + cool_level + "' in the system =======================================");
        for (Truck t : logistical_center.getTrucks()) {
            if (t.getCold_level().getValue() == cool_level.getValue()) {
                t.truckDisplay();
            }
        }
    }

    private cold_level get_cold_level_by_string(String cold_lvl){
        switch (cold_lvl){
            case "Cold":
                return cold_level.Cold;
            case "Freeze":
                return cold_level.Freeze;
            case "Dry":
                return cold_level.Dry;
        }
        return null;
    }

    public void set_started_transport(int transport_id){
        Transport transport = logistical_center.get_transport_by_id(transport_id);
        transport.setStarted(true);
    }

    public boolean at_least_one_unsent_transport(){
        for (Transport transport: logistical_center.getTransport_Log().values()){
            if (!transport.Started()){
                return true;
            }
        }
        return false;
    }

    public void load_database(){
        ready_database = new ready_database();
        ready_database.load_database();
    }
}
