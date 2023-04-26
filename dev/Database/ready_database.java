package Database;

import Business.controllers.Logistical_center_controller;
import Business.objects.Logistical_Center;
import Business.objects.Truck;
import Business.objects.Truck_Driver;
import Business.objects.cold_level;

// singleton
public class ready_database {
    // singleton
    private static ready_database instance = null;
    private static ready_database getInstance() {
        if (instance == null) {
            instance = new ready_database();
        }
        return instance;
    }

    public void load_database() {
        Logistical_Center logistical_center = Logistical_center_controller.getInstance().getLogistical_center();
        logistical_center.add_driver(new Truck_Driver(912345678, "Gal", 12345, cold_level.Freeze, 1000));
        logistical_center.add_driver(new Truck_Driver(912345679, "Oded", 12346, cold_level.Cold, 2000));
        logistical_center.add_truck(new Truck("12345678", "Volvo VNL", 100, 1000, cold_level.Cold, 100));
        logistical_center.add_truck(new Truck("87654321", "Toyota", 100, 2000, cold_level.Cold, 100));
    }
}
