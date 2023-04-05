import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductController {
    private static List<Product> products;
    //private static int counter=0;


    public ProductController() {
        products = new ArrayList<Product>();
    }

    public void addProduct(int Bd, String Pn, String Sp, double s_price, double c_price, String cat, String scat, String sscat, int sd, String man, int min){
        //counter++;
        if(!CategoryController.check_if_exist_cat(cat)){
            CategoryController.addCategory(cat);
        }
        Product P = new Product(Bd,Pn,Sp,s_price,c_price,cat,scat,sscat,sd,man, min);
        products.add(P);
    }

    public void update_defected_product(int sp_id, int p_id, String defected_reporter, String Dtype){
        for(int i=0; i< products.size();i++){
            if(products.get(i).getBarcode()==p_id){
                products.get(i).add_defected_specific_product(sp_id, defected_reporter, Dtype);
            }
        }
    }

    public int get_specific_product_location(int sp_id, int p_id) {
        for (int i = 0; i < products.size(); i++)
            if (products.get(i).getBarcode() == p_id)
                return products.get(i).getSpecificProduct(sp_id).getLocation_in_Store();
        return -1;
    }

    public void change_Shelf_Warehouse(int sp_id, int p_id) {
        boolean found = false;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getBarcode() == p_id) {
                found = true;
                if (products.get(i).getSpecificProduct(sp_id).isInWarehouse() == true) {
                    products.get(i).getSpecificProduct(sp_id).setInWarehouse(false);
                    Random r = new Random();
                    int rand = r.nextInt(200);
                    products.get(i).getSpecificProduct(sp_id).setLocation_in_Store(rand);
                    products.get(i).setWarehouse_amount(products.get(i).getWarehouse_amount()-1);
                    products.get(i).setShelf_amount(products.get(i).getShelf_amount()+1);
                    System.out.println("Product transfer to store in shelf number " + rand);
                } else {
                    products.get(i).getSpecificProduct(sp_id).setInWarehouse(true);
                    products.get(i).getSpecificProduct(sp_id).setLocation_in_Store(-1);
                    products.get(i).setShelf_amount( products.get(i).getShelf_amount()-1);
                    products.get(i).setWarehouse_amount( products.get(i).getWarehouse_amount()+1);
                    System.out.println("Product transfer to warehouse!");
                }
            }
        }
        if(found==false){
            System.out.println("Product wasn't found!");
        }
    }

    public void GetAllProductBarcode(){
        System.out.println("******Store Products Barcodes******");
        for(int i=0; i<products.size(); i++){
            System.out.println(products.get(i).getPName() + ": " +products.get(i).getBarcode());
        }
    }

    public static List<Product> getProducts() {
        return products;
    }

    public Product getProductByBarcode(int BID){
        for(int i=0; i<products.size(); i++){
            if(products.get(i).getBarcode()==BID){
                return products.get(i);
            }
        }
        return null;
    }


}
