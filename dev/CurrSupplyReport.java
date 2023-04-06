//this class represents the report of supply in the store

public class CurrSupplyReport extends Report{

    //constructor -  creates a report according to the supply in the store
    //the constructor will print the report
    public CurrSupplyReport(String reporter) {
        super(reporter); //inherit from abstract report class
        System.out.println("***** Products Supply Report *****");
        System.out.println("Reporter : "+ reporter);
        for(int i=0; i<ProductController.getProducts().size();i++){ //for every product in the store
            System.out.println("Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() +
                    " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                    " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size());
        }
        ReportController.addReport(this);//add the report to report controller to save its details
    }

    //this function returns the report type, used to print all reports' information
    @Override
    public String getReportType() {
        return "Products Supply Report";
    }
}
