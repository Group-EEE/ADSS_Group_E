import java.time.LocalTime;

public class Shift{
    private ShiftType m_shift_type;
    private int m_start_time;
    private int m_end_time;
    private double m_shift_length;
    private boolean m_need_manager = true;
    private boolean m_need_cashier = true;
    private boolean m_need_general = true;
    private boolean m_need_wearhouse = true;
    public Shift(ShiftType shift_type, int start_time, int end_time){
        this.m_shift_type = shift_type;
        this.m_start_time = start_time;
        this.m_end_time = end_time;
        this.m_shift_length = end_time - start_time;
    }
    public boolean setNeedManager(boolean need_manager) {
        return m_need_manager = need_manager;
    }
    public boolean setNeedCashier(boolean need_cashier) {
        return m_need_cashier = need_cashier;
    }
    public boolean setNeedGeneral(boolean need_general) {
        return m_need_general = need_general;
    }
    public boolean setNeedWearhouse(boolean need_wearhouse) {
        return m_need_wearhouse = need_wearhouse;
    }
}