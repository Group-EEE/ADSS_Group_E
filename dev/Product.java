import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int sp_counter = 0;
    private int Barcode;
    private String PName;
    private String Supplier;
    private double Supplier_Price;
    private double Costumer_Price;
    private int Shelf_amount;
    private int Warehouse_amount;
    private String Category;
    private String SubCategory;
    private String SubSubCategory;
    private int Supply_Days;
    private String Manufacturer;
    private int Minimum_Amount;

    //Product Associations
    private List<SpecificProduct> specificProducts;
    private List<SpecificProduct> defectedProducts;
    private List<OrderReport> orderReports;
    private List<CurrSupplyReport> currSupplyReports;
    private List<ByCategoryReport> byCategoryReports;
    private List<ExpOrDefectReport> expOrDefectReports;

    public Product(int Bd, String Pn, String Sp, double s_price, double c_price, int sa, int wa, String cat, String scat, String sscat, int sd, String man, int min){
        this.Barcode = Bd;
        this.PName = Pn;
        this.Supplier = Sp;
        this.Supplier_Price = s_price;
        this.Costumer_Price = c_price;
        this.Shelf_amount = sa;
        this.Warehouse_amount = wa;
        this.Category = cat;
        this.SubCategory = scat;
        this.SubSubCategory = sscat;
        this.Supply_Days = sd;
        this.Manufacturer = man;
        this.Minimum_Amount = min;
        specificProducts = new ArrayList<SpecificProduct>();
        defectedProducts = new ArrayList<SpecificProduct>();
        orderReports = new ArrayList<OrderReport>();
        currSupplyReports = new ArrayList<CurrSupplyReport>();
        byCategoryReports = new ArrayList<ByCategoryReport>();
        expOrDefectReports = new ArrayList<ExpOrDefectReport>();
    }

    public int getBarcode() {
        return Barcode;
    }

    public void setBarcode(int barcode) {
        Barcode = barcode;
    }

    public double getSupplier_Price() {
        return Supplier_Price;
    }

    public void setSupplier_Price(double supplier_Price) {
        Supplier_Price = supplier_Price;
    }

    public double getCostumer_Price() {
        return Costumer_Price;
    }

    public void setCostumer_Price(double costumer_Price) {
        Costumer_Price = costumer_Price;
    }

    public int getShelf_amount() {
        return Shelf_amount;
    }

    public void setShelf_amount(int shelf_amount) {
        Shelf_amount = shelf_amount;
    }

    public int getWarehouse_amount() {
        return Warehouse_amount;
    }

    public void setWarehouse_amount(int warehouse_amount) {
        Warehouse_amount = warehouse_amount;
    }

    public int getSupply_Days() {
        return Supply_Days;
    }

    public void setSupply_Days(int supply_Days) {
        Supply_Days = supply_Days;
    }

    public String getPName() {
        return PName;
    }

    public int getMinimum_Amount() {
        return Minimum_Amount;
    }

    public List<SpecificProduct> getSpecificProducts() {
        return specificProducts;
    }

    public void setSpecificProducts(List<SpecificProduct> specificProducts) {
        this.specificProducts = specificProducts;
    }


    public void addSpecificProduct(int pID, LocalDateTime aExp_date, boolean aDefective, String aDefect_report_by, boolean aInWarehouse, String aStoreBranch, int aLocationInStore, Discount aDiscount)
    {
        if(this.Barcode == pID){
            this.sp_counter++;
            SpecificProduct SP = new SpecificProduct(this.sp_counter, pID, aExp_date, aDefective, aDefect_report_by, aInWarehouse, aStoreBranch, aLocationInStore, aDiscount);
            specificProducts.add(SP);
        }
        else{
            System.out.println("Product doesn't exist in store!");
        }
    }

    public void removeSpecificProduct(int sp_id)
    {
        for(int i=0; i<specificProducts.size();i++){
            if(specificProducts.get(i).getSp_ID() == sp_id){
                specificProducts.remove(i);
                break;
            }
        }
    }

    public void add_defected_specific_product(int sp_id, String defected_reporter, String Dtype) {
        for (int i = 0; i < specificProducts.size(); i++) {
            if (specificProducts.get(i).getSp_ID() == sp_id) {
                specificProducts.get(i).setDefective(true, Dtype);
                specificProducts.get(i).setDefect_Report_By(defected_reporter);
                defectedProducts.add(specificProducts.remove(i));
            }
        }
    }

    public SpecificProduct getSpecificProduct(int sp_id)
    {
        for(int i=0; i<specificProducts.size();i++){
            if(specificProducts.get(i).getSp_ID() == sp_id){
                return specificProducts.get(i);
            }
        }
        return null;
    }

    public String getProductLocationInStore(int sp_id){
        for(int i=0; i<specificProducts.size();i++) {
            if (specificProducts.get(i).getSp_ID() == sp_id) {
                if (specificProducts.get(i).getLocation_in_Store() == -1) {
                    return "Product is stored in warehouse";
                } else {

                    return Integer.toString(specificProducts.get(i).getLocation_in_Store());
                }
            }
        }
        return null;
    }

    public String getCategory() {
        return Category;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public String getSubSubCategory() {
        return SubSubCategory;
    }

    public String getSupplier() {
        return Supplier;
    }

    public String getManufacturer() {
        return Manufacturer;
    }
}
