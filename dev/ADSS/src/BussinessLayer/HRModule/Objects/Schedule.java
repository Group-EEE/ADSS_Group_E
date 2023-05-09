package BussinessLayer.HRModule.Objects;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private int _scheduleID;
    private String _storeName;
    private LocalDate _startDateOfWeek;
    private List<Shift> _shifts;

    public Schedule(int scheduleID, String storeName, LocalDate startDateOfWeek){
        this._scheduleID = scheduleID;
        this._storeName = storeName;
        this._startDateOfWeek = startDateOfWeek;
    }

    //setters
    public boolean setShifts(List<Shift> shifts){
        if (shifts == null)
            return false;
        this._shifts = shifts;
        return true;
    }

    //getters
    /**
     * @param shiftID - the id of the shift
     * @return - the shift with the given id, null if the id is invalid
     */
    public Shift getShift(int shiftID){
        if (shiftID < 0 || shiftID > 13)
            return null;
        return _shifts.get(shiftID);
    }

    public List<Shift> getShifts(){
        return _shifts;
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

    //methods

    public boolean addRequiredRoleToShift(int shiftID, RoleType roleType){
        if (_shifts == null)
            throw new IllegalArgumentException("shifts haven't been initialized");
        if (shiftID < 0 || shiftID > 13)
            return false;
        return _shifts.get(shiftID).addRequiredRole(roleType);
    }

    public boolean removeRequiredRoleFromShift(int shiftID, RoleType roleType){
        if (_shifts == null)
            throw new IllegalArgumentException("shifts haven't been initialized");
        if (shiftID < 0 || shiftID > 13)
            return false;
        return _shifts.get(shiftID).removeRequiredRole(roleType);
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
        return _shifts.get(shiftID).setStartHour(newStartHour) && _shifts.get(shiftID).setEndHour(newEndHour);
    }

    public List<Shift> approveSchedule(){
        List<Shift> rejectedShifts = new ArrayList<Shift>();
        for (int i = 0; i < 14; i++) {
            Shift shift = _shifts.get(i);
            if (!shift.approveShift())
                rejectedShifts.add(shift);
        }
        return rejectedShifts;
    }

    public boolean addEmployeeToShift(Employee employee, int shiftID){
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null");
        if (shiftID < 0 || shiftID > 13)
            throw new IllegalArgumentException("Invalid shiftID");
        return _shifts.get(shiftID).addInquiredEmployee(employee);
    }

    public String toString() {
        String output = "Schedule ID: " + _scheduleID + ", Store name: " + _storeName + ", Start date of week: " + _startDateOfWeek;
        if (_shifts == null)
            return output;
        output += ", Shifts: \n";
        for (Shift shift : _shifts) {
            output += shift.toString() + "\n";
        }
        return output.substring(0, output.length() - 1);
    }

}

