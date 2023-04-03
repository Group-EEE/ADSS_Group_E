import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private static List<Report> AllReports;

    static int R_id=0;

    public ReportController(){
        AllReports = new ArrayList<Report>();
    }
    public void GetAllIssuedReports(){
        System.out.println("*****Reports Issued In System*****");
        for(int i=0; i< AllReports.size(); i++){
            System.out.println(AllReports.get(i).getID()+ ": "+ AllReports.get(i).getReportType());
        }

    }

    public static void addReport(Report r){
        AllReports.add(r);
    }





}
