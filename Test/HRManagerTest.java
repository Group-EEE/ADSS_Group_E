//import BussinessLayer.Controllers.HRManager;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class HRManagerTest {
//
//    @Test
//    void getEmployeeFirstNameById() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        assertEquals("yossi", hrManager.getEmployeeFirstNameById(123456789));
//    }
//
//    @Test
//    void getStoreByName() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createStore("a", "b");
//        assertEquals(hrManager.getM_stores().get(0), hrManager.getStoreByName("a"));
//    }
//
//    @Test
//    void createEmployee() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        assertTrue(hrManager.createEmployee("yosi", "avi", 18, 1234567898, "ouhuh","jibguhvrwe"));
//        assertFalse(hrManager.createEmployee("yosi", "avi", 18, -5, "ouhuh","jibguhvrwe"));
//
//    }
//
//
//    @Test
//    void createStore() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        assertTrue(hrManager.createStore("a", "b"));
//        assertFalse(hrManager.createStore(null, "a"));
//        assertFalse(hrManager.createStore("a", null));
//
//    }
//
//    @Test
//    void addRoleToEmployee() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        hrManager.createStore("a", "b");
//        hrManager.addEmployeeToStore(123456789, "a");
//        CleanerRole c = new CleanerRole();
//        assertTrue(hrManager.addRoleToEmployee(123456789, c));
//    }
//
//    @Test
//    void addEmployeeToStore() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        hrManager.createStore("a", "b");
//        assertTrue(hrManager.addEmployeeToStore(123456789, "a"));
//    }
//
//    @Test
//    void findEmployeeByID() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        assertEquals(hrManager.getM_employees().get(0), hrManager.findEmployeeByID(123456789));
//    }
//
//    @Test
//    void getRolesById() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        hrManager.createStore("a", "b");
//        hrManager.addEmployeeToStore(123456789, "a");
//        CleanerRole c = new CleanerRole();
//        hrManager.addRoleToEmployee(123456789, c);
//        assertEquals(c,hrManager.getRolesById(123456789).get(0));
//        assertNull(hrManager.getRolesById(234567));
//    }
//
//    @Test
//    void findStoreByName() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createStore("a", "b");
//        assertEquals(hrManager.getM_stores().get(0), hrManager.findStoreByName("a"));
//        assertNull(hrManager.findStoreByName("b"));
//    }
//
//    @Test
//    void removeEmployeeFromStore() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createStore("a", "b");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        hrManager.addEmployeeToStore(123456789, "a");
//        assertTrue(hrManager.removeEmployeeFromStore(123456789, "a"));
//        assertEquals(0, hrManager.getM_stores().get(0).getEmployees().size());
//        assertFalse(hrManager.addEmployeeToStore(-2, "a"));
//        assertFalse(hrManager.addEmployeeToStore(123456789, null));
//    }
//
//    @Test
//    void removeEmployee() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        assertFalse(hrManager.removeEmployee(-5));
//        assertFalse(hrManager.removeEmployee(3456567));
//        assertTrue(hrManager.removeEmployee(123456789));
//        assertFalse(hrManager.removeEmployee(123456789));
//    }
//
//    @Test
//    void removeStore() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createStore("a", "b");
//        assertFalse(hrManager.removeStore(null));
//        assertFalse(hrManager.removeStore("b"));
//        assertTrue(hrManager.removeStore("a"));
//        assertFalse(hrManager.removeStore("a"));
//    }
//
//    @Test
//    void removeRoleFromEmployee() {
//        HRManager hrManager = new HRManager("daniel","shapira", 26, 234567890, "a", "kjbhvbu23");
//        hrManager.createEmployee("yossi", "levi", 25, 123456789, "clal", "fjh78");
//        hrManager.createStore("a", "b");
//        hrManager.addEmployeeToStore(123456789, "a");
//        CleanerRole c = new CleanerRole();
//        SecurityRole s = new SecurityRole();
//        hrManager.addRoleToEmployee(123456789, c);
//        assertFalse(hrManager.removeRoleFromEmployee(345678, c));
//        assertFalse(hrManager.removeRoleFromEmployee(123456789, s));
//        assertTrue(hrManager.removeRoleFromEmployee(123456789, c));
//        assertEquals(0, hrManager.getM_employees().get(0).getRoles().size());
//    }
//
//    @Test
//    void removeRoleFromShift() {
//    }
//
//    @Test
//    void approveSchedule() {
//    }
//
//    @Test
//    void addRoleToShift() {
//    }
//}