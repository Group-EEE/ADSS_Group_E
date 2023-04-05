package InterfaceLayer;

import BussinessLayer.Controllers.EmployeeController;
import BussinessLayer.Controllers.StoreController;
import BussinessLayer.Objects.AEmployee;
import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.HRManager;

import java.util.Scanner;


public class HRModuleCLI {

    protected AEmployee _loggedUser = null;
    protected HRManager _HRManager = null;
    protected Scanner scanner;
    protected EmployeeController _employeeController = EmployeeController.getInstance();
    protected StoreController _storeController = StoreController.getInstance();
    private EmployeesCLI _employeesCLI;
    private HRManagerCLI _hrManagerCLI;

    public void HRModuleCLI(){
        EmployeesCLI _employeesCLI = EmployeesCLI.getInstance();
        HRManagerCLI _hrManagerCLI = HRManagerCLI.getInstance();
        scanner = new Scanner(System.in);
    }
    public void start(){
        if (_HRManager == null)
            _hrManagerCLI.HRMenuCreateEmployee(); //create HR manager
        while (_loggedUser == null) {
            LoginUser();
            if (_loggedUser instanceof HRManager) {
                System.out.println("Welcome to the HR system!");
                _hrManagerCLI.HRMenu();
            } else if (_loggedUser instanceof Employee) {
                System.out.println("Welcome to the employee system!");
                _employeesCLI.employeeMenu();
            }
        }
    }
    public boolean LoginUser() {
        while (_loggedUser == null) {
            System.out.println("Please login to your user");
            System.out.println("Please enter your ID:");
            int id = scanner.nextInt();
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();
            _loggedUser = _employeeController.login(id, password);
            if (_loggedUser == null)
                System.out.println("Invalid ID or password");
        }
        return true;
    }

    /**
     * @return true if the employee was able to update his information
     * employee menu - 2. update personal Information
     * hr menu - 7. update personal information
     */
    public boolean updateInformation() {
        System.out.println("Please select what do you want to update");
        System.out.println("1. first name");
        System.out.println("2. last name");
        System.out.println("3. bank account");
        System.out.println("0. Back to main menu");
        String option = scanner.nextLine();
        switch (option) {
            case "1": //first name
                System.out.println("What is your new first name? ");
                _loggedUser.setNewFirstName(scanner.nextLine());
                break;
            case "2": //last name
                System.out.println("What is your new last name? ");
                _loggedUser.setNewLastName(scanner.nextLine());
                break;
            case "3": //bank account
                System.out.println("What is your new bank account? ");
                _loggedUser.setNewBankAccount(scanner.nextLine());
                break;
            case "0": //back to main menu
                return false;
            default: //invalid choice
                System.out.println("Invalid choice");
                break;
        }
        return true;
    }
}
