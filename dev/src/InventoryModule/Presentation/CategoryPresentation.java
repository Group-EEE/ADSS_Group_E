package InventoryModule.Presentation;

import InventoryModule.CategoryController;

import java.util.Scanner;

public class CategoryPresentation {
    public CategoryController categoryController;
    Scanner reader = null;
    public CategoryPresentation(){
        categoryController =CategoryController.getInstance();
    }

    public void ShowCategoryMenu(){
        Scanner option4 = new Scanner(System.in);
        System.out.println("Please choose an option");
        System.out.println("1. Get all category names");
        System.out.println("2. Add new category");
        System.out.println("3. Remove category");
        int c4 = option4.nextInt();
        switch (c4) {
            case 1: //Get all category names
                categoryController.PrintCategorysInSystem();
                break;
            case 2: //Add new category
                System.out.println("Please enter new Category name:");
                String cat = reader.nextLine();
                CategoryController.addCategory(cat);
                break;
            case 3: //Remove category
                System.out.println("Please enter Category name to remove:");
                String cat3 = reader.nextLine();
                categoryController.removeCategory(cat3);
                break;
        }
    }

    public void ShowSubCategory(){
        Scanner option5 = new Scanner(System.in);
        System.out.println("Please choose an option");
        System.out.println("1. Add new subcategory");
        System.out.println("2. Remove subcategory");
        int c5 = option5.nextInt();
        switch (c5) {
            case 1: //Add new subcategory
                System.out.println("Please enter new Category name:");
                String cat = reader.nextLine();
                System.out.println("Please enter new SubCategory name:");
                String subcat = reader.nextLine();
                CategoryController.addSubCategory(cat,subcat);
                break;
            case 2: //Remove subcategory
                System.out.println("Please enter Category name:");
                String cat2 = reader.nextLine();
                System.out.println("Please enter SubCategory name to remove:");
                String subcat2 = reader.nextLine();
                categoryController.removeSubCategory(cat2,subcat2);
                break;
        }
    }

    public void ShowSubSubCategory(){
        Scanner option6 = new Scanner(System.in);
        System.out.println("Please choose an option");
        System.out.println("1. Add new subsubcategory");
        System.out.println("2. Remove subsubcategory");
        int c6 = option6.nextInt();
        switch (c6) {
            case 1: //Add new subsubcategory
                System.out.println("Please enter new SubCategory name:");
                String subcat1 = reader.nextLine();
                System.out.println("Please enter new SubCategory name:");
                String subsubcat1 = reader.nextLine();
                CategoryController.addSubSubCategory(subcat1, subsubcat1);
                break;
            case 2: //Remove subsubcategory
                System.out.println("Please enter Category name:");
                String cat2 = reader.nextLine();
                System.out.println("Please enter SubCategory name:");
                String subcat2 = reader.nextLine();
                System.out.println("Please enter SubCategory name to remove:");
                String subsubcat2 = reader.nextLine();
                categoryController.removeSubSubCategory(subsubcat2, subcat2, cat2);
                break;
        }
    }
}
