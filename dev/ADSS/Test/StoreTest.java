
import BussinessLayer.Controllers.Facade;
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Test
    void getName() {
        Store store = new Store(1, "a", "b");
        assertEquals("a", store.getName());
    }

    @Test
    void addEmployee() {
        Facade facade = Facade.getInstance();
        facade.createStore(1, "a", "b");
        facade.createEmployee("daniel", "shapira", 26, 209876676, "234657", "a", false);
        assertTrue(facade.addEmployeeToStore(209876676, "a"));
    }



    @Test
    void removeEmployee() {
        Facade facade = Facade.getInstance();
        facade.createStore(1, "a", "b");
        facade.createEmployee("daniel", "shapira", 26, 209876676, "234657", "a", false);
        assertTrue(facade.addEmployeeToStore(209876676, "a"));
        assertTrue(facade.removeEmployeeFromStore(209876676, "a"));
    }

    @Test
    void getAddress() {
        Store store = new Store(1,"a", "b");
        assertEquals("b", store.getAddress());
    }
}