package InterfaceLayer;

import BussinessLayer.Controllers.EmployeeController;
import BussinessLayer.Controllers.Facade;
import BussinessLayer.Controllers.StoreController;
import BussinessLayer.Objects.Employee;


import java.util.Scanner;


public class HRModuleCLI {
    private Scanner scanner;
    private Facade _facade;
    private EmployeesCLI _employeesCLI;
    private HRManagerCLI _hrManagerCLI;

    public void HRModuleCLI(){
        _employeesCLI = EmployeesCLI.getInstance();
        _hrManagerCLI = HRManagerCLI.getInstance();
        _facade = Facade.getInstance();
        scanner = new Scanner(System.in);
    }
    public void start(){
        if (_facade.hasHRManager() == false)
            _hrManagerCLI.HRMenuCreateEmployee(true); //create HR manager
        while (_facade.hasLoggedUser() == false) {
            LoginUser();
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
        while (_facade.hasLoggedUser() == false) {
            System.out.println("Please login to your user");
            System.out.println("Please enter your ID:");
            int id = scanner.nextInt();
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();
            try {
                _facade.login(id, password);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }


}
