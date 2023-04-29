//
//import BussinessLayer.HRModule.Controllers.EmployeeController;
//import BussinessLayer.HRModule.Controllers.Facade;
//import BussinessLayer.HRModule.Objects.Employee;
//import BussinessLayer.HRModule.Objects.RoleType;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class EmployeeTest {
//
//    Facade _facade = Facade.getInstance();
//    @Test
//    void addStore() {
//        _facade.createEmployee(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23);
//        _facade.createStore(1,"a", "b");
//        assertTrue(_facade.addEmployeeToStore(209876676, "a"));
//    }
//
//    @Test
//    void get_first_name() {
//        Employee employee = new Employee(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23));
//        assertEquals(_facade.getEmployeeNameById(209876676), EmployeeController.getInstance().getEmployeeNameById(209876676));
//    }
//
//    @Test
//    void setRole() {
//        _facade.createEmployee(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23),"a",false);
//        //assertTrue(employee.addRole(RoleType.Cleaner));
//
//    }
//
//    @Test
//    void getRoles() {
//        Employee employee = new Employee(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23);
//        employee.addRole(RoleType.Cleaner);
//        employee.addRole(RoleType.Cashier);
//        assertEquals(RoleType.Cleaner, employee.getRoles().get(0));
//        assertEquals(RoleType.Cashier,employee.getRoles().get(1));
//    }
//}