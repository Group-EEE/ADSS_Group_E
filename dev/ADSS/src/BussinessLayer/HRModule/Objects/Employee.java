package BussinessLayer.HRModule.Objects;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class Employee{
    private final List<RoleType> _roles = new ArrayList<RoleType>();
    private final int _id;
    private String _firstName;
    private String _lastName;
    private int _age;
    private String _bankAccount;
    private int _salary;
    private String _hiringCondition;
    private LocalDate _startDateOfEmployement;
    private boolean _finishWorking;
    public Employee(String firstName, String lastName, int age, int id, String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment) {
        this._firstName = firstName;
        this._lastName = lastName;
        this._age = age;
        this._id = id;
        this._bankAccount = bankAccount;
        this._salary = salary;
        this._startDateOfEmployement = startDateOfEmployment;
        this._finishWorking = false;
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

    /**
     * @return the first name of the employee
     */
    public String getFullNameName(){
        return this._firstName+" "+this._lastName;
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

    public String getFirstName(){
        return this._firstName;
    }

    public String getLastName(){
        return this._lastName;
    }

    public int getAge(){
        return this._age;
    }

    public String getBankAccount(){
        return this._bankAccount;
    }

    public int getSalary(){
        return this._salary;
    }

    public String getHiringCondition(){
        return this._hiringCondition;
    }

    public LocalDate getStartDateOfEmployement(){
        return this._startDateOfEmployement;
    }

    public boolean getFinishWorking(){
        return this._finishWorking;
    }


}