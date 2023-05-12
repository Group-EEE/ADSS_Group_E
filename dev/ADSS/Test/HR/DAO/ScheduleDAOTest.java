package HR.DAO;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.ShiftType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleDAOTest {

    Facade _facade = Facade.getInstance();

    @BeforeEach
    void setUp() {
        _facade.createStore("testStore", "testAddress", "testPhone", "testContact", 1);
        _facade.createNewStoreSchedule("testStore", 11, 12, 2021);
    }
    @AfterEach
    void tearDown() {
        _facade.deleteSchedule("testStore");
        _facade.removeStore("testStore");
    }

    @Test
    void getShift(){
        Schedule schedule = _facade.getSchedule("testStore");
        assertEquals(ShiftType.MORNING, schedule.getShift(0).getShiftType());
        assertNull(schedule.getShift(15));
    }

    @Test
    void addRoleToShift(){
        assertTrue(_facade.addRequiredRoleToShift("testStore", 0, RoleType.Cleaner,false));
        assertTrue(_facade.removeRequiredRoleFromShift("testStore", 0, RoleType.Cleaner));
    }


}