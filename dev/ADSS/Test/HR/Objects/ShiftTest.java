package HR.Objects;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {

    Shift shift;
    Employee employee1;
    Employee employee2;
    Employee employee3;
    Employee employee4;

    @BeforeEach
    void setUp() {
        List<RoleType> roles = List.of(RoleType.Cashier, RoleType.ShiftManager, RoleType.General,RoleType.Warehouse);
        List<RoleType> rolesHaveToBeFilled = List.of(RoleType.ShiftManager);
        shift = new Shift(1,1,ShiftType.MORNING, 8, 16, LocalDate.of(2017, 1, 13));
        shift.setRequiredRoles(roles);
        shift.setRolesMustBeFilled(rolesHaveToBeFilled);
        employee1 = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        employee2 = new Employee(2,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        employee3 = new Employee(3,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        employee4 = new Employee(4,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
    }

    //setters
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

    @Test
    void setRequiredRoles(){
        assertEquals(4, shift.getRequiredRoles().size());
        List<RoleType> list = new ArrayList<>(Arrays.asList(RoleType.Cashier, RoleType.Cleaner));
        assertTrue(shift.setRequiredRoles(list));
        assertEquals(2, shift.getRequiredRoles().size());
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setRequiredRoles(null);
        });
    }

    @Test
    void setInquiredEmployees(){
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setInquiredEmployees(null);
        });
        assertEquals(0, shift.getInquiredEmployees().size());
        List<Employee> list = new ArrayList<>(Arrays.asList(employee1,employee2));
        assertTrue(shift.setInquiredEmployees(list));
        assertEquals(2, shift.getInquiredEmployees().size());
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setInquiredEmployees(list);
        });
    }

    @Test
    void setAssignedEmployees(){
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setAssignedEmployees(null);
        });
        assertEquals(0, shift.getAssignedEmployees().size());
        HashMap<RoleType,Employee> list = new HashMap<>();
        list.put(RoleType.Cashier, employee1);
        list.put(RoleType.Cleaner, employee2);
        list.put(RoleType.ShiftManager, employee3);
        list.put(RoleType.General, employee4);
        assertTrue(shift.setAssignedEmployees(list));
        assertEquals(4, shift.getAssignedEmployees().size());
        assertThrows(IllegalArgumentException.class, () -> {
            shift.setAssignedEmployees(list);
        });
    }

    //getters
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
    void getAssignedEmployees(){
        assertEquals(0, shift.getAssignedEmployees().size());
        addFilledRole();
        assertEquals(1, shift.getAssignedEmployees().size());
    }


    //adders
    @Test
    void addInquiredEmployee() {
        Employee employee = new Employee(1,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"a");
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
    void addFilledRole(){
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addFilledRole(null,employee1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shift.addFilledRole(RoleType.Cleaner,null);
        });
        //employee wasn't inquired
        assertThrows(IllegalArgumentException.class, () -> shift.addFilledRole(RoleType.Cleaner,employee1));
        shift.addInquiredEmployee(employee1);
        //role wasn't required
        assertThrows(IllegalArgumentException.class, () -> shift.addFilledRole(RoleType.ShiftManager,employee1));
        shift.addRequiredRole(RoleType.Cleaner);
        //employee don't have this role
        assertThrows(IllegalArgumentException.class, () -> shift.addFilledRole(RoleType.Cleaner,employee1));
        employee1.addRole(RoleType.Cleaner);
        assertTrue(shift.addFilledRole(RoleType.Cleaner,employee1));
        //role was already filled
        assertThrows(IllegalArgumentException.class, () -> shift.addFilledRole(RoleType.Cleaner,employee1));
    }

    //removers
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
        Employee employeeNotInquired = new Employee(2,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        assertTrue(shift.addInquiredEmployee(employee1));
        assertTrue(shift.removeInquiredEmployee(employee1));
        assertFalse(shift.removeInquiredEmployee(employee1));
        assertFalse(shift.removeInquiredEmployee(employeeNotInquired));
        assertThrows(IllegalArgumentException.class, () -> {
            shift.removeInquiredEmployee(null);
        });
        assertEquals(0, shift.getInquiredEmployees().size());
    }

    //shift methods
    @Test
    void testToString(){
        assertEquals("ShiftID: 1, Date: 2017-01-13, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false", shift.toString());
        employee1.addRole(RoleType.ShiftManager);
        shift.addInquiredEmployee(employee1);
        shift.addFilledRole(RoleType.ShiftManager,employee1);
        assertEquals("ShiftID: 1, Date: 2017-01-13, ShiftType: MORNING, Start hour: 8, End hour: 16, Length time: 8, Approved: false, Rejected: false, Assigned employees: ShiftManager: a b", shift.toString());
    }

    @Test
    void hasAssignedEmployee(){
        Employee employeeNotInquired = new Employee(2,"a", "b", 22,"a", 567853345,"c",LocalDate.of(2017, 1, 13),"passwordTest");
        addFilledRole();
        assertTrue(shift.hasAssignedEmployee(employee1));
        assertThrows(IllegalArgumentException.class, () -> {
            shift.hasAssignedEmployee(null);
        });
        assertFalse(shift.hasAssignedEmployee(employeeNotInquired));
    }

    @Test
    void hasFilledRole(){
        addFilledRole();
        assertThrows(IllegalArgumentException.class, () -> {
            shift.hasFilledRole(null);
        });
        assertTrue(shift.hasFilledRole(RoleType.Cleaner));
    }

    @Test
    void approveShift(){
        employee1.addRole(RoleType.Cashier);
        employee2.addRole(RoleType.Warehouse);
        employee3.addRole(RoleType.ShiftManager);
        employee4.addRole(RoleType.General);
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee1,employee2,employee3,employee4));
        //add employees to shift
        shift.setInquiredEmployees(employees);
        assertTrue(shift.approveShift());
        assertTrue(shift.isApproved());
        assertFalse(shift.approveShift());
    }

    @Test
    void approveShiftNoShiftManager(){
        employee1.addRole(RoleType.Cashier);
        employee2.addRole(RoleType.Warehouse);
        employee3.addRole(RoleType.Cleaner);
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
        //add employees to shift
        shift.setInquiredEmployees(employees);
        assertFalse(shift.approveShift());
        assertTrue(shift.isRejected());
    }

    @Test
    void approveShiftButNotFilled(){
        employee1.addRole(RoleType.Cashier);
        employee2.addRole(RoleType.Warehouse);
        employee3.addRole(RoleType.General);
        employee3.addRole(RoleType.ShiftManager);
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
        //add employees to shift
        shift.setInquiredEmployees(employees);
        assertFalse(shift.approveShift());
    }

    @Test
    void approveEmptyShift(){
        assertFalse(shift.isApproved());
        assertFalse(shift.approveShift());
        assertTrue(shift.isRejected());
        assertFalse(shift.approveShift());
    }

}