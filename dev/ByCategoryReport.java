import java.util.List;

public class ByCategoryReport extends Report{
    private String Category;
    private String SubCategory;
    private String SubSubCategory;

    public ByCategoryReport(String reporter, List<String> Categories){
        super(reporter);
        System.out.println("***** By Category Products Report *****");
        System.out.println("Reporter : "+ reporter);
        for(int j=0; j<Categories.size();j++){
            System.out.println("Category Name: " + Categories.get(j));
            for(int i=0; i<ProductController.getProducts().size();i++){
                if(ProductController.getProducts().get(i).getCategory().compareTo(Categories.get(j))==0){
                    System.out.println("Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() +
                            " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                            " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size());
                }
            }

        }
        ReportController.addReport(this);
    }

    @Override
    public String getReportType() {
        return "Product By Category Report";
    }
}
