import java.time.LocalDateTime;
import java.util.List;
//abstract class that all kinds of reports inherit
public abstract class Report {
    private int ID;
    private String Reporter;
    private LocalDateTime Date; //the date of publishing

    public Report(String Reporter) { //constructor
        ReportController.R_id++;
        this.ID = ReportController.R_id;
        this.Reporter = Reporter;
        this.Date = LocalDateTime.now();
    }

    //the function that creates and prints the report
    public abstract String getReportType();

    //return the id of the report
    public int getID() {
        return ID;
    }

    //return the date of the report
    public LocalDateTime getDate() {
        return Date;
    }
}
