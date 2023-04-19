import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transport_System {

    private Logistical_Center logistical_center;

    Transport_System(){
        System.out.println("Hey Boss, currently the system does not have the details about the logistics center.");
        String origin_address = null;
        boolean isValid = false;
        String input;
        Scanner scanner = new Scanner(System.in);
        while (!isValid) {
            System.out.println("Please enter the logistical center address: ");
            input = scanner.nextLine();
            if (input.strip().equals("")) {
                System.out.print("Invalid input. ");
            }
            else{
                origin_address = input;
                isValid = true;
            }
        }
        String origin_phone = null;
        isValid = false;
        while(!isValid) {
            System.out.println("Please enter the logistical center phone number (only digits 0-9): ");
            origin_phone = scanner.nextLine();
            if(!containsOnlyNumbers(origin_phone) || origin_phone.strip().equals("")){
                System.out.print("Invalid input. ");
            }
            else{
                isValid = true;
            }
        }
        String origin_name = "Logistical Center";
        isValid = false;
        String origin_contact_name = null;
        while (!isValid) {
            System.out.println("Please enter the contact person name of the logistical center: ");
            origin_contact_name = scanner.nextLine();
            if(!origin_contact_name.strip().equals("")){
                isValid = true;
            }
            else{
                System.out.print("Invalid input. ");

            }
        }
        this.logistical_center = new Logistical_Center(origin_address, origin_phone, origin_name, origin_contact_name);
    }

    // assigning a truck for a truck_driver by a given driver ID and a truck's registration plate.
    public void hire_driver(){
        Scanner scanner = new Scanner(System.in);
        int ID = 0;
        boolean isValid = false;
        // getting the driver's ID
        while (!isValid) {
            System.out.println("Enter an ID (a 9 digit integer with no 0 at the beginning): ");
            String input = scanner.nextLine();
            if(input.startsWith("0")){
                System.out.print("ID number cannot contain 0 at the beginning. ");
                if(input.length() != 9){
                    System.out.print("Input must be a 9 digit integer. ");
                }
            }
            else {
                try {
                    ID = Integer.parseInt(input);
                    // Check if the input is a 9 digit integer
                    if (input.length() == 9) {
                        isValid = true;
                    } else {
                        System.out.print("Input must be a 9 digit integer. ");
                    }


                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. ");
                }
                // check if the ID number is already exist in the system.
                for (Truck_Driver truck_driver : logistical_center.getDrivers()) {
                    if (truck_driver.getID() == ID) {
                        isValid = false;
                        System.out.println("The ID number - " + ID + " - is already belong to some driver in the transport system. Please enter a valid 9 digit integer: ");
                    }
                }
            }
        }
        System.out.println("Please enter the driver's license details: ");
        isValid = false;
        int License_ID = 0;
        // getting The driver's license ID
        while (!isValid) {
            System.out.println("Enter an license ID (a 5 digit integer): ");
            String input = scanner.nextLine();
            if(input.startsWith("0")){
                System.out.print("License ID number cannot contain 0 at the beginning. ");
                if(input.length() != 5){
                    System.out.print("Input must be a 5 digit integer. ");
                }
            }
            else {
                try {
                    License_ID = Integer.parseInt(input);

                    // Check if the input is a 5 digit integer
                    if (input.length() == 5) {

                        isValid = true;
                    } else {
                        System.out.print("Input must be a 5 digit integer. ");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid 5 digit integer.");
                }
                // check if the license ID number is already exist in the system.
                for (Truck_Driver truck_driver : logistical_center.getDrivers()) {
                    if (truck_driver.getLicense().getL_ID() == License_ID) {
                        isValid = false;
                        System.out.println("The license ID number - " + License_ID + " - is already belong to some driver in the transport system. Please enter a valid 5 digit integer: ");
                    }
                }
            }
        }
        isValid = false;
        // getting the driver's license truck restrictions
        cold_level level = null;
        double weight = 0;
        int level_ch;
        while (!isValid) {
            System.out.println("Enter the cold level of the truck the driver can drive in (1- Freeze, 2- Cold, 3- Dry),\n and the the Max weight of the truck he can drive in: ");
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
                        switch (level_ch) {
                            case 1 -> level = cold_level.Freeze;
                            case 2 -> level = cold_level.Cold;
                            case 3 -> level = cold_level.Dry;
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
            System.out.println("Enter the driver's full name: ");
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
    public boolean change_transport(Transport transport_doc, Truck truck, Truck_Driver driver){
        int choice = 0;
        System.out.println("The truck max weight is: " + truck.getMax_weight() + " but her current weight is " + truck.getCurrent_weight() + "!!!");
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
                case 1 -> {
                    int stores = 0;
                    for (Site site : truck.get_route()) {
                        if (!site.is_supplier()) {
                            stores++;
                        }
                    }
                    if (stores <= 1) {
                        System.out.println("Sorry Boss, this is the only store left for today...");
                        break;
                    }
                    // asking for the store until he gives a valid store name from are current destinations.
                    System.out.println("Please enter the Store you want to cancel the shipment:");
                    String site_to_remove = null;
                    boolean exist = false;
                    while (!exist) {
                        String store = scanner.nextLine();
                        for (Site site : truck.get_route()) {
                            if (site.getSite_name().equals(store)) {
                                if (site.is_supplier()) {
                                    System.out.println("You've entered a supplier Boss... you need to put a store!");
                                } else {
                                    site_to_remove = store;
                                    exist = true;
                                }
                                break;
                            }
                        }
                        if (!exist) {
                            System.out.println("This store is not on the list boss...");
                        }
                    }
                    // deleting the documents with the relevant documents according to the deleted destination.
                    for (Site_Supply site_supply : driver.getSites_documents()) {
                        if (site_supply.getStore().getSite_name().equals(site_to_remove)) {
                            for (String product : site_supply.getItems().keySet()) {
                                transport_doc.deleteProducts(product, site_supply.getItems().get(product));
                                System.out.println("We dropped " + site_supply.getItems().get(product) + " of the product - " + product);
                            }
                            truck.addWeight(-1 * site_supply.getProducts_total_weight());
                        }
                    }
                    driver.delete_site_document_by_destination(site_to_remove);
                    transport_doc.deleteDestination(site_to_remove);
                    truck.getNavigator().delete_site(site_to_remove);
                    if (!check_weight(truck)) {
                        return change_transport(transport_doc, truck, driver);
                    }
                    return true;
                }

                // changing the truck to a suitable one.
                case 2 -> {
                    Truck new_truck = getTruckByColdAndWeight(truck.getCold_level(), truck.getCurrent_weight());
                    if (new_truck == null) {
                        System.out.println("Sorry Boss, we don't have a suitable truck...");
                    } else {
                        //now we need to check if we need to assign a driver to the new truck.
                        for (Truck_Driver truck_driver : logistical_center.getDrivers()) {
                            if (truck_assigning(truck_driver.getID(), new_truck.getRegistration_plate())) {
                                // updating the current driver's truck and the opposite.
                                driver.setCurrent_truck(null);
                                truck.setCurrent_driver(null);
                                // updating the details in the transport document
                                transport_doc.setDriver_name(truck_driver.getName());
                                transport_doc.setTruck_number(new_truck.getRegistration_plate());
                                new_truck.setCurrent_weight(truck.getCurrent_weight());
                                // transferring the goods and the documents
                                if (!truck_driver.equals(driver)) {
                                    truck_driver.setSites_documents(driver.getSites_documents());
                                    new_truck.setNavigator(truck.getNavigator().getRoute());
                                    driver.setSites_documents(null);
                                }
                                System.out.println("The trucks finished to transfer all the goods and it's ready to go,");
                                System.out.println("The new driver is: " + truck_driver.getName() + " and his driving the truck: " + new_truck.getRegistration_plate());
                                return true;
                            }
                        }
                        System.out.println("Sorry Boss, we have the right truck for the job, but we don't have a driver with a license for that truck right now...");
                    }
                }

                // postpone the supplier to the end of the shipment.
                case 3 -> {
                    // checking how many suppliers are left for today.
                    boolean end_case = true;
                    for (Site_Supply site_supply : driver.getSites_documents()) {
                        if (!site_supply.getOrigin().equals(truck.get_current_location().getSite_name())) {
                            end_case = false;
                            break;
                        }
                    }
                    if (end_case) {
                        System.out.println("Sorry Boss, this is the only supplier left for today...");
                        break;
                    }
                    int manager_choice = 0;
                    System.out.println("Please choose:");
                    System.out.println("1 - cancel this suppliers goods for today.");
                    System.out.println("2 - get back to this supplier later today.");
                    boolean exist_2 = false;
                    while (!exist_2) {
                        String ch = scanner.nextLine();
                        try {
                            manager_choice = Integer.parseInt(ch);

                            // Check if the input is a 9 digit integer
                            if (manager_choice == 1 || manager_choice == 2) {
                                exist_2 = true;
                            } else {
                                System.out.println("Input must be a 1 or 2.");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. please enter 1 or 2.");
                        }
                    }
                    // adding the supplier to the end of the route and afterwards the destinations the supplier needs to ship the goods.

                    truck.getNavigator().delete_site(truck.get_current_location().getSite_name());
                    // if the manager chose to  get back to this supplier later today, we're changing te route.
                    if (manager_choice == 2) {
                        truck.getNavigator().add_site(truck.get_current_location());
                        for (Site_Supply site_supply : driver.getSites_documents()) {
                            if (site_supply.getOrigin().equals(truck.get_current_location().getSite_name())) {
                                truck.getNavigator().add_site(site_supply.getStore());
                            }
                        }
                    }
                    // getting the products back to the supplier and delete the documents that not relevant for now.
                    for (Site_Supply site_supply : driver.getSites_documents()) {
                        if (site_supply.getOrigin().equals(truck.get_current_location().getSite_name())) {
                            for (String product : site_supply.getItems().keySet()) {
                                transport_doc.deleteProducts(product, site_supply.getItems().get(product));
                            }
                            driver.delete_site_document_by_origin(site_supply.getOrigin());
                        }
                    }
                    // now we are getting back to the previous weight:

                    transport_doc.delete_last_Weight();
                    truck.setCurrent_weight(transport_doc.get_last_weight());
                    if (manager_choice == 1) {
                        System.out.println("The goods from the current supplier removed from the truck.");
                    } else {
                        System.out.println("The truck route has changed and the supplier goods will be shipped later today by the truck.");
                    }
                    return true;
                }

                // deleting the transport.
                case 4 -> {
                    return false;
                }
            }

        }
        return true;

    }


    // return some truck by temperature level.
    public Truck getTruckByColdLevel (cold_level level){
        Truck truck = null;
        for(Truck t : logistical_center.getTrucks()){
            if(t.getCold_level().getValue() <= level.getValue() && !t.Occupied()) {
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

    public Truck getTruckByColdAndWeight(cold_level level, double weight){
        Truck truck = null;
        for(Truck t : logistical_center.getTrucks()){
            if(t.getCold_level().getValue() <= level.getValue() && t.getMax_weight() > weight) {
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
                    String manager_choice;
                    ArrayList<Integer> transport_IDS = new ArrayList<>();
                    boolean choosing = false;
                    while (!choosing){
                        System.out.println("Hey Boss, which transport you want to send?");
                        System.out.println("1 - display all the transports that haven't started:");
                        System.out.println("2 - send transport by enter his ID:");
                        System.out.println("3- that's all for now.");
                        manager_choice = scanner.nextLine();
                        if (manager_choice.equals("1")){
                            for (Map.Entry<Integer, Transport> entry : logistical_center.getTransport_Log().entrySet()) {
                                if (!entry.getValue().Started()) {
                                    int t_id = entry.getKey();
                                    Transport temp_transport = entry.getValue();
                                    System.out.println("=========== Transport - " + t_id + " - information ===========");
                                    temp_transport.transportDisplay();
                                }
                            }
                        } else if (manager_choice.equals("2")) {
                            int key =0;
                            boolean end_choosing = false;
                            while (!end_choosing){
                                if (logistical_center.getTransport_Log().size() == 0){
                                    System.out.println("We don't have any registered transport...");
                                    break;
                                }
                                System.out.println("Please enter the transport ID you want to start:");
                                try {
                                    String key_input = scanner.nextLine();
                                    key = Integer.parseInt(key_input);
                                    if (logistical_center.getTransport_Log().containsKey(key)) {
                                        Transport temp = logistical_center.getTransport_Log().get(key);
                                        temp.setStarted(true);
                                        transport_IDS.add(key);
                                        System.out.println("Transport " + key + " will start soon. If you want to send another one please press 1, to get back to transports sending menu press anything else:");
                                        String inp1 = scanner.nextLine();
                                        if (inp1.equals("1")){
                                            continue;
                                        }
                                        else {
                                            end_choosing = true;
                                        }
                                    } else {
                                        System.out.println("Transport ID does not exist. If you don't want to continue press 1, otherwise press anything else:");
                                        String inp2 = scanner.nextLine();
                                        if (inp2.equals("1")){
                                            end_choosing = true;
                                        }
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid 5 digit integer.");
                                }
                            }
                        } else if (manager_choice.equals("3")) {
                            choosing = true;
                        }
                        else{
                            System.out.println("Invalid input.");
                        }
                    }
                    // starting the transports the manager chose.
                    System.out.println("Starting the transports.");
                    for (int KEY: transport_IDS){
                        boolean aborted = false;
                        Transport chosen_transport = logistical_center.getTransport_Log().get(KEY);
                        // ======================== Date And Time ======================== //
                        LocalDateTime now  = LocalDateTime.now();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String Date = now.toLocalDate().format(dateFormatter);
                        String Time = now.toLocalTime().format(timeFormatter);
                        chosen_transport.setDate(Date);
                        chosen_transport.setDeparture_time(Time);
                        // ========================= starting the transport ======================= //
                        Truck truck = getTruckByNumber(chosen_transport.getTruck_number());
                        truck.setNavigator(chosen_transport.getDestinations());
                        System.out.println("Transport - " + chosen_transport.getTransport_ID() + " started.");
                        Site current = truck.get_next_site();
                        while (current != null){
                            if (current.is_supplier()){
                                boolean isValidChoice = false;
                                String ch = null;
                                while (!isValidChoice) {
                                    System.out.println("Hey " + current.getSite_name() + " manager!");
                                    // creating a document
                                    create_site_supply(chosen_transport, current.getAddress());
                                    // asking if he needs to make another one
                                    System.out.println("Do you have items to ship to another store? (Write YES/NO): ");
                                    System.out.println("1 - YES");
                                    System.out.println("2 - NO");
                                    while (true){
                                        ch = scanner.nextLine();
                                        if (ch.equals("YES")){
                                            break;
                                        }
                                        else if (ch.equals("NO")){
                                            isValidChoice = true;
                                            break;
                                        }
                                        else {
                                            System.out.println("You must enter YES/NO.");
                                        }
                                    }
                                }
                                //insert the weight
                                chosen_transport.insertToWeights(truck.getCurrent_weight());
                                //checking the weight
                                if (!check_weight(truck)){
                                    boolean abort_transport = !change_transport(chosen_transport, truck, truck.getCurrent_driver());
                                    if (abort_transport){
                                        //delete_transport();
                                        System.out.println("Transport was aborted. you can try to send it later");
                                        aborted = true;
                                        chosen_transport.setStarted(false);
                                        break;
                                    }
                                    else {
                                        truck = getTruckByNumber(chosen_transport.getTruck_number());
                                    }
                                }
                            }
                            // unloading the goods in the store
                            else if (current.is_store()){
                                if (unload_goods((Store) current, truck, truck.getCurrent_driver())){
                                    System.out.println("goods unloaded in " + current.getSite_name());
                                }
                                else {
                                    System.out.println("We currently don't have any goods for " + current.getSite_name() + " ,skip this store for now.");
                                }
                            }
                            // driving to the next site.
                            current = truck.get_next_site();
                        }
                        if (!aborted) {
                            Truck_Driver driver = truck.getCurrent_driver();
                            driver.setCurrent_truck(null);
                            truck.setCurrent_driver(null);
                            truck.setOccupied(false);
                            System.out.println("Transport " + chosen_transport.getTransport_ID() + " now finished.");
                        }
                    }
                    choosing = true;
                    break;

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
        // ======================== Supplier ID ======================== //
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
        String input = null;
        int site_supplier_ID = 0;
        Truck_Driver truck_driver = null;
        while(!isValid){
            System.out.println("Please enter the site supply ID number (5 digits, only with the digits 0-9): ");
            input = scanner.nextLine();
            if(input.strip().equals("")){
                System.out.print("Invalid input. ");
            }
            else {
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
                for (Site_Supply driver_doc : truck_driver.getSites_documents()) {
                    if (driver_doc.getId() == site_supplier_ID) {
                        isValid = false;
                        System.out.print("This site supply ID number is already exist in this transport.");
                    }
                }

                for (Store store : logistical_center.getDelivered_supplies_documents().keySet()) {
                    ArrayList<Site_Supply> site_supplies = this.logistical_center.getDelivered_supplies_documents().get(store);
                    for (Site_Supply site_supply: site_supplies) {
                        if (site_supply.getId() == site_supplier_ID) {
                            isValid = false;
                            System.out.print("This site supply ID number is already exist in the system.");
                        }
                    }
                }
            }
        }
        // ======================== Store Address ======================== //
        String store_name = null;
        isValid = false;
        while (!isValid) {
            System.out.println("Please enter the store name: ");
            store_name = scanner.nextLine();
            if(store_name.strip().equals("")){
                System.out.println("Invalid input. ");
            }
            else {
                for (Site site : transport.getDestinations()) {
                    if (site.getSite_name().equals(store_name)) {
                        if (site.is_store()) {
                            isValid = true;
                        } else {
                            System.out.println("The name is not belong to store. ");
                        }
                        break;
                    }
                }
                if (!isValid) {
                    System.out.print("This address is not part of this transport. ");
                }
            }
        }
        // ======================== Store As Destination ======================== //
        Store store = transport.getStoreByName(store_name);
        // ======================== Create Site Supply Document ======================== //
        Site_Supply site_supply_doc = new Site_Supply(site_supplier_ID, store, supplier_address);
        // ======================== Insert Items ======================== //
        isValid = false;
        while(!isValid) {
            String item_name = "";
            while (item_name.equals("")) {
                System.out.println("Please enter the name of the item: ");
                item_name = scanner.nextLine();
            }
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
        double items_weight = 0.0;
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
        // ======================== Insert Weight To Site Supply Document ======================== //
        site_supply_doc.setProducts_total_weight(items_weight);
        // ======================== Insert Weight To Truck ======================== //
        Truck truck = getTruckByNumber(transport.getTruck_number());
        truck.addWeight(items_weight);
        // ======================== Add The Site Supply Document To Truck Driver ======================== //
        truck_driver.Add_site_document(site_supply_doc);
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
        String input = null;
        // ======================== Transport ID ======================== //
        int transport_Id = 0;
        boolean isValid = false;
        // getting the transport's ID number.
        while (!isValid) {
            System.out.println("Please enter transport ID number (5 digits, only with the digits 0-9): ");
            input = scanner.nextLine();
            if(input.strip().equals("")){
                System.out.print("Invalid input. ");
            }
            else {
                try {
                    transport_Id = Integer.parseInt(input);
                    // Check if the input is a 5 digit integer
                    if (input.length() == 5) {
                        isValid = true;
                    } else {
                        System.out.print("Input must be a 5 digit integer. ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. ");
                }
                Transport transport = logistical_center.getTransport_Log().get(transport_Id);
                if (transport != null) {
                    isValid = false;
                    System.out.println("The transport ID number is already exist in the transports system. ");
                }
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
        if (truck == null){
            System.out.println("there's no Truck fit to this transport.");
            return null;
        }
        String truck_number = truck.getRegistration_plate();
        // ======================== Truck Driver ======================== //
        String driver_name = null;
        boolean assigned = false;
        for(Truck_Driver driver: logistical_center.getDrivers()){
            if(truck_assigning(driver.getID(), truck.getRegistration_plate())){
                assigned = true;
                driver_name = driver.getName();
                driver.setCurrent_truck(truck);
                truck.setCurrent_driver(driver);
                break;
            }
        }
        if (!assigned){
            System.out.println("there's no driver fit to this transport.");
            return null;
        }
        // ======================== Create Transport Document ======================== //
        cold_level cold_level = truck.getCold_level();
        Transport transport_doc = new Transport(transport_Id, "TBD", "TBD", truck_number, driver_name, logistical_center.getSite_name(), cold_level);
        // ======================== Add Destinations ======================== //
        boolean stop_adding_destinations = false;
        Site destination;
        boolean at_least_one_store = false;
        boolean at_least_one_supplier = false;
        boolean areaValid = false;
        int area = 0;
        System.out.println("Please enter the destinations for this current transport.");
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
                if(!areaValid) {
                    isValid = false;
                    while (!isValid) {
                        try {
                            System.out.println("Please enter the area of the stores in this transport (one digit 0-9 number): ");
                            input = scanner.nextLine();
                            area = Integer.parseInt(input);
                            // Check if the input is one digit integer
                            if (input.length() == 1) {
                                isValid = true;
                                areaValid = true;
                            } else {
                                System.out.print("Input must be only one digit integer. ");
                            }
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input. ");
                        }
                    }
                }
                isValid = false;
                String store_address = null;
                while(!isValid) {
                    System.out.println("Please enter the store address: ");
                    input = scanner.nextLine();
                    if (input.strip().equals("")) {
                        System.out.print("Invalid input. ");
                    }
                    else{
                        store_address = input;
                        isValid = true;
                    }
                }
                String phone_number = null;
                isValid = false;
                while(!isValid) {
                    System.out.println("Please enter the store phone number: ");
                    phone_number = scanner.nextLine();
                    if(!containsOnlyNumbers(phone_number) || phone_number.strip().equals("")){
                        System.out.print("Invalid input. ");
                    }
                    else{
                        isValid = true;
                    }
                }
                isValid = false;
                String store_name = null;
                while (!isValid) {
                    System.out.println("Please enter the name of the store: ");
                    store_name = scanner.nextLine();
                    if(!store_name.strip().equals("")){
                        boolean name_exist = false;
                        for (Site site: transport_doc.getDestinations()){
                            if (site.getSite_name().equals(store_name)){
                                System.out.println("This store name is already in the system. please enter a new store name.");
                                name_exist = true;
                            }
                        }
                        if (!name_exist) {
                            isValid = true;
                        }
                    }
                    else {
                        System.out.print("Invalid input. ");
                    }
                }
                isValid = false;
                String store_contact_name = null;
                while (!isValid) {
                    System.out.println("Please enter the contact person name of the store: ");
                    store_contact_name = scanner.nextLine();
                    if(!store_contact_name.strip().equals("")){
                        isValid = true;
                    }
                    else {
                        System.out.print("Invalid input. ");
                    }
                }
                isValid = false;
                String manager_name = null;
                while (!isValid) {
                    System.out.println("Please enter the manager name of the store: ");
                    manager_name = scanner.nextLine();
                    if(!manager_name.strip().equals("")){
                        isValid = true;
                    }
                    else {
                        System.out.print("Invalid input. ");
                    }
                }
                destination = new Store(store_address, phone_number, store_name, manager_name, area, store_contact_name);
                transport_doc.insertToDestinations(destination);
                at_least_one_store = true;
            }
            if(site_type.equals("2")){
                isValid = false;
                String supplier_address = null;
                while(!isValid) {
                    System.out.println("Please enter the supplier address: ");
                    supplier_address = scanner.nextLine();
                    if(!supplier_address.strip().equals("")){
                        isValid = true;
                    }
                    else{
                        System.out.print("Invalid input. ");
                    }
                }
                String phone_number = null;
                isValid = false;
                while(!isValid) {
                    System.out.println("Please enter the supplier phone number: ");
                    phone_number = scanner.nextLine();
                    if(!containsOnlyNumbers(phone_number) || phone_number.strip().equals("")){
                        System.out.print("Invalid input. ");
                    }
                    else{
                        isValid = true;
                    }
                }
                isValid = false;
                String supplier_contact_name = null;
                while(!isValid) {
                    System.out.println("Please enter the contact person name of the supplier:");
                    supplier_contact_name = scanner.nextLine();
                    if(!supplier_contact_name.strip().equals("")){
                        isValid = true;
                    }
                    else {
                        System.out.print("Invalid input. ");
                    }
                }
                isValid = false;
                String supplier_name = null;
                while(!isValid) {
                    System.out.println("Please enter the name of the supplier: ");
                    supplier_name = scanner.nextLine();
                    if(!supplier_name.strip().equals("")){
                        boolean name_exist = false;
                        for (Site site: transport_doc.getDestinations()){
                            if (site.getSite_name().equals(supplier_name)){
                                System.out.println("This supplier name is already in the system. please enter a new supplier name.");
                                name_exist = true;
                            }
                        }
                        if (!name_exist) {
                            isValid = true;
                        }
                    }
                    else{
                        System.out.print("Invalid input. ");
                    }
                }

                destination = new Supplier(supplier_address, phone_number, supplier_name, supplier_contact_name);
                transport_doc.insertToDestinations(destination);
                at_least_one_supplier = true;
            }
            System.out.println("Do you want to add another destination (Store / Supplier)? (press 1 or 2 only) ");
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
                if (at_least_one_store && at_least_one_supplier) {
                    stop_adding_destinations = true;
                }
                else {
                    System.out.println("You must add at least one supplier and at least one store.");
                }
            }
        }
        // ======================== Add Transport Document ======================== //
        logistical_center.getTransport_Log().put(transport_Id, transport_doc);
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
        for (int i = 0; i< driver.getSites_documents().size(); i++){
            if (driver.getSites_documents().get(i).getStore().getAddress().equals(store.getAddress())){
                unloaded = true;
                if (logistical_center.getDelivered_supplies_documents().containsKey(store)) {
                    ArrayList<Site_Supply> site_supplies= logistical_center.getDelivered_supplies_documents().get(store);
                    site_supplies.add(driver.getSites_documents().get(i));
                }
                else {
                    ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
                    siteSupplies.add(driver.getSites_documents().get(i));
                    logistical_center.getDelivered_supplies_documents().put(store, siteSupplies);
                }
                // subtracts the weight of the goods that was unloaded
                truck.addWeight(-1 * driver.getSites_documents().get(i).getProducts_total_weight());
                // change to delete only one site.
                driver.delete_site_document_by_ID(driver.getSites_documents().get(i).getId());
                i--;
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
