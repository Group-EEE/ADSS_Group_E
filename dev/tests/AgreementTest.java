import SuppliersModule.Business.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AgreementTest {
    SupplierProduct sp1;
    SupplierProduct sp2;
    GenericProduct p1;
    GenericProduct p2;
    Manufacturer m1;
    List<String> categoryList;
    Map<String, Contact> contactList;
    Supplier s1;

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
        p2 = new GenericProduct ("Cheese", m1, 2);
        sp1 = new SupplierProduct((float)2.5,"001", 40, s1, p1, s1.getMyAgreement());
        sp2 = new SupplierProduct((float)3.5,"002", 50, s1, p2, s1.getMyAgreement());
        s1.getMyAgreement().addOrderDiscount("q",100,(float)80);
    }

    @Test
    void deleteProductFromTheAgreement() {
        assertEquals(2, s1.getMyAgreement().getProductInAgreement().size());
        s1.getMyAgreement().deleteProductFromTheAgreement(sp1);
        assertEquals(1, s1.getMyAgreement().getProductInAgreement().size());
        s1.getMyAgreement().deleteProductFromTheAgreement(sp2);
        assertEquals(0, s1.getMyAgreement().getProductInAgreement().size());
    }


    @Test
    void CheckIfExistOrderDiscount() {
        assertEquals(true, s1.getMyAgreement().CheckIfExistOrderDiscount("q",100));
        assertEquals(false, s1.getMyAgreement().CheckIfExistOrderDiscount("p",60));

    }
}