package BussinessLayer.Controllers;

import BussinessLayer.Objects.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleController {

    private static ScheduleController _scheduleController = null;
    private HashMap<Store, Schedule> _schedules = new HashMap<Store, Schedule>();

    private ScheduleController(){}
    public static ScheduleController getInstance(){
        if (_scheduleController == null)
            _scheduleController = new ScheduleController();
        return _scheduleController;
    }

    public boolean createNewSchedule(Store store, int day, int month, int year){
        if (store == null)
            throw new IllegalArgumentException("Invalid store");
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0)
            throw new IllegalArgumentException("Invalud date parameters");
        LocalDate localDate = LocalDate.of(year, month, day);
        _schedules.put(store, new Schedule(localDate));
        return true;
    }

    public boolean printSchedule(Store store){
        if (store == null)
            throw new IllegalArgumentException("Invalid store");
        Schedule schedule = _schedules.get(store);
        if (schedule == null)
            throw new IllegalArgumentException("Invalid store");
        for(int i=0; i<schedule.getShifts().length; i++){
            System.out.println(i+schedule.getShift(i).toString());
        }
        return true;
    }

    public boolean changeShiftHours(Store store, int newStartHour, int newEndHour, int shifID){
        if (store == null)
            throw new IllegalArgumentException("Invalid store");
        if (newStartHour < 0 || newStartHour > 23 || newEndHour < 0 || newEndHour > 23)
            throw new IllegalArgumentException("Invalid hours");
        Schedule schedule = _schedules.get(store);
        if (schedule == null)
            throw new IllegalArgumentException("Invalid store");
        schedule.getShift(shifID).setStartHour(newStartHour);
        schedule.getShift(shifID).setEndHour(newEndHour);
        return true;
    }
    /**
     * @param store - the store to approve the schedule for
     * @return the list of shifts that were rejected
     */
    public List<Shift> approveSchedule(Store store){
        if (store == null)
            throw new IllegalArgumentException("Invalid store");
        Schedule schedule = _schedules.get(store);
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
     * @param shift - the shift to approve
     * @return true if the shift was approved successfully, false otherwise
     */


    /**
     * @param shift - the shift to check possible
     * @param employee - the employee to add to the shift
     * @return
     */
    public List<RoleType> shiftHasMissingRequiredRole(Shift shift){
        if (shift == null)
            return null;
        return shift.getRequiredRoles();
    }


    /**
     * @param shift - the shift to add the required role
     * @param role - the role to add to the shift
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRoleToShift(Shift shift, RoleType role){
        if (shift == null || role == null)
            return false;
        return shift.addRequiredRole(role);
    }
}
