package InventoryModule.Business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//this class represent a general product
//for example pasta that supplied by Barilla, its barcode is 12345 etc.
public class SuperLiProduct {
    private int sp_counter = 0; //count how many specific products we have of this product
    private int Barcode; //the id of the product
    private String PName; //the name of the product
    private double Costumer_Price; //the price that we declare to the costumers
    private int Shelf_amount; //how many specific products there are on the shelves in the store
    private int Warehouse_amount; //how many specific products there are in the warehouse
    private Category Category; //the main category that the product is categorised by
    private String SubCategory; //the main subcategory that the product is categorised by
    private String SubSubCategory; //the subsubcategory that the product is categorised by
    private int Supply_Days; //how much time it takes to the supplier to supply the product
    private String Manufacturer; //the name of the manufacturer
    private int Minimum_Amount; //if we have this amount or less we have to order the product

    //Product Associations
    private List<SpecificProduct> specificProducts; //list to save all the specific products of this product
    private List<SpecificProduct> defectedProducts; //list to save all the defected specific products of this product

   //constructor
    public SuperLiProduct(int barcode, String name, double c_price, String cat, String scat, String sscat, int sd, String man, int min){
        this.Barcode = barcode;
        this.PName = name;
        this.Costumer_Price = c_price;
        this.Shelf_amount = 0;
        this.Warehouse_amount = 0;
        Category c = new Category(cat);
        this.Category = c;
        this.SubCategory = scat;
        this.SubSubCategory = sscat;
        this.Supply_Days = sd;
        this.Manufacturer = man;
        this.Minimum_Amount = min;
        specificProducts = new ArrayList<SpecificProduct>();
        defectedProducts = new ArrayList<SpecificProduct>();
    }

    //function that returns the barcode(id) of the main product
    public int getBarcode() {
        return Barcode;
    }

    //function that returns the price that the costumers pay
    public double getCostumer_Price() {
        return Costumer_Price;
    }

    //function to change the price that the costumers pay
    public void setCostumer_Price(double costumer_Price) {
        Costumer_Price = costumer_Price;
    }

    //function that returns how many specific products of this product there are in the store's shelves
    public int getShelf_amount() {
        return Shelf_amount;
    }

    public void setShelf_amount(int shelf_amount) {
        Shelf_amount = shelf_amount;
    }

    //function that returns how many specific products of this product there are in the warehouse
    public int getWarehouse_amount() {
        return Warehouse_amount;
    }

    public void setWarehouse_amount(int warehouse_amount) {
        Warehouse_amount = warehouse_amount;
    }

    //function that returns how many days it takes to the supplier to supply the product
    public int getSupply_Days() {
        return Supply_Days;
    }

    //function that returns the name of the product
    public String getPName() {
        return PName;
    }

    //function that return the amount that we need to order from
    public int getMinimum_Amount() {
        return Minimum_Amount;
    }

    //function that return the list of specific products
    public List<SpecificProduct> getSpecificProducts() {
        return specificProducts;
    }

    //function that return the list of defected/exp products
    public List<SpecificProduct> getDefectedProducts() {
        return defectedProducts;
    }

    //function that create specific product of this general product
    public void addSpecificProduct(String supplier,double supplierPrice, int pID, LocalDateTime aExp_date, boolean aDefective, String aDefect_report_by, boolean aInWarehouse, String aStoreBranch, int aLocationInStore, Discount aDiscount, String adefectedtype)
    {
        if(this.Barcode == pID){ //check if the specific product is from this product
            this.sp_counter++; //gives the specific product its id
            //call the constructor of the specific product
            SpecificProduct SP = new SpecificProduct(supplierPrice,supplier, this.sp_counter,pID, aExp_date, aDefective, aDefect_report_by, aInWarehouse, aStoreBranch, aLocationInStore, aDiscount, adefectedtype);
            if(SP.isDefective()==false){
                specificProducts.add(SP); //add the specific product to the list
            }
            else{
                defectedProducts.add(SP);
            }
            if(SP.isInWarehouse()==true){
                this.Warehouse_amount++;
            }
            else{
                this.Shelf_amount++;
            }
        }
    }

    //remove a specific product from the list
    public void removeSpecificProduct(int sp_id)
    {
        for(int i=0; i<specificProducts.size();i++){
            if(specificProducts.get(i).getSp_ID() == sp_id){ //looking for the specific product by its id
                if(specificProducts.get(i).isInWarehouse()==true){
                    this.Warehouse_amount--;
                }
                else{
                    this.Shelf_amount--;
                }
                specificProducts.remove(i);
                break;
            }
        }

    }

    //function to add defective product
    public void add_defected_specific_product(int sp_id, String defected_reporter, String Dtype) {
        for (int i = 0; i < specificProducts.size(); i++) {
            if (specificProducts.get(i).getSp_ID() == sp_id) { //find the spec.product by its id
                specificProducts.get(i).setDefective(true, Dtype); //change the field
                specificProducts.get(i).setDefect_Report_By(defected_reporter); //change the field
                defectedProducts.add(specificProducts.remove(i));//remove it from the list
            }
        }
    }

    //function that return specific product when given id
    public SpecificProduct getSpecificProduct(int sp_id)
    {
        for(int i=0; i<specificProducts.size();i++){
            if(specificProducts.get(i).getSp_ID() == sp_id){ //find the spec. product by its id
                return specificProducts.get(i); //return the product
            }
        }
        return null;
    }

    //function that return the shelf number if in store or -1 if in warehouse
    public String getProductLocationInStore(int sp_id){
        String s = null;
        for(int i=0; i<specificProducts.size();i++) {
            if (specificProducts.get(i).getSp_ID() == sp_id) {
                if (specificProducts.get(i).getLocation_in_Store() == -1) {
                    s ="Product is stored in warehouse";
                } else {
                    s = "Product is stored in shelf number "+ Integer.toString(specificProducts.get(i).getLocation_in_Store());
                }
            }
        }
        return s;
    }

    //return the product's category
    public String getCategory() {
        return Category.getName();
    }

    //return the product's subcategory
    public String getSubCategory() {
        return SubCategory;
    }

    //return the product's subsubcategory
    public String getSubSubCategory() {
        return SubSubCategory;
    }

    //return the product's manufacturer
    public String getManufacturer() {
        return Manufacturer;
    }

    //this setter set the List of specificProducts for each SuperLiProduct when reading from the DB
    public void setSpecificProducts(List<SpecificProduct> specificProducts) {
        this.specificProducts = specificProducts;
    }

    public void setSp_counter(int sp_counter) {
        this.sp_counter = sp_counter;
    }

    public int getSp_counter() {
        return sp_counter;
    }
}
