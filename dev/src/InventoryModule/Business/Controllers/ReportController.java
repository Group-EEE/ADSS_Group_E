package InventoryModule.Business.Controllers;

import InventoryModule.Business.*;
import SuppliersModule.Business.Generator.OrderGenerator;

import java.util.ArrayList;
import java.util.List;
//class that saves all the details about the reports that was issued
public class ReportController {
    private static List<Report> AllReports; //list to save al the reports
    static ReportController reportController;
    private static Report lastReport;
    public static int R_id=0; //counter that will give every report its id

    private ReportController(){ //constructor
        AllReports = new ArrayList<Report>();
    }

    public static ReportController getInstance(){
        if(reportController == null)
            reportController = new ReportController();
        return reportController;
    }
    public String GetAllIssuedReports(){ //function that return the id and type of every issued report
        String details = "*****Reports Issued In System*****"+"\n";
        for(int i=0; i< AllReports.size(); i++){
            details= details+ AllReports.get(i).getID();
            details+=": ";
            details+=AllReports.get(i).getReportType();
            details+="\n";
        }
        return details;
    }
    public OrderReport createOrderReport(String reporter){
        OrderReport orderReport = new OrderReport(reporter);
        return orderReport;
    }
    public CurrSupplyReport createCurrSupplyReport(String reporter){
        CurrSupplyReport currSupplyReport = new CurrSupplyReport(reporter);
        return currSupplyReport;
    }
    public ExpOrDefectReport createExpOrDefectReport(String reporter){
        ExpOrDefectReport expOrDefectReport = new ExpOrDefectReport(reporter);
        return expOrDefectReport;
    }
    public ByCategoryReport createByCategoryReport(String reporter, List<String> cate){
        ByCategoryReport byCategoryReport = new ByCategoryReport(reporter, cate);
        return byCategoryReport;
    }

    //add report to the list of reports
    public static void addReport(Report r){
        AllReports.add(r);
        lastReport = r;
    }

    public String makeOrderForLastReport(){
        OrderReport r = (OrderReport) lastReport;
        return OrderGenerator.makeOrderFromOrderReport(r);
    }
}
