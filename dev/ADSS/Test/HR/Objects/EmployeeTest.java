package HR.Objects;

import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23),"test");
    }

    //getters
    @Test
    void getFirstName() {
        assertEquals(employee.getFirstName(), "daniel");
    }

    @Test
    void getLastName(){
        assertEquals(employee.getLastName(), "shapira");
    }

    @Test
    void getAge() {
        assertEquals(employee.getAge(), 26);
    }

    @Test
    void getBankAccount() {
        assertEquals(employee.getBankAccount(), "234657");
    }

    @Test
    void getSalary() {
        assertEquals(employee.getSalary(), 10);
    }

    @Test
    void getHiringCondition() {
        assertEquals(employee.getHiringCondition(), "a");
    }

    @Test
    void getStartDateOfEmployement() {
        assertEquals(employee.getStartDateOfEmployement(), LocalDate.of(2023,4, 23));
    }

    @Test
    void getFinishedWorking() {
        assertFalse(employee.getFinishedWorking());
    }

    @Test
    void getPassword() {
        assertEquals(employee.getPassword(), "test");
    }

    @Test
    void getRoles() {
        employee.addRole(RoleType.Cleaner);
        employee.addRole(RoleType.Cashier);
        assertEquals(RoleType.Cleaner, employee.getRoles().get(0));
        assertEquals(RoleType.Cashier,employee.getRoles().get(1));
    }

    @Test
    void getEmployeeID() {
        assertEquals(209876676, employee.getEmployeeID());
    }

    @Test
    void getFullName() {
        assertEquals("daniel shapira", employee.getFullName());
    }


    //setters
    @Test
    void setNewLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setNewLastName(null);
        });
        employee.setNewLastName("shapir");
        assertEquals(employee.getLastName(), "shapir");
    }

    @Test
    void setNewFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setNewFirstName(null);
        });
        employee.setNewFirstName("dani");
        assertEquals(employee.getFirstName(), "dani");
    }

    @Test
    void setNewBankAccount(){
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setNewBankAccount(null);
        });
        assertTrue(employee.setNewBankAccount("123456"));
    }

    @Test
    void setFinishedWorking() {
        assertTrue(employee.setFinishedWorking());
    }

    @Test
    void setNewPassword(){
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setNewPassword(null);
        });
        assertTrue(employee.setNewPassword("test2"));
    }


    @Test
    void addRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addRole(null);
        });
        assertTrue(employee.addRole(RoleType.HRManager));
    }

    @Test
    void removeRoles() {
        assertThrows(IllegalArgumentException.class, () -> {
            employee.removeRole(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            employee.removeRole(employee.getRoles().size());
        });
        employee.addRole(RoleType.Cleaner);
        employee.addRole(RoleType.Cashier);
        assertTrue(employee.removeRole(0));
        assertEquals(RoleType.Cashier,employee.getRoles().get(0));
    }

    @Test
    void hasRole(){
        assertFalse(employee.hasRole(RoleType.Cleaner));
        employee.addRole(RoleType.Cleaner);
        assertTrue(employee.hasRole(RoleType.Cleaner));
    }
    @Test
    void testToString(){
        assertEquals("Employee: daniel shapira, ID: 209876676, age: 26", employee.toString());
        employee.addRole(RoleType.Cleaner);
        employee.addRole(RoleType.Cashier);
        assertEquals("Employee: daniel shapira, ID: 209876676, age: 26, Roles: Cleaner, Cashier", employee.toString());
    }
}