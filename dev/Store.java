public class Store extends Site{
    private String manager_n;
    private int site_area;


    public Store(String address, String phone, String site_name, String manager_name, int site_area, String contact_name){
        super(address, phone, site_name, contact_name);
        this.manager_n = manager_name;
        this.site_area = site_area;
    }

    @Override
    public boolean is_supplier() {
        return false;
    }

    @Override
    public boolean is_logistical_center() {
        return false;
    }

    @Override
    public boolean is_store() {
        return true;
    }

    public String getManager_n() {
        return manager_n;
    }

    public void setManager_n(String manager_n) {
        this.manager_n = manager_n;
    }

    public int getSite_area() {
        return site_area;
    }

    public void setSite_area(int site_area) {
        this.site_area = site_area;
    }
}
