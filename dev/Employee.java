import java.util.Map;

public class Employee{
    private String first_name;
    private String last_name;
    private int age;
    private int id;
    private int bank_account;
    private Map<String,int>[] time;
    private boolean m_isManager = false;
    private boolean m_isWearhouse = false;
    private boolean m_isGeneral = false;
    private boolean m_isCashier = false;

    public Employee(String first_name, String last_name, int age, int id, int bank_account){
        this.first_name=first_name;
        this.last_name=last_name;
        this.age=age;
        this.id=id;
        this.bank_account=bank_account;
    }
    public boolean canWork(Map<String,int>[] arr_can_work){
        this.time = arr_can_work;
    }
    public boolean isManager(){
        return this.m_isManager;
    }
    public boolean isCashier() {
        return m_isCashier;
    }
    public boolean isWearhouse() {
        return m_isWearhouse;
    }
    public boolean isGeneral() {
        return m_isGeneral;
    }
    public void setManager(boolean manager) {
        m_isManager = manager;
    }
    public void setCashier(boolean cashier) {
        m_isCashier = cashier;
    }
    public void setWearhouse(boolean wearhouse) {
        m_isWearhouse = wearhouse;
    }
    public void setGeneral(boolean general) {
        m_isGeneral = general;
    }
}