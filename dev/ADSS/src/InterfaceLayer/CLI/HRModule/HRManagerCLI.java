package InterfaceLayer.CLI.HRModule;

import BussinessLayer.HRModule.Controllers.Facade;
import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.cold_level;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HRManagerCLI{

    private static HRManagerCLI _HRManagerCLI;
    private final Facade _facade;
    private final Scanner scanner;

    private HRManagerCLI(){
        _facade = Facade.getInstance();
        scanner = new Scanner(System.in);
    }

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
        System.out.println("13. select required roles from a shift");
        System.out.println("14. remove required roles from a shift");
        System.out.println("15. print all employees");
        System.out.println("16. print all stores");
        System.out.println("17. print schedule");
        System.out.println("18. create new driver");
        System.out.println("19. create new logistics schedule");
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
                    HRMenuCreateNewStoreSchedule();
                    break;
                case "6": //6. approve schedule
                    HRMenuApproveSchedule(false);
                    break;
                case "7": //7. update personal information
                    updateInformation();
                    break;
                case "8": // 8. change schedule hours
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
                    HRMenuAddRequiredRoleToShift();
                    break;
                case "14":
                    HRMenuRemoveRequiredRoleFromShift();
                    break;
                case "15":
                    HRMenuPrintEmployees();
                    break;
                case "16":
                    HRMenuPrintStores();
                    break;
                case "17":
                    HRMenuPrintSchedule();
                    break;
                case "18":
                    HRMenuCreateDriver();
                    break;
                case "19":
                    HRMenuCreateNewLogisiticSchedule();
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
     * @return true if the employee was created successfully
     * 1. create new employee
     */
    public boolean HRMenuCreateEmployee(boolean isHRManager){
        boolean valid = false;
        int age = 0;
        String firstName = "";
        String lastName = "";
        int id = 0;
        String bankAccount = "0";
        String password = "";
        int salary = 0;
        String hiringCondition = "";
        LocalDate startDateOfEmployment;
        System.out.println("Welcome to the HR system!");

        while(valid == false){
            valid = true;
            System.out.println("Please enter the following details:");
            System.out.println("ID:");
            try{
                id = Integer.valueOf(scanner.nextLine());
            }
            catch (InputMismatchException e){
                System.out.println("Invalid ID");
                valid = false;
                continue;
            }
            System.out.println("First name:");
            firstName = scanner.nextLine();
            System.out.println("Last name:");
            lastName = scanner.nextLine();
            System.out.println("Age:");
            try{
                age = Integer.valueOf(scanner.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid age");
                valid = false;
                continue;
            }

            System.out.println("Bank account:");
            bankAccount = scanner.nextLine();



            System.out.println("salary:");
            try{
                salary = Integer.valueOf(scanner.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid salary");
                valid = false;
                continue;
            }

            System.out.println("hiring condition:");
            hiringCondition = scanner.nextLine();

            int day = validInput("Please enter the start day of the employement","Please enter a valid integer for the day.",1,31);
            int month = validInput("Please enter the start month of the employement","Please enter a valid integer for the month.",1,12);
            int year = validInput("Please enter the start year of the employement","The year must start from 2020 to 2025",2020,2025);
            try {
                startDateOfEmployment = LocalDate.of(year, month, day);
            } catch (Exception e){
                System.out.println("Invalid date");
                valid = false;
                continue;
            }
            System.out.println("password:");
            password = scanner.nextLine();
            try {
                _facade.createEmployee(id, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password, isHRManager);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
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
        boolean valid = false;
        int storeArea = 0;
        while(!valid) {
            String storeName = getStoreName();
            if (storeName == null)
                return false;
            System.out.println("Store address:");
            String storeAddress = scanner.nextLine();
            System.out.println("Store phone number:");
            String storePhoneNumber = scanner.nextLine();
            System.out.println("Store contact name:");
            String storeContactName = scanner.nextLine();
            try {
                System.out.println("Store area:");
                storeArea = Integer.valueOf(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid store area");
                continue;
            }
            try {
                _facade.createStore(storeName, storeAddress,storePhoneNumber,storeContactName,storeArea);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            valid = true;
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
            int employeeID = validInput("Please enter employee id","Please enter valid employee ID", 0);
            if (employeeID == 0)
                return false;
            try{
                if (_facade.addEmployeeToStore(employeeID, storeName))
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
        int employeeID = validInput("Please enter employee id","Please enter valid employee ID",0);
        if (employeeID == 0)
            return false;
        RoleType newRole = getRoleByMenu();
        if (newRole == null) {
            System.out.println("Role was not added to the employee");
            return false;
        }
        try{
            _facade.addRoleToEmployee(employeeID, newRole);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Role was not added to the employee");
            return false;
        }
        System.out.println("The role "+newRole+" was added to the employee "+_facade.getEmployeeFullNameById(employeeID)+" successfully");
        return true;
    }

    /**
     * @return Role by user choice of the roles menu
     */
    public RoleType getRoleByMenu(){
        printRoles();
        String choice = scanner.nextLine();
        RoleType newRole = null;
        while (newRole == null) {
            switch (choice) {
                case "1":
                    newRole = RoleType.Cashier;
                    break;
                case "2":
                    newRole = RoleType.Warehouse;
                    break;
                case "3":
                    newRole = RoleType.General;
                    break;
                case "4":
                    newRole = RoleType.ShiftManager;
                    break;
                case "5":
                    newRole = RoleType.Security;
                    break;
                case "6":
                    newRole = RoleType.Cleaner;
                    break;
                case "7":
                    newRole = RoleType.Usher;
                    break;
                case "0":
                    return null;
                default:
                    break;
            }
        }
        return newRole;
    }

    /**
     * @return true if the schedule was created successfully, false otherwise
     * //5. create new schedule
     */
    public boolean HRMenuCreateNewStoreSchedule(){
       String storeName = getStoreName();
       if (storeName == null)
           return false;
       System.out.println("Please enter the start date of the schedule (dd/mm/yyyy):");
       int day = validInput("Please enter the start day of the schedule","Please enter a valid integer for the day.",1,31);
       int month = validInput("Please enter the month of the schedule","Please enter a valid integer for the month.",1,12);
       int year = validInput("Please enter the year of the schedule","The year must start from 2020 to 2025",2020,2025);
       try{
           _facade.createNewStoreSchedule(storeName, day, month, year);
           System.out.println("The schedule was created successfully for the store "+storeName);
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
    public boolean HRMenuApproveSchedule(boolean isLogistics) {
        String storeName;
        if (isLogistics)
            storeName = "Logistics";
        else
            storeName = getStoreName();

        if (storeName == null)
            return false;
        List<Shift> rejectShifts;
        try {
            rejectShifts = _facade.approveSchedule(storeName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        for (Shift shift : rejectShifts) {
            System.out.println("The shift " + shift + " was rejected");
            List<RoleType> roles = shift.getRequiredRoles();
            int iter = 0; //because the list is a not deep copy, the roles are getting deleted while in the loop. so foreach not work here
            while(roles != null && iter < roles.size()) {
                System.out.println("The role " + roles.get(iter) + " didn't have an employee");
                System.out.println("Do you want to remove this role from the shift? (Y)");
                String choice = scanner.nextLine();
                if (choice.equals("Y") || choice.equals("y")) {
                    shift.removeRequiredRole(roles.get(iter));
                }
                else
                    iter++;
            }
        }
        return true;
    }

    /**
     * @return true if the schedule was created successfully, false otherwise
     * 8. change schedule hours
     */
    public boolean HRMenuChangeHours(){
        String storeName = getStoreName();
        if (storeName == null)
            return false;
        try{
            System.out.println(_facade.getSchedule(storeName));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        boolean valid = false;
        while (!valid){
            int shiftNum = validInput("Please enter the number of the shift you want to change","Please enter a valid integer",0,14);
            if (shiftNum == 0)
                return true;
            int startHour = validInput("Please select the new start hour","Please enter a valid integer",0,23);
            int endHour = validInput("Please select the new end hour","Please enter a valid integer",0,23);
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
    public int validInput(String text, String error){
        boolean valid = false;
        int input = 0;
        while (!valid) {
            try {
                System.out.println(text);
                input = Integer.valueOf(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input."+error);
            }
        }
        return input;
    }
    public int validInput(String text,String error,int min){
        boolean valid = false;
        int input = 0;
        System.out.println("Enter 0 to quit");
        while (!valid) {
            try {
                System.out.println(text);
                input = Integer.valueOf(scanner.nextLine());
                if (input == 0)
                    return 0;
                if (input < min){
                    throw new InputMismatchException();
                }
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input."+error);
            }
        }
        return input;
    }
    public int validInput(String text, String error,int min,int max){
        boolean valid = false;
        int input = 0;
        while (!valid) {
            try {
                System.out.println(text);
                input = Integer.valueOf(scanner.nextLine());
                if (input < min || input > max){
                    System.out.println("Invalid input.");
                    continue;
                }
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input."+error);
            }
        }
        return input;
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

    /**
     * @return true if the role was added successfully, false otherwise
     * 9. remove role from employee
     */
    public boolean HRMenuRemoveRoleFromEmployee() {
        int employeeID = validInput("Please enter employee id","Please enter a valid integer for the employee ID", 0);
        if (employeeID == 0)
            return false;
        String firstName;
        try {
            firstName = _facade.getEmployeeFullNameById(employeeID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Please select what role to remove from " + firstName);
        System.out.println("Please enter the role ID:");
        try {
            for(RoleType role : _facade.getEmployeeRoles(employeeID))
                System.out.println(role);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        int roleChoice = validInput("Please select the role you want to remove", "Please enter a valid integer for the role", 0);
        if (roleChoice == 0)
            return false;
        try {
            _facade.removeRoleFromEmployee(employeeID, roleChoice-1);
            System.out.println("The role was removed successfully from " + firstName);
        } catch (Exception e) {
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
        int employeeID = Integer.valueOf(scanner.nextLine());
        String storeName = getStoreName();
        if (storeName == null)
            return false;
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
        int employeeID = validInput("Please enter employee id","Please enter a valid integer for the employee ID",1);
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
        String storeName = getStoreName();
        if (storeName == null)
            return false;
        try{
            _facade.removeStore(storeName);
        }
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
    public boolean HRMenuAddRequiredRoleToShift() { //13. select required roles
        String storeName = getStoreName();
        if (storeName == null)
            return false;
        boolean exit = false;
        while(!exit) {
            try{
                System.out.println(_facade.getSchedule(storeName));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
            int shiftNum = validInput("Please enter the number of the shift you want to add the required role","Please enter a valid integer", 0, 14);
            if (shiftNum == 0)
                exit = true;
            RoleType newRole = getRoleByMenu();
            if (newRole == null)
                exit = true;
            System.out.println("Is this role must be filled?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int choice = validInput("Please enter your choice","Please enter a valid integer", 1, 2);
            try {
                if (choice == 1)
                    _facade.addRequiredRoleToShift(storeName, shiftNum-1, newRole, true);
                else
                    _facade.addRequiredRoleToShift(storeName, shiftNum-1, newRole, false);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean HRMenuRemoveRequiredRoleFromShift(){
        String storeName = getStoreName();
        if (storeName == null)
            return false;
        boolean exit = false;
        while(!exit) {
            try{
                System.out.println(_facade.getSchedule(storeName));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
            int shiftNum = validInput("Please enter the number of the shift you want to remove some of the required roles","Please enter a valid integer", 0, 14);
            if (shiftNum == 0)
                exit = true;
            RoleType newRole = getRoleByMenu();
            if (newRole == null)
                exit = true;
            try {
                _facade.removeRequiredRoleFromShift(storeName, shiftNum-1, newRole);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean HRMenuPrintEmployees(){
        for (Employee employee :_facade.getAllEmployees()){
            System.out.println(employee);
        }
        return true;
    }

    public boolean HRMenuPrintStores(){
        for (Store store : _facade.getAllStores()){
            System.out.println(store);
        }
        return true;
    }

    public boolean HRMenuPrintSchedule(){
        String storeName = getStoreName();
        if (storeName == null)
            return false;
        try{
            System.out.println(_facade.getSchedule(storeName));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void HRMenuCreateDriver(){
        boolean valid = false;
        int age = 0;
        String firstName = "";
        String lastName = "";
        int id = 0;
        String bankAccount = "0";
        String password = "";
        int salary = 0;
        String hiringCondition = "";
        LocalDate startDateOfEmployment = null;
        int License_ID = 0;
        cold_level level = null;
        double weight = 0;
        int level_ch;
        while(valid == false){
            valid = true;
            System.out.println("Please enter the following details:");
            System.out.println("ID:");
            try{
                id = Integer.valueOf(scanner.nextLine());
            }
            catch (InputMismatchException e){
                System.out.println("Invalid ID");
                valid = false;
                continue;
            }
            System.out.println("First name:");
            firstName = scanner.nextLine();
            System.out.println("Last name:");
            lastName = scanner.nextLine();
            System.out.println("Age:");
            try{
                age = Integer.valueOf(scanner.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid age");
                valid = false;
                continue;
            }

            System.out.println("Bank account:");
            bankAccount = scanner.nextLine();



            System.out.println("salary:");
            try{
                salary = Integer.valueOf(scanner.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid salary");
                valid = false;
                continue;
            }

            System.out.println("hiring condition:");
            hiringCondition = scanner.nextLine();

            int day = validInput("Please enter the start day of the employement","Please enter a valid integer for the day.",1,31);
            int month = validInput("Please enter the start month of the employement","Please enter a valid integer for the month.",1,12);
            int year = validInput("Please enter the start year of the employement","The year must start from 2020 to 2025",2020,2025);
            try {
                startDateOfEmployment = LocalDate.of(year, month, day);
            } catch (Exception e){
                System.out.println("Invalid date");
                valid = false;
                continue;
            }
            System.out.println("password:");
            password = scanner.nextLine();
            //license ID
            System.out.println("Enter an license ID (a 5 digit integer): ");
            String input = scanner.nextLine();
            if(input.startsWith("0")){
                System.out.print("License ID number cannot contain 0 at the beginning. ");
                if(input.length() != 5){
                    System.out.print("Input must be a 5 digit integer. ");
                }
                valid = false;
                continue;
            }
            try {
                License_ID = Integer.parseInt(input);
                // Check if the input is a 5 digit integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 5 digit integer.");
            }
            // TODO: check if the license ID number is already exist in the system.
            System.out.println("Enter the cold level of the truck the driver can drive in (1- Freeze, 2- Cold, 3- Dry),\n and the the Max weight of the truck he can drive in: ");
            input = scanner.nextLine();
            String[] parts = input.split(" ");
            try {
                // Check if the input contains two parts
                if (parts.length != 2) {
                    System.out.println("Input must contain two numbers - Int and then Double, separated by a space.");
                    continue;}
                level_ch = Integer.parseInt(parts[0]);
                weight = Double.parseDouble(parts[1]);

                // Check if the input numbers are positive
                if (level_ch > 3 || level_ch < 1){
                    System.out.println("only the number 1-3 is valid for cold level.");
                    continue;
                }
                switch (level_ch) {
                    case 1 -> level = cold_level.Freeze;
                    case 2 -> level = cold_level.Cold;
                    case 3 -> level = cold_level.Dry;
                }
                if (weight < 0) {
                    System.out.println("Weight must be positive.");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter two valid positive numbers, An integer and then double separated by a space.");
            }
        }
        try {
            _facade.createDriver(id, firstName, lastName, age, bankAccount, salary, hiringCondition, startDateOfEmployment, password, License_ID, level, weight);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean HRMenuCreateNewLogisiticSchedule(){ // TODO:create new schedule when existing
        System.out.println("Please enter the start date of the schedule (dd/mm/yyyy):");
        int day = validInput("Please enter the start day of the schedule","Please enter a valid integer for the day.",1,31);
        int month = validInput("Please enter the month of the schedule","Please enter a valid integer for the month.",1,12);
        int year = validInput("Please enter the year of the schedule","The year must start from 2020 to 2025",2020,2025);
        try{
            boolean res = _facade.createNewLogisiticsSchedule(day, month, year);
            if (res)
                System.out.println("The schedule was created successfully for the store Logistics");
            else
                System.out.println("some error occurred");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
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
