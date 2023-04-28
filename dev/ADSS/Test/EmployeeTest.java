
import BussinessLayer.Controllers.EmployeeController;
import BussinessLayer.Controllers.Facade;
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void addStore() {
        Facade.getInstance().createEmployee("daniel", "shapira", 26, 209876676, "234657", "a", false);
        Facade.getInstance().createStore(1,"a", "b");
        assertTrue(Facade.getInstance().addEmployeeToStore(209876676, "a"));
    }

    @Test
    void get_first_name() {
        Facade.getInstance().createEmployee("daniel", "shapira", 26, 209876676, "234657", "a", false);
        assertEquals(Facade.getInstance().getEmployeeNameById(209876676), EmployeeController.getInstance().getEmployeeNameById(209876676));
    }

    @Test
    void setRole() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        assertTrue(employee.addRole(RoleType.Cleaner));

    }

    @Test
    void getRoles() {
        Employee employee = new Employee("daniel", "shapira", 26, 209876676, "234657");
        employee.addRole(RoleType.Cleaner);
        employee.addRole(RoleType.Cashier);
        assertEquals(RoleType.Cleaner, employee.getRoles().get(0));
        assertEquals(RoleType.Cashier,employee.getRoles().get(1));
    }
}