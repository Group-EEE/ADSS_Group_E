package BussinessLayer.HRModule.Objects;

public enum ShiftType {
    MORNING, NIGHT; //there are only two shifts, morning and night
    public static ShiftType toEnum(String str){
        switch (str){
            case "MORNING":
                return ShiftType.MORNING;
            case "NIGHT":
                return ShiftType.NIGHT;
            default:
                throw new IllegalArgumentException("Shift type must be either MORNING or NIGHT");}
    }
}