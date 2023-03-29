
import java.util.List;
import Roles.*;

public class Store {
    private String m_name;
    private String m_address; 
    private List<Employee> m_employees;

    private List<Employee> m_general_employees;
    private List<Employee> m_cashier_employees;
    private List<Employee> m_usher_employees;
    private List<Employee> m_security;
    private List<Employee> m_warehouse_employees;
    private List<Employee> m_cleaner_Employees;
    private List<Employee> m_shift_manager_employees;

    private List<Schedule> m_past_schedule;
    public Store(String m_name, String address){
        this.m_name = m_name;
        this.m_address = address;
    }
    public boolean addEmployee(Employee employee){
        if (employee == null)
            return false;
        this.m_employees.add(employee);
        return true;
    }
    public boolean updateRoles(Employee employee){
        if (employee == null)
            return false;
        for (IRole role : employee.getRoles()){
            if (role instanceof CashierRole){
                this.m_cashier_employees.add(employee);
            }
            else if (role instanceof WarehouseRole){
                this.m_warehouse_employees.add(employee);
            }
            else if (role instanceof ShiftManagerRole){
                this.m_shift_manager_employees.add(employee);
            }
            else if (role instanceof GeneralRole)
                this.m_general_employees.add(employee);
            else if (role instanceof UsherRole)
                this.m_usher_employees.add(employee);
            else if (role instanceof CleanerRole){
                this.m_cleaner_Employees.add(employee);
            }
            else if (role instanceof SecurityRole){
                this.m_security.add(employee);
            }
        }
        return true;
    }
    public boolean addSchedule(Schedule schedule){
        this.m_past_schedule.add(schedule);
        return true;
    }
    public String getAddress() {
        return m_address;
    }
}
