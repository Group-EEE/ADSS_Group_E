package BussinessLayer.TransportationModule.objects;

public class Supplier extends Site{
    public Supplier(String address, String phone, String supplier_name, String contact_name) {
        super(address, phone, supplier_name, contact_name);
    }

    @Override
    public boolean is_supplier() {
        return true;
    }

    @Override
    public boolean is_logistical_center() {
        return false;
    }

    @Override
    public boolean is_store() {
        return false;
    }

    public String getSupplier_name() {
        return getSite_name();
    }

    public void setSupplier_name(String supplier_name) {
        this.site_name = supplier_name;
    }

    @Override
    public void siteDisplay() {
        super.siteDisplay();
    }

    public void supplierDisplay() {
        System.out.println("\t Supplier Address: " + address);
        System.out.println("\t Supplier Phone Number: " + phone);
        System.out.println("\t Supplier Name: " + this.site_name);
        System.out.println("\t Contact Name: " + this.site_contact_name);
        System.out.println();
    }
}
