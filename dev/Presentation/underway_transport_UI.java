package Presentation;

import Business.controllers.Logistical_center_controller;
import Business.controllers.underway_transport_controller;
import Business.objects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class underway_transport_UI {
    // ===== attributes =====
    private underway_transport_controller controller;
    Logistical_center_controller  logistical_center_controller;
    Scanner scanner;
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
        Transport chosen_transport = logistical_center.getTransport_Log().get(transport_ID);
        // ======================== Date And Time ======================== //
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String Date = now.toLocalDate().format(dateFormatter);
        String Time = now.toLocalTime().format(timeFormatter);
        controller.set_time_and_date_for_transport(transport_ID, Date, Time);
        // ========================= starting the transport ======================= //
        Truck truck = logistical_center_controller.getTruckByNumber(chosen_transport.getTruck_number());
        controller.set_navigator_for_transport(transport_ID);
        System.out.println("Transport - " + transport_ID + " started.");
        while (controller.is_current_location_not_null(transport_ID)) {
            if (!controller.is_current_location_is_store(transport_ID)) {
                boolean isValidChoice = false;
                String ch = null;
                System.out.println("Hey " + controller.get_current_location_name(transport_ID) + " manager!");
                while (!isValidChoice) {
                    // creating a document
                    create_site_supply(chosen_transport, controller.get_current_location_address(transport_ID));
                    // asking if he needs to make another one
                    System.out.println("Do you have items to ship to another store? (Write 1 or 2): ");
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
                //insert the weight
                chosen_transport.insertToWeights(truck.getCurrent_weight());
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
                    if (controller.unload_goods((Store) current, truck, truck.getCurrent_driver())) {
                        System.out.println("goods unloaded in " + controller.get_current_location_name(transport_ID));
                    } else {
                        System.out.println("We currently don't have any goods for " + controller.get_current_location_name(transport_ID) + " ,skip this store for now.");
                    }
                }
                // driving to the next site.
                controller.drive_to_next_location(transport_ID);
            }
        // if the transport wasn't aborted, we update the truck and the driver so they can now go to another shipment.
            if (!aborted) {
                controller.reset_transport(transport_ID, true);
                System.out.println("Transport " + chosen_transport.getTransport_ID() + " now finished.");
            }


    }

    /**
     * @param transport transport document
     * @param address address of the supplier
     *                ask from the supplier details to create a site supply document and give it to the driver
     */
    private void create_site_supply(Transport transport, String address) {
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
                 if(controller.is_siteSupply_id_exist_in_current_transport(transport, site_supplier_ID)){
                 isValid = false;
                 System.out.print("This site supply ID number is already exist in this transport.");
                 }

                 if (controller.is_siteSupply_id_exist_in_system(site_supplier_ID)){
                     isValid = false;
                     System.out.print("This site supply ID number is already exist in the system.");
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
        // ======================== Add Site Supply Document ======================== //
        controller.add_site_document_to_driver(controller.getDriverByTruckNumber(transport.getTruck_number()), site_supplier_ID,transport.getStoreByName(store_name), address);
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
            controller.insert_item_to_siteSupply(site_supplier_ID, controller.getDriverByTruckNumber(transport.getTruck_number()), item_name, item_amount);
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
        controller.insert_weight_to_siteSupply(site_supplier_ID, controller.getDriverByTruckNumber(transport.getTruck_number()), items_weight);
        // ======================== Insert Weight To Truck ======================== //
        controller.get_truck_by_registration_plate(transport.getTruck_number()).addWeight(items_weight);;
    }



    private boolean change_transport(int transport_ID){
        int choice = 0;
        System.out.println("The truck max weight is: " + controller.get_truck_weight(transport_ID, "m") + " but her current weight is " + controller.get_truck_weight(transport_ID, "c") + "!!!");
        while (choice != 5) {
            System.out.println("Hey boss, You need to update the current Transport. please choose one of this following options:");
            System.out.println("1: I want to cancel shipping to a certain store.");
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
                    String site_to_remove = null;
                    boolean exist = false;
                    while (!exist) {
                        String store = scanner.nextLine();
                        exist = controller.is_site_exist(transport_ID, "store", store);
                        if (!exist) {
                            System.out.println("This store is not on the list boss...");
                        }
                    }
                    // transfer to controller called cancel_shipping_to_store(transport_doc, )
                    // deleting the documents with the relevant documents according to the deleted destination.
                    boolean is_weight_good = controller.delte_site(transport_ID, site_to_remove);
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
                    Truck new_truck = controller.getTruckByColdAndWeight(truck.getCold_level(), truck.getCurrent_weight());
                    if (new_truck == null) {
                        System.out.println("Sorry Boss, we don't have a suitable truck...");
                    } else {
                        //now we need to check if we need to assign a driver to the new truck.
                        for (Truck_Driver truck_driver : logistical_center.getDrivers()) {
                            if (logistical_center_controller.truck_assigning(new_truck.getRegistration_plate())) {
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
                    // adding the supplier to the end of the route and afterward the destinations the supplier needs to ship the goods.

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




}
