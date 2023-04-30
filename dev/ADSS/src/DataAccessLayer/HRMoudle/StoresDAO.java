package DataAccessLayer.HRMoudle;

public class StoresDAO {
    private static StoresDAO _storesDAO = null;

    private StoresDAO() {
    }

    public static StoresDAO getInstance() {
        if (_storesDAO == null)
            _storesDAO = new StoresDAO();
        return _storesDAO;
    }


}
