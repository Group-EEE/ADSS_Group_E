package HR.Controllers;

import BussinessLayer.HRModule.Controllers.EmployeeController;
import BussinessLayer.HRModule.Controllers.Facade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {

    static EmployeeController _employeeController;
    @BeforeAll
    static void setUpAll() {
        _employeeController = EmployeeController.getInstance();
    }

    @BeforeEach
    void setUp() {
        _employeeController.createEmployee(9999, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest");
    }

    @AfterEach
    void tearDown() {
        _employeeController.removeEmployee(9999);
    }

    @Test
    void login() {
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.login(209876676, "falsePassword");
        });
        assertEquals(_employeeController.login(9999, "passwordTest"),_employeeController.getEmployee(9999));
    }
}
