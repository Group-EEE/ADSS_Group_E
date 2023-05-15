package TransportModule;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class NavigatorTest {

    Store store = new Store("Candy Factory","Hertzel 36, Tel Aviv", "0506489571", "Idan levinshtain", 1);
    Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
    Logistical_Center logistical_center = new Logistical_Center("Lamdan 15", "050684575", "Logistical Center", "Yaron Avraham");


    @Test
    void drive_to_next() {
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        destinations.add(logistical_center);
        Navigator navigator = new Navigator(destinations);
        assertEquals(store, navigator.drive_to_next());
        assertEquals(supplier, navigator.drive_to_next());
        assertEquals(logistical_center, navigator.drive_to_next());
    }

    @Test
    void getRoute() {
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        Navigator navigator = new Navigator(destinations);
        navigator.create_route();
        ArrayList<Site> route = navigator.getRoute();
        assertEquals(supplier, route.get(0));
        assertEquals(store, route.get(1));
    }

    @Test
    void delete_site() {
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        destinations.add(logistical_center);
        Navigator navigator = new Navigator(destinations);
        navigator.create_route();
        ArrayList<Site> route = navigator.getRoute();
        assertEquals(supplier, route.get(0));
        assertEquals(store, route.get(1));
        navigator.delete_site(supplier.getSite_name());
        assertEquals(store, route.get(0));
    }

    @Test
    void getCurrent_location() {
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        destinations.add(logistical_center);
        Navigator navigator = new Navigator(destinations);
        navigator.setCurrent_location(logistical_center);
        assertEquals(logistical_center, navigator.getCurrent_location());
        navigator.setCurrent_location(store);
        assertEquals(store, navigator.getCurrent_location());
    }

    @Test
    void add_site() {
        ArrayList<Site> destinations = new ArrayList<>();
        Navigator navigator = new Navigator(destinations);
        navigator.create_route();
        ArrayList<Site> route = navigator.getRoute();
        navigator.add_site(store);
        navigator.add_site(supplier);
        assertEquals(store, route.get(0));
        assertEquals(supplier, route.get(1));
    }
}