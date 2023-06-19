package BussinessLayer.TransportationModule.controllers;

import BussinessLayer.HRModule.Objects.*;
import BussinessLayer.TransportationModule.objects.*;
import DataAccessLayer.HRMoudle.EmployeesDAO;
import DataAccessLayer.HRMoudle.SchedulesDAO;
import DataAccessLayer.HRMoudle.ShiftsDAO;
import DataAccessLayer.HRMoudle.StoresDAO;
import DataAccessLayer.Transport.*;
import BussinessLayer.HRModule.Controllers.ScheduleController;
import BussinessLayer.HRModule.Controllers.ScheduleController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// singleton
public class Logistical_center_controller {
    // singleton


    private static Logistical_center_controller instance;
    private Logistical_Center logistical_center;
    EmployeesDAO _employeesDAO;

    public static Logistical_center_controller getInstance(){
        if (instance == null){
            instance = new Logistical_center_controller();
        }
        return instance;
    }
    // singleton
    private Logistical_center_controller(){
        _employeesDAO = EmployeesDAO.getInstance();
    }

    public void create_Logistical_Center(String address, String phone, String name, String site_contact_name){
        if (logistical_center == null){
            logistical_center = new Logistical_Center(address, phone, name, site_contact_name);
        }
    }

    public Logistical_Center getLogistical_center() {
        return logistical_center;
    }

//    public void add_driver(int driver_ID, String driver_name, int license_id, cold_level level, double truck_weight){
//        Truck_Driver new_driver = new Truck_Driver(driver_ID, driver_name, license_id, level, truck_weight);
//        _employeesDAO.insertDriver(new_driver);
//    }

    public Transport get_transport_by_id(int transport_id){
        return Transport_dao.getInstance().getTransport(transport_id);

    }

    public boolean check_if_site_supply_exist(int id){
        return Site_Supply_dao.getInstance().check_if_site_supply_exists(id);

    }

    public void insert_site_supply_to_database(Site_Supply site_supply){
        Site_Supply_dao.getInstance().Insert(site_supply);
    }

    public void add_transport(int transport_id, String truck_number, String driver_name, String cold_lvl, String planned_date, int driver_id){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        Transport transport = new Transport(transport_id, "TBD", "TBD", truck_number, driver_name, "Logistics", cool_level, planned_date, driver_id );
        Transport_dao.getInstance().Insert(transport);
    }

    ///// need to notify HR when the driver is needed to be in the weekly schedule
    public boolean truck_assigning(String new_truck_registration_plate, String planned_date, Employee E_driver){
        Truck truck = getTruckByNumber(new_truck_registration_plate);
        Truck_Driver driver =  EmployeesDAO.getInstance().getDriver(E_driver.getEmployeeID());
        // checking if the given parameters are valid, and getting the diver and the truck if they exist.

        if (driver.getLicense().getWeight() >= truck.getMax_weight() && driver.getLicense().getCold_level().getValue() <= truck.getCold_level().getValue() && !Transport_dao.getInstance().check_if_driver_taken_that_date(planned_date, driver.getEmployeeID())){

            LocalDate date = LocalDate.parse(planned_date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int shift_id = ScheduleController.getInstance().getShiftIDByDate("Logistics", date, ShiftType.MORNING);
//            ScheduleController.getInstance().addDriverToLogisticsShift(driver.getEmployeeID(), shift_id);
//            ScheduleController.getInstance().addDriverToLogisticsShift(driver.getEmployeeID(), shift_id+1);
            return true;
        }
        return false;
    }

    public ArrayList<Truck_Driver> get_possible_drivers_for_truck(String truck_number, String planned_date){
        Truck truck = getTruckByNumber(truck_number);
        ArrayList<Truck_Driver> drivers = new ArrayList<>();
        for (Truck_Driver driver : EmployeesDAO.getInstance().getDrivers()){
            if(driver.getLicense().getWeight() >= truck.getMax_weight() && driver.getLicense().getCold_level().getValue() <= truck.getCold_level().getValue() && !Transport_dao.getInstance().check_if_driver_taken_that_date(planned_date, driver.getEmployeeID())){
                drivers.add(driver);
            }
        }
        return drivers;
    }



    public boolean truck_assigning_drivers_in_shift(String new_truck_registration_plate, String planned_date){
        LocalDate date = LocalDate.parse(planned_date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int shift_id = ScheduleController.getInstance().getShiftIDByDate("Logistics", date, ShiftType.MORNING);
        // TODO: check with chen that he gives us a function that get the schedule by any day that in that schedule. not only the exact day that is starts.
        Schedule schedule = SchedulesDAO.getInstance().getSchedule(date, "Logistics");
        Shift shift = schedule.getShift(shift_id);
//        List<Shift> all_shifts = ShiftsDAO.getInstance().getShiftsByScheduleID(schedule_id);
//        Shift current_shift = null;
//        for (Shift shift : all_shifts){
//            if(shift.getShiftID() == shift_id){
//                current_shift = shift;
//                break;
//            }
//        }
        List<Employee> drivers = new ArrayList<>(shift.getAssignedEmployees().values());

        for (Employee driver : drivers){
            if(!Transport_dao.getInstance().check_if_driver_taken_that_date(planned_date, driver.getEmployeeID())){
                if(truck_assigning(new_truck_registration_plate, planned_date, driver)){
                    Truck truck = getTruckByNumber(new_truck_registration_plate);
                    truck.setCurrent_driver(EmployeesDAO.getInstance().getDriver(driver.getEmployeeID()));
                    Truck_Driver truck_driver = EmployeesDAO.getInstance().getDriver(driver.getEmployeeID());
                    truck_driver.setCurrent_truck(truck);
                    // update the driver in the transports database

                    return true;
                }
            }
        }
        return false;
    }


    public String get_truck_number_by_cold_level(String cold_lvl, String planned_date){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        Truck truck = null;
        for(Truck t : Trucks_dao.get_instance().getTrucks()){
            if(t.getCold_level().getValue() <= cool_level.getValue() && !Transport_dao.getInstance().check_if_truck_taken_that_date(planned_date, t.getRegistration_plate())) {
                if(t.getCold_level().getValue() == cool_level.getValue()){
                    truck = t;
                    break;
                }
                else if (truck == null) {
                    truck = t;
                } else if (cool_level.getValue() - t.getCold_level().getValue() < cool_level.getValue() - truck.getCold_level().getValue()) {
                    truck = t;
                }
            }
        }
        return truck.getRegistration_plate();
    }

    /**
     * @param cold_lvl cold level as string
     * @return true if we have a truck that not occupied and suitable with the cold level constraint, otherwise false
     */
    public boolean check_if_truck_exist_by_cold_level(String cold_lvl, String planned_date){
        cold_level cool_level = cold_level.fromString(cold_lvl);
        for(Truck t : Trucks_dao.get_instance().getTrucks()){
            if(!t.Occupied() && t.getCold_level().getValue() <= cool_level.getValue() && !Transport_dao.getInstance().check_if_truck_taken_that_date(planned_date, t.getRegistration_plate())){
                return true;
            }
        }

        return false;
    }

    /**
     * @param transport_id int representing the transport id
     * @param truck_ID string representing the truck number
     *                 this function inserts the truck weight to the transport
     */
    public void insert_weight_to_transport(int transport_id, String truck_ID){
        Truck truck = getTruckByNumber(truck_ID);
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        transport.insertToWeights(truck.getNet_weight());
    }

    public List<Truck_Driver> getDrivers(){
        return _employeesDAO.getDrivers();
    }

    public boolean check_if_site_exist_in_transport(String site_name, int transport_id){
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        for (Site site: transport.getDestinations()){
            if (site.getSite_name().equals(site_name)){
                return true;
            }
        }
        return false;
    }

    public void insert_store_to_transport(int transport_id, String name){
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        try {
            transport.insertToDestinations(StoresDAO.getInstance().getStore(name));
        } catch (Exception e) {
        }
    }

    public void insert_supplier_to_transport(int transport_id, String supplier_name){
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        transport.insertToDestinations(Suppliers_dao.getInstance().get_supplier_by_name(supplier_name));
    }

    public Map<Integer, Transport> getTransport_Log(){
        return Transport_dao.getInstance().get_transports_map();
    }

    public Truck getTruckByNumber(String truck_number){
        return Trucks_dao.get_instance().get_truck_by_registration_plate(truck_number);
    }

    public void add_truck(String registration, String truck_model, double truck_net_weight, double truck_max_weight, cold_level level, double current_weight){
        Truck truck = new Truck(registration, truck_model, truck_net_weight, truck_max_weight, level, current_weight);
        Trucks_dao.get_instance().Insert(truck);
    }

    public boolean is_truck_exist(String truck_ID){
        return Trucks_dao.get_instance().check_if_truck_exists(truck_ID);
    }

    public void display_transport_doc(){
        if(Transport_dao.getInstance().get_transports().size() == 0){
            System.out.println("\nThere are no transports documents in the system\n");
        }
        else {
            System.out.println("======================================= Transport Documents in the system =======================================");
            for (Map.Entry<Integer, Transport> entry : Transport_dao.getInstance().get_transports_map().entrySet()) {
                int id = entry.getKey();
                Transport transport = entry.getValue();
                System.out.println("=========== Transport - " + id + " - information ===========");
                transport.transportDisplay();
            }
        }
    }

    public void display_stores(){
        if(StoresDAO.getInstance().SelectAllStores().size() == 0){
            System.out.println("\nThere are no stores in the system\n");
        }
        else {
            int store_num = 1;
            System.out.println("======================================= Stores in the system =======================================");
            for (Store store : StoresDAO.getInstance().SelectAllStores()) {
                System.out.println("\t=========== Store - " + store_num + " - information ===========");
                store.storeDisplay();
                store_num++;
            }
        }
    }



    public void display_trucks(){
        if(Trucks_dao.get_instance().getTrucks().size() == 0){
            System.out.println("\nThere are no trucks in the system\n");
        }
        else {
            System.out.println("======================================= Trucks in the system =======================================");
            for (Truck t : Trucks_dao.get_instance().getTrucks()) {
                t.truckDisplay();
            }
        }
    }

    public ArrayList<Transport> get_transports(){
        return Transport_dao.getInstance().get_transports();
    }

    public void display_drivers(){
        if(_employeesDAO.getDrivers().size() == 0){
            System.out.println("\nThere are no drivers in the system\n");
        }
        else {
            System.out.println("======================================= Drivers in the system =======================================");
            for (Truck_Driver driver : _employeesDAO.getDrivers()) {
                driver.driverDisplay();
            }
        }
    }

    public void display_site_supply(){
        if(Site_Supply_dao.getInstance().get_site_supply_documents().size() == 0){
            System.out.println("\nThere are no site supplies documents in the system\n");
        }
        else {
            System.out.println("======================================= Site Supply in the system =======================================");
            for (Site_Supply siteSupply : Site_Supply_dao.getInstance().get_site_supply_documents()) {
                siteSupply.sDisplay();
            }
        }
    }

    public boolean check_if_driver_Id_exist(int driver_ID){
        return _employeesDAO.existDriver(driver_ID);
    }

    public boolean check_if_license_id_exist(int license_ID){
        return License_dao.getInstance().check_if_license_exist(license_ID);
    }

    public boolean check_if_transport_id_exist(int transport_ID){
        return Transport_dao.getInstance().check_if_Transport_exist(transport_ID);
    }

    public void display_trucks_by_cold_level(String cold_lvl){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        System.out.println("======================================= Trucks with cold level '" + cool_level + "' in the system =======================================");
        for (Truck t : Trucks_dao.get_instance().getTrucks()) {
            if (t.getCold_level().getValue() == cool_level.getValue()) {
                t.truckDisplay();
            }
        }
    }

    public ArrayList<String> get_trucks_by_cold_level(String cold_lvl, boolean including_colder_trucks){
        cold_level cool_level = get_cold_level_by_string(cold_lvl);
        ArrayList<String> trucks_ids = new ArrayList<>();
        if (!including_colder_trucks) {
            for (Truck t : Trucks_dao.get_instance().getTrucks()) {
                if (t.getCold_level().getValue() == cool_level.getValue()) {
                    trucks_ids.add(t.getRegistration_plate());
                }
            }
        }
        else {
            for (Truck t : Trucks_dao.get_instance().getTrucks()) {
                if (t.getCold_level().getValue() <= cool_level.getValue()) {
                    trucks_ids.add(t.getRegistration_plate());
                }
            }
        }
        return trucks_ids;
    }

    private cold_level get_cold_level_by_string(String cold_lvl){
        switch (cold_lvl){
            case "Cold":
                return cold_level.Cold;
            case "Freeze":
                return cold_level.Freeze;
            case "Dry":
                return cold_level.Dry;
        }
        return null;
    }

    public void set_started_transport(int transport_id){
        Transport transport = Transport_dao.getInstance().getTransport(transport_id);
        transport.setStarted(true);
    }

    public boolean at_least_one_unsent_transport(){
        for (Transport transport: Transport_dao.getInstance().get_transports()){
            if (!transport.Started()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Truck> get_trucks(){
        return Trucks_dao.get_instance().getTrucks();
    }

    public boolean can_send_the_transport(int transport_ID, String current_date){
        if (!check_if_transport_id_exist(transport_ID)){
            System.out.println("We don't have the transport" + transport_ID + " in the system.");
            return false;
        }

        Transport transport = get_transport_by_id(transport_ID);

        if (!transport.getPlanned_date().equals(current_date)){
            System.out.println("It's not the date of the transport.");
            return false;
        }
        if (transport.Started()){
            System.out.println("this transport is already finished.");
            return false;
        }
        return true;
    }

    public void add_supplier(String address, String phone, String supplier_name, String contact_name){
        Supplier supplier = new Supplier(address, phone, supplier_name, contact_name);
        Suppliers_dao.getInstance().Insert(supplier);
    }

    public void insert_sites_names_to_transport(int transport_ID, String stores, String suppliers){
        Transport_dao.getInstance().insert_stores_and_suppliers_to_table(transport_ID, stores, suppliers);
    }


    public void display_suppliers() {
        if(Suppliers_dao.getInstance().SelectAllSuppliers().size() == 0){
            System.out.println("\nThere are no suppliers in the system\n");
        }
        else {
            int supplier_num = 1;
            System.out.println("======================================= Suppliers in the system =======================================");
            for (Supplier supplier : Suppliers_dao.getInstance().SelectAllSuppliers()) {
                System.out.println("\t=========== Supplier - " + supplier_num + " - information ===========");
                supplier.supplierDisplay();
                supplier_num++;
            }
        }
    }

    public Truck_Driver add_standby_driver_by_date(LocalDate date) {
        int shift_id = ScheduleController.getInstance().getShiftIDByDate("Logistics", date, ShiftType.MORNING);
        Schedule schedule = ScheduleController.getInstance().getSchedule("Logistics");
        Shift shift = schedule.getShift(shift_id);
        List<Employee> drivers_in_shift = shift.getInquiredEmployees();
        //HashMap<RoleType, Employee> employees = ScheduleController.getInstance().getSchedule("Logistics").getShift(shift_id).getAssignedEmployees();
        //ArrayList<Employee> drivers_in_shift = new ArrayList<>(employees.values());
        List<Truck_Driver> all_drivers = EmployeesDAO.getInstance().getDrivers();
        Truck_Driver standby_driver = null;
        if(ScheduleController.getInstance().hasStandByDriver("Logistics", shift_id )){
            System.out.println("\nThis shift already had standby truck driver. \n");
        }
        else {
            for (Truck_Driver driver : all_drivers) {
                if (!Transport_dao.getInstance().check_if_driver_taken_that_date(date.toString(), driver.getEmployeeID()) && !drivers_in_shift.contains(driver)) {
                    standby_driver = driver;
                    break;
                }
            }
            if(standby_driver != null) {
                ScheduleController.getInstance().addStandByDriverToLogisticsShift(standby_driver.getEmployeeID(), shift_id);
                ScheduleController.getInstance().addStandByDriverToLogisticsShift(standby_driver.getEmployeeID(), shift_id+1);

                return standby_driver;
            }
        }
        return null;
    }
}
