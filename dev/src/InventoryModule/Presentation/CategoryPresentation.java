package InventoryModule.Presentation;

import InventoryModule.Business.Controllers.CategoryController;

import java.util.Scanner;

public class CategoryPresentation {
    public CategoryController categoryController;
    Scanner reader = null;
    public CategoryPresentation(){
        categoryController =CategoryController.getInstance();
    }

    public void ShowCategoryMenu(){
        String c4 ="";
        while (!c4.equals("0")) {
            Scanner option4 = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Get all category names");
            System.out.println("2. Add new category");
            System.out.println("3. Remove category");
            System.out.println("0. Exit");
            c4 = option4.nextLine();
            switch (c4) {
                case "1": //Get all category names
                    categoryController.PrintCategorysInSystem();
                    break;
                case "2": //Add new category
                    System.out.println("Please enter new Category name:");
                    String cat = reader.nextLine();
                    CategoryController.addCategory(cat);
                    break;
                case "3": //Remove category
                    System.out.println("Please enter Category name to remove:");
                    String cat3 = reader.nextLine();
                    categoryController.removeCategory(cat3);
                    break;
            }
        }
    }

    public void ShowSubCategory(){
        String c5 ="";
        while (!c5.equals("0")) {
            Scanner option5 = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Add new subcategory");
            System.out.println("2. Remove subcategory");
            System.out.println("0. Exit");
            c5 = option5.nextLine();
            switch (c5) {
                case "1": //Add new subcategory
                    System.out.println("Please enter new Category name:");
                    String cat = reader.nextLine();
                    System.out.println("Please enter new SubCategory name:");
                    String subcat = reader.nextLine();
                    CategoryController.addSubCategory(cat,subcat);
                    break;
                case "2": //Remove subcategory
                    System.out.println("Please enter Category name:");
                    String cat2 = reader.nextLine();
                    System.out.println("Please enter SubCategory name to remove:");
                    String subcat2 = reader.nextLine();
                    categoryController.removeSubCategory(cat2,subcat2);
                    break;
            }
        }
    }

    public void ShowSubSubCategory(){
        String c6 ="";
        while (!c6.equals("0")) {
            Scanner option6 = new Scanner(System.in);
            System.out.println("Please choose an option");
            System.out.println("1. Add new subsubcategory");
            System.out.println("2. Remove subsubcategory");
            System.out.println("0. Exit");
            c6 = option6.nextLine();
            switch (c6) {
                case "1": //Add new subsubcategory
                    System.out.println("Please enter new Category name:");
                    String cat1 = reader.nextLine();
                    System.out.println("Please enter new SubCategory name:");
                    String subcat1 = reader.nextLine();
                    System.out.println("Please enter new SubCategory name:");
                    String subsubcat1 = reader.nextLine();
                    CategoryController.addSubSubCategory(subcat1, subsubcat1, cat1);
                    break;
                case "2": //Remove subsubcategory
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
}
