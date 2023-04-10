
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void addStore() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        Store store = new Store(1,"a", "b");
        employee.addStore(store);
        assertEquals(store, employee.getStores().get(0));
    }

    @Test
    void get_first_name() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        assertEquals("daniel", employee.getFirstName());
    }

    @Test
    void setRole() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        assertTrue(employee.addRole(RoleType.Cleaner));

    }

    @Test
    void getRoles() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        employee.addRole(RoleType.Cleaner);
        employee.addRole(RoleType.Cashier);
        assertEquals(RoleType.Cleaner, employee.getRoles().get(0));
        assertEquals(RoleType.Cashier,employee.getRoles().get(1));
    }

    @Test
    void getStores() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        Store store = new Store(1,"a", "b");
        employee.addStore(store);
        assertEquals(store, employee.getStores().get(0));
    }

    @Test
    void removeStore() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        Store store1 = new Store(1,"a", "b");
        employee.addStore(store1);
        Store store2 = new Store(2,"c", "d");
        employee.addStore(store2);
        employee.removeStore(store1);
        assertEquals(store2, employee.getStores().get(0));
        employee.removeStore(store1);
        assertTrue(employee.getStores().get(0) == store2);
        assertTrue(employee.getStores().size() == 1);
    }
}