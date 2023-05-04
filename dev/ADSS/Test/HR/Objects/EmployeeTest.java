package HR.Objects;

import BussinessLayer.HRModule.Controllers.EmployeeController;
import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    Facade _facade = Facade.getInstance();
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23));
    }

    @Test
    void getFirstName() {
        assertEquals(employee.getFirstName(), "daniel");
    }

    @Test
    void getLastName(){
        assertEquals(employee.getLastName(), "shapira");
    }

    @Test
    void setNewFirstName() {
        employee.setNewFirstName("dani");
        assertEquals(employee.getFirstName(), "dani");
    }

    @Test
    void setNewLastName() {
        employee.setNewLastName("shapir");
        assertEquals(employee.getLastName(), "shapir");
    }

    @Test
    void setNewBankAccount(){
        assertTrue(employee.setNewBankAccount("123456"));
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
    void setFinishedWorking() {
        assertTrue(employee.setFinishedWorking());
        assertTrue(employee.getFinishedWorking());
    }

    @Test
    void setRole() {
        assertTrue(employee.addRole(RoleType.HRManager));
    }

    @Test
    void getRoles() {
        employee.addRole(RoleType.Cleaner);
        employee.addRole(RoleType.Cashier);
        assertEquals(RoleType.Cleaner, employee.getRoles().get(0));
        assertEquals(RoleType.Cashier,employee.getRoles().get(1));
    }

    @Test
    void removeRoles() {
        employee.addRole(RoleType.Cleaner);
        employee.addRole(RoleType.Cashier);
        assertTrue(employee.removeRole(1));
        assertEquals(RoleType.Cashier,employee.getRoles().get(0));
    }

    @Test
    void getID() {
        assertEquals(209876676, employee.getID());
    }

    @Test
    void getFullNameName() {
        assertEquals("daniel shapira", employee.getFullNameName());
    }
}