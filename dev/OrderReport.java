import java.util.List;

//this class represents the report of all products that needs to be ordered in the store
public class OrderReport extends Report{

    //constructor -  creates a report of all products that needs to be ordered in the store
    //the constructor will print the report
    public OrderReport(String reporter){
        super(reporter); //inherit from abstract report class
        System.out.println("***** Products Order Report *****");
        System.out.println("Reporter : "+ reporter);
        //for every product
        for(int i=0; i<ProductController.getProducts().size();i++){
            //check for all of its specific products if the amount of not defected products
            //(on shelf and in warehouse) is equal or less than the minimum amount
            if(ProductController.getProducts().get(i).getSpecificProducts().size() <= ProductController.getProducts().get(i).getMinimum_Amount()){
                System.out.println("Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() +
                        " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                        " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size() +
                        " Product's Manufacturer: " + ProductController.getProducts().get(i).getManufacturer());
            }
        }
        ReportController.addReport(this); //add the report to report controller to save its details
    }

    //this function returns the report type, used to print all reports' information

    @Override
    public String getReportType() {
        return "Order Report";
    }

}
