

public class License {
    private int L_ID;
    private cold_level cold_level;
    private double weight;

    public License(int ID, cold_level level, double truck_weight){
        this.L_ID = ID;
        this.cold_level = level;
        this.weight = truck_weight;
    }

    public void setL_ID(int l_ID) {
        L_ID = l_ID;
    }

    public int getL_ID() {
        return L_ID;
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
        System.out.println("\t\t License ID Number: " + L_ID);
        System.out.println("\t\t Truck Cold Level: " + cold_level.name());
        System.out.println("\t\t Weight: " + weight);
    }
}
