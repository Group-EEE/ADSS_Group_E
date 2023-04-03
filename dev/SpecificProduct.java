import java.time.LocalDateTime;

public class SpecificProduct {
    private int Sp_ID;
    private int P_ID;
    private LocalDateTime ExpDate;
    private boolean Defective;
    private String Defect_Report_By;
    private boolean InWarehouse;
    private String Store_Branch;
    private int Location_in_Store;
    private Discount Discount;
    private String DefectType;
    public SpecificProduct(int aID, int pID, LocalDateTime aExp_date, boolean aDefective, String aDefect_report_by, boolean aInWarehouse, String aStoreBranch, int aLocationInStore, Discount aDiscount) {
        this.Sp_ID = aID;
        this.P_ID = pID;
        this.ExpDate = aExp_date;
        this.Defective = aDefective;
        this.Defect_Report_By = aDefect_report_by;
        this.InWarehouse = aInWarehouse;
        this.Location_in_Store = aLocationInStore;
        this.Discount = aDiscount;
        this.DefectType = null;
    }

    public boolean isDefective() {
        return Defective;
    }

    public void setDefective(boolean defective ,String dType) {

        Defective = defective;
        this.setDefectType(dType);

    }

    public String getDefect_Report_By() {
        return Defect_Report_By;
    }

    public void setDefect_Report_By(String defect_Report_By) {
        Defect_Report_By = defect_Report_By;
    }

    public boolean isInWarehouse() {
        return InWarehouse;
    }

    public void setInWarehouse(boolean inWarehouse) {
        InWarehouse = inWarehouse;
    }

    public int getLocation_in_Store() {
        return Location_in_Store;
    }

    public void setLocation_in_Store(int location_in_Store) {
        Location_in_Store = location_in_Store;
    }

    public Discount getDiscount() {
        return Discount;
    }

    public void setDiscount(Discount discount) {
        Discount = discount;
    }

    public int getSp_ID() {
        return Sp_ID;
    }

    public void setSp_ID(int sp_ID) {
        Sp_ID = sp_ID;
    }

    public LocalDateTime getExpDate() {
        return ExpDate;
    }

    public String getDefectType() {
        return DefectType;
    }

    public void setDefectType(String defectType) {
        DefectType = defectType;
    }
}
