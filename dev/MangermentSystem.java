
import java.util.Scanner;
public class MangermentSystem {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int choice = 1;
        //write for a menu the user can choose from. must be get a number and not a string or char
        while (choice != 0) {
            HRManager hr_manager = null;
            while (hr_manager == null){
                System.out.println("Welcome to the HR system.");
                System.out.println("Please create a HR manager first");
                hr_manager = createHREmployee();
            }
            choice = scanner.nextInt();
            printMenu();
            switch (choice) {
                    case 1:
                    //HRmenu();
                    break;
                case 2:
                    //createStore();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
    public static void EmployeeMenu(){
        int choice = 1;
        while (choice != 0) {
            printEmployeeMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    //todo
                    break;
            }
        }
    }

    public static void HRmenu(HRManager hr_manager){
        int choice = 1;
        while (choice != 0) {
            printHRMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    //hr_manager.createEmployee();
                    break;
                case 2:
                    //createStore();
                    break;
                case 3:
                    //addEmployeeToStore();
                    break;
                case 4:
                    //addRoleToEmployee();
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
        System.out.println("Please enter the following details:");
        System.out.println("First name:");
        String first_name = scanner.next();
        System.out.println("Last name:");
        String last_name = scanner.next();
        System.out.println("Age:");
        int age = scanner.nextInt();
        System.out.println("ID:");
        int id = scanner.nextInt();
        System.out.println("Bank account:");
        int bank_account = scanner.nextInt();
        return new HRManager(first_name, last_name, age, id, bank_account);

        
    }
    public static void printMenu(){
        System.out.println("Please select a menu: ");
        System.out.println("1. HR menu");
        System.out.println("2. Employee menu");
        System.out.println("0. Exit");
    }
    public static void printEmployeeMenu(){
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
