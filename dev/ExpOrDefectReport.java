import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
//this class represents the report of all defected/ Exp product/ product that their Exp date
// is in less than 3 days that exist in the store

public class ExpOrDefectReport extends Report{
    private List<String> Supplier;
    private boolean Exp;
    private boolean Defective;

    //constructor -  creates a report according to all defected/ Exp product/ product that their Exp date
    //is in less than 3 days that exist in the store. The constructor will print the report
    public ExpOrDefectReport(String reporter){
        super(reporter);
        System.out.println("***** Exp/Defected Products Report *****");
        System.out.println("Reporter : "+ reporter);
        for (int i = 0; i < ProductController.getProducts().size(); i++) { //for every product in the store
            //for every specific product of this general product
            for (int j = 0; j < ProductController.getProducts().get(i).getSpecificProducts().size(); j++) {
              //check if expired, if it is,  move it to the defective list in the product controller
                if (ProductController.getProducts().get(i).getSpecificProducts().get(j).getExpDate().isBefore(LocalDateTime.now())) {
                    ProductController.getProducts().get(i).add_defected_specific_product(ProductController.getProducts().get(i).getSpecificProducts().get(j).getSp_ID(), reporter, "Expired");
                }
            }
            //for every defected specific product
            for (int a = 0; a < ProductController.getProducts().get(i).getDefectedProducts().size(); a++) {
                    System.out.println("Product's Barcode: " + ProductController.getProducts().get(i).getBarcode() +
                            " Product's ID: " + ProductController.getProducts().get(i).getDefectedProducts().get(a).getSp_ID() +
                            " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                            " Product's Category: " + ProductController.getProducts().get(i).getCategory() +
                            " Product's Defect Type: " + ProductController.getProducts().get(i).getDefectedProducts().get(a).getDefectType());
                }
            }

        System.out.println("***** Products About to be Exp in less than 3 days *****");
        for (int i = 0; i < ProductController.getProducts().size(); i++) { //for every product
            //check for every specific product if its exp. day is 3 days or less from today
            for (int j = 0; j < ProductController.getProducts().get(i).getSpecificProducts().size(); j++) {
                long days = ChronoUnit.DAYS.between(LocalDate.from(LocalDateTime.now()),ProductController.getProducts().get(i).getSpecificProducts().get(j).getExpDate().toLocalDate());
                if (days <= (long)3) {
                    System.out.println("Product's Barcode: " + ProductController.getProducts().get(i).getBarcode() +
                            " Product's ID: " + ProductController.getProducts().get(i).getSpecificProducts().get(j).getSp_ID() +
                            " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                            " Product's Category: " + ProductController.getProducts().get(i).getCategory());
                }

            }
        }
        ReportController.addReport(this); //add the report to report controller to save its details
    }

    //this function returns the report type, used to print all reports' information
    @Override
    public String getReportType() {
        return "Exp/ Defected Product Report";
    }
}
