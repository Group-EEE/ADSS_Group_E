package Business.objects;

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
}
