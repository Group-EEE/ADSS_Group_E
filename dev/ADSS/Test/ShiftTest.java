import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {

    @Test
    void setStartHour() {
        Shift shift = new Shift(ShiftType.MORNING, 8, 16, LocalDate.of(2017, 1, 13));
        assertFalse(shift.setStartHour(17));
        assertFalse(shift.setStartHour(-5));
        assertTrue(shift.setStartHour(8));
    }

    @Test
    void addInquiredEmployee() {
        Shift shift = new Shift(ShiftType.MORNING, 8, 16, LocalDate.of(2017, 1, 13));
        Employee employee = new Employee("a", "b", 22, 567853345,"c");
        assertTrue(shift.addInquiredEmployee(employee));
        assertEquals(1, shift.getInquiredEmployees().size());
    }

    @Test
    void addRequiredRole() {
        Shift shift = new Shift(ShiftType.MORNING, 8, 16, LocalDate.of(2017, 1, 13));
        assertFalse(shift.addRequiredRole(null));
        assertTrue(shift.addRequiredRole(RoleType.Cleaner));
        assertEquals(5, shift.getRequiredRoles().size());
    }

    @Test
    void removeRequiredRole() {
        Shift shift = new Shift(ShiftType.MORNING, 8, 16, LocalDate.of(2017, 1, 13));
        shift.addRequiredRole(RoleType.Cleaner);
        assertTrue(shift.removeRequiredRole(RoleType.Cleaner));
        assertEquals(4, shift.getRequiredRoles().size());
    }

    @Test
    void removeInquiredEmployee() {
        Shift shift = new Shift(ShiftType.MORNING, 8, 16, LocalDate.of(2017, 1, 13));
        Employee employee = new Employee("a", "b", 22, 567853345,"c");
        shift.addInquiredEmployee(employee);
        assertTrue(shift.removeInquiredEmployee(employee));
        assertFalse(shift.removeInquiredEmployee(employee));
        assertEquals(0, shift.getInquiredEmployees().size());
    }
}