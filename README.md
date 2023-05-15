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

Libraries we used in the system:\
java.util\
java.sql\
sqlite-jdbc-3.41.2.1.jar

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


OrderDiscount table
-
| SupplierNum | ByPriceOrQuantity | Amount | Discount |
|-------------|-------------------|--------|----------|
| 0001        | p                 | 100    | 10       |
| 0001        | q                 | 100    | 30       |
| 0002        | p                 | 150    | 30       |
| 0002        | q                 | 60     | 40       |
| 0003        | p                 | 200    | 35       | 
| 0003        | q                 | 70     | 40       |
| 0005        | p                 | 300    | 25       |
| 0006        | q                 | 130    | 15       |
| 0007        | p                 | 2000   | 20       | 
| 0007        | p                 | 3000   | 25       |
| 0008        | p                 | 5000   | 40       |
| 0008        | q                 | 100    | 30       |


SupplierProduct table
-
| GenericProduct  | Manufacturer  | SupplierNum | Price | SupplierCatalog | Amount |
|-----------------|---------------|-------------|-------|-----------------|--------|
| Apple           | Tzvika farm   | 0001        | 4     | 001             | 400    |
| Banana          | Tzvika farm   | 0001        | 2     | 002             | 400    |
| Butter          | Tara          | 0004        | 6.5   | 072             | 700    |
| Butter          | Tnuva         | 0004        | 6     | 120             | 1000   |
| Carrot          | Shavit farm   | 0002        | 7     | 003             | 200    |
| Carrot          | Shavit farm   | 0003        | 7     | 003             | 400    |
| Cola            | Coca cola     | 0006        | 4     | 007             | 150    |
| Cola            | Coca cola     | 0005        | 5     | 234             | 100    |
| Fuze tea        | Coca cola     | 0005        | 6     | 382             | 900    |
| Milk            | Tnuva         | 0004        | 7.5   | 014             | 200    |
| Milk            | Tnuva         | 0006        | 6     | 042             | 200    |
| Orange          | Tzvika farm   | 0002        | 4     | 004             | 500    |
| Orange          | Tzvika farm   | 0001        | 8     | 003             | 400    |
| Orange          | Tzvika farm   | 0003        | 2     | 012             | 300    |
| Potato          | Shavit farm   | 0002        | 5     | 002             | 500    |
| Salmon          | Crownfish     | 0007        | 110   | 445             | 350    |
| Sprite          | Coca cola     | 0005        | 5.5   | 928             | 650    |
| Steak           | Adom adom     | 0008        | 160   | 973             | 500    |
| Steak           | Adom adom     | 0007        | 150   | 883             | 300    |
| Tomato          | Shavit farm   | 0002        | 3.5   | 001             | 550    |
| Tuna            | Crownfish     | 0007        | 70    | 873             | 860    |
| Yellow cheese   | Tnuva         | 0006        | 18    | 231             | 90     |
| Yellow cheese   | Tnuva         | 0004        | 17    | 020             | 80     |
| Yogurt          | Tara          | 0006        | 5.5   | 128             | 140    |


SupplierProductDiscount table
-
| SupplierNum | SupplierCatalog     | Amount | Discount |
|-------------|---------------------|--------|----------|
| 0001        | 001                 | 25     | 200      |
| 0001        | 001                 | 50     | 300      |
| 0001        | 002                 | 25     | 150      |
| 0001        | 003                 | 20     | 100      |
| 0002        | 001                 | 20     | 150      | 
| 0002        | 001                 | 30     | 250      |
| 0002        | 003                 | 20     | 90       |
| 0003        | 003                 | 50     | 300      |
| 0004        | 014                 | 30     | 100      | 
| 0004        | 014                 | 40     | 150      |
| 0004        | 020                 | 20     | 30       |
| 0004        | 072                 | 60     | 500      |
| 0005        | 234                 | 25     | 60       |
| 0005        | 928                 | 10     | 110      |
| 0005        | 928                 | 20     | 200      |
| 0006        | 007                 | 30     | 90       |
| 0006        | 042                 | 75     | 160      | 
| 0006        | 128                 | 30     | 45       |
| 0006        | 128                 | 55     | 60       |
| 0006        | 231                 | 30     | 40       |
| 0006        | 231                 | 35     | 50       | 
| 0007        | 445                 | 20     | 120      |
| 0007        | 883                 | 25     | 160      |
| 0007        | 883                 | 40     | 200      |
| 0008        | 973                 | 45     | 300      |


SupplierCategories table
-
| SupplierNum | Category    |
|-------------|-------------|
| 0001        | Fruits      |
| 0002        | Fruits      |
| 0002        | Vegetables  |
| 0003        | Fruits      |
| 0004        | Dairy       |
| 0005        | Drinks      |
| 0006        | Dairy       |
| 0006        | Drinks      |
| 0007        | Fishs       |
| 0007        | Meats       |
| 0008        | Meats       |


Supplier_Manufacturer table
-
| SupplierNum | Category    |
|-------------|-------------|
| 0001        | Tzvika farm |
| 0002        | Shavit farm |
| 0002        | Tzvika farm |
| 0003        | Shavit farm |
| 0003        | Tzvika farm |
| 0004        | Tara        |
| 0004        | Tnuva       |
| 0005        | Coca cola   |
| 0006        | Coca cola   |
| 0006        | Tara        |
| 0006        | Tnuva       |
| 0007        | Adom adom   |
| 0007        | Crownfish   |
| 0008        | Adom adom   |

SuperLiProduct table
-
| Barcode | Pname         | Costumer_Price | Shelf_amount | Warehouse_amount | Category   | SubCategory | SubSubCategory | Supply_Days | Manufacturer | Minimum_amount | SP_Counter |
|---------|---------------|----------------|--------------|------------------|------------|-------------|----------------|-------------|--------------|----------------|------------|
| 123456  | Yellow cheese | 20             | 1            | 1                | Dairy      | cheese      | 50g            | 1           | Tnuva        | 100            | 2          |
| 234567  | Milk          | 6              | 2            | 0                | Dairy      | milk        | 3%             | 2           | Tnuva        | 300            | 2          |
| 888777  | Salmon        | 100            | 1            | 0                | Fishs      | sea         | pink           | 3           | Crown Fish   | 300            | 1          |
| 789001  | Butter        | 8              | 2            | 0                | Dairy      | salty       | 200g           | 4           | Tara         | 400            | 2          |
| 237899  | Sprite        | 5              | 1            | 0                | Drinks     | sugar       | fuzzy          | 5           | Coca Cola    | 500            | 1          |
| 999911  | Steak         | 130            | 2            | 1                | Meats      | cow         | blood          | 6           | Adom Adom    | 600            | 3          |
| 777666  | Tuna          | 80             | 1            | 0                | Fishs      | sea         | pink           | 7           | Crown Fish   | 700            | 1          |
| 984325  | Fuze tea      | 5              | 2            | 0                | Drinks     | sugar       | not-fuzzy      | 8           | Coca Cola    | 800            | 2          |
| 556677  | Butter        | 8              | 1            | 0                | Dairy      | salty       | 200g           | 9           | Tnuva        | 900            | 1          |
| 687239  | Yogurt        | 9              | 2            | 0                | Dairy      | sheep       | 50g            | 1           | Tara         | 100            | 2          |
| 113366  | Cola          | 5              | 1            | 0                | Drinks     | sugar       | fuzzy          | 2           | Coca Cola    | 200            | 1          |
| 990006  | Carrot        | 3              | 1            | 1                | Vegetables | green       | orange         | 3           | Shavit farm  | 500            | 2          |
| 990005  | Potato        | 3              | 1            | 0                | Vegetables | green       | brown          | 4           | Shavit farm  | 400            | 1          |
| 990004  | Tomato        | 3              | 1            | 0                | Vegetables | green       | red            | 5           | Shavit farm  | 500            | 1          |
| 990003  | Orange        | 3              | 1            | 0                | Fruits     | green       | orange         | 6           | Tzvika farm  | 600            | 1          |
| 990002  | Banana        | 8              | 1            | 0                | Fruits     | green       | yellow         | 7           | Tzvika farm  | 650            | 1          |
| 990001  | Apple         | 15             | 1            | 0                | Fruits     | green       | red            | 8           | Tzvika farm  | 300            | 1          |

SpecificProduct table
-
| SP_ID | Barcode | ExpDate             | Defect_Report_By | Defective | InWarehouse | Store_Branch | Location_In_Store | DefectType | Supplier | Supplier_Price | arrivaldate         |
|-------|---------|---------------------|------------------|-----------|-------------|--------------|-------------------|------------|----------|----------------|---------------------|
| 1     | 123456  | 2023-05-01T00:00:00 | sapir            | True      | True        | shufersal    | -1                | expired    | 0006     | 18             | 2023-04-01T00:00:00 |
| 2     | 123456  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 1                 | null       | 0006     | 18             | 2023-04-02T00:00:00 |
| 1     | 234567  | 2024-12-11T00:00:00 | null             | False     | False       | shufersal    | 2                 | null       | 0006     | 6              | 2023-04-03T00:00:00 |
| 2     | 234567  | 2024-05-22T00:00:00 | null             | False     | False       | shufersal    | 3                 | null       | 0006     | 6              | 2023-04-04T00:00:00 |
| 1     | 888777  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 4                 | null       | 0007     | 110            | 2023-04-05T00:00:00 |
| 1     | 789001  | 2024-08-22T00:00:00 | null             | False     | False       | shufersal    | 5                 | null       | 0004     | 6.5            | 2023-04-06T00:00:00 |
| 2     | 789001  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 6                 | null       | 0004     | 6.5            | 2023-04-07T00:00:00 |
| 1     | 237899  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 7                 | null       | 0005     | 5.5            | 2023-04-08T00:00:00 |
| 1     | 999911  | 2024-05-01T00:00:00 | liron            | True      | True        | shufersal    | -1                | expired    | 0008     | 160            | 2023-04-09T00:00:00 |
| 2     | 999911  | 2024-05-12T00:00:00 | null             | False     | False       | shufersal    | 7                 | null       | 0008     | 160            | 2023-04-10T00:00:00 |
| 3     | 999911  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 6                 | null       | 0008     | 160            | 2023-04-11T00:00:00 |
| 1     | 777666  | 2024-06-11T00:00:00 | null             | False     | False       | shufersal    | 5                 | null       | 0007     | 70             | 2023-04-12T00:00:00 |
| 1     | 984325  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 4                 | null       | 0005     | 6              | 2023-04-13T00:00:00 |
| 2     | 984325  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 3                 | null       | 0005     | 6              | 2023-04-14T00:00:00 |
| 1     | 556677  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 2                 | null       | 0004     | 6              | 2023-04-15T00:00:00 |
| 1     | 687239  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 1                 | null       | 0006     | 5.5            | 2023-04-16T00:00:00 |
| 2     | 687239  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 2                 | null       | 0006     | 5.5            | 2023-04-17T00:00:00 |
| 1     | 113366  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 3                 | null       | 0006     | 4              | 2023-04-18T00:00:00 |
| 1     | 990006  | 2024-10-01T00:00:00 | alon             | True      | True        | shufersal    | -1                | expired    | 0002     | 7              | 2023-04-19T00:00:00 |
| 2     | 990006  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 2                 | null       | 0002     | 7              | 2023-04-20T00:00:00 |
| 1     | 990005  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 3                 | null       | 0002     | 5              | 2023-04-21T00:00:00 |
| 1     | 990004  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 4                 | null       | 0002     | 3.5            | 2023-04-22T00:00:00 |
| 1     | 990003  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 5                 | null       | 0001     | 8              | 2023-04-23T00:00:00 |
| 1     | 990002  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 6                 | null       | 0001     | 2              | 2023-04-24T00:00:00 |
| 1     | 990001  | 2024-05-11T00:00:00 | null             | False     | False       | shufersal    | 4                 | null       | 0001     | 4              | 2023-04-25T00:00:00 |

Category table
-
| Name       |
|------------|
| Dairy      |
| Fishs      |
| Drinks     |
| Meats      |
| Vegetables |
| Fruits     |

SubCategory table
-
| Name   | Category   |
|--------|------------|
| cheese | Dairy      |
| milk   | Dairy      |
| sea    | Fishs      |
| salty  | Dairy      |
| sugar  | Drinks     |
| cow    | Meats      |
| sheep  | Dairy      |
| green  | Vegetables |
| green  | Fruits     |

SubSubCategory table
-
| Name      | Category   | SubCategory |
|-----------|------------|-------------|
| 50g       | Dairy      | cheese      |
| 3%        | Dairy      | milk        |
| pink      | Fishs      | sea         |
| 200g      | Dairy      | salty       |
| fuzzy     | Drinks     | sugar       |
| blood     | Meats      | cow         |
| not-fuzzy | Drinks     | sugar       |
| 50g       | Dairy      | sheep       |
| orange    | Vegetables | green       |
| brown     | Vegetables | green       |
| red       | Vegetables | green       |
| orange    | Fruits     | green       |
| yellow    | Fruits     | green       |
| red       | Fruits     | green       |

Discount table
-
| Barcode | SP_ID | StartDate           | EndDate             | Discount |
|---------|-------|---------------------|---------------------|----------|
| 123456  | 1     | 2023-04-01T00:00:00 | 2023-05-01T00:00:00 | 1        | 
| 123456  | 2     | 2023-04-02T00:00:00 | 2024-05-11T00:00:00 | 2        |
| 234567  | 1     | 2023-04-03T00:00:00 | 2024-12-11T00:00:00 | 3        |
| 234567  | 2     | 2023-04-04T00:00:00 | 2024-05-22T00:00:00 | 4        |
| 888777  | 1     | 2023-04-05T00:00:00 | 2024-05-11T00:00:00 | 5        |
| 789001  | 1     | 2023-04-06T00:00:00 | 2024-08-22T00:00:00 | 6        |
| 789001  | 2     | 2023-04-07T00:00:00 | 2024-05-11T00:00:00 | 7        |
| 237899  | 1     | 2023-04-08T00:00:00 | 2024-05-11T00:00:00 | 1        |
| 999911  | 1     | 2023-04-09T00:00:00 | 2024-05-01T00:00:00 | 2        |
| 999911  | 2     | 2023-04-10T00:00:00 | 2024-05-12T00:00:00 | 3        |
| 999911  | 3     | 2023-04-11T00:00:00 | 2024-05-11T00:00:00 | 4        |
| 777666  | 1     | 2023-04-12T00:00:00 | 2024-06-11T00:00:00 | 5        |
| 984325  | 1     | 2023-04-13T00:00:00 | 2024-05-11T00:00:00 | 6        |
| 984325  | 2     | 2023-04-14T00:00:00 | 2024-05-11T00:00:00 | 7        |
| 556677  | 1     | 2023-04-15T00:00:00 | 2024-05-11T00:00:00 | 1        |
| 687239  | 1     | 2023-04-16T00:00:00 | 2024-05-11T00:00:00 | 2        |
| 687239  | 2     | 2023-04-17T00:00:00 | 2024-05-11T00:00:00 | 3        |
| 113366  | 1     | 2023-04-18T00:00:00 | 2024-05-11T00:00:00 | 4        |
| 990006  | 1     | 2023-04-19T00:00:00 | 2024-10-01T00:00:00 | 5        |
| 990006  | 2     | 2023-04-20T00:00:00 | 2024-05-11T00:00:00 | 6        |
| 990005  | 1     | 2023-04-21T00:00:00 | 2024-05-11T00:00:00 | 7        |
| 990004  | 1     | 2023-04-22T00:00:00 | 2024-05-11T00:00:00 | 1        |
| 990003  | 1     | 2023-04-23T00:00:00 | 2024-05-11T00:00:00 | 2        |
| 990002  | 1     | 2023-04-24T00:00:00 | 2024-05-11T00:00:00 | 3        |
| 990001  | 1     | 2023-04-25T00:00:00 | 2024-05-11T00:00:00 | 4        |

