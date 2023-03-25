import java.util.Map;

public class Employee {
    private String m_first_name;
    private String m_last_name;
    private int m_age;
    private int m_id;
    private int m_bank_account;private Map<String,int>[]time;
    private boolean m_isManager = false;
    private boolean m_isWearhouse = false;
    private boolean m_isGeneral = false;
    private boolean m_isCashier = false;

    public Employee(String first_name, String last_name, int age, int id, int bank_account) {
        this.m_first_name = first_name;
        this.m_last_name = last_name;
        this.m_age = age;
        this.m_id = id;
        this.m_bank_account = bank_account;
    }

    public boolean canWork(Map<String,int>[] arr_can_work){
        this.time = arr_can_work;
    }

    public boolean isManager() {
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