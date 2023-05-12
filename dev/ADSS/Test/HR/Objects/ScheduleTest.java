package HR.Objects;
import BussinessLayer.HRModule.Objects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule(1, "testStore", LocalDate.of(1999,1,1));
    }


    //setters
    @Test
    void setShifts(){
        List<Shift> listShifts = new ArrayList<>();
        for (int i=0; i<14; i++) {
            Shift shift = new Shift(1, i, ShiftType.MORNING, 8, 16, LocalDate.of(1999, 1, 1));
            shift.setRequiredRoles(List.of(RoleType.Cashier, RoleType.ShiftManager, RoleType.General,RoleType.Warehouse));
            shift.setRolesMustBeFilled(List.of(RoleType.ShiftManager));
            listShifts.add(shift);
        }
        assertTrue(schedule.setShifts(listShifts));
        assertFalse(schedule.setShifts(null));
    }

    //getters

    @Test
    void getShift(){
        setShifts();
        assertEquals(ShiftType.MORNING, schedule.getShift(0).getShiftType());
        assertEquals(1, schedule.getShift(1).getShiftID());
        assertNull(schedule.getShift(14));
        assertNull(schedule.getShift(-1));
    }

    @Test
    void getShifts(){
        setShifts();
        assertEquals(14, schedule.getShifts().size());
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


    //methods
    @Test
    void addRequiredRoleToShift(){
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.addRequiredRoleToShift(1,null);
        });
        setShifts();
        assertFalse(schedule.addRequiredRoleToShift(0, RoleType.Cashier));
        assertTrue(schedule.addRequiredRoleToShift(0, RoleType.Cleaner));
        assertFalse(schedule.addRequiredRoleToShift(0, RoleType.Cleaner));
        assertFalse(schedule.addRequiredRoleToShift(14, RoleType.Cashier));
        assertFalse(schedule.addRequiredRoleToShift(-1, RoleType.Cashier));
    }

    @Test
    void removeRequiredRoleFromShift(){
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.removeRequiredRoleFromShift(1,null);
        });
        setShifts();
        assertFalse(schedule.removeRequiredRoleFromShift(0, RoleType.Cleaner));
        assertTrue(schedule.removeRequiredRoleFromShift(0, RoleType.Cashier));
        assertFalse(schedule.removeRequiredRoleFromShift(0, RoleType.Cashier));
        assertFalse(schedule.removeRequiredRoleFromShift(14, RoleType.Cashier));
        assertFalse(schedule.removeRequiredRoleFromShift(-1, RoleType.Cashier));
    }

    @Test
    void changeHoursShift(){
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.changeHoursShift(8,13,0);
        });
        setShifts();
        assertFalse(schedule.changeHoursShift(0, 7, 14)); //shiftID >13
        assertFalse(schedule.changeHoursShift(0, 8, -1)); //shiftID <0
        assertFalse(schedule.changeHoursShift(-1, 8, 0)); //newStartHour <0
        assertFalse(schedule.changeHoursShift(16, 25, 1)); //newEndHour > 24
        assertThrows(IllegalArgumentException.class, () -> {//startHour > endHour
            schedule.changeHoursShift(22, 16, 0);
        });
        assertTrue(schedule.changeHoursShift(8, 13, 0));
    }

    @Test
    void addEmployeeToShift(){
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.addEmployeeToShift(null,1);
        });
        setShifts();
        Employee employee1 = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.addEmployeeToShift(employee1,-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.addEmployeeToShift(employee1,14);
        });
        assertTrue(schedule.addEmployeeToShift(employee1,0));
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.addEmployeeToShift(employee1,0);
        });
    }

    @Test
    void testToString() {
        assertEquals("Schedule ID: 1, Store name: testStore, Start date of week: 1999-01-01", schedule.toString());
        setShifts();
        assertEquals("Schedule ID: 1, Store name: testStore, Start date of week: 1999-01-01, Shifts: \n" +
                "ShiftID: 0, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 1, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 2, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 3, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 4, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 5, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 6, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 7, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 8, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 9, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 10, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 11, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 12, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false\n" +
                "ShiftID: 13, Date: 1999-01-01, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false", schedule.toString());
    }

    @Test
    void approveSchedule(){
        setShifts();
        Employee employee1 = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        Employee employee2 = new Employee(2,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        Employee employee3 = new Employee(3,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        Employee employee4 = new Employee(4,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        employee1.addRole(RoleType.Cashier);
        employee2.addRole(RoleType.Warehouse);
        employee3.addRole(RoleType.ShiftManager);
        employee4.addRole(RoleType.General);
        for (int i = 0; i < 14; i++) {
            schedule.addEmployeeToShift(employee1,i);
            schedule.addEmployeeToShift(employee2,i);
            schedule.addEmployeeToShift(employee3,i);
            schedule.addEmployeeToShift(employee4,i);
        }
        List<Shift> rejectedShifts = schedule.approveSchedule();
        assertEquals(rejectedShifts.size(), 0);
    }

    @Test
    void approveScheduleWithRejects(){
        setShifts();
        Employee employee1 = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        Employee employee2 = new Employee(2,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        Employee employee3 = new Employee(3,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        Employee employee4 = new Employee(4,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        employee1.addRole(RoleType.Cashier);
        employee2.addRole(RoleType.Warehouse);
        employee3.addRole(RoleType.ShiftManager);
        employee4.addRole(RoleType.General);
        for (int i = 0; i < 13; i++) {
            schedule.addEmployeeToShift(employee1,i);
            schedule.addEmployeeToShift(employee2,i);
            schedule.addEmployeeToShift(employee3,i);
            schedule.addEmployeeToShift(employee4,i);
        }
        List<Shift> rejectedShifts = schedule.approveSchedule();
        assertEquals(rejectedShifts.size(), 1);
    }
}