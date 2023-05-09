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
        _scheduleController.createNewSchedule("testStore", 1,1,1999);
    }

    @AfterEach
    void tearDown() {
        _scheduleController.deleteSchedule("testStore");
        _storeController.removeStore("testStore");
    }

    @Test
    void createNewSchedule() {
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.createNewSchedule(null, 1,1,1999);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.createNewSchedule("testStoreNotExist", 1,1,1999);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _scheduleController.createNewSchedule("testStoreNotExist", 30,2,1999);
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
        assertTrue(_scheduleController.deleteScheduleIDFromStore("testStore"));
    }

}
