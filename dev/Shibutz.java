import java.util.*;


public class Shibutz {
    private Map<Employee, List<Map<Day, ShiftType>>> shibutz;
    private Date start_week;
    private Date end_week;
    public Shibutz(Date start, Date end){
        this.start_week = start;
        this.end_week = end;
        this.shibutz = new HashMap<>();
    }

    public void setStart_week(Date start_week) {
        this.start_week = start_week;
    }
    public void setEnd_week(Date end_week){
        this.end_week = end_week;
    }

    public Date getStart_week(){
        return this.start_week;
    }

    public Date getEnd_week(){
        return this.end_week;
    }

    public Map<Employee,List<Map<Day, ShiftType>>> register_for_shifts(Employee e){
        for (long date = this.start_week.getTime(); date <= this.end_week.getTime(); date += 86400000) {
            if (this.shibutz.values().size() == 7){
                break;
            }
            Map<Day, ShiftType> dayShiftMap = new HashMap<>();
            Date currentDate = new Date(date);
            Scanner myObj = new Scanner(System.in);
            System.out.println("can you work in" + currentDate);
            String answer = myObj.nextLine();
            if (answer == "Yes"){
                Day day_of_work = new Day(currentDate);
                Scanner myObj1 = new Scanner(System.in);
                System.out.println("What shift would you like to work?");
                ShiftType answer1 = ShiftType.valueOf(myObj1.nextLine());
                dayShiftMap.put(day_of_work,answer1);
                if (this.shibutz.values().isEmpty()) {
                    List<Map<Day, ShiftType>> employeeSchedules = shibutz.getOrDefault(e, new ArrayList<>());
                    employeeSchedules.add(dayShiftMap);
                    this.shibutz.put(e, employeeSchedules);
                }
                else {
                    List<Map<Day, ShiftType>> employeeSchedules = shibutz.get(e);
                    employeeSchedules.add(dayShiftMap);
                    shibutz.put(e, employeeSchedules);
                }
            }
        }
        return this.shibutz;
    }
}

