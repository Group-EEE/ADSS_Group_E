package Presentation;

import Business.controllers.Logistical_center_controller;
import Business.controllers.underway_transport_controller;
import Business.objects.Logistical_Center;
import Business.objects.Site;
import Business.objects.Transport;
import Business.objects.Truck;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        chosen_transport.setDate(Date);
        chosen_transport.setDeparture_time(Time);
        // ========================= starting the transport ======================= //
        Truck truck = logistical_center_controller.getTruckByNumber(chosen_transport.getTruck_number());
        truck.setNavigator(chosen_transport.getDestinations());
        System.out.println("Transport - " + chosen_transport.getTransport_ID() + " started.");
        Site current = truck.get_next_site();
        while (current != null) {
            if (current.is_supplier()) {
                boolean isValidChoice = false;
                String ch = null;
                while (!isValidChoice) {
                    System.out.println("Hey " + current.getSite_name() + " manager!");
                    // creating a document
                    create_site_supply(chosen_transport, current.getAddress());
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
                if (!check_weight(truck)) {
                    boolean abort_transport = !change_transport(chosen_transport, truck, truck.getCurrent_driver());
                    if (abort_transport) {
                        //delete_transport();
                        System.out.println("Transport was aborted. you can try to send it later");
                        aborted = true;
                        chosen_transport.setStarted(false);
                        break;
                    } else {
                        truck = getTruckByNumber(chosen_transport.getTruck_number());
                    }
                }
            }
            // else if will be here
        }
    }

    private void create_site_supply(Transport transport, String address) {
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

}
