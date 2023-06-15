package InventoryModule.Business;

import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.Controllers.ReportController;

import java.util.List;
//this class represents the report by its category.
public class ByCategoryReport extends Report{

    private String Reporter;
    List<String> Categories;
    //constructor -  creates a report by category according to the user chosen categories
    //the constructor will print the report
    public ByCategoryReport(String reporter, List<String> Categories){
        super(reporter); //inherit from abstract report class
        this.Reporter = reporter;
        this.Categories = Categories;
    }

    @Override
    public String toString()
    {
        String details = "";
        details = details + "***** By Category Products Report *****" + "\n";
        details = details + "Report Date: "+ this.getDate().toLocalDate() + "\n";
        details += "Reporter : "+ Reporter;
        for(int j=0; j<Categories.size();j++) { //for every category in the store, so the report will be
            //by category
            details +="Category Name: " + Categories.get(j) + "\n"; //print category name
            for (int i = 0; i < ProductController.getProducts().size(); i++) { //for every product in the store
                //if the product belongs to the current category
                if (ProductController.getProducts().get(i).getCategory().compareTo(Categories.get(j)) == 0) {
                    //print the details of this product
                    String temp="Product's Barcode: " + ProductController.getProducts().get(i).getBarcode() +
                            " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                            " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size()+ "\n";
                    details+=temp;
                }
            }
        }
        ReportController.addReport(this); //add the report to report controller to save its details
        return details;
    }
//this function returns the report type, used to print all reports' information
    @Override
    public String getReportType() {
        return "Product By Category Report";
    }
}
