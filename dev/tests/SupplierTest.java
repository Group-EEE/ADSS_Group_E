import SuppliersModule.Business.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SupplierTest {

    List<String> categoryList;
    Map<String,Contact> contactList;
    Supplier s1;
    SupplierProduct sp1;
    GenericProduct p1;
    Manufacturer m1;

    OrderFromSupplier o1;

    @BeforeEach
    public void setUp() {
        contactList = new HashMap<String, Contact>();
        categoryList = new ArrayList<String>();
        contactList.put("0502384932",new Contact("Mor", "0502384932"));
        categoryList.add("Dairy");
        s1 = new Supplier("yoni", "0123", "232453", PaymentTerm.Net, contactList, categoryList,
                true, true, new boolean[]{true, true, false, false, false, true, true},-1);
        m1 = new Manufacturer("Tnuva");
        p1 = new GenericProduct("Milk", m1, 1);
        sp1 = new SupplierProduct((float)5.5,"023", 5, s1, p1 , s1.getMyAgreement());
        o1 = new OrderFromSupplier(s1);
    }

    @Test
    public void addContact() {
        assertEquals(1, s1.numberOfContacts());
        s1.addContact("Mor", "0502384932");
        assertEquals(1, s1.numberOfContacts());
        s1.addContact("May", "0544482940");
        assertEquals(2, s1.numberOfContacts());
    }

    @Test
    public void deleteContact() {
        s1.deleteContact("0504584942");
        assertEquals(1, s1.numberOfContacts());
        s1.deleteContact("0502384932");
        assertEquals(1, s1.numberOfContacts());
        s1.addContact("May", "0544482940");
        assertEquals(2, s1.numberOfContacts());
        s1.deleteContact("0502384932");
        assertEquals(1, s1.numberOfContacts());
    }

    @Test
    void addNewProduct() {
        s1.addNewProduct(sp1);
        assertEquals(sp1,s1.getSupplierProduct("023"));
    }

    @Test
    void deleteSupplierProduct() {
        s1.deleteSupplierProduct("023");
        assertNull(s1.getSupplierProduct("023"));
    }

    @Test
    void fireSupplier(){
        s1.addNewProduct(sp1);
        s1.addManufacturer(m1);
        s1.fireSupplier();
        assertEquals(0, s1.getMyManufacturers().size());
        assertEquals(0, s1.getMyProducts().size());
    }

    @Test
    void printSupplier(){
        System.out.println(s1);
    }
}