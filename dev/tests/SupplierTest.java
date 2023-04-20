import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class SupplierTest {

    List<String> categoryList;
    Map<String,Contact> contactList;
    Supplier s1;
    SupplierProduct sp1;
    Product p1;
    Manufacturer m1;

    Order o1;

    @BeforeEach
    public void setUp() {
        contactList = new HashMap<String, Contact>();
        categoryList = new ArrayList<String>();
        contactList.put("0502384932",new Contact("Mor", "0502384932"));
        categoryList.add("Dairy");
        s1 = new Supplier("yoni", "0123", "232453", PaymentTerm.Net, contactList, categoryList,
                true, true, new boolean[]{true, true, false, false, false, true, true},-1);
        m1 = new Manufacturer("Tnuva");
        p1 = new Product("Milk", m1);
        sp1 = new SupplierProduct((float)5.5,"023", 5, s1, p1 , s1.getMyAgreement());
        o1 = new Order(s1);
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
        assertEquals(sp1,s1.getSupplierProduct("Milk", "Tnuva"));
    }

    @Test
    void deleteSupplierProduct() {
        s1.deleteSupplierProduct(sp1);
        assertEquals(s1.getSupplierProduct("Milk", "Tnuva"),null);
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