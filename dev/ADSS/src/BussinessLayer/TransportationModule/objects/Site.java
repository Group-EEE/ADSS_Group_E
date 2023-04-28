package BussinessLayer.TransportationModule.objects;

abstract public class Site {
    protected String address, phone, site_name, site_contact_name;


    public Site(String address, String phone, String site_name, String site_contact_name){
        this.address = address;
        this.phone = phone;
        this.site_name = site_name;
        this.site_contact_name = site_contact_name;
    }

    public abstract boolean is_supplier();
    public abstract boolean is_logistical_center();
    public abstract boolean is_store();

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


    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_contact_name() {
        return site_contact_name;
    }

    public void setSite_contact_name(String site_contact_name) {
        this.site_contact_name = site_contact_name;
    }

    // display
    public void siteDisplay(){
        System.out.println("\t\t Address: " + address);
        System.out.println("\t\t Phone: " + phone);
        System.out.println("\t\t Site name: " + site_name);
        System.out.println("\t\t Person contact name: " + site_contact_name);
    }
}
