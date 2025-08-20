package com.bennu.app;

import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FolderFileManager {

    public static final String FOLDER = "archivos";

    public static void createFolderForTxt(){
        File folder = new File(FOLDER);
        if (!folder.exists()){
            folder.mkdir();
        }
    }

    public static void createTxtFile(String fileName){
        createFolderForTxt();

        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }

        String path = getFilePath(fileName);

        File file = new File(path);

        if (file.exists()) {
            System.out.println("No se pudo crear el archivo: ya existe un archivo con ese nombre.");
            return;
        }

        if (!file.exists()){
            try(FileWriter fw = new FileWriter(file)){
                System.out.println("Archivo: " + fileName + " creado.");

            }
            catch (IOException e){
                System.out.println("Error al crear el archivo. Verifique permisos de acceso.");
                e.printStackTrace();
            }
        }
    }

    public static String getFilePath(String fileName) {
        return FOLDER + File.separator + fileName;
    }

    private static File[] verifyFolderExists() {
        File folder = new File(FOLDER);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("La carpeta 'archivos' no existe.");
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No hay archivos .txt en la carpeta, por favor cree un archivo desde el Menu.");
            return null;
        }
        return files;
    }

    public static void listFiles(File[] files) {
        System.out.println("Archivos disponibles:");
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            System.out.println((i + 1) + " - " + files[i].getName());
        }
    }

    private static void listNumbersInFile(File selectedFile) {
        if (selectedFile == null || !selectedFile.exists() || !selectedFile.isFile()) {
            System.out.println("El archivo seleccionado no existe o no es válido.");
            return;
        }

        final int pageSize = 50;

        try {
            String content = Files.readString(selectedFile.toPath());
            if (content == null || content.trim().isEmpty()) {
                System.out.println("El archivo está vacío.");
                return;
            }

            String[] numbers = content.trim().split("[,\\s]+");

            int totalPages = (int) Math.ceil(numbers.length / (double) pageSize);
            Scanner sc = new Scanner(System.in);

            for (int page = 0; page < totalPages; page++) {
                System.out.println("Números en '" + selectedFile.getName() + "' (página " + (page + 1) + " de " + totalPages + "):");

                int start = page * pageSize;
                int end = Math.min(start + pageSize, numbers.length);
                for (int i = start; i < end; i++) {
                    System.out.println(numbers[i]);
                }

                if (page < totalPages - 1) {
                    System.out.println("-- Presione Enter para ver más o 0 para volver al menú --");
                    String input = sc.nextLine();
                    if ("0".equals(input)) {
                        System.out.println("Volviendo al menú...");
                        return;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
        }
    }

    public static void selectFileAndRead() {
        File[] files = verifyFolderExists();
        if (files == null) return;

        listFiles(files);

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el número del archivo que desea leer: ");
        int option = sc.nextInt();

        if (option < 1 || option > files.length) {
            System.out.println("Número inválido.");
            return;
        }

        File selectedFile = files[option - 1];

        sc.nextLine();
        listNumbersInFile(selectedFile);
    }

    public static void addRandomNumberToATxtFile() {
        File[] files = verifyFolderExists();
        if (files == null) return;

        listFiles(files);

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el número del archivo donde desea agregar los números aleatorios: ");
        int option = sc.nextInt();

        if (option < 1 || option > files.length) {
            System.out.println("Archivo inválido.");
            return;
        }

        File selectedFile = files[option - 1];

        System.out.print("Ingrese la cantidad de números aleatorios que desea agregar: ");
        int numberOfNumbers = sc.nextInt();

        try (FileWriter fw = new FileWriter(selectedFile, true)) {
            for (int i = 0; i < numberOfNumbers; i++) {
                int randomNumber = new Random().nextInt(1_000_000);
                if (selectedFile.length() > 0 || i > 0) {
                    fw.write(','); // separar por comas
                }
                fw.write(String.valueOf(randomNumber));
            }
            System.out.println(numberOfNumbers + " número(s) agregados al archivo '" + selectedFile.getName() + "'");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo. Verifique permisos de acceso.");
            e.printStackTrace();
        }
    }

    public static List<Integer> readNumbersFromFile(File file) {
        List<Integer> numbers = new ArrayList<>();
        try {
            String content = Files.readString(file.toPath());
            String[] parts = content.split("[,\\s]+");
            for (String p : parts) {
                if (!p.isBlank()) {
                    numbers.add(Integer.parseInt(p.trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
        return numbers;
    }
}
