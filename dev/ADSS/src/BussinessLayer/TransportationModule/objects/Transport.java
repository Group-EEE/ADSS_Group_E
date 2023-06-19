package BussinessLayer.TransportationModule.objects;

import BussinessLayer.HRModule.Objects.Store;

import java.util.*;

public class Transport {
    private int transport_ID;
    private String date;
    private String departure_time;
    private String truck_number;
    private String driver_name;
    private String origin;
    private cold_level required_level;
    private ArrayList<Site> destinations;
    private Map<String, Integer> products;
    private ArrayList<Double> weighing;
    private boolean started;
    private String Planned_date;
    private int driver_ID;

    private String Estimated_end_time;

    public Transport(int transport_ID, String date, String departure_time, String truck_number, String driver_name, String origin, cold_level cold_level, String planned_date, int driver_ID){
        this.transport_ID = transport_ID;
        this.date = date;
        this.departure_time = departure_time;
        this.truck_number = truck_number;
        this.driver_name = driver_name;
        this.origin = origin;
        this.required_level = cold_level;
        this.destinations = new ArrayList<>();
        this.products = new HashMap<>();
        this.weighing = new ArrayList<>();
        this.started = false;
        this.Planned_date = planned_date;
        this.driver_ID = driver_ID;
        this.Estimated_end_time = null;
    }


    public String getPlanned_date() {
        return Planned_date;
    }

    public void setPlanned_date(String planned_date) {
        Planned_date = planned_date;
    }

    public int getDriver_ID() {
        return driver_ID;
    }

    public void setDriver_ID(int driver_ID) {
        this.driver_ID = driver_ID;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean Started(){
        return started;
    }

    public cold_level getRequired_level() {
        return required_level;
    }

    public void setRequired_level(cold_level required_level) {
        this.required_level = required_level;
    }

    public int getTransport_ID() {
        return transport_ID;
    }

    public void setWeighing(ArrayList<Double> weighing) {
        this.weighing = weighing;
    }

    public void setTransport_ID(int transport_ID) {
        this.transport_ID = transport_ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getTruck_number() {
        return truck_number;
    }

    public void setTruck_number(String truck_number) {
        this.truck_number = truck_number;
    }

    public String getDriver_name() {
        return driver_name;
    }


    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String  origin) {
        this.origin = origin;
    }

    public ArrayList<Site> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<Site> destinations) {
        this.destinations = destinations;
    }

    public int Number_of_suppliers(){
        int count = 0;
        for (int i = 0; i < destinations.size(); i++) {
            if (destinations.get(i).is_supplier()){
                count++;
            }
        }
        return count;
    }

    public int Number_of_stores(){
        int count = 0;
        for (int i = 0; i < destinations.size(); i++) {
            if (!destinations.get(i).is_supplier()){
                count++;
            }
        }
        return count;
    }

    /// improve this//////
    public void insertToDestinations(Site s){
        destinations.add(s);
    }
    public void insertToWeights(Double weight){
        weighing.add(weight);
    }
    public void insertToProducts(String product_name, int amount){
        if (products.containsKey(product_name)){
            products.put(product_name, products.get(product_name) + amount);
        }
        else {
            products.put(product_name, amount);
        }
    }

    public int getProduct(String product){
        return products.get(product);
    }

    public void deleteDestination(String site){
        int i = 0;
        for(Site s : destinations){
            if(site.equals(s.getSite_name())){
                destinations.remove(i);
                break;
            }
            i++;
        }
    }

    public Double get_last_weight(){
        return weighing.get(weighing.size()-1);
    }

    public void deleteProducts(String product, int amount){
        if (products.containsKey(product)){
            if (products.get(product) - amount == 0){
                products.remove(product);
            }
            else {
                products.put(product, products.get(product) - amount);
            }
        }
    }

    public Store getStoreByName(String name){
        Store store = null;
        for(Site site :  destinations){
            if(site.getSite_name().equals(name) && site.is_store()){
                store = (Store) site;
            }
        }
        return store;
    }

    public ArrayList<Store> getStores(){
        ArrayList<Store> stores = new ArrayList<>();
        for(Site site : destinations){
            if(site.is_store()){
                stores.add((Store) site);
            }
        }
        return stores;
    }

    public void delete_last_Weight(){
        weighing.remove(weighing.size()-1);
    }

    public String details(){
        return "Transport ID: " + transport_ID +
                "\n Date: " + date +
                "\n Departure Time: " + departure_time +
                "\n Truck Number: " + truck_number +
                "\n Driver Name: " + driver_name +
                "\n Origin Details: " + origin +
                "\n Cold Level: " + required_level.name() +
                "\n Destinations: " + destinations.size() +
                "\n Products: " + products.size();
    }

    public void transportDisplay(){
        System.out.println("\t Transport ID: " + transport_ID);
        System.out.println("\t Date: " + date);
        System.out.println("\t Departure Time: " + departure_time);
        System.out.println("\t Truck Number: " + truck_number);
        System.out.println("\t Driver Name: " + driver_name);
        System.out.println("\t Origin Details: " + origin);
        System.out.println("\t Cold Level: " + required_level.name());
        System.out.println("\t Destinations: ");
        int i = 1;
        for(Site s : destinations){
            System.out.println("\t ================= Destination " + i + " =================");
            if(s.is_store()) {
                System.out.println("\t\t Site Type: Store");
            }
            if(s.is_supplier()) {
                System.out.println("\t\t Site Type: Supplier");
            }
            if(s.is_logistical_center()) {
                System.out.println("\t\t Site Type: Logistical Center");
            }
            s.siteDisplay();
            System.out.println();
            i++;
        }
        System.out.println("\t ================= Products =================");
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            System.out.println("\t\t" + entry.getKey() + " = " + entry.getValue());
        }
        System.out.println();
        System.out.println("\t ================= Last Weight =================");

        if(weighing.size() > 0) {
            System.out.println("\t Weight: " + weighing.get(weighing.size() - 1));
        }
        else{
            System.out.println("\t Weight: 0.0");
        }
        System.out.println();
    }

    public Map<String, Integer> getProducts(){
        return this.products;
    }

    public String getEstimated_end_time() {
        return Estimated_end_time;
    }

    public String get_suppliers_name(){
        String suppliers = "";
        for(Site s : destinations){
            if(s.is_supplier()){
                suppliers += s.getSite_name() + ", ";
            }
        }
        suppliers = suppliers.substring(0, suppliers.length() - 2);
        return suppliers;
    }

    public String get_stores_name(){
        String stores = "";
        for(Site s : destinations){
            if(s.is_store()){
                stores += s.getSite_name() + ", ";
            }
        }
        stores = stores.substring(0, stores.length() - 2);
        return stores;
    }

    public void setEstimated_end_time(String estimated_end_time) {
        Estimated_end_time = estimated_end_time;
    }
}