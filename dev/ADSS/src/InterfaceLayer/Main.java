package InterfaceLayer;

import BussinessLayer.HRModule.Controllers.Facade;
import DataAccessLayer.DAO;
import InterfaceLayer.CLI.HRModule.HRModuleCLI;
import InterfaceLayer.TransportModule.transport_manager_UI;

import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("Invalid input");
            exit(0);
        }
        try {
            if (args[0].equals("CLI")) {
                mainCLI();
            } else if (args[0].equals("GUI")) {
                mainGUI();
            } else {
                System.out.println("Invalid input");
                exit(0);
            }
        } catch (Exception e) {
            System.out.println("Goodbye!");
        }
        finally {
            try{
                if (DAO.connection != null)
                    DAO.connection.close();
                DAO.connection = null;
            }
            catch (SQLException e){}
            exit(0);
        }
    }
    public static void mainCLI(){
        int menuChoice = -1;
        while (true) {
            System.out.println("Welcome to Super-Lee System !\n");
            System.out.println("Please select the required module:");
            System.out.println("1. HR Module");
            System.out.println("2. Transport Module");
            System.out.println("0. Exit");
            try {
                menuChoice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Goodbye!");
                exit(0);
            }
            if (menuChoice == 1)
                mainCLIHR();
            else if (menuChoice == 2)
                mainCLITransport();
            else if (menuChoice == 0) {
                return;
            } else
                System.out.println("Invalid input");
        }
    }

    public static void mainCLITransport() {
        transport_manager_UI transport_manager_ui = new transport_manager_UI();
        transport_manager_ui.start();
    }

    public static void mainCLIHR(){
        HRModuleCLI _HRModule = new HRModuleCLI();
        _HRModule.start();
    }

    public static void mainGUI(){

    }

}
