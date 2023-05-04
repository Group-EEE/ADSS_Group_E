package BussinessLayer.HRModule.Objects;

import DataAccessLayer.HRMoudle.SchedulesDAO;
import DataAccessLayer.HRMoudle.ShiftsDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Schedule {
    private int _scheduleID;
    private String _storeName;
    private LocalDate _startDateOfWeek;
    private Shift[] _shifts;


    public Schedule(int scheduleID, LocalDate startDateOfWeek, String storeName){
        this._scheduleID = scheduleID;
        this._startDateOfWeek = startDateOfWeek;
        this._storeName = storeName;
    }

    public boolean addShifts(List<Shift> shifts){
        for (int i=0; i<14; i++){
            _shifts[i] = shifts.get(i);
        }
        return true; //TODO
    }

    /**
     * @param newStartHour - the new start hour of the shift
     * @param newEndHour - the new end hour of the shift
     * @param shiftID - the id of the shift
     * * @return - true if the change was successful, false otherwise
     */
    public boolean changeHoursShift(int newStartHour, int newEndHour, int shiftID){
        if (_shifts == null)
            throw new IllegalArgumentException("shifts haven't been initialized"); //TODO:

        if (shiftID < 0 || shiftID > 13 || newEndHour > 24 || newStartHour <0)
            return false;
        if (!_shifts[shiftID].setStartHour(newStartHour))
            return false;
        if (!_shifts[shiftID].setEndHour(newEndHour))
            return false;
        return true;
    }

    /**
     * @param shiftID - the id of the shift
     * @return - the shift with the given id, null if the id is invalid
     */
    public Shift getShift(int shiftID){
        if (shiftID < 0 || shiftID > 13)
            return null;
        return _shifts[shiftID];
    }

    public List<Shift> approveSchedule(){
        List<Shift> rejectedShifts = new ArrayList<Shift>();
        for (int i = 0; i < 14; i++) {
            Shift shift = _shifts[i];
            if (!shift.approveShift())
                rejectedShifts.add(shift);
        }
        return rejectedShifts;
    }

    public Shift[] getShifts(){
        return _shifts;
    }

    public boolean addEmployeeToShift(Employee employee, int choice){
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null");
        if (choice < 0 || choice > 13)
            throw new IllegalArgumentException("Invalid choice");
        return _shifts[choice].addInquiredEmployee(employee);
    }

    public int getScheduleID(){
        return this._scheduleID;
    }

    public LocalDate getStartDateOfWeek(){
        return _startDateOfWeek;
    }

    public String getStoreName(){
        return this._storeName;
    }

    public String toString(){
        String output="";
        for (Shift shift : _shifts) {
            output += shift.toString()+"\n";
        }
        return output;
    }
}

