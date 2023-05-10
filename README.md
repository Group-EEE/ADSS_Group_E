Group E - Inventory & Suppliers
-

The code development team:

Sapir Tzaig - 206795429\
Liron Miriam Shemen - 209131952\
Alon Azran - 207219742\
Yehonatan Kidushim - 319068789

Instruction 
-

Hello dear costumer,
Attached are clear operating instructions to facilitate your experience and use of the system.

In these instructions,
Outputs of the system are marked in <font color="red">red.</font>\
Commands are marked in <font color="green">green.</font>\
Important things to knew ar marked in <font color="orange">orange.</font>

In order to start working with the system, you must enter the following link:\
https://github.com/Group-EEE/ADSS_Group_E/tree/319068789_207219742_206795429_209131952

Click on "release" directory, then download the jar file called "adss2023_v02".\
In your computer, open the terminal and enter the following command:

<font color="green">java -jar pathToJarFile/adss2023_v02.jar</font>

When the system is activated, the following menu will appear:

<font color="red">Please choose one of the options shown in the menu:\
1.Suppliers system\
2.Inventory system\
0.Exit</font>

You must choose whether you want to enter a supplier system or an inventory system.

**If you chose a supplier system, the following menu will open:**

<font color="red">Please choose one of the options shown in the menu:\
1.Insert a new supplier into the system.\
2.Create a new periodic order.\
3.Show orders history.\
4.Delete supplier from the system.\
5.Update Supplier Agreement.\
6.Change supplier details.\
7.Print supplier details.\
0.Exit.</font>

You must choose which actions you want in a supplier system.\
There are options in the menu that, if you select them, additional sub-menus can be opened.\
For each choice, the system will guide you by asking you questions,\
and you will have to enter your choices by the characters you will be asked.\
Every time you finish an action, you will return to the menu.\
You may return to the previous menu by selecting the exit option.


**If you chose an inventory system, the following menu will open:**

<font color="red">"Please choose an option":\
1.Products\
2.Specific Products\
3.Reports\
4.Categories\
5.SubCategories\
6.SubSubCategories\
7.Discounts\
0.Exit</font>

You must choose which actions you want in a inventory system.\
There are options in the menu that, if you select them, additional sub-menus can be opened.\
For each choice, the system will guide you by asking you questions,\
and you will have to enter your choices by the characters you will be asked.\
Every time you finish an action, you will return to the menu.\
You may return to the previous menu by selecting the exit option.


<font color="orange">Important things to know:

1.A database is also provided with the system.\ 
  All the actions you perform will be saved in the system even if you turn off the system.

2.Every day at 11 o'clock,\
  the system checks whether the periodic orders that are waiting for approval should be ordered automatically.

3.When a product supplied by a certain supplier is added, when you enter the inventory system,\
  you will be notified that there are new products that you must check.

4.To exit the system, select the exit option in the first menu that the system is activated.</font>


**We have provided you data that is loaded into the system when it is activated.
Details of the existing data are shown in the following tables:**

Supplier table
-
| SupplierNum | Name  | BankAccount | PaymentTerm |
|-------------|-------|-------------|-------------|
| 0001        | Yoni  | 272893      | 1           |
| 0002        | Alon  | 563743      | 2           |
| 0003        | May   | 123412      | 0           |
| 0004        | Avi   | 234234      | 1           |
| 0005        | Eden  | 859930      | 0           | 
| 0006        | Noam  | 829004      | 2           |
| 0007        | Nir   | 884392      | 0           |
| 0008        | Nevo  | 986203      | 2           |

Contact table
-
| SupplierNum | Name    | PhoneNumber   |
|-------------|---------|---------------|
| 0001        | Ilan    | 0523742939    | 
| 0001        | Sarit   | 0542872329    |
| 0002        | Itay    | 0509382947    |
| 0002        | Aliza   | 0532759266    |
| 0003        | Adi     | 0502349978    |
| 0004        | Ben     | 0538294728    |
| 0005        | Adir    | 0502834922    |
| 0005        | Erez    | 0532833945    |
| 0006        | Dina    | 0527602983    |
| 0006        | Ran     | 0548299126    |
| 0007        | Oded    | 0502197825    |
| 0007        | Or      | 0529388127    |
| 0008        | Iris    | 0508399747    |

Agreement table
-
| SupplierNum | HasPermanentDays  | IsSupplierBringProduct | Sunday | Monday | Tuesday | Wednesday | Thursday | Friday  | Saturday | NumberOfDayToSupply |
|-------------|-------------------|------------------------|--------|--------|---------|-----------|----------|---------|----------|---------------------|
| 0001        | 1                 | 1                      | 0      | 1      | 0       | 0         | 1        | 0       | 0        | -1                  |
| 0002        | 0                 | 1                      | 0      | 0      | 0       | 0         | 0        | 0       | 0        | 6                   |
| 0003        | 0                 | 1                      | 0      | 0      | 0       | 0         | 0        | 0       | 0        | 2                   |
| 0004        | 0                 | 0                      | 0      | 0      | 0       | 0         | 0        | 0       | 0        | -1                  |
| 0005        | 1                 | 1                      | 1      | 1      | 0       | 0         | 1        | 0       | 0        | -1                  |
| 0006        | 1                 | 1                      | 0      | 0      | 1       | 0         | 0        | 0       | 0        | -1                  |
| 0007        | 0                 | 0                      | 0      | 0      | 0       | 0         | 0        | 0       | 0        | -1                  |
| 0008        | 0                 | 1                      | 0      | 0      | 0       | 0         | 0        | 0       | 0        | 3                   |

GenericProduct table
-
| Name            | ManufacturerName   | Barcode |
|-----------------|--------------------|---------|
| Yellow cheese   | Tnuva              | 123456  | 
| blue cheese     | gad                | 55555   |
| Milk            | Tnuva              | 234567  |
| Salmon          | Crownfish          | 888777  |
| Butter          | Tara               | 789001  |
| Sprite          | Coca cola          | 237899  |
| Steak           | Adom adom          | 999911  |
| Tuna            | Crownfish          | 777666  |
| Fuze tea        | Coca cola          | 984325  |
| Butter          | Tnuva              | 556677  |
| Yogurt          | Tara               | 687239  |
| Cola            | Coca cola          | 113366  |
| Carrot          | Shavit farm        | 990006  |
| Potato          | Shavit farm        | 990005  |
| Tomato          | Shavit farm        | 990004  |
| Orange          | Tzvika farm        | 990003  |
| Banana          | Tzvika farm        | 990002  |
| Apple           | Tzvika farm        | 990001  |

Manufacturer
-
| Name              |
|-------------------|
| Tzvika farm       | 
| Tnuva             |
| Shavit farm       |
| Adom adom         |
| Tara              |
| Coca cola         |
| gad               |
| Crownfish         |

