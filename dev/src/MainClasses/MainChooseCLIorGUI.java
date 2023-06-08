package MainClasses;

public class MainChooseCLIorGUI {
    public static void main(String[] args)
    {
        //----------------------------------- Check length of args -----------------------------------

        if(args.length != 2)
        {
            System.out.println("The number of parameters must be 2");
            return;
        }

        //----------------------------------- Check If args[0] is valid -----------------------------------

        if(!args[0].equals("GUI") && !args[0].equals("CLI"))
        {
            System.out.println("The first argument must be GUI or CLI");
            return;
        }

        //----------------------------------- Check If args[1] is valid -----------------------------------

        if(!args[1].equals("StoreManager") && !args[1].equals("SupplierManager") && !args[1].equals("Storekeeper"))
        {
            System.out.println("The second argument must be StoreManager or SupplierManager or Storekeeper");
            return;
        }

        //----------------------------------------------------------------------------------------------

        switch (args[0]) {
            case "GUI":                                             // If args[0] is GUI
                SuperLiMainGUI.main(new String[]{args[1]});
                break;
            case "CLI":                                             // If args[0] is CLI
                SuperLiMainCLI.main(new String[]{args[1]});
                break;
        }
    }
}