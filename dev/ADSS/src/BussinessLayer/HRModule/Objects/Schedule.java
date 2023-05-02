package BussinessLayer.HRModule.Objects;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public static int scheduleCounter = 0;
    private int _scheduleID;
    private int _storeID;
    private LocalDate _startDateOfWeek;
    private Shift[] _shifts = new Shift[14];

    public Schedule(LocalDate startWeek, int storeID){
        this._startDateOfWeek = startWeek;
        this._storeID = storeID;
        _scheduleID = scheduleCounter++;
        for (int i=0; i<14; i++){
            if (i % 2 == 0)
                _shifts[i] = new Shift(_scheduleID,i, ShiftType.MORNING, 8 , 16,startWeek.plusDays(i));
            else
                _shifts[i] = new Shift(_scheduleID,i, ShiftType.NIGHT, 16 , 24,startWeek.plusDays(i));
        }
    }

    public Schedule(int scheduleID, LocalDate startDateOfWeek, int storeID, List<Shift> shifts){
        this._scheduleID = scheduleID;
        this._startDateOfWeek = startDateOfWeek;
        this._storeID = storeID;
        for (int i=0; i<14; i++){
            _shifts[i] = shifts.get(i);
        }
    }

    /**
     * @param newStartHour - the new start hour of the shift
     * @param newEndHour - the new end hour of the shift
     * @param shiftID - the id of the shift
     * * @return - true if the change was successful, false otherwise
     */
    public boolean changeHoursShift(int newStartHour, int newEndHour, int shiftID){
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

    public Shift[] getShifts() {
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

    public Date getStartDateOfWeek(){
        return Date.valueOf(_startDateOfWeek);
    }

    public int getStoreID(){
        return this._storeID;
    }

}

