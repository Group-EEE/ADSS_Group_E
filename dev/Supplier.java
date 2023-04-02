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

    public String getSupplier_n() {
        return supplier_n;
    }

    public void setSupplier_n(String supplier_n) {
        this.supplier_n = supplier_n;
    }
}
