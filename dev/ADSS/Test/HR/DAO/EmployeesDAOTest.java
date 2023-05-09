package HR.DAO;

import BussinessLayer.HRModule.Controllers.Facade;
import DataAccessLayer.HRMoudle.EmployeesDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeesDAOTest {
    private static EmployeesDAO _employeesDAO;

    @BeforeAll
    static void setUpAll() {
        _employeesDAO = EmployeesDAO.getInstance();
    }

    @BeforeEach
    void setUp() {
        _employeesDAO.insertEmployee(9999, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest");
    }

    @AfterEach
    void tearDown() {
        _employeesDAO.deleteEmployee(9999);
    }

    @Test
    void existsEmployee(){
        assertTrue(_employeesDAO.existEmployee(9999));
    }

    @Test
    void doubleCreate(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeesDAO.insertEmployee(9999, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest");
        });
    }

    @Test
    void selectAllEmployees(){
        assertTrue(_employeesDAO.SelectAllEmployees().size() > 0);
    }
}