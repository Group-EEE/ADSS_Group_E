package HR.Controllers;

import BussinessLayer.HRModule.Controllers.EmployeeController;
import BussinessLayer.HRModule.Controllers.ScheduleController;
import BussinessLayer.HRModule.Controllers.StoreController;
import BussinessLayer.HRModule.Objects.Employee;
import BussinessLayer.HRModule.Objects.RoleType;
import BussinessLayer.HRModule.Objects.Schedule;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.HRMoudle.SchedulesDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleControllerTest {

    ScheduleController _scheduleController = ScheduleController.getInstance();
    StoreController _storeController = StoreController.getInstance();
    EmployeeController _employeeController = EmployeeController.getInstance();

    @BeforeEach
    void setUp() {
        _employeeController.createEmployee(9999, "John", "Doe", 30, "123456789", 10000, "Full time", LocalDate.of(1999,4,22),"passwordTest");
        _employeeController.createEmployee(312736, "filledRoleFirstNameTest", "filledRoleLastNameTest", 30, "123456789", 10000, "Full time", LocalDate.of(1999,4,22),"passwordTest");
        _storeController.createStore("testStore", "testAddress", "testPhone", "testContact",0);
        _storeController.addEmployeeToStore( 9999,"testStore");
        _scheduleController.createNewStoreSchedule("testStore", 1,1,1999);
        _scheduleController.addEmployeeToShift(_employeeController.getEmployee(9999), "testStore", 0);

    }

    @AfterEach
    void tearDown() {
        _scheduleController.deleteSchedule("testStore");
        _storeController.removeStore("testStore");
        _employeeController.removeEmployee(9999);
        _employeeController.removeEmployee(312736);
    }

    @Test
    void createNewSchedule() {
        assertThrows(IllegalArgumentException.class, () -> _scheduleController.createNewStoreSchedule(null, 1,1,1999));
        assertThrows(IllegalArgumentException.class, () -> _scheduleController.createNewStoreSchedule("testStoreNotExist", 1,1,1999));
        assertThrows(IllegalArgumentException.class, () -> _scheduleController.createNewStoreSchedule("testStoreNotExist", 1,1,1999));
        assertThrows(IllegalArgumentException.class, () -> _scheduleController.createNewStoreSchedule("testStore", 30,2,1999));
        _scheduleController.createNewStoreSchedule("testStore", 1,1,1999);
    }

//    @Test
//    void deleteScheduleIDFromStore(){
//        assertThrows(IllegalArgumentException.class, () -> {
//            _scheduleController.deleteScheduleIDFromStore(null);
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            _scheduleController.deleteScheduleIDFromStore("testStoreNotExist");
//        });
//    }

    @Test
    void deleteSchedule(){
        assertThrows(IllegalArgumentException.class, () -> _scheduleController.deleteSchedule(null));
        assertThrows(IllegalArgumentException.class, () -> _scheduleController.deleteSchedule(-1));
        _storeController.createStore("testStore2", "testAddress2", "testPhone2", "testContact2",0);
        _scheduleController.createNewStoreSchedule("testStore2", 1,1,1999);
        int scheduleID = _scheduleController.getSchedule("testStore2").getScheduleID();
        assertTrue(_scheduleController.deleteSchedule(scheduleID));
        _storeController.removeStore("testStore2");
    }

    @Test
    void getSchedule(){
        assertThrows(IllegalArgumentException.class, () -> _scheduleController.getSchedule(null));
        _scheduleController.addRequiredRoleToShift("testStore", 1, RoleType.Driver,false);
        _scheduleController.addMustBeFilledRole("testStore", 1, RoleType.Driver);
        Employee employee = new Employee(9999, "John", "Doe", 30, "123456789", 10000, "Full time", null,"passwordTest");
        _scheduleController.addEmployeeToShift(employee,"testStore", 1);
        assertNotNull(_scheduleController.getSchedule("testStore"));
    }

    @Test //if the system is functional, it will replace the active schedule!!
    void createNewLogisticsSchedule(){
        assertTrue(_scheduleController.createNewLogisticsSchedule(1,1,1999));
        Schedule schedule = _scheduleController.getSchedule("Logistics");
        assertNotNull(schedule);
    }

    @Test
    void getScheduleNoCache(){
        assertTrue(_employeeController.addRoleToEmployee(9999, RoleType.Cashier));
        assertTrue(_scheduleController.addFilledRoleToShift("testStore",9999, 0, RoleType.Cashier));
        assertTrue(SchedulesDAO.getInstance().removeCache());
        assertNotNull(_scheduleController.getSchedule("testStore"));
    }

    @Test
    void changeShiftHours(){
        assertThrows(IllegalArgumentException.class, () -> { //invalid storeName
            _scheduleController.changeShiftHours(null, 0, 0, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid shiftsID -1
            _scheduleController.changeShiftHours("testStore", 0, 0, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid shiftsID 14
            _scheduleController.changeShiftHours("testStore", 0, 0, 14);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid endHour
            _scheduleController.changeShiftHours("testStore", 0, 25, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid endHour
            _scheduleController.changeShiftHours("testStore", 0, -1, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid startHour
            _scheduleController.changeShiftHours("testStore", 25, 0, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid startHour
            _scheduleController.changeShiftHours("testStore", -1, 0, 0);
        });
        assertTrue(_scheduleController.changeShiftHours("testStore", 7, 13, 0));
    }

    @Test
    void getEmployeeSchedule(){
        assertTrue(_employeeController.addRoleToEmployee(9999, RoleType.Cashier));
        assertTrue(_employeeController.addRoleToEmployee(9999, RoleType.Driver));
        assertTrue(_scheduleController.addFilledRoleToShift("testStore", 9999, 0, RoleType.Cashier));
        assertTrue(_storeController.createStore("testStore2", "testAddress", "testPhone", "testContact", 0));
        assertTrue(_scheduleController.createNewStoreSchedule("testStore2", 1, 1, 1999));
        assertTrue(_storeController.addEmployeeToStore(9999, "testStore2"));
        assertTrue(_scheduleController.addEmployeeToShift(_employeeController.getEmployee(9999), "testStore2", 1));
        assertTrue(_scheduleController.addRequiredRoleToShift("testStore2", 1, RoleType.Driver, false));
        assertTrue(_scheduleController.addFilledRoleToShift("testStore2", 9999, 1, RoleType.Driver));
        assertEquals(_scheduleController.getEmployeeSchedule(_employeeController.getEmployee(9999)).size(), 2);
        assertTrue(_scheduleController.deleteSchedule("testStore2"));
        assertTrue(_storeController.removeStore("testStore2"));
    }

    @Test
    void addRequiredRoleToShift(){
        assertThrows(IllegalArgumentException.class, () -> { //invalid storeName
            _scheduleController.addRequiredRoleToShift(null, 0, RoleType.Cashier, false);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid shiftsID -1
            _scheduleController.addRequiredRoleToShift("testStore", -1, RoleType.Cashier, false);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid shiftsID 14
            _scheduleController.addRequiredRoleToShift("testStore", 14, RoleType.Cashier, false);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid roleType null
            _scheduleController.addRequiredRoleToShift("testStore", 0, null, false);
        });
        _scheduleController.addRequiredRoleToShift("testStore", 1, RoleType.Driver,false);
    }

    @Test
    void addDriverToLogisticsShift(){
        assertTrue(_employeeController.addRoleToEmployee(9999, RoleType.Driver));
        assertTrue(_scheduleController.createNewLogisticsSchedule(1,1,1999));
        assertTrue(_scheduleController.addDriverToLogisticsShift(9999, 0));
    }

    @Test
    void getShiftIDByDate(){
        assertThrows(IllegalArgumentException.class, () -> { //invalid storeName
            _scheduleController.getShiftIDByDate(null, LocalDate.of(1999, 1, 1), ShiftType.MORNING);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid date null
            _scheduleController.getShiftIDByDate("testStore", null, ShiftType.MORNING);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid shiftType null
            _scheduleController.getShiftIDByDate("testStore", LocalDate.of(1999, 1, 1), null);
        });
        assertEquals(_scheduleController.getShiftIDByDate("testStore", LocalDate.of(1999, 1, 1), ShiftType.MORNING), 0);
        assertEquals(_scheduleController.getShiftIDByDate("testStore", LocalDate.of(1999, 1, 1), ShiftType.NIGHT), 1);
        assertEquals(_scheduleController.getShiftIDByDate("testStore", LocalDate.of(1999, 1, 2), ShiftType.MORNING), 2);
        assertEquals(_scheduleController.getShiftIDByDate("testStore", LocalDate.of(1999, 1, 2), ShiftType.NIGHT), 3);
        assertThrows(IllegalArgumentException.class, () -> { //invalid date
            _scheduleController.getShiftIDByDate("testStore", LocalDate.of(1999, 2, 1), ShiftType.MORNING);
        });
    }

    @Test
    void isThereStandByDriverAndWareHouseEmpty(){
        assertThrows(IllegalArgumentException.class, () -> { //invalid storeName
            _scheduleController.isThereStandByDriverAndWareHouse(null, LocalDate.of(1999, 1, 1), ShiftType.MORNING);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid date null
            _scheduleController.isThereStandByDriverAndWareHouse("testStore", null, ShiftType.MORNING);
        });
        assertThrows(IllegalArgumentException.class, () -> { //invalid shiftType null
            _scheduleController.isThereStandByDriverAndWareHouse("testStore", LocalDate.of(1999, 1, 1), null);
        });
        assertFalse(_scheduleController.isThereStandByDriverAndWareHouse("testStore", LocalDate.of(1999, 1, 1), ShiftType.MORNING));
    }

    @Test
    void isThereStandByDriverAndWareHouse(){
        assertTrue(_employeeController.addRoleToEmployee(9999, RoleType.DriverStandBy));
        assertTrue(_scheduleController.createNewLogisticsSchedule(1,1,1999));
        assertTrue(_scheduleController.addStandByDriverToLogisticsShift(9999, 0));
        assertTrue(_employeeController.addRoleToEmployee(312736,RoleType.Warehouse));
        assertTrue(_scheduleController.addEmployeeToShift(_employeeController.getEmployee(312736), "testStore", 0));
        assertTrue(_scheduleController.addFilledRoleToShift("testStore", 312736, 0, RoleType.Warehouse));
        assertTrue(_scheduleController.isThereStandByDriverAndWareHouse("testStore", LocalDate.of(1999, 1, 1), ShiftType.MORNING));
    }

}
