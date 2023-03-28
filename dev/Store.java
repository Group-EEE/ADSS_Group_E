import java.util.List;

import Employee.Employee;

public class Store {
    private String m_name;
    private String m_address; 
    private List<Employee> m_employees;
    private List<Employee> m_general_employees;
    private List<Employee> m_cashier_employees;
    private List<Employee> m_wearhouse_employees;
    private List<Employee> m_manager_employees;
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
        for (roleType role : employee.getRoles()) {
            switch (role) {
                case cashier:
                    this.m_cashier_employees.add(employee);
                    break;
                case general:
                    this.m_general_employees.add(employee);
                    break;
                case wearhouse:
                    this.m_wearhouse_employees.add(employee);
                    break;
                case manager:
                    this.m_manager_employees.add(employee);
                    break;
                default:
                    return false;
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
