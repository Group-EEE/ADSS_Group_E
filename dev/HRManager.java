
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

    public String getEmployeeFirstNameById(int id){
        if (id < 0)
            return null;
        return findEmployeeByID(id).get_first_name();
    }

    public Store getStoreByName(String store_name){
        if (store_name == null)
            return null;
        for (Store store : m_stores){
            if (store.getName().equals(store_name))
                return store;
        }
        return null;
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
        if (id_employee <0 || role == null)
            return false;
        Employee employee = findEmployeeByID(id_employee);
        List<Store> stores = employee.getStores(); 
        List<IRole> roles = employee.getRoles();
        roles.add(role);
        for (Store store : stores) {
            store.updateRoles(employee);
        }

        return employee.setRole(role);
    }

    public boolean addEmployeeToStore(int id_employee, String store_name) {
        if (id_employee <0 || store_name == null)
            return false;
        Store store_obj = findStoreByName(store_name);
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

    /**
     * @param id - the id of the employee
     * @return the employee with the given id
     */
    public Employee findEmployeeByID(int id) {
        if (id <0)
            return null;
        for (Employee employee : this.m_employees) {
            if (employee.getID() == id) {
                return employee;
            }
        }
        return null;
    }

    /**
     * @param id - the id of the employee
     * @return the list of roles of the employee
     * for HRMenuRemoveRoleFromEmployee
     */
    public List<IRole> getRolesById(int id) {
        Employee employee = findEmployeeByID(id);
        if (employee == null) {
            return null;
        }
        return employee.getRoles();
    }

    /**
     * @param store_name - the name of the store
     * @return the store with the given name
     */
    public Store findStoreByName(String store_name) {
        if (store_name == null)
            return null;
        for (Store store : this.m_stores) {
            if (store.getName().equals(store_name)) {
                return store;
            }
        }
        return null;
    }

    /**
     * @param id_employee - the id of the employee
     * @param store_name - the name of the store
     * @return
     */
    public boolean removeEmployeeFromStore(int id_employee, String store_name) {
        if (id_employee <0 || store_name == null)
            return false;
        Store store = findStoreByName(store_name);
        if (store == null) {
            return false;
        }
        Employee employee = findEmployeeByID(id_employee);
        if (employee == null) {
            return false;
        }
        store.removeEmployee(employee);
        return true;
    }

    /**
     * @param id_employee - the id of the employee
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeEmployee(int id_employee) {
        if (id_employee <0)
            return false;
        Employee employee = findEmployeeByID(id_employee);
        if (employee == null) {
            return false;
        }
        List<Store> stores = employee.getStores();
        for (Store store : stores) {
            if (!store.removeEmployee(employee))
                return false;
        }
        this.m_employees.remove(employee);
        return true;
    }

    /**
     * @param store_name - the name of the store
     * @return true if the store was removed successfully, false otherwise
     */
    public boolean removeStore(String store_name) {
        if (store_name == null)
            return false;
        Store store = findStoreByName(store_name);
        if (store == null) {
            return false;
        }
        List<Employee> employees = store.getEmployees();
        for (Employee employee : employees) {
            if (!employee.removeStore(store))
                return false;
        }
        this.m_stores.remove(store);
        return true;
    }

    /**
     * @param id_employee - the id of the employee
     * @param role - the role to remove
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRoleFromEmployee(int id_employee, IRole role) {
        if (id_employee <0 || role == null)
            return false;
        Employee employee = findEmployeeByID(id_employee);
        if (employee == null)
            return false;
        for (Store store : employee.getStores()) {
            if (!store.removeRoleFromEmployee(role, employee))
                return false;
        }
        return employee.getRoles().remove(role);
    }

    public boolean removeRoleFromShift(Shift shift,IRole role){
        if (shift == null || role == null)
            return false;
        return shift.removeRequiredRole(role);
    }


    public boolean approveSchedule(Schedule schedule){
        if (schedule == null)
            return false;
        for (int i = 0; i < 14; i++) {

        }
        return true;
    }

    public boolean addRoleToShift(Shift shift,IRole role){
        if (shift == null || role == null)
            return false;
        return shift.addRequiredRole(role);
    }
}
