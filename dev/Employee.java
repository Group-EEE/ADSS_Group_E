import java.util.List;
import java.util.Set;

public class Employee extends AEmployee{
    private List<Store> m_stores;
    private Set<roleType> m_roles; 


    public Employee(String first_name, String last_name, int age, int id,String bank_account) {
        super(first_name, last_name, age, id, bank_account);
    }
    
    public boolean addStore(Store store){
        this.m_stores.add(store);
        return store.addEmployee(this);
    }
    public Set<roleType> getRoles(){
        return this.m_roles;
    }
    public boolean setRoles(roleType role) {
        if (role == null)
        this.m_roles.add(role);
        for (Store obj_store : this.m_stores) {
            obj_store.updateRoles(this);
        }
        return true;
    }
}