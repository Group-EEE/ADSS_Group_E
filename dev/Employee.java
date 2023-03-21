import java.util.Map;

public abstract class Employee{
    private String first_name;
    private String last_name;
    private int age;
    private int id;
    private int bank_account;
    private Map<String,int>[] time;
    public Employee(String first_name, String last_name, int age, int id, int bank_account){
        this.first_name=first_name;
        this.last_name=last_name;
        this.age=age;
        this.id=id;
        this.bank_account=bank_account;
    }
    public void canWork(Map<String,int>[] arr_can_work){
        this.time = arr_can_work;
    }

}