package com.bennu.app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        Banner.printBanner();
        System.out.println("Bienvenido al generador de numeros aleatorios de Bennu.");
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
                    FolderFileManager.addRandomNumberToATxtFile();
                }
                case 3 -> {
                    FolderFileManager.selectFileAndRead();
                }
                case 4 -> {
                    FileSorter.sortFileWithInsertion();
                }
                case 5 -> {
                    FileSearcher.searchNumberInFile();
                }
                case 6 -> {}

            }
        }
        while(option != 7);
    }

}
