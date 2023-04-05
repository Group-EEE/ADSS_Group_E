import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductDiscountTest {
    SupplierProduct sp1;
    SupplierProduct sp2;
    Product p1;
    Product p2;
    Manufacturer m1;
    Manufacturer m2;
    List<String> categoryList;
    Map<String,Contact> contactList;
    Supplier s1;

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
        m2 = new Manufacturer("Tara");
        p1 = new Product("Milk", m1);
        p2 = new Product ("Cheese", m2);
        sp1 = new SupplierProduct((float)2.5,"001", 40, s1, p1, s1.getMyAgreement());
        sp2 = new SupplierProduct((float)3.5,"002", 50, s1, p2, s1.getMyAgreement());
        sp1.addProductDiscount(30,20);
        sp2.addProductDiscount(50,30);
        o1 = new Order(s1);
        s1.getMyAgreement().addOrderDiscount("q",100,(float)80);
        o1.addProductToOrder(30,sp1);
        o1.addProductToOrder(50,sp2);
    }

    @Test
    void getDiscountPercentages() {
        assertEquals(30,sp1.getDiscountPercentages(30));
    }


}