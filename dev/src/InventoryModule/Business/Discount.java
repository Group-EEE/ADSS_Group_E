package InventoryModule.Business;

import InventoryModule.Business.Controllers.ProductController;

import java.time.LocalDateTime;
//this class represent the discount that specific product can have
public class Discount {
    LocalDateTime Start; //the date that the discount starts
    LocalDateTime End; //the date that the discount ends
    double Discount; //the percents of discount

    //constructor
    public Discount(LocalDateTime s, LocalDateTime e, double d) {
        this.Start = s;
        this.End = e;
        this.Discount = d;
    }


    //this function update discount on spec. products that are categorized on given category from any supplier
    public static void update_discount_bycategory(String cat, LocalDateTime start44, LocalDateTime end44, double discount4){
        Discount dis4 = new Discount(start44, end44, discount4);
        for(int i = 0; i< ProductController.getProducts().size(); i++){ //check for every product in the store
            //if the product categorized in the given category
            if(ProductController.getProducts().get(i).getCategory().compareTo(cat)==0){
                //update the discount for every spec. product in the main product's list
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(dis4);
                }
            }
        }
    }

    //this function update discount on spec. products by given product
    public static void update_discount_byproduct(String Pname, LocalDateTime start55,LocalDateTime end55,double discount5){
        Discount dis5 = new Discount(start55, end55, discount5);
        for(int i=0; i<ProductController.getProducts().size();i++){//check for every product in the store
            //if it is the given product
            if(ProductController.getProducts().get(i).getPName().compareTo(Pname)==0){
                //update the discount for every spec. product in the main product's list
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(dis5);
                }
            }
        }
    }


    //this function update discount on spec. product
    //we use the name of the main product to find the specific one in its list
    public static void update_discount_byspecificproduct(String pname , int spid, LocalDateTime start66, LocalDateTime end66, double discount6){
        Discount dis6 = new Discount(start66, end66, discount6);
        for(int i=0; i<ProductController.getProducts().size();i++){ //check for every product in the store
            //if it is the given product name
            if(ProductController.getProducts().get(i).getPName().compareTo(pname)==0){
                //search in the product's list for the specific product by the given id
                for(int j=0; j<ProductController.getProducts().get(i).getSpecificProducts().size(); j++){
                    //update the discount if found
                    if(ProductController.getProducts().get(i).getSpecificProducts().get(j).getSp_ID()==spid){
                        ProductController.getProducts().get(i).getSpecificProducts().get(j).setDiscount(dis6);
                    }
                }
            }
        }
    }
}
