import java.util.List;
//this class represents the report by its category.
public class ByCategoryReport extends Report{

    //constructor -  creates a report by category according to the user chosen categories
    //the constructor will print the report
    public ByCategoryReport(String reporter, List<String> Categories){
        super(reporter); //inherit from abstract report class
        System.out.println("***** By Category Products Report *****");
        System.out.println("Report Date: "+ this.getDate().toLocalDate());
        System.out.println("Reporter : "+ reporter);
        for(int j=0; j<Categories.size();j++){ //for every category in the store, so the report will be
            //by category
            System.out.println("Category Name: " + Categories.get(j)); //print category name
            for(int i=0; i<ProductController.getProducts().size();i++){ //for every product in the store
                //if the product belongs to the current category
                if(ProductController.getProducts().get(i).getCategory().compareTo(Categories.get(j))==0){
                  //print the details of this product
                    System.out.println("Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() +
                            " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                            " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size());
                }
            }

        }
        ReportController.addReport(this); //add the report to report controller to save its details
    }
//this function returns the report type, used to print all reports' information
    @Override
    public String getReportType() {
        return "Product By Category Report";
    }
}
