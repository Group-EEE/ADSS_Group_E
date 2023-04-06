import java.util.ArrayList;
import java.util.List;
//class that saves all the details about the reports that was issued
public class ReportController {
    private static List<Report> AllReports; //list to save al the reports

    static int R_id=0; //counter that will give every report its id

    public ReportController(){ //constructor
        AllReports = new ArrayList<Report>();
    }
    public void GetAllIssuedReports(){ //function that return the id and type of every issued report
        System.out.println("*****Reports Issued In System*****");
        for(int i=0; i< AllReports.size(); i++){
            System.out.println(AllReports.get(i).getID()+ ": "+ AllReports.get(i).getReportType());
        }

    }

    //add report to the list of reports
    public static void addReport(Report r){
        AllReports.add(r);
    }





}
