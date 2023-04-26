package Presentation;

import Business.controllers.Logistical_center_controller;
import Business.objects.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * class that present the main menu
 */
public class transport_manager_UI {
    // ===== attributes =====
    Logistical_center_controller controller;
    underway_transport_UI underway_transport_ui;
    Scanner scanner;
    public transport_manager_UI() {
        this.controller = Logistical_center_controller.getInstance();
        this.underway_transport_ui = new underway_transport_UI();
        this.scanner = new Scanner(System.in);
    }

    /**
     * The main function that runs the menu for the transport manager.
     */
    // ===== Main menu =====
    public void start() {
        if (controller.getLogistical_center() == null) {
            get_logistical_center();
        }
        int choice = 0;
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
        String input;
        int inp = 0;
        while (!isValid) {
            System.out.println("Please choose - 1 for ready database and 2 for empty.");
            input = scanner.nextLine();
            if(input.equals("1")){
                inp = 1;
                isValid = true;
            } else if (input.equals("2")) {
                inp = 2;
                isValid =true;
            }
            else {
                System.out.println("Please enter 1 or 2");
            }

        }
        if (inp == 1) {
            controller.load_database();
        }

        isValid = false;
        input = null;
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
            while (!isValid) {
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
            switch (choice) {
                // hire a new driver
                case 0:
                    create_driver();
                    break;
                // have all the trucks by a cold level
                case 1:
                    display_trucks_by_cold_level();
                    break;
                // creating a new transport
                case 2:
                    System.out.println("Hey Boss!");
                    create_transport_document();
                    break;
                case 3:
                    ArrayList<Integer> chosen_transports = choose_transport_to_send();
                    for (int key: chosen_transports){
                        underway_transport_ui.start_transport(key);
                    }
                    break;
                // add a new truck to the system.
                case 4:
                    create_truck();
                    break;
                case 5:
                    controller.display_drivers();
                    break;
                case 6:
                    controller.display_trucks();
                    break;
                case 7:
                    controller.display_transport_doc();
                    break;
                case 8:
                    controller.display_site_supply();
                    break;
                case 9:
                    return;
                                }

        }
    }

    /**
     * @param str some string
     * @return is the string contains numbers only
     */
    public boolean containsOnlyNumbers (String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * a function that asks from the user all the details for a truck driver and then add it to the database.
     */
    private void create_driver(){
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
                 if(controller.check_if_driver_Id_exist(ID)){
                 isValid = false;
                 System.out.println("The ID number - " + ID + " - is already belong to some driver in the transport system. Please enter a valid 9 digit integer: ");
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
                 if(controller.check_if_license_id_exist(License_ID)){
                 isValid = false;
                 System.out.println("The license ID number - " + License_ID + " - is already belong to some driver in the transport system. Please enter a valid 5 digit integer: ");
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
        controller.add_driver(ID, Driver_name, License_ID, level, weight);

    }

    /**
     * asking from the user details about the logistical center and create it in the controller.
     */
    private void get_logistical_center() {
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
            } else {
                origin_address = input;
                isValid = true;
            }
        }
        String origin_phone = null;
        isValid = false;
        while (!isValid) {
            System.out.println("Please enter the logistical center phone number (only digits 0-9): ");
            origin_phone = scanner.nextLine();
            if (!containsOnlyNumbers(origin_phone) || origin_phone.strip().equals("")) {
                System.out.print("Invalid input. ");
            } else {
                isValid = true;
            }
        }
        String origin_name = "Logistical Center";
        isValid = false;
        String origin_contact_name = null;
        while (!isValid) {
            System.out.println("Please enter the contact person name of the logistical center: ");
            origin_contact_name = scanner.nextLine();
            if (!origin_contact_name.strip().equals("")) {
                isValid = true;
            } else {
                System.out.print("Invalid input. ");
            }
        }
        controller.create_Logistical_Center(origin_address, origin_phone, origin_name, origin_contact_name);
    }

    /**
     * display trucks by cold level that the user choose
     */
    private void display_trucks_by_cold_level(){
        String cool_level = null;
        boolean isValid2 = false;
        String input = "";
        while (!isValid2) {
            System.out.println("Please enter the required cold level of the truck (press 1, 2 or 3 only): ");
            System.out.println("1 - Freeze");
            System.out.println("2 - Cold");
            System.out.println("3 -  Dry");
            input = scanner.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                isValid2 = true;
            } else {
                System.out.print("Invalid input. ");
            }
        }
        switch (input) {
            case "1" -> cool_level = "Freeze";
            case "2" -> cool_level = "Cold";
            case "3" -> cool_level = "Dry";
        }
        controller.display_trucks_by_cold_level(cool_level);
    }

    /**
     * creates transport document getting the details from the manager
     */
    private void create_transport_document(){
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
                 if(controller.check_if_transport_id_exist(transport_Id)){
                 isValid = false;
                 System.out.println("The transport ID number is already exist in the transport system. ");
                }
            }
        }
        // ======================== Truck ======================== //
        String cool_level = null;
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
            case "1" -> cool_level = "Freeze";
            case "2" -> cool_level = "Cold";
            case "3" -> cool_level = "Dry";
        }
        if (!controller.check_if_truck_exist_by_cold_level(cool_level)){
            System.out.println("there's no Truck fit to this transport.");
            return;
        }
        String truck_number = controller.get_truck_number_by_cold_level(cool_level);
        // ======================== Truck Driver ======================== //
        String driver_name = null;
        boolean assigned = false;
        for(Truck_Driver driver: controller.getDrivers()){
            if(controller.truck_assigning(truck_number)){
                assigned = true;
                driver_name = driver.getName();
                break;
            }
        }
        if (!assigned){
            System.out.println("there's no driver fit to this transport.");
            return;
        }
        // ======================== Create Transport Document ======================== //
        controller.add_transport(transport_Id, truck_number, driver_name,  cool_level);
        // ======================== Update Weight - Add Net Weight Of The Truck To Transport Weight List ======================== //
        controller.insert_weight_to_transport(transport_Id, truck_number);
        // ======================== Add Destinations ======================== //
        boolean stop_adding_destinations = false;
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
                        boolean name_exist = controller.check_if_site_exist_in_transport(store_name, transport_Id);
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
                controller.insert_store_to_transport(transport_Id, store_address, phone_number, store_name, manager_name, area, store_contact_name);
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
                        boolean name_exist = controller.check_if_site_exist_in_transport(supplier_name, transport_Id);;
                        if (!name_exist) {
                            isValid = true;
                        }
                    }
                    else{
                        System.out.print("Invalid input. ");
                    }
                }
                controller.insert_supplier_to_transport(transport_Id, supplier_address, phone_number, supplier_name, supplier_contact_name);
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
    }

    /**
     * @return an array with all the transports ID the manager (user) chose to send.
     */
    public ArrayList<Integer> choose_transport_to_send(){
        String manager_choice;
        ArrayList<Integer> transport_IDS = new ArrayList<>();
        boolean choosing = false;
        while (!choosing) {
            System.out.println("Hey Boss, which transport you want to send?");
            System.out.println("1 - display all the transports that haven't started:");
            System.out.println("2 - send transport by enter his ID:");
            System.out.println("3- that's all for now.");
            manager_choice = scanner.nextLine();
            if (manager_choice.equals("1")) {
                for (Map.Entry<Integer, Transport> entry : controller.getTransport_Log().entrySet()) {
                    if (!entry.getValue().Started()) {
                        int t_id = entry.getKey();
                        Transport temp_transport = entry.getValue();
                        System.out.println("=========== Transport - " + t_id + " - information ===========");
                        temp_transport.transportDisplay();
                    }
                }
            } else if (manager_choice.equals("2")) {
                int key = 0;
                boolean end_choosing = false;
                while (!end_choosing) {
                    if (!controller.at_least_one_unsent_transport()) {
                        System.out.println("We don't have any registered transport that haven't started yet....");
                        break;
                    }
                    System.out.println("Please enter the transport ID you want to start:");
                    try {
                        String key_input = scanner.nextLine();
                        key = Integer.parseInt(key_input);
                        if (controller.getTransport_Log().containsKey(key)) {
                            controller.set_started_transport(key);
                            transport_IDS.add(key);
                            System.out.println("Transport " + key + " will start soon. If you want to send another one please press 1, to get back to transports sending menu press anything else:");
                            String inp1 = scanner.nextLine();
                            if (!inp1.equals("1")) {
                                end_choosing = true;
                            }
                        } else {
                            System.out.println("Transport ID does not exist. If you don't want to continue press 1, otherwise press anything else:");
                            String inp2 = scanner.nextLine();
                            if (inp2.equals("1")) {
                                end_choosing = true;
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid 5 digit integer.");
                    }
                }
            } else if (manager_choice.equals("3")) {
                choosing = true;
            } else {
                System.out.println("Invalid input.");
            }
        }
        return transport_IDS;
    }



    /**
     * a function that asks from the user all the details for a new truck and then add it to the database.
     */
    public void create_truck(){
        Scanner scanner = new Scanner(System.in);
        String input = null;
        boolean isValid = false;
        // ======================== Registration Number ======================== //
        String registration_number = null;
        while(!isValid){
            System.out.println("Please enter the registration number of the truck (8 digits, only with the digits 0-9): ");
            input = scanner.nextLine();
            if(!containsOnlyNumbers(input)){
                System.out.println("Invalid input. ");
                continue;
            }
            else if (input.length() != 8) {
                System.out.println("Input must be a 8 digit integer. ");
                continue;
            }
            // checking if the truck already exist in the system
            if(controller.is_truck_exist(input)){
                registration_number = input;
                isValid = true;
            }
            else{
                System.out.println("The Truck is already exist in the system! ");
                break;
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
        controller.add_truck(registration_number, truck_moodle, truck_net_weight, truck_max_weight, cool_level ,truck_net_weight);
    }



}
