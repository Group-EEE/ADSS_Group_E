import java.util.HashMap;
import java.util.Map;

public class Site_Supply {
    private int id;
    private Map<String, Integer> items;
    private Store store;
    private String origin;

    private double products_total_weight = 0.0;

    public Site_Supply(int d_id, Store store_site, String supplier){
        origin = supplier;
        id = d_id;
        store = store_site;
        items = new HashMap<String, Integer>();
    }

    public String getOrigin() {
        return origin;
    }

    public int getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void insert_item(String item, int amount) {
        if (items.containsKey(item)){
            items.put(item, items.get(item) + amount);
        }
        else {
            items.put(item, amount);
        }
    }

    public boolean equals(Site_Supply site_doc) {
        return (site_doc.getId() == this.id);
    }

    public boolean equals(int doc_id) {
        return (doc_id == this.id);
    }


    // display
    public void sDisplay(){
        System.out.println("\t =========== Site Supply - " + id + " - information ===========");
        System.out.println("\t Store name: " + store.getSite_n());
        System.out.println("\t Origin: " + origin);
        System.out.println("\t ======= Items =======");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.print("\t\t");
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println();

    }

    public void setProducts_total_weight(double weight){
        this.products_total_weight = weight;
    }
}
