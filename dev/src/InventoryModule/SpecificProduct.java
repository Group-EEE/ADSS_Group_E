package InventoryModule;

import SuppliersModule.Business.SupplierProduct;

import java.time.LocalDateTime;

//this class represent a specific product - a pack of pasta that you can hold in your hand.
//each "pack" is a specific product with id
public class SpecificProduct {
    private int Sp_ID; //specific product id
    private int P_ID; //the main product's id
    private LocalDateTime ExpDate; //the expiration date of the spec. product
    private boolean Defective; //if the product is defective will be true
    private String Defect_Report_By; //if not defective will be null
    private boolean InWarehouse; //if the spec. product on shelf in store will be false
    private String Store_Branch; //the branch
    private int Location_in_Store;//if in warehouse will be -1, else the number of shelf
    private Discount Discount; //the discount on this specific product
    private String DefectType;//if not defected will be null
    private String Supplier; //the supplier
    private double Supplier_Price; //the price that supplier declares from us

    //constructor
    public SpecificProduct(double supplier_Price,String supplier, int aID, int pID, LocalDateTime aExp_date, boolean aDefective, String aDefect_report_by, boolean aInWarehouse, String aStoreBranch, int aLocationInStore, Discount aDiscount, String defectType) {
        this.Sp_ID = aID;
        this.P_ID = pID;
        this.ExpDate = aExp_date;
        this.Defective = aDefective;
        this.Defect_Report_By = aDefect_report_by;
        this.InWarehouse = aInWarehouse;
        this.Location_in_Store = aLocationInStore;
        this.Discount = aDiscount;
        this.DefectType = defectType;
        this.Store_Branch = aStoreBranch;
        this.Supplier_Price =supplier_Price;
        this.Supplier =supplier;
    }

    //function return true if defective
    public boolean isDefective() {
        return Defective;
    }

    //change the product ti defective if needed
    public void setDefective(boolean defective ,String dType) {

        this.Defective = defective;
        this.setDefectType(dType);

    }

    //return who report the defect
    public String getDefect_Report_By() {
        return Defect_Report_By;
    }

    //change the reporter. mostly from null
    public void setDefect_Report_By(String defect_Report_By) {
        Defect_Report_By = defect_Report_By;
    }

    //return true if the spec. product in warehouse
    public boolean isInWarehouse() {
        return InWarehouse;
    }

    //put the spec. product in the warehouse
    public void setInWarehouse(boolean inWarehouse) {
        InWarehouse = inWarehouse;
    }

    //return the location in the store, or -1 if in warehouse
    public int getLocation_in_Store() {
        return Location_in_Store;
    }

    //function that change the location in store- shelf number or to -1 if un warehouse.
    public void setLocation_in_Store(int location_in_Store) {
        Location_in_Store = location_in_Store;
    }

    //return the discount on the specific product
    public Discount getDiscount() {
        return Discount;
    }

    //used to change the discount on the product
    public void setDiscount(Discount discount) {
        Discount = discount;
    }

    //return the spec. product id
    public int getSp_ID() {
        return Sp_ID;
    }

    //return the expiration date of the spec. product
    public LocalDateTime getExpDate() {
        return ExpDate;
    }

    //return the defect type as it was reported
    public String getDefectType() {
        return DefectType;
    }

    //change the defect type-mostly from null
    public void setDefectType(String defectType) {
        DefectType = defectType;
    }
}
