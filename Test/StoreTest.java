
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Test
    void getName() {
        Store store = new Store("a", "b");
        assertEquals("a", store.getName());
    }

    @Test
    void addEmployee() {
        Store store = new Store("a", "b");
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        assertTrue(store.addEmployee(employee));
    }

    @Test
    void get_curr_schedule() {
    }

    @Test
    void updateRoles() {
        Store store1 = new Store("a", "b");
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        SecurityRole s = new SecurityRole();
        employee.setRole(s);
        store1.addEmployee(employee);
        store1.updateRoles(employee);
        assertEquals(1,store1.getM_security().size());
    }

    @Test
    void removeEmployee() {
        Store store = new Store("a", "b");
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
    void removeRoleFromEmployee() {
        Store store = new Store("a", "b");
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        WarehouseRole w = new WarehouseRole();
        employee.setRole(w);
        store.addEmployee(employee);
        store.updateRoles(employee);
        assertEquals(1,store.getEmployees().size());
        assertEquals(1, store.getM_warehouse_employees().size());
        store.removeRoleFromEmployee(w,employee);
        assertEquals(0, store.getM_warehouse_employees().size());
    }

    @Test
    void getEmployees() {
    }

    @Test
    void getAddress() {
        Store store = new Store("a", "b");
        assertEquals("b", store.getAddress());
    }
}