import java.util.List;

//this class represents the report of all products that needs to be ordered in the store

public class OrderReport extends Report{

    //constructor -  creates a report of all products that needs to be ordered in the store

    public OrderReport(String reporter){
        super(reporter);
        System.out.println("***** Products Order Report *****");
        System.out.println("Reporter : "+ reporter);
        for(int i=0; i<ProductController.getProducts().size();i++){
            if(ProductController.getProducts().get(i).getSpecificProducts().size() <= ProductController.getProducts().get(i).getMinimum_Amount()){
                System.out.println("Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() +
                        " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                        " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size() +
                        " Product's Manufacturer: " + ProductController.getProducts().get(i).getManufacturer());
            }
        }
        ReportController.addReport(this);
    }

    //this function returns the report type

    @Override
    public String getReportType() {
        return "Order Report";
    }

}
