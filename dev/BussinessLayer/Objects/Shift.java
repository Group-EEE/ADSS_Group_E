package BussinessLayer.Objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shift{

    private ShiftType _shiftType;
    private int _startHour;
    private int _endHour;
    private int _shiftLength;
    private boolean _approved;
    private boolean _rejected;
    private LocalDate _date;
    private List<Employee> _inquiredEmployees;
    private HashMap<RoleType,Employee> _assignedEmployees;
    private List<RoleType> _requiredRoles;
    private List<RoleType> _filledRoles;

    public Shift(ShiftType shiftType, int startTime, int endTime, LocalDate date){
        this._assignedEmployees = new HashMap<RoleType,Employee>();
        this._inquiredEmployees = new ArrayList<Employee>();
        this._requiredRoles = new ArrayList<RoleType>();
        this._filledRoles = new ArrayList<RoleType>();
        this._approved = false;
        this._rejected = false;
        this._shiftType = shiftType;
        this._startHour = startTime;
        this._endHour = endTime;
        this._shiftLength = endTime - startTime;
        this._date = date;
        this._requiredRoles.add(RoleType.Cashier);
        this._requiredRoles.add(RoleType.ShiftManager);
        this._requiredRoles.add(RoleType.Warehouse);
        this._requiredRoles.add(RoleType.General);
    }

    /**
     * @param startTime - the start time of the shift
     * @return true if the start time was set successfully, false otherwise
     */
    public boolean setStartHour(int startTime) {
        if (startTime < 0 || startTime > 24)
            throw new IllegalArgumentException("Start time must be between 0 and 24");
        if (startTime > _endHour)
            throw new IllegalArgumentException("Start time cannot be after end time");
        if (_endHour - _startHour < 0) {
            throw new IllegalArgumentException("Shift length cannot be negative");
        }
        _shiftLength = _endHour - _startHour;
        this._startHour = startTime;
        return true;
    }

    /**
     * @param endTime - the end time of the shift
     * @return true if the end time was set successfully, false otherwise
     */
    public boolean setEndHour(int endTime) {
        if (endTime < 0 || endTime > 24)
            throw new IllegalArgumentException("End time must be between 0 and 24");
        if (endTime < _startHour)
            throw new IllegalArgumentException("End time cannot be before start time");
        if (_endHour - _startHour < 0)
            throw new IllegalArgumentException("Shift length cannot be negative");
        this._endHour = endTime;
        _shiftLength = _endHour - _startHour;
        return true;
    }

    /**
     * @param employee - the employee to add to the approved employees list
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addInquiredEmployee(Employee employee){
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null");
        return _inquiredEmployees.add(employee);
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
            throw new IllegalArgumentException("Invalid role");
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

        return _filledRoles.add(role);
    }

    public boolean setApproved(boolean approved){
        if (approved == true)
            _rejected = false;
        _approved = approved;
        return true;
    }

    public boolean approveShift(){
        if (_approved || _rejected) //shift was already approved or canceled.
            return false;
        //first we assign all the employee that has only one match.
        for (Employee employee : _inquiredEmployees){
            RoleType role = findMatch(employee);
            if (role != null){
                //we found a match, we remove the employee from the seaching list and add the role to the
                addFilledRole(role, employee);
            }
        }
        for (RoleType role : _requiredRoles) {
            for (Employee employee : _inquiredEmployees) {
                if (employee.getRoles().contains(role)){
                    if (!addFilledRole(role, employee))
                        return false;
                    break;
                }
            }
        }
        if (_requiredRoles.size() == 0){
            setApproved(true);
        }
        else
            return false;
        return true;
    }

    public RoleType findMatch(Employee employee){
        if (employee == null)
            throw new IllegalArgumentException("Invalid Employee");
        int counter = 0;
        RoleType lastRole = null;
        for (RoleType Role : employee.getRoles()) {
            if (_requiredRoles.contains(Role)){
                counter++;
                lastRole = Role;
            }
        }
        if (counter == 1)
            return lastRole;
        return null;
    }
}