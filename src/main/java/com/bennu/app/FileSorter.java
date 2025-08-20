package com.bennu.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.bennu.app.FolderFileManager.readNumbersFromFile;

public class FileSorter {
    public static void insertionSort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            int key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    public static List<Integer> readNumbersFromTxtFile(File file){
        List<Integer> numbers = new ArrayList<>();
        try {
            String content = Files.readString(file.toPath());
            String[] parts = content.split("[,\\s]+");
            for (String p : parts) {
                if (!p.isBlank()) {
                    numbers.add(Integer.parseInt(p.trim()));
                }
            }
        } catch (IOException e){
            System.out.println("Error al leer el archivo" + e.getMessage());
        }
        return numbers;
    }

    private static void saveNumbersToFile(List<Integer> numbers, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < numbers.size(); i++) {
                writer.write(numbers.get(i).toString());
                if (i < numbers.size() - 1) {
                    writer.write(", ");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }

    public static void sortFileWithInsertion(){
        File folder = new File("archivos");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No hay archivos disponibles.");
            return;
        }

        FolderFileManager.listFiles(files);

        Scanner sc = new Scanner(System.in);
        System.out.print("Seleccione el número del archivo a ordenar: ");
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

        insertionSort(numbers);

        String newFileName = selectedFile.getName().replace(".txt", "_ordenado.txt");
        File newFile = new File(selectedFile.getParent(), newFileName);

        saveNumbersToFile(numbers, newFile);

        System.out.println("Archivo ordenado creado: " + newFile.getAbsolutePath());
    }
}
