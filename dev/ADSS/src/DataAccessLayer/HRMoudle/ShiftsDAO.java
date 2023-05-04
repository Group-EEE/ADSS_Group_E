package DataAccessLayer.HRMoudle;

import BussinessLayer.HRModule.Objects.Shift;
import BussinessLayer.HRModule.Objects.ShiftType;
import DataAccessLayer.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShiftsDAO extends DAO {
    private static ShiftsDAO _shiftsDAO = null;

    public static final String ScheduleIDColumnName = "scheduleID";
    public static final String ShiftIDColumnName = "shiftID";
    public static final String ShiftTypeColumnName = "shiftType";
    public static final String StartTimeColumnName = "startHour";
    public static final String EndTimeColumnName = "endHour";
    public static final String StoreIDColumnName = "storeID";
    public static final String approvedColumnName = "approved";
    public static final String rejectedColumnName = "rejected";

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
    public Shift convertReaderToObject(ResultSet rs) throws SQLException {
        return new Shift(rs.getInt(1),rs.getInt(2),ShiftType.toEnum(rs.getString(3)),rs.getInt(4),rs.getInt(5),parseLocalDate(rs.getString(6)));
    }
    

    public List<Shift> getShiftsByScheduleID(int scheduleID){
        return Select(makeList(ScheduleIDColumnName),makeList(String.valueOf(scheduleID)));
    }
}
