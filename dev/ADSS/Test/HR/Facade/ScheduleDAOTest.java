package HR.Facade;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.HRMoudle.SchedulesDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleDAOTest {

    Schedule schedule;
    Facade _facade = Facade.getInstance();
    SchedulesDAO _schedulesDAO = SchedulesDAO.getInstance();

    @BeforeEach
    void setUp() {
        _facade.createNewSchedule("testStore", 11, 12, 2021);
    }
    @AfterEach
    void tearDown() {
        _schedulesDAO.Delete("testStore");
    }

    @Test
    void getShift(){
        assertEquals(ShiftType.MORNING, schedule.getShift(0).getShiftType());
        assertNull(schedule.getShift(15));
    }

}