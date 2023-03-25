import java.util.List;

public class Store {
    private String m_address; 
    private List<Employee> m_employees;
    private List<Schedule> m_past_schedule;
    public Store(String address){
        this.m_address = address;
    }
    public boolean addEmployee(Employee employee){
        this.m_employees.add(employee);
        return true;
    }
    public boolean removeEmployee(Employee employee){
        this.m_employees.remove(employee);
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
