package HR.Controllers;

import BussinessLayer.HRModule.Controllers.ScheduleController;
import BussinessLayer.HRModule.Controllers.StoreController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleControllerTest {

    ScheduleController _scheduleController = ScheduleController.getInstance();
    StoreController _storeController = StoreController.getInstance();

    @BeforeEach
    void setUp() {
        _storeController.createStore("testStore", "testAddress", "testPhone", "testContact",0);
        _scheduleController.createNewStoreSchedule("testStore", 1,1,1999);
    }

    @AfterEach
    void tearDown() {
        _scheduleController.deleteSchedule("testStore");
        _storeController.removeStore("testStore");
    }

    @Test
    void createNewSchedule() {
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.createNewStoreSchedule(null, 1,1,1999);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.createNewStoreSchedule("testStoreNotExist", 1,1,1999);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.createNewStoreSchedule("testStoreNotExist", 1,1,1999);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.createNewStoreSchedule("testStore", 30,2,1999);
        });
    }

    @Test
    void deleteScheduleIDFromStore(){
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.deleteScheduleIDFromStore(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.deleteScheduleIDFromStore("testStoreNotExist");
        });
    }

    @Test
    void deleteSchedule(){
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.deleteSchedule(null);
        });
    }

    @Test
    void getSchedule(){
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.getSchedule(null);
        });
        _storeController.createStore("testStore2", "testAddress", "testPhone", "testContact",0);
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.getSchedule("testStore2");
        });
        _storeController.removeStore("testStore2");
    }



}
