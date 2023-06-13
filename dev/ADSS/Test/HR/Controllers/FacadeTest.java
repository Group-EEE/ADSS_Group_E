package HR.Controllers;

import BussinessLayer.HRModule.Controllers.Facade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {

    static Facade _facade;

    @BeforeAll
    static void setUpAll() {
        _facade = Facade.getInstance();
    }

    @BeforeEach
    void setUp() {
        _facade.createEmployee(9999, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest",false);

    }

    @AfterEach
    void tearDown() {
        _facade.removeEmployee(9999);
    }

    @Test
    void login() {
        assertFalse(_facade.login(209876676, "falsePassword"));
        assertFalse(_facade.login(9999, "falsePassword"));
        assertTrue(_facade.login(9999, "passwordTest"));
    }

    @Test
    void logout(){
        login();
        assertTrue(Facade.getInstance().logout());
    }

    @Test
    void createAndFillSchedule(){
        _facade.createStore("testName", "testAddress", "testPhone", "testContactName",0);
        assertTrue(_facade.createAndFillSchedule("testName", LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        _facade.deleteSchedule("testName");
        _facade.removeStore("testName");
        _facade.deleteSchedule("Logistics");
    }
}
