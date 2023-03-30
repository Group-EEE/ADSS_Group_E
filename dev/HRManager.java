
import java.util.List;

import Roles.IRole;

import java.util.ArrayList;

public class HRManager extends AEmployee{
    private List<Employee> m_employees = new ArrayList<Employee>();
    private List<Store> m_stores = new ArrayList<Store>();

    public HRManager(String first_name, String last_name, int age, int id, String bank_account,String password) {
        super(first_name, last_name, age, id, bank_account);
        Login.createUser(id, password, this);
    }

    public boolean createEmployee(String first_name, String last_name, int age, int id, String bank_account, String password) {
        if (first_name == null || last_name == null || age < 0 || id < 0 || bank_account == null)
            return false;
        Employee new_employee = new Employee(first_name, last_name, age, id, bank_account);
        m_employees.add(new_employee);
        Login.createUser(id, password, new_employee);
        return true; 
        //return addEmployeeToStore(new_employee, store_location);
    }
    public boolean createStore(String store_name, String store_address) {
        if (store_name == null || store_address == null)
            return false;
        m_stores.add(new Store(store_name, store_address));
        return true;
    }

    public boolean addRoleToEmployee(int id_employee, IRole role) {
        Employee employee = findEmployeeByID(id_employee);
        List<Store> stores = employee.getStores(); 
        List<IRole> roles = employee.getRoles();
        roles.add(role);
        for (Store store : stores) {
            store.updateRoles(employee);
        }

        return employee.setRoles(role);
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
}
