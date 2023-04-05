package BussinessLayer.Controllers;

import BussinessLayer.Objects.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreController {
    private HashMap<Integer,Store> _storesByID = new HashMap<Integer, Store>();
    private HashMap<String,Store> _storesByName = new HashMap<String, Store>();
    private static StoreController _storeController = null;
    public StoreController(){

    }

    public static StoreController getInstance(){
        if (_storeController == null)
            _storeController = new StoreController();
        return _storeController;
    }

    /**
     * @param storeName - the name of the store
     * @param storeAddress - the address of the store
     * @return - true if the store was created successfully, false otherwise
     */
    public boolean createStore(int storeID, String storeName, String storeAddress) {
        if (storeName == null || storeAddress == null)
            return false;
        Store newStore = new Store(storeID,storeName, storeAddress));
        _storesByID.put(storeID, newStore);
        _storesByName.put(storeName, newStore);
        return true;
    }

    /**
     * @param store_id - the name of the store
     * @return - the store with the given name, null if the name is invalid
     */
    public Store getStoreByID(int store_id){
        if (store_id <0)
            return null;
        for (Map.Entry<Integer, Store> entry : _storesByID.entrySet()){
            if (entry.getKey().equals(store_id))
                return entry.getValue();
        }
        return null;
    }

    /**
     * @param storeName - the name of the store
     * @return - the store with the given name, null if didn't find the Store
     */
    public Store getStoreByName(String storeName){
        if (storeName == null)
            return null;
        for (Map.Entry<String, Store> entry : _storesByName.entrySet()){
            if (entry.getKey().equals(storeName))
                return entry.getValue();
        }
        return null;
    }
}
