

import java.util.List;
import java.util.ArrayList;

public class Employee extends AEmployee{
    private List<Store> _stores = new ArrayList<Store>();
    private List<ARole> _roles = new ArrayList<ARole>();


    public Employee(String first_name, String last_name, int age, int id,String bank_account) {
        super(first_name, last_name, age, id, bank_account);
    }
    
    public boolean addStore(Store store){
        this._stores.add(store);
        return store.addEmployee(this);
    }

    public String get_first_name(){
        return this._firstName;
    }
    public boolean setRole(ARole role){
        if (role == null)
            return false;
        _roles.add(role);
        return true;
    }
    public List<ARole> getRoles(){
        return this._roles;
    }

    public List<Store> getStores(){
        return this._stores;
    }

    public boolean removeStore(Store store){
        if (store == null)
            return false;
        this._stores.remove(store);
        return store.removeEmployee(this);
    }

    public boolean hasRole(ARole role){
        if (role == null)
            return false;
        for (ARole r : _roles){
            if (r.equals(role))
                return true;
        }
        return false;
    }
}