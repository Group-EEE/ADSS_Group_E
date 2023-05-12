package HR.DAO;

import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.HRMoudle.ShiftsDAO;
import DataAccessLayer.HRMoudle.StoresDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShiftsDAOTest {

    static ShiftsDAO _shiftsDAO;

    @BeforeAll
    static void setUpAll() {
        _shiftsDAO = ShiftsDAO.getInstance();
    }

    @BeforeEach
    void setUp() {
        _shiftsDAO.insertShift(9999, 0, ShiftType.NIGHT.toString(), 8, 16, LocalDate.of(2020,12,20));
        _shiftsDAO.insertShift(9999, 1, ShiftType.NIGHT.toString(), 8, 16, LocalDate.of(2020,12,20));
    }

    @AfterEach
    void tearDown() {
        _shiftsDAO.deleteShift(9999,0);
        _shiftsDAO.deleteShift(9999,1);
    }


    @Test
    void deleteShifts(){
        assertTrue(_shiftsDAO.deleteShifts(9999));
    }

    @Test
    void insertAndDeleteRequiredRole(){
        assertTrue(_shiftsDAO.insertRequiredRole(9999,0,"testRole",false));
        assertTrue(_shiftsDAO.removeRequiredRole(9999,0,"testRole"));
    }

    @Test
    void insertAndDeleteInquiredEmployee(){
        assertTrue(_shiftsDAO.insertInquiredEmployee(9999,0,9999));
        assertTrue(_shiftsDAO.insertInquiredEmployee(9999,0,10000));
        assertTrue(_shiftsDAO.deleteInquiredEmployees(9999,0));
    }

    @Test
    void setStartTime(){
        assertTrue(_shiftsDAO.setStartTime(9999,0,9));
    }

    @Test
    void setEndTime(){
        assertTrue(_shiftsDAO.setEndTime(9999,0,17));
    }

    @Test
    void getShiftsByScheduleID(){
        assertTrue(_shiftsDAO.getShiftsByScheduleID(9999).size() == 2);
    }

    @Test
    void getInquireEmployees(){
        assertEquals(_shiftsDAO.getInquireEmployees(9999,0).size(),0);
        assertTrue(_shiftsDAO.insertInquiredEmployee(9999,0,9999));
        assertTrue(_shiftsDAO.insertInquiredEmployee(9999,0,10000));
        assertEquals(_shiftsDAO.getInquireEmployees(9999,0).size(),2);
        assertTrue(_shiftsDAO.deleteInquiredEmployees(9999,0));
    }




}
