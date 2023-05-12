package HR.Controllers;

import BussinessLayer.HRModule.Controllers.EmployeeController;
import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.TransportationModule.objects.cold_level;
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
            _employeeController.login(-1, "somepassword");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.login(9999, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.login(1230123981, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.login(9999, "falsePassword");
        });
        assertEquals(_employeeController.login(9999, "passwordTest"),_employeeController.getEmployee(9999));
    }

    @Test
    void createEmployee(){
        assertTrue(_employeeController.createEmployee(9998, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest"));
        assertEquals(_employeeController.getEmployee(9998).getEmployeeID(),9998);
        assertTrue(_employeeController.removeEmployee(9998));
    }
    @Test
    void createDriver(){

        assertTrue(_employeeController.createDriver(9998, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.now(),"passwordTest",9999, cold_level.Cold,33.3));
        assertEquals(_employeeController.getDriver(9998).getEmployeeID(),9998);
        assertTrue(_employeeController.removeDriver(9998));
    }

    @Test
    void existsEmployee(){
        assertTrue(_employeeController.existsEmployee(9999));
        assertFalse(_employeeController.existsEmployee(9998));
    }

    @Test
    void updatePassword(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updatePassword(9999, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updatePassword(-1, "falseFirstName");
        });
        assertTrue(_employeeController.updatePassword(9999,"newPassword"));
        assertEquals(_employeeController.login(9999,"newPassword"),_employeeController.getEmployee(9999));
        assertTrue(_employeeController.updatePassword(9999,"passwordTest"));
        assertEquals(_employeeController.getEmployee(9999).getPassword(),"passwordTest");
    }

    @Test
    void updateFirstName(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updateFirstName(9999, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updateFirstName(-1, "falseFirstName");
        });
        assertTrue(_employeeController.updateFirstName(9999,"newFirstName"));
        assertEquals(_employeeController.getEmployee(9999).getFirstName(),"newFirstName");
    }

    @Test
    void updateLastName(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updateLastName(9999, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updateLastName(-1, "falseLastName");
        });
        assertTrue(_employeeController.updateLastName(9999,"newLastName"));
        assertEquals(_employeeController.getEmployee(9999).getLastName(),"newLastName");
    }

    @Test
    void updateBankAccount(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updateBankAccount(9999, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.updateBankAccount(-1, "falseBankAccount");
        });
        assertTrue(_employeeController.updateBankAccount(9999,"newBankAccount"));
        assertEquals(_employeeController.getEmployee(9999).getBankAccount(),"newBankAccount");
    }

    @Test
    void getEmployee(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.getEmployee(-1);
        });
        assertEquals(_employeeController.getEmployee(9999).getEmployeeID(),9999);
    }

    @Test
    void removeEmployee(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.removeEmployee(-1);
        });
        assertTrue(_employeeController.removeEmployee(9999));
        assertFalse(_employeeController.existsEmployee(9999));
    }

    @Test
    void removeDriver(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.removeDriver(-1);
        });
    }

    @Test
    void getDriver(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.getDriver(-1);
        });
        //tests already done inside createDriver();
    }
    @Test
    void getEmployeeFullNameById(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.getEmployeeFullNameById(-1);
        });
        assertEquals(_employeeController.getEmployeeFullNameById(9999),"John Doe");
        updateFirstName();
        updateLastName();
        assertEquals(_employeeController.getEmployeeFullNameById(9999),"newFirstName newLastName");
    }

    @Test
    void getEmployeeRoles(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.getEmployeeRoles(-1);
        });
        assertEquals(_employeeController.getEmployeeRoles(9999).size(),0);
        _employeeController.addRoleToEmployee(9999, RoleType.Driver);
        assertEquals(_employeeController.getEmployeeRoles(9999).size(),1);
    }

    @Test
    void removeRoleFromEmployee(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.removeRoleFromEmployee(-1,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.removeRoleFromEmployee(9999,0);
        });
        _employeeController.addRoleToEmployee(9999, RoleType.Driver);
        assertEquals(_employeeController.getEmployeeRoles(9999).size(),1);
        _employeeController.removeRoleFromEmployee(9999,0);
        assertEquals(_employeeController.getEmployeeRoles(9999).size(),0);
    }

    @Test
    void addRoleToEmployee(){
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.addRoleToEmployee(-1,RoleType.Driver);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            _employeeController.addRoleToEmployee(9999,null);
        });
        assertEquals(_employeeController.getEmployeeRoles(9999).size(),0);
        _employeeController.addRoleToEmployee(9999, RoleType.Driver);
        assertEquals(_employeeController.getEmployeeRoles(9999).size(),1);
    }



}
