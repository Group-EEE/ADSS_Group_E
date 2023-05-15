package TransportModule;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.Site_Supply;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.Assert.*;


class Site_SupplyTest {
    Store store = new Store("Afula market","Nahal avner 52, Afula", "086452317",  "Yaniv Bitton", 1);
    Site_Supply siteSupply = new Site_Supply(123456789, store, "Lamdan 15, Nof Hagalil");

    @Test
    void getOrigin() {
        assertEquals("Lamdan 15, Nof Hagalil", siteSupply.getOrigin());
    }

    @Test
    void getId() {
        assertEquals(123456789, siteSupply.getId());
    }

    @Test
    void getStore() {
        assertEquals(store, siteSupply.getStore());
    }

    @Test
    void setStore() {
        assertEquals("Afula market", siteSupply.getStore().getSite_name());
        Store store1 = new Store("Sombrero","Ben Yahuda 322, Yeruham", "086589593", "Noam Kishon", 1);
        siteSupply.setStore(store1);
        assertEquals(store1, siteSupply.getStore());
        assertEquals("Sombrero", siteSupply.getStore().getSite_name());
    }

    @Test
    void setId() {
        assertEquals(123456789, siteSupply.getId());
        siteSupply.setId(987654321);
        assertEquals(987654321, siteSupply.getId());
    }

    @Test
    void getItems() {
        siteSupply.insert_item("Bamba", 13);
        siteSupply.insert_item("Bisli", 65);
        siteSupply.insert_item("Abadi", 36);
        siteSupply.insert_item("Chips", 100);
        Map<String, Integer> products = siteSupply.getItems();
        assertTrue(products.containsKey("Bamba"));
        assertTrue(products.containsKey("Chips"));
//        assertEquals(65, products.get("Bisli"));
//        assertEquals(36, products.get("Abadi"));
        assertFalse(products.containsKey("Cola"));
    }

    @Test
    void insert_item() {
        siteSupply.insert_item("Bamba", 13);
        Map<String, Integer> products = siteSupply.getItems();
//        assertEquals(13, products.get("Bamba"));
    }

    @Test
    void testEquals() {
        Site_Supply siteSupply1 = new Site_Supply(123456789, store, "Lamdan 15, Nof Hagalil");
        Site_Supply siteSupply2 = new Site_Supply(987654321, store, "Lamdan 15, Nof Hagalil");
        assertTrue(siteSupply.equals(siteSupply1));
        assertFalse(siteSupply.equals(siteSupply2));
    }

    @Test
    void testEquals1() {
        assertTrue(siteSupply.getId() == 123456789);
        assertFalse(siteSupply.getId() == 987654321);
    }


    @Test
    void setProducts_total_weight() {
        siteSupply.setProducts_total_weight(8746);
        assertEquals(8746, siteSupply.getProducts_total_weight(), 0);
        siteSupply.setProducts_total_weight(546.23);
        assertEquals(546.23, siteSupply.getProducts_total_weight(), 0);
    }

    @Test
    void getProducts_total_weight() {
        siteSupply.setProducts_total_weight(8746);
        assertEquals(8746, siteSupply.getProducts_total_weight(), 0);
    }
}