package Business.controllers;

import Business.objects.*;

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

    public void add_site_document_to_driver(int driver_id, int site_supplier_ID, Store store, String address) {
        Truck_Driver driver = logistical_center_controller.getLogistical_center().get_driver_by_id(driver_id);
        Site_Supply site_supply = new Site_Supply(site_supplier_ID, store, address);
        driver.Add_site_document(site_supply);
    }

    public Truck_Driver getDriverByTruckNumber(String truck_number){
        Truck truck = null;
        for(Truck t : logistical_center_controller.getLogistical_center().getTrucks()){
            if(t.getRegistration_plate().equals(truck_number)){
                truck = t;
            }
        }
        return truck.getCurrent_driver();
    }

}
