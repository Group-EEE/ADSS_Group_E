package BussinessLayer.HRModule.Controllers;

import BussinessLayer.HRModule.Objects.*;
import BussinessLayer.TransportationModule.controllers.Logistical_center_controller;
import DataAccessLayer.HRMoudle.*;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleController {


    private static ScheduleController _scheduleController = null;
    private final SchedulesDAO _schedulesDAO;
    private final ShiftsDAO _shiftsDAO;
    private final EmployeesDAO _employeesDAO;
    private final StoresDAO _storesDAO;

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

    public boolean createNewStoreSchedule(String storeName, int day, int month, int year){
        //per gal's request, delete all transport for the past week
        if (hasSchedule(storeName,day,month,year)){
            //first get list of transports
            //Logistical_center_controller.getInstance().deleteTransports(LocalDate.of(day,month,year).minus(7, ChronoUnit.DAYS));
        }
        return createNewSchedule(storeName,day,month,year, List.of(RoleType.ShiftManager, RoleType.Cashier, RoleType.General,RoleType.Warehouse),List.of(RoleType.ShiftManager));
    }
    public boolean createNewLogisticsSchedule(int day, int month, int year){
        return createNewSchedule("Logistics",day,month,year, List.of(RoleType.Driver),List.of(RoleType.Driver));
    }

    public boolean createNewSchedule(String storeName, int day, int month, int year,List<RoleType> requiredRoles,List<RoleType> rolesMustBeFilled){
        if (storeName == null || storeName.equals(""))
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
        Shift shift;
        for (int i=0; i<14; i++){
            if (i % 2 == 0)
                shift = _shiftsDAO.insertShift(scheduleID,i, ShiftType.MORNING.toString(), 8 , 16,localDate.plusDays(i/2));
            else
                shift =_shiftsDAO.insertShift(scheduleID,i, ShiftType.NIGHT.toString(), 16 , 24,localDate.plusDays(i/2));
            shifts.add(shift);
            shift.setRequiredRoles(requiredRoles);
            shift.setRolesMustBeFilled(rolesMustBeFilled);
            for (RoleType role : shift.getRequiredRoles())
                _shiftsDAO.insertRequiredRole(shift.getScheduleID(),shift.getShiftID(),role.toString(), rolesMustBeFilled.contains(role));
        }
        if (hasSchedule(storeName)) {
            _storesDAO.deleteActive(storeName);
        }
        _storesDAO.insertActiveSchedule(storeName, scheduleID);
        _schedulesDAO.insertSchedule(scheduleID, storeName,localDate);
        Schedule schedule = _schedulesDAO.getSchedule(scheduleID);
        return schedule.setShifts(shifts);
    }
//    public boolean deleteScheduleIDFromStore(String storeName){
//        if (storeName == null)
//            throw new IllegalArgumentException("Invalid store ID");
//        if (!_storesDAO.existsStore(storeName))
//            throw new IllegalArgumentException("Store does not exist");
//        return _storesDAO.deleteActive(storeName);
//    }

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
        boolean res = _storesDAO.deleteActive(storeName);
        res = res && _shiftsDAO.deleteShifts(scheduleID);
        return res && _schedulesDAO.deleteSchedule(scheduleID);
    }

    public Schedule getSchedule(String StoreName){
        if (StoreName == null )
            throw new IllegalArgumentException("Invalid store ID");
        int scheduleID = _storesDAO.getActiveSchedule(StoreName);
        if (_schedulesDAO.hasScheduleInCache(scheduleID))
            return _schedulesDAO.getSchedule(scheduleID);
        Schedule schedule = _schedulesDAO.getSchedule(scheduleID);
        List<Shift> shifts = _shiftsDAO.getShiftsByScheduleID(scheduleID);
        for (Shift shift : shifts){
            for (String strRole : _shiftsDAO.getMustBeFilledRole(scheduleID,shift.getShiftID())){
                shift.addMustBeFilledRole(RoleType.valueOf(strRole));
            }
            for (String strRole : _shiftsDAO.getRequiredRoles(scheduleID,shift.getShiftID())){
                shift.addRequiredRole(RoleType.valueOf(strRole));
            }
            for (Integer employeeID : _shiftsDAO.getInquireEmployees(scheduleID,shift.getShiftID())){
                shift.addInquiredEmployee(_employeesDAO.getEmployee(employeeID));
            }
            for (Map.Entry<String, Integer> entry : _shiftsDAO.getAssignedEmployees(shift.getScheduleID(),shift.getShiftID()).entrySet()){
                try {
                    shift.addFilledRole(RoleType.valueOf(entry.getKey()), _employeesDAO.getEmployee(entry.getValue()));
                }
                catch (Exception ex){
                    System.out.println("This code is bad.");
                }

            }
            shift.setRejected(_shiftsDAO.getRejected(shift.getScheduleID(),shift.getShiftID()));
            shift.setApproved(_shiftsDAO.getApproved(shift.getScheduleID(),shift.getShiftID()));
        }
        schedule.setShifts(shifts);
        return schedule;
    }

    public Schedule get_schedule_object_only(String StoreName){
        int scheduleID = _storesDAO.getActiveSchedule(StoreName);
        return  _schedulesDAO.getSchedule(scheduleID);
    }

    public List<Shift> getEmployeeSchedule(Employee employee){
        List<Shift> shiftList = new ArrayList<>();
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
        if (storeName == null || storeName.equals("")) {
            System.out.println("Invalid store name");
            return false;
        }
        if (shiftID < 0 || shiftID >13 ){
            System.out.println("Invalid shift ID");
            return false;
        }
        if (newStartHour < 0 || newStartHour > 24 || newEndHour < 0 || newEndHour > 24){
            System.out.println("Invalid hours");
            return false;
        }
        Schedule schedule = getSchedule(storeName);
        boolean res = schedule.changeHoursShift(newStartHour, newEndHour, shiftID);
        res = res && _shiftsDAO.setStartTime(schedule.getScheduleID(),shiftID, newStartHour);
        res = res && _shiftsDAO.setEndTime(schedule.getScheduleID(),shiftID, newEndHour);
        return res;
    }

    public List<Shift> approveSchedule(String storeName){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        Schedule schedule = getSchedule(storeName);
        List<Shift> rejectedShifts = schedule.approveSchedule();
        for (Shift shift : schedule.getShifts()){
            if (shift.isRejected())
                _shiftsDAO.updateRejected(shift.getScheduleID(),shift.getShiftID(),true);
            if (shift.isApproved())
                _shiftsDAO.updateApproved(shift.getScheduleID(),shift.getShiftID(),true);
            for (HashMap.Entry<RoleType,Employee> entry : shift.getAssignedEmployees().entrySet()) {
                _shiftsDAO.insertAssignedEmployee(schedule.getScheduleID(), shift.getShiftID(), entry.getKey().toString(), entry.getValue().getEmployeeID());
                //_shiftsDAO.removeRequiredRole(schedule.getScheduleID(), shift.getShiftID(), entry.getKey().toString());
                //_shiftsDAO.deleteInquiredEmployee(schedule.getScheduleID(), shift.getShiftID(), entry.getValue().getEmployeeID());
            }
        }
        return rejectedShifts;
    }


//    public List<RoleType> shiftHasMissingRequiredRole(Shift shift){
//        if (shift == null)
//            throw new IllegalArgumentException("Invalid shift");
//        return shift.getRequiredRoles();
//    }


    /**
     * @param shiftID - the shift to add the required role
     * @param role - the role to add to the shift
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRoleToShift(String storeName, int shiftID, RoleType role,boolean mustBeFilled){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        if (shiftID < 0 || shiftID >13 )
            throw new IllegalArgumentException("Invalid shift ID");
        Schedule schedule = getSchedule(storeName);
        boolean res = _shiftsDAO.insertRequiredRole(schedule.getScheduleID(),shiftID, role.toString(),mustBeFilled);
        return res && schedule.addRequiredRoleToShift(shiftID, role);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        Schedule schedule = getSchedule(storeName);
        boolean res = _shiftsDAO.removeRequiredRole(schedule.getScheduleID(),shiftID, role.toString());
        return res && schedule.removeRequiredRoleFromShift(shiftID, role);
    }

    public boolean addDriverToLogisticsShift(int employeeID, int shiftID){
        //boolean res = addRequiredRoleToShift("Logistics",shiftID,RoleType.Driver,true);
        boolean res = addEmployeeToShift(_employeesDAO.getEmployee(employeeID),"Logistics",shiftID);
        return res && addFilledRoleToShift("Logistics",employeeID,shiftID,RoleType.Driver);
    }

    public boolean addStandByDriverToLogisticsShift(int employeeID, int shiftID){
        boolean res = addEmployeeToShift(_employeesDAO.getEmployee(employeeID),"Logistics",shiftID);
        res = res && addRequiredRoleToShift("Logistics",shiftID,RoleType.DriverStandBy,false);
        return res && addFilledRoleToShift("Logistics",employeeID,shiftID,RoleType.DriverStandBy);
    }

    public boolean addFilledRoleToShift(String storeName, int employeeID, int shiftID,RoleType role){
        Employee employee = _employeesDAO.getEmployee(employeeID);
        Schedule schedule = getSchedule(storeName);
        boolean res = schedule.getShift(shiftID).addFilledRole(role,employee);
        return res && _shiftsDAO.insertAssignedEmployee(schedule.getScheduleID(),shiftID,role.toString(),employeeID);
    }


    public boolean addMustBeFilledWareHouse(String storeName, int shiftID){
        return addMustBeFilledRole(storeName,shiftID,RoleType.Warehouse);
    }
    public boolean addMustBeFilledRole(String storeName, int shiftID, RoleType role){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        Schedule schedule = getSchedule(storeName);
        boolean res = _shiftsDAO.updateMustBeFilledRole(schedule.getScheduleID(),shiftID, role.toString(),true);
        List<Shift> shifts = ShiftsDAO.getInstance().getShiftsByScheduleID(schedule.getScheduleID());
        Shift shift = null;
        for (Shift s : shifts){
            if (s.getShiftID() == shiftID){
                shift = s;
                break;
            }
        }
        return res && shift.addMustBeFilledRole(role);
    }

    //TODO: shiftID getShiftIdByDate(String storeName, Date currentDate,ShiftType shiftType
    public int getShiftIDByDate(String storeName, LocalDate currentDate,ShiftType shiftType){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (currentDate == null)
            throw new IllegalArgumentException("Invalid date");
        if (shiftType == null)
            throw new IllegalArgumentException("Invalid shift type");
        Schedule schedule = getSchedule(storeName);
        //Schedule schedule = get_schedule_object_only(storeName);
        long days = ChronoUnit.DAYS.between(schedule.getStartDateOfWeek(), currentDate);
        if (days > 7)
            throw new IllegalArgumentException("Invalid date");
        if (shiftType == ShiftType.MORNING)
            return (int)days*2;
        else
            return (int)days*2 + 1;
    }




    public boolean isThereStandByDriverAndWareHouse(String storeName,LocalDate localDate, ShiftType shiftType){
        if (storeName == null)
            throw new IllegalArgumentException("Invalid store name");
        if (localDate == null)
            throw new IllegalArgumentException("Invalid date");
        if (shiftType == null)
            throw new IllegalArgumentException("Invalid shift type");

        int shiftIDStore = getShiftIDByDate(storeName,localDate,shiftType);
        int shiftIDLogistics = getShiftIDByDate("Logistics",localDate,shiftType);

        Schedule scheduleStore = getSchedule(storeName);
        Schedule scheduleLogistics = getSchedule("Logistics");

        Shift shiftStore = scheduleStore.getShift(shiftIDStore);
        Shift shiftLogistics = scheduleLogistics.getShift(shiftIDLogistics);

        return shiftLogistics.hasFilledRole(RoleType.DriverStandBy) && shiftStore.hasFilledRole(RoleType.Warehouse);
    }
    public boolean addEmployeeToShift(Employee employee, String storeName, int shiftID){
        if (storeName == null )
            throw new IllegalArgumentException("Invalid store name");
        if (shiftID < 0 || shiftID >13 )
            throw new IllegalArgumentException("Invalid shift ID");
        Schedule schedule = getSchedule(storeName);
        boolean res = _shiftsDAO.insertInquiredEmployee(schedule.getScheduleID(),shiftID,employee.getEmployeeID());
        return res && schedule.addEmployeeToShift(employee,shiftID);
    }

    public boolean hasSchedule(String storeName){
        try{
            _storesDAO.getActiveSchedule(storeName);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public boolean hasWareHouse(String storeName, int shiftID){
        Schedule schedule = getSchedule(storeName);
        List<Shift> shifts = ShiftsDAO.getInstance().getShiftsByScheduleID(schedule.getScheduleID());
        Shift shift = null;
        for (Shift s : shifts){
            if (s.getShiftID() == shiftID){
                shift = s;
            }
        }
        return shift.hasFilledRole(RoleType.Warehouse);
    }

    public boolean hasStandByDriver(String storeName, int shiftID){
        Schedule schedule = getSchedule(storeName);
        return schedule.getShift(shiftID).hasFilledRole(RoleType.DriverStandBy);
    }

    /**
     * iterates between 7 past days and checks if there is a schedule for the store in one of them
     * @param storeName
     * @param day
     * @param month
     * @param year
     * @return
     */
    public boolean hasSchedule(String storeName, int day, int month, int year){
        for (int i = 0; i < 7; i++) {
            try{
        _schedulesDAO.getSchedule(LocalDate.of(year,month,day).minus(i,ChronoUnit.DAYS),storeName);
        return true;
            }catch (IllegalArgumentException e){
               continue;
            }
        }
        return false;
    }


}
