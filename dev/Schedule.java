import java.time.LocalDate;

public class Schedule {
    private LocalDate _startDateOfWeek;
    private Shift[] _shifts = new Shift[14];

    public Schedule(LocalDate startWeek) {
        this._startDateOfWeek = startWeek;
        for (int i=0; i<14; i++){
            if (i % 2 == 0)
                _shifts[i] = new Shift(ShiftType.MORNING, 8 , 16,startWeek.plusDays(i));
            else
                _shifts[i] = new Shift(ShiftType.NIGHT, 16 , 24,startWeek.plusDays(i));
        }
    }

    /**
     * @param newStartHour - the new start hour of the shift
     * @param newEndHour - the new end hour of the shift
     * @param shiftId - the id of the shift
     * * @return - true if the change was successful, false otherwise
     */
    public boolean changeHoursShift(int newStartHour, int newEndHour, int shiftId){
        if (shiftId < 0 || shiftId > 13 || newEndHour > 24 || newStartHour <0)
            return false;
        if (!_shifts[shiftId].setStartHour(newStartHour))
            return false;
        if (!_shifts[shiftId].setEndHour(newEndHour))
            return false;
        return true;
    }

    /**
     * @param shiftId - the id of the shift
     * @return - the shift with the given id, null if the id is invalid
     */
    public Shift getShift(int shiftId){
        if (shiftId < 0 || shiftId > 13)
            return null;
        return _shifts[shiftId];
    }
}