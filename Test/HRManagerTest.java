import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HRManagerTest {

    @Test
    void getEmployeeFirstNameById() {
        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
        assertEquals("yossi", hrManager.getEmployeeFirstNameById(123456789));
    }

    @Test
    void getStoreByName() {
        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
        hrManager.createStore("a", "b");
        assertEquals(hrManager.getM_stores().get(0), hrManager.getStoreByName("a"));
    }

    @Test
    void createEmployee() {
        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
        assertTrue(hrManager.createEmployee("yosi", "avi", 18, 1234567898, "ouhuh","jibguhvrwe"));
        assertFalse(hrManager.createEmployee("yosi", "avi", 18, -5, "ouhuh","jibguhvrwe"));

    }


    @Test
    void createStore() {
        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
        assertTrue(hrManager.createStore("a", "b"));
        assertFalse(hrManager.createStore(null, "a"));
        assertFalse(hrManager.createStore("a", null));

    }

    @Test
    void addRoleToEmployee() {
        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
        hrManager.createStore("a", "b");
        hrManager.addEmployeeToStore(123456789, "a");
        CleanerRole c = new CleanerRole();
        assertTrue(hrManager.addRoleToEmployee(123456789, c));
    }

    @Test
    void addEmployeeToStore() {
    }

    @Test
    void findEmployeeByID() {
    }

    @Test
    void getRolesById() {
    }

    @Test
    void findStoreByName() {
    }

    @Test
    void removeEmployeeFromStore() {
    }

    @Test
    void removeEmployee() {
    }

    @Test
    void removeStore() {
    }

    @Test
    void removeRoleFromEmployee() {
    }

    @Test
    void removeRoleFromShift() {
    }

    @Test
    void approveSchedule() {
    }

    @Test
    void addRoleToShift() {
    }
}