package HR.DAO;

import BussinessLayer.HRModule.Controllers.EmployeeController;
import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.TransportationModule.objects.Truck_Driver;
import BussinessLayer.TransportationModule.objects.cold_level;
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

    @Test
    void updateBankAccount(){
        assertTrue(_employeesDAO.updateBankAccount(9999, "987654321"));
    }

    @Test
    void updateSalary(){
        assertTrue(_employeesDAO.updateSalary(9999, 20000));
    }

    @Test
    void updateFirstName(){
        assertTrue(_employeesDAO.updateFirstName(9999, "Jane"));
    }

    @Test
    void updateLastName(){
        assertTrue(_employeesDAO.updateLastName(9999, "Doe"));
        //assertTrue(_employeesDAO.getEmployee(9999).getLastName().equals("Doe"));
    }

    @Test
    void updateFinishedWorking(){
        assertTrue(_employeesDAO.updateFinishWorking(9999, true));
        //assertTrue(_employeesDAO.getEmployee(9999).getFinishedWorking());
    }

    @Test
    void updatePassword(){
        assertTrue(_employeesDAO.updatePassword(9999, "passwordUpdateTest"));
        //assertTrue(_employeesDAO.getEmployee(9999).getPassword().equals("passwordUpdateTest"));
    }

    @Test
    void getEmployee(){
        assertNotNull(_employeesDAO.getEmployee(9999));
        assertThrows(IllegalArgumentException.class, () -> {
            _employeesDAO.getEmployee(9998);
        });
    }

    @Test
    void existEmployee(){
        assertTrue(_employeesDAO.existEmployee(9999));
        assertFalse(_employeesDAO.existEmployee(9998));
    }

    @Test
    void checkPassword(){
        assertTrue(_employeesDAO.checkPassword(9999, "passwordTest"));
        assertFalse(_employeesDAO.checkPassword(9999, "passwordTest2"));
    }

    @Test
    void insertRoleToEmployee(){
        assertTrue(_employeesDAO.insertRoleToEmployee(9999, "Cleaner"));
    }

    @Test
    void deleteRoleFromEmployee(){
        insertRoleToEmployee();
        assertTrue(_employeesDAO.deleteRoleFromEmployee(9999, "Cleaner"));
        insertRoleToEmployee();
    }

    @Test
    void deleteAllRolesFromEmployee(){
        assertTrue(_employeesDAO.insertRoleToEmployee(9999, "Cleaner"));
        assertTrue(_employeesDAO.insertRoleToEmployee(9999, "ShiftManager"));
        assertTrue(_employeesDAO.deleteAllRolesFromEmployee(9999));
        assertTrue(_employeesDAO.insertRoleToEmployee(9999, "Cleaner"));
        assertTrue(_employeesDAO.insertRoleToEmployee(9999, "ShiftManager"));
    }

    @Test
    void existHRmanager(){
        assertTrue(_employeesDAO.existHRmanager());
        assertTrue(_employeesDAO.insertRoleToEmployee(9999, "HRmanager"));
        assertTrue(_employeesDAO.existHRmanager());
    }

    @Test
    void getHRManagerID(){
        assertTrue(_employeesDAO.getHRManagerID() > 0);
    }

    @Test
    void getDrivers(){
//        EmployeeController.getInstance().createDriver(99991, "driverTestFirstName", "driverTestLastName", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest",99991, cold_level.Cold, 9999);
//        EmployeeController.getInstance().createDriver(99992, "driverTest2FirstName", "driverTest2LastName", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest",99992, cold_level.Cold, 9999);
        for (Truck_Driver driver : _employeesDAO.getDrivers()){
            assertNotNull(driver);
        }
        EmployeeController.getInstance().removeDriver(99991);
        EmployeeController.getInstance().removeDriver(99992);
    }

}