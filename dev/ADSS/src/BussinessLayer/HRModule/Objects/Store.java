package BussinessLayer.HRModule.Objects;

import BussinessLayer.TransportationModule.objects.Site;

import java.util.ArrayList;
import java.util.List;

public class Store extends Site {
    private final String _name;
    private final int _storeID;
    private List<Schedule> _pastSchedules;

    public Store(int storeID, String name, String address, String phone, String site_contact_name){
        super(address, phone, name, site_contact_name);
        this._storeID = storeID;
        this._name = name;
        this._pastSchedules = new ArrayList<>();
    }

    /**
     * @return the name of the store
     */
    public String getName() {
        return _name;
    }

//    /**
//     * @param employee the employee to add to the store
//     * @return true if the employee was added successfully, false otherwise
//     */
//    public boolean addEmployee(Employee employee){
//        if (employee == null)
//            return false;
//        this._employees.add(employee);
//        return true;
//    }

//    /**
//     * @param employee the employee to remove from the store
//     * @return true if the employee was removed successfully, false otherwise
//     */
//    public boolean removeEmployee(Employee employee){
//        if (employee == null)
//            return false;
//        if(!(this._employees.remove(employee)))
//            return false;
//        return true;
//    }

//    /**
//     * @return the list of all employees in the store
//     */
//    public List<Employee> getEmployees(){
//        return this._employees;
//    }

    @Override
    public boolean is_supplier() {
        return false;
    }

    @Override
    public boolean is_logistical_center() {
        return false;
    }

    @Override
    public boolean is_store() {
        return false;
    }

    /**
     * @return the list of all cashier employees in the store
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the past Schedules
     */
    public List<Schedule> getPastSchedules() {
        return _pastSchedules;
    }

    public String toString(){
       return "Store ID: " + this._storeID + ", Store Name: " + this._name + ", Store Address: " + this.address+ ", Store Phone: " + this.phone + ", Store Contact Name: " + this.site_contact_name;
    }

    public int getStoreID() {
        return _storeID;
    }
}
