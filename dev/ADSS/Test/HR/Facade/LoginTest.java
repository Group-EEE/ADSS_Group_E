package HR.Facade;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class LoginTest {

    Employee employee;
    Facade _facade = Facade.getInstance();
    @BeforeEach
    void createUser() {
        _facade.createEmployee(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23), "a", false);
    }

    @AfterEach
    void removeUser() {
        _facade.removeEmployee(209876676);
    }

    @Test
    void login() {
        assertThrows(IllegalArgumentException.class, () -> {
            _facade.login(209876676, "b");
        });
        assertTrue(_facade.login(209876676, "a"));
    }

    @Test
    void logout(){
        login();
        assertTrue(_facade.logout());
    }

    @Disabled("not implment yet cli level")
    void changePassword() {
        //not implmented yet
    }
}