package HR.Objects;

import BussinessLayer.HRModule.Objects.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    Store store;

    @BeforeEach
    void setUp() {
        store = new Store("testName", "testAddress", "testPhone", "testContactName",0);
    }

    @Test
    void getName() {
        assertEquals("testName", store.getName());
    }

    @Test
    void getAddress() {
        assertEquals("testAddress", store.getAddress());
    }

    @Test
    void getArea(){
        assertEquals(0, store.get_area());
    }

    @Test
    void is_store(){
        assertTrue(store.is_store());
    }

    @Test
    void is_supplier(){
        assertFalse(store.is_supplier());
    }

    @Test
    void is_logistical_center(){
        assertFalse(store.is_logistical_center());
    }

    @Test
    void toStringTest(){
        assertEquals("Store Name: testName, Store Address: testAddress, Store Phone: testPhone, Store Contact Name: testContactName, Store Area: 0", store.toString());
    }
}