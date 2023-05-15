package TransportModule;

import BussinessLayer.TransportationModule.objects.Supplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    @Test
    void is_supplier() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        assertFalse(supplier.is_logistical_center());
        assertTrue(supplier.is_supplier());
        assertFalse(supplier.is_store());
    }

    @Test
    void getSupplier_n() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        assertEquals("Osem", supplier.getSite_name());
    }

    @Test
    void setSupplier_n() {
        Supplier supplier = new Supplier("Ben Gurion", "054876542", "Osem", "David Shafir");
        supplier.setSupplier_name("Tnuva");
        assertEquals("Tnuva", supplier.getSupplier_name());
        assertNotEquals("Osem", supplier.getSupplier_name());
    }
}