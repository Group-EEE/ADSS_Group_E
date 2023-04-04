import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class LoginTest {
    @Test
    void createUser() {
        Employee employee = new Employee("daniel", "shapira", 26, 123456789, "yahav");
        Boolean a = Login.createUser(123456789, "abcdefg", employee);
        assertEquals(true, a);
        assertTrue(Login.getM_passwords().containsKey(123456789));
        assertTrue(Login.getM_employees().containsKey(123456789));
    }

    @Test
    void login() {
        Employee employee = new Employee("daniel", "shapira", 26, 123456789, "yahav");
        Login.createUser(123456789, "abcdefg", employee);
        assertNull(Login.login(123456789,"abcdef"));
        assertEquals(employee ,Login.login(123456789,"abcdefg"));
    }

    @Test
    void removeUser() {
        Employee employee = new Employee("daniel", "shapira", 26, 123456789, "yahav");
        Login.createUser(123456789, "abcdefg", employee);
        assertFalse(Login.removeUser(123));
        assertFalse(Login.removeUser(-1));
        assertTrue(Login.removeUser(123456789));
        assertEquals(0, Login.getM_passwords().size());
        assertEquals(0, Login.getM_employees().size());
    }

    @Test
    void changePassword() {
        Employee employee = new Employee("daniel", "shapira", 26, 123456789, "yahav");
        Login.createUser(123456789, "abcdefg", employee);
        assertTrue(Login.changePassword(123456789, "abcd"));
        assertFalse(Login.changePassword(12345678, "abcd"));
    }
}