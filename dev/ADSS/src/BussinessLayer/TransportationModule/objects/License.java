package BussinessLayer.TransportationModule.objects;

public class License {
    private int _licenseID;
    private int _employeeID;
    private cold_level cold_level;
    private double weight;

    public License(int employeeID, int licenseID, cold_level level, double truck_weight){
        _employeeID = employeeID;
        this._licenseID = licenseID;
        this.cold_level = level;
        this.weight = truck_weight;
    }

    public void setL_ID(int l_ID) {
        _licenseID = l_ID;
    }

    public int getL_ID() {
        return _licenseID;
    }

    public void setCold_level(cold_level cold_level) {
        this.cold_level = cold_level;
    }

    public cold_level getCold_level() {
        return cold_level;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // display

    public void licenseDisplay(){
        System.out.println("\t\t Truck Cold Level: " + cold_level.name());
        System.out.println("\t\t Weight: " + weight);
    }
}
