import BussinessLayer.TransportationModule.objects.Logistical_Center;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class Logistical_CenterTest {

    @Test
    void is_logistical_center() {
        Logistical_Center logistical_center = new Logistical_Center("Lamdan 15", "050684575", "Logistical Center", "Yaron Avraham");
        assertTrue(logistical_center.is_logistical_center());
        assertFalse(logistical_center.is_supplier());
        assertFalse(logistical_center.is_store());
    }
}