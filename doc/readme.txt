Group E - Inventory & Suppliers

The code development team:

Sapir Tzaig - 206795429
Liron Miriam Shemen - 209131952
Alon Azran - 207219742
Yehonatan Kidushim - 319068789

Hello dear costumer,
Attached are clear operating instructions to facilitate your experience and use of the system.

In order to start working with the system, you must enter the following link:
https://github.com/Group-EEE/ADSS_Group_E/tree/319068789_207219742_206795429_209131952

Click on "release" directory, then download the jar file called "adss2023_v03".
In your computer, open the terminal and enter one of the two types of commands below:


**One type of command**

java -cp pathToJarFile/adss2023_v03.jar (a) (b)

Where 'a' can be: "SuperLIMainCLI" OR "SuperLiMainGUI". 
Here, you choose how you would like to see the data, in Command Line Interface or Graphical User Interface.

Where 'b' can be: "StoreManager" OR "SupplierManager" OR "StoreKeeper". 
Here, you choose the role of the employee who enters the system. 
For each role, a different menu will be displayed according to the user's permissions according to his role.


**Seconde type of command**

java -jar pathToJarFile/adss2023_v03.jar (a) (b)

Where 'a' can be: "CLI" OR "GUI". 
Here, you choose how you would like to see the data, in Command Line Interface or Graphical User Interface.

Where 'b' can be: "StoreManager" OR "SupplierManager" OR "StoreKeeper". 
Here, you choose the role of the employee who enters the system. 
For each role, a different menu will be displayed according to the user's permissions according to his role.

For example, if you want GUI and the role is "StoreKeeper".
so enter command:

java -cp adss2023_v03.jar SuperLiMainGUI StoreKeeper **OR** java -jar adss2023_v03.jar GUI StoreKeeper

*****************************************************************************************************************************

If you chose a SupplierManager role , the following menu will open:

Please choose one of the options shown in the menu:
1.Insert a new supplier into the system.
2.Create a new periodic order.
3.Show orders history.
4.Delete supplier from the system.
5.Update Supplier Agreement.
6.Change supplier details.
7.Print supplier details.
0.Exit.

You must choose which actions you want.
There are options in the menu that, if you select them, additional sub-menus can be opened.
For each choice, the system will guide you by asking you questions,
and you will have to enter your choices by the characters you will be asked.
Every time you finish an action, you will return to the menu.
You may return to the previous menu by selecting the exit option.



If you chose a StoreKeeper role , the following menu will open:

Please choose an option:
1.Products
2.Specific Products
3.Reports
4.Categories
5.SubCategories
6.SubSubCategories
7.Discounts
8.Update or Delete periodic order
0.Exit

You must choose which actions you want.
There are options in the menu that, if you select them, additional sub-menus can be opened.
For each choice, the system will guide you by asking you questions,
and you will have to enter your choices by the characters you will be asked.
Every time you finish an action, you will return to the menu.
You may return to the previous menu by selecting the exit option.



If you chose a StoreManager role , the following menu will open:

Please choose one of the options shown in the menu:
1.SupplierManager menu
2.StoreKeeper menu
0.Exit

Using this menu, you can navigate between the supplier manager's menu and the StoreKeeper's menu.
This way, you can access all the actions that the supplier manager and the StoreKeeper can do.
You may return to the previous menu by selecting the exit option.

***********************************************************************************************************


Important things to know:

1.A database is also provided with the system.
  All the actions you perform will be saved in the system even if you turn off the system.

2.Every day at 11 o'clock,
  the system checks whether the periodic orders that are waiting for approval should be ordered automatically.

3.When a product supplied by a certain supplier is added, when you enter the system with "StoreKeeper" role,
  you will be notified that there are new products that you must check.

4.To exit the system, select the exit option in the first menu that the system is activated.


Libraries we used in the system:

java.util - For the purpose of using data structures such as List, Map.
java.sql - For the purpose of connecting to a DB.
sqlite-jdbc-3.41.2.1.jar - For the purpose of connecting to a DB.
javax.swing - For use in creating a GUI.
java.awt - For the purpose of using for displaying red color for label.
java.time - For the purpose of the periodic order and the dates.


We have provided you data that is loaded into the system when it is activated.
Details of the existing data are shown in "The existing data in the DB" file.

