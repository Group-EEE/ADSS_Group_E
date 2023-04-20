package SuppliersModule.Business.Generator;

import SuppliersModule.Business.Contact;
import SuppliersModule.Business.Controllers.SupplierController;
import SuppliersModule.Business.PaymentTerm;
import SuppliersModule.Business.Supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierGenerator {

    static SupplierGenerator supplierGenerator;

    SupplierController supplierController;


    Map<String, Contact> contacts;

    private SupplierGenerator() {
        reset();
        supplierController = SupplierController.getInstance();
    }

    public void reset() {
        contacts = new HashMap<>();
    }

    public static SupplierGenerator getInstance() {
        if (supplierGenerator == null)
            supplierGenerator = new SupplierGenerator();
        return supplierGenerator;
    }

    public void addContact(String name, String phoneNumber) {
        contacts.put(phoneNumber, new Contact(name, phoneNumber));
    }

    public void CreateSupplierAndAgreement(String name, String supplierNum, String bankAccount, PaymentTerm payment, List<String> categories,
                                           boolean hasPermanentDays, boolean isSupplierBringProduct, boolean[] deliveryDays, int numberOfDaysToSupply) {
        Supplier supplier = new Supplier(name, supplierNum, bankAccount, payment, contacts, categories,
                hasPermanentDays, isSupplierBringProduct, deliveryDays, numberOfDaysToSupply);
        supplierController.addNewSupplier(supplier);
    }
}
