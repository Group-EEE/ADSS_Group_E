import java.util.*;

public class Transport {
    private int transport_ID;
    private String date;
    private String departure_time;
    private String truck_number;
    private String driver_n;
    private Logistical_Center origin;
    private cold_level required_level;
    private ArrayList<Site> destinations;
    private Map<String, Integer> products;
    private ArrayList<Double> weighing;
    private boolean started;

    public Transport(int transport_ID, String date, String departure_time, String truck_number, String driver_name, Logistical_Center origin, cold_level cold_level){
        this.transport_ID = transport_ID;
        this.date = date;
        this.departure_time = departure_time;
        this.truck_number = truck_number;
        this.driver_n = driver_name;
        this.origin = origin;
        this.required_level = cold_level;
        this.destinations = new ArrayList<>();
        this.products = new HashMap<>();
        this.weighing = new ArrayList<>();
        this.started = false;
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

    public String getDriver_n() {
        return driver_n;
    }

    public void setDriver_n(String driver_n) {
        this.driver_n = driver_n;
    }

    public Site getOrigin() {
        return origin;
    }

    public void setOrigin(Logistical_Center origin) {
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

    public void deleteDestination(String site){
        int i = 0;
        for(Site s : destinations){
            if(site.equals(s.getSite_n())){
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

    public Store getStoreByAddress(String address){
        Store store = null;
        for(Site site :  destinations){
            if(site.getAddress().equals(address) && site.is_store()){
                store = (Store)site;
            }
        }
        return store;
    }

    public void delete_last_Weight(){
        weighing.remove(weighing.size()-1);
    }

    // display

    public void transportDisplay(){
        System.out.println("\t Transport ID: " + transport_ID);
        System.out.println("\t Date: " + date);
        System.out.println("\t Departure Time: " + departure_time);
        System.out.println("\t Truck Number: " +truck_number);
        System.out.println("\t Driver Name: " + driver_n);
        System.out.println("\t Origin Details: ");
        origin.siteDisplay();
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
            System.out.println("\t Weight:0.0");
        }
        System.out.println();
    }
}