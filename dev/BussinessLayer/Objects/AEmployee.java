package BussinessLayer.Objects;

public abstract class AEmployee {

    protected int _id;
    protected String _firstName;
    protected String _lastName;
    protected int _age;
    protected String _bankAccount;

    public AEmployee(String firstName, String lastName, int age, int id, String bankAccount) {
        this._firstName = firstName;
        this._lastName = lastName;
        this._age = age;
        this._id = id;
        this._bankAccount = bankAccount;
    }

    /**
     * @return the employee's id
     */
    public int getID(){
        return this._id;
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
}