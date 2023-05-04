//import BussinessLayer.HRModule.Controllers.Facade;
//import BussinessLayer.HRModule.Objects.Schedule;
//import BussinessLayer.HRModule.Objects.ShiftType;
//import DataAccessLayer.HRMoudle.SchedulesDAO;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ScheduleTest {
//
//    Schedule schedule;
//    Facade _facade = Facade.getInstance();
//    SchedulesDAO _schedulesDAO = SchedulesDAO.getInstance();
//
//    @BeforeEach
//    void setUp() {
//        _facade.createNewSchedule("testStore", 11, 12, 2021);
//    }
//    @AfterEach
//    void tearDown() {
//        _schedulesDAO.Delete("testStore");
//    }
//    @Disabled
//    void changeHoursShift(){
//        assertTrue(schedule.changeHoursShift(7,15,12));
//        //invalid shiftID
//        assertFalse(schedule.changeHoursShift(7, 18, 16));
//        //invalid start hour
//        assertFalse(schedule.changeHoursShift(-2, 18, 1));
//        //invalid end hour
//        assertFalse(schedule.changeHoursShift(7, 25, 1));
//    }
//
//    @Test
//    void getShift(){
//        assertEquals(ShiftType.MORNING, schedule.getShift(0).getShiftType());
//        assertNull(schedule.getShift(15));
//    }
//
//}