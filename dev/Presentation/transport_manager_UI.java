package Presentation;

import Business.controllers.Logistical_center_controller;
import Business.objects.*;

import java.util.Scanner;

public class transport_manager_UI {
    Logistical_center_controller controller;
    Scanner scanner;
    public transport_manager_UI() {
        controller = new Logistical_center_controller();
        scanner = new Scanner(System.in);
    }

    public void start() {
        if (controller.getLogistical_center() == null) {
            get_logistical_center();
        }
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
            }

        }

    }

    public boolean containsOnlyNumbers (String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
                for (Truck_Driver truck_driver : controller.getLogistical_center().getDrivers()) {
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
                for (Truck_Driver truck_driver : controller.getLogistical_center().getDrivers()) {
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
        controller.add_driver(ID, Driver_name, License_ID, level, weight);

    }
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

    private void display_trucks_by_cold_level(){
        cold_level cool_level = null;
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
            case "1" -> cool_level = cold_level.Freeze;
            case "2" -> cool_level = cold_level.Cold;
            case "3" -> cool_level = cold_level.Dry;
        }
        for (Truck t : controller.getLogistical_center().getTrucks()) {
            if (t.getCold_level().getValue() == cool_level.getValue()) {
                System.out.println(t.getRegistration_plate());
            }
        }
    }

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
                Transport transport = controller.get_transport_by_id(transport_Id);
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
        Truck truck = controller.getTruckByColdLevel(cool_level);
        if (truck == null){
            System.out.println("there's no Truck fit to this transport.");
            return;
        }
        String truck_number = truck.getRegistration_plate();
        // ======================== Truck Driver ======================== //
        String driver_name = null;
        boolean assigned = false;
        for(Truck_Driver driver: controller.getDrivers()){
            if(controller.truck_assigning(driver.getID(), truck.getRegistration_plate())){
                assigned = true;
                driver_name = driver.getName();
                driver.setCurrent_truck(truck);
                truck.setCurrent_driver(driver);
                break;
            }
        }
        if (!assigned){
            System.out.println("there's no driver fit to this transport.");
            return;
        }
        // ======================== Create Transport Document ======================== //
        cold_level cold_level = truck.getCold_level();
        controller.add_transport(transport_Id, truck_number, driver_name,  cold_level);
        // ======================== Update Weight - Add Net Weight Of The Truck To Transport Weight List ======================== //
        controller.insert_weight_to_transport(transport_Id, truck.getNet_weight());
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


}
