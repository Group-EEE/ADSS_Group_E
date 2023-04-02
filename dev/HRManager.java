
import java.util.List;


import java.util.ArrayList;

public class HRManager extends AEmployee{
    private List<Employee> m_employees = new ArrayList<Employee>();
    private List<Store> m_stores = new ArrayList<Store>();

    public HRManager(String first_name, String last_name, int age, int id, String bank_account,String password) {
        super(first_name, last_name, age, id, bank_account);
        Login.createUser(id, password, this);
    }

    /**
     * @param id - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public String getEmployeeFirstNameById(int id){
        if (id < 0)
            return null;
        return findEmployeeByID(id).get_first_name();
    }

    /**
     * @param store_name - the name of the store
     * @return - the store with the given name, null if the name is invalid
     */
    public Store getStoreByName(String store_name){
        if (store_name == null)
            return null;
        for (Store store : m_stores){
            if (store.getName().equals(store_name))
                return store;
        }
        return null;
    }

    /**
     * @param first_name - the first name of the employee
     * @param last_name - the last name of the employee
     * @param age - the age of the employee
     * @param id - the id of the employee
     * @param bank_account - the bank account of the employee
     * @param password - the password of the employee
     * @return - true if the employee was created successfully, false otherwise
     */
    public boolean createEmployee(String first_name, String last_name, int age, int id, String bank_account, String password) {
        if (first_name == null || last_name == null || age < 0 || id < 0 || bank_account == null)
            return false;
        Employee new_employee = new Employee(first_name, last_name, age, id, bank_account);
        m_employees.add(new_employee);
        if (!Login.createUser(id, password, new_employee))
            return false;
        return true;
    }

    /**
     * @param store_name - the name of the store
     * @param store_address - the address of the store
     * @return - true if the store was created successfully, false otherwise
     */
    public boolean createStore(String store_name, String store_address) {
        if (store_name == null || store_address == null)
            return false;
        m_stores.add(new Store(store_name, store_address));
        return true;
    }

    /**
     * @param id_employee - the id of the employee
     * @param role - the role of the employee
     * @return - true if the role was added successfully, false otherwise
     */
    public boolean addRoleToEmployee(int id_employee, ARole role) {
        if (id_employee <0 || role == null)
            return false;
        Employee employee = findEmployeeByID(id_employee);
        List<Store> stores = employee.getStores(); 
        List<ARole> roles = employee.getRoles();
        roles.add(role);
        for (Store store : stores) {
            if (!store.updateRoles(employee))
                return false;
        }
        return employee.setRole(role);
    }

    /**
     * @param id_employee - the id of the employee
     * @param store_name - the name of the store
     * @return - true if the employee was added successfully, false otherwise
     */
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
        return store_obj.addEmployee(employee);
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
    public List<ARole> getRolesById(int id) {
        if (id <0)
            return null;
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
    public boolean removeRoleFromEmployee(int id_employee, ARole role) {
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

    /**
     * @param shift - the shift to remove the role from
     * @param role - the role to remove
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRoleFromShift(Shift shift,ARole role){
        if (shift == null || role == null)
            return false;
        return shift.removeRequiredRole(role);
    }


    /**
     * @param schedule - the schedule to approve
     * @return the list of shifts that were rejected
     */
    public List<Shift> approveSchedule(Schedule schedule){
        if (schedule == null)
            return null;
        List<Shift> rejectedShifts = new ArrayList<Shift>();

        for (int i = 0; i < 14; i++) {
            Shift shift = schedule.getShift(i);
            if (!approveShift(shift))
                rejectedShifts.add(shift);
        }
        return rejectedShifts;
    }

    /**
     * @param shift - the shift to approve
     * @return true if the shift was approved successfully, false otherwise
     */
    public boolean approveShift(Shift shift){
        if (shift == null)
            return false;
        if (shift.isApproved() || shift.isRejected()) //shift was already approved or canceled.
            return false;
        //first we assign all the employee that has only one match.
        for (Employee employee : shift.getInquiredEmployees()){
            ARole role = findMatch(shift, employee);
            if (role != null){
                //we found a match, we remove the employee from the seaching list and add the role to the
                if (!shift.addFilledRole(role, employee))
                    return false;
            }
        }
        for (ARole role : shift.getRequiredRoles()) {
            if (role.hasEmployee())
                continue;
            for (Employee employee : shift.getInquiredEmployees()) {
                if (employee.getRoles().contains(role)){
                    if (!shift.addFilledRole(role, employee))
                        return false;
                    break;
                }
            }
        }
        if (shift.getRequiredRoles().size() == 0){
            shift.setApproved(true);
        }
        else
            return false;
        return true;
    }

    /**
     * @param shift - the shift to check possible
     * @param employee - the employee to add to the shift
     * @return
     */
    public ARole findMatch(Shift shift, Employee employee){
        if (shift == null || employee == null)
            return null;
        int counter = 0;
        ARole lastRole = null;
        for (ARole Role : employee.getRoles()) {
            if (shift.getRequiredRoles().contains(Role)){
                counter++;
                lastRole = Role;
            }
        }
        if (counter == 1)
            return lastRole;
        return null;
    }

    public List<ARole> shiftHasMissingRequiredRole(Shift shift){
        if (shift == null)
            return null;
        return shift.getRequiredRoles();
    }


    /**
     * @param shift - the shift to add the required role
     * @param role - the role to add to the shift
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRoleToShift(Shift shift, ARole role){
        if (shift == null || role == null)
            return false;
        return shift.addRequiredRole(role);
    }
}
