package serviceLayer.objectsServices;

import BussinessLayer.Controllers.Facade;
import BussinessLayer.Objects.RoleType;
import BussinessLayer.Objects.Shift;

import java.util.List;

public class ScheduleService {

    private final Facade _facade;
    private static ScheduleService _scheduleService;

    private ScheduleService(){
        _facade = Facade.getInstance();
    }

    public static ScheduleService getInstance(){
        if(_scheduleService == null)
            _scheduleService = new ScheduleService();
        return _scheduleService;
    }


    //_ScheduleController
    public boolean createNewSchedule(String StoreName, int day, int month, int year){
        return _facade.createNewSchedule(StoreName, day, month, year);
    }

    public boolean addEmployeeToShift(String storeName, int choice){
        return _facade.addEmployeeToShift(storeName, choice);
    }

    public boolean printSchedule(String storeName){
        return _facade.printSchedule(storeName);
    }

    public List<Shift> approveSchedule(String storeName){
        return _facade.approveSchedule(storeName);
    }

    public boolean changeHoursShift(String storeName, int newStartHour, int newEndHour, int shiftID){
        return _facade.changeHoursShift(storeName, newStartHour, newEndHour, shiftID);
    }

    public boolean addRequiredRoleToShift(String storeName, int shiftID, RoleType role){
        return _facade.addRequiredRoleToShift(storeName, shiftID, role);
    }

    public boolean removeRequiredRoleFromShift(String storeName, int shiftID, RoleType role){
        return _facade.removeRequiredRoleFromShift(storeName, shiftID, role);
    }
}
