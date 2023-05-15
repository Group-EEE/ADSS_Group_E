package TransportModule;

import BussinessLayer.TransportationModule.objects.License;
import BussinessLayer.TransportationModule.objects.cold_level;
import org.junit.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class LicenseTest {

    @Test
    void setL_ID() {
        License license = new License(1,646843, cold_level.Cold, 98465.23);
        assertEquals(646843, license.getL_ID());
        license.setL_ID(513265);
        assertEquals(513265, license.getL_ID());
    }

    @Test
    void getL_ID() {
        License license = new License(1,646843, cold_level.Cold, 98465.23);
        assertEquals(646843, license.getL_ID());
        license.setL_ID(984651);
        assertEquals(984651, license.getL_ID());    }

    @Test
    void setCold_level() {
        License license = new License(1,954867, cold_level.Cold, 98347.98);
        assertEquals(2, license.getCold_level().getValue());
        license.setCold_level(cold_level.Dry);
        assertNotEquals(2, license.getCold_level().getValue());
        assertEquals(3, license.getCold_level().getValue());
        license.setCold_level(cold_level.Freeze);
        assertNotEquals(3, license.getCold_level().getValue());
        assertEquals(1, license.getCold_level().getValue());

    }

    @Test
    void getCold_level() {
        License license = new License(1,65468, cold_level.Freeze, 58765.25);
        assertEquals(1, license.getCold_level().getValue());
        assertNotEquals(2, license.getCold_level().getValue());
        assertNotEquals(3, license.getCold_level().getValue());
        License license1 = new License(2,32154, cold_level.Cold, 47612.30);
        assertEquals(2, license1.getCold_level().getValue());
        assertNotEquals(1, license1.getCold_level().getValue());
        assertNotEquals(3, license1.getCold_level().getValue());
        License license2 = new License(3,874351, cold_level.Dry, 94683.65);
        assertEquals(3, license2.getCold_level().getValue());
        assertNotEquals(1, license2.getCold_level().getValue());
        assertNotEquals(2, license2.getCold_level().getValue());
    }

//    @Test
//    void getWeight() {
//        License license = new License(1,324543, cold_level.Freeze, 4555.90);
//        assertEquals(4555.90, license.getWeight());
//        license.setWeight(93475);
//        assertEquals(93475, license.getWeight());
//    }
//
//    @Test
//    void setWeight() {
//        License license = new License(1,846959, cold_level.Cold, 984653.20);
//        assertEquals(984653.20, license.getWeight());
//        license.setWeight(8463321);
//        assertEquals(8463321, license.getWeight());
//    }
}