package BussinessLayer.HRModule.Objects;

public enum RoleType {
    Cashier, Cleaner, General, Warehouse, ShiftManager, Security, Usher, HRManager;

    public static RoleType toEnum(String str){
        switch (str){
            case "Cashier":
                return RoleType.Cashier;
            case "Cleaner":
                return RoleType.Cleaner;
            case "General":
                return RoleType.General;
            case "Warehouse":
                return RoleType.Warehouse;
            case "ShiftManager":
                return RoleType.ShiftManager;
            case "Security":
                return RoleType.Security;
            case "Usher":
                return RoleType.Usher;
            case "HRManager":
                return RoleType.HRManager;
            case default:
                return null;
        }
    }
}
