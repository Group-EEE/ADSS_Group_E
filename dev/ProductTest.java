import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class ProductTest {

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
    void deleteSupplierProduct() {
        s1.deleteSupplierProduct(s1.getSupplierProduct("Milk","Tnuva"));
        assertEquals(0, s1.getMyProducts().size());


    }

    void getSuppliers() {
        assertEquals(1, p1.getMySuppliersProduct().size());
    }


}