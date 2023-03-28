import java.util.HashMap;

import Employee.AEmployee;
public class Login {
    public static HashMap<Integer,String> m_passwords = new HashMap<Integer,String>();
    public static HashMap<Integer,AEmployee> m_employees = new HashMap<Integer,AEmployee>();
    public static boolean createUser(int id, String password, AEmployee employee){
        if (id < 0 || password == null)
            return false;
        m_passwords.put(id, password);
        m_employees.put(id, employee);
        return true;
    }
    public static AEmployee login(int id, String password){
        if (id < 0 || password == null)
            return null;
        if (m_employees.containsKey(id) && m_passwords.containsKey(id) && m_passwords.get(id).equals(password))
            return m_employees.get(id);
        return null;
    }
    public static boolean removeUser(int id){
        if (id < 0)
            return false;
        if (m_employees.containsKey(id) && m_passwords.containsKey(id)){
            m_employees.remove(id);
            m_passwords.remove(id);
            return true;
        }
        return false;
    }
    public static boolean changePassword(int id, String new_password){
        if (id < 0 || new_password == null)
            return false;
        if (m_passwords.containsKey(id)){
            m_passwords.replace(id, new_password);
            return true;
        }
        return false;
    }
}
