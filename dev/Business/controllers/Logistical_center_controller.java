package Business.controllers;

import Business.objects.*;

import java.util.ArrayList;
import java.util.Map;

// singleton
public class Logistical_center_controller {
    // singleton
    private static Logistical_center_controller instance;
    private Transport_System transport_system;
    private Logistical_Center logistical_center;
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

    public void add_transport(int transport_id, String truck_number, String driver_name, cold_level cold_level){
        Transport transport = new Transport(transport_id, "TBD", "TBD", truck_number, driver_name, this.logistical_center.getSite_name(), cold_level);
        logistical_center.add_transport(transport);
    }

    public boolean truck_assigning(int driver_id, String truck_registration_plate){
        Truck_Driver driver = null;
        Truck truck = null;
        // checking if the given parameters are valid, and getting the diver and the truck if they exist.
        for (int i = 0; i < logistical_center.getTrucks().size(); i++) {
            if (logistical_center.getTrucks().get(i).equals(truck_registration_plate)){
                truck = logistical_center.getTrucks().get(i);
                break;
            }
        }
        if (truck == null){
            return false;
        }

        for (int i = 0; i < logistical_center.getDrivers().size(); i++) {
            if (logistical_center.getDrivers().get(i).equals(driver_id)){
                driver = logistical_center.getDrivers().get(i);
                break;
            }
        }
        if (driver == null){
            return false;
        }

        if (driver.getLicense().getWeight() < truck.getMax_weight() || driver.getLicense().getCold_level().getValue() > truck.getCold_level().getValue()){
            return false;
        } else if (truck.Occupied()) {
            return false;
        } else if (driver.getCurrent_truck() != null) {
            return false;
        }
        truck.setCurrent_driver(driver);
        truck.setOccupied(true);
        driver.setCurrent_truck(truck);
        return true;
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

    public void insert_weight_to_transport(int transport_id, double weight){
        Transport transport = logistical_center.get_transport_by_id(transport_id);
        transport.insertToWeights(weight);
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
}
