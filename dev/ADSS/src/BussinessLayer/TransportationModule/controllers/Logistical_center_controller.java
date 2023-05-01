package BussinessLayer.TransportationModule.controllers;

import BussinessLayer.TransportationModule.objects.*;
import DataAccessLayer.Transport.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// singleton
public class Logistical_center_controller {
    // singleton
    private Trucks_dao trucks_dao;
    private Transport_dao transport_dao;
    private Site_Supply_dao site_supply_dao;
    private Drivers_dao drivers_dao;

    private License_dao license_dao;
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
        trucks_dao = new Trucks_dao("Trucks");
        transport_dao = new Transport_dao("Transports");
        license_dao = new License_dao("Licenses");
        drivers_dao = new Drivers_dao("Drivers");
        site_supply_dao = new Site_Supply_dao("Sites_Documents");
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
        drivers_dao.Insert(new_driver);
    }

    public Transport get_transport_by_id(int transport_id){
        return transport_dao.getTransport(transport_id);
    }

    public boolean check_if_site_supply_exist(int id){
        return site_supply_dao.check_if_site_supply_exists(id);
    }

    public void insert_site_supply_to_database(Site_Supply site_supply){
        site_supply_dao.Insert(site_supply);
    }

    public void add_transport(int transport_id, String truck_number, String driver_name, String cold_lvl, String planned_date, int driver_id){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        Transport transport = new Transport(transport_id, "TBD", "TBD", truck_number, driver_name, this.logistical_center.getSite_name(), cool_level, planned_date, driver_id );
        transport_dao.Insert(transport);
    }

    public boolean truck_assigning(String new_truck_registration_plate){
        Truck_Driver driver;
        Truck truck = getTruckByNumber(new_truck_registration_plate);
        // checking if the given parameters are valid, and getting the diver and the truck if they exist.

        for (Truck_Driver truck_driver  : drivers_dao.getDrivers()) {
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
        for(Truck t : trucks_dao.getTrucks()){
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

    /**
     * @param cold_lvl cold level as string
     * @return true if we have a truck that not occupied and suitable with the cold level constraint, otherwise false
     */
    public boolean check_if_truck_exist_by_cold_level(String cold_lvl){
        cold_level cool_level = cold_level.fromString(cold_lvl);
        for(Truck t : trucks_dao.getTrucks()){
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
        Transport transport = transport_dao.getTransport(transport_id);
        transport.insertToWeights(truck.getNet_weight());
    }

    public ArrayList<Truck_Driver> getDrivers(){
        return drivers_dao.getDrivers();
    }

    public boolean check_if_site_exist_in_transport(String site_name, int transport_id){
        Transport transport = transport_dao.getTransport(transport_id);
        for (Site site: transport.getDestinations()){
            if (site.getSite_name().equals(site_name)){
                return true;
            }
        }
        return false;
    }

    public void insert_store_to_transport(int transport_id, String store_address, String phone_number, String store_name, String manager_name, int area, String store_contact_name){
        Transport transport = transport_dao.getTransport(transport_id);
        Store store = new Store(store_address, phone_number, store_name, manager_name, area, store_contact_name);
        transport.insertToDestinations(store);
    }

    public void insert_supplier_to_transport(int transport_id, String supplier_name, String supplier_address, String phone_number, String contact_name){
        Transport transport = transport_dao.getTransport(transport_id);
        Supplier supplier = new Supplier(supplier_name, supplier_address, phone_number, contact_name);
        transport.insertToDestinations(supplier);
    }

    public Map<Integer, Transport> getTransport_Log(){
        return transport_dao.get_transports_map();
    }

    public Truck getTruckByNumber(String truck_number){
        return trucks_dao.get_truck_by_registration_plate(truck_number);
    }

    public void add_truck(String registration, String truck_model, double truck_net_weight, double truck_max_weight, cold_level level, double current_weight){
        Truck truck = new Truck(registration, truck_model, truck_net_weight, truck_max_weight, level, current_weight);
        trucks_dao.Insert(truck);
    }

    public boolean is_truck_exist(String truck_ID){
        return trucks_dao.check_if_truck_exists(truck_ID);
    }

    public void display_transport_doc(){
        if(transport_dao.get_transports().size() == 0){
            System.out.println("\nThere are no transports documents in the system\n");
        }
        else {
            for (Map.Entry<Integer, Transport> entry : transport_dao.get_transports_map().entrySet()) {
                int id = entry.getKey();
                Transport transport = entry.getValue();
                System.out.println("=========== Transport - " + id + " - information ===========");
                transport.transportDisplay();
            }
        }
    }

    public void display_trucks(){
        if(trucks_dao.getTrucks().size() == 0){
            System.out.println("\nThere are no trucks in the system\n");
        }
        else {
            System.out.println("======================================= Trucks in the system =======================================");
            for (Truck t : trucks_dao.getTrucks()) {
                t.truckDisplay();
            }
        }
    }

    public ArrayList<Transport> get_transports(){
        return transport_dao.get_transports();
    }

    public void display_drivers(){
        if(drivers_dao.getDrivers().size() == 0){
            System.out.println("\nThere are no drivers in the system\n");
        }
        else {
            System.out.println("======================================= Drivers in the system =======================================");
            for (Truck_Driver driver : drivers_dao.getDrivers()) {
                driver.driverDisplay();
            }
        }
    }

    public void display_site_supply(){
        if(site_supply_dao.get_site_supply_documents().size() == 0){
            System.out.println("\nThere are no site supplies documents in the system\n");
        }
        else {
            for (Site_Supply siteSupply : site_supply_dao.get_site_supply_documents()) {
                siteSupply.sDisplay();
            }
        }
    }

    public boolean check_if_driver_Id_exist(int driver_ID){
        return drivers_dao.check_if_driver_exists(driver_ID);
    }

    public boolean check_if_license_id_exist(int license_ID){
        return license_dao.check_if_license_exist(license_ID);
    }

    public boolean check_if_transport_id_exist(int transport_ID){
        return transport_dao.check_if_Transport_exist(transport_ID);
    }

    public void display_trucks_by_cold_level(String cold_lvl){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        System.out.println("======================================= Trucks with cold level '" + cool_level + "' in the system =======================================");
        for (Truck t : trucks_dao.getTrucks()) {
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
        Transport transport = transport_dao.getTransport(transport_id);
        transport.setStarted(true);
    }

    public boolean at_least_one_unsent_transport(){
        for (Transport transport: transport_dao.get_transports()){
            if (!transport.Started()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Truck> get_trucks(){
        return trucks_dao.getTrucks();
    }

}
