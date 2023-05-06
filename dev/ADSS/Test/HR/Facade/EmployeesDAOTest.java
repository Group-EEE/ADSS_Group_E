package HR.Objects;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Employee;
import DataAccessLayer.HRMoudle.EmployeesDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeesDAOTest {
    private Employee employee;
    private static Facade _facade;
    private static EmployeesDAO _employeesDAO;

    @BeforeAll
    static void setUpAll() {
        _facade = Facade.getInstance();
        _employeesDAO = EmployeesDAO.getInstance();
    }

    @BeforeEach
    void setUp() {
        _facade.createEmployee(9999, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.now(),"a",false);
    }

    @AfterEach
    void tearDown() {
        _facade.removeEmployee(9999);
    }

    @Test
    void existsEmployee(){
        assertTrue(_employeesDAO.existEmployee(1));
    }


}