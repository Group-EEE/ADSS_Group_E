public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
    Transport_System system = new Transport_System();
    Truck truck1 = new Truck("ABC123", "Volvo VNL", 15000.0, 25000.0, cold_level.Freeze, 15000.0);
    Truck truck2 = new Truck("DEF456", "Freightliner Cascadia", 18000.0, 30000.0, cold_level.Cold, 18000.0);
    Truck truck3 = new Truck("GHI789", "Kenworth T680", 17000.0, 28000.0, cold_level.Cold, 17000.0);
    Truck truck4 = new Truck("JKL012", "Peterbilt 579", 16000.0, 27000.0, cold_level.Dry, 16000.0);
    Truck truck5 = new Truck("MNO345", "International LT", 17500.0, 29000.0, cold_level.Freeze, 17500.0);
    Truck truck6 = new Truck("PQR678", "Mack Anthem", 16500.0, 27500.0, cold_level.Cold, 16500.0);
    Truck truck7 = new Truck("STU901", "Western Star 5700XE", 19000.0, 31000.0, cold_level.Dry, 19000.0);
    Truck truck8 = new Truck("VWX234", "Kenworth W900", 15500.0, 26000.0, cold_level.Cold, 15500.0);
    Truck truck9 = new Truck("YZA567", "Volvo VNR", 18500.0, 30500.0, cold_level.Freeze, 18500.0);
    system.add_truck(truck1);
    system.add_truck(truck2);
    system.add_truck(truck3);
    system.add_truck(truck4);
    system.add_truck(truck5);
    system.add_truck(truck6);
    system.add_truck(truck7);
    system.add_truck(truck8);
    system.add_truck(truck9);
    Truck_Driver driver1 = new Truck_Driver(912345678, "Alice Johnson", 12345, cold_level.Freeze, 30000.0);
    Truck_Driver driver2 = new Truck_Driver(865432109, "Bob Smith", 23456, cold_level.Cold, 35000.0);
    Truck_Driver driver3 = new Truck_Driver(324567890, "Charlie Brown", 34567, cold_level.Dry, 40000.0);
    Truck_Driver driver4 = new Truck_Driver(678901234, "Dave Davis", 45678, cold_level.Freeze, 39000.0);
    Truck_Driver driver5 = new Truck_Driver(456789012, "Ella Green", 56789, cold_level.Cold, 27800.0);
    Truck_Driver driver6 = new Truck_Driver(789012345, "Frank Lee", 67890, cold_level.Dry, 60000.0);
    Truck_Driver driver7 = new Truck_Driver(123456789, "Gina Martinez", 78901, cold_level.Freeze, 52000.0);
    Truck_Driver driver8 = new Truck_Driver(901234567, "Harry Thompson", 89012, cold_level.Cold, 22500.0);
    Truck_Driver driver9 = new Truck_Driver(345678901, "Isabel Ramirez", 90123, cold_level.Dry, 32000.0);
    Truck_Driver driver10 = new Truck_Driver(765432109, "Jack Brown", 12345, cold_level.Freeze, 16000.0);
    Truck_Driver driver11 = new Truck_Driver(321098765, "Kate Davis", 23456, cold_level.Cold, 65000.0);
    Truck_Driver driver12 = new Truck_Driver(890123456, "Larry Green", 34567, cold_level.Dry, 29000.0);
    Truck_Driver driver13 = new Truck_Driver(543210987, "Maggie Lee", 45678, cold_level.Freeze, 26500.0);
    Truck_Driver driver14 = new Truck_Driver(210987654, "Nate Martinez", 56789, cold_level.Cold, 38500.0);
    Truck_Driver driver15 = new Truck_Driver(678905432, "Olivia Thompson", 67890, cold_level.Dry, 56500.0);
    system.add_driver(driver1);
    system.add_driver(driver2);
    system.add_driver(driver3);
    system.add_driver(driver4);
    system.add_driver(driver5);
    system.add_driver(driver6);
    system.add_driver(driver7);
    system.add_driver(driver8);
    system.add_driver(driver9);
    system.add_driver(driver10);
    system.add_driver(driver11);
    system.add_driver(driver12);
    system.add_driver(driver13);
    system.add_driver(driver14);
    system.add_driver(driver15);

    system.start_transport();

//    system.display_transport_doc();
//    system.display_drivers();
//    system.display_trucks();
    system.display_site_supply();
    }
}