package HR.Objects;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.HRMoudle.SchedulesDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule(1, LocalDate.of(1999,1,1), "testStore");
    }

    @Test
    void setShifts(){
        List<Shift> listShifts = new ArrayList<>();
        for (int i=0; i<14; i++)
            listShifts.add(new Shift(1, i, ShiftType.MORNING, 8, 16, LocalDate.of(1999,1,1)));
        assertTrue(schedule.setShifts(listShifts));
        assertFalse(schedule.setShifts(null));
    }


    @Test
    void getShift(){
        setShifts();
        assertEquals(ShiftType.MORNING, schedule.getShift(0).getShiftType());
        assertEquals(1, schedule.getShift(1).getShiftID());
        assertNull(schedule.getShift(14));
        assertNull(schedule.getShift(-1));
    }

    @Test
    void getScheduleID(){
        assertEquals(1, schedule.getScheduleID());
    }

    @Test
    void getStartDateOfWeek(){
        assertEquals(LocalDate.of(1999,1,1),schedule.getStartDateOfWeek());
    }

    @Test
    void getStoreName(){
        assertEquals("testStore", schedule.getStoreName());
    }
}