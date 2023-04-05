package BussinessLayer.Objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shift{

    private ShiftType _shiftType;
    private int _startHour;
    private int _endHour;
    private int _shiftLength;
    private boolean _approved = false;
    private boolean _rejected = false;
    private LocalDate _date;
    private List<Employee> _inquiredEmployees = new ArrayList<Employee>();
    private List<Employee> _approvedEmployees = new ArrayList<Employee>();
    private List<RoleType> _requiredRoles = new ArrayList<RoleType>();
    private List<RoleType> _filledRoles = new ArrayList<RoleType>();

    public Shift(ShiftType shiftType, int startTime, int endTime, LocalDate date){
        this._shiftType = shiftType;
        this._startHour = startTime;
        this._endHour = endTime;
        this._shiftLength = endTime - startTime;
        this._date = date;
        this._requiredRoles.add(new CashierRole());
        this._requiredRoles.add(new ShiftManagerRole());
        this._requiredRoles.add(new WarehouseRole());
        this._requiredRoles.add(new GeneralRole());
    }

    /**
     * @param startTime - the start time of the shift
     * @return true if the start time was set successfully, false otherwise
     */
    public boolean setStartHour(int startTime) {
        if (startTime < 0 || startTime > 24)
            return false;
        if (startTime > _endHour)
            return false;
        this._startHour = startTime;
        _shiftLength = _endHour - _startHour;
        if (_shiftLength < 0)
            return false;
        return true;
    }

    /**
     * @param endTime - the end time of the shift
     * @return true if the end time was set successfully, false otherwise
     */
    public boolean setEndHour(int endTime) {
        if (endTime < 0 || endTime > 24)
            return false;
        if (endTime < _startHour)
            return false;
        this._endHour = endTime;
        _shiftLength = _endHour - _startHour;
        if (_shiftLength < 0)
            return false;
        return true;
    }

    /**
     * @param employee - the employee to add to the approved employees list
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addInquiredEmployee(Employee employee){
        if (employee == null)
            return false;
        return _inquiredEmployees.add(employee);
    }

    /**
     * @return shift length
     */
    public int getShiftLength() {
        return _shiftLength;
    }

    /**
     * @return the start hour
     */
    public int getStartHour() {
        return _startHour;
    }

    /**
     * @return the end hour
     */
    public int getEndHour() {
        return _endHour;
    }


    public String toString(){
        return "Date: " + _date + ", Shift type: " + _shiftType + ", Date: "+_date+", Start time: " + _startHour + ", End time: " + _endHour;
    }

    /**
     * @param role the role to add to the required roles list
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRole(RoleType role){
        if (role == null)
            return false;
        return _requiredRoles.add(role);
    }

    /**
     * @param role - the role to remove from the required roles list
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRequiredRole(RoleType role){
        if (role == null)
            return false;
        return _requiredRoles.remove(role);
    }

    /**
     * @return boolean if the shift is approved
     */
    public boolean isApproved(){
        return _approved;
    }

    /**
     * @return boolean if the shift is rejected
     */
    public boolean isRejected(){
        return _rejected;
    }

    /**
     * @return the list of required roles
     */
    public List<RoleType> getRequiredRoles(){
        return _requiredRoles;
    }

    /**
     * @return the list of Inquired employees
     */
    public List<Employee> getInquiredEmployees(){
        return _inquiredEmployees;
    }

    /**
     * @param employee - the employee to remove from the inquired employees list
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeInquiredEmployee(Employee employee){
        if (employee == null)
            return false;
        return _inquiredEmployees.remove(employee);
    }

    public boolean addFilledRole(RoleType role, Employee employee){
        if (role == null || employee == null)
            return false;
        if (!removeRequiredRole(role))
            return false;
        if (!removeInquiredEmployee(employee))
            return false;
        if (!role.setEmployee(employee))
            return false;

        return _filledRoles.add(role);
    }

    public boolean setApproved(boolean approved){
        if (approved == true)
            _rejected = false;
        _approved = approved;
        return true;
    }
}