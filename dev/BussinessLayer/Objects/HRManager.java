package BussinessLayer.Objects;

import BussinessLayer.Controllers.EmployeeController;

import java.util.List;


import java.util.ArrayList;

public class HRManager extends AEmployee{


    private static HRManager _HRManager = null;

    private HRManager(String first_name, String last_name, int age, int id, String bank_account,String password) {
        super(first_name, last_name, age, id, bank_account);
        //EmployeeController.createUser(id, password, this);
    }

    public static HRManager getInstace(){
        return _HRManager;
    }

    public static void createHRManager(String first_name, String last_name, int age, int id, String bank_account,String password){
        if (first_name == null || last_name == null || age < 0 || id < 0 || bank_account == null || password == null)
            return;
        _HRManager = new HRManager(first_name, last_name, age, id, bank_account, password);
    }

    /**
     * @param id - the id of the employee
     * @return - the employee with the given id, null if the id is invalid
     */
    public String getEmployeeFirstNameById(int id){
        if (id < 0)
            return null;
        return findEmployeeByID(id).getFirstName();
    }



    /**
     * @param id_employee - the id of the employee
     * @param role - the role of the employee
     * @return - true if the role was added successfully, false otherwise
     */
    public boolean addRoleToEmployee(int id_employee, RoleType role) {
        if (id_employee <0 || role == null)
            return false;
        Employee employee = findEmployeeByID(id_employee);
        List<Store> stores = employee.getStores(); 
        List<RoleType> roles = employee.getRoles();
        roles.add(role);
        for (Store store : stores) {
            if (!store.updateRoles(employee))
                return false;
        }
        return true;
    }

    /**
     * @param id_employee - the id of the employee
     * @param store_name - the name of the store
     * @return - true if the employee was added successfully, false otherwise
     */
    public boolean addEmployeeToStore(int id_employee, Store store) {
        if (id_employee <0 || store == null)
            return false;
        Employee employee = findEmployeeByID(id_employee);
        if (employee == null) {
            return false;
        }
        return store.addEmployee(employee);
    }

    /**
     * @param id - the id of the employee
     * @return the employee with the given id
     */
    public Employee findEmployeeByID(int id) {
        if (id <0)
            return null;
        for (Employee employee : this._employees) {
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
    public List<RoleType> getRolesById(int id) {
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
        for (Store store : this._stores) {
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
        this._employees.remove(employee);
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
        this._stores.remove(store);
        return true;
    }

    /**
     * @param id_employee - the id of the employee
     * @param role - the role to remove
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRoleFromEmployee(int id_employee, RoleType role) {
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
    public boolean removeRoleFromShift(Shift shift,RoleType role){
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
            RoleType role = findMatch(shift, employee);
            if (role != null){
                //we found a match, we remove the employee from the seaching list and add the role to the
                if (!shift.addFilledRole(role, employee))
                    return false;
            }
        }
        for (RoleType role : shift.getRequiredRoles()) {
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
    public RoleType findMatch(Shift shift, Employee employee){
        if (shift == null || employee == null)
            return null;
        int counter = 0;
        RoleType lastRole = null;
        for (RoleType Role : employee.getRoles()) {
            if (shift.getRequiredRoles().contains(Role)){
                counter++;
                lastRole = Role;
            }
        }
        if (counter == 1)
            return lastRole;
        return null;
    }

    public List<RoleType> shiftHasMissingRequiredRole(Shift shift){
        if (shift == null)
            return null;
        return shift.getRequiredRoles();
    }


    /**
     * @param shift - the shift to add the required role
     * @param role - the role to add to the shift
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRoleToShift(Shift shift, RoleType role){
        if (shift == null || role == null)
            return false;
        return shift.addRequiredRole(role);
    }
}
