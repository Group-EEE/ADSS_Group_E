package SuppliersModule.DataAccess;

import SuppliersModule.Business.OrderDiscount;
import SuppliersModule.Business.PaymentTerm;
import SuppliersModule.Business.Supplier;

public class test {

    public static void main(String[] args)
    {
        SuperLeeDBConnection superLeeDBConnection = SuperLeeDBConnection.getInstance();
        Supplier supplier = new Supplier("A", "A", "A" , PaymentTerm.values()[1], null, null, true, true, new boolean[]{true, true, false,false,false,true,true}, -1);
        SupplierDAO supplierDAO = SupplierDAO.getInstance(superLeeDBConnection.getConnection());
        //supplierDAO.saveSupplier(supplier);
        OrderDiscount orderDiscount = new OrderDiscount("p", 50, 20, supplierDAO.getSupplier("A").getMyAgreement());
        OrderDiscountDAO orderDiscountDAO = OrderDiscountDAO.getInstance(superLeeDBConnection.getConnection());
        //orderDiscountDAO.saveOrderDiscount("A", orderDiscount);
        System.out.println(supplierDAO.getSupplier(supplier.getSupplierNum()).getMyAgreement().getDiscountOnOrder().get(0).getDiscount());
    }
}
