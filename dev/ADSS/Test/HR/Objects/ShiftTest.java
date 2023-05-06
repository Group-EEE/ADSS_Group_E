package HR.Objects;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {

    Shift shift;
    @BeforeEach
    void setUp() {
        shift = new Shift(1,1,ShiftType.MORNING, 8, 16, LocalDate.of(2017, 1, 13));
    }
    @Test
    void setStartHour() {
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setStartHour(17);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setStartHour(-1);
        });
        assertTrue(shift.setStartHour(8));
    }
    @Test
    void setEndHour(){
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setEndHour(7);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setEndHour(25);
        });
        assertTrue(shift.setEndHour(16));
    }

    @Test
    void addInquiredEmployee() {
        Employee employee = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13));
        assertTrue(shift.addInquiredEmployee(employee));
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addInquiredEmployee(employee);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addInquiredEmployee(null);
        });
        assertEquals(1, shift.getInquiredEmployees().size());
        assertEquals(employee, shift.getInquiredEmployees().get(0));
    }

    @Test
    void addRequiredRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addRequiredRole(null);
        });
        assertEquals(4, shift.getRequiredRoles().size());
        assertTrue(shift.addRequiredRole(RoleType.Cleaner));
        assertEquals(5, shift.getRequiredRoles().size());
    }

    @Test
    void removeRequiredRole() {
        shift.addRequiredRole(RoleType.Cleaner);
        assertTrue(shift.removeRequiredRole(RoleType.Cleaner));
        assertEquals(4, shift.getRequiredRoles().size());
        assertThrows(IllegalArgumentException.class, () -> {
            shift.removeRequiredRole(null);
        });
    }

    @Test
    void removeInquiredEmployee() {
        Employee employee = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13));
        Employee employeeNotInquired = new Employee(2,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13));
        assertTrue(shift.addInquiredEmployee(employee));
        assertTrue(shift.removeInquiredEmployee(employee));
        assertFalse(shift.removeInquiredEmployee(employee));
        assertFalse(shift.removeInquiredEmployee(employeeNotInquired));
        assertThrows(IllegalArgumentException.class, () -> {
            shift.removeInquiredEmployee(null);
        });
        assertEquals(0, shift.getInquiredEmployees().size());
    }

    @Test
    void getScheduleID() {
        assertEquals(1, shift.getScheduleID());
    }
    @Test
    void getShiftID() {
        assertEquals(1, shift.getShiftID());
    }

    @Test
    void getShiftType() {
        assertEquals(ShiftType.MORNING, shift.getShiftType());
    }

    @Test
    void getStartHour() {
        assertEquals(8, shift.getStartHour());
    }

    @Test
    void getEndHour() {
        assertEquals(16, shift.getEndHour());
    }

    @Test
    void isApproved() {
        assertFalse(shift.isApproved());
    }

    @Test
    void isRejected() {
        assertFalse(shift.isRejected());
    }

    @Test
    void getRequiredRoles() {
        assertEquals(4, shift.getRequiredRoles().size());
        addRequiredRole();
        assertEquals(5, shift.getRequiredRoles().size());
    }

    @Test
    void getInquiredEmployees() {
        assertEquals(0, shift.getInquiredEmployees().size());
        addInquiredEmployee();
        assertEquals(1, shift.getInquiredEmployees().size());
    }

    @Test
    void getDate() {
        assertEquals(LocalDate.of(2017, 1, 13), shift.getDate());
    }

    @Test
    void addFilledRole(){
        Employee employee = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13));
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addFilledRole(null,employee);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addFilledRole(RoleType.Cleaner,null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addFilledRole(null,null);
        });
        assertFalse(shift.addFilledRole(RoleType.Cleaner,employee));
        assertFalse(shift.addFilledRole(RoleType.ShiftManager,employee));
        shift.addInquiredEmployee(employee);
        assertFalse(shift.addFilledRole(RoleType.Cleaner,employee));
        assertTrue(shift.addFilledRole(RoleType.ShiftManager,employee));
        assertFalse(shift.addFilledRole(RoleType.Cashier,employee));
    }

    @Test
    void setApproved(){
        shift.setApproved(true);
        assertTrue(shift.isApproved());
        shift.setApproved(false);
        assertFalse(shift.isApproved());
    }

    @Test
    void setApprovedWasRejected(){
        shift.setRejected(true);
        assertFalse(shift.isApproved());
        assertTrue(shift.setApproved(true));
        assertFalse(shift.isRejected());
    }

    @Test
    void setRejected(){
        shift.setRejected(true);
        assertTrue(shift.isRejected());
        shift.setRejected(false);
        assertFalse(shift.isRejected());
    }

    @Test
    void setRejectedWasApproved(){
        shift.setApproved(true);
        assertFalse(shift.isRejected());
        assertTrue(shift.setRejected(true));
        assertFalse(shift.isApproved());
    }





}