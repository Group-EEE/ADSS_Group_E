
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import java.util.InputMismatchException;

public class MangementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static HRManager hr_manager;
    private static Login login; //Login is a static class
    private static AEmployee logged_user;

    public static void start() {
        if (hr_manager == null)
            HRMenuCreateEmployee(); //create HR manager
        while (logged_user == null) {
            LoginUser();
            if (logged_user instanceof HRManager) {
                System.out.println("Welcome to the HR system!");
                HRMenu();
            }
            else if (logged_user instanceof Employee) {
                System.out.println("Welcome to the employee system!");
                EmployeeMenu();
            }
        }
    }

    public static boolean LoginUser() {
        while (logged_user == null) {
            System.out.println("Please login to your user");
            System.out.println("Please enter your ID:");
            int id = scanner.nextInt();
            System.out.println("Please enter your password:");
            String password = scanner.next();
            logged_user = Login.login(id, password);
            if (logged_user == null)
                System.out.println("Invalid ID or password");
        }
        return true;
    }

    public static void printEmployeeMenu() {
        System.out.println("Please select an option");
        System.out.println("1. select shifts for this week");
        System.out.println("2. update personal Information");
        System.out.println("0. log out");
    }

    public static void EmployeeMenu() {
        String choice = "1";
        while (choice != "0") {
            printEmployeeMenu();
            choice = scanner.next();
            switch (choice) {
                case "1":
                    employeeMenuSelectShifts();
                    break;
                case "2":
                    updateInformation();
                case "0":
                    logged_user = null;
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
    public static boolean employeeMenuSelectShifts(){
        System.out.println("Please enter the Store name:");
        String storeName = scanner.next();
        Store store = hr_manager.getStoreByName(storeName);
        Schedule store_schedule = store.getCurrSchedule();
        if (store_schedule == null){
            System.out.println("HR managed haven't created a schedule for this store yet");
            return false;
        }

        System.out.println("Please select the shifts you ARE AVAILABLE to work at: ");
        System.out.println("Press Y or Yes to confirm");
        for (int i = 0; i < 14; i++) {
            System.out.println(store_schedule.getShift(i));
            if (scanner.next().equals("Y") || scanner.next().equals("Yes")) {
                boolean res = store_schedule.getShift(i).addInquiredEmployee((Employee)logged_user);
                if (res)
                    System.out.println("You have sighted up for this shift");
                else
                    System.out.println("Error");
            }
        }
        return true;
    }


    /**
     * @return true if the employee was able to update his information
     *  employee menu - 2. update personal Information
     *  hr menu - 7. update personal information
     */
    public static boolean updateInformation(){
        System.out.println("Please select what do you want to update");
        System.out.println("1. first name");
        System.out.println("2. last name");
        System.out.println("3. bank account");
        System.out.println("0. Back to main menu");
        String option = scanner.next();
        switch (option){
            case "1": //first name
                System.out.println("What is your new first name? ");
                logged_user.set_new_first_name(scanner.next());
                break;
            case "2": //last name
                System.out.println("What is your new last name? ");
                logged_user.set_new_last_name(scanner.next());
                break;
            case "3": //bank account
                System.out.println("What is your new bank account? ");
                logged_user.set_new_bank_account(scanner.next());
                break;
            case "0": //back to main menu
                return false;
            default: //invalid choice
                System.out.println("Invalid choice");
                break;
        }
        return true;
    }

    public static void printHRMenu() {

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

    public static void HRMenu() {
        String choice = "1";
        while (choice != "0") {
            printHRMenu();
            choice = scanner.next();
            switch (choice) {
                case "1":
                    HRMenuCreateEmployee(); //1. create new employee
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
                    logged_user = null;
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
    public static boolean HRMenuCreateEmployee(){
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
            password = scanner.next();
            System.out.println("Bank account:");
            bank_account = scanner.next();
            if (hr_manager == null){
                hr_manager = new HRManager(first_name, last_name, age, id, bank_account,password);
                if (hr_manager == null)
                    return false;
                return true;
            }

        }
        return hr_manager.createEmployee(first_name, last_name, age, id, bank_account,password);
    }

    /**
     * @return true if the store was created successfully, false otherwise
     * 2. create new store
     */
    public static boolean HRMenuCreateStore(){
        System.out.println("Please enter the following details:");
        System.out.println("Store name:");
        scanner.next();
        String storeName = scanner.nextLine();
        System.out.println("Store address:");
        String store_address = scanner.nextLine();
        return hr_manager.createStore(storeName, store_address);
    }

    /**
     * @return true if the employee was added successfully, false otherwise
     * 3. add employee to the store
     */
    public static boolean HRMenuAddEmployeeToStore(){
        System.out.println("Please enter the following details:");
        System.out.println("Store name:");
        String storeName = scanner.nextLine();
        System.out.println("Employee ID:");
        int employee_id = validInput("Please enter valid employee ID",0);
        return hr_manager.addEmployeeToStore(employee_id, storeName);
    }

    /**
     * @return true if the role was added successfully, false otherwise
     * add role to employee
     */
    public static boolean HRMenuAddRoleToEmployee(){
        System.out.println("Please enter the following details:");
        System.out.println("Employee ID:");
        int employee_id = validInput("Please enter valid employee ID",0);
        ARole new_role = getRoleByMenu();
        return hr_manager.addRoleToEmployee(employee_id, new_role);
    }

    /**
     * @return Role by user choice of the roles menu
     */
    public static ARole getRoleByMenu(){
        printRoles();
        String choice = scanner.next();
        ARole new_role = null;
        while (choice != "0") {
            switch (choice) {
                case "1":
                    new_role = new CashierRole();
                    break;
                case "2":
                    new_role = new WarehouseRole();
                    break;
                case "3":
                    new_role = new GeneralRole();
                    break;
                case "4":
                    new_role = new ShiftManagerRole();
                    break;
                case "5":
                    new_role = new SecurityRole();
                    break;
                case "6":
                    new_role = new CleanerRole();
                    break;
                case "7":
                    new_role = new UsherRole();
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
    public static boolean HRMenuCreateNewSchedule(){
        scanner.next();
        System.out.println("Please enter the Store name:");
        System.out.println("Enter 0 to exit");
        String storeName = "";
        Store store = null;
        while (!storeName.equals("0") && store == null){
            storeName = scanner.nextLine();
            if (storeName.equals("0"))
                return false;
            store = hr_manager.getStoreByName(storeName);
            if (store == null){
                System.out.println("There is no store with this name");
                System.out.println("Please enter a valid store name");
            }
        }
        Schedule store_scedule = store.getCurrSchedule();
        if (store_scedule != null){
            System.out.println("There is already a schedule");
            System.out.println("You must approve a schedule before creating a new one");
            return false;
        }

        System.out.println("Please enter the start date of the schedule (dd/mm/yyyy):");
        int day = validInput("Please enter a valid integer for the day.",1,31);
        int month = validInput("Please enter a valid integer for the month.",1,12);
        int year = validInput("Please enter a valid integer for the year.",2020,2025);

        LocalDate start_date = LocalDate.of(year, month, day);
        store_scedule = new Schedule(start_date);

        return true;
    }

    /**
     * @return true if the schedule was approved successfully, false otherwise
     * approve schedule
     */
    public static boolean HRMenuApproveSchedule(){
        System.out.println("Please enter the Store name:");
        System.out.println("Enter '0' to exit");
        String storeName = "1";
        Store store = null;
        scanner.next();
        while (!storeName.equals("0") && store == null){
            storeName = scanner.nextLine();
            if (storeName.equals("0"))
                return false;
            store = hr_manager.getStoreByName(storeName);
            if (store == null){
                System.out.println("There is no store with this name");
                System.out.println("Please enter a valid store name");
            }
        }
        Schedule store_scedule = store.getCurrSchedule();
        if (store_scedule == null){
            System.out.println("There is no schedule");
            System.out.println("You must create a schedule before approving it");
            return false;
        }
        List<Shift> rejectShifts = hr_manager.approveSchedule(store_scedule);
        for(Shift shift : rejectShifts){
            System.out.println("The shift " + shift + " was rejected");
            for (ARole role : shift.getRequiredRoles()){
                System.out.println("The role " + role + " didn't have an employee");
                System.out.println("Do you want to remove this role from the shift? (Y)");
                String choice = scanner.next();
                if (choice.equals("Y") || choice.equals("y")){
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
    public static boolean HRMenuChangeHours(){
        System.out.println("Please enter the Store name:");
        String storeName = scanner.next();
        Store store = hr_manager.getStoreByName(storeName);
        Schedule store_scedule = store.getCurrSchedule();
        if (store_scedule == null){
            System.out.println("There is no schedule");
            System.out.println("You must create a schedule before changing the hours");
            return false;
        }
        System.out.println("Please enter 'Y' or 'Yes' in order to change the hours to the shift");
        for (int i = 0; i < 14; i++) {
            System.out.println(store_scedule.getShift(i));
            String choice = scanner.next();
            System.out.println("Change hours? (Y/N)");
            if (choice == "Y" || choice == "Yes"){
                System.out.println("Please enter the start hour of the shift:");
                int start_hour = validInput("Please enter a valid start hour for the day",0,12);
                System.out.println("Please enter the end hour of the shift:");
                int end_hour = validInput("Please enter a valid end hour for the day",12,24);
                if (!store_scedule.getShift(i).setStartHour(start_hour))
                    return false;
                if (!store_scedule.getShift(i).setEndHour(end_hour))
                    return false;
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
    public static int validInput(String error){
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
    public static int validInput(String error,int min){
        boolean valid = false;
        int input = 0;
        while (!valid) {
            try {
                System.out.println("Please enter the employee ID:");
                input = scanner.nextInt();
                if (input < min){
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
    public static int validInput(String error,int min,int max){
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
    public static boolean validStringInput(String input){
        while (!input.equals("0") && input == null){
            input = scanner.nextLine();
            if (input.equals("0"))
                return false;
            input = hr_manager.getStoreByName(storeName);
            if (input == null){
                System.out.println("There is no store with this name");
                System.out.println("Please enter a valid store name");
            }
        }
        return true;
    }

    /**
     * @return true if the role was added successfully, false otherwise
     * 9. remove role from employee
     */
    public static boolean HRMenuRemoveRoleFromEmployee(){
        System.out.println("Please enter the employee ID:");
        int employee_id = validInput("Please enter a valid integer for the employee ID",0);
        System.out.println("Please select what role to remove from "+hr_manager.getEmployeeFirstNameById(employee_id));
        System.out.println("Please enter the role ID:");
        List<ARole> roles = hr_manager.getRolesById(employee_id);
        for(int i=0; i<roles.size(); i++){
            System.out.println(i+". "+roles.get(i));
        }
        System.out.println("0. Cancel");
        boolean validRoleChoice = false;
        int roleChoice = validInput("Please enter a valid integer for the role",0,roles.size());
        if (roleChoice == 0)
            return false;
        return hr_manager.removeRoleFromEmployee(employee_id, roles.get(roleChoice));
    }

    /**
     * @return true if the employee was removed successfully, false otherwise
     * 10. remove employee from Store
     */
    public static boolean HRMenuRemoveEmployeeFromStore(){
        System.out.println("Please enter the employee ID:");
        int employee_id = scanner.nextInt();
        System.out.println("Please enter the store name:");
        String storeName = scanner.next();
        return hr_manager.removeEmployeeFromStore(employee_id, storeName);
    }

    /**
     * @return true if the employee was removed successfully, false otherwise
     * 11. remove employee from system
     */
    public static boolean HRMenuRemoveEmployeeFromSystem(){
        System.out.println("Please enter the employee ID:");
        int employee_id = scanner.nextInt();
        return hr_manager.removeEmployee(employee_id);
    }

    /**
     * @return true if the store was removed successfully, false otherwise
     * 12. remove store
     */
    public static boolean HRMenuRemoveStoreFromSystem(){ //12. remove store
        System.out.println("Please enter the store name:");
        String storeName = scanner.next();
        return hr_manager.removeStore(storeName);
    }

    /**
     * @return true if the required roles were changed successfully, false otherwise
     *  13. select required roles
     */
    public static boolean HRMenuSelectRequiredRoles() { //13. select required roles
        System.out.println("Please enter the store name:");
        String storeName = scanner.next();
        Store store = hr_manager.getStoreByName(storeName);
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
        ARole new_role;
        while (choice != 0) {
            System.out.println("Please select if you want to remove or to add role");
            System.out.println("1. Add role");
            System.out.println("2. Remove role");
            System.out.println("0. Cancel");
            String input = scanner.next();
            switch (input) {
                case "1":
                    new_role = getRoleByMenu();
                    if (!hr_manager.addRequiredRoleToShift(shift, new_role))
                        return false;
                    System.out.println("The role "+ new_role + " was added successfully to "+shift);
                    break;
                case "2":
                    new_role = getRoleByMenu();
                    if (!hr_manager.removeRoleFromShift(shift, new_role))
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

    public static void printRoles() {
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
