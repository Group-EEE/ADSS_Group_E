//package BussinessLayer.Controllers;
//
//import BussinessLayer.Objects.*;
//
//import java.util.List;
//
//
//import java.util.ArrayList;
//
//public class HRManager extends AEmployee {
//
//
//    private static HRManager _HRManager = null;
//
//    private HRManager(String first_name, String last_name, int age, int id, String bank_account,String password) {
//        super(first_name, last_name, age, id, bank_account);
//        //EmployeeController.createUser(id, password, this);
//    }
//
//    public static HRManager getInstance(){
//        return _HRManager;
//    }
//
//    public static void createHRManager(String first_name, String last_name, int age, int id, String bank_account,String password){
//        if (first_name == null || last_name == null || age < 0 || id < 0 || bank_account == null || password == null)
//            return;
//        _HRManager = new HRManager(first_name, last_name, age, id, bank_account, password);
//    }
//
//
//
//
//}
