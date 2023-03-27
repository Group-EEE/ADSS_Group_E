
import java.util.Scanner;
import java.util.InputMismatchException;

public class MangermentSystem {
    public static Scanner scanner = new Scanner(System.in);
    public static HRManager hr_manager;
    public static Login login;
    public static void main(String[] args) {
        HRMenuCreateEmployee(); //create HR manager
        String choice = "1";
        // write for a menu the user can choose from. must be get a number and not a
        // string or char
        while (choice != "0") {
            AEmployee user = LoginUser();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    HRMenu();
                    break;
                case "2":
                    EmployeeMenu();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
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
                    // addEmployeeToStore();
                    break;
                case 4:
                    // addRoleToEmployee();
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
        String store_name = scanner.nextLine();
        System.out.println("Store address:");
        String store_address = scanner.nextLine();
        return hr_manager.createStore(store_name, store_address);
    }
    public AEmployee LoginUser() {
        AEmployee user = null;
        while (user == null) {
            System.out.println("Please enter your ID:");
            int id = scanner.nextInt();
            System.out.println("Please enter your password:");
            String password = scanner.next();
            user = Login.login(id, password);
            if (user == null)
                System.out.println("Invalid ID or password");
        }
        return user;
    }

    public static void printEmployeeMenu() {
        System.out.println("Please select an option");
    }

    public static void printHRMenu() {
        System.out.println("Hello to the HR system!");
        System.out.println("Please select the following options");
        System.out.println("1. create new employee");
        System.out.println("2. create new store");
        System.out.println("3. add employee to store");
        System.out.println("4. add role to employee");
        System.out.println("0. Back to main menu");
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
