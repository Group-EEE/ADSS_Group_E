package BussinessLayer.HRModule.Objects;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shift{
    private final int _scheduleID;
    private final int _shiftID;
    private final ShiftType _shiftType;
    private int _startHour;
    private int _endHour;
    private int _shiftLength;
    private boolean _approved;
    private boolean _rejected;
    private final LocalDate _date;
    private List<Employee> _inquiredEmployees;
    private HashMap<RoleType,Employee> _assignedEmployees;
    private List<RoleType> _requiredRoles;
    //roles the needs to be filled in the shift
    private final List<RoleType> _filledRoles;
    private List<RoleType> _rolesMustBeFilled;
    //if one of the roles inside aren't filled at the end of shift approval, the shift will be rejected.
    public Shift(int scheduleID, int shiftID, ShiftType shiftType, int startHour, int endHour, LocalDate date){
        this._scheduleID = scheduleID;
        this._shiftID = shiftID;
        this._assignedEmployees = new HashMap<>();
        this._inquiredEmployees = new ArrayList<>();
        this._requiredRoles = new ArrayList<>();
        this._rolesMustBeFilled = new ArrayList<>();
        this._filledRoles = new ArrayList<>();
        this._approved = false;
        this._rejected = false;
        this._shiftType = shiftType;
        this._startHour = startHour;
        this._endHour = endHour;
        this._shiftLength = endHour - startHour;
        this._date = date;
    }

    //setters

    /**
     * @param startHour - the start time of the shift
     * @return true if the start time was set successfully, false otherwise
     */
    public boolean setStartHour(int startHour) {
        if (startHour < 0 || startHour > 24)
            throw new IllegalArgumentException("Start time must be between 0 and 24");
        if (startHour > _endHour)
            throw new IllegalArgumentException("Start time cannot be after end time");
        _shiftLength = _endHour - _startHour;
        this._startHour = startHour;
        return true;
    }

    /**
     * @param endHour - the end time of the shift
     * @return true if the end time was set successfully, false otherwise
     */
    public boolean setEndHour(int endHour) {
        if (endHour < 0 || endHour > 24)
            throw new IllegalArgumentException("End time must be between 0 and 24");
        if (endHour < _startHour)
            throw new IllegalArgumentException("End time cannot be before start time");
        this._endHour = endHour;
        _shiftLength = _endHour - _startHour;
        return true;
    }

    public boolean setRequiredRoles(List<RoleType> roles){
        _requiredRoles.clear();
        if (roles == null)
            throw new IllegalArgumentException("Invalid roles");
        _requiredRoles.addAll(roles);
        return true;
    }

    public boolean setInquiredEmployees(List<Employee> employees){
        if (employees == null)
            throw new IllegalArgumentException("Invalid employees");
        if (_inquiredEmployees.size() != 0)
            throw new IllegalArgumentException("Inquired employees already set");
        _inquiredEmployees = employees;
        return true;
    }

    public boolean setAssignedEmployees(HashMap<RoleType,Employee> assignedEmployees){
        if (assignedEmployees == null)
            throw new IllegalArgumentException("Invalid employees");
        if (_assignedEmployees.size() != 0)
            throw new IllegalArgumentException("Assigned employees already set");
        _assignedEmployees = assignedEmployees;
        return true;
    }

    public boolean setApproved(boolean approved){
        if (approved)
            _rejected = false;
        _approved = approved;
        return true;
    }

    public boolean setRejected(boolean rejected){
        if (rejected)
            _approved = false;
        _rejected = rejected;
        return true;
    }

    public boolean setRolesMustBeFilled(List<RoleType> roles){
        _rolesMustBeFilled.clear();
        if (roles == null)
            throw new IllegalArgumentException("Invalid roles");
        _rolesMustBeFilled.addAll(roles);
        return true;
    }

    //getters

    public int getScheduleID(){
        return _scheduleID;
    }

    public int getShiftID(){
        return _shiftID;
    }

    public ShiftType getShiftType(){
        return _shiftType;
    }

    public int getStartHour(){
        return _startHour;
    }

    public int getEndHour(){
        return _endHour;
    }

    public LocalDate getDate() {
        return _date;
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

    public HashMap<RoleType,Employee> getAssignedEmployees(){
        return _assignedEmployees;
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


    public boolean addFilledRole(RoleType role, Employee employee){
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee");
        if (_assignedEmployees.containsKey(role))
            throw new IllegalArgumentException("Role already filled");
        if (!_inquiredEmployees.contains(employee))
            throw new IllegalArgumentException("Employee wasn't inquired");
        if (!_requiredRoles.contains(role))
            throw new IllegalArgumentException("The role isn't required for this shift");
        if (!employee.hasRole(role))
            throw new IllegalArgumentException("Employee doesn't have this role");
        if (!removeRequiredRole(role) || !removeInquiredEmployee(employee))
            return false;
        _requiredRoles.remove(role);
        _assignedEmployees.put(role, employee);
        return _filledRoles.add(role);
    }


    public boolean approveShift(){
        if (_approved || _rejected) //shift was already approved or canceled.
            return false;
        //first we assign all the employee that has only one match.
        int iter=0;
        int jter=0;
        while(_inquiredEmployees.size() != 0 && iter < _inquiredEmployees.size()){
            Employee employee = _inquiredEmployees.get(iter);
            RoleType role = findMatch(employee);
            if (role != null)
                //we found a match, we remove the employee from the searching list and add the role to the
            {
                addFilledRole(role, employee);
            }
            else {
                iter++;
            }
        }
        iter =0;
        while(_requiredRoles.size() != 0 && iter <_requiredRoles.size() && _inquiredEmployees.size() != 0 && jter < _inquiredEmployees.size()){
            while(_inquiredEmployees.size() != 0 && jter < _inquiredEmployees.size()){
                Employee employee = _inquiredEmployees.get(jter);
                RoleType role = _requiredRoles.get(iter);
                if (employee.getRoles().contains(role)) {
                    if (addFilledRole(role, employee))
                        jter++; //filled the role, we can move to the next employee.
                }
                else
                    iter++;
                if (iter == employee.getRoles().size()) //finished all the roles of the such employee.
                    jter++;
            }
        }
        if (_requiredRoles.size() == 0)
            return setApproved(true);
        for(RoleType role : _requiredRoles){
            if (_rolesMustBeFilled.contains(role))
                setRejected(true);
        }
        return false;
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

    public boolean hasAssignedEmployee(Employee employee){
        if (employee == null)
            throw new IllegalArgumentException("Invalid Employee");
        for (Map.Entry<RoleType,Employee> entry : _assignedEmployees.entrySet()){
            if (entry.getValue() == employee)
                return true;
        }
        return false;
    }


    //adders
    /**
     * @param employee - the employee to add to the approved employees list
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addInquiredEmployee(Employee employee){
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null");
        if (_inquiredEmployees.contains(employee))
            throw new IllegalArgumentException("Employee already inquired");
        return _inquiredEmployees.add(employee);
    }

    /**
     * @param role the role to add to the required roles list
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addRequiredRole(RoleType role){
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        if (_requiredRoles.contains(role))
            return false;
        return _requiredRoles.add(role);
    }

    public boolean addMustBeFilledRole(RoleType role){
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        return _rolesMustBeFilled.add(role);
    }

    //removers
    /**
     * @param role - the role to remove from the required roles list
     * @return true if the role was removed successfully, false otherwise
     */
    public boolean removeRequiredRole(RoleType role){
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        return _requiredRoles.remove(role);
    }

    /**
     * @param employee - the employee to remove from the inquired employees list
     * @return true if the employee was removed successfully, false otherwise
     */
    public boolean removeInquiredEmployee(Employee employee){
        if (employee == null)
            throw new IllegalArgumentException("Invalid employee");
        return _inquiredEmployees.remove(employee);
    }

    public String toString(){
        StringBuilder output = new StringBuilder("ShiftID: " + _shiftID + ", Date: " + _date + ", ShiftType: " + _shiftType + ", Start hour: " + _startHour + ", End hour: " + _endHour + ", Length time: " + _shiftLength + ", Approved: " + _approved + ", Rejected: " + _rejected);
        if (_assignedEmployees.isEmpty())
            return output.toString();
        output.append(", Assigned employees: ");
        for (Map.Entry<RoleType,Employee> entry : _assignedEmployees.entrySet()){
            output.append(entry.getKey()).append(": ").append(entry.getValue().getFullName()).append(", ");
        }
        return output.substring(0,output.length()-2);
    }

    public boolean hasFilledRole(RoleType role){
        if (role == null)
            throw new IllegalArgumentException("Invalid role");
        return _filledRoles.contains(role);
    }
}