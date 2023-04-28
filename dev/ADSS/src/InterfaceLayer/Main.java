package InterfaceLayer;

import InterfaceLayer.HRModule.HRModuleCLI;
import InterfaceLayer.TransportModule.transport_manager_UI;
import ServiceLayer.IntegratedService;
import ServiceLayer.IntegratedService;

import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Super-Lee System !\n");
        System.out.println("Please select the required module:");
        System.out.println("1. HR Module");
        System.out.println("2. Transport Module");
        System.out.println("0. Exit");
        int menuChoice = 0;
        try {
            menuChoice = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Goodbye!");
            exit(0);
        }
        if (menuChoice == 1)
            mainHR();
        else if (menuChoice == 2)
            mainTransport();
        else if (menuChoice == 0)
            exit(0);
        else
            System.out.println("Invalid input");

    }

    public static void mainTransport() {
        transport_manager_UI transport_manager_ui = new transport_manager_UI();
        transport_manager_ui.start();
    }

    public static void mainHR(){
        System.out.println("load data ? y/n");
        String yesOrNo = scanner.nextLine();
        if(yesOrNo.equals("y")){
            try{
                IntegratedService.getInstance().loadData();
            }
            catch (Exception e){
                System.out.println("Didn't load data");
                System.out.println("Error: " + e.getMessage());
            }
        }
        HRModuleCLI _HRModule = new HRModuleCLI();
        _HRModule.start();
    }
}
