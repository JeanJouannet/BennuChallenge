package com.bennu.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

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

    private static void listFiles(File[] files) {
        System.out.println("Archivos disponibles:");
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            System.out.println((i + 1) + " - " + files[i].getName());
        }
    }

    public static void listTxtFilesToSelectAndRead(){
        File[] files = verifyFolderExists();
        if (files == null) {
            return;
        }

        listFiles(files);

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el número del archivo que desea leer: ");
        int option = sc.nextInt();

        if (option < 1 || option > files.length) {
            System.out.println("Número inválido.");
            return;
        }

        File selectedFile = files[option - 1];

        try {
            String contenido = Files.readString(selectedFile.toPath());
            System.out.println("Contenido del archivo '" + selectedFile.getName() + "':");
            System.out.println("--------------------------------------");
            System.out.println(contenido);
            System.out.println("--------------------------------------");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
        }
    }

    public static void addRandomNumberToATxtFile(){
        File[] files = verifyFolderExists();
        if (files == null) {
            return;
        }
        listFiles(files);

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el número del archivo donde desea agregar los números aleatorios: ");
        int option = sc.nextInt();

        if (option < 1 || option > files.length) {
            System.out.println("Número inválido.");
            return;
        }

    }
}
