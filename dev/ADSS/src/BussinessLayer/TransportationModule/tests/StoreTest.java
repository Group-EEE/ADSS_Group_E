package BussinessLayer.TransportationModule.tests;

import BussinessLayer.TransportationModule.objects.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Test
    void is_store() {
        Store store = new Store("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        assertTrue(store.is_store());
        assertFalse(store.is_supplier());
        assertFalse(store.is_logistical_center());
    }

    @Test
    void getManager_name() {
        Store store = new Store("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        assertEquals("Idan levinshtain", store.getManager_name());

    }

    @Test
    void setManager_name() {
        Store store = new Store("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        store.setManager_name("Yuval Rahamim");
        assertEquals("Yuval Rahamim", store.getManager_name());
        assertNotEquals("Idan levinshtain", store.getManager_name());
    }

    @Test
    void getSite_area() {
        Store store = new Store("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        assertEquals(3, store.getSite_area());

    }

    @Test
    void setSite_area() {
        Store store = new Store("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        store.setSite_area(8);
        assertEquals(8, store.getSite_area());
        assertNotEquals(3, store.getSite_area());
    }

}