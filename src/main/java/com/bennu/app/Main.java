package com.bennu.app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();

        int option;
        do{
            menu.showMenu();
            System.out.println("Elige una opción: ");
            option = scanner.nextInt();
            FolderFileManager.createFolderForTxt();
            switch(option){
                case 0 -> menu.showMenu();
                case 1 -> {
                    System.out.println("Ingrese el nombre del archivo: ");
                    String name = scanner.next();
                    FolderFileManager.createTxtFile(name);
                }
                case 2 -> {

                }
                case 3 -> {
                    FolderFileManager.listTxtFilesToSelectAndRead();
                }
                case 4 -> {}
                case 5 -> {}
                case 6 -> {}

            }
        }
        while(option != 7);
    }

}
