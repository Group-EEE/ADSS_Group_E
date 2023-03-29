
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
}