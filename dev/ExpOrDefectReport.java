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
    //is in less than 3 days that exist in the store

    public ExpOrDefectReport(String reporter){
        super(reporter);
        System.out.println("***** Exp/Defected Products Report *****");
        System.out.println("Reporter : "+ reporter);
        for (int i = 0; i < ProductController.getProducts().size(); i++) {
            for (int j = 0; j < ProductController.getProducts().get(i).getSpecificProducts().size(); j++) {
                if (ProductController.getProducts().get(i).getSpecificProducts().get(j).isDefective() ||
                        ProductController.getProducts().get(i).getSpecificProducts().get(j).getExpDate().isBefore(LocalDateTime.now())) {
                    System.out.println("Product's Barcode: " + ProductController.getProducts().get(i).getBarcode() +
                            " Product's ID: " + ProductController.getProducts().get(i).getSpecificProducts().get(j).getSp_ID() +
                            " Product's Name: " + ProductController.getProducts().get(i).getPName() +
                            " Product's Category: " + ProductController.getProducts().get(i).getCategory() +
                            " Product's Defect Type: " + ProductController.getProducts().get(i).getSpecificProducts().get(j).getDefectType());
                }

            }
        }
        System.out.println("***** Products About to be Exp in less than 3 days *****");
        for (int i = 0; i < ProductController.getProducts().size(); i++) {
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
        ReportController.addReport(this);
    }

    //this function returns the report type

    @Override
    public String getReportType() {
        return "Exp/ Defected Product Report";
    }
}
