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
    }

    @Test
    void removeEmployee() {
    }

    @Test
    void removeRoleFromEmployee() {
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