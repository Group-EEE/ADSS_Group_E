package InterfaceLayer.TransportModule;

import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import BussinessLayer.TransportationModule.controllers.underway_transport_controller;
import BussinessLayer.TransportationModule.objects.Logistical_Center;
import InterfaceLayer.TransportModule.GUI.Send_transport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class underway_transport_UI {
    // ===== attributes =====
    private final underway_transport_controller controller;
    Logistical_center_controller  logistical_center_controller;
    private final Scanner scanner;
    Logistical_Center logistical_center;
    public underway_transport_UI(){
        this.controller = underway_transport_controller.getInstance();
        this.scanner = new Scanner(System.in);
        this.logistical_center = Logistical_center_controller.getInstance().getLogistical_center();
        logistical_center_controller = Logistical_center_controller.getInstance();
    }

    /**
     * @param transport_ID the transport's id that the user chose to send.
     *                     the function send the transport to his way.
     */
    // ===== Starting the transport =====
    public void start_transport(int transport_ID){
        boolean aborted = false;
        // ======================== get Date And Time ======================== //
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String Date = now.toLocalDate().format(dateFormatter);
        String Time = now.toLocalTime().format(timeFormatter);
        // ======================== check if the transport is in the right status ========================
        // check_if_warehouse_worker_exist_in_all_stores(transport_ID) - need to implement the right functions with Chen
        if (!controller.check_if_warehouse_worker_exist_in_all_stores(transport_ID, today)){
            System.out.println("Transport cancelled, there's no warehouse worker in all of the stores.");
            return;
        }
        // ======================== Update Date And Time ======================== //
        controller.match_driver_and_truck(transport_ID);
        controller.set_time_and_date_for_transport(transport_ID, Date, Time);
        // ======================== Estimated End Time ======================== //
        controller.getRandomTimeAfter(Time, transport_ID);
        // ========================= starting the transport ======================= //
        controller.set_navigator_for_transport(transport_ID);
        controller.insert_weight_to_transport(transport_ID);
        System.out.println("Transport - " + transport_ID + " started.");
        controller.drive_to_next_location(transport_ID);
        while (controller.is_current_location_not_null(transport_ID)) {
            if (!controller.is_current_location_is_store(transport_ID)) {
                boolean isValidChoice = false;
                String ch = null;
                System.out.println("Hey " + controller.get_current_location_name(transport_ID) + " manager!");
                while (!isValidChoice) {
                    // creating a document
                    create_site_supply(transport_ID);
                    // asking if he needs to make another one
                    System.out.println("Do you have items to ship to another store? (press 1 or 2 only): ");
                    System.out.println("1 - YES");
                    System.out.println("2 - NO");
                    while (true) {
                        ch = scanner.nextLine();
                        if (ch.equals("1")) {
                            break;
                        } else if (ch.equals("2")) {
                            isValidChoice = true;
                            break;
                        } else {
                            System.out.println("You must enter 1 or 2.");
                        }
                    }
                }
                //checking the weight
                if (!controller.check_weight(transport_ID)) {
                    System.out.println("Alert! the truck is in overweight!");
                    boolean abort_transport = !change_transport(transport_ID);
                    if (abort_transport) {
                        // aborting the transport and resets the details of the driver and the truck.
                        System.out.println("Transport was aborted. you can try to send it later");
                        controller.reset_transport(transport_ID, false);
                        return;
                    }
                }
            }
            // unloading the goods in the store
            else if (controller.is_current_location_is_store(transport_ID)) {
                if (controller.unload_goods(transport_ID)) {
                    System.out.println("goods unloaded in " + controller.get_current_location_name(transport_ID));
                } else {
                    System.out.println("We currently don't have any goods for " + controller.get_current_location_name(transport_ID) + " ,skip this store for now.");
                }
            }
            check_for_estimated_finish_time_update(transport_ID);
            // driving to the next site.
            controller.drive_to_next_location(transport_ID);
        }
        // if the transport wasn't aborted, we update the truck and the driver, so they can now go to another shipment.
        if (!aborted) {
            controller.reset_transport(transport_ID, true);
            System.out.println("Transport " + transport_ID + " now finished.");
        }
    }

    /**
     * @param transport_id transport id
     *                ask from the supplier details to create a site supply document and give it to the driver
     */
    private void create_site_supply(int transport_id) {
        // ======================== Supplier ID ======================== //
        boolean isValid = false;
        String input = null;
        int site_supplier_ID = 0;
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
                if(controller.is_siteSupply_id_exist_in_current_transport(transport_id, site_supplier_ID)){
                    isValid = false;
                    System.out.print("This site supply ID number is already exist in this transport.");
                }

                if (controller.is_siteSupply_id_exist_in_system(site_supplier_ID)){
                    isValid = false;
                    System.out.print("This site supply ID number is already exist in the system.");
                }

            }
        }
        // ======================== Store Name ======================== //
        String store_name = null;
        isValid = false;
        while (!isValid) {
            System.out.println("Please enter the store's name: ");
            store_name = scanner.nextLine();
            if(store_name.strip().equals("")){
                System.out.println("Invalid input. ");
            }
            else {
                isValid = controller.is_site_exist(transport_id, "store", store_name);
                if (!isValid) {
                    System.out.print("This store is not part of this transport. ");
                }
            }
        }
        // ======================== Add Site Supply Document ======================== //
        controller.add_site_document_to_driver(transport_id, site_supplier_ID,store_name);
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
            controller.insert_item_to_siteSupply(site_supplier_ID, transport_id, item_name, item_amount);
            controller.insert_item_to_transport(transport_id, item_name, item_amount);
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
        // ======================== Insert Weight To Site Supply Document, transport document and to the Truck ======================== //
        controller.insert_weight_to_siteSupply(site_supplier_ID, transport_id, items_weight);
        // ======================== Add the store to the route if it don't exist ======================== //
        boolean add_site_to_route = !controller.is_store_exist_in_route(transport_id, store_name);
        if(add_site_to_route){
            controller.add_store_to_route(transport_id, store_name);
        }
    }


    private boolean change_transport(int transport_ID){
        int choice = 0;
        System.out.println("The truck max weight is: " + controller.get_truck_weight(transport_ID, "m") + " but her current weight is " + controller.get_truck_weight(transport_ID, "c") + "!!!");
        while (choice != 5) {
            System.out.println("Hey boss, You need to update the current Transport. please choose one of this following options:");
            System.out.println("1: I want to cancel shipments I currently haveto a certain store.");
            System.out.println("2: I want to change the truck to complete the transport.");
            System.out.println("3: I want to get the goods from the current supplier later / skip this supplier goods for today.");
            System.out.println("4: I want to cancel all the shipping, I tried all, currently we don't have other option!");

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
                // deleting a store from the transport
                case 1 -> {
                    if (!controller.is_there_more_than_one(transport_ID, "store")) {
                        System.out.println("Sorry Boss, this is the only store left for today...");
                        break;
                    }
                    // asking for the store until he gives a valid store name from are current destinations.
                    System.out.println("Please enter the Store you want to cancel the shipment:");
                    String store_to_remove = null;
                    boolean exist = false;
                    while (!exist) {
                        store_to_remove = scanner.nextLine();
                        exist = controller.is_site_exist(transport_ID, "store", store_to_remove);
                        if (!exist) {
                            System.out.println("This store is not on the list boss...");
                        }
                    }
                    // deleting the documents with the relevant documents according to the deleted destination.
                    if(!controller.delete_store_from_route(transport_ID, store_to_remove)){
                        break;
                    }
                    return true;
                }

                // changing the truck to a suitable one.
                case 2 -> {
                    if(!controller.is_suitable_truck_exist(transport_ID)){
                        System.out.println("Sorry Boss, we don't have a suitable truck...");
                        break;
                    }
                    //now we need to check if we need to assign a driver to the new truck.
                    if(controller.change_truck(transport_ID)){
                        System.out.println("The trucks finished to transfer all the goods and it's ready to go,");
                        System.out.println("The new driver is: " + controller.get_driver_name(transport_ID) + " and his driving the truck: " + controller.get_truck_number(transport_ID));
                        return true;
                    }
                    System.out.println("Sorry Boss, we have the right truck for the job, but we don't have a driver with a license for that truck right now...");
                    break;
                }

                // postpone the supplier to the end of the shipment.
                case 3 -> {
                    // checking how many suppliers are left for today.
                    boolean moreThanOne = controller.is_there_more_than_one(transport_ID, "supplier");
                    if (!moreThanOne){
                        System.out.println("Sorry Boss, this is the only supplier left for today...");
                        break;
                    }
                    int manager_choice = 0;
                    System.out.println("Please choose:");
                    System.out.println("1 - cancel this supplier's goods for today.");
                    System.out.println("2 - get back to this supplier later today.");
                    boolean exist_2 = false;
                    while (!exist_2) {
                        String ch = scanner.nextLine();
                        try {
                            manager_choice = Integer.parseInt(ch);
                            if (manager_choice == 1 || manager_choice == 2) {
                                exist_2 = true;
                            } else {
                                System.out.println("Input must be a 1 or 2.");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. please enter 1 or 2.");
                        }
                    }
                    // updating the truck route, and unloading all the products from the truck that the supplier needs to ship.
                    controller.delete_supplier_from_route(transport_ID);
                    // if the manager chose to get back to this supplier later today, we're changing te route.
                    if (manager_choice == 1) {
                        System.out.println("The goods from the current supplier removed from the truck.");
                    } else {
                        controller.add_supplier_to_route(transport_ID);
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

    public void check_for_estimated_finish_time_update(int transport_ID){
        System.out.println("The current Estimated finish time is: " + controller.getEstimatedEndTime(transport_ID) + " Would you like to update the estimated finish time of the transport? ");
        System.out.println("1 - YES");
        System.out.println("2 - NO");
        while (true) {
            String ch = scanner.nextLine();
            if (ch.equals("1")) {
                System.out.println("What is the new estimated finish time? (enter time in HH:mm:ss format");
                String newTime = scanner.nextLine();
                while(!controller.isValidTime(newTime)){
                    System.out.println("Wrong input, try again. ");
                    newTime = scanner.nextLine();
                }
                controller.setEstimatedEndTime(transport_ID, newTime);
                break;
            } else if (ch.equals("2")) {
                break;
            } else {
                System.out.println("You must enter 1 or 2.");
            }
        }
    }


}
