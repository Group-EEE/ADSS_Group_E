package InventoryModule.Business;

import InventoryModule.Business.Controllers.ProductController;
import InventoryModule.Business.Controllers.ReportController;
import SuppliersModule.Business.Generator.OrderGenerator;

import java.util.ArrayList;
import java.util.List;

import static SuppliersModule.Business.Generator.OrderGenerator.makeOrderFromOrderReport;

//this class represents the report of all products that needs to be ordered in the store
public class OrderReport extends Report{
    private List<Integer> barcodes;
    private List<Integer> amount;
    private String Reporter;
    //constructor -  creates a report of all products that needs to be ordered in the store
    //the constructor will print the report
    public OrderReport(String reporter){
        super(reporter); //inherit from abstract report class
        Reporter = reporter;
        barcodes = new ArrayList<>();
        amount = new ArrayList<>();
    }

    //this function returns the report type, used to print all reports' information

    @Override
    public String getReportType() {
        return "Order Report";
    }

    public List<Integer> getBarcodes() {
        return barcodes;
    }

    public List<Integer> getAmount() {
        return amount;
    }

    @Override
    public String toString()
    {
        String details = "";
        details = details + "***** Products Order Report *****" + "\n";
        details = details + "Report Date: "+ this.getDate().toLocalDate() + "\n";
        details += "Reporter : "+ Reporter;
        for(int i = 0; i< ProductController.getProducts().size(); i++){
            //check for all of its specific products if the amount of not defected products
            //(on shelf and in warehouse) is equal or less than the minimum amount
            if(ProductController.getProducts().get(i).getSpecificProducts().size() <= ProductController.getProducts().get(i).getMinimum_Amount()){
                details += "Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() + "\n";
                details += "Product's Name: " + ProductController.getProducts().get(i).getPName() + "\n";
                details += " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size() + "\n";
                details += " Product's Manufacturer: " + ProductController.getProducts().get(i).getManufacturer() + "\n";
                barcodes.add(ProductController.getProducts().get(i).getBarcode());
                amount.add((ProductController.getProducts().get(i).getMinimum_Amount()+20)-ProductController.getProducts().get(i).getSpecificProducts().size());
            }
        }
        ReportController.addReport(this); //add the report to report controller to save its details
        return details;
    }

}
