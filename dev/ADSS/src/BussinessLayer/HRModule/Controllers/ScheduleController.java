package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.*;
import DataAccessLayer.HRMoudle.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleController {


    private static ScheduleController _scheduleController = null;
    private SchedulesDAO _schedulesDAO;
    private ShiftsDAO _shiftsDAO;
    private EmployeesDAO _employeesDAO;
    private StoresDAO _storesDAO;

    private ScheduleController(){
        _schedulesDAO = SchedulesDAO.getInstance();
        _shiftsDAO = ShiftsDAO.getInstance();
        _employeesDAO = EmployeesDAO.getInstance();
        _storesDAO = StoresDAO.getInstance();
    }
    public static ScheduleController getInstance(){
        if (_scheduleController == null)
            _scheduleController = new ScheduleController();
        return _scheduleController;
    }

    public boolean createNewSchedule(String storeName, int day, int month, int year){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        if(!_storesDAO.existsStore(storeName))
            throw new IllegalArgumentException("Store does not exist");
        LocalDate localDate;
        try {
            localDate = LocalDate.of(year, month, day);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid date");
        }
        int scheduleID = _schedulesDAO.getScheduleMaxID();
        List<Shift> shifts = new ArrayList<>();
        for (int i=0; i<14; i++){
            if (i % 2 == 0)
                shifts.add(_shiftsDAO.insertShift(scheduleID,i, ShiftType.MORNING.toString(), 8 , 16,localDate.plusDays(i/2)));
            else
                shifts.add(_shiftsDAO.insertShift(scheduleID,i, ShiftType.NIGHT.toString(), 16 , 24,localDate.plusDays(i/2)));

        }
        _storesDAO.insertActiveSchedule(storeName, scheduleID);
        _schedulesDAO.insertSchedule(scheduleID, storeName,localDate);
        Schedule schedule = _schedulesDAO.getSchedule(scheduleID);
        return schedule.setShifts(shifts);
    }

    public boolean deleteScheduleIDFromStore(String storeName){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store ID");
        if (!_storesDAO.existsStore(storeName))
            throw new IllegalArgumentException("Store does not exist");
        return _storesDAO.deleteActive(storeName);
    }

    public boolean deleteSchedule(int scheduleID){
        if (scheduleID < 0)
            throw new IllegalArgumentException("Invalid schedule ID");
        _shiftsDAO.deleteShifts(scheduleID);
        return _schedulesDAO.deleteSchedule(scheduleID);
    }

    public boolean deleteSchedule(String storeName){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid schedule ID");
        int scheduleID = _storesDAO.getActiveSchedule(storeName);
        _storesDAO.deleteActive(storeName);
        _shiftsDAO.deleteShifts(scheduleID);
        return _schedulesDAO.deleteSchedule(scheduleID);
    }

    public Schedule getSchedule(String StoreName){
        if (StoreName == null )
            throw new IllegalArgumentException("Invalid store ID");
        int scheduleID = _storesDAO.getActiveSchedule(StoreName);
        Schedule schedule = _schedulesDAO.getSchedule(scheduleID);
        List<Shift> shifts = _shiftsDAO.getShiftsByScheduleID(scheduleID);
        for (Shift shift : shifts){
            for (String strRole : _shiftsDAO.getRequiredRoles(scheduleID,shift.getShiftID())){
                shift.addRequiredRole(RoleType.valueOf(strRole));
            }
            for (Integer employeeID : _shiftsDAO.getInquireEmployees(scheduleID,shift.getShiftID())){
                shift.addInquiredEmployee(_employeesDAO.getEmployee(employeeID));
            }
            for (Map.Entry<String, Integer> entry : _shiftsDAO.getAssignedEmployees(shift.getScheduleID(),shift.getShiftID()).entrySet()){
                shift.addFilledRole(RoleType.valueOf(entry.getKey()), _employeesDAO.getEmployee(entry.getValue()));
            }
        }
        schedule.setShifts(shifts);
        return schedule;
    }

    public List<Shift> getEmployeeSchedule(Employee employee){
        List<Shift> shiftList = new ArrayList<>();
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee");
        //get all the stores that employee works in
        List<String> storesNames = _storesDAO.getStoreNamesByEmployeeID(employee.getEmployeeID());
        //get all the active schedules for those stores
        for (String storeName :storesNames){
            for (Shift shift : getSchedule(storeName).getShifts()){
                if (shift.hasAssignedEmployee(employee)){
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
        Schedule schedule = getSchedule(storeName);
        if (schedule == null)
            throw new IllegalArgumentException("Invalid store");
        schedule.changeHoursShift(shiftID, newStartHour, newEndHour);
        _shiftsDAO.setStartTime(schedule.getScheduleID(),shiftID, newStartHour);
        _shiftsDAO.setEndTime(schedule.getScheduleID(),shiftID, newEndHour);
        return true;
    }

    public List<Shift> approveSchedule(String storeName){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        Schedule schedule = getSchedule(storeName);
        List<Shift> rejectedShifts = schedule.approveSchedule();
        for (Shift shift : schedule.getShifts()){
            for (HashMap.Entry<RoleType,Employee> entry : shift.getAssignedEmployees().entrySet())
                _shiftsDAO.insertAssignedEmployee(schedule.getScheduleID(),shift.getShiftID(),entry.getKey().toString(),entry.getValue().getEmployeeID());
        }
        return rejectedShifts;
    }


    public boolean removeRoleFromShift(Shift shift, RoleType role){
        if (shift == null || role == null)
            return false;
        _shiftsDAO.removeRequiredRole(shift.getScheduleID(), shift.getShiftID(), role.toString());
        return shift.removeRequiredRole(role);
    }

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
        Schedule schedule = getSchedule(storeName);
        _shiftsDAO.insertRequiredRole(schedule.getScheduleID(),shiftID, role.toString());
        return schedule.addRequiredRoleToShift(shiftID, role);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        Schedule schedule = getSchedule(storeName);
        if (!_shiftsDAO.removeRequiredRole(schedule.getScheduleID(),shiftID, role.toString()))
            return false;
        return schedule.removeRequiredRoleFromShift(shiftID, role);
    }


    public boolean addEmployeeToShift(Employee employee, String storeName, int choice){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        Schedule schedule = getSchedule(storeName);
        if (!_shiftsDAO.insertInquiredEmployee(schedule.getScheduleID(),choice,employee.getEmployeeID()))
            return false;
        return schedule.addEmployeeToShift(employee,choice);
    }

    public boolean hasWareHouse(String storeName, int storeID){
        Schedule schedule = getSchedule(storeName);
        return schedule.getShift(storeID).hasFilledRole(RoleType.Warehouse);
    }
}
