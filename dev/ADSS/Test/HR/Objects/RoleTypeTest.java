package HR.Objects;
import BussinessLayer.HRModule.Objects.RoleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class RoleTypeTest {

    @Test
    void toEnum() {
        assertEquals(RoleType.Cashier, RoleType.toEnum("Cashier"));
        assertEquals(RoleType.Cleaner, RoleType.toEnum("Cleaner"));
        assertEquals(RoleType.General, RoleType.toEnum("General"));
        assertEquals(RoleType.Warehouse, RoleType.toEnum("Warehouse"));
        assertEquals(RoleType.ShiftManager, RoleType.toEnum("ShiftManager"));
        assertEquals(RoleType.Security, RoleType.toEnum("Security"));
        assertEquals(RoleType.Usher, RoleType.toEnum("Usher"));
        assertEquals(RoleType.HRManager, RoleType.toEnum("HRManager"));
        assertNull(RoleType.toEnum("test"));
    }
}
