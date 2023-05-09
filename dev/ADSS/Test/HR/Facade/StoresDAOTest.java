package HR.Facade;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Store;
import DataAccessLayer.HRMoudle.SchedulesDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StoresDAOTest {
    static Facade _facade;

    @BeforeAll
    static void setUpAll() {
        _facade = Facade.getInstance();
    }

    @BeforeEach
    void setUp() {
        _facade.createStore("testName", "testAddress", "testPhone", "testContactName",0);
    }
    @AfterEach
    void tearDown() {
        _facade.removeStore("testName");
    }

    @Test
    void createSchedule(){
        assertTrue(_facade.createNewSchedule("testName", 11, 12, 2021));
        assertTrue(_facade.deleteSchedule("testName"));
    }

}