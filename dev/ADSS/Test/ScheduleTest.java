import BussinessLayer.HRModule.Objects.Schedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    @Test
    void changeHoursShift(){
        Schedule schedule = new Schedule(LocalDate.of(2021, 12, 11));
        assertTrue(schedule.changeHoursShift(7,15,12));
        assertFalse(schedule.changeHoursShift(7, 18, 16));
        assertFalse(schedule.changeHoursShift(-2, 18, 16));
        assertFalse(schedule.changeHoursShift(7, 25, 16));
        assertFalse(schedule.changeHoursShift(7, 18, -1));
    }

}