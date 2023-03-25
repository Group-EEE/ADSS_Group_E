import java.util.List;

public class HRManager {
    private String m_first_name;
    private String m_last_name;
    private int m_age;
    private int m_id;
    private int m_bank_account;
    private List<Store> m_stores;

    public HRManager(String first_name, String last_name, int age, int id, int bank_account) {
        this.m_first_name = first_name;
        this.m_last_name = last_name;
        this.m_age = age;
        this.m_id = id;
        this.m_bank_account = bank_account;
    }

    public boolean createEmployee(String first_name, String last_name, int age, int id, int bank_account, String store_location) {
        Employee new_employee = new Employee(first_name, last_name, age, id, bank_account);
        return addEmployeeToStore(new_employee, store_location);
    }

    public boolean addEmployeeToStore(Employee employee, String store_location) {
        Store store_obj = findStoreByLocation(store_location);
        if (store_obj == null) {
            return false;
        }
        store_obj.addEmployee(employee);
        return true;
    }

    public Store findStoreByLocation(String location) {
        for (Store store : this.m_stores) {
            if (store.getAddress().equals(location)) {
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
