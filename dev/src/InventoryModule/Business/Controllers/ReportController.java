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
    public void GetAllIssuedReports(){ //function that return the id and type of every issued report
        System.out.println("*****Reports Issued In System*****");
        for(int i=0; i< AllReports.size(); i++){
            System.out.println(AllReports.get(i).getID()+ ": "+ AllReports.get(i).getReportType());
        }

    }
    public OrderReport createOrderReport(String reporter){
        OrderReport orderReport = new OrderReport(reporter);
        return orderReport;
    }
    public void createCurrSupplyReport(String reporter){
        CurrSupplyReport currSupplyReport = new CurrSupplyReport(reporter);
    }
    public void createExpOrDefectReport(String reporter){
        ExpOrDefectReport expOrDefectReport = new ExpOrDefectReport(reporter);
    }
    public void createByCategoryReport(String reporter, List<String> cate){
        ByCategoryReport byCategoryReport = new ByCategoryReport(reporter, cate);
    }





    //add report to the list of reports
    public static void addReport(Report r){
        AllReports.add(r);
        lastReport = r;
    }

    public void makeOrderForLastReport(){
        OrderReport r = (OrderReport) lastReport;
        OrderGenerator.makeOrderFromOrderReport(r);
    }
}
