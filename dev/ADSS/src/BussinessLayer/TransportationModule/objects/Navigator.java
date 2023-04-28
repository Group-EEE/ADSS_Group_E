package BussinessLayer.TransportationModule.objects;

import java.util.ArrayList;
import java.util.Iterator;

public class Navigator {
    private ArrayList<Site> route;
    private Iterator<Site> iterator;
    private Site current_location = null;
    public Navigator(ArrayList<Site> destinations){
        route = destinations;
        iterator = destinations.iterator();
    }

    public void create_route(){
        ArrayList<Site> new_route = new ArrayList<>();
        for (Site site: route){
            if (site.is_supplier()){
                new_route.add(site);
            }
        }
        for (Site site: route){
            if (!site.is_supplier()){
                new_route.add(site);
            }
        }
        this.route = new_route;
        this.iterator = route.iterator();
    }

    public Site drive_to_next(){
        if (!iterator.hasNext()){
            this.current_location = null;
            return null;
        }
        Site next_site = iterator.next();
        route.remove(0);
        iterator = route.iterator();
        this.current_location = next_site;
        return next_site;
    }

    public ArrayList<Site> getRoute() {
        return route;
    }

    public void delete_site(String site){
        for (int i = 0; i < route.size(); i++) {
            if (route.get(i).getSite_name().equals(site)){
                route.remove(i);
            }
        }
        iterator = route.iterator();
    }

    public Site getCurrent_location() {
        return current_location;
    }

    public void add_site(Site site){
        route.add(site);
        iterator = route.iterator();
    }

    public void setCurrent_location(Site current_location) {
        this.current_location = current_location;
    }
}
