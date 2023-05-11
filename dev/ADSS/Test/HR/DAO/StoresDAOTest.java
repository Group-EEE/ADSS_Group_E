package HR.DAO;

import DataAccessLayer.HRMoudle.StoresDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoresDAOTest {
    static StoresDAO _storesDAO;

    @BeforeAll
    static void setUpAll() {
        _storesDAO = StoresDAO.getInstance();
    }

    @BeforeEach
    void setUp() {
        _storesDAO.insertStore("testName", "testAddress", "testPhone", "testContactName",0);
    }
    @AfterEach
    void tearDown() {
        _storesDAO.deleteStore("testName");
    }

    @Test
    void getStore(){
        assertNotNull(_storesDAO.getStore("testName"));
        assertThrows(IllegalArgumentException.class, () -> {_storesDAO.getStore("testStore2");});
    }

    @Test
    void isAnyStoreExist(){
        assertTrue(_storesDAO.isAnyStoreExist());
    }

    @Test
    void isStoreInArea(){
        assertTrue(_storesDAO.isStoreInArea("testName",0));
        assertFalse(_storesDAO.isStoreInArea("testName",1));
    }

    @Test
    void getStoreNamesByEmployeeID(){
        assertEquals(_storesDAO.getStoreNamesByEmployeeID(9999).size(),0);
        _storesDAO.insertEmployeeToStore(9999,"testName");
        assertEquals(_storesDAO.getStoreNamesByEmployeeID(9999).size(),1);
    }

    @Test
    void getEmployeesIDByStoreName(){
        assertEquals(_storesDAO.getEmployeesIDByStoreName("testName").size(),0);
        _storesDAO.insertEmployeeToStore(9999,"testName");
        assertEquals(_storesDAO.getEmployeesIDByStoreName("testName").size(),1);
        assertEquals(_storesDAO.getEmployeesIDByStoreName("testName").get(0),9999);
    }

    @Test
    void checkIfEmployeeInStore(){
        assertFalse(_storesDAO.checkIfEmployeeInStore(9999,"testName"));
        _storesDAO.insertEmployeeToStore(9999,"testName");
        assertTrue(_storesDAO.checkIfEmployeeInStore(9999,"testName"));
        _storesDAO.deleteEmployeeFromStore(9999,"testName");
        assertFalse(_storesDAO.checkIfEmployeeInStore(9999,"testName"));
    }

    @Test
    void deleteActive(){
        _storesDAO.insertActiveSchedule("testName",9999);
        assertEquals(_storesDAO.getActiveSchedule("testName"),9999);
        _storesDAO.deleteActive("testName");
        assertThrows(IllegalArgumentException.class, () -> {_storesDAO.getActiveSchedule("testName");});
    }



}