package InterfaceLayer;

import BussinessLayer.Controllers.EmployeeController;
import BussinessLayer.Controllers.Facade;
import BussinessLayer.Controllers.StoreController;
import BussinessLayer.Objects.Employee;
import serviceLayer.IntegratedService;


import java.util.Scanner;


public class HRModuleCLI {
    private final Scanner scanner;
    private final IntegratedService _integratedService;
    private final EmployeesCLI _employeesCLI;
    private final HRManagerCLI _hrManagerCLI;

    public HRModuleCLI(){
        _employeesCLI = EmployeesCLI.getInstance();
        _hrManagerCLI = HRManagerCLI.getInstance();
        _integratedService = IntegratedService.getInstance();
        scanner = new Scanner(System.in);
    }
    public void start(){
        if (_integratedService.hasHRManager() == false)
            _hrManagerCLI.HRMenuCreateEmployee(true); //create HR manager
        while (_integratedService.hasLoggedUser() == false) {
            LoginUser();
            if (_integratedService.isLoggedUserIsHRManager()) {
                System.out.println("Welcome to the HR system!");
                _hrManagerCLI.HRMenu();
            } else {
                System.out.println("Welcome to the employee system!");
                _employeesCLI.employeeMenu();
            }
        }
    }
    public boolean LoginUser() {
        while (_integratedService.hasLoggedUser() == false) {
            System.out.println("Please login to your user");
            System.out.println("Please enter your ID:");
            int id;
            try {
                id = Integer.valueOf(scanner.nextLine());
            }
            catch (Exception e){
                System.out.println("Invalid input");
                continue;
            }
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();
            try {
                _integratedService.login(id, password);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }


}
