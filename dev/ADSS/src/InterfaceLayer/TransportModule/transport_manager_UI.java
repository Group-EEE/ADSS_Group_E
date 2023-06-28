package InterfaceLayer.TransportModule;

import BussinessLayer.HRModule.Controllers.ScheduleController;
import BussinessLayer.HRModule.Objects.ShiftType;
import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.objects.Transport;
import BussinessLayer.TransportationModule.objects.Truck_Driver;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.HRMoudle.StoresDAO;
import DataAccessLayer.Transport.Suppliers_dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private DateTimeFormatter formatter;

    Scanner scanner;
    public transport_manager_UI() {
        this.controller = Logistical_center_controller.getInstance();
        this.underway_transport_ui = new underway_transport_UI();
        this.scanner = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * The main function that runs the menu for the transport manager.
     */
    // ===== Main menu =====
    public void start() {
        int choice = 0;
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
        String input;
        isValid = false;
        while (true) {
            System.out.println("Hey Boss! what would you like to do?");
            System.out.println("0 - register a supplier to the System");
            System.out.println("1 - See all the trucks with a cold level of your choice: \n\t 1- Freeze \n\t 2- Cold \n \t 3- Dry");
            System.out.println("2 - create a new transport");
            System.out.println("3 - send transports");
            System.out.println("4 - Add new truck to the system");
            System.out.println("5 - Display all drivers in the system");
            System.out.println("6 - Display all trucks in the system");
            System.out.println("7 - Display all transport documents in the system");
            System.out.println("8 - Display all site supplies documents in the system");
            System.out.println("9 - Display all stores in the system");
            System.out.println("10 - Display all suppliers in the system");
            System.out.println("11 - Add standby driver to existing schedule by date.");
            System.out.println("12 - quit");
            while (!isValid) {
                try {
                    input = scanner.nextLine();
                    choice = Integer.parseInt(input);

                    // Check if the input is a 5 digit integer
                    if (input.length() <= 2 && choice >= 0 && choice < 13) {
                        isValid = true;
                    } else {
                        System.out.println("Input must be an int between 0-12. ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid int between 0-14. ");
                }
            }
            isValid = false;
            switch (choice) {
                case 0 -> create_supplier();

                // have all the trucks by a cold level
                case 1 -> display_trucks_by_cold_level();

                // creating a new transport
                case 2 -> {
                    System.out.println("Hey Boss!");
                    create_transport_document();
                }
                case 3 -> {
                    if (ScheduleController.getInstance().hasSchedule("Logistics")) {
                        ArrayList<Integer> chosen_transports = choose_transport_to_send();
                        for (int key : chosen_transports) {
                            underway_transport_ui.start_transport(key);
                        }
                    }
                    else {
                        // write here we can't
                        System.out.println("Currently the HR manager haven't built a schedule for the drivers.");
                    }
                }
                // add a new truck to the system.
                case 4 -> create_truck();
                case 5 -> controller.display_drivers();
                case 6 -> controller.display_trucks();
                case 7 -> controller.display_transport_doc();
                case 8 -> controller.display_site_supply();
                case 9 -> controller.display_stores();
                case 10 -> controller.display_suppliers();
                case 11 -> {
                    if (ScheduleController.getInstance().hasSchedule("Logistics")) {
                        add_standby_driver();
                    }
                    else {
                        System.out.println("Currently the HR manager haven't built a schedule for the drivers.");
                    }
                }
                case 12 -> {
                    return;
                }
            }

        }
    }

    /**
     * @param str some string
     * @return is the string contains numbers only
     */
    public boolean containsOnlyNumbers(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * asking from the user details about the logistical center and create it in the controller.
     */
    private void create_logistical_center() {
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
    private boolean create_transport_document(){
        if (!StoresDAO.getInstance().isAnyStoreExist()){
            System.out.println("There's no stores in the Database!");
            return false;
        }
        if (!Suppliers_dao.getInstance().is_any_supplier_exist()){
            System.out.println("There's no suppliers in the Database!");
            return false;
        }
        boolean is_today = false;
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

        // ==================== getting the planned date for the transport ================
        int currentYear = LocalDate.now().getYear();
        LocalDate currentDate = LocalDate.now();
        String inputDate = "";
        boolean validInput = false;
        String planned_date = "";
        LocalDate date = null;
        while (!validInput) {
            System.out.print("Please enter the date you want to send the transport in the format dd/mm (please note that you can make transports for the next week only) : ");
            inputDate = scanner.nextLine();
            // Check if the input matches the expected format
            if (inputDate.matches("\\d{2}/\\d{2}")) {
                planned_date = inputDate + "/" + currentYear;
                try {
                    date = LocalDate.parse(planned_date, formatter);
                    // Check if the parsed date is not before the current date and not more than one week from the current date
                    if (!date.isBefore(currentDate) && !date.isAfter(currentDate.plusWeeks(1))) {
//                        if (currentDate.equals(date)) {
//                            int shift_id = ScheduleController.getInstance().getShiftIDByDate("Logistics", date, ShiftType.MORNING);
//                            if (!SchedulesDAO.getInstance().getSchedule(currentDate, "Logistics").getShift(shift_id).isApproved() || !SchedulesDAO.getInstance().getSchedule(currentDate, "Logistics").getShift(shift_id + 1).isApproved()) {
//                                System.out.println("There's no shifts approved for the current date.");
//                                return false;
//                            }
//                            is_today = true;
//                        }
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. The date must not be before the current date and not more than one week from the current date.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid input. Please enter a date in the format dd/mm.");
                }
            } else {
                System.out.println("Invalid input. Please enter a date in the format dd/mm.");
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
        if (!controller.check_if_truck_exist_by_cold_level(cool_level, planned_date)){
            System.out.println("there's no Truck fit to this transport.");
            return false;
        }
        String truck_number = controller.get_truck_number_by_cold_level(cool_level, planned_date);
        // ======================== Truck Driver ======================== //
        String driver_name = null;
        int driver_id = 0;
        boolean assigned = false;
        for(Truck_Driver driver: controller.getDrivers()){
//            if (is_today) {
//                if(controller.truck_assigning_drivers_in_shift(truck_number, planned_date)){
//                    assigned = true;
//                    driver_name = driver.getFullName();
//                    driver_id = driver.getEmployeeID();
//                    break;
//                }
//            }
            //else {
                if (controller.truck_assigning(truck_number, planned_date, driver)) {
                    assigned = true;
                    driver_name = driver.getFullName();
                    driver_id = driver.getEmployeeID();
                    break;
                }
            //}
        }
        if (!assigned){
            System.out.println("there's no driver fit to this transport.");
            return false;
        }

        // ======================== Create Transport Document ======================== //
        controller.add_transport(transport_Id, truck_number, driver_name,  cool_level, planned_date , driver_id);
        // ======================== Update Weight - Add Net Weight Of The Truck To Transport Weight List ======================== //
        controller.insert_weight_to_transport(transport_Id, truck_number);
        // ======================== Add Destinations ======================== //
        boolean stop_adding_destinations = false;
        boolean at_least_one_store = false;
        boolean at_least_one_supplier = false;
        boolean areaValid = false;
        int area = 0;
        System.out.println("Please enter the destinations for this current transport.");
        String stores = "";
        String suppliers = "";
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
                String store_name = null;
                boolean chose_to_add = true;
                while (!isValid) {
                    System.out.println("Please enter the name of the store (if you don't want to add more press 0) :");
                    store_name = scanner.nextLine();
                    if (store_name.equals("0")){chose_to_add = false; break;}
                    if(!store_name.strip().equals("")){
                        boolean store_exist = StoresDAO.getInstance().existsStore(store_name);
                        if (!store_exist){
                            System.out.println("The store is not known to the system...");
                            continue;
                        }

                        if (!StoresDAO.getInstance().isStoreInArea(store_name, area)){
                            System.out.println("This store is not in the area of this transport...");
                        }

                        boolean name_exist = controller.check_if_site_exist_in_transport(store_name, transport_Id);
                        if (name_exist) {
                            System.out.println("You've already added that store...");
                            continue;
                        }
                        stores += store_name + ",";
                        isValid = true;
                    }
                    else {
                        System.out.print("Invalid input. ");
                    }
                }
                if (chose_to_add) {
                    controller.insert_store_to_transport(transport_Id,  store_name);
                    at_least_one_store = true;
                }
            }
            if(site_type.equals("2")){
                isValid = false;
                boolean chose_to_add = true;
                String supplier_name = null;
                while(!isValid) {
                    System.out.println("Please enter the name of the supplier (if you don't want to add more press 0) : ");
                    supplier_name = scanner.nextLine();
                    if (supplier_name.equals("0")){chose_to_add = false; break;}
                    if(!supplier_name.strip().equals("")){
                        if (!Suppliers_dao.getInstance().is_supplier_exist(supplier_name)){
                            System.out.println("This supplier is not known to the system...");
                        }
                        boolean name_exist = controller.check_if_site_exist_in_transport(supplier_name, transport_Id);
                        if (name_exist) {
                            System.out.println("You've already entered this supplier...");
                        }
                        suppliers += supplier_name + ",";
                        isValid = true;
                    }
                    else{
                        System.out.print("Invalid input. ");
                    }
                }
                if (chose_to_add) {
                    controller.insert_supplier_to_transport(transport_Id, supplier_name);
                    at_least_one_supplier = true;
                }
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
        controller.insert_sites_names_to_transport(transport_Id, stores, suppliers);
        for (String store : stores.split(",")){
            int shift_id = ScheduleController.getInstance().getShiftIDByDate(store, date, ShiftType.MORNING);
            ScheduleController.getInstance().addMustBeFilledWareHouse(store, shift_id);
            ScheduleController.getInstance().addMustBeFilledWareHouse(store, shift_id+1);
        }
        return true;
    }

    /**
     * @return an array with all the transports ID the manager (user) chose to send.
     */

    ///////////////// need to make sure when the manager can send!!!
    public ArrayList<Integer> choose_transport_to_send(){
        String manager_choice;
        ArrayList<Integer> transport_IDS = new ArrayList<>();
        boolean choosing = false;
        while (!choosing) {
            System.out.println("Hey Boss, which transport you want to send?");
            System.out.println("1 - display all the transports that haven't started:");
            System.out.println("2 - send transport by enter his ID:");
            System.out.println("3- that's all for now.");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            String date_today = currentDate.format(formatter);
            manager_choice = scanner.nextLine();
            if (manager_choice.equals("1")) {
                for (Map.Entry<Integer, Transport> entry : controller.getTransport_Log().entrySet()) {
                    if (!entry.getValue().Started() && entry.getValue().getPlanned_date().equals(date_today)) {
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

                        if (controller.can_send_the_transport(key, date_today)) {
                            controller.set_started_transport(key);
                            transport_IDS.add(key);
                            System.out.println("Transport " + key + " will start soon. If you want to send another one please press 1, to get back to transports sending menu press anything else:");
                            String inp1 = scanner.nextLine();
                            if (!inp1.equals("1")) {
                                end_choosing = true;
                            }
                        } else {
                            System.out.println("If you don't want to continue press 1, otherwise press anything else:");
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
            if(!controller.is_truck_exist(input)){
                registration_number = input;
                isValid = true;
            }
            else{
                System.out.println("The Truck is already exist in the system! ");
                break;
            }
            // ======================== Truck model ======================== //
            System.out.println("Please enter the truck model: ");
            String truck_model = scanner.nextLine();
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
            controller.add_truck(registration_number, truck_model, truck_net_weight, truck_max_weight, cool_level ,truck_net_weight);
        }
    }

    public void create_supplier(){
        boolean isValid = false;
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
        String supplier_name = "";
        isValid = false;
        while (isValid){
            System.out.println("Please enter the name of the supplier:");
            supplier_name = scanner.nextLine();
            if(!supplier_name.strip().equals("")){
                if (Suppliers_dao.getInstance().is_supplier_exist(supplier_name)){
                    System.out.println("This supplier is already known to the system...");
                    continue;
                }
            }
            else {
                System.out.println("Please enter a valid name.");
                continue;
            }
            isValid = true;
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
        controller.add_supplier(supplier_address, phone_number, supplier_name, supplier_contact_name);
    }

    public void add_standby_driver(){
        String input = null;
        LocalDate date = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate first_day_in_week = currentDate.plusDays(1);
        String firstDate_str = first_day_in_week.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate lastDate = currentDate.plusWeeks(1);
        String lastDate_str = lastDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        boolean isValidDate = false;
        while(!isValidDate) {
            System.out.print("Please enter a date between " + firstDate_str + " and " + lastDate_str + " only in dd/MM/yyyy format: (Press 0 for exit) ");
            input = scanner.nextLine();
            if(input.equals("0")) {
                return;
            }
            try {
                date = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. try again. ");
            }
            if (date.equals(LocalDate.now())) {
                System.out.println("Invalid input. it is not possible to add standby driver in this day. ");
            } else if (date.isBefore(currentDate)) {
                System.out.println("Invalid input. date can be only after the current date, try again. ");
            } else if (date.isAfter(lastDate)) {
                System.out.println("Invalid input. date can not be only after the last date in the schedule, try again. ");
            } else {
                controller.add_standby_driver_by_date(date);
                isValidDate = true;
            }
        }
    }
}
