public class Supplier extends Site{
    private String supplier_n;
    public Supplier(String address, String phone, String site_name, String supplier_name, String contact_name) {
        super(address, phone, site_name, contact_name);
        this.supplier_n = supplier_name;
    }

    @Override
    public boolean is_supplier() {
        return true;
    }

    public String getSupplier_n() {
        return supplier_n;
    }

    public void setSupplier_n(String supplier_n) {
        this.supplier_n = supplier_n;
    }
}
