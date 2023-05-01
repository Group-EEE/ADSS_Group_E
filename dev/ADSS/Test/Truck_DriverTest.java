import BussinessLayer.TransportationModule.objects.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Truck_DriverTest {
    License license = new License(84365, cold_level.Freeze, 87002.5);
    Truck_Driver truck_driver = new Truck_Driver(984325, "Daniel Shapiro", license);

    @Test
    void setCurrent_truck() {
        Truck truck = new Truck("65412387", "Volvo FRS", 12000.0, 98000.5, cold_level.Cold, 56235.28);
        truck_driver.setCurrent_truck(truck);
        assertEquals(truck, truck_driver.getCurrent_truck());
    }

    @Test
    void add_site_document() {
        Store_to_delete store = new Store_to_delete("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        Store_to_delete store1 = new Store_to_delete("Golomb 67", "05468132384", "Candy World", "Hila Naim", 2, "Ido Moshe");
        Site_Supply site_supply = new Site_Supply(846635, store, "Osem");
        Site_Supply site_supply1 = new Site_Supply(416535, store1, "Tnuva");
        ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
        siteSupplies.add(site_supply);
        siteSupplies.add(site_supply1);
        truck_driver.setSites_documents(siteSupplies);
        assertEquals(site_supply, truck_driver.getSites_documents().get(0));
        assertEquals(site_supply1, truck_driver.getSites_documents().get(1));
    }

    @Test
    void delete_truck() {
        Truck truck = new Truck("65412387", "Volvo FRS", 12000.0, 98000.5, cold_level.Cold, 56235.28);
        truck_driver.setCurrent_truck(truck);
        assertEquals(truck, truck_driver.getCurrent_truck());
        truck_driver.delete_truck();
        assertNull(truck_driver.getCurrent_truck());
    }

    @Test
    void get_document() {
        Store_to_delete store = new Store_to_delete("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        Store_to_delete store1 = new Store_to_delete("Golomb 67", "05468132384", "Candy World", "Hila Naim", 2, "Ido Moshe");
        Site_Supply site_supply = new Site_Supply(846635, store, "Osem");
        Site_Supply site_supply1 = new Site_Supply(416535, store1, "Tnuva");
        ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
        siteSupplies.add(site_supply);
        siteSupplies.add(site_supply1);
        truck_driver.setSites_documents(siteSupplies);
        assertNull(truck_driver.get_document(8746135));
        assertEquals(site_supply1, truck_driver.get_document(416535));
    }

    @Test
    void delete_site_document_by_origin() {
        Store_to_delete store = new Store_to_delete("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        Store_to_delete store1 = new Store_to_delete("Golomb 67", "05468132384", "Candy World", "Hila Naim", 2, "Ido Moshe");
        Site_Supply site_supply = new Site_Supply(846635, store, "Osem");
        Site_Supply site_supply1 = new Site_Supply(416535, store1, "Tnuva");
        ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
        siteSupplies.add(site_supply);
        siteSupplies.add(site_supply1);
        truck_driver.setSites_documents(siteSupplies);
        truck_driver.delete_site_document_by_origin("Osem");
        assertNull(truck_driver.get_document(846635));
    }

    @Test
    void delete_site_document_by_destination() {
        Store_to_delete store = new Store_to_delete("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        Store_to_delete store1 = new Store_to_delete("Golomb 67", "05468132384", "Candy World", "Hila Naim", 2, "Ido Moshe");
        Site_Supply site_supply = new Site_Supply(846635, store, "Osem");
        Site_Supply site_supply1 = new Site_Supply(416535, store1, "Tnuva");
        ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
        siteSupplies.add(site_supply);
        siteSupplies.add(site_supply1);
        truck_driver.setSites_documents(siteSupplies);
        truck_driver.delete_site_document_by_destination("Candy World");
        assertNull(truck_driver.get_document(416535));
    }

    @Test
    void delete_site_document_by_ID() {
        Store_to_delete store = new Store_to_delete("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        Store_to_delete store1 = new Store_to_delete("Golomb 67", "05468132384", "Candy World", "Hila Naim", 2, "Ido Moshe");
        Site_Supply site_supply = new Site_Supply(846635, store, "Osem");
        Site_Supply site_supply1 = new Site_Supply(416535, store1, "Tnuva");
        ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
        siteSupplies.add(site_supply);
        siteSupplies.add(site_supply1);
        truck_driver.setSites_documents(siteSupplies);
        truck_driver.delete_site_document_by_ID(416535);
        assertNull(truck_driver.get_document(416535));
    }

    @Test
    void getID() {
        assertEquals(984325, truck_driver.getID());
        truck_driver.setID(75693);
        assertEquals(75693, truck_driver.getID());

    }

    @Test
    void getLicense() {
        assertEquals(84365, truck_driver.getLicense().getL_ID());
    }

    @Test
    void getName() {
        assertEquals("Daniel Shapiro", truck_driver.getName());
    }

    @Test
    void setLicense() {
        License license1 = new License(983267, cold_level.Dry, 23487.9);
        truck_driver.setLicense(license1);
        assertEquals(license1, truck_driver.getLicense());
    }

    @Test
    void setName() {
        truck_driver.setName("Noam Peretz");
        assertEquals("Noam Peretz", truck_driver.getName());
    }

    @Test
    void is_site_exist() {
        Store_to_delete store = new Store_to_delete("Hertzel 36, Tel Aviv", "0506489571", "Candy Factory", "Idan levinshtain", 3, "Maor Peretz");
        Site_Supply site_supply = new Site_Supply(846635, store, "Osem");
        ArrayList<Site_Supply> siteSupplies = new ArrayList<>();
        siteSupplies.add(site_supply);
        truck_driver.setSites_documents(siteSupplies);
        assertTrue(truck_driver.is_site_exist("Candy Factory"));
        assertFalse(truck_driver.is_site_exist("Candy World"));
    }

    @Test
    void testEquals() {
        License license = new License(84365, cold_level.Freeze, 87002.5);
        License license1 = new License(983267, cold_level.Dry, 23487.9);
        Truck_Driver truck_driver1 = new Truck_Driver(984325, "Daniel Shapiro", license);
        Truck_Driver truck_driver2 = new Truck_Driver(94387, "Omri Navon", license1);
        assertFalse(truck_driver.equals(truck_driver2));
        assertTrue(truck_driver.equals(truck_driver1));
        assertTrue(truck_driver.equals(984325));
        assertFalse(truck_driver.equals(94387));

    }

}