package ServiceLayer.objectsServices;

import BussinessLayer.Controllers.Facade;

public class StoreService {
    private static StoreService _storeService;
    private final Facade _facade;
    private StoreService(){_facade = Facade.getInstance();}
    public static StoreService getInstance(){
        if(_storeService == null)
            _storeService = new StoreService();
        return _storeService;
    }

    //_storeController
    public boolean createStore(int storeId, String storeName, String storeAddress){
        return _facade.createStore(storeId, storeName, storeAddress);
    }

    public boolean removeStore(String storeName){
        return _facade.removeStore(storeName);
    }

    public boolean addEmployeeToStore(int employeeID, String storeName){
        return _facade.addEmployeeToStore(employeeID, storeName);
    }

    public boolean removeEmployeeFromStore(int employeeID, String storeName){
        return _facade.removeEmployeeFromStore(employeeID, storeName);
    }

    public boolean printStores(){
        return _facade.printStores();
    }
}
