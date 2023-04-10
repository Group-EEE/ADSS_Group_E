
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Test
    void getName() {
        Store store = new Store(1, "a", "b");
        assertEquals("a", store.getName());
    }

    @Test
    void addEmployee() {
        Store store = new Store(1, "a", "b");
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        assertTrue(store.addEmployee(employee));
    }



    @Test
    void removeEmployee() {
        Store store = new Store(1,"a", "b");
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        Employee employee2 = new Employee("daniel", "shapira", 26, 209876676, "234657");
        store.addEmployee(employee);
        assertEquals(1,store.getEmployees().size());
        store.removeEmployee(employee);
        assertEquals(0, store.getEmployees().size());
        store.addEmployee(employee);
        Boolean a = store.removeEmployee(employee2);
        assertFalse(a);
    }

    @Test
    void getAddress() {
        Store store = new Store(1,"a", "b");
        assertEquals("b", store.getAddress());
    }
}