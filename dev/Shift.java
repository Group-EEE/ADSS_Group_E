import Roles.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shift{

    private ShiftType _shiftType;
    private int _startTime;
    private int _endTime;
    private int _shiftLength;
    private boolean _approved = false;
    private LocalDate _date;
    private List<Employee> _employees = new ArrayList<Employee>();
    private List<IRole> _required_roles = new ArrayList<IRole>();

    public Shift(ShiftType shiftType, int startTime, int endTime, LocalDate date){
        this._shiftType = shiftType;
        this._startTime = startTime;
        this._endTime = endTime;
        this._shiftLength = endTime - startTime;
        this._date = date;
        this._required_roles.add(new CashierRole());
        this._required_roles.add(new ShiftManagerRole());
        this._required_roles.add(new WarehouseRole());
        this._required_roles.add(new GeneralRole());
    }
    public boolean setStartHour(int startTime) {
        if (startTime < 0 || startTime > 24)
            return false;
        this._startTime = startTime;
        _shiftLength = _endTime - _startTime;
        return true;
    }
    public boolean setEndHour(int endTime) {
        if (endTime < 0 || endTime > 24)
            return false;
        this._endTime = endTime;
        _shiftLength = _endTime - _startTime;
        return true;
    }
    public boolean addEmployee(Employee employee){
        if (employee == null)
            return false;
        _employees.add(employee);
        return true;
    }
    public int getShiftLength() {
        return _shiftLength;
    }
    public int getStartHour() {
        return _startTime;
    }
    public int getEndHour() {
        return _endTime;
    }
    public String toString(){
        return "Date: " + _date + ", Shift type: " + _shiftType + ", Date: "+_date+", Start time: " + _startTime + ", End time: " + _endTime;
    }

    /**
     * @param role the role to add to the required roles list
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRole(IRole role){
        if (role == null)
            return false;
        _required_roles.add(role);
        return true;
    }

    /**
     * @param role - the role to remove from the required roles list
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRequiredRole(IRole role){
        if (role == null)
            return false;
        _required_roles.remove(role);
        return true;
    }

}