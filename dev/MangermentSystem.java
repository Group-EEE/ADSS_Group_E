
import java.util.Scanner;

import Roles.*;

import java.util.InputMismatchException;

public class MangermentSystem {
    public static Scanner scanner = new Scanner(System.in);
    public static HRManager hr_manager;
    public static Login login;
    public static AEmployee logged_user;
    public static void main(String[] args) {
        if (hr_manager == null)
            HRMenuCreateEmployee(); //create HR manager
        while (logged_user == null) {
            LoginUser();
            if (logged_user instanceof HRManager)
                HRMenu();
            else if (logged_user instanceof Employee)
                EmployeeMenu();
        }
    }

    public static void EmployeeMenu() {
        int choice = 1;
        while (choice != 0) {
            printEmployeeMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // todo
                    break;
            }
        }
    }

    public static void HRMenu() {
        int choice = 1;
        while (choice != 0) {
            printHRMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    HRMenuCreateEmployee();
                    break;
                case 2:
                    HRMenuCreateStore();
                    break;
                case 3:
                    HRMenuAddEmployeeToStore();
                    break;
                case 4:
                    HRMenuAddRoleToEmployee();
                    break;
                case 0:
                    System.out.println("Back to main menu");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

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
                //Login.createUser(id, password, hr_manager);
                if (hr_manager == null)
                    return false;
                return true;
            }

        }
        return hr_manager.createEmployee(first_name, last_name, age, id, bank_account,password);
    }

    public static boolean HRMenuAddEmployeeToStore(){
        System.out.println("Please enter the following details:");
        System.out.println("Store name:");
        String store_name = scanner.nextLine();
        System.out.println("Employee ID:");
        int employee_id = scanner.nextInt();
        return hr_manager.addEmployeeToStore(employee_id, store_name);
    }
    public static boolean HRMenuCreateStore(){
        System.out.println("Please enter the following details:");
        System.out.println("Store name:");
        String store_name = scanner.next();
        System.out.println("Store address:");
        String store_address = scanner.next();
        return hr_manager.createStore(store_name, store_address);
    }

    public static boolean HRMenuAddRoleToEmployee(){
        System.out.println("Please enter the following details:");
        System.out.println("Employee ID:");
        int employee_id = scanner.nextInt();
        System.out.println("Role:");
        System.out.println("1. Cashier");
        System.out.println("2. Warehouse Employee");
        System.out.println("3. General Employee");
        System.out.println("4. Shift Manager");
        System.out.println("5. Security");
        System.out.println("6. Cleaner");
        System.out.println("7. usher");
        System.out.println("0. Back to main menu");


        String role = scanner.next();
        IRole new_role;
        switch (role){
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
                new_role = new ShiftRole();
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
                return false;
        }
        return hr_manager.addRoleToEmployee(employee_id, role);
    }
    public static boolean LoginUser() {
        while (logged_user == null) {
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
    }

    public static void printHRMenu() {
        System.out.println("Hello to the HR system!");
        System.out.println("Please select the following options");
        System.out.println("1. create new employee");
        System.out.println("2. create new store");
        System.out.println("3. add employee to store");
        System.out.println("4. add role to employee");
        System.out.println("5. create new schedule");
        System.out.println("6. approve shifts");
        System.out.println("0. log out");
    }

    public static void printCreateEmployeeMenu() {
        System.out.println("What kind of Employee do you want to add?");
        System.out.println("1. Cashier");
        System.out.println("2. Warehouse Employee");
        System.out.println("3. General Employee");
        System.out.println("4. Shift manager");
        System.out.println("0. Back to main menu");
    }
}
