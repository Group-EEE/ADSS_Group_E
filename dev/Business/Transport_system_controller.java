package Business;

public class Transport_system_controller {
    private Transport_System transport_system;
    private static Logistical_Center logistical_center;
    public Transport_system_controller(){
        transport_system = new Transport_System();
    }

    private void create_Logistical_Center(String address, String phone, String name, String site_contact_name){
        if (logistical_center == null){
            logistical_center = new Logistical_Center(address, phone, name, site_contact_name);
        }
    }

    public static Logistical_Center getLogistical_center() {
        return logistical_center;
    }
}
