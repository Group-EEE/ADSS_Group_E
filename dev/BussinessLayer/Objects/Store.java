package BussinessLayer.Objects;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private final String _name;
    private final String _address;
    private final int _storeID;
    private List<Employee> _employees;
    private List<Schedule> _pastSchedules;

    public Store(int storeID, String name, String address){
        this._storeID = storeID;
        this._name = name;
        this._address = address;
        this._employees = new ArrayList<>();
        this._pastSchedules = new ArrayList<>();
    }

    /**
     * @return the name of the store
     */
    public String getName() {
        return _name;
    }

    /**
     * @param employee the employee to add to the store
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee){
        if (employee == null)
            return false;
        this._employees.add(employee);
        return true;
    }

    /**
     * @param employee the employee to remove from the store
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeEmployee(Employee employee){
        if (employee == null)
            return false;
        if(!(this._employees.remove(employee)))
            return false;
        return true;
    }

    /**
     * @return the list of all employees in the store
     */
    public List<Employee> getEmployees(){
        return this._employees;
    }

    /**
     * @return the list of all cashier employees in the store
     */
    public String getAddress() {
        return _address;
    }

    /**
     * @return the past Schedules
     */
    public List<Schedule> getPastSchedules() {
        return _pastSchedules;
    }

    public String toString(){
        String storeToString =  "Store ID: " + this._storeID + " Store Name: " + this._name + " Store Address: " + this._address+"\n";
        storeToString += "Employees:\n";
        for (Employee employee : this._employees) {
            storeToString += employee.toString() + "\n";
        }
       return storeToString;
    }
}
