abstract public class Site {
    protected String address, phone, site_n, site_contact_n, supplier_n;


    public Site(String address, String phone, String site_name, String site_contact_name){
        this.address = address;
        this.phone = phone;
        this.site_n = site_name;
        this.site_contact_n = site_contact_name;
    }
    public abstract boolean is_supplier();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite_n() {
        return site_n;
    }

    public void setSite_n(String site_n) {
        this.site_n = site_n;
    }

    public String getSupplier_n() {
        return supplier_n;
    }

    public void setSupplier_n(String supplier_n) {
        this.supplier_n = supplier_n;
    }
}
