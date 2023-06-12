package InterfaceLayer.CLI.HRModule;

import BussinessLayer.HRModule.Controllers.Facade;

import java.util.Scanner;

import java.util.InputMismatchException;

public class EmployeesCLI {
    private static EmployeesCLI _employeesCLI;
    private final Facade _facade;
    private final Scanner scanner;

    private EmployeesCLI() {
        _facade = Facade.getInstance();
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
        System.out.println("3. Print your schedule");
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
                case "3":
                    printEmployeeSchedule();
                    break;
                case "0":
                    _facade.logout();
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
        String storeName = getStoreName();
        if (storeName == null)
            return false;
        if (!_facade.checkIfEmployeeWorkInStore(storeName)){
            System.out.println("You don't work in this store");
            return false;
        }
        try{
            System.out.println(_facade.getSchedule(storeName));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        boolean valid=true;
        while (valid){
            System.out.println("Please select the shifts you ARE AVAILABLE to work at: ");
            System.out.println("Enter 'exit' to exit");
            try {
                String input = scanner.nextLine();
                if (input.equals("exit")){
                    valid=false;
                    break;
                }
                int shiftID = Integer.valueOf(input);
                try {
                    _facade.addEmployeeToShift(storeName, shiftID);
                    System.out.println("You asked to work at the shift " + shiftID+" successfully");
                } catch (Exception e) {
                    System.out.println("Could not add employee to shift");
                    valid = false;
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
        System.out.println("4. password");
        System.out.println("0. Back to main menu");
        String option = scanner.nextLine();
        switch (option) {
            case "1": //first name
                System.out.println("What is your new first name? ");
                if (_facade.setNewFirstName(scanner.nextLine()))
                    System.out.println("First name updated successfully");
                else
                    System.out.println("First name update failed");
                break;
            case "2": //last name
                System.out.println("What is your new last name? ");
                if (_facade.setNewLastName(scanner.nextLine()))
                    System.out.println("Last name updated successfully");
                else
                    System.out.println("Last name update failed");
                break;
            case "3": //bank account
                System.out.println("What is your new bank account? ");
                if (_facade.setNewBankAccount(scanner.nextLine()))
                    System.out.println("Bank account updated successfully");
                else
                    System.out.println("Bank account update failed");
                break;
            case "4": //password
                System.out.println("What is your new password? ");
                if (_facade.setNewPassword(scanner.nextLine()))
                    System.out.println("Password updated successfully");
                else
                    System.out.println("Password update failed");
                break;
            case "0": //back to main menu
                return false;
            default: //invalid choice
                System.out.println("Invalid choice");
                break;
        }
        return true;
    }

    public boolean printEmployeeSchedule(){
        try{
            _facade.printEmployeeSchedule();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public String getStoreName(){
        System.out.println("Please enter the Store name:");
        System.out.println("Enter '0' to exit");
        String storeName = null;
        storeName = scanner.nextLine();
        if (storeName.equals("0"))
            return null;
        return storeName;
    }
}