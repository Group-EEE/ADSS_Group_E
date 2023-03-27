import java.util.HashMap;
public class Login {
    private static HashMap<Integer,String> m_users;
    public static boolean createUser(int id, String password){
        if (id < 0 || password == null)
            return false;
        m_users.put(id, password);
        return true;
    }
    public static boolean login(int id, String password){
        if (id < 0 || password == null)
            return false;
        if (m_users.containsKey(id) && m_users.get(id).equals(password))
            return true;
        return false;
    }
    public static boolean removeUser(int id){
        if (id < 0)
            return false;
        if (m_users.containsKey(id)){
            m_users.remove(id);
            return true;
        }
        return false;
    }
    public static boolean changePassword(int id, String new_password){
        if (id < 0 || new_password == null)
            return false;
        if (m_users.containsKey(id)){
            m_users.replace(id, new_password);
            return true;
        }
        return false;
    }
}
