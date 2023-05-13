package BussinessLayer.TransportationModule.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Logistical_Center extends Site{

    private ArrayList<Truck> trucks;

    private ArrayList<Truck_Driver> drivers;


    public Logistical_Center(String address, String phone, String name, String site_contact_name) {
        super(address, phone, name, site_contact_name);
        trucks = new ArrayList<>();
        drivers = new ArrayList<>();
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




    public Truck_Driver get_driver_by_id(int id){
        for (Truck_Driver driver : drivers) {
            if (driver.getEmployeeID() == id)
                return driver;
        }
        return null;
    }


    public void add_driver(Truck_Driver driver){
        drivers.add(driver);
    }

    public void add_truck(Truck truck){
        trucks.add(truck);
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
