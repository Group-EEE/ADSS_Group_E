import java.util.ArrayList;
import java.util.List;

public class Store {
    private final String _name;
    private final String _address;

    private List<Employee> m_employees = new ArrayList<>();
    private List<Employee> m_general_employees = new ArrayList<>();
    private List<Employee> m_cashier_employees = new ArrayList<>();
    private List<Employee> m_usher_employees = new ArrayList<>();
    private List<Employee> m_security = new ArrayList<>();
    private List<Employee> m_warehouse_employees = new ArrayList<>();
    private List<Employee> m_cleaner_Employees = new ArrayList<>();
    private List<Employee> m_shift_manager_employees = new ArrayList<>();

    private Schedule curr_schedule;
    private List<Schedule> m_past_schedule = new ArrayList<>();

    public Store(String _name, String address){
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
     * @param employee the employee to add to the store
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean updateRoles(Employee employee){
        if (employee == null)
            return false;
        for (ARole role : employee.getRoles()){
            if (role instanceof CashierRole)
                this.m_cashier_employees.add(employee);
            else if (role instanceof WarehouseRole)
                this.m_warehouse_employees.add(employee);
            else if (role instanceof ShiftManagerRole)
                this.m_shift_manager_employees.add(employee);
            else if (role instanceof GeneralRole)
                this.m_general_employees.add(employee);
            else if (role instanceof UsherRole)
                this.m_usher_employees.add(employee);
            else if (role instanceof CleanerRole)
                this.m_cleaner_Employees.add(employee);
            else if (role instanceof SecurityRole)
                this.m_security.add(employee);
            else{
                System.out.println("Invalid role");
                return false;
            }
        }
        return true;
    }

    /**
     * @param employee the employee to remove from the store
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeEmployee(Employee employee){
        if (employee == null)
            return false;
        this.m_employees.remove(employee);
        for(ARole role : employee.getRoles()) {
            if(!removeRoleFromEmployee(role, employee))
                return false;
        }
        return true;
    }

    public boolean removeRoleFromEmployee(ARole role, Employee employee){
        if (role == null || employee == null)
            return false;
        if (role instanceof CashierRole)
            this.m_cashier_employees.remove(employee);
        else if (role instanceof WarehouseRole)
            this.m_warehouse_employees.remove(employee);
        else if (role instanceof ShiftManagerRole)
            this.m_shift_manager_employees.remove(employee);
        else if (role instanceof GeneralRole)
            this.m_general_employees.remove(employee);
        else if (role instanceof UsherRole)
            this.m_usher_employees.remove(employee);
        else if (role instanceof CleanerRole)
            this.m_cleaner_Employees.remove(employee);
        else if (role instanceof SecurityRole)
            this.m_security.remove(employee);
        else {
            System.out.println("Invalid role");
            return false;
        }
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
}
