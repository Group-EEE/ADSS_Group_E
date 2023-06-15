package InventoryModule.Business;//this class represents the report of supply in the store

import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.Controllers.ReportController;

public class CurrSupplyReport extends Report{

    String reporter;
    //constructor -  creates a report according to the supply in the store
    //the constructor will print the report
    public CurrSupplyReport(String reporter) {
        super(reporter); //inherit from abstract report class
    }

    @Override
    public String toString(){
        String details = "";
        details = details + "***** Products Supply Report *****" + "\n";
        details = details + "Report Date: "+ this.getDate().toLocalDate() + "\n";
        details += "Reporter : "+ reporter;
        for(int i = 0; i< ProductController.getProducts().size(); i++){ //for every product in the store
            details+="Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() + "\n";
            details+=   " Product's Name: " + ProductController.getProducts().get(i).getPName() + "\n";
            details+= " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size()+ "\n";
        }
        ReportController.addReport(this);//add the report to report controller to save its details
        return details;
    }
    //this func
    // tion returns the report type, used to print all reports' information
    @Override
    public String getReportType() {
        return "Products Supply Report";
    }
}
