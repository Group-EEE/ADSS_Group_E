package TransportModule;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

class TruckTest {

    Truck truck = new Truck("65412387", "Volvo FRS", 12000.0, 98000.5, cold_level.Cold, 56235.28);

    @Test
    void setOccupied() {
        truck.setOccupied(true);
        assertTrue(truck.Occupied());
        truck.setOccupied(false);
        assertFalse(truck.Occupied());
    }

    @Test
    void setNavigator() {

        Store store = new Store("Candy Factory", "Hertzel 36, Tel Aviv", "0506489571", "Idan levinshtain", 3);
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        truck.setNavigator(destinations);
        assertEquals(supplier, truck.get_next_site());
        assertEquals(store, truck.get_next_site());
    }

    @Test
    void getCurrent_driver() {
        assertNull(truck.getCurrent_driver());
        License license = new License(1,65432, cold_level.Freeze, 90000);
        Truck_Driver truck_driver = new Truck_Driver(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23),"test", license);
        truck.setCurrent_driver(truck_driver);
        assertEquals("daniel shapira", truck.getCurrent_driver().getFullName());
    }


    @Test
    void getCurrent_weight() {
        assertEquals(56235.28, truck.getCurrent_weight());
    }

    @Test
    void setCurrent_weight() {
        assertEquals(56235.28, truck.getCurrent_weight());
        truck.setCurrent_weight(84322.5);
        assertEquals(84322.5, truck.getCurrent_weight());
        assertNotEquals(56235.28, truck.getCurrent_weight());
    }

    @Test
    void addWeight() {
        assertEquals(56235.28, truck.getCurrent_weight());
        truck.addWeight(10000);
        assertEquals(66235.28, truck.getCurrent_weight());
    }


    @Test
    void delete_driver() {
        License license = new License(1,65432, cold_level.Freeze, 90000);
        Truck_Driver truck_driver = new Truck_Driver(209876676, "daniel", "shapira", 26, "234657",10, "a", LocalDate.of(2023,4, 23),"test", license);
        truck.setCurrent_driver(truck_driver);
        assertEquals("daniel shapira", truck.getCurrent_driver().getFullName());
        truck.delete_driver();
        assertNull(truck.getCurrent_driver());

    }

    @Test
    void getCold_level() {
        assertEquals(2, truck.getCold_level().getValue());
    }

    @Test
    void setCold_level() {
        truck.setCold_level(cold_level.Dry);
        assertEquals(3, truck.getCold_level().getValue());
    }

    @Test
    void getMax_weight() {
        assertEquals(98000.5, truck.getMax_weight());
    }

    @Test
    void getNet_weight() {
        assertEquals(12000.0, truck.getNet_weight());
    }

    @Test
    void getmodel() {
        assertEquals("Volvo FRS", truck.getModel());
    }

    @Test
    void getRegistration_plate() {
        assertEquals("65412387", truck.getRegistration_plate());
    }

    @Test
    void setMax_weight() {
        assertEquals(98000.5, truck.getMax_weight());
        truck.setMax_weight(68000);
        assertEquals(68000, truck.getMax_weight());
    }

    @Test
    void setmodel() {
        assertEquals("Volvo FRS", truck.getModel());
        truck.setModel("MAN");
        assertEquals("MAN", truck.getModel());
    }

    @Test
    void setNet_weight() {
        assertEquals(12000.0, truck.getNet_weight());
        truck.setNet_weight(10000);
        assertEquals(10000.0, truck.getNet_weight());

    }

    @Test
    void setRegistration_plate() {
        assertEquals("65412387", truck.getRegistration_plate());
        truck.setRegistration_plate("984365");
        assertEquals("984365", truck.getRegistration_plate());
    }

    @Test
    void testEquals() {
        String truck_number = "65412387";
        String truck_number1 = "64532187";
        assertTrue(truck.equals(truck_number));
        assertFalse(truck.equals(truck_number1));
        Truck truck0 = new Truck("65412387", "Volvo FRS", 12000.0, 98000.5, cold_level.Cold, 56235.28);
        Truck truck1 = new Truck("9843464", "MAN", 10000.0, 98000.5, cold_level.Cold, 56235.28);
        assertTrue(truck.equals(truck0));
        assertFalse(truck.equals(truck1));
    }

}