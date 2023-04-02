//package Roles;

public abstract class ARole {
    private Employee _employee;
    public abstract String toString();
    public Employee getEmployee() {
        return _employee;
    }
    public boolean setEmployee(Employee employee) {
        if (employee == null)
            return false;
        _employee = employee;
        return true;
    }
    public boolean hasEmployee() {
        return _employee != null;
    }
}
