
public abstract class AEmployee {
    protected int m_id;
    protected String m_first_name;
    protected String m_last_name;
    protected int m_age;
    protected String m_bank_account;
    public AEmployee(String first_name, String last_name, int age, int id, String bank_account) {
        this.m_first_name = first_name;
        this.m_last_name = last_name;
        this.m_age = age;
        this.m_id = id;
        this.m_bank_account = bank_account;
    }
    public int getID(){
        return this.m_id;
    }
    public boolean set_new_first_name(String first_name){
        if (first_name == null)
            return false;
        m_first_name = first_name;
        return true;
    }
    public boolean set_new_last_name(String last_name){
        if (last_name == null)
            return false;
        m_last_name = last_name;
        return true;
    }
    public boolean set_new_bank_account(String bank_account){
        if (bank_account == null)
            return false;
        m_bank_account = bank_account;
        return true;
    }
}