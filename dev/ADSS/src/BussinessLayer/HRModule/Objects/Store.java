package BussinessLayer.HRModule.Objects;

import BussinessLayer.TransportationModule.objects.Site;

import java.util.ArrayList;
import java.util.List;

public class Store extends Site {
    private final String _name;
    private final int _area;

    public Store(String name, String address, String phone, String site_contact_name, int area){
        super(address, phone, name, site_contact_name);
        this._name = name;
        this._area = area;
    }

    /**
     * @return the name of the store
     */
    public String getName() {
        return _name;
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

    /**
     * @return the list of all cashier employees in the store
     */
    public String getAddress() {
        return address;
    }


    public String toString(){
       return "Store Name: " + this._name + ", Store Address: " + this.address+ ", Store Phone: " + this.phone + ", Store Contact Name: " + this.site_contact_name+ ", Store Area: " + this._area;
    }

    public int get_area() {
        return _area;
    }

    public void storeDisplay() {
        System.out.println("\t Store Name: " + site_name);
        System.out.println("\t Store Address: " + address);
        System.out.println("\t Store Phone Number: " + phone);
        System.out.println("\t Contact Name: " + site_contact_name);
        System.out.println("\t Store Area: " + _area);
        System.out.println();
    }
}
