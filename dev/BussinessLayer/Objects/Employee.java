package BussinessLayer.Objects;

import java.util.List;
import java.util.ArrayList;

public class Employee{
    //private final List<Store> _stores = new ArrayList<Store>();
    private final List<RoleType> _roles = new ArrayList<RoleType>();
    private final int _id;
    private String _firstName;
    private String _lastName;
    private int _age;
    private String _bankAccount;

    public Employee(String firstName, String lastName, int age, int id, String bankAccount) {
        this._firstName = firstName;
        this._lastName = lastName;
        this._age = age;
        this._id = id;
        this._bankAccount = bankAccount;
    }

    /**
     * @param firstName - the new first name of the employee
     * @return true if the first name was changed successfully, false otherwise
     */
    public boolean setNewFirstName(String firstName){
        if (firstName == null)
            return false;
        _firstName = firstName;
        return true;
    }

    /**
     * @param lastName - the new last name of the employee
     * @return true if the last name was changed successfully, false otherwise
     */
    public boolean setNewLastName(String lastName){
        if (lastName == null)
            return false;
        _lastName = lastName;
        return true;
    }

    /**
     * @param bankAccount - the new bank account of the employee
     * @return true if the bank account was changed successfully, false otherwise
     */
    public boolean setNewBankAccount(String bankAccount){
        if (bankAccount == null)
            return false;
        _bankAccount = bankAccount;
        return true;
    }

//    /**
//     * @param store - the store to add to the employee
//     * @return true if the store was added successfully, false otherwise
//     */
//    public boolean addStore(Store store){
//        this._stores.add(store);
//        return store.addEmployee(this);
//    }

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
    public boolean addRole(RoleType role){
        if (role == null)
            return false;
        _roles.add(role);
        return true;
    }

    /**
     * @param roleIndex - the index of the role to remove from the employee
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRole(int roleIndex){
        if (roleIndex < 1 || roleIndex > this._roles.size())
            throw new IllegalArgumentException("Illegal role index");
        this._roles.remove(roleIndex-1);
        return true;
    }

    /**
     * @return the list of all roles of the employee
     */
    public List<RoleType> getRoles(){
        return this._roles;
    }

//    /**
//     * @return the list of all stores of the employee
//     */
//    public List<Store> getStores(){
//        return this._stores;
//    }

    /**
     * @param store - the store to remove from the employee
     * @return true if the store was removed successfully, false otherwise
     */
//    public boolean removeStore(Store store){
//        if (store == null)
//            throw new IllegalArgumentException("Illegal store");
//        this._stores.remove(store);
//        return store.removeEmployee(this);
//    }
//
//    public boolean checkIfEmployeeWorkInStore(Store store){
//        if (store == null)
//            return false;
//        return this._stores.contains(store);
//    }

    public String toString(){
        String employeeToString = "Employee: " + this._firstName + " " + this._lastName + ", ID: " + this._id+", age: "+this._age;
        employeeToString += ", Roles: ";
        for (RoleType role : this._roles){
            employeeToString += role + ", ";
        }
        return employeeToString;
    }

    public int getID(){
        return this._id;
    }
}