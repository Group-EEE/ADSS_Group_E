

import java.util.List;
import java.util.ArrayList;
import Roles.*;

public class Employee extends AEmployee{
    private List<Store> m_stores = new ArrayList<Store>();
    private List<IRole> m_roles = new ArrayList<IRole>(); 


    public Employee(String first_name, String last_name, int age, int id,String bank_account) {
        super(first_name, last_name, age, id, bank_account);
    }
    
    public boolean addStore(Store store){
        this.m_stores.add(store);
        return store.addEmployee(this);
    }
    public List<IRole> getRoles(){
        return this.m_roles;
    }
    public List<Store> getStores(){
        return this.m_stores;
    }
}