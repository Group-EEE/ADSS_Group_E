import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transport_System {
    private ArrayList<Truck> trucks;
    private ArrayList<Truck_Driver> drivers;
    private Map<Integer, Transport> Transport_Log;
    private Map<Store, ArrayList<Site_Supply>> delivered_supplies_documents;

    private Logistical_Center logistical_center;
    Transport_System(){
        trucks = new ArrayList<Truck>();
        drivers = new ArrayList<Truck_Driver>();
        Transport_Log = new HashMap<>();
        delivered_supplies_documents = new HashMap<>();
        this.logistical_center = null;
    }

    // assigning a truck for a truck_driver by a given driver ID and a truck's registration plate.
    public void hire_driver(){
        Scanner scanner = new Scanner(System.in);
        int ID = 0;
        boolean isValid = false;
        // getting the driver's ID
        while (!isValid) {
            System.out.print("Enter an ID (a 9 digit integer) : \n");
            String input = scanner.nextLine();

            try {
                ID = Integer.parseInt(input);

                // Check if the input is a 9 digit integer
                if (input.length() == 9) {
                    isValid = true;
                } else {
                    System.out.println("Input must be a 9 digit integer.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 9 digit integer.\n");
            }
        }
        System.out.println("Please enter the driver's license details:\n");
        isValid = false;
        int License_ID = 0;
        // getting The driver's license ID
        while (!isValid) {
            System.out.print("Enter an ID (a 5 digit integer) : \n");
            String input = scanner.nextLine();

            try {
                License_ID = Integer.parseInt(input);

                // Check if the input is a 5 digit integer
                if (input.length() == 5) {

                    isValid = true;
                } else {
                    System.out.println("Input must be a 5 digit integer.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 5 digit integer.");
            }
        }
        isValid = false;
        // getting the driver's license truck restrictions
        cold_level level = null;
        double weight = 0;
        int level_ch = 0;
        while (!isValid) {
            System.out.print("Enter the cold level of the truck the driver can drive in (1- Freeze, 2- Cold, 3- Dry),\n and the the Max weight of the truck he can drive in: \n ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            try {
                // Check if the input contains two parts
                if (parts.length == 2) {
                    level_ch = Integer.parseInt(parts[0]);
                    weight = Double.parseDouble(parts[1]);

                    // Check if the input numbers are positive
                    if (level_ch > 3 || level_ch < 1){
                        System.out.println("only the number 1-3 is valid for cold level.");
                        continue;
                    }
                    else {
                        switch (level_ch){
                            case 1:
                                level = cold_level.Freeze;
                                break;
                            case 2:
                                level = cold_level.Cold;
                                break;
                            case 3:
                                level = cold_level.Dry;
                                break;
                        }
                    }
                    if (weight > 0) {
                        isValid = true;
                    } else {
                        System.out.println("Weight must be positive.");
                    }
                } else {
                    System.out.println("Input must contain two numbers - Int and then Double, separated by a space.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter two valid positive numbers, An integer and then double separated by a space.");
            }
        }
        // getting the driver's name
        isValid = false;
        String Driver_name = "";
        while (!isValid) {
            System.out.print("Enter the driver's full name:\n");
            String input = scanner.nextLine();

            // Check if the input only contains alphabetic letters
            boolean isAlphabetic = true;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    isAlphabetic = false;
                    break;
                }
            }

            if (isAlphabetic) {
                Driver_name = input;
                isValid = true;
            } else {
                System.out.println("Input must contain only alphabetic letters.");
            }
        }

        Truck_Driver driver = new Truck_Driver(ID, Driver_name, License_ID, level, weight);
        drivers.add(driver);
    }
    public void add_driver(Truck_Driver driver){
        drivers.add(driver);
    }
    public void add_truck(Truck truck){
        trucks.add(truck);
    }
    public void setDrivers(ArrayList<Truck_Driver> drivers) {
        this.drivers = drivers;
    }
    public void setTrucks(ArrayList<Truck> trucks) {
        this.trucks = trucks;
    }
    public Truck_Driver getDriverByTruckNumber(String truck_number){
        Truck truck = null;
        for(Truck t : trucks){
            if(t.getRegistration_plate().equals(truck_number)){
                truck = t;
            }
        }
        return truck.getCurrent_driver();
    }
    public boolean change_transport(Transport transport_doc, Truck truck, Truck_Driver driver){
        int choice = 0;
        while (choice != 5) {
            System.out.println("Hey boss, You need to update the current Transport. please choose one of this following options:");
            System.out.println("1: I want to cancel shipping to a certain store.");
            System.out.println("2: I want to change the truck to complete the transport.");
            System.out.println("3: I want to get the goods from the current supplier later / skip this supplier goods for today.");
            System.out.println("4: I want to cancel all the shipping, I tried all, currently we don't have other option!");
            Scanner scanner = new Scanner(System.in);

            boolean isValid = false;
            while (!isValid) {
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);

                    // Check if the input is a 1,2,3 or 4.
                    if (choice < 5 && choice > 0) {
                        isValid = true;
                    } else {
                        System.out.println("Boss, there's only 4 options...");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
            switch (choice) {
                // deleting a store  from the transport
                // TO DO : also delete the destination from the route.
                case 1:

                    int stores = 0;
                    for (Site site: truck.get_route()){
                        if (!site.is_supplier()){
                            stores++;
                        }
                    }
                    if (stores <= 1){
                        System.out.println("Sorry Boss, this is the only store left for today...");
                        break;
                    }
                    // asking for the store until he gives a valid store name from are current destinations.
                    System.out.println("Please enter the Store you want to cancel the shipment:");
                    String site_to_remove = null;
                    boolean exist = false;
                    while (!exist){
                        String store = scanner.nextLine();
                        for (Site site: transport_doc.getDestinations()){
                            if (site.getSite_n() == store){
                                if (site.is_supplier()){
                                    System.out.println("You've entered a supplier Boss... you need to put a store!");
                                }
                                site_to_remove = store;
                                exist = true;
                            }
                        }
                        System.out.println("This store is not on the list boss...");
                    }
                    // deleting the documents with the relevant documents according to the deleted destination.
                    for (Site_Supply site_supply: driver.getSites_documents()) {
                        if (site_supply.getStore().getSite_n() == site_to_remove) {
                            for (String product : site_supply.getItems().keySet()) {
                                transport_doc.deleteProducts(product, site_supply.getItems().get(product));
                                System.out.println("We dropped " + site_supply.getItems().get(product) + "of the product - " + product + " how much weight it was?");
                                boolean is_double = false;
                                String input = "";
                                double drop_weight = 0.0;
                                while (!is_double) {
                                    input = scanner.nextLine();
                                    try {
                                        drop_weight = Double.parseDouble(input);
                                        if (drop_weight > 0) {
                                            truck.addWeight(-1 * drop_weight);
                                            is_double = true;
                                        } else {
                                            System.out.println("Please enter a positive number.");
                                        }

                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a valid weight represented by double.");
                                    }
                                }
                            }
                        }
                    }
                    driver.delete_site_document_by_destination(site_to_remove);
                    transport_doc.deleteDestination(site_to_remove);
                    truck.getNavigator().delete_site(site_to_remove);
                    if (!check_weight(truck)){
                        if (!change_transport(transport_doc, truck, driver)) {
                            return false;
                        }
                    }
                    return true;

                // changing the truck to a suitable one.
                case 2:
                    Truck new_truck = null;
                    for (Truck tr: trucks){
                        // checks that the new truck have suitable max weight, and also temperature.
                        if (tr.getCold_level().getValue() >= transport_doc.getRequired_level().getValue()
                                && tr.getMax_weight() > truck.getCurrent_weight()){
                            new_truck = tr;
                        }
                    }
                    if (new_truck == null){
                        System.out.println("Sorry Boss, we don't have a suitable truck...");
                    }
                    else {
                        //now we need to check if we need to assign a driver to the new truck.
                        for (Truck_Driver truck_driver: drivers){
                            if (truck_assigning(driver.getID(), new_truck.getRegistration_plate())){
                                // updating the current driver's truck and the opposite.
                                driver.setCurrent_truck(null);
                                truck.setCurrent_driver(null);
                                // updating the details in the transport document
                                transport_doc.setDriver_n(truck_driver.getName());
                                transport_doc.setTruck_number(new_truck.getRegistration_plate());
                                // transferring the goods and the documents
                                if (!truck_driver.equals(driver)){
                                    truck_driver.setSites_documents(driver.getSites_documents());
                                    driver.setSites_documents(null);
                                }
                                new_truck.setNavigator(truck.getNavigator().getRoute());
                                System.out.println("The trucks finished to transfer all the goods and it's ready to go.");
                                return true;
                            }
                        }
                        System.out.println("Sorry Boss, we have the right truck for the job, but we don't have a driver with a license for that truck right now...");
                    }
                    break;

                // postpone the supplier to the end of the shipment.
                case 3:
                    // checking how many suppliers are left for today.
                    int count = 0;
                    for (Site site: truck.get_route()){
                        if (site.is_supplier()){
                            count++;
                        }
                    }
                    if (count <= 1){
                        System.out.println("Sorry Boss, this is the only supplier left for today...");
                        break;
                    }

                    int manager_choice = 0;
                    System.out.println("Please choose:");
                    System.out.println("1 - cancel this suppliers goods for today.");
                    System.out.println("2 - get back to this supplier later today.");;
                    boolean exist_2 = false;
                    while (!exist_2){
                        String ch = scanner.nextLine();
                        try {
                            manager_choice = Integer.parseInt(ch);

                            // Check if the input is a 9 digit integer
                            if (manager_choice == 1 || manager_choice == 2) {
                                isValid = true;
                            } else {
                                System.out.println("Input must be a 1 or 2.");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. please enter 1 or 2.");
                        }
                    }
                    // adding the supplier to the end of the route and afterwards the destinations the supplier needs to ship the goods.

                    truck.getNavigator().delete_site(truck.get_current_location().getSite_n());
                    // if the manager chose to  get back to this supplier later today, we're changing te route.
                    if (manager_choice == 2) {
                        truck.getNavigator().add_site(truck.get_current_location());
                        for (Site_Supply site_supply : driver.getSites_documents()) {
                            if (site_supply.getStore().getSupplier_n() == truck.get_current_location().getSite_n()) {
                                truck.getNavigator().add_site(site_supply.getStore());
                            }
                        }
                    }
                    // getting the products back to the supplier and delete the documents that not relevant for now.
                    for (Site_Supply site_supply: driver.getSites_documents()) {
                        if (site_supply.getOrigin() == truck.get_current_location().getSite_n()) {
                            for (String product : site_supply.getItems().keySet()) {
                                transport_doc.deleteProducts(product, site_supply.getItems().get(product));
                            }
                            driver.delete_site_document_by_origin(site_supply.getOrigin());
                        }
                    }
                    // now we are getting back to the previous weight:

                    transport_doc.delete_last_Weight();
                    truck.setCurrent_weight(transport_doc.get_last_weight());
                    if (manager_choice == 1){
                        System.out.println("The goods from the current supplier removed from the truck.");
                    }
                    else {
                        System.out.println("The truck route has changed and the supplier goods will be shipped later today by the truck.");
                    }
                    choice = 5;
                    break;

                // deleting the transport.
                case 4:
                    return false;
            }

        }
        return true;

    }
    // return truck by temperature level.
    public Truck getTruckByColdLevel (cold_level level){
        Truck truck = null;
        for(Truck t : trucks){
            if(t.getCold_level().getValue() <= level.getValue()) {
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

    //////// MAIN FUNCTION ///////////////
    public void start_transport(){
        int choice = 0;
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
        String input = null;
        while (choice != -1) {
            System.out.println("Hey Boss! what would you like to do?");
            System.out.println("1 - Hire a new driver");
            System.out.println("2 - See all the trucks with a cold level of your choice - \n\t 1- Freeze \n\t 2- Cold \n \t 3- Dry");
            System.out.println("3 - send a new transport to his way.");
            System.out.println("4 - quit.");
            while(!isValid){
                try {
                    input = scanner.nextLine();
                    choice = Integer.parseInt(input);

                    // Check if the input is a 5 digit integer
                    if (input.length() == 1 && choice > 0 && choice < 5) {
                        isValid = true;
                    } else {
                        System.out.println("Input must be an int between 1-4. ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid int between 1-4: ");
                }
            }
            isValid = false;
            switch (choice){
                // hire a new driver
                case 1:
                    hire_driver();
                    break;
                // have all the trucks by a cold level
                case 2:
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
                for(Truck t : trucks){
                    if(t.getCold_level().getValue() <= cool_level.getValue()) {
                        System.out.println(t.getRegistration_plate());
                    }
                }
                break;

                // make a new transport
                case 3:
                    System.out.println("Hey supplier!");
                    Transport transport = create_transport_document();
                    Truck truck = getTruckByNumber(transport.getTruck_number());
                    truck.setNavigator(transport.getDestinations());
                    Site current = truck.get_current_location();
                    while (current != null){
                        if (current.is_supplier()){
                            boolean isValidChoice = false;
                            String ch = null;
                            while (!isValidChoice) {
                                // creating a document
                                create_site_supply(transport);
                                // asking if he needs to make another one
                                System.out.println("Do you have items to ship to another store? (Write YES/NO): ");
                                System.out.println("1 - YES");
                                System.out.println("2 - NO");
                                ch = scanner.nextLine();
                                if (ch.equals("YES")){
                                    continue;
                                }
                                else if (ch.equals("NO")){
                                    isValidChoice = true;
                                }
                                else {
                                    System.out.println("You must enter YES/NO.");
                                }
                            }
                            //checking the weight
                            if (!check_weight(truck)){
                                boolean abort_transport = change_transport(transport, truck, truck.getCurrent_driver());
                                if (abort_transport){
                                    //delete_transport();
                                    System.out.println("Transport was aborted.");
                                    //return;
                                }
                            }
                        }
                        // unloading the goods in the store
                        else if (current.is_store()){
                            if (unload_goods((Store) current, truck, truck.getCurrent_driver())){
                                System.out.println("goods unloaded in " + current.getSite_n());
                            }
                            else {
                                System.out.println("We currently don't have any goods for " + current.getSite_n() + " , skip this store for now.");
                            }
                        }
                        // driving to the next site.
                        current = truck.get_next_site();
                    }
                    break;

                // quit the menu
                case 4:
                    return;
            }

        }
    }

    // check if string contain only numbers.
    public static boolean containsOnlyNumbers(String str) {
        return str.matches("[0-9]+");
    }

    public boolean truck_assigning(int driver_id, String truck_registration_plate){
        Truck_Driver driver = null;
        Truck truck = null;
        // checking if the given parameters are valid, and getting the diver and the truck if they exist.
        for (int i = 0; i < trucks.size(); i++) {
            if (trucks.get(i).equals(truck_registration_plate)){
                truck = trucks.get(i);
            }
        }
        if (truck == null){
            return false;
        }

        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).equals(driver_id)){
                driver = drivers.get(i);
            }
        }
        if (driver == null){
            return false;
        }

        if (driver.getLicense().getWeight() < truck.getMax_weight() || driver.getLicense().getCold_level().getValue() < truck.getCold_level().getValue()){
//            System.out.println("The driver's license does not fit to the truck");
            return false;
        }
        truck.setCurrent_driver(driver);
        driver.setCurrent_truck(truck);
        return true;
    }

    // create site supply document for the truck driver.
    public void create_site_supply(Transport transport){
        // ======================== Supplier ID ======================== //
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
        String input = null;
        int site_supplier_ID = 0;
        Truck_Driver truck_driver = null;
        while(!isValid){
            System.out.println("Please enter the site supply ID number (5 digits, only with the digits 0-9): ");
            input = scanner.nextLine();
            try {
                site_supplier_ID = Integer.parseInt(input);
                // Check if the input is a 5 digit integer
                if (input.length() == 5) {
                    isValid = true;
                } else {
                    System.out.print("Input must be a 5 digit integer. ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. ");
            }
            truck_driver = getDriverByTruckNumber(transport.getTruck_number());
            for(Site_Supply driver_doc : truck_driver.getSites_documents()){
                if(driver_doc.getId() == site_supplier_ID){
                    isValid = false;
                    System.out.print("This site supply ID number is already exist in this transport.");
                }
            }
        }
        // ======================== Supplier Address ======================== //
        String supplier_address = null;
        isValid = false;
        while (!isValid) {
            System.out.println("Please enter the address of the supplier: ");
            supplier_address = scanner.nextLine();
            for (Site site : transport.getDestinations()) {
                if(site.getAddress().equals(supplier_address)){
                    if(site.is_supplier()) {
                        isValid = true;
                    }
                    else{
                        System.out.print("The address is exist in this transport, but not belongs to supplier. ");
                    }
                    break;
                }
            }
            if(!isValid){
                System.out.print("This address is not part of this transport. ");
            }
        }
        // ======================== Store Address ======================== //
        String store_address = null;
        isValid = false;
        while (!isValid) {
            System.out.println("Please enter a store destination address: ");
            store_address = scanner.nextLine();
            for (Site site : transport.getDestinations()) {
                if(site.getAddress().equals(store_address)){
                    if(site.is_store()) {
                        isValid = true;
                    }
                    else{
                        System.out.println("The address is not belong to store. ");
                    }
                    break;
                }
            }
            if(!isValid){
                System.out.print("This address is not part of this transport. ");
            }
        }
        // ======================== Store As Destination ======================== //
        Store store = transport.getStoreByAddress(store_address);
        // ======================== Create Site Supply Document ======================== //
        Site_Supply site_supply_doc = new Site_Supply(site_supplier_ID, store, supplier_address);
        // ======================== Insert Items ======================== //
        isValid = false;
        while(!isValid) {
            System.out.println("Please enter the name of the item: ");
            String item_name = scanner.nextLine();
            boolean isValidNumber = false;
            int item_amount = 0;
            while (!isValidNumber) {
                System.out.println("Please enter the items amount of " + item_name + " (Integers numbers only): ");
                input = scanner.nextLine();
                try {
                    item_amount = Integer.parseInt(input);
                    isValidNumber = true;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. ");
                }
            }
            System.out.println("Do you have another item to add? (press 1 or 2 only): ");
            boolean isValidChoice = false;
            String choice = null;
            while (!isValidChoice) {
                System.out.println("\t 1 - YES");
                System.out.println("\t 2 - NO");
                choice = scanner.nextLine();
                if(!choice.equals("1") && !choice.equals("2")){
                    System.out.println("Invalid Input. Please choose a valid option: ");
                }
                else{
                    isValidChoice = true;
                }
            }
            if(choice.equals("2")){
                isValid = true;
            }
            // ======================== Insert Items To Site Supply And Transport Documents ======================== //
            site_supply_doc.insert_item(item_name, item_amount);
            transport.insertToProducts(item_name, item_amount);
        }
        // ======================== Items Total Weight ======================== //
        isValid = false;
        Double items_weight = 0.0;
        while(!isValid){
            System.out.println("Please enter the weight of all item together (Integers numbers only):  ");
            try {
                input = scanner.nextLine();
                items_weight = Double.parseDouble(input);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. ");
            }
        }
        // ======================== Insert Weight To Transport Document ======================== //
        transport.insertToWeights(items_weight);
        // ======================== Insert Weight To Truck ======================== //
        Truck truck = getTruckByNumber(transport.getTruck_number());
        truck.addWeight(items_weight);
        // ======================== Add the Site Supply Document To Truck Driver ======================== //
        truck_driver.Add_site_document(site_supply_doc);
    }
    public Truck getTruckByNumber(String truck_number){
        Truck truck = null;
        for(Truck t : trucks){
            if(t.getRegistration_plate().equals(truck_number)){
                truck = t;
            }
        }
        return truck;
    }

    // create a transport document in the system.
    public Transport create_transport_document(){
        Scanner scanner = new Scanner(System.in);
        String input = null;
        // ======================== Transport ID ======================== //
        int transport_Id = 0;
        boolean isValid = false;
        // getting the transport's ID number.
        while (!isValid) {
            System.out.println("Please enter transport ID number (5 digits, only with the digits 0-9): ");
            input = scanner.nextLine();
            try {
                transport_Id = Integer.parseInt(input);
                // Check if the input is a 5 digit integer
                if (input.length() == 5) {
                    isValid = true;
                } else {
                    System.out.print("Input must be a 5 digit integer. ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. ");
            }
            Transport transport = Transport_Log.get(transport_Id);
            if(transport != null){
                isValid = false;
                System.out.println("The transport ID number is already exist in the transports system. ");
            }
        }
        // ======================== Truck ======================== //
        cold_level cool_level = null;
        isValid = false;
        while(!isValid){
            System.out.println("Please enter the required cold level of the truck (press 1, 2 or 3 only): ");
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
        Truck truck = getTruckByColdLevel(cool_level);
        String truck_number = truck.getRegistration_plate();
        // ======================== Truck Driver ======================== //
        String driver_name = null;
        for(Truck_Driver driver: drivers){
            if(truck_assigning(driver.getID(), truck.getRegistration_plate())){
                driver_name = driver.getName();
                driver.setCurrent_truck(truck);
                truck.setCurrent_driver(driver);
                break;
            }
        }
        // ======================== Origin - Logistical Center  ======================== //
        Logistical_Center origin = null;
        if(logistical_center == null) {
            System.out.println("Please enter the origin address: ");
            String origin_address = scanner.nextLine();
            System.out.println("Please enter the origin phone number: ");
            String origin_phone = scanner.nextLine();
            String origin_name = "Logistical Center";
            System.out.println("Please enter the contact person name of the origin: ");
            String origin_contact_name = scanner.nextLine();
            origin = new Logistical_Center(origin_address, origin_phone, origin_name, origin_contact_name);
            this.logistical_center = origin;
        }
        else{
            origin = logistical_center;
        }
        // ======================== Date And Time ======================== //
        LocalDateTime now  = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String Date = now.toLocalDate().format(dateFormatter);
        String Time = now.toLocalTime().format(timeFormatter);
        // ======================== Create Transport Document ======================== //
        cold_level cold_level = truck.getCold_level();
        Transport transport_doc = new Transport(transport_Id, Date, Time, truck_number, driver_name, origin, cold_level);
        // ======================== Add Destinations ======================== //
        boolean stop_adding_destinations = false;
        Site destination;
        while(!stop_adding_destinations) {
            String site_type = null;
            isValid = false;
            while (!isValid) {
                System.out.println("Is the destination is a store or supplier? (press 1 or 2 only) ");
                System.out.println("\t 1 - Store ");
                System.out.println("\t 2 - Supplier ");
                site_type = scanner.nextLine();
                if (!site_type.equals("1") && !site_type.equals("2")) {
                    System.out.println("Invalid input. try again. ");
                } else {
                    isValid = true;
                }
            }
            if(site_type.equals("1")){
                System.out.println("Please enter the store address: ");
                String store_address = scanner.nextLine();
                System.out.println("Please enter the store phone number: ");
                String phone_number = scanner.nextLine();
                System.out.println("Please enter the name of the store: ");
                String store_name = scanner.nextLine();
                System.out.println("Please enter the contact person name of the store: ");
                String store_contact_name = scanner.nextLine();
                System.out.println("Please enter the manager name of the store: ");
                String manager_name = scanner.nextLine();
                System.out.println("Please enter the area of the store (one digit 0-9 number): ");
                input = scanner.nextLine();
                int area = 0;
                isValid = false;
                while(!isValid) {
                    try {
                        area = Integer.parseInt(input);
                        // Check if the input is one digit integer
                        if (input.length() == 1) {
                            isValid = true;
                        } else {
                            System.out.println("Input must be only one digit integer. ");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid 1 digit integer: ");
                    }
                }
                destination = new Store(store_address, phone_number, store_name, manager_name, area, store_contact_name);
                transport_doc.insertToDestinations(destination);
            }
            if(site_type.equals("2")){
                System.out.println("Please enter the supplier address: ");
                String supplier_address = scanner.nextLine();
                System.out.println("Please enter the supplier phone number: ");
                String phone_number = scanner.nextLine();
                System.out.println("Please enter the contact person name of the supplier:");
                String supplier_contact_name = scanner.nextLine();
                System.out.println("Please enter the name of the supplier: ");
                String supplier_name = scanner.nextLine();
                destination = new Supplier(supplier_address, phone_number, supplier_name, supplier_contact_name);
                transport_doc.insertToDestinations(destination);
            }
            System.out.println("Do you want to add another destination? ");
            String choice = null;
            isValid = false;
            while(!isValid) {
                System.out.println("\t 1 - YES");
                System.out.println("\t 2 - NO");
                choice = scanner.nextLine();
                if(!choice.equals("1") && !choice.equals("2")){
                    System.out.println("Invalid input. Please enter a valid choice:");
                }
                else{
                    isValid = true;
                }
            }
            if(choice.equals("2")){
                stop_adding_destinations = true;
            }
        }
        // ======================== Add Transport Document ======================== //
        Transport_Log.put(transport_Id, transport_doc);
        return transport_doc;
    }
    public boolean check_weight(Truck truck){
        if (truck.getCurrent_weight() > truck.getMax_weight()){
            System.out.println("Alert! the truck is in overweight!");
            return false;
        }
        else {
            return true;
        }
    }
    // unloading all the goods in a store, and update the weight of the truck accordingly.
    public boolean unload_goods(Store store, Truck truck, Truck_Driver driver){
        boolean unloaded = false;
        for (Site_Supply site_supply: driver.getSites_documents()){
            if (site_supply.getStore().getAddress() == store.getAddress()){
                unloaded = true;
                if (delivered_supplies_documents.containsKey(store)) {
                    ArrayList<Site_Supply> site_supplies= delivered_supplies_documents.get(store);
                    site_supplies.add(site_supply);
                }
                else {
                    ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
                    siteSupplies.add(site_supply);
                    delivered_supplies_documents.put(store, siteSupplies);
                }
                // change to delete only one site.
                driver.delete_site_document_by_ID(site_supply.getId());
            }
        }
        System.out.println("Hey there truck driver");
        boolean valid_input = false;
        double weight = 0;
        String input = null;
        Scanner scanner = new Scanner(System.in);
        while (!valid_input){
            System.out.println("please weight your truck after unloading and tell us the weight you got:");
            input = scanner.nextLine();
            try {
                weight = Double.parseDouble(input);
                if (weight > truck.getMax_weight()){
                    System.out.println("No way... it's above the maximum weight!");
                } else if (weight > truck.getCurrent_weight()) {
                    System.out.println("No way... it's above the weight you had before unloading the goods!");
                } else if (weight < truck.getNet_weight()) {
                    System.out.println("No way... it's below the Net weight of the truck!");
                }
                else {
                    truck.setCurrent_weight(weight);
                    valid_input = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid positive double number. ");
            }
        }
        return unloaded;
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
            for(Truck truck : trucks){
                if(truck.getRegistration_plate().equals(input)){
                    System.out.print("The Truck is already exist in the system! ");
                }
            }
            if(!containsOnlyNumbers(input)){
                System.out.print("Invalid input. ");
            }
            else if (input.length() != 8) {
                System.out.println("Input must be a 8 digit integer. ");
            }
            else{
                registration_number = input;
                isValid = true;
            }
        }
        // ======================== Truck Moodle ======================== //
        System.out.println("Please enter the truck moodle: ");
        String truck_moodle = scanner.nextLine();
        // ======================== Truck Net Weight ======================== //
        double truck_net_weight = 0.0;
        System.out.println("Please enter the net weight of the truck: ");
        input = scanner.nextLine();
        try {
            truck_net_weight = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.print("Invalid input. ");
        }
        // ======================== Truck max Weight ======================== //
        double truck_max_weight = 0.0;
        System.out.println("Please enter the maximum weight of the truck: ");
        input = scanner.nextLine();
        try {
            truck_max_weight = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.print("Invalid input. ");
        }
        // ======================== Truck Cold Level ======================== //
        cold_level cool_level = null;
        isValid = false;
        while(!isValid){
            System.out.print("Please enter the cold level of the truck (press 1, 2 or 3 only): ");
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
        trucks.add(truck);
    }



    /// ========== display for test ======= ///

    public void display_transport_doc(){
        for (Map.Entry<Integer, Transport> entry : Transport_Log.entrySet()) {
            int id = entry.getKey();
            Transport transport = entry.getValue();
            System.out.println("=========== Transport - " + id + " - information ===========");
            transport.transportDisplay();
        }
    }

    public void display_trucks(){
        System.out.println("======================================= Trucks in the system =======================================");
        for(Truck t : trucks){
            t.truckDisplay();
        }
    }

    public void display_drivers(){
        System.out.println("======================================= Drivers in the system =======================================");
        for(Truck_Driver driver : drivers){
            driver.driverDisplay();
        }
    }

    public void display_site_supply(){
        for (Map.Entry<Store, ArrayList<Site_Supply>> entry : delivered_supplies_documents.entrySet()) {
            for(Site_Supply siteSupply : entry.getValue()){
                siteSupply.sDisplay();
            }
        }
    }




}
