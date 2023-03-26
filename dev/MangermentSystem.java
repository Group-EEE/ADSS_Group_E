
import java.util.Scanner;
import java.util.InputMismatchException;

public class MangermentSystem {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // there must be an HR manager in order to use the system
        HRManager hr_Manager = createHREmployee();
        String choice = "1";
        // write for a menu the user can choose from. must be get a number and not a
        // string or char
        while (choice != "0") {
            printMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    HRmenu();
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

    public static void HRMenu(HRManager hr_manager) {
        int choice = 1;
        while (choice != 0) {
            printHRMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // hr_manager.createEmployee();
                    break;
                case 2:
                    // createStore();
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

    public static HRManager createHREmployee(){
        boolean valid = false;
        int age = 0;
        String first_name = "";
        String last_name = "";
        int id = 0;
        String bank_account = "0";
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
            System.out.println("Bank account:");
            bank_account = scanner.nextLine();
        }
        return new HRManager(first_name, last_name, age, id, bank_account);

        
    }

    public static void printMenu() {
        System.out.println("Please select a menu: ");
        System.out.println("1. HR menu");
        System.out.println("2. Employee menu");
        System.out.println("0. Exit");
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
