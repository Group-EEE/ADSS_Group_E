import Roles.CleanerRole;
import Roles.IRole;
import Roles.SecurityRole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void addStore() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        Store store = new Store("a", "b");
        employee.addStore(store);
        assertEquals(store, employee.getStores().get(0));
    }

    @Test
    void get_first_name() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        assertEquals("daniel", employee.get_first_name());
    }

    @Test
    void setRole() {
        CleanerRole cleanerRole = new CleanerRole();
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        assertTrue(employee.setRole(cleanerRole));

    }

    @Test
    void getRoles() {
        CleanerRole cleanerRole = new CleanerRole();
        SecurityRole securityRole = new SecurityRole();
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        employee.setRole(cleanerRole);
        employee.setRole(securityRole);
        assertEquals(cleanerRole, employee.getRoles().get(0));
        assertEquals(securityRole,employee.getRoles().get(1));
    }

    @Test
    void getStores() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        Store store = new Store("a", "b");
        employee.addStore(store);
        assertEquals(store, employee.getStores().get(0));
    }

    @Test
    void removeStore() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        Store store1 = new Store("a", "b");
        employee.addStore(store1);
        Store store2 = new Store("c", "d");
        employee.addStore(store2);
        employee.removeStore(store1);
        assertEquals(store2, employee.getStores().get(0));
        employee.removeStore(store1);
        assertTrue(employee.getStores().get(0) == store2);
        assertTrue(employee.getStores().size() == 1);
    }
}