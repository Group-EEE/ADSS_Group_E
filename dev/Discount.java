import java.time.LocalDateTime;

public class Discount {
    LocalDateTime Start;
    LocalDateTime End;
    double Discount;

    public Discount(LocalDateTime s, LocalDateTime e, double d) {
        this.Start = s;
        this.End = e;
        this.Discount = d;
    }

    public void update_discount_bycategory_from_supplier(String cat, String supplier){
        for(int i=0; i<ProductController.getProducts().size();i++){
            if(ProductController.getProducts().get(i).getCategory().compareTo(cat)==0 && ProductController.getProducts().get(i).getSupplier().compareTo(supplier)==0){
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(this);
                }
            }
        }
    }

    public void update_discount_bycategory(String cat){
        for(int i=0; i<ProductController.getProducts().size();i++){
            if(ProductController.getProducts().get(i).getCategory().compareTo(cat)==0){
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(this);
                }
            }
        }
    }

    public void update_discount_byproduct_from_supplier(String Pname, String supplier){
        for(int i=0; i<ProductController.getProducts().size();i++){
            if(ProductController.getProducts().get(i).getPName().compareTo(Pname)==0 && ProductController.getProducts().get(i).getSupplier().compareTo(supplier)==0){
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(this);
                }
            }
        }
    }

    public void update_discount_byproduct(String Pname){
        for(int i=0; i<ProductController.getProducts().size();i++){
            if(ProductController.getProducts().get(i).getPName().compareTo(Pname)==0){
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(this);
                }
            }
        }
    }

    public void update_discount_byspecificproduct_from_supplier(String pname , int spid, String supplier){
        for(int i=0; i<ProductController.getProducts().size();i++){
            if(ProductController.getProducts().get(i).getPName().compareTo(pname)==0 && ProductController.getProducts().get(i).getSupplier().compareTo(supplier)==0){
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    if(ProductController.getProducts().get(i).getSpecificProducts().get(j).getSp_ID()==spid){
                        ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(this);
                    }
                }
            }
        }
    }

    public void update_discount_byspecificproduct(String pname , int spid){
        for(int i=0; i<ProductController.getProducts().size();i++){
            if(ProductController.getProducts().get(i).getPName().compareTo(pname)==0){
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    if(ProductController.getProducts().get(i).getSpecificProducts().get(j).getSp_ID()==spid){
                        ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(this);
                    }
                }
            }
        }
    }
}
