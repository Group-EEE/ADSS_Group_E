package InterfaceLayer.CLI.HRModule;


import BussinessLayer.HRModule.Controllers.Facade;

import java.util.Scanner;


public class HRModuleCLI {
    private final Scanner scanner;
    private final Facade _facade;
    private final EmployeesCLI _employeesCLI;
    private final HRManagerCLI _hrManagerCLI;

    public HRModuleCLI(){
        _employeesCLI = EmployeesCLI.getInstance();
        _hrManagerCLI = HRManagerCLI.getInstance();
        _facade = Facade.getInstance();
        scanner = new Scanner(System.in);
    }
    public void start(){
        if (_facade.hasHRManager() == false){
            System.out.println("You must create an HR employee in order to use the system");
            _hrManagerCLI.HRMenuCreateEmployee(true); //create HR manager
        }
        while (_facade.hasLoggedUser() == false) {
            if(!LoginUser())
                return;
            if (_facade.isLoggedUserIsHRManager()) {
                System.out.println("Welcome to the HR system!");
                _hrManagerCLI.HRMenu();
            } else {
                System.out.println("Welcome to the employee system!");
                _employeesCLI.employeeMenu();
            }
        }
    }
    public boolean LoginUser() {
        while (!_facade.hasLoggedUser()) {
            System.out.println("Please login to your user, 0 for exit");
            System.out.println("Please enter your ID:");
            int id;
            try {
                id = Integer.valueOf(scanner.nextLine());
                if (id == 0) {
                    return false;
                }
            }
            catch (Exception e){
                System.out.println("Invalid input");
                continue;
            }
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();
            try {
                _facade.login(id, password);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        if (_facade.hasLoggedUser()){
            System.out.println("Login successful");
            return true;
        }
        return false;
    }
}
