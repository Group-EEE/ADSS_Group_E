package Business.controllers;

import Business.objects.*;

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

    public void add_site_document_to_driver(Truck_Driver driver, int site_supplier_ID, Store store, String address) {
        Site_Supply site_supply = new Site_Supply(site_supplier_ID, store, address);
        driver.Add_site_document(site_supply);
    }

    public void insert_item_to_siteSupply(int site_supplier_ID, Truck_Driver truck_driver, String item_name, int item_amount){
        Site_Supply site_supply = null;
        for(Site_Supply s : truck_driver.getSites_documents()){
            if(s.getId() == site_supplier_ID){
                site_supply = s;
                break;
            }
        }
        site_supply.insert_item(item_name, item_amount);
    }

    public void insert_weight_to_siteSupply(int site_supplier_ID, Truck_Driver truck_driver, double  weight){
        Site_Supply site_supply = null;
        for(Site_Supply s : truck_driver.getSites_documents()){
            if(s.getId() == site_supplier_ID){
                site_supply = s;
                break;
            }
        }
        site_supply.setProducts_total_weight(weight);
    }

    /**
     * @param chosen_transport the transport that need to be reset because she was aborted
     *                         the function reset the transport to not started, the driver documents to 0 documents, and the  truck weight to her net weight.
     */
    public void reset_transport(Transport chosen_transport){
        chosen_transport.setStarted(false);
        Truck_Driver  driver = getDriverByTruckNumber(chosen_transport.getTruck_number());
        ArrayList<Site_Supply> empty_array = new ArrayList<>();
        driver.setSites_documents(empty_array);
        Truck truck = driver.getCurrent_truck();
        truck.setCurrent_weight(truck.getNet_weight());
    }

    /**
     * @param registration_plate registration plate of the truck
     * @return the truck that has the registration plate
     */
    public Truck get_truck_by_registration_plate(String registration_plate){
        Truck truck = null;
        for(Truck t : logistical_center_controller.getLogistical_center().getTrucks()){
            if(t.getRegistration_plate().equals(registration_plate)){
                truck = t;
                break;
            }
        }
        return truck;
    }

    /**
     * @param transport_ID transport ID
     *                     the function set the navigator for the transport
     */
    public void set_navigator_for_transport(int transport_ID){
        Transport transport = logistical_center_controller.getLogistical_center().get_transport_by_id(transport_ID);
        Truck truck = get_truck_by_registration_plate(transport.getTruck_number());
        truck.setNavigator(transport.getDestinations());
    }

    /**
     * @param truck_number registration_plate of the truck
     * @return the driver of the truck
     */
    public Truck_Driver getDriverByTruckNumber(String truck_number){
        Truck truck = null;
        for(Truck t : logistical_center_controller.getLogistical_center().getTrucks()){
            if(t.getRegistration_plate().equals(truck_number)){
                truck = t;
            }
        }
        return truck.getCurrent_driver();
    }

    /**
     * @param transport_ID int represent the transport ID
     * @param Date String represent the date
     * @param Time String represent the current time
     *             the function set the date and time for the transport when it starts.
     */
    public void set_time_and_date_for_transport(int transport_ID,String Date,String Time){
        Transport transport = logistical_center_controller.getLogistical_center().get_transport_by_id(transport_ID);
        transport.setDate(Date);
        transport.setDeparture_time(Time);
    }

    public boolean is_siteSupply_id_exist_in_current_transport(Transport transport, int siteSupply_ID){
        Truck_Driver driver = get_truck_by_registration_plate(transport.getTruck_number()).getCurrent_driver();
        for(Site_Supply s : driver.getSites_documents()){
            if(s.getId() == siteSupply_ID){
                return true;
            }
        }
        return false;
    }

    public boolean is_siteSupply_id_exist_in_system(int siteSupply_ID){
        for(ArrayList<Site_Supply> s : logistical_center_controller.getLogistical_center().getDelivered_supplies_documents().values()){
            for(Site_Supply ss : s){
                if(ss.getId() == siteSupply_ID){
                    return true;
                }
            }
        }
        return false;
    }
}
