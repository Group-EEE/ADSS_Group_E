package DataAccessLayer.HRMoudle;

public class PasswordDAO {
    private static PasswordDAO _passwordDAO = null;

    private PasswordDAO(){
    }

    public static PasswordDAO getInstance(){
        if (_passwordDAO == null)
            _passwordDAO = new PasswordDAO();
        return _passwordDAO;
    }
}
