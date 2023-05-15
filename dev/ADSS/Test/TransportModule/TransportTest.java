package TransportModule;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransportTest {

    Logistical_Center logistical_center = new Logistical_Center("Lamdan 15", "050684575", "Logistical Center", "Yaron Avraham");


    Transport transport = new Transport(123456789, "10/04/2023", "12:00", "82645978", "David Doron", "Logistical Center", cold_level.Freeze, "13/04/2023", 315478945);

    @Test
    void getRequired_level() {
        assertEquals(1 ,transport.getRequired_level().getValue());
    }

    @Test
    void setRequired_level() {
        transport.setRequired_level(cold_level.Dry);
        assertEquals(3, transport.getRequired_level().getValue());
    }

    @Test
    void getTransport_ID() {
        assertEquals(123456789, transport.getTransport_ID());
    }

    @Test
    void setTransport_ID() {
        transport.setTransport_ID(658495325);
        assertEquals(658495325, transport.getTransport_ID());
        assertNotEquals(123456789, transport.getTransport_ID());
    }

    @Test
    void getDate() {
        assertEquals("10/04/2023", transport.getDate());
    }

    @Test
    void setDate() {
        transport.setDate("30/08/2023");
        assertEquals("30/08/2023", transport.getDate());
        assertNotEquals("10/04/2023", transport.getDate());
    }

    @Test
    void getDeparture_time() {
        assertEquals("12:00", transport.getDeparture_time());
    }

    @Test
    void setDeparture_time() {
        transport.setDeparture_time("14:00");
        assertEquals("14:00", transport.getDeparture_time());
        assertNotEquals("12:00", transport.getDeparture_time());
    }

    @Test
    void getTruck_number() {
        assertEquals("82645978", transport.getTruck_number());
    }

    @Test
    void setTruck_number() {
        transport.setTruck_number("54631248");
        assertEquals("54631248", transport.getTruck_number());
        assertNotEquals("82645978", transport.getTruck_number());
    }

    @Test
    void getDriver_name() {
        assertEquals("David Doron", transport.getDriver_name());
    }

    @Test
    void setDriver_name() {
        transport.setDriver_name("Yehonatan Dvir");
        assertNotEquals("David Doron", transport.getDriver_name());
        assertEquals("Yehonatan Dvir", transport.getDriver_name());
    }

    @Test
    void getOrigin() {
        assertEquals("Logistical Center", transport.getOrigin());
    }

    @Test
    void setOrigin() {
        transport.setOrigin("Logistical Center new");
        assertEquals("Logistical Center new", transport.getOrigin());
    }

    @Test
    void getDestinations() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        Store store = new Store("Candy Factory", "Hertzel 36, Tel Aviv", "0506489571",  "Idan levinshtain", 3);
        Store store1 = new Store("Candy World", "Derech hashalom", "0523147859",  "Tamar Yahalom", 7);
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        destinations.add(store1);
        transport.setDestinations(destinations);
        assertEquals(store1, transport.getDestinations().get(2));
        assertEquals(store, transport.getDestinations().get(0));

    }


    @Test
    void number_of_suppliers() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        Store store = new Store("Candy Factory", "Hertzel 36, Tel Aviv", "0506489571", "Idan levinshtain", 3);
        Store store1 = new Store("Candy World", "Derech hashalom", "0523147859", "Tamar Yahalom", 7);
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        destinations.add(store1);
        transport.setDestinations(destinations);
        assertEquals(1, transport.Number_of_suppliers());
    }

    @Test
    void number_of_stores() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        Store store = new Store("Candy Factory", "Hertzel 36, Tel Aviv", "0506489571", "Idan levinshtain", 3);
        Store store1 = new Store("Candy World", "Derech hashalom", "0523147859", "Tamar Yahalom", 7);
        ArrayList<Site> destinations = new ArrayList<>();
        destinations.add(store);
        destinations.add(supplier);
        destinations.add(store1);
        transport.setDestinations(destinations);
        assertEquals(2, transport.Number_of_stores());
    }

    @Test
    void insertToDestinations() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        Store store = new Store("Candy Factory", "Hertzel 36, Tel Aviv", "0506489571", "Idan levinshtain", 3);
        Store store1 = new Store("Candy World", "Derech hashalom", "0523147859", "Tamar Yahalom", 7);
        transport.insertToDestinations(store1);
        transport.insertToDestinations(supplier);
        transport.insertToDestinations(store);
        assertEquals(store1, transport.getDestinations().get(0));
        assertEquals(supplier, transport.getDestinations().get(1));
        assertEquals(store, transport.getDestinations().get(2));
    }

    @Test
    void insertToWeights() {
        transport.insertToWeights(456.23);
        assertEquals(456.23, transport.get_last_weight());
        transport.insertToWeights(4975.32);
        assertEquals(4975.32, transport.get_last_weight());
        transport.insertToWeights(648.84);
        assertEquals(648.84, transport.get_last_weight());

    }

    @Test
    void insertToProducts() {
        transport.insertToProducts("Shampo", 654);
        transport.insertToProducts("Abadi", 751);
        transport.insertToProducts("Milk", 345);
        transport.insertToProducts("Cookies", 935);
        assertEquals(654, transport.getProduct("Shampo"));
        assertEquals(751, transport.getProduct("Abadi"));
        assertEquals(345, transport.getProduct("Milk"));
        assertEquals(935, transport.getProduct("Cookies"));
    }

    @Test
    void deleteDestination() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        Store store = new Store("Candy Factory", "Hertzel 36, Tel Aviv", "0506489571", "Idan levinshtain", 3);
        Store store1 = new Store("Candy World", "Derech hashalom", "0523147859", "Tamar Yahalom", 7);
        transport.insertToDestinations(store1);
        transport.insertToDestinations(supplier);
        transport.insertToDestinations(store);
        assertEquals(supplier, transport.getDestinations().get(1));
        transport.deleteDestination(supplier.getSite_name());
        assertEquals(store, transport.getDestinations().get(1));
    }


    @Test
    void deleteProducts() {
        transport.insertToProducts("Shampo", 654);
        transport.insertToProducts("Abadi", 751);
        transport.insertToProducts("Milk", 345);
        transport.insertToProducts("Cookies", 935);
        assertEquals(654, transport.getProduct("Shampo"));
        assertEquals(751, transport.getProduct("Abadi"));
        assertEquals(345, transport.getProduct("Milk"));
        assertEquals(935, transport.getProduct("Cookies"));
        transport.deleteProducts("Shampo", 500);
        assertEquals(154, transport.getProduct("Shampo"));
        transport.deleteProducts("Cookies", 700);
        assertEquals(235, transport.getProduct("Cookies"));
    }

    @Test
    void getStoreByName() {
        Store store = new Store("Candy Factory", "Hertzel 36, Tel Aviv", "0506489571", "Idan levinshtain", 3);
        Store store1 = new Store("Candy World", "Derech hashalom", "0523147859", "Tamar Yahalom", 7);
        transport.insertToDestinations(store);
        transport.insertToDestinations(store1);
        assertEquals(store, transport.getStoreByName("Candy Factory"));
        assertEquals(store1, transport.getStoreByName("Candy World"));
    }

    @Test
    void delete_last_Weight() {
        transport.insertToWeights(456.23);
        transport.insertToWeights(4975.32);
        transport.insertToWeights(648.84);
        assertEquals(648.84, transport.get_last_weight());
        transport.delete_last_Weight();
        assertEquals(4975.32, transport.get_last_weight());
    }

}