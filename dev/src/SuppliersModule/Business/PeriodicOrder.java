package SuppliersModule.Business;

public class PeriodicOrder {
    private final OrderFromSupplier orderFromSupplier;
    private int dayForInvite; // The day in the week that the supplier gets the order from the store
    private int id;

    public PeriodicOrder(OrderFromSupplier orderFromSupplier, int dayForInvite) {
        this.orderFromSupplier = orderFromSupplier;
        this.dayForInvite = dayForInvite;
        this.id = orderFromSupplier.getId();
        orderFromSupplier.getMySupplier().addPeriodicOrder(this);
    }

    public void invite(){
        OrderFromSupplier orderFromSupplierCopy = orderFromSupplier.clone();
        orderFromSupplierCopy.invite();
    }

    public OrderFromSupplier getOrderFromSupplier() {
        return orderFromSupplier;
    }

    public int getDayForInvite() {
        return dayForInvite;
    }

    public void setDayForInvite(int dayForInvite) {
        this.dayForInvite = dayForInvite;
    }

    public int getId() {
        return id;
    }

    public void delete(){
        orderFromSupplier.getMySupplier().deletePeriodicOrder(id);
    }

    public void setId(int id) {
        this.id = id;
    }


}
