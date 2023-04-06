import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//this class saves all the information about the products in the store
public class ProductController {
    private static List<Product> products; //list that saves akk the products in the store

    public ProductController() { //constructor
        products = new ArrayList<Product>();
    }

    //function that add product to the store- to the controller
    public void addProduct(int Bd, String Pn, String Sp, double s_price, double c_price, String cat, String scat, String sscat, int sd, String man, int min){
       //if the category of the new product doesnt exist yet, we will create it
        if(!CategoryController.check_if_exist_cat(cat)){
            CategoryController.addCategory(cat);
        }
        //create the new product - call to its constructor
        Product P = new Product(Bd,Pn,Sp,s_price,c_price,cat,scat,sscat,sd,man, min);
        products.add(P); //add to the controller's list
    }


    //this function removes specific product from shelf in store to the warehouse
    //and from warehouse to the store
    public void change_Shelf_Warehouse(int sp_id, int p_id) {
        boolean found = false; //if true- the product was found
        //check if the product exist in the controller
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getBarcode() == p_id) {
                found = true;
                //if the product is in warehouse
                if (products.get(i).getSpecificProduct(sp_id).isInWarehouse() == true) {
                    //change the field to false - not in the warehouse
                    products.get(i).getSpecificProduct(sp_id).setInWarehouse(false);
                    //get a random number for the shelf number
                    Random r = new Random();
                    int rand = r.nextInt(200);
                    //change the field to the new shelf number
                    products.get(i).getSpecificProduct(sp_id).setLocation_in_Store(rand);
                    //change the number of spec. product that on the shelf for this main product
                    products.get(i).setWarehouse_amount(products.get(i).getWarehouse_amount()-1);
                    //change the number of spec. product that on the shelf for this main product
                    products.get(i).setShelf_amount(products.get(i).getShelf_amount()+1);
                    System.out.println("Product transfer to store in shelf number " + rand);
                } else {  //if the product is in store
                    ////change the field to true - in the warehouse
                    products.get(i).getSpecificProduct(sp_id).setInWarehouse(true);
                    //change the location in store to -1 - represent warehouse
                    products.get(i).getSpecificProduct(sp_id).setLocation_in_Store(-1);
                    //change the number of spec. product that on the shelf for this main product
                    products.get(i).setShelf_amount( products.get(i).getShelf_amount()-1);
                    //change the number of spec. product that on the shelf for this main product
                    products.get(i).setWarehouse_amount( products.get(i).getWarehouse_amount()+1);
                    System.out.println("Product transfer to warehouse!");
                }
            }
        }
        if(found==false){
            System.out.println("Product wasn't found!");
        }
    }

    public void GetAllProductBarcode(){ //print all the barcodes that exist in the store
        System.out.println("******Store Products Barcodes******");
        for(int i=0; i<products.size(); i++){
            System.out.println(products.get(i).getPName() + ": " +products.get(i).getBarcode());
        }
    }

    //function that return the list of all products
    public static List<Product> getProducts() {
        return products;
    }

    //function that get barcode and return a product
    public Product getProductByBarcode(int BID){
        for(int i=0; i<products.size(); i++){ //search the given barcode in the controller's list
            if(products.get(i).getBarcode()==BID){
                return products.get(i);
            }
        }
        return null;
    }


}
