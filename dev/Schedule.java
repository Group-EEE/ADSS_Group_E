public class Schedule {
   private Day[] m_week = new Day[7];
   public Shift getShift(int day, ShiftType shift_type){
       return m_week[day].getShift(shift_type);
   }
}
