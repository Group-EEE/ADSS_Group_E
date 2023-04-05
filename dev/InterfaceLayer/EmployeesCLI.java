package InterfaceLayer;

import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.Schedule;
import BussinessLayer.Objects.Store;
import java.util.Scanner;

import java.util.InputMismatchException;

public class EmployeesCLI extends HRModuleCLI{
    private static EmployeesCLI _employeesCLI;

    private EmployeesCLI() {
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
        while (choice != "0") {
            printEmployeeMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    employeeMenuSelectShifts();
                    break;
                case "2":
                    updateInformation();
                case "0":
                    _loggedUser = null;
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
        Store store = _storeController.getStoreByName(storeName);
        Schedule store_schedule = store.getCurrSchedule();
        if (store_schedule == null) {
            System.out.println("HR managed haven't created a schedule for this store yet");
            return false;
        }

        System.out.println("Please select the shifts you ARE AVAILABLE to work at: ");
        System.out.println("Press Y or Yes to confirm");
        for (int i = 0; i < 14; i++) {
            System.out.println(store_schedule.getShift(i));
            if (scanner.nextLine().equals("Y") || scanner.nextLine().equals("Yes")) {
                boolean res = store_schedule.getShift(i).addInquiredEmployee((Employee) _loggedUser);
                if (res)
                    System.out.println("You have sighted up for this shift");
                else
                    System.out.println("Error");
            }
        }
        return true;
    }
}