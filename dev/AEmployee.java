
public abstract class AEmployee {
    protected int _id;
    protected String _firstName;
    protected String _lastName;
    protected int _age;
    protected String _bankAccount;
    public AEmployee(String first_name, String last_name, int age, int id, String bank_account) {
        this._firstName = first_name;
        this._lastName = last_name;
        this._age = age;
        this._id = id;
        this._bankAccount = bank_account;
    }
    public int getID(){
        return this._id;
    }
    public boolean set_new_first_name(String first_name){
        if (first_name == null)
            return false;
        _firstName = first_name;
        return true;
    }
    public boolean set_new_last_name(String last_name){
        if (last_name == null)
            return false;
        _lastName = last_name;
        return true;
    }
    public boolean set_new_bank_account(String bank_account){
        if (bank_account == null)
            return false;
        _bankAccount = bank_account;
        return true;
    }
}