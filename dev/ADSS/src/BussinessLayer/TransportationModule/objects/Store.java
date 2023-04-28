package BussinessLayer.TransportationModule.objects;

public class Store extends Site{
    private String manager_name;
    private int site_area;


    public Store(String address, String phone, String site_name, String manager_name, int site_area, String contact_name){
        super(address, phone, site_name, contact_name);
        this.manager_name = manager_name;
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

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public int getSite_area() {
        return site_area;
    }

    public void setSite_area(int site_area) {
        this.site_area = site_area;
    }


    //display

    @Override
    public void siteDisplay() {
        super.siteDisplay();
        System.out.println("\t\t Manager store name: " + manager_name);
        System.out.println("\t\t Area: " + site_area);
    }
}
