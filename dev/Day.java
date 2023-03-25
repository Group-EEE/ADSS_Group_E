import java.util.Date;
public class Day {
    private Shift m_morning_shift = new Shift(ShiftType.MORNING, 8 , 16);
    private Shift m_night_shift = new Shift(ShiftType.NIGHT, 16 , 24);
    private Date m_date;
    public Day(Date date){
        this.m_date = date;
    }
    public Shift getShift(ShiftType shift_type){
        switch (shift_type) {
            case MORNING:
                return m_morning_shift;
            case NIGHT:
                return m_night_shift;
            default:
                return null;
        }
    }


}
