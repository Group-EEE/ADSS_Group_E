import SuppliersModule.Business.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderFromSupplierTest {
    SupplierProduct sp1;
    SupplierProduct sp2;
    GenericProduct p1;
    GenericProduct p2;
    Manufacturer m1;
    Manufacturer m2;
    List<String> categoryList;
    Map<String,Contact> contactList;
    Supplier s1;

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
        m2 = new Manufacturer("Tara");
        p1 = new GenericProduct("Milk", m1, 1);
        p2 = new GenericProduct ("Cheese", m2, 2);
        sp1 = new SupplierProduct((float)2.5,"001", 40, s1, p1, s1.getMyAgreement());
        sp2 = new SupplierProduct((float)3.5,"002", 50, s1, p2, s1.getMyAgreement());
        sp1.addProductDiscount(30,20);
        sp2.addProductDiscount(50,30);
        o1 = new OrderFromSupplier(s1);
        s1.getMyAgreement().addOrderDiscount("q",100,(float)80);
        o1.addProductToOrder(30,sp1);
        o1.addProductToOrder(50,sp2);
    }

    @Test
    void addProductToOrder() {
        assertEquals(2, o1.getProductsInOrder().size());
    }

    @Test
    void getTotalPrice(){
        assertEquals((float) 140, o1.getPriceBeforeTotalDiscount(), 0.001);
        s1.getMyAgreement().addOrderDiscount("q",80,(float)80);
        assertEquals((float) 28, o1.getTotalPriceAfterDiscount(), 0.001);
    }

    @Test
    void invite() {
        assertEquals(0,s1.getMyOrders().size());
        o1.invite();
        assertEquals(1,s1.getMyOrders().size());
    }

    @Test
    void getPriceBeforeTotalDiscount() {
        assertEquals((float) 140, o1.getPriceBeforeTotalDiscount(), 0.001);
    }
}