package InventoryModule.Presentation;

import InventoryModule.Business.Controllers.ReportController;
import SuppliersModule.Presentation.OrderPresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//this class represent the sub menu of Reports. Here we can send Order due to shortage.
public class ReportPresentation {
    private ReportController reportController;
    Scanner reader;
    public ReportPresentation(){
        reportController =ReportController.getInstance();
        reader = new Scanner(System.in);
    }

    //print to the screen the report menu
    public void ShowReportMenu(){
        String c3 ="";
        while (!c3.equals("0")) {
            Scanner option3 = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Get all reports in system");
            System.out.println("2. Issue order products report");
            System.out.println("3. Issue current supply");
            System.out.println("4. Issue EXP/ defected products report");
            System.out.println("5. Issue report by category");
            System.out.println("0. Exit");
            c3 = option3.nextLine();
            switch (c3) {
                case "1": //Get all reports in system
                    reportController.GetAllIssuedReports();
                    break;
                case "2": //Issue order products report
                    System.out.println("Please enter Issue's reporter's name:");
                    String r = reader.nextLine();
                    reportController.createOrderReport(r);
                    System.out.println("Do you want to send the order? yes/no");
                    String s = reader.nextLine();
                    if(s.equals("yes"))
                        System.out.println(reportController.makeOrderForLastReport());
                    break;
                case "3": //Issue current supply
                    System.out.println("Please enter Issue's reporter's name:");
                    String r3 = reader.nextLine();
                    reportController.createCurrSupplyReport(r3);
                    break;
                case "4": //Issue EXP/ defected products report
                    System.out.println("Please enter Issue's reporter's name:");
                    String r4 = reader.nextLine();
                    reportController.createExpOrDefectReport(r4);
                    break;
                case "5": //Issue report by category
                    System.out.println("Please enter Issue's reporter's name:");
                    String r5 = reader.nextLine();
                    System.out.println("Please enter the number of categories:");
                    int number = reader.nextInt();
                    List<String> cate = new ArrayList<String>();
                    for(int i=0; i<number; i++){
                        System.out.println("Please enter Issue's Category name:");
                        String category = reader.nextLine();
                        cate.add(category);
                    }
                    reportController.createByCategoryReport(r5,cate);
                    break;
            }
        }
    }

}
