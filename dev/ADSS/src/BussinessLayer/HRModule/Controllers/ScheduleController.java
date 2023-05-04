package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.*;
import DataAccessLayer.HRMoudle.EmployeesToStoreDAO;
import DataAccessLayer.HRMoudle.SchedulesDAO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleController {


    private static ScheduleController _scheduleController = null;
    private final SchedulesDAO _schedulesDAO;
    private final EmployeesToStoreDAO _employeesToStoreDAO;

    private ScheduleController(){
        _schedulesDAO = SchedulesDAO.getInstance();
        _employeesToStoreDAO = EmployeesToStoreDAO.getInstance();
    }
    public static ScheduleController getInstance(){
        if (_scheduleController == null)
            _scheduleController = new ScheduleController();
        return _scheduleController;
    }

    public boolean createNewSchedule(String storeName, int day, int month, int year){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store ID");
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0)
            throw new IllegalArgumentException("Invalid date parameters");
        LocalDate localDate = LocalDate.of(year, month, day);
        int scheduleID = _schedulesDAO.getScheduleMaxID();
        Schedule schedule = new Schedule(scheduleID, localDate, storeName);
        return _schedulesDAO.Insert(schedule);
    }

    public Schedule getSchedule(String StoreName){
        if (StoreName == null )
            throw new IllegalArgumentException("Invalid store ID");
        Schedule schedule = _schedulesDAO.getSchedule(StoreName);
        if (schedule == null)
            throw new IllegalArgumentException("Could not find schedule for this store");
        return schedule;
    }


    public List<Shift> getEmployeeSchedule(Employee employee){
        List<Shift> shiftList = new ArrayList<>();
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee");
        //get all the stores that employee works in
        List<String> storesNames = _employeesToStoreDAO.getStoreNameByEmployeeID(employee.getID());
        //get all the active schedules for those stores
        List<Schedule> schedules = new ArrayList<>();
        for (String storeName :storesNames){
            Schedule schedule = _schedulesDAO.getSchedule(storeName);
            if (schedule != null)
                schedules.add(schedule);
        }
        //get objects schedules by schedules ids
        for (Schedule schedule : schedules){
            for (Shift shift : schedule.getShifts()){
                if (shift.hasEmployee(employee)){
                    shiftList.add(shift);
                }
            }
        }
        return shiftList;
    }

    public boolean changeShiftHours(String storeName, int newStartHour, int newEndHour, int shiftID){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (newStartHour < 0 || newStartHour > 23 || newEndHour < 0 || newEndHour > 23)
            throw new IllegalArgumentException("Invalid hours");
        Schedule schedule = _schedulesDAO.getSchedule(storeName);
        if (schedule == null)
            throw new IllegalArgumentException("Invalid store");
        schedule.getShift(shiftID).setStartHour(newStartHour);
        schedule.getShift(shiftID).setEndHour(newEndHour);
        return true;
    }
    /**
     * @param storeName - the store to approve the schedule for
     * @return the list of shifts that were rejected
     */
    public List<Shift> approveSchedule(String storeName){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        Schedule schedule = _schedulesDAO.getSchedule(storeName);
        if (schedule == null)
            throw new IllegalArgumentException("Schedule not yet created");
        List<Shift> rejectedShifts = schedule.approveSchedule();
        return rejectedShifts;
    }

    /**
     * @param shift - the shift to remove the role from
     * @param role - the role to remove
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRoleFromShift(Shift shift, RoleType role){
        if (shift == null || role == null)
            return false;
        return shift.removeRequiredRole(role);
    }


    /**
     * @param shift - the shift to check possible
     * @return
     */
    public List<RoleType> shiftHasMissingRequiredRole(Shift shift){
        if (shift == null)
            return null;
        return shift.getRequiredRoles();
    }


    /**
     * @param shiftID - the shift to add the required role
     * @param role - the role to add to the shift
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRoleToShift(String storeName, int shiftID, RoleType role){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        Schedule schedule = _schedulesDAO.getSchedule(storeName);
        if (schedule == null)
            throw new IllegalArgumentException("schedule not yet made");
        Shift shift = schedule.getShift(shiftID);
        if (shift == null)
            throw new IllegalArgumentException("Invalid shift");
        return shift.addRequiredRole(role);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        Schedule schedule = _schedulesDAO.getSchedule(storeName);
        if (schedule == null)
            throw new IllegalArgumentException("schedule not yet made");
        Shift shift = schedule.getShift(shiftID);
        if (shift == null)
            throw new IllegalArgumentException("Invalid shift");
        return shift.removeRequiredRole(role);
    }

    public boolean addEmployeeToShift(Employee employee, int storeID, int choice){
        if (storeID < 0 )
            throw new IllegalArgumentException("Invalid store ID");
        Schedule schedule = _schedulesDAO.getSchedule(storeID);
        if (schedule == null)
            throw new IllegalArgumentException("schedule not yet made");
        return schedule.addEmployeeToShift(employee,choice);
    }

    public boolean addEmployeeToShift(Employee employee, String storeName, int choice){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        Schedule schedule = _schedulesDAO.getSchedule(storeName);
        if (schedule == null)
            throw new IllegalArgumentException("schedule not yet made");
        return schedule.addEmployeeToShift(employee,choice);
    }
}
