package Business.controllers;

// singleton
public class underway_transport_controller {
    private static underway_transport_controller instance;
    private static Logistical_center_controller logistical_center_controller;

    public static underway_transport_controller getInstance() {
        if (instance == null) {
            instance = new underway_transport_controller();
        }
        return instance;
    }

    private underway_transport_controller() {
        logistical_center_controller = Logistical_center_controller.getInstance();
    }

    public void add_site_document_to_driver(String driver_id, String site_document_id) {

    }

}
