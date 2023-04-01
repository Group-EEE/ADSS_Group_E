import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transport_System {
    private ArrayList<Truck> trucks;
    private ArrayList<Truck_Driver> drivers;
    private Map<Integer, Transport> Transport_Log;
    Transport_System(){
        trucks = new ArrayList<Truck>();
        drivers = new ArrayList<Truck_Driver>();
        Transport_Log = new HashMap<>();
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
                    System.out.println("Input must be a 9 digit integer.\n");
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
                    System.out.println("Input must be a 5 digit integer.\n");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 5 digit integer.\n");
            }
        }
        isValid = false;
        // getting the driver's license truck restrictions
        double temperature = 0;
        double weight = 0;
        while (!isValid) {
            System.out.print("Enter the temperature Limit of the truck the driver can drive in, and the the Max weight of the truck he can drive in: \n: ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            try {
                // Check if the input contains two parts
                if (parts.length == 2) {
                    temperature = Double.parseDouble(parts[0]);
                    weight = Double.parseDouble(parts[1]);

                    // Check if the input numbers are positive
                    if (weight > 0) {
                        isValid = true;
                    } else {
                        System.out.println("Weight must be positive.");
                    }
                } else {
                    System.out.println("Input must contain two integers separated by a space.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter two valid positive integers separated by a space.");
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

        Truck_Driver driver = new Truck_Driver(ID, Driver_name, License_ID, temperature, weight);
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

                    // Check if the input is a 1,2 or 3.
                    if (choice != 1 || choice != 2 || choice != 3) {
                        isValid = true;
                    } else {
                        System.out.println("Boss, there's only 3 options...");
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
                        if (!change_transport(transport_doc, truck, driver));
                        return false;
                    }
                    choice = 5;
                    break;

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
                                choice = 5;
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

    //return truck by temperature level.needs to change by cooling level - !!enum!!
    public Truck getTruckByCoolingLevel(cold_level level){
        Truck truck = null;
        for(Truck t : trucks){
            if (t.getCold_level().equals(level)) {
                truck = t;
                break;
//            } else if (t.getTemperature_capability() < temperature && t.getTemperature_capability() > truck.getTemperature_capability()) {
//                truck = t;
//            }
        }
        return truck;
    }

    public void start_transport(){

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
            System.out.println("The driver's license does not fit to the truck");
            return false;
        }
        truck.setCurrent_driver(driver);
        driver.setCurrent_truck(truck);
        return true;
    }

    public void create_site_supply(Transport transport){
        // ======================== Supplier ID ======================== //
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
        String input = null;
        int supplier_ID = 0;
        System.out.println("Please enter the site supply ID number: ");
        while(!isValid){
            input = scanner.nextLine();
            try {
                supplier_ID = Integer.parseInt(input);

                // Check if the input is a 5 digit integer
                if (input.length() == 5) {
                    isValid = true;
                } else {
                    System.out.println("Input must be a 5 digit integer. ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 5 digit integer: ");
            }
        }
        // ======================== Supplier Address ======================== //
        System.out.println("Please enter the address of the supplier: ");
        String supplier_address = null;
        isValid = false;
        while (!isValid) {
            supplier_address = scanner.nextLine();
            for (Site supplier : transport.getDestinations()) {
                if(supplier.getAddress().equals(supplier_address)){
                    isValid = true;
                }
            }
            if(!isValid){
                System.out.println("This address is not part of the transport. Please enter a valid address: ");
            }
        }
        // ======================== Supplier Name ======================== //
        System.out.println("Please enter the name of the supplier: ");
        String supplier_name = scanner.nextLine();
        // ======================== Store As Destination ======================== //
        Store store = transport.getStoreByAddress(supplier_address);
        // ======================== Create Site Supply Document ======================== //
        Site_Supply site_supply_doc = new Site_Supply(supplier_ID, supplier_name, supplier_address, store);
        // ======================== Insert Items ======================== //
        isValid = false;
        while(!isValid) {
            System.out.println("Please enter the name of the item: ");
            String item_name = scanner.nextLine();
            boolean isValidNumber = false;
            int item_amount = 0;
            while (!isValidNumber) {
                System.out.println("Please enter the amount of items of " + item_name + " (Integers numbers only): ");
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
                System.out.println("1 - YES");
                System.out.println("2 - NO");
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
        // TO-DO: Insert weight to the truck as well!
        transport.insertToWeights(store, items_weight);

    }


    public Transport create_transport_document(){
        Scanner scanner = new Scanner(System.in);
        // ======================== Transport_ID ======================== //
        System.out.print("Please enter transport ID number (5 digits, only with the digits 0-9) : ");
        int transport_Id = 0;
        boolean isValid = false;
        // getting the transport's ID
        while (!isValid) {
            String input = scanner.nextLine();
            try {
                transport_Id = Integer.parseInt(input);

                // Check if the input is a 5 digit integer
                if (input.length() == 5) {
                    isValid = true;
                } else {
                    System.out.println("Input must be a 5 digit integer. ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 5 digit integer: ");
            }
        }

        // ======================== Truck ======================== //
        System.out.print("Please enter the maximal temperature level of the truck: ");
        Double temperature = null;
        isValid = false;
        // getting the truck number
        while(!isValid){
            String input = scanner.nextLine();
            try {
                temperature = Double.parseDouble(input);
                // Check if the input is 8 digit integer
                if (input.length() == 8) {
                    isValid = true;
                } else
                {
                    System.out.println("Input must be a 8 digit integer. ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 8 digit integer: ");
            }
        }
        Truck truck = getTruckByCoolingLevel(temperature);
        String truck_number = truck.getRegistration_plate();

        // ======================== Truck Driver ======================== //
        String driver_name = null;
        for(Truck_Driver driver: drivers){
            if(truck_assigning(driver.getID(), truck.getRegistration_plate())){
                driver_name = driver.getName();
                driver.setCurrent_truck(truck);
                break;
            }
        }

        // ======================== Site Origin ======================== //
        System.out.println("Please enter the origin address: ");
        String origin_address = scanner.nextLine();
        System.out.println("Please enter the origin phone number: ");
        String origin_phone = scanner.nextLine();
        System.out.println("Please enter the contact person name of the origin: ");
        String origin_contact_name = scanner.nextLine();
        isValid = false;
        System.out.println("Is the origin is store, supplier or logistics center? (press 1, 2, or 3) ");
        String site_type = null;
        while(!isValid) {
            System.out.println("1 - Store ");
            System.out.println("2 - Supplier ");
            System.out.println("3 - Logistics center ");
            site_type = scanner.nextLine();
            if(!Objects.equals(site_type, "1") || !Objects.equals(site_type, "2") || !Objects.equals(site_type, "3")){
                System.out.println("Invalid input. Please enter a valid choice:");
            }
            else{
                isValid = true;
            }
        }
        Site origin = null;
        // ======================== Site - Store ======================== //
        if(Objects.equals(site_type, "1")){
            System.out.println("Please enter the name of the store manager: ");
            String store_manager = scanner.nextLine();
            int area = 0;
            isValid = false;
            while(!isValid){
                System.out.println("Please enter the area of the store (one digit 0-9 only): ");
                String input = scanner.nextLine();
                try {
                    area = Integer.parseInt(input);
                    // Check if the input is one digit integer
                    if (input.length() == 1) {
                        isValid = true;
                    } else
                    {
                        System.out.println("Input must be only one digit integer. ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid 1 digit integer: ");
                }
            }
            origin = new Store(origin_address, origin_phone, origin_contact_name, store_manager, area);
        }
        // ======================== Site - Supplier ======================== //

        if(Objects.equals(site_type, "2")){
            System.out.println("Please enter the supplier name: ");
            String supplier_name = scanner.nextLine();
            origin = new Supplier(origin_address, origin_phone, origin_contact_name, supplier_name);
        }
        // ======================== Site - Logistical Center ======================== //
        if(Objects.equals(site_type, "3")){
            if(logisticalCenter != null){
                System.out.println("The system already have the logistical center details. ");
            }
            else {
                origin = new Logistical_Center(origin_address, origin_phone, origin_contact_name);
            }
        }

        // ======================== Date And Time ======================== //
        LocalDateTime now  = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String Date = now.toLocalDate().format(dateFormatter);
        String Time = now.toLocalTime().format(timeFormatter);

        // ======================== Create Transport Document ======================== //
        Transport transport_doc = new Transport(transport_Id, Date, Time, truck_number, driver_name, origin);

        // ======================== Add Destinations ======================== //
        boolean stop_adding_destinations = false;
        Site destination;
        while(!stop_adding_destinations) {
            System.out.println("Is the destination is a store or supplier? (press 1 or 2) ");
            site_type = null;
            isValid = false;
            while (!isValid) {
                System.out.println("1 - Store ");
                System.out.println("2 - Supplier ");
                site_type = scanner.nextLine();
                if (!Objects.equals(site_type, "1") || !Objects.equals(site_type, "2")) {
                    System.out.println("Invalid input. Please enter a valid choice:");
                } else {
                    isValid = true;
                }
            }
            if(site_type.equals("1")){
                System.out.println("Please enter the store address: ");
                String store_address = scanner.nextLine();
                System.out.println("Please enter the store phone number: ");
                String phone_number = scanner.nextLine();
                System.out.println("Please enter the contact person name of the store: ");
                String store_contact_name = scanner.nextLine();
                System.out.println("Please enter the manager name of the store: ");
                String manager_name = scanner.nextLine();
                System.out.println("Please enter the area of the store: (one digit 0-9 number)");
                String input = scanner.nextLine();
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
                destination = new Store(store_address, phone_number, store_contact_name, manager_name, area);
                transport_doc.insertToDestinations(destination);
            }
            if(site_type.equals("2")){
                System.out.println("Please enter the supplier address: ");
                String supplier_address = scanner.nextLine();
                System.out.println("Please enter the supplier phone number: ");
                String phone_number = scanner.nextLine();
                System.out.println("Please enter the contact person name of the supplier:");
                String supplier_contact_name = scanner.nextLine();
                System.out.println("Please enter the manager name of the supplier: ");
                String supplier_name = scanner.nextLine();
                destination = new Supplier(supplier_address, phone_number, supplier_contact_name, supplier_name);
                transport_doc.insertToDestinations(destination);
            }
            System.out.println("Do you want to add another destination? ");
            String choice = null;
            isValid = false;
            while(!isValid) {
                System.out.println("1 - YES");
                System.out.println("2 - NO");
                choice = scanner.nextLine();
                if(!Objects.equals(choice, "1") || !Objects.equals(choice, "2")){
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
        transports.put(transport_Id, transport_doc);
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
}
