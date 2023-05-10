package BussinessLayer.TransportationModule.objects;

import BussinessLayer.HRModule.Objects.Employee;

import java.time.LocalDate;
import java.util.ArrayList;

public class Truck_Driver extends Employee{
    private License license;
    private ArrayList<Site_Supply> sites_documents;
    private Truck current_truck = null;

//    public Truck_Driver(int employeeID, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment, String password, int license_id, cold_level level, double truck_weight) {
//        super(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password);
//        this.license = new License(license_id, level, truck_weight);
//          sites_documents = new ArrayList<>();
//    }

    public Truck_Driver(int employeeID, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment, String password, License D_license){
        super(employeeID, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password);
        this.license = D_license;
        sites_documents = new ArrayList<>();
    }

    public void setCurrent_truck(Truck current_truck) {
        this.current_truck = current_truck;
    }

    public Truck getCurrent_truck() {
        return current_truck;
    }

    public ArrayList<Site_Supply> getSites_documents() {
        return sites_documents;
    }

    public void setSites_documents(ArrayList<Site_Supply> sites_documents) {
        this.sites_documents = sites_documents;
    }

    public void delete_truck(){
        if(current_truck == null){
            System.out.println("There's no current truck.");
        }
        else {
            current_truck = null;
        }
    }

    public void Add_site_document(Site_Supply doc){
        if (doc != null){
            sites_documents.add(doc);
        }
    }

    public Site_Supply get_document(int doc_id){
        for (int i = 0; i < sites_documents.size(); i++){
            if (sites_documents.get(i).equals(doc_id)){
                return sites_documents.get(i);
            }
        }
        return null;
    }

    public void delete_site_document_by_origin(String origin){
        for (int i = 0; i < sites_documents.size(); i++) {
            if (sites_documents.get(i).getOrigin().equals(origin)){
                sites_documents.remove(i);
            }
        }
    }

    public void delete_site_document_by_destination(String destination){
        for (int i = 0; i < sites_documents.size(); i++) {
            if (sites_documents.get(i).getStore().getSite_name().equals(destination)){
                sites_documents.remove(i);
            }
        }
    }

    // create delete by ID
    public void delete_site_document_by_ID(int ID){
        for (int i = 0; i < sites_documents.size(); i++) {
            if (sites_documents.get(i).getId() == ID){
                sites_documents.remove(i);
                return;
            }
        }
    }



    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public boolean is_site_exist(String site){
        for (int i = 0; i < sites_documents.size(); i++) {
            if (sites_documents.get(i).getStore().getSite_name() == site){
                return true;
            }
        }
        return false;
    }

    public boolean equals(Truck_Driver td) {
        return (_employeeID == td.getEmployeeID());
    }


    public boolean equals(int d_ID) {
        return (_employeeID == d_ID);
    }



    // display

    public void driverDisplay(){
        System.out.println("Driver ID Number - " + this._employeeID);
        System.out.println("\t Driver Name: " + this._firstName);
        System.out.println("\t License Details: ");
        license.licenseDisplay();
        if(current_truck != null) {
            System.out.println("\t Current Truck: " + current_truck.getRegistration_plate());
        }
        System.out.println();
    }
}
