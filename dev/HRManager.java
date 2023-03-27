import java.util.List;

public class HRManager implements IEmployee{
    private String m_first_name;
    private String m_last_name;
    private int m_age;
    private int m_id;
    private String m_bank_account;
    private List<Employee> m_employees;
    private List<Store> m_stores;

    public HRManager(String first_name, String last_name, int age, int id, String bank_account) {
        this.m_first_name = first_name;
        this.m_last_name = last_name;
        this.m_age = age;
        this.m_id = id;
        this.m_bank_account = bank_account;
    }

    public boolean createEmployee(String first_name, String last_name, int age, int id, String bank_account) {
        if (first_name == null || last_name == null || age < 0 || id < 0 || bank_account == null)
            return false;
        m_employees.add(new Employee(first_name, last_name, age, id, bank_account));
        return true; 
        //return addEmployeeToStore(new_employee, store_location);
    }
    public boolean createStore(String store_name, String store_address) {
        if (store_name == null || store_address == null)
            return false;
        m_stores.add(new Store(store_name, store_address));
        return true;
    }

    public boolean addEmployeeToStore(int id_employee, String store_location) {
        Store store_obj = findStoreByName(store_location);
        if (store_obj == null) {
            return false;
        }
        Employee employee = findEmployeeByID(id_employee);
        if (employee == null) {
            return false;
        }
        store_obj.addEmployee(employee);
        return true;
    }
    public Employee findEmployeeByID(int id) {
        for (Employee employee : this.m_employees) {
            if (employee.getID() == id) {
                return employee;
            }
        }
        return null;
    }

    public Store findStoreByName(String store_name) {
        for (Store store : this.m_stores) {
            if (store.getAddress().equals(store_name)) {
                return store;
            }
        }
        return null;
    }

    public boolean addRole(Employee employee, roleType role) {
        if (employee == null || role == null)
            return false;
        return employee.setRoles(role);
    }

}
