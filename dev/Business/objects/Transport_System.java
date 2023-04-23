package Business.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transport_System {

    private Logistical_Center logistical_center;

    public Transport_System(){
    }

    // assigning a truck for a truck_driver by a given driver ID and a truck's registration plate.
    public void hire_driver(){
        Scanner scanner = new Scanner(System.in);

        Truck_Driver driver = new Truck_Driver(ID, Driver_name, License_ID, level, weight);
        logistical_center.getDrivers().add(driver);
    }
    public void add_driver(Truck_Driver driver){
        logistical_center.getDrivers().add(driver);
    }
    public void add_truck(Truck truck){
        logistical_center.getTrucks().add(truck);
    }
    public void setTrucks(ArrayList<Truck> trucks) {
        this.logistical_center.setTrucks(trucks);
    }



    // return a truck driver by truck registration number.
    public Truck_Driver getDriverByTruckNumber(String truck_number){
        Truck truck = null;
        for(Truck t : logistical_center.getTrucks()){
            if(t.getRegistration_plate().equals(truck_number)){
                truck = t;
            }
        }
        return truck.getCurrent_driver();
    }



    // return some truck by temperature level.





    // =============================== MAIN FUNCTION =============================== //
    public void start_transport(){
        int choice = 0;
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
        String input = null;
        while (true) {
            System.out.println("Hey Boss! what would you like to do?");
            System.out.println("0 - Hire a new driver");
            System.out.println("1 - See all the trucks with a cold level of your choice: \n\t 1- Freeze \n\t 2- Cold \n \t 3- Dry");
            System.out.println("2 - create a new transport");
            System.out.println("3 - send transports");
            System.out.println("4 - Add new truck to the system");
            System.out.println("5 - Display all drivers in the system");
            System.out.println("6 - Display all trucks in the system");
            System.out.println("7 - Display all transport documents in the system");
            System.out.println("8 - Display all site supplies documents in the system");
            System.out.println("9 - quit");
            while(!isValid){
                try {
                    input = scanner.nextLine();
                    choice = Integer.parseInt(input);

                    // Check if the input is a 5 digit integer
                    if (input.length() == 1 && choice >= 0 && choice < 10) {
                        isValid = true;
                    } else {
                        System.out.println("Input must be an int between 0-9. ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid int between 0-9. ");
                }
            }
            isValid = false;
            switch (choice){
                // hire a new driver
                case 0:
                    hire_driver();
                    break;
                // have all the trucks by a cold level
                case 1:
                    cold_level cool_level = null;
                    boolean isValid2 = false;
                    while(!isValid2){
                        System.out.println("Please enter the required cold level of the truck (press 1, 2 or 3 only): ");
                        System.out.println("1 - Freeze");
                        System.out.println("2 - Cold");
                        System.out.println("3 -  Dry");
                        input = scanner.nextLine();
                        if(input.equals("1") || input.equals("2") || input.equals("3")){
                            isValid2 = true;
                        }
                        else{
                            System.out.print("Invalid input. ");
                        }
                    }
                    switch (input) {
                        case "1" -> cool_level = cold_level.Freeze;
                        case "2" -> cool_level = cold_level.Cold;
                        case "3" -> cool_level = cold_level.Dry;
                    }
                    for(Truck t : logistical_center.getTrucks()){
                        if(t.getCold_level().getValue() == cool_level.getValue()) {
                            System.out.println(t.getRegistration_plate());
                        }
                    }
                    break;

                // make a new transport
                case 2:
                    System.out.println("Hey Boss!");
                    create_transport_document();
                    break;

                // quit the menu
                case 3:
                    // starting the transports the manager chose.
                    System.out.println("Starting the transports.");






                case 4:
                    addNewTruck();
                    break;
                case 5:
                    display_drivers();
                    break;
                case 6:
                    display_trucks();
                    break;
                case 7:
                    display_transport_doc();
                    break;
                case 8:
                    display_site_supply();
                    break;
                case 9:
                    logistical_center = null;
                    return;
            }
        }
    }

    // check if string contain only numbers.
    public boolean containsOnlyNumbers(String str) {
        try{
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
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

    // create site supply document for the truck driver.
    public void create_site_supply(Transport transport, String supplier_address){

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

    // create a transport document in the system.
    public Transport create_transport_document(){
        Scanner scanner = new Scanner(System.in);

        // ======================== Add Transport Document ======================== //
        logistical_center.getTransport_Log().put(transport_Id, transport_doc);
        return transport_doc;
    }



    // adding new truck to list by parameters.
    public void addNewTruck(){
        Scanner scanner = new Scanner(System.in);
        String input = null;
        boolean isValid = false;
        // ======================== Registration Number ======================== //
        String registration_number = null;
        while(!isValid){
            System.out.println("Please enter the registration number of the truck (8 digits, only with the digits 0-9): ");
            input = scanner.nextLine();
            boolean number_exist = false;
            if(!containsOnlyNumbers(input)){
                System.out.println("Invalid input. ");
                continue;
            }
            else if (input.length() != 8) {
                System.out.println("Input must be a 8 digit integer. ");
                continue;
            }
            // checking if the truck already exist
            for(Truck truck : logistical_center.getTrucks()){
                if(truck.getRegistration_plate().equals(input)){
                    System.out.println("The Truck is already exist in the system! ");
                    number_exist = true;
                    break;
                }
            }
            if (!number_exist){
                registration_number = input;
                isValid = true;
            }
        }
        // ======================== Truck Moodle ======================== //
        System.out.println("Please enter the truck moodle: ");
        String truck_moodle = scanner.nextLine();
        // ======================== Truck Net Weight ======================== //
        double truck_net_weight = 0.0;
        boolean valid_net_weight = false;
        while (!valid_net_weight) {
            System.out.println("Please enter the net weight of the truck: ");
            input = scanner.nextLine();
            try {
                truck_net_weight = Double.parseDouble(input);
                if (truck_net_weight > 0){
                    valid_net_weight = true;
                }
                else {
                    System.out.println("The weight should be positive...");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. ");
            }
        }
        // ======================== Truck max Weight ======================== //
        double truck_max_weight = 0.0;
        boolean valid_max_weight = false;
        while (!valid_max_weight) {
            System.out.println("Please enter the maximum weight of the truck: ");
            input = scanner.nextLine();
            try {
                truck_max_weight = Double.parseDouble(input);
                if (truck_max_weight <= truck_net_weight){
                    System.out.println("Max weight must be above the net weight.");
                }
                else {
                    valid_max_weight = true;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. ");
            }
        }
        // ======================== Truck Cold Level ======================== //
        cold_level cool_level = null;
        isValid = false;
        while(!isValid){
            System.out.println("Please enter the cold level of the truck (press 1, 2 or 3 only): ");
            System.out.println("\t 1 - Freeze");
            System.out.println("\t 2 - Cold");
            System.out.println("\t 3 -  Dry");
            input = scanner.nextLine();
            if(input.equals("1") || input.equals("2") || input.equals("3")){
                isValid = true;
            }
            else{
                System.out.print("Invalid input. ");
            }
        }
        switch (input) {
            case "1" -> cool_level = cold_level.Freeze;
            case "2" -> cool_level = cold_level.Cold;
            case "3" -> cool_level = cold_level.Dry;
        }
        // ======================== Create And Adding The New Truck  ======================== //
        Truck truck = new Truck(registration_number, truck_moodle, truck_net_weight, truck_max_weight, cool_level ,truck_net_weight);
        logistical_center.getTrucks().add(truck);
    }


    /// ========== display for test ======= ///

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
