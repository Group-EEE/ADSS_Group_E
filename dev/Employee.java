import java.util.List;
import java.util.Set;

public class Employee {
    private String m_first_name;
    private String m_last_name;
    private int m_age;
    private int m_id;
    private int m_bank_account;
    private List<Store> m_stores;
    private Set<roleType> m_roles; 


    public Employee(String first_name, String last_name, int age, int id, int bank_account) {
        this.m_first_name = first_name;
        this.m_last_name = last_name;
        this.m_age = age;
        this.m_id = id;
        this.m_bank_account = bank_account;
    }
    
    public boolean addStore(Store store){
        this.m_stores.add(store);
        return store.addEmployee(this);
    }
    public Set<roleType> getRoles(){
        return this.m_roles;
    }
    public boolean setRoles(roleType role) {
        this.m_roles.add(role);
        for (Store obj_store : this.m_stores) {
            obj_store.updateRoles(this);
        }
        return true;
    }
}