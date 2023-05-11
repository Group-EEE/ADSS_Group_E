package HR.Controllers;

import BussinessLayer.HRModule.Controllers.StoreController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StoreControllerTest {

    StoreController _storeController = StoreController.getInstance();

    @BeforeEach
    void setUp() {
        _storeController.createStore("testName", "testAddress", "testPhone", "testContactName",0);
    }

    @AfterEach
    void tearDown() {
        _storeController.removeStore("testName");
    }

    @Test
    void createStore() {
        assertThrows(IllegalArgumentException.class,() -> _storeController.createStore(null, "testAddress", "testPhone", "testContactName",0));
        assertThrows(IllegalArgumentException.class,() -> _storeController.createStore("TestStore2", null, "testPhone", "testContactName",0));
        assertThrows(IllegalArgumentException.class,() -> _storeController.createStore("TestStore2", "testAddress", null, "testContactName",0));
        assertThrows(IllegalArgumentException.class,() -> _storeController.createStore("TestStore2", "testAddress", "testPhone", null,0));
        assertThrows(IllegalArgumentException.class,() -> _storeController.createStore("TestStore2", "testAddress", "testPhone", "testContactName",-1));
        assertThrows(IllegalArgumentException.class,() -> _storeController.createStore("testName", "testAddress", "testPhone", "testContactName",0));
    }

    @Test
    void existsStore(){
        assertThrows(IllegalArgumentException.class,() -> _storeController.existsStore(null));
        assertTrue(_storeController.existsStore("testName"));
        assertFalse(_storeController.existsStore("testName2"));
    }

    @Test
    void getStore(){
        assertThrows(IllegalArgumentException.class,() -> _storeController.getStore(null));
        assertNotNull(_storeController.getStore("testName"));
        assertThrows(IllegalArgumentException.class, () ->{_storeController.getStore("testName2");});
    }

    @Test
    void addEmployeeToStore(){
        assertThrows(IllegalArgumentException.class,() -> _storeController.addEmployeeToStore(-1, "testName"));
        assertThrows(IllegalArgumentException.class,() -> _storeController.addEmployeeToStore(1, null));
        assertTrue(_storeController.addEmployeeToStore(1, "testName"));
    }

    @Test
    void removeEmployeeFromStore(){
        assertThrows(IllegalArgumentException.class,() -> _storeController.removeEmployeeFromStore(-1,"testName"));
        assertThrows(IllegalArgumentException.class,() -> _storeController.removeEmployeeFromStore(1,null));
        assertThrows(IllegalArgumentException.class,() -> _storeController.removeEmployeeFromStore( 1,"testName"));
        addEmployeeToStore();
        assertTrue(_storeController.removeEmployeeFromStore(1,"testName"));
    }

    @Test
    void removeEmployee(){
        assertThrows(IllegalArgumentException.class,() -> _storeController.removeEmployee(-1));
        assertTrue(_storeController.removeEmployee(1));
    }

    @Test
    void removeStore(){
        assertThrows(IllegalArgumentException.class,() -> _storeController.removeStore(null));
        assertTrue(_storeController.removeStore("testName"));
    }

    @Test
    void getAllStores(){
        assertTrue(_storeController.getAllStores().size()>0);
    }


}
