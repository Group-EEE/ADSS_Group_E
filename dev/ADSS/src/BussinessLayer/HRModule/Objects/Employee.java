package BussinessLayer.HRModule.Objects;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class Employee{
    protected final List<RoleType> _roles = new ArrayList<RoleType>();
    protected final int _employeeID;
    protected String _firstName;
    protected String _lastName;
    protected int _age;
    protected String _bankAccount;
    protected int _salary;
    protected String _hiringCondition;
    protected LocalDate _startDateOfEmployement;
    protected boolean _finishWorking;
    protected String _password;
    public Employee(int employeeID, String firstName, String lastName, int age , String bankAccount, int salary, String hiringCondition, LocalDate startDateOfEmployment,String password) {
        this._employeeID = employeeID;
        this._firstName = firstName;
        this._lastName = lastName;
        this._age = age;
        this._bankAccount = bankAccount;
        this._salary = salary;
        this._hiringCondition = hiringCondition;
        this._startDateOfEmployement = startDateOfEmployment;
        this._finishWorking = false;
        this._password = password;
    }

    /**
     * @param firstName - the new first name of the employee
     * @return true if the first name was changed successfully, false otherwise
     */
    public boolean setNewFirstName(String firstName){
        if (firstName == null)
            throw new IllegalArgumentException("First name cannot be null");
        _firstName = firstName;
        return true;
    }

    /**
     * @param lastName - the new last name of the employee
     * @return true if the last name was changed successfully, false otherwise
     */
    public boolean setNewLastName(String lastName){
        if (lastName == null)
            throw new IllegalArgumentException("Last name cannot be null");
        _lastName = lastName;
        return true;
    }

    /**
     * @param bankAccount - the new bank account of the employee
     * @return true if the bank account was changed successfully, false otherwise
     */
    public boolean setNewBankAccount(String bankAccount){
        if (bankAccount == null)
            throw new IllegalArgumentException("Bank account cannot be null");
        _bankAccount = bankAccount;
        return true;
    }

    public boolean setNewPassword(String password){
        if (password == null)
            throw new IllegalArgumentException("Password cannot be null");
        _password = password;
        return true;
    }


    public boolean setFinishedWorking(){
        this._finishWorking = true;
        return true;
    }

    /**
     * @return the first name of the employee
     */
    public String getFullName(){
        return this._firstName+" "+this._lastName;
    }

    /**
     * @param role - the role to add to the employee
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRole(RoleType role){
        if (role == null)
            throw new IllegalArgumentException("Role cannot be null");
        return _roles.add(role);
    }

    /**
     * @param roleIndex - the index of the role to remove from the employee
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRole(int roleIndex){
        if (roleIndex < 0 || roleIndex > this._roles.size()-1)
            throw new IllegalArgumentException("Illegal role index");
        this._roles.remove(roleIndex);
        return true;
    }

    /**
     * @return the list of all roles of the employee
     */
    public List<RoleType> getRoles(){
        return this._roles;
    }


    public String toString(){
        String employeeToString = "Employee: " + this._firstName + " " + this._lastName + ", ID: " + this._employeeID+", age: "+this._age;
        if (_roles.size() == 0)
            return employeeToString;
        employeeToString += ", Roles: ";
        for (RoleType role : this._roles){
            employeeToString += role + ", ";
        }
        return employeeToString.substring(0, employeeToString.length()-2);
    }

    public int getEmployeeID(){
        return this._employeeID;
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

    public boolean getFinishedWorking(){
        return this._finishWorking;
    }

    public String getPassword(){
        return this._password;
    }


    public boolean hasRole(RoleType role){
        return this._roles.contains(role);
    }
}