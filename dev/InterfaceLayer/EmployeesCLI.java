package InterfaceLayer;

import ServiceLayer.ModulesServices.IntegratedService;

import java.util.Scanner;

import java.util.InputMismatchException;

public class EmployeesCLI {
    private static EmployeesCLI _employeesCLI;
    private final IntegratedService _integrationService;
    private final Scanner scanner;

    private EmployeesCLI() {
        _integrationService = IntegratedService.getInstance();
        scanner = new Scanner(System.in);
    }

    public static EmployeesCLI getInstance() {
        if (_employeesCLI == null)
            _employeesCLI = new EmployeesCLI();
        return _employeesCLI;
    }

    public void printEmployeeMenu() {
        System.out.println("Please select an option");
        System.out.println("1. select shifts for this week");
        System.out.println("2. update personal Information");
        System.out.println("0. log out");
    }

    public void employeeMenu() {
        String choice = "1";
        while (!choice.equals("0")) {
            printEmployeeMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    employeeMenuSelectShifts();
                    break;
                case "2":
                    updateInformation();
                    break;
                case "0":
                    _integrationService.logout();
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    /**
     * @return true if the employee was able to select shifts
     * 1. select shifts for this week
     */
    public boolean employeeMenuSelectShifts() {
        System.out.println("Please enter the Store name:");
        String storeName = scanner.nextLine();
        try{
            _integrationService.printSchedule(storeName);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Please select the shifts you ARE AVAILABLE to work at: ");
        System.out.println("Enter 0 to exit");
        boolean valid=false;
        while (!valid){
            try {
                int choice = Integer.valueOf(scanner.nextLine());
                if (choice == 0)
                    return true;
                try {
                    _integrationService.addEmployeeToShift(storeName, choice - 1);
                } catch (Exception e) {
                    System.out.println("Could not add employee to shift");
                    System.out.println(e.getMessage());
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }
        }
        return true;
    }

    /**
     * @return true if the employee was able to update his information
     * employee menu - 2. update personal Information
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
                if (_integrationService.setNewFirstName(scanner.nextLine()))
                    System.out.println("First name updated successfully");
                else
                    System.out.println("First name update failed");
                break;
            case "2": //last name
                System.out.println("What is your new last name? ");
                if (_integrationService.setNewLastName(scanner.nextLine()))
                    System.out.println("Last name updated successfully");
                else
                    System.out.println("Last name update failed");
                break;
            case "3": //bank account
                System.out.println("What is your new bank account? ");
                if (_integrationService.setNewBankAccount(scanner.nextLine()))
                    System.out.println("Bank account updated successfully");
                else
                    System.out.println("Bank account update failed");
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