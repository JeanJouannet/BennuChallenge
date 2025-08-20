package com.bennu.app;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static com.bennu.app.FolderFileManager.readNumbersFromFile;

public class FileSearcher {
    public static void searchNumberInFile() {
        File folder = new File("archivos");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No hay archivos disponibles.");
            return;
        }

        FolderFileManager.listFiles(files);

        Scanner sc = new Scanner(System.in);
        System.out.print("Seleccione el número del archivo: ");
        int option = sc.nextInt();

        if (option < 1 || option > files.length) {
            System.out.println("Opción inválida.");
            return;
        }

        File selectedFile = files[option - 1];
        List<Integer> numbers = readNumbersFromFile(selectedFile);

        if (numbers.isEmpty()) {
            System.out.println("El archivo está vacío o no contiene números.");
            return;
        }

        System.out.print("Ingrese el número a buscar: ");
        int numberToSearch = sc.nextInt();

        if (numbers.contains(numberToSearch)) {
            System.out.println("Número " + numberToSearch + " encontrado en el archivo '" + selectedFile.getName() + "'");
        } else {
            System.out.println("Número " + numberToSearch + " No encontrado en el archivo '" + selectedFile.getName() + "'");
        }
    }
}
