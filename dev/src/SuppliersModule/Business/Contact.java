package SuppliersModule.Business;

/**
A contact class that describes a supplier contact
*/
public class Contact {

    //------------------------------------------ Attributes ---------------------------------------
    private final String Name;
    private String PhoneNumber;

    //-----------------------------------Methods related to This -------------------------------------------

    //Constructor
    public Contact(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String toString(){
        return "SuppliersModule.Business.Contact name: " + Name + ", phone number: " + PhoneNumber + "\n";
    }
}
