package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.DAO;

import java.sql.ResultSet;
import java.util.List;

public class ShiftsDAO extends DAO {
    private static ShiftsDAO _shiftsDAO = null;
    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String ShiftIDColumnName = "shiftID";
    public static final String ShiftNameColumnName = "shiftName";
    public static final String ShiftStartTimeColumnName = "shiftStartTime";
    public static final String ShiftEndTimeColumnName = "shiftEndTime";
    public static final String ShiftDurationColumnName = "shiftDuration";
    public static final String ShiftTypeColumnName = "shiftType";
    public static final String ShiftStoreIDColumnName = "shiftStoreID";

    private ShiftsDAO(){
        super("Shifts");
    }

    public static ShiftsDAO getInstance(){
        if (_shiftsDAO == null)
            _shiftsDAO = new ShiftsDAO();
        return _shiftsDAO;
    }

    @Override
    public boolean Insert(Object objectShift) {
        return false;
    }

    @Override
    public boolean Delete(Object objectShift) {
        return false;
    }

    @Override
    public Shift convertReaderToObject(ResultSet resultSet) {
        Shift shift = null;
        try {
            shift = new Shift(stringToEnum(resultSet.getString(3)), // VARCHAR(255)
                    resultSet.getInt(4), //startHour
                    resultSet.getInt(5), //endHour
                    resultSet.getDate(9).toLocalDate());//date )
        } catch (Exception e) {
            System.out.println("Got Exception:");
            System.out.println(e.getMessage());
        }
        return shift;
    }

    public ShiftType stringToEnum(String str){
        switch (str){
            case "MORNING":
                return ShiftType.MORNING;
            case "NIGHT":
                return ShiftType.NIGHT;
            default:
                throw new IllegalArgumentException("Invalid shift type");
        }
    }

    public List<Shift> getShiftsByScheduleID(int scheduleID){
        List<Employee> result = Select(makeList(ScheduleIDColumnName), makeList(String.valueOf(scheduleID)));
        if (result.size() == 0)
            throw new IllegalArgumentException("Employee " + id + " does not exist");
        Employee employee = result.get(0);
        employeesCache.put(id, employee);
        return employee;
    }
}
