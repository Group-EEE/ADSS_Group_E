package InterfaceLayer;

import BussinessLayer.Controllers.Facade;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Schedule;
import BussinessLayer.Objects.Shift;
import BussinessLayer.Objects.Store;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HRManagerCLI{

    private static HRManagerCLI _HRManagerCLI = null;
    private Facade _facade = Facade.getInstance();
    private Scanner scanner;
    private HRManagerCLI(){}

    public static HRManagerCLI getInstance(){
        if(_HRManagerCLI == null)
            _HRManagerCLI = new HRManagerCLI();
        return _HRManagerCLI;
    }

    public void printHRMenu() {
        System.out.println("Please select the following options");
        System.out.println("1. create new employee");
        System.out.println("2. create new store");
        System.out.println("3. add employee to store");
        System.out.println("4. add role to employee");
        System.out.println("5. create new schedule");
        System.out.println("6. approve shifts");
        System.out.println("7. update personal information");
        System.out.println("8. change schedule hours");
        System.out.println("9. remove role from employee");
        System.out.println("10. remove employee from store");
        System.out.println("11. remove employee from system");
        System.out.println("12. remove store from system");
        System.out.println("13. select required roles for a schedule");
        System.out.println("0. log out");
    }

    public void HRMenu() {
        String choice = "1";
        while (choice != "0") {
            printHRMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    HRMenuCreateEmployee(false); //1. create new employee
                    break;
                case "2":
                    HRMenuCreateStore(); //2. create new store
                    break;
                case "3":
                    HRMenuAddEmployeeToStore(); //3. add employee to the store
                    break;
                case "4":
                    HRMenuAddRoleToEmployee(); //4. add role to employee
                    break;
                case "5": //5. create new schedule
                    HRMenuCreateNewSchedule();
                    break;
                case "6": //6. approve schedule
                    HRMenuApproveSchedule();
                    break;
                case "7": //7. update personal information
                    updateInformation();
                    break;
                case "8": // 8. change scedule hours
                    HRMenuChangeHours();
                    break;
                case "9": // 9. remove role from employee
                    HRMenuRemoveRoleFromEmployee();
                    break;
                case "10":
                    HRMenuRemoveEmployeeFromStore();
                    break;
                case "11":
                    HRMenuRemoveEmployeeFromSystem();
                    break;
                case "12":
                    HRMenuRemoveStoreFromSystem();
                    break;
                case "13":
                    HRMenuSelectRequiredRoles();
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
     * @return true if the employee was created successfully
     * 1. create new employee
     */
    public boolean HRMenuCreateEmployee(boolean isHRManager){
        boolean valid = false;
        int age = 0;
        String first_name = "";
        String last_name = "";
        int id = 0;
        String bank_account = "0";
        String password = "";
        System.out.println("Welcome to the HR system!");
        System.out.println("You must create an HR employee in order to use the system");
        while(valid == false){
            valid = true;
            System.out.println("Please enter the following details:");
            System.out.println("First name:");
            first_name = scanner.nextLine();
            System.out.println("Last name:");
            last_name = scanner.nextLine();
            System.out.println("Age:");
            try{
                age = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid age");
                valid = false;
                scanner.nextLine();
                continue;
            }
            System.out.println("ID:");
            try{
                id = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid ID");
                valid = false;
                scanner.nextLine();
                continue;
            }
            System.out.println("password:");
            password = scanner.nextLine();
            System.out.println("Bank account:");
            bank_account = scanner.nextLine();
            _facade.createEmployee(first_name, last_name, age, id, bank_account,password);
        }
        return true;
    }

    /**
     * @return true if the employee was able to update his information
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
                _facade.setNewFirstName(scanner.nextLine());
                break;
            case "2": //last name
                System.out.println("What is your new last name? ");
                _facade.setNewLastName(scanner.nextLine());
                break;
            case "3": //bank account
                System.out.println("What is your new bank account? ");
                _facade.setNewBankAccount(scanner.nextLine());
                break;
            case "0": //back to main menu
                return false;
            default: //invalid choice
                System.out.println("Invalid choice");
                break;
        }
        return true;
    }

    /**
     * @return true if the store was created successfully, false otherwise
     * 2. create new store
     */
    public boolean HRMenuCreateStore(){
        System.out.println("Please enter the following details:");
        scanner.nextLine();
        boolean valid = false;
        int storeId = 0;
        while(!valid) {
            try {
                System.out.println("Store id:");
                storeId = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid store ID");
            }
            String storeName = scanner.nextLine();
            System.out.println("Store address:");
            String storeAddress = scanner.nextLine();
            try {
                _facade.createStore(storeId, storeName, storeAddress);
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /**
     * @return true if the employee was added successfully, false otherwise
     * 3. add employee to the store
     */
    public boolean HRMenuAddEmployeeToStore() {
        System.out.println("Please enter the following details:");
        boolean valid = false;
        while (!valid) {
            String storeName = getStoreName();
            if (storeName == null)
                return false;
            System.out.println("Employee ID:");
            int employeeID = validInput("Please enter valid employee ID", 0);
            if (employeeID == 0)
                return false;
            try{
                _facade.addEmployeeToStore(employeeID, storeName);
                System.out.println("Employee was added successfully");
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /**
     * @return true if the role was added successfully, false otherwise
     * add role to employee
     */
    public boolean HRMenuAddRoleToEmployee(){
        System.out.println("Please enter the following details:");
        System.out.println("Employee ID:");
        int employeeID = validInput("Please enter valid employee ID",0);
        RoleType new_role = getRoleByMenu();
        try{ _facade.addRoleToEmployee(employeeID, new_role); }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Role was not added to the employee");
            return false;
        }
        return true;
    }

    /**
     * @return Role by user choice of the roles menu
     */
    public RoleType getRoleByMenu(){
        printRoles();
        String choice = scanner.nextLine();
        RoleType new_role = null;
        while (choice != "0") {
            switch (choice) {
                case "1":
                    new_role = RoleType.Cashier;
                    break;
                case "2":
                    new_role = RoleType.Warehouse;
                    break;
                case "3":
                    new_role = RoleType.General;
                    break;
                case "4":
                    new_role = RoleType.ShiftManager;
                    break;
                case "5":
                    new_role = RoleType.Security;
                    break;
                case "6":
                    new_role = RoleType.Cleaner;
                    break;
                case "7":
                    new_role = RoleType.Usher;
                    break;
                case "0":
                    return null;
            }
        }
        return new_role;
    }

    /**
     * @return true if the schedule was created successfully, false otherwise
     * //5. create new schedule
     */
    public boolean HRMenuCreateNewSchedule(){
        scanner.nextLine();
        System.out.println("Please enter the Store name:");
        System.out.println("Enter 0 to exit");
        String storeName = scanner.nextLine();
       if (storeName.equals("0"))
            return false;
        System.out.println("Please enter the start date of the schedule (dd/mm/yyyy):");
        int day = validInput("Please enter a valid integer for the day.",1,31);
        int month = validInput("Please enter a valid integer for the month.",1,12);
        int year = validInput("Please enter a valid integer for the year.",2020,2025);
        try{
            _facade.createNewSchedule(storeName, day, month, year);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @return true if the schedule was approved successfully, false otherwise
     * approve schedule
     */
    public boolean HRMenuApproveSchedule() {
        System.out.println("Please enter the Store name:");
        System.out.println("Enter '0' to exit");
        String storeName = "1";
        Store store = null;
        scanner.nextLine();
        while (!storeName.equals("0")) {
            storeName = scanner.nextLine();
            if (storeName.equals("0"))
                return false;
        }
        List<Shift> rejectShifts;
        try {
            rejectShifts = _facade.approveSchedule(storeName);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            return false;
        }
        for (Shift shift : rejectShifts) {
            System.out.println("The shift " + shift + " was rejected");
            for (RoleType role : shift.getRequiredRoles()) {
                System.out.println("The role " + role + " didn't have an employee");
                System.out.println("Do you want to remove this role from the shift? (Y)");
                String choice = scanner.nextLine();
                if (choice.equals("Y") || choice.equals("y")) {
                    shift.removeRequiredRole(role);
                }

            }
        }
        return true;
    }

    /**
     * @return true if the schedule was created successfully, false otherwise
     * 8. change schedule hours
     */
    public boolean HRMenuChangeHours(){
        System.out.println("Please enter the Store name:");
        String storeName = scanner.nextLine();
        try{
            _facade.printSchedule(storeName);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        boolean valid = false;
        while (!valid){
            System.out.println("Please enter the number of the shift you want to change");
            System.out.println("Enter 0 to exit");
            int shiftNum = validInput("Please enter a valid integer",0,14);
            if (shiftNum == 0)
                return true;
            System.out.println("Please select the new start hour");
            int startHour = validInput("Please enter a valid integer",0,23);
            System.out.println("Please select the new end hour");
            int endHour = validInput("Please enter a valid integer",0,23);
            try{
                _facade.changeHoursShift(storeName,shiftNum,startHour,endHour);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /**
     * @param error - the error message to print
     * @return int input from the user
     * @throws InputMismatchException if the input is not an integer
     * to clean the code
     */
    public int validInput(String error){
        boolean valid = false;
        int input = 0;
        while (!valid) {
            try {
                System.out.println("Please enter the employee ID:");
                input = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input."+error);
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return input;
    }
    public int validInput(String error,int min){
        boolean valid = false;
        int input = 0;
        System.out.println("Enter 0 to quit");
        while (!valid) {
            try {
                System.out.println("Please enter the employee ID:");
                input = scanner.nextInt();
                if (input == 0)
                    return 0;
                if (input < min){
                    throw new InputMismatchException();
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input."+error);
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return input;
    }
    public int validInput(String error,int min,int max){
        boolean valid = false;
        int input = 0;
        while (!valid) {
            try {
                System.out.println("Please enter the employee ID:");
                input = scanner.nextInt();
                if (input < min || input > max){
                    System.out.println("Invalid input.");
                    continue;
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input."+error);
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return input;
    }

    public String getStoreName(){
        System.out.println("Please enter the Store name:");
        System.out.println("Enter '0' to exit");
        String input = "1";
        Store store = null;
        scanner.nextLine();
        while (store == null){
            input = scanner.nextLine();
            if (input.equals("0"))
                return null;
        }
        return input;
    }

    /**
     * @return true if the role was added successfully, false otherwise
     * 9. remove role from employee
     */
    public boolean HRMenuRemoveRoleFromEmployee(){
        System.out.println("Please enter the employee ID:");
        int employeeID = validInput("Please enter a valid integer for the employee ID",0);
        System.out.println("Please select what role to remove from "+_facade.getEmployeeFirstNameById());
        System.out.println("Please enter the role ID:");
        System.out.println("0. Cancel");
        try {
            _facade.printEmployeeRoles(employeeID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        int roleChoice = validInput("Please enter a valid integer for the role",0,roles.size());
        if (roleChoice == 0)
            return false;
        try{
            _facade.removeRoleFromEmployee(employeeID, roleChoice);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @return true if the employee was removed successfully, false otherwise
     * 10. remove employee from Store
     */
    public boolean HRMenuRemoveEmployeeFromStore(){
        System.out.println("Please enter the employee ID:");
        int employeeID = scanner.nextInt();
        System.out.println("Please enter the store name:");
        String storeName = scanner.nextLine();
        try{
            _facade.removeEmployeeFromStore(employeeID, storeName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @return true if the employee was removed successfully, false otherwise
     * 11. remove employee from system
     */
    public boolean HRMenuRemoveEmployeeFromSystem(){
        int employeeID = validInput("Please enter a valid integer for the employee ID",1);
        if (employeeID == 0)
            return false;
        try{
            _facade.removeEmployee(employeeID);
            System.out.println("Employee was removed successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Employee was not removed");
            return false;
        }
        return true;
    }

    /**
     * @return true if the store was removed successfully, false otherwise
     * 12. remove store
     */
    public boolean HRMenuRemoveStoreFromSystem(){ //12. remove store
        System.out.println("Please enter the store name:");
        System.out.println("Enter '0' to exit");
        String storeName = scanner.nextLine();
        if (storeName.equals("0"))
            return false;
        try{ _facade.removeStore(storeName); }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Store was not removed");
            return false;
        }
        return true;
    }

    /**
     * @return true if the required roles were changed successfully, false otherwise
     *  13. select required roles
     */
    public boolean HRMenuSelectRequiredRoles() { //13. select required roles
        System.out.println("Please enter the store name:");
        String storeName = scanner.nextLine();
        Store store = _storeController.getStoreByName(storeName);
        if (store == null) {
            System.out.println("There is no store with this name");
            return false;
        }
        Schedule store_scedule = store.getCurrSchedule();
        if (store_scedule == null) {
            System.out.println("There is no schedule");
            System.out.println("You must create a schedule before changing the hours");
            return false;
        }
        System.out.println("Please select what shift do you want to change the required roles");
        for (int i = 0; i < 14; i++) {
            System.out.println((i + 1) + "" + store_scedule.getShift(i));
        }
        System.out.println("0. Cancel");
        int choice = validInput("Please enter a valid choice", 0, 14);
        if (choice == 0)
            return false;
        Shift shift = store_scedule.getShift(choice - 1);
        RoleType new_role;
        while (choice != 0) {
            System.out.println("Please select if you want to remove or to add role");
            System.out.println("1. Add role");
            System.out.println("2. Remove role");
            System.out.println("0. Cancel");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    new_role = getRoleByMenu();
                    if (!_HRManager.addRequiredRoleToShift(shift, new_role))
                        return false;
                    System.out.println("The role "+ new_role + " was added successfully to "+shift);
                    break;
                case "2":
                    new_role = getRoleByMenu();
                    if (!_HRManager.removeRoleFromShift(shift, new_role))
                        return false;
                    System.out.println("The role "+ new_role + " was added removed to "+shift);
                    break;
                case "0":
                    break;
                default:
                    ;
            }
        }
        return true;
    }

    public void printRoles() {
        System.out.println("Choose role: ");
        System.out.println("1. Cashier");
        System.out.println("2. Warehouse Employee");
        System.out.println("3. General Employee");
        System.out.println("4. Shift Manager");
        System.out.println("5. Security");
        System.out.println("6. Cleaner");
        System.out.println("7. usher");
        System.out.println("0. Back to main menu");
    }

}
