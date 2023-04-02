import java.util.ArrayList;
import java.util.Objects;

enum cold_level{
    Freeze (1),
    Cold(2),
    Dry(3);
    private int value;
    cold_level(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}

public class Truck {
    private String registration_plate;
    private String moodle;
    private double net_weight;
    private double max_weight;
    private cold_level cold_level;
    private Truck_Driver current_driver = null;
    private double current_weight;
    private Navigator navigator = null;
    private boolean occupied = false;

    public Truck(String registration, String truck_moodle, double truck_net_weight, double truck_max_weight, cold_level level, double current_weight){
        this.registration_plate = registration;
        this.moodle = truck_moodle;
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
    }

    public Site get_current_location(){
        return navigator.getCurrent_location();
    }

    public ArrayList<Site> get_route(){
        return navigator.getRoute();
    }
    public Navigator getNavigator() {
        return navigator;
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

    public String getMoodle() {
        return moodle;
    }

    public String getRegistration_plate() {
        return registration_plate;
    }

    public void setMax_weight(double max_weight) {
        this.max_weight = max_weight;
    }

    public void setMoodle(String moodle) {
        this.moodle = moodle;
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

    public Truck_Driver getCurrent_driver() {
        return current_driver;
    }
}
