package InterfaceLayer;
import java.util.Scanner;
import ServiceLayer.ModulesServices.IntegratedService;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Super-Lee System !\n");
        System.out.println("load data ? y/n");
        String yesOrNo = scanner.nextLine();
        if(yesOrNo.equals("y")){
            try{
                IntegratedService.getInstance().loadData();
            }
            catch (Exception e){
                System.out.println("Didn't load data");
                System.out.println("Error: " + e.getMessage());
            }
        }
        HRModuleCLI _HRModule = new HRModuleCLI();
        _HRModule.start();
    }

}
