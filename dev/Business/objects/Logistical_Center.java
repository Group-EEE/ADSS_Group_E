package Business.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Logistical_Center extends Site{

    private ArrayList<Truck> trucks;

    private ArrayList<Truck_Driver> drivers;
    private Map<Integer, Transport> Transport_Log;
    private Map<Store, ArrayList<Site_Supply>> delivered_supplies_documents;

    public Logistical_Center(String address, String phone, String name, String site_contact_name) {
        super(address, phone, name, site_contact_name);
        trucks = new ArrayList<>();
        drivers = new ArrayList<>();
        Transport_Log = new HashMap<>();
        delivered_supplies_documents = new HashMap<>();
    }

    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(ArrayList<Truck> trucks) {
        this.trucks = trucks;
    }

    public ArrayList<Truck_Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<Truck_Driver> drivers) {
        this.drivers = drivers;
    }

    public Map<Integer, Transport> getTransport_Log() {
        return Transport_Log;
    }

    public void setTransport_Log(Map<Integer, Transport> transport_Log) {
        Transport_Log = transport_Log;
    }

    public Map<Store, ArrayList<Site_Supply>> getDelivered_supplies_documents() {
        return delivered_supplies_documents;
    }

    public Transport get_transport_by_id(int id){
        return Transport_Log.get(id);
    }

    public void add_transport(Transport transport){
        Transport_Log.put(transport.getTransport_ID(), transport);
    }

    public void setDelivered_supplies_documents(Map<Store, ArrayList<Site_Supply>> delivered_supplies_documents) {
        this.delivered_supplies_documents = delivered_supplies_documents;
    }

    public void add_driver(Truck_Driver driver){
        drivers.add(driver);
    }

    @Override
    public boolean is_supplier() {
        return false;
    }

    @Override
    public boolean is_logistical_center() {
        return true;
    }

    @Override
    public boolean is_store() {
        return false;
    }

    // display

    @Override
    public void siteDisplay() {
        super.siteDisplay();
    }


}
