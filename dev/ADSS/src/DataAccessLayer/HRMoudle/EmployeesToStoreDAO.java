package DataAccessLayer.HRMoudle;

public class EmployeesToStoreDAO {
    private static EmployeesToStoreDAO _employeeToStoreDAO = null;

    private EmployeesToStoreDAO(){
        _employeeToStoreDAO = new EmployeesToStoreDAO();
    }

    public EmployeesToStoreDAO getInstance(){
        if (_employeeToStoreDAO == null)
            _employeeToStoreDAO = new EmployeesToStoreDAO();
        return _employeeToStoreDAO;
    }
}
