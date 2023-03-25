
public class MangermentSystem {

    public static void main(String[] args) {
        int choice = 1;
        while (choice != 0) {
            printMenu();
        }
    }

    public static void printMenu() {
        System.out.println("Hello to the HR system!");
        System.out.println("Please select the following options");
        System.out.println("1. add new employee");
        System.out.println("2. add new store");
    }

    public static void printEmployeeMenu() {
        System.out.println("What kind of Employee do you want to add?");
        System.out.println("1. HR manager");
        System.out.println("2. Casier");
        System.out.println("3. Warehouse Employee");
    }
}
