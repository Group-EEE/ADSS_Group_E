package BussinessLayer.TransportationModule.objects;

public enum cold_level {
        Freeze (1),
        Cold(2),
        Dry(3);
        private int value;
        cold_level(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

    @Override
    public String toString() {
        switch (this.value){
            case 1:
                return "Freeze";
            case 2:
                return "Cold";
            case 3:
                return "Dry";
        }
        return null;
    }

    public static cold_level fromString(String str) {
        switch (str.toLowerCase()) {
            case "freeze":
                return Freeze;
            case "cold":
                return Cold;
            case "dry":
                return Dry;
            default:
                throw new IllegalArgumentException("Invalid cold level string: " + str);
        }
    }
}
