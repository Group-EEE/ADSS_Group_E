package BussinessLayer.TransportationModule.objects;

import java.util.ArrayList;
import java.util.Objects;


public class Truck {
    private String registration_plate;
    private String model;
    private double net_weight;
    private double max_weight;
    private cold_level cold_level;
    private Truck_Driver current_driver = null;
    private double current_weight;
    private Navigator navigator = null;
    private boolean occupied = false;

    public Truck(String registration, String truck_model, double truck_net_weight, double truck_max_weight, cold_level level, double current_weight){
        this.registration_plate = registration;
        this.model = truck_model;
        this.net_weight = truck_net_weight;
        this.max_weight = truck_max_weight;
        this.cold_level = level;
        this.current_weight = current_weight;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setNavigator(ArrayList<Site> destinations) {
        this.navigator = new Navigator(destinations);
        navigator.create_route();
    }

    public Truck_Driver getCurrent_driver() {
        return current_driver;
    }

    public Site get_current_location(){
        return navigator.getCurrent_location();
    }
    public Site get_next_site(){
        if (navigator.drive_to_next() != null) {
            System.out.println("GPS: You have arrived to " + navigator.getCurrent_location().getSite_name());
        }
        return navigator.getCurrent_location();
    }

    public ArrayList<Site> get_route(){
        return navigator.getRoute();
    }
    public Navigator getNavigator() {
        return navigator;
    }

    public void set_ready_navigator(Navigator navigator){
        this.navigator = navigator;
    }

    public double getCurrent_weight() {
        return current_weight;
    }

    public void setCurrent_weight(double current_weight) {
        this.current_weight = current_weight;
    }

    public void addWeight(double weight){
        this.current_weight += weight;
    }

    public void setCurrent_driver(Truck_Driver current_driver) {
        this.current_driver = current_driver;
    }

    public void delete_driver(){
        if(current_driver == null){
            System.out.println("There's no current driver.");
        }
        else {
            current_driver = null;
        }
    }

    public cold_level getCold_level() {
        return cold_level;
    }

    public void setCold_level(cold_level cold_level) {
        this.cold_level = cold_level;
    }

    public double getMax_weight() {
        return max_weight;
    }

    public double getNet_weight() {
        return net_weight;
    }

    public String getModel() {
        return model;
    }

    public String getRegistration_plate() {
        return registration_plate;
    }

    public void setMax_weight(double max_weight) {
        this.max_weight = max_weight;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setNet_weight(double net_weight) {
        this.net_weight = net_weight;
    }

    public void setRegistration_plate(String registration_plate) {
        this.registration_plate = registration_plate;
    }

    public boolean equals(Truck truck) {
        return (registration_plate == truck.getRegistration_plate());
    }

    public boolean equals(String registration_plate) {
        return (Objects.equals(this.registration_plate, registration_plate));
    }

    public boolean Occupied() {
        return occupied;
    }
//display

    public void truckDisplay(){
        System.out.println("Truck Registration plate number - " + registration_plate);
        System.out.println("\t model: " + model);
        System.out.println("\t Net Weight: " + net_weight);
        System.out.println("\t Max Weight: " + max_weight);
        System.out.println("\t Cold Level: " + cold_level.name());
        if(current_driver != null) {
            System.out.println("\t Truck Driver name: " + current_driver.getFullName());
        }
        System.out.println("\t Current Weight: " + current_weight);
        // with no navigator and occupied
        System.out.println();
    }

    public String details(){
        return "Truck Registration plate number - " + registration_plate + "\n" + "\t model: " + model + "\n" + "\t Net Weight: " + net_weight + "\n" + "\t Max Weight: " + max_weight + "\n" + "\t Cold Level: " + cold_level.name() + "\n" + "\t Current Weight: " + current_weight;
    }

}
