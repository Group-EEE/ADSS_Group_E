package DataAccessLayer.HRMoudle;

public class EmployeesToRolesDAO {
    private static EmployeesToRolesDAO _employeesToRolesDAO = null;

    private EmployeesToRolesDAO(){
    }

    public static EmployeesToRolesDAO getInstance(){
        if (_employeesToRolesDAO == null)
            _employeesToRolesDAO = new EmployeesToRolesDAO();
        return _employeesToRolesDAO;
    }

}
