package BussinessLayer.Objects;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private final String _name;
    private final String _address;
    private final int _id;
    private List<Employee> m_employees = new ArrayList<>();
    private Schedule curr_schedule;
    private List<Schedule> m_past_schedule = new ArrayList<>();

    public Store(int id, String _name, String address){
        this._id = id;
        this._name = _name;
        this._address = address;
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
        this.m_employees.add(employee);
        return true;
    }

    /**
     * @return the current schedule of the store
     */
    public Schedule getCurrSchedule() {
        return curr_schedule;
    }

    /**
     * @param employee the employee to remove from the store
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeEmployee(Employee employee){
        if (employee == null)
            return false;
        if(!(this.m_employees.remove(employee)))
            return false;
        return true;
    }

    /**
     * @return the list of all employees in the store
     */
    public List<Employee> getEmployees(){
        return this.m_employees;
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
        return m_past_schedule;
    }
}
