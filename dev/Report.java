import java.time.LocalDateTime;
import java.util.List;

public abstract class Report {
    private int ID;
    private String Reporter;
    private LocalDateTime Date;
    private List<String> Pname;
    private List<Integer> Barcode;
    private List<Integer> Amount;

    public Report(String Reporter) {
        ReportController.R_id++;
        this.ID = ReportController.R_id;
        this.Reporter = Reporter;
        this.Date = LocalDateTime.now();
    }

    public abstract String getReportType();

    public int getID() {
        return ID;
    }
}
