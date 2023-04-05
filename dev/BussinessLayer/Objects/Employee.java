package BussinessLayer.Objects;

import java.util.List;
import java.util.ArrayList;

public class Employee extends AEmployee{
    private List<Store> _stores = new ArrayList<Store>();
    private List<RoleType> _roles = new ArrayList<RoleType>();


    public Employee(String firstName, String lastName, int age, int id, String bankAccount) {
        super(firstName, lastName, age, id, bankAccount);
    }

    /**
     * @param store - the store to add to the employee
     * @return true if the store was added successfully, false otherwise
     */
    public boolean addStore(Store store){
        this._stores.add(store);
        return store.addEmployee(this);
    }

    /**
     * @return the first name of the employee
     */
    public String getFirstName(){
        return this._firstName;
    }

    /**
     * @param role - the role to add to the employee
     * @return true if the role was added successfully, false otherwise
     */
    public boolean setRole(RoleType role){
        if (role == null)
            return false;
        _roles.add(role);
        return true;
    }

    /**
     * @return the list of all roles of the employee
     */
    public List<RoleType> getRoles(){
        return this._roles;
    }

    /**
     * @return the list of all stores of the employee
     */
    public List<Store> getStores(){
        return this._stores;
    }

    /**
     * @param store - the store to remove from the employee
     * @return true if the store was removed successfully, false otherwise
     */
    public boolean removeStore(Store store){
        if (store == null)
            return false;
        this._stores.remove(store);
        return store.removeEmployee(this);
    }

    public boolean hasJob(RoleType role){
        if (role == null)
            return false;
        for (RoleType r : _roles){
            if (r.equals(role))
                return true;
        }
        return false;
    }
}