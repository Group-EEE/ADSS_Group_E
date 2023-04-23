package SuppliersModule.DataAccess;

import SuppliersModule.Business.Agreement;
import SuppliersModule.Business.PaymentTerm;
import SuppliersModule.Business.Supplier;

public class test {

    public static void main(String[] args)
    {
        SuperLeeDBConnection superLeeDBConnection = SuperLeeDBConnection.getInstance();
        AgreementDAO agreementDAO = AgreementDAO.getInstance(superLeeDBConnection.getConnection());
        Supplier supplier = new Supplier("A", "A", "A" , PaymentTerm.values()[1], null, null, true, true, new boolean[]{true, true, false,false,false,true,true}, -1);
        //agreementDAO.saveAgreement(supplier.getMyAgreement());
        //System.out.println(agreementDAO.getAgreement("A").getNumberOfDaysToSupply());
        //agreementDAO.updateAgreement("A", false, false, new boolean[]{false, false, false,false,false,false,false}, 6);
        //agreementDAO.deleteAgreement("A");
    }
}
