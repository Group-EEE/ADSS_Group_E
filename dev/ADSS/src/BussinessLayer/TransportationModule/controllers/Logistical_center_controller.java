package BussinessLayer.TransportationModule.controllers;

import BussinessLayer.TransportationModule.objects.*;
import DataAccessLayer.HRMoudle.StoresDAO;
import DataAccessLayer.Transport.*;
import BussinessLayer.HRModule.Objects.Store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// singleton
public class Logistical_center_controller {
    // singleton


    private static Logistical_center_controller instance;
    private Logistical_Center logistical_center;
    public static Logistical_center_controller getInstance(){
        if (instance == null){
            instance = new Logistical_center_controller();
        }
        return instance;
    }
    // singleton


    public void create_Logistical_Center(String address, String phone, String name, String site_contact_name){
        if (logistical_center == null){
            logistical_center = new Logistical_Center(address, phone, name, site_contact_name);
        }
    }

    public Logistical_Center getLogistical_center() {
        return logistical_center;
    }

    public void add_driver(int driver_ID, String driver_name, int license_id, cold_level level, double truck_weight){
        Truck_Driver new_driver = new Truck_Driver(driver_ID, driver_name, license_id, level, truck_weight);
        Drivers_dao.get_instance().Insert(new_driver);
        Drivers_dao.get_instance().get_instance();
    }

    public Transport get_transport_by_id(int transport_id){
        return Transport_dao.getInstance().getTransport(transport_id);

    }

    public boolean check_if_site_supply_exist(int id){
        return Site_Supply_dao.getInstance().check_if_site_supply_exists(id);

    }

    public void insert_site_supply_to_database(Site_Supply site_supply){
        Site_Supply_dao.getInstance().Insert(site_supply);
    }

    public void add_transport(int transport_id, String truck_number, String driver_name, String cold_lvl, String planned_date, int driver_id){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        Transport transport = new Transport(transport_id, "TBD", "TBD", truck_number, driver_name, this.logistical_center.getSite_name(), cool_level, planned_date, driver_id );
        Transport_dao.getInstance().Insert(transport);
    }

    ///// need to notify HR when the driver is needed to be in the weekly schedule
    public boolean truck_assigning(String new_truck_registration_plate, String planned_date){
        Truck_Driver driver;
        Truck truck = getTruckByNumber(new_truck_registration_plate);
        // checking if the given parameters are valid, and getting the diver and the truck if they exist.

        for (Truck_Driver truck_driver  : Drivers_dao.get_instance().getDrivers()) {
            if (truck_driver.getLicense().getWeight() >= truck.getMax_weight() && truck_driver.getLicense().getCold_level().getValue() <= truck.getCold_level().getValue() && !Transport_dao.getInstance().check_if_driver_taken_that_date(planned_date, truck_driver.getID())){
                driver = truck_driver;
                truck.setCurrent_driver(truck_driver);
                truck.setOccupied(true);
                driver.setCurrent_truck(truck);
                return true;
            }
        }
        return false;
    }

    // need to implent, I need to pull out the drivers that work today and check if they can drive this truck.
    public boolean truck_assigning_drivers_in_shift(String new_truck_registration_plate, String planned_date){
        return false;
    }


    public String get_truck_number_by_cold_level(String cold_lvl, String planned_date){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        Truck truck = null;
        for(Truck t : Trucks_dao.get_instance().getTrucks()){
            if(t.getCold_level().getValue() <= cool_level.getValue() && Transport_dao.getInstance().check_if_truck_taken_that_date(planned_date, t.getRegistration_plate())) {
                if(t.getCold_level().getValue() == cool_level.getValue()){
                    truck = t;
                    break;
                }
                else if (truck == null) {
                    truck = t;
                } else if (cool_level.getValue() - t.getCold_level().getValue() < cool_level.getValue() - truck.getCold_level().getValue()) {
                    truck = t;
                }
            }
        }
        return truck.getRegistration_plate();
    }

    /**
     * @param cold_lvl cold level as string
     * @return true if we have a truck that not occupied and suitable with the cold level constraint, otherwise false
     */
    public boolean check_if_truck_exist_by_cold_level(String cold_lvl, String planned_date){
        cold_level cool_level = cold_level.fromString(cold_lvl);
        for(Truck t : Trucks_dao.get_instance().getTrucks()){
            if(!t.Occupied() && t.getCold_level().getValue() <= cool_level.getValue() && !Transport_dao.getInstance().check_if_truck_taken_that_date(planned_date, t.getRegistration_plate())){
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
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        transport.insertToWeights(truck.getNet_weight());
    }

    public ArrayList<Truck_Driver> getDrivers(){
        return Drivers_dao.get_instance().getDrivers();
    }

    public boolean check_if_site_exist_in_transport(String site_name, int transport_id){
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        for (Site site: transport.getDestinations()){
            if (site.getSite_name().equals(site_name)){
                return true;
            }
        }
        return false;
    }

    public void insert_store_to_transport(int transport_id, String name){
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        transport.insertToDestinations(StoresDAO.getInstance().getStore(name));
    }

    public void insert_supplier_to_transport(int transport_id, String supplier_name){
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        transport.insertToDestinations(Suppliers_dao.getInstance().get_supplier_by_name(supplier_name));
    }

    public Map<Integer, Transport> getTransport_Log(){
        return Transport_dao.getInstance().get_transports_map();
    }

    public Truck getTruckByNumber(String truck_number){
        return Trucks_dao.get_instance().get_truck_by_registration_plate(truck_number);
    }

    public void add_truck(String registration, String truck_model, double truck_net_weight, double truck_max_weight, cold_level level, double current_weight){
        Truck truck = new Truck(registration, truck_model, truck_net_weight, truck_max_weight, level, current_weight);
        Trucks_dao.get_instance().Insert(truck);
    }

    public boolean is_truck_exist(String truck_ID){
        return Trucks_dao.get_instance().check_if_truck_exists(truck_ID);
    }

    public void display_transport_doc(){
        if(Transport_dao.getInstance().get_transports().size() == 0){
            System.out.println("\nThere are no transports documents in the system\n");
        }
        else {
            for (Map.Entry<Integer, Transport> entry : Transport_dao.getInstance().get_transports_map().entrySet()) {
                int id = entry.getKey();
                Transport transport = entry.getValue();
                System.out.println("=========== Transport - " + id + " - information ===========");
                transport.transportDisplay();
            }
        }
    }

    public void display_trucks(){
        if(Trucks_dao.get_instance().getTrucks().size() == 0){
            System.out.println("\nThere are no trucks in the system\n");
        }
        else {
            System.out.println("======================================= Trucks in the system =======================================");
            for (Truck t : Trucks_dao.get_instance().getTrucks()) {
                t.truckDisplay();
            }
        }
    }

    public ArrayList<Transport> get_transports(){
        return Transport_dao.getInstance().get_transports();
    }

    public void display_drivers(){
        if(Drivers_dao.get_instance().getDrivers().size() == 0){
            System.out.println("\nThere are no drivers in the system\n");
        }
        else {
            System.out.println("======================================= Drivers in the system =======================================");
            for (Truck_Driver driver : Drivers_dao.get_instance().getDrivers()) {
                driver.driverDisplay();
            }
        }
    }

    public void display_site_supply(){
        if(Site_Supply_dao.getInstance().get_site_supply_documents().size() == 0){
            System.out.println("\nThere are no site supplies documents in the system\n");
        }
        else {
            for (Site_Supply siteSupply : Site_Supply_dao.getInstance().get_site_supply_documents()) {
                siteSupply.sDisplay();
            }
        }
    }

    public boolean check_if_driver_Id_exist(int driver_ID){
        return Drivers_dao.get_instance().check_if_driver_exists(driver_ID);
    }

    public boolean check_if_license_id_exist(int license_ID){
        return License_dao.getInstance().check_if_license_exist(license_ID);
    }

    public boolean check_if_transport_id_exist(int transport_ID){
        return Transport_dao.getInstance().check_if_Transport_exist(transport_ID);
    }

    public void display_trucks_by_cold_level(String cold_lvl){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        System.out.println("======================================= Trucks with cold level '" + cool_level + "' in the system =======================================");
        for (Truck t : Trucks_dao.get_instance().getTrucks()) {
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
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        transport.setStarted(true);
    }

    public boolean at_least_one_unsent_transport(){
        for (Transport transport: Transport_dao.getInstance().get_transports()){
            if (!transport.Started()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Truck> get_trucks(){
        return Trucks_dao.get_instance().getTrucks();
    }

    public boolean can_send_the_transport(int transport_ID, String current_date){
        if (!check_if_transport_id_exist(transport_ID)){
            System.out.println("We don't have the transport" + transport_ID + " in the system.");
            return false;
        }

        Transport transport = get_transport_by_id(transport_ID);

        if (!transport.getPlanned_date().equals(current_date)){
            System.out.println("It's not the date of the transport.");
            return false;
        }
        if (transport.Started()){
            System.out.println("this transport is alredy finished.");
            return false;
        }
        return true;
    }

    public void add_supplier(String address, String phone, String supplier_name, String contact_name){
        Supplier supplier = new Supplier(address, phone, supplier_name, contact_name);
        Suppliers_dao.getInstance().Insert(supplier);
    }

}
