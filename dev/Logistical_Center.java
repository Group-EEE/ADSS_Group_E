public class Logistical_Center extends Site{

     public Logistical_Center(String address, String phone, String name, String site_contact_name) {
         super(address, phone, name, site_contact_name);
     }

    @Override
    public boolean is_supplier() {
        return false;
    }

    @Override
    public boolean is_logistical_center() {
        return true;
    }

    @Override
    public boolean is_store() {
        return false;
    }

    // display

    @Override
    public void siteDisplay() {
        super.siteDisplay();
    }
}
