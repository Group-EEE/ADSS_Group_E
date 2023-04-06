package BussinessLayer.Controllers;

import BussinessLayer.Objects.Employee;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Schedule;
import BussinessLayer.Objects.Shift;

import java.util.ArrayList;
import java.util.List;

public class ScheduleController {

    private static ScheduleController _scheduleController = null;

    public static ScheduleController getInstance(){
        if (_scheduleController == null)
            _scheduleController = new ScheduleController();
        return _scheduleController;
    }

    /**
     * @param schedule - the schedule to approve
     * @return the list of shifts that were rejected
     */
    public List<Shift> approveSchedule(Schedule schedule){
        if (schedule == null)
            return null;
        List<Shift> rejectedShifts = new ArrayList<Shift>();

        for (int i = 0; i < 14; i++) {
            Shift shift = schedule.getShift(i);
            if (!approveShift(shift))
                rejectedShifts.add(shift);
        }
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
    public boolean approveShift(Shift shift){
        if (shift == null)
            return false;
        if (shift.isApproved() || shift.isRejected()) //shift was already approved or canceled.
            return false;
        //first we assign all the employee that has only one match.
        for (Employee employee : shift.getInquiredEmployees()){
            RoleType role = findMatch(shift, employee);
            if (role != null){
                //we found a match, we remove the employee from the seaching list and add the role to the
                if (!shift.addFilledRole(role, employee))
                    return false;
            }
        }
        for (RoleType role : shift.getRequiredRoles()) {
            if (role.hasEmployee())
                continue;
            for (Employee employee : shift.getInquiredEmployees()) {
                if (employee.getRoles().contains(role)){
                    if (!shift.addFilledRole(role, employee))
                        return false;
                    break;
                }
            }
        }
        if (shift.getRequiredRoles().size() == 0){
            shift.setApproved(true);
        }
        else
            return false;
        return true;
    }

    /**
     * @param shift - the shift to check possible
     * @param employee - the employee to add to the shift
     * @return
     */
    public RoleType findMatch(Shift shift, Employee employee){
        if (shift == null || employee == null)
            return null;
        int counter = 0;
        RoleType lastRole = null;
        for (RoleType Role : employee.getRoles()) {
            if (shift.getRequiredRoles().contains(Role)){
                counter++;
                lastRole = Role;
            }
        }
        if (counter == 1)
            return lastRole;
        return null;
    }

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
