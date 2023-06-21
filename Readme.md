Group E - HR and transpose

The code development team:

Daniel Shapira - 208895656
Gal Levi - 315479354
Chen Fridman - 208009845
Oded Atias - 311394365

Hello dear costumer,
Attached are clear operating instructions to facilitate your experience and use of the system.



###################################################################################################################################
**command**

java -cp pathToJarFile/adss2023_v03.jar (a) (b)

Where 'a' can be: "CLI" OR "GUI".
Here, you choose how you would like to see the data, in Command Line Interface or Graphical User Interface.

Where 'b' can be: "StoreManager" OR "HRManager" OR "TransportManager".
if you are an employee please choose HRManager and put your id and password
Here, you choose the role of the employee who enters the system.
For each role, a different menu will be displayed according to the user's permissions according to his role.

*****************************************************************************************************************************

If you chose a HrManager role  and you are the HRManager, choose HRmodule and than a Login option will open and you will need to enter your id and password:


Please login to your user, 0 for exit
Please enter your ID:
1
Please enter your password:
1234




than, the following menu will open:

Welcome to the HR system!
Please select the following options
1. create new employee
2. create new store
3. add employee to store
4. add role to employee
5. create new schedule
6. approve shifts
7. update personal information
8. change schedule hours
9. remove role from employee
10. remove employee from store
11. remove employee from system
12. remove store from system
13. select required roles from a shift
14. remove required roles from a shift
15. print all employees
16. print all stores
17. print schedule
18. create new driver
19. create new logistics schedule
0. log out


You must choose which actions you want.
There are options in the menu that, if you select them, additional sub-menus can be opened.
For each choice, the system will guide you by asking you questions,
and you will have to enter your choices by the characters you will be asked.
Every time you finish an action, you will return to the menu.
You may return to the previous menu by selecting the exit option.



If you chose HrManager role  and you are an employee choose HRmodule and than a Login option will open and you will need to enter your id and password:


Please login to your user, 0 for exit
Please enter your ID:
2
Please enter your password:
1234


than, the following menu will open:

Welcome to the employee system!
Please select an option
1. select shifts for this week
2. update personal Information
3. Print your schedule
0. log out

You must choose which actions you want.
There are options in the menu that, if you select them, additional sub-menus can be opened.
For each choice, the system will guide you by asking you questions,
and you will have to enter your choices by the characters you will be asked.
Every time you finish an action, you will return to the menu.
You may return to the previous menu by selecting the exit option.


If you chose a TransportManager role choose Transposemodule and than the following menu will open:

Hey Boss! what would you like to do?
0. register a supplier to the System
1. See all the trucks with a cold level of your choice:
    1. Freeze
    2. Cold
    3. Dry
2. create a new transport
3. send transports
4. Add new truck to the system
5. Display all drivers in the system
6. Display all trucks in the system
7. Display all transport documents in the system
8. Display all site supplies documents in the system
9. Display all stores in the system
10. Display all suppliers in the system
11. Add standby driver to existing schedule by date.
12. quit

Using this menu, you can navigate between the supplier manager's menu and the StoreKeeper's menu.
This way, you can access all the actions that the supplier manager and the StoreKeeper can do.
You may return to the previous menu by selecting the exit option.
***********************************************************************************************************
Important things to know:

1.A database is also provided with the system.
All the actions you perform will be saved in the system even if you turn off the system.


2.To exit the system, select the exit option in the first menu that the system is activated.


Libraries we used in the system:

java.util - For the purpose of using data structures such as List, Map.
java.sql - For the purpose of connecting to a DB.
sqlite-jdbc-3.41.2.1.jar - For the purpose of connecting to a DB.
javax.swing - For use in creating a GUI.
java.awt - For the purpose of using for displaying red color for label.
java.time - For the purpose of the periodic order and the dates.


We have provided you data that is loaded into the system when it is activated.
Details of the existing data are shown in "The existing data in the DB" file.

