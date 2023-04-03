public class CurrSupplyReport extends Report{

    public CurrSupplyReport(String reporter) {
        super(reporter);
        System.out.println("***** Products Supply Report *****");
        System.out.println("Reporter : "+ reporter);
        for(int i=0; i<ProductController.getProducts().size();i++){
            System.out.println("Product's Barcode: " +ProductController.getProducts().get(i).getBarcode() +
                    " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                    " Product's Amount: " + ProductController.getProducts().get(i).getSpecificProducts().size());
        }
        ReportController.addReport(this);
    }

    @Override
    public String getReportType() {
        return "Products Supply Report";
    }
}
